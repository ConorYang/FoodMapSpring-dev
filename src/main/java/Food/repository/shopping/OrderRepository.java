package Food.repository.shopping;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Food.entity.shopping.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

       // 根據帳號ID查找訂單
       List<Order> findByAccountId(Long accountId);

       // 根據帳號ID分頁查找訂單
       Page<Order> findByAccountId(Long accountId, Pageable pageable);

       Order findByOrderNo(String orderNo);

}