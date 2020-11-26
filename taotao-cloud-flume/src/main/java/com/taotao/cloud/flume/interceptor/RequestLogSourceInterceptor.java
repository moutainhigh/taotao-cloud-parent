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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <br>
 *
 * @author dengtao
 * @version v1.0.0
 * @date 2020/9/22 13:30
 */
public class RequestLogSourceInterceptor implements Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(RequestLogSourceInterceptor.class);

	@Override
	public void initialize() {
	}

	@Override
	public Event intercept(Event event) {
		try {
			byte[] body = event.getBody();
			String bodyStr = new String(body);
			JSONObject bodyJson = JSONObject.parseObject(bodyStr);

			Long startTime = bodyJson.getLong("start_time");
			String ctime = new SimpleDateFormat("yyyy-MM-dd").format(new Date(startTime));
			event.getHeaders().put("ctime", ctime);
			event.setBody(body);
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
			return new RequestLogSourceInterceptor();
		}

		@Override
		public void configure(Context context) {

		}
	}
}
