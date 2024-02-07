package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryId;

public record UpdateCategoryOutput(
        CategoryId id
) {
    public static UpdateCategoryOutput from(final Category category) {
        return new UpdateCategoryOutput(category.getId());
    }
}
