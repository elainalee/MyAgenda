package ui;

import Model.Saveable;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//copied from LoggingCalculator
public class MyAgenda implements Model.Agenda, Saveable{
    ArrayList<MyEvent> operationSchedule;
    Scanner scanner;

    public MyAgenda() {
        operationSchedule = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: nothing
    public void run() throws ParseException, IOException {
        String operation;
        load("TheSchedule");
        while (true) {
            System.out.println("what would you like to do? [1] add an event [2] delete an event [3] find an event [4] see the entire schedules.");
            System.out.println("If you are done with every operations, enter quit.");
            operation = scanner.nextLine();
            System.out.println("you selected: " + operation);

            if (operation.equals("1")) {
                AddEvent();}

            else if (operation.equals("2")) {
                DeleteEvent();}

            else if (operation.equals("3")) {
                FindEvent();}

            else if (operation.equals("4")) {
                PrintSchedule();}

            else if (operation.equals("quit")) {
                break;}

            else {System.out.println("you selected the wrong option.");}

        }
        System.out.println("Thank you for using the system.");}


    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: adds an event to the schedule
    @Override
    public void AddEvent() throws ParseException, IOException {
        MyEvent opEvent = new MyEvent();
        MyEvent result;
        result = MakeEvent(opEvent);
        operationSchedule.add(result);
        System.out.println("The event has been added.");
        save("TheSchedule");
    }

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: adds context, place, and date to myEvent, returns modified myEvent
    private MyEvent MakeEvent(MyEvent myEvent) throws ParseException {
        System.out.println("Please enter the context of event.");
        String first = scanner.next();
        System.out.println("Please enter the place of the event.");
        String second = scanner.next();
        String third = getDate();
        String four = getTime();
        scanner.nextLine(); //clears the line,
        // otherwise the carriage return is taken as the next input
        // and you get a blank "selected" loop
        myEvent.SetContext(first);
        myEvent.SetPlace(second);
        myEvent.SetDate(myEvent.MakeDate(third, four));
        return myEvent;
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: return the date that user requested
    private String getDate() {
        boolean isItRightFormat = false;
        String date = "";
        do {
            try {
                System.out.println("Please enter the date of the event in format <yyyy/MM/dd>");
                date = scanner.next();
                checkIfDateForm(date);
                isItRightFormat = true;
            }
            catch (Exception e) {
                System.out.println("It is not in the right format. Please try again.");
            }
        } while (!isItRightFormat);
        return date;
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: check if the date is in correct date form
    private void checkIfDateForm (String date) throws ParseException {
        SimpleDateFormat takenInFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date dateReceived = takenInFormat.parse(date);
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: return the time that user requested
    private String getTime() {
        boolean isItRightFormat = false;
        String time = "";
        do {
            try {
                System.out.println("Please enter the time of the event in 24 hours format");
                time = scanner.next();
                checkIfTimeForm(time);
                isItRightFormat = true;
            }
            catch (Exception e) {
                System.out.println("It is not in the right format. Please try again.");
            }
        } while (!isItRightFormat);
        return time;
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: check if the time is in correct time form
    private void checkIfTimeForm (String time) throws ParseException {
        SimpleDateFormat takenInFormat = new SimpleDateFormat("H");
        Date timeReceived = takenInFormat.parse(time);
    }

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: deletes the event from the schedule
    @Override
    public void DeleteEvent() throws IOException {
        System.out.println("Please enter the context of event you want to delete.");
        ArrayList<MyEvent> toBeDeleted;
        toBeDeleted = FindEventByContext();
        if (toBeDeleted.size() == 0) {
            System.out.println("The event with the context could not be found in the schedule");
        } else if (toBeDeleted.size() == 1) {
            operationSchedule.remove(toBeDeleted.get(0));
            System.out.println("The event has been removed");
        } else {
            System.out.println("There are " + toBeDeleted.size() + " number of events with the following context.");
            boolean x = true;
            do {
                System.out.println("Which event would you like to delete?");
                Integer tbdNumber = 1;
                for (MyEvent tbd : toBeDeleted) {
                    System.out.print("[" + tbdNumber + "] ");
                    if (tbdNumber < toBeDeleted.size()) {
                        System.out.print(tbd);
                        System.out.print(", ");
                    } else if (tbdNumber == toBeDeleted.size())
                        System.out.println(tbd);
                    tbdNumber = tbdNumber + 1;
                }
                String deleteWhich = scanner.nextLine();
                try {
                    operationSchedule.remove(toBeDeleted.get(Integer.parseInt(deleteWhich) - 1));
                    System.out.println("The event has been removed");
                    x = false;
                } catch (Exception e) {
                    System.out.println("You selected the wrong option.");
                }
            } while (x);
            save("TheSchedule");
        }
    }


    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: prints out the event if in the schedule
    @Override
    public void FindEvent() throws ParseException {
        while (true) {
        System.out.println("How would you like to find the event? [1] By Context [2] By Place [3] By Date");
        String operation = scanner.nextLine();
            if (operation.equals("1") || operation.equals("2") || operation.equals("3")) {
                if (operation.equals("1")) {
                    System.out.println("Enter the context of the event you're trying to find.");
                    ArrayList<MyEvent> result = FindEventByContext();
                    if (result.size() == 0) {
                        System.out.println("The event with the context could not be found in the schedule.");
                    } else if (result.size() == 1) {
                        System.out.println(result.get(0));
                    } else {
                        System.out.println("There are " + result.size() + " number of events with the following context.");
                        System.out.println(result);}
                } else if (operation.equals("2")) {
                    System.out.println("Enter the place of the event you're trying to find.");
                    ArrayList<MyEvent> result = FindEventByPlace();
                    if (result.size() == 0) {
                        System.out.println("The event with the place could not be found in the schedule");
                    } else if (result.size() == 1) {
                        System.out.println(result.get(0));
                    } else {
                        System.out.println("There are " + result.size() + " number of events with the following context.");
                        System.out.println(result);
                    }
                } else if (operation.equals("3")) {
                    System.out.println("Enter the date of the event you're trying to find, in <yyyy/MM/dd> format.");
                    ArrayList<MyEvent> result = FindEventByDate();
                    if (result.size() == 0) {
                        System.out.println("The event with the date could not be found in the schedule");
                    } else if (result.size() == 1) {
                        System.out.println(result.get(0));
                    } else {
                        System.out.println("There are " + result.size() + " number of events with the following context.");
                        System.out.println(result);
                    }
                } break;
            } else System.out.println("you selected the wrong option.");

        }
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: returns the requested event
    private ArrayList<MyEvent> FindEventByContext() {
        String context = scanner.next();
        ArrayList<MyEvent> theEvents = new ArrayList<>();
        for (MyEvent me : operationSchedule) {
            if (context.equals(me.ContextIs()))
                theEvents.add(me);
        }
        scanner.nextLine();
        return theEvents;
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: returns the requested event
    private ArrayList<MyEvent> FindEventByPlace() {
        String place = scanner.next();
        ArrayList<MyEvent> theEvents = new ArrayList<>();
        for (MyEvent me : operationSchedule) {
            if (place.equals(me.PlaceIs()))
                theEvents.add(me);
        }
        scanner.nextLine();
        return theEvents;
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: returns the requested event
    private ArrayList<MyEvent> FindEventByDate() throws ParseException {
        String date = scanner.next();
        ArrayList<MyEvent> theEvents = new ArrayList<>();
        SimpleDateFormat takenInFormat = new SimpleDateFormat("yyyy/MM/dd");
        for (MyEvent me : operationSchedule) {
            if (date.equals(takenInFormat.format(me.DateIs())))
                theEvents.add(me);
        }
        scanner.nextLine();
        return theEvents;
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: prints out the operationSchedule
    @Override
    public void PrintSchedule() {
        System.out.println(operationSchedule);
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: returns the operationSchedule
    public ArrayList<MyEvent> ScheduleIs() {
        return operationSchedule;
    }

    //REQUIRES: nothing
    //MODIFIES: TheSchedule
    //EFFECTS: save the operationSchedule to TheSchedule file
    // Copied from FileReaderWriter
    @Override
    public void save(String file) throws IOException {
        PrintWriter context = new PrintWriter(file,"UTF-8");
        for (MyEvent me : operationSchedule) {
            context.println(me.context + "  " +
                    me.DatetoStringPrintform(me.date)
                    + "  " + me.place);
        }
        context.close();
    }

    //REQUIRES: nothing
    //MODIFIES: TheSchedule
    //EFFECTS: load the TheSchedule file to the operationSchedule
    @Override
    public void load(String file) throws IOException, ParseException {
        SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a'>' MMM dd, yyyy");
        List<String> lines = Files.readAllLines(Paths.get(file));
        for (String line : lines) {
            ArrayList<String> partsOfLine = splitOnSpace(line);
            MyEvent savedEvent = new MyEvent();
            savedEvent.SetContext(partsOfLine.get(0));
            savedEvent.SetDate(datePrintform.parse(partsOfLine.get(1)));
            savedEvent.SetPlace(partsOfLine.get(2));
            operationSchedule.add(savedEvent);
        }
    }


    // Copied from FileReaderWriter
    private static ArrayList<String> splitOnSpace(String line){
        String[] splits = line.split("  ");
        return new ArrayList<>(Arrays.asList(splits));
    }


    public static void main(String[] args) throws ParseException, IOException {
        MyAgenda agenda = new MyAgenda();
        agenda.run();
    }
}