package com.boot.olifewebsocket.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.olifewebsocket.entity.MsgDTO;
import com.boot.olifewebsocket.service.SendMessageService;
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
 * @date: 2021.5.16
 * @GitHub https://github.com/AbrahamTemple/
 * @description:
 */
@Slf4j
@Component
public class RabbitReceiver {

    @Resource
    SendMessageService sendMessageService;

    @RabbitHandler
    @RabbitListener(queues = RabbitConfig.FANOUT_QUEUE)
    @OnMessage
    public void WsReceiver(String msg) throws IOException {
        log.info("Ws已接收到消息---"+msg);
        JSONObject object  = JSON.parseObject(msg);
        MsgDTO msgDTO = JSON.toJavaObject(object, MsgDTO.class);
        sendMessageService.sendToUser(msgDTO);
    }
}
