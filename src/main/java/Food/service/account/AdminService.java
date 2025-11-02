package Food.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Food.dto.account.UserDTO;
import Food.entity.account.User;
import Food.repository.account.UserRepository;
import Food.service.blackList.BlacklistService;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BlacklistService blacklistService;

	// 分頁查詢所有帳號
	public Page<User> getAccounts(String account, Integer status, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("accountId").descending());

		if (status != null) {
			return userRepository.findByAccountContainingAndStatus(account, status, pageable);
		} else {
			return userRepository.findByAccountContaining(account, pageable);
		}
	}
	
	
	// 更改狀態
	public Page<UserDTO> getAccountsDTO(String account, Integer status, int page, int size) {
		Page<User> users = getAccounts(account, status, page, size);

		return users.map(user -> {
			UserDTO dto = new UserDTO(user);
			dto.setAccountId(user.getAccountId());
			dto.setAccount(user.getAccount());
			dto.setStatus(user.getStatus());

			//給當帳號轉給黑名單用
			dto.setMemberId(user.getMember() != null ? Long.valueOf(user.getMember().getMemberId()) : null);

			return dto;
		});
	}

	// 修改帳號狀態
	@Transactional
	public boolean updateStatus(Long accountId, Integer newStatus) {
		int updated = userRepository.updateStatus(accountId, newStatus);
		if (updated == 0)
			return false;

		// 先找對應的 MemberID (給當帳號被選到黑名單用)
		Long memberId = userRepository.findMemberIdByAccountId(accountId);
		if (memberId != null) {
			if (newStatus == 3) {
				blacklistService.addToBlacklist(memberId, "手動加入黑名單");
			} else {
				blacklistService.removeFromBlacklist(memberId);
			}
		}

		return true;
	}

	// 刪除帳號
	public boolean deleteAccount(Long id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
