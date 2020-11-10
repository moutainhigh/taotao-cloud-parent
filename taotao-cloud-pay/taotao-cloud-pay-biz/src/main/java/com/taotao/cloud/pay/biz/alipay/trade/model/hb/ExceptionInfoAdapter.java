package com.taotao.cloud.pay.biz.alipay.trade.model.hb;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.List;

import com.taotao.cloud.pay.biz.alipay.trade.utils.Utils;
import org.apache.commons.lang.StringUtils;

public class ExceptionInfoAdapter implements JsonSerializer<List<ExceptionInfo>> {
	
	@Override
	public JsonElement serialize(List<ExceptionInfo> exceptionInfos, Type type,
								 JsonSerializationContext jsonSerializationContext) {
		if (Utils.isListEmpty(exceptionInfos)) {
			return null;
		}
		return new JsonPrimitive(StringUtils.join(exceptionInfos, "|"));
	}
	
}
