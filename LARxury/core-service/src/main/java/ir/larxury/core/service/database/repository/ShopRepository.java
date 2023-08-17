package ir.larxury.core.service.database.repository;

import ir.larxury.core.service.database.model.Shop;
import ir.larxury.core.service.database.model.enums.ShopStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {

    Boolean existsByName(String name);

    List<Shop> findAllByShopStatus(ShopStatus status);
}
