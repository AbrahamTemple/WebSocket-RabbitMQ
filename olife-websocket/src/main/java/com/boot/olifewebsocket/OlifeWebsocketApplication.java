package com.boot.olifewebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OlifeWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(OlifeWebsocketApplication.class, args);
    }

}
