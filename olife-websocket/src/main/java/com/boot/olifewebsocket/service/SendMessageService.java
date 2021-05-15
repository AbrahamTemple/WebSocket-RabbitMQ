package com.boot.olifewebsocket.service;

import com.boot.olifewebsocket.entity.MsgDTO;

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
     * @param msgDTO 对象
     * @return String
     */
    String send(MsgDTO msgDTO);

    /**
     * 发送消息给用户
     * @param msgDTO 对象
     */
    void sendToUser(MsgDTO msgDTO);

}
