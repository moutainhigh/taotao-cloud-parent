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
package com.taotao.cloud.gateway;

import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.taotao.cloud.log.annotation.EnableTaoTaoCloudSysLog;
import com.taotao.cloud.redis.annotation.EnableTaoTaoCloudRedis;
import com.taotao.cloud.security.annotation.EnableTaoTaoCloudOAuth2RedisTokenStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;

/**
 * TaotaoCloudGatewayApplication
 *
 * @author dengtao
 * @date 2020/10/10 09:06
 * @since v1.0
 */
@EnableTaoTaoCloudSysLog
@EnableTaoTaoCloudRedis
@EnableTaoTaoCloudOAuth2RedisTokenStore
@SpringBootApplication
@EnableDiscoveryClient
public class TaotaoCloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaotaoCloudGatewayApplication.class, args);
    }

}
