package ir.larxury.core.service.database.repository;

import ir.larxury.core.service.database.model.ShopPlace;
import ir.larxury.core.service.database.model.enums.PlaceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopPlaceRepository extends JpaRepository<ShopPlace, Long> {

    Optional<ShopPlace> findByStatus(PlaceStatus placeStatus);
}
