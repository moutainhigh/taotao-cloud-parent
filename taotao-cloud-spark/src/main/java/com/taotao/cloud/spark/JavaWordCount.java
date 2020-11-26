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
package com.taotao.cloud.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

/**
 * 1.本地运行 本地数据参数
 * <p>
 * ----	 /Users/dengtao/spark/input /Users/dengtao/spark/input
 * <p>
 * 2.本地运行 hadoop数据参数
 * <p>
 * ----  hadoop://127.0.0.1:9000/spark/input hadoop://127.0.0.1:9000/spark/input
 * <p>
 * 3.上传jar包提交集群运行
 * <p>
 * ./spark-submit \
 * --master spark://127.0.0.1:7077 \
 * --class com.taotao.cloud.spark.JavaWordCount \
 * --executor-memory 512m \
 * --total-executor-cores 2 \
 * /root/spark/jar/taotao-cloud-spark-1.0-all.jar \
 * hdfs://127.0.0.1/spark/wordcount/input \
 * hdfs://127.0.0.1/spark/wordcount/output
 * <p>
 *
 * @author dengtao
 * @date 2020/11/26 上午9:35
 * @since v1.0
 */
public class JavaWordCount {
	public static void main(String[] args) throws InterruptedException {
		SparkConf javaWordCount = new SparkConf()
			.setAppName("JavaWordCount").setMaster("local[1]");

		JavaSparkContext jsc = new JavaSparkContext(javaWordCount);

		JavaPairRDD<String, Integer> counts = jsc.textFile(args[0])
			.flatMap(lines -> Arrays.asList(lines.split(" ")).iterator())
			.mapToPair(word -> Tuple2.apply(word, 1))
			.reduceByKey(Integer::sum);

		JavaPairRDD<String, Integer> sorts = counts.mapToPair(tuple2 -> Tuple2.apply(tuple2._2(), tuple2._1()))
			.sortByKey(false)
			.mapToPair(Tuple2::swap);

		sorts.saveAsTextFile(args[1]);

		jsc.stop();
	}
}
