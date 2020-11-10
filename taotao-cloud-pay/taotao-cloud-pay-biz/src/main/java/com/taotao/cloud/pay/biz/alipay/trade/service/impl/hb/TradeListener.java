package com.taotao.cloud.pay.biz.alipay.trade.service.impl.hb;

public abstract interface TradeListener {

	void onPayTradeSuccess(String paramString, long paramLong);

	void onPayInProgress(String paramString, long paramLong);

	void onPayFailed(String paramString, long paramLong);

	void onConnectException(String paramString, long paramLong);

	void onSendException(String paramString, long paramLong);

	void onReceiveException(String paramString, long paramLong);
}
