import java.sql.*;

public class TaskDB {
    private static final String PROJECTS_DB_URL = DMConfig.projects_db;
    private static final String TABLE_NAME = "tasks";
    private static final String TASK_ID_COL = "taskID";
    private static final String TASK_NAME_COL = "taskNAME";
    private static final String TASK_PRIORITY_COL = "taskPRIORITY";
    private static final String TASK_USER_COL = "taskUSER";
    private static final String TASK_PROJECT_COL = "taskPROJECT";

    static final String OK = "OK";

    TaskDB() {createTable();}
//supposed to create task table if one is not created already
    private void createTable(){
        try (Connection conn = DriverManager.getConnection(PROJECTS_DB_URL);
             Statement statement = conn.createStatement()){
             String createTableSQLTemplate = "CREATE TABLE IF NOT EXists %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s INTEGER, %s INTEGER";
             String createTableSQL = String.format(createTableSQLTemplate, TABLE_NAME, TASK_ID_COL, TASK_NAME_COL, TASK_PRIORITY_COL, TASK_USER_COL, TASK_PROJECT_COL);
            statement.executeUpdate(createTableSQL);
        }catch (SQLException sqle){
            throw new RuntimeException(sqle);
        }
    }
        //this is supposed to add a task to the database
    protected static void addTask (String tn, String pri, int u, int p) {
        final String taskAddition = "insert into tasks (taskNAME, taskPRIORITY, taskUSER, taskPROJECT) values (?,?,?,?,?)";
        try(Connection connection = DriverManager.getConnection(DMConfig.projects_db);
            PreparedStatement ps = connection.prepareStatement(taskAddition)){


            ps.setString(1, tn);
            ps.setString(2, pri);
            ps.setInt(3, u);
            ps.setInt(4, p);
            ps.execute();
        }catch (SQLException e){
            System.err.println("There was an error adding the task");
        }
    }
        //this is to delete the task
    protected static void deleteSelectedTask (int Tid){
        final String deleteTask = "delete from tasks where taskID like ?";

        try(Connection connection = DriverManager.getConnection(DMConfig.projects_db);
            PreparedStatement ps = connection.prepareStatement(deleteTask)){
            ps.setInt(1, Tid);
            ps.execute();
        }catch (SQLException e){
            System.err.println("There was an error deleting the task");
        }
    }
}
