package ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class MyPersonalEvent extends MyEvent{
    String place;
    MyAgenda myAgenda;

    public void SetPersonalEvent(String context, Date date, String place) {
        super.SetContext(context);
        super.SetDate(date);
        this.place = place;
    }

    public void SetPlace(String place) {this.place = place;}

    public String PlaceIs() {return place;}



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyPersonalEvent that = (MyPersonalEvent) o;
        return Objects.equals(place, that.place) && that.context.equals(this.context) && that.date.equals(this.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(place);
    }

    @Override
    public String toString() {
        SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a'>' MMM dd, yyyy");
        return datePrintform.format(date) + ": " + context + " at " + place;
    }
}
