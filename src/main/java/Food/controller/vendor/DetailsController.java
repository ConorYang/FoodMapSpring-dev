package Food.controller.vendor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Food.entity.vendor.Details;
import Food.entity.vendor.Vendor;
import Food.service.vendor.DetailsService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/vendor/details")
public class DetailsController {

    @Autowired
    private DetailsService detailsService;

    // 公開查詢，任何人都可查
    @GetMapping("/")
    public List<Details> findAll() {
        return detailsService.findAll();
    }

    // 取得自己的 Details，需要登入
    @GetMapping("/self")
    public Details getBySelf(HttpSession session) {
        Vendor vendor = (Vendor) session.getAttribute("vendor");
        if (vendor == null) {
            throw new RuntimeException("尚未登入或 Session 過期");
        }
        return detailsService.findByVendorId(vendor.getVendorId());
    }

    // 建立 Details（可以公開或內部使用，看需求）
    @PostMapping("/")
    public Details create(@RequestBody Details bean) {
        return detailsService.create(bean);
    }

    // 修改自己的 Details，需要登入
    @PutMapping("/self")
    public Details modifyBySelf(@RequestBody Details newDetails, HttpSession session) {
        Vendor vendor = (Vendor) session.getAttribute("vendor");
        if (vendor == null) {
            throw new RuntimeException("尚未登入或 Session 過期");
        }
        newDetails.setVendorId(vendor.getVendorId());
        return detailsService.modifyByVendorId(vendor.getVendorId(), newDetails);
    }

    // 刪除某筆 Details
    @DeleteMapping("/{id}")
    public boolean remove(@PathVariable Integer id) {
        return detailsService.remove(id);
    }

    // 廠商ID查詢
    @GetMapping("{vendorId}")
    public Details findByVendorId(@PathVariable Integer vendorId) {
        return detailsService.findByVendorId(vendorId);
    }
}
