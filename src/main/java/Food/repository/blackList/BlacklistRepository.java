package Food.repository.blackList;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Food.entity.blackList.Blacklist;

public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {
    List<Blacklist> findByReasonContainingIgnoreCase(String keyword);

    Optional<Blacklist> findByMemberId(Long memberId);

    void deleteByMemberId(Long memberId);

    boolean existsByMemberId(Long memberId);
}
