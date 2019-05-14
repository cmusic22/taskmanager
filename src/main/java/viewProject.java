
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class viewProject extends JFrame {
    private JTextArea projectView;
    private JPanel printGUI;
    private JButton backToMain;

    Project project;
    taskGUI taskWindow;

    public viewProject(Project project, taskGUI taskWindow){

        this.project = project;
        this.taskWindow = taskWindow;

        setContentPane(printGUI);
        pack();
        setVisible(true);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  // this will quit the program

        viewComponents();

    }

    private void viewComponents(){
        //projectView display project selected or working on and the tasks
        //backToMain brings user back to main GUI

        projectView.setText(project.getName());

        backToMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewProject.this.dispose();  // closes this window, returns focus to taskGUI
            }
        });
    }
}

