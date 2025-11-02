package Food;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

//擴大掃描範圍，包含 reservation
@SpringBootApplication(scanBasePackages = "Food")
@EnableAsync
@EnableJpaRepositories(basePackages = {
		"Food.repository.account",
		"Food.repository.admin",
		"Food.repository.member",
		"Food.repository.shopping",
		"Food.repository.vendor",
		"Food.repository.reservation",
		"Food.repository.comments",
		"Food.repository.cuSer",
		"Food.repository.blackList",
		"Food.repository.lookHistory",
		"Food.repository.search",
		"Food.repository.ad",
		"Food.repository.recommend",
})
@EntityScan(basePackages = {
		"Food.entity.account",
		"Food.entity.admin",
		"Food.entity.member",
		"Food.entity.shopping",
		"Food.entity.vendor",
		"Food.entity.reservation",
		"Food.entity.comments",
		"Food.entity.cuSer",
		"Food.entity.blackList",
		"Food.entity.lookHistory",
		"Food.entity.recommend",
		"Food.entity.ad"
})
public class UserApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
}