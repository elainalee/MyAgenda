package ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyEvent {
    MyEvent myEvent;
    String context;
    String place;
    Date date;

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: sets the context of the event
    public void SetContext(String context) { this.context = context; }

    public String ContextIs() {
        return context;
    }

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: sets the place of the event
    public void SetPlace(String place) {
        this.place = place;
    }

    public String PlaceIs() {
        return place;
    }

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: sets the date of the event
    public void SetDate(String date, String time) throws ParseException {
        SimpleDateFormat takenInFormat = new SimpleDateFormat("yyyy/MM/dd H");
        Date eventDate = takenInFormat.parse(date+" "+time);
        SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a '>' MMM dd, yyyy");
        // changes eventDate to String in Printform
        String String_eventDateinPrintform = datePrintform.format(eventDate);
        // changes eventDate String to eventDate Date
        Date eventDateinPrintform = datePrintform.parse(String_eventDateinPrintform);
        this.date = eventDateinPrintform;
    }


    public Date DateIs() { return date; }

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: returns all the details of the event
    public String toString() {
        SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a '>' MMM dd, yyyy");
        return datePrintform.format(date) + ": " + context + " at " + place;
    }
}
