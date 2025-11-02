package Food.repository.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Food.entity.member.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
	List<Favorite> findByMemberID(int memberID);

	Optional<Favorite> findByFavoriteIDAndMemberID(int favoriteID, int memberID);

	Optional<Favorite> findByMemberIDAndVendorID(int memberID, int vendorID);
}
