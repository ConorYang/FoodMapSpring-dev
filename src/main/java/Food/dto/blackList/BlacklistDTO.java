package Food.dto.blackList;

public class BlacklistDTO {
    private Long memberId;  // 對應資料表 MemberID
    private String account; // 帳號（來自 userRepository）
    private String reason;  // 黑名單原因
    

    // --- Getters & Setters ---
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
