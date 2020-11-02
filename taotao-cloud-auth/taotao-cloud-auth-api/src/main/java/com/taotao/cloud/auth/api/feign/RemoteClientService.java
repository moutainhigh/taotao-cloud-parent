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
package com.taotao.cloud.auth.api.feign;

import com.taotao.cloud.auth.api.feign.fallback.RemoteClientFallbackImpl;
import com.taotao.cloud.common.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 远程调用客户端
 *
 * @author dengtao
 * @date 2020/5/2 16:42
 * @since v1.0
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstant.TAOTAO_CLOUD_UC_CENTER, fallbackFactory = RemoteClientFallbackImpl.class)
public interface RemoteClientService {
}

