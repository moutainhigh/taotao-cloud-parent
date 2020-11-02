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
package com.taotao.cloud.security.service;

import com.taotao.cloud.core.model.SecurityUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * IUserDetailsService
 *
 * @author dengtao
 * @date 2020/4/29 17:33
 */
public interface IUserDetailsService extends UserDetailsService {

    /**
     * 获取SecurityUser
     *
     * @param username  用户标识
     * @param userType  用户类型
     * @param loginType 登录类型
     * @return com.taotao.cloud.core.model.SecurityUser
     * @author dengtao
     * @date 2020/10/19 14:06
     * @since v1.0
     */
    SecurityUser loadUserSecurityUser(String username, String userType, String loginType);
}
