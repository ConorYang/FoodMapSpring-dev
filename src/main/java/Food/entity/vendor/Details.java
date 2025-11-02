package Food.entity.vendor;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "VD_Details")
public class Details {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VD_DetailID")
	private Integer vdDetailId;

	@Column(name = "VendorID", insertable = false, updatable = false)
	private Integer vendorId;

	@Column(name = "SeatsNumber", nullable = false)
	private Integer seatsNumber;

	@Column(name = "AirConditioner", nullable = false)
	private Boolean airConditioner;

	@Column(name = "Park", nullable = false)
	private Boolean park;

	@Column(name = "BabyFriendly", nullable = false)
	private Boolean babyFriendly;

	@Column(name = "PetFriendly", nullable = false)
	private Boolean petFriendly;

	@Column(name = "VeganFriendly", nullable = false)
	private Boolean veganFriendly;

	@Column(name = "HalalFriendly", nullable = false)
	private Boolean halalFriendly;

	@Column(name = "PriceMin", nullable = false)
	private Integer priceMin;

	@Column(name = "PriceMax", nullable = false)
	private Integer priceMax;

	@Column(name = "ServiceCharge", nullable = false)
	private Integer serviceCharge;

	@Column(name = "LastUpdated", nullable = false)
	private LocalDateTime lastUpdated;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VendorID")
	@JsonIgnore
	private Vendor vendor;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VD_StyleID")
	@JsonIgnoreProperties("details")
	private VD_Styles style;

	public Details() {
	}

	// --- Getter & Setter ---
	public Integer getVdDetailId() {
		return vdDetailId;
	}

	public void setVdDetailId(Integer vdDetailId) {
		this.vdDetailId = vdDetailId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getSeatsNumber() {
		return seatsNumber;
	}

	public void setSeatsNumber(Integer seatsNumber) {
		this.seatsNumber = seatsNumber;
	}

	public Boolean getAirConditioner() {
		return airConditioner;
	}

	public void setAirConditioner(Boolean airConditioner) {
		this.airConditioner = airConditioner;
	}

	public Boolean getPark() {
		return park;
	}

	public void setPark(Boolean park) {
		this.park = park;
	}

	public Boolean getBabyFriendly() {
		return babyFriendly;
	}

	public void setBabyFriendly(Boolean babyFriendly) {
		this.babyFriendly = babyFriendly;
	}

	public Boolean getPetFriendly() {
		return petFriendly;
	}

	public void setPetFriendly(Boolean petFriendly) {
		this.petFriendly = petFriendly;
	}

	public Boolean getVeganFriendly() {
		return veganFriendly;
	}

	public void setVeganFriendly(Boolean veganFriendly) {
		this.veganFriendly = veganFriendly;
	}

	public Boolean getHalalFriendly() {
		return halalFriendly;
	}

	public void setHalalFriendly(Boolean halalFriendly) {
		this.halalFriendly = halalFriendly;
	}

	public Integer getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(Integer priceMin) {
		this.priceMin = priceMin;
	}

	public Integer getPriceMax() {
		return priceMax;
	}

	public void setPriceMax(Integer priceMax) {
		this.priceMax = priceMax;
	}

	public Integer getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Integer serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public VD_Styles getStyle() {
		return style;
	}

	public void setStyle(VD_Styles style) {
		this.style = style;
	}

}