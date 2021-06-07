package com.cloud.olifebase.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.olifebase.dto.MsgDto;
import com.cloud.olifebase.entity.OAdvisory;
import com.cloud.olifebase.service.IOAdvisoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.OnMessage;
import java.io.IOException;

/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.5.14
 * @GitHub https://github.com/AbrahamTemple/
 * @description:
 */
@Slf4j
@Component
public class MqReceiver {

    @Resource
    IOAdvisoryService advisoryService;

    @RabbitHandler
    @RabbitListener(queues = "advisory_queue")
    @OnMessage
    public void WsReceiver(String msg) throws IOException {
        log.info("MQ已接收到消息---"+msg);
        JSONObject object  = JSON.parseObject(msg);
        MsgDto msgDto = JSON.toJavaObject(object, MsgDto.class);
        OAdvisory advisory;
        if(msgDto.getIsm()){
            advisory = new OAdvisory(Long.valueOf(msgDto.getPersons().get(1)),Long.valueOf(msgDto.getPersons().get(0)),msgDto.getMsg());
        } else {
            advisory = new OAdvisory(Long.valueOf(msgDto.getPersons().get(0)),Long.valueOf(msgDto.getPersons().get(1)),msgDto.getMsg());
        }
        advisoryService.save(advisory);
    }

}
