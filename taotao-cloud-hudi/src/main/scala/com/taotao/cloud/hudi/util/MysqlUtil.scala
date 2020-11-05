package com.taotao.cloud.hudi.util

import java.sql.{Connection, DriverManager}

import scala.collection.mutable

class MysqlUtil private(val config: collection.mutable.Map[String, String]) {

  def getMysqlConn: Connection = {
    var conn: Connection = null
    try {
      Class.forName(config("driver"))
      conn = DriverManager.getConnection(config("url"), config("username"), config("password"))
    } catch {
      case e: Exception =>
        e.printStackTrace()

        try {
          conn.close()
        } catch {
          case e: Exception => {
            e.printStackTrace()
          }
        }
    }
    conn
  }

};

object MysqlUtil {

  val mysqlConfig: mutable.Map[String, String] = collection.mutable.Map(
    "driver" -> "com.mysql.cj.jdbc.Driver",
    "url" -> "jdbc:mysql://127.0.0.1:3306/taotao-cloud-uc-center?autoReconnect=true",
    "username" -> "root",
    "password" -> "123456"
  )


  def apply(): MysqlUtil = {
    new MysqlUtil(mysqlConfig);
  }
}
