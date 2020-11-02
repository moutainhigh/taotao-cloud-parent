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
package com.taotao.cloud.ribbon.component;

import com.taotao.cloud.common.constant.StarterNameConstant;
import com.taotao.cloud.ribbon.filter.LbIsolationFilter;
import com.taotao.cloud.ribbon.filter.TenantFilter;
import com.taotao.cloud.ribbon.filter.TraceFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.DefaultPropertiesFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Ribbon扩展配置类
 *
 * @author dengtao
 * @date 2020/6/15 11:31
 * @since v1.0
 */
@Slf4j
public class RibbonComponent implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("[TAOTAO CLOUD][" + StarterNameConstant.TAOTAO_CLOUD_RIBBON_STARTER + "]" + "ribbon模块已启动");
    }

    @Bean
    public DefaultPropertiesFactory defaultPropertiesFactory() {
        return new DefaultPropertiesFactory();
    }

    @Bean
    @Order(0)
    public TraceFilter traceFilter() {
        return new TraceFilter();
    }

    @Bean
    @Order(1)
    public TenantFilter tenantFilter() {
        return new TenantFilter();
    }

    @Bean
    @Order(2)
    public LbIsolationFilter lbIsolationFilter() {
        return new LbIsolationFilter();
    }

    // @Bean
    // @Order(3)
    // public AuthFilter authFilter() {
    //     return new AuthFilter();
    // }

    /**
     * httpclient 实现的ClientHttpRequestFactory
     */
    @Bean
    public ClientHttpRequestFactory httpRequestFactory(@Autowired HttpClient httpClient) {
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new OkHttp3ClientHttpRequestFactory());
        return restTemplate;
    }

}
