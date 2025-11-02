package Food.service.vendor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Food.entity.vendor.OpeningHours;
import Food.entity.vendor.Vendor;
import Food.repository.vendor.OpeningHoursRepository;
import Food.repository.vendor.VendorRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OpeningHoursService {

	@Autowired
	private OpeningHoursRepository openingHoursRepository;
	@Autowired
	private VendorRepository vendorRepository;

	// 單一查詢vendorId
	public List<OpeningHours> getByVendorId(Integer vendorId) {
		return openingHoursRepository.findByVendor_VendorId(vendorId);
	}

//	// 單一查詢O
//	public OpeningHours findById(Integer id) {
//		return openingHoursRepository.findById(id).orElse(null);
//	}

	// 查詢所有
	public List<OpeningHours> findAll() {
		return openingHoursRepository.findAll();
	}

	public OpeningHours create(OpeningHours openingHours) {
		if (openingHours == null || openingHours.getVendor() == null || openingHours.getVendor().getVendorId() == null) {
			throw new IllegalArgumentException("Vendor 或 vendorId 不能為 null");
		}

//		         從資料庫抓 Vendor 實體
		Vendor vendor = vendorRepository.findById(openingHours.getVendor().getVendorId())
				.orElseThrow(() -> new IllegalArgumentException("vendorId 不存在"));

		openingHours.setVendor(vendor); // 設定受管理的實體
		return openingHoursRepository.save(openingHours);
	}

	@Transactional
	public List<OpeningHours> upsertOpeningHours(Integer vendorId, List<OpeningHours> list) {
	    List<OpeningHours> result = new ArrayList<>();

	    // 取得受管理的 Vendor 實體
	    Vendor vendor = vendorRepository.findById(vendorId)
	            .orElseThrow(() -> new IllegalArgumentException("vendorId 不存在"));

	    for (OpeningHours oh : list) {
	        // 查詢 vendorId + dayOfWeek
	        OpeningHours exist = openingHoursRepository
	                .findByVendor_VendorIdAndDayOfWeek(vendorId, oh.getDayOfWeek());

	        if (exist != null) {
	            // 更新
	            exist.setOpeningTime(oh.getOpeningTime());
	            exist.setClosingTime(oh.getClosingTime());
	            result.add(openingHoursRepository.save(exist));
	        } else {
	            // 新增
	            oh.setVendor(vendor);
	            result.add(openingHoursRepository.save(oh));
	        }
	    }

	    return result;
	}



	public boolean remove(Integer id) {
		if (openingHoursRepository.existsById(id)) {
			openingHoursRepository.deleteById(id);
			return true;
		}
		return false;
	}
}