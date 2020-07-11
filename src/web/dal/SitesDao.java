package web.dal;

import web.model.Sites;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class SitesDao {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static SitesDao instance = null;
    protected SitesDao() {
        connectionManager = new ConnectionManager();
    }
    public static SitesDao getInstance() {
        if(instance == null) {
            instance = new SitesDao();
        }
        return instance;
    }

    public Sites create(Sites site) throws SQLException{
        String sql = "INSERT INTO Sites(Name, Date)" +
                " VALUES(?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, site.getName());
            insertStmt.setTimestamp(2, new Timestamp(site.getDate().getTime()));
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int siteId = -1;
            if(resultKey.next()) {
            	siteId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            site.setSiteId(siteId);
            return site;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(insertStmt != null) {
                insertStmt.close();
            }
            if(resultKey != null) {
                resultKey.close();
            }
        }
    }

    public Sites getSitesBySiteId(int siteId) throws SQLException{
        String sql = "SELECT SiteId, Name, Date" +
                " FROM Sites WHERE SiteId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, siteId);
            rs = ps.executeQuery();
            while (rs.next()){
                int localsiteId = rs.getInt("SiteId");
                String name = rs.getString("Name");
                Date date = new Date(rs.getTimestamp("Date").getTime());

                Sites site = new Sites(localsiteId, name, date);
                return site;
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

    /*
    public Sites getCompanyByCompanyKey(int companyKey) throws SQLException{
        String sql = "SELECT CompanyKey, Name, Description" +
                " FROM Company WHERE CompanyKey = ?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CompaniesDao companiesDao = CompaniesDao.getInstance();
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, companyKey);
            rs = ps.executeQuery();
            while (rs.next()){
                String name = rs.getString("Name");
                String description = rs.getString("Description");

                Companies company = new Companies(companyKey, name, description);
                return company;
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
    */
    

    public Sites updateAbout(Sites site, String newAbout) throws SQLException{
        String sql = "UPDATE Sites SET Name = ? WHERE SiteId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, newAbout);
            ps.setInt(2, site.getSiteId());
            ps.executeUpdate();

            site.setName(newAbout);
            return site;
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

    public Sites delete(Sites site) throws SQLException{
        String sql = "DELETE FROM Sites WHERE SiteId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, site.getSiteId());
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
}
