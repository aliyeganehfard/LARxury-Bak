package ir.larxury.core.service.controller;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import ir.larxury.core.service.common.dto.shopPlace.req.ShopPlaceReq;
import ir.larxury.core.service.common.dto.shopPlace.res.ShopPlaceRes;
import ir.larxury.core.service.database.model.ShopPlace;
import ir.larxury.core.service.service.ShopPlaceService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/shop/place/")
public class ShopPlaceController {

    @Autowired
    private ShopPlaceService shopPlaceService;

    private final ModelMapper mapper = new ModelMapper();

    @PreAuthorize("hasAnyRole('MANAGER')")
    @PostMapping("save")
    public ResponseEntity<GeneralResponse> save(@RequestBody @Valid ShopPlaceReq req) {
        var shopPlace = mapper.map(req, ShopPlace.class);
        shopPlaceService.save(shopPlace);
        var res = GeneralResponse.successfulResponse(ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("getById/{id}")
    public ResponseEntity<GeneralResponse> findById(@PathVariable(name = "id") Long id) {
        var shopPlace = shopPlaceService.findById(id);
        var shopPlaceDto = mapper.map(shopPlace, ShopPlaceRes.class);
        var res = GeneralResponse.successfulResponse(shopPlaceDto, ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("getAll")
    public ResponseEntity<GeneralResponse> findAll() {
        var shopPlaces = shopPlaceService.findAll();
        var shopPlacesDto = shopPlaces.stream()
                .map(shopPlace -> mapper.map(shopPlace, ShopPlaceRes.class))
                .collect(Collectors.toList());
        var res = GeneralResponse.successfulResponse(shopPlacesDto, ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
