package Food.repository.ad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Food.entity.ad.AD_Plan;

@Repository
public interface AD_PlanRepository extends JpaRepository<AD_Plan, Integer> {

}
