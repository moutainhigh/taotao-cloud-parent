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
package com.taotao.cloud.auth.api.feign.fallback;

import com.taotao.cloud.auth.api.feign.RemoteClientService;
import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * RemoteClientFallbackImpl
 *
 * @author dengtao
 * @date 2020/4/29 21:43
 * @since v1.0
 */
@AllArgsConstructor
@Component
public class RemoteClientFallbackImpl implements FallbackFactory<RemoteClientService> {
    @Override
    public RemoteClientService create(Throwable throwable) {
        return new RemoteClientService() {

        };
    }
}
