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
package com.taotao.cloud.ribbon.component;

import com.google.gson.Gson;
import com.taotao.cloud.common.constant.StarterNameConstant;
import com.taotao.cloud.common.exception.BaseException;
import com.taotao.cloud.common.utils.GsonUtil;
import com.taotao.cloud.common.utils.LogUtil;
import feign.Logger;
import feign.Response;
import feign.Retryer;
import feign.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Feign统一配置
 *
 * @author dengtao
 * @date 2020/6/15 11:30
 * @since v1.0
 */
@Slf4j
public class FeignComponent implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("[TAOTAO CLOUD][" + StarterNameConstant.TAOTAO_CLOUD_FEIGN_STARTER + "]" + "feign模块已启动");
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    Retryer retryer() {
        return new Retryer.Default();
    }

    @Bean
    FeignClientErrorDecoder feignClientErrorDecoder() {
        return new FeignClientErrorDecoder();
    }

    @Slf4j
    public static class FeignClientErrorDecoder implements feign.codec.ErrorDecoder {
        @Override
        public Exception decode(String methodKey, Response response) {
            String errorContent;
            try {
                errorContent = Util.toString(response.body().asReader(Charset.defaultCharset()));
                LogUtil.error("feign调用异常{0}", errorContent);
                return GsonUtil.gson().fromJson(errorContent, BaseException.class);
            } catch (IOException e) {
                e.printStackTrace();
                return new BaseException("500", e);
            }
        }
    }
}
