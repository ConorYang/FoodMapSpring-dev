package Food.dto.vendor;

// 廠商頁面取資料用
public interface VendorDetailView {

    Integer getVendorID();

    String getVendorName();

    Integer getVD_CategoryID();

    String getCategoryName();

    String getDescription();

    Integer getVdDetailId();

    Integer getStyleId();

    String getStyleName();
}