package com.taotao.cloud.spark

import org.apache.spark.{SparkConf, SparkContext}

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
 * --class com.taotao.cloud.spark.ScalaWordCount \
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
object ScalaWordCount {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("WordCountApp")
      .setMaster("local[1]")

    val context = new SparkContext(conf)

    context.textFile(args(0))
      .flatMap(_.split(" "))
      .map((_, 1))
      .reduceByKey(_ + _)
      .sortBy(_._2, ascending = false)
      .saveAsTextFile(args(1))

    context.stop()
  }
}
