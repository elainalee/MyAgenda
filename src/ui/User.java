package ui;

import observer.EventObserver;

import java.util.ArrayList;
import java.util.List;

public class User implements EventObserver {
    private String name;
    private List<MyEvent> myEvents = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    public void addEvent(MyEvent me) {
        if (!(myEvents.contains(me))) {
            myEvents.add(me);
            me.addUser(this);
        }
    }

    @Override
    public void update() {
        System.out.println("To "+name+ " : An event has been added.");

    }
}
