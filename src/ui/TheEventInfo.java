package ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TheEventInfo {
    protected String context;
    protected String description;
    protected Date date;
    protected Date time;
    SimpleDateFormat takenInFormat_date = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat takenInFormat_time = new SimpleDateFormat("h");
    SimpleDateFormat datePrintform = new SimpleDateFormat("MMM dd, yyyy");


    public void setContext(String context) {this.context = context;}

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) throws ParseException {
        Date eventTime = takenInFormat_time.parse(time);
        this.time = eventTime;
    }

    public void setDate(String date) throws ParseException {
        Date eventDate = takenInFormat_date.parse(date);
        this.date = eventDate;}

    public String descriptionIs() {return description;}

    public String dateIs() {
        String String_dateinPrintform = datePrintform.format(date);
        return String_dateinPrintform;
    }

    public String timeIs() {
        String String_timeinPrintform = takenInFormat_time.format(time);
        return String_timeinPrintform;
    }
}
