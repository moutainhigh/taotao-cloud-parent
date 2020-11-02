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
package com.taotao.cloud.auth.biz.controller;

import com.taotao.cloud.auth.biz.service.ISmsCodeService;
import com.taotao.cloud.core.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 手机验证码管理API
 *
 * @author dengtao
 * @date 2020/4/29 16:07
 * @since v1.0
 */
@Validated
@RestController
@Api(value = "手机验证码管理API", tags = {"手机验证码管理API"})
public class SmsCodeController {

    @Autowired
    private ISmsCodeService validateCodeService;

    @ApiOperation(value = "发送手机验证码 后期要加接口限制")
    @GetMapping("/sms/code")
    public Result<Boolean> sendSmsCode(@NotBlank(message = "手机号码不能为空")
                                       @Pattern(regexp = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$", message = "手机号码格式错误")
                                       @RequestParam(value = "phone") String phone) {
        return validateCodeService.sendSmsCode(phone);
    }
}
