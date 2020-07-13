package web.dal;

import web.model.Cameras;
import web.model.Posts;
import web.model.UAVs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UAVsDao {
    protected ConnectionManager connectionManager;
    private static UAVsDao instance = null;
    protected UAVsDao() {
        connectionManager = new ConnectionManager();
    }
    public static UAVsDao getInstance() {
        if(instance == null) {
            instance = new UAVsDao();
        }
        return instance;
    }

    protected UAVs genUAV(ResultSet rs) throws SQLException {
        return new UAVs(rs.getInt("UavId"),rs.getString("Model"),
              new Cameras(rs.getInt("CameraId")),rs.getInt("Weight"));
    }

    public UAVs create(UAVs uav) throws SQLException {
        String sql = "insert into UAVs(Model,CameraId,Weight) " +
              "values(?,?,?);";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, uav.getModel());
            ps.setInt(2, uav.getCamera().getCameraId());
            ps.setFloat(3, uav.getWeight());
            ps.executeUpdate();

            // Retrieve the auto-generated key and set it
            rs = ps.getGeneratedKeys();
            int id;
            if(rs.next()) {
                id = rs.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            uav.setUavId(id);
            return uav;
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

    public UAVs delete(UAVs uav) throws SQLException {
        String sql = "DELETE FROM UAVs WHERE UavId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, uav.getUavId());
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
     * @return UAVs
     * @throws SQLException
     */
    public UAVs getUavById(int id) throws SQLException {
        String sql = "SELECT * FROM UAVs WHERE UavId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                return genUAV(rs);
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
     * @param id
     * @return UAVs
     * @throws SQLException
     */
    public List<UAVs> getUavByCameraId(int id) throws SQLException {
        String sql = "SELECT * FROM UAVs WHERE CameraId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            List<UAVs> uavList = new ArrayList<UAVs>();
            while(rs.next()) {
                uavList.add(genUAV(rs));
            }
            return uavList;
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
}
