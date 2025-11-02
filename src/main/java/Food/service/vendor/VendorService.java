
package Food.service.vendor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Food.dto.vendor.VendorDTO;
import Food.dto.vendor.VendorDetailView;
import Food.entity.member.Member;
import Food.entity.vendor.Vendor;
import Food.repository.vendor.VendorRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public Vendor getVendorByAccountId(Long accountId) {
        if (accountId == null) {
            return null;
        }
        return vendorRepository.findByUser_AccountId(accountId);
    }

    public Vendor getVendorById(Integer vendorId) {
        return vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("店家不存在: " + vendorId));
    }

    // 單一查詢
    public Vendor findById(Integer id) {
        return vendorRepository.findById(id).orElse(null);
    }

    // 全部 DTO
    public List<VendorDTO> findAllDTO() {
        return vendorRepository.findAllVendorDTO();
    }

    // 新增
    public Vendor create(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    // 修改
    public Vendor modify(Vendor vendor) {
        if (vendor != null && vendorRepository.existsById(vendor.getVendorId())) {
            return vendorRepository.save(vendor);
        }
        return null;
    }

    // 刪除
    public boolean remove(Integer id) {
        if (vendorRepository.existsById(id)) {
            vendorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 客服表單用 - 搜尋全部
    public List<Vendor> searchAllVendors() {
        return vendorRepository.findAll();
    }

    // 客服表單用 - 字串轉型
    public List<Vendor> searchVendors(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return vendorRepository.findAll();
        }
        return vendorRepository.findByVendorNameContainingIgnoreCase(keyword);
    }

    // 廠商頁面抓資料使用 - 串Vendor、VD_Categories
    public Optional<VendorDetailView> findMoreDetailsByVendorId(Integer vendorId) {
        return vendorRepository.findMoreDetailsByVendorId(vendorId);
    }

    // 廠商頁面抓資料使用 - 串Vendor、VD_Styles
    public Optional<VendorDetailView> findStyleDetailsByVendorId(Integer vendorId) {
        return vendorRepository.findStyleDetailsByVendorId(vendorId);
    }
}