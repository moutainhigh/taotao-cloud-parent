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
package com.taotao.cloud.hudi;

import org.apache.avro.SchemaParseException;

import java.sql.SQLException;

/**
 * @author dengtao
 * @date 2020/11/4 16:28
 * @since v1.0
 */
public class TestJson {
	public static void main(String[] args) throws SQLException {
		String s = "country ";
		char first = s.charAt(0);

		if (!(Character.isLetter(first) || first == '_')) {
			throw new SchemaParseException("Illegal initial character: " + s);
		}
		System.out.println("ok");
		// Connection connection = null;
		// JSONObject jsonObject = new JSONObject();
		// try {
		// 	Class.forName("com.mysql.jdbc.Driver");
		// 	connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/taotao-cloud-uc-center?autoReconnect=true",
		// 		"root", "123456");
		// 	Statement statement = connection.createStatement();
		// 	ResultSet resultSet = statement.executeQuery("select field, field_type from log_meta");
		//
		// 	while (resultSet.next()) {
		// 		String field = resultSet.getString("field");
		// 		String fieldType = resultSet.getString("field_type");
		//
		// 		genSimpleJsonMeta(jsonObject, field, fieldType);
		// 	}
		// } catch (Exception e) {
		// 	e.printStackTrace();
		// }finally {
		// 	assert connection != null;
		// 	connection.close();
		// }
		//
		// String s = "{\"distinct_id\":\"174489e063d961-0903670dbb0a25-15306251-1296000-174489e063ed42\",\"lib\":{\"$lib\":\"js\",\"$lib_method\":\"code\",\"$lib_version\":\"1.15.16\"},\"properties\":{\"$timezone_offset\":-480,\"$screen_height\":900,\"$screen_width\":1440,\"$lib\":\"js\",\"$lib_version\":\"1.15.16\",\"$latest_traffic_source_type\":\"url的domain解析失败\",\"$latest_search_keyword\":\"url的domain解析失败\",\"$latest_referrer\":\"url的domain解析失败\",\"current_url\":\"http://localhost:3000/\",\"referrer\":\"\",\"ProductName\":\"MacBook Pro\",\"ProductPrice\":123.45,\"IsAddedToFav\":false,\"$is_first_day\":false},\"anonymous_id\":\"174489e063d961-0903670dbb0a25-15306251-1296000-174489e063ed42\",\"type\":\"track\",\"event\":\"BuyProduct\",\"_track_id\":255173669,\"$country\":'zhonggauo'}";
		// JSONObject js1 = JSON.parseObject(s);
		//
		// JSONObject lib = js1.getJSONObject("lib");
		// JSONObject properties = js1.getJSONObject("properties");
		// js1.remove("lib");
		// js1.remove("properties");
		//
		// js1.putAll(lib);
		// js1.putAll(properties);
		//
		// jsonObject.putAll(js1);
		// System.out.println(jsonObject.toJSONString());
	}

	// public static void genSimpleJsonMeta(JSONObject jsonObj, String field, String fieldType) {
	// 	String type = fieldType.toLowerCase();
	// 	switch (type) {
	// 		case "string":
	// 			jsonObj.put(field, "");
	// 			break;
	// 		case "int":
	// 			jsonObj.put(field, 0);
	// 			break;
	// 		case "bigint":
	// 			jsonObj.put(field, 0L);
	// 			break;
	// 		case "double":
	// 			jsonObj.put(field, 0.1);
	// 			break;
	// 		case "boolean":
	// 			jsonObj.put(field, false);
	// 			break;
	// 		case "datetime":
	// 			jsonObj.put(field, null);
	// 			break;
	// 		case "array":
	// 			jsonObj.put(field, new ArrayList<>());
	// 			break;
	// 		default:
	// 			break;
	// 	}
	//
	// }
}
