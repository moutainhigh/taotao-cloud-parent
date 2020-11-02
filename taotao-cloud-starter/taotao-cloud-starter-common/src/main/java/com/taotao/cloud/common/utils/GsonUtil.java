/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.taotao.cloud.common.utils;

import com.google.gson.*;
import com.taotao.cloud.common.constant.CommonConstant;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * GsonUtil
 *
 * @author dengtao
 * @date 2020/6/2 16:36
 * @since v1.0
 */
@UtilityClass
public class GsonUtil {

    /**
     * 对象转字符串
     *
     * @param src 源对象
     * @return java.lang.String
     * @author dengtao
     * @date 2020/10/15 15:09
     * @since v1.0
     */
    public String toGson(Object src) {
        Gson gson = gson();
        return gson.toJson(src);
    }

    /**
     * 获取gosn
     *
     * @return com.google.gson.Gson
     * @author dengtao
     * @date 2020/10/15 15:09
     * @since v1.0
     */
    public Gson gson() {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .setDateFormat(CommonConstant.DATETIME_FORMAT)
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .serializeNulls()
                .create();
    }

    /**
     * 格式化 LocalDateTime
     *
     * @author dengtao
     * @date 2020/10/15 15:09
     * @since v1.0
     */
    public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime> {
        @Override
        public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(localDateTime.format(CommonConstant.DATETIME_FORMATTER));
        }
    }

}
