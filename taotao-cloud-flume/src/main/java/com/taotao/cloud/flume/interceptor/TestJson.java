/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.taotao.cloud.flume.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * @author dengtao
 * @date 2020/11/25 下午3:35
 * @since v1.0
 */
public class TestJson {
	public static void main(String[] args) {

		String aa = "%";
		String bb = "_";
		String cc = "$";

		JSONObject jsonObject = JSON.parseObject("{\"ctime\":1606277316432,\"project\":\"taotao-cloud-backend\",\"content\":{\"login_id\":\"11111111111111\",\"_track_id\":63276154,\"lib\":{\"$lib\":\"js\",\"$lib_method\":\"code\",\"$lib_version\":\"1.15.16\"},\"distinct_id\":\"11111111111111\",\"anonymous_id\":\"174489e063d961-0903670dbb0a25-15306251-1296000-174489e063ed42\",\"original_id\":\"174489e063d961-0903670dbb0a25-15306251-1296000-174489e063ed42\",\"type\":\"track_signup\",\"event\":\"$SignUp\",\"properties\":{\"$screen_width\":1440,\"project\":\"taotao-cloud-backend\",\"$screen_height\":900,\"$lib\":\"js\",\"current_url\":\"http://localhost:3000/login\",\"$device_id\":\"174489e063d961-0903670dbb0a25-15306251-1296000-174489e063ed42\",\"$latest_landing_page\":\"url???domain????????????\",\"$title\":\"React Index\",\"$timezone_offset\":-480,\"$lib_version\":\"1.15.16\",\"$latest_search_keyword\":\"url???domain????????????\",\"$latest_traffic_source_type\":\"url???domain????????????\",\"$url\":\"http://localhost:3000/login\",\"$latest_referrer\":\"url???domain????????????\",\"$latest_referrer_host\":\"????????????\"}}}");

		JSONObject js = JSON.parseObject("{\n" +
			"\"$screen_width\": 1440,\n" +
			"\"project\": \"taotao-cloud-backend\",\n" +
			"\"$screen_height\": 900,\n" +
			"\"$lib\": \"js\",\n" +
			"\"current_url\": \"http://localhost:3000/login\",\n" +
			"\"$device_id\": \"174489e063d961-0903670dbb0a25-15306251-1296000-174489e063ed42\",\n" +
			"\"$latest_landing_page\": \"url???domain????????????\",\n" +
			"\"$title\": \"React Index\",\n" +
			"\"$timezone_offset\": -480,\n" +
			"\"$lib_version\": \"1.15.16\",\n" +
			"\"$latest_search_keyword\": \"url???domain????????????\",\n" +
			"\"$latest_traffic_source_type\": \"url???domain????????????\",\n" +
			"\"$url\": \"http://localhost:3000/login\",\n" +
			"\"$latest_referrer\": \"url???domain????????????\",\n" +
			"\"$latest_referrer_host\": \"????????????\"\n" +
			"}");

		// for (Map.Entry<String, Object> entry : js.entrySet()) {
		// 	String key = entry.getKey();
		// 	if (key.startsWith(aa) || key.startsWith(bb) || key.startsWith(cc)) {
		// 		String substring = key.substring(1);
		// 		js.put(substring, entry.getValue());
		// 	}
		// }


		JSONObject j1 = new JSONObject();
		Iterator<Map.Entry<String, Object>> iterator = js.entrySet().iterator();
		while (iterator.hasNext()){
			Map.Entry<String, Object> next = iterator.next();
			String key = next.getKey();
			Object value = next.getValue();

			if (key.startsWith(aa) || key.startsWith(bb) || key.startsWith(cc)) {
				iterator.remove();
				String substring = key.substring(1);
				j1.put(substring, value);
			}
		}
		js.putAll(j1);


		System.out.println(js);

	}
}
