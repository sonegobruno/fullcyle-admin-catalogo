package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryId;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.Error;
import com.fullcycle.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase{
    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand updateCategoryCommand) {
        final var id = CategoryId.from(updateCategoryCommand.id());

        final var category = this.categoryGateway.findById(id)
                    .orElseThrow(() -> DomainException.with(new Error("Category with ID %s was not found".formatted(id.getValue()))));

        category.update(updateCategoryCommand.name(), updateCategoryCommand.description(), updateCategoryCommand.isActive());
        final var notification = Notification.create();
        category.validate(notification);

        return notification.hasErrors() ? API.Left(notification) : update(category);
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category category) {
        return API.Try(() -> this.categoryGateway.update(category)).toEither().bimap(Notification::create, UpdateCategoryOutput::from);
    }
}
