package tests;

import org.junit.jupiter.api.Test;
import ui.MyEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        testEvent.SetDate(testEvent.MakeDate("1999/09/29", "1"));
        String DateExample;
        SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a '>' MMM dd, yyyy");
        DateExample = datePrintform.format(testEvent.DateIs());
        assertEquals("<Wed at 1 AM > Sep 29, 1999", DateExample);
    }

    @Test
    public void TestMakeDate() throws ParseException {
        Date testDate1_1 = testEvent.MakeDate("1998/09/29", "5");

        SimpleDateFormat takenInFormat = new SimpleDateFormat("yyyy/MM/dd H");
        Date testDate1_2 = takenInFormat.parse("1998/09/29"+" "+"5");
        assertEquals(testDate1_2, testDate1_1);

        Date testDate2_1 = testEvent.MakeDate("2000/09/29", "14");
        Date testDate2_2 = takenInFormat.parse("2000/09/29"+" "+"14");
        assertEquals(testDate2_2, testDate2_1);
    }

}
