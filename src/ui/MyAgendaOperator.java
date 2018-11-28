package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyAgendaOperator extends JFrame {
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JPanel myAgendaDisplay;
    private JList list1;

    public MyAgendaOperator(){
        add(myAgendaDisplay);

        setTitle("bla");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        DefaultListModel listModel = new DefaultListModel();

        list1.setModel(listModel);

        listModel.addElement("Jennifer you shold have known this");

        //int selectedIndex = list1.getSelectedIndex(); (bound is needed)

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //stuff you write here will happen if that button is pressed
                String test = JOptionPane.showInputDialog("enter something and look");
                listModel.addElement(test);



            }
        });
    }

    public static void main(String[] args) {
        MyAgendaOperator myAgendaOperator = new MyAgendaOperator();
        myAgendaOperator.setVisible(true);
    }

}
