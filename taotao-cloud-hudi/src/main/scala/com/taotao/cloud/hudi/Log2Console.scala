package com.taotao.cloud.hudi

import com.taotao.cloud.hudi.config.Config
import com.taotao.cloud.hudi.util.SparkHelper
import org.apache.spark.sql.streaming.{OutputMode, Trigger}
import org.slf4j.{Logger, LoggerFactory}

object Log2Console {
  val LOGGER: Logger = LoggerFactory.getLogger("Log2Console")

  def main(args: Array[String]): Unit = {

    val name = "$app_id"
    println(name.substring(1, name.length))
//    System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
//      "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl")
//    System.setProperty("HADOOP_USER_NAME", "root")
//    //System.setProperty("hadoop.home.dir", "/usr/local/hadoop-common")
//
//    val config = Config.parseConfig(Log2Console, args);
//    val spark = SparkHelper.getSparkSession(config.env);
//
//    import spark.implicits._
//
//    val dataframe = spark.readStream
//      .format("kafka")
//      .option("kafka.bootstrap.servers", config.brokerList)
//      .option("subscribe", config.sourceTopic)
//      .option("staringOffsets", "latest")
//      .option("kafka.consumer.commit.groupid", "Log2Console")
//      .load()
//
//    dataframe.selectExpr("cast(value as string)", "offset")
//      .as[(String, Long)]
//      .writeStream
//      .trigger(Trigger.ProcessingTime(config.trigger + " seconds"))
//      //.queryName("action-log")
//      .outputMode(OutputMode.Append())
//      .format("console")
//      .start()
//      .awaitTermination()
  }
}
