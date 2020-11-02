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
package com.taotao.cloud.p6spy.configuration;

import com.p6spy.engine.spy.P6ModuleManager;
import com.p6spy.engine.spy.P6SpyDriver;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.option.SystemProperties;
import com.taotao.cloud.p6spy.properties.P6spyProperties;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * P6spyAutoConfiguration
 *
 * @author dengtao
 * @date 2020/10/14 09:18
 * @since v1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(P6SpyDriver.class)
@ConditionalOnProperty(prefix = "taotao.cloud.p6spy", name = "enabled", havingValue = "true", matchIfMissing = true)
public class P6spyConfiguration implements ApplicationRunner {
    private final Environment environment;

    public P6spyConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) {
        P6spyConfiguration.p6spyReload(environment);
    }

    public static void p6spyReload(Environment p6spyProperties) {
        Map<String, String> defaults = P6SpyOptions.getActiveInstance().getDefaults();
        Field[] fields = P6spyProperties.class.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String propertiesName = "taotao.cloud.p6spy.".concat(fieldName);
            if (p6spyProperties.containsProperty(propertiesName)) {
                String systemPropertyValue = p6spyProperties.getProperty(propertiesName, defaults.get(fieldName));
                defaults.put(fieldName, systemPropertyValue);
            }
        }
        P6SpyOptions.getActiveInstance().load(defaults);
        P6ModuleManager.getInstance().reload();
    }
}
