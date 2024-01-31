package com.fullcycle.admin.catalogo.application.category.create;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Objects;

public class CreateCategoryUseCaseTest {
    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final CategoryGateway categoryGateway = Mockito.mock(CategoryGateway.class);
        Mockito.when(categoryGateway.create(Mockito.any())).thenAnswer(returnsFirstArg());
        final var useCase = new CreateCategoryUseCase(categoryGateway);

        final var currentOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(currentOutput);
        Assertions.assertNotNull(currentOutput.getId());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .create(Mockito.argThat(aCategory -> {
                    return Objects.equals(expectedName, aCategory.getName())
                            && Objects.equals(expectedDescription, aCategory.getDescription())
                            && Objects.equals(expectedIsActive, aCategory.isActive())
                            && Objects.nonNull(aCategory.getCreatedAt())
                            && Objects.nonNull(aCategory.getId())
                            && Objects.nonNull(aCategory.getUpdatedAt())
                            && Objects.isNull(aCategory.getDeletedAt());
                }

        ));
    }
}
