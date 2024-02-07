package com.fullcycle.admin.catalogo.application.category.delete;

import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryId;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public DefaultDeleteCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public void execute(final String categoryId) {
        this.categoryGateway.deleteById(CategoryId.from(categoryId));
    }
}
