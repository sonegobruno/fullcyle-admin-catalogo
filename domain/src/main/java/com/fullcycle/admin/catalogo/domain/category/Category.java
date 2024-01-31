package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.AggregateRoot;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.UUID;

public class Category extends AggregateRoot<CategoryId> {
    private String name;

    private String description;

    private boolean active;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    private Category(
        final CategoryId anId,
        final String aName,
        final String aDescription,
        final boolean isActive,
        final Instant aCreatedAt,
        final Instant aUpdatedAt,
        final Instant aDeletedAt
    ) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.active = isActive;
        this.createdAt = aCreatedAt;
        this.updatedAt = aUpdatedAt;
        this.deletedAt = aDeletedAt;
    }

    public static Category newCategory(final String aName, final String aDescription, final boolean aIsActive) {
        final var id = CategoryId.unique();
        final var now = Instant.now();
        final var deletedAt = aIsActive ? null : Instant.now();
        return new Category(id, aName, aDescription, aIsActive, now, now, deletedAt);
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this,handler).validate();
    }

    public CategoryId getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isActive() {
        return this.active;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public Category deactivate() {
        if(getDeletedAt() == null) {
            this.deletedAt = Instant.now();
        }

        this.active = false;
        this.updatedAt = Instant.now();

        return this;
    }

    public Category activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();

        return this;
    }

    public Category update(final String aName, final String aDescription, final boolean isActive) {
        if(isActive) {
            this.activate();
        } else {
            this.deactivate();
        }

        this.name = aName;
        this.description = aDescription;
        this.updatedAt = Instant.now();

        return this;
    }
}
