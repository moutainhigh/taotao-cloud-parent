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
package com.taotao.cloud.pay.biz.controller;

import com.taotao.cloud.core.model.Result;
import com.taotao.cloud.log.annotation.SysOperateLog;
import com.taotao.cloud.pay.api.vo.PayFlowVO;
import com.taotao.cloud.pay.biz.entity.PayFlow;
import com.taotao.cloud.pay.biz.mapper.PayFlowMapper;
import com.taotao.cloud.pay.biz.service.IPayFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付流水管理API
 *
 * @author dengtao
 * @date 2020/11/13 09:58
 * @since v1.0
 */
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/pay/flow")
@Api(value = "支付流水管理API", tags = {"支付流水管理API"})
public class PayFlowController {

	private final IPayFlowService payFlowService;

	@ApiOperation("根据id查询支付信息")
	@SysOperateLog(description = "根据id查询支付信息")
	@PreAuthorize("hasAuthority('pag:flow:info:id')")
	@GetMapping("/info/id/{id:[0-9]*}")
	public Result<PayFlowVO> findPayFlowById(@PathVariable(value = "id") Long id) {
		PayFlow payFlow = payFlowService.findPayFlowById(id);
		PayFlowVO vo = PayFlowMapper.INSTANCE.payFlowToPayFlowVO(payFlow);
		return Result.succeed(vo);
	}

}
