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
package com.taotao.cloud.news.biz.service.impl;

import com.taotao.cloud.news.biz.entity.Withdraw;
import com.taotao.cloud.news.biz.repository.WithdrawRepository;
import com.taotao.cloud.news.biz.service.IWithdrawService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * WithdrawServiceImpl
 *
 * @author dengtao
 * @date 2020/11/13 10:00
 * @since v1.0
 */
@Service
@AllArgsConstructor
public class WithdrawServiceImpl implements IWithdrawService {

	private final WithdrawRepository withdrawRepository;

	@Override
	public Withdraw findWithdrawById(Long id) {
		return withdrawRepository.getOne(id);
	}
}
