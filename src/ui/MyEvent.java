package ui;

import java.util.*;

public class MyEvent {

    String context;
    String date;
    String place;

    public void SetContext(String context) {
        this.context = context;
    }

    public void SetPlace(String place) {
        this.place = place;
    }

    public void SetDate(String date) {
        this.date = date;
    }

    public String toString(){
        return context + " " + "is happening at" + " " + place + " " + "on" + " " + date;
    }

}
