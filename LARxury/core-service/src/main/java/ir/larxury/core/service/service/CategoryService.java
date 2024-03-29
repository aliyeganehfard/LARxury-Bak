package ir.larxury.core.service.service;

import ir.larxury.core.service.database.model.Category;

import java.util.List;

public interface CategoryService {

    void saveCategory(Category category);

    void saveSubCategory(Category category);

    List<Category> findRootCategoryByIds(List<Long> categoryIds);

    Category findSubCategoryById(Long id);
}
