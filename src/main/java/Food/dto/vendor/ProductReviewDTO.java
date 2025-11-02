package Food.dto.vendor;

import java.time.LocalDate;

public class ProductReviewDTO {
    private Integer productId;
    private String productName;
    private Integer unitPrice;
    private Integer specialPrice;
    private Integer stock;
    private LocalDate endDate;
    private String status;
    private Integer vendorId;      // 🆕 新增
    private String vendorName;
    private String picUrl;          // 🆕 新增（給前端顯示圖片用）
    private String reason;          // 🆕 新增（拒絕原因）

    // 無參數 constructor
    public ProductReviewDTO() {}

    // ✅ JPQL 需要的 constructor（新增 vendorId, picUrl, reason）
    public ProductReviewDTO(Integer productId, String productName, Integer unitPrice,
            Integer specialPrice, Integer stock, java.time.LocalDate endDate,
            String status, Integer vendorId, String vendorName, 
            String picUrl, String reason) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.specialPrice = specialPrice;
        this.stock = stock;
        this.endDate = endDate;
        this.status = status;
        this.vendorId = vendorId;    // 🆕
        this.vendorName = vendorName;
        this.picUrl = picUrl;         // 🆕
        this.reason = reason;         // 🆕
    }

    // Getter & Setter
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}