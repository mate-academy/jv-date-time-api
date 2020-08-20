package core.basesyntax;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

public class JavaDateTimeApiTest {

    private JavaDateTimeApi javaDateTimeApi = new JavaDateTimeApi();
    private static final LocalDateTime LOCAL_DATE_TIME =
            LocalDateTime.of(2000, 12, 31, 23, 59, 59);

    @Test
    public void todayDateFull() {
        String expected = String.valueOf(LocalDate.now());
        String result = javaDateTimeApi.todayDate(DateTimePart.FULL);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getDate() {
        Integer[] dateParams = {2018, 12, 21};
        Optional<LocalDate> expected =
                Optional.of(LocalDate.of(2018, 12, 21));
        Optional<LocalDate> result = javaDateTimeApi.getDate(dateParams);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getDateIncorrect() {
        Integer[] dateParams = {2018, 22, 441};
        Optional<LocalDate> expected = Optional.empty();
        Optional<LocalDate> result = javaDateTimeApi.getDate(dateParams);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getDateEmpty() {
        Integer[] dateParams = {};
        Optional<LocalDate> expected = Optional.empty();
        Optional<LocalDate> result = javaDateTimeApi.getDate(dateParams);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void addHours() {
        LocalTime expected = LocalTime.of(3, 59, 59);
        LocalTime result = javaDateTimeApi.addHours(LOCAL_DATE_TIME.toLocalTime(), 268);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void addMinutes() {
        LocalTime expected = LocalTime.of(9, 48, 59);
        LocalTime result = javaDateTimeApi.addMinutes(LOCAL_DATE_TIME.toLocalTime(), 589);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void addSeconds() {
        LocalTime expected = LocalTime.of(6, 39, 48);
        LocalTime result = javaDateTimeApi.addSeconds(LOCAL_DATE_TIME.toLocalTime(), 369589);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void addWeeks() {
        LocalDate expected = LocalDate.of(2001, 1, 14);
        LocalDate result = javaDateTimeApi.addWeeks(LOCAL_DATE_TIME.toLocalDate(), 2);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void beforeDate() {
        LocalDate someDate = LocalDate.of(2019, 9, 3);
        String expected = someDate + " is before " + LocalDate.now();
        String result = javaDateTimeApi.beforeOrAfter(someDate);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void afterDate() {
        LocalDate someDate = LocalDate.now().plusMonths(2);
        String expected = someDate + " is after " + LocalDate.now();
        String result = javaDateTimeApi.beforeOrAfter(someDate);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void nowDate() {
        LocalDate someDate = LocalDate.now();
        String expected = someDate + " is today";
        String result = javaDateTimeApi.beforeOrAfter(someDate);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getDateInSpecificTimeZone() {
        LocalDateTime expectedResult1 = LocalDateTime.parse("2020-04-17T00:23:01");
        LocalDateTime actualResult1 = javaDateTimeApi
                .getDateInSpecificTimeZone("2020-04-16T15:23:01Z", "Asia/Tokyo");
        Assert.assertEquals(expectedResult1, actualResult1);

        LocalDateTime expectedResult2 = LocalDateTime.parse("2020-04-16T18:23:01");
        LocalDateTime actualResult2 = javaDateTimeApi
                .getDateInSpecificTimeZone("2020-04-16T15:23:01Z", "Europe/Athens");
        Assert.assertEquals(expectedResult2, actualResult2);
    }

    @Test
    public void offsetUkraine() {
        LocalDateTime localDateTime =
                LocalDateTime.of(2019, Month.SEPTEMBER, 06, 13, 17);
        OffsetDateTime expected = OffsetDateTime.of(localDateTime, ZoneOffset.of("+02:00"));
        OffsetDateTime result = javaDateTimeApi.offsetDateTime(localDateTime);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void parseDate() {
        LocalDate localDate = LocalDate.of(2019, 9, 21);
        Optional<LocalDate> expected = Optional.of(localDate);
        Optional<LocalDate> result = javaDateTimeApi.parseDate("20190921");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void parseDateIncorrect() {
        Optional<LocalDate> expected = Optional.empty();
        Optional<LocalDate> result = javaDateTimeApi.parseDate("20193921");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void customParseDate() {
        Optional<LocalDate> expected = Optional.of(LocalDate.of(2019, 9, 6));
        Optional<LocalDate> result = javaDateTimeApi.customParseDate("06 Sep 2019");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void customParseDateIncorrect() {
        Optional<LocalDate> expected = Optional.empty();
        Optional<LocalDate> result = javaDateTimeApi.customParseDate("36 Sep 2019");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void formatDate() {
        String expected = "06 September 2019 16:15";
        LocalDateTime localDateTime = LocalDateTime.of(2019, 9, 6, 16, 15, 26);
        String result = javaDateTimeApi.formatDate(localDateTime);
        Assert.assertEquals(expected, result);
    }
}
