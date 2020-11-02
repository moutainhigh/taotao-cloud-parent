package com.taotao.cloud.hudi

import com.taotao.cloud.hudi.config.Config
import com.taotao.cloud.hudi.extract.NewsAction
import com.taotao.cloud.hudi.util.{HudiUtil, MetaUtil, SparkHelper}
import org.apache.hudi.DataSourceWriteOptions
import org.apache.hudi.config.{HoodieIndexConfig, HoodieWriteConfig}
import org.apache.hudi.index.HoodieIndex
import org.apache.spark.sql.streaming.StreamingQueryListener.{QueryProgressEvent, QueryStartedEvent, QueryTerminatedEvent}
import org.apache.spark.sql.streaming.{StreamingQueryListener, Trigger}
import org.apache.spark.sql.{Dataset, SaveMode}
import org.slf4j.{Logger, LoggerFactory}

object Log2Hudi {
  val LOGGER: Logger = LoggerFactory.getLogger("Log2Hudi")

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "/usr/local/hadoop-common")

    val config = Config.parseConfig(Log2Hudi, args);
    val spark = SparkHelper.getSparkSession(config.env);

    spark.streams.addListener(new StreamingQueryListener() {
      override def onQueryStarted(queryStarted: QueryStartedEvent): Unit = {
        println("Query started: " + queryStarted.id)
      }
      override def onQueryTerminated(queryTerminated: QueryTerminatedEvent): Unit = {
        println("Query terminated: " + queryTerminated.id)
      }
      override def onQueryProgress(queryProgress: QueryProgressEvent): Unit = {
        println("Query made progress: " + queryProgress.progress)
      }
    })

    import org.apache.spark.sql.functions._
    import spark.implicits._

    val dataframe = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", config.brokerList)
      .option("subscribe", config.sourceTopic)
      .option("staringOffsets", "latest")
      .option("maxOffsetsPerTrigger", 100000)
      .option("failOnDataLoss", value = false)
      .option("kafka.consumer.commit.groupid", "Log2Hudi")
      .load()

    val event = MetaUtil.getMetaJson("event")
    val user = MetaUtil.getMetaJson("user")

    val metaEvent = spark.read.json(Seq(event).toDS())
    val metaUser = spark.read.json(Seq(user).toDS())
    val metaEventScheme = metaEvent.schema
    val metaUserScheme = metaUser.schema

    val msb = spark.sparkContext.broadcast((metaEventScheme, metaUserScheme))

    dataframe.selectExpr("cast(value as string)")
      .as[String]
      .writeStream
      .queryName("action2hudi")
      .option("checkpointLocation", config.checkpointDir + "/action/")
      .trigger(Trigger.ProcessingTime(config.trigger + " seconds"))
      .foreachBatch((batchDF: Dataset[String], batchId: Long) => {
        batchDF.persist()

        val newDF: Dataset[String] = batchDF.map(new NewsAction().unionMeatAndBody)
          .filter(_ != null)

        if (!newDF.isEmpty) {
          newDF.select(from_json($"value", msb.value._1).as("data_event"))
            .select("data_event.*")
            .filter("type=event")
            .write
            .format("org.apache.hudi")
            .options(HudiUtil.getEnentConfig(config.tableType, config.syncJDBCUrl, config.syncJDBCUsername))
            .option(HoodieWriteConfig.TABLE_NAME, "event")
            .option(DataSourceWriteOptions.HIVE_DATABASE_OPT_KEY, config.syncDB)
            .option(HoodieIndexConfig.BLOOM_INDEX_UPDATE_PARTITION_PATH, "true")
            .option(HoodieIndexConfig.INDEX_TYPE_PROP, HoodieIndex.IndexType.GLOBAL_BLOOM.name())
            .mode(SaveMode.Append)
            .save(config.hudiEventBasePath)

          newDF.select(from_json($"value", msb.value._2).as("data_user"))
            .select("data_user.*")
            .filter("type=user")
            .write
            .format("org.apache.hudi")
            .options(HudiUtil.getUserConfig(config.tableType, config.syncJDBCUrl, config.syncJDBCUsername))
            .option(HoodieWriteConfig.TABLE_NAME, "user")
            .option(DataSourceWriteOptions.HIVE_DATABASE_OPT_KEY, config.syncDB)
            //.option(HoodieIndexConfig.BLOOM_INDEX_UPDATE_PARTITION_PATH, "true")
            .option(HoodieIndexConfig.INDEX_TYPE_PROP, HoodieIndex.IndexType.GLOBAL_BLOOM.name())
            .mode(SaveMode.Append)
            .save(config.hudiUserBasePath)
        }

        batchDF.unpersist()
      }).start()
      .awaitTermination()
  }
}
