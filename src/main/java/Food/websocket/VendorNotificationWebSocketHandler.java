package Food.websocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import Food.entity.vendor.Vendor; // 假設你的 Vendor 類在這裡

@Component
public class VendorNotificationWebSocketHandler extends TextWebSocketHandler {

	// 儲存所有店家的連線 (vendorId -> WebSocketSession)
	private final ConcurrentHashMap<Integer, WebSocketSession> vendorSessions = new ConcurrentHashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// ✅ 除錯:印出所有 session attributes
		System.out.println("🔍 Session attributes: " + session.getAttributes());
		Object obj = session.getAttributes().get("vendor");
		if (obj instanceof Vendor vendor) { // Java 16+ pattern matching
			Integer vendorId = vendor.getVendorId();
			vendorSessions.put(vendorId, session);
			System.out.println("Vendor " + vendorId + " connected.");
		} else {
			System.out.println("⚠ 無法從 session 取得 vendor 資訊");
		}
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 店家主動傳訊息的處理（通常用不到）
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		Object obj = session.getAttributes().get("vendor");
		if (obj instanceof Vendor vendor) {
			Integer vendorId = vendor.getVendorId();
			vendorSessions.remove(vendorId);
			System.out.println("Vendor " + vendorId + " disconnected.");
		}
	}

	// 修改原本的方法,加上 type 參數
	public void sendMessageToVendor(Integer vendorId, String type, String message) {
		WebSocketSession session = vendorSessions.get(vendorId);
		if (session != null && session.isOpen()) {
			try {
				// ✅ 包含 type 和 message
				String json = String.format("{\"type\":\"%s\",\"message\":\"%s\"}", type, message);
				session.sendMessage(new TextMessage(json));
				System.out.println("📤 已推播 " + type + " 通知給 Vendor " + vendorId);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("⚠️ Vendor " + vendorId + " 不在線上");
		}
	}

	// ✅ 保留舊的方法以便向下相容 (商品審核還在用)
	public void sendMessageToVendor(Integer vendorId, String message) {
		sendMessageToVendor(vendorId, "product", message); // 預設為 product 類型
	}

}
