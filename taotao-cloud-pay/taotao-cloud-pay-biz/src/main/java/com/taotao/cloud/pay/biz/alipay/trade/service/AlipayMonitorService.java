package com.taotao.cloud.pay.biz.alipay.trade.service;

import com.alipay.api.response.MonitorHeartbeatSynResponse;
import com.taotao.cloud.pay.biz.alipay.trade.model.builder.AlipayHeartbeatSynContentBuilder;

public abstract interface AlipayMonitorService {
	
	public abstract MonitorHeartbeatSynResponse heartbeatSyn(
			AlipayHeartbeatSynContentBuilder paramAlipayHeartbeatSynContentBuilder);

	public abstract MonitorHeartbeatSynResponse heartbeatSyn(
			AlipayHeartbeatSynContentBuilder paramAlipayHeartbeatSynContentBuilder, String paramString);
}
