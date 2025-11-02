package Food.service.vendor;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Food.dto.vendor.ProductReviewDTO;
import Food.dto.vendor.ReviewDTO;
import Food.entity.vendor.VProduct;
import Food.repository.vendor.ProductRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	// 圖片上傳路徑
	private static final String UPLOAD_DIR = "src/main/resources/static/Product_Pic/";

	// @Autowired
	// private VendorRepository vendorRepository;

	// findById
	public List<VProduct> findByVendorId(Integer vendorId) {
		List<VProduct> opt = productRepository.findByVendorId(vendorId);
		return opt;
	};

	// findAll
	public List<VProduct> findAll() {
		return productRepository.findAll();
	}

	// create
	public VProduct createProduct(VProduct product) {
		return productRepository.save(product);
	}

	public VProduct reviewProduct(Integer id, ReviewDTO dto) {
		VProduct product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("找不到商品"));
		product.setStatus(dto.getStatus());
		product.setReason(dto.getReason());
		return productRepository.save(product);
	}

	// 根據狀態搜尋
	public List<ProductReviewDTO> getPendingProductsForAdmin() {
		return productRepository.findPendingProductsWithVendor();
	}

	// modify
	public VProduct modify(VProduct vProduct) {
		if (vProduct != null && productRepository.existsById(vProduct.getProductId())) {

			return productRepository.save(vProduct);
		}
		return null;
	}

	// delete
	public boolean remove(Integer productId) {
		if (productRepository.existsById(productId)) {
			productRepository.deleteById(productId);
			return true;
		}
		return false;
	}

	// 查詢所有商品(給管理員用)
	public List<ProductReviewDTO> getAllProducts() {
		return productRepository.findAllProductsWithVendor();
	}

	// 根據狀態查詢商品(彈性查詢，備用)
	public List<ProductReviewDTO> getProductsByStatus(String status) {
		return productRepository.findProductsByStatus(status);
	}

	/**
	 * 切換商品上架/下架狀態 - 「上架中」→「已下架」 - 「已下架」→「上架中」 - 其他狀態不允許切換
	 */
	public VProduct toggleProductStatus(Integer productId, Integer vendorId) {
		// 1.查詢商品
		VProduct product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("找不到商品"));
		// 2.確認是商品擁有者
		if (!product.getVendor().getVendorId().equals(vendorId)) {
			throw new RuntimeException("無權操作此商品");
		}
		// 3. 根據目前狀態切換
		String currentStatus = product.getStatus();
		switch (currentStatus) {
		case "上架中":
			product.setStatus("已下架");
			break;
		case "已下架":
			product.setStatus("上架中");
			break;
		case "審核中":
			throw new RuntimeException("商品審核中，無法操作");
		case "審核不通過":
			throw new RuntimeException("請先修改商品後重新送審");
		default:
			throw new RuntimeException("未知的商品狀態");
		}
		return productRepository.save(product);
	}

	/**
	 * 修改商品資訊（只能修改庫存和到期日） - 「上架中」、「已下架」狀態可以修改 - 「審核中」、「審核不通過」狀態也可以修改（為了重新送審）
	 */
	public VProduct updateProductDetails(Integer productId, Integer vendorId, Integer stock, LocalDate endDate,
			String productName, // 🆕 新增
			MultipartFile picFile) {
		// 1. 查詢商品
		VProduct product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("找不到商品"));

		// 2. 確認是商品擁有者
		if (!product.getVendor().getVendorId().equals(vendorId)) {
			throw new RuntimeException("無權限操作此商品");
		}
		// 3. 驗證必填欄位
		if (stock == null || stock < 0) {
			throw new RuntimeException("庫存數量不正確");
		}

		if (endDate == null) {
			throw new RuntimeException("到期日不可為空");
		}

		if (endDate.isBefore(LocalDate.now())) {
			throw new RuntimeException("到期日不能早於今天");
		}

		// 4.更新資料
		product.setStock(stock);
		product.setEndDate(endDate);
		// 5. 🆕 更新商品名稱（選填）
		if (productName != null && !productName.trim().isEmpty()) {
			product.setProductName(productName);
		}

		// 6. 🆕 更新商品圖片（選填）
		if (picFile != null && !picFile.isEmpty()) {
			try {
				String newFilename = saveImage(picFile);
				product.setPicUrl(newFilename);
			} catch (Exception e) {
				throw new RuntimeException("圖片上傳失敗：" + e.getMessage());
			}
		}

		return productRepository.save(product);
	}


	/**
	 * 重新送審（針對「審核不通過」的商品） - 將狀態改為「審核中」 - 清除原本的拒絕原因
	 */
	public VProduct resubmitProduct(Integer productId, Integer vendorId) {
		// 1. 查詢商品
		VProduct product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("找不到商品"));
		// 2. 確認是商品擁有者
		if (!product.getVendor().getVendorId().equals(vendorId)) {
			throw new RuntimeException("無權限操作此商品");
		}
		// 3.確認是審核不通過的狀態
		if (!"審核不通過".equals(product.getStatus())) {
			throw new RuntimeException("只有審核不通過的商品可以重新送審");
		}

		// 4.更新狀態
		product.setStatus("審核中");
		product.setReason(null); // 清除拒絕原因

		return productRepository.save(product);
	}

	// ==================== 🆕 輔助方法 ====================

	/**
	 * 儲存圖片檔案
	 */
	// 改為 public
	public String saveImage(MultipartFile file) throws Exception {
	    // 1. 產生唯一檔名
	    String originalFilename = file.getOriginalFilename();
	    String extension = "";
	    if (originalFilename != null && originalFilename.contains(".")) {
	        extension = originalFilename.substring(originalFilename.lastIndexOf("."));
	    }
	    String newFilename = UUID.randomUUID().toString() + extension;

	    // 2. ✅ 使用絕對路徑
	    String projectPath = System.getProperty("user.dir");
	    String uploadPath = projectPath + File.separator + UPLOAD_DIR;
	    
	    // 3. 確保目錄存在
	    File uploadDir = new File(uploadPath);
	    if (!uploadDir.exists()) {
	        boolean created = uploadDir.mkdirs();
	        if (!created) {
	            throw new IOException("無法創建上傳目錄: " + uploadPath);
	        }
	    }

	    // 4. 儲存檔案
	    File destinationFile = new File(uploadPath + newFilename);
	    file.transferTo(destinationFile);
	    
	    System.out.println("✅ 圖片已儲存至: " + destinationFile.getAbsolutePath());

	    return newFilename;
	}

}