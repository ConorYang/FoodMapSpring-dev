package Food.controller.member;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import Food.entity.account.User;
import Food.entity.member.Member;
import Food.service.account.UserService;
import Food.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private Environment env; // 注入Environment

	// ---------------- 歡迎首頁用 ----------------
	@GetMapping("/memberHome")
	public String memberHome(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		Member member = memberService.getMemberByAccountId(user.getAccountId());

		if (member == null) {
			member = new Member();
			member.setUser(user);
			member.setAvatarURL(null);
		}

		model.addAttribute("user", user);
		model.addAttribute("member", member);

		return "member/memberHome";
	}


	
	// ---------------- 顯示個人資料編輯頁面 ----------------
	@GetMapping("/profile")
	public String memberProfile(HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("member");

		if (member == null) {
			return "redirect:/login";
		}

		model.addAttribute("member", member);
		return "member/profile";
	}

	@GetMapping("/setting")
	public String getSettingsForm(HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("member");

		if (member == null) {

			return "redirect:/member/memberHome";
		}

		model.addAttribute("member", member);
		// 回傳 fragment
		return "member/setting :: settingsForm";
	}

	// ---------------- 更新個人資料 ----------------
	@PostMapping("/setting/update")
	@ResponseBody
	public ResponseEntity<Map<String, String>> updateSettings(@ModelAttribute Member member,
			@RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile, HttpSession session,
			HttpServletRequest request) {

		Map<String, String> result = new HashMap<>();
		try {
			Member existing = (Member) session.getAttribute("member");
			if (existing == null) {
				result.put("status", "error");
				result.put("message", "會員未登入或找不到會員資料");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
			}
			// ---------------- 更新文字欄位 ----------------
			existing.setUserName(member.getUserName());
			existing.setPhone(member.getPhone());
			existing.setGender(member.getGender());
			existing.setBirthdate(member.getBirthdate());
			existing.setCity(member.getCity());
			existing.setDistrict(member.getDistrict());

			String newFilename = null;
			if (avatarFile != null && !avatarFile.isEmpty()) {
				String originalFilename = avatarFile.getOriginalFilename();
				String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
				newFilename = UUID.randomUUID().toString() + ext;

				String uploadDir = env.getProperty("upload.avatar.dir");
				File dir = new File(uploadDir);
				if (!dir.exists())
					dir.mkdirs();

				File dest = new File(dir, newFilename);
				avatarFile.transferTo(dest);

				existing.setAvatarURL(newFilename);
			}

			memberService.updateMember(existing);
			session.setAttribute("member", existing);

			result.put("status", "success");
			if (newFilename != null) {
				result.put("avatar", newFilename);
			}

			return ResponseEntity.ok(result);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("message", "伺服器錯誤");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	@PostMapping("/user/changePwd")
	@ResponseBody
	public Map<String, Object> changePwd(@RequestBody Map<String, String> req, HttpSession session) {
		Map<String, Object> response = new HashMap<>();

		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.put("success", false);
			response.put("message", "請先登入！");
			return response;
		}

		String password = req.get("password");
		String newPassword = req.get("newPassword");
		String confirmPassword = req.get("confirmPassword");

		if (!newPassword.equals(confirmPassword)) {
			response.put("success", false);
			response.put("message", "新密碼與確認密碼不一致");
			return response;
		}

		if (!password.equals(user.getPassword())) {
			response.put("success", false);
			response.put("message", "目前密碼錯誤");
			return response;
		}

		// 更新密碼 (這裡不加密)
		user.setPassword(newPassword);
		userService.updateUser(user);

		response.put("success", true);
		response.put("message", "密碼更新成功！");
		return response;
	}

}
