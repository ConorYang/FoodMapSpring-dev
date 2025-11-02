package Food.entity.lookHistory;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MemberLookHistroies")
public class LookHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer lookHistoryId;

	private Integer memberId;
	private Integer vendorId;

	@Column(name = "CreateAt")
	private LocalDateTime createAt = LocalDateTime.now();

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
}
