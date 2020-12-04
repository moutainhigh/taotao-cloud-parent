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

import com.facebook.presto.common.block.BlockBuilder;
import com.facebook.presto.common.type.StandardTypes;
import com.facebook.presto.spi.function.AggregationFunction;
import com.facebook.presto.spi.function.AggregationState;
import com.facebook.presto.spi.function.CombineFunction;
import com.facebook.presto.spi.function.InputFunction;
import com.facebook.presto.spi.function.OutputFunction;
import com.facebook.presto.spi.function.SqlType;
import com.taotao.cloud.presto.udf.component.LongAndDoubleState;
import com.taotao.cloud.presto.udf.utils.PrecisionUtil;

import static com.facebook.presto.common.type.DoubleType.DOUBLE;

/**
 * @author dengtao
 * @date 2020/10/29 17:58
 * @since v1.0
 */
@AggregationFunction("_avg")
public class AVG {
    private AVG() {
    }

    @InputFunction
    public static void input(@AggregationState LongAndDoubleState state, @SqlType(StandardTypes.BIGINT) long value) {
        state.setLong(state.getLong() + 1);
        state.setDouble(state.getDouble() + value);
    }

    @InputFunction
    public static void input(@AggregationState LongAndDoubleState state, @SqlType(StandardTypes.DOUBLE) double value) {
        state.setLong(state.getLong() + 1);
        state.setDouble(state.getDouble() + value);
    }

    @CombineFunction
    public static void combine(@AggregationState LongAndDoubleState state,
                               @AggregationState LongAndDoubleState otherState) {
        state.setLong(state.getLong() + otherState.getLong());
        state.setDouble(state.getDouble() + otherState.getDouble());
    }

    @OutputFunction(StandardTypes.DOUBLE)
    public static void output(@AggregationState LongAndDoubleState state, BlockBuilder out) {
        long count = state.getLong();
        if (count == 0) {
            out.appendNull();
        } else {
            double value = state.getDouble();
            double tmp = PrecisionUtil.getPrecision(value / count);
            DOUBLE.writeDouble(out, tmp);
        }
    }

}
