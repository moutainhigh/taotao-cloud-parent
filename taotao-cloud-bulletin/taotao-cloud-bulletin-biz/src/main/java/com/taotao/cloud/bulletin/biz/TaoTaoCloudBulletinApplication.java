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
package com.taotao.cloud.bulletin.biz;

import com.taotao.cloud.core.annotation.EnableTaoTaoCloudMVC;
import com.taotao.cloud.data.jpa.annotation.EnableTaoTaoCloudJPA;
import com.taotao.cloud.job.annotation.EnableTaoTaoCloudXxlJob;
import com.taotao.cloud.log.annotation.EnableTaoTaoCloudRequestLog;
import com.taotao.cloud.p6spy.annotation.EnableTaoTaoCloudP6spy;
import com.taotao.cloud.redis.annotation.EnableTaoTaoCloudRedis;
import com.taotao.cloud.ribbon.annotation.EnableTaoTaoCloudFeign;
import com.taotao.cloud.seata.annotation.EnableTaoTaoCloudSeata;
import com.taotao.cloud.security.annotation.EnableTaoTaoCloudOauth2ResourceServer;
import com.taotao.cloud.sentinel.annotation.EnableTaoTaoCloudSentinel;
import com.taotao.cloud.swagger.annotation.EnableTaoTaoCloudSwagger2;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * TaoTaoCloudBulletinApplication
 *
 * @author dengtao
 * @date 2020/11/26 下午7:55
 * @since v1.0
 */
@EnableTaoTaoCloudSwagger2
@EnableTaoTaoCloudOauth2ResourceServer
@EnableTaoTaoCloudJPA
@EnableTaoTaoCloudP6spy
@EnableTaoTaoCloudFeign
@EnableTaoTaoCloudMVC
@EnableTaoTaoCloudXxlJob
@EnableTaoTaoCloudRequestLog
@EnableTaoTaoCloudRedis
@EnableTaoTaoCloudSeata
@EnableTaoTaoCloudSentinel
@EnableEncryptableProperties
@EnableTransactionManagement(proxyTargetClass = true)
@EnableDiscoveryClient
@SpringBootApplication
public class TaoTaoCloudBulletinApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaoTaoCloudBulletinApplication.class, args);
	}

}
