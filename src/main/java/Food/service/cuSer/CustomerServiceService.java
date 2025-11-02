package Food.service.cuSer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Food.entity.cuSer.CustomerServiceBean;
import Food.repository.cuSer.CustomerServiceRepository;

@Service
@Transactional
public class CustomerServiceService {

	@Autowired
	private CustomerServiceRepository repository;

	// 新增
	public CustomerServiceBean createNewCase(CustomerServiceBean cs) {

		CustomerServiceBean insert = new CustomerServiceBean();
		insert.setEmail(cs.getEmail());
		insert.setSubject(cs.getSubject());
		insert.setContext(cs.getContext());
		insert.setMemberId(cs.getMemberId());
		insert.setVendorId(cs.getVendorId());
		insert.setCreateAt(LocalDateTime.now());
		insert.setCsStatus("待處理");
		return repository.save(insert);

	}

	// 回覆(修改)
	public CustomerServiceBean reply(Integer id, CustomerServiceBean cs) {

		Optional<CustomerServiceBean> optional = repository.findById(id);
		if (optional.isPresent()) {
			CustomerServiceBean update = optional.get();
			update.setReply(cs.getReply());
			update.setReplyAt(LocalDateTime.now());
			update.setAccountId(cs.getAccountId());
			update.setCsStatus("已回覆");
			return repository.save(update);
		}
		return null;
	}

	// 查詢全部
	public List<CustomerServiceBean> findAll() {
		return repository.findAll();
	}

	// 取得會員已結案客服案件
	public List<CustomerServiceBean> findCloseByMemberId(Integer memberId) {
		// 只抓取 CS_Status != "已回覆"
		return repository.findByMemberIdAndCsStatusNot(memberId, "待處理");
	}

	// 用狀態查詢
	public List<CustomerServiceBean> findByCsStatus(String status) {
		return repository.findByCsStatus(status);
	}

	// // 用email查詢
	// public Optional<CustomerServiceBean> findByEmail(String email) {
	// return repository.findByEmail(email);
	// }

	// 用memberid查詢
	public List<CustomerServiceBean> findBymemberId(Integer memberId) {
		return repository.findBymemberId(memberId);
	}

	// 關鍵字查詢
	public List<CustomerServiceBean> search(String keyword) {
		return repository.searchByKeyword(keyword);
	}

}
