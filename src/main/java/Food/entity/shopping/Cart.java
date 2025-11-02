package Food.entity.shopping;

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
@Table(name = "Carts")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CartID")
	private Integer cartId;

	@Column(name = "AccountID", nullable = false)
	private Long accountId;

	@Column(name = "ProductID", nullable = true)
	private Integer productId;

	@Column(name = "PlanID", nullable = true)
	private Integer planId;

	@Column(name = "Quantity", nullable = false)
	private Integer quantity;

	// 關聯商品資訊 (懶加載)
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ProductID", insertable = false, updatable = false)
	private VProduct product;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PlanID", insertable = false, updatable = false)
	private AD_Plan adPlan;

	// 預設建構子
	public Cart() {
	}

	public Cart(Long accountId, Integer productId, Integer planId, Integer quantity) {
		this.accountId = accountId;
		this.productId = productId;
		this.planId = planId;
		this.quantity = quantity;
	}

	// Getter 和 Setter 方法
	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public VProduct getProduct() {
		return product;
	}

	public void setProduct(VProduct product) {
		this.product = product;
	}

	public AD_Plan getAdPlan() {
		return adPlan;
	}

	public void setAdPlan(AD_Plan adPlan) {
		this.adPlan = adPlan;
	}

	// toString 方法
	@Override
	public String toString() {
		return "Cart{" + "cartId=" + cartId + ", accountId=" + accountId + ", productId=" + productId + ", quantity="
				+ quantity + '}';
	}

}
