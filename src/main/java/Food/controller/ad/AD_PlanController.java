package Food.controller.ad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Food.entity.ad.AD_Plan;
import Food.service.ad.AD_PlanService;

@RestController
@RequestMapping("/ad")
public class AD_PlanController {

    @Autowired
    private AD_PlanService adPlanService;

    @GetMapping("/")
    public List<AD_Plan> findAll() {
        return adPlanService.findAll();
    }
}
