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
package com.taotao.cloud.flink;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author dengtao
 * @date 2020/11/3 09:07
 * @since v1.0
 */
public class JStreamWordCount {
	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		DataStream<String> dss = env.socketTextStream("127.0.0.1", 8888);

		DataStream<String> dso = dss.flatMap(new FlatMapFunction<String, String>() {
			@Override
			public void flatMap(String value, Collector<String> out) throws Exception {
				String[] s = value.split(" ");
				for (String s1 : s) {
					out.collect(s1);
				}
			}
		});

		DataStream<Tuple2<String, Integer>> dst = dso.map((MapFunction<String, Tuple2<String, Integer>>) value -> Tuple2.of(value, 1));

		KeyedStream<Tuple2<String, Integer>, String> kst = dst.keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
			@Override
			public String getKey(Tuple2<String, Integer> value) throws Exception {
				return value.f0;
			}
		});

		SingleOutputStreamOperator<Tuple2<String, Integer>> sum = kst.sum(1);

		sum.print();

		env.execute("JBatchWordCount");
	}
}
