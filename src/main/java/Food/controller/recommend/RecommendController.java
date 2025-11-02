package Food.controller.recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import Food.entity.member.Member;
import Food.service.recommend.RecommendService;

@RestController
@RequestMapping("/api/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    @GetMapping("/personal")
    public List<Map<String, Object>> getPersonalRecommendations(HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            throw new RuntimeException("尚未登入或 Session 過期");
        }
        return recommendService.getRecommendations(member.getMemberId());
    }
}
