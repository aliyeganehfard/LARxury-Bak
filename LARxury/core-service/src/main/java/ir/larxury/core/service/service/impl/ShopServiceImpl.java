package ir.larxury.core.service.service.impl;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.service.UserSecurityService;
import ir.larxury.core.service.common.aop.exception.CoreServiceException;
import ir.larxury.core.service.database.model.Shop;
import ir.larxury.core.service.database.model.enums.ShopStatus;
import ir.larxury.core.service.database.repository.ShopRepository;
import ir.larxury.core.service.provider.AuthProviderImpl;
import ir.larxury.core.service.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private AuthProviderImpl authProvider;

    @Autowired
    private UserSecurityService userSecurityService;

    @Override
    public void saveNewShop(Shop shop) {

        if (existByName(shop.getName())){
            log.error(ErrorCode.CORE_SERVICE_DUPLICATE_SHOP_NAME.getTechnicalMessage() + " shop name = {}" , shop.getName());
            throw new CoreServiceException(ErrorCode.CORE_SERVICE_DUPLICATE_SHOP_NAME);
        }

        String currentUser = userSecurityService.getCurrentUser();
        var userId = authProvider.getUserId(currentUser);
        shop.setUserId(userId);
        shop.setShopStatus(ShopStatus.AWAITING_CONFIRMATION);
        shopRepository.save(shop);
        log.info("save shop with id {}", shop.getId());
    }

    private Boolean existByName(String shopName){
        return shopRepository.existsByName(shopName);
    }
}
