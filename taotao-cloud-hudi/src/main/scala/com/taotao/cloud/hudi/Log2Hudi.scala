package com.taotao.cloud.hudi

import com.taotao.cloud.hudi.config.Config
import com.taotao.cloud.hudi.extract.LogExtract
import com.taotao.cloud.hudi.util.{HudiUtil, MetaUtil, SparkHelper}
import org.apache.hudi.DataSourceWriteOptions
import org.apache.hudi.config.{HoodieIndexConfig, HoodieWriteConfig}
import org.apache.hudi.index.HoodieIndex
import org.apache.spark.sql.streaming.StreamingQueryListener.{QueryProgressEvent, QueryStartedEvent, QueryTerminatedEvent}
import org.apache.spark.sql.streaming.{StreamingQueryListener, Trigger}
import org.apache.spark.sql.{DataFrame, Dataset, SaveMode}
import org.slf4j.{Logger, LoggerFactory}

//-e dev -b 192.168.99.37:9092 -t taotao-cloud-backend -m 0 -i 10 -c /checkpoint/spark/log_hudi -g /user/hudi/cow/action_data -s action_data -y cow -r jdbc:hive2://192.68.99.37:10000 -n root
object Log2Hudi {
  val LOGGER: Logger = LoggerFactory.getLogger("Log2Hudi")

  def main(args: Array[String]): Unit = {
    val config = Config.parseConfig(Log2Hudi, args);
    val spark = SparkHelper.getSparkSession(config.env);

    spark.streams.addListener(new StreamingQueryListener() {
      override def onQueryStarted(queryStarted: QueryStartedEvent): Unit = {
        LOGGER.info("Query started: " + queryStarted.id)
      }

      override def onQueryTerminated(queryTerminated: QueryTerminatedEvent): Unit = {
        LOGGER.info("Query terminated: " + queryTerminated.id)
      }

      override def onQueryProgress(queryProgress: QueryProgressEvent): Unit = {
        LOGGER.info("Query made progress: " + queryProgress.progress)
      }
    })

    import org.apache.spark.sql.functions._
    import spark.implicits._

    val dataframe: DataFrame = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", config.brokerList)
      .option("subscribe", config.sourceTopic)
      .option("staringOffsets", "earliest")
      .option("maxOffsetsPerTrigger", 100000)
      .option("failOnDataLoss", value = false)
      .option("consumer.group.id", "Log2Hudi")
      .load()

    val metaType: String = config.metaType
    val meta_log_json_str: String = MetaUtil.getMetaJson(metaType.toInt)
    val log_meta: DataFrame = spark.read.json(Seq(meta_log_json_str).toDS())
    val log_meta_schema = log_meta.schema

    val msb = spark.sparkContext.broadcast(log_meta_schema)
    dataframe.selectExpr("CAST(value as String)")
      .as[String]
      .writeStream
      .queryName("log_hudi")
      .option("checkpointLocation", config.checkpointDir + "/action/")
      .trigger(Trigger.ProcessingTime(config.trigger + " seconds"))
      .foreachBatch((batchDF: Dataset[String], batchId: Long) => {
        batchDF.persist()

        val newDF: Dataset[String] = batchDF.map(new LogExtract().unionMeatAndBody(_, meta_log_json_str))

        if (!newDF.isEmpty) {
          newDF.select(from_json($"value", msb.value).as("log_data"))
            .select("log_data.*")
            .write
            .format("org.apache.hudi")
            .options(HudiUtil.getEnentConfig(config.tableType, config.syncJDBCUrl, config.syncJDBCUsername))
            .option(HoodieWriteConfig.TABLE_NAME, config.sourceTopic)
            .option(DataSourceWriteOptions.HIVE_DATABASE_OPT_KEY, config.syncDB)
            .option(HoodieIndexConfig.BLOOM_INDEX_UPDATE_PARTITION_PATH, "true")
            .option(HoodieIndexConfig.INDEX_TYPE_PROP, HoodieIndex.IndexType.GLOBAL_BLOOM.name())
            .mode(SaveMode.Append)
            .save(config.hudiBasePath)
        }

        batchDF.unpersist()
        println()
      })
      .start()
      .awaitTermination()

    //val action_data_log_json_str = MetaUtil.getMetaJson(config.metaType)
    //val sys_access_log_json_str = MetaUtil.getMetaJson(1)

    //val action_data_log_meta = spark.read.json(Seq(action_data_log_json_str).toDS())
    //val sys_access_log_meta = spark.read.json(Seq(sys_access_log_json_str).toDS())
    //val action_data_log_schema = action_data_log_meta.schema
    //val sys_access_log_schema = sys_access_log_meta.schema
    //
    //val msb = spark.sparkContext.broadcast((action_data_log_schema, sys_access_log_schema))

    //    dataframe.selectExpr("CAST(value as String)")
    //      .as[String]
    //      .writeStream
    //      .queryName("action2hudi")
    //      //.option("checkpointLocation", config.checkpointDir + "/action/")
    //      .trigger(Trigger.ProcessingTime(config.trigger + " seconds"))
    //      .foreachBatch((batchDF: Dataset[String], batchId: Long) => {
    //        batchDF.persist()
    //
    //        val newDF: Dataset[String] = batchDF.map(new NewsAction().unionMeatAndBody(_, action_data_log_json_str))
    //
    //        if (!newDF.isEmpty) {
    //          newDF.select(from_json($"value", msb.value._1).as("action_data"))
    //            .select("action_data.*")
    //            .show()
    //          .filter("type=event")
    //          .write
    //                      .format("org.apache.hudi")
    //                      .options(HudiUtil.getEnentConfig(config.tableType, config.syncJDBCUrl, config.syncJDBCUsername))
    //                      .option(HoodieWriteConfig.TABLE_NAME, "event")
    //                      .option(DataSourceWriteOptions.HIVE_DATABASE_OPT_KEY, config.syncDB)
    //                      .option(HoodieIndexConfig.BLOOM_INDEX_UPDATE_PARTITION_PATH, "true")
    //                      .option(HoodieIndexConfig.INDEX_TYPE_PROP, HoodieIndex.IndexType.GLOBAL_BLOOM.name())
    //                      .mode(SaveMode.Append)
    //                      .save(config.hudiEventBasePath)
    //
    //                    newDF.select(from_json($"value", msb.value._2).as("data_user"))
    //                      .select("data_user.*")
    //                      .filter("type=user")
    //                      .write
    //                      .format("org.apache.hudi")
    //                      .options(HudiUtil.getUserConfig(config.tableType, config.syncJDBCUrl, config.syncJDBCUsername))
    //                      .option(HoodieWriteConfig.TABLE_NAME, "user")
    //                      .option(DataSourceWriteOptions.HIVE_DATABASE_OPT_KEY, config.syncDB)
    //                      .option(HoodieIndexConfig.BLOOM_INDEX_UPDATE_PARTITION_PATH, "true")
    //                      .option(HoodieIndexConfig.INDEX_TYPE_PROP, HoodieIndex.IndexType.GLOBAL_BLOOM.name())
    //                      .mode(S¬aveMode.Append)
    //                      .save(config.hudiUserBasePath)
    //        }
    //
    //        batchDF.unpersist()
    //        printf("----------------------")
    //      })
    //      .start()
    //      .awaitTermination()
  }
}
