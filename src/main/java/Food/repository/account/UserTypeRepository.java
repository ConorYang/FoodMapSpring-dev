package Food.repository.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Food.entity.account.UserType;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {

    // 使用屬性名稱id
    Optional<UserType> findById(Long id);

}
