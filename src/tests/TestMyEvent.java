package tests;

import org.junit.jupiter.api.Test;
import ui.DateFormat;
import ui.MyPersonalEvent;
import ui.MySchoolEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class TestMyEvent {
    MyPersonalEvent testPersonalEvent = new MyPersonalEvent();
    MySchoolEvent testSchoolEvent = new MySchoolEvent();
    DateFormat dateFormat = new DateFormat();


    // test if the method takes in the string
    @Test
    public void TestSetContext() {
        testPersonalEvent.SetContext("testContext");
        testSchoolEvent.SetContext("testContext");
        assertEquals("testContext", testPersonalEvent.ContextIs());
        assertEquals("testContext", testSchoolEvent.ContextIs());
    }

    // test if the method takes in the date
    @Test
    public void TestSetDate() throws ParseException {
        testPersonalEvent.SetDate(dateFormat.MakeDate("1999/09/29", "1"));
        testSchoolEvent.SetDate(dateFormat.MakeDate("1999/09/29", "2"));

        SimpleDateFormat datePrintform = new SimpleDateFormat("'<'E 'at' h a '>' MMM dd, yyyy");
        String DatePersonalExample = datePrintform.format(testPersonalEvent.DateIs());
        assertEquals("<Wed at 1 AM > Sep 29, 1999", DatePersonalExample);
        String DateSchoolExample = datePrintform.format(testSchoolEvent.DateIs());
        assertEquals("<Wed at 2 AM > Sep 29, 1999", DateSchoolExample);
    }

    @Test
    public void TestMakeDate() throws ParseException {
        Date testDate1_1 = dateFormat.MakeDate("1998/09/29", "5");

        SimpleDateFormat takenInFormat = new SimpleDateFormat("yyyy/MM/dd H");
        Date testDate1_2 = takenInFormat.parse("1998/09/29"+" "+"5");
        assertEquals(testDate1_2, testDate1_1);

        Date testDate2_1 = dateFormat.MakeDate("2000/09/29", "14");
        Date testDate2_2 = takenInFormat.parse("2000/09/29"+" "+"14");
        assertEquals(testDate2_2, testDate2_1);
    }

    // For MyPersonalEvent:
    // test if the method takes in the string
    @Test
    public void TestSetPlace() {
        testPersonalEvent.SetPlace("testPlace");
        String PlaceExample = testPersonalEvent.PlaceIs();
        assertEquals("testPlace", PlaceExample);
    }

    @Test
    public void TestSetCourse() {
        testSchoolEvent.SetCourse("testCourse");
        String CourseExample = testSchoolEvent.CourseIs();
        assertEquals("testCourse", CourseExample);

    }

    @Test
    public void TestStringtoString() throws ParseException{
        // test for personalEvent
        testPersonalEvent.SetContext("testContext_p");
        testPersonalEvent.SetDate(dateFormat.MakeDate("2000/02/02", "1"));
        testPersonalEvent.SetPlace("testPlace_p");
        assertEquals("<Wed at 1 AM> Feb 02, 2000: testContext_p at testPlace_p", testPersonalEvent.toString());
        // test for schoolEvent
        testSchoolEvent.SetContext("testContext_s");
        testSchoolEvent.SetDate(dateFormat.MakeDate("2000/02/02", "2"));
        testSchoolEvent.SetCourse("testCourse_s");
        assertEquals("testCourse_s testContext_s is due on <Wed at 2 AM> Feb 02, 2000", testSchoolEvent.toString());

    }

}
