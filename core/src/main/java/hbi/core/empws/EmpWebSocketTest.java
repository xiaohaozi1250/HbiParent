package hbi.core.empws;

import com.hand.hap.message.IMessageConsumer;
import com.hand.hap.message.TopicMonitor;
import com.hand.hap.message.websocket.CommandMessage;
import com.hand.hap.message.websocket.WebSocketSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;


/**
 * Created by La on 2018/11/9.
 */
@Component
@TopicMonitor(channel = {"pm:prep.websockt:test"})
public class EmpWebSocketTest implements IMessageConsumer<CommandMessage> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    WebSocketSessionManager webSocketSessionManager;

    @Override
    public void onMessage(CommandMessage commandMessage, String channel) {
        List<WebSocketSession> sessions = webSocketSessionManager.getSession(commandMessage.getSessionId());
        //List<WebSocketSession> sessions = webSocketSessionManager.getSession("lyf");
        logger.info("WebSocket Test:{}", commandMessage.getParameter().get("MSG"));
        sessions.stream().forEach(webSocketSession -> {
            if (webSocketSession.isOpen()) {
                webSocketSessionManager.sendCommandMessage(webSocketSession, commandMessage);
            }
        });
    }
}
