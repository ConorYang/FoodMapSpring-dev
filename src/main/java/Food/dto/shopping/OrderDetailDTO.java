package Food.dto.shopping;

import Food.entity.shopping.OrderDetail;

public class OrderDetailDTO {
    private Integer orderDetailId;
    private Integer orderId;
    private String productName;
    private String planName;
    private Integer quantity;
    private Integer unitPrice;
    private Integer subtotal;

    // 建構子：從 OrderDetail 實體轉換
    public OrderDetailDTO(OrderDetail orderDetail) {
        this.orderDetailId = orderDetail.getOrderDetailId();
        this.orderId = orderDetail.getOrderId();
        this.productName = orderDetail.getProduct() != null ? orderDetail.getProduct().getProductName() : null;
        this.planName = orderDetail.getAdPlan() != null ? orderDetail.getAdPlan().getPlanName() : null;
        this.quantity = orderDetail.getQuantity();
        this.unitPrice = orderDetail.getUnitPrice();
        this.subtotal = orderDetail.getSubtotal();
    }

    // Getter / Setter
    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
}
