import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;


public class addEditGUI extends JFrame{
    private JLabel selectedEnteredProjectName;
    private JTextField newUserName;
    private JTextField newTask;
    private JRadioButton priorityHigh;
    private JRadioButton priorityMedium;
    private JRadioButton priorityLow;
    private JButton addUserButton;
    private JButton addTask;
    private JButton deleteTask;
    private JButton deleteUser;
    private JButton viewProject;
    private JPanel editGUI;
    private JList<User> userList;
    private JTable taskTable;



    private DefaultListModel<User> allUserListModel;
    private AbstractTableModel taskTableModel;

    private String[] columnNames = {"Task ID", "Task Name", "Task Priority", "Task User"};


    addEditGUI (String pName) {

        selectedEnteredProjectName.setText(pName);

        allUserListModel = new DefaultListModel<>();
        userList.setModel(allUserListModel);

        taskTableModel = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return 0;
            }

            @Override
            public int getColumnCount() {
                return columnNames.length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return null;
            }
        };
        taskTable.setModel(taskTableModel);


        setContentPane(editGUI);
        pack();
        setVisible(true);
        UserDB.fetchUserRecords();
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

                UserDB.addUser(newUser);//TODO: requery fire data table update user table in GUI

            }
        });

        addTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = newTask.getText();
                String priority = getPriority();//TODO: get priority selection
                User uID = userList.getSelectedValue();
                int sUID = uID.getId();//TODO: get user id that was assigned
                String pID = selectedEnteredProjectName.getText();//TODO: get project id
                int projectID = getProjectID(pID);
                TaskDB.addTask(taskName, priority, sUID, projectID);//TODO: send to task database with project listed in project label
                //TODO: update task table
            }
        });

        deleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = userList.getSelectedValue();//TODO: get selected users ID
                int selectedUID = user.getId();
                UserDB.deleteSelectedUser(selectedUID);//TODO: delete selected user in table
            }
        });

        deleteTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int task = taskTable.getSelectedRow();
                int selectedTID = task.getId();//TODO: get taskID
                TaskDB.deleteSelectedTask(selectedTID); //TODO: delete task selected in table
            }
        });

        viewProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: show project tasks, priorities, & assigned users in view GUI
            }
        });

    }

    private String getPriority(){
        if (priorityHigh.isSelected()) {
            return priorityHigh.getText();
        }else if (priorityMedium.isSelected()){
            return priorityMedium.getText();
        }else {
            return priorityLow.getText();
        }
    }

    private int getProjectID (String n){

        try(Connection connection = DriverManager.getConnection(DMConfig.projects_db);
            Statement ps = connection.createStatement()){

            String projectNID = "SELECT projectID FROM projects projectName VALUES" + n;
            ResultSet pID = ps.executeQuery(projectNID);
            int id = pID.getInt(1);
            pID.close();
            return id;
        }catch (SQLException sqle){
            throw new RuntimeException(sqle);
        }
    }
}
