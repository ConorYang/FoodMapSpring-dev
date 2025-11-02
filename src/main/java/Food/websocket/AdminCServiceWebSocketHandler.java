package Food.websocket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import Food.entity.cuSer.CustomerServiceBean;

@Component
public class AdminCServiceWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> adminSessions = Collections.synchronizedSet(new HashSet<>());
    private final ObjectMapper objectMapper;

    public AdminCServiceWebSocketHandler() {
        // ✅ 初始化 ObjectMapper，支援 Java 8 時間類型
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        adminSessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        adminSessions.remove(session);
    }

    public void sendNewCustomerServiceToAdmin(CustomerServiceBean cs) {
        try {


            if (adminSessions.isEmpty()) {

                return;
            }


            String payload = objectMapper.writeValueAsString(cs);
            TextMessage message = new TextMessage(payload);

            synchronized (adminSessions) {
                adminSessions.removeIf(session -> !session.isOpen());

                for (WebSocketSession session : adminSessions) {
                    session.sendMessage(message);
                }
            }

       

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
