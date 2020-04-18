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

public class JavaDateTimeApi {
    /**
     * Верните текущую дату в виде строки в зависимости от запроса.
     *
     * @param datePart Запрос на часть даты или всю дата целиком:
     * - FULL - текущая дата целиком год, месяц, день (число месяца)
     * в формате YYYY-MM-DD, возвращаемое значение по умолчанию;
     * - YEAR - текущий год;
     * - MONTH - название текущего месяца;
     * - DAY - текущий день (число месяца);
     * В любом другом случае бросить DateTimeException
     **/
    private static final DateTimeFormatter FULL = DateTimeFormatter
            .ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter YYYY = DateTimeFormatter
            .ofPattern("yyyy");
    private static final DateTimeFormatter MM = DateTimeFormatter
            .ofPattern("MM");
    private static final DateTimeFormatter DD = DateTimeFormatter
            .ofPattern("dd");
    private static final String UA_OFFSET = "+02:00";
    private static final DateTimeFormatter DD_MMMM_YYYY_HH_MM = DateTimeFormatter
            .ofPattern("dd MMMM YYYY HH:mm", Locale.ENGLISH);
    private static final DateTimeFormatter D_MMM_YYYY = DateTimeFormatter
            .ofPattern("d MMM yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter YYYYMMDD = DateTimeFormatter
            .ofPattern("yyyyMMdd");

    public String todayDate(DateTimePart datePart) {
        LocalDate today = LocalDate.now();
        switch (datePart) {
            case FULL:
                return today.format(FULL);
            case YEAR:
                return today.format(YYYY);
            case MONTH:
                return today.format(MM);
            case DAY:
                return today.format(DD);
            default:
                throw new DateTimeException("Wrong date!");
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
        if (dateParams.length == 0) {
            return Optional.empty();
        }
        try {
            return Optional.of(LocalDate.of(dateParams[0], dateParams[1], dateParams[2]));
        } catch (DateTimeException e) {
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
        return someDate.isAfter(today) ? someDate + " is after " + today
                : someDate.isBefore(today) ? someDate + " is before " + today
                : someDate + " is today";
    }

    /**
     * Дана дата в строковом формате и временная зона.
     * Верните LocalDateTime в этой временной зоне.
     *
     * @return LocalDateTime
     */
    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        ZoneId zoneId = ZoneId.of(zone);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateInString).withZoneSameInstant(zoneId);
        return zonedDateTime.toLocalDateTime();
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
        return OffsetDateTime.of(localTime, ZoneOffset.of(UA_OFFSET));
    }

    /**
     * Дана строка в виде "yyyymmdd".
     * Необходимо вернуть Optional даты в LocalDate формате
     */
    public Optional<LocalDate> parseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, YYYYMMDD));
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
            return Optional.of(LocalDate.parse(date, D_MMM_YYYY));
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
            return dateTime.format(DD_MMMM_YYYY_HH_MM);
        } catch (Exception e) {
            throw new DateTimeException("dateTime can't be formatted!");
        }
    }
}
