package ir.larxury.core.service.database.repository;

import ir.larxury.core.service.database.model.SearchCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchCategoryRepository extends JpaRepository<SearchCategory, Long> {
}
