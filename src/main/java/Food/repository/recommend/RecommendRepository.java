package Food.repository.recommend;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import Food.entity.vendor.Vendor;

@Repository
public interface RecommendRepository extends JpaRepository<Vendor, Integer> {

    @Query(value = """
                SELECT TOP 8
                    v.VendorID,
                    v.VendorName,
                    v.Address,
                    v.LogoURL,
                    s.StyleName,

                    --收藏分數
                    CASE WHEN d.VD_StyleID IN (
                        SELECT DISTINCT d2.VD_StyleID
                        FROM Favorites f
                        JOIN VD_Details d2 ON f.VendorID = d2.VendorID
                        WHERE f.MemberID = :memberId
                    ) THEN 4 ELSE 0 END AS favoriteScore,

                    --瀏覽分數
                    CASE WHEN d.VD_StyleID IN (
                        SELECT DISTINCT d3.VD_StyleID
                        FROM MemberLookHistroies lh
                        JOIN VD_Details d3 ON lh.VendorID = d3.VendorID
                        WHERE lh.MemberID = :memberId
                    ) THEN 3 ELSE 0 END AS lookScore,

                    --搜尋分數
                    CASE WHEN d.VD_StyleID IN (
                        SELECT DISTINCT sh.StyleID
                        FROM SearchHistory sh
                        WHERE sh.MemberID = :memberId AND sh.StyleID IS NOT NULL
                    ) THEN 2 ELSE 0 END AS searchScore,

                    --居住城市分數
                    CASE WHEN v.Address LIKE CONCAT('%', (
                        SELECT City FROM Members WHERE MemberID = :memberId
                    ), '%') THEN 1 ELSE 0 END AS cityScore,

                    --總分
                    (
                        CASE WHEN d.VD_StyleID IN (
                            SELECT DISTINCT d2.VD_StyleID
                            FROM Favorites f
                            JOIN VD_Details d2 ON f.VendorID = d2.VendorID
                            WHERE f.MemberID = :memberId
                        ) THEN 4 ELSE 0 END
                        +
                        CASE WHEN d.VD_StyleID IN (
                            SELECT DISTINCT d3.VD_StyleID
                            FROM MemberLookHistroies lh
                            JOIN VD_Details d3 ON lh.VendorID = d3.VendorID
                            WHERE lh.MemberID = :memberId
                        ) THEN 3 ELSE 0 END
                        +
                        CASE WHEN d.VD_StyleID IN (
                            SELECT DISTINCT sh.StyleID
                            FROM SearchHistory sh
                            WHERE sh.MemberID = :memberId AND sh.StyleID IS NOT NULL
                        ) THEN 2 ELSE 0 END
                        +
                        CASE WHEN v.Address LIKE CONCAT('%', (
                            SELECT City FROM Members WHERE MemberID = :memberId
                        ), '%') THEN 1 ELSE 0 END
                    ) AS score

                FROM Vendors v
                JOIN VD_Details d ON v.VendorID = d.VendorID
                JOIN VD_Styles s ON d.VD_StyleID = s.VD_StyleID

                WHERE v.VendorID NOT IN (
                    SELECT VendorID FROM Favorites WHERE MemberID = :memberId
                )

                ORDER BY score DESC, NEWID();
            """, nativeQuery = true)
    List<Map<String, Object>> recommendWithScores(@Param("memberId") Integer memberId);
}
