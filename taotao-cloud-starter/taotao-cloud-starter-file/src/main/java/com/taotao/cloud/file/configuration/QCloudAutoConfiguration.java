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
package com.taotao.cloud.file.configuration;

import com.taotao.cloud.file.canstants.FileCanstant;
import com.taotao.cloud.file.propeties.NginxProperties;
import com.taotao.cloud.file.propeties.QCloudProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.Assert;

/**
 * @author dengtao
 * @date 2020/10/26 10:28
 * @since v1.0
 */
@ConditionalOnProperty(name = "taotao.cloud.file.type", havingValue = FileCanstant.DFS_QCLOUD)
public class QCloudAutoConfiguration {

    private final QCloudProperties properties;

    public QCloudAutoConfiguration(QCloudProperties properties) {
        super();
        Assert.notNull(properties, "QCloudProperties为null");
        this.properties = properties;
    }

}
