package Food.repository.account;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Food.entity.account.User;
import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByAccount(String account);
		

	Optional<User> findByVerificationToken(String token);
		

    // 查詢帳號+狀態
    Page<User> findByAccountContainingAndStatus(String account, Integer status, Pageable pageable);

    // 查詢帳號,不篩選狀態
    Page<User> findByAccountContaining(String account, Pageable pageable);
    


    // 更新 User 狀態 (給AdminService 資料表透過accountsId去更改Accounts的status用)
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :status WHERE u.accountId = :accountId")
    int updateStatus(@Param("accountId") Long accountId, @Param("status") Integer status);

    // 取得 memberId (給BlackLists 資料表透過memberId找帳號用)
    @Query("SELECT u FROM User u WHERE u.member.id = :memberId")
    Optional<User> findByMemberId(@Param("memberId") Long memberId);

    
    // 透過 accountId 找對應的 memberId (給當帳號被選到黑名單用)
    @Query("SELECT u.member.memberId FROM User u WHERE u.accountId = :accountId")
    Long findMemberIdByAccountId(@Param("accountId") Long accountId);



}
