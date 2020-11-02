package com.taotao.cloud.uc.biz.controller;

import com.taotao.cloud.core.model.Result;
import com.taotao.cloud.log.annotation.SysOperateLog;
import com.taotao.cloud.uc.api.dto.member.MemberUserDTO;
import com.taotao.cloud.uc.api.query.member.MemberQuery;
import com.taotao.cloud.uc.biz.entity.MemberUser;
import com.taotao.cloud.uc.biz.service.IMemberUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * 会员管理API
 *
 * @author dengtao
 * @date 2020-10-16 16:23:49
 * @since 1.0
 */
@Validated
@RestController
@RequestMapping("/member")
@Api(value = "会员管理API", tags = {"会员管理API"})
@AllArgsConstructor
public class MemberUserController {
    private final IMemberUserService memberUserService;

    @ApiOperation("查询会员是否已(注册)存在")
    @SysOperateLog(description = "查询会员是否已(注册)存在")
    @GetMapping("/exist")
    public Result<Boolean> existMember(@Validated @NotNull(message = "查询条件不能为空") MemberQuery memberQuery) {
        Boolean result = memberUserService.existMember(memberQuery);
        return Result.succeed(result);
    }

    @ApiOperation("注册新会员用户")
    @SysOperateLog(description = "注册新会员用户")
    @PostMapping
    public Result<Boolean> registerUser(@Validated @RequestBody MemberUserDTO memberDTO) {
        MemberUser result = memberUserService.registerUser(memberDTO);
        return Result.succeed(Objects.nonNull(result));
    }

    // **********************内部微服务接口*****************************

    @ApiIgnore
    @ApiOperation("查询会员用户")
    @SysOperateLog(description = "查询会员用户")
    @GetMapping("/info/security")
    public Result<MemberUser> findMember(@Validated @NotBlank(message = "查询条件不能为空")
                                         @RequestParam(value = "nicknameOrUserNameOrPhoneOrEmail") String nicknameOrUserNameOrPhoneOrEmail) {
        MemberUser result = memberUserService.findMember(nicknameOrUserNameOrPhoneOrEmail);
        return Result.succeed(result);
    }
}
