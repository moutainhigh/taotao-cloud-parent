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
package org.springframework.cloud.netflix.ribbon;

import com.netflix.loadbalancer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.cloud.netflix.ribbon.SpringClientFactory.NAMESPACE;

/**
 * 扩展 spring cloud ribbon的PropertiesFactory
 * 使其能够支持 配置全局的ribbon.NFLoadBalancerRuleClassName=package.YourRule
 * 然后各个微服务还可以根据自身情况做个性化定制。如:SERVICE_ID.ribbon.NFLoadBalancerRuleClassName=package.YourRule
 *
 * @author dengtao
 * @date 2020/6/15 11:31
 * @since v1.0
 */
public class DefaultPropertiesFactory extends PropertiesFactory {

    @Autowired
    private Environment environment;

    private final Map<Class, String> classToProperty = new HashMap<>(5);

    public DefaultPropertiesFactory() {
        super();
        classToProperty.put(ILoadBalancer.class, "NFLoadBalancerClassName");
        classToProperty.put(IPing.class, "NFLoadBalancerPingClassName");
        classToProperty.put(IRule.class, "NFLoadBalancerRuleClassName");
        classToProperty.put(ServerList.class, "NIWSServerListClassName");
        classToProperty.put(ServerListFilter.class, "NIWSServerListFilterClassName");
    }

    /**
     * 重写 支持 ribbon.NFLoadBalancerRuleClassName=package.YourRule 全局配置的方式
     */
    @Override
    public String getClassName(Class clazz, String name) {
        String className = super.getClassName(clazz, name);
        // 读取全局配置
        if (!StringUtils.hasText(className) && this.classToProperty.containsKey(clazz)) {
            String classNameProperty = this.classToProperty.get(clazz);
            className = environment.getProperty(NAMESPACE + "." + classNameProperty);
        }
        return className;
    }
}
