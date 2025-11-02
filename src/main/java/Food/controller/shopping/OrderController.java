package Food.controller.shopping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Food.dto.shopping.OrderDTO;
import Food.dto.shopping.OrderDetailDTO;
import Food.entity.account.User;
import Food.entity.shopping.Order;
import Food.entity.shopping.OrderDetail;
import Food.entity.vendor.Vendor;
import Food.service.shopping.OrderService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	private Long getAccountId(HttpSession session) {
		User user = (User) session.getAttribute("user");
		return user != null ? user.getAccountId() : null;
	}

	@PostMapping("/create")
	public ResponseEntity<String> createOrder(HttpSession session) throws Exception {
		Long accountId = getAccountId(session);
		if (accountId == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		String paymentForm = orderService.createOrderFromCartAndGeneratePayment(accountId);
		return ResponseEntity.ok(paymentForm);
	}

	@PostMapping("/payment-callback")
	public ResponseEntity<String> handlePaymentCallback(@RequestParam Map<String, String> params) {
		try {
			orderService.handlePaymentCallback(params);
			return ResponseEntity.ok("1|OK");
		} catch (Exception e) {
			return ResponseEntity.ok("0|Error");
		}
	}

	@GetMapping
	public ResponseEntity<List<Order>> getOrders(HttpSession session) {
		Long accountId = getAccountId(session);
		if (accountId == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		return ResponseEntity.ok(orderService.getOrders(accountId));
	}

	@GetMapping("/result")
	public ResponseEntity<OrderDTO> getOrderResult(
			@RequestParam(value = "orderNo", required = false) String orderNo,
			@RequestParam(value = "MerchantTradeNo", required = false) String merchantTradeNo,
			HttpSession session) {

		Long accountId = getAccountId(session);
		if (accountId == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		String tradeNo = orderNo != null ? orderNo : merchantTradeNo;
		if (tradeNo == null)
			return ResponseEntity.badRequest().build();

		try {
			OrderDTO dto = orderService.getOrderResult(tradeNo);
			if (dto == null) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/history")
	public ResponseEntity<Page<OrderDTO>> getOrderHistory(
			// *** 關鍵: 定義預設排序為 PaidAt 降序 (DESC) ***
			@PageableDefault(sort = "paidAt", direction = Sort.Direction.DESC, size = 10) Pageable pageable,
			HttpSession session) {

		Long accountId = getAccountId(session);
		if (accountId == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		try {
			// 服務層只負責傳遞 Pageable 物件
			Page<Order> orderPage = orderService.getOrderHistoryByAccountId(accountId, pageable);

			// 將 Order 實體 Page 轉換為 OrderDTO Page
			List<OrderDTO> dtos = orderPage.getContent().stream()
					.map(OrderDTO::new)
					.collect(Collectors.toList());

			// 建立一個包含分頁資訊的 PageImpl
			Page<OrderDTO> dtoPage = new PageImpl<>(
					dtos,
					pageable,
					orderPage.getTotalElements());

			return ResponseEntity.ok(dtoPage);
		} catch (Exception e) {
			// 紀錄錯誤 e
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/details/{orderId}")
	public ResponseEntity<List<OrderDetailDTO>> getOrderDetails(
			@PathVariable Long orderId,
			HttpSession session) {

		Long accountId = getAccountId(session);
		if (accountId == null) {
			System.out.println("❌ 未登入");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		try {
			System.out.println("✅ 查詢訂單明細 - orderId: " + orderId + ", accountId: " + accountId);

			// 取得訂單明細
			List<OrderDetail> details = orderService.getOrderDetailsByOrderId(orderId);

			System.out.println("✅ 找到 " + details.size() + " 筆明細");

			// 轉換為 DTO
			List<OrderDetailDTO> dtos = details.stream()
					.map(detail -> {
						OrderDetailDTO dto = new OrderDetailDTO(detail);
						return dto;
					})
					.collect(Collectors.toList());

			return ResponseEntity.ok(dtos);

		} catch (Exception e) {
			System.err.println("❌ 查詢訂單明細失敗: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	// 1. 先加取得 vendorId 的方法
	private Integer getVendorId(HttpSession session) {
	    Vendor vendor = (Vendor) session.getAttribute("vendor");
	    return vendor != null ? vendor.getVendorId() : null;
	}

	// 2. 新增總營收 API
	@GetMapping("/vendor/revenue")
	public ResponseEntity<Integer> getVendorTotalRevenue(HttpSession session) {
	    Integer vendorId = getVendorId(session);
	    if (vendorId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	    
	    Integer totalRevenue = orderService.getTotalRevenueByVendor(vendorId);
	    return ResponseEntity.ok(totalRevenue);
	}
}
