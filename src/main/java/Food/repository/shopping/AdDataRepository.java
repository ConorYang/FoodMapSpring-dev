package Food.repository.shopping;

import org.springframework.data.jpa.repository.JpaRepository;

import Food.entity.shopping.AdData;

public interface AdDataRepository extends JpaRepository<AdData, Integer> {
	
	
}
