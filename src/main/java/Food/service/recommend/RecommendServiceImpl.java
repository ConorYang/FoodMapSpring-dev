package Food.service.recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import Food.repository.recommend.RecommendRepository;

@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private RecommendRepository recommendRepository;

    @Override
    public List<Map<String, Object>> getRecommendations(Integer memberId) {
        return recommendRepository.recommendWithScores(memberId);
    }
}
