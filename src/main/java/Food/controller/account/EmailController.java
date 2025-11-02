package Food.controller.account;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Food.service.account.UserService;
@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    private UserService userService;

    
    @GetMapping("/verify")
    public Map<String, Object> verifyAccount(@RequestParam("token") String token) {
        boolean success = userService.verifyAccount(token);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "驗證成功！您現在可以登入帳號。" 
                                      : "驗證失敗：token 無效或已過期。");
        return result;
    }
}
