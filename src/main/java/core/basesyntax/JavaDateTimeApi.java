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
import java.util.Locale;
import java.util.Optional;

public class JavaDateTimeApi {
    private static final DateTimeFormatter DATE_TIME_MINUTE_FORMATTER
            = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm", Locale.ENGLISH);
    private static final String UKRAINE_OFFSET = "+02:00";

    public String todayDate(DateTimePart datePart) {
        LocalDate currentDate = LocalDate.now();
        switch (datePart) {
            case FULL: return currentDate.toString();
            case YEAR: return Integer.toString(currentDate.getYear());
            case MONTH: return currentDate.getMonth().toString();
            case DAY: return Integer.toString(currentDate.getDayOfMonth());
            default: throw new DateTimeException("Incorrect format of the current date");
        }
    }

    public Optional<LocalDate> getDate(Integer[] dateParams) {
        try {
            return Optional.of(LocalDate.of(dateParams[0], dateParams[1], dateParams[2]));
        } catch (DateTimeException | ArrayIndexOutOfBoundsException exception) {
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
        LocalDate currentMoment = LocalDate.now();
        return someDate.isAfter(currentMoment) ? someDate + " is after " + currentMoment
                : someDate.isBefore(currentMoment) ? someDate + " is before " + currentMoment
                : someDate + " is today";
    }

    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        return LocalDateTime.ofInstant(Instant.parse(dateInString), ZoneId.of(zone));
    }

    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        return OffsetDateTime.of(localTime, ZoneOffset.of(UKRAINE_OFFSET));
    }

    public Optional<LocalDate> parseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDate> customParseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, DATE_TIME_MINUTE_FORMATTER));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }
}
