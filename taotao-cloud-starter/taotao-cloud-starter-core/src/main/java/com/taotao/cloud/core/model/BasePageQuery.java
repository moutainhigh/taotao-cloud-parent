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
package com.taotao.cloud.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 基础分页查询
 *
 * @author dengtao
 * @date 2020/5/2 16:40
 * @since v1.0
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BasePageQuery implements Serializable {

    private static final long serialVersionUID = -2483306509077581330L;

    @ApiModelProperty(value = "当前第几页，默认1", example = "0")
    @NotNull(message = "当前页显示数量不能为空")
    @Builder.Default
    @Min(value = 0)
    @Max(value = Integer.MAX_VALUE)
    private Integer currentPage = 0;

    @ApiModelProperty(value = "每页显示条数，默认10", example = "10")
    @NotNull(message = "每页数据显示数量不能为空")
    @Builder.Default
    @Min(value = 5)
    @Max(value = 100)
    private Integer pageSize = 10;
}
