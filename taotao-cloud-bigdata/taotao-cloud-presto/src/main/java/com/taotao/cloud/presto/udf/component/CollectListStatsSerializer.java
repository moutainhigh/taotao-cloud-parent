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
package com.taotao.cloud.presto.udf.component;

import com.facebook.presto.common.block.Block;
import com.facebook.presto.common.block.BlockBuilder;
import com.facebook.presto.common.type.Type;
import com.facebook.presto.spi.function.AccumulatorStateSerializer;
import com.taotao.cloud.presto.udf.udf.CollectListAggregation;

import static com.facebook.presto.common.type.VarbinaryType.VARBINARY;

/**
 * @author dengtao
 * @date 2020/10/29 18:04
 * @since v1.0
 */
public class CollectListStatsSerializer implements AccumulatorStateSerializer<CollectListAggregation.CollectState> {
    @Override
    public Type getSerializedType() {
        return VARBINARY;
    }

    @Override
    public void serialize(CollectListAggregation.CollectState state, BlockBuilder out) {
        if (state.get() == null) {
            out.appendNull();
        } else {
            VARBINARY.writeSlice(out, state.get().serialize());
        }
    }

    @Override
    public void deserialize(Block block, int index, CollectListAggregation.CollectState state) {
        state.set(new CollectListStats(VARBINARY.getSlice(block, index)));
    }
}
