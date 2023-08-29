package ir.larxury.core.service.service.impl;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.core.service.common.aop.exception.CoreServiceException;
import ir.larxury.core.service.database.model.ShopPlace;
import ir.larxury.core.service.database.model.enums.PlaceStatus;
import ir.larxury.core.service.database.repository.ShopPlaceRepository;
import ir.larxury.core.service.service.ShopPlaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ShopPlaceServiceImpl implements ShopPlaceService {

    @Autowired
    private ShopPlaceRepository shopPlaceRepository;

    @Override
    public void save(ShopPlace shopPlace) {
        shopPlace.setStatus(PlaceStatus.KNOWN);
        shopPlaceRepository.save(shopPlace);
        log.info("save shop place with id {}", shopPlace.getId());
    }

    @Override
    public ShopPlace findById(Long id) {
        return shopPlaceRepository.findById(id).orElseThrow(() -> {
            log.error(ErrorCode.CORE_SERVICE_SHOP_PLACE_NOT_FOUND.getTechnicalMessage() + " with id {}", id);
            return new CoreServiceException(ErrorCode.CORE_SERVICE_SHOP_PLACE_NOT_FOUND);
        });
    }

    @Override
    public List<ShopPlace> findAll() {
        return shopPlaceRepository.findAll();
    }

    @Override
    public ShopPlace getUnknownPlace() {
        return shopPlaceRepository.findByStatus(PlaceStatus.UNKNOWN).orElseThrow(() -> {
            log.error(ErrorCode.CORE_SERVICE_SHOP_PLACE_UNKNOWN_ERROR.getTechnicalMessage());
            return new CoreServiceException(ErrorCode.CORE_SERVICE_SHOP_PLACE_UNKNOWN_ERROR);
        });
    }
}
