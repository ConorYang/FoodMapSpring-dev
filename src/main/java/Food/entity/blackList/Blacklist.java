package Food.entity.blackList;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Blacklists")
public class Blacklist {

    @Id
    @Column(name = "MemberID")
    private Long memberId;

    @JsonProperty
    @Column(nullable = false)
    private String reason;

    // getters & setters
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
