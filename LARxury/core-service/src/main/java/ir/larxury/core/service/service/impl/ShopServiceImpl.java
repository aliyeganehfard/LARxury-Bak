package ir.larxury.core.service.service.impl;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.service.JWTVerificationService;
import ir.larxury.core.service.common.aop.exception.CoreServiceException;
import ir.larxury.core.service.database.model.Category;
import ir.larxury.core.service.database.model.Shop;
import ir.larxury.core.service.database.model.ShopPlace;
import ir.larxury.core.service.database.model.enums.ShopStatus;
import ir.larxury.core.service.database.repository.ShopRepository;
import ir.larxury.core.service.provider.AsyncEngine;
import ir.larxury.core.service.provider.AuthServiceProvider;
import ir.larxury.core.service.service.CategoryService;
import ir.larxury.core.service.service.ShopPlaceService;
import ir.larxury.core.service.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private JWTVerificationService jwtVerificationService;

    @Autowired
    private AuthServiceProvider authServiceProvider;

    @Autowired
    private AsyncEngine asyncEngine;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ShopPlaceService shopPlaceService;

    @Override
    @Transactional
    public void saveNewShop(Shop shop, String token) {

        if (existByName(shop.getName())) {
            log.error(ErrorCode.CORE_SERVICE_DUPLICATE_SHOP_NAME.getTechnicalMessage() + " shop name = {}", shop.getName());
            throw new CoreServiceException(ErrorCode.CORE_SERVICE_DUPLICATE_SHOP_NAME);
        }

        var userId = jwtVerificationService.getUuid(token);
        shop.setUserId(userId);
        shop.setShopStatus(ShopStatus.AWAITING_CONFIRMATION);

        var categories =
                categoryService.findRootCategoryByIds(
                        shop.getCategories()
                                .stream()
                                .map(Category::getId)
                                .collect(Collectors.toList())
                );

        shop.setCategories(categories);
        shop.setPlace(getShopPlace(shop));

        shopRepository.save(shop);

        asyncEngine.sendEmailInstantDeliveryMessage(
                userId,
                "LARxury",
                "اطلاعات فروشگاه شما با موفقیت ثبت شد"
        );

        log.info("save shop with id {}", shop.getId());
    }

    private ShopPlace getShopPlace(Shop shop) {
        ShopPlace shopPlace;
        if (shop.getPlace() == null)
            shopPlace = shopPlaceService.getUnknownPlace();
        else
            shopPlace = shopPlaceService.findById(shop.getPlace().getId());
        return shopPlace;
    }

    @Override
    public List<Shop> findAwaitingConfirmation() {
        return shopRepository.findAllByShopStatus(ShopStatus.AWAITING_CONFIRMATION);
    }

    @Override
    public void approveShop(Long shopId) {
        var shop = findById(shopId);
        if (ShopStatus.CONFIRM.getIndex() <= shop.getShopStatus().getIndex()) {
            log.error(ErrorCode.CORE_SERVICE_SHOP_EARLIER_APPROVED.getTechnicalMessage() + " shop id = {}", shop.getId());
            throw new CoreServiceException(ErrorCode.CORE_SERVICE_SHOP_EARLIER_APPROVED);
        }
        authServiceProvider.setShopAdminRoleToUser(shop.getUserId());
        shop.setShopStatus(ShopStatus.CONFIRM);
        update(shop);

        asyncEngine.sendEmailInstantDeliveryMessage(
                shop.getUserId(),
                "LARxury",
                "فروشگاه شما تایید شد"
        );

        log.info("shop with id = {} confirmed ", shop.getId());
    }

    @Override
    public void rejectShop(Long shopId) {
        var shop = findById(shopId);
        if (!ShopStatus.AWAITING_CONFIRMATION.equals(shop.getShopStatus())) {
            log.error(ErrorCode.CORE_SERVICE_SHOP_TROUBLE_TO_REJECT.getTechnicalMessage() + " shop id = {}", shop.getId());
            throw new CoreServiceException(ErrorCode.CORE_SERVICE_SHOP_TROUBLE_TO_REJECT);
        }

        shop.setShopStatus(ShopStatus.REJECTION);
        update(shop);

        asyncEngine.sendEmailInstantDeliveryMessage(
                shop.getUserId(),
                "LARxury",
                "فروشگاه شما تایید نشد"
        );

        log.info("shop reject with id {} ", shop.getId());
    }

    private Shop findById(Long shopId) {
        return shopRepository.findById(shopId).orElseThrow(() -> {
            log.error(ErrorCode.CORE_SERVICE_SHOP_NOT_FOUND.getTechnicalMessage() + " with shop id = {}", shopId);
            return new CoreServiceException(ErrorCode.CORE_SERVICE_SHOP_NOT_FOUND);
        });
    }

    private Boolean existByName(String shopName) {
        return shopRepository.existsByName(shopName);
    }

    @Transactional
    protected void update(Shop shop) {
        log.info("shop update with id = {} ", shop.getId());
        shopRepository.save(shop);
    }

}
