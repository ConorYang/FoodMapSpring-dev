package Food.repository.vendor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Food.dto.vendor.VendorDTO;
import Food.dto.vendor.VendorDetailView;
import Food.entity.vendor.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {

        @Query("SELECT new Food.dto.vendor.VendorDTO(" +
                        "v.vendorId, v.vendorName, v.taxID, v.ownerName, v.contactName, " +
                        "v.contactTitle, v.contactTel, v.contactEmail, v.address, v.mapApi, " +
                        "v.vdCategoryID, v.logoURL, v.vdStatus, " +
                        "CASE WHEN v.user IS NOT NULL THEN v.user.accountId ELSE NULL END) " +
                        "FROM Vendor v")
        List<VendorDTO> findAllVendorDTO();

        // 客服功能用
        // 模糊搜尋 vendorName 包含 keyword，不分大小寫
        List<Vendor> findByVendorNameContainingIgnoreCase(String keyword);

        // 廠商頁面抓資料使用 - 串Vendor、VD_Categories
        @Query(value = """
                        select v.VendorID, v.VendorName, v.VD_CategoryID, vc.CategoryName, vc.Description
                        from Vendors v
                        left join VD_Categories vc on v.VD_CategoryID = vc.VD_CategoryID
                        where v.VendorID = :vendorId
                        """, nativeQuery = true)
        Optional<VendorDetailView> findMoreDetailsByVendorId(@Param("vendorId") Integer vendorId);

        // 廠商頁面抓資料使用 - 串Vendor、VD_Styles
        @Query(value = """
                        select d.VD_DetailID,d.VendorID,d.VD_StyleID,s.StyleName from VD_Details d
                        left join VD_Styles s on d.VD_StyleID = s.VD_StyleID
                        where d.VendorID = :vendorId
                        """, nativeQuery = true)
        Optional<VendorDetailView> findStyleDetailsByVendorId(@Param("vendorId") Integer vendorId);

        Vendor findByUser_AccountId(Long accountId);

}
