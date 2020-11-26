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

import com.alibaba.fastjson.JSON;
import com.taotao.cloud.log.model.RequestLog;
import com.taotao.cloud.log.service.IRequestLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

/**
 * 审计日志实现类-Kafka
 *
 * @author dengtao
 * @date 2020/5/2 11:18
 * @since v1.0
 */
@Slf4j
public class KafkaRequestLogServiceImpl implements IRequestLogService {

	public static final String REQUEST_LOG_TOPIC = "taotao-cloud-request-log";

	@Resource
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void save(RequestLog requestLog) {
		String request = JSON.toJSONString(requestLog);
		ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(REQUEST_LOG_TOPIC, request);
		future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
			@Override
			public void onFailure(Throwable throwable) {
				log.error("远程日志记录失败：{}", throwable.getMessage());
			}

			@Override
			public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
				log.info("远程日志记录成功：{}", requestLog);
			}
		});
	}
}

