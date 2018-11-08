package ui;

import Model.Event;

import java.util.Date;

public abstract class MyEvent implements Event{
    MyEvent myEvent;
    String context;
    Date date;


    public void SetContext(String context) {this.context = context;}

    public void SetDate(Date date) {this.date = date;}

    public String ContextIs() {return context;}

    public Date DateIs() {return date;}

    public abstract String toString();

}
