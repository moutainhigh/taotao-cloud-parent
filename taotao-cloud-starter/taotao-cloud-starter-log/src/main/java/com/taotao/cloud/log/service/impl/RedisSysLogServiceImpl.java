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

import com.taotao.cloud.common.utils.GsonUtil;
import com.taotao.cloud.log.model.SysLog;
import com.taotao.cloud.log.service.ISysLogService;
import com.taotao.cloud.redis.repository.RedisRepository;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 审计日志实现类-redis
 *
 * @author dengtao
 * @date 2020/5/2 11:18
 * @since v1.0
 */
@Slf4j
public class RedisSysLogServiceImpl implements ISysLogService {

    private final static String SYS_LOG = "sys:log:request:";

    @Resource
    private RedisRepository redisRepository;

    @Override
    public void save(SysLog sysLog) {
        String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault()).format(Instant.now());
        Long index = redisRepository.leftPush(SYS_LOG + date, GsonUtil.toGson(sysLog));
        if (index > 0) {
            log.info("redis远程日志记录成功：{}", sysLog.getActionUrl());
        } else {
            log.error("redis远程日志记录失败：{}", sysLog);
        }
    }
}
