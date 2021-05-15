package com.boot.olifewebsocket.rest;

import com.alibaba.fastjson.JSONObject;
import com.boot.olifewebsocket.entity.MsgDTO;
import com.boot.olifewebsocket.service.SendMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.5.15
 * @GitHub https://github.com/AbrahamTemple/
 * @description:
 */
@RestController
@RequestMapping("/send")
public class PushMessageController {

    @Resource
    SendMessageService sendService;

    /**
     *
     * @param map 用户+消息  用户为空则为群发
     * @return String
     */
    @GetMapping("/sendToUser")
    public String sendToUser(@RequestBody Map<String, Object> map) {
        JSONObject param = new JSONObject(map);
        MsgDTO msgDTO = param.toJavaObject(MsgDTO.class);
        return sendService.send(msgDTO);
    }
}
