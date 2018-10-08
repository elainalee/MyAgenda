package tests;

import org.junit.jupiter.api.Test;
import ui.MyAgenda;
import ui.MyEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMyAgenda {
    MyAgenda testAgenda = new MyAgenda();

    @Test
    public void testLoad() throws IOException, ParseException {
        SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a'>' MMM dd, yyyy");
        testAgenda.load("TestInput");
        assertEquals("context2", testAgenda.ScheduleIs().get(0).ContextIs());
        assertEquals("context3", testAgenda.ScheduleIs().get(1).ContextIs());
        assertEquals("eating", testAgenda.ScheduleIs().get(2).ContextIs());
        assertEquals("place2", testAgenda.ScheduleIs().get(0).PlaceIs());
        assertEquals("place3", testAgenda.ScheduleIs().get(1).PlaceIs());
        assertEquals("home", testAgenda.ScheduleIs().get(2).PlaceIs());
        assertEquals("<Tue at 5 AM> Sep 29, 1998", datePrintform.format(testAgenda.ScheduleIs().get(0).DateIs()));
        assertEquals("<Mon at 6 AM> Sep 29, 1997", datePrintform.format(testAgenda.ScheduleIs().get(1).DateIs()));
        assertEquals("<Sat at 7 AM> Sep 29, 2018", datePrintform.format(testAgenda.ScheduleIs().get(2).DateIs()));
    }

    @Test
    public void testSave() throws IOException, ParseException {
        MyEvent testEvent1 = new MyEvent();
        MyEvent testEvent2 = new MyEvent();
        testEvent1.SetContext("context1");
        testEvent2.SetContext("context2");
        testEvent1.SetPlace("place1");
        testEvent2.SetPlace("place2");
        testEvent1.SetDate(testEvent1.MakeDate("2001/09/29", "1"));
        testEvent2.SetDate(testEvent2.MakeDate("2002/09/29", "2"));
        testAgenda.ScheduleIs().add(testEvent1);
        testAgenda.ScheduleIs().add(testEvent2);
        testAgenda.save("TestOutput");

        List<String> lines = Files.readAllLines(Paths.get("TestOutput"));

        assertEquals("context1  <Sat at 1 AM> Sep 29, 2001  place1", lines.get(0));
        assertEquals("context2  <Sun at 2 AM> Sep 29, 2002  place2", lines.get(1));

    }
}