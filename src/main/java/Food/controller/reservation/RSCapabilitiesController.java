package Food.controller.reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Food.dto.reservation.ApiResponse;
import Food.dto.reservation.RSCapabilitiesDTO;
import Food.entity.reservation.RSCapabilities;
import Food.entity.reservation.RSCapabilitiesId;
import Food.entity.vendor.Vendor;
import Food.service.reservation.RSCapabilitiesService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/capabilities")
public class RSCapabilitiesController {

    private final RSCapabilitiesService service;

    public RSCapabilitiesController(RSCapabilitiesService service) {
        this.service = service;
    }

    // -----------------------------
    // 會員前台取得指定餐廳的可訂位資訊
    // -----------------------------
    @GetMapping("/vendor/{vendorId}/public")
    public ApiResponse<List<RSCapabilitiesDTO>> getCapabilitiesByVendorId(@PathVariable Integer vendorId) {
        List<RSCapabilities> list = service.findByVendorId(vendorId, true);

        if (list.isEmpty()) {
            return ApiResponse.error("該餐廳沒有可訂位資料");
        }

        List<RSCapabilitiesDTO> dtoList = list.stream().map(entity -> {
            RSCapabilitiesDTO dto = new RSCapabilitiesDTO();
            dto.setVendorId(entity.getId().getVendorId());
            dto.setReservationDate(entity.getId().getReservationDate());
            dto.setReservationPeriod(entity.getId().getReservationPeriod());
            dto.setCapability(entity.getCapability());
            dto.setMaxGuests(entity.getMaxGuests()); // 新增
            return dto;
        }).toList();

        return ApiResponse.success(dtoList);
    }

    // 取得所有資料（廠商自己的）
    @GetMapping
    public ApiResponse<List<RSCapabilities>> getAll(HttpSession session) {
        Vendor vendor = (Vendor) session.getAttribute("vendor");
        if (vendor == null) {
            return ApiResponse.error("尚未登入或不是廠商身分");
        }
        Integer vendorId = vendor.getVendorId();
        List<RSCapabilities> list = service.findByVendorId(vendorId, true);
        if (list.isEmpty()) {
            return ApiResponse.error("該廠商沒有資料");
        }
        return ApiResponse.success(list);
    }

    // 取得單筆資料
    @GetMapping("/{date}/{period}")
    public ApiResponse<RSCapabilities> getOne(
            HttpSession session,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable String period) {

        Vendor vendor = (Vendor) session.getAttribute("vendor");
        if (vendor == null) {
            return ApiResponse.error("尚未登入或不是廠商身分");
        }
        Integer vendorId = vendor.getVendorId();

        RSCapabilitiesId id = new RSCapabilitiesId(vendorId, date, period);
        Optional<RSCapabilities> optional = service.findById(id);

        return optional.map(ApiResponse::success)
                .orElseGet(() -> ApiResponse.error("沒有該資料"));
    }

    // 取得廠商全部資料
    @GetMapping("/vendor")
    public ApiResponse<List<RSCapabilitiesDTO>> getByVendor(HttpSession session) {
        Vendor vendor = (Vendor) session.getAttribute("vendor");
        if (vendor == null) {
            return ApiResponse.error("尚未登入或不是廠商身分");
        }

        List<RSCapabilities> list = service.findByVendorId(vendor.getVendorId(), true);

        if (list.isEmpty()) {
            return ApiResponse.error("該廠商沒有資料");
        }

        List<RSCapabilitiesDTO> dtoList = list.stream().map(entity -> {
            RSCapabilitiesDTO dto = new RSCapabilitiesDTO();
            dto.setVendorId(entity.getId().getVendorId());
            dto.setReservationDate(entity.getId().getReservationDate());
            dto.setReservationPeriod(entity.getId().getReservationPeriod());
            dto.setCapability(entity.getCapability());
            dto.setMaxGuests(entity.getMaxGuests()); // 新增
            return dto;
        }).toList();

        return ApiResponse.success(dtoList);
    }

    // 新增資料
    @PostMapping
    public ApiResponse<RSCapabilities> create(@RequestBody RSCapabilitiesDTO dto, HttpSession session) {
        Vendor vendor = (Vendor) session.getAttribute("vendor");
        if (vendor == null) {
            return ApiResponse.error("尚未登入或不是廠商身分");
        }
        Integer vendorId = vendor.getVendorId();

        RSCapabilitiesId id = new RSCapabilitiesId(vendorId, dto.getReservationDate(), dto.getReservationPeriod());
        if (service.findById(id).isPresent()) {
            return ApiResponse.error("資料已存在");
        }

        RSCapabilities entity = new RSCapabilities();
        entity.setId(id);
        entity.setCapability(dto.getCapability());
        entity.setMaxGuests(dto.getMaxGuests()); // 新增
        RSCapabilities saved = service.save(entity);

        return ApiResponse.success("新增成功", saved);
    }

    // 更新資料
    @PutMapping
    public ApiResponse<RSCapabilities> update(@RequestBody RSCapabilitiesDTO dto, HttpSession session) {
        Vendor vendor = (Vendor) session.getAttribute("vendor");
        if (vendor == null) {
            return ApiResponse.error("尚未登入或不是廠商身分");
        }
        Integer vendorId = vendor.getVendorId();

        RSCapabilitiesId id = new RSCapabilitiesId(vendorId, dto.getReservationDate(), dto.getReservationPeriod());
        Optional<RSCapabilities> optional = service.findById(id);

        if (optional.isPresent()) {
            RSCapabilities entity = optional.get();
            entity.setCapability(dto.getCapability());
            entity.setMaxGuests(dto.getMaxGuests()); // 新增
            RSCapabilities saved = service.save(entity);
            return ApiResponse.success("修改成功", saved);
        } else {
            return ApiResponse.error("沒有該資料");
        }
    }

    // 刪除資料
    @DeleteMapping("/{date}/{period}")
    public ApiResponse<RSCapabilities> delete(
            HttpSession session,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable String period) {

        Vendor vendor = (Vendor) session.getAttribute("vendor");
        if (vendor == null) {
            return ApiResponse.error("尚未登入或不是廠商身分");
        }
        Integer vendorId = vendor.getVendorId();

        RSCapabilitiesId id = new RSCapabilitiesId(vendorId, date, period);
        Optional<RSCapabilities> optional = service.findById(id);

        if (optional.isPresent()) {
            service.deleteById(id);
            return ApiResponse.success("刪除成功", optional.get());
        } else {
            return ApiResponse.error("沒有該資料");
        }
    }
}
