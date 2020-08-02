package web.model;

public class Researchers extends Users{
    protected String firstName;
    protected String lastName;
    protected int gender;
    protected String academicPaper;
    protected String institute;

    public Researchers(int userId, String userName, String password, Status status, String firstName, String lastName, int gender, String academicPaper, String institute) {
        super(userId, userName, password, status);
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.academicPaper = academicPaper;
        this.institute = institute;
    }

    public Researchers(int userId, String firstName, String lastName, int gender, String academicPaper, String institute) {
        super(userId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.academicPaper = academicPaper;
        this.institute = institute;
    }

    public Researchers(String userName, String password, Status status, String firstName, String lastName, int gender, String academicPaper, String institute) {
        super(userName, password, status);
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.academicPaper = academicPaper;
        this.institute = institute;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAcademicPaper() {
        return academicPaper;
    }

    public void setAcademicPaper(String academicPaper) {
        this.academicPaper = academicPaper;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }
}
