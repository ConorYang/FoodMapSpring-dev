package Food.entity.member;

import java.time.LocalDateTime;

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
@Table(name = "SearchHistory")
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SearchID")
    private Integer searchId;

    // 關聯會員，可為 null（未登入搜尋）
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MemberID")
    private Member member;

    @Column(name = "Keyword", length = 100)
    private String keyword;

    @Column(name = "StyleID")
    private Integer styleId;

    @Column(name = "City", length = 50)
    private String city;

    @Column(name = "PriceRange", length = 20)
    private String priceRange;

    @Column(name = "TimeSlot", length = 20)
    private String timeSlot;

    @Column(name = "SearchTime")
    private LocalDateTime searchTime = LocalDateTime.now();

    // ===== Constructors =====
    public SearchHistory() {
    }

    public SearchHistory(Member member, String keyword, Integer styleId, String city, String priceRange,
            String timeSlot) {
        this.member = member;
        this.keyword = keyword;
        this.styleId = styleId;
        this.city = city;
        this.priceRange = priceRange;
        this.timeSlot = timeSlot;
        this.searchTime = LocalDateTime.now();
    }

    // ===== Getters & Setters =====
    public Integer getSearchId() {
        return searchId;
    }

    public void setSearchId(Integer searchId) {
        this.searchId = searchId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public LocalDateTime getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(LocalDateTime searchTime) {
        this.searchTime = searchTime;
    }
}
