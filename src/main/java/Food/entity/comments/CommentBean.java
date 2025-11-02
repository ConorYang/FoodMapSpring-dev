package Food.entity.comments;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "Comments")
public class CommentBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommentID", nullable = false)
    private Integer commentId;

    // 未設外鍵
    @Column(name = "OrderID", nullable = false)
    private Integer orderId;

    @Column(name = "Delicious", nullable = false)
    private boolean delicious;

    @Column(name = "Quick", nullable = false)
    private boolean quick;

    @Column(name = "Friendly", nullable = false)
    private boolean friendly;

    @Column(name = "Photogenic", nullable = false)
    private boolean photogenic;

    @Column(name = "TopCP", nullable = false)
    private boolean topCP;

    @Column(name = "CommentContext", length = 500)
    private String commentContext;

    @Lob
    @Column(name = "Photo1")
    private String photo1;

    @Lob
    @Column(name = "Photo2")
    private String photo2;

    @Lob
    @Column(name = "Photo3")
    private String photo3;

    @Column(name = "CreateAt", nullable = false)
    private LocalDateTime createAt;

    // Getter & Setter
    public CommentBean() {

    }

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

    public boolean isDelicious() {
        return delicious;
    }

    public void setDelicious(boolean delicious) {
        this.delicious = delicious;
    }

    public boolean isQuick() {
        return quick;
    }

    public void setQuick(boolean quick) {
        this.quick = quick;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public void setFriendly(boolean friendly) {
        this.friendly = friendly;
    }

    public boolean isPhotogenic() {
        return photogenic;
    }

    public void setPhotogenic(boolean photogenic) {
        this.photogenic = photogenic;
    }

    public boolean isTopCP() {
        return topCP;
    }

    public void setTopCP(boolean topCP) {
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
