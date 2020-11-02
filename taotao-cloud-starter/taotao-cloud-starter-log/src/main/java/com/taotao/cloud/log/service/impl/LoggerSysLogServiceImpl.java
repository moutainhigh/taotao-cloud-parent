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
package com.taotao.cloud.log.service.impl;

import com.taotao.cloud.log.model.SysLog;
import com.taotao.cloud.log.service.ISysLogService;
import lombok.extern.slf4j.Slf4j;

/**
 * 审计日志实现类-logger
 *
 * @author dengtao
 * @date 2020/5/2 11:18
 * @since v1.0
 */
@Slf4j
public class LoggerSysLogServiceImpl implements ISysLogService {

    @Override
    public void save(SysLog sysLog) {
        log.info("本地日志记录成功：{}", sysLog);
    }
}
