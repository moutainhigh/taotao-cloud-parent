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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.taotao.cloud.common.constant.CommonConstant;
import com.taotao.cloud.common.enums.ResultEnum;
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
     * 返回数据
     */
    private T data;
    /**
     * 返回code
     */
    private Integer code;
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public Result() {
    }

    public Result(T data, Integer code, String message, String requestId, LocalDateTime timestamp) {
        this.data = data;
        this.code = code;
        this.message = message;
        this.requestId = requestId;
        this.timestamp = timestamp;
    }

    private static <T> Result<T> of(T data, Integer code, String msg) {
        return Result.<T>builder()
                .code(code)
                .message(msg)
                .data(data)
                .timestamp(LocalDateTime.now())
                .requestId(MDC.get(CommonConstant.TRACE_ID))
                .build();
    }

    public static <T> Result<T> succeed(T data) {
        return of(data, ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
    }

    public static <T> Result<T> succeed(T data, String msg) {
        return of(data, ResultEnum.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> succeed(T data, Integer code, String msg) {
        return of(data, code, msg);
    }

    public static <T> Result<T> succeed(T data, ResultEnum resultEnum) {
        return of(data, resultEnum.getCode(), resultEnum.getMessage());
    }

    public static <T> Result<T> succeed(ResultEnum resultEnum) {
        return of(null, resultEnum.getCode(), resultEnum.getMessage());
    }


    public static Result<String> failed() {
        return of(null, ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage());
    }

    public static <T> Result<T> failed(T data) {
        return of(data, ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage());
    }

    public static <T> Result<T> failed(T data, Integer code) {
        return of(data, code, ResultEnum.ERROR.getMessage());
    }

    public static <T> Result<T> failed(Integer code, String msg) {
        return of(null, code, msg);
    }

    public static <T> Result<T> failed(T data, Integer code, String msg) {
        return of(data, code, msg);
    }

    public static Result<String> failed(ResultEnum resultEnum) {
        return of(null, resultEnum.getCode(), resultEnum.getMessage());
    }

    public static <T> Result<T> failed(T data, ResultEnum resultEnum) {
        return of(data, resultEnum.getCode(), resultEnum.getMessage());
    }

}
