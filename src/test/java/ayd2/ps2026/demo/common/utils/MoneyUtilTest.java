package ayd2.ps2026.demo.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyUtilTest {

    @Test
    void round_success() {

        // Arrange
        Double number = 10.456;

        // Act
        Double result = MoneyUtil.round(number);

        // Assert
        assertEquals(10.46, result);
    }

    @Test
    void round_roundDown_success() {

        // Arrange
        Double number = 10.454;

        // Act
        Double result = MoneyUtil.round(number);

        // Assert
        assertEquals(10.45, result);
    }

    @Test
    void roundTwoDecimals_success() {

        // Arrange
        Double number = 25.678;

        // Act
        Float result = MoneyUtil.roundTwoDecimals(number);

        // Assert
        assertEquals(25.68f, result);
    }

    @Test
    void roundTwoDecimals_roundDown_success() {

        // Arrange
        Double number = 25.674;

        // Act
        Float result = MoneyUtil.roundTwoDecimals(number);

        // Assert
        assertEquals(25.67f, result);
    }

}