package ir.larxury.core.service.database.repository;

import ir.larxury.core.service.database.model.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductViewRepository extends JpaRepository<ProductView, Long> {

    @Query("SELECT COUNT(view) FROM ProductView view WHERE view.product.id = :productId")
    Long getProductViews(@Param("productId") Long productId);
}
