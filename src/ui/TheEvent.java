package ui;

public class TheEvent {
    protected String context;
    protected String description;

    public void setContext(String context) {
        this.context = context;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String contextIs() {return context;}

    public String descriptionIs() {return description;}
}
