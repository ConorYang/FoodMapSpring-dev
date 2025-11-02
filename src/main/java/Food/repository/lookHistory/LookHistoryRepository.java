package Food.repository.lookHistory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Food.entity.lookHistory.LookHistory;

public interface LookHistoryRepository extends JpaRepository<LookHistory, Integer> {
    List<LookHistory> findTop16ByMemberIdOrderByCreateAtDesc(Integer memberId);

    List<LookHistory> findByMemberIdAndVendorIdOrderByCreateAtDesc(Integer memberId, Integer vendorId);

    // 計算某店家瀏覽次數
    @Query("SELECT COUNT(l) FROM LookHistory l WHERE l.vendorId = :vendorId")
    int countByVendorId(@Param("vendorId") int vendorId);

    // 依日期統計瀏覽數 (日報表)
    @Query("SELECT CAST(l.createAt AS date), COUNT(l) " +
            "FROM LookHistory l " +
            "WHERE l.vendorId = :vendorId " +
            "GROUP BY CAST(l.createAt AS date) " +
            "ORDER BY CAST(l.createAt AS date)")
    List<Object[]> countByVendorIdGroupByDate(@Param("vendorId") int vendorId);

}
