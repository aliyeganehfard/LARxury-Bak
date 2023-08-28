package ir.larxury.core.service.database.repository;

import ir.larxury.core.service.database.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("FROM Category cat WHERE cat.parentCategory IS Null AND cat.id IN :ids")
    List<Category> findRootCategoryByIds(@Param("ids") List<Long> ids);
}
