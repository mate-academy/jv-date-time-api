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
            case DAY: return String.valueOf(LocalDate.now().getDayOfMonth());
            case MONTH: return LocalDate.now().getMonth().toString();
            case YEAR: return String.valueOf(LocalDate.now().getYear());
            case FULL: return LocalDate.now().toString();
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

        if (someDate.isEqual(currentDate)) {
            return someDate + " is today";
        }
        return someDate.toString();
    }

    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        ZoneId zoneId = ZoneId.of(zone);
        Instant instant = Instant.parse(dateInString);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return localDateTime;
    }

    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        ZoneOffset zoneOffset = ZoneOffset.ofHours(2);
        return localTime.atOffset(zoneOffset);
    }

    public Optional<LocalDate> parseDate(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
            return Optional.of(localDate);
        } catch (DateTimeException | NullPointerException e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDate> customParseDate(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter
                    .ofPattern("d MMM yyyy",Locale.UK));
            return Optional.ofNullable(localDate);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy H:mm", Locale.UK));
    }
}
