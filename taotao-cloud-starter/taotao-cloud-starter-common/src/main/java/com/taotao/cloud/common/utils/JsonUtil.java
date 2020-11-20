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

import com.fasterxml.jackson.core.type.TypeReference;
import com.taotao.cloud.common.serialize.JsonSerializer;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Type;

/**
 * JsonUtils
 *
 * @author dengtao
 * @date 2020/6/2 16:36
 * @since v1.0
 */
@UtilityClass
public class JsonUtil {

    public final JsonSerializer DEFAULT = new JsonSerializer();

    /**
     * 序列化
     *
     * @param object 对象
     * @return java.lang.String
     * @author dengtao
     * @date 2020/10/15 15:15
     * @since v1.0
     */
    public String serialize(Object object) {
        return DEFAULT.serialize(object);
    }

    /**
     * 反序列化
     *
     * @param str  str
     * @param type 类型
     * @return T
     * @author dengtao
     * @date 2020/10/15 15:15
     * @since v1.0
     */
    public <T> T deserialize(String str, Type type) {
        return DEFAULT.deserialize(str, type);
    }

    /**
     * 反序列化
     *
     * @param str  str
     * @param type 类型
     * @return T
     * @author dengtao
     * @date 2020/10/15 15:16
     * @since v1.0
     */
    public <T> T deserialize(String str, TypeReference<T> type) {
        return DEFAULT.deserialize(str, type.getType());
    }
}
