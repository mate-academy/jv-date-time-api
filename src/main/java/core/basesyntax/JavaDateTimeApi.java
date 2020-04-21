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
    private static final int DAYS_OF_MONTH = 31;
    private static final int MONTHS_OF_YEAR = 12;
    private static final DateTimeFormatter DATE_FORMAT
            = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter DATE_TIME_FORMAT
            = DateTimeFormatter.ofPattern("dd MMMM yyyy k:m", Locale.ENGLISH);
    private static final int UKR_ZONE = 2;

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

    public String todayDate(DateTimePart datePart) {
        final LocalDate localDate = LocalDate.now();
        switch (datePart) {
            case FULL:
                return localDate.toString();
            case DAY:
                return String.valueOf(localDate.getDayOfMonth());
            case MONTH:
                return localDate.getMonth().name();
            case YEAR:
                return String.valueOf(localDate.getYear());
            default:
                throw new DateTimeException("Incorrect value: " + datePart);
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
        if (dateParams.length == 0 || dateParams[1] > MONTHS_OF_YEAR || dateParams[1] < 1
                || dateParams[2] > DAYS_OF_MONTH || dateParams[2] < 1) {
            return Optional.empty();
        }
        return Optional.of(LocalDate.of(dateParams[0], dateParams[1], dateParams[2]));
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
        final LocalDate localDate = LocalDate.now();
        return localDate.isAfter(someDate) ? someDate + " is before " + localDate
                : localDate.isBefore(someDate)
                ? someDate + " is after " + localDate : someDate + " is today";
    }

    /**
     * localDate + " is after " + someDate
     * Дана дата в строковом формате и временная зона.
     * Верните LocalDateTime в этой временной зоне.
     *
     * @return LocalDateTime
     */
    public LocalDateTime getDateInSpecificTimeZone(String dateInString, String zone) {
        return Instant.parse(dateInString).atZone(ZoneId.of(zone)).toLocalDateTime();
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
        return OffsetDateTime.of(localTime,
                ZoneOffset.ofHours(UKR_ZONE));
    }

    /**
     * Дана строка в виде "yyyymmdd".
     * Необходимо вернуть Optional даты в LocalDate формате
     */
    public Optional<LocalDate> parseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE));
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Дана строка в виде "d MMM yyyy" (MMM - Sep, Oct, etc).
     * Необходимо вернуть Optional даты в LocalDate формате
     */
    public Optional<LocalDate> customParseDate(String date) {
        try {
            return Optional.of(LocalDate.parse(date, DATE_FORMAT));
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Даны произвольные время и дата.
     * Верните строку с датой и временем в формате
     * "день(2 цифры) месяц(полное название на английском) год(4 цифры) час(24 часа):минуты",
     * например: "01 January 2000 18:00",
     * или сообщение "dateTime can't be formatted!"
     */
    public String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMAT);
    }
}
