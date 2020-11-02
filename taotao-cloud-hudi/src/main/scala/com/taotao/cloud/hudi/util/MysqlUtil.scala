package com.taotao.cloud.hudi.util

import java.sql.{Connection, DriverManager}

import scala.collection.mutable

class MysqlUtil private(val config: collection.mutable.Map[String, String]) {

  def getMysqlConn: Connection = {
    var conn: Connection = null
    try {
      Class.forName("driver")
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
    "driver" -> "com.mysql.jdbc.driver",
    "url" -> "jdbc:mysql://192.168.99.10:3306/biz?autoReconnect=true",
    "username" -> "root",
    "password" -> "123456"
  )

  var mysqlUtil: MysqlUtil = _

  def apply(): MysqlUtil = if (mysqlUtil == null) {
    mysqlUtil = new MysqlUtil(mysqlConfig);
    mysqlUtil
  };
}
