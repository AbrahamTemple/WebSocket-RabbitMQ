package com.boot.olifewebsocket.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.olifewebsocket.config.RabbitConfig;
import com.boot.olifewebsocket.entity.MsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.5.16
 * @GitHub https://github.com/AbrahamTemple/
 * @description:
 */
@ServerEndpoint("/websocket/{userId}")
@Component
@Slf4j
public class WebSocketEndPoint {

    @Resource
    AmqpTemplate amqpTemplate;

    /**
     * 用于存放所有在线客户端
     */
    private static final Map<String, Session> SESSION_MAP = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    public static Map<String, Session> getSessionMap() {
        return SESSION_MAP;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        log.info("有新的客户端上线: {}", session.getId());
        SESSION_MAP.put(userId, session);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        String sessionId = session.getId();
        log.info("有客户端离线: {}", sessionId);
        SESSION_MAP.remove(sessionId);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        JSONObject object = JSON.parseObject(message);
        MsgDTO msgDTO = JSON.toJavaObject(object, MsgDTO.class);
        if (ObjectUtils.isEmpty(msgDTO) || !StringUtils.hasText(msgDTO.getMsg())) {
            return;
        }
        String msg = JSON.toJSONString(msgDTO);
        log.info("消息：{}", msg);
        msg = msg.replace("\\t", "").replace("\\n", "");
        amqpTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE,RabbitConfig.FANOUT_QUEUE,msg);
        log.info("{}", "消息为空，不进行发送");
    }

    /**
     * @param session 消息
     * @param error   异常
     */
    @OnError
    public void onError(Session session, Throwable error) {
        String sessionId = session.getId();
        if (SESSION_MAP.get(sessionId) != null) {
            log.info("发生了错误,移除客户端: {}", sessionId);
            SESSION_MAP.remove(sessionId);
        }
        log.error("发生异常：", error);
    }

    /**
     * 指定对象发送消息
     *
     * @param message 消息对象
     */
    public static void sendToUser(List<String> persons, String message) {
        persons.forEach(userId -> send(userId, message));
    }

    /**
     * 单发 1对1
     *
     * @param userId  用户id
     * @param message 消息
     */
    public static void send(String userId, String message) {
        log.info("{}", userId);
        if (SESSION_MAP.containsKey(userId)) {
            SESSION_MAP.get(userId).getAsyncRemote().sendText(message);
            log.info("消息发送成功");
        }
        log.info("本服务器中无此用户session");
    }

    /**
     * 群发
     *
     * @param message 消息
     */
    public static void batchSend(String message) {
        for (String sessionId : SESSION_MAP.keySet()) {
            SESSION_MAP.get(sessionId).getAsyncRemote().sendText(message);
        }
    }
}
