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

    private static final DateTimeFormatter DATE_FORMATTER
            = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("dd MMMM y HH:mm", Locale.ENGLISH);
    private static final ZoneOffset UKRAINE_TIMEZONE_OFFSET = ZoneOffset.of("+02:00");
    private static final LocalDate today = LocalDate.now();

    public String todayDate(DateTimePart datePart) {
        switch (datePart) {
            case FULL:
                return today.toString();
            case YEAR:
                return Integer.toString(today.getYear());
            case MONTH:
                return today.getMonth().toString();
            case DAY:
                return Integer.toString(today.getDayOfMonth());
            default:
                throw new DateTimeException("Illegal date part");
        }
    }

    public Optional<LocalDate> getDate(Integer[] dateParams) {
        try {
            return Optional.of(LocalDate.of(dateParams[0], dateParams[1], dateParams[2]));
        } catch (DateTimeException | ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
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
        if (today.equals(someDate)) {
            return someDate + " is today";
        }
        if (today.isAfter(someDate)) {
            return someDate + " is before " + today;
        }
        return someDate + " is after " + today;
    }

    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        return LocalDateTime.ofInstant(Instant.parse(dateInString), ZoneId.of(zone));
    }

    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        return OffsetDateTime.of(localTime, UKRAINE_TIMEZONE_OFFSET);
    }

    public Optional<LocalDate> parseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE));
        } catch (DateTimeException e) {
            System.out.println(e);
        }
        return Optional.empty();
    }

    public Optional<LocalDate> customParseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, DATE_FORMATTER));
        } catch (DateTimeException e) {
            System.out.println(e);
        }
        return Optional.empty();
    }

    public String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }
}
