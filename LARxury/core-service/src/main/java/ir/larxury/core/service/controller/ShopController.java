package ir.larxury.core.service.controller;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import ir.larxury.core.service.common.dto.shop.ShopReq;
import ir.larxury.core.service.database.model.Shop;
import ir.larxury.core.service.service.ShopService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/shop/")
public class ShopController {

    @Autowired
    private ShopService shopService;

    private final ModelMapper mapper = new ModelMapper();

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("save")
    public ResponseEntity<GeneralResponse> save(@RequestBody @Valid ShopReq req){
        var shop = mapper.map(req, Shop.class);
        shopService.saveNewShop(shop);
        var res = GeneralResponse.successfulResponse(ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
