package observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    protected List<EventObserver> observers = new ArrayList<>();

    public void addObserver(EventObserver o) {
        if (!(observers.contains(o))) {
            observers.add(o);
        }
    }

    public void notifyObservers() {
        for (EventObserver observer : observers) {
            observer.update();
        }
    }

}

