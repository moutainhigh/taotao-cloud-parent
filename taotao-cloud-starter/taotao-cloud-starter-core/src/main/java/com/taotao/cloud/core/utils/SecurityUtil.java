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
package com.taotao.cloud.core.utils;

import cn.hutool.core.util.CharsetUtil;
import com.taotao.cloud.common.utils.GsonUtil;
import com.taotao.cloud.core.model.Result;
import com.taotao.cloud.core.model.SecurityUser;
import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;

/**
 * 安全服务工具类
 *
 * @author dengtao
 * @date 2020/4/30 10:39
 * @since v1.0
 */
@UtilityClass
public class SecurityUtil {

    /**
     * 回写数据
     *
     * @param result   result
     * @param response response
     * @return void
     * @author dengtao
     * @date 2020/10/15 15:54
     * @since v1.0
     */
    public void writeResponse(Result<?> result, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter printWriter = response.getWriter();
        printWriter.write(GsonUtil.toGson(result));
        printWriter.flush();
    }

    /**
     * 获取认证信息
     *
     * @return org.springframework.security.core.Authentication
     * @author dengtao
     * @date 2020/10/15 15:54
     * @since v1.0
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户信息
     *
     * @param authentication 认证信息
     * @return com.taotao.cloud.core.model.SecurityUser
     * @author dengtao
     * @date 2020/10/15 15:54
     * @since v1.0
     */
    public SecurityUser getUser(Authentication authentication) {
        if (Objects.isNull(authentication)) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof SecurityUser) {
            return (SecurityUser) principal;
        } else if (principal instanceof Map) {
            return GsonUtil.gson().fromJson(GsonUtil.toGson(principal), SecurityUser.class);
        }
        return null;
    }

    /**
     * 获取用户信息
     *
     * @return com.taotao.cloud.core.model.SecurityUser
     * @author dengtao
     * @date 2020/10/15 15:55
     * @since v1.0
     */
    public SecurityUser getUser() {
        Authentication authentication = getAuthentication();
        return getUser(authentication);
    }

    /**
     * 获取用户姓名
     *
     * @return java.lang.String
     * @author dengtao
     * @date 2020/10/15 15:55
     * @since v1.0
     */
    public String getUsername() {
        SecurityUser user = getUser();
        return Objects.isNull(user) ? "" : user.getUsername();
    }

    /**
     * 获取用户id
     *
     * @return java.lang.String
     * @author dengtao
     * @date 2020/10/15 15:55
     * @since v1.0
     */
    public String getUserId() {
        SecurityUser user = getUser();
        return Objects.isNull(user) ? "" : String.valueOf(user.getUserId());
    }

    /**
     * 获取客户端id
     *
     * @return java.lang.String
     * @author dengtao
     * @date 2020/10/15 15:55
     * @since v1.0
     */
    public String getClientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
            return auth2Authentication.getOAuth2Request().getClientId();
        }
        return null;
    }
}
