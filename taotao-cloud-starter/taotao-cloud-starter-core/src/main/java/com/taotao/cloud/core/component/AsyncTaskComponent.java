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
package com.taotao.cloud.core.component;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;
import com.taotao.cloud.core.properties.AsyncTaskProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.request.RequestContextListener;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 默认异步任务配置
 *
 * @author dengtao
 * @date 2020/5/2 09:12
 * @since v1.0
 */
@EnableAsync(proxyTargetClass = true)
public class AsyncTaskComponent {

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Bean
    public TaskExecutor taskExecutor(AsyncTaskProperties asyncTaskProperties) {
        ThreadPoolTaskExecutor executor = new DefaultThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncTaskProperties.getCorePoolSize());
        executor.setMaxPoolSize(asyncTaskProperties.getMaxPoolSiz());
        executor.setQueueCapacity(asyncTaskProperties.getQueueCapacity());
        executor.setThreadNamePrefix(asyncTaskProperties.getThreadNamePrefix());

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    public static class DefaultThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {
        private static final long serialVersionUID = -5887035957049288777L;

        @Override
        public void execute(Runnable task) {
            Runnable ttlRunnable = TtlRunnable.get(task);
            super.execute(ttlRunnable);
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            Callable ttlCallable = TtlCallable.get(task);
            return super.submit(ttlCallable);
        }

        @Override
        public Future<?> submit(Runnable task) {
            Runnable ttlRunnable = TtlRunnable.get(task);
            return super.submit(ttlRunnable);
        }

        @Override
        public ListenableFuture<?> submitListenable(Runnable task) {
            Runnable ttlRunnable = TtlRunnable.get(task);
            return super.submitListenable(ttlRunnable);
        }

        @Override
        public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
            Callable ttlCallable = TtlCallable.get(task);
            return super.submitListenable(ttlCallable);
        }
    }
}
