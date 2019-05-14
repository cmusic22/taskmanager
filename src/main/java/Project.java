public class Project {

    private int id;
    private String name;

    //string names for projects in DB
    public Project(int projectID, String projectName) {
        //week 6 slides
        id = projectID;
        name = projectName;
    }

    // TODO add get and set methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Project" +
                "id=" + id +
                ", name='" + name + "'";
    }
}
