package Food.entity.shopping;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Payments")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PaymentID")
	private Integer paymentId;

	@Column(name = "PaymentMethod", nullable = false, length = 10)
	private String paymentMethod;

	@Column(name = "PaymentStatus", nullable = false, length = 10)
	private String paymentStatus;

	@Column(name = "TransactionID", nullable = false, length = 100)
	private String transactionId;

	@Column(name = "PaymentDate", nullable = false)
	private LocalDateTime paymentDate;

	// 預設建構子
	public Payment() {
		this.paymentDate = LocalDateTime.now();
	}

	// 帶參數的建構子
	public Payment(String paymentMethod, String paymentStatus, String transactionId) {
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.transactionId = transactionId;
		this.paymentDate = LocalDateTime.now();
	}

	// 完整建構子
	public Payment(Integer paymentId, String paymentMethod, String paymentStatus, String transactionId,
			LocalDateTime paymentDate) {
		this.paymentId = paymentId;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.transactionId = transactionId;
		this.paymentDate = paymentDate != null ? paymentDate : LocalDateTime.now();
	}

	// Getter 和 Setter 方法
	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	// 業務邏輯方法
	public boolean isSuccessful() {
		return "已付款".equals(paymentStatus) || "SUCCESS".equalsIgnoreCase(paymentStatus);
	}

	public boolean isPending() {
		return "待付款".equals(paymentStatus) || "PENDING".equalsIgnoreCase(paymentStatus);
	}

	public boolean isFailed() {
		return "付款失敗".equals(paymentStatus) || "FAILED".equalsIgnoreCase(paymentStatus);
	}

	public boolean isCancelled() {
		return "已取消".equals(paymentStatus) || "CANCELLED".equalsIgnoreCase(paymentStatus);
	}

	public String getStatusBadgeClass() {
		switch (paymentStatus) {
		case "已付款":
			return "badge bg-success";
		case "待付款":
			return "badge bg-warning text-dark";
		case "付款失敗":
			return "badge bg-danger";
		case "已取消":
			return "badge bg-secondary";
		default:
			return "badge bg-light text-dark";
		}
	}

	// toString 方法
	@Override
	public String toString() {
		return "Payment{" + "paymentId=" + paymentId + ", paymentMethod='" + paymentMethod + '\'' + ", paymentStatus='"
				+ paymentStatus + '\'' + ", transactionId='" + transactionId + '\'' + ", paymentDate=" + paymentDate
				+ '}';
	}

	// equals 和 hashCode 方法
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Payment payment = (Payment) o;
		return paymentId != null && paymentId.equals(payment.paymentId);
	}

	@Override
	public int hashCode() {
		return paymentId != null ? paymentId.hashCode() : 0;
	}
}
