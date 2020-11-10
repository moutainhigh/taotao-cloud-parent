package com.taotao.cloud.pay.biz.alipay.trade.model.result;

import com.alipay.api.response.AlipayTradeRefundResponse;
import com.taotao.cloud.pay.biz.alipay.trade.model.TradeStatus;

public class AlipayF2FRefundResult implements Result {
	
	private TradeStatus tradeStatus;
	private AlipayTradeRefundResponse response;

	public AlipayF2FRefundResult(AlipayTradeRefundResponse response) {
		this.response = response;
	}

	public void setTradeStatus(TradeStatus tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public void setResponse(AlipayTradeRefundResponse response) {
		this.response = response;
	}

	public TradeStatus getTradeStatus() {
		return this.tradeStatus;
	}

	public AlipayTradeRefundResponse getResponse() {
		return this.response;
	}

	@Override
	public boolean isTradeSuccess() {
		return (this.response != null) && (TradeStatus.SUCCESS.equals(this.tradeStatus));
	}
}
