package Food.repository.search;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Food.entity.vendor.VD_Styles;

@Repository
public interface StyleRepository extends JpaRepository<VD_Styles, Integer> {

    // 依風格名稱找 Style
    VD_Styles findByStyleName(String styleName);
}
