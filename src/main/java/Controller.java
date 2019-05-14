import java.lang.reflect.Array;
import java.util.ArrayList;

public class Controller {

    private taskGUI tGUI;
    private ProjectsDB pdb;

    public static void main(String[] args){
        new Controller().startApp();
    }

    private void startApp(){
        pdb = new ProjectsDB();

        ArrayList<Project> allData = pdb.fetchAllRecords();

        tGUI = new taskGUI(this);
        tGUI.setListData(allData);
    }

    ArrayList<Project> getAllData(){
        return pdb.fetchAllRecords();
    }
}
