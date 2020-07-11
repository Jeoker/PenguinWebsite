package web.dal;

import web.model.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDao {

    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static UsersDao instance = null;
    public UsersDao() {
        connectionManager = new ConnectionManager();
    }
    public static UsersDao getInstance(){
        if(instance == null) {
            instance = new UsersDao();
        }
        return instance;
    }

    /**
     * Create a user instance
     * User SIGN UP
     * @param user
     * @return user
     * @throws SQLException
     */
    public Users create(Users user) throws SQLException{
        String sql = "INSERT INTO Users(UserName, Password, Status)" +
                " VALUES(?, ?, ?);";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = connectionManager.getConnection();

            // BlogPosts has an auto-generated key. So we want to retrieve that key.
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getStatus().name());
            ps.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            // For more details, see:
            // http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
            rs = ps.getGeneratedKeys();
            int id = -1;
            if(rs.next()){
                id = rs.getInt(1);
            }else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            user.setUserId(id);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(ps != null) {
                ps.close();
            }
            if(rs != null) {
                rs.close();
            }
        }
    }

    /**
     * Delete a user instance
     * Administrator can delete users
     * @param user
     * @return null
     * @throws SQLException
     */
    public Users delete(Users user) throws SQLException{
        String sql = "DELETE FROM Users WHERE UserId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, user.getUserId());
            ps.executeUpdate();

            // Return null so the caller can no longer operate on the Persons instance.
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(ps != null) {
                ps.close();
            }
        }
    }

    /**
     * Update user password
     * User update
     * @param user
     * @param newPassword
     * @return user
     * @throws SQLException
     */
    public Users updatePassword(Users user, String newPassword) throws SQLException{
        String sql = "UPDATE Users SET Password = ? WHERE UserId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setInt(2, user.getUserId());
            ps.executeUpdate();

            // Update the user param before returning to the caller.
            user.setPassword(newPassword);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(ps != null) {
                ps.close();
            }
        }
    }

    /**
     * Update user userName
     * User update
     * @param user
     * @param newUserName
     * @return user
     * @throws SQLException
     */
    public Users updateUserName(Users user, String newUserName) throws SQLException{
        String sql = "UPDATE Users SET UserName = ? WHERE UserId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, newUserName);
            ps.setInt(2, user.getUserId());
            ps.executeUpdate();

            // Update the user param before returning to the caller.
            user.setUserName(newUserName);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(ps != null) {
                ps.close();
            }
        }
    }

    /**
     * Get one user record by userId
     * User can get their own record
     * @param userId
     * @return
     * @throws SQLException
     */
    public Users getUserFromUserId(int userId) throws SQLException{
        String sql = "SELECT UserId, UserName, Password, Status FROM Users WHERE UserId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()){
                int resultUserId = rs.getInt("UserId");
                String userName = rs.getString("UserName");
                String password = rs.getString("Password");
                Users.Status status = Users.Status.valueOf(rs.getString("Status"));
                Users user = new Users(resultUserId, userName, password, status);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(ps != null) {
                ps.close();
            }
            if(rs != null) {
                rs.close();
            }
        }
        return null;
    }

    /**
     * Get all user records
     * Administrator can get all user records
     * @return a List of users
     * @throws SQLException
     */
    public List<Users> getAllUser() throws SQLException{
        List<Users> usersList = new ArrayList<>();
        String sql = "SELECT * FROM Users;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                int userId = rs.getInt("UserId");
                String userName = rs.getString("UserName");
                String password = rs.getString("Password");
                Users.Status status = Users.Status.valueOf(rs.getString("Status"));
                Users user = new Users(userId, userName, password, status);
                usersList.add(user);
            }
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(ps != null) {
                ps.close();
            }
            if(rs != null) {
                rs.close();
            }
        }
        return usersList;
    }

    /**
     * User, Researcher and Administrator Login
     * @param user
     * @return user
     */
    public Users getUserByUserNamePasswordStatus(Users user) throws SQLException{
        String sql = "SELECT UserId, UserName, Password, Status " +
                "FROM Users " +
                "WHERE UserName = ? AND Password = ? AND Status = ?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getStatus().name());
            rs = ps.executeQuery();
            if (rs.next()){
                int userId = rs.getInt("UserId");
                String userName = rs.getString("UserName");
                String password = rs.getString("Password");
                Users.Status status = Users.Status.valueOf(rs.getString("Status"));

                Users users = new Users(userId, userName, password, status);
                return users;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(ps != null) {
                ps.close();
            }
            if(rs != null) {
                rs.close();
            }
        }
        return null;
    }
}
