package com.taotao.cloud.hudi.config

import scopt.OptionParser

case class Config(
                   env: String = "",
                   brokerList: String = "",
                   sourceTopic: String = "",
                   checkpointDir: String = "",
                   path: String = "",
                   trigger: String = "300",
                   hudiEventBasePath: String = "",
                   hudiUserBasePath: String = "",
                   tableType: String = "COW",
                   syncDB: String = "",
                   syncJDBCUrl: String = "",
                   syncJDBCUsername: String = ""
                 )

object Config {
  def parseConfig(obj: Object, args: Array[String]): Config = {
    val programName = obj.getClass.getSimpleName.replaceAll("\\$", "")
    val parser = new OptionParser[Config]("spark ss hudi" + programName) {
      head(programName, "1.0")
      opt[String]('e', "env").required().action((x, config) => config.copy(env = x)).text("env dev or prod")
      opt[String]('b', "brokerList").required().action((x, config) => config.copy(brokerList = x)).text("brokerList")
      opt[String]('t', "sourceTopic").required().action((x, config) => config.copy(sourceTopic = x)).text("sourceTopic")

      programName match {
        case "Log2Console" =>

        case "Log2Hdfs" =>
          opt[String]('c', "checkpointDir").required().action((x, config) => config.copy(checkpointDir = x)).text("checkpointDir")
          opt[String]('p', "path").required().action((x, config) => config.copy(path = x)).text("path")
          opt[String]('i', "trigger").required().action((x, config) => config.copy(trigger = x)).text("trigger")

        case "Log2Hudi" =>
          opt[String]('i', "trigger").required().action((x, config) => config.copy(trigger = x)).text("trigger")
          opt[String]('c', "checkpointDir").required().action((x, config) => config.copy(checkpointDir = x)).text("checkpointDir")
          opt[String]('g', "hudiEventBasePath").required().action((x, config) => config.copy(hudiEventBasePath = x)).text("hudiEventBasePath")
          opt[String]('u', "hudiUserBasePath").required().action((x, config) => config.copy(hudiUserBasePath = x)).text("hudiUserBasePath")
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
