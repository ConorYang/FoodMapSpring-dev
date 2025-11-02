package Food.entity.reservation;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationID;

    private Integer memberID;
    private Integer vendorID;

    @Column(columnDefinition = "date")
    private Date reservationDate;

    private String reservationPeriod;
    private Short guestCount;

    @Column(columnDefinition = "nchar(10)")
    private String RV_Status;

    @Column(updatable = false)
    private Date createTime;

    private Date updateTime;

    // Getter & Setter
    public Integer getReservationID() {
        return reservationID;
    }

    public void setReservationID(Integer reservationID) {
        this.reservationID = reservationID;
    }

    public Integer getMemberID() {
        return memberID;
    }

    public void setMemberID(Integer memberID) {
        this.memberID = memberID;
    }

    public Integer getVendorID() {
        return vendorID;
    }

    public void setVendorID(Integer vendorID) {
        this.vendorID = vendorID;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationPeriod() {
        return reservationPeriod;
    }

    public void setReservationPeriod(String reservationPeriod) {
        this.reservationPeriod = reservationPeriod;
    }

    public Short getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(Short guestCount) {
        this.guestCount = guestCount;
    }

    public String getRV_Status() {
        return RV_Status;
    }

    public void setRV_Status(String RV_Status) {
        this.RV_Status = RV_Status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
