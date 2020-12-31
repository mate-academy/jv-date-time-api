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
    private static final String CUSTOM_FORMAT = "d MMM yyyy";
    private static final String FORMAT_DATE_AND_TIME = "dd MMMM yyyy H:mm";
    private static final int UKRAINE_TIME_ZONE = 2;

    public String todayDate(DateTimePart datePart) {
        LocalDate dateNow = LocalDate.now();
        switch (datePart) {
            case DAY: return String.valueOf(dateNow.getDayOfMonth());
            case MONTH: return dateNow.getMonth().toString();
            case YEAR: return String.valueOf(dateNow.now().getYear());
            case FULL: return dateNow.toString();
            default: throw new DateTimeException("Wrong datePart");
        }
    }

    public Optional<LocalDate> getDate(Integer[] dateParams) {
        try {
            LocalDate localDate = LocalDate.of(dateParams[0], dateParams[1], dateParams[2]);
            return Optional.of(localDate);
        } catch (DateTimeException | ArrayIndexOutOfBoundsException e) {
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
        Instant instant = Instant.parse(dateInString);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return localDateTime;
    }

    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        ZoneOffset zoneOffset = ZoneOffset.ofHours(UKRAINE_TIME_ZONE);
        return localTime.atOffset(zoneOffset);
    }

    public Optional<LocalDate> parseDate(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
            return Optional.of(localDate);
        } catch (DateTimeException | NullPointerException e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDate> customParseDate(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter
                    .ofPattern(CUSTOM_FORMAT,Locale.UK));
            return Optional.of(localDate);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE_AND_TIME, Locale.UK));
    }
}
