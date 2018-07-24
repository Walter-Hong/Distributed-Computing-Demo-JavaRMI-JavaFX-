package sample.util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    private static final String Pattern = "yyyy-MM-dd";

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Pattern);

    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }

        return dateFormatter.format(date);
    }

    public static LocalDate parse(String dateString) {
        try {
            return dateFormatter.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static boolean validDate(String dateString) {
        return DateUtil.parse(dateString) != null;
    }
}
