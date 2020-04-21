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
import java.util.Locale;
import java.util.Optional;

public class JavaDateTimeApi {
    private static final DateTimeFormatter US_DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("dd MMMM yyyy HH:mm", Locale.US);
    private static final DateTimeFormatter US_DATE_FORMATTER = DateTimeFormatter
            .ofPattern("d MMM yyyy", Locale.US);

    public String todayDate(DateTimePart datePart) {
        LocalDate localDate = LocalDate.now();
        switch (datePart) {
            case FULL:
                return String.valueOf(localDate);
            case YEAR:
                return String.valueOf(localDate.getDayOfYear());
            case DAY:
                return String.valueOf(localDate.getDayOfMonth());
            case MONTH:
                return String.valueOf(localDate.getDayOfMonth());
            default:
                throw new DateTimeException("Date Time Exception");
        }
    }

    public Optional<LocalDate> getDate(Integer[] dateParams) {
        try {
            return dateParams.length == 0 ? Optional.empty()
                    : Optional.of(LocalDate.of(dateParams[0], dateParams[1], dateParams[2]));
        } catch (DateTimeException e) {
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
        LocalDate localDate = LocalDate.now();
        return someDate.isBefore(localDate) ? someDate + " is before " + localDate
                : someDate.isAfter(localDate) ? someDate + " is after " + localDate
                : someDate + " is today";
    }

    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {

        return ZonedDateTime.parse(dateInString)
                .withZoneSameInstant(ZoneId.of(zone))
                .toLocalDateTime();
    }

    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        return OffsetDateTime.of(localTime, ZoneOffset.ofHours(2));
    }

    public Optional<LocalDate> parseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date,
                    DateTimeFormatter.BASIC_ISO_DATE));
        } catch (DateTimeException e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDate> customParseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date,US_DATE_FORMATTER));
        } catch (DateTimeException e) {
            return Optional.empty();
        }
    }

    public String formatDate(LocalDateTime dateTime) {
        try {
            return dateTime.format(US_DATE_TIME_FORMATTER);
        } catch (DateTimeException e) {
            return "dateTime can't be formatted!";
        }
    }
}
