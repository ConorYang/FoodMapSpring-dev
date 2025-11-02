package Food.repository.vendor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Food.entity.vendor.OpeningHours;

@Repository
public interface OpeningHoursRepository extends JpaRepository<OpeningHours, Integer> {

	OpeningHours findByVendor_VendorIdAndDayOfWeek(Integer vendorId, Integer dayOfWeek);

	List<OpeningHours> findByVendor_VendorId(Integer vendorId);
}