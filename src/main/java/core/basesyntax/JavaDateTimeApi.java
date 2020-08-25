package core.basesyntax;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;

public class JavaDateTimeApi {
    private static final ZoneOffset ZONE_OFFSET_UKRAINE = ZoneOffset.of("+02:00");
    private static final DateTimeFormatter FORMATTER1 = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter FORMATTER2 = DateTimeFormatter
            .ofPattern("d MMM yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter FORMATTER3 = DateTimeFormatter
            .ofPattern("dd LLLL yyyy HH:mm", Locale.ENGLISH);
    private static final DateTimeFormatter FORMATTER4 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private LocalDate today = LocalDate.now();

    public String todayDate(DateTimePart datePart) {
        switch (datePart) {
            case FULL:
                return today.format(FORMATTER4);
            case YEAR:
                return String.valueOf(today.getYear());
            case MONTH:
                return String.valueOf(today.getMonth());
            case DAY:
                return String.valueOf(today.getDayOfWeek());
            default:
                throw new DateTimeException("Wrong format");
        }
    }

    public Optional<LocalDate> getDate(Integer[] dateParams) {
        if (dateParams.length < 3) {
            return Optional.empty();
        }
        try {
            return Optional.of(LocalDate.of(dateParams[0], dateParams[1], dateParams[2]));
        } catch (DateTimeException e) {
            return Optional.empty();
        }
    }

    public LocalTime addHours(LocalTime localTime, Integer hoursToAdd) {
        return localTime.plusHours(hoursToAdd);
    }

    public LocalTime addMinutes(LocalTime localTime, Integer minutesToAdd) {
        return localTime.plusMinutes(minutesToAdd);
    }

    public LocalTime addSeconds(LocalTime localTime, Integer secondsToAdd) {
        return localTime.plusSeconds(secondsToAdd);
    }

    public LocalDate addWeeks(LocalDate localDate, Integer numberOfWeeks) {
        return localDate.plusWeeks(numberOfWeeks);
    }

    public String beforeOrAfter(LocalDate someDate) {
        return (today.isEqual(someDate))
                ? someDate + " is today"
                : (today.isBefore(someDate))
                ? someDate + " is after " + today
                : someDate + " is before " + today;
    }

    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        return LocalDateTime.ofInstant(Instant.parse(dateInString), ZoneId.of(zone));
    }

    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        return OffsetDateTime.of(localTime, ZONE_OFFSET_UKRAINE);
    }

    public Optional<LocalDate> parseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, FORMATTER1));
        } catch (DateTimeException e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDate> customParseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, FORMATTER2));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public String formatDate(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER3);
    }
}
