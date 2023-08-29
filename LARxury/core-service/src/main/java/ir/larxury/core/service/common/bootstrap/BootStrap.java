package ir.larxury.core.service.common.bootstrap;

import ir.larxury.core.service.database.repository.ShopPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BootStrap {

    @Autowired
    private ShopPlaceRepository shopPlaceRepository;

    @Autowired
    public void init(){
//        var shopPlace = ShopPlace.builder()
//                .status(PlaceStatus.UNKNOWN)
//                .build();
//        shopPlaceRepository.save(shopPlace);
    }
}
