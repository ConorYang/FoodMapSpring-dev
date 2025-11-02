package Food.controller.comments;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Food.dto.comment.CommentDTO;
import Food.entity.comments.CommentBean;
import Food.entity.vendor.Vendor;
import Food.repository.comments.CommentRepository;
import Food.service.comments.CommentService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService service;

	@Autowired
	private CommentRepository commentRepository;

	// 新增案件
	@PostMapping
	public CommentBean createComment(@RequestBody CommentBean cm) {
		return service.create(cm);
	}

	// 新增可放圖片的評論 Web請求處理所以放在Controller
	private final String uploadDir = "src/main/resources/static/image/comment/";

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadComment(
			// @RequestParam("memberId") Integer memberId,
			@RequestParam("orderId") Integer orderId,
			@RequestParam("commentContext") String commentContext,
			@RequestParam(value = "delicious", required = false) Boolean delicious,
			@RequestParam(value = "quick", required = false) Boolean quick,
			@RequestParam(value = "friendly", required = false) Boolean friendly,
			@RequestParam(value = "photogenic", required = false) Boolean photogenic,
			@RequestParam(value = "topCP", required = false) Boolean topCP,
			@RequestParam(value = "photos", required = false) MultipartFile[] photos) {
		try {
			CommentBean comment = new CommentBean();
			// comment.setMemberId(memberId);
			comment.setOrderId(orderId);
			comment.setCommentContext(commentContext);
			comment.setDelicious(delicious != null ? delicious : false);
			comment.setQuick(quick != null ? quick : false);
			comment.setFriendly(friendly != null ? friendly : false);
			comment.setPhotogenic(photogenic != null ? photogenic : false);
			comment.setTopCP(topCP != null ? topCP : false);
			comment.setCreateAt(LocalDateTime.now());

			// 儲存圖片
			if (photos != null) {
				for (int i = 0; i < Math.min(photos.length, 3); i++) {
					MultipartFile file = photos[i];
					if (!file.isEmpty()) {
						String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
						Path path = Paths.get(uploadDir + filename);
						Files.createDirectories(path.getParent());
						Files.write(path, file.getBytes());

						switch (i) {
							case 0 -> comment.setPhoto1("/image/comment/" + filename);
							case 1 -> comment.setPhoto2("/image/comment/" + filename);
							case 2 -> comment.setPhoto3("/image/comment/" + filename);
						}
					}
				}
			}

			commentRepository.save(comment);
			return ResponseEntity.ok("評論與圖片上傳成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上傳失敗");
		}
	}

	// 修改
	@PutMapping("/{id}")
	public CommentBean modifyComment(@PathVariable Integer id, @RequestBody CommentBean cm) {
		return service.modify(id, cm);
	}

	// 查詢全部 - 餐廳頁面
	@PostMapping("/find")
	public List<CommentBean> findAll() {
		return service.findAll();
	}

	// 查詢單筆 - 訂單頁面
	@GetMapping("/{id}")
	public Optional<CommentBean> findById(@PathVariable Integer id) {
		return service.findById(id);
	}

	// 刪除整筆
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Integer id) {
		service.deleteById(id);
	}

	// 取得超美味評價數量
	@GetMapping("/delicious/{vendorId}")
	public long getDeliciousComments(@PathVariable Integer vendorId) {
		return service.getDeliciousCount(vendorId);
	}

	// 取得超友善評價數量
	@GetMapping("/friendly/{vendorId}")
	public long getFriendlyComments(@PathVariable Integer vendorId) {
		return service.getFriendlyCount(vendorId);
	}

	// 取得超快速評價數量
	@GetMapping("/quick/{vendorId}")
	public long getQuickComments(@PathVariable Integer vendorId) {
		return service.getQuickCount(vendorId);
	}

	// 取得超上相評價數量
	@GetMapping("/photogenic/{vendorId}")
	public long getPhotogenicComments(@PathVariable Integer vendorId) {
		return service.getPhotogenicCount(vendorId);
	}

	// 取得高CP值評價數量
	@GetMapping("/topcp/{vendorId}")
	public long getTopCpComments(@PathVariable Integer vendorId) {
		return service.getTopCPCount(vendorId);
	}

	// 依orderID取得評價，確認是否已評價過
	@GetMapping("/byorder/{orderId}")
	public Optional<CommentBean> findByOrderId(@PathVariable Integer orderId) {
		return service.findByOrderId(orderId);
	}

	// 依廠商取得所有評論，DTO(只顯示有評論或照片，去除空字串)
	@GetMapping("/byvendor/{vendorId}")
	public List<CommentDTO> findByVendorId(@PathVariable Integer vendorId) {
		return service.findByVendorId(vendorId);
	}
	
	// 依vendorID取得評論 給店家後台看自己的
	@GetMapping("/commentVendor/{vendorId}")
	public ResponseEntity<?> findByVendorIdForVendor(
	        @PathVariable Integer vendorId,
	        HttpSession session) {

	    // 1. 檢查是否登入
	    Vendor vendor = (Vendor) session.getAttribute("vendor");
	    if (vendor == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body("尚未登入或 Session 過期");
	    }

	    // 2. 檢查是否為本人
	    if (!vendor.getVendorId().equals(vendorId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                .body("無權查看其他店家的評論");
	    }

	    // 3. 返回評論列表（使用新方法）
	    List<CommentBean> comments = service.findCommentsByVendorIdForBackend(vendorId);
	    return ResponseEntity.ok(comments);
	}


	// 會員後台顯示用
	@GetMapping("/bymember/{memberId}")
	public List<CommentDTO> findByMemberID(@PathVariable Integer memberId) {
		return service.findByMemberID(memberId);
	}
}
