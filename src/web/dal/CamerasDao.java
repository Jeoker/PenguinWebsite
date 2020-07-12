package web.dal;

import web.model.*;

import java.sql.*;

public class CamerasDao {
    protected ConnectionManager connectionManager;
    private static CamerasDao instance = null;
    protected CamerasDao() {
        connectionManager = new ConnectionManager();
    }
    public static CamerasDao getInstance() {
        if(instance == null) {
            instance = new CamerasDao();
        }
        return instance;
    }

    protected Cameras genCamera(ResultSet rs) throws SQLException {
        return new Cameras(rs.getInt("CameraId"),rs.getString("Name"));
    }

    /**
     * Users create camera
     * @param camera
     * @return Images
     * @throws SQLException
     */
    public Cameras create(Cameras camera) throws SQLException {
        String sql = "INSERT INTO Cameras(Name) " +
              "VALUES(?);";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, camera.getName());
            ps.executeUpdate();

            // Retrieve the auto-generated key and set it
            rs = ps.getGeneratedKeys();
            int id;
            if(rs.next()) {
                id = rs.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            camera.setCameraId(id);
            return camera;
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
     * User can delete camera
     * @param camera
     * @return null
     * @throws SQLException
     */
    public Cameras delete(Cameras camera) throws SQLException {
        String sql = "DELETE FROM Cameras WHERE CameraId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, camera.getCameraId());
            ps.executeUpdate();

            // Return null pointer
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
     * @param id
     * @return Cameras
     * @throws SQLException
     */
    public Cameras getCameraById(int id) throws SQLException {
        String sql = "SELECT * FROM Cameras WHERE CameraId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                return genCamera(rs);
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
