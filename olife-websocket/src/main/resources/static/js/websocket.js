let wsObj = null;
let wsUri = null;
let userId = -1;

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

function initWsEvent() {
    try {
        wsObj.onopen = function (event) {
            setMessageBody("WebSocket连接成功");
        };
        wsObj.onmessage = function (event) {
            setMessageBody(event.data);
        };
        wsObj.onclose = function (event) {
            setMessageBody("执行关闭事件，开始重连");
        };
        wsObj.onerror = function (event) {
            setMessageBody("执行发生错误，开始重连");
        };
    }catch (e) {
        setMessageBody("连接失败:"+e);
    }
}

function setMessageBody(body){
    console.log(body);
}
/**
 * @return {string}
 */
function GetQueryString(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
    let r = window.location.search.substr(1).match(reg); //获取url中的?后的字符串并正则
    let context = "";
    if (r != null){
        context = r[2];
    }
    reg = null;
    r = null;
    return context == null || context === "" || context === "undefine"?"":context;
}