package web.dal;

import web.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikesDao {
    protected ConnectionManager connectionManager;

    private static LikesDao instance = null;
    protected LikesDao() {
        connectionManager = new ConnectionManager();
    }
    public static LikesDao getInstance() {
        if(instance == null) {
            instance = new LikesDao();
        }
        return instance;
    }

    /**
     * User can create like
     * @param like
     * @return like
     * @throws SQLException
     */
    public Likes create(Likes like) throws SQLException {
        String sql = "INSERT INTO Likes(UserId,PostId,CommentId) VALUES(?,?,?);";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,like.getUser().getUserId());
            // NullPointerException
            if(like.getPost() != null){
                ps.setInt(2,like.getPost().getPostId());
            }else {
                ps.setNull(2,Types.INTEGER);
            }
            if(like.getComment() != null){
                ps.setInt(3,like.getComment().getCommentId());
            }else {
                ps.setNull(3,Types.INTEGER);
            }
            ps.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            rs = ps.getGeneratedKeys();
            int likeId = -1;
            if(rs.next()) {
                likeId = rs.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            like.setLikeId(likeId);
            return like;
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
     * User can delete like
     * @param like
     * @return null
     * @throws SQLException
     */
    public Likes delete(Likes like) throws SQLException {
        String sql = "DELETE FROM Likes WHERE LikeId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, like.getLikeId());
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
     * Administrator can get like by likeId
     * @param likeId
     * @return like
     * @throws SQLException
     */
    public Likes getLikeById(int likeId) throws SQLException {
        String sql =
                "SELECT LikeId,UserId,PostId,CommentId " +
                        "FROM Likes " +
                        "WHERE LikeId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, likeId);
            rs = ps.executeQuery();
            UsersDao usersDao = UsersDao.getInstance();
            PostsDao postsDao = PostsDao.getInstance();
            CommentsDao commentsDao = CommentsDao.getInstance();
            if(rs.next()) {
                int resultLikeId = rs.getInt("LikeId");
                int userId = rs.getInt("UserId");
                int postId = rs.getInt("PostId");
                int commentId = rs.getInt("CommentId");

                Users user = usersDao.getUserFromUserId(userId);
                Posts post = postsDao.getPostByPostId(postId);
                Comments comment = commentsDao.getCommentById(commentId);
                Likes likes = new Likes(resultLikeId, user, post, comment);
                return likes;
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
     * Administrator can get the number of likes by postId
     * @param postId
     * @return like
     * @throws SQLException
     */
    public int getLikeNumberByPostId(int postId) throws SQLException {
        String sql = "SELECT COUNT(*) AS numberOfLikes FROM Likes WHERE PostId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, postId);
            rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt("numberOfLikes");
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
        return -1;
    }

    /**
     * Administrator can get the number of likes by commentId
     * @param commentId
     * @return like
     * @throws SQLException
     */
    public int getLikeNumberByCommentId(int commentId) throws SQLException {
        String sql = "SELECT COUNT(*) AS numberOfLikes FROM Likes WHERE CommentId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, commentId);
            rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt("numberOfLikes");
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
        return -1;
    }

    /**
     * Users can get likes by UserId
     * @param user
     * @return list of likes
     * @throws SQLException
     */
    public List<Likes> getLikesByUserId(Users user) throws SQLException {
        List<Likes> likes = new ArrayList<Likes>();
        String sql =
                "SELECT LikeId,UserId,PostId,CommentId " +
                        "FROM Likes " +
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
                int likeId = rs.getInt("LikeId");
                int postId = rs.getInt("PostId");
                int commentId = rs.getInt("CommentId");

                Posts post = postsDao.getPostByPostId(postId);
                Comments comment = commentsDao.getCommentById(commentId);
                Likes like = new Likes(likeId, user, post, comment);
                likes.add(like);
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
        return likes;
    }

    /**
     * Users can get likes by UserId
     * @param user
     * @return list of likes
     * @throws SQLException
     */
    public Likes getLikesByUserIdPostId(Users user, Posts post) throws SQLException {
        String sql =
                "SELECT LikeId,UserId,PostId,CommentId " +
                        "FROM Likes " +
                        "WHERE UserId=? AND PostId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1,user.getUserId());
            ps.setInt(2,post.getPostId());
            rs = ps.executeQuery();
            CommentsDao commentsDao = CommentsDao.getInstance();
            while(rs.next()) {
                int likeId = rs.getInt("LikeId");
                int commentId = rs.getInt("CommentId");

                Comments comment = commentsDao.getCommentById(commentId);
                Likes like = new Likes(likeId, user, post, comment);
                return like;
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
     * Users can get likes by UserId and CommentId
     * @param user
     * @return list of likes
     * @throws SQLException
     */
    public Likes getLikesByUserIdCommentId(Users user, Comments comment) throws SQLException {
        String sql =
                "SELECT LikeId,UserId,PostId,CommentId " +
                        "FROM Likes " +
                        "WHERE UserId=? AND CommentId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1,user.getUserId());
            ps.setInt(2,comment.getCommentId());
            rs = ps.executeQuery();
            PostsDao postsDao = PostsDao.getInstance();
            while(rs.next()) {
                int likeId = rs.getInt("LikeId");
                int postId = rs.getInt("PostId");

                Posts post = postsDao.getPostByPostId(postId);
                Likes like = new Likes(likeId, user, post, comment);
                return like;
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
     * Administrator can get all likes
     * @return list of likes
     * @throws SQLException
     */
    public List<Likes> getAllLikes() throws SQLException {
        List<Likes> likes = new ArrayList<Likes>();
        String sql =
                "SELECT LikeId,UserId,PostId,CommentId FROM Likes;";
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
                int likeId = rs.getInt("LikeId");
                int userId = rs.getInt("UserId");
                int postId = rs.getInt("PostId");
                int commentId = rs.getInt("CommentId");

                Users user = usersDao.getUserFromUserId(userId);
                Posts post = postsDao.getPostByPostId(postId);
                Comments comment = commentsDao.getCommentById(commentId);
                Likes like = new Likes(likeId, user, post, comment);
                likes.add(like);
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
        return likes;
    }
}
