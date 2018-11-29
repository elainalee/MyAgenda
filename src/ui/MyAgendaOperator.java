package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyAgendaOperator extends JFrame {
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JPanel myAgendaDisplay;
    private JList list;
    private Map<String,TheEventInfo> Events = new HashMap<>();

    public MyAgendaOperator() throws ParseException {
        add(myAgendaDisplay);

        setTitle("MyAgenda");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        DefaultListModel listModel = new DefaultListModel();

        list.setModel(listModel);

        //adding a few events in prior - 1
        String test1 = "Test";
        listModel.addElement(test1);
        TheEventInfo theEventInfo1 = new TheEventInfo();
        theEventInfo1.setContext(test1);
        theEventInfo1.setDescription("Math test from chapter 1-13");
        theEventInfo1.setDate("1999/09/29");
        theEventInfo1.setTime("1");
        Events.put(test1,theEventInfo1);

        //adding a few events in prior - 2
        String test2 = "Event";
        listModel.addElement(test2);
        TheEventInfo theEventInfo2 = new TheEventInfo();
        theEventInfo2.setContext(test2);
        theEventInfo2.setDescription("event happening at sauder");
        theEventInfo2.setDate("1999/09/29");
        theEventInfo2.setTime("2");
        Events.put(test2,theEventInfo2);

        //adding a few events in prior - 3
        String test3 = "Meeting";
        listModel.addElement(test3);
        TheEventInfo theEventInfo3 = new TheEventInfo();
        theEventInfo3.setContext(test3);
        theEventInfo3.setDescription("meeting for commerce");
        theEventInfo3.setDate("2000/09/29");
        theEventInfo3.setTime("3");
        Events.put(test3,theEventInfo3);

        //int selectedIndex = list.getSelectedIndex();


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeAddEvent(e);
            }

            public void makeAddEvent(ActionEvent e) {
                String eventContext = JOptionPane.showInputDialog("Enter the context of the event");
                if (!(listModel.contains(eventContext))) {
                    String eventDescription = JOptionPane.showInputDialog("Enter the description of the event");

                    TheEventInfo theEventInfo = new TheEventInfo();

                    theEventInfo.setContext(eventContext);
                    theEventInfo.setDescription(eventDescription);
                    setDateTime(theEventInfo);
                    Events.put(eventContext,theEventInfo);

                    listModel.addElement(eventContext);
                }
                else {
                    JOptionPane.showMessageDialog(getParent(), "An event with the context already exists. Please try again.");
                    actionPerformed(e);
                }
            }

            public void setDateTime(TheEventInfo theEventInfo){

                // Dealing with the date
                Integer i = 0;
                do {
                    try {
                        String eventDate = JOptionPane.showInputDialog("Enter the date of the event");
                        theEventInfo.setDate(eventDate);
                        i++;
                    } catch (ParseException e) {
                        JOptionPane.showMessageDialog(getParent(),"It is not in the right format. Please try again.");
                    }
                } while(i==0);

                do {
                    try {
                        String eventTime = JOptionPane.showInputDialog("Enter the time of the event");
                        theEventInfo.setTime(eventTime);
                        i++;
                    } catch (ParseException e) {
                        JOptionPane.showMessageDialog(getParent(),"It is not in the right format. Please try again.");
                    }
                } while(i==1);

            }

        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = list.getSelectedIndex();
                if (!(selectedIndex == -1)) {
                    listModel.remove(selectedIndex);
                    }
                }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = list.getSelectedIndex();
                if (selectedIndex > -1) {
                    TheEventInfo TEI = Events.get(listModel.get(selectedIndex));
                    String info = ("Description: "+TEI.descriptionIs()+", Date: "+TEI.dateIs()+" at "+TEI.timeIs());
                    JOptionPane.showMessageDialog(getParent(), info);
                }
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDate();
            }

            public void getDate() {
                Integer i = 0;
                do {
                    try {
                        String eventDate = JOptionPane.showInputDialog("Enter the date of the event");
                        SimpleDateFormat takenInFormat_date = new SimpleDateFormat("yyyy/MM/dd");
                        Date eventDate_date = takenInFormat_date.parse(eventDate);
                        //getting date ended;
                        List<TheEventInfo> eventsToPrint = new ArrayList<>();
                        for (TheEventInfo TEI : Events.values()) {
                            if (TEI.date.equals(eventDate_date)) {
                                eventsToPrint.add(TEI);
                            }
                        }
                        List<String> contextsToPrint = new ArrayList<>();
                        for (TheEventInfo TEI : eventsToPrint) {
                            contextsToPrint.add(TEI.context);
                        }
                        JOptionPane.showMessageDialog(getParent(),contextsToPrint);
                        i++;


                    } catch (ParseException e) {
                        JOptionPane.showMessageDialog(getParent(),"It is not in the right format. Please try again.");
                    }
                } while (i==0);
            }
        });

    }

    public static void main(String[] args) throws ParseException {
        MyAgendaOperator myAgendaOperator = new MyAgendaOperator();
        myAgendaOperator.setVisible(true);
    }

}
