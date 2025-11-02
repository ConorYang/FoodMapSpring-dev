package Food.repository.cuSer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Food.entity.cuSer.CustomerServiceBean;

@Repository
public interface CustomerServiceRepository extends JpaRepository<CustomerServiceBean, Integer> {
	List<CustomerServiceBean> findByCsStatus(String csStatus);

	// Optional<CustomerServiceBean> findByEmail(String email);

	List<CustomerServiceBean> findBymemberId(Integer memberId);

	// 取得會員已結案客服案件
	List<CustomerServiceBean> findByMemberIdAndCsStatus(Integer memberId, String csStatus);

    // 取得會員未結案客服案件
	List<CustomerServiceBean> findByMemberIdAndCsStatusNot(Integer memberId, String csStatus);

	// 模糊搜尋：會員ID / email / vendorId 任一符合即可
	@Query("""
			    SELECT c FROM CustomerServiceBean c
			    WHERE
			        CAST(c.memberId AS string) LIKE %:keyword%
			        OR LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
			        OR CAST(c.vendorId AS string) LIKE %:keyword%
			""")
	List<CustomerServiceBean> searchByKeyword(String keyword);

}
