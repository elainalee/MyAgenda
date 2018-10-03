package ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//copied from LoggingCalculator
public class MyAgenda {
    ArrayList<MyEvent> operationSchedule = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: nothing
    public MyAgenda() throws ParseException, IOException {
        String operation;
        load();
        while (true) {
            System.out.println("what would you like to do? [1] add an event [2] delete an event [3] find an event [4] see the entire schedules.");
            System.out.println("If you are done with every operations, enter quit.");
            operation = scanner.nextLine();
            System.out.println("you selected: " + operation);

            if (operation.equals("1")) {
                MyEvent opEvent = new MyEvent();
                MyEvent result;
                result = AddSchedule(opEvent);
                operationSchedule.add(result);
                System.out.println("The event has been added.");
                save();
            }

            else if (operation.equals("2")) {
                DeleteSchedule();
                save();
            }

            else if (operation.equals("3")) {
                MyEvent result;
                System.out.println("Enter the context of the event you're trying to find.");
                result = FindSchedule();
                if (result == null) {
                    System.out.println("The event with the context could not be found in the schedule");
                }
                else System.out.println(result);
            }

            else if (operation.equals("4")) {
                System.out.println(operationSchedule);
            }

            else if (operation.equals("quit")) {
                break;
            }

            else {System.out.println("you selected the wrong option. ");}

        }

        System.out.println("Thank you for using the system.");
    }

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: adds context, place, and date to myEvent, returns modified myEvent
    private MyEvent AddSchedule(MyEvent myEvent) throws ParseException {
        System.out.println("Please enter the context of event.");
        String first = scanner.next();
        System.out.println("Please enter the place of the event.");
        String second = scanner.next();
        System.out.println("Please enter the date of the event in format <yyyy/MM/dd>");
        String third = scanner.next();
        System.out.println("Please enter the time of the event in 24 hours format");
        String four = scanner.next();
        scanner.nextLine(); //clears the line,
        // otherwise the carriage return is taken as the next input
        // and you get a blank "selected" loop
        myEvent.SetContext(first);
        myEvent.SetPlace(second);
        myEvent.SetDate(myEvent.MakeDate(third, four));
        return myEvent;
    }


    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: deletes the requested event from the schedule
    private void DeleteSchedule() {
        System.out.println("Please enter the context of event you want to delete.");
        MyEvent toBeDeleted;
        toBeDeleted = FindSchedule();
        if (toBeDeleted == null) {
            System.out.println("The event with the context could not be found in the schedule");
        }
        else {
            operationSchedule.remove(toBeDeleted);
            System.out.println("The event has been removed");
        }
    }


    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: returns the requested event
    private MyEvent FindSchedule() {
        String one = scanner.next();
        MyEvent theEvent = null;
        for (MyEvent me : operationSchedule) {
            if (one.equals(me.ContextIs()))
                theEvent = me;
        }
        scanner.nextLine();
        return theEvent;
    }

    //REQUIRES: nothing
    //MODIFIES: TheSchedule
    //EFFECTS: save the operationSchedule to TheSchedule file
    // Copied from FileReaderWriter
    private void save() throws IOException {
        SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a'>' MMM dd, yyyy");
        PrintWriter context = new PrintWriter("TheSchedule","UTF-8");
        for (MyEvent me : operationSchedule) {
            context.println(me.context + "  " +
                    datePrintform.format(me.date)
                    + "  " + me.place);
        }
        context.close();
    }

    //REQUIRES: nothing
    //MODIFIES: TheSchedule
    //EFFECTS: load the TheSchedule file to the operationSchedule
    private void load() throws IOException, ParseException {
        SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a'>' MMM dd, yyyy");
        List<String> lines = Files.readAllLines(Paths.get("TheSchedule"));
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
    public static ArrayList<String> splitOnSpace(String line){
        String[] splits = line.split("  ");
        return new ArrayList<>(Arrays.asList(splits));
    }



    public static void main(String[] args) throws ParseException, IOException {
        new MyAgenda();
    }
}