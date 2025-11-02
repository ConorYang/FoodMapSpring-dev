package Food.controller.lookHistory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Food.entity.member.Member;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/lookHistory")
public class LookHistoryController {

	@GetMapping("/lookHistory")
	public String lookHistoryPage(HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("member");
		if (member != null) {
			model.addAttribute("member", member);
		}
		return "lookHistory/lookHistory";
	}

}
