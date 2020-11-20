package com.taotao.cloud.aftersale.api.feign.fallback;

import com.taotao.cloud.aftersale.api.feign.RemoteWithdrawService;
import com.taotao.cloud.aftersale.api.vo.WithdrawVO;
import com.taotao.cloud.common.utils.LogUtil;
import com.taotao.cloud.core.model.Result;
import feign.hystrix.FallbackFactory;

/**
 * RemoteLogFallbackImpl
 *
 * @author dengtao
 * @date 2020/4/29 21:43
 */
public class RemoteWithdrawFallbackImpl implements FallbackFactory<RemoteWithdrawService> {
	@Override
	public RemoteWithdrawService create(Throwable throwable) {
		return new RemoteWithdrawService() {
			@Override
			public Result<WithdrawVO> getMemberSecurityUser(Long id) {
				LogUtil.error("调用getMemberSecurityUser异常：{}", throwable, id);
				return Result.failed(null, 500);
			}
		};
	}
}
