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
package com.taotao.cloud.common.utils;

import javassist.ClassPool;
import javassist.LoaderClassPath;
import lombok.experimental.UtilityClass;

/**
 * ClassPoolUtils
 *
 * @author dengtao
 * @date 2020/6/2 16:33
 * @since v1.0
 */
@UtilityClass
public class ClassPoolUtil {

    private static volatile ClassPool instance;

    /**
     * 获取对象池
     *
     * @return javassist.ClassPool
     * @author dengtao
     * @date 2020/10/15 14:49
     * @since v1.0
     */
    public ClassPool getInstance() {
        if (instance == null) {
            synchronized (ClassPoolUtil.class) {
                if (instance == null) {
                    instance = ClassPool.getDefault();
                    instance.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
                }
            }
        }
        return instance;
    }
}
