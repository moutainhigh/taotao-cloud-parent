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

import com.taotao.cloud.core.mvc.validator.IntEnumsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * IntEnums
 *
 * @author dengtao
 * @date 2020/10/14 13:39
 * @since v1.0 RequireDigital
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Constraint(validatedBy = IntEnumsValidator.class)
public @interface IntEnums {
    int[] enumList() default {};

    String message() default "当前值不在字段范围内";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
