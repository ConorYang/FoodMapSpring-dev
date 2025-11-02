package Food.entity.shopping;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import Food.entity.ad.AD_Plan;
import Food.entity.vendor.VProduct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "OrderDetails")
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OrderDetailID")
	private Integer orderDetailId;

	@Column(name = "OrderID", nullable = false)
	private Integer orderId;

	@Column(name = "ProductID")
	private Integer productId;

	@Column(name = "PlanID")
	private Integer planId;

	@Column(name = "Quantity", nullable = false)
	private Integer quantity;

	@Column(name = "UnitPrice", nullable = false)
	private Integer unitPrice;

	@Column(name = "ExpireDate")
	private LocalDate expireDate;

	// 關聯商品資訊
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ProductID", insertable = false, updatable = false)
	private VProduct product;

	// 關聯訂單資訊
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OrderID", insertable = false, updatable = false)
	private Order order;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PlanID", insertable = false, updatable = false)
	private AD_Plan adPlan;

	// 預設建構子
	public OrderDetail() {
	}

	// 帶參數的建構子
	public OrderDetail(Integer orderId, Integer productId, Integer quantity, Integer unitPrice, Integer planId) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.planId = planId;
	}

	// 完整建構子
	public OrderDetail(Integer orderDetailId, Integer orderId, Integer productId, Integer planId, Integer quantity,
			Integer unitPrice, LocalDate expireDate) {
		this.orderDetailId = orderDetailId;
		this.orderId = orderId;
		this.productId = productId;
		this.planId = planId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.expireDate = expireDate;
	}

	// Getter 和 Setter 方法
	public Integer getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
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

	public LocalDate getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}

	public VProduct getProduct() {
		return product;
	}

	public void setProduct(VProduct product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public AD_Plan getAdPlan() {
		return adPlan;
	}

	public void setAdPlan(AD_Plan adPlan) {
		this.adPlan = adPlan;
	}

	// 業務邏輯方法
	public Integer getSubtotal() {
		if (unitPrice != null && quantity != null) {
			return unitPrice * quantity;
		}
		return 0;
	}

	public boolean isExpired() {
		if (expireDate != null) {
			return LocalDate.now().isAfter(expireDate);
		}
		return false;
	}

	// equals 和 hashCode 方法
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		OrderDetail that = (OrderDetail) o;
		return orderDetailId != null && orderDetailId.equals(that.orderDetailId);
	}

	@Override
	public int hashCode() {
		return orderDetailId != null ? orderDetailId.hashCode() : 0;
	}
}
