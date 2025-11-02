package Food.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Food.entity.account.User;
import Food.entity.member.Member;
@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByUser(User user); // 用登入的User查Member
    Member findByUser_AccountId(Long accountId); 
    Optional<Member> findByUserName(String userName); 
}