package com.boot.olifewebsocket;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//websocket是需要依赖tomcat等容器的启动。所以在测试过程中我们要真正的启动一个tomcat作为容器
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OlifeWebsocketApplicationTests {

    @Test
    void contextLoads() {
    }

}
