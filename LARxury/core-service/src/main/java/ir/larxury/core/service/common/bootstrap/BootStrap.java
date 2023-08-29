package ir.larxury.core.service.common.bootstrap;

import ir.larxury.core.service.database.model.Category;
import ir.larxury.core.service.database.model.Shop;
import ir.larxury.core.service.database.model.ShopPlace;
import ir.larxury.core.service.database.model.enums.PlaceStatus;
import ir.larxury.core.service.database.repository.CategoryRepository;
import ir.larxury.core.service.database.repository.ShopPlaceRepository;
import ir.larxury.core.service.database.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BootStrap {

    @Autowired
    private ShopPlaceRepository shopPlaceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    @Transactional
    public void init(){
        var shopPlace = ShopPlace.builder()
                .status(PlaceStatus.UNKNOWN)
                .build();
        shopPlaceRepository.save(shopPlace);

        var category = Category.builder()
                .title("t")
                .build();
        categoryRepository.save(category);

    }

}
