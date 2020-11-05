package com.taotao.cloud.hudi.config

import scopt.OptionParser

case class Config(
                   env: String = "",
                   brokerList: String = "",
                   sourceTopic: String = "",
                   metaType: String = "",
                   checkpointDir: String = "",
                   path: String = "",
                   trigger: String = "50",
                   hudiBasePath: String = "",
                   tableType: String = "COW",
                   syncDB: String = "",
                   syncJDBCUrl: String = "",
                   syncJDBCUsername: String = ""
                 )

object Config {
  def parseConfig(obj: Object, args: Array[String]): Config = {
    val programName = obj.getClass.getSimpleName.replaceAll("\\$", "")
    val parser = new OptionParser[Config]("spark ss hudi" + programName) {
      head(programName, "3.x")
      opt[String]('e', "env").required().action((x, config) => config.copy(env = x)).text("env dev or prod")
      opt[String]('b', "brokerList").required().action((x, config) => config.copy(brokerList = x)).text("brokerList")
      opt[String]('t', "sourceTopic").required().action((x, config) => config.copy(sourceTopic = x)).text("sourceTopic")
      opt[String]('m', "metaType").required().action((x, config) => config.copy(metaType = x)).text("metaType")

      programName match {
        case "Log2Console" =>

        case "Log2Hdfs" =>
          opt[String]('c', "checkpointDir").required().action((x, config) => config.copy(checkpointDir = x)).text("checkpointDir")
          opt[String]('p', "path").required().action((x, config) => config.copy(path = x)).text("path")
          opt[String]('i', "trigger").required().action((x, config) => config.copy(trigger = x)).text("trigger")

        case "Log2Hudi" =>
          opt[String]('i', "trigger").required().action((x, config) => config.copy(trigger = x)).text("trigger")
          opt[String]('c', "checkpointDir").required().action((x, config) => config.copy(checkpointDir = x)).text("checkpointDir")
          opt[String]('g', "hudiBasePath").required().action((x, config) => config.copy(hudiBasePath = x)).text("hudiBasePath")
          opt[String]('s', "syncDB").required().action((x, config) => config.copy(syncDB = x)).text("syncDB")
          opt[String]('y', "tableType").required().action((x, config) => config.copy(tableType = x)).text("tableType")
          opt[String]('r', "syncJDBCUrl").required().action((x, config) => config.copy(syncJDBCUrl = x)).text("syncJDBCUrl")
          opt[String]('n', "syncJDBCUsername").required().action((x, config) => config.copy(syncJDBCUsername = x)).text("syncJDBCUsername")
      }
    }

    parser.parse(args, Config()) match {
      case Some(conf) => conf
      case None =>
        System.exit(-1)
        null
    }
  }
}
