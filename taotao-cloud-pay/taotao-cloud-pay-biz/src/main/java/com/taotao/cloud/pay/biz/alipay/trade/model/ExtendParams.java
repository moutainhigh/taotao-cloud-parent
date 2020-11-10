package com.taotao.cloud.pay.biz.alipay.trade.model;

import com.google.gson.annotations.SerializedName;

public class ExtendParams {

	@SerializedName("sys_service_provider_id")
	private String sysServiceProviderId;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("ExtendParams{");
		sb.append("sysServiceProviderId='").append(this.sysServiceProviderId).append('\'');
		sb.append('}');
		return sb.toString();
	}

	public String getSysServiceProviderId() {
		return this.sysServiceProviderId;
	}

	public ExtendParams setSysServiceProviderId(String sysServiceProviderId) {
		this.sysServiceProviderId = sysServiceProviderId;
		return this;
	}
}
