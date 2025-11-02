package Food.entity.account;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import Food.entity.member.Member;
import Food.entity.vendor.Vendor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "Accounts")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AccountID")
	private Long accountId;

	@ManyToOne
	@JoinColumn(name = "UserID", nullable = false)
	private UserType userType;

	// 與Member關聯
	@OneToOne(mappedBy = "user")
	private Member member;

	// 與Vendor關聯
	@OneToOne(mappedBy = "user")
	@JsonIgnoreProperties("user")
	private Vendor vendor;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	@Column(name = "Account", nullable = false, unique = true)
	private String account;

	@Column(name = "Password", nullable = false)
	private String password;

	@Column(name = "VerificationToken")
	private String verificationToken;

	@Column(name = "IsVerified", nullable = false)
	private Boolean isVerified = false;

	@Column(name = "CreatedAt", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "LastLogIn")
	private LocalDateTime lastLogIn;

	@Column(name = "Status", nullable = false)
	private Integer status = 0; // 0=測試用, 1=啟用, 2=停用, 3=黑名單

	// --- 自動設定欄位 ---
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		// 每次更新 Entity 都會呼叫，登入成功時更新 LastLogIn
		// Service層手動呼叫 setLastLogIn(LocalDateTime.now())
		// 不自動設定 LastLogIn
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	// 密碼在 Service層用BCryptPasswordEncoder加密
	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	public Boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	// CreatedAt 只在註冊時設定，通常不需要 setter
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getLastLogIn() {
		return lastLogIn;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public void setLastLogIn(LocalDateTime lastLogIn) {
		this.lastLogIn = lastLogIn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

}
