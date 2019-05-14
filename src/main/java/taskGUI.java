
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;



public class taskGUI extends JFrame {

    private JTextField newProjectName;
    private JButton addProject;
    private JTable currentProjects;
    private JButton deleteProject;
    private JButton viewProject;
    private JPanel mainPanel;

    private Controller controller;

    private DefaultListModel<Project> allProjectsListModel;

   taskGUI (Controller controller){
       this.controller = controller;
       allProjectsListModel = new DefaultListModel<Project>();
       projectList.setModel(allProjectsListModel);

       setupUIComponents();

       setContentPane(mainPanel);
       pack();
       setVisible(true);
       setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //call configure and implement functionality

   }

   void setListData(ArrayList<Project> data){
       allProjectsListModel.clear();

       if (data != null){
           for (Project project : data){
               allProjectsListModel.addElement(project);
           }
       }
   }


    protected void showAlertDialog(String message){
        JOptionPane.showMessageDialog(this, message);
    }

    private void setupUIComponents(){
            //TODO: addProject button, gets newProjectName and transfers it to addEditGUI {project name label}

                addProject.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String projectName = newProjectName.getText();

                        if(projectName.isEmpty()){
                            showAlertDialog("Enter a project name.");
                            return;
                        }

                        boolean exsits  = checkProjectDB(projectName);

                        if (exsits){
                            String existsMessage = "That name already exists, please choose a new name";
                            showAlertDialog(existsMessage);
                        }else {
                            int projectID = getProjectID();
                            ProjectsDB.sendNewProject(projectID, projectName);//add projectName to the database\
                            new addEditGUI(projectName);//open editGUI

                        }
                    }
                });

            //TODO: show current projects table

            //claraj/Java2545 Examples
            //selectListener to get JTable info
            //week 9 extra gui examples Tree GUI, two_windows
            //GUI.launch
            //TODO: view project button, get selected project and send to viewProject tasks and users
            viewProject.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentProjects.getSelectedRow();//TODO: pull projectID of selected project
                    //TODO: pull tasks, priority, & users for selected project ID
                }
            });

            //TODO: delete selected project button
            deleteProject.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int projectID = currentProjects.getSelectedColumn();//TODO: Get selected projectID
                    ProjectsDB.deleteSelectedProject(projectID);
                }
            });



    }

    private boolean checkProjectDB (String name){
            final String nameCheck = "SELECT * from projects where ? = projectName";
                    //use row set
            try(Connection connection = DriverManager.getConnection(DMConfig.projects_db);
                PreparedStatement ps = connection.prepareStatement(nameCheck)){

                    ps.setString(1,name);
                    ResultSet answer = ps.executeQuery(); //lab11  slide 81

                if(!answer.isBeforeFirst()){
                     return false;
                } else {
                    return true;
                }
            }catch (SQLException e){
                throw new RuntimeException();
            }

    }

    private int getProjectID () {
       int Pid = 0;
       if (Pid <= 0){
           Pid++;
       }
    return Pid;}


}
