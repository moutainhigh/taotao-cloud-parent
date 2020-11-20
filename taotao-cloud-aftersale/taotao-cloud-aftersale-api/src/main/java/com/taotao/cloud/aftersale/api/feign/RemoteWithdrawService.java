package com.taotao.cloud.aftersale.api.feign;

import com.taotao.cloud.aftersale.api.feign.fallback.RemoteWithdrawFallbackImpl;
import com.taotao.cloud.aftersale.api.vo.WithdrawVO;
import com.taotao.cloud.common.constant.ServiceNameConstant;
import com.taotao.cloud.core.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 远程调用售后模块
 *
 * @author dengtao
 * @date 2020/5/2 16:42
 */
@FeignClient(contextId = "remoteWithdrawService", value = ServiceNameConstant.TAOTAO_CLOUD_AFTERSALE_CENTER, fallbackFactory = RemoteWithdrawFallbackImpl.class)
public interface RemoteWithdrawService {

	/**
	 * 根据id查询提现申请信息
	 *
	 * @param id id
	 * @return com.taotao.cloud.core.model.Result<com.taotao.cloud.aftersale.api.vo.WithdrawVO>
	 * @author dengtao
	 * @date 2020/11/20 上午9:50
	 * @since v1.0
	 */
	@GetMapping(value = "/withdraw/info/id/{id:[0-9]*}")
	Result<WithdrawVO> getMemberSecurityUser(@PathVariable(value = "id") Long id);
}

