package ir.larxury.core.service.service.impl;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.core.service.common.aop.exception.CoreServiceException;
import ir.larxury.core.service.database.model.Category;
import ir.larxury.core.service.database.repository.CategoryRepository;
import ir.larxury.core.service.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void saveCategory(Category category) {
        categoryRepository.save(category);
        log.info("saved category with id {}", category.getId());
    }

    @Override
    @Transactional
    public void saveSubCategory(Category category) {
        var parent = findById(category.getParentCategory().getId());
        category.setParentCategory(parent);
        saveCategory(category);
    }

    @Override
    public List<Category> findRootCategoryByIds(List<Long> categoryIds) {
        var categoryIdsAsSet = new HashSet<>(categoryIds);
        if (categoryIds.size() != categoryIdsAsSet.size()) {
            log.error(ErrorCode.CORE_SERVICE_CATEGORY_ID_NOT_UNIQUE.getTechnicalMessage());
            throw new CoreServiceException(ErrorCode.CORE_SERVICE_CATEGORY_ID_NOT_UNIQUE);
        }

        List<Category> categories = categoryRepository.findRootCategoryByIds(categoryIds);
        if (categories.size() != categoryIds.size()) {
            log.error(ErrorCode.CORE_SERVICE_CATEGORY_BAD_CATEGORY_IDS.getTechnicalMessage());
            throw new CoreServiceException(ErrorCode.CORE_SERVICE_CATEGORY_BAD_CATEGORY_IDS);
        }

        return categories;
    }

    @Override
    public Category findSubCategoryById(Long id) {
        return categoryRepository.findSubCategoryById(id).orElseThrow(() -> {
            log.error(ErrorCode.CORE_SERVICE_CATEGORY_IS_NUT_SUBCATEGORY.getTechnicalMessage());
            throw new CoreServiceException(ErrorCode.CORE_SERVICE_CATEGORY_IS_NUT_SUBCATEGORY);
        });
    }

    private Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            log.error(ErrorCode.CORE_SERVICE_CATEGORY_NOT_FOUND.getTechnicalMessage() + " with id {}", id);
            return new CoreServiceException(ErrorCode.CORE_SERVICE_CATEGORY_NOT_FOUND);
        });
    }
}
