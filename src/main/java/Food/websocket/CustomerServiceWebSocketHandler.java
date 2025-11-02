package Food.websocket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import Food.entity.cuSer.CustomerServiceBean;

@Component
public class CustomerServiceWebSocketHandler extends TextWebSocketHandler {

    private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("客服 WebSocket 連線已建立: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status)
            throws Exception {
        sessions.remove(session);
        System.out.println("客服 WebSocket 連線已關閉: " + session.getId());
    }

    // 將新增案件推送給所有連線客戶端
    public void sendNewCase(CustomerServiceBean cs) {
        try {
            String payload = objectMapper.writeValueAsString(cs);
            TextMessage message = new TextMessage(payload);

            synchronized (sessions) {
                for (WebSocketSession session : sessions) {
                    if (session.isOpen()) {
                        session.sendMessage(message);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
