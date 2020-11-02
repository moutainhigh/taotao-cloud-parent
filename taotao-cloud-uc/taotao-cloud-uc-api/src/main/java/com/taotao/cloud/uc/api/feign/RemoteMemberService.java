package com.taotao.cloud.uc.api.feign;

import com.taotao.cloud.common.constant.ServiceNameConstant;
import com.taotao.cloud.core.model.Result;
import com.taotao.cloud.core.model.SecurityUser;
import com.taotao.cloud.uc.api.feign.fallback.RemoteUserFallbackImpl;
import com.taotao.cloud.uc.api.query.member.MemberQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 远程调用会员用户模块
 *
 * @author dengtao
 * @date 2020/5/2 16:42
 */
@FeignClient(contextId = "remoteMemberService", value = ServiceNameConstant.TAOTAO_CLOUD_UC_CENTER, fallbackFactory = RemoteUserFallbackImpl.class)
public interface RemoteMemberService {

    /**
     * 通过用户名查询用户包括角色权限等o
     *
     * @param nicknameOrUserNameOrPhoneOrEmail 用户名
     * @return com.taotao.cloud.common.model.Result<com.taotao.cloud.uc.api.dto.UserDetailsInfo>
     * @author dengtao
     * @date 2020/4/29 17:48
     */
    @GetMapping(value = "/member/info/security")
    Result<SecurityUser> getMemberSecurityUser(String nicknameOrUserNameOrPhoneOrEmail);
}

