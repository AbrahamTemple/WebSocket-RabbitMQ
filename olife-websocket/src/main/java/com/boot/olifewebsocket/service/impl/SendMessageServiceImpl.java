package com.boot.olifewebsocket.service.impl;

import com.alibaba.fastjson.JSON;
import com.boot.olifewebsocket.bean.WebSocketEndPoint;
import com.boot.olifewebsocket.config.RabbitConfig;
import com.boot.olifewebsocket.entity.MsgDTO;
import com.boot.olifewebsocket.service.SendMessageService;
import com.boot.olifewebsocket.vo.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.5.15
 * @GitHub https://github.com/AbrahamTemple/
 * @description:
 */
@Service
@Slf4j
public class SendMessageServiceImpl implements SendMessageService {

    @Resource
    AmqpTemplate amqpTemplate;

    @Override
    public String send(MsgDTO msgDTO) {
        String message = msgDTO.getMsg();
        if (StringUtils.hasText(message)) {
            String msg = JSON.toJSONString(msgDTO);
            msg = msg.replace("\\t","").replace("\\n","");
            amqpTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE,"",msg);
            return ResultEnum.SEND_SUCCESS.getTitle();
        }
        return ResultEnum.SEND_NULL.getTitle();
    }
    /**
     *
     * @param msgDTO  实体类
     */
    @Override
    public void sendToUser(MsgDTO msgDTO) {
        List<String> persons = msgDTO.getPersons();
        String message = msgDTO.getMsg();
        if (!StringUtils.hasText(message)) {
            return;
        }
        if (ObjectUtils.isEmpty(persons)) {
            WebSocketEndPoint.batchSend(message);
            return;
        }
        WebSocketEndPoint.sendToUser(persons,message);
    }
}
