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
package com.taotao.cloud.common;

import java.util.stream.IntStream;

/**
 * @author dengtao
 * @date 2020/11/18 下午10:22
 * @since v1.0
 */
public class ProduceConsumer {

	private static final Object LOCK = new Object();

	private volatile boolean flag = true;

	private int index = 1;

	public void produce() {
		synchronized (LOCK) {
			if (flag) {
				System.out.println(Thread.currentThread().getName() + "--生产一个数据: " + (index++));
				LOCK.notifyAll();
				flag = false;
			} else {
				try {
					LOCK.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void consumer() {
		synchronized (LOCK) {
			if (flag) {
				try {
					LOCK.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println(Thread.currentThread().getName() + "消费一个数据: " + (index));
				LOCK.notifyAll();
				flag = true;
			}
		}
	}

	public static void main(String[] args) {
		ProduceConsumer p = new ProduceConsumer();

		IntStream.of(1, 2, 3).forEach(e -> {
			new Thread(() -> {
				while (true) {
					p.produce();
				}
			}, "生产者一" + e).start();
		});

		// IntStream.of(1, 2).forEach(e -> {
		// 	new Thread(() -> {
		// 		while (true) {
		// 			p.consumer();
		// 		}
		// 	},"消费者一" + e).start();
		// });


	}
}
