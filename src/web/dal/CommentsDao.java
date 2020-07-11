package web.dal;

import web.model.Comments;
import web.model.Posts;
import web.model.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentsDao {
    protected ConnectionManager connectionManager;

    private static CommentsDao instance = null;
    protected CommentsDao() {
        connectionManager = new ConnectionManager();
    }
    public static CommentsDao getInstance() {
        if(instance == null) {
            instance = new CommentsDao();
        }
        return instance;
    }

    /**
     * Users create comments
     * @param comment
     * @return comment
     * @throws SQLException
     */
    public Comments create(Comments comment) throws SQLException {
        String sql = "INSERT INTO Comments(Content,Created,UserId,PostId) VALUES(?,?,?,?);";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, comment.getContent());
            ps.setTimestamp(2, new Timestamp(comment.getCreated().getTime()));
            ps.setInt(3, comment.getUser().getUserId());
            ps.setInt(4, comment.getPost().getPostId());
            ps.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            rs = ps.getGeneratedKeys();
            int commentId = -1;
            if(rs.next()) {
                commentId = rs.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            comment.setCommentId(commentId);
            return comment;
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
     * @param comment
     * @param newContent
     * @return comment
     * @throws SQLException
     */
    public Comments updateContent(Comments comment, String newContent) throws SQLException {
        String sql = "UPDATE Comments SET Content=?,Created=? WHERE CommentId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, newContent);
            java.util.Date newCreatedTimestamp = new Date();
            ps.setTimestamp(2, new Timestamp(newCreatedTimestamp.getTime()));
            ps.setInt(3, comment.getCommentId());
            ps.executeUpdate();

            // Update the blogComment param before returning to the caller.
            comment.setContent(newContent);
            comment.setCreated(newCreatedTimestamp);
            return comment;
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
     * @param comment
     * @return
     * @throws SQLException
     */
    public Comments delete(Comments comment) throws SQLException {
        String sql = "DELETE FROM Comments WHERE CommentId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, comment.getCommentId());
            ps.executeUpdate();

            // Return null so the caller can no longer operate on the BlogComments instance.
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
     * @param commentId
     * @return comment
     * @throws SQLException
     */
    public Comments getCommentById(int commentId) throws SQLException {
        String sql =
                "SELECT CommentId,Content,Created,UserId,PostId " +
                "FROM Comments WHERE CommentId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, commentId);
            rs = ps.executeQuery();
            PostsDao postsDao = PostsDao.getInstance();
            UsersDao usersDao = UsersDao.getInstance();
            if(rs.next()) {
                int resultCommentId = rs.getInt("CommentId");
                String content = rs.getString("Content");
                Date created =  new Date(rs.getTimestamp("Created").getTime());
                int postId = rs.getInt("PostId");
                int userId = rs.getInt("UserId");

                Posts post = postsDao.getPostByPostId(postId);
                Users user = usersDao.getUserFromUserId(userId);
                Comments comment = new Comments(resultCommentId, content,
                        created, post, user);
                return comment;
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
     * @param user
     * @return list of comments
     * @throws SQLException
     */
    public List<Comments> getCommentsByUserId(Users user) throws SQLException {
        List<Comments> Comments = new ArrayList<Comments>();
        String sql =
                "SELECT CommentId,Content,Created,UserId,PostId " +
                        "FROM Comments WHERE UserId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, user.getUserId());
            rs = ps.executeQuery();
            PostsDao postsDao = PostsDao.getInstance();
            while(rs.next()) {
                int commentId = rs.getInt("CommentId");
                String content = rs.getString("Content");
                Date created =  new Date(rs.getTimestamp("Created").getTime());
                int postId = rs.getInt("PostId");

                Posts post = postsDao.getPostByPostId(postId);
                Comments comment = new Comments(commentId, content, created, post, user);
                Comments.add(comment);
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
        return Comments;
    }

    /**
     * Users can get comments by postId
     * @param post
     * @return list of comments
     * @throws SQLException
     */
    public List<Comments> getCommentsByPostId(Posts post) throws SQLException {
        List<Comments> Comments = new ArrayList<Comments>();
        String sql =
                "SELECT CommentId,Content,Created,UserId,PostId " +
                        "FROM Comments WHERE PostId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, post.getPostId());
            rs = ps.executeQuery();
            UsersDao usersDao = UsersDao.getInstance();
            while(rs.next()) {
                int commentId = rs.getInt("CommentId");
                String content = rs.getString("Content");
                Date created =  new Date(rs.getTimestamp("Created").getTime());
                int userId = rs.getInt("UserId");

                Users user = usersDao.getUserFromUserId(userId);
                Comments comment = new Comments(commentId, content, created, post, user);
                Comments.add(comment);
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
        return Comments;
    }

    /**
     * Users and Administrator can get all comments
     * @return list of comments
     * @throws SQLException
     */
    public List<Comments> getAllComment() throws SQLException{
        List<Comments> Comments = new ArrayList<Comments>();
        String sql = "SELECT CommentId,Content,Created,UserId,PostId FROM Comments;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            UsersDao usersDao = UsersDao.getInstance();
            PostsDao postsDao = PostsDao.getInstance();
            while(rs.next()) {
                int commentId = rs.getInt("CommentId");
                String content = rs.getString("Content");
                Date created =  new Date(rs.getTimestamp("Created").getTime());
                int postId = rs.getInt("PostId");
                int userId = rs.getInt("UserId");

                Posts post = postsDao.getPostByPostId(postId);
                Users user = usersDao.getUserFromUserId(userId);
                Comments comment = new Comments(commentId, content, created, post, user);
                Comments.add(comment);
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
        return Comments;
    }
}
