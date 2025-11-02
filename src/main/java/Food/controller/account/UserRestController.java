package Food.controller.account;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import Food.entity.account.User;
import Food.entity.account.UserType;
import Food.entity.blackList.Blacklist;
import Food.entity.member.Member;
import Food.entity.vendor.Vendor;
import Food.repository.account.UserTypeRepository;
import Food.repository.blackList.BlacklistRepository;
import Food.service.account.UserService;
import Food.service.member.MemberService;
import Food.service.vendor.VendorService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class UserRestController {

	@Autowired
	private UserService userService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private UserTypeRepository userTypeRepository;

	@Autowired
	private BlacklistRepository blacklistRepository;

	// -------------------註冊-------------------

	@PostMapping("/register")
	public Map<String, Object> register(@RequestBody Map<String, String> req) {
		try {
			String account = req.get("account");
			String password = req.get("password");
			Long roleId = Long.parseLong(req.get("roleId"));

			// 取得 UserType
			UserType selectedRole = userTypeRepository.findById(roleId)
					.orElseThrow(() -> new IllegalArgumentException("無效的角色ID: " + roleId));

			// 呼叫 Service 註冊，Service 內會加密密碼
			User user = userService.register(account, password, selectedRole);

			return Map.of("success", true, "message", "註冊成功!請前往信箱完成驗證後才能登入",
					"userId", user.getAccountId());

		} catch (RuntimeException e) {
			return Map.of("success", false, "message", e.getMessage());
		}
	}

	// -------------------登入-------------------

	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody Map<String, String> req, HttpSession session) {
		try {
			String account = req.get("account");
			String rawPassword = req.get("password");

			// ✅ 呼叫 Service 登入，會自動驗證密碼、狀態、Email，並更新 LastLogIn
			User user = userService.login(account, rawPassword);

			// 設定 Session
			session.setAttribute("user", user);
			if (user.getUserType().getId() == 2L && user.getMember() != null) {
				session.setAttribute("member", user.getMember());
			}
			if (user.getUserType().getId() == 3L && user.getVendor() != null) {
				session.setAttribute("vendor", user.getVendor());
			}

			// 回傳成功資料
			Map<String, Object> data = new HashMap<>();
			data.put("success", true);
			data.put("message", "登入成功");
			data.put("userId", user.getAccountId());
			data.put("roleId", user.getUserType().getId());
			data.put("userName", user.getAccount());

			if (user.getUserType().getId() == 2L && user.getMember() != null) {
				data.put("memberId", user.getMember().getMemberId());
				data.put("memberUserName", user.getMember().getUserName());
			}

			if (user.getUserType().getId() == 3L && user.getVendor() != null) {
				data.put("vendorId", user.getVendor().getVendorId());
				data.put("vendorUserName", user.getVendor().getVendorName());
			}

			return data;

		} catch (RuntimeException e) {
			return Map.of("success", false, "message", e.getMessage());
		}
	}

	// -------------------Refresh 可保持登入-------------------
	@GetMapping("/currentUser")
	@ResponseBody
	public Map<String, Object> currentUser(HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user == null) {
			return Map.of("user", null);
		}

		Member member = memberService.getMemberByAccountId(user.getAccountId());
		Vendor vendor = vendorService.getVendorByAccountId(user.getAccountId());

		Map<String, Object> res = new HashMap<>();
		Map<String, Object> userInfo = new HashMap<>();

		userInfo.put("userId", user.getAccountId());
		userInfo.put("email", user.getAccount());
		userInfo.put("roleId", user.getUserType() != null ? user.getUserType().getId() : null);
		userInfo.put("roleType", user.getUserType() != null ? user.getUserType().getType() : null);
		userInfo.put("roleDescription", user.getUserType() != null ? user.getUserType().getDescription() : null);
		userInfo.put("status", user.getStatus());
		userInfo.put("memberPhone", member != null ? member.getPhone() : null);
		userInfo.put("contactTel", vendor != null ? vendor.getContactTel() : null);
		userInfo.put("vendorName", vendor != null ? vendor.getVendorName() : null);

		// 新增黑名單原因
		if (member != null) {
			userInfo.put("memberId", member.getMemberId());
			userInfo.put("memberUserName", member.getUserName());

			// 這邊拿黑名單資料
			Blacklist blacklist = blacklistRepository.findByMemberId(member.getMemberId().longValue()).orElse(null);
			userInfo.put("blacklistReason", blacklist != null ? blacklist.getReason() : null);
		}

		res.put("user", userInfo);
		return res;
	}

	@GetMapping("/")
	public String index(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		boolean isAdmin = user != null && user.getUserType() != null && user.getUserType().getId() == 1;
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("member", session.getAttribute("member"));
		return "index";
	}

	@PostMapping("/logout")
	public Map<String, Object> logout(HttpSession session) {
		session.invalidate();
		return Map.of("success", true, "message", "已登出");
	}

	// ------------------Google登入--------------------
	@PostMapping("/google-login")
	public Map<String, Object> googleLogin(@RequestBody Map<String, String> req, HttpSession session) {
		String token = req.get("token");
		String roleIdStr = req.get("roleId"); // 前端傳角色 2 or 3
		if (token == null || roleIdStr == null) {
			return Map.of("success", false, "message", "缺少 Google token 或角色資訊");
		}

		try {
			final String GOOGLE_CLIENT_ID = "155562336859-rq8homtfrs5qf9bpr1dljodf9m8o1ka4.apps.googleusercontent.com";

			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
					GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance())
					.setAudience(Collections.singletonList(GOOGLE_CLIENT_ID)).build();

			GoogleIdToken idToken = verifier.verify(token);
			if (idToken == null) {
				return Map.of("success", false, "message", "無效的 Google token");
			}

			GoogleIdToken.Payload payload = idToken.getPayload();
			String email = payload.getEmail();
			String name = (String) payload.get("name");

			UserType role = userTypeRepository.findById(Long.parseLong(roleIdStr))
					.orElseThrow(() -> new RuntimeException("無效角色"));

			// 查User 是否存在
			User user = userService.loginWithGoogle(email, name); // <-- 舊版邏輯統一

			// 會員角色
			if (user.getUserType().getId() == 2L) {
				Member member = memberService.getMemberByAccountId(user.getAccountId());
				if (member == null) {
					member = memberService.createMemberForUser(user);
				}
				session.setAttribute("member", member);
			}

			// Vendor 角色
			if (user.getUserType().getId() == 3L && user.getVendor() != null) {
				session.setAttribute("vendor", user.getVendor());
			}

			session.setAttribute("user", user);
			if (user.getUserType().getId() == 3L && user.getVendor() != null) {
				session.setAttribute("vendor", user.getVendor());
			}

			// 回傳資料
			Map<String, Object> data = new HashMap<>();
			data.put("success", true);
			data.put("message", "登入成功");
			data.put("userId", user.getAccountId());
			data.put("roleId", user.getUserType().getId());
			data.put("userName", user.getAccount());

			if (user.getUserType().getId() == 2L) {
				Member member = (Member) session.getAttribute("member");
				data.put("memberId", member.getMemberId());
				data.put("memberUserName", member.getUserName());
			}

			if (user.getUserType().getId() == 3L && user.getVendor() != null) {
				data.put("vendorId", user.getVendor().getVendorId());
				data.put("vendorUserName", user.getVendor().getVendorName());
			}

			return data;

		} catch (Exception e) {
			e.printStackTrace();
			return Map.of("success", false, "message", "登入失敗：" + e.getMessage());
		}
	}

}
