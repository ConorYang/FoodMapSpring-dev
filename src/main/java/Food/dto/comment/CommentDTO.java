package Food.dto.comment;

import java.sql.Timestamp;

public class CommentDTO {

    // 共用欄位
    private Integer commentId;
    private String userName;
    private Integer vendorId;
    private Integer orderId;
    private Boolean delicious;
    private Boolean quick;
    private Boolean friendly;
    private Boolean photogenic;
    private Boolean topCP;
    private String commentContext;
    private String photo1;
    private String photo2;
    private String photo3;

    // 14欄專用
    private Integer memberId;
    private String vendorName;
    private Timestamp createdAt;

    // 13參數構造子（供13欄repository使用）
    public CommentDTO(String userName, Integer vendorId, Integer commentId, Integer orderId,
            Boolean delicious, Boolean quick, Boolean friendly, Boolean photogenic,
            Boolean topCP, String commentContext, String photo1, String photo2, String photo3) {
        this.userName = userName;
        this.vendorId = vendorId;
        this.commentId = commentId;
        this.orderId = orderId;
        this.delicious = delicious;
        this.quick = quick;
        this.friendly = friendly;
        this.photogenic = photogenic;
        this.topCP = topCP;
        this.commentContext = commentContext;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.photo3 = photo3;
    }

    // 14參數構造子（供14欄repository使用）
    public CommentDTO(Integer memberId, String vendorName, Integer commentId, Integer orderId,
            Boolean delicious, Boolean quick, Boolean friendly, Boolean photogenic,
            Boolean topCP, String commentContext, String photo1, String photo2, String photo3,
            Timestamp createdAt) {
        this.memberId = memberId;
        this.vendorName = vendorName;
        this.commentId = commentId;
        this.orderId = orderId;
        this.delicious = delicious;
        this.quick = quick;
        this.friendly = friendly;
        this.photogenic = photogenic;
        this.topCP = topCP;
        this.commentContext = commentContext;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.photo3 = photo3;
        this.createdAt = createdAt;
    }

    // Getter / Setter
    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean isDelicious() {
        return delicious;
    }

    public void setDelicious(Boolean delicious) {
        this.delicious = delicious;
    }

    public Boolean isQuick() {
        return quick;
    }

    public void setQuick(Boolean quick) {
        this.quick = quick;
    }

    public Boolean isFriendly() {
        return friendly;
    }

    public void setFriendly(Boolean friendly) {
        this.friendly = friendly;
    }

    public Boolean isPhotogenic() {
        return photogenic;
    }

    public void setPhotogenic(Boolean photogenic) {
        this.photogenic = photogenic;
    }

    public Boolean isTopCP() {
        return topCP;
    }

    public void setTopCP(Boolean topCP) {
        this.topCP = topCP;
    }

    public String getCommentContext() {
        return commentContext;
    }

    public void setCommentContext(String commentContext) {
        this.commentContext = commentContext;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
