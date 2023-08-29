package ir.larxury.core.service.controller;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import ir.larxury.core.service.common.dto.product.req.ProductSaveReq;
import ir.larxury.core.service.database.model.Product;
import ir.larxury.core.service.service.ProductService;
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
@RequestMapping("v1/product/")
public class ProductController {

    @Autowired
    private ProductService productService;

    private final ModelMapper mapper = new ModelMapper();

    @PreAuthorize("hasAnyRole('SHOP_ADMIN')")
    @PostMapping("save")
    public ResponseEntity<GeneralResponse> save(@RequestBody @Valid ProductSaveReq req){
        var product = mapper.map(req, Product.class);
        productService.save(product);
        var res = GeneralResponse.successfulResponse(ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
