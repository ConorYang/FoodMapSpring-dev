package Food.entity.reservation;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class RSCapabilitiesId implements Serializable {

    private Integer vendorId;
    private LocalDate reservationDate;
    private String reservationPeriod;

    public RSCapabilitiesId() {
    }

    public RSCapabilitiesId(Integer vendorId, LocalDate reservationDate, String reservationPeriod) {
        this.vendorId = vendorId;
        this.reservationDate = reservationDate;
        this.reservationPeriod = reservationPeriod;
    }

    // Getter & Setter
    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationPeriod() {
        return reservationPeriod;
    }

    public void setReservationPeriod(String reservationPeriod) {
        this.reservationPeriod = reservationPeriod;
    }

    // hashCode & equals
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof RSCapabilitiesId))
            return false;
        RSCapabilitiesId that = (RSCapabilitiesId) o;
        return Objects.equals(vendorId, that.vendorId) &&
                Objects.equals(reservationDate, that.reservationDate) &&
                Objects.equals(reservationPeriod, that.reservationPeriod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendorId, reservationDate, reservationPeriod);
    }
}
