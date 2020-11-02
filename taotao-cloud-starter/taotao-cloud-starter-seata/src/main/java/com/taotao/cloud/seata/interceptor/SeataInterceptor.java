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
package com.taotao.cloud.seata.interceptor;

import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;

/**
 * @author dengtao
 * @date 2020/10/22 17:00
 * @since v1.0
 */
public class SeataInterceptor implements RequestInterceptor {
    // 这里在feign请求的header中加入xid
    // 注意：这里一定要将feign.hystrix.enabled设为false，因为为true时feign是通过线程池调用，而XID并不是一个InheritablThreadLocal变量。
    @Override
    public void apply(RequestTemplate template) {
        String xid = RootContext.getXID();
        if (StrUtil.isNotBlank(xid)) {
            template.header("xid", xid);
        }
    }
}
