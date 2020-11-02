package com.taotao.cloud.demo.graphql;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

@SpringBootApplication
@EnableKafka
@Slf4j
public class TaotaoCloudDemoGraphqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaotaoCloudDemoGraphqlApplication.class, args);
    }

//	@KafkaListener(topics = "access")
//	public void onMessage(ConsumerRecord<String, String> record) {
//		String value = record.value();
//		log.info(value);
//		if (value.length() % 2 == 0) {
//			throw new RuntimeException("模拟业务出错");
//		}
//	}



}
