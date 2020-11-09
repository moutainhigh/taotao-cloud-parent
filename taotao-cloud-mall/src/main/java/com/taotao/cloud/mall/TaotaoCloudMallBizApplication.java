package com.taotao.cloud.mall;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TaotaoCloudMallBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaotaoCloudMallBizApplication.class, args);
    }

}
