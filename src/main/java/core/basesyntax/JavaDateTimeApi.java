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
    private static final int NUMBER_OF_DAYS = 31;
    private static final int NUMBER_OF_MONTH = 12;

    public String todayDate(DateTimePart datePart) {
        switch (datePart) {
            case FULL:
                return String.valueOf(LocalDate.now());
            case YEAR:
                return String.valueOf(LocalDate.now().getYear());
            case MONTH:
                return String.valueOf(LocalDate.now().getMonth());
            case DAY:
                return String.valueOf(LocalDate.now().getDayOfMonth());
            default: throw new DateTimeException("No such option");
        }
    }

    public Optional<LocalDate> getDate(Integer[] dateParams) {
        if (dateParams.length == 0
                || dateParams[1] > NUMBER_OF_MONTH
                || dateParams[2] > NUMBER_OF_DAYS) {
            return Optional.empty();
        }
        return Optional.of(LocalDate.of(dateParams[0], dateParams[1], dateParams[2]));
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
        if (someDate.isBefore(currentDate)) {
            return someDate + " is before " + currentDate;
        }
        if (someDate.isAfter(currentDate)) {
            return someDate + " is after " + currentDate;
        }
        return someDate + " is today";
    }

    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        return ZonedDateTime.parse(dateInString)
                .withZoneSameInstant(ZoneId.of(zone)).toLocalDateTime();
    }

    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        return localTime.atOffset(ZonedDateTime.now().getOffset());
    }

    public Optional<LocalDate> parseDate(String date) {
        if (Integer.parseInt(date.substring(6)) > NUMBER_OF_DAYS
                || Integer.parseInt(date.substring(4, 6)) > NUMBER_OF_MONTH) {
            return Optional.empty();
        }
        return Optional.of(LocalDate.parse(date,
                DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    public Optional<LocalDate> customParseDate(String date) {
        if (date.isEmpty()
                || date.isBlank()
                || Integer.parseInt(date.substring(0, 2)) > NUMBER_OF_DAYS) {
            return Optional.empty();
        }
        return Optional.of(LocalDate.parse(date,
                DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.US)));
    }

    public String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm", Locale.US));
    }
}
