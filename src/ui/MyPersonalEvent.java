package ui;

import java.text.SimpleDateFormat;

public class MyPersonalEvent extends MyEvent{
    String place;

    public void SetPlace(String place) {this.place = place;}

    public String PlaceIs() {return place;}

    @Override
    public String toString() {
        SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a'>' MMM dd, yyyy");
        return datePrintform.format(date) + ": " + context + " at " + place;
    }
}
