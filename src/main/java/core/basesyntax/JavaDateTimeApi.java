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
import java.util.Locale;
import java.util.Optional;

public class  JavaDateTimeApi {
    /**
     * Верните текущую дату в виде строки в зависимости от запроса.
     *
     * @param datePart Запрос на часть даты или всю дата целиком:
     *                 - FULL - текущая дата целиком год, месяц, день (число месяца)
     *                 в формате YYYY-MM-DD, возвращаемое значение по умолчанию;
     *                 - YEAR - текущий год;
     *                 - MONTH - название текущего месяца;
     *                 - DAY - текущий день (число месяца);
     *                 В любом другом случае бросить DateTimeException
     **/
    public static final String TIME = "+02:00";
    public static final DateTimeFormatter FORMATTER_FIRST = DateTimeFormatter
            .ofPattern("d MMM yyyy", Locale.ENGLISH);
    public static final DateTimeFormatter FORMATTER_SECOND = DateTimeFormatter
            .ofPattern("dd MMMM yyyy k:mm", Locale.ENGLISH);

    public String todayDate(DateTimePart datePart) {
        LocalDate currentDate = LocalDate.now();
        switch (datePart) {
            case FULL: return currentDate.toString();
            case YEAR: return String.valueOf(currentDate.getYear());
            case MONTH: return currentDate.getMonth().toString();
            case DAY: return String.valueOf(currentDate.getDayOfMonth());
            default: throw new DateTimeException("Wrong DateTimePart!");
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
    public Optional<LocalDate> getDate(Integer[] dateParams) {
        try {
            return Optional.of(LocalDate.of(dateParams[0], dateParams[1], dateParams[2]));
        } catch (ArrayIndexOutOfBoundsException | DateTimeException e) {
            return Optional.empty();
        }
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
        LocalDate today = LocalDate.now();
        return someDate.isBefore(today) ? someDate.toString() + " is before " + today.toString()
                : someDate.isEqual(today) ? someDate.toString() + " is today"
                : someDate.toString() + " is after " + today.toString();
    }

    /**
     * Даны две временные зоны.
     * Верните Optional часовой разницы между двумя временными зонами.
     * @return Optional positive Integer
     */
    public Optional<Integer> diffBetweenZones(String firstZone, String secondZone) {
        try {
            int firstZoneHours = ZonedDateTime.now(ZoneId.of(firstZone)).getHour();
            int secondZoneHours = ZonedDateTime.now(ZoneId.of(secondZone)).getHour();
            return Optional.of(secondZoneHours - firstZoneHours);
        } catch (DateTimeException e) {
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
        return OffsetDateTime.of(localTime, ZoneOffset.of(TIME));
    }

    /**
     * Дана строка в виде "yyyymmdd".
     * Необходимо вернуть Optional даты в LocalDate формате
     */
    public Optional<LocalDate> parseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE));
        } catch (DateTimeException e) {
            return Optional.empty();
        }
    }

    /**
     * Дана строка в виде "d MMM yyyy" (MMM - Sep, Oct, etc).
     * Необходимо вернуть Optional даты в LocalDate формате
     */
    public Optional<LocalDate> customParseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, FORMATTER_FIRST));
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
        try {
            return dateTime.format(FORMATTER_SECOND);
        } catch (DateTimeException e) {
            return "Date can't be formatted!";
        }
    }
}
