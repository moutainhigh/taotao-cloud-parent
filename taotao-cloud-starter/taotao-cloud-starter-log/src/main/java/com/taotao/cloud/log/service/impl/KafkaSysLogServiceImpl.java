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
package com.taotao.cloud.log.service.impl;

import com.taotao.cloud.common.utils.GsonUtil;
import com.taotao.cloud.log.model.SysLog;
import com.taotao.cloud.log.service.ISysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 审计日志实现类-Kafka
 *
 * @author dengtao
 * @date 2020/5/2 11:18
 * @since v1.0
 */
@Slf4j
public class KafkaSysLogServiceImpl implements ISysLogService {

    @Value("${spring.application.name:----}")
    private String appName;

    public static final String SYS_LOG_TOPIC = "taotao-cloud-business-log-topic-";

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void save(SysLog sysLog) {
        String obj = GsonUtil.toGson(sysLog);
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
        String topic = SYS_LOG_TOPIC.concat(appName).concat("-").concat(format);
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, obj);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("kafka主题: {}, 远程日志记录失败：{}", topic, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                log.info("kafka主题: {}, 远程日志记录成功：{}", topic, sysLog.getActionUrl());
            }
        });
    }
}

