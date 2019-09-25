package core.basesyntax;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

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
import java.time.zone.ZoneRulesException;
import java.util.NoSuchElementException;
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
     *                 В любом другом случае бросить DateTimeException
     **/

    private static final String UKR_ZONE = "+02:00";
    private static final DateTimeFormatter CUSTOM_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy");
    private static final DateTimeFormatter CUSTOM_FORMATTER_WITH_TIME =
            DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");

    public String todayDate(DateTimePart datePart) {
        switch (datePart) {
            case FULL:
                return String.valueOf(LocalDate.now());
            case YEAR:
                return String.valueOf(LocalDate.now().getYear());
            case MONTH:
                return String.valueOf(LocalDate.now().getMonth());
            case DAY:
                return String.valueOf(LocalDate.now().getDayOfMonth());
            default: return null;
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
        if (dateParams == null || dateParams.length < 3) {
            return Optional.empty();
        }
        LocalDate localDate;
        try {
            localDate = LocalDate.of(dateParams[0], dateParams[1], dateParams[2]);
        } catch (DateTimeException dte) {
            return Optional.empty();
        }
        return Optional.of(localDate);
    }

    /**
     * Дано время и на сколько часов нужно его изменить.
     * Верните измененное время на указаную величину.
     */
    public LocalTime addHours(LocalTime localTime, Integer hoursToAdd) {
        if (localTime == null || hoursToAdd == null) {
            throw new NoSuchElementException();
        }
        return localTime.plusHours(hoursToAdd);
    }

    /**
     * Дано время и на сколько минут нужно его изменить.
     * Верните измененное время на указаную величину.
     */
    public LocalTime addMinutes(LocalTime localTime, Integer minutesToAdd) {
        if (localTime == null || minutesToAdd == null) {
            throw new NoSuchElementException();
        }
        return localTime.plusMinutes(minutesToAdd);
    }

    /**
     * Дано время и на сколько секунд нужно его изменить.
     * Верните измененное время на указаную величину.
     */
    public LocalTime addSeconds(LocalTime localTime, Integer secondsToAdd) {
        if (localTime == null || secondsToAdd == null) {
            throw new NoSuchElementException();
        }
        return localTime.plusSeconds(secondsToAdd);
    }

    /**
     * Дана дата и на сколько недель нужно ее изменить.
     * Верните получившуюся дату
     */
    public LocalDate addWeeks(LocalDate localDate, Integer numberOfWeeks) {
        if (localDate == null || numberOfWeeks == null) {
            throw new NoSuchElementException();
        }
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
        LocalDate currentDate = LocalDate.now();
        if (someDate == null) {
            throw new NoSuchElementException();
        }
        if (someDate.isAfter(currentDate)) {
            return someDate + " is after " + currentDate;
        }
        if (someDate.isBefore(currentDate)) {
            return someDate + " is before " + currentDate;
        }
        return someDate + " is today";
    }

    /**
     * Даны две временные зоны.
     * Верните Optional часовой разницы между двумя временными зонами.
     * @return Optional positive Integer
     */
    public Optional<Integer> diffBetweenZones(String firstZone, String secondZone) {
        if (firstZone == null || secondZone == null) {
            return Optional.empty();
        }
        ZonedDateTime firstZona;
        ZonedDateTime secondZona;
        try {
            firstZona = ZonedDateTime.now(ZoneId.of(firstZone));
            secondZona = ZonedDateTime.now(ZoneId.of(secondZone));
        } catch (ZoneRulesException zre) {
            return Optional.empty();
        }
        return Optional.of(Math.abs(firstZona.getHour() - secondZona.getHour()));
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
        if (localTime == null) {
            throw new NoSuchElementException();
        }
        return OffsetDateTime.of(localTime, ZoneOffset.of(UKR_ZONE));
    }

    /**
     * Дана строка в виде "yyyymmdd".
     * Необходимо вернуть Optional даты в LocalDate формате
     */
    public Optional<LocalDate> parseDate(String date) {
        if (date == null) {
            throw new NoSuchElementException();
        }
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date, BASIC_ISO_DATE);
        } catch (DateTimeParseException dtpe) {
            return Optional.empty();
        }
        return Optional.of(localDate);
    }

    /**
     * Дана строка в виде "d MMM yyyy" (MMM - Sep, Oct, etc).
     * Необходимо вернуть Optional даты в LocalDate формате
     */
    public Optional<LocalDate> customParseDate(String date) {
        if (date == null) {
            throw new NoSuchElementException();
        }
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date, CUSTOM_FORMATTER);
        } catch (DateTimeParseException dtpe) {
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
        if (dateTime == null) {
            throw new NoSuchElementException();
        }
        return dateTime.format(CUSTOM_FORMATTER_WITH_TIME);
    }
}
