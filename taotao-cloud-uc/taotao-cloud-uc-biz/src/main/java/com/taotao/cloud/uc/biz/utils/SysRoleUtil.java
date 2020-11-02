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
package com.taotao.cloud.uc.biz.utils;

import com.taotao.cloud.common.utils.BeanUtil;
import com.taotao.cloud.uc.api.vo.role.RoleVO;
import com.taotao.cloud.uc.biz.entity.SysRole;

/**
 * @author dengtao
 * @date 2020/10/20 16:16
 * @since v1.0
 */
public class SysRoleUtil {
    public static RoleVO copy(SysRole role) {
        RoleVO vo = RoleVO.builder().build();
        BeanUtil.copyIncludeNull(role, vo);
        return vo;
    }
}
