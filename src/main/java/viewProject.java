import javax.swing.*;

public class viewProject {
    private JTextArea projectView;
    private JPanel printGUI;
    private JButton backToMain;


    public viewProject(view viewProgram){

        setContentPane(printGUI);
        pack();
        setVisible(WHENCALLCED);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        viewComponents();

    }

    private void viewComponents(){
        //projectView display project selected or working on and the tasks
        //backToMain brings user back to main GUI
    }
}
