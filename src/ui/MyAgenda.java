package ui;

import Model.Loadable;
import Model.Saveable;
import exceptions.AlreadyExisting;
import exceptions.BackOneStep;
import exceptions.BackToMenu;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//copied from LoggingCalculator
public class MyAgenda implements Model.Agenda, Saveable, Loadable{
    ArrayList<MyPersonalEvent> opPersonalSchedule;
    ArrayList<MySchoolEvent> opSchoolSchedule;
    Scanner scanner;
    SimpleDateFormat takenInFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a'>' MMM dd, yyyy");
    Map<String, MyPersonalEvent> opCategorySchedule = new HashMap<>();

    public MyAgenda() {
        opPersonalSchedule = new ArrayList<>();
        opSchoolSchedule = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: nothing
    public void run() throws ParseException, IOException {
        String operation;
        load("MyPersonalSchedule");
        load("MySchoolSchedule");
        load("Events By Categories");
        while (true) {
            System.out.println("what would you like to do? [1] add an event [2] delete an event [3] Deal with categories [4] find an event [5] see the schedule.");
            System.out.println("If you are done with every operations, enter quit.");
            operation = scanner.next();

            if (operation.equals("1")) {
                try {AddEvent();}
                catch (AlreadyExisting ae) {
                     System.out.println("The following event already exists.");
                }
                catch (BackToMenu btm) {
                }
            }

            else if (operation.equals("2")) {
                try {
                    DeleteEvent();
                } catch (BackToMenu btm) {
                }
            }

            else if (operation.equals("3")) {
                DealWithCategory();}

            else if (operation.equals("4")) {
                FindEvent();}

            else if (operation.equals("5")) {
                try {
                    PrintSchedule();
                } catch (BackToMenu backToMenu) {
                }
            }

            else if (operation.equals("quit")) {
                break;}

            else {System.out.println("You selected the wrong option.");}

        }
    }


    @Override
    public void AddEvent() throws IOException, ParseException, AlreadyExisting, BackToMenu {
        String whichEvent;
        while (true) {
            System.out.println("Which event would you like to add? [1] personal event [2] school event");
             whichEvent = scanner.next();

             if (whichEvent.equals("1")) {
                 try {
                     AddPersonalEvent();
                 } catch (BackOneStep bos) {
                 }
                 break;}
             else if (whichEvent.equals("2")) {
                 try {
                     AddSchoolEvent();
                 } catch (BackOneStep bos) {
                 }
                 break;
             }
             else if ((whichEvent.equals("back")) || (whichEvent.equals("menu"))) {
                 throw new BackToMenu();
             }

             else System.out.print("You selected the wrong option. ");
        }
    }

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: adds an event to MyPersonalSchedule
    public void AddPersonalEvent() throws ParseException, IOException, AlreadyExisting, BackToMenu, BackOneStep {
        MyPersonalEvent opEvent = new MyPersonalEvent();
        MyPersonalEvent result;
        result = MakePersonalEvent(opEvent);
        opPersonalSchedule.add(result);
        System.out.println("The event has been added.");
        save("MyPersonalSchedule");
    }

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: adds context, place, and date to myEvent, returns modified myPersonalEvent
    private MyPersonalEvent MakePersonalEvent(MyPersonalEvent myPersonalEvent) throws ParseException, AlreadyExisting, BackOneStep, BackToMenu {
        System.out.println("Please enter the context of event.");
        String first = scanner.next();
        if (first.equals("back")) throw new BackOneStep();
        if (first.equals("menu")) throw new BackToMenu();
        System.out.println("Please enter the place of the event.");
        String second = scanner.next();
        if (second.equals("back")) throw new BackOneStep();
        if (second.equals("menu")) throw new BackToMenu();
        String third = getDate();
        String four = getTime();
        scanner.nextLine(); //clears the line,
        // otherwise the carriage return is taken as the next input
        // and you get a blank "selected" loop
        myPersonalEvent.SetContext(first);
        myPersonalEvent.SetPlace(second);
        myPersonalEvent.SetDate(myPersonalEvent.MakeDate(third, four));
        for (MyPersonalEvent mpe : opPersonalSchedule) {
            if (myPersonalEvent.equals(mpe)) {
                throw new AlreadyExisting();
            }
        }
        return myPersonalEvent;
    }

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: adds an event to MySchoolSchedule
    public void AddSchoolEvent() throws ParseException, IOException, AlreadyExisting, BackToMenu, BackOneStep {
        MySchoolEvent opEvent = new MySchoolEvent();
        MySchoolEvent result;
        result = MakeSchoolEvent(opEvent);
        opSchoolSchedule.add(result);
        System.out.println("The event has been added.");
        save("MySchoolSchedule");
    }

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: adds context, place, and date to myEvent, returns modified myEvent
    private MySchoolEvent MakeSchoolEvent(MySchoolEvent mySchoolEvent) throws ParseException, AlreadyExisting,BackOneStep, BackToMenu {
        System.out.println("Please enter the context of event.");
        String first = scanner.next();
        if (first.equals("back")) throw new BackOneStep();
        if (first.equals("menu")) throw new BackToMenu();
        System.out.println("Please enter which course that "+first+" is for.");
        String second = scanner.next();
        if (second.equals("back")) throw new BackOneStep();
        if (second.equals("menu")) throw new BackToMenu();
        String third = getDate();
        String four = getTime();
        scanner.nextLine(); //clears the line,
        // otherwise the carriage return is taken as the next input
        // and you get a blank "selected" loop
        for (MySchoolEvent mse : opSchoolSchedule) {
            if ((first.equals(mse.context)) && (second.equals(mse.course)
                    && (mySchoolEvent.MakeDate(third, four)).equals(mse.date))) {
                throw new AlreadyExisting();
            }
        }
        mySchoolEvent.SetContext(first);
        mySchoolEvent.SetCourse(second);
        mySchoolEvent.SetDate(mySchoolEvent.MakeDate(third, four));
        return mySchoolEvent;
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
    public void DeleteEvent() throws IOException, BackToMenu {
        String whichEvent;
        while (true) {
            System.out.println("Which event would you like to delete? [1] personal event [2] school event");
            whichEvent = scanner.next();

            if (whichEvent.equals("1")) {
                DeletePersonalEvent();
                break;
            } else if (whichEvent.equals("2")) {
                DeleteSchoolEvent();
                break;
            }
            else if ((whichEvent.equals("back")) || (whichEvent.equals("menu"))) {
                throw new BackToMenu();
            }
            else System.out.print("You selected the wrong option. ");
        }
    }



    public void DeletePersonalEvent() throws IOException {
        System.out.println("Please enter the context of event you want to delete.");
        ArrayList<MyPersonalEvent> toBeDeleted;
        toBeDeleted = FindPersonalEventByContext();
        if (toBeDeleted.size() == 0) {
            System.out.println("The event with the context could not be found in the schedule");
        } else if (toBeDeleted.size() == 1) {
            opPersonalSchedule.remove(toBeDeleted.get(0));
            System.out.println("The event has been removed");
        } else {
            System.out.println("There are " + toBeDeleted.size() + " number of events with the following context.");
            boolean x = true;
            do {
                System.out.println("Which event would you like to delete?");
                Integer tbdNumber = 1;
                for (MyPersonalEvent tbd : toBeDeleted) {
                    System.out.print("[" + tbdNumber + "] ");
                    if (tbdNumber < toBeDeleted.size()) {
                        System.out.print(tbd);
                        System.out.print(", ");
                    } else if (tbdNumber == toBeDeleted.size())
                        System.out.println(tbd);
                    tbdNumber = tbdNumber + 1;
                }
                String deleteWhich = scanner.next();
                try {
                    opPersonalSchedule.remove(toBeDeleted.get(Integer.parseInt(deleteWhich) - 1));
                    System.out.println("The event has been removed");
                    x = false;
                } catch (Exception e) {
                    System.out.println("You selected the wrong option.");
                }
            } while (x);
        }
        save("MyPersonalSchedule");
    }

    public void DeleteSchoolEvent() throws IOException {
        System.out.println("Please enter the context of event you want to delete.");
        ArrayList<MySchoolEvent> toBeDeleted;
        toBeDeleted = FindSchoolEventByContext();
        if (toBeDeleted.size() == 0) {
            System.out.println("The event with the context could not be found in the schedule");
        } else if (toBeDeleted.size() == 1) {
            opSchoolSchedule.remove(toBeDeleted.get(0));
            System.out.println("The event has been removed");
        } else {
            System.out.println("There are " + toBeDeleted.size() + " number of events with the following context.");
            boolean x = true;
            do {
                System.out.println("Which event would you like to delete?");
                Integer tbdNumber = 1;
                for (MySchoolEvent tbd : toBeDeleted) {
                    System.out.print("[" + tbdNumber + "] ");
                    if (tbdNumber < toBeDeleted.size()) {
                        System.out.print(tbd);
                        System.out.print(", ");
                    } else if (tbdNumber == toBeDeleted.size())
                        System.out.println(tbd);
                    tbdNumber = tbdNumber + 1;
                }
                String deleteWhich = scanner.next();
                try {
                    opSchoolSchedule.remove(toBeDeleted.get(Integer.parseInt(deleteWhich) - 1));
                    System.out.println("The event has been removed");
                    x = false;
                } catch (Exception e) {
                    System.out.println("You selected the wrong option.");
                }
            } while (x);
        }
        save("MyPersonalSchedule");
    }

    public void DealWithCategory() throws ParseException, IOException {
        System.out.println("To insert category to an event, select [1]. To find an event by the category, select [2].");
        String operation = scanner.next();

        if (operation.equals("1")) {
            System.out.println("Please enter the context of event you want to insert category to.");
            MyPersonalEvent toBeInserted = new MyPersonalEvent();

            String context = scanner.next();
            for (MyPersonalEvent mpe : opPersonalSchedule) {
                if (context.equals(mpe.ContextIs()))
                    toBeInserted = mpe;
            }

            if (toBeInserted == null) {
                System.out.println("The event with the category could not be found in the schedule");
            } else {
                System.out.println("Please enter the category of the event.");
                String category = scanner.next();
                MyPersonalEvent mapEvent = toBeInserted;
                opCategorySchedule.put(category, mapEvent);
                System.out.println("The category has been added.");
            }
            scanner.nextLine();
        }

        else if (operation.equals("2")) {
            System.out.println("Please enter the category of the event you want to find");
            String category = scanner.next();
            System.out.println(opCategorySchedule.get(category));
        }
        save("Events By Categories");

    }


    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: prints out the event if in the schedule
    @Override
    public void FindEvent() throws ParseException {
        String operation;
        while (true) {
            System.out.println("Which event do you want to find?");
            System.out.println("[1] Any events [2] personal events [3] school events");
            operation = scanner.next();
            if (operation.equals("1") || operation.equals("2") || operation.equals("3")) {
                if (operation.equals("1")) FindAnyEvent();
                else if (operation.equals("2")) FindPersonalEvent();
                else if (operation.equals("3")) FindSchoolEvent();
                break;
            } else System.out.println("you selected the wrong option.");
        }
    }

    public void FindAnyEvent() throws ParseException {
        String operation;
        while (true) {
            System.out.print("How do you want to find the event? ");
            System.out.println("[1] By context [2] By date");
            operation = scanner.next();
            if (operation.equals("1") || operation.equals("2")) {
                if (operation.equals("1")) {
                    System.out.println("Enter the context of the event you're trying to find.");
                    ArrayList<MyEvent> result = FindEventByContext();
                    if (result.size() == 0) {
                        System.out.println("The event with the context could not be found in the schedule.");
                    } else if (result.size() == 1) {
                        System.out.println(result.get(0));
                    } else {
                        System.out.println("There are " + result.size() + " number of events with the following context.");
                        System.out.println(result);
                    }
                } else if (operation.equals("2")) {
                    System.out.println("Enter the date of the event you're trying to find, in <yyyy/MM/dd> format.");
                    ArrayList<MyEvent> result = FindEventByDate();
                    if (result.size() == 0) {
                        System.out.println("The event with the date could not be found in the schedule");
                    } else if (result.size() == 1) {
                        System.out.println(result.get(0));
                    } else {
                        System.out.println("There are " + result.size() + " number of events with the following date.");
                        System.out.println(result);
                    }
                }
                break;
            } else System.out.println("you selected the wrong option.");
        }
    }

    public void FindPersonalEvent () throws ParseException {
        String operation;
        while (true) {
            System.out.print("How do you want to find the event? ");
            System.out.println("[1] By context [2] By date [3] By place");
            operation = scanner.next();
            if (operation.equals("1") || operation.equals("2") || operation.equals("3")) {
                if (operation.equals("1")) {
                    System.out.println("Enter the context of the event you're trying to find.");
                    ArrayList<MyPersonalEvent> result = FindPersonalEventByContext();
                    if (result.size() == 0) {
                        System.out.println("The event with the context could not be found in the schedule.");
                    } else if (result.size() == 1) {
                        System.out.println(result.get(0));
                    } else {
                        System.out.println("There are " + result.size() + " number of events with the following context.");
                        System.out.println(result);
                    }
                } else if (operation.equals("2")) {
                    System.out.println("Enter the date of the event you're trying to find, in <yyyy/MM/dd> format.");
                    ArrayList<MyPersonalEvent> result = FindPersonalEventByDate();
                    if (result.size() == 0) {
                        System.out.println("The event with the date could not be found in the schedule");
                    } else if (result.size() == 1) {
                        System.out.println(result.get(0));
                    } else {
                        System.out.println("There are " + result.size() + " number of events with the following date.");
                        System.out.println(result);
                    }
                }
                if (operation.equals("3")) {
                    System.out.println("Enter the place of the event you're trying to find.");
                    ArrayList<MyPersonalEvent> result = FindPersonalEventByPlace();
                    if (result.size() == 0) {
                        System.out.println("The event with the place could not be found in the schedule.");
                    } else if (result.size() == 1) {
                        System.out.println(result.get(0));
                    } else {
                        System.out.println("There are " + result.size() + " number of events with the following place.");
                        System.out.println(result);
                    }
                }
                break;
            } else System.out.println("you selected the wrong option.");
        }
    }

    public void FindSchoolEvent () throws ParseException {
        String operation;
        while (true) {
            System.out.print("How do you want to find the event? ");
            System.out.println("[1] By context [2] By date [3] By course");
            operation = scanner.next();
            if (operation.equals("1") || operation.equals("2") || operation.equals("3")) {
                if (operation.equals("1")) {
                    System.out.println("Enter the context of the event you're trying to find.");
                    ArrayList<MySchoolEvent> result = FindSchoolEventByContext();
                    if (result.size() == 0) {
                        System.out.println("The event with the context could not be found in the schedule.");
                    } else if (result.size() == 1) {
                        System.out.println(result.get(0));
                    } else {
                        System.out.println("There are " + result.size() + " number of events with the following context.");
                        System.out.println(result);
                    }
                } else if (operation.equals("2")) {
                    System.out.println("Enter the date of the event you're trying to find, in <yyyy/MM/dd> format.");
                    ArrayList<MySchoolEvent> result = FindSchoolEventByDate();
                    if (result.size() == 0) {
                        System.out.println("The event with the date could not be found in the schedule");
                    } else if (result.size() == 1) {
                        System.out.println(result.get(0));
                    } else {
                        System.out.println("There are " + result.size() + " number of events with the following date.");
                        System.out.println(result);
                    }
                }
                if (operation.equals("3")) {
                    System.out.println("Enter the course of the event you are trying to find.");
                    ArrayList<MySchoolEvent> result = FindSchoolEventByCourse();
                    if (result.size() == 0) {
                        System.out.println("The event with the course could not be found in the schedule.");
                    } else if (result.size() == 1) {
                        System.out.println(result.get(0));
                    } else {
                        System.out.println("There are " + result.size() + " number of events with the following course.");
                        System.out.println(result);
                    }
                }
                break;
            } else System.out.println("you selected the wrong option.");
        }
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: returns the requested event
    private ArrayList<MyEvent> FindEventByContext() {
        String context = scanner.next();
        ArrayList<MyEvent> theEvents = new ArrayList<>();
        for (MyEvent me : opPersonalSchedule) {
            if (context.equals(me.ContextIs()))
                theEvents.add(me);
        }
        for (MyEvent me : opSchoolSchedule) {
            if (context.equals(me.ContextIs()))
                theEvents.add(me);
        }
        scanner.nextLine();
        return theEvents;
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: returns the requested event
    private ArrayList<MyPersonalEvent> FindPersonalEventByContext() {
        String context = scanner.next();
        ArrayList<MyPersonalEvent> thePersonalEvents = new ArrayList<>();
        for (MyPersonalEvent mpe : opPersonalSchedule) {
            if (context.equals(mpe.ContextIs()))
                thePersonalEvents.add(mpe);
        }
        scanner.nextLine();
        return thePersonalEvents;
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: returns the requested event
    private ArrayList<MySchoolEvent> FindSchoolEventByContext() {
        String context = scanner.next();
        ArrayList<MySchoolEvent> theSchoolEvents = new ArrayList<>();
        for (MySchoolEvent mse : opSchoolSchedule) {
            if (context.equals(mse.ContextIs()))
                theSchoolEvents.add(mse);
        }
        scanner.nextLine();
        return theSchoolEvents;
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: returns the requested event
    private ArrayList<MyEvent> FindEventByDate() throws ParseException {
        String date = scanner.next();
        ArrayList<MyEvent> theEvents = new ArrayList<>();
        for (MyEvent me : opPersonalSchedule) {
            if (date.equals(takenInFormat.format(me.DateIs())))
                theEvents.add(me);
        }
        for (MyEvent me : opSchoolSchedule) {
            if (date.equals(takenInFormat.format(me.DateIs())))
                theEvents.add(me);
        }
        scanner.nextLine();
        return theEvents;
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: returns the requested event
    private ArrayList<MyPersonalEvent> FindPersonalEventByDate() throws ParseException {
        String date = scanner.next();
        ArrayList<MyPersonalEvent> thePersonalEvents = new ArrayList<>();
        for (MyPersonalEvent mpe : opPersonalSchedule) {
            if (date.equals(takenInFormat.format(mpe.DateIs())))
                thePersonalEvents.add(mpe);
        }
        scanner.nextLine();
        return thePersonalEvents;
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: returns the requested event
    private ArrayList<MySchoolEvent> FindSchoolEventByDate() throws ParseException {
        String date = scanner.next();
        ArrayList<MySchoolEvent> theSchoolEvents = new ArrayList<>();
        for (MySchoolEvent mse : opSchoolSchedule) {
            if (date.equals(takenInFormat.format(mse.DateIs())))
                theSchoolEvents.add(mse);
        }
        scanner.nextLine();
        return theSchoolEvents;
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: returns the requested event
    private ArrayList<MyPersonalEvent> FindPersonalEventByPlace() {
        String place = scanner.next();
        ArrayList<MyPersonalEvent> thePersonalEvents = new ArrayList<>();
        for (MyPersonalEvent me : opPersonalSchedule) {
            if (place.equals(me.PlaceIs()))
                thePersonalEvents.add(me);
        }
        scanner.nextLine();
        return thePersonalEvents;
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: returns the requested event
    private ArrayList<MySchoolEvent> FindSchoolEventByCourse() {
        String place = scanner.next();
        ArrayList<MySchoolEvent> theSchoolEvents = new ArrayList<>();
        for (MySchoolEvent mse : opSchoolSchedule) {
            if (place.equals(mse.CourseIs()))
                theSchoolEvents.add(mse);
        }
        scanner.nextLine();
        return theSchoolEvents;
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: prints out the operationSchedule
    @Override
    public void PrintSchedule() throws BackToMenu {
        while (true) {
            System.out.print("Which schedule do you want to print out? ");
            System.out.println("[1] entire schedule [2] personal schedule [3] school schedule");
            String operation = scanner.next();
            if (operation.equals("1") || operation.equals("2") || operation.equals("3")) {
                if (operation.equals("1")) {
                    System.out.println(EntireScheduleIs());
                }
                else if (operation.equals("2")) {
                    System.out.println(opPersonalSchedule);
                }
                else if (operation.equals("3")) {
                    System.out.println(opSchoolSchedule);
                }
                else if ((operation.equals("back")) || (operation.equals("menu"))) {
                    throw new BackToMenu();
                }
                break;
            }
            else System.out.println("you selected the wrong option.");
        }
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: returns the entire schedule
    public ArrayList<MyEvent> EntireScheduleIs() {
        ArrayList<MyEvent> operationSchedule = new ArrayList<>();
        operationSchedule.addAll(opPersonalSchedule);
        operationSchedule.addAll(opSchoolSchedule);
        return operationSchedule;
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: returns the personal schedule
    public ArrayList<MyPersonalEvent> PersonalScheduleIs() {
        return opPersonalSchedule;
    }

    //REQUIRES: nothing
    //MODIFIES: nothing
    //EFFECTS: returns the personal schedule
    public ArrayList<MySchoolEvent> SchoolScheduleIs() {
        return opSchoolSchedule;
    }

    //REQUIRES: nothing
    //MODIFIES: MyPersonalSchedule
    //EFFECTS: save the operationSchedule to MyPersonalSchedule file
    // Copied from FileReaderWriter
    public void save(String file) throws IOException {
        PrintWriter context = new PrintWriter(file,"UTF-8");
        if (file == "MyPersonalSchedule") {
            for (MyPersonalEvent mpe : opPersonalSchedule) {
                context.println(mpe.context + " : " +
                        mpe.DatetoStringPrintform(mpe.date)
                        + " : " + mpe.place);
            }
        }
        else if (file == "MySchoolSchedule") {
            for (MySchoolEvent mse : opSchoolSchedule) {
                context.println(mse.context + " : " +
                        mse.DatetoStringPrintform(mse.date)
                        + " : " + mse.course);
            }
        }

        else if (file == "Events By Categories") {
            for (MyPersonalEvent mpe : opCategorySchedule.values())
                context.print(mpe.context + " : " +
                        mpe.DatetoStringPrintform(mpe.date)
                        + " : " + mpe.place);
            for (String category : opCategorySchedule.keySet()) {
                context.println(" : "+category);
            }
        }

        // for the sake of the test
        else {
            for (MyPersonalEvent mpe : opPersonalSchedule) {
                context.println(mpe.context + " : " +
                        mpe.DatetoStringPrintform(mpe.date)
                        + " : " + mpe.place);
            }
        }
        context.close();
    }

    //REQUIRES: nothing
    //MODIFIES: MyPersonalSchedule
    //EFFECTS: load the MyPersonalSchedule file to the operationSchedule
    public void load(String file) throws IOException, ParseException {
        if (file == "MyPersonalSchedule") {
            List<String> lines = Files.readAllLines(Paths.get(file));
            for (String line : lines) {
                ArrayList<String> partsOfLine = splitOnSpace(line);
                MyPersonalEvent savedEvent = new MyPersonalEvent();
                savedEvent.SetContext(partsOfLine.get(0));
                savedEvent.SetDate(datePrintform.parse(partsOfLine.get(1)));
                savedEvent.SetPlace(partsOfLine.get(2));
                opPersonalSchedule.add(savedEvent);
            }
        } else if (file == "MySchoolSchedule") {
            List<String> lines = Files.readAllLines(Paths.get(file));
            for (String line : lines) {
                ArrayList<String> partsOfLine = splitOnSpace(line);
                MySchoolEvent savedEvent = new MySchoolEvent();
                savedEvent.SetContext(partsOfLine.get(0));
                savedEvent.SetDate(datePrintform.parse(partsOfLine.get(1)));
                savedEvent.SetCourse(partsOfLine.get(2));
                opSchoolSchedule.add(savedEvent);
            }
        }

        if (file == "Events By Categories") {
            List<String> lines = Files.readAllLines(Paths.get(file));
            for (String line : lines) {
                ArrayList<String> partsOfLine = splitOnSpace(line);
                MyPersonalEvent savedEvent = new MyPersonalEvent();
                savedEvent.SetContext(partsOfLine.get(0));
                savedEvent.SetDate(datePrintform.parse(partsOfLine.get(1)));
                savedEvent.SetPlace(partsOfLine.get(2));
                opCategorySchedule.put(partsOfLine.get(3), savedEvent);
            }
        }

        // for the sake of the test
        else {
            List<String> lines = Files.readAllLines(Paths.get(file));
            for (String line : lines) {
                ArrayList<String> partsOfLine = splitOnSpace(line);
                MyPersonalEvent savedEvent = new MyPersonalEvent();
                savedEvent.SetContext(partsOfLine.get(0));
                savedEvent.SetDate(datePrintform.parse(partsOfLine.get(1)));
                savedEvent.SetPlace(partsOfLine.get(2));
                opPersonalSchedule.add(savedEvent);
            }
        }
    }


    // Copied from FileReaderWriter
    private static ArrayList<String> splitOnSpace(String line){
        String[] splits = line.split(" : ");
        return new ArrayList<>(Arrays.asList(splits));
    }


    public static void main(String[] args) throws ParseException, IOException {
        MyAgenda agenda = new MyAgenda();
        try {agenda.run();}
        catch (Exception e) {System.out.println("The system has been shut down due to as system error." + e);}
        finally {System.out.println("Thank you for using the system.");}
    }
}