package tests;

import org.junit.jupiter.api.Test;
import ui.MyEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMyEvent {
    MyEvent testEvent = new MyEvent();


    // test if the method takes in the string
    @Test
    public void TestSetContext() {
        testEvent.SetContext("testContext");
        String ContextExample;
        ContextExample = testEvent.ContextIs();
        assertEquals("testContext", ContextExample);
    }

    // test if the method takes in the string
    @Test
    public void TestSetPlace() {
        testEvent.SetPlace("testPlace");
        String PlaceExample;
        PlaceExample = testEvent.PlaceIs();
        assertEquals("testPlace", PlaceExample);
    }

    // test if the method takes in the date
    @Test
    public void TestSetDate() throws ParseException {
        testEvent.SetDate("1999/09/29", "1");
        String DateExample;
        SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a '>' MMM dd, yyyy");
        DateExample = datePrintform.format(testEvent.DateIs());
        assertEquals("<Wed at 1 AM > Sep 29, 1999", DateExample);
    }

}
