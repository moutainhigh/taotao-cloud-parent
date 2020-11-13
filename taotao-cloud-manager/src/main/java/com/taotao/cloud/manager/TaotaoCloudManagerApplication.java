package com.taotao.cloud.manager;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TaotaoCloudManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaotaoCloudManagerApplication.class, args);
    }

}
