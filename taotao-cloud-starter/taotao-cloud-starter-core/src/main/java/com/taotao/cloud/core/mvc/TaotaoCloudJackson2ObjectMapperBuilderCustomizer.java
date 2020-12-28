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
package com.taotao.cloud.core.mvc;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taotao.cloud.common.constant.CommonConstant;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义LocalDateTime -> jackson
 *
 * @author dengtao
 * @date 2020/9/29 11:18
 * @since v1.0
 */
public class TaotaoCloudJackson2ObjectMapperBuilderCustomizer {

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
		return customizer -> {
			customizer.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(
				DateTimeFormatter.ofPattern(CommonConstant.DATETIME_FORMAT)));
			customizer.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(
				DateTimeFormatter.ofPattern(CommonConstant.DATETIME_FORMAT)));
		};
	}
}