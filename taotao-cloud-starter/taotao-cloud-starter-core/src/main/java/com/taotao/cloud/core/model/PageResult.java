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

import com.taotao.cloud.common.constant.CommonConstant;
import com.taotao.cloud.common.enums.ResultEnum;
import lombok.Builder;
import lombok.Data;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 返回分页实体类
 *
 * @author dengtao
 * @date 2020/4/29 15:40
 * @since v1.0
 */
@Data
@Builder
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = -275582248840137389L;

    private int code;
    private String message;
    private long total;
    private long pageSize;
    private long currentPage;
    private List<T> data;
    private String requestId;
    private LocalDateTime timestamp;

    public PageResult() {
    }

    public PageResult(int code, String message, long total, long pageSize, long currentPage, List<T> data, String requestId, LocalDateTime timestamp) {
        this.code = code;
        this.message = message;
        this.total = total;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.data = data;
        this.requestId = requestId;
        this.timestamp = timestamp;
    }

    public static <T> PageResult<T> succeed(Page<T> page) {
        return of(ResultEnum.SUCCESS.getCode(),
                ResultEnum.SUCCESS.getMessage(),
                page.getTotalElements(),
                page.getSize(),
                page.getNumber(),
                page.getContent()
        );
    }

    public static <T> PageResult<T> succeed(Page<T> page, ResultEnum resultEnum) {
        return of(resultEnum.getCode(),
                resultEnum.getMessage(),
                page.getTotalElements(),
                page.getSize(),
                page.getNumber(),
                page.getContent());
    }

    public static <T> PageResult<T> succeed(long total,
                                            long pageSize,
                                            long currentPage,
                                            List<T> data) {
        return of(ResultEnum.SUCCESS.getCode(),
                ResultEnum.SUCCESS.getMessage(),
                total,
                pageSize,
                currentPage,
                data);
    }

    public static <T> PageResult<T> of(Integer code,
                                       String msg,
                                       long total,
                                       long pageSize,
                                       long currentPage,
                                       List<T> data) {
        return PageResult.<T>builder()
                .code(code)
                .message(msg)
                .total(total)
                .pageSize(pageSize)
                .currentPage(currentPage)
                .data(data)
                .timestamp(LocalDateTime.now())
                .requestId(MDC.get(CommonConstant.TRACE_ID))
                .build();
    }

}
