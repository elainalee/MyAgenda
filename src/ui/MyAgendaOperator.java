package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MyAgendaOperator extends JFrame {
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JPanel myAgendaDisplay;
    private JList list;
    private List<TheEvent> listEvents;
    private DateFormat date;

    public MyAgendaOperator(){
        add(myAgendaDisplay);

        setTitle("MyAgenda");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        DefaultListModel listModel = new DefaultListModel();

        list.setModel(listModel);

        List listEvents = new ArrayList();

        listModel.addElement("Jennifer you should have known this");

        //int selectedIndex = list.getSelectedIndex();


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TheEvent theEvent = new TheEvent();

                String eventContext = JOptionPane.showInputDialog("Enter the context of the event");

                if (!(listModel.contains(eventContext))) {
                    String eventDescription = JOptionPane.showInputDialog("Enter the description of the event");

                    theEvent.setContext(eventContext);
                    theEvent.setDescription(eventDescription);

                    listModel.addElement(eventContext);
                    listEvents.add(theEvent);
                }
                else {
                    JOptionPane.showMessageDialog(getParent(), "An event with the context already exists. Please try again.");
                    actionPerformed(e);
                }
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
