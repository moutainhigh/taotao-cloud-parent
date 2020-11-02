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
package com.taotao.cloud.auth.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * TokenVO
 *
 * @author dengtao
 * @date 2020/4/29 16:56
 * @since v1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TokenVO", description = "TokenVO")
public class TokenVO implements Serializable {

    private static final long serialVersionUID = -6656955957477645319L;

    @ApiModelProperty(value = "tokenValue")
    private String tokenValue;

    @ApiModelProperty(value = "到期时间")
    private Date expiration;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "clientId")
    private String clientId;

    @ApiModelProperty(value = "授权类型")
    private String grantType;
}
