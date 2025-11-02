package Food.controller.account;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Food.dto.account.UserDTO;
import Food.entity.account.User;
import Food.repository.account.UserRepository;
import Food.service.account.AdminService;

@RestController
@RequestMapping("/api/accounts")
public class AdminRestController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;

    // 分頁 + 搜尋 + 狀態篩選
    @GetMapping("/list")
    public Page<UserDTO> list(
            @RequestParam(defaultValue = "") String account,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Page<User> users = adminService.getAccounts(account, status, page, size);
        return users.map(UserDTO::new);
    }

    @GetMapping("/all")
    public List<UserDTO> getAllAccounts() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDTO::new).toList();
    }

    // 修改帳號狀態
    @PutMapping("/{accountId}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable("accountId") Long accountId,
            @RequestBody Map<String, Integer> body) {

        Integer status = body.get("status");
        if (status == null) {
            return ResponseEntity.badRequest().body("status 參數缺失");
        }

        boolean ok = adminService.updateStatus(accountId, status);
        return ok ? ResponseEntity.ok("帳號狀態已更新") : ResponseEntity.notFound().build();
    }

    // 刪除帳號
    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> delete(@PathVariable("accountId") Long accountId) {
        boolean ok = adminService.deleteAccount(accountId);
        return ok ? ResponseEntity.ok("帳號已刪除") : ResponseEntity.notFound().build();
    }
}
