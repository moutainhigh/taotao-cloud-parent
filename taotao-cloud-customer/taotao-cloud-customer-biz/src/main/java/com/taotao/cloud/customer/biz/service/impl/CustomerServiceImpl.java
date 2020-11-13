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
package com.taotao.cloud.customer.biz.service.impl;

import com.taotao.cloud.customer.biz.repository.CustomerRepository;
import com.taotao.cloud.customer.biz.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author dengtao
 * @date 2020/11/13 10:00
 * @since v1.0
 */
@AllArgsConstructor
@Service
public class CustomerServiceImpl implements ICustomerService {
	private final CustomerRepository customerRepository;
}
