package Food.repository.reservation;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Food.entity.reservation.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Procedure(procedureName = "dbo.sp_UpsertReservation")
    void upsertReservation(
            @Param("ReservationID") Integer reservationID,
            @Param("MemberID") Integer memberID,
            @Param("VendorID") Integer vendorID,
            @Param("ReservationDate") Date reservationDate,
            @Param("ReservationPeriod") String reservationPeriod,
            @Param("GuestCount") Short guestCount);

    @Procedure(procedureName = "dbo.sp_DeleteReservation")
    void deleteReservation(@Param("ReservationID") Integer reservationID);

    // 查詢會員所有訂位，按日期倒序
    List<Reservation> findByMemberIDOrderByReservationDateDesc(Integer memberID);

    // 查詢指定會員的單筆訂位
    Optional<Reservation> findByReservationIDAndMemberID(Integer reservationID, Integer memberID);

    // 廠商查詢自己id的所有訂位
    List<Reservation> findByVendorID(Integer vendorID);

    // 查詢指定會員的所有訂位，按訂位ID倒序
    List<Reservation> findByMemberIDOrderByReservationIDDesc(Integer memberID);
}
