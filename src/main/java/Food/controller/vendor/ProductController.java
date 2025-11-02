package Food.controller.vendor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import Food.dto.vendor.ProductReviewDTO;
import Food.dto.vendor.ReviewDTO;
import Food.dto.vendor.UpdateProductDTO;
import Food.entity.account.User;
import Food.entity.vendor.VProduct;
import Food.entity.vendor.Vendor;
import Food.repository.vendor.ProductRepository;
import Food.service.vendor.ProductService;
import Food.service.vendor.VendorService;
import Food.websocket.VendorNotificationWebSocketHandler;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/vProduct")
public class ProductController {

	private final ProductRepository productRepository;

	@Autowired
	private ProductService productService;
	@Autowired
	private VendorService vendorService;
	
	//private static final String UPLOAD_DIR = "src/main/resources/static/Product_Pic/";
	@Autowired
	private VendorNotificationWebSocketHandler vendorNotificationWebSocketHandler;

	  // ==================== 輔助方法 ====================

	ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@GetMapping("/{vendorId}")
	public List<VProduct> findByVendorIdController(@PathVariable Integer vendorId) {
		return productService.findByVendorId(vendorId);
	}

	@GetMapping("/search")
	public Optional<VProduct> findByProductId(@RequestParam Integer productId) {
		return productRepository.findByProductId(productId);
	}

	@GetMapping("/all")
	public List<VProduct> findAll() {
		return productService.findAll();
	}

	@GetMapping("/self")
	public List<VProduct> findMyProducts(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			throw new RuntimeException("未登入");
		}
		Vendor vendor = (Vendor) session.getAttribute("vendor");
		if (vendor == null)
	        throw new RuntimeException("找不到廠商資料");
		return productService.findByVendorId(vendor.getVendorId());
	}

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(
            @RequestParam("productName") String productName,
            @RequestParam("unitPrice") Integer unitPrice,
            @RequestParam(value = "specialPrice", required = false) Integer specialPrice,
            @RequestParam("endDate") String endDate,
            @RequestParam("stock") Integer stock,
            @RequestParam(value = "picFile", required = false) MultipartFile picFile,
            HttpSession session) {

        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登入");
            }

    		Vendor vendor = (Vendor) session.getAttribute("vendor");
            if (vendor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("找不到廠商資料");
            }

            VProduct product = new VProduct();
            product.setProductName(productName);
            product.setUnitPrice(unitPrice);
            product.setSpecialPrice(specialPrice);
            product.setEndDate(LocalDate.parse(endDate));
            product.setStock(stock);
            product.setStatus("審核中");
            product.setVendor(vendor);
            product.setVendorId(vendor.getVendorId());

            // ✅ 使用 Service 的方法處理圖片上傳
            if (picFile != null && !picFile.isEmpty()) {
                String filename = productService.saveImage(picFile);
                product.setPicUrl(filename);
            }
//            // 處理圖片上傳
//            if (picFile != null && !picFile.isEmpty()) {
//                String originalFilename = picFile.getOriginalFilename();
//                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//                String newFilename = UUID.randomUUID().toString() + extension;
//
//                File uploadDir = new File(UPLOAD_DIR);
//                if (!uploadDir.exists()) {
//                    uploadDir.mkdirs();
//                }
//
//                File destinationFile = new File(UPLOAD_DIR + newFilename);
//                picFile.transferTo(destinationFile);
//                product.setPicUrl(newFilename);
//            }
            
            VProduct saved = productService.createProduct(product);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("新增商品失敗：" + e.getMessage());
        }
    }

	// 管理者"看" 審核商品
	@GetMapping("/pending")
	public List<ProductReviewDTO> getPendingProducts(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null)
			throw new RuntimeException("未登入");
		return productService.getPendingProductsForAdmin();
	}

    @PutMapping("/{id}/review")
    public ResponseEntity<?> reviewProduct(
            @PathVariable Integer id,
            @RequestBody ReviewDTO dto,
            HttpSession session) {

        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登入");
            }

            // 驗證是否為管理員
            if (user.getUserType()== null|| user.getUserType().getId() !=1L) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("無權限");
            }

            VProduct updated = productService.reviewProduct(id, dto);
         // 審核完成後發送 WebSocket 通知
            try {
                Integer vendorId = updated.getVendorId();
                String status = dto.getStatus();
                String reason = dto.getReason();
                String productName = updated.getProductName();
                
                String message;
                if ("上架中".equals(status)) {
                    message = String.format("您的商品「%s」已審核通過並上架！", productName);
                } else if ("審核不通過".equals(status)) {
                    message = String.format("您的商品「%s」審核不通過，原因：%s", productName, reason);
                } else {
                    message = String.format("您的商品「%s」狀態已更新", productName);
                }
                
                // 發送通知
                vendorNotificationWebSocketHandler.sendMessageToVendor(vendorId, "product", message);
                System.out.println(" 已發送商品審核通知給 Vendor " + vendorId);
                
            } catch (Exception notifyError) {
                // 通知失敗不影響審核結果
                System.err.println("發送通知失敗（不影響審核）: " + notifyError.getMessage());
            }
            return ResponseEntity.ok(updated);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("審核失敗：" + e.getMessage());
        }
    }

    
    /**
     * 修改商品（原有方法）
     */
    @PutMapping("/{productId}")
    public ResponseEntity<?> modifyProduct(
            @PathVariable Integer productId,
            @RequestBody VProduct vProduct,
            HttpSession session) {

        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登入");
            }

            vProduct.setProductId(productId);
            VProduct modified = productService.modify(vProduct);

            if (modified != null) {
                return ResponseEntity.ok(modified);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("商品不存在");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("修改失敗：" + e.getMessage());
        }
    }

    /**
     * 刪除商品
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable Integer productId,
            HttpSession session) {

        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登入");
            }

            boolean deleted = productService.remove(productId);
            if (deleted) {
                return ResponseEntity.ok("刪除成功");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("商品不存在");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("刪除失敗：" + e.getMessage());
        }
    }

//==========================================================================
    /**
     * 管理員查詢所有商品（新版）
     */
    @GetMapping("/Admin/all")
    public ResponseEntity<?> getAllProducts(HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登入");
            }

            // 驗證是否為管理員
            if (user.getUserType()== null|| user.getUserType().getId() !=1L) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("無權限");
            }

            List<ProductReviewDTO> products = productService.getAllProducts();
            return ResponseEntity.ok(products);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("查詢失敗：" + e.getMessage());
        }
    }

    /**
     * 廠商切換商品上架/下架狀態
     */
    @PutMapping("/{productId}/toggle-status")
    public ResponseEntity<?> toggleProductStatus(
            @PathVariable Integer productId,
            HttpSession session) {

        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登入");
            }

    		Vendor vendor = (Vendor) session.getAttribute("vendor");
            if (vendor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("找不到廠商資料");
            }

            VProduct updated = productService.toggleProductStatus(productId, vendor.getVendorId());
            return ResponseEntity.ok(updated);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("操作失敗：" + e.getMessage());
        }
    }

    /**
     * 廠商修改商品資訊（只能修改庫存和到期日）
     */
    @PutMapping("/{productId}/update-details")
    public ResponseEntity<?> updateProductDetails(
            @PathVariable Integer productId,
            @RequestParam("stock") Integer stock,
            @RequestParam("endDate") String endDate,
            @RequestParam(value = "productName", required = false) String productName,      // 🆕 選填
            @RequestParam(value = "picFile", required = false) MultipartFile picFile,       // 🆕 選填
            HttpSession session) {

        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登入");
            }

    		Vendor vendor = (Vendor) session.getAttribute("vendor");
            if (vendor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("找不到廠商資料");
            }

            // 🆕 呼叫改造後的 Service 方法
            VProduct updated = productService.updateProductDetails(
                productId, 
                vendor.getVendorId(),
                stock,
                LocalDate.parse(endDate),
                productName,    // 可能是 null
                picFile         // 可能是 null
            );
            
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("修改失敗：" + e.getMessage());
        }
    }

    /**
     * 廠商重新送審商品
     */
    @PutMapping("/{productId}/resubmit")
    public ResponseEntity<?> resubmitProduct(
            @PathVariable Integer productId,
            HttpSession session) {

        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登入");
            }

    		Vendor vendor = (Vendor) session.getAttribute("vendor");
            if (vendor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("找不到廠商資料");
            }

            VProduct updated = productService.resubmitProduct(productId, vendor.getVendorId());
            return ResponseEntity.ok(updated);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("重新送審失敗：" + e.getMessage());
        }
    }
}