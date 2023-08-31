package ir.larxury.core.service.controller;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import ir.larxury.common.utils.config.BaseModelMapper;
import ir.larxury.core.service.common.dto.shop.req.ShopRegistrationReq;
import ir.larxury.core.service.common.dto.shop.res.ShopAwaitingConfigurationRes;
import ir.larxury.core.service.database.model.Shop;
import ir.larxury.core.service.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/shop/")
public class ShopController {

    @Autowired
    private ShopService shopService;

    private final BaseModelMapper mapper = new BaseModelMapper();

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("register")
    public ResponseEntity<GeneralResponse> save(@RequestBody @Valid ShopRegistrationReq req,
                                                @RequestHeader("Authorization") String token) {
        var shop = mapper.toModel(req, Shop.class);
        shopService.saveNewShop(shop, token);
        var res = GeneralResponse.successfulResponse(ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @PostMapping("find/awaiting/confirmation")
    public ResponseEntity<GeneralResponse> findAwaitingConfirmation() {
        var shops = shopService.findAwaitingConfirmation();
        var shopsDto = mapper.toDtoList(shops, ShopAwaitingConfigurationRes.class);
        var res = GeneralResponse.successfulResponse(shopsDto, ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @PostMapping("registration/state/approve")
    public ResponseEntity<GeneralResponse> approveShop(@RequestParam(name = "shopId") Long shopId) {
        shopService.approveShop(shopId);
        var res = GeneralResponse.successfulResponse(ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @PostMapping("registration/state/reject")
    public ResponseEntity<GeneralResponse> rejectShop(@RequestParam(name = "shopId") Long shopId) {
        shopService.rejectShop(shopId);
        var res = GeneralResponse.successfulResponse(ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
