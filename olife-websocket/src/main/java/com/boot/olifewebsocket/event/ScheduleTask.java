package com.boot.olifewebsocket.event;

import com.boot.olifewebsocket.bean.WebSocketEndPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.Date;
import java.util.Map;

/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.5.15
 * @GitHub https://github.com/AbrahamTemple/
 * @description:
 */
@Component
@EnableScheduling
@Slf4j
public class ScheduleTask {
    @Scheduled(cron = "0/10 * *  * * ?")
    public void sendMessage(){
        String message = "定时报时间,现在:"+new Date();
        log.info("{}","**********定时任务执行***********");
        Map<String, Session> map = WebSocketEndPoint.getSessionMap();
        for (String userId : map.keySet()) {
            map.get(userId).getAsyncRemote().sendText(message);
        }
    }
}
