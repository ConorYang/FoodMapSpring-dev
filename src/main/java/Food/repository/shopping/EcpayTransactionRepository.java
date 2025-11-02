package Food.repository.shopping;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Food.entity.shopping.EcpayTransaction;

@Repository
public interface EcpayTransactionRepository extends JpaRepository<EcpayTransaction, Long> {
    
    Optional<EcpayTransaction> findByMerchantTradeNo(String merchantTradeNo);
    
    Optional<EcpayTransaction> findByTradeNo(String tradeNo);
    
    Optional<EcpayTransaction> findByOrderNo(String orderNo);
    
    List<EcpayTransaction> findByMerchantId(String merchantId);
    
    boolean existsByMerchantTradeNo(String merchantTradeNo);
}
