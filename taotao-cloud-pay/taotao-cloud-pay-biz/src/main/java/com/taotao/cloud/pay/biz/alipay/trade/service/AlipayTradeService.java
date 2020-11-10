package com.taotao.cloud.pay.biz.alipay.trade.service;


import com.taotao.cloud.pay.biz.alipay.trade.model.builder.AlipayTradePayContentBuilder;
import com.taotao.cloud.pay.biz.alipay.trade.model.builder.AlipayTradePrecreateContentBuilder;
import com.taotao.cloud.pay.biz.alipay.trade.model.builder.AlipayTradeRefundContentBuilder;
import com.taotao.cloud.pay.biz.alipay.trade.model.result.AlipayF2FPayResult;
import com.taotao.cloud.pay.biz.alipay.trade.model.result.AlipayF2FPrecreateResult;
import com.taotao.cloud.pay.biz.alipay.trade.model.result.AlipayF2FQueryResult;
import com.taotao.cloud.pay.biz.alipay.trade.model.result.AlipayF2FRefundResult;

public abstract interface AlipayTradeService {
	
	public abstract AlipayF2FPayResult tradePay(AlipayTradePayContentBuilder paramAlipayTradePayContentBuilder);

	public abstract AlipayF2FQueryResult queryTradeResult(String paramString);

	public abstract AlipayF2FRefundResult tradeRefund(AlipayTradeRefundContentBuilder paramAlipayTradeRefundContentBuilder);

	public abstract AlipayF2FPrecreateResult tradePrecreate(AlipayTradePrecreateContentBuilder paramAlipayTradePrecreateContentBuilder);
}
