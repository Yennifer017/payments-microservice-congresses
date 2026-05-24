package ayd2.ps2026.demo.common.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MethodArgumentErrorExtractorTest {

    private MethodArgumentErrorExtractor methodArgumentErrorExtractor;

    @BeforeEach
    void setUp() {
        methodArgumentErrorExtractor =
                new MethodArgumentErrorExtractor();
    }

    @Test
    void extractMethodArgumentError_success() {

        // Arrange
        Object target = new Object();

        BindingResult bindingResult =
                new BeanPropertyBindingResult(target, "target");

        bindingResult.addError(
                new FieldError(
                        "target",
                        "username",
                        "El username es obligatorio"
                )
        );

        bindingResult.addError(
                new FieldError(
                        "target",
                        "password",
                        "La contraseña es obligatoria"
                )
        );

        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(
                        null,
                        bindingResult
                );

        // Act
        String result =
                methodArgumentErrorExtractor
                        .extractMethodArgumentError(exception);

        // Assert
        assertEquals(
                "- El username es obligatorio<br>" +
                        "- La contraseña es obligatoria<br>",
                result
        );
    }

    @Test
    void extractMethodArgumentError_emptyErrors_returnsEmptyString() {

        // Arrange
        Object target = new Object();

        BindingResult bindingResult =
                new BeanPropertyBindingResult(target, "target");

        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(
                        null,
                        bindingResult
                );

        // Act
        String result =
                methodArgumentErrorExtractor
                        .extractMethodArgumentError(exception);

        // Assert
        assertEquals("", result);
    }
}