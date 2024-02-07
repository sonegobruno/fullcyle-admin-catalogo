package com.fullcycle.admin.catalogo.domain.validation;

import java.util.List;

public interface ValidationHandler {
    ValidationHandler append(Error error);
    ValidationHandler append(ValidationHandler handler);

    ValidationHandler validate(Validation validation);

    default boolean hasErrors() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError() {
        if(getErrors() != null && !getErrors().isEmpty()) {
            return getErrors().get(0);
        } else {
            return null;
        }
    }


    List<Error> getErrors();

   public interface Validation {
       void validate();
   }
}
