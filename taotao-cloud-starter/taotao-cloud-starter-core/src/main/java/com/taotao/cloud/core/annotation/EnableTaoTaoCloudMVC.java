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
package com.taotao.cloud.core.annotation;

import com.taotao.cloud.core.lock.LockAspect;
import com.taotao.cloud.core.mvc.DefaultExceptionAdvice;
import com.taotao.cloud.core.mvc.TaotaoCloudJackson2ObjectMapperBuilderCustomizer;
import com.taotao.cloud.core.mvc.WebMvcConfiguration;
import com.taotao.cloud.core.mvc.filter.LbIsolationFilter;
import com.taotao.cloud.core.mvc.filter.TenantFilter;
import com.taotao.cloud.core.mvc.filter.TraceFilter;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启mvc
 *
 * @author dengtao
 * @date 2020/5/3 07:47
 * @since v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@RestControllerAdvice
@Import({
	DefaultExceptionAdvice.class,
	TaotaoCloudJackson2ObjectMapperBuilderCustomizer.class,
	WebMvcConfiguration.class,
	LockAspect.class,
	LbIsolationFilter.class,
	TenantFilter.class,
	TraceFilter.class
})
public @interface EnableTaoTaoCloudMVC {

}
