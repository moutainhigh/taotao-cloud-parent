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
public class SourceInterceptor implements Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(SourceInterceptor.class);

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
				JSONObject metaJson = JSONObject.parseObject(metaStr);

				String contentStr = new String(Base64.decodeBase64(content));
				JSONObject contentJson = JSONObject.parseObject(contentStr);

				String ctime = new SimpleDateFormat("yyyy-MM-dd").format(Double.parseDouble(metaJson.getString("ctime")));
				event.getHeaders().put("ctime", ctime);
				event.getHeaders().put("type", "source");

				JSONObject result = new JSONObject();
				result.put("ctime", metaJson.getLongValue("ctime"));
				result.put("project", metaJson.getString("project"));
				result.put("content", contentJson);

				event.setBody(result.toJSONString().getBytes());
				return event;
			}
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
			return new SourceInterceptor();
		}

		@Override
		public void configure(Context context) {

		}
	}
}
