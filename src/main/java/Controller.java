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

        ArrayList<Projects> allData = pdb.fetchAllRecords();

        tGUI = new taskGUI(this);
        tGUI.setListData(allData);
    }

    ArrayList<Projects> getAllData(){
        return pdb.fetchAllRecords();
    }
}
