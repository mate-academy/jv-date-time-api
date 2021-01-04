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
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class JavaDateTimeApi {

    public static final int DEFAULT_LENGTH_LOCAL_DATE = 10;
    public static final String DELIMITER = "-";
    public static final String SHORT_IDS_ECT = "ECT";
    public static final String PATTERN_DATE_YYYY_MM_DD = "yyyyMMdd";
    public static final String PATTERN_DATE_DD_MMM_YYY = "dd MMM yyy";
    public static final String PATTERN_DATE_DD_MMMM_YYYY_HH_MM = "dd MMMM yyyy HH:mm";

    /**
     * Return the current date as a String depending on a query.
     * <p>
     * The query can be passed for the whole date or for it's part:
     * - FULL - current date as a whole: year, month, day of month
     * formatted as `YYYY-MM-DD` (a default return value);
     * - YEAR - current year;
     * - MONTH - name of the current month;
     * - DAY - current day of month;
     * In any other case throw DateTimeException.
     **/
    public String todayDate(DateTimePart datePart) {
        LocalDate localDate = LocalDate.now();
        switch (datePart) {
            case DAY:
                return String.valueOf(localDate.getDayOfMonth());
            case MONTH:
                return String.valueOf(localDate.getMonth());
            case YEAR:
                return String.valueOf(localDate.getYear());
            case FULL:
                return String.valueOf(localDate);
            default:
                throw new DateTimeException("Don't valid dataPart " + datePart);
        }
    }

    /**
     * Given an Array of 3 elements, where
     * - 1-st element is a `year`;
     * - 2-nd element is s `month`;
     * - 3-rd element is a `day of month`;
     * <p>
     * Return Optional of a date built from these elements.
     */
    public Optional<LocalDate> getDate(Integer[] dateParams) {
        String collect = Arrays.stream(dateParams)
                .map(String::valueOf)
                .collect(Collectors.joining(DELIMITER));
        return dateParams.length == 0 || collect.length() > DEFAULT_LENGTH_LOCAL_DATE
                ? Optional.empty() : Optional.ofNullable(LocalDate.parse(collect));
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
        LocalDate current = LocalDate.now();
        return someDate.isBefore(current) ? someDate + " is before " + current
                : someDate.isAfter(current) ? someDate + " is after " + current
                : someDate + " is today";
    }

    /**
     * Given a String representation of a date and some timezone,
     * return LocalDateTime in this timezone.
     */
    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        return LocalDateTime.from(ZonedDateTime.parse(dateInString).withZoneSameInstant(ZoneId.of(zone)));
    }

    /**
     * Given some LocalDateTime, return an OffsetDateTime with the local time offset applied
     * (`+02:00` for Ukraine).
     * <p>
     * Example: we receive a LocalDateTime with a value `2019-09-06T13:17`.
     * We should return the OffsetDateTime with a value `2019-09-06T13:17+02:00`,
     * where `+02:00` is the offset for our local timezone.
     * <p>
     * OffsetDateTime is recommended to use for storing date values in a database.
     */
    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        ZoneId ukraineZoneId = ZoneId.of(ZoneId.SHORT_IDS.get(SHORT_IDS_ECT));
        ZoneOffset zoneOffset = ukraineZoneId.getRules().getOffset(localTime);
        return OffsetDateTime.of(localTime, zoneOffset);
    }

    /**
     * Given a String formatted as `yyyymmdd`,
     * return Optional of this date as a LocalDate.
     */
    public Optional<LocalDate> parseDate(String date) {
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern(PATTERN_DATE_YYYY_MM_DD);
        try {
            return Optional.ofNullable(LocalDate.parse(date, dateTimeFormatter));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    /**
     * Given a String formatted as `d MMM yyyy` (MMM - Sep, Oct, etc),
     * return Optional of this date as a LocalDate.
     */
    public Optional<LocalDate> customParseDate(String date) {
        DateTimeFormatter currentDateTimeFormatter =
                DateTimeFormatter.ofPattern(PATTERN_DATE_DD_MMM_YYY);
        try {
            return Optional.ofNullable(LocalDate.parse(date,
                    currentDateTimeFormatter.withLocale(Locale.US)));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    /**
     * Given some LocalDateTime, return a String formatted as
     * `day(2-digit) month(full name in English) year(4-digit) hours(24-hour format):minutes`.
     * <p>
     * Example: "01 January 2000 18:00".
     */
    public String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter
                .ofPattern(PATTERN_DATE_DD_MMMM_YYYY_HH_MM).withLocale(Locale.US));
    }
}
