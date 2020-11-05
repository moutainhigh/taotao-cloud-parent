package com.taotao.cloud.hudi.util

import com.alibaba.fastjson.JSONObject

import scala.collection.mutable

object MetaUtil {
  def getMetaJson(metaType: Int): String = {
    val conn = MysqlUtil().getMysqlConn
    val statement = conn.createStatement()
    val sql = "select field, field_type from log_meta where meta_type = " + metaType

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
    val conn = MysqlUtil().getMysqlConn
    val statement = conn.createStatement()
    val resultSet = statement.executeQuery("select field, field_type from log_meta")

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
      case "boolean" => jsonObj.put(field, false)
      case "datetime" => jsonObj.put(field, null)
      case "array" => jsonObj.put(field, List())
    }
  }
}
