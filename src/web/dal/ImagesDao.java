package web.dal;

import web.model.Cameras;
import web.model.Images;
import web.model.Sites;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImagesDao {
    protected ConnectionManager connectionManager;
    private static ImagesDao instance = null;
    protected ImagesDao() {
        connectionManager = new ConnectionManager();
    }
    UAVsDao uavsDao = UAVsDao.getInstance();
    public static ImagesDao getInstance() {
        if(instance == null) {
            instance = new ImagesDao();
        }
        return instance;
    }

    protected Images genImage(ResultSet rs) throws SQLException {
        SitesDao sitesDao = SitesDao.getInstance();
        CamerasDao camerasDao = CamerasDao.getInstance();
        Sites site = sitesDao.getSitesBySiteId(rs.getInt("SiteId"));
        Cameras camera = camerasDao.getCameraById(rs.getInt("CameraId"));
        return new Images(rs.getInt("ImageId"),rs.getString("FileName"),
              rs.getString("FileType"),rs.getInt("Size"),site,
              rs.getString("MediaLink"),rs.getTimestamp("TimeStamp"),
              rs.getInt("Width"),rs.getInt("Height"),rs.getDouble("Longitude"),
              rs.getDouble("Latitude"),camera);
    }

    /**
     * Users create comments
     * @param image
     * @return Images
     * @throws SQLException
     */
    public Images create(Images image) throws SQLException {
        String sql = "INSERT INTO Images(FileName,FileType,SiteId,Size,MediaLink," +
              "TimeStamp,Width,Height,Longitude,Latitude,CameraId) " +
              "VALUES(?,?,?,?,?,?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, image.getFileName());
            ps.setString(2, image.getFileType());
            ps.setInt(3, image.getSite().getSiteId());
            ps.setInt(4, image.getSize());
            ps.setString(5, image.getMediaLink());
            ps.setTimestamp(6, image.getTimestamp());
            ps.setInt(7, image.getWidth());
            ps.setInt(8, image.getHeight());
            ps.setDouble(9, image.getLongitude());
            ps.setDouble(10, image.getLatitude());
            ps.setInt(11, image.getCamera().getCameraId());
            ps.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            rs = ps.getGeneratedKeys();
            int id;
            if(rs.next()) {
                id = rs.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            image.setImageId(id);
            return image;
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
     * Users update their comments
     * @param image
     * @param newPath
     * @return Images
     * @throws SQLException
     */
    public Images updatePath(Images image, String newPath) throws SQLException {
        String sql = "UPDATE Images SET MediaLink=? WHERE ImageId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, newPath);
            ps.setInt(2, image.getImageId());
            ps.executeUpdate();

            // Update the link before returning
            image.setMediaLink(newPath);
            return image;
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
     * Users delete comment
     * @param image
     * @return
     * @throws SQLException
     */
    public Images delete(Images image) throws SQLException {
        String sql = "DELETE FROM Images WHERE ImageId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, image.getImageId());
            ps.executeUpdate();

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
     * User get comment by commentId
     * @param id
     * @return image
     * @throws SQLException
     */
    public Images getImageById(int id) throws SQLException {
        String sql =
              "SELECT ImageId,FileName,FileType,SiteId,Size,MediaLink,TimeStamp," +
                    "Width,Height,Longitude,Latitude,CameraId " +
                    "FROM Images WHERE ImageId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                return genImage(rs);
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
     * Users can get comments by userId
     * @param siteId
     * @return list of Images
     * @throws SQLException
     */
    public List<Images> getImageBySite(int siteId, int start, int numImages)
          throws SQLException {
        List<Images> images = new ArrayList<Images>();
        String sql =
              "SELECT ImageId,FileName,FileType,SiteId,Size,MediaLink,TimeStamp," +
                    "Width,Height,Longitude,Latitude,CameraId " +
                    "FROM Images WHERE SiteId=? and Size<20 Limit ?,?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, siteId);
            ps.setInt(2, start);
            ps.setInt(3, numImages);
            rs = ps.executeQuery();
            while(rs.next()) {
                images.add(genImage(rs));
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
        return images;
    }

    public List<Images> getImageBySite(int siteId) throws SQLException {
        return getImageBySite(siteId,0,10);
    }


    /**
     * Users can get comments by postId
     * @param time
     * @return Images
     * @throws SQLException
     */
    public Images getImageByClosestTime(Timestamp time) throws SQLException {
        String sql = "select *,abs(timestampdiff(second,Images.TimeStamp,?)) " +
              "as diff from Images where TimeStamp is not null order by diff asc " +
              "limit 1;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, time);
            rs = ps.executeQuery();
            if(rs.next()) {
                return(genImage(rs));
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

    public Float[] getWeatherForImage(int id) throws SQLException {
        String sql = "select TmpOut,WindSpeed," +
              "       abs(round(6367000*2*asin(" +
              "               sqrt(pow(sin(((Weathers.Latitude * pi())/180" +
              "                   - (the_image.Latitude*pi())/180)/2),2)" +
              "                   + cos((the_image.Latitude*pi())/180)" +
              "                        *cos((Weathers.Latitude*pi())/180)" +
              "                        *pow(sin(((Weathers.Longitude*pi())/180" +
              "                           -(the_image.Longitude * pi())/180)/2),2)" +
              "                   )))) as distance " +
              "from Weathers, (select * from Images where ImageId = ?) as the_image " +
              "where the_image.Latitude is not null and the_image.Longitude is not null " +
              "and the_image.TimeStamp is not null " +
              "order by distance, abs(the_image.TimeStamp-Weathers.Time) limit 1;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                Float ans[] = {rs.getFloat("TmpOut"),rs.getFloat("WindSpeed")};
                return(ans);
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

    public int countImagesFromSite(int siteId) throws SQLException {
        String sql = "select count(*) as count from Images where SiteId=?" +
              " and Size<20";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, siteId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        }
        return 0;
    }

    public String getUAVInfo(int id)  throws SQLException {
        String sql = "select UAVs.Model from Images,UAVs " +
              "where ImageId=? and Images.CameraId=UAVs.CameraId;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Model");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        }
        return "";
    }
}
