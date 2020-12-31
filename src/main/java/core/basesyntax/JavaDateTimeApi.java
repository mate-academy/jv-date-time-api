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
    private static final String DATE_PATTERN_SPECIFIC_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String TIMEZONE_UTC = "UTC";
    private static final String ZONE_OFFSET = "+02:00";
    private static final String DATE_PATTERN = "d MMM yyyy";
    private static final String DATE_TIME_PATTERN = "dd MMMM yyyy HH:mm";

    /**
     * Return the current date as a String depending on a query.
     * The query can be passed for the whole date or for it's part:
     * - FULL - current date as a whole: year, month, day of month
     * formatted as `YYYY-MM-DD` (a default return value);
     * - YEAR - current year;
     * - MONTH - name of the current month;
     * - DAY - current day of month;
     * In any other case throw DateTimeException.
     **/
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
            default:
                throw new DateTimeException("Can't resolve datePart");
        }
    }

    /**
     * Given an Array of 3 elements, where
     * - 1-st element is a `year`;
     * - 2-nd element is s `month`;
     * - 3-rd element is a `day of month`;
     * Return Optional of a date built from these elements.
     */
    public Optional<LocalDate> getDate(Integer[] dateParams) {
        try {
            return Optional.of(LocalDate.of(dateParams[0], dateParams[1], dateParams[2]));
        } catch (IndexOutOfBoundsException | DateTimeException exception) {
            return Optional.empty();
        }
    }

    /**
     * Given the time and the number of hours to add, return the changed time.
     */
    public LocalTime addHours(LocalTime localTime, Integer hoursToAdd) {
        return localTime.plusHours(hoursToAdd);
    }

    /**
     * Given the time and the number of minutes to add, return the changed time.
     */
    public LocalTime addMinutes(LocalTime localTime, Integer minutesToAdd) {
        return localTime.plusMinutes(minutesToAdd);
    }

    /**
     * Given the time and the number of seconds to add, return the changed time.
     */
    public LocalTime addSeconds(LocalTime localTime, Integer secondsToAdd) {
        return localTime.plusSeconds(secondsToAdd);
    }

    /**
     * Given the date and the number of weeks to add, return the changed date.
     */
    public LocalDate addWeeks(LocalDate localDate, Integer numberOfWeeks) {
        return localDate.plusWeeks(numberOfWeeks);
    }

    /**
     * Given a random `someDate` date, return one of the following Strings:
     * - "`someDate` is after `currentDate`"
     * if `someDate` is in the future relating to the `current date`;
     * - "`someDate` is before `currentDate`"
     * if `someDate` is in the past relating to the `current date`;
     * - "`someDate` is today"
     * if `someDate` is today;
     */
    public String beforeOrAfter(LocalDate someDate) {
        LocalDate now = LocalDate.now();
        if (someDate.isAfter(now)) {
            return someDate + " is after " + now;
        }
        if (someDate.isBefore(now)) {
            return someDate + " is before " + now;
        }
        return someDate + " is today";
    }

    /**
     * Given a String representation of a date and some timezone,
     * return LocalDateTime in this timezone.
     */
    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN_SPECIFIC_TIME_ZONE);
        LocalDateTime dateTime = LocalDateTime.parse(dateInString, formatter);
        ZoneId zoneId = ZoneId.of(zone);
        ZoneId zoneIdUtc = ZoneId.of(TIMEZONE_UTC);
        ZonedDateTime zonedDateTimeUtc = dateTime.atZone(zoneIdUtc);
        ZonedDateTime zonedDateTime = zonedDateTimeUtc.withZoneSameInstant(zoneId);
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        return localDateTime;
    }

    /**
     * Given some LocalDateTime, return an OffsetDateTime with the local time offset applied
     * (`+02:00` for Ukraine).
     * Example: we receive a LocalDateTime with a value `2019-09-06T13:17`.
     * We should return the OffsetDateTime with a value `2019-09-06T13:17+02:00`,
     * where `+02:00` is the offset for our local timezone.
     * OffsetDateTime is recommended to use for storing date values in a database.
     */
    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        return OffsetDateTime.of(localTime, ZoneOffset.of(ZONE_OFFSET));
    }

    /**
     * Given a String formatted as `yyyymmdd`,
     * return Optional of this date as a LocalDate.
     */
    public Optional<LocalDate> parseDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
            return Optional.of(LocalDate.parse(date, formatter));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    /**
     * Given a String formatted as `d MMM yyyy` (MMM - Sep, Oct, etc),
     * return Optional of this date as a LocalDate.
     */
    public Optional<LocalDate> customParseDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN,
                    Locale.ENGLISH);
            return Optional.of(LocalDate.parse(date, formatter));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    /**
     * Given some LocalDateTime, return a String formatted as
     * `day(2-digit) month(full name in English) year(4-digit) hours(24-hour format):minutes`.
     * Example: "01 January 2000 18:00".
     */
    public String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN,
                Locale.ENGLISH);
        return dateTime.format(formatter);
    }
}
