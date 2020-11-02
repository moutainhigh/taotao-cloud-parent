package com.taotao.cloud.order.api.feign.fallback;

import com.taotao.cloud.common.utils.LogUtil;
import com.taotao.cloud.core.model.Result;
import com.taotao.cloud.order.api.dto.OrderDTO;
import com.taotao.cloud.order.api.feign.RemoteOrderService;
import com.taotao.cloud.order.api.vo.OrderVO;
import feign.hystrix.FallbackFactory;

/**
 * RemoteLogFallbackImpl
 *
 * @author dengtao
 * @date 2020/4/29 21:43
 */
public class RemoteOrderFallbackImpl implements FallbackFactory<RemoteOrderService> {
    @Override
    public RemoteOrderService create(Throwable throwable) {
        return new RemoteOrderService() {
            @Override
            public Result<OrderVO> findOrderInfoByCode(String code) {
                LogUtil.error("调用findOrderInfoByCode异常：{}", code, throwable);
                return Result.failed(null, 500);
            }

            @Override
            public Result<OrderVO> saveOrder(OrderDTO orderDTO) {
                LogUtil.error("调用saveOrder异常：{}", orderDTO, throwable);
                return Result.failed(null, 500);
            }
        };
    }
}
