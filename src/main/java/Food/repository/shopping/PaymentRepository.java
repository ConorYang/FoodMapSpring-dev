package Food.repository.shopping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Food.entity.shopping.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    
    // 根據交易ID查找付款記錄
    Optional<Payment> findByTransactionId(String transactionId);
    
    // 根據付款狀態查找記錄
    List<Payment> findByPaymentStatus(String paymentStatus);
    
    // 根據付款方式查找記錄
    List<Payment> findByPaymentMethod(String paymentMethod);
    
    // 根據付款方式和狀態查找記錄
    List<Payment> findByPaymentMethodAndPaymentStatus(String paymentMethod, String paymentStatus);
    
    // 查找指定日期範圍內的付款記錄
    List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // 分頁查找付款記錄
    Page<Payment> findByPaymentStatus(String paymentStatus, Pageable pageable);
    
    // 查找成功的付款記錄
    @Query("SELECT p FROM Payment p WHERE p.paymentStatus = '已付款' OR p.paymentStatus = 'SUCCESS'")
    List<Payment> findSuccessfulPayments();
    
    // 查找失敗的付款記錄
    @Query("SELECT p FROM Payment p WHERE p.paymentStatus = '付款失敗' OR p.paymentStatus = 'FAILED'")
    List<Payment> findFailedPayments();
    
    // 查找待處理的付款記錄
    @Query("SELECT p FROM Payment p WHERE p.paymentStatus = '待付款' OR p.paymentStatus = 'PENDING'")
    List<Payment> findPendingPayments();
    
    // 查找已取消的付款記錄
    @Query("SELECT p FROM Payment p WHERE p.paymentStatus = '已取消' OR p.paymentStatus = 'CANCELLED'")
    List<Payment> findCancelledPayments();
    
    // 統計各付款狀態的數量
    @Query("SELECT p.paymentStatus, COUNT(p) FROM Payment p GROUP BY p.paymentStatus")
    List<Object[]> countByPaymentStatus();
    
    // 統計各付款方式的數量
    @Query("SELECT p.paymentMethod, COUNT(p) FROM Payment p GROUP BY p.paymentMethod")
    List<Object[]> countByPaymentMethod();
    
    // 查找最近的付款記錄
    List<Payment> findTop10ByOrderByPaymentDateDesc();
    
    // 根據狀態查找最近的付款記錄
    List<Payment> findTop10ByPaymentStatusOrderByPaymentDateDesc(String paymentStatus);
    
    // 統計成功付款總數
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.paymentStatus = '已付款' OR p.paymentStatus = 'SUCCESS'")
    Long countSuccessfulPayments();
    
    // 統計失敗付款總數
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.paymentStatus = '付款失敗' OR p.paymentStatus = 'FAILED'")
    Long countFailedPayments();
    
    // 統計指定日期範圍內的付款數量
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    Long countByDateRange(@Param("startDate") LocalDateTime startDate, 
                         @Param("endDate") LocalDateTime endDate);
    
    // 查找綠界科技的付款記錄
    List<Payment> findByPaymentMethodContaining(String keyword);
    
    // 檢查交易ID是否已存在
    boolean existsByTransactionId(String transactionId);
    
    // 查找指定時間之前的待付款記錄（用於清理過期訂單）
    @Query("SELECT p FROM Payment p WHERE p.paymentStatus = '待付款' AND p.paymentDate < :expireTime")
    List<Payment> findExpiredPendingPayments(@Param("expireTime") LocalDateTime expireTime);
    
    // 批量更新付款狀態
    @Query("UPDATE Payment p SET p.paymentStatus = :newStatus WHERE p.paymentId IN :paymentIds")
    int updatePaymentStatus(@Param("paymentIds") List<Integer> paymentIds, 
                           @Param("newStatus") String newStatus);
}