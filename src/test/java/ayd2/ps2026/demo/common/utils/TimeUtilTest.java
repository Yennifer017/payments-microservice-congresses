package ayd2.ps2026.demo.common.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeUtilTest {

    @Test
    void convertToLocalDateTime_nullDate_returnsNull() {

        // Act
        LocalDateTime result = TimeUtil.convertToLocalDateTime(null, true);

        // Assert
        assertNull(result);
    }

    @Test
    void convertToLocalDateTime_fromBegin_true() {

        // Arrange
        LocalDate localDate =
                LocalDate.of(2026, 5, 24);

        // Act
        LocalDateTime result =
                TimeUtil.convertToLocalDateTime(
                        localDate,
                        true
                );

        // Assert
        assertEquals(
                LocalDateTime.of(
                        2026,
                        5,
                        24,
                        0,
                        0,
                        0
                ),
                result
        );
    }

    @Test
    void convertToLocalDateTime_fromBegin_false() {

        // Arrange
        LocalDate localDate =
                LocalDate.of(2026, 5, 24);

        // Act
        LocalDateTime result =
                TimeUtil.convertToLocalDateTime(
                        localDate,
                        false
                );

        // Assert
        assertEquals(
                localDate.atTime(LocalTime.MAX),
                result
        );
    }
}