package Food.controller.blacklist;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Food.entity.blackList.Blacklist;
import Food.service.blackList.BlacklistService;
import Food.websocket.BlacklistWebSocketHandler;

@RestController
@RequestMapping("/api/blacklist")
public class BlacklistController {

	@Autowired
	private BlacklistService blacklistService;

	// 取得所有黑名單
	@GetMapping("/list")
	public ResponseEntity<?> getAllBlacklist() {
		return ResponseEntity.ok(blacklistService.findAllWithAccount());
	}

	// 新增黑名單
	@PostMapping
public ResponseEntity<?> addBlacklist(@RequestBody Map<String, Object> body) throws Exception {
    Long memberId = ((Number) body.get("memberId")).longValue();
    String reason = (String) body.get("reason");

    // 新增黑名單到資料庫
    Blacklist result = blacklistService.addToBlacklist(memberId, reason);

    // 發送 WebSocket 通知前端
    wsHandler.sendBlacklistNotification(memberId, result, "add");

    // 回傳結果
    return ResponseEntity.ok(result);
}


	// 移除黑名單
	@PostMapping("remove")
	public ResponseEntity<?> removeFromBlacklist(@RequestBody Map<String, Object> body) {
		Long memberId = ((Number) body.get("memberId")).longValue();
		boolean removed = blacklistService.removeFromBlacklist(memberId);
		return ResponseEntity.ok(removed);
	}

	@Autowired
	private BlacklistWebSocketHandler wsHandler;

	public void notifyBlacklist(Long memberId, Blacklist blacklist, String type) throws Exception {
		wsHandler.sendBlacklistNotification(memberId, blacklist, type);
	}

}
