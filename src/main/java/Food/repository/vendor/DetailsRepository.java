package Food.repository.vendor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Food.entity.vendor.Details;

@Repository
public interface DetailsRepository extends JpaRepository<Details, Integer> {
	List<Details> findByVendorId(Integer vendorId);

}