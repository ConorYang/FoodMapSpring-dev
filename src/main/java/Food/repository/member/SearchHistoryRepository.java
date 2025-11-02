package Food.repository.member;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Food.entity.member.SearchHistory;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Integer> {

    // 查某會員的所有搜尋紀錄
    List<SearchHistory> findByMember_MemberIdOrderBySearchTimeDesc(Integer memberId);

    // 查某會員最近 N 筆紀錄
    List<SearchHistory> findTop10ByMember_MemberIdOrderBySearchTimeDesc(Integer memberId);
}
