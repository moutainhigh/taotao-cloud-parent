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
package com.taotao.cloud.core.lock;

/**
 * 分布式锁顶级接口
 * <p>
 * 例如：
 * RETRY_TIMES=100，SLEEP_MILLIS=100
 * RETRY_TIMES * SLEEP_MILLIS = 10000 意味着如果一直获取不了锁，最长会等待10秒后抛超时异常
 *
 * @author dengtao
 * @date 2020/4/30 10:25
 * @since v1.0
 */
public interface DistributedLock {

	/**
	 * 默认超时时间
	 */
	long TIMEOUT_MILLIS = 5000;

	/**
	 * 重试次数
	 */
	int RETRY_TIMES = 100;

	/**
	 * 每次重试后等待的时间
	 */
	long SLEEP_MILLIS = 100;

	/**
	 * 获取锁
	 *
	 * @param key key
	 * @return boolean
	 * @author dengtao
	 * @date 2020/10/15 15:39
	 * @since v1.0
	 */
	Boolean lock(String key);

	/**
	 * 获取锁
	 *
	 * @param key        key
	 * @param retryTimes 重试次数
	 * @return boolean
	 * @author dengtao
	 * @date 2020/10/15 15:39
	 * @since v1.0
	 */
	Boolean lock(String key, int retryTimes);

	/**
	 * 获取锁
	 *
	 * @param key         key
	 * @param retryTimes  重试次数
	 * @param sleepMillis 获取锁失败的重试间隔
	 * @return boolean
	 * @author dengtao
	 * @date 2020/10/15 15:40
	 * @since v1.0
	 */
	Boolean lock(String key, int retryTimes, long sleepMillis);

	/**
	 * 获取锁
	 *
	 * @param key    key
	 * @param expire 获取锁超时时间
	 * @return boolean
	 * @author dengtao
	 * @date 2020/10/15 15:40
	 * @since v1.0
	 */
	Boolean lock(String key, long expire);

	/**
	 * 获取锁
	 *
	 * @param key        key
	 * @param expire     获取锁超时时间
	 * @param retryTimes 重试次数
	 * @return boolean
	 * @author dengtao
	 * @date 2020/10/15 15:40
	 * @since v1.0
	 */
	Boolean lock(String key, long expire, int retryTimes);

	/**
	 * 获取锁
	 *
	 * @param key         key
	 * @param expire      获取锁超时时间
	 * @param retryTimes  重试次数
	 * @param sleepMillis 获取锁失败的重试间隔
	 * @return boolean
	 * @author dengtao
	 * @date 2020/10/15 15:40
	 * @since v1.0
	 */
	Boolean lock(String key, long expire, int retryTimes, long sleepMillis);

	/**
	 * 释放锁
	 *
	 * @param key key
	 * @return boolean
	 * @author dengtao
	 * @date 2020/10/15 15:41
	 * @since v1.0
	 */
	Boolean releaseLock(String key);
}
