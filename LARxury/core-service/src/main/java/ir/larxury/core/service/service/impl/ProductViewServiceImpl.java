package ir.larxury.core.service.service.impl;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.service.JWTVerificationService;
import ir.larxury.core.service.common.aop.exception.CoreServiceException;
import ir.larxury.core.service.database.model.ProductView;
import ir.larxury.core.service.database.repository.ProductViewRepository;
import ir.larxury.core.service.service.ProductService;
import ir.larxury.core.service.service.ProductViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class ProductViewServiceImpl implements ProductViewService {

    @Autowired
    private ProductViewRepository productViewRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private JWTVerificationService jwtVerificationService;

    private final List<ProductView> views = new ArrayList<>();

    @Override
    public void save(Long productId, String token) {
        var product = productService.findById(productId);
        var userId = jwtVerificationService.getUuid(token);

        var view = ProductView.builder()
                .date(new Date())
                .product(product)
                .userId(userId)
                .build();

        views.add(view);
    }

    @Override
    public Long getProductViews(Long productId) {
        return productViewRepository.getProductViews(productId);
    }

    @Scheduled(fixedRate = 900000)
    @Transactional
    public void schedulingSave() {
        List<ProductView> tempViews = new ArrayList<>(views);
        try {
            if (!tempViews.isEmpty()) {
                views.removeAll(tempViews);
                productViewRepository.saveAll(tempViews);
                log.info("products views saved!");
            }
        } catch (Exception e) {
            views.addAll(tempViews);
            log.error(ErrorCode.CORE_SERVICE_TROUBLE_TO_SAVE_PRODUCT_VIEWS.getTechnicalMessage());
            throw new CoreServiceException(ErrorCode.CORE_SERVICE_TROUBLE_TO_SAVE_PRODUCT_VIEWS, e);
        }
    }
}
