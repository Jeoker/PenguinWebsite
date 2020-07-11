package web.dal;

import web.model.Comments;
import web.model.Posts;
import web.model.Reshares;
import web.model.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResharesDao {
    protected ConnectionManager connectionManager;

    private static ResharesDao instance = null;
    protected ResharesDao() {
        connectionManager = new ConnectionManager();
    }
    public static ResharesDao getInstance() {
        if(instance == null) {
            instance = new ResharesDao();
        }
        return instance;
    }

    /**
     * User can create reshare
     * @param reshare
     * @return reshare
     * @throws SQLException
     */
    public Reshares create(Reshares reshare) throws SQLException {
        String sql = "INSERT INTO Reshares(UserId,PostId,CommentId) VALUES(?,?,?);";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,reshare.getUser().getUserId());
            // NullPointerException
            if(reshare.getPost() != null){
                ps.setInt(2,reshare.getPost().getPostId());
            }else {
                ps.setNull(2,Types.INTEGER);
            }
            if(reshare.getComment() != null){
                ps.setInt(3,reshare.getComment().getCommentId());
            }else {
                ps.setNull(3,Types.INTEGER);
            }
            ps.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            rs = ps.getGeneratedKeys();
            int reshareId = -1;
            if(rs.next()) {
                reshareId = rs.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            reshare.setReshareId(reshareId);
            return reshare;
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
    public Reshares delete(Reshares reshare) throws SQLException {
        String sql = "DELETE FROM Reshares WHERE ReshareId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, reshare.getReshareId());
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
    public Reshares getReshareById(int reshareId) throws SQLException {
        String sql =
                "SELECT ReshareId,UserId,PostId,CommentId " +
                        "FROM Reshares " +
                        "WHERE ReshareId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, reshareId);
            rs = ps.executeQuery();
            UsersDao usersDao = UsersDao.getInstance();
            PostsDao postsDao = PostsDao.getInstance();
            CommentsDao commentsDao = CommentsDao.getInstance();
            if(rs.next()) {
                int resultReshareId = rs.getInt("ReshareId");
                int userId = rs.getInt("UserId");
                int postId = rs.getInt("PostId");
                int commentId = rs.getInt("CommentId");

                Users user = usersDao.getUserFromUserId(userId);
                Posts post = postsDao.getPostByPostId(postId);
                Comments comment = commentsDao.getCommentById(commentId);
                Reshares reshare = new Reshares(resultReshareId, user, post, comment);
                return reshare;
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
    public List<Reshares> getResharesByUserId(Users user) throws SQLException {
        List<Reshares> reshares = new ArrayList<Reshares>();
        String sql =
                "SELECT ReshareId,UserId,PostId,CommentId " +
                        "FROM Reshares " +
                        "WHERE UserId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1,user.getUserId());
            rs = ps.executeQuery();
            PostsDao postsDao = PostsDao.getInstance();
            CommentsDao commentsDao = CommentsDao.getInstance();
            while(rs.next()) {
                int reshareId = rs.getInt("ReshareId");
                int postId = rs.getInt("PostId");
                int commentId = rs.getInt("CommentId");

                Posts post = postsDao.getPostByPostId(postId);
                Comments comment = commentsDao.getCommentById(commentId);
                Reshares reshare = new Reshares(reshareId, user, post, comment);
                reshares.add(reshare);
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
        return reshares;
    }

    /**
     * Administrator can get all reshares
     * @return list of reshares
     * @throws SQLException
     */
    public List<Reshares> getAllReshares() throws SQLException {
        List<Reshares> reshares = new ArrayList<Reshares>();
        String sql =
                "SELECT ReshareId,UserId,PostId,CommentId FROM Reshares;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            UsersDao usersDao = UsersDao.getInstance();
            PostsDao postsDao = PostsDao.getInstance();
            CommentsDao commentsDao = CommentsDao.getInstance();
            while(rs.next()) {
                int reshareId = rs.getInt("ReshareId");
                int userId = rs.getInt("UserId");
                int postId = rs.getInt("PostId");
                int commentId = rs.getInt("CommentId");

                Users user = usersDao.getUserFromUserId(userId);
                Posts post = postsDao.getPostByPostId(postId);
                Comments comment = commentsDao.getCommentById(commentId);
                Reshares reshare = new Reshares(reshareId, user, post, comment);
                reshares.add(reshare);
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
        return reshares;
    }
}
