package ir.larxury.core.service.service;

import ir.larxury.core.service.database.model.ShopPlace;

import java.util.List;

public interface ShopPlaceService {

    void save(ShopPlace shopPlace);

    ShopPlace findById(Long id);

    List<ShopPlace> findAll();

    ShopPlace getUnknownPlace();
}
