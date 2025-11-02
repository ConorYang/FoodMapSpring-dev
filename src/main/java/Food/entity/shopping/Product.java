// package Food.entity.shopping;

// import java.time.LocalDate;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;

// @Entity
// @Table(name = "Products")
// public class Product {

// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// @Column(name = "ProductID")
// private Integer productId;

// @Column(name = "ProductName", nullable = false, length = 100)
// private String productName;

// @Column(name = "VendorID", nullable = false)
// private Integer vendorId;

// @Column(name = "UnitPrice", nullable = false)
// private Integer unitPrice;

// @Column(name = "SpecialPrice")
// private Integer specialPrice;

// @Column(name = "SpecialDiscount")
// private Float specialDiscount;

// @Column(name = "EndDate", nullable = false)
// private LocalDate endDate;

// @Column(name = "Stock", nullable = false)
// private Integer stock;

// @Column(name = "Pic_URL", nullable = true, length = 255)
// private String picURL;

// // 預設建構子
// public Product() {
// }

// // 帶參數的建構子
// public Product(String productName, Integer vendorId, Integer unitPrice,
// Integer stock) {
// this.productName = productName;
// this.vendorId = vendorId;
// this.unitPrice = unitPrice;
// this.stock = stock;
// this.endDate = LocalDate.of(2099, 12, 31);
// }

// // 完整建構子
// public Product(Integer productId, String productName, Integer vendorId,
// Integer unitPrice,
// Integer specialPrice, Float specialDiscount, LocalDate endDate, Integer
// stock, String picURL) {
// this.productId = productId;
// this.productName = productName;
// this.vendorId = vendorId;
// this.unitPrice = unitPrice;
// this.specialPrice = specialPrice;
// this.specialDiscount = specialDiscount;
// this.endDate = endDate;
// this.stock = stock;
// }

// // Getter 和 Setter 方法
// public Integer getProductId() {
// return productId;
// }

// public void setProductId(Integer productId) {
// this.productId = productId;
// }

// public String getProductName() {
// return productName;
// }

// public void setProductName(String productName) {
// this.productName = productName;
// }

// public Integer getVendorId() {
// return vendorId;
// }

// public void setVendorId(Integer vendorId) {
// this.vendorId = vendorId;
// }

// public Integer getUnitPrice() {
// return unitPrice;
// }

// public void setUnitPrice(Integer unitPrice) {
// this.unitPrice = unitPrice;
// }

// public Integer getSpecialPrice() {
// return specialPrice;
// }

// public void setSpecialPrice(Integer specialPrice) {
// this.specialPrice = specialPrice;
// }

// public Float getSpecialDiscount() {
// return specialDiscount;
// }

// public void setSpecialDiscount(Float specialDiscount) {
// this.specialDiscount = specialDiscount;
// }

// public LocalDate getEndDate() {
// return endDate;
// }

// public void setEndDate(LocalDate endDate) {
// this.endDate = endDate;
// }

// public Integer getStock() {
// return stock;
// }

// public void setStock(Integer stock) {
// this.stock = stock;
// }

// public String getPicURL() {
// return picURL;
// }

// public void setPicURL(String picURL) {
// this.picURL = picURL;
// }

// // 業務邏輯方法
// public Integer getCurrentPrice() {
// return specialPrice != null ? specialPrice : unitPrice;
// }

// public boolean hasSpecialPrice() {
// return specialPrice != null && specialPrice < unitPrice;
// }

// public boolean isInStock() {
// return stock != null && stock > 0;
// }

// public boolean hasEnoughStock(Integer quantity) {
// return stock != null && stock >= quantity;
// }

// // toString 方法
// @Override
// public String toString() {
// return "Product{" +
// "productId=" + productId +
// ", productName='" + productName + '\'' +
// ", vendorId=" + vendorId +
// ", unitPrice=" + unitPrice +
// ", specialPrice=" + specialPrice +
// ", specialDiscount=" + specialDiscount +
// ", endDate=" + endDate +
// ", stock=" + stock +
// '}';
// }

// // equals 和 hashCode 方法
// @Override
// public boolean equals(Object o) {
// if (this == o)
// return true;
// if (o == null || getClass() != o.getClass())
// return false;
// Product product = (Product) o;
// return productId != null && productId.equals(product.productId);
// }

// @Override
// public int hashCode() {
// return productId != null ? productId.hashCode() : 0;
// }
// }