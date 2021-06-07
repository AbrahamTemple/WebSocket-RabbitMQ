# WebSocket搭配RabbitMQ实现分布式广播或定点推送消息

## 代码指引

- javascript连接websocket（websocket.js）

``` javascript
function createWebSocket() {
    let host = window.location.host;
    userId = GetQueryString("userId");
    wsUri = "ws://" + host + "/websocket/" +userId;
    console.log(wsUri);
    try {
        wsObj = new WebSocket(wsUri);
        initWsEvent();
    } catch (e) {
        setMessageBody("创建失败:"+e);
    }
}
```

- RabbitMQ推送消息（WebSockert服务端）

``` java
@OnMessage
public void onMessage(String message) {
  JSONObject object = JSON.parseObject(message);
  MsgDto msgDTO = JSON.toJavaObject(object, MsgDto.class);
  if (ObjectUtils.isEmpty(msgDTO) || !StringUtils.hasText(msgDTO.getMsg())) {
    return;
  }
  if (msgDTO.getMsg().length()<1){
    log.info("{}", "消息为空，不进行发送");
    return;
  }
  String msg = JSON.toJSONString(msgDTO);
  log.info("WS已收到消息---{}", msg);
  msg = msg.replace("\\t", "").replace("\\n", "");
  if(amqpTemplate!=null) {
    amqpTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE, RabbitConfig.FANOUT_QUEUE, msg);
  } else {
    connectRabbitmq(msg);
  }
}
```

- RabbitMQ监听业务消息（Base服务端）

``` java
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
```

- WebSocket实现对用户发送消息的最终业务（WebSocketEndPoint.java）

``` java
/**
 * 定点
 * @param userId  用户id
 * @param message 消息
 */
public static void send(String userId, String message) {
  log.info("{}", userId);
  if (SESSION_MAP.containsKey(userId)) {
    SESSION_MAP.get(userId).getAsyncRemote().sendText(message);
    log.info("消息发送成功");
  } else {
    log.info("服务器找不到此Session");
  }
}

/**
 * 群发
 * @param message 消息
 */
public static void batchSend(String message) {
  for (String sessionId : SESSION_MAP.keySet()) {
    SESSION_MAP.get(sessionId).getAsyncRemote().sendText(message);
  }
}
```

- 从网页推送消息到服务器转发（vue.html）

``` javascript
let vm = new Vue({
    el: "#app",
    data: {
        selected: '',
        sendBtn: "发给服务器",
        messageTitle: "来自服务器的消息",
        selectedTitle:"要推送的用户",
        opList:[1,2,3,4,5,6]
    },
    methods: {
        sendMessageBody: function () {
            //如果指定发送对象就定点推送，如果没有则群发
            let personList=this.selected!==''?[userId,this.selected]:[];
            let data = {
                "persons": personList,
                "msg": $('#send-body').val()
            };
            wsObj.send(JSON.stringify(data));
        },
    },
    mounted(){
        createWebSocket();
    }
});
```

## 测试

- postman发送请求执行定点推送,目标id为1和2

![Screenshot](docs/postman.png)

- userId为1和2的都收到了消息

![Screenshot](docs/userId1.png)

![Screenshot](docs/userId2.png)

- userId为3的并没有收到消息

![Screenshot](docs/userId3.png)

## Vue版

- 定点与群发

![Screenshot](docs/vueUserId1.png)

![Screenshot](docs/vueUserId2.png)

![Screenshot](docs/vueUserId3.png)
