import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class ProjectsDB {
    private static final String PROJECTS_DB_URL = DMConfig.projects_db;

    private static final String TABLE_NAME = "projects";
    private static final String PROJECT_ID_COL = "projectID";
    private static final String PROJECT_NAME_COL = "projectName";

    static final String OK = "OK";

    ProjectsDB() {creatTable();}

    private void creatTable(){
        try(Connection conn = DriverManager.getConnection(PROJECTS_DB_URL);
        Statement statement = conn.createStatement()){
            String createTableSQLTemplate = "CREATE TABLE IF NOT Exists %s (%s INTEGER PRIMARY KEY, %s TEXT)";
            String createTableSQL = String.format(createTableSQLTemplate, TABLE_NAME, PROJECT_ID_COL, PROJECT_NAME_COL);

            statement.executeUpdate(createTableSQL);
        }catch (SQLException sqle){
            throw new RuntimeException(sqle);
        }
    }

    public static ArrayList<Project> fetchAllRecords(){
        ArrayList<Project> allRecords = new ArrayList<Project>();

        try (Connection conn = DriverManager.getConnection(PROJECTS_DB_URL);
             Statement statement = conn.createStatement()){
            String selectAllSQL = "SELECT * FROM " + TABLE_NAME;
            ResultSet rsAll = statement.executeQuery(selectAllSQL);

            while (rsAll.next()){
                int projectID = rsAll.getInt(PROJECT_ID_COL);
                String projectName = rsAll.getString(PROJECT_NAME_COL);
                Project project = new Project(projectID, projectName);
                allRecords.add(project);
            }

            rsAll.close();
            return allRecords;
        }catch (SQLException sqle){
            throw new RuntimeException(sqle);
        }
    }

    protected static void sendNewProject (int id, String n) {
        final String newProject = "insert into projects (projectName) values (?)";

        try (Connection connection = DriverManager.getConnection(DMConfig.projects_db);
             PreparedStatement ps = connection.prepareStatement(newProject)){
            // SQLite will automatically generate a project ID, so you just need to set the name
            ps.setString(1, n);
            ps.execute();
        }catch (SQLException e){
            System.err.println("There was an error adding the project" + e);
        }
    }

    protected static void deleteSelectedProject (int id){
        final String deleteProject = "delete from projects where projectID like ?";

        try(Connection connection = DriverManager.getConnection(DMConfig.projects_db);
        PreparedStatement ps = connection.prepareStatement(deleteProject)){
            ps.setInt(1, id);
            ps.execute();
        }catch (SQLException e){
            System.err.println("There was an error deleting the project" + e);

        }
    }

}
