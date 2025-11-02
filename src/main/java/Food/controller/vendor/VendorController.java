package Food.controller.vendor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Food.dto.vendor.VendorDTO;
import Food.dto.vendor.VendorDetailView;
import Food.entity.vendor.Vendor;
import Food.service.vendor.VendorService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @GetMapping("/r/{vendorId}")
    public ResponseEntity<VendorDTO> getVendor(@PathVariable Integer vendorId, HttpSession session) {
        Vendor vendor = vendorService.getVendorById(vendorId);
        if (vendor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        VendorDTO dto = new VendorDTO(
                vendor.getVendorId(),
                vendor.getVendorName(),
                vendor.getContactName(),
                vendor.getContactTel(),
                vendor.getAddress(),
                vendor.getLogoURL(),
                vendor.getVdStatus());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/self")
    public Vendor findBySelf(HttpSession session) {
        Vendor vendor = (Vendor) session.getAttribute("vendor");
        if (vendor == null) {
            throw new RuntimeException("尚未登入或 Session 過期");
        }
        return vendor;
    }

    @GetMapping("/all")
    public List<VendorDTO> findAll() {
        return vendorService.findAllDTO();
    }

    @PostMapping("/")
    public Vendor create(@RequestBody Vendor bean) {
        return vendorService.create(bean);
    }

    @PutMapping("/")
    public Vendor modify(@RequestBody Vendor bean) {
        // 直接用前端送的 vendorId
        return vendorService.modify(bean);
    }

    @DeleteMapping("/{id}")
    public boolean remove(@PathVariable Integer id) {
        return vendorService.remove(id);
    }

    // 廠商頁面用，單純單筆查詢
    @GetMapping("/{id}")
    public Vendor findById(@PathVariable Integer id) {
        return vendorService.findById(id);
    }

    // 客服表單用，字串轉型 1-xxxxx
    @GetMapping("/searchforcsservice")
    public List<Map<String, Object>> search(@RequestParam String keyword) {
        return vendorService.searchVendors(keyword).stream().map(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", v.getVendorId());
            map.put("label", v.getVendorId() + "-" + v.getVendorName());
            return map;
        }).toList();
    }

    // 廠商頁面抓資料使用 - 串Vendor、VD_Categories
    @GetMapping("/more/{vendorId}")
    public Optional<VendorDetailView> findMoreDetailsByVendorId(@PathVariable Integer vendorId) {
        return vendorService.findMoreDetailsByVendorId(vendorId);
    }

    // 廠商頁面抓資料使用 - 串Vendor、VD_Styles
    @GetMapping("/style/{vendorId}")
    public Optional<VendorDetailView> findStyleDetailsByVendorId(@PathVariable Integer vendorId) {
        return vendorService.findStyleDetailsByVendorId(vendorId);
    }
}