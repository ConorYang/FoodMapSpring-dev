package Food.service.blackList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Food.dto.blackList.BlacklistDTO;
import Food.entity.blackList.Blacklist;
import Food.repository.account.UserRepository;
import Food.repository.blackList.BlacklistRepository;
import Food.websocket.BlacklistWebSocketHandler;

@Service
public class BlacklistService {

	@Autowired
	private BlacklistRepository blacklistRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BlacklistWebSocketHandler wsHandler; // 注入 WebSocketHandler

	// 查詢全部黑名單並帶帳號
	public List<BlacklistDTO> findAllWithAccount() {
		return blacklistRepository.findAll().stream().map(b -> {
			BlacklistDTO dto = new BlacklistDTO();
			dto.setMemberId(b.getMemberId());
			dto.setReason(b.getReason());

			userRepository.findByMemberId(b.getMemberId()).ifPresent(user -> dto.setAccount(user.getAccount()));

			return dto;
		}).collect(Collectors.toList());
	}

	// 新增黑名單
	public Blacklist addToBlacklist(Long memberId, String reason) {
		// 如果已經存在，先刪除舊的
		blacklistRepository.findById(memberId).ifPresent(existing -> {
			blacklistRepository.delete(existing);
		});

		Blacklist b = new Blacklist();
		b.setMemberId(memberId);
		b.setReason(reason);
		Blacklist saved = blacklistRepository.save(b);

		// ===== 推播給該會員 =====
		try {
			Map<String, Object> data = Map.of("memberId", memberId, "reason", reason);

			String type = "add";
			wsHandler.sendBlacklistNotification(memberId, data, type);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return saved;
	}

	// 移除黑名單
	public boolean removeFromBlacklist(Long memberId) {
		return blacklistRepository.findById(memberId).map(b -> {
			blacklistRepository.delete(b);

			// ===== 推播給該會員 =====
			try {
				wsHandler.sendBlacklistNotification(memberId, null, "remove");
			} catch (Exception e) {
				e.printStackTrace();
			}

			return true;
		}).orElse(false);
	}
}
