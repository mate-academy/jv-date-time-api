package core.basesyntax;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;

public class JavaDateTimeApi {
    private static final String OFFSET_UA = "+02:00";
    private static final String TRIMMED_MONTH_PATTERN = "dd MMM yyyy";
    private static final String FULL_MONTH_PATTERN = "dd MMMM yyyy HH:mm";
    
    public String todayDate(DateTimePart datePart) {
        switch (datePart) {
            case FULL:
                return LocalDate.now().toString();
            case YEAR:
                return String.valueOf(LocalDate.now().getYear());
            case MONTH:
                return LocalDate.now().getMonth().toString();
            case DAY:
                return String.valueOf(LocalDate.now().getDayOfMonth());
            default:
                throw new DateTimeException("No such option");
        }
    }
    
    public Optional<LocalDate> getDate(Integer[] dateParams) {
        int yearPosition = 0;
        int monthPosition = 1;
        int dayPosition = 2;
        if (dateParams.length == 0
                || dateParams[monthPosition] > 12
                || dateParams[dayPosition] > 31) {
            return Optional.empty();
        }
        return Optional.of(LocalDate.of(dateParams[yearPosition],
                dateParams[monthPosition],
                dateParams[dayPosition]));
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
        if (someDate.isAfter(LocalDate.now())) {
            return someDate + " is after " + LocalDate.now();
        }
        if (someDate.isBefore(LocalDate.now())) {
            return someDate + " is before " + LocalDate.now();
        }
        return someDate + " is today";
    }
    
    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        return ZonedDateTime.parse(dateInString)
                .withZoneSameInstant(ZoneId.of(zone)).toLocalDateTime();
    }
    
    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        return OffsetDateTime.of(localTime, ZoneOffset.of(OFFSET_UA));
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
                    DateTimeFormatter.ofPattern(TRIMMED_MONTH_PATTERN, Locale.US)));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }
    
    public String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter
                .ofPattern(FULL_MONTH_PATTERN, Locale.US));
    }
}
