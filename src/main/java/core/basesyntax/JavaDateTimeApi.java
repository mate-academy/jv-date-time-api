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

    public String todayDate(DateTimePart datePart) {
        switch (datePart) {
            case DAY:
                return String.valueOf(LocalDateTime.now().getDayOfMonth());
            case YEAR:
                return String.valueOf(LocalDateTime.now().getYear());
            case MONTH:
                return String.valueOf(LocalDateTime.now().getMonth());
            case FULL:
                return String.valueOf(LocalDate.now());
            default:
                throw new DateTimeException("Can't return any date!");
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
        LocalDate localDate = LocalDate.now();
        return someDate.isAfter(localDate)
                ? someDate + " is after " + localDate
                : someDate.isBefore(localDate)
                ? someDate + " is before " + localDate
                : someDate + " is today";
    }

    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        return LocalDateTime.ofInstant(Instant.parse(dateInString), ZoneId.of(zone));
    }

    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        final String offsetForUkraine = "+02:00";
        return OffsetDateTime.of(localTime, ZoneOffset.of(offsetForUkraine));
    }

    public Optional<LocalDate> parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        try {
            return Optional.of(LocalDate.parse(date,formatter));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDate> customParseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("d MMM yyyy", Locale.ENGLISH);
        try {
            return Optional.of(LocalDate.parse(date, formatter));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd MMMM yyyy HH:mm", Locale.ENGLISH);
        return dateTime.format(formatter);
    }
}
