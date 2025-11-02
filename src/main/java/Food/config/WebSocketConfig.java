package Food.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import Food.websocket.AdminCServiceWebSocketHandler;
import Food.websocket.BlacklistWebSocketHandler;
import Food.websocket.CustomerServiceWebSocketHandler;
import Food.websocket.ReservationWebSocketHandler;
import Food.websocket.VendorNotificationWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Autowired
	private BlacklistWebSocketHandler wsHandler;

	@Autowired
	private ReservationWebSocketHandler reservationHandler;

	@Autowired
	private CustomerServiceWebSocketHandler customerServiceHandler;

	@Autowired
	private AdminCServiceWebSocketHandler adminCSHandler;

	@Autowired
	private VendorNotificationWebSocketHandler vendorHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		System.out.println("Registering WebSocket handlers");

		// 黑名單 WebSocket
		registry.addHandler(wsHandler, "/ws/blacklist").setAllowedOrigins("*");

		// 訂位通知 WebSocket
		registry.addHandler(reservationHandler, "/ws/reservation").setAllowedOrigins("*");

		// 使用者客訴回覆通知
		registry.addHandler(customerServiceHandler, "/ws/cs").setAllowedOrigins("*");

		// 管理者客訴通知
		registry.addHandler(adminCSHandler, "/ws/admin-cs").setAllowedOrigins("*");
		
		//店家通知 webSocket
		registry.addHandler(vendorHandler, "/ws/vendor")
				.setAllowedOrigins("*")
				.addInterceptors(new HttpSessionHandshakeInterceptor());

	}
}
