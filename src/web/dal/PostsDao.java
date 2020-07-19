package web.dal;

import web.model.Posts;
import web.model.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostsDao {
    protected ConnectionManager connectionManager;

    private static PostsDao instance = null;
    protected PostsDao() {
        connectionManager = new ConnectionManager();
    }
    public static PostsDao getInstance() {
        if(instance == null) {
            instance = new PostsDao();
        }
        return instance;
    }

    /**
     * Users create posts
     * @param post
     * @return post
     * @throws SQLException
     */
    public Posts create(Posts post) throws SQLException{
        String sql =
                "INSERT INTO Posts(Title,Picture,Content,Published,Created,UserId) " +
                        "VALUES(?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, post.getTitle());
            // Note: for the sake of simplicity, just set Picture to null for now.
            if (post.getPicture() == null || post.getPicture().equals("")){
                ps.setNull(2, Types.BLOB);
            }else {
                ps.setString(2, post.getPicture());
            }
            ps.setString(3, post.getContent());
            ps.setBoolean(4, post.isPublished());
            ps.setTimestamp(5, new Timestamp(post.getCreated().getTime()));
            ps.setInt(6, post.getUser().getUserId());
            ps.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            rs = ps.getGeneratedKeys();
            int postId = -1;
            if(rs.next()) {
                postId = rs.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            post.setPostId(postId);
            return post;
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
     * Users can delete their own posts
     * Administrator can delete user's posts
     * @param post
     * @return null
     * @throws SQLException
     */
    public Posts delete(Posts post) throws SQLException {
        // Note: BlogComments has a fk constraint on BlogPosts with the reference option
        // ON DELETE CASCADE. So this delete operation will delete all the referencing
        // BlogComments.
        String sql = "DELETE FROM Posts WHERE PostId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, post.getPostId());
            ps.executeUpdate();

            // Return null so the caller can no longer operate on the BlogPosts instance.
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
     * Users can update their post's content
     * @param post
     * @param newContent
     * @return post
     * @throws SQLException
     */
    public Posts updateContent(Posts post, String newContent) throws SQLException {
        String sql = "UPDATE Posts SET Content=?,Created=? WHERE PostId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, newContent);
            // Sets the Created timestamp to the current time.
            java.util.Date newCreatedTimestamp = new Date();
            ps.setTimestamp(2, new Timestamp(newCreatedTimestamp.getTime()));
            ps.setInt(3, post.getPostId());
            ps.executeUpdate();

            // Update the Post param before returning to the caller.
            post.setContent(newContent);
            post.setCreated(newCreatedTimestamp);
            return post;
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
     * Users can update their post's picture
     * @param post
     * @param newPicture
     * @return post
     * @throws SQLException
     */
    public Posts updatePicture(Posts post, String newPicture) throws SQLException {
        String sql = "UPDATE Posts SET Picture=?,Created=? WHERE PostId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, newPicture);
            // Sets the Created timestamp to the current time.
            java.util.Date newCreatedTimestamp = new Date();
            ps.setTimestamp(2, new Timestamp(newCreatedTimestamp.getTime()));
            ps.setInt(3, post.getPostId());
            ps.executeUpdate();

            // Update the Post param before returning to the caller.
            post.setPicture(newPicture);
            post.setCreated(newCreatedTimestamp);
            return post;
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
     * Administrator can get post by postId
     * @param postId
     * @return post
     * @throws SQLException
     */
    public Posts getPostByPostId(int postId) throws SQLException {
        String sql = "SELECT PostId,Title,Picture,Content,Published,Created,UserId FROM Posts WHERE PostId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, postId);
            rs = ps.executeQuery();
            UsersDao usersDao = UsersDao.getInstance();
            if(rs.next()) {
                int resultPostId = rs.getInt("PostId");
                String title = rs.getString("Title");
                String picture = rs.getString("Picture");
                String content = rs.getString("Content");
                boolean published = rs.getBoolean("Published");
                Date created =  new Date(rs.getTimestamp("Created").getTime());
                int userId = rs.getInt("UserId");

                Users User = usersDao.getUserFromUserId(userId);
                Posts post = new Posts(resultPostId, title, picture, content, published, created, User);
                return post;
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
     * Users can get their own posts by userId
     * @param user
     * @return list of posts
     * @throws SQLException
     */
    public List<Posts> getPostsByUserId(Users user) throws SQLException {
        List<Posts> posts = new ArrayList<Posts>();
        String sql = "SELECT PostId,Title,Picture,Content,Published,Created,UserId FROM Posts WHERE UserId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, user.getUserId());
            rs = ps.executeQuery();
            while(rs.next()) {
                int postId = rs.getInt("PostId");
                String title = rs.getString("Title");
                String picture = rs.getString("Picture");
                String content = rs.getString("Content");
                boolean published = rs.getBoolean("Published");
                Date created =  new Date(rs.getTimestamp("Created").getTime());
                Posts post = new Posts(postId, title, picture, content, published, created, user);
                posts.add(post);
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
        return posts;
    }

    public List<Posts> getAllPost() throws SQLException {
        List<Posts> posts = new ArrayList<Posts>();
        String sql = "SELECT PostId,Title,Picture,Content,Published,Created,UserId FROM Posts;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UsersDao usersDao = UsersDao.getInstance();
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                int postId = rs.getInt("PostId");
                String title = rs.getString("Title");
                String picture = rs.getString("Picture");
                String content = rs.getString("Content");
                boolean published = rs.getBoolean("Published");
                Date created =  new Date(rs.getTimestamp("Created").getTime());
                int userId = rs.getInt("UserId");

                Users user = usersDao.getUserFromUserId(userId);
                Posts post = new Posts(postId, title, picture, content, published, created, user);
                posts.add(post);
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
        return posts;
    }

    public List<Posts> getAllPostByNumberOfLikes() throws SQLException {
        List<Posts> posts = new ArrayList<Posts>();
        String sql = "SELECT Posts.PostId,Title,Picture,Content,Published," +
                "Created, Posts.UserId " +
                "FROM Posts LEFT JOIN Likes ON Posts.postId = Likes.postId " +
                "GROUP BY Posts.postId " +
                "ORDER BY COUNT(Likes.postId) DESC;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UsersDao usersDao = UsersDao.getInstance();
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                int postId = rs.getInt("PostId");
                String title = rs.getString("Title");
                String picture = rs.getString("Picture");
                String content = rs.getString("Content");
                boolean published = rs.getBoolean("Published");
                Date created =  new Date(rs.getTimestamp("Created").getTime());
                int userId = rs.getInt("UserId");

                Users user = usersDao.getUserFromUserId(userId);
                Posts post = new Posts(postId, title, picture, content, published, created, user);
                posts.add(post);
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
        return posts;
    }

    public List<Posts> getAllPostByNumberOfCollections() throws SQLException {
        List<Posts> posts = new ArrayList<Posts>();
        String sql = "SELECT Posts.PostId,Title,Picture,Content,Published," +
                "Created, Posts.UserId " +
                "FROM Posts LEFT JOIN Collections ON Posts.postId = Collections.postId " +
                "GROUP BY Posts.postId " +
                "ORDER BY COUNT(Collections.postId) DESC;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UsersDao usersDao = UsersDao.getInstance();
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                int postId = rs.getInt("PostId");
                String title = rs.getString("Title");
                String picture = rs.getString("Picture");
                String content = rs.getString("Content");
                boolean published = rs.getBoolean("Published");
                Date created =  new Date(rs.getTimestamp("Created").getTime());
                int userId = rs.getInt("UserId");

                Users user = usersDao.getUserFromUserId(userId);
                Posts post = new Posts(postId, title, picture, content, published, created, user);
                posts.add(post);
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
        return posts;
    }

    public List<Posts> getAllPostByCreated() throws SQLException {
        List<Posts> posts = new ArrayList<Posts>();
        String sql = "SELECT PostId,Title,Picture,Content,Published,Created,UserId " +
                "FROM Posts ORDER BY Created DESC;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UsersDao usersDao = UsersDao.getInstance();
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                int postId = rs.getInt("PostId");
                String title = rs.getString("Title");
                String picture = rs.getString("Picture");
                String content = rs.getString("Content");
                boolean published = rs.getBoolean("Published");
                Date created =  new Date(rs.getTimestamp("Created").getTime());
                int userId = rs.getInt("UserId");

                Users user = usersDao.getUserFromUserId(userId);
                Posts post = new Posts(postId, title, picture, content, published, created, user);
                posts.add(post);
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
        return posts;
    }
}
