package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {
    Category create(Category aCategory);
    Category deleteById(CategoryId anId);
    Optional<Category> findById(CategoryId anId);
    Category update(Category aCategory);
    Pagination<Category> findAll(CategorySearchQuery aQuery);
}
