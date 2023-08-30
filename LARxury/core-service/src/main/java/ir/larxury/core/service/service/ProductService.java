package ir.larxury.core.service.service;

import ir.larxury.core.service.database.model.Product;

public interface ProductService {

    void save(Product product);

    Product findById(Long id);

}
