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
package com.taotao.cloud.log.listener;

import com.taotao.cloud.core.utils.BeanUtil;
import com.taotao.cloud.log.event.RequestLogEvent;
import com.taotao.cloud.log.model.RequestLog;
import com.taotao.cloud.log.service.impl.KafkaRequestLogServiceImpl;
import com.taotao.cloud.log.service.impl.LoggerRequestLogServiceImpl;
import com.taotao.cloud.log.service.impl.RedisRequestLogServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * 注解形式的监听 异步监听日志事件
 *
 * @author dengtao
 * @date 2020/6/3 13:33
 * @since v1.0
 */
@Slf4j
public class RequestLogListener {

	@Async
	@EventListener(RequestLogEvent.class)
	public void saveRequestLog(RequestLogEvent event) {
		RequestLog requestLog = (RequestLog) event.getSource();
		KafkaRequestLogServiceImpl kafkaRequestLogService = BeanUtil.getBean(KafkaRequestLogServiceImpl.class, true);
		if (null != kafkaRequestLogService) {
			kafkaRequestLogService.save(requestLog);
		}

		LoggerRequestLogServiceImpl loggerRequestLogService = BeanUtil.getBean(LoggerRequestLogServiceImpl.class, true);
		if (null != loggerRequestLogService) {
			loggerRequestLogService.save(requestLog);
		}

		RedisRequestLogServiceImpl redisRequestLogService = BeanUtil.getBean(RedisRequestLogServiceImpl.class, true);
		if (null != redisRequestLogService) {
			redisRequestLogService.save(requestLog);
		}

		//sysLogService.save(requestLog);
	}
}
