package com.taotao.cloud.pay.biz.alipay.trade.model.hb;

public class PosTradeInfo implements TradeInfo {
	
	private HbStatus status;
	private String time;
	private int timeConsume;

	public static PosTradeInfo newInstance(HbStatus status, String time, int timeConsume) {
		PosTradeInfo info = new PosTradeInfo();
		if ((timeConsume > 99) || (timeConsume < 0)) {
			timeConsume = 99;
		}
		info.setTimeConsume(timeConsume);
		info.setStatus(status);
		info.setTime(time);
		return info;
	}

	@Override
	public String toString() {
		return this.status.name() + this.time
				+ String.format("%02d", new Object[] { Integer.valueOf(this.timeConsume) });
	}

	@Override
	public HbStatus getStatus() {
		return this.status;
	}

	public void setStatus(HbStatus status) {
		this.status = status;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public double getTimeConsume() {
		return this.timeConsume;
	}

	public void setTimeConsume(int timeConsume) {
		this.timeConsume = timeConsume;
	}
}
