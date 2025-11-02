package Food.controller.cuSer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Food.entity.cuSer.CustomerServiceBean;
import Food.service.cuSer.CustomerServiceService;
import Food.websocket.AdminCServiceWebSocketHandler;
import Food.websocket.CustomerServiceWebSocketHandler;

@RestController
@RequestMapping("/customerservice")

public class CustomerServiceController {

	@Autowired
	private CustomerServiceService service;

	@Autowired
	private CustomerServiceWebSocketHandler customerServiceHandler;

	@Autowired
	private AdminCServiceWebSocketHandler adminCSHandler; // 管理者 WS Handler

	// 新增案件
	@PostMapping
	public CustomerServiceBean saveCustomerServiceData(@RequestBody CustomerServiceBean cs) {

		System.out.println("🧩 adminCSHandler 是否為 null？ " + (adminCSHandler == null));

		// 新增客服案件
		CustomerServiceBean savedCase = service.createNewCase(cs);

		// 推送給 WebSocket 訂閱者
		customerServiceHandler.sendNewCase(savedCase);

		 // 推播給管理者 WebSocket
         adminCSHandler.sendNewCustomerServiceToAdmin(savedCase);

		// 回傳新增的案件
		return savedCase;
	}

	// 回覆(修改)
	@PutMapping("/{id}")
	public CustomerServiceBean replyCustomerServiceData(@PathVariable Integer id, @RequestBody CustomerServiceBean cs) {
		return service.reply(id, cs);
	}

	// 查詢全部 - 管理頁面
	@PostMapping("/find")
	public List<CustomerServiceBean> findAll() {
		return service.findAll();
	}

	// 用狀態查詢 - 管理頁面
	@GetMapping
	public List<CustomerServiceBean> findByCsStatus(@RequestParam String status) {
		return service.findByCsStatus(status);
	}

	// // 用email查詢 - 新增頁面（自動填入memberID)
	// @GetMapping("/findbyemail")
	// public Optional<CustomerServiceBean> findByEmail(@RequestParam String email)
	// {
	// return service.findByEmail(email);
	// }

	// 用memberid查詢
	@GetMapping("/{memberId}")
	public List<CustomerServiceBean> findBymemberId(@PathVariable Integer memberId) {
		return service.findBymemberId(memberId);
	}

	// 取得會員已結案客服案件
	@GetMapping("/close/{memberId}")
	public List<CustomerServiceBean> findCloseByMemberId(@PathVariable Integer memberId) {
		return service.findCloseByMemberId(memberId);
	}

	@GetMapping("/search")
	public List<CustomerServiceBean> search(@RequestParam String keyword) {
		return service.search(keyword);
	}


}
