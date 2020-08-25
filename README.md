# jv-data-time-api

1. Return the current date as a String depending on a query.
   
   The query can be passed for the whole date or for it's part:
     - FULL - current date as a whole: year, month, day of month 
       formatted as `YYYY-MM-DD` (a default return value);
     - YEAR - current year;
     - MONTH - name of the current month;
     - DAY - current day of month;
   In any other case throw DateTimeException.

2. Given an Array of 3 elements, where
        - 1-st element is a `year`;
        - 2-nd element is s `month`;
        - 3-rd element is a `day of month`;
   
   Return Optional of a date built from these elements.

3. Given the time and the number of hours to add, return the changed time.
   
4. Given the time and the number of minutes to add, return the changed time.

5. Given the time and the number of seconds to add, return the changed time.

6. Given the date and the number of weeks to add, return the changed date.

7. Given a random `someDate` date, return one of the following Strings:
        - "`someDate` is after `currentDate`" - if `someDate` is in the future relating to the `current date`;
        - "`someDate` is before `currentDate`" - if `someDate` is in the past relating to the `current date`;
        - "`someDate` is today" - if `someDate` is today;
        
8. Given a String representation of a date and some timezone, return LocalDateTime in this timezone.
   
9. Given some LocalDateTime, return an OffsetDateTime with the local time offset applied (`+02:00` for Ukraine).  
   
   Example: we receive a LocalDateTime with a value `2019-09-06T13:17`.  
            We should return the OffsetDateTime with a value `2019-09-06T13:17+02:00`,  
            where `+02:00` is the offset for our local timezone.

   OffsetDateTime is recommended to use for storing date values in a database.  

10. Given a String formatted as `yyyymmdd`,
    return Optional of this date as a LocalDate.

11. Given a String formatted as `d MMM yyyy` (MMM - Sep, Oct, etc),
    return Optional of this date as a LocalDate.

12. Given some LocalDateTime, return a String formatted as  
    `day(2-digit) month(full name in English) year(4-digit) hours(24-hour format):minutes`.  
    
    Example: "01 January 2000 18:00".
