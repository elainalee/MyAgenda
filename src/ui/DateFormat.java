package ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    SimpleDateFormat takenInFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a'>' MMM dd, yyyy");

    public Date MakeDate(String date, String time) throws ParseException {
        SimpleDateFormat takenInFormat = new SimpleDateFormat("yyyy/MM/dd H");
        Date eventDate = takenInFormat.parse(date+" "+time);
        return eventDate;
    }

    public String DatetoStringPrintform (Date date) {
        // changes eventDate to String in Printform
        String String_dateinPrintform = datePrintform.format(date);
        return String_dateinPrintform;
    }

    public String DatetoStringTakenform (Date date) {
        // changes eventDate to String in Printform
        String String_dateinTakenform = takenInFormat.format(date);
        return String_dateinTakenform;
    }

    public Date StringtakenFormtoDate (String string) throws ParseException {
        Date DateinTakenform = takenInFormat.parse(string);
        return DateinTakenform;
    }

    public Date StringprintFormtoDate (String string) throws ParseException {
        Date DateinPrintform = datePrintform.parse(string);
        return DateinPrintform;
    }

    //EFFECTS: check if the date is in correct date form
    public void checkIfDateForm (String date) throws ParseException {
        Date dateReceived = takenInFormat.parse(date);
    }

    //EFFECTS: check if the time is in correct time form
    public void checkIfTimeForm (String time) throws ParseException {
        SimpleDateFormat takenInFormat = new SimpleDateFormat("H");
        Date timeReceived = takenInFormat.parse(time);
    }
}
