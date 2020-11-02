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
package com.taotao.cloud.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.taotao.cloud.common.utils.LogUtil;
import com.taotao.cloud.core.model.Result;
import com.taotao.cloud.core.utils.ResponseUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * 限流、熔断统一处理类
 *
 * @author dengtao
 * @date 2020/5/13 17:32
 * @since v1.0
 */
public class SentinelAutoConfigure {
    @Bean
    @ConditionalOnClass(HttpServletRequest.class)
    public BlockExceptionHandler blockExceptionHandler() {
        LogUtil.info("-------------");
        return (request, response, e) -> {
            LogUtil.error("WebmvcHandler Sentinel调用失败: {0}", e);
            Result<String> result = Result.failed(e.getMessage());
            ResponseUtil.failed(response, result);
        };
    }

    @Bean
    @ConditionalOnClass(ServerResponse.class)
    public BlockRequestHandler blockRequestHandler() {
        return (exchange, e) -> {
            LogUtil.error("WebfluxHandler Sentinel调用失败: {0}", e);
            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(Result.failed(e.getMessage())));
        };
    }
}
