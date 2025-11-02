package Food.service.comments;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Food.dto.comment.CommentDTO;
import Food.entity.comments.CommentBean;
import Food.repository.comments.CommentRepository;

@Service
@Transactional
public class CommentService {

	@Autowired
	private CommentRepository repository;

	// 新增
	public CommentBean create(CommentBean cm) {

		CommentBean insert = new CommentBean();
		insert.setOrderId(cm.getOrderId());
		insert.setDelicious(cm.isDelicious());
		insert.setQuick(cm.isQuick());
		insert.setFriendly(cm.isFriendly());
		insert.setPhotogenic(cm.isPhotogenic());
		insert.setTopCP(cm.isTopCP());
		insert.setCommentContext(cm.getCommentContext());
		insert.setPhoto1(cm.getPhoto1());
		insert.setPhoto2(cm.getPhoto2());
		insert.setPhoto3(cm.getPhoto3());
		insert.setCreateAt(LocalDateTime.now());
		return repository.save(insert);

	}

	// 修改
	public CommentBean modify(Integer id, CommentBean cm) {

		Optional<CommentBean> optional = repository.findById(id);
		if (optional.isPresent()) {
			CommentBean update = optional.get();
			update.setDelicious(cm.isDelicious());
			update.setQuick(cm.isQuick());
			update.setFriendly(cm.isFriendly());
			update.setPhotogenic(cm.isPhotogenic());
			update.setTopCP(cm.isTopCP());
			update.setCommentContext(cm.getCommentContext());
			update.setPhoto1(cm.getPhoto1());
			update.setPhoto2(cm.getPhoto2());
			update.setPhoto3(cm.getPhoto3());
			update.setCreateAt(LocalDateTime.now());
			return repository.save(update);
		}
		return null;
	}

	// 查詢全部
	public List<CommentBean> findAll() {
		return repository.findAll();
	}

	// 查詢單筆
	public Optional<CommentBean> findById(Integer id) {
		return repository.findById(id);
	}

	// 刪除單筆
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

	// 取得評價數量-超美味
	public long getDeliciousCount(Integer vendorId) {
		return repository.countDeliciousComments(vendorId);
	}

	// 取得評價數量-超友善
	public long getFriendlyCount(Integer vendorId) {
		return repository.countFriendlyComments(vendorId);
	}

	// 取得評價數量-超快速
	public long getQuickCount(Integer vendorId) {
		return repository.countQuickComments(vendorId);
	}

	// 取得評價數量-超上相
	public long getPhotogenicCount(Integer vendorId) {
		return repository.countPhotogenicComments(vendorId);
	}

	// 取得評價數量-高CP值
	public long getTopCPCount(Integer vendorId) {
		return repository.countTopCPComments(vendorId);
	}

	// 依OrderID取得評價，確認是否已評價過
	public Optional<CommentBean> findByOrderId(Integer orderId) {
		return repository.findByOrderId(orderId);
	}

	// 依廠商取得所有評論，DTO(只顯示有評論或照片，去除空字串)
	public List<CommentDTO> findByVendorId(Integer vendorId) {
		return repository.findByVendorId(vendorId);
	}
	// 依VendorID取得評論（店家後台專用 - 新增）
	public List<CommentBean> findCommentsByVendorIdForBackend(Integer vendorId) {
	    return repository.findCommentsByVendorIdForBackend(vendorId);
	}

	// 會員後台顯示用
	public List<CommentDTO> findByMemberID(Integer memberId) {
		return repository.findByMemberID(memberId);
	}
}
