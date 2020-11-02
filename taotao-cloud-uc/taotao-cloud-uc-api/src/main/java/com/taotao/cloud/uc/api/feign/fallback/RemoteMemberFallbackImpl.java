package com.taotao.cloud.uc.api.feign.fallback;

import com.taotao.cloud.common.utils.LogUtil;
import com.taotao.cloud.core.model.Result;
import com.taotao.cloud.core.model.SecurityUser;
import com.taotao.cloud.uc.api.feign.RemoteMemberService;
import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * RemoteLogFallbackImpl
 *
 * @author dengtao
 * @date 2020/4/29 21:43
 */
public class RemoteMemberFallbackImpl implements FallbackFactory<RemoteMemberService> {
    @Override
    public RemoteMemberService create(Throwable throwable) {
        return new RemoteMemberService() {
            @Override
            public Result<SecurityUser> getMemberSecurityUser(String getMemberSecurityUser) {
                LogUtil.error("调用getMemberSecurityUser异常：{}", throwable, getMemberSecurityUser);
                return Result.failed(null, 500);
            }
        };
    }
}
