/**
 * Project Name: projects
 * Package Name: com.taotao.cloud.flume.interceptor
 * Date: 2020/9/22 13:30
 * Author: dengtao
 */
package com.taotao.cloud.flume.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author dengtao
 * @version v1.0.0
 * @date 2020/9/22 13:30
 */
public class AccessLogTransformInterceptor implements Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(AccessLogTransformInterceptor.class);

	private static final List<String> PREFIX = Arrays.asList("%", "_", "$");

	private JSONObject metaJson;

	@Override
	public void initialize() {
		metaJson = MetaUtils.getLogMetaJson();
	}

	@Override
	public Event intercept(Event event) {
		try {
			byte[] body = event.getBody();
			String bodyStr = new String(body);

			JSONObject result = new JSONObject();

			JSONObject bodyJson = JSONObject.parseObject(bodyStr);
			JSONObject contentJson = bodyJson.getJSONObject("content");
			bodyJson.remove("content");
			result.putAll(bodyJson);

			JSONObject propertiesJson = contentJson.getJSONObject("properties");
			JSONObject libJson = contentJson.getJSONObject("lib");
			contentJson.remove("properties");
			contentJson.remove("lib");
			result.putAll(contentJson);
			result.putAll(propertiesJson);
			result.putAll(libJson);

			JSONObject jsonObject = new JSONObject();
			Iterator<Map.Entry<String, Object>> iterator = result.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> next = iterator.next();
				String key = next.getKey();

				String one = String.valueOf(key.charAt(0));
				if (PREFIX.contains(one)) {
					iterator.remove();
					jsonObject.put(key.substring(1), next.getValue());
				}
			}
			result.putAll(jsonObject);

			if (null != metaJson) {
				result.putAll(metaJson);
			}

			event.getHeaders().put("type", "parquet");
			event.setBody(result.toJSONString().getBytes());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			logger.error("error--------" + e.getMessage());
			return null;
		}

		return event;
	}

	@Override
	public List<Event> intercept(List<Event> list) {
		List<Event> result = new ArrayList<>();
		for (Event event : list) {
			Event intercept = intercept(event);
			if (null != intercept) {
				result.add(intercept);
			}
		}
		return result;
	}

	@Override
	public void close() {

	}

	public static class Builder implements Interceptor.Builder {

		@Override
		public Interceptor build() {
			return new AccessLogTransformInterceptor();
		}

		@Override
		public void configure(Context context) {

		}
	}
}
