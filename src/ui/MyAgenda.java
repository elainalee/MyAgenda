package ui;

import java.util.*;

//copied from LoggingCalculator
public class MyAgenda {
    ArrayList<MySchedule> operationSchedule = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public MyAgenda(){
        String operation = "";
        MySchedule opSchedule = new MySchedule();
        while (true){
            System.out.println("what would you like to do? [1] add a schedule [2] check the schedule [3] see all the schedules." +
                    " " + "If you are done with every operations, enter quit.");
            operation = scanner.nextLine();
            System.out.println("you selected: "+operation);
            String result;
            if (operation.equals("1")) {
                result = AddSchedule(opSchedule);
                System.out.println(result);

            }
            else if (operation.equals("quit")) {
                break;
            }
            operationSchedule.add(opSchedule);
        }
        System.out.println("Thank you for using the system.");

    }

    private String AddSchedule(MySchedule mySchedule) {
        System.out.println("Please enter the context of schedule");
        String first = scanner.next();
        scanner.nextLine(); //clears the line,
        // otherwise the carriage return is taken as the next input
        // and you get a blank "selected" loop
        return "The schedule has been added.";
    }


    public static void main(String[] args) {
        new MyAgenda();
    }
}