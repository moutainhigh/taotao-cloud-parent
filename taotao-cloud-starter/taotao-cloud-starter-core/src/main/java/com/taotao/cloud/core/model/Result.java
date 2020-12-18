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
package com.taotao.cloud.core.model;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.taotao.cloud.common.constant.CommonConstant;
import com.taotao.cloud.common.enums.ResultEnum;
import com.taotao.cloud.common.utils.IdGeneratorUtil;
import lombok.Builder;
import lombok.Data;
import org.slf4j.MDC;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 返回实体类
 *
 * @author dengtao
 * @date 2020/4/29 15:15
 * @since v1.0
 */
@Data
@Builder
public class Result<T> implements Serializable {

	private static final long serialVersionUID = -3685249101751401211L;
	/**
	 * 返回code
	 */
	private Integer code;
	/**
	 * 返回数据
	 */
	private T data;
	/**
	 * 消息类型 success error
	 */
	private String type;
	/**
	 * 消息体
	 */
	private String message;
	/**
	 * 请求id
	 */
	private String requestId;
	/**
	 * 请求结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timestamp;

	public Result() {
	}

	public Result(Integer code, T data, String type, String message, String requestId, LocalDateTime timestamp) {
		this.code = code;
		this.data = data;
		this.type = type;
		this.message = message;
		this.requestId = requestId;
		this.timestamp = timestamp;
	}

	private static <T> Result<T> of(Integer code, T data, String type, String msg) {
		return Result.<T>builder()
			.code(code)
			.data(data)
			.type(type)
			.message(msg)
			.timestamp(LocalDateTime.now())
			.requestId(StrUtil.isNotBlank(MDC.get(CommonConstant.TRACE_ID)) ? MDC.get(CommonConstant.TRACE_ID) : IdGeneratorUtil.getIdStr())
			.build();
	}

	public static <T> Result<T> succeed(T data) {
		return of(ResultEnum.SUCCESS.getCode(), data, CommonConstant.SUCCESS, ResultEnum.SUCCESS.getMessage());
	}

	public static <T> Result<T> succeed(T data, String msg) {
		return of(ResultEnum.SUCCESS.getCode(), data, CommonConstant.SUCCESS, msg);
	}

	public static <T> Result<T> succeed(T data, Integer code, String msg) {
		return of(code, data, CommonConstant.SUCCESS, msg);
	}

	public static <T> Result<T> succeed(T data, ResultEnum resultEnum) {
		return of(resultEnum.getCode(), data, CommonConstant.SUCCESS, resultEnum.getMessage());
	}

	public static <T> Result<T> succeed(ResultEnum resultEnum) {
		return of(resultEnum.getCode(), null, CommonConstant.SUCCESS, resultEnum.getMessage());
	}

	public static Result<String> failed() {
		return of(ResultEnum.ERROR.getCode(), null, CommonConstant.ERROR, ResultEnum.ERROR.getMessage());
	}

	public static <T> Result<T> failed(T data) {
		return of(ResultEnum.ERROR.getCode(), data, CommonConstant.ERROR, ResultEnum.ERROR.getMessage());
	}

	public static <T> Result<T> failed(T data, Integer code) {
		return of(code, data, CommonConstant.ERROR, ResultEnum.ERROR.getMessage());
	}

	public static <T> Result<T> failed(Integer code, String msg) {
		return of(code, null, CommonConstant.ERROR, msg);
	}

	public static <T> Result<T> failed(T data, Integer code, String msg) {
		return of(code, data, CommonConstant.ERROR, msg);
	}

	public static Result<String> failed(ResultEnum resultEnum) {
		return of(resultEnum.getCode(), null, CommonConstant.ERROR, resultEnum.getMessage());
	}

	public static <T> Result<T> failed(T data, ResultEnum resultEnum) {
		return of(resultEnum.getCode(), data, CommonConstant.ERROR, resultEnum.getMessage());
	}
}
