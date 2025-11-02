package Food.dto.shopping;

import java.time.LocalDateTime;
import java.util.List;

import Food.entity.shopping.Order;

public class OrderDTO {
    private Long orderId;
    private String status; // success / error
    private String orderNo; // 系統訂單編號
    private Integer totalAmount; // 總金額
    private String paymentStatus;
    // === 新增三個綠界相關欄位 ===
    private String paymentType;
    private LocalDateTime paymentDate; // 付款完成時間
    private String tradeNo; // 綠界交易序號

    private LocalDateTime paidAt;
    private LocalDateTime createdAt;
    private List<OrderDetailDTO> details; // 訂單明細

    public OrderDTO() {
    }

    public OrderDTO(Order order) {
        this.orderId = order.getOrderId();
        this.status = "success";
        this.orderNo = order.getOrderNo();
        this.totalAmount = order.getTotalAmount();
        this.paymentStatus = order.getPaymentStatus();
        this.paidAt = order.getPaidAt();
        this.createdAt = order.getCreatedAt();
    }

    // === 可用於回傳成功付款的完整建構子 ===
    public OrderDTO(Long orderId, String status, String orderNo, Integer totalAmount,
            String paymentType, LocalDateTime paymentDate, String tradeNo,
            List<OrderDetailDTO> details) {
        this.orderId = orderId;
        this.status = status;
        this.orderNo = orderNo;
        this.totalAmount = totalAmount;
        this.paymentType = paymentType;
        this.paymentDate = paymentDate;
        this.tradeNo = tradeNo;
        this.details = details;
    }

    public OrderDTO(String status, String orderNo, Integer totalAmount,
            LocalDateTime paidAt, LocalDateTime createdAt,
            List<OrderDetailDTO> details) {
        this.status = status;
        this.orderNo = orderNo;
        this.totalAmount = totalAmount;
        this.paidAt = paidAt;
        this.createdAt = createdAt;
        this.details = details;
    }

    // Getter / Setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetailDTO> details) {
        this.details = details;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

}
