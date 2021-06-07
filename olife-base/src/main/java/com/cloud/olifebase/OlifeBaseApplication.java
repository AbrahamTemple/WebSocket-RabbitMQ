package com.cloud.olifebase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@MapperScan("com.cloud.olifebase.mapper")
//@ComponentScan(basePackages = "com.cloud.olifebase.config")
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class OlifeBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(OlifeBaseApplication.class, args);
    }

}
