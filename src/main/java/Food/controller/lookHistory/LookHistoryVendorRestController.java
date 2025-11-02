package Food.controller.lookHistory;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Food.dto.lookHistory.LookHistoryDTO;
import Food.entity.lookHistory.LookHistory;
import Food.service.lookHistory.LookHistoryService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/lookhistory")
public class LookHistoryVendorRestController {

	@Autowired
	private LookHistoryService lookHistoryService;

	@GetMapping("/{memberId}")
	public List<LookHistoryDTO> getLookHistoryByMemberId(@PathVariable Integer memberId) {
		return lookHistoryService.getHistoryByMemberId(memberId);
	}

	@PostMapping("/add")
	public LookHistory addLookHistory(@RequestBody Map<String, Integer> data, HttpSession session) {
		Object memberObj = session.getAttribute("member");
		Integer memberId = null;

		if (memberObj instanceof Food.entity.member.Member member) {
			memberId = member.getMemberId();
		} else {
			memberId = (Integer) session.getAttribute("memberId");
		}

		if (memberId == null) {
			System.out.println("無法取得登入會員資訊");
			throw new RuntimeException("尚未登入");
		}

		Integer vendorId = data.get("vendorId");
		if (vendorId == null) {
			throw new RuntimeException("缺少 vendorId");
		}

		System.out.println("新增瀏覽紀錄：memberId=" + memberId + ", vendorId=" + vendorId);
		return lookHistoryService.addOrUpdateHistory(memberId, vendorId);
	}

}
