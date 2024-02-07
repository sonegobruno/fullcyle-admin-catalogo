package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryId;

public record CreateCategoryOutput(
        CategoryId id
) {
    public static CreateCategoryOutput from(final Category category) {
        return new CreateCategoryOutput(category.getId());
    }
}
