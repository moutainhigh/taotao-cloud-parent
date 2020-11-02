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
package com.taotao.cloud.common.serialize;

import java.lang.reflect.Type;

/**
 * 序列化接口
 *
 * @author dengtao
 * @date 2020/4/17 15:36
 * @since v1.0
 */
public interface Serializer {

    /**
     * 序列化
     *
     * @param object object
     * @return java.lang.String
     * @author dengtao
     * @date 2020/10/15 14:40
     * @since v1.0
     */
    String serialize(Object object);

    /**
     * 反序列化
     *
     * @param str  str
     * @param type type
     * @return T
     * @author dengtao
     * @date 2020/10/15 14:40
     * @since v1.0
     */
    <T> T deserialize(String str, Type type);

}
