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
    private static final int FIRST_INDEX = 0;
    private static final int SECOND_INDEX = 1;
    private static final int THIRD_INDEX = 2;
    private static final ZoneOffset UKRAINE_TIMEZONE = ZoneOffset.of("+02:00");
    private static final DateTimeFormatter DATE_FORMATTER
            = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("dd LLLL yyyy HH:mm", Locale.ENGLISH);

    public String todayDate(DateTimePart datePart) {
        LocalDate now = LocalDate.now();
        switch (datePart) {
            case FULL:
                return now.toString();
            case DAY:
                return String.valueOf(now.getDayOfMonth());
            case YEAR:
                return String.valueOf(now.getYear());
            case MONTH:
                return String.valueOf(now.getMonth());
            default:
                throw new DateTimeException("It's not correct data/time format");
        }
    }

    public Optional<LocalDate> getDate(Integer[] dateParams) {
        try {
            return Optional.of(LocalDate.of(dateParams[FIRST_INDEX],
                    dateParams[SECOND_INDEX], dateParams[THIRD_INDEX]));
        } catch (DateTimeException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong format!!");
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
        LocalDate now = LocalDate.now();
        return now.isAfter(someDate)
                ? someDate + " is before " + now
                : now.equals(someDate)
                ? someDate + " is today"
                : someDate + " is after " + now;
    }

    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        return LocalDateTime.ofInstant(Instant.parse(dateInString), ZoneId.of(zone));
    }

    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        return OffsetDateTime.of(localTime, UKRAINE_TIMEZONE);
    }

    public Optional<LocalDate> parseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE));
        } catch (DateTimeParseException e) {
            System.out.println("Wrong format!!");
        }
        return Optional.empty();
    }

    public Optional<LocalDate> customParseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, DATE_FORMATTER));
        } catch (DateTimeParseException e) {
            System.out.println("Wrong format!!");
        }
        return Optional.empty();
    }

    public String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }
}
