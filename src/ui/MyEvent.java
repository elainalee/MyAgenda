package ui;

import Model.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyEvent implements Event{
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
        return eventDate;
    }

    public String DatetoStringPrintform (Date date) {
        SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a'>' MMM dd, yyyy");
        // changes eventDate to String in Printform
        String String_dateinPrintform = datePrintform.format(date);
        return String_dateinPrintform;
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
