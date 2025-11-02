package Food.repository.search;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Food.entity.vendor.Vendor;

@Repository
public interface VendorsRepository extends JpaRepository<Vendor, Integer> {
	@Query("SELECT v FROM Vendor v ORDER BY v.vendorId ASC")
	List<Vendor> findTop3(Pageable pageable);

	@Query(value = """
			SELECT DISTINCT
			    v.VendorID AS vendorId,
			    v.VendorName AS vendorName,
			    v.LogoURL AS logoUrl,
			    v.Address AS address,
			    v.ContactTel AS contactTel,
			    s.StyleName AS styleName,
			    CONVERT(varchar(5),
			        MIN(
			            CASE
			                WHEN o.OpeningTime IS NOT NULL AND o.ClosingTime IS NOT NULL
			                THEN o.OpeningTime
			            END
			        ), 108
			    ) AS openingTime,
			    CONVERT(varchar(5),
			        MAX(
			            CASE
			                WHEN o.OpeningTime IS NOT NULL AND o.ClosingTime IS NOT NULL
			                THEN o.ClosingTime
			            END
			        ), 108
			    ) AS closingTime,
			    STRING_AGG(
			        CASE
			            WHEN o.OpeningTime IS NULL OR o.ClosingTime IS NULL
			            THEN DATENAME(WEEKDAY, DATEADD(DAY, o.DayOfWeek - 1, '1900-01-01'))
			        END, ', '
			    ) AS closedDays
			FROM Vendors v
			LEFT JOIN VD_Details d ON v.VendorID = d.VendorID
			LEFT JOIN VD_Styles s ON d.VD_StyleID = s.VD_StyleID
			LEFT JOIN VD_OpeningHours o ON v.VendorID = o.VendorID
			LEFT JOIN VD_Categories c ON v.VD_CategoryID = c.VD_CategoryID
			WHERE
			    (
			        :keyword IS NULL
			        OR v.VendorName LIKE CONCAT('%', :keyword, '%')
			        OR s.StyleName LIKE CONCAT('%', :keyword, '%')
			        OR s.Description LIKE CONCAT('%', :keyword, '%')
			        OR c.CategoryName LIKE CONCAT('%', :keyword, '%')
			        OR c.Description LIKE CONCAT('%', :keyword, '%')
			    )
			    AND (:styleId IS NULL OR d.VD_StyleID = :styleId)
			    AND (:city IS NULL OR v.Address LIKE CONCAT('%', :city, '%'))
			    AND (
			        (:userMinPrice IS NULL OR d.PriceMax >= :userMinPrice)
			        AND (:userMaxPrice IS NULL OR d.PriceMin <= :userMaxPrice)
			    )
			    AND (:airConditioner IS NULL OR d.AirConditioner = :airConditioner)
			    AND (:veganFriendly IS NULL OR d.VeganFriendly = :veganFriendly)
			    AND (:petFriendly IS NULL OR d.PetFriendly = :petFriendly)
			    AND (
			        :slotStart IS NULL OR EXISTS (
			            SELECT 1
			            FROM VD_OpeningHours oh
			            WHERE oh.VendorID = v.VendorID
			              AND (:dayOfWeek IS NULL OR oh.DayOfWeek = :dayOfWeek)
			              AND oh.OpeningTime <= CAST(:slotEnd AS time)
			              AND oh.ClosingTime >= CAST(:slotStart AS time)
			        )
			    )
			GROUP BY
			    v.VendorID, v.VendorName, v.LogoURL, v.Address, v.ContactTel, s.StyleName ,c.CategoryName ORDER BY v.VendorID ASC
			""", nativeQuery = true)

	List<Object[]> advancedSearchRaw(
			@Param("keyword") String keyword,
			@Param("styleId") Integer styleId,
			@Param("city") String city,
			@Param("userMinPrice") Integer userMinPrice,
			@Param("userMaxPrice") Integer userMaxPrice,
			@Param("slotStart") LocalTime slotStart,
			@Param("slotEnd") LocalTime slotEnd,
			@Param("airConditioner") Boolean airConditioner,
			@Param("veganFriendly") Boolean veganFriendly,
			@Param("petFriendly") Boolean petFriendly,
			@Param("dayOfWeek") Integer dayOfWeek);

	// 熱門排行榜（前3名）
	@Query(value = """
			SELECT TOP 3
			    v.VendorID AS vendorId,
			    v.VendorName AS vendorName,
			    v.LogoURL AS logoUrl,
			    v.Address AS address,
			    v.ContactTel AS contactTel,
			    ISNULL(f.FavCount, 0) AS favoriteCount,
			    ISNULL(l.ViewCount, 0) AS lookCount,
			    (ISNULL(f.FavCount, 0) * 2 + ISNULL(l.ViewCount, 0)) AS popularScore
			FROM Vendors v
			LEFT JOIN (
			    SELECT VendorID, COUNT(*) AS FavCount
			    FROM Favorites
			    GROUP BY VendorID
			) f ON v.VendorID = f.VendorID
			LEFT JOIN (
			    SELECT VendorID, COUNT(*) AS ViewCount
			    FROM MemberLookHistroies
			    GROUP BY VendorID
			) l ON v.VendorID = l.VendorID
			ORDER BY popularScore DESC
			""", nativeQuery = true)
	List<Object[]> findTop3PopularVendors();

}
