package web.dal;

import web.model.Participates;
import web.model.Researchers;
import web.model.Sites;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipatesDao {
    protected ConnectionManager connectionManager;

    private static ParticipatesDao instance = null;
    protected ParticipatesDao() {
        connectionManager = new ConnectionManager();
    }
    public static ParticipatesDao getInstance() {
        if(instance == null) {
            instance = new ParticipatesDao();
        }
        return instance;
    }

    /**
     * User can create reshare
     * @param reshare
     * @return reshare
     * @throws SQLException
     */
    public Participates create(Participates participate) throws SQLException {
        String sql = "INSERT INTO Participates(SiteId,ResearcherId) VALUES(?,?);";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,participate.getSite().getSiteId());
            ps.setInt(2,participate.getResearcher().getUserId());
            ps.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            rs = ps.getGeneratedKeys();
            int participateId = -1;
            if(rs.next()) {
            	participateId = rs.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            participate.setParticipateId(participateId);
            return participate;
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
     * User can delete reshare
     * @param reshare
     * @return null
     * @throws SQLException
     */
    public Participates delete(Participates participate) throws SQLException {
        String sql = "DELETE FROM Participates WHERE ParticipateId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, participate.getParticipateId());
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
     * Administrator can get reshare by reshareId
     * @param reshareId
     * @return reshare
     * @throws SQLException
     */
    public Participates getParticipateById(int participateId) throws SQLException {
        String sql =
                "SELECT ParticipateId,SiteId,ResearcherId " +
                        "FROM Participates " +
                        "WHERE ParticipateId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, participateId);
            rs = ps.executeQuery();
            SitesDao sitesDao = SitesDao.getInstance();
            ResearchersDao researchersDao =  ResearchersDao.getInstance();           
            if(rs.next()) {
                int localparticipateId = rs.getInt("ParticipateId");
                int siteId = rs.getInt("SiteId");
                int userId = rs.getInt("ResearcherId");

                Sites site = sitesDao.getSitesBySiteId(siteId);
                Researchers researcher = researchersDao.getResearchersByUserId(userId);
                Participates participate = new Participates(localparticipateId, site, researcher);
                return participate;
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
     * Users can get reshares by userId
     * @param user
     * @return list of reshares
     * @throws SQLException
     */
    public List<Participates> getParticipatesByUserId(Researchers researcher) throws SQLException {
        List<Participates> participates = new ArrayList<Participates>();
        String sql =
                "SELECT ParticipateId,SiteId,ResearcherId " +
                        "FROM Participates " +
                        "WHERE ResearcherId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            System.out.println(researcher.getUserId());
            ps.setInt(1,researcher.getUserId());
            rs = ps.executeQuery();
            SitesDao sitesDao = SitesDao.getInstance();
            ResearchersDao researchersDao =  ResearchersDao.getInstance(); 
            while(rs.next()) {
            	int localparticipateId = rs.getInt("ParticipateId");
                int siteId = rs.getInt("SiteId");
                //int localuserId = rs.getInt("UserId");
                
                Sites site = sitesDao.getSitesBySiteId(siteId);
                //Researchers researcher = researchersDao.getResearchersByUserId(localuserId);
                Participates participate = new Participates(localparticipateId, site, researcher);
                participates.add(participate);
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
        return participates;
    }

    /**
     * Administrator can get all reshares
     * @return list of reshares
     * @throws SQLException
     */
    
    
    
    public List<Participates> getAllParticipates() throws SQLException {
        List<Participates> participates = new ArrayList<Participates>();
        String sql =
                "SELECT ParticipateId,SiteId,ResearcherId " +
                        "FROM Participates; " ;                    
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            SitesDao sitesDao = SitesDao.getInstance();
            ResearchersDao researchersDao =  ResearchersDao.getInstance(); 
            while(rs.next()) {
            	int localparticipateId = rs.getInt("ParticipateId");
                int siteId = rs.getInt("SiteId");
                int userId = rs.getInt("UserId");
                
                Sites site = sitesDao.getSitesBySiteId(siteId);
                Researchers researcher = researchersDao.getResearchersByUserId(userId);
                Participates participate = new Participates(localparticipateId, site, researcher);
                participates.add(participate);
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
        return participates;
        
    }
    

    
}
