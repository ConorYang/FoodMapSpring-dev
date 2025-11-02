package Food.repository.comments;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Food.dto.comment.CommentDTO;
import Food.entity.comments.CommentBean;

@Repository
public interface CommentRepository extends JpaRepository<CommentBean, Integer> {

    // 統計評價數量-超美味
    @Query(value = """
            select count(*) from (
                select distinct
                    m.UserName, v.VendorID, c.CommentID, c.OrderId,
                    c.Delicious, c.Quick, c.Friendly, c.Photogenic, c.TopCP,
                    c.CommentContext, c.Photo1, c.Photo2, c.Photo3
                from Orders o
                left join OrderDetails od on o.OrderId = od.OrderID
                left join Products p on od.ProductID = p.ProductID
                left join Vendors v on p.VendorID = v.VendorID
                left join Comments c on o.OrderId = c.OrderID
                left join Accounts a on a.AccountID = o.AccountId
                left join Members m on m.AccountID = a.AccountID
                where c.Delicious = 1 and v.VendorID = :vendorId
            ) as t
            """, nativeQuery = true)
    long countDeliciousComments(@Param("vendorId") Integer vendorId);

    // 統計評價數量-超友善
    @Query(value = """
            select count(*) from (
                select distinct
                    m.UserName, v.VendorID, c.CommentID, c.OrderId,
                    c.Delicious, c.Quick, c.Friendly, c.Photogenic, c.TopCP,
                    c.CommentContext, c.Photo1, c.Photo2, c.Photo3
                from Orders o
                left join OrderDetails od on o.OrderId = od.OrderID
                left join Products p on od.ProductID = p.ProductID
                left join Vendors v on p.VendorID = v.VendorID
                left join Comments c on o.OrderId = c.OrderID
                left join Accounts a on a.AccountID = o.AccountId
                left join Members m on m.AccountID = a.AccountID
                where c.Friendly = 1 and v.VendorID = :vendorId
            ) as t
            """, nativeQuery = true)

    long countFriendlyComments(@Param("vendorId") Integer vendorId);

    // 統計評價數量-超快速
    @Query(value = """
            select count(*) from (
                select distinct
                    m.UserName, v.VendorID, c.CommentID, c.OrderId,
                    c.Delicious, c.Quick, c.Friendly, c.Photogenic, c.TopCP,
                    c.CommentContext, c.Photo1, c.Photo2, c.Photo3
                from Orders o
                left join OrderDetails od on o.OrderId = od.OrderID
                left join Products p on od.ProductID = p.ProductID
                left join Vendors v on p.VendorID = v.VendorID
                left join Comments c on o.OrderId = c.OrderID
                left join Accounts a on a.AccountID = o.AccountId
                left join Members m on m.AccountID = a.AccountID
                where c.Quick = 1 and v.VendorID = :vendorId
            ) as t
            """, nativeQuery = true)
    long countQuickComments(@Param("vendorId") Integer vendorId);

    // 統計評價數量-超上相
    @Query(value = """
            select count(*) from (
                select distinct
                    m.UserName, v.VendorID, c.CommentID, c.OrderId,
                    c.Delicious, c.Quick, c.Friendly, c.Photogenic, c.TopCP,
                    c.CommentContext, c.Photo1, c.Photo2, c.Photo3
                from Orders o
                left join OrderDetails od on o.OrderId = od.OrderID
                left join Products p on od.ProductID = p.ProductID
                left join Vendors v on p.VendorID = v.VendorID
                left join Comments c on o.OrderId = c.OrderID
                left join Accounts a on a.AccountID = o.AccountId
                left join Members m on m.AccountID = a.AccountID
                where c.Photogenic = 1 and v.VendorID = :vendorId
            ) as t
            """, nativeQuery = true)
    long countPhotogenicComments(@Param("vendorId") Integer vendorId);

    // 統計評價數量-高CP值
    @Query(value = """
            select count(*) from (
                select distinct
                    m.UserName, v.VendorID, c.CommentID, c.OrderId,
                    c.Delicious, c.Quick, c.Friendly, c.Photogenic, c.TopCP,
                    c.CommentContext, c.Photo1, c.Photo2, c.Photo3
                from Orders o
                left join OrderDetails od on o.OrderId = od.OrderID
                left join Products p on od.ProductID = p.ProductID
                left join Vendors v on p.VendorID = v.VendorID
                left join Comments c on o.OrderId = c.OrderID
                left join Accounts a on a.AccountID = o.AccountId
                left join Members m on m.AccountID = a.AccountID
                where c.TopCP = 1 and v.VendorID = :vendorId
            ) as t
            """, nativeQuery = true)

    long countTopCPComments(@Param("vendorId") Integer vendorId);

    // 依OrderID取得評價，確認是否已評價過
    Optional<CommentBean> findByOrderId(Integer orderId);

    @Query(value = """
            select distinct
            m.UserName, v.VendorID, c.CommentID, c.OrderId,
            c.Delicious, c.Quick, c.Friendly, c.Photogenic, c.TopCP, c.CommentContext, c.Photo1, c.Photo2, c.Photo3
            from Orders o
            left join OrderDetails od on o.OrderId = od.OrderID
            left join Products p on od.ProductID = p.ProductID
            left join Vendors v on p.VendorID = v.VendorID
            left join Comments c on o.OrderId = c.OrderID
            left join Accounts a on a.AccountID = o.AccountId
            left join Members m on m.AccountID = a.AccountID
            where
                ( (c.CommentContext is not null and c.CommentContext <> '')
                  or (c.Photo1 is not null and c.Photo1 <> '')
                  or (c.Photo2 is not null and c.Photo2 <> '')
                  or (c.Photo3 is not null and c.Photo3 <> '') )
              and
                v.VendorID = :vendorId ORDER BY c.Photo1 desc;
                                    """, nativeQuery = true)

    List<CommentDTO> findByVendorId(Integer vendorId);

    // 會員後台顯示用
    @Query(value = """
                    select distinct m.MemberID, v.VendorName, c.CommentID, c.OrderId, c.Delicious, c.Quick, c.Friendly, c.Photogenic, c.TopCP, c.CommentContext,
            c.Photo1, c.Photo2, c.Photo3, c.CreateAt  from Comments c
            left join Orders o on c.OrderID = o.OrderId
            left join Members m on m.AccountID = o.AccountID
            left join OrderDetails od on od.OrderID = o.OrderId
            left join Products p on p.ProductID = od.ProductID
            left join Vendors v on v.VendorID = p.VendorID
            where m.MemberID = :memberId
            order by c.CommentID
            """, nativeQuery = true)
    List<CommentDTO> findByMemberID(Integer memberId);

     // 依廠商取得所有評論（店家後台專用 - 新增）
        @Query(value = """
            SELECT DISTINCT
                c.CommentID, 
                c.OrderId,
                c.Delicious, 
                c.Quick, 
                c.Friendly, 
                c.Photogenic, 
                c.TopCP, 
                c.CommentContext, 
                c.Photo1, 
                c.Photo2, 
                c.Photo3,
                c.CreateAt
            FROM Orders o
            LEFT JOIN OrderDetails od ON o.OrderId = od.OrderID
            LEFT JOIN Products p ON od.ProductID = p.ProductID
            LEFT JOIN Vendors v ON p.VendorID = v.VendorID
            LEFT JOIN Comments c ON o.OrderId = c.OrderID
            WHERE v.VendorID = :vendorId
                AND c.CommentID IS NOT NULL
            ORDER BY c.CreateAt DESC
            """, nativeQuery = true)
        List<CommentBean> findCommentsByVendorIdForBackend(@Param("vendorId") Integer vendorId);
}
