package ir.larxury.core.service.controller;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import ir.larxury.core.service.common.dto.category.req.CategoryReq;
import ir.larxury.core.service.common.dto.category.req.SubCategoryReq;
import ir.larxury.core.service.database.model.Category;
import ir.larxury.core.service.service.CategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/category/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private final ModelMapper mapper = new ModelMapper();

    @PreAuthorize("hasAnyRole('MANAGER')")
    @PostMapping("save")
    public ResponseEntity<GeneralResponse> save(@RequestBody @Valid CategoryReq req){
        var category = mapper.map(req, Category.class);
        categoryService.saveCategory(category);
        var res = GeneralResponse.successfulResponse(ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @PostMapping("sub/save")
    public ResponseEntity<GeneralResponse> saveSubCategory(@RequestBody @Valid SubCategoryReq req){
        var category = mapper.map(req, Category.class);
        categoryService.saveSubCategory(category);
        var res = GeneralResponse.successfulResponse(ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

}