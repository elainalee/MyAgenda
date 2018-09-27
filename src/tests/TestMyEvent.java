package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ui.MyEvent;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMyEvent {
    MyEvent testEvent;

    @BeforeAll
    public void SetUp() {
        testEvent = new MyEvent();
        testEvent.SetContext("testContext");
        testEvent.SetDate("testDate");
        testEvent.SetPlace("testPlace");
    }


    // test if the method takes in the string
    @Test
    public void TestSetContext() {
        String ContextExample;
        ContextExample = testEvent.ContextIs();
        assertTrue (ContextExample.equals("testContext"));
    }

    // test if the method takes in the string
    @Test
    public void TestSetPlace() {
        String PlaceExample;
        PlaceExample = testEvent.PlaceIs();
        assertTrue(PlaceExample.equals("testPlace"));
    }

    // test if the method takes in the string
    @Test
    public void TestSetDate() {
        String DateExample;
        DateExample = testEvent.DateIs();
        assertTrue(DateExample.equals("testDate"));
    }

}
