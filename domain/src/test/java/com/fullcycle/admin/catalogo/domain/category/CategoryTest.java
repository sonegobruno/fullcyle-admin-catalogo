package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CategoryTest {
    @Test
    void givenAValidParams_whenCallNewCategory_thenInstantiateACategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var currentCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(currentCategory);
        Assertions.assertNotNull(currentCategory.getId());
        Assertions.assertEquals(expectedName, currentCategory.getName());
        Assertions.assertEquals(expectedDescription, currentCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, currentCategory.isActive());
        Assertions.assertNotNull(currentCategory.getCreatedAt());
        Assertions.assertNotNull(currentCategory.getUpdatedAt());
        Assertions.assertNull(currentCategory.getDeletedAt());
    }

    @Test
    void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenThrowReceiveError() {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var currentCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var exception = Assertions.assertThrows(DomainException.class, () -> currentCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
    }

    @Test
    void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenThrowReceiveError() {
        final String expectedName = "   ";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        final var currentCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var exception = Assertions.assertThrows(DomainException.class, () -> currentCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
    }

    @Test
    void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenThrowReceiveError() {
        final String expectedName = "Fi ";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedErrorCount = 1;

        final var currentCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var exception = Assertions.assertThrows(DomainException.class, () -> currentCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
    }

    @Test
    void givenAnInvalidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenThrowReceiveError() {
        final String expectedName = "salutatus himenaeos gubergren ornatus vivamus indoctum posuere impetus sociosqu dicat dico fugit voluptatibus quis aeque convallis honestatis accusata utinam quis vivendo platonem eruditi tincidunt minim vestibulum homero reprimique sea taciti sociis gloriatur tempus omittantur ligula venenatis inimicus est dictum vel suscipiantur perpetua habeo cetero animal efficitur ad principes fugit litora no ancillae necessitatibus adipiscing intellegebat utroque sodales iuvaret libris pretium cursus dicant congue felis pertinacia donec dis lectus convenire vituperata cubilia dicit accusata persecuti deterruisset dicam auctor regione nam dicunt euripidis auctor appetere blandit omittantur morbi sententiae nonumes pretium constituam patrioque senserit tantas appareat pertinacia habemus erat detracto torquent periculis volutpat at menandri voluptaria congue vivamus netus sagittis scripserit persequeris cursus aliquip vivendo inciderint suscipiantur recteque enim felis sadipscing electram erroribus sonet hendrerit pertinacia convenire dictum interpretaris tincidunt gloriatur quaerendum equidem electram sollicitudin mnesarchum necessitatibus quaeque sonet feugait lorem facilisi audire suspendisse praesent ocurreret suscipit nostra consul suavitate veritus constituto explicari doctus malesuada aperiri ut maecenas pertinacia morbi tation iaculis putent integer adhuc mei cubilia delicata potenti utinam mnesarchum vivamus noluisse ante imperdiet auctor simul consequat omittam eam prodesset purus nulla nostrum tation comprehensam ubique facilisi sententiae primis conubia rutrum definitionem theophrastus animal suspendisse commodo conubia quaerendum dicant at erroribus taciti dui usu utamur est per erroribus ridens esse magnis iisque felis commodo urna verterem quaeque delicata et volutpat mattis expetenda partiendo ancillae persequeris aenean maiestatis equidem penatibus brute suas reque propriae eos dicunt elaboraret indoctum sagittis definiebas ius docendi parturient agam platea augue iusto graeco suspendisse causae postulant inimicus patrioque dicat natoque eu ridiculus elitr";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedErrorCount = 1;

        final var currentCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var exception = Assertions.assertThrows(DomainException.class, () -> currentCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
    }

    @Test
    void givenAValidEmptyDescription_whenCallNewCategory_thenInstantiateACategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "    ";
        final var expectedIsActive = true;

        final var currentCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> currentCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(currentCategory);
        Assertions.assertNotNull(currentCategory.getId());
        Assertions.assertEquals(expectedName, currentCategory.getName());
        Assertions.assertEquals(expectedDescription, currentCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, currentCategory.isActive());
        Assertions.assertNotNull(currentCategory.getCreatedAt());
        Assertions.assertNotNull(currentCategory.getUpdatedAt());
        Assertions.assertNull(currentCategory.getDeletedAt());
    }

    @Test
    void givenAValidFalseIsActive_whenCallNewCategory_thenInstantiateACategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida!";
        final var expectedIsActive = false;

        final var currentCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> currentCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(currentCategory);
        Assertions.assertNotNull(currentCategory.getId());
        Assertions.assertEquals(expectedName, currentCategory.getName());
        Assertions.assertEquals(expectedDescription, currentCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, currentCategory.isActive());
        Assertions.assertNotNull(currentCategory.getCreatedAt());
        Assertions.assertNotNull(currentCategory.getUpdatedAt());
        Assertions.assertNotNull(currentCategory.getDeletedAt());
    }

    @Test
    void givenAValidaActiveCategory_whenCallDeactivate_thenReturnCategoryInactivated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida!";
        final var expectedIsActive = false;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, true);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertNull(aCategory.getDeletedAt());
        Assertions.assertTrue(aCategory.isActive());

        final var currentCategory = aCategory.deactivate();

        Assertions.assertDoesNotThrow(() -> currentCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(aCategory.getId(),currentCategory.getId());
        Assertions.assertEquals(expectedName, currentCategory.getName());
        Assertions.assertEquals(expectedDescription, currentCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, currentCategory.isActive());
        Assertions.assertEquals(currentCategory.getCreatedAt(), createdAt);
        Assertions.assertTrue(currentCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(currentCategory.getDeletedAt());
    }

    @Test
    void givenAValidaInactiveCategory_whenCallActivate_thenReturnCategoryActivated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida!";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, false);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertNotNull(aCategory.getDeletedAt());
        Assertions.assertFalse(aCategory.isActive());

        final var currentCategory = aCategory.activate();

        Assertions.assertDoesNotThrow(() -> currentCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(aCategory.getId(),currentCategory.getId());
        Assertions.assertEquals(expectedName, currentCategory.getName());
        Assertions.assertEquals(expectedDescription, currentCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, currentCategory.isActive());
        Assertions.assertEquals(currentCategory.getCreatedAt(), createdAt);
        Assertions.assertTrue(currentCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(currentCategory.getDeletedAt());
    }

    @Test
    void givenAValidCategory_WhenCallUpdate_thenReturnCategoryUpdated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida!";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory("Film", "A categoria", expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        final var currentCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> currentCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(aCategory.getId(),currentCategory.getId());
        Assertions.assertEquals(expectedName, currentCategory.getName());
        Assertions.assertEquals(expectedDescription, currentCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, currentCategory.isActive());
        Assertions.assertEquals(currentCategory.getCreatedAt(), createdAt);
        Assertions.assertTrue(currentCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(currentCategory.getDeletedAt());
    }

    @Test
    void givenAValidCategory_WhenCallUpdateToInactivate_thenReturnCategoryUpdated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida!";
        final var expectedIsActive = false;

        final var aCategory = Category.newCategory("Film", "A categoria", true);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();


        final var currentCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> currentCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(aCategory.getId(),currentCategory.getId());
        Assertions.assertEquals(expectedName, currentCategory.getName());
        Assertions.assertEquals(expectedDescription, currentCategory.getDescription());
        Assertions.assertFalse(aCategory.isActive());
        Assertions.assertEquals(currentCategory.getCreatedAt(), createdAt);
        Assertions.assertTrue(currentCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(aCategory.getDeletedAt());
    }

    @Test
    void givenAValidCategory_WhenCallUpdateWithInvalidParams_thenReturnCategoryUpdated() {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida!";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory("Filmes", "A categoria", expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        final var currentCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(aCategory.getId(),currentCategory.getId());
        Assertions.assertEquals(expectedName, currentCategory.getName());
        Assertions.assertEquals(expectedDescription, currentCategory.getDescription());
        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertEquals(currentCategory.getCreatedAt(), createdAt);
        Assertions.assertTrue(currentCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(aCategory.getDeletedAt());
    }
}
