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
package com.taotao.cloud.common.enums;

/**
 * 日志操作类型
 *
 * @author dengtao
 * @date 2020/4/30 10:25
 * @since v1.0
 */
public enum LogOperateTypeEnum implements BaseEnum{
    /**
     * 操作记录
     */
    OPERATE_RECORD(1, "操作记录"),

    /**
     * 异常记录
     */
    EXCEPTION_RECORD(2, "异常记录");

    private Integer value;
    private String description;

    LogOperateTypeEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getNameByCode(int code) {
        for (LogOperateTypeEnum result : LogOperateTypeEnum.values()) {
            if (result.getCode() == code) {
                return result.name().toLowerCase();
            }
        }
        return null;
    }

    @Override
    public Integer getCode() {
        return value;
    }
}
