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

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

import java.io.Serializable;

/**
 * 日志
 *
 * @author dengtao
 * @date 2020/6/15 11:00
 * @since v1.0
 */
@Data
public class RequestLog implements Serializable {

	private static final long serialVersionUID = -749360940290141180L;

	/**
	 * 请求日志id
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "trace_id")
	private String traceId;

	/**
	 * 服务名称
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "application_name")
	private String applicationName;

	/**
	 * 操作IP
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "request_ip")
	private String requestIp;

	/**
	 * 操作类型 1 操作记录 2异常记录
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "type")
	private Integer type;

	/**
	 * 操作人ID
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "username")
	private String username;

	/**
	 * 操作人ID
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "user_id")
	private Long userId;

	/**
	 * 客户端ID
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "client_id")
	private String clientId;

	/**
	 * 操作描述
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "description")
	private String description;

	/**
	 * 请求方法
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "action_method")
	private String actionMethod;

	/**
	 * 请求url
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "action_url")
	private String actionUrl;

	/**
	 * 方法参数
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "args")
	private String args;

	/**
	 * 请求参数
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "params")
	private String params;

	/**
	 * 请求头
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "headers")
	private String headers;

	/**
	 * 浏览器
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "ua")
	private String ua;

	/**
	 * 类路径
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "classpath")
	private String classpath;

	/**
	 * 请求方法
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "request_method")
	private String requestMethod;

	/**
	 * 操作类型（1查询/获取，2添加，3修改，4删除）
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "operate_type")
	private Integer operateType;

	/**
	 * 开始时间
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "start_time")
	private Long startTime;

	/**
	 * 完成时间
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "finish_time")
	private Long finishTime;

	/**
	 * 消耗时间
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "consuming_time")
	private Long consumingTime;

	/**
	 * 异常详情信息 堆栈信息
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "ex_detail")
	private String exDetail;

	/**
	 * 异常描述 e.getMessage
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "ex_desc")
	private String exDesc;

	/**
	 * 租户id
	 */
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue, name = "tenant_id")
	private String tenantId;


}
