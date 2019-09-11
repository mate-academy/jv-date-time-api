package core.basesyntax;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.zone.ZoneRulesException;
import java.util.Locale;
import java.util.Optional;

public class JavaDateTimeApi {
    private static final DateTimeFormatter FORMATTER
            = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.UK);
    private static final DateTimeFormatter FORMATTER_PLUS_TIME
            = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm", Locale.UK);
    private static final String OUR_TIME_ZONE = "+02:00";

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
    public static String todayDate(DateTimePart datePart) {
        LocalDate localDate = LocalDate.now();
        if (DateTimePart.YEAR.equals(datePart)) {
            return String.valueOf(localDate.getYear());
        }
        if (DateTimePart.MONTH.equals(datePart)) {
            return String.valueOf(localDate.getMonth());
        }
        if (DateTimePart.DAY.equals(datePart)) {
            return String.valueOf(localDate.getDayOfMonth());
        }
        return localDate.toString();
    }

    /**
     * Верните Optional даты соответствующей дате в массиве.
     *
     * @param dateParams Дан массив данных состоящий из 3-х элементов, где:
     *                   - 1-й элемент массива - год;
     *                   - 2-й элемент массива - месяц;
     *                   - 3-й элемент массива - день (число);
     */
    public Optional<LocalDate> getDate(Integer[] dateParams) {
        if (dateParams.length != 3
                || dateParams[1] > 12
                || dateParams[2] > 31
                || dateParams[0] > 2200) {
            return Optional.empty();
        }
        return Optional.ofNullable(LocalDate.of(dateParams[0], dateParams[1], dateParams[2]));
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
        if (localDate.equals(someDate)) {
            return someDate + " is today";
        }
        if (localDate.isAfter(someDate)) {
            return someDate + " is before " + localDate;
        }
        if (localDate.isBefore(someDate)) {
            return someDate + " is after " + localDate;
        }
        return someDate + " Entered incorrect data";
    }

    /**
     * Даны две временные зоны.
     * Верните Optional часовой разницы между двумя временными зонами.
     *
     * @return Optional positive Integer
     */
    public Optional<Integer> diffBetweenZones(String firstZone, String secondZone) {
        int hourInFirstZone;
        int hourInSecondZone;
        try {
            hourInFirstZone = ZonedDateTime.now(ZoneId.of(firstZone)).getHour();
            hourInSecondZone = ZonedDateTime.now(ZoneId.of(secondZone)).getHour();
        } catch (ZoneRulesException e) {
            return Optional.empty();
        }

        return Optional.of(Math.abs(hourInFirstZone - hourInSecondZone));
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
        return OffsetDateTime.of(localTime, ZoneOffset.of(OUR_TIME_ZONE));
    }

    /**
     * Дана строка в виде "yyyymmdd".
     * Необходимо вернуть Optional даты в LocalDate формате
     */
    public Optional<LocalDate> parseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, DateTimeFormatter
                    .ofPattern("yyyyMMdd", Locale.UK)));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    /**
     * Дана строка в виде "d MMM yyyy" (MMM - Sep, Oct, etc).
     * Необходимо вернуть Optional даты в LocalDate формате
     */
    public Optional<LocalDate> customParseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, FORMATTER));
        } catch (DateTimeParseException e) {
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
        try {
            return dateTime.format(FORMATTER_PLUS_TIME);
        } catch (DateTimeParseException e) {
            return "Date can't be formatted!";
        }
    }
}
