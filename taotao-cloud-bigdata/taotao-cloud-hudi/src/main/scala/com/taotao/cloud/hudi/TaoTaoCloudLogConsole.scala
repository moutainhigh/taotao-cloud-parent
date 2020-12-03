package com.taotao.cloud.hudi

import com.taotao.cloud.hudi.config.Config
import com.taotao.cloud.hudi.util.SparkHelper
import org.apache.spark.sql.streaming.{OutputMode, Trigger}
import org.slf4j.{Logger, LoggerFactory}

/**
 * Log2Console
 *
 * -e dev -b 106.13.201.31:9092 -t taotao-cloud-sys-log -m 0
 *
 * @author dengtao
 * @date 2020/11/27 下午3:06
 * @since v1.0
 */
object TaoTaoCloudLogConsole {
  val LOGGER: Logger = LoggerFactory.getLogger("TaoTaoCloudLogConsole")

  def main(args: Array[String]): Unit = {
    System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
      "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl")
    //System.setProperty("HADOOP_USER_NAME", "root")
    //System.setProperty("hadoop.home.dir", "/usr/local/hadoop-common")

    val config = Config.parseConfig(TaoTaoCloudLogConsole, args)
    val spark = SparkHelper.getSparkSession(config.env)

    import spark.implicits._

    val dataframe = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", config.brokerList)
      .option("subscribe", config.sourceTopic)
      .option("startingOffsets", "earliest")
      .option("group.id", "Log2Console")
      .load()

    dataframe.selectExpr("cast(value as string)", "offset")
      .as[(String, Long)]
      .writeStream
      .trigger(Trigger.ProcessingTime(config.trigger + " seconds"))
      .queryName("action-log")
      .outputMode(OutputMode.Append())
      .format("console")
      .start()
      .awaitTermination()
  }
}
