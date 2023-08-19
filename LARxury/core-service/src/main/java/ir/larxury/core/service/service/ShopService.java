package ir.larxury.core.service.service;

import ir.larxury.core.service.database.model.Shop;

import java.util.List;

public interface ShopService {

    void saveNewShop(Shop shop, String token);

    List<Shop> findAwaitingConfirmation();

    void approveShop(Long shopId);
}
