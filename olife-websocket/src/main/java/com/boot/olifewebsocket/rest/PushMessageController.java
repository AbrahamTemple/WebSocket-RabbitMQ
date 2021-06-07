package com.boot.olifewebsocket.rest;

import com.alibaba.fastjson.JSONObject;
import com.boot.olifewebsocket.bean.WebSocketEndPoint;
import com.boot.olifewebsocket.dto.MsgDto;
import com.boot.olifewebsocket.service.SendMessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
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
        MsgDto msgDTO = new JSONObject(map).toJavaObject(MsgDto.class);
        return sendService.send(msgDTO);
    }

    /**
     *
     * @return 总连接数
     */
    @CrossOrigin
    @GetMapping("/getAllUser")
    public int getAllUser(){
        return WebSocketEndPoint.getSessionMap().size();
    }
}
