package Food.entity.vendor;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "VD_OpeningHours")
public class OpeningHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VD_OpeningHourID")
    private Integer vdOpeningHourId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VendorID")
    @JsonIgnoreProperties({ "openingHours", "user" })
    private Vendor vendor;

    @Column(name = "DayOfWeek", nullable = false)
    private Integer dayOfWeek;

    @Column(name = "OpeningTime")
    private LocalTime openingTime;

    @Column(name = "ClosingTime")
    private LocalTime closingTime;

    // 建構子
    public OpeningHours() {
    }

    // getter & setter
    // public Integer getVendorId() {
    // return vendor != null ? vendor.getVendorId() : null;
    // }

    public Integer getVdOpeningHourId() {
        return vdOpeningHourId;
    }

    public void setVdOpeningHourId(Integer vdOpeningHourId) {
        this.vdOpeningHourId = vdOpeningHourId;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }
}