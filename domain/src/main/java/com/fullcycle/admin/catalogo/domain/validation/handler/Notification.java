package com.fullcycle.admin.catalogo.domain.validation.handler;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.Error;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {
    private final List<Error> errors;

    private Notification(final List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Error anError) {
        return new Notification(new ArrayList<>()).append(anError);
    }

    public static Notification create(final Throwable throwable) {
        return create(new Error(throwable.getMessage()));
    }

    @Override
    public Notification append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public Notification append(ValidationHandler handler) {
        this.errors.addAll(handler.getErrors());
        return this;
    }

    @Override
    public Notification validate(Validation validation) {
        try {
            validation.validate();
        } catch (final DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch (final Exception ex) {
            this.errors.add(new Error(ex.getMessage()));
        }
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}
