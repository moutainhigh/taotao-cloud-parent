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

import com.taotao.cloud.core.constraints.EnumValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

/**
 * EnumValueValidator
 *
 * @author dengtao
 * @date 2020/10/14 13:40
 * @since v1.0
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue, Integer> {
    private Class<? extends Enum> enumClass;
    private static final String METHOD_NAME = "toEnum";

    //这个方法做一些初始化校验
    @Override
    public void initialize(EnumValue constraintAnnotation) {
        enumClass = constraintAnnotation.value();
        try {
            // 先判断该enum是否实现了toEnum方法
            enumClass.getDeclaredMethod(METHOD_NAME, int.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("the enum class has not toEnum method", e);
        }
    }


    // 这个方法写具体的校验逻辑：校验数字是否属于指定枚举类型的范围
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        Method declareMethod;
        try {
            declareMethod = enumClass.getDeclaredMethod(METHOD_NAME, int.class);
        } catch (NoSuchMethodException e) {
            return false;
        }
        try {
            declareMethod.invoke(null, value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

