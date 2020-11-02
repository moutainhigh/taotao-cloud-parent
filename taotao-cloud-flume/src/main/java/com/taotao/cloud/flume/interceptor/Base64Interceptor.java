/**
 * Project Name: projects
 * Package Name: com.taotao.cloud.flume.interceptor
 * Date: 2020/9/22 13:30
 * Author: dengtao
 */
package com.taotao.cloud.flume.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <br>
 *
 * @author dengtao
 * @version v1.0.0
 * @date 2020/9/22 13:30
 */
public class Base64Interceptor implements Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(Base64Interceptor.class);

	@Override
	public void initialize() {
	}

	@Override
	public Event intercept(Event event) {
		try {
			byte[] body = event.getBody();
			String bodyStr = new String(body);

			String[] fields = bodyStr.split("-");
			if (fields.length == 2) {
				String meta = fields[0];
				String content = fields[1];

				String metaStr = new String(Base64.decodeBase64(meta));
				logger.info("metaStr--------" + metaStr);
				JSONObject metaJson = JSONObject.parseObject(metaStr);

				String contentStr = new String(Base64.decodeBase64(content));
				logger.info("contentStr--------" + contentStr);
				JSONObject contentJson = JSONObject.parseObject(contentStr);

				String ctime = new SimpleDateFormat("yyyyMMdd").format(Double.parseDouble(metaJson.getString("ctime")));
				event.getHeaders().put("ctime", ctime);
//                String chour = new SimpleDateFormat("HH").format(Double.parseDouble(metaJson.getString("chour")));
//                event.getHeaders().put("chour", chour);

				JSONObject result = new JSONObject();
				result.put("ctime", metaJson.getLongValue("ctime"));
				result.put("project", metaJson.getString("project"));
				result.put("content", contentJson);

				logger.info("result--------" + result.toJSONString());
				event.setBody(result.toJSONString().getBytes());
				return event;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			logger.info("error--------" + e.getMessage());
			return null;
		}

		return event;
	}

	@Override
	public List<Event> intercept(List<Event> list) {
		List<Event> result = new ArrayList<Event>();
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
			return new Base64Interceptor();
		}

		@Override
		public void configure(Context context) {

		}
	}
}
