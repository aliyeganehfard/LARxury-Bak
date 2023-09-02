package ir.larxury.core.service.database.repository;

import ir.larxury.core.service.database.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT coomment FROM Comment coomment " +
            " WHERE coomment.id = :commentId AND " +
            " coomment.product.shop.userId = :shopOwnerId")
    Optional<Comment> findCommentForPostReply(@Param("commentId") Long commentId, @Param("shopOwnerId") String shopOwnerId);

    @Query("SELECT comment FROM Comment comment " +
            " WHERE comment.answer IS NULL AND " +
            " comment.product.shop.userId = :shopOwnerId")
    List<Comment> findUnansweredComment(@Param("shopOwnerId") String shopOwnerId);


    // todo . encounter to error when a user have two shop
    @Query("SELECT comment FROM Comment comment " +
            " WHERE comment.product.shop.userId = :shopOwnerId")
    List<Comment> findShopCommentsByShopOwnerId(@Param("shopOwnerId") String shopOwnerId);

    List<Comment> findAllByProductId(Long productId);
}
