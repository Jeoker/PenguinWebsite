package web.model;

public class Users {
    protected int userId;
    protected String userName;
    protected String password;
    protected Status status;

    public enum Status{
        Administrator, Researcher, User
    }

    public Users(int userId, String userName, String password, Status status) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.status = status;
    }

    public Users(int userId) {
        this.userId = userId;
    }

    public Users(String userName, String password, Status status) {
        this.userName = userName;
        this.password = password;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
