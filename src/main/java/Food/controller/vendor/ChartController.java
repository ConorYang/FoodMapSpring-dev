package Food.controller.vendor;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Food.entity.vendor.Vendor;
import Food.service.vendor.ChartService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/charts")
public class ChartController {

	private final ChartService chartService;

	public ChartController(ChartService chartService) {
		this.chartService = chartService;
	}

	// 店家被收藏次數
	@GetMapping("/favorites")
	public ResponseEntity<Integer> getFavoriteCount(HttpSession session) {
		Vendor vendor = (Vendor) session.getAttribute("vendor");
		if (vendor == null || vendor.getVendorId() == null) {
			return ResponseEntity.status(401).build();
		}
		int count = chartService.getFavoriteCountByVendor(vendor.getVendorId());
		return ResponseEntity.ok(count);
	}

	@GetMapping("/views")
	public ResponseEntity<Integer> getViewCount(HttpSession session) {
		Vendor vendor = (Vendor) session.getAttribute("vendor");
		if (vendor == null || vendor.getVendorId() == null) {
			return ResponseEntity.status(401).build();
		}
		int count = chartService.getViewCountByVendor(vendor.getVendorId());
		return ResponseEntity.ok(count);
	}

	// 日報表瀏覽數
	@GetMapping("/views/daily")
	public ResponseEntity<List<Map<String, Object>>> getViewCountDaily(HttpSession session) {
		Vendor vendor = (Vendor) session.getAttribute("vendor");
		if (vendor == null || vendor.getVendorId() == null) {
			return ResponseEntity.status(401).build();
		}
		List<Map<String, Object>> data = chartService.getViewCountByDate(vendor.getVendorId());
		return ResponseEntity.ok(data);
	}

}