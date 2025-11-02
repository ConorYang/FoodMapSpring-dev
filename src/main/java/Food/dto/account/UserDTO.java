package Food.dto.account;

import Food.entity.account.User;
import Food.entity.account.UserType;

public class UserDTO {
	private Long accountId;
	private String account;
	private UserType userId;
	private Integer status;
	private Long memberId;
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public UserDTO() {
	}

	public UserDTO(User user) {
		this.accountId = user.getAccountId();
		this.account = user.getAccount();
		this.userId = user.getUserType();
		this.status = user.getStatus();
		this.memberId = (user.getMember() != null) ? Long.valueOf(user.getMember().getMemberId()) : null;

	}


	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public UserType getUserId() {
		return userId;
	}

	public void setUserId(UserType userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
