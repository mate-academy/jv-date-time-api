package core.basesyntax;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.zone.ZoneRulesException;
import java.util.Locale;
import java.util.Optional;

public class JavaDateTimeApi {

    /**
     * Верните текущую дату в виде строки в зависимости от запроса.
     *
     * @param datePart Запрос на часть даты или всю дата целиком:
     *                 - FULL - текущая дата целиком год, месяц, день (число месяца)
     *                 в формате YYYY-MM-DD, возвращаемое значение по умолчанию;
     *                 - YEAR - текущий год;
     *                 - MONTH - название текущего месяца;
     *                 - DAY - текущий день (число месяца);
     **/
    public String todayDate(DateTimePart datePart) {
        LocalDateTime localDate = LocalDateTime.now();
        switch (datePart) {
            case DAY:
                return Integer.toString(localDate.getDayOfMonth());
            case YEAR:
                return Integer.toString(localDate.getYear());
            case FULL:
                return localDate.toLocalDate().toString();
            case MONTH:
                return localDate.getMonth().toString();
            case HOURS:
                return Integer.toString(localDate.getHour());
            case MINUTES:
                return Integer.toString(localDate.getMinute());
            case SECONDS:
                return Integer.toString(localDate.getSecond());
            default:
                throw new DateTimeException("Check your input!");
        }
    }

    /**
     * Верните Optional даты соответствующей дате в массиве.
     *
     * @param dateParams Дан массив данных состоящий из 3-х элементов, где:
     *                   - 1-й элемент массива - год;
     *                   - 2-й элемент массива - месяц;
     *                   - 3-й элемент массива - день (число);
     */
    public Optional<LocalDate> getDate(Integer[] dateParams)
            throws DateTimeException {
        if (dateParams.length == 3) {
            try {
                LocalDate localDate = LocalDate.of(dateParams[0],
                        dateParams[1], dateParams[2]);
                return Optional.of(localDate);
            } catch (DateTimeException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    /**
     * Дано время и на сколько часов нужно его изменить.
     * Верните измененное время на указаную величину.
     */
    public LocalTime addHours(LocalTime localTime, Integer hoursToAdd) {
        return localTime.plusHours(hoursToAdd);
    }

    /**
     * Дано время и на сколько минут нужно его изменить.
     * Верните измененное время на указаную величину.
     */
    public LocalTime addMinutes(LocalTime localTime, Integer minutesToAdd) {
        return localTime.plusMinutes(minutesToAdd);
    }

    /**
     * Дано время и на сколько секунд нужно его изменить.
     * Верните измененное время на указаную величину.
     */
    public LocalTime addSeconds(LocalTime localTime, Integer secondsToAdd) {
        return localTime.plusSeconds(secondsToAdd);
    }

    /**
     * Дана дата и на сколько недель нужно ее изменить.
     * Верните получившуюся дату
     */
    public LocalDate addWeeks(LocalDate localDate, Integer numberOfWeeks) {
        return localDate.plusWeeks(numberOfWeeks);
    }

    /**
     * Дана произвольная дата getDate.
     * Определите соотношение сегодня к someDate и верните строку:
     * - "someDate is after текущая дата" - если getDate в будующем
     * - "someDate is before текущая дата" - если getDate в прошлом
     * - "someDate is today" - если someDate - сегодня
     */
    public String beforeOrAfter(LocalDate someDate) {
        LocalDate localDate = LocalDate.now();
        return someDate.isAfter(localDate) ? someDate + " is after " + localDate
                : someDate.isBefore(localDate) ? someDate + " is before " + localDate
                : someDate.isEqual(localDate) ? someDate + " is today" : null;
    }

    /**
     * Даны две временные зоны.
     * Верните Optional часовой разницы между двумя временными зонами.
     *
     * @return Optional positive Integer
     */
    public Optional<Integer> diffBetweenZones(
            String firstZone, String secondZone) throws ZoneRulesException {
        try {
            LocalDateTime dt = LocalDateTime.now();
            ZonedDateTime firstZonedTime = dt.atZone(ZoneId.of(firstZone));
            ZonedDateTime secondZonedTime = dt.atZone(ZoneId.of(secondZone));
            return Optional.of((int) Math.abs(Duration.between(
                    firstZonedTime, secondZonedTime).toHours()));
        } catch (ZoneRulesException e) {
            return Optional.empty();
        }
    }

    /**
     * Данны дата и время. Надо вернуть дату и время с местным временным смещением,
     * пусть это будет для Украины "+02:00".
     * Приведем пример: при вызове метода передается переменная типа LocalDateTime,
     * в формате "2019-09-06T13:17", нам надо вернуть переменную типа OffsetDateTime,
     * в формате "2019-09-06T13:17+02:00", где "+02:00" и будет смещение для нашей
     * временной зоны.
     * OffsetDateTime советуют использовать при записи даты в базу данных.
     */
    public OffsetDateTime offsetDateTime(LocalDateTime localTime) {
        final String zone = "Europe/Warsaw";
        return OffsetDateTime.of(localTime, ZoneId.of(zone)
                .getRules().getOffset(Instant.now()));
    }

    /**
     * Дана строка в виде "yyyymmdd".
     * Необходимо вернуть Optional даты в LocalDate формате
     */
    public Optional<LocalDate> parseDate(String date) throws DateTimeParseException {
        try {
            final DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyyMMdd");
            return Optional.of(LocalDate.parse(
                    date, dateTimeFormatter));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    /**
     * Дана строка в виде "d MMM yyyy" (MMM - Sep, Oct, etc).
     * Необходимо вернуть Optional даты в LocalDate формате
     */
    public Optional<LocalDate> customParseDate(String date)
            throws DateTimeParseException {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                    .ofPattern("dd MMM yyyy", Locale.US);
            return Optional.of(LocalDate.parse(date, dateTimeFormatter));
        } catch (DateTimeException e) {
            return Optional.empty();
        }
    }

    /**
     * Даны произвольные время и дата.
     * Верните строку с датой и временем в формате
     * "день(2 цифры) месяц(полное название на английском) год(4 цифры) час(24 часа):минуты",
     * например: "01 January 2000 18:00",
     * или сообщение "dateTime can't be formatted!"
     */
    public String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
                "dd LLLL yyyy HH:mm", Locale.ENGLISH);
        return dateTime.format(dateTimeFormatter);
    }
}
