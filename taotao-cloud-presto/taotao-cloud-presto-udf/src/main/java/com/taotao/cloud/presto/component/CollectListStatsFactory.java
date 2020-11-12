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
package com.taotao.cloud.presto.component;

import com.facebook.presto.spi.function.AccumulatorStateFactory;
import com.facebook.presto.spi.function.GroupedAccumulatorState;
import com.taotao.cloud.presto.udf.CollectListAggregation;

/**
 * @author dengtao
 * @date 2020/10/29 18:06
 * @since v1.0
 */
public class CollectListStatsFactory implements AccumulatorStateFactory<CollectListAggregation.CollectState> {
	@Override
	public CollectListAggregation.CollectState createSingleState() {
		return new SingleState();
	}

	@Override
	public Class<? extends CollectListAggregation.CollectState> getSingleStateClass() {
		return SingleState.class;
	}

	@Override
	public CollectListAggregation.CollectState createGroupedState() {
		return new GroupState();
	}

	@Override
	public Class<? extends CollectListAggregation.CollectState> getGroupedStateClass() {
		return GroupState.class;
	}

	public static class GroupState implements GroupedAccumulatorState, CollectListAggregation.CollectState {
		// private final ObjectBigArray<CollectListStats> collectStatsList = new ObjectBigArray<>();
		private long size;
		private long groupId;

		@Override
		public void setGroupId(long groupId) {
			this.groupId = groupId;
		}

		@Override
		public void ensureCapacity(long size) {
			//collectStatsList.ensureCapacity(size);
		}

		@Override
		public CollectListStats get() {
			//return collectStatsList.get(groupId);
			return null;
		}

		@Override
		public void set(CollectListStats value) {
			CollectListStats previous = get();
			if (previous != null) {
				size -= previous.estimatedInMemorySize();
			}
			//collectStatsList.set(groupId, value);
			size += value.estimatedInMemorySize();
		}

		@Override
		public long getEstimatedSize() {
			//return size + collectStatsList.sizeOf();
			return 0L;
		}
	}

	public static class SingleState implements CollectListAggregation.CollectState {
		private CollectListStats stats;

		@Override
		public CollectListStats get() {
			return stats;
		}

		@Override
		public void set(CollectListStats value) {
			stats = value;
		}

		@Override
		public long getEstimatedSize() {
			if (stats == null) {
				return 0;
			}
			return stats.estimatedInMemorySize();
		}
	}
}
