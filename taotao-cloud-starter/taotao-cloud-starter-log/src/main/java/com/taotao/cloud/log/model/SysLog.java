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
package com.taotao.cloud.log.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日志
 *
 * @author dengtao
 * @date 2020/6/15 11:00
 * @since v1.0
 */
@Data
public class SysLog implements Serializable {

    private static final long serialVersionUID = -749360940290141180L;

    /**
     * 请求日志id
     */
    private String traceId;

    /**
     * 服务名称
     */
    private String applicationName;

    /**
     * 操作IP
     */
    private String requestIp;

    /**
     * 操作类型 1 操作记录 2异常记录
     */
    private Integer type;

    /**
     * 操作人ID
     */
    private String userName;

    /**
     * 操作人ID
     */
    private String userId;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 请求方法
     */
    private String actionMethod;

    /**
     * 请求url
     */
    private String actionUrl;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 浏览器
     */
    private String ua;

    /**
     * 类路径
     */
    private String classPath;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 操作类型（1查询/获取，2添加，3修改，4删除）
     */
    private Integer operateType;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 完成时间
     */
    private LocalDateTime finishTime;

    /**
     * 消耗时间
     */
    private Long consumingTime;

    /**
     * 异常详情信息 堆栈信息
     */
    private String exDetail;

    /**
     * 异常描述 e.getMessage
     */
    private String exDesc;

    /**
     * 租户id
     */
    private String tenantId;
}
