import java.sql.*;
import java.util.ArrayList;

public class UserDB {
    private static final String PROJECTS_DB_URL = DMConfig.projects_db;
    private static final String TABLE_NAME = "users";
    private static final String USER_ID_COL = "userID";
    private static final String USER_NAME_COL = "userName";

    static final String OK = "OK";

    UserDB() {creatTable();}
//this is supposed to create the table if one hasn't been created
    private void creatTable(){
        try(Connection conn = DriverManager.getConnection(PROJECTS_DB_URL);
            Statement statement = conn.createStatement()) {
            String createTableSQLTemplate = "CREATE TABLE IF NOT Exists %s (%s INTEGER PRIMARY KEY, %s TEXT)";
            String createTableSQL = String.format(createTableSQLTemplate, TABLE_NAME, USER_ID_COL, USER_NAME_COL);

            statement.executeUpdate(createTableSQL);
        }catch (SQLException sqle){
            throw new RuntimeException(sqle);
        }
    }
    //this is supposed to fetch all user records and put them into an ArrayList to be displayed in the JList
    public static ArrayList<User> fetchUserRecords(){
        ArrayList<User> allUsers = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(PROJECTS_DB_URL);
            Statement statement = conn.createStatement()){
            String selectUSERSQL = "SELECT * FROM" + TABLE_NAME;
            ResultSet sUSER = statement.executeQuery(selectUSERSQL);

            while (sUSER.next()){
                int userID = sUSER.getInt(USER_ID_COL);
                String userName = sUSER.getString(USER_NAME_COL);
                User user = new User(userID, userName);
                allUsers.add(user);
            }

            sUSER.close();
            return allUsers;
        }catch (SQLException sqle){
            throw new RuntimeException(sqle);
        }
    }
    //this is supposed to add a user to the user table then fetch all users and updated the JList
    protected static void addUser (String un){
        final String user = "insert into users userName values (?)";

        try (Connection connection = DriverManager.getConnection(DMConfig.projects_db);
             PreparedStatement ps = connection.prepareStatement(user)){

            ps.setString(1, un);
            ps.execute();
            fetchUserRecords(); //suposed to get users from table and put into list and refresh JTable
        }catch (SQLException e){
            System.err.println("There was an error adding the user");
        }
    }


        //this is supposed to delete the selected user from the database, but don't know if it works becasue the List wont show any users
    protected static void deleteSelectedUser (int Uid){
        final String deleteUser = "delete from users where userID like ?";

        try(Connection connection = DriverManager.getConnection(DMConfig.projects_db);
        PreparedStatement ps = connection.prepareStatement(deleteUser)){

            ps.setInt(1, Uid);
            ps.execute();
        }catch (SQLException e){
            System.err.println("There was an error deleting the user" + e);
        }
    }


}
