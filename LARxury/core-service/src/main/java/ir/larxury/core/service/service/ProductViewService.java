package ir.larxury.core.service.service;

public interface ProductViewService {

    void save(Long productId, String token);

    Long getProductViews(Long productId);
}
