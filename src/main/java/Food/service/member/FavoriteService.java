package Food.service.member;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Food.entity.member.Favorite;
import Food.repository.member.FavoriteRepository;

@Service
public class FavoriteService {

	@Autowired
	private FavoriteRepository favoriteRepository;

	public List<Favorite> getFavoritesByMemberId(int memberID) {
		return favoriteRepository.findByMemberID(memberID);
	}

	public Favorite addFavorite(int memberID, int vendorID) {

		Optional<Favorite> existing = favoriteRepository.findByMemberIDAndVendorID(memberID, vendorID);
		if (existing.isPresent()) {
			throw new RuntimeException("已收藏過此店家");
		}
		Favorite favorite = new Favorite();
		favorite.setMemberID(memberID);
		favorite.setVendorID(vendorID);
		return favoriteRepository.save(favorite);
	}

	public void removeFavorite(int favoriteID, int memberID) {
		Favorite fav = favoriteRepository.findByFavoriteIDAndMemberID(favoriteID, memberID)
				.orElseThrow(() -> new RuntimeException("收藏不存在或非本人"));
		favoriteRepository.delete(fav);
	}

	public void removeFavoriteByVendor(int memberID, int vendorID) {
		Favorite fav = favoriteRepository.findByMemberIDAndVendorID(memberID, vendorID)
				.orElseThrow(() -> new RuntimeException("收藏不存在或非本人"));
		favoriteRepository.delete(fav);
	}
}
