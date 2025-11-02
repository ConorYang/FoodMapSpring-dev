package Food.service.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Food.entity.member.Member;
import Food.entity.member.SearchHistory;
import Food.repository.member.SearchHistoryRepository;

@Service
public class SearchHistoryService {

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    /**
     * 記錄搜尋條件（登入或未登入都會記錄）
     */
    public void recordSearch(Member member, String keyword, Integer styleId, String city, String priceRange,
            String timeSlot) {

        if (keyword != null && keyword.trim().isEmpty())
            keyword = null;
        if (city != null && city.trim().isEmpty())
            city = null;
        if (priceRange != null && priceRange.trim().isEmpty())
            priceRange = null;
        if (timeSlot != null && timeSlot.trim().isEmpty())
            timeSlot = null;

        boolean allEmpty = keyword == null &&
                styleId == null &&
                city == null &&
                priceRange == null &&
                timeSlot == null;

        if (allEmpty) {
            return;
        }

        SearchHistory history = new SearchHistory(member, keyword, styleId, city, priceRange, timeSlot);

        searchHistoryRepository.save(history);
    }

    /**
     * 查詢某會員最近搜尋紀錄
     */
    public List<SearchHistory> getRecentSearches(Integer memberId) {
        return searchHistoryRepository.findTop10ByMember_MemberIdOrderBySearchTimeDesc(memberId);
    }
}
