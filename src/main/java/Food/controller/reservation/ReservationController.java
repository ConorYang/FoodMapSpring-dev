package Food.controller.reservation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Food.dto.reservation.ApiResponse;
import Food.dto.reservation.ReservationsDTO;
import Food.entity.member.Member;
import Food.entity.reservation.Reservation;
import Food.entity.vendor.Vendor;
import Food.service.reservation.ReservationService;
import Food.websocket.VendorNotificationWebSocketHandler;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

	private final ReservationService service;
	@Autowired // ✅ 注入 WebSocket Handler
	private VendorNotificationWebSocketHandler vendorNotificationHandler;

	public ReservationController(ReservationService service) {
		this.service = service;
	}

	// 廠商取得自己餐廳的所有訂位
	@GetMapping("/vendor")
	public ApiResponse<List<Reservation>> getVendorReservations(HttpSession session) {
		Vendor vendor = (Vendor) session.getAttribute("vendor");
		if (vendor == null) {
			return ApiResponse.error("未登入廠商");
		}

		List<Reservation> reservations = service.findByVendorId(vendor.getVendorId());
		return ApiResponse.success(reservations);
	}

	// 會員取得自己的所有訂位
	@GetMapping("/my")
	public ApiResponse<List<Reservation>> getMyReservations(HttpSession session) {
		Member member = (Member) session.getAttribute("member");
		if (member == null) {
			return ApiResponse.error("未登入會員");
		}
		List<Reservation> reservations = service.findByMemberId(member.getMemberId());
		return ApiResponse.success(reservations);
	}

	// 會員取得單筆自己的訂位
	@GetMapping("/my/{id}")
	public ApiResponse<Reservation> getMyReservation(@PathVariable Integer id, HttpSession session) {
		Member member = (Member) session.getAttribute("member");
		if (member == null) {
			return ApiResponse.error("未登入會員");
		}

		Optional<Reservation> res = service.findById(id);
		if (res.isEmpty() || !res.get().getMemberID().equals(member.getMemberId())) {
			return ApiResponse.error("無權限查看此訂位");
		}
		return ApiResponse.success(res.get());
	}

	// 新增或修改自己的訂位
	@PostMapping("/my")
	public ApiResponse<Reservation> createOrUpdate(@RequestBody ReservationsDTO dto, HttpSession session) {
		Member member = (Member) session.getAttribute("member");
		if (member == null) {
			return ApiResponse.error("未登入會員");
		}

		// 強制指定 memberId
		dto.setMemberId(member.getMemberId());

		Reservation res = service.upsertReservation(dto);
		if (res != null) {
			String message = String.format("收到新訂位！日期: %s %s，人數: %d", res.getReservationDate(),
					res.getReservationPeriod(), res.getGuestCount());

			vendorNotificationHandler.sendMessageToVendor(dto.getVendorId(), "reservation", message);

			return ApiResponse.success("Reservation saved", res);
		}
		return ApiResponse.error("Failed to save reservation");
	}

	// 刪除自己的訂位
	@DeleteMapping("/my/{id}")
	public ApiResponse<Reservation> delete(@PathVariable Integer id, HttpSession session) {
		Member member = (Member) session.getAttribute("member");
		if (member == null) {
			return ApiResponse.error("未登入會員");
		}

		Reservation res = service.deleteReservation(id, member.getMemberId());
		if (res != null) {
			return ApiResponse.success("Reservation deleted", res);
		}
		return ApiResponse.error("無權限刪除此訂位或訂位不存在");
	}
}
