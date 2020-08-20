package core.basesyntax;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class JavaDateTimeApi {
    private static final LocalDate LOCAL_DATE = LocalDate.now();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("dd MMMM yyyy HH:mm", Locale.ENGLISH);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("dd MMM yyyy", Locale.ENGLISH);

    public String todayDate(DateTimePart datePart) {
        switch (datePart) {
            case DAY:
                return "" + LOCAL_DATE.getDayOfMonth();
            case MONTH:
                return LOCAL_DATE.getMonth().toString();
            case YEAR:
                return "" + LOCAL_DATE.getYear();
            case FULL:
                return LOCAL_DATE.toString();
            default:
                throw new IllegalArgumentException("The required parameter is missing");
        }
    }

    public Optional<LocalDate> getDate(Integer[] dateParams) {
        try {
            return Optional.ofNullable(LocalDate.parse(Arrays.stream(dateParams)
                    .map(String::valueOf)
                    .collect(Collectors.joining("-"))));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public LocalTime addHours(LocalTime localTime, Integer hoursToAdd) {
        return localTime.plus(hoursToAdd, ChronoUnit.HOURS);
    }

    public LocalTime addMinutes(LocalTime localTime, Integer minutesToAdd) {
        return localTime.plus(minutesToAdd, ChronoUnit.MINUTES);
    }

    public LocalTime addSeconds(LocalTime localTime, Integer secondsToAdd) {
        return localTime.plus(secondsToAdd, ChronoUnit.SECONDS);
    }

    public LocalDate addWeeks(LocalDate localDate, Integer numberOfWeeks) {
        return localDate.plusWeeks(numberOfWeeks);
    }

    public String beforeOrAfter(LocalDate someDate) {
        if (someDate.isAfter(LOCAL_DATE)) {
            return String.format("%s is after %s", someDate, LOCAL_DATE);
        }
        if (someDate.isBefore(LOCAL_DATE)) {
            return String.format("%s is before %s", someDate, LOCAL_DATE);
        }
        return String.format("%s is today", someDate);
    }

    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        return LocalDateTime.ofInstant(Instant.parse(dateInString), ZoneId.of(zone));
    }

    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        return OffsetDateTime.of(localTime, ZoneOffset.of("+02:00"));
    }

    public Optional<LocalDate> parseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDate> customParseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, DATE_FORMATTER));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public String formatDate(LocalDateTime dateTime) {
        return DATE_TIME_FORMATTER.format(dateTime);
    }
}
