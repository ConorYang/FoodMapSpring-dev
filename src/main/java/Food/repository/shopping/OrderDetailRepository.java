package Food.repository.shopping;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Food.entity.shopping.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

       // 根據訂單ID查找訂單明細
       List<OrderDetail> findByOrderId(Integer orderId);

       // 根據商品ID查找訂單明細
       List<OrderDetail> findByProductId(Integer productId);

       @Query("SELECT od FROM OrderDetail od LEFT JOIN FETCH od.product WHERE od.orderId = :orderId")
       List<OrderDetail> findByOrderIdWithProduct(Long orderId);

       // 計算店家總營收
       @Query("SELECT COALESCE(SUM(od.unitPrice * od.quantity), 0) " +
              "FROM OrderDetail od " +
              "WHERE od.product.vendorId = :vendorId")
       Integer sumRevenueByVendorId(@Param("vendorId") Integer vendorId);
}
