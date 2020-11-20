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
package com.taotao.cloud.pay.biz.service;

import com.taotao.cloud.pay.biz.entity.PayFlow;

/**
 * @author dengtao
 * @date 2020/11/13 09:59
 * @since v1.0
 */
public interface IPayFlowService {

	/**
	 * 根据id查询支付信息
	 *
	 * @param id id
	 * @return com.taotao.cloud.log.biz.entity.MemberLogin
	 * @author dengtao
	 * @date 2020/11/20 下午1:08
	 * @since v1.0
	 */
	PayFlow findPayFlowById(Long id);
}
