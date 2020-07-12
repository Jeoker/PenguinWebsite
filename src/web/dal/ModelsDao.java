package web.dal;

import web.model.Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModelsDao {

    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static ModelsDao instance = null;
    public ModelsDao() {
        connectionManager = new ConnectionManager();
    }
    public static ModelsDao getInstance(){
        if(instance == null) {
            instance = new ModelsDao();
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
    public Models create(Models model) throws SQLException{
        String sql = "INSERT INTO Models(Name, CreateTime)" +
                " VALUES(?, ?);";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = connectionManager.getConnection();

            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, model.getName());
            //ps.setTimestamp(2, model.getCreateTime());
            ps.setTimestamp(2, new Timestamp(model.getCreateTime().getTime()));
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
            model.setModelId(id);
            return model;
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
    public Models delete(Models model) throws SQLException{
        String sql = "DELETE FROM Models WHERE ModelId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, model.getModelId());
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
     * Update model name
     * User update
     * @param model
     * @param newName
     * @return model
     * @throws SQLException
     */
    public Models updateName(Models model, String newName) throws SQLException{
        String sql = "UPDATE Models SET Name = ? WHERE ModelId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setInt(2, model.getModelId());
            ps.executeUpdate();

            // Update the user param before returning to the caller.
            model.setName(newName);
            return model;
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
    public Models getModelByModelId(int modelId) throws SQLException{
        String sql = "SELECT ModelId, Name, CreateTime FROM Models WHERE ModelId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, modelId);
            rs = ps.executeQuery();
            if (rs.next()){
                int resultmodelId = rs.getInt("ModelId");
                String name = rs.getString("Name");
                Timestamp createTime =  new Timestamp(rs.getTimestamp("CreateTime").getTime());
                Models model = new Models(resultmodelId, name, createTime);
                return model;
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
     * User, Researcher and Administrator Login
     * @param user
     * @return user
     */
    public Models getModelFromName(String name) throws SQLException{
        String sql = "SELECT ModelId, Name, CreateTime FROM Models WHERE Name = ?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()){
                int modelId = rs.getInt("ModelId");
                String localname = rs.getString("Name");
                Timestamp createTime =  new Timestamp(rs.getTimestamp("CreateTime").getTime());
                Models model = new Models(modelId, localname, createTime);
                return model;
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
}
