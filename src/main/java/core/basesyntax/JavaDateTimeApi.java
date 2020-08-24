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

    private static final ZoneOffset UKRAINE_TIMEZONE_OFFSET = ZoneOffset.of("+02:00");
    private static final LocalDate today = LocalDate.now();

    public String todayDate(DateTimePart datePart) {
        switch (datePart) {
            case FULL:
                return today.toString();
            case YEAR:
                return Integer.toString(today.getYear());
            case MONTH:
                return Integer.toString(today.getMonthValue());
            case DAY:
                return Integer.toString(today.getDayOfMonth());
            default:
                throw new DateTimeException("Illegal date part");
        }
    }

    public Optional<LocalDate> getDate(Integer[] dateParams) {
        try {
            return Optional.of(LocalDate.of(dateParams[0], dateParams[1], dateParams[2]));
        } catch (Exception e) {
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
        if (today.equals(someDate)) {
            return someDate + " is today";
        } else if (today.isAfter(someDate)) {
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
            return Optional.ofNullable(LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDate> customParseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date,
                    DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm", Locale.ENGLISH));
    }
}
