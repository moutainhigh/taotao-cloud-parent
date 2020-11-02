/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.taotao.cloud.security.component;

import com.taotao.cloud.common.constant.CommonConstant;
import com.taotao.cloud.common.enums.ResultEnum;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * 添加CustomWebResponseExceptionTranslator，登录发生异常时指定exceptionTranslator
 *
 * @author dengtao
 * @date 2020/4/30 09:09
 * @since v1.0
 */
public class WebResponseExceptionTranslatorComponent extends DefaultWebResponseExceptionTranslator {
    public static final String BAD_MSG = "坏的凭证";

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        OAuth2Exception oAuth2Exception;
        if (e.getMessage() != null && e.getMessage().equals(BAD_MSG)) {
            oAuth2Exception = new InvalidGrantException("用户名或密码错误", e);
        } else if (e instanceof InternalAuthenticationServiceException) {
            oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
        } else if (e instanceof RedirectMismatchException) {
            oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
        } else if (e instanceof InvalidScopeException) {
            oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
        } else {
            oAuth2Exception = new UnsupportedResponseTypeException("登录失败:未查询到用户", e);
        }
        ResponseEntity<OAuth2Exception> response = super.translate(oAuth2Exception);
        ResponseEntity.status(200);
        response.getBody().addAdditionalInformation("code", ResultEnum.UNAUTHORIZED.getCode() + "");
        response.getBody().addAdditionalInformation("message", oAuth2Exception.getMessage());
        response.getBody().addAdditionalInformation("data", null);
        response.getBody().addAdditionalInformation("requestId", MDC.get(CommonConstant.TRACE_ID));
        response.getBody().addAdditionalInformation("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return response;
    }
}
