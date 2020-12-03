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
package com.taotao.cloud.presto.udf.udf;

import com.facebook.presto.common.type.StandardTypes;
import com.facebook.presto.spi.function.Description;
import com.facebook.presto.spi.function.ScalarFunction;
import com.facebook.presto.spi.function.SqlType;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dengtao
 * @date 2020/10/29 17:36
 * @since v1.0
 */
public class ToDate {
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    @ScalarFunction
    @Description("hive to_date function")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice toDate(@SqlType(StandardTypes.TIMESTAMP) long input) {
        final DateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return Slices.utf8Slice(format.format(new Date(input)));
    }
}
