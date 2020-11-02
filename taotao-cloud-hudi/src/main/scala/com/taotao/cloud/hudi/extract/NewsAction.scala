package com.taotao.cloud.hudi.extract

import java.text.SimpleDateFormat
import java.util.Base64

import com.alibaba.fastjson.{JSON, JSONObject}

class NewsAction extends java.io.Serializable {

  def unionMeatAndBody(base64Line: String): String = {
    try {
      val textArray = base64Line.split("[-]]")
      if (textArray.length != 2) {
        return null
      }

      val metaBytes = Base64.getDecoder.decode(textArray(0))
      val meta = new String(metaBytes)

      val bodyBytes = Base64.getDecoder.decode(textArray(1))
      val body = new String(bodyBytes)

      val metaJson = JSON.parseObject(meta);
      val bodyJson = JSON.parseObject(body);

      val unionJson = new JSONObject()
      val contentJson = bodyJson.getJSONObject("content")
      val propertiesJson = contentJson.getJSONObject("properties")
      unionJson.putAll(propertiesJson.asInstanceOf[java.util.Map[String, _]])

      contentJson.remove("properties")
      unionJson.putAll(contentJson.asInstanceOf[java.util.Map[String, _]])
      unionJson.putAll(metaJson.asInstanceOf[java.util.Map[String, _]])

      val sdf = new SimpleDateFormat("yyyyMMdd")
      val serverTime = metaJson.getBigInteger("ctime")
      val logDay = sdf.format(serverTime)

      unionJson.put("logday", logDay)

      unionJson.toJSONString
    } catch {
      case e: Exception =>
        null
    }
  }

}
