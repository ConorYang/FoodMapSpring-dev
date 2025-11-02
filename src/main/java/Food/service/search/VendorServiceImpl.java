package Food.service.search;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Food.entity.vendor.VD_Styles;
import Food.repository.search.StyleRepository;
import Food.repository.search.VendorsRepository;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorsRepository vendorsRepository;

    @Autowired
    private StyleRepository styleRepository;

    @Override
    public List<Map<String, Object>> getTop3PopularVendors() {
        List<Object[]> results = vendorsRepository.findTop3PopularVendors();
        List<Map<String, Object>> list = new ArrayList<>();

        for (Object[] r : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("vendorId", r[0]);
            map.put("vendorName", r[1]);
            map.put("logoUrl", r[2]);
            map.put("address", r[3]);
            map.put("contactTel", r[4]);
            map.put("favoriteCount", r[5]);
            map.put("lookCount", r[6]);
            map.put("popularScore", r[7]);
            list.add(map);
        }
        return list;
    }

    /**
     * 進階搜尋：轉換 timeSlot 為時間範圍後傳給 repository
     */
    @Override
    public List<Map<String, Object>> advancedSearch(
            String keyword, Integer styleId, String city, Integer userMinPrice, Integer userMaxPrice,
            String timeSlot,
            Boolean airConditioner, Boolean veganFriendly, Boolean petFriendly,
            Integer dayOfWeek) {

        LocalTime slotStart = null;
        LocalTime slotEnd = null;

        if (timeSlot != null) {
            switch (timeSlot) {
                case "breakfast" -> {
                    slotStart = LocalTime.of(5, 0);
                    slotEnd = LocalTime.of(9, 59);
                }
                case "lunch" -> {
                    slotStart = LocalTime.of(10, 0);
                    slotEnd = LocalTime.of(13, 59);
                }
                case "afternoon" -> {
                    slotStart = LocalTime.of(14, 0);
                    slotEnd = LocalTime.of(16, 59);
                }
                case "dinner" -> {
                    slotStart = LocalTime.of(17, 0);
                    slotEnd = LocalTime.of(23, 59);
                }
                default -> {
                    slotStart = null;
                    slotEnd = null;
                }
            }
        }

        List<Object[]> results = vendorsRepository.advancedSearchRaw(
                keyword, styleId, city,
                userMinPrice, userMaxPrice,
                slotStart, slotEnd,
                airConditioner, veganFriendly, petFriendly,
                dayOfWeek);

        List<Map<String, Object>> list = new ArrayList<>();
        for (Object[] r : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("vendorId", r[0]);
            map.put("vendorName", r[1]);
            map.put("logoUrl", r[2]);
            map.put("address", r[3]);
            map.put("contactTel", r[4]);
            map.put("styleName", r[5]);
            map.put("openingTime", r[6]);
            map.put("closingTime", r[7]);
            map.put("closedDays", r[8]);
            list.add(map);
        }
        return list;
    }

    @Override
    public List<VD_Styles> findAllStyles() {
        return styleRepository.findAll();
    }
}
