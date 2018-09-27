package ui;

import java.util.*;

public class MyEvent {

    String context;
    String date;
    String place;

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: sets the context of the event
    public void SetContext(String context) {
        this.context = context;
    }

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: sets the place of the event
    public void SetPlace(String place) {
        this.place = place;
    }

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: sets the date of the event
    public void SetDate(String date) {
        this.date = date;
    }

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: returns all the details of the event
    public String toString(){
        return date + ": " + context + " is at " + place;
    }

}
