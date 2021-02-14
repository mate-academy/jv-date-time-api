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

public class JavaDateTimeApi {
    private static final int YEAR_INDEX = 0;
    private static final int MONTH_INDEX = 1;
    private static final int DAY_INDEX = 2;
    private static final String OFFSET_UKRAINE = "+02:00";
    private static final String PATTERN_D_MMM_YYYY = "d MMM yyyy";
    private static final String PATTERN_DD_MMMM_YYYY_HH_MM = "dd MMMM yyyy HH:mm";

    public String todayDate(DateTimePart datePart) {
        LocalDate current = LocalDate.now();
        switch (datePart) {
            case DAY:
                return String.valueOf(current.getDayOfMonth());
            case FULL:
                return String.valueOf(current);
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
                    Optional.of(LocalDate.of(dateParams[YEAR_INDEX], dateParams[MONTH_INDEX],
                            dateParams[DAY_INDEX]));
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
        ZoneOffset offset = ZoneOffset.of(OFFSET_UKRAINE);
        return OffsetDateTime.of(localTime, offset);
    }

    public Optional<LocalDate> parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
        try {
            return Optional.of(LocalDate.parse(date, formatter));
        } catch (DateTimeException e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDate> customParseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date,
                    DateTimeFormatter.ofPattern(PATTERN_D_MMM_YYYY)));
        } catch (DateTimeException e) {
            return Optional.empty();
        }
    }

    public String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(PATTERN_DD_MMMM_YYYY_HH_MM);
        return dateTime.format(format);
    }
}
