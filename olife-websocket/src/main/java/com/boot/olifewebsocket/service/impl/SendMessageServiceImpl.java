package com.boot.olifewebsocket.service.impl;

import com.alibaba.fastjson.JSON;
import com.boot.olifewebsocket.bean.WebSocketEndPoint;
import com.boot.olifewebsocket.config.RabbitConfig;
import com.boot.olifewebsocket.dto.MsgDto;
import com.boot.olifewebsocket.entity.OAdvisory;
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
    public String send(MsgDto msgDto) {
        String message = msgDto.getMsg();
        if (StringUtils.hasText(message)) {
            String msg = JSON.toJSONString(msgDto);
            msg = msg.replace("\\t","").replace("\\n","");
            amqpTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE,"",msg);
            return ResultEnum.SEND_SUCCESS.getTitle();
        }
        return ResultEnum.SEND_NULL.getTitle();
    }
    /**
     *
     * @param msgDto  实体类
     */
    @Override
    public void sendToUser(MsgDto msgDto) {
        List<String> persons = msgDto.getPersons();
        String message = msgDto.getMsg();
        Boolean isMaster = msgDto.getIsm();
        if (!StringUtils.hasText(message)) {
            return;
        }
        if (ObjectUtils.isEmpty(persons)) {
            WebSocketEndPoint.batchSend(message);
            return;
        }
        if (isMaster){
            OAdvisory advisory = new OAdvisory(Long.valueOf(persons.get(0)),Long.valueOf(persons.get(1)),message);
            log.info("核心对象---{}",advisory);
        }
        WebSocketEndPoint.sendToUser(persons,message);
    }
}
