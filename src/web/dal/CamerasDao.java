package web.dal;

import web.model.Cameras;
import web.model.Images;
import web.model.Sites;

import java.sql.ResultSet;
import java.sql.SQLException;

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

}
