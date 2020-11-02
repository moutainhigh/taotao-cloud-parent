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
package com.taotao.cloud.hadoop.mr.service.impl;

import com.taotao.cloud.hadoop.mr.service.MapReduceService;
import org.apache.commons.lang3.StringUtils;

/**
 * @author dengtao
 * @date 2020/10/30 17:40
 * @since v1.0
 */
public class MapReduceServiceImpl implements MapReduceService {
	// 默认reduce输出目录
	private static final String OUTPUT_PATH = "/output";

	@Override
	public void groupSort(String jobName, String inputPath) {
		if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
			return;
		}
		// 输出目录 = output/当前Job
		String outputPath = OUTPUT_PATH + "/" + jobName;
		if (HdfsService.existFile(outputPath)) {
			HdfsService.deleteFile(outputPath);
		}
		ReduceJobsUtils.groupSort(jobName, inputPath, outputPath);
	}
}
