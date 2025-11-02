package Food.entity.cuSer;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CustomerService")
public class CustomerServiceBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CustomerServiceID")
	private Integer customerServiceId;

	@Column(name = "Email", length = 30, nullable = false)
	private String email;

	@Column(name = "MemberID")
	private Integer memberId;

	@Column(name = "VendorID")
	private Integer vendorId;

	@Column(name = "Subject", length = 50, nullable = false)
	private String subject;

	@Column(name = "Context", length = 300, nullable = false)
	private String context;

	@Column(name = "CreateAt", nullable = false)
	private LocalDateTime createAt;

	@Column(name = "Reply", length = 300)
	private String reply;

	@Column(name = "ReplyAt")
	private LocalDateTime replyAt;

	@Column(name = "AccountID")
	private Integer accountId;

	@Column(name = "CS_Status", length = 10, nullable = false)
	private String csStatus;

	public CustomerServiceBean() {
	}

	public Integer getCustomerServiceId() {
		return customerServiceId;
	}

	public void setCustomerServiceId(Integer customerServiceId) {
		this.customerServiceId = customerServiceId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public LocalDateTime getReplyAt() {
		return replyAt;
	}

	public void setReplyAt(LocalDateTime replyAt) {
		this.replyAt = replyAt;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getCsStatus() {
		return csStatus;
	}

	public void setCsStatus(String csStatus) {
		this.csStatus = csStatus;
	}

}
