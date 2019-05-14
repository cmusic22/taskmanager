import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class addEditGUI extends JFrame{
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


    addEditGUI (String pName) {
        setContentPane(editGUI);
        pack();
        setVisible(true);
        selectedEnteredProjectName.setText(pName);

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

                if(newUser.isEmpty()){
                    showAlertDialog("Enter a users name.");
                    return;
                }
                int userID = getUserId();//TODO: assign user ID number
                UserDB.addUser(userID, newUser);//TODO: requery fire data table update user table in GUI
                //TODO: update userTable
            }
        });

//        addTask.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String taskName = newTask.getText();
//                String priority = getPriority();//TODO: get priority selection
//                int uID = userDisplay.getSelectedRow();//TODO: get user id that was assigned
//                int pID = getPID(selectedEnteredProjectName.getText());//TODO: get project id
//                int taskID = getTaskID();//TODO: Get taskID
//                UserDB.addTask(taskID, taskName, priority, uID, pID);//TODO: send to task database with project listed in project label
//                //TODO: update task table
//            }
//        });

//        deleteUser.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedUID = getSelectedUserID();//TODO: get selected users ID
//                UserDB.deleteSelectedUser(selectedUID);//TODO: delete selected user in table
//            }
//        });
//
//        deleteTask.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedTID = getSelectedTaskID();//TODO: get taskID
//                UserDB.deleteSelectedTask(selectedTID); //TODO: delete task selected in table
//            }
//        });

        viewProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: show project tasks, priorities, & assigned users in view GUI(needs to be created)
            }
        });

    }

    private int getUserId () {
        int Uid = 0;
        if (Uid <= 0){
            Uid++;
        }
    return  Uid;}

    private int getTaskID () {
        int Tid = 0;
        if (Tid <= 0){
            Tid++;
        }
    return  Tid;}

    private String getPriority(){
        if (priorityHigh.isSelected()) {
            return priorityHigh.getText();
        }else if (priorityMedium.isSelected()){
            return priorityMedium.getText();
        }else {
            return priorityLow.getText();
        }
    }

//    private int getPID(String PN){
//        final String pID = "select * from projects where ? = projectName get projectID";
//
//        try(Connection connection = DriverManager.getConnection(DMConfig.projects_db);
//            PreparedStatement ps = connection.prepareStatement(pID)) {
//
//            ps.setString(1, PN);
//            ResultSet proID = ps.executeQuery();
//            int id = Integer.parseInt(proID);
//            return id;
//        }catch (SQLException e){
//            throw new RuntimeException();
//        }
//   }
}
