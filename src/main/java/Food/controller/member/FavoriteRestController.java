package Food.controller.member;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import Food.dto.member.FavoriteDTO;
import Food.entity.member.Favorite;
import Food.entity.member.Member;
import Food.entity.vendor.Vendor;
import Food.repository.vendor.VendorRepository;
import Food.service.member.FavoriteService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteRestController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private VendorRepository vendorRepository;

    // 取得登入會員的收藏列表
    @GetMapping("/my")
    public List<FavoriteDTO> getMyFavorites(HttpSession session, @RequestParam(required = false) String name) {
        // 從 session 取得 Member
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "尚未登入");
        }

        int memberID = member.getMemberId();

        // 取得該會員收藏
        List<Favorite> favorites = favoriteService.getFavoritesByMemberId(memberID);

        // 將 Favorite 轉成 FavoriteDTO，查 VendorName
        return favorites.stream().map(fav -> {
            Vendor vendor = vendorRepository.findById(fav.getVendorID()).orElse(null);

            String vendorName = vendor != null ? vendor.getVendorName() : "未知店家";
            String logoUrl = vendor != null ? vendor.getLogoURL() : "default.jpg";
            String styleName = (vendor != null && vendor.getDetails() != null && vendor.getDetails().getStyle() != null)
                    ? vendor.getDetails().getStyle().getStyleName()
                    : "無分類";

            return new FavoriteDTO(
                    fav.getFavoriteID(),
                    vendorName,
                    logoUrl,
                    styleName,
                    fav.getVendorID());
        }).toList();
    }

    @PostMapping("/my")
    public Object addFavorite(@RequestBody Map<String, Integer> data, HttpSession session) {
        int vendorID = data.get("vendorID");
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "尚未登入");
        }

        int memberID = member.getMemberId();
        try {
            Favorite favorite = favoriteService.addFavorite(memberID, vendorID);

            return Map.of(
                    "success", true,
                    "message", "收藏成功",
                    "favoriteID", favorite.getFavoriteID());

        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/my/favoriteid/{favoriteID}")
    public void removeFavoriteByFavoriteID(@PathVariable Integer favoriteID, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "尚未登入");
        }

        System.out.println("刪除收藏 favoriteID = " + favoriteID);
        favoriteService.removeFavorite(favoriteID, member.getMemberId());
    }

    @DeleteMapping("/my/vendorid/{vendorID}")
    public void removeFavorite(@PathVariable Integer vendorID, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "尚未登入");
        }

        favoriteService.removeFavoriteByVendor(member.getMemberId(), vendorID);
    }
}
