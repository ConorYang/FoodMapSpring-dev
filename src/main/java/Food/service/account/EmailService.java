package Food.service.account;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private final JavaMailSender mailSender;

	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	// 通用寄信方法
	public void sendEmail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		mailSender.send(message);
	}

	// 非同步發送方法
	@Async
	public void sendEmailAsync(String to, String subject, String text) {
		System.out.println("[非同步] 開始發送 Email 給: " + to + " (執行緒: " + Thread.currentThread().getName() + ")");

		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);
			mailSender.send(message);

			System.out.println("[非同步] Email 發送成功: " + to);

		} catch (Exception e) {
			System.err.println("[非同步] Email 發送失敗: " + to);
			e.printStackTrace();
		}
	}

	// 發送驗證信
	public void sendVerificationEmail(String to, String token) {
		// String verifyUrl = "localhost:8080/foodjap/verify?token=" + token;
		String verifyUrl = "http://localhost:5173/verify?token=" + token;

		String subject = "會員驗證信";
		String text = "您好,請點擊以下連結完成註冊驗證:\n" + verifyUrl;

		// 呼叫通用寄信方法
		sendEmail(to, subject, text);
	}
}
