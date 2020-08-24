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
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter;
        switch (datePart) {
            case FULL:
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                break;
            case YEAR:
                formatter = DateTimeFormatter.ofPattern("yyyy");
                break;
            case MONTH:
                formatter = DateTimeFormatter.ofPattern("MM");
                break;
            case DAY:
                formatter = DateTimeFormatter.ofPattern("dd");
                break;
            default:
                throw new DateTimeException("Wrong format");
        }
        return today.format(formatter);
    }

    public Optional<LocalDate> getDate(Integer[] dateParams) {
        String data = "";
        String regex = "^([0-9]{4}[-/]?((0[13-9]|1[012])[-/]?(0[1-9]"
                + "|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]"
                + "|1[0-9]|2[0-8]))|([0-9]{2}(([2468][048]|[02468][48])"
                + "|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00)[-/]?02[-/]?29)$";
        for (Integer param : dateParams) {
            data += Integer.toString(param);
        }
        if (data.matches(regex)) {
            return Optional.of(LocalDate.of(dateParams[0], dateParams[1], dateParams[2]));
        }
        return Optional.empty();
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
        LocalDate today = LocalDate.now();
        return (today.isEqual(someDate)) ? someDate + " is today"
                : (today.isBefore(someDate)) ? someDate + " is after " + today
                : someDate + " is before " + today;
    }

    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        return LocalDateTime.ofInstant(Instant.parse(dateInString), ZoneId.of(zone));
    }

    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        return OffsetDateTime.of(localTime, ZoneOffset.of("+02:00"));
    }

    public Optional<LocalDate> parseDate(String date) {
        String regex = "^([0-9]{4}[-/]?((0[13-9]"
                + "|1[012])[-/]?(0[1-9]|[12][0-9]|30)"
                + "|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]"
                + "|1[0-9]|2[0-8]))|([0-9]{2}(([2468][048]"
                + "|[02468][48])|[13579][26])|([13579][26]"
                + "|[02468][048]|0[0-9]|1[0-6])00)[-/]?02[-/]?29)$";
        if (date.matches(regex)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate ld = LocalDate.parse(date, formatter);
            Optional<LocalDate> opt = Optional.ofNullable(ld);
            return opt;
        }
        return Optional.empty();
    }

    public Optional<LocalDate> customParseDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);
            LocalDate ld = LocalDate.parse(date, formatter);
            return Optional.ofNullable(ld);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd LLLL yyyy HH:mm", Locale.ENGLISH);
        return dateTime.format(formatter);
    }
}
