package Food.service.reservation;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Food.dto.reservation.ReservationsDTO;
import Food.entity.reservation.Reservation;
import Food.repository.reservation.ReservationRepository;
import Food.service.account.EmailService;
import Food.websocket.ReservationWebSocketHandler;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepo;

    @Autowired
    private ReservationWebSocketHandler reservationWsHandler;
    @Autowired
    private EmailService emailService;

    Reservation savedReservation;

    public ReservationService(ReservationRepository reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

    // ======================
    // 新增 / 更新訂位
    // ======================
    public Reservation upsertReservation(ReservationsDTO dto) {
        // 判斷是新增還是修改
        boolean isNewReservation = (dto.getReservationID() == null);
        // 呼叫資料庫的 upsert stored procedure
        reservationRepo.upsertReservation(
                dto.getReservationID(),
                dto.getMemberId(),
                dto.getVendorId(),
                Date.valueOf(dto.getReservationDate()),
                dto.getReservationPeriod(),
                dto.getGuestCount().shortValue());

        Reservation savedReservation;

        if (dto.getReservationID() != null) {
            // 修改 用原本 ID 撈出更新後的資料
            savedReservation = reservationRepo.findById(dto.getReservationID()).orElse(null);
        } else {
            // 新增 用 reservationID DESC 撈出該會員最新那筆（ID 自增）
            List<Reservation> reservations = reservationRepo.findByMemberIDOrderByReservationIDDesc(dto.getMemberId());
            savedReservation = reservations.isEmpty() ? null : reservations.get(0);
        }

        // 通知 WebSocket（不論新增或修改都會發送）
        if (savedReservation != null) {
            try {
                String type = (dto.getReservationID() == null)
                        ? "new_reservation"
                        : "update_reservation";

                reservationWsHandler.sendReservationNotification(
                        dto.getMemberId().longValue(),
                        savedReservation,
                        type);
                System.out.println("已發送訂位通知給 memberId=" + dto.getMemberId() + "，類型：" + type);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("訂位通知發送失敗");
            }
            // 發送 Email 通知
            if (isNewReservation) {
                // 新增訂位 → 發送確認信
                sendReservationConfirmationEmail(dto);
            } else {
                // 修改訂位 → 發送修改通知信
                sendReservationUpdateEmail(dto);
            }
        }

        return savedReservation;
    }

    // ✅ 5. 新增整個方法：發送訂位成功確認信
    private void sendReservationConfirmationEmail(ReservationsDTO dto) {
        try {
            String memberEmail = dto.getMemberEmail();
            String memberName = dto.getMemberName();
            String vendorName = dto.getVendorName();

            // 檢查必要欄位
            if (memberEmail == null || memberEmail.isEmpty()) {
                System.err.println("❌ 會員 email 為空，無法發送訂位確認信");
                return;
            }

            if (vendorName == null || vendorName.isEmpty()) {
                vendorName = "餐廳";
            }

            // 組合信件內容
            String subject = "訂位成功通知 - " + vendorName;
            String content = String.format(
                    "親愛的 %s 您好，\n\n" +
                            "您的訂位已成功！以下是您的訂位資訊：\n\n" +
                            "━━━━━━━━━━━━━━━━━━━━━━\n" +
                            "📍 餐廳名稱：%s\n" +
                            "📅 訂位日期：%s\n" +
                            "🕐 用餐時段：%s\n" +
                            "👥 訂位人數：%d 位\n" +
                            "━━━━━━━━━━━━━━━━━━━━━━\n\n" +
                            "⚠️ 請準時前往餐廳報到，謝謝！\n\n" +
                            "如有任何問題，請聯繫餐廳。\n\n" +
                            "祝您用餐愉快！",
                    memberName != null ? memberName : "會員",
                    vendorName,
                    dto.getReservationDate(),
                    dto.getReservationPeriod(),
                    dto.getGuestCount());

            // 發送 email（改用非同步）
            emailService.sendEmailAsync(memberEmail, subject, content);

            System.out.println("✅ 訂位確認信已發送至: " + memberEmail);

        } catch (Exception e) {
            System.err.println("❌ 發送訂位確認信失敗: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ 6. 新增整個方法：發送訂位修改通知信
    private void sendReservationUpdateEmail(ReservationsDTO dto) {
        try {
            String memberEmail = dto.getMemberEmail();
            String memberName = dto.getMemberName();
            String vendorName = dto.getVendorName();

            // 檢查必要欄位
            if (memberEmail == null || memberEmail.isEmpty()) {
                System.err.println("❌ 會員 email 為空，無法發送訂位修改通知信");
                return;
            }

            if (vendorName == null || vendorName.isEmpty()) {
                vendorName = "餐廳";
            }

            // 組合信件內容
            String subject = "訂位修改通知 - " + vendorName;
            String content = String.format(
                    "親愛的 %s 您好，\n\n" +
                            "您的訂位已修改成功！以下是您的最新訂位資訊：\n\n" +
                            "━━━━━━━━━━━━━━━━━━━━━━\n" +
                            "📍 餐廳名稱：%s\n" +
                            "📅 訂位日期：%s\n" +
                            "🕐 用餐時段：%s\n" +
                            "👥 訂位人數：%d 位\n" +
                            "━━━━━━━━━━━━━━━━━━━━━━\n\n" +
                            "⚠️ 請準時前往餐廳報到，謝謝！\n\n" +
                            "如有任何問題，請聯繫餐廳。\n\n" +
                            "祝您用餐愉快！",
                    memberName != null ? memberName : "會員",
                    vendorName,
                    dto.getReservationDate(),
                    dto.getReservationPeriod(),
                    dto.getGuestCount());

            // 發送 email（改用非同步）
            emailService.sendEmailAsync(memberEmail, subject, content);

            System.out.println("✅ 訂位修改通知信已發送至: " + memberEmail);

        } catch (Exception e) {
            System.err.println("❌ 發送訂位修改通知信失敗: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ======================
    // 刪除訂位
    // ======================
    public Reservation deleteReservation(Integer reservationID, Integer memberID) {
        Optional<Reservation> reservation = reservationRepo.findByReservationIDAndMemberID(reservationID, memberID);
        if (reservation.isPresent()) {
            reservationRepo.deleteReservation(reservationID);

            // 通知刪除事件
            try {
                reservationWsHandler.sendReservationNotification(
                        memberID.longValue(),
                        reservation.get(),
                        "delete_reservation");
                System.out.println("已發送刪除通知給 memberId=" + memberID);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return reservation.get();
        }
        return null;
    }

    // ======================
    // 查詢功能
    // ======================
    public List<Reservation> findAll() {
        return reservationRepo.findAll();
    }

    public Optional<Reservation> findById(Integer id) {
        return reservationRepo.findById(id);
    }

    public List<Reservation> findByMemberId(Integer memberId) {
        return reservationRepo.findByMemberIDOrderByReservationDateDesc(memberId);
    }

    // 廠商查詢自己餐廳的所有訂位
    public List<Reservation> findByVendorId(Integer vendorId) {
        return reservationRepo.findByVendorID(vendorId);
    }

}