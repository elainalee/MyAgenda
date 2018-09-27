package ui;

import java.util.*;

//copied from LoggingCalculator
public class MyAgenda {
    ArrayList<MyEvent> operationSchedule = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);


    public MyAgenda() {
        String operation;

        while (true) {
            MyEvent opEvent = new MyEvent();
            System.out.println("what would you like to do? [1] add an event [2] find an event [3] see the entire schedules.");
            System.out.println("If you are done with every operations, enter quit.");
            operation = scanner.nextLine();
            System.out.println("you selected: " + operation);

            if (operation.equals("1")) {
                String result;
                result = AddSchedule(opEvent);
                System.out.println(result);
            }
            else if (operation.equals("2")) {
                MyEvent result;
                result = FindSchedule();
                if (result == null) {
                    System.out.println("The event with the context could not be found in the schedule");
                }
                else System.out.println(result);
            }
            else if (operation.equals("3")) {
                System.out.println(operationSchedule);}

            else if (operation.equals("quit")) {
                break;
            }

            else {System.out.println("you selected the wrong option. ");}

        }

        System.out.println("Thank you for using the system.");
    }


    private String AddSchedule(MyEvent myEvent) {
        System.out.println("Please enter the context of event.");
        String first = scanner.next();
        System.out.println("Please enter the place of the event.");
        String second = scanner.next();
        System.out.println("Please enter the date of the event.");
        String third = scanner.next();
        scanner.nextLine(); //clears the line,
        // otherwise the carriage return is taken as the next input
        // and you get a blank "selected" loop
        myEvent.SetContext(first);
        myEvent.SetPlace(second);
        myEvent.SetDate(third);
        operationSchedule.add(myEvent);
        return "The event has been added.";
    }

    private MyEvent FindSchedule() {
        System.out.println("Enter the context of the event you're trying to find.");
        System.out.println(operationSchedule);
        String one = scanner.next();
        MyEvent theEvent = null;
        for (MyEvent me : operationSchedule) {
            if (one.equals(me.context))
                theEvent = me;
        }
        scanner.nextLine();
        return theEvent;
    }


    public static void main(String[] args) {
        new MyAgenda();
    }
}