package ir.larxury.core.service.service.impl;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.core.service.common.aop.exception.CoreServiceException;
import ir.larxury.core.service.database.model.Product;
import ir.larxury.core.service.database.repository.ProductRepository;
import ir.larxury.core.service.service.CategoryService;
import ir.larxury.core.service.service.ProductService;
import ir.larxury.core.service.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void save(Product product) {

        var shop = shopService.findById(product.getShop().getId());
        var category = categoryService.findSubCategoryById(product.getCategory().getId());

        product.setShop(shop);
        product.setCategory(category);

        productRepository.save(product);

        log.info("save product with id {}",product.getId());
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(()->{
            log.error(ErrorCode.CORE_SERVICE_PRODUCT_NOT_FOUND.getTechnicalMessage() + " with id {}", id);
            return new CoreServiceException(ErrorCode.CORE_SERVICE_PRODUCT_NOT_FOUND);
        });
    }
}
