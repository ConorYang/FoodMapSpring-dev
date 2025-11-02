package Food.service.shopping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Food.dto.shopping.OrderDTO;
import Food.dto.shopping.OrderDetailDTO;
import Food.entity.shopping.Cart;
import Food.entity.shopping.EcpayTransaction;
import Food.entity.shopping.Order;
import Food.entity.shopping.OrderDetail;
import Food.repository.shopping.CartRepository;
import Food.repository.shopping.EcpayTransactionRepository;
import Food.repository.shopping.OrderDetailRepository;
import Food.repository.shopping.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private EcpayTransactionRepository ecpayTransactionRepository;
	@Autowired
	private EcpayService ecpayService; // 註解：注入綠界支付服務

	@Transactional // 註解：確保事務一致性
	public String createOrderFromCartAndGeneratePayment(Long accountId) throws Exception { // 註解：修改返回型別為 String (支付表單
																							// HTML)
		List<Cart> cartItems = cartRepository.findByAccountIdWithDetails(accountId);
		if (cartItems.isEmpty()) {
			throw new RuntimeException("Cart is empty"); // 註解：購物車空檢查
		}

		// 計算總額
		int totalAmount = cartItems.stream().mapToInt(item -> {
			int price = 0;

			// 廠商方案價格優先：如果 PlanPrice 存在且大於 0 (表示是廠商購買方案)
			// 修正：將 item.getPlanPrice() 替換為 item.getAdPlan().getPlanPrice()
			if (item.getAdPlan() != null && item.getAdPlan().getPlanPrice().doubleValue() > 0) {
				// 由於綠界支付要求整數，這裡確保 PlanPrice (可能是 Double) 被四捨五入為整數
				price = (int) Math.round(item.getAdPlan().getPlanPrice().doubleValue());
			} else {
				// 否則使用產品的 CurrentPrice (一般會員購買產品)
				// 假設 CurrentPrice 返回 int
				price = item.getProduct() != null ? item.getProduct().getCurrentPrice() : 0;
			}

			return item.getQuantity() * price;
		}).sum();

		String tradeNo = ecpayService.generateTradeNo();// 註解：生成唯一訂單號

		// 創建訂單
		Order order = new Order();
		order.setAccountId(accountId);
		order.setOrderNo(tradeNo); // 註解：儲存並使用生成的訂單號
		order.setTotalAmount(totalAmount);
		order.setPaymentStatus("PENDING");
		order = orderRepository.save(order); // 註解：儲存訂單

		// 轉換購物車到訂單細節
		for (Cart cart : cartItems) {
			OrderDetail detail = new OrderDetail();
			detail.setOrderId(order.getOrderId().intValue());
			detail.setProductId(cart.getProductId());
			detail.setPlanId(cart.getPlanId());
			detail.setQuantity(cart.getQuantity());

			Integer priceToUse;
			// 廠商方案價格優先
			// 修正：將 cart.getPlanPrice() 替換為 cart.getAdPlan().getPlanPrice()
			if (cart.getAdPlan() != null && cart.getAdPlan().getPlanPrice() > 0) {
				priceToUse = cart.getAdPlan().getPlanPrice();
			} else {
				// 否則使用產品的 CurrentPrice
				priceToUse = cart.getProduct() != null ? cart.getProduct().getCurrentPrice() : 0;
			}
			detail.setUnitPrice(priceToUse); // 註解：設定單價
			orderDetailRepository.save(detail); // 註解：儲存細節
		}

		// 清空購物車
		cartRepository.deleteAll(cartItems); // 註解：訂單生成後清空

		// 生成綠界支付表單
		String itemName = cartItems.stream().map(item -> item.getProduct() != null ? item.getProduct().getProductName()
				: item.getAdPlan().getPlanName()).collect(Collectors.joining("#")); // 註解：組合商品名稱，用 # 分隔（綠界要求）
		String paymentForm = ecpayService.generatePaymentForm(totalAmount, itemName, order.getOrderNo()); // 註解：生成支付表單
		return paymentForm; // 註解：返回支付表單 HTML，讓前端直接顯示導向
	}

	// 處理綠界支付回傳
	public void handlePaymentCallback(Map<String, String> params) {
		System.out.println("OrderService: Handling payment callback with params: " + params);
		try {
			// 驗證 CheckMacValue
			boolean isValid = ecpayService.verifyCheckMacValue(params);
			if (!isValid) {
				System.out.println("OrderService: CheckMacValue verification failed");
				throw new RuntimeException("Invalid CheckMacValue");
			}

			// 取得必要參數
			String merchantTradeNo = params.get("MerchantTradeNo");
			String merchantId = params.get("MerchantID");
			if (merchantTradeNo == null || merchantTradeNo.isEmpty()) {
				throw new RuntimeException("Missing MerchantTradeNo");
			}
			if (merchantId == null || merchantId.isEmpty()) {
				throw new RuntimeException("Missing MerchantID");
			}

			// 查詢訂單
			Order order = orderRepository.findByOrderNo(merchantTradeNo);
			if (order == null) {
				throw new RuntimeException("Order not found");
			}

			// 更新訂單狀態
			String rtnCode = params.get("RtnCode");
			if ("1".equals(rtnCode)) {
				order.setPaymentStatus("PAID");
				order.setPaidAt(LocalDateTime.now());
			} else {
				order.setPaymentStatus("FAILED");
			}
			orderRepository.save(order);

			// 儲存交易紀錄
			EcpayTransaction transaction = new EcpayTransaction();
			transaction.setMerchantId(merchantId); // 必填
			transaction.setMerchantTradeNo(merchantTradeNo);
			transaction.setTradeNo(params.getOrDefault("TradeNo", ""));
			transaction.setOrderNo(order.getOrderNo());

			try {
				transaction.setRtnCode(Integer.parseInt(rtnCode));
			} catch (NumberFormatException e) {
				transaction.setRtnCode(0);
			}

			transaction.setRtnMsg(params.getOrDefault("RtnMsg", ""));
			try {
				transaction.setTradeAmt(new BigDecimal(params.getOrDefault("TradeAmt", "0")));
			} catch (NumberFormatException e) {
				transaction.setTradeAmt(BigDecimal.ZERO);
			}

			// TradeDate
			String tradeDateStr = params.get("TradeDate");
			if (tradeDateStr != null && !tradeDateStr.isEmpty()) {
				transaction.setTradeDate(
						LocalDateTime.parse(tradeDateStr, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
			} else {
				transaction.setTradeDate(LocalDateTime.now());
			}

			try {
				String paymentDate = params.get("PaymentDate");
				if (paymentDate != null && !paymentDate.isEmpty()) {
					transaction.setPaymentDate(
							LocalDateTime.parse(paymentDate, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
				} else {
					transaction.setPaymentDate(LocalDateTime.now());
				}
			} catch (DateTimeParseException e) {
				transaction.setPaymentDate(LocalDateTime.now());
			}

			transaction.setCheckMacValue(params.getOrDefault("CheckMacValue", ""));
			transaction.setRawData(params.toString());

			ecpayTransactionRepository.save(transaction);

			System.out.println("OrderService: Successfully processed callback for orderNo: "
					+ merchantTradeNo + ", paymentStatus: " + order.getPaymentStatus());

		} catch (Exception e) {
			System.out.println("OrderService: Payment callback error for params: "
					+ params + ", error: " + e.getMessage());
			throw e; // 回傳 0|Error 給綠界
		}
	}

	public List<Order> getOrders(Long accountId) {
		System.out.println("OrderService: Fetching orders for accountId: " + accountId);
		return orderRepository.findByAccountId(accountId);
	}

	public Order getOrderByOrderNo(String orderNo) {
		System.out.println("OrderService: Fetching order by orderNo: " + orderNo); // 註解：記錄查詢
		return orderRepository.findByOrderNo(orderNo);
	}

	@Transactional(readOnly = true)
	public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
		// 這裡用 join fetch 確保 product 資料也抓到
		return orderDetailRepository.findByOrderIdWithProduct(orderId);
	}

	public OrderDTO getOrderResult(String orderNo) {

		// 1️⃣ 查訂單主檔
		Order order = orderRepository.findByOrderNo(orderNo);
		if (order == null) {
			throw new RuntimeException("訂單不存在");
		}

		// 2️⃣ 查綠界交易紀錄
		EcpayTransaction ecpay = ecpayTransactionRepository.findByOrderNo(orderNo)
				.orElse(null);

		// 3️⃣ 訂單狀態轉換
		String status;
		String dbStatus = order.getPaymentStatus();
		if ("PAID".equals(dbStatus)) {
			status = "success";
		} else if ("PENDING".equals(dbStatus) || "NEW".equals(dbStatus) || dbStatus == null) {
			status = "pending";
		} else {
			status = "error";
		}

		// 4️⃣ 明細轉換
		List<OrderDetail> details = getOrderDetailsByOrderId(order.getOrderId());
		List<OrderDetailDTO> detailDTOs = details.stream()
				.map(OrderDetailDTO::new)
				.collect(Collectors.toList());

		// 5️⃣ 組 DTO
		OrderDTO dto = new OrderDTO();
		dto.setStatus(status);
		dto.setOrderNo(order.getOrderNo());
		dto.setTotalAmount(order.getTotalAmount());
		dto.setDetails(detailDTOs);

		// 6️⃣ 綠界付款資訊（有資料才填）
		if (ecpay != null) {
			dto.setPaymentType(ecpay.getPaymentType() != null ? ecpay.getPaymentType() : "Credit_CreditCard");
			dto.setPaymentDate(ecpay.getPaymentDate());
			dto.setTradeNo(ecpay.getTradeNo());
		} else {
			dto.setPaymentType("Credit_CreditCard");
			dto.setPaymentDate(null);
			dto.setTradeNo(null);
		}

		return dto;
	}

	@Transactional(readOnly = true)
	public Page<Order> getOrderHistoryByAccountId(Long accountId, Pageable pageable) {
		if (accountId == null) {
			throw new IllegalArgumentException("Account ID cannot be null.");
		}
		// 調用 Repository 中自動生成的查詢方法
		return orderRepository.findByAccountId(accountId, pageable);
	}
	public Integer getTotalRevenueByVendor(Integer vendorId) {
	    return orderDetailRepository.sumRevenueByVendorId(vendorId);
	}
}