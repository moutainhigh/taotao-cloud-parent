package com.taotao.cloud.flink

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, createTypeInformation}

object BatchWordCount {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val lines: DataStream[String] = env.readTextFile("/Users/dengtao/spark/hello.txt")

    lines.flatMap(_.split(" "))
      .map((_, 1))
      .keyBy(_._1)
      .sum(1)
      .writeAsText("/Users/dengtao/sparktest")

    env.execute("BatchWordCount")
  }
}
