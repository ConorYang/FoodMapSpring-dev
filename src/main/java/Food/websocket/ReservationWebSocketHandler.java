package Food.websocket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ReservationWebSocketHandler extends TextWebSocketHandler {

    private final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String query = session.getUri().getQuery();
        if (query != null && query.startsWith("memberId=")) {
            Long memberId = Long.valueOf(query.split("=")[1]);
            sessions.put(memberId, session);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.entrySet().removeIf(entry -> entry.getValue().equals(session));
    }

    // 推播訂位通知 
    public void sendReservationNotification(Long memberId, Object data, String type) throws Exception {
        WebSocketSession session = sessions.get(memberId);

        if (session != null && session.isOpen()) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("type", type);
            payload.put("data", data);

            String json = objectMapper.writeValueAsString(payload);
            session.sendMessage(new TextMessage(json));


        } else {
            System.out.println("無法發送,session 不存在或已關閉 (memberId=" + memberId + ")");
        }
    }
}
