package Food.entity.reservation;

import jakarta.persistence.*;

@Entity
@Table(name = "RS_Capabilities")
public class RSCapabilities {

    @EmbeddedId
    private RSCapabilitiesId id;

    private Integer capability;
    private Integer maxGuests;

    // Getter & Setter
    public RSCapabilitiesId getId() {
        return id;
    }

    public void setId(RSCapabilitiesId id) {
        this.id = id;
    }

    public Integer getCapability() {
        return capability;
    }

    public void setCapability(Integer capability) {
        this.capability = capability;
    }

    public Integer getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(Integer maxGuests) {
        this.maxGuests = maxGuests;
    }
}
