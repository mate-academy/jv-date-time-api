package core.basesyntax;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.swing.text.DateFormatter;

public class JavaDateTimeApi {

    public String todayDate(DateTimePart datePart) {
        LocalDateTime current = LocalDateTime.now();
        switch (datePart) {
            case DAY:
                return String.valueOf(current.getDayOfMonth());
            case FULL:
                return String.valueOf(current.toLocalDate());
            case YEAR:
                return String.valueOf(current.getYear());
            case MONTH:
                return String.valueOf(current.getMonth());
            default:
                throw new DateTimeException("Incorrect input data:" + datePart);
        }
    }

    public Optional<LocalDate> getDate(Integer[] dateParams) {
        try {
            return dateParams.length == 0 ? Optional.empty() :
                    Optional.of(LocalDate.of(dateParams[0], dateParams[1], dateParams[2]));
        } catch (DateTimeException e) {
            return Optional.empty();
        }
    }

    public LocalTime addHours(LocalTime localTime, Integer hoursToAdd) {
        return localTime.plus(Duration.ofHours(hoursToAdd));
    }

    public LocalTime addMinutes(LocalTime localTime, Integer minutesToAdd) {
        return localTime.plus(Duration.ofMinutes(minutesToAdd));
    }

    public LocalTime addSeconds(LocalTime localTime, Integer secondsToAdd) {
        return localTime.plus(Duration.ofSeconds(secondsToAdd));
    }

    public LocalDate addWeeks(LocalDate localDate, Integer numberOfWeeks) {
        return localDate.plus(Period.ofWeeks(numberOfWeeks));
    }

    public String beforeOrAfter(LocalDate someDate) {
        LocalDate date = LocalDate.now();
        if (someDate.isAfter(date)) {
            return someDate + " is after " + date;
        }
        if (someDate.isBefore(date)) {
            return someDate + " is before " + date;
        }
        return someDate + " is today";
    }

    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        ZoneId zoneId = ZoneId.of(zone);
        return LocalDateTime.from(ZonedDateTime.parse(dateInString).withZoneSameInstant(zoneId));
    }

    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        ZoneOffset offset = ZoneOffset.of("+02:00");
        return OffsetDateTime.of(localTime, offset);
    }

    public Optional<LocalDate> parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        try {
            return Optional.of(LocalDate.parse(date, formatter));
        } catch (DateTimeException e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDate> customParseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("d MMM yyyy")));
        } catch (DateTimeException e) {
            return Optional.empty();
        }
    }

    public String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
        return dateTime.format(format);
    }
}
