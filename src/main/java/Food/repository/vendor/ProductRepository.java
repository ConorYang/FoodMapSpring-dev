package Food.repository.vendor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Food.dto.vendor.ProductReviewDTO;
import Food.entity.vendor.VProduct;

@Repository
// 只有在有多個相同型別的 Bean（例如有兩個 JpaRepository
// 都操作同一種 entity時，才需要手動命名以區分
public interface ProductRepository extends JpaRepository<VProduct, Integer> {

	public List<VProduct> findByVendorId(Integer vendorId);

	public Optional<VProduct> findByProductId(Integer productId);


    // 查詢待審核商品
    @Query("SELECT new Food.dto.vendor.ProductReviewDTO(" +
            "p.productId, p.productName, p.unitPrice, p.specialPrice, p.stock, " +
            "p.endDate, p.status, p.vendorId, v.vendorName, " +
            "p.picUrl, p.reason) " +
            "FROM VProduct p LEFT JOIN p.vendor v WHERE p.status = '審核中'")
    List<ProductReviewDTO> findPendingProductsWithVendor();

    // 查詢所有商品
    @Query("SELECT new Food.dto.vendor.ProductReviewDTO(" +
            "p.productId, p.productName, p.unitPrice, p.specialPrice, p.stock, " +
            "p.endDate, p.status, p.vendorId, v.vendorName, " +
            "p.picUrl, p.reason) " +
            "FROM VProduct p LEFT JOIN p.vendor v")
    List<ProductReviewDTO> findAllProductsWithVendor();

    // 根據狀態查詢
    @Query("SELECT new Food.dto.vendor.ProductReviewDTO(" +
            "p.productId, p.productName, p.unitPrice, p.specialPrice, p.stock, " +
            "p.endDate, p.status, p.vendorId, v.vendorName, " +
            "p.picUrl, p.reason) " +
            "FROM VProduct p LEFT JOIN p.vendor v WHERE p.status = :status")
    List<ProductReviewDTO> findProductsByStatus(@Param("status") String status);

}