package Food.entity.vendor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Products")
public class VProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProductID")
	private Integer productId;

	@Column(name = "ProductName", nullable = false, length = 100)
	private String productName;

	@Column(name = "VendorID", nullable = false)
	private Integer vendorId;

	@ManyToOne
	@JoinColumn(name = "VendorID", referencedColumnName = "VendorID", insertable = false, updatable = false)
	@JsonIgnore
	// Properties({ "details", "openingHours" })
	private Vendor vendor;

	@Column(name = "UnitPrice", nullable = false)
	private Integer unitPrice;

	@Column(name = "SpecialPrice")
	private Integer specialPrice;

	@Column(name = "SpecialDiscount")
	private Float specialDiscount;

	@Column(name = "EndDate", nullable = false)
	private LocalDate endDate;

	@Column(name = "Stock", nullable = false)
	private Integer stock;

	@Column(name = "Pic_URL")
	private String picUrl;

	@Column(name = "Status", nullable = false, length = 20)
	private String status;

	@Column(name = "Reason", length = 255)
	private String reason;

	public VProduct() {
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Vendor getVendor() { // 新增 getter
		return vendor;
	}

	public void setVendor(Vendor vendor) { // 新增 setter
		this.vendor = vendor;
	}

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getSpecialPrice() {
		return specialPrice;
	}

	public void setSpecialPrice(Integer specialPrice) {
		this.specialPrice = specialPrice;
	}

	public Float getSpecialDiscount() {
		return specialDiscount;
	}

	public void setSpecialDiscount(Float specialDiscount) {
		this.specialDiscount = specialDiscount;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	// 業務邏輯方法
	public Integer getCurrentPrice() {
		return specialPrice != null ? specialPrice : unitPrice;
	}

	public boolean hasSpecialPrice() {
		return specialPrice != null && specialPrice < unitPrice;
	}

	public boolean isInStock() {
		return stock != null && stock > 0;
	}

	public boolean hasEnoughStock(Integer quantity) {
		return stock != null && stock >= quantity;
	}

	// toString 方法
	@Override
	public String toString() {
		return "Product{" +
				"productId=" + productId +
				", productName='" + productName + '\'' +
				", vendorId=" + vendorId +
				", unitPrice=" + unitPrice +
				", specialPrice=" + specialPrice +
				", specialDiscount=" + specialDiscount +
				", endDate=" + endDate +
				", stock=" + stock +
				'}';
	}

}
