package Food.service.search;

import java.util.List;
import java.util.Map;

import Food.entity.vendor.VD_Styles;

public interface VendorService {
    List<Map<String, Object>> advancedSearch(
            String keyword, Integer styleId, String city,
            Integer userMinPrice,
            Integer userMaxPrice,
            String timeSlot,
            Boolean airConditioner, Boolean veganFriendly, Boolean petFriendly,
            Integer dayOfWeek);

    List<VD_Styles> findAllStyles();

    List<Map<String, Object>> getTop3PopularVendors();
}
