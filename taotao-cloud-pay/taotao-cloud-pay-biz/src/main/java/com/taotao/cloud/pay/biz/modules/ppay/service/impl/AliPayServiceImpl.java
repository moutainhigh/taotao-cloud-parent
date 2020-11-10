package com.taotao.cloud.pay.biz.modules.ppay.service.impl;

import cn.hutool.core.util.IdUtil;
import com.taotao.cloud.pay.biz.common.model.Product;
import com.taotao.cloud.pay.biz.common.model.Result;
import com.taotao.cloud.pay.biz.modules.ppay.service.AliPayService;
import com.taotao.cloud.pay.biz.modules.ppay.util.PayUtils;
import com.yungouos.pay.common.PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    private PayUtils payUtils;

    @Override
    public Result aliPay(Product product) {
        try {
            Long orderNo = IdUtil.getSnowflake(1,1).nextId();
            product.setOutTradeNo(orderNo+"");
            String result = payUtils.aliPay(product);
            /**
             * 数据库生成订单
             */
            return Result.ok(result);
        }catch (PayException e){
            return Result.error(e.getMessage());
        }
    }
}
