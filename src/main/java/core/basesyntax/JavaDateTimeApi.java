package core.basesyntax;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;

public class JavaDateTimeApi {
    private static final int FIRST_INDEX = 0;
    private static final int SECOND_INDEX = 1;
    private static final int THIRD_INDEX = 2;
    private static final int MAX_MONTH = 12;
    private static final int MAX_DAYS = 31;
    private static final ZoneId ZONE = ZoneId.of("+02:00");
    private static final LocalDate NOW = LocalDate.now();
    private static final DateTimeFormatter NATIVE
            = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter FORMAT_TIME
            = DateTimeFormatter.ofPattern("dd LLLL yyyy HH:mm", Locale.ENGLISH);
    private static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);

    public String todayDate(DateTimePart datePart) {
        switch (datePart) {
            case FULL:
                NOW.toString();
                break;
            case DAY:
                NOW.getDayOfMonth();
                break;
            case YEAR:
                NOW.getYear();
                break;
            case MONTH:
                NOW.getMonthValue();
                break;
            default:
                throw new DateTimeException("It's not correct data/time format");
        }
        return String.valueOf(NOW);
    }

    public Optional<LocalDate> getDate(Integer[] dateParams) {
        return dateParams.length == 0
                || dateParams[SECOND_INDEX] > MAX_MONTH
                || dateParams[THIRD_INDEX] > MAX_DAYS
                ? Optional.empty()
                : Optional.of(LocalDate.of(dateParams[FIRST_INDEX],
                dateParams[SECOND_INDEX], dateParams[THIRD_INDEX]));
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
        return NOW.isAfter(someDate)
                ? someDate + " is before " + NOW
                : NOW.equals(someDate)
                ? someDate + " is today"
                : someDate + " is after " + NOW;
    }

    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        return LocalDateTime
                .from(ZonedDateTime.parse(dateInString)
                        .withZoneSameInstant(ZoneId.of(zone)));
    }

    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        return OffsetDateTime
                .from(ZonedDateTime.of(localTime, ZONE)
                        .withZoneSameInstant(ZONE));
    }

    public Optional<LocalDate> parseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, DATE_TIME_FORMATTER));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDate> customParseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, NATIVE));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public String formatDate(LocalDateTime dateTime) {
        return dateTime.format(FORMAT_TIME);
    }
}
