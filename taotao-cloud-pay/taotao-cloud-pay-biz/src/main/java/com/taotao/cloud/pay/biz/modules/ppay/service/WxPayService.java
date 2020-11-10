package com.taotao.cloud.pay.biz.modules.ppay.service;


import com.taotao.cloud.pay.biz.common.model.Product;
import com.taotao.cloud.pay.biz.common.model.Result;

/**
 * 微信支付
 * 爪哇笔记 https://blog.52itstyle.vip
 * @author 小柒2012
 */
public interface WxPayService {

    Result wxPay(Product product);

}
