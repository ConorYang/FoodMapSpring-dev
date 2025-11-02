package Food.dto.vendor;

import java.io.Serializable;

public class OpeningHoursDTO implements Serializable {
    private Integer vdOpeningHourId;
    private Integer vendorId;
    private Integer dayOfWeek;
    private String openingTime;
    private String closingTime;

    public OpeningHoursDTO() {}

    public OpeningHoursDTO(Integer vdOpeningHourId, Integer vendorId, Integer dayOfWeek, String openingTime, String closingTime) {
        this.vdOpeningHourId = vdOpeningHourId;
        this.vendorId = vendorId;
        this.dayOfWeek = dayOfWeek;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public Integer getVdOpeningHourId() { return vdOpeningHourId; }
    public void setVdOpeningHourId(Integer vdOpeningHourId) { this.vdOpeningHourId = vdOpeningHourId; }

    public Integer getVendorId() { return vendorId; }
    public void setVendorId(Integer vendorId) { this.vendorId = vendorId; }

    public Integer getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(Integer dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getOpeningTime() { return openingTime; }
    public void setOpeningTime(String openingTime) { this.openingTime = openingTime; }

    public String getClosingTime() { return closingTime; }
    public void setClosingTime(String closingTime) { this.closingTime = closingTime; }
}