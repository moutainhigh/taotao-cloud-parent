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
package com.taotao.cloud.ws.controller;

import com.taotao.cloud.ws.model.MessageParam;
import com.taotao.cloud.ws.model.MessageResult;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author dengtao
 * @date 2020/12/18 下午3:20
 * @since v1.0
 */
@Controller
public class WebSocketController {

	@MessageMapping("/hello")
	@SendTo("/topic/messages")
	public MessageResult test(@RequestBody MessageParam message) {
		return new MessageResult("：" + message.getContent());
	}

	@Scheduled(fixedRate = 5000)
	@SendTo("/topic/messages")
	public MessageResult test() {
		System.out.println("lfasldkfjlsd");
		return new MessageResult( ":我是服务器的数据");
	}

	// @Scheduled(fixedRate = 5000)
	// @SubscribeMapping("/topic/getResponse")
	// public MessageResult sub() {
	// 	return new MessageResult( ":我是服务器的数据");
	// }
}
