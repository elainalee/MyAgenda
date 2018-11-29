package ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TheEvent {
    protected String context;
    protected String description;
    protected String time;
    SimpleDateFormat takenInFormat = new SimpleDateFormat("h");

    public void setContext(String context) {
        this.context = context;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) throws ParseException {
        Date eventTime = takenInFormat.parse(time);
        this.time = time;}

    public String contextIs() {return context;}

    public String descriptionIs() {return description;}

    public String timeIs() {return time;}
}
