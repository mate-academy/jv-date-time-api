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
import java.time.temporal.ChronoUnit;
import java.time.zone.ZoneRulesException;
import java.util.Locale;
import java.util.Optional;

public class JavaDateTimeApi {
    private static final String UKR_OFFSET = "+02:00";
    private static final String FORMATTER = "yyyyMMdd";
    private static final String FULL_DATE = "dd MMM yyyy";
    private static final String DATE_TIME = "dd MMMM yyyy HH:mm";

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
        LocalDate localDate = LocalDate.now();
        String formattedDate = "";
        if (datePart == DateTimePart.FULL) {
            formattedDate =
                    localDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
        }
        if (datePart == DateTimePart.YEAR) {
            formattedDate =
                    localDate.format(DateTimeFormatter.ofPattern("YYYY"));
        }
        if (datePart == DateTimePart.MONTH) {
            formattedDate =
                    localDate.format(DateTimeFormatter.ofPattern("MM"));
        }
        if (datePart == DateTimePart.DAY) {
            formattedDate =
                    localDate.format(DateTimeFormatter.ofPattern("dd"));
        }
        return formattedDate;
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
        LocalDate localDate;
        if (dateParams == null || dateParams.length != 3) {
            return Optional.empty();
        }
        try {
            localDate = LocalDate.of(dateParams[0], dateParams[1], dateParams[2]);
        } catch (DateTimeException e) {
            return Optional.empty();
        }
        return Optional.of(localDate);
    }

    /**
     * Дано время и на сколько часов нужно его изменить.
     * Верните измененное время на указаную величину.
     */
    public LocalTime addHours(LocalTime localTime, Integer hoursToAdd) {
        return localTime.plus(hoursToAdd, ChronoUnit.HOURS);
        //return LocalTime.now();
    }

    /**
     * Дано время и на сколько минут нужно его изменить.
     * Верните измененное время на указаную величину.
     */
    public LocalTime addMinutes(LocalTime localTime, Integer minutesToAdd) {
        return localTime.plus(minutesToAdd, ChronoUnit.MINUTES);
    }

    /**
     * Дано время и на сколько секунд нужно его изменить.
     * Верните измененное время на указаную величину.
     */
    public LocalTime addSeconds(LocalTime localTime, Integer secondsToAdd) {
        return localTime.plus(secondsToAdd, ChronoUnit.SECONDS);
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
        String result = "";
        if (someDate.isBefore(LocalDate.now())) {
            result += someDate + " is before " + LocalDate.now();
        }
        if (someDate.isAfter(LocalDate.now())) {
            result += someDate + " is after " + LocalDate.now();
        }
        if (someDate.compareTo(LocalDate.now()) == 0) {
            result += someDate + " is today";
        }
        return result;
    }

    /**
     * Даны две временные зоны.
     * Верните Optional часовой разницы между двумя временными зонами.
     *
     * @return Optional positive Integer
     */
    public Optional<Integer> diffBetweenZones(String firstZone, String secondZone) {
        try {
            ZonedDateTime zoneOne = ZonedDateTime.now(ZoneId.of(firstZone));
            ZonedDateTime zoneTwo = ZonedDateTime.now(ZoneId.of(secondZone));
            return Optional.of(Math.abs(zoneOne.getHour() - zoneTwo.getHour()));
        } catch (ZoneRulesException e) {
            System.out.println("There is no such zone.");
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
        return OffsetDateTime.of(localTime, ZoneOffset.of(UKR_OFFSET));
    }

    /**
     * Дана строка в виде "yyyymmdd".
     * Необходимо вернуть Optional даты в LocalDate формате
     */
    public Optional<LocalDate> parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATTER);
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
        return Optional.of(localDate);
    }

    /**
     * Дана строка в виде "d MMM yyyy" (MMM - Sep, Oct, etc).
     * Необходимо вернуть Optional даты в LocalDate формате
     */
    public Optional<LocalDate> customParseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FULL_DATE, Locale.UK);
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
        return Optional.of(localDate);
    }

    /**
     * Даны произвольные время и дата.
     * Верните строку с датой и временем в формате
     * "день(2 цифры) месяц(полное название на английском) год(4 цифры) час(24 часа):минуты",
     * например: "01 January 2000 18:00",
     * или сообщение "dateTime can't be formatted!"
     */
    public String formatDate(LocalDateTime dateTime) {
        //return dateTime.format(DateTimeFormatter.ofPattern(dateTime));
        try {
            return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME, Locale.UK));
        } catch (Exception e) {
            return "Date can't be formatted!";
        }
    }
}
