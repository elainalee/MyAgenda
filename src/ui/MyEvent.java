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
    //EFFECTS: takes in strings and turns it into Date
    public Date MakeDate(String date, String time) throws ParseException {
        SimpleDateFormat takenInFormat = new SimpleDateFormat("yyyy/MM/dd H");
        Date eventDate = takenInFormat.parse(date+" "+time);
        SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a'>' MMM dd, yyyy");
        // changes eventDate to String in Printform
        String String_eventDateinPrintform = datePrintform.format(eventDate);
        // changes eventDate String to eventDate Date
        Date eventDateinPrintform = datePrintform.parse(String_eventDateinPrintform);
        return eventDateinPrintform;
    }

    public void SetContext(String context) {this.context = context;}
    public void SetPlace(String place) {this.place = place;}
    public void SetDate(Date date) {this.date = date;}


    public String ContextIs() {
        return context;
    }
    public String PlaceIs() {
        return place;
    }
    public Date DateIs() { return date; }

    public String toString() {
        SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a'>' MMM dd, yyyy");
        return datePrintform.format(date) + ": " + context + " at " + place;
    }
}
