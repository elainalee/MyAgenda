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

public class TheirAgenda implements Model.Agenda {
    ArrayList<MyEvent> operationSchedule = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    @Override
    public void AddEvent() throws ParseException, IOException {
    }

    @Override
    public void DeleteEvent() throws IOException {
    }

    @Override
    public void FindEvent() throws ParseException {
    }

    @Override
    public void PrintSchedule() {
    }

    public ArrayList<MyEvent> ScheduleIs() {
        return operationSchedule;
    }

    @Override
    public void save(String file) throws IOException {
        SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a'>' MMM dd, yyyy");
        PrintWriter context = new PrintWriter(file,"UTF-8");
        for (MyEvent me : operationSchedule) {
            context.println(me.context + "  " +
                    datePrintform.format(me.date)
                    + "  " + me.place);
        }
        context.close();
    }

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

}
