package com.boot.olifewebsocket.service;

import com.boot.olifewebsocket.dto.MsgDto;

/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.5.15
 * @GitHub https://github.com/AbrahamTemple/
 * @description:
 */
public interface SendMessageService {
    /**
     *  发送消息到RabbitMQ
     * @param msgDto 对象
     * @return String
     */
    String send(MsgDto msgDto);

    /**
     * 发送消息给用户
     * @param msgDto 对象
     */
    void sendToUser(MsgDto msgDto);

}
