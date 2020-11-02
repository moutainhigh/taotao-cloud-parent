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
 * StringUtil
 *
 * @author dengtao
 * @date 2020/6/2 16:42
 * @since v1.0
 */
@UtilityClass
public class StringUtil {
    public String nullToEmpty(Object str) {
        return str != null ? str.toString() : "";
    }

    public boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 部分字符串获取
     *
     * @param str    str
     * @param maxlen 最大长度
     * @return java.lang.String
     * @author dengtao
     * @date 2020/10/15 15:37
     * @since v1.0
     */
    public static String subString2(String str, int maxlen) {
        if (org.springframework.util.StringUtils.isEmpty(str)) {
            return str;
        }
        if (str.length() <= maxlen) {
            return str;
        }
        return str.substring(0, maxlen);
    }

    /**
     * 部分字符串获取 超出部分末尾...
     *
     * @param str    str
     * @param maxlen maxlen
     * @return java.lang.String
     * @author dengtao
     * @date 2020/10/15 15:38
     * @since v1.0
     */
    public static String subString3(String str, int maxlen) {
        if (org.springframework.util.StringUtils.isEmpty(str)) {
            return str;
        }
        if (str.length() <= maxlen) {
            return str;
        }
        return str.substring(0, maxlen) + "...";
    }
}
