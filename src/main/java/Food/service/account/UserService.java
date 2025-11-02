package Food.service.account;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Food.entity.account.User;
import Food.entity.account.UserType;
import Food.entity.member.Member;
import Food.entity.vendor.Vendor;
import Food.repository.account.UserRepository;
import Food.repository.account.UserTypeRepository;
import Food.repository.member.MemberRepository;
import Food.repository.vendor.VendorRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserTypeRepository userTypeRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private VendorRepository vendorRepository;

	@Autowired
	private EmailService emailService;

	@Value("${frontend.url}")
	private String frontendUrl;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// -------------------- 註冊 --------------------
	@Transactional
	public User register(String account, String password, UserType role) {
		// 檢查帳號是否存在
		if (userRepository.findByAccount(account).isPresent()) {
			throw new RuntimeException("帳號已存在");
		}

		// 密碼加密
		String encryptedPassword = passwordEncoder.encode(password);

		// 建立 User
		User user = new User();
		user.setAccount(account);
		user.setPassword(encryptedPassword);
		user.setUserType(role);
		user.setIsVerified(false);
		user.setStatus(1);
		user.setVerificationToken(UUID.randomUUID().toString());
		user.setCreatedAt(LocalDateTime.now());

		User savedUser = userRepository.save(user);

		// 建立對應的會員/店家資料
		Long roleId = role.getId();
		if (roleId == 2L) {
			Member member = new Member();
			member.setUserName(account);
			member.setUser(savedUser);
			member.setCity("未知");
			member.setDistrict("未知");
			member.setBirthdate(LocalDate.of(2000, 1, 1));
			memberRepository.save(member);
		} else if (roleId == 3L) {
			Vendor vendor = new Vendor();
			vendor.setVendorName(savedUser.getAccount());
			vendor.setContactEmail(savedUser.getAccount());
			vendor.setAddress("待填寫");
			vendor.setOwnerName("待填寫");
			vendor.setContactName("待填寫");
			vendor.setContactTitle("待填寫");
			vendor.setContactTel("0000-0000");
			vendor.setVdCategoryID(1);
			vendor.setVdStatus("審核中");
			vendor.setLogoURL(null);
			vendor.setUser(savedUser);
			vendorRepository.save(vendor);
		}

		// 寄送驗證信
		String verifyUrl = frontendUrl + "/verify?token=" + user.getVerificationToken();
		String subject = "請驗證您的帳號";
		String content = "您好，請點擊以下連結完成帳號驗證：\n" + verifyUrl;
		emailService.sendEmail(savedUser.getAccount(), subject, content);

		return savedUser;
	}

	// -------------------- 登入 --------------------
	public User login(String account, String password) {
		User user = userRepository.findByAccount(account)
				.orElseThrow(() -> new RuntimeException("帳號不存在"));

		String dbPassword = user.getPassword();

		boolean passwordMatches = false;

		if (passwordEncoder.matches(password, dbPassword)) {
			passwordMatches = true;
		} else if (password.equals(dbPassword)) {
			
			user.setPassword(passwordEncoder.encode(password));
			userRepository.save(user);
			passwordMatches = true;
			System.out.println("自動升級明碼密碼 -> 已加密儲存");
		}

		if (!passwordMatches) {
			throw new RuntimeException("密碼錯誤");
		}

		// ✅ 驗證信箱
		if (!Boolean.TRUE.equals(user.getIsVerified())) {
			throw new RuntimeException("帳號尚未驗證，請先完成信箱驗證");
		}

		// ✅ 停用檢查
		if (user.getStatus() == 2) {
			throw new RuntimeException("此帳號已被停用，請聯繫管理員。");
		}

		user.setLastLogIn(LocalDateTime.now());
		userRepository.saveAndFlush(user);

		System.out.println("登入時間已更新: " + user.getLastLogIn());
		return user;
	}

	// -------------------- 驗證帳號 --------------------
	@Transactional
	public boolean verifyAccount(String token) {
		if (token == null || token.trim().isEmpty()) {
			return false;
		}

		Optional<User> opt = userRepository.findByVerificationToken(token.trim());
		if (opt.isEmpty()) {
			return false;
		}

		User user = opt.get();
		user.setIsVerified(true);
		user.setStatus(1);
		user.setLastLogIn(LocalDateTime.now());
		userRepository.save(user);

		System.out.println("使用者驗證成功: " + user.getAccount());
		return true;
	}

	// -------------------- Google 登入 --------------------
	@Transactional
	public User loginWithGoogle(String account, String name) {
		account = account.trim();

		Optional<User> opt = userRepository.findByAccount(account);
		if (opt.isPresent()) {
			User user = opt.get();
			user.setLastLogIn(LocalDateTime.now());
			return userRepository.save(user);
		} else {
			User newUser = new User();
			newUser.setAccount(account);
			newUser.setPassword(passwordEncoder.encode("GOOGLE_LOGIN"));
			newUser.setIsVerified(true);
			newUser.setCreatedAt(LocalDateTime.now());
			newUser.setLastLogIn(LocalDateTime.now());
			newUser.setStatus(1);

			UserType memberRole = userTypeRepository.findById(2L)
					.orElseThrow(() -> new RuntimeException("找不到會員角色 (roleId=2)"));
			newUser.setUserType(memberRole);

			User savedUser = userRepository.save(newUser);

			Member member = new Member();
			member.setUserName(account);
			member.setUser(savedUser);
			member.setCity("未知");
			member.setDistrict("未知");
			member.setBirthdate(LocalDate.of(2000, 1, 1));
			memberRepository.save(member);

			return savedUser;
		}
	}

	// -------------------- 密碼驗證 --------------------
	public boolean checkPassword(String rawPassword, String hashedPassword) {
		return passwordEncoder.matches(rawPassword, hashedPassword);
	}

	// -------------------- 更新使用者 --------------------
	@Transactional
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	// -------------------- 查詢帳號 --------------------
	public User findByAccount(String account) {
		return userRepository.findByAccount(account).orElse(null);
	}
}
