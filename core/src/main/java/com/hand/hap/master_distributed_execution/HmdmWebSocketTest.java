package com.hand.hap.master_distributed_execution;

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
@Component
@TopicMonitor(channel = {"pm:prep.websockt:hmdmtest"})
public class HmdmWebSocketTest implements IMessageConsumer<CommandMessage> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    WebSocketSessionManager webSocketSessionManager;

    @Override
    public void onMessage(CommandMessage commandMessage, String channel) {
        List<WebSocketSession> sessions = webSocketSessionManager.getSession(commandMessage.getSessionId());
        //List<WebSocketSession> sessions = webSocketSessionManager.getSession("lyf");
        System.out.println("222");
        logger.info("WebSocket Test:{}", commandMessage.getParameter().get("MSG"));
        sessions.stream().forEach(webSocketSession -> {
            if (webSocketSession.isOpen()) {
                webSocketSessionManager.sendCommandMessage(webSocketSession, commandMessage);
            }
        });
    }
}
