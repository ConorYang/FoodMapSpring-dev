package Food.service.recommend;

import java.util.List;
import java.util.Map;

public interface RecommendService {
    List<Map<String, Object>> getRecommendations(Integer memberId);
}
