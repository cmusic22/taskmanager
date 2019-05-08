import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addEditGUI {
    private JLabel selectedEnteredProjectName;
    private JTextField newUserName;
    private JTextField newTask;
    private JRadioButton priorityHigh;
    private JRadioButton priorityMedium;
    private JRadioButton priorityLow;
    private JButton addUserButton;
    private JTable userDisplay;
    private JButton addTask;
    private JTable currentProjectTasks;
    private JButton deleteTask;
    private JTable allUsers;
    private JButton deleteUser;
    private JButton viewProject;
    private JPanel editGUI;


    public addEditGUI (addEdit addEditProgram){
        setContentPane(editGUI);
        pack();
        setVisible(WHENCALLED);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addEditComponents();
    }

    protected void showAlertDialog(String message){
        JOptionPane.showMessageDialog(this, message);
    }

    private void addEditComponents() {
        // TODO: place custom component creation code here
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUser = newUserName.getText();
                //TODO: check to see if user already exsits
                //TODO: if user does display alert message
                //TODO: if user does not add to user database and assign user ID number
                //TODO: update user table in GUI
            }
        });

        addTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = newTask.getText();
                //TODO: get priority selection
                //TODO: get user assigned
                //TODO: send to task database with project listed in projec label
                //TODO: update task table
            }
        });

        deleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: delete selected user in table
            }
        });

        deleteTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: delete task selected in table
            }
        });

        viewProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: show project tasks, priorities, & assigned users in view GUI(needs to be created)
            }
        });
        
    }
}
