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
package com.taotao.cloud.core.validator;

import cn.hutool.core.util.ArrayUtil;
import com.taotao.cloud.core.constraints.IntEnums;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

/**
 * IntEnumsValidator
 *
 * @author dengtao
 * @date 2020/10/14 13:56
 * @since v1.0
 */
public class IntEnumsValidator implements ConstraintValidator<IntEnums, Integer> {
    private int[] enumList;
    private IntEnums constraintAnnotation;

    /**
     * 做初始化工作
     *
     * @param constraintAnnotation 自己定义的注解
     */
    @Override
    public void initialize(IntEnums constraintAnnotation) {
        this.enumList = constraintAnnotation.enumList();
        this.constraintAnnotation = constraintAnnotation;
    }

    /**
     * 做校验
     *
     * @param value                      待校验的值
     * @param constraintValidatorContext 上下文
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value) || ArrayUtil.contains(enumList, value)) {
            return true;
        } else {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(String.format("当前值: [%s] 不在字段范围内,字段范围为[%s]", value, Arrays.toString(enumList)))
                    .addConstraintViolation();
            return false;
        }
    }
}
