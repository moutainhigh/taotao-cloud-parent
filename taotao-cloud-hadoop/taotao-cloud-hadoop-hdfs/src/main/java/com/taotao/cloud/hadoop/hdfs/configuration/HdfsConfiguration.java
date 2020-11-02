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
package com.taotao.cloud.hadoop.hdfs.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * HDFS配置类
 *
 * @author dengtao
 * @date 2020/10/29 15:11
 * @since v1.0
 */
@Data
@Configuration
public class HdfsConfiguration implements Serializable {

    private static final long serialVersionUID = -3927708731917979149L;

    @Value("${hdfs.path:hdfs://10.211.55.7:9001}")
    private String path;

    @Value("${hdfs.username:root}")
    private String username;
}
