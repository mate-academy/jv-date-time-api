package core.basesyntax;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

public class JavaDateTimeApi {
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
        int year = 0;
        int month = 1;
        int day = 2;
        if (dateParams.length == 0 || dateParams[month] > 12 || dateParams[day] > 31) {
            return Optional.empty();
        }
        return Optional.of(LocalDate.of(dateParams[year], dateParams[month], dateParams[day]));
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
            return someDate.toString() + " is after " + LocalDate.now().toString();
        }
        if (someDate.isBefore(LocalDate.now())) {
            return someDate.toString() + " is before " + LocalDate.now().toString();
        }
        return someDate.toString() + " is today";
    }
    
    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        return ZonedDateTime.parse(dateInString)
                .withZoneSameInstant(ZoneId.of(zone)).toLocalDateTime();
    }
    
    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        return localTime.atOffset(ZonedDateTime.now().getOffset());
    }
    
    public Optional<LocalDate> parseDate(String date) {
        if (Integer.parseInt(date.substring(6)) > 31
                || Integer.parseInt(date.substring(4, 6)) > 12) {
            return Optional.empty();
        }
        return Optional.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd")));
    }
    
    public Optional<LocalDate> customParseDate(String date) {
        if (date.isEmpty()
                || date.isBlank()
                || Integer.parseInt(date.substring(0, 2)) > 31) {
            return Optional.empty();
        }
        return Optional.of(LocalDate.parse(date,
                DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.US)));
    }
    
    public String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm", Locale.US));
    }
}
