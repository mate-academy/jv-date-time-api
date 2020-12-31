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
    private static final String OFFSET_UKR_TIME = "+02:00";

    public String todayDate(DateTimePart datePart) {
        LocalDate localDate = LocalDate.now();
        switch (datePart) {
            case DAY:
                return String.valueOf(localDate.getDayOfMonth());
            case YEAR:
                return String.valueOf(localDate.getYear());
            case MONTH:
                return String.valueOf(localDate.getMonth());
            case FULL:
                return String.valueOf(localDate);
            default:
                throw new DateTimeException("Something went wrong");
        }
    }

    public Optional<LocalDate> getDate(Integer[] dateParams) {
        try {
            return Optional.of(LocalDate.of(dateParams[0], dateParams[1], dateParams[2]));
        } catch (RuntimeException e) {
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
        LocalDate today = LocalDate.now();
        if (someDate.isAfter(today)) {
            return String.format("%s is after %s", someDate,today);
        } else if (someDate.isBefore(today)) {
            return String.format("%s is before %s", someDate,today);
        }
        return String.format("%s is today", someDate);
    }

    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        return LocalDateTime.ofInstant(Instant.parse(dateInString), ZoneId.of(zone));
    }

    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        return OffsetDateTime.of(localTime, ZoneOffset.of(OFFSET_UKR_TIME));
    }

    public Optional<LocalDate> parseDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        try {
            return Optional.of(LocalDate.parse(date, dateTimeFormatter));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDate> customParseDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern("d MMM yyyy", Locale.ENGLISH);
        try {
            return Optional.of(LocalDate.parse(date, dateTimeFormatter));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern("dd MMMM yyyy HH:mm", Locale.ENGLISH);
        return dateTime.format(dateTimeFormatter);
    }
}
