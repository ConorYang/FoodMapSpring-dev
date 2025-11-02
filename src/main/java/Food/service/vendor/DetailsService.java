package Food.service.vendor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Food.entity.vendor.Details;
import Food.repository.vendor.DetailsRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetailsService {

	@Autowired
	private DetailsRepository detailsRepository;

	// 單一查詢
	public Details findByVendorId(Integer vendorId) {
		List<Details> list = detailsRepository.findByVendorId(vendorId);
		if (list.isEmpty()) {
			return null; // 或拋例外
		}
		return list.get(0); // 取第一筆
	}

	// 查詢所有
	public List<Details> findAll() {
		return detailsRepository.findAll();
	}

	public Details create(Details details) {
		return detailsRepository.save(details);
	}

	public Details modifyByVendorId(Integer vendorId, Details newDetails) {
		List<Details> list = detailsRepository.findByVendorId(vendorId);
		if (!list.isEmpty()) {
			Details existing = list.get(0);

			if (existing != null) {
				// 設定基本欄位
				existing.setSeatsNumber(
						newDetails.getSeatsNumber() != null ? newDetails.getSeatsNumber() : existing.getSeatsNumber());
				existing.setAirConditioner(
						newDetails.getAirConditioner() != null ? newDetails.getAirConditioner() : false);
				existing.setPark(newDetails.getPark() != null ? newDetails.getPark() : false);
				existing.setBabyFriendly(newDetails.getBabyFriendly() != null ? newDetails.getBabyFriendly() : false);
				existing.setPetFriendly(newDetails.getPetFriendly() != null ? newDetails.getPetFriendly() : false);
				existing.setVeganFriendly(
						newDetails.getVeganFriendly() != null ? newDetails.getVeganFriendly() : false);
				existing.setHalalFriendly(
						newDetails.getHalalFriendly() != null ? newDetails.getHalalFriendly() : false);

				// PriceMin / PriceMax / ServiceCharge / LastUpdated 不可為 null
				existing.setPriceMin(newDetails.getPriceMin() != null ? newDetails.getPriceMin() : 0);
				existing.setPriceMax(newDetails.getPriceMax() != null ? newDetails.getPriceMax() : 0);
				existing.setServiceCharge(newDetails.getServiceCharge() != null ? newDetails.getServiceCharge() : 0);
				existing.setLastUpdated(newDetails.getLastUpdated() != null ? newDetails.getLastUpdated()
						: java.time.LocalDateTime.now());

				return detailsRepository.save(existing);
			}
		}
		return null;
	}

	public boolean remove(Integer id) {
		if (detailsRepository.existsById(id)) {
			detailsRepository.deleteById(id);
			return true;
		}
		return false;
	}
}