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
package com.taotao.cloud.ribbon.fegin;

import cn.hutool.core.util.StrUtil;
import com.taotao.cloud.common.constant.CommonConstant;
import com.taotao.cloud.common.context.TenantContextHolder;
import feign.RequestInterceptor;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 * feign拦截器，只包含http相关数据
 *
 * @author dengtao
 * @date 2020/4/5 13:33
 * @since v1.0
 */
public class FeignHttpInterceptorConfig {

	protected List<String> requestHeaders = new ArrayList<>();

	@PostConstruct
	public void initialize() {
		requestHeaders.add(CommonConstant.USER_ID_HEADER);
		requestHeaders.add(CommonConstant.USER_NAME_HEADER);
		requestHeaders.add(CommonConstant.ROLE_HEADER);
		requestHeaders.add(CommonConstant.T_VERSION);
	}

	/**
	 * 使用feign client访问别的微服务时，将上游传过来的access_token、username、roles等信息放入header传递给下一个服务
	 */
	@Bean
	public RequestInterceptor httpFeignInterceptor() {
		return template -> {
			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			if (requestAttributes != null) {
				ServletRequestAttributes attributes = (ServletRequestAttributes) Objects.requireNonNull(requestAttributes);
				RequestContextHolder.setRequestAttributes(attributes, true);
				HttpServletRequest request = attributes.getRequest();
				Enumeration<String> headerNames = request.getHeaderNames();
				if (headerNames != null) {
					String headerName;
					String headerValue;

					while (headerNames.hasMoreElements()) {
						headerName = headerNames.nextElement();
						if (requestHeaders.contains(headerName)) {
							headerValue = request.getHeader(headerName);
							template.header(headerName, headerValue);
						}
					}
				}

				//传递client
				//传递access_token，无网络隔离时需要传递
				String tenant = TenantContextHolder.getTenant();
				if (StrUtil.isNotEmpty(tenant)) {
					template.header(CommonConstant.TENANT_HEADER, tenant);
				}

				//传递日志traceId
				String traceId = MDC.get(CommonConstant.TRACE_ID);
				if (StrUtil.isNotEmpty(traceId)) {
					template.header(CommonConstant.TRACE_HEADER, traceId);
				}
				String token = extractHeaderToken(request);
				if (StrUtil.isEmpty(token)) {
					token = request.getParameter(CommonConstant.ACCESS_TOKEN);
				}
				if (StrUtil.isNotEmpty(token)) {
					template.header(CommonConstant.TOKEN_HEADER, CommonConstant.BEARER_TYPE + " " + token);
				}
			}
		};
	}

	/**
	 * 解析head中的token
	 *
	 * @param request request
	 */
	private String extractHeaderToken(HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaders(CommonConstant.TOKEN_HEADER);
		while (headers.hasMoreElements()) {
			String value = headers.nextElement();
			if (value.startsWith(CommonConstant.BEARER_TYPE)) {
				String authHeaderValue = value.substring(CommonConstant.BEARER_TYPE.length()).trim();
				int commaIndex = authHeaderValue.indexOf(',');
				if (commaIndex > 0) {
					authHeaderValue = authHeaderValue.substring(0, commaIndex);
				}
				return authHeaderValue;
			}
		}
		return null;
	}
}
