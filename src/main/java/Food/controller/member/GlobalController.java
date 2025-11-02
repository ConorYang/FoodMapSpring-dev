package Food.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import Food.entity.account.User;
import Food.entity.member.Member;
import Food.service.member.MemberService;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private MemberService memberService;

    // 每個頁面都會有 user
    @ModelAttribute("user")
    public User populateUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    // 每個頁面都會有 member
    @ModelAttribute("member")
    public Member populateMember(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return memberService.getMemberByAccountId(user.getAccountId());
        }
        return null;
    }
    
    
    
}
