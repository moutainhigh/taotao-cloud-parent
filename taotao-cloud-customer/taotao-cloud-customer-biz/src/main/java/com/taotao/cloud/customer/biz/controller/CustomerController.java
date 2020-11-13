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
package com.taotao.cloud.customer.biz.controller;

import com.taotao.cloud.customer.biz.service.ICustomerService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客服表管理API
 *
 * @author dengtao
 * @date 2020/11/13 09:58
 * @since v1.0
 */
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/customer")
@Api(value = "客服表管理API", tags = {"客服表管理API"})
public class CustomerController {
	private final ICustomerService customerService;
}
