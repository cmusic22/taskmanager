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
        //used information on Java2s.com & docs.orocal,com
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
        // supposed to add user to JList by calling fetchUserRecords(); in the UserDB.addUser(); method
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUser = newUserName.getText();

                if(newUser.isEmpty()){
                    showAlertDialog("Enter a users name."); //this isn't firing for some reason?
                    return;
                }

                UserDB.addUser(newUser);//TODO: requery fire data table update user table in GUI

            }
        });
            //supposed to add task to the Task Database and appear in the JTable when it has been added
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
            //this is to delete the selected user from the user list (Don't know if it works yet beacause the User list isn't updateing)
        deleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = userList.getSelectedValue();//TODO: get selected users ID
                int selectedUID = user.getId();
                UserDB.deleteSelectedUser(selectedUID);//TODO: delete selected user in table
            }
        });
            //this is to delete the selected task in the table (don't know if it works because addTask isn't working for some reason)
        deleteTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int task = taskTable.getSelectedRow();
                TaskDB.deleteSelectedTask(task); //TODO: delete task selected in table
            }
        });
            //this is to 'print' the tasks of the current project you're adding tasks to (Not Working Yet)
        viewProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // project = selectedEnteredProjectName.getText();
                //new viewProject(project, addEditGUI.this)//TODO: show project tasks, priorities, & assigned users in view GUI
            }
        });

    }
    //need to put buttons into a group (Stackoverflow)
    private String getPriority(){
        if (priorityHigh.isSelected()) {
            return priorityHigh.getText();
        }else if (priorityMedium.isSelected()){
            return priorityMedium.getText();
        }else {
            return priorityLow.getText();
        }
    }
        //found a similar version of this on Stackoverflow altered for my needs
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
