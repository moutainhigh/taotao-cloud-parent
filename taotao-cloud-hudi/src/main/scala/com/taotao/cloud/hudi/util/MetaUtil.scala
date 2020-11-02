package com.taotao.cloud.hudi.util

import com.alibaba.fastjson.JSONObject

import scala.collection.mutable

object MetaUtil {
  def getMetaJson(metaType: String): String = {
    val conn = MysqlUtil().getMysqlConn()
    val statement = conn.createStatement()
    var sql = ""

    metaType match {
      case "event" =>
        sql = "select field, field_type from biz.meta where meta_type = 0"
      case "user" =>
        sql = "select field, field_type from biz.meta where meta_type = 1"
      case _ =>
        return ""
    }

    val resultSet = statement.executeQuery(sql)
    val jsonMeta = new JSONObject()
    while (resultSet.next()) {
      val field = resultSet.getString("field")
      val fieldType = resultSet.getString("field_type")

      genSimpleJsonMeta(jsonMeta, field, fieldType)
    }

    conn.close()
    jsonMeta.toJSONString
  }

  def getMeta: mutable.HashMap[String, String] = {
    val columnMetaMap = new mutable.HashMap[String, String]
    val conn = MysqlUtil().getMysqlConn()
    val statement = conn.createStatement()
    val resultSet = statement.executeQuery("select field, field_type from biz.meta")

    while (resultSet.next()) {
      val field = resultSet.getString("field")
      val fieldType = resultSet.getString("field_type")

      columnMetaMap += (field -> fieldType)
    }
    conn.close()
    columnMetaMap
  }

  def genSimpleJsonMeta(jsonObj: JSONObject, field: String, fieldType: String): AnyRef = {
    fieldType.toLowerCase match {
      case "string" => jsonObj.put(field, "")
      case "int" => jsonObj.put(field, 0)
      case "bigint" => jsonObj.put(field, 0L)
      case "double" => jsonObj.put(field, 0.1)
      case "array" => jsonObj.put(field, List())
    }
  }
}
