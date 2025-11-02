package Food.dto.member;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import Food.entity.account.User;
import Food.entity.member.Member;

public class MemberDTO {
	private Integer memberId;
	private String userName;
	private String phone;
	private String avatarURL;
	private String gender;
	private String birthdate;
	private String city;
	private String district;

	private Long accountId;
	private String account;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING)
	private LocalDateTime createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING)
	private LocalDateTime lastLogIn;

	public MemberDTO(Member member, User user) {
		this.memberId = member.getMemberId();
		this.userName = member.getUserName();
		this.phone = member.getPhone();
		this.avatarURL = member.getAvatarURL();
		this.gender = member.getGender() != null ? member.getGender() : "";
		this.birthdate = member.getBirthdate() != null ? member.getBirthdate().toString() : "";
		this.city = member.getCity() != null ? member.getCity() : "";
		this.district = member.getDistrict() != null ? member.getDistrict() : "";

		this.accountId = user.getAccountId();
		this.account = user.getAccount();
		this.createdAt = user.getCreatedAt(); // 保留 LocalDateTime，可為 null
		this.lastLogIn = user.getLastLogIn(); // 保留 LocalDateTime，可為 null
	}

	// Getter & Setter
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAvatarURL() {
		return avatarURL;
	}

	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getLastLogIn() {
		return lastLogIn;
	}

	public void setLastLogIn(LocalDateTime lastLogIn) {
		this.lastLogIn = lastLogIn;
	}
}
