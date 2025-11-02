package Food.controller.vendor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Food.dto.vendor.OpeningHoursDTO;
import Food.entity.vendor.OpeningHours;
import Food.entity.vendor.Vendor;
import Food.service.vendor.OpeningHoursService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/vendor/openingHours")
public class OpeningHoursController {

    @Autowired
    private OpeningHoursService openingHoursService;

    // ------------------------------------------------------------
    // GET: 根據 VendorId 取得開放時間 (DTO)
    @GetMapping("/{vendorId}")
    public List<OpeningHoursDTO> getByVendorId(@PathVariable Integer vendorId) {
        List<OpeningHours> list = openingHoursService.getByVendorId(vendorId);
        return list.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    // ------------------------------------------------------------
    // GET: 取得自己登入的 Vendor 開放時間 (DTO)
    @GetMapping("/self")
    public List<OpeningHoursDTO> findSelf(HttpSession session) {
        Vendor vendor = (Vendor) session.getAttribute("vendor");
        if (vendor == null) throw new RuntimeException("尚未登入或 Session 過期");

        List<OpeningHours> list = openingHoursService.getByVendorId(vendor.getVendorId());
        return list.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    // ------------------------------------------------------------
    // GET: 取得所有開放時間 (DTO)
    @GetMapping("/")
    public List<OpeningHoursDTO> findAll() {
        List<OpeningHours> list = openingHoursService.findAll();
        return list.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    // ------------------------------------------------------------
    // POST: 新增開放時間
    @PostMapping("/")
    public OpeningHoursDTO create(@RequestBody OpeningHours bean) {
        OpeningHours created = openingHoursService.create(bean);
        return toDTO(created);
    }

    // ------------------------------------------------------------
    // PUT: 修改自己登入的開放時間
    @PutMapping("/self")
    public List<OpeningHoursDTO> modifySelf(@RequestBody List<OpeningHours> list, HttpSession session) {
        Vendor vendor = (Vendor) session.getAttribute("vendor");
        if (vendor == null) throw new RuntimeException("尚未登入或 Session 過期");

        List<OpeningHours> updated = openingHoursService.upsertOpeningHours(vendor.getVendorId(), list);
        return updated.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    // ------------------------------------------------------------
    // DELETE: 刪除開放時間
    @DeleteMapping("/{id}")
    public boolean remove(@PathVariable Integer id) {
        return openingHoursService.remove(id);
    }

    // ------------------------------------------------------------
    // 私有方法: Entity -> DTO
    private OpeningHoursDTO toDTO(OpeningHours h) {
        return new OpeningHoursDTO(
            h.getVdOpeningHourId(),
            h.getVendor() != null ? h.getVendor().getVendorId() : null,
            h.getDayOfWeek(),
            h.getOpeningTime() != null ? h.getOpeningTime().toString() : null,
            h.getClosingTime() != null ? h.getClosingTime().toString() : null
        );
    }
}