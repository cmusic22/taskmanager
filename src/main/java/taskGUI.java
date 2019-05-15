
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;



public class taskGUI extends JFrame {

    private JTextField newProjectName;
    private JButton addProject;
    private JList<Project> currentProjects;
    private JButton deleteProject;
    private JButton viewProject;
    private JPanel mainPanel;

    private Controller controller;

    private DefaultListModel<Project> allProjectsListModel;

   taskGUI (Controller controller){
       this.controller = controller;
       allProjectsListModel = new DefaultListModel<>();
       currentProjects.setModel(allProjectsListModel);



       setContentPane(mainPanel);
       pack();
       setVisible(true);
       setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

       setupUIComponents();//call configure and implement functionality

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
                            ProjectsDB.sendNewProject(projectName);//add projectName to the database\
                            new addEditGUI(projectName);//open editGUI
                            // todo fix addEdtiGUI and put this line back

                            // Refresh JList
                            ArrayList<Project> allProjects = ProjectsDB.fetchAllRecords();
                            allProjectsListModel.clear();
                            for (Project project: allProjects) {
                                allProjectsListModel.addElement(project);
                            }

                        }
                    }
                });


            //claraj/Java2545 Examples
            //selectListener to get JTable info
            //week 9 extra gui examples Tree GUI, two_windows
            //GUI.launch
            //TODO: view project button, get selected project and send to viewProject tasks and users
            viewProject.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Project project = currentProjects.getSelectedValue(); //TODO: pull projectID of selected project
                    //TODO: pull tasks, priority, & users for selected project ID
                    new viewProject(project, taskGUI.this);

                }
            });

            //TODO: delete selected project button
            deleteProject.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Project project = currentProjects.getSelectedValue();//TODO: Get selected projectID
                    int projectID = project.getId();
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

}
