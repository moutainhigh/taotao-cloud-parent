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

import lombok.experimental.UtilityClass;

/**
 * EnumUtils
 *
 * @author dengtao
 * @date 2020/6/2 16:35
 * @since v1.0
 */
@UtilityClass
public class EnumUtil {

    /**
     * 通过属性获取枚举
     *
     * @param enumType enumType
     * @param name     name
     * @return T
     * @author dengtao
     * @date 2020/10/15 14:57
     * @since v1.0
     */
    public <T extends Enum<?>> T lookup(Class<T> enumType, String name) {
        for (T t : enumType.getEnumConstants()) {
            if (t.name().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }
}
