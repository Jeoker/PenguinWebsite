package web.dal;

import web.model.Detections;
import web.model.Images;
import web.model.Models;
import web.model.Sites;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetectionsDao {

    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static DetectionsDao instance = null;
    public DetectionsDao() {
        connectionManager = new ConnectionManager();
    }
    public static DetectionsDao getInstance(){
        if(instance == null) {
            instance = new DetectionsDao();
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
    public Detections create(Detections detection) throws SQLException{
        String sql = "INSERT INTO Detections(ImageId, Count, PathOnCloud, ModelId)" +
                " VALUES(?, ?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = connectionManager.getConnection();

            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, detection.getImage().getImageId());
            ps.setInt(2, detection.getCount());
            ps.setString(3, detection.getPathOnCloud());
            ps.setInt(4, detection.getModel().getModelId());
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
            detection.setDetectionId(id);
            return detection;
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
    public Detections delete(Detections detection) throws SQLException{
        String sql = "DELETE FROM Detections WHERE DetectionId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, detection.getDetectionId());
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
    public Detections updateCount(Detections detection, int newCount) throws SQLException{
        String sql = "UPDATE Models SET Count = ? WHERE DetectionId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, newCount);
            ps.setInt(2, detection.getDetectionId());
            ps.executeUpdate();

            // Update the user param before returning to the caller.
            detection.setCount(newCount);
            return detection;
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
    public Detections getDetectionByDetectionId(int detectionId) throws SQLException{
        String sql = "SELECT DetectionId, ImageId, Count, PathOnCloud, ModelId FROM Detections WHERE ImageId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, detectionId);
            rs = ps.executeQuery();
            ModelsDao modelsDao = ModelsDao.getInstance();
            ImagesDao imagesDao = ImagesDao.getInstance();
            if (rs.next()){
                int resultdetectionId = rs.getInt("DetectionId");
                int imageId = rs.getInt("ImageId");
                int count = rs.getInt("Count");
                String pathOnCloud = rs.getString("PathOnCloud");
                int modelId = rs.getInt("ModelId");
                
                Images image = imagesDao.getImageById(imageId);
                Models model = modelsDao.getModelByModelId(modelId);
                Detections detection = new Detections(resultdetectionId, image, count, pathOnCloud, model);
                return detection;
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
    public Detections getDetectionByImageId(int imageId) throws SQLException{
        String sql = "SELECT DetectionId, ImageId, Count, PathOnCloud, ModelId FROM Detections WHERE ImageId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, imageId);
            rs = ps.executeQuery();
            ModelsDao modelsDao = ModelsDao.getInstance();
            ImagesDao imagesDao = ImagesDao.getInstance();
            if (rs.next()){
                int detectionId = rs.getInt("DetectionId");
                int resultimageId = rs.getInt("ImageId");
                int count = rs.getInt("Count");
                String pathOnCloud = rs.getString("PathOnCloud");
                int modelId = rs.getInt("ModelId");
                
                Images image = imagesDao.getImageById(imageId);
                Models model = modelsDao.getModelByModelId(modelId);
                Detections detection = new Detections(detectionId, image, count, pathOnCloud, model);
                return detection;
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
