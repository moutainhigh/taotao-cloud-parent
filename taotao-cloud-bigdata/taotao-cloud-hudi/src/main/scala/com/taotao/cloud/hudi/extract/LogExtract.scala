package com.taotao.cloud.hudi.extract

import java.text.SimpleDateFormat
import java.util.Base64

import com.alibaba.fastjson.{JSON, JSONObject}
import org.slf4j.{Logger, LoggerFactory}

class LogExtract extends java.io.Serializable {
  val LOGGER: Logger = LoggerFactory.getLogger("LogExtract")

  def unionMeatAndBody(base64Line: String, jsonStr: String): String = {
    try {
      val textArray = base64Line.split("-")
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
      val propertiesJson = bodyJson.getJSONObject("properties")
      val libJson = bodyJson.getJSONObject("lib")
      unionJson.putAll(propertiesJson.asInstanceOf[java.util.Map[String, _]])
      unionJson.putAll(libJson.asInstanceOf[java.util.Map[String, _]])

      bodyJson.remove("properties")
      bodyJson.remove("lib")

      unionJson.putAll(bodyJson.asInstanceOf[java.util.Map[String, _]])
      unionJson.putAll(metaJson.asInstanceOf[java.util.Map[String, _]])

      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      val serverTime = metaJson.getBigInteger("ctime")
      val logDay = sdf.format(serverTime)

      unionJson.put("logday", logDay)
      unionJson.put("uuid", logDay)

      val json = JSON.parseObject(jsonStr)
      unionJson.forEach((key, value) => {json.put(key.trim(), value)})

      val result = new JSONObject()
      json.forEach((key, value) => {
        if(key.startsWith("$") || key.startsWith("_")){
          result.put(key.substring(1, key.length).trim(), value)
        }else{
          result.put(key.trim(), value)
        }
      })

      LOGGER.info("result==============" + result)
      result.toJSONString
    } catch {
      case e: Exception =>
        null
    }
  }

}
