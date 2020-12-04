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
import com.facebook.presto.common.type.VarcharType;
import com.facebook.presto.spi.function.AccumulatorState;
import com.facebook.presto.spi.function.AccumulatorStateMetadata;
import com.facebook.presto.spi.function.AggregationState;
import com.facebook.presto.spi.function.CombineFunction;
import com.facebook.presto.spi.function.InputFunction;
import com.facebook.presto.spi.function.OutputFunction;
import com.facebook.presto.spi.function.SqlType;
import com.taotao.cloud.presto.udf.component.CollectListStats;
import com.taotao.cloud.presto.udf.component.CollectListStatsFactory;
import com.taotao.cloud.presto.udf.component.CollectListStatsSerializer;
import io.airlift.slice.Slice;

/**
 * @author dengtao
 * @date 2020/10/29 17:52
 * @since v1.0
 */
public class CollectListAggregation {
    @AccumulatorStateMetadata(stateSerializerClass = CollectListStatsSerializer.class, stateFactoryClass = CollectListStatsFactory.class)
    public interface CollectState extends AccumulatorState {
        CollectListStats get();

        void set(CollectListStats value);
    }

    @InputFunction
    public static void input(@AggregationState CollectState state,
                             @SqlType(StandardTypes.VARCHAR) Slice id,
                             @SqlType(StandardTypes.VARCHAR) Slice key) {
        try {
            CollectListStats stats = state.get();
            if (stats == null) {
                stats = new CollectListStats();
                state.set(stats);
            }
            int inputId = Integer.parseInt(id.toStringUtf8());
            String inputKey = key.toStringUtf8();
            stats.addCollectList(inputId, inputKey, 1);
        } catch (Exception e) {
            throw new RuntimeException(e + " ---------  input err");
        }
    }

    @CombineFunction
    public static void combine(@AggregationState CollectState state, CollectState otherState) {
        try {
            CollectListStats collectListStats = state.get();
            CollectListStats oCollectListStats = otherState.get();
            if (collectListStats == null) {
                state.set(oCollectListStats);
            } else {
                collectListStats.mergeWith(oCollectListStats);
            }
        } catch (Exception e) {
            throw new RuntimeException(e + " --------- combine err");
        }
    }

    @OutputFunction(StandardTypes.VARCHAR)
    public static void output(@AggregationState CollectState state, BlockBuilder out) {
        try {
            CollectListStats stats = state.get();
            if (stats == null) {
                out.appendNull();
                return;
            }
            // 统计
            Slice result = stats.getCollectResult();
            if (result == null) {
                out.appendNull();
            } else {
                VarcharType.VARCHAR.writeSlice(out, result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e + " -------- output err");
        }
    }
}
