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
package com.taotao.cloud.log.aspect;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.URLUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.baomidou.mybatisplus.extension.api.R;
import com.taotao.cloud.common.constant.CommonConstant;
import com.taotao.cloud.common.context.TenantContextHolder;
import com.taotao.cloud.common.enums.LogOperateTypeEnum;
import com.taotao.cloud.common.utils.AddrUtil;
import com.taotao.cloud.common.utils.LogUtil;
import com.taotao.cloud.core.utils.SecurityUtil;
import com.taotao.cloud.log.event.RequestLogEvent;
import com.taotao.cloud.log.model.RequestLog;
import com.taotao.cloud.log.properties.RequestLogProperties;
import com.taotao.cloud.log.utils.LoggerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;


/**
 * 日志切面
 * <p>
 * ①切面注解得到请求数据 -> ②发布监听事件 -> ③异步监听日志入库
 * </p>
 *
 * @author dengtao
 * @date 2020/6/3 11:47
 * @since v1.0
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class RequestLogAspect {

	@Value("${spring.application.name}")
	private String applicationName;

	@Resource
	private RequestLogProperties requestLogProperties;

	private final ApplicationEventPublisher publisher;

	/**
	 * log实体类
	 **/
	private final TransmittableThreadLocal<RequestLog> SYS_LOG_THREAD_LOCAL = new TransmittableThreadLocal<>();

	/**
	 * 事件发布是由ApplicationContext对象管控的，我们发布事件前需要注入ApplicationContext对象调用publishEvent方法完成事件发布
	 *
	 * @param publisher publisher
	 */
	public RequestLogAspect(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	/***
	 * 定义controller切入点拦截规则，拦截SysLog注解的方法
	 */
	@Pointcut("@annotation(com.taotao.cloud.log.annotation.RequestOperateLog)")
	public void requestLogAspect() {

	}

	/***
	 * 拦截控制层的操作日志
	 * @param joinPoint joinPoint
	 */
	@Before(value = "requestLogAspect()")
	public void recordLog(JoinPoint joinPoint) throws Throwable {
		if (requestLogProperties.getEnabled()) {
			RequestLog requestLog = new RequestLog();
			SYS_LOG_THREAD_LOCAL.set(requestLog);
			ServletRequestAttributes attributes = (ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
			RequestContextHolder.setRequestAttributes(attributes, true);
			HttpServletRequest request = attributes.getRequest();
			requestLog.setApplicationName(applicationName);
			requestLog.setStartTime(Timestamp.valueOf(LocalDateTime.now()).getTime());
			requestLog.setTraceId(MDC.get(CommonConstant.TRACE_ID));
			requestLog.setRequestIp(AddrUtil.getRemoteAddr(request));
			requestLog.setClientId(SecurityUtil.getClientId());
			requestLog.setUserId(SecurityUtil.getUserId());
			requestLog.setUsername(SecurityUtil.getUsername());
			requestLog.setActionUrl(URLUtil.getPath(request.getRequestURI()));
			requestLog.setRequestMethod(request.getMethod());
			requestLog.setUa(request.getHeader("user-agent"));
			Object[] args = joinPoint.getArgs();
			requestLog.setClasspath(joinPoint.getTarget().getClass().getName());
			String name = joinPoint.getSignature().getName();
			requestLog.setActionMethod(name);

			requestLog.setParams(Arrays.toString(args));
			requestLog.setOperateType(LogUtil.getOperateType(name));
			requestLog.setDescription(LoggerUtil.getControllerMethodDescription(joinPoint));
		}
	}

	@AfterReturning(returning = "ret", pointcut = "requestLogAspect()")
	public void doAfterReturning(Object ret) {
		RequestLog requestLog = SYS_LOG_THREAD_LOCAL.get();
		if (Objects.nonNull(requestLog)) {
			R r = Convert.convert(R.class, ret);
			if (r.getCode() == HttpStatus.OK.value()) {
				requestLog.setType(LogOperateTypeEnum.OPERATE_RECORD.getValue());
			} else {
				requestLog.setType(LogOperateTypeEnum.EXCEPTION_RECORD.getValue());
				requestLog.setExDetail(r.getMsg());
			}
			requestLog.setTenantId(TenantContextHolder.getTenant());
			requestLog.setFinishTime(Timestamp.valueOf(LocalDateTime.now()).getTime());
			long endTime = Instant.now().toEpochMilli();
			requestLog.setConsumingTime(endTime - requestLog.getStartTime());

			publisher.publishEvent(new RequestLogEvent(requestLog));
			SYS_LOG_THREAD_LOCAL.remove();
		}
	}

	@AfterThrowing(pointcut = "requestLogAspect()", throwing = "e")
	public void doAfterThrowable(Throwable e) {
		RequestLog requestLog = SYS_LOG_THREAD_LOCAL.get();
		if (Objects.nonNull(requestLog)) {
			requestLog.setType(LogOperateTypeEnum.EXCEPTION_RECORD.getValue());
			requestLog.setExDetail(LogUtil.getStackTrace(e));
			requestLog.setExDesc(e.getMessage());
			publisher.publishEvent(new RequestLogEvent(requestLog));
			SYS_LOG_THREAD_LOCAL.remove();
		}
	}
}
