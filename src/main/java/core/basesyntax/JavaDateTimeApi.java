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
    private static final String UKRAINIAN_TIME = "+02:00";
    private static final String CUSTOM_FORMAT = "dd MMM yyyy";
    private static final String DATE_TIME_FORMAT = "dd MMMM yyyy HH:mm";

    public String todayDate(DateTimePart datePart) {
        LocalDate localDate = LocalDate.now();
        switch (datePart) {
            case FULL:
                return localDate.toString();
            case YEAR:
                return String.valueOf(localDate.getYear());
            case MONTH:
                return String.valueOf(localDate.getMonth());
            case DAY:
                return String.valueOf(localDate.getDayOfMonth());
            default:
                throw new DateTimeException("Wrong date format");
        }
    }

    public Optional<LocalDate> getDate(Integer[] dateParams) {
        try {
            return Optional.of(LocalDate.of(dateParams[0], dateParams[1], dateParams[2]));
        } catch (ArrayIndexOutOfBoundsException | DateTimeException e) {
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
        LocalDate currentDate = LocalDate.now();
        if (someDate.isAfter(currentDate)) {
            return someDate + " is after " + currentDate;
        }
        if (someDate.isBefore(currentDate)) {
            return someDate + " is before " + currentDate;
        }
        return someDate + " is today";
    }

    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        ZoneId zoneId = ZoneId.of(zone);
        return Instant.parse(dateInString).atZone(zoneId).toLocalDateTime();
    }

    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        return OffsetDateTime.of(localTime, ZoneOffset.of(UKRAINIAN_TIME));
    }

    public Optional<LocalDate> parseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDate> customParseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date,
                    DateTimeFormatter.ofPattern(CUSTOM_FORMAT, Locale.ENGLISH)));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter
                .ofPattern(DATE_TIME_FORMAT, Locale.ENGLISH));
    }
}
