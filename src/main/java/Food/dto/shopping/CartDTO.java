package Food.dto.shopping;

public class CartDTO {
	private Integer cartId;
	private Integer productId;
	private Integer planId;
	private Integer quantity;
	private String productName; // 註解：從 Product 取出名稱
	private String planName; // 註解：從 AdPlan 取出廣告名稱
	private Integer unitPrice;
	private Integer specialPrice;
	private Integer planPrice; // 註解：從 AdPlan 取出價格

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
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

	public Integer getSpecialPrice() {
		return specialPrice;
	}

	public void setSpecialPrice(Integer specialPrice) {
		this.specialPrice = specialPrice;
	}

	public Integer getPlanPrice() {
		return planPrice;
	}

	public void setPlanPrice(Integer planPrice) {
		this.planPrice = planPrice;
	}
}
