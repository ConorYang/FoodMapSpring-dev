package Food.entity.shopping;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "AD_Data", schema = "dbo") // 映射到 dbo.AD_Data
public class AdData {

	@Id // 主鍵標記
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 對應 SQL Server 的 IDENTITY(1,1)
	@Column(name = "ADID") // 映射到 ADID 欄位
	private Integer adId;

	@Column(name = "PlanID", nullable = false) // 映射到 PlanID 欄位，且不可為 null
	private Integer planId;

	@Column(name = "VendorID", nullable = false) // 映射到 VendorID 欄位，且不可為 null
	private Integer vendorId;

	@Column(name = "CampaignName", nullable = false, length = 100) // 映射到 CampaignName，長度為 100
	private String campaignName;

	@Column(name = "StartDate", nullable = false) // 映射到 StartDate 欄位
	private LocalDateTime startDate; // 使用 java.time.LocalDateTime 處理日期時間

	@Column(name = "EndDate", nullable = false) // 映射到 EndDate 欄位
	private LocalDateTime endDate;

	@Column(name = "AD_Status", nullable = false, length = 10) // 映射到 AD_Status 欄位，長度為 10
	private String adStatus;

	// nvarchar(max) 在 JPA 中通常用 String 或 @Lob 標記，若長度非常長則用 Lob，但此處先假設是較長的 String
	@Column(name = "PIC_URL", columnDefinition = "nvarchar(max)") // 映射到 PIC_URL 欄位，可為 null
	private String picUrl;

	// --- Constructors ---
	public AdData() {
	}

	// 您可以根據需要添加帶參數的構造函數 (例如不包含 ADID)

	// --- Getter and Setter Methods ---

	public Integer getAdId() {
		return adId;
	}

	public void setAdId(Integer adId) {
		this.adId = adId;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public String getAdStatus() {
		return adStatus;
	}

	public void setAdStatus(String adStatus) {
		this.adStatus = adStatus;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}
