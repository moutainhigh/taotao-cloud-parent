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
package com.taotao.cloud.log.component;

import com.taotao.cloud.common.constant.StarterNameConstant;
import com.taotao.cloud.core.annotation.EnableTaoTaoCloudAsync;
import com.taotao.cloud.log.aspect.SysLogAspect;
import com.taotao.cloud.log.listener.SysLogListener;
import com.taotao.cloud.log.service.impl.KafkaSysLogServiceImpl;
import com.taotao.cloud.log.service.impl.LoggerSysLogServiceImpl;
import com.taotao.cloud.log.service.impl.RedisSysLogServiceImpl;
import com.taotao.cloud.redis.repository.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;

/**
 * 当web项目引入此依赖时，自动配置对应的内容 初始化log的事件监听与切面配置
 *
 * @author dengtao
 * @date 2020/4/30 10:21
 * @since v1.0
 */
@Slf4j
@EnableTaoTaoCloudAsync
public class SysLogComponent implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(StarterNameConstant.TAOTAO_CLOUD_LOG_STARTER, "日志模块已启动");
    }

    @Bean
    public SysLogListener sysLogListener() {
        return new SysLogListener();
    }

    @Bean
    public SysLogAspect sysLogAspect(ApplicationEventPublisher publisher) {
        return new SysLogAspect(publisher);
    }

    @Bean
    @ConditionalOnProperty(prefix = "taotao.cloud.log", name = "type", havingValue = "logger", matchIfMissing = true)
    public LoggerSysLogServiceImpl loggerSysLogService() {
        return new LoggerSysLogServiceImpl();
    }

    @Bean
    @ConditionalOnProperty(prefix = "taotao.cloud.log", name = "type", havingValue = "redis")
    @ConditionalOnBean(value = {RedisRepository.class})
    public RedisSysLogServiceImpl redisSysLogService() {
        return new RedisSysLogServiceImpl();
    }

    @Bean
    @ConditionalOnProperty(prefix = "taotao.cloud.log", name = "type", havingValue = "kafka")
    public KafkaSysLogServiceImpl kafkaSysLogService() {
        return new KafkaSysLogServiceImpl();
    }

}

