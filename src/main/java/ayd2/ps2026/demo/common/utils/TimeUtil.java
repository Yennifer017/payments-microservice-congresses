package ayd2.ps2026.demo.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtil {

    public static LocalDateTime convertToLocalDateTime(
            LocalDate localDate,
            boolean fromBegin
    ) {
        if (localDate == null) {
            return null;
        }

        return fromBegin
                ? localDate.atStartOfDay()
                : localDate.atTime(LocalTime.MAX);
    }

}
