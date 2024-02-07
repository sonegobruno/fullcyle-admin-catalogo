package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.application.category.create.CreateCategoryCommand;
import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryId;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.handler.Notification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {
    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {
        final var category = Category.newCategory("Film", null, true);
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId =  category.getId();

        final var command = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.findById(Mockito.eq(category.getId()))).thenReturn(Optional.of(category.clone()));
        Mockito.when(categoryGateway.update(Mockito.any())).thenAnswer(returnsFirstArg());

        final var currentOutput = useCase.execute(command).get();

        Assertions.assertNotNull(currentOutput);
        Assertions.assertNotNull(currentOutput.id());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .findById(category.getId());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .update(Mockito.argThat(aCategory -> {
                            return Objects.equals(expectedName, aCategory.getName())
                                    && Objects.equals(expectedDescription, aCategory.getDescription())
                                    && Objects.equals(expectedIsActive, aCategory.isActive())
                                    && Objects.equals(category.getId(), aCategory.getId())
                                    && Objects.equals(category.getCreatedAt(), aCategory.getCreatedAt())
                                    && category.getUpdatedAt().isBefore(aCategory.getUpdatedAt())
                                    && Objects.isNull(aCategory.getDeletedAt());
                        }
                ));
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCategory_shouldReturnDomainException() {
        final var category = Category.newCategory("Film", null, true);

        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId =  category.getId();

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(category.clone()));

        Notification notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());

        Mockito.verify(categoryGateway, Mockito.times(0)).update(Mockito.any());
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsUpdateCategory_shouldReturnInactiveCategoryId() {
        final var category = Category.newCategory("Film", null, true);

        Assertions.assertTrue(category.isActive());
        Assertions.assertNull(category.getDeletedAt());

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedId =  category.getId();

        final var command = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.findById(Mockito.eq(category.getId()))).thenReturn(Optional.of(category.clone()));
        Mockito.when(categoryGateway.update(Mockito.any())).thenAnswer(returnsFirstArg());

        final var currentOutput = useCase.execute(command).get();

        Assertions.assertNotNull(currentOutput);
        Assertions.assertNotNull(currentOutput.id());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .findById(category.getId());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .update(Mockito.argThat(aCategory -> {
                            return Objects.equals(expectedName, aCategory.getName())
                                    && Objects.equals(expectedDescription, aCategory.getDescription())
                                    && Objects.equals(expectedIsActive, aCategory.isActive())
                                    && Objects.equals(category.getId(), aCategory.getId())
                                    && Objects.equals(category.getCreatedAt(), aCategory.getCreatedAt())
                                    && category.getUpdatedAt().isBefore(aCategory.getUpdatedAt())
                                    && Objects.nonNull(aCategory.getDeletedAt());
                        }
                ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final var category = Category.newCategory("Film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;
        final var expectedId =  category.getId();

        final var command = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.findById(Mockito.eq(category.getId()))).thenReturn(Optional.of(category.clone()));
        Mockito.when(categoryGateway.update(Mockito.any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        Notification notification = useCase.execute(command).getLeft();
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .update(Mockito.argThat(aCategory -> {
                            return Objects.equals(expectedName, aCategory.getName())
                                    && Objects.equals(expectedDescription, aCategory.getDescription())
                                    && Objects.equals(expectedIsActive, aCategory.isActive())
                                    && Objects.equals(category.getId(), aCategory.getId())
                                    && Objects.equals(category.getCreatedAt(), aCategory.getCreatedAt())
                                    && category.getUpdatedAt().isBefore(aCategory.getUpdatedAt())
                                    && Objects.isNull(aCategory.getDeletedAt());
                        }
                ));
    }

    @Test
    public void givenAnInvalidIdCommand_whenCallsUpdateCategory_shouldReturnNotFoundException() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedId =  "123";
        final var expectedErrorMessage = "Category with ID %s was not found".formatted(expectedId);

        final var command = UpdateCategoryCommand.with(expectedId, expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.findById(Mockito.eq(CategoryId.from(expectedId)))).thenReturn(Optional.empty());

        final var currentException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(command));

        Assertions.assertEquals(currentException.getErrors().get(0).message(), expectedErrorMessage);
        Assertions.assertEquals(currentException.getErrors().size(), 1);

        Mockito.verify(categoryGateway, Mockito.times(1))
                .findById(CategoryId.from(expectedId));

        Mockito.verify(categoryGateway, Mockito.times(0))
                .update(Mockito.any());
    }
}
