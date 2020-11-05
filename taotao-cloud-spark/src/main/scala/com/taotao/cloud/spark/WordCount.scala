package com.taotao.cloud.spark

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("WordCountApp")
      .setMaster("local[1]")

    val context = new SparkContext(conf)

    context.textFile("/Users/dengtao/spark")
      .flatMap(_.split(" "))
      .map((_, 1))
      .reduceByKey(_+_)
      .sortBy(_._2, ascending = false)
      .saveAsTextFile("/Users/dengtao/sparktest")

    context.stop()
  }
}
