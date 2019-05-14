import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDB {

    protected static void addUser (int Uid, String un){
        final String user = "insert into users values (?, ?)";

        try (Connection connection = DriverManager.getConnection(DMConfig.user_db);
             PreparedStatement ps = connection.prepareStatement(user)){

            ps.setInt(1, Uid);
            ps.setString(2, un);
            ps.execute();
        }catch (SQLException e){
            System.err.println("There was an error adding the user");
        }
    }

    protected static void addTask (int Tid, String tn, String pri, int u, int p) {
        final String taskAddition = "insert into tasks values (?,?,?,?,?)";
        try(Connection connection = DriverManager.getConnection(DMConfig.tasks_db);
        PreparedStatement ps = connection.prepareStatement(taskAddition)){

            ps.setInt(1, Tid);
            ps.setString(2, tn);
            ps.setString(3, pri);
            ps.setInt(4, u);
            ps.setInt(5, p);
            ps.execute();
        }catch (SQLException e){
            System.err.println("There was an error adding the task");
        }
    }

    protected static void deleteSelectedUser (int Uid){
        final String deleteUser = "delete from users where userID like ?";

        try(Connection connection = DriverManager.getConnection(DMConfig.user_db);
        PreparedStatement ps = connection.prepareStatement(deleteUser)){

            ps.setInt(1, Uid);
            ps.execute();
        }catch (SQLException e){
            System.err.println("There was an error deleting the user" + e);
        }
    }

    protected static void deleteSelectedTask (int Tid){
        final String deleteTask = "delete from tasks where taskID like ?";

        try(Connection connection = DriverManager.getConnection(DMConfig.tasks_db);
        PreparedStatement ps = connection.prepareStatement(deleteTask)){
            ps.setInt(1, Tid);
            ps.execute();
        }catch (SQLException e){
            System.err.println("There was an error deleting the task");
        }
    }
}
