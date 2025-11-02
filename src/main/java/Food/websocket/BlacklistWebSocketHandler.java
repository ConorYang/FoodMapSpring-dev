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
public class BlacklistWebSocketHandler extends TextWebSocketHandler {

	private final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String query = session.getUri().getQuery();
		if (query != null && query.startsWith("memberId=")) {
			Long memberId = Long.valueOf(query.split("=")[1]);
			sessions.put(memberId, session);
			System.out.println("WebSocket connected: memberId=" + memberId);
			System.out.println("目前 WebSocket sessions: " + sessions.keySet());
		}
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 前端也可以用這裡傳訊息
		System.out.println("Received message: " + message.getPayload());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.entrySet().removeIf(entry -> entry.getValue().equals(session));
		System.out.println("WebSocket disconnected, current sessions: " + sessions.keySet());
	}

	public void sendBlacklistNotification(Long memberId, Object data, String type) throws Exception {
		WebSocketSession session = sessions.get(memberId);

		if (session != null && session.isOpen()) {
			Map<String, Object> payload = new HashMap<>();
			payload.put("type", type); // "add" 或 "remove"

			payload.put("data", data);

			String json = objectMapper.writeValueAsString(payload);
			session.sendMessage(new TextMessage(json));
			System.out.println("@@Sending payload: " + json);

			System.out.println("Sent blacklist notification to memberId=" + memberId);
		} else {
			System.out.println("Session 不存在或已關閉，無法發送通知");
		}
	}

}
