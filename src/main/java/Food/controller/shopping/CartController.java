package Food.controller.shopping;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Food.dto.shopping.CartDTO;
import Food.entity.account.User;
import Food.entity.shopping.Cart;
import Food.service.shopping.CartService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    private Long getAccountId(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return (Long) user.getAccountId();
        }
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<CartDTO> addToCart(@RequestBody CartDTO request, HttpSession session) {

        Long accountId = getAccountId(session);

        System.out.println("Session accountId: " + accountId);
        System.out.println("Request body: productId=" + request.getProductId() + ", adId=" + request.getPlanId()
                + ", quantity=" + request.getQuantity()); // 註解：記錄請求體內容，確認 JSON 解析正確

        if (accountId == null) {
            System.out.println("accountId is null, returning 400 Bad Request");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            Cart cart = cartService.addToCart(accountId, request.getProductId(), request.getPlanId(),
                    request.getQuantity());
            System.out.println("addToCart success, returning Cart: " + cart); // 註解：記錄成功呼叫 Service 後的結果
            CartDTO dto = convertToDto(cart);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            System.out.println("Error in addToCart: " + e.getMessage()); // 註解：記錄 Service 層異常（e.g., 產品不存在）
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CartDTO>> getCart(HttpSession session) {

        Long accountId = getAccountId(session);
        // 獲取 Enumeration
        java.util.Enumeration<String> names = session.getAttributeNames();

        // 將 Enumeration 轉換為 Stream，然後收集到 List 中
        java.util.List<String> attributeNames = java.util.Collections.list(names);

        // 列印 List 的內容
        System.out.println("All session attributes: " + attributeNames);
        System.out.println("Session accountId: " + accountId); // 註解：記錄 session 中的 accountId 值，若 null 則會返回 400

        if (accountId == null) {
            System.out.println("accountId is null, returning 400 Bad Request"); // 註解：記錄返回 400 的原因，這可能是您的問題點
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            List<Cart> items = cartService.getCartItems(accountId);
            if (items == null || items.isEmpty()) {
                System.out.println("getCart success, returning 0 items.");
                return ResponseEntity.ok(Collections.emptyList());
            }

            // 4. 【修正 2：將 List<Cart> 轉換為 List<CartDTO>】
            // 使用 Stream API 進行轉換，並強制載入必要的關聯資料
            List<CartDTO> responsedto = items.stream()
                    .map(this::convertToDto)
                    .toList(); // Java 16+ 的寫法

            System.out.println("getCart success, returning items count: " + responsedto.size());
            return ResponseEntity.ok(responsedto);
        } catch (Exception e) {
            System.out.println("Error in getCart: " + e.getMessage()); // 註解：記錄 Service 層異常（e.g., 資料庫問題）
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<Cart> updateQuantity(@PathVariable Integer cartId,
            @RequestBody Map<String, Integer> request) { // 註解：使用 Map 接收 JSON body
        System.out.println("=== Entering updateQuantity method ==="); // 註解：記錄進入 PUT 方法
        Integer quantity = request.get("quantity"); // 註解：從 JSON 提取 quantity
        System.out.println("Updating cartId: " + cartId + ", new quantity: " + quantity); // 註解：記錄參數值

        // 註解：手動驗證 quantity 是否有效
        if (quantity == null || quantity <= 0) {
            System.out.println("Invalid quantity: " + quantity + ", returning 400 Bad Request");
            return ResponseEntity.badRequest().build();
        }

        try {
            Cart updated = cartService.updateQuantity(cartId, quantity); // 註解：傳遞 quantity 給 Service
            System.out.println("updateQuantity success, returning: " + updated); // 註解：記錄更新結果
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            System.out.println("Error in updateQuantity: " + e.getMessage()); // 註解：記錄異常
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Integer cartId) {
        System.out.println("=== Entering removeFromCart method ==="); // 註解：記錄進入 DELETE 方法
        System.out.println("Removing cartId: " + cartId); // 註解：記錄參數值

        try {
            cartService.removeFromCart(cartId);
            System.out.println("removeFromCart success"); // 註解：記錄成功刪除
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Error in removeFromCart: " + e.getMessage()); // 註解：記錄異常
            return ResponseEntity.badRequest().build();
        }
    }

    private CartDTO convertToDto(Cart cart) {
        CartDTO dto = new CartDTO();

        // 1. 設定基本 ID 和數量
        dto.setCartId(cart.getCartId());
        dto.setProductId(cart.getProduct() != null ? cart.getProduct().getProductId() : null); // 從 Product 實體取得 ID
        dto.setPlanId(cart.getAdPlan() != null ? cart.getAdPlan().getPlanId() : null); // 從 AdData 實體取得 ID
        dto.setQuantity(cart.getQuantity());

        // 2. 設定 Product/AdData 詳細資訊 (從已載入的實體中提取)
        if (cart.getProduct() != null) {
            // 從 Product 實體中提取詳細資訊
            dto.setProductName(cart.getProduct().getProductName());
            dto.setUnitPrice(cart.getProduct().getUnitPrice());
            dto.setSpecialPrice(cart.getProduct().getSpecialPrice());
        } else if (cart.getAdPlan() != null) {
            // 從 AdData 實體中提取詳細資訊
            dto.setPlanName(cart.getAdPlan().getPlanName()); // 假設 AdData 有 getName()
            dto.setPlanPrice(cart.getAdPlan().getPlanPrice());
        }

        return dto;
    }
}