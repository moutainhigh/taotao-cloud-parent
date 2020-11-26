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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * <br>
 *
 * @author dengtao
 * @version v1.0.0
 * @date 2020/9/22 13:30
 */
public class SysLogSourceInterceptor implements Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(SysLogSourceInterceptor.class);

	@Override
	public void initialize() {
	}

	@Override
	public Event intercept(Event event) {
		try {
			byte[] body = event.getBody();
			String bodyStr = new String(body);
			JSONObject bodyJson = JSONObject.parseObject(bodyStr);

			SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);
			String timestamp = bodyJson.getString("timestamp");
			Date date = dff.parse(timestamp);
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			String ctime = df1.format(date);

			event.getHeaders().put("ctime", ctime);
			event.setBody(body);
		} catch (NumberFormatException | ParseException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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
			return new SysLogSourceInterceptor();
		}

		@Override
		public void configure(Context context) {

		}
	}
}
