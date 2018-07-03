package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeParser {

    /**
     * This method parses a String to a Date Object, the
     * format has been defined for the {@link resources.NewsfeedResource}.
     * @param dateString String to be parsed to a Date
     * @return the parsed Date
     * @throws ParseException
     */
    public static Date parseNewsfeedDate(String dateString) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newsfeedDate = df.parse((dateString));
        return newsfeedDate;
    }

}
