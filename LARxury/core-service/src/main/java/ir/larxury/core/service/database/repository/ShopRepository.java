package ir.larxury.core.service.database.repository;

import ir.larxury.core.service.database.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {

    Boolean existsByName(String name);
}
