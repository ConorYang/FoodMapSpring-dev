package Food.repository.search;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import Food.entity.vendor.OpeningHours;

public interface OpeningHoursRepository1 extends JpaRepository<OpeningHours, Integer> {

  // 查某店家特定星期的營業時間 (正確屬性路徑：vendor.vendorId)
  List<OpeningHours> findByVendor_VendorIdAndDayOfWeek(Integer vendorId, Integer dayOfWeek);

  // 查某天有營業的所有店家（考慮跨日營業，例如 22:00–05:00）
  @Query("""
      SELECT o FROM OpeningHours o
      WHERE o.dayOfWeek = :day
        AND (
            (o.openingTime < o.closingTime AND :time BETWEEN o.openingTime AND o.closingTime)
            OR
            (o.openingTime > o.closingTime AND (:time >= o.openingTime OR :time <= o.closingTime))
        )
      """)
  List<OpeningHours> findOpenVendors(@Param("day") Integer day, @Param("time") LocalTime time);
}
