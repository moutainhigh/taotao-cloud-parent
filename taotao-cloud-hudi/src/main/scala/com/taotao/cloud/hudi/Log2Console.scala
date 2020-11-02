package com.taotao.cloud.hudi

import com.taotao.cloud.hudi.config.Config
import com.taotao.cloud.hudi.util.SparkHelper
import org.apache.spark.sql.streaming.OutputMode
import org.slf4j.{Logger, LoggerFactory}

object Log2Console {
  val LOGGER: Logger = LoggerFactory.getLogger("Log2Console")

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "/usr/local/hadoop-common")
    val config = Config.parseConfig(Log2Console, args);
    val spark = SparkHelper.getSparkSession(config.env);

    import spark.implicits._

    val dataframe = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", config.brokerList)
      .option("subscribe", config.sourceTopic)
      .option("staringOffsets", "earliest")
      .option("kafka.consumer.commit.groupid", "test1")
      .load()

    dataframe.selectExpr("cast(value as string)", "offset")
      .as[(String, Long)]
      .writeStream
      .queryName("action-log")
      .outputMode(OutputMode.Append())
      .format("console")
      .start()
      .awaitTermination()
  }
}
