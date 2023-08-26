package ir.larxury.core.service.service.impl;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.service.JWTVerificationService;
import ir.larxury.core.service.common.aop.exception.CoreServiceException;
import ir.larxury.core.service.database.model.Shop;
import ir.larxury.core.service.database.model.enums.ShopStatus;
import ir.larxury.core.service.database.repository.ShopRepository;
import ir.larxury.core.service.service.provider.AsyncEngine;
import ir.larxury.core.service.service.provider.AuthProvider;
import ir.larxury.core.service.service.ShopService;
import ir.larxury.core.service.service.provider.MessageDispatcherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private JWTVerificationService jwtVerificationService;

    @Autowired
    private AuthProvider authProvider;

    @Autowired
    private AsyncEngine asyncEngine;

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
        shopRepository.save(shop);

        asyncEngine.sendInstantDeliveryMessage(
                "LARxury",
                "اطلاعات فروشگاه شما با موفقیت ثبت شد",
                "aliyeganefard81@gmail.com");

        log.info("save shop with id {}", shop.getId());
    }

    @Override
    public List<Shop> findAwaitingConfirmation() {
        return shopRepository.findAllByShopStatus(ShopStatus.AWAITING_CONFIRMATION);
    }

    @Override
    public void approveShop(Long shopId) {
        var shop = findById(shopId);
        if (shop.getShopStatus().getIndex() <= ShopStatus.CONFIRM.getIndex()) {
            log.error(ErrorCode.CORE_SERVICE_SHOP_EARLIER_APPROVED.getTechnicalMessage() + " shop id = {}", shop.getId());
            throw new CoreServiceException(ErrorCode.CORE_SERVICE_SHOP_EARLIER_APPROVED);
        }
        authProvider.setShopAdminRoleToUser(shop.getUserId());
        shop.setShopStatus(ShopStatus.CONFIRM);
        update(shop);
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
