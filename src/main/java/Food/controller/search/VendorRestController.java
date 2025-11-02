package Food.controller.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import Food.service.member.SearchHistoryService;
import Food.service.search.VendorService;
import jakarta.servlet.http.HttpSession;
import Food.entity.member.Member;
import Food.entity.vendor.VD_Styles;
import Food.repository.member.FavoriteRepository;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vendors")
public class VendorRestController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private SearchHistoryService searchHistoryService;

    @GetMapping("/search")
    public List<Map<String, Object>> searchVendors(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer styleId,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Integer userMinPrice,
            @RequestParam(required = false) Integer userMaxPrice,
            @RequestParam(required = false) String timeSlot,
            @RequestParam(required = false) Boolean airConditioner,
            @RequestParam(required = false) Boolean veganFriendly,
            @RequestParam(required = false) Boolean petFriendly,
            @RequestParam(required = false) Integer dayOfWeek,
            HttpSession session) {

        // 把空字串轉成 null，避免 Service 判斷失敗
        if (keyword != null && keyword.isBlank())
            keyword = null;
        if (city != null && city.isBlank())
            city = null;
        if (timeSlot != null && timeSlot.isBlank())
            timeSlot = null;

        List<Map<String, Object>> vendors = vendorService.advancedSearch(
                keyword, styleId, city, userMinPrice, userMaxPrice,
                timeSlot, airConditioner, veganFriendly, petFriendly, dayOfWeek);

        // 搜尋後記錄搜尋紀錄（登入或未登入都記）
        String priceRange = null;
        if (userMinPrice != null || userMaxPrice != null) {
            String min = (userMinPrice != null) ? userMinPrice.toString() : "0";
            String max = (userMaxPrice != null) ? userMaxPrice.toString() : "∞";
            priceRange = min + "-" + max;
        }

        Member member = (Member) session.getAttribute("member");
        searchHistoryService.recordSearch(member, keyword, styleId, city, priceRange, timeSlot);

        if (member != null) {
            int memberId = member.getMemberId();

            Set<Integer> favoriteVendorIds = favoriteRepository.findByMemberID(memberId)
                    .stream()
                    .map(fav -> fav.getVendorID())
                    .collect(Collectors.toSet());

            for (Map<String, Object> v : vendors) {
                Integer vid = (Integer) v.get("vendorId");
                v.put("isFavorited", favoriteVendorIds.contains(vid));
            }
        } else {
            // 未登入則全部設為未收藏
            vendors.forEach(v -> v.put("isFavorited", false));
        }

        return vendors;
    }

    @GetMapping("/styles")
    public List<VD_Styles> getAllStyles() {
        return vendorService.findAllStyles();
    }

    // Top3 店家
    @GetMapping("/popular/top3")
    public List<Map<String, Object>> getTop3Popular() {
        return vendorService.getTop3PopularVendors();
    }

}
