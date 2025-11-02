package Food.service.shopping;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Food.entity.ad.AD_Plan;
import Food.entity.shopping.Cart;
import Food.entity.vendor.VProduct;
import Food.repository.ad.AD_PlanRepository;
import Food.repository.shopping.CartRepository;
import Food.repository.vendor.ProductRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private AD_PlanRepository adPlanRepository;

	// 添加產品到購物車
	public Cart addToCart(Long accountId, Integer productId, Integer planId, Integer quantity) {
		// 註解：檢查產品或廣告是否存在，並處理庫存
		VProduct product = null;
		AD_Plan ad = null;
		if (productId != null) {
			product = productRepository.findByProductId(productId)
					.orElseThrow(() -> new RuntimeException("Product not found"));
			if (!product.hasEnoughStock(quantity)) {
				throw new RuntimeException("Insufficient stock"); // 註解：庫存不足檢查
			}
		} else if (planId != null) {
			ad = adPlanRepository.findById(planId).orElseThrow(() -> new RuntimeException("Ad not found"));
			// 註解：廣告相關邏輯（如檢查有效期）

		}

		Optional<Cart> existingCartItem = Optional.empty();

		if (productId != null) {
			// 根據帳號和產品 ID 查找
			existingCartItem = cartRepository.findByAccountIdAndProductId(accountId, productId);
		} else if (planId != null) {
			// 根據帳號和廣告 ID 查找
			existingCartItem = cartRepository.findByAccountIdAndPlanId(accountId, planId);
		}

		if (existingCartItem.isPresent()) {
			// --- 3. 【更新邏輯】：如果找到現有項目，則更新數量 ---
			Cart cart = existingCartItem.get();
			int newQuantity = cart.getQuantity() + quantity; // 舊數量 + 新增數量

			// 註解：如果需要，在這裡執行更新後的總庫存檢查 (只針對 Product)
			if (productId != null && !product.hasEnoughStock(newQuantity)) {
				throw new RuntimeException("Insufficient stock for total quantity");
			}

			cart.setQuantity(newQuantity);
			return cartRepository.save(cart); // 儲存更新後的數量

		} else {
			// --- 4. 【新增邏輯】：如果沒有找到，則建立新項目 (保持原樣) ---
			Cart cart = new Cart();
			cart.setAccountId(accountId);
			cart.setProductId(productId);
			cart.setPlanId(planId);
			cart.setProduct(product);
			cart.setAdPlan(ad);
			cart.setQuantity(quantity);
			return cartRepository.save(cart); // 儲存新項目
		}
	}

	public List<Cart> getCartItems(Long accountId) {
		return cartRepository.findByAccountId(accountId);
	}

	// 更新數量
	public Cart updateQuantity(Integer cartId, Integer quantity) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart item not found"));
		cart.setQuantity(quantity);
		return cartRepository.save(cart); // 註解：更新並儲存
	}

	// 刪除項目
	public void removeFromCart(Integer cartId) {
		cartRepository.deleteById(cartId); // 註解：直接刪除
	}
}
