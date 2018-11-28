package ui;

import observer.Subject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class MyEvent extends Subject{
    String context;
    Date date;
    private List<User> users = new ArrayList<>();


    public void addUser(User user) {
        if (!(users.contains(user))) {
            users.add(user);
            user.addEvent(this);
            addObserver(user);
            notifyObservers();
        }
    }


    public void SetContext(String context) {this.context = context;}

    public void SetDate(Date date) {this.date = date;}

    public String ContextIs() {return context;}

    public Date DateIs() {return date;}

    public abstract String toString();

}
