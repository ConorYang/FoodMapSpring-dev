package Food.repository.shopping;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Food.entity.shopping.Cart;
import jakarta.transaction.Transactional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

       // 根據帳號ID查找購物車商品 (保持原有方法)
       List<Cart> findByAccountId(Long accountId);

       // **修正：使用 JOIN FETCH 確保同時載入 AdPlan 和 Product 資訊，以避免 Lazy Loading 導致的價格為零問題**
       @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.product LEFT JOIN FETCH c.adPlan WHERE c.accountId = :accountId")
       List<Cart> findByAccountIdWithDetails(@Param("accountId") Long accountId);

       // 根據帳號ID和商品ID查找購物車項目
       Optional<Cart> findByAccountIdAndProductId(Long accountId, Integer productId);

       Optional<Cart> findByAccountIdAndPlanId(Long accountId, Integer planId);

       // 檢查購物車中是否已存在該商品
       boolean existsByAccountIdAndProductId(Long accountId, Integer productId);

       // 根據帳號ID刪除所有購物車項目
       @Modifying
       @Transactional
       void deleteByAccountId(Long accountId);

       // 根據帳號ID和商品ID刪除購物車項目
       @Modifying
       @Transactional
       void deleteByAccountIdAndProductId(Long accountId, Integer productId);

       // 計算購物車商品總數量
       @Query("SELECT COALESCE(SUM(c.quantity), 0) FROM Cart c WHERE c.accountId = :accountId")
       Integer getTotalQuantityByAccountId(@Param("accountId") Long accountId);

       // 計算購物車總金額（需要關聯商品表）
       // @Query("SELECT COALESCE(SUM(c.quantity * CASE WHEN p.specialPrice IS NOT NULL
       // THEN p.specialPrice ELSE p.unitPrice END), 0) "
       // +
       // "FROM Cart c JOIN Product p ON c.productId = p.productId WHERE c.accountId =
       // :accountId")
       // Integer getTotalAmountByAccountId(@Param("accountId") Long accountId);

       // 查找購物車商品並關聯商品資訊
       // @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.product WHERE c.accountId =
       // :accountId")
       // List<Cart> findByAccountIdWithProduct(@Param("accountId") Long accountId);

       // // 查找特定用戶的購物車項目數量
       // @Query("SELECT COUNT(c) FROM Cart c WHERE c.accountId = :accountId")
       // Long countByAccountId(@Param("accountId") Long accountId);

       // // 更新購物車商品數量
       // @Modifying
       // @Transactional
       // @Query("UPDATE Cart c SET c.quantity = :quantity WHERE c.cartId = :cartId")
       // int updateQuantity(@Param("cartId") Integer cartId, @Param("quantity")
       // Integer quantity);

       // // 批量刪除購物車項目
       // @Modifying
       // @Transactional
       // @Query("DELETE FROM Cart c WHERE c.cartId IN :cartIds")
       // int deleteByCartIdIn(@Param("cartIds") List<Integer> cartIds);

       // // 查找庫存不足的購物車項目
       // @Query("SELECT c FROM Cart c JOIN Product p ON c.productId = p.productId " +
       // "WHERE c.accountId = :accountId AND p.stock < c.quantity")
       // List<Cart> findOutOfStockItems(@Param("accountId") Long accountId);
}
