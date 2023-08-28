package ir.larxury.core.service.database.repository;

import ir.larxury.core.service.database.model.ShopPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopPlaceRepository extends JpaRepository<ShopPlace, Long> {
}
