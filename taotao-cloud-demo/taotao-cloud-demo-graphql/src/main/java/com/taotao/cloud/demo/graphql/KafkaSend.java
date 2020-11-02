/**
 * Project Name: projects
 * Package Name: com.taotao.cloud.demo.graphql
 * Date: 2020/9/23 16:48
 * Author: dengtao
 */
package com.taotao.cloud.demo.graphql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * <br>
 *
 * @author dengtao
 * @version v1.0
 * @date 2020/9/23 16:48
 */
@Component
@Slf4j
public class KafkaSend implements CommandLineRunner {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String data) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送消息失败："+ ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {

            }
        });
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 1100; i++) {
            sendMessage("access", "eyJjdGltZSI6MTYwMDg1MjAzMzMwNywiaXAiOiIxNzIuMTguMC4xIiwicHJvamVjdCI6InRhb3Rhby1jbG91ZC1iYWNrZW5kLXVpIn0=-eyJkaXN0aW5jdF9pZCI6IjE3NDQ4OWUwNjNkOTYxLTA5MDM2NzBkYmIwYTI1LTE1MzA2MjUxLTEyOTYwMDAtMTc0NDg5ZTA2M2VkNDIiLCJsaWIiOnsiJGxpYiI6ImpzIiwiJGxpYl9tZXRob2QiOiJjb2RlIiwiJGxpYl92ZXJzaW9uIjoiMS4xNS4xNiJ9LCJwcm9wZXJ0aWVzIjp7IiR0aW1lem9uZV9vZmZzZXQiOi00ODAsIiRzY3JlZW5faGVpZ2h0Ijo5MDAsIiRzY3JlZW5fd2lkdGgiOjE0NDAsIiRsaWIiOiJqcyIsIiRsaWJfdmVyc2lvbiI6IjEuMTUuMTYiLCIkbGF0ZXN0X3RyYWZmaWNfc291cmNlX3R5cGUiOiJ1cmznmoRkb21haW7op6PmnpDlpLHotKUiLCIkbGF0ZXN0X3NlYXJjaF9rZXl3b3JkIjoidXJs55qEZG9tYWlu6Kej5p6Q5aSx6LSlIiwiJGxhdGVzdF9yZWZlcnJlciI6InVybOeahGRvbWFpbuino%2BaekOWksei0pSIsIiRyZWZlcnJlciI6IiIsIiR1cmwiOiJodHRwOi8vbG9jYWxob3N0OjMwMDAvbG9naW4iLCIkdXJsX3BhdGgiOiIvbG9naW4iLCIkdGl0bGUiOiJSZWFjdCBJbmRleCIsIiRpc19maXJzdF9kYXkiOmZhbHNlLCIkaXNfZmlyc3RfdGltZSI6ZmFsc2UsIiRyZWZlcnJlcl9ob3N0IjoiIn0sImFub255bW91c19pZCI6IjE3NDQ4OWUwNjNkOTYxLTA5MDM2NzBkYmIwYTI1LTE1MzA2MjUxLTEyOTYwMDAtMTc0NDg5ZTA2M2VkNDIiLCJ0eXBlIjoidHJhY2siLCJldmVudCI6IiRwYWdldmlldyIsIl90cmFja19pZCI6MTU0NzQ1MTY2fQ%3D%3D");
        }
    }
}
