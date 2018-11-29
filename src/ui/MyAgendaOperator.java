package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAgendaOperator extends JFrame {
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JPanel myAgendaDisplay;
    private JList list;
    private Map<String,String> AgendaInfo = new HashMap<>();
    private List<TheEvent> listEvents;
    private DateFormat date;

    public MyAgendaOperator(){
        add(myAgendaDisplay);

        setTitle("MyAgenda");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        DefaultListModel listModel = new DefaultListModel();

        list.setModel(listModel);

        listEvents = new ArrayList();

        listModel.addElement("Jennifer you should have known this");

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

                    TheEvent theEvent = new TheEvent();

                    theEvent.setContext(eventContext);
                    theEvent.setDescription(eventDescription);
                    setDateTime(theEvent);

                    listModel.addElement(eventContext);
                    listEvents.add(theEvent);
                }
                else {
                    JOptionPane.showMessageDialog(getParent(), "An event with the context already exists. Please try again.");
                    actionPerformed(e);
                }
            }

            public void setDateTime(TheEvent theEvent){

                // Dealing with the date
                Integer i = 0;
                do {
                    try {
                        String eventDate = JOptionPane.showInputDialog("Enter the date of the event");
                        theEvent.setDate(eventDate);
                        i++;
                    } catch (ParseException e) {
                        JOptionPane.showMessageDialog(getParent(),"It is not in the right format. Please try again.");
                    }
                } while(i==0);

                do {
                    try {
                        String eventTime = JOptionPane.showInputDialog("Enter the time of the event");
                        theEvent.setTime(eventTime);
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
                if (selectedIndex > -1) {
                    listModel.remove(selectedIndex);
                }
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = list.getSelectedIndex();
                if (selectedIndex > -1) {
                    String description = AgendaInfo.get(listModel.get(selectedIndex));
                    JOptionPane.showMessageDialog(getParent(), description);
                }
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    public static void main(String[] args) {
        MyAgendaOperator myAgendaOperator = new MyAgendaOperator();
        myAgendaOperator.setVisible(true);
    }

}
