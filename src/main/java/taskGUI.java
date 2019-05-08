
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.UUID;


public class taskGUI extends JFrame {

    private JTextField newProjectName;
    private JButton addProject;
    private JTable currentProjects;
    private JButton deleteProject;
    private JButton viewProject;
    private JPanel mainPanel;

    private ModuleLayer.Controller controller;

    private DefaultListModel<Projects> allProjectsListModel;

   taskGUI (ModuleLayer.Controller controller){
       this.controller = controller;
       allProjectsListModel = new DefaultListModel<Projects>();
       projectsList.setModel(allProjectsListModel);

       setupUIComponents();

       setContentPane(mainPanel);
       pack();
       setVisible(true);
       setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //call configure and implement functionality

   }

   void setListData(ArrayList<Projects> data){
       allPlacesListModel.clear();

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

                        if (true){
                            String existsMessage = "That name already exists, please choose a new name";
                            showAlertDialog(existsMessage);
                        }else {

                            ProjectsDB.sendNewProject(projectName);//add projectName to the database

                            //open editGUI
                        }
                    }
                });

            //TODO: show current projects table
            //
            //TODO: view project button, get selected project and send to viewProject tasks and users
            viewProject.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            //TODO: delete selected project button
            deleteProject.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });



    }

    private boolean checkProjectDB (String name){
            final String nameCheck = "SELECT * from projects where ? = projectName";

            try(Connection connection = DriverManager.getConnection(DMConfig.projects_db);
                PreparedStatement ps = connection.prepareStatement(nameCheck)){

                    ps.setString(1,name);

            }catch (SQLException e){
                showAlertDialog("There was a problem checking if the name exsists");
            }
            boolean answer = nameCheckAnswer;
    return answer;}


}
