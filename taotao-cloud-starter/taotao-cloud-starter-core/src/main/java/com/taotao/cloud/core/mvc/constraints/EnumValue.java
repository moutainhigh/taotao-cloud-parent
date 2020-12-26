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
package com.taotao.cloud.core.mvc.constraints;

import com.taotao.cloud.core.mvc.validator.EnumValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * EnumValue
 *
 * @author dengtao
 * @date 2020/10/14 13:39
 * @since v1.0
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumValueValidator.class})
@Repeatable(EnumValue.List.class)
public @interface EnumValue {
    // 默认错误消息
    String message() default "the integer is not one of the enum values";

    // 约束注解在验证时所属的组别
    Class<?>[] groups() default {};

    // 约束注解的有效负载
    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum> value();

    // 同时指定多个时使用
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        EnumValue[] value();
    }
}
