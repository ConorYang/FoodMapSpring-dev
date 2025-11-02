package Food.service.ad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Food.entity.ad.AD_Plan;
import Food.repository.ad.AD_PlanRepository;

@Service
public class AD_PlanService {

    @Autowired
    private AD_PlanRepository adPlanRepository;

    public List<AD_Plan> findAll() {
        return adPlanRepository.findAll();
    }
}