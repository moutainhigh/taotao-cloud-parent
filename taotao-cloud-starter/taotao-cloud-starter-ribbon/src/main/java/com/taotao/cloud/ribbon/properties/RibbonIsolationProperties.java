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
package com.taotao.cloud.ribbon.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * RibbonProperties 配置
 *
 * @author dengtao
 * @date 2017/11/17
 * @since v1.0
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "taotao.cloud.ribbon.isolation")
public class RibbonIsolationProperties {

    /**
     * 是否开启ribbon自定义隔离规则
     */
    private boolean enabled = true;
}
