package Food.controller.account;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Food.entity.account.User;
import Food.repository.account.UserRepository;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    // 忘記密碼：輸入email寄出重設連結
    @PostMapping("/forgot-password")
    public Map<String, Object> forgotPassword(HttpServletRequest request, @RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        String account = body.get("account");
        String frontendBaseUrl = body.get("baseUrl");

        if (account == null || account.isEmpty()) {
            response.put("success", false);
            response.put("message", "請輸入信箱");
            return response;
        }

        Optional<User> optionalUser = userRepository.findByAccount(account);
        if (optionalUser.isEmpty()) {
            response.put("success", false);
            response.put("message", "查無此信箱");
            return response;
        }

        // 產生 token 並更新資料庫
        User user = optionalUser.get();
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        userRepository.save(user);

        // 動態判斷前端網址
        String origin = request.getHeader("Origin"); // 例如 http://localhost:5174
        String baseUrl = frontendBaseUrl != null && !frontendBaseUrl.isEmpty()
                ? frontendBaseUrl
                : (origin != null ? origin : "http://localhost:5173"); // fallback 預設值

        // 組合重設連結
        String resetUrl = baseUrl + "/reset-password?token=" + token;

        // 寄信
        sendResetEmail(account, resetUrl);

        response.put("success", true);
        response.put("message", "重設密碼連結已寄至信箱");
        response.put("resetUrl", resetUrl);
        return response;
    }

    // 驗證 Token 是否有效
    @GetMapping("/validate-token")
    public Map<String, Object> validateToken(@RequestParam String token) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> optionalUser = userRepository.findByVerificationToken(token);

        if (optionalUser.isEmpty()) {
            response.put("success", false);
            response.put("message", "連結無效或已過期");
            return response;
        }

        response.put("success", true);
        response.put("message", "Token 有效");
        return response;
    }

    // 使用token重設密碼
    @PostMapping("/reset-password")
    public Map<String, Object> resetPassword(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        String token = request.get("token");
        String newPassword = request.get("newPassword");

        if (token == null || newPassword == null) {
            response.put("success", false);
            response.put("message", "請輸入完整資訊");
            return response;
        }

        Optional<User> optionalUser = userRepository.findByVerificationToken(token);
        if (optionalUser.isEmpty()) {
            response.put("success", false);
            response.put("message", "Token 無效");
            return response;
        }

        User user = optionalUser.get();
        // 密碼加密
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        userRepository.save(user);

        response.put("success", true);
        response.put("message", "密碼重設成功");
        return response;
    }

    // 發送重設密碼信件
    private void sendResetEmail(String to, String resetUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("重設密碼通知");
        message.setText("請點擊以下連結重設密碼（30 分鐘內有效）：\n" + resetUrl);
        mailSender.send(message);
    }
}
