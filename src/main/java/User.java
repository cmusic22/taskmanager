public class User {

    private int id;
    private String name;

    public User (int userID, String userName){

        id = userID;
        name = userName;
    }

    public int getId {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getName{
        return name;
    }

    public void  setName (String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "User" +
                "id=" + id +
                ", name='" + name +"'";
    }
}
