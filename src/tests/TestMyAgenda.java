package tests;

import org.junit.jupiter.api.Test;
import ui.DateFormat;
import ui.MyAgenda;
import ui.MyPersonalEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class TestMyAgenda {
    MyAgenda testAgenda = new MyAgenda();
    DateFormat dateFormat = new DateFormat();

    @Test
    public void testLoad_personalSchedule() throws IOException, ParseException {
        SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a'>' MMM dd, yyyy");
        testAgenda.load("TestPersonalInput");
        assertEquals("context2", testAgenda.PersonalScheduleIs().get(0).ContextIs());
        assertEquals("context3", testAgenda.PersonalScheduleIs().get(1).ContextIs());
        assertEquals("eating", testAgenda.PersonalScheduleIs().get(2).ContextIs());
        assertEquals("place2", testAgenda.PersonalScheduleIs().get(0).PlaceIs());
        assertEquals("place3", testAgenda.PersonalScheduleIs().get(1).PlaceIs());
        assertEquals("home", testAgenda.PersonalScheduleIs().get(2).PlaceIs());
        assertEquals("<Tue at 5 AM> Sep 29, 1998", datePrintform.format(testAgenda.PersonalScheduleIs().get(0).DateIs()));
        assertEquals("<Mon at 6 AM> Sep 29, 1997", datePrintform.format(testAgenda.PersonalScheduleIs().get(1).DateIs()));
        assertEquals("<Sat at 7 AM> Sep 29, 2018", datePrintform.format(testAgenda.PersonalScheduleIs().get(2).DateIs()));
    }

    @Test
    public void testSave() throws IOException, ParseException {
        MyPersonalEvent testEvent1 = new MyPersonalEvent();
        MyPersonalEvent testEvent2 = new MyPersonalEvent();
        testEvent1.SetContext("context1");
        testEvent2.SetContext("context2");
        testEvent1.SetPlace("place1");
        testEvent2.SetPlace("place2");
        testEvent1.SetDate(dateFormat.MakeDate("2001/09/29", "1"));
        testEvent2.SetDate(dateFormat.MakeDate("2002/09/29", "2"));
        testAgenda.PersonalScheduleIs().add(testEvent1);
        testAgenda.PersonalScheduleIs().add(testEvent2);
        testAgenda.save("TestOutput");

        List<String> lines = Files.readAllLines(Paths.get("TestOutput"));

        assertEquals("context1 : <Sat at 1 AM> Sep 29, 2001 : place1", lines.get(0));
        assertEquals("context2 : <Sun at 2 AM> Sep 29, 2002 : place2", lines.get(1));

    }

    @Test
    public void SaveNotSupportedFile() throws IOException {
        try{testAgenda.save("TestOutput");}
        catch (Exception ignored) {fail("Thank you for using the system.");}

    }
}