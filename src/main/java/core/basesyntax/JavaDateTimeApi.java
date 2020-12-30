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
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.Optional;

public class JavaDateTimeApi {
    private static final int DAY_INDEX = 0;
    private static final int MONTH_INDEX = 1;
    private static final int YEAR_INDEX = 2;
    private static final int UKRAINE_OFFSET = 2;
    private static final String PATTERN_D_MMM_YYYY = "d MMM yyyy";
    private static final String PATTERN_DD_MMMM_YYYY_HH_MM = "dd MMMM yyyy HH:mm";

    /**
     * Return the current date as a String depending on a query.
     *
     * The query can be passed for the whole date or for it's part:
     *      - FULL - current date as a whole: year, month, day of month
     *        formatted as `YYYY-MM-DD` (a default return value);
     *      - YEAR - current year;
     *      - MONTH - name of the current month;
     *      - DAY - current day of month;
     * In any other case throw DateTimeException.
     **/
    public String todayDate(DateTimePart datePart) {
        LocalDate localdate = LocalDate.now();
        switch (datePart) {
            case FULL:
                return localdate.toString();
            case YEAR:
                return String.valueOf(localdate.getYear());
            case MONTH:
                return localdate.getMonth().name();
            case DAY:
                return String.valueOf(localdate.getDayOfMonth());
            default:
                throw new DateTimeException("Wrong query");
        }
    }

    /**
     * Given an Array of 3 elements, where
     *         - 1-st element is a `year`;
     *         - 2-nd element is s `month`;
     *         - 3-rd element is a `day of month`;
     *
     * Return Optional of a date built from these elements.
     */
    public Optional<LocalDate> getDate(Integer[] dateParams) {
        if (dateParams.length == 0
                || dateParams[DAY_INDEX] <= 0
                || dateParams[MONTH_INDEX] > 12
                || dateParams[YEAR_INDEX] > 31) {
            return Optional.empty();
        }
        LocalDate outputDate = LocalDate.of(dateParams[DAY_INDEX],
                dateParams[MONTH_INDEX], dateParams[YEAR_INDEX]);
        return Optional.of(outputDate);
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
     *     - "`someDate` is after `currentDate`"
     *                  if `someDate` is in the future relating to the `current date`;
     *     - "`someDate` is before `currentDate`"
     *                  if `someDate` is in the past relating to the `current date`;
     *     - "`someDate` is today"
     *                  if `someDate` is today;
     */
    public String beforeOrAfter(LocalDate someDate) {
        LocalDate currentDate = LocalDate.now();
        if (someDate.isAfter(currentDate)) {
            return String.format("%s is after %s", someDate, currentDate);
        }
        if (someDate.isBefore(currentDate)) {
            return String.format("%s is before %s", someDate, currentDate);
        }
        return String.format("%s is today", someDate);
    }

    /**
     * Given a String representation of a date and some timezone,
     * return LocalDateTime in this timezone.
     */
    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        return LocalDateTime.ofInstant(Instant.parse(dateInString), ZoneId.of(zone));
    }

    /**
     * Given some LocalDateTime, return an OffsetDateTime with the local time offset applied
     * (`+02:00` for Ukraine).
     *
     * Example: we receive a LocalDateTime with a value `2019-09-06T13:17`.
     *          We should return the OffsetDateTime with a value `2019-09-06T13:17+02:00`,
     *          where `+02:00` is the offset for our local timezone.
     *
     * OffsetDateTime is recommended to use for storing date values in a database.
     */
    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        return OffsetDateTime.of(localTime, ZoneOffset.ofHours(UKRAINE_OFFSET));
    }

    /**
     * Given a String formatted as `yyyymmdd`,
     * return Optional of this date as a LocalDate.
     */
    public Optional<LocalDate> parseDate(String date) {
        TemporalAccessor temporalAccessor;
        try {
            temporalAccessor = DateTimeFormatter.BASIC_ISO_DATE.parse(date);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
        return Optional.of(LocalDate.from(temporalAccessor));
    }

    /**
     * Given a String formatted as `d MMM yyyy` (MMM - Sep, Oct, etc),
     * return Optional of this date as a LocalDate.
     */
    public Optional<LocalDate> customParseDate(String date) {
        try {
            TemporalAccessor temporalAccessor = DateTimeFormatter.ofPattern(PATTERN_D_MMM_YYYY)
                    .withLocale(Locale.ENGLISH).parse(date);
            return Optional.of(LocalDate.from(temporalAccessor));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    /**
     * Given some LocalDateTime, return a String formatted as
     * `day(2-digit) month(full name in English) year(4-digit) hours(24-hour format):minutes`.
     *
     * Example: "01 January 2000 18:00".
     */
    public String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(PATTERN_DD_MMMM_YYYY_HH_MM)
                .withLocale(Locale.ENGLISH));
    }
}
