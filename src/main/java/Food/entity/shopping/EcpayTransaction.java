package Food.entity.shopping;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ecpay_Transaction")
public class EcpayTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ecpay_id")
	private Long ecpayId;

	@Column(name = "merchant_id", nullable = false, length = 20)
	private String merchantId;

	@Column(name = "merchant_trade_no", nullable = false, length = 50)
	private String merchantTradeNo;

	@Column(name = "trade_no", nullable = false, length = 50)
	private String tradeNo;

	@Column(name = "trade_date", nullable = false)
	private LocalDateTime tradeDate;

	@Column(name = "payment_date")
	private LocalDateTime paymentDate;

	@Column(name = "payment_type", length = 50)
	private String paymentType;

	@Column(name = "payment_type_charge_fee", precision = 10, scale = 2)
	private BigDecimal paymentTypeChargeFee;

	@Column(name = "rtn_code", nullable = false)
	private Integer rtnCode;

	@Column(name = "rtn_msg", length = 255)
	private String rtnMsg;

	@Column(name = "trade_amt", nullable = false, precision = 10, scale = 2)
	private BigDecimal tradeAmt;

	@Column(name = "store_id", length = 50)
	private String storeId;

	@Column(name = "simulate_paid")
	private Boolean simulatePaid;

	@Column(name = "custom_field1", length = 255)
	private String customField1;

	@Column(name = "custom_field2", length = 255)
	private String customField2;

	@Column(name = "custom_field3", length = 255)
	private String customField3;

	@Column(name = "custom_field4", length = 255)
	private String customField4;

	@Column(name = "check_mac_value", nullable = false, length = 100)
	private String checkMacValue;

	@Column(name = "order_no", length = 50)
	private String orderNo;

	@Column(name = "raw_data", columnDefinition = "nvarchar(max)")
	private String rawData;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}

	// Getters and Setters
	public Long getEcpayId() {
		return ecpayId;
	}

	public void setEcpayId(Long ecpayId) {
		this.ecpayId = ecpayId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantTradeNo() {
		return merchantTradeNo;
	}

	public void setMerchantTradeNo(String merchantTradeNo) {
		this.merchantTradeNo = merchantTradeNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public LocalDateTime getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(LocalDateTime tradeDate) {
		this.tradeDate = tradeDate;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getPaymentTypeChargeFee() {
		return paymentTypeChargeFee;
	}

	public void setPaymentTypeChargeFee(BigDecimal fee) {
		this.paymentTypeChargeFee = fee;
	}

	public Integer getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(Integer rtnCode) {
		this.rtnCode = rtnCode;
	}

	public String getRtnMsg() {
		return rtnMsg;
	}

	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}

	public BigDecimal getTradeAmt() {
		return tradeAmt;
	}

	public void setTradeAmt(BigDecimal tradeAmt) {
		this.tradeAmt = tradeAmt;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public Boolean getSimulatePaid() {
		return simulatePaid;
	}

	public void setSimulatePaid(Boolean simulatePaid) {
		this.simulatePaid = simulatePaid;
	}

	public String getCustomField1() {
		return customField1;
	}

	public void setCustomField1(String customField1) {
		this.customField1 = customField1;
	}

	public String getCustomField2() {
		return customField2;
	}

	public void setCustomField2(String customField2) {
		this.customField2 = customField2;
	}

	public String getCustomField3() {
		return customField3;
	}

	public void setCustomField3(String customField3) {
		this.customField3 = customField3;
	}

	public String getCustomField4() {
		return customField4;
	}

	public void setCustomField4(String customField4) {
		this.customField4 = customField4;
	}

	public String getCheckMacValue() {
		return checkMacValue;
	}

	public void setCheckMacValue(String checkMacValue) {
		this.checkMacValue = checkMacValue;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
