package com.taotao.cloud.hudi

import com.taotao.cloud.hudi.config.Config
import com.taotao.cloud.hudi.util.SparkHelper
import org.apache.spark.sql.streaming.{OutputMode, Trigger}
import org.slf4j.{Logger, LoggerFactory}

//-e dev -b 192.168.99.37:9092 -t taotao-cloud-backend -m 0
object Log2Console {

  val LOGGER: Logger = LoggerFactory.getLogger("Log2Console")

  def main(args: Array[String]): Unit = {
    //System.setProperty("hadoop.home.dir", "/usr/local/hadoop-common")

    val config = Config.parseConfig(Log2Console, args);
    val spark = SparkHelper.getSparkSession(config.env);

    import spark.implicits._

    val dataframe = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", config.brokerList)
      .option("subscribe", config.sourceTopic)
      .option("staringOffsets", "latest")
      .option("kafka.consumer.commit.groupid", "Log2Console")
      .load()

    dataframe.selectExpr("cast(value as string)", "offset")
      .as[(String, Long)]
      .writeStream
      .trigger(Trigger.ProcessingTime(config.trigger + " seconds"))
      //.queryName("action-log")
      .outputMode(OutputMode.Append())
      .format("console")
      .start()
      .awaitTermination()
  }
}
