package com.taotao.cloud.pay.biz.alipay.trade.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.taotao.cloud.pay.biz.alipay.trade.model.hb.EquipStatus;
import com.taotao.cloud.pay.biz.alipay.trade.model.hb.EquipStatusAdapter;
import com.taotao.cloud.pay.biz.alipay.trade.model.hb.ExceptionInfoAdapter;
import com.taotao.cloud.pay.biz.alipay.trade.model.hb.TradeInfoAdapter;

import java.lang.reflect.Type;

public class GsonFactory {

	public static Gson getGson() {
		return GsonHolder.GSON;
	}

	private static class GsonHolder {
		private static final Type EXCEPTION_LIST_TYPE = new TypeToken() {
		}.getType();
		private static final Type TRADE_INFO_LIST_TYPE = new TypeToken() {
		}.getType();

		private static final Gson GSON = new GsonBuilder().registerTypeAdapter(EXCEPTION_LIST_TYPE, new ExceptionInfoAdapter())
			.registerTypeAdapter(TRADE_INFO_LIST_TYPE, new TradeInfoAdapter())
			.registerTypeAdapter(EquipStatus.class, new EquipStatusAdapter()).create();
	}
}
