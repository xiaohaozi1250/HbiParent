package com.hand.hap.master_distributed_execution.service.impl;

import com.hand.hap.message.IMessageConsumer;
import com.hand.hap.message.TopicMonitor;
import com.hand.hap.message.websocket.CommandMessage;
import com.hand.hap.message.websocket.WebSocketSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

/**
 * @autor by Val.Zhang
 * @mail wei.zhang12@hand-china.com
 * @date 2019/1/14
 */
/*泛指各种组件，就是说当我们的类不属于各种归类的时候（不属于@Controller、@Services等的时候），我们就可以使用@Component来标注这个类。*/
@Component
//表示这个类要监听队列
@TopicMonitor(channel = {HmdmWebSocketDemo.CHANNEL_WEB_SOCKET})
public class HmdmWebSocketDemo implements IMessageConsumer<CommandMessage> {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String CHANNEL_WEB_SOCKET = "pm:prep.websockt:hmdmtest";
    @Autowired
    WebSocketSessionManager webSocketSessionManager;

    @Override
    public void onMessage(CommandMessage commandMessage, String channel) {
        //获取当前用户的会话信息
        //List<WebSocketSession> sessions = webSocketSessionManager.getSession(commandMessage.getUserName());
        //获取指定人的会话信息
        List<WebSocketSession> sessions = webSocketSessionManager.getSession("eric");
        logger.info("WebSocket Test:{}", commandMessage.getParameter().get("MSG"));
        // 同步处理多线程推送 ，未处理，则会报错 [TEXT_PARTIAL_WRITING]
        synchronized (sessions) {
            //循环推送消息
            sessions.stream().forEach(webSocketSession -> {
                if (webSocketSession.isOpen()) {
                    webSocketSessionManager.sendCommandMessage(webSocketSession, commandMessage);
                }
            });
        }
    }
}
