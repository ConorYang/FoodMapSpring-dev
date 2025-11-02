package Food.dto.lookHistory;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LookHistoryDTO {
	private Integer lookHistoryId;
	private Integer memberId;
	private Integer vendorId;
	private String vendorName;
	private String logoUrl;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING)
	private LocalDateTime createAt;

	public LookHistoryDTO() {
	}

	public LookHistoryDTO(Integer lookHistoryId, Integer memberId, Integer vendorId, String vendorName, String logoUrl,
			LocalDateTime createAt) {
		this.lookHistoryId = lookHistoryId;
		this.memberId = memberId;
		this.vendorId = vendorId;
		this.vendorName = vendorName;
		this.logoUrl = logoUrl;
		this.createAt = createAt;

	}

	public Integer getLookHistoryId() {
		return lookHistoryId;
	}

	public void setLookHistoryId(Integer lookHistoryId) {
		this.lookHistoryId = lookHistoryId;
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

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

}
