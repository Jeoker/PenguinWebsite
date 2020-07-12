package web.dal;

import web.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollectionsDao {
    protected ConnectionManager connectionManager;

    private static CollectionsDao instance = null;
    protected CollectionsDao() {
        connectionManager = new ConnectionManager();
    }
    public static CollectionsDao getInstance() {
        if(instance == null) {
            instance = new CollectionsDao();
        }
        return instance;
    }

    /**
     * User can create collection
     * @param collection
     * @return collection
     * @throws SQLException
     */
    public Collections create(Collections collection) throws SQLException {
        String sql = "INSERT INTO Collections(UserId,PostId,CommentId) VALUES(?,?,?);";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,collection.getUser().getUserId());
            // NullPointerException
            if(collection.getPost() != null){
                ps.setInt(2,collection.getPost().getPostId());
            }else {
                ps.setNull(2,Types.INTEGER);
            }
            if(collection.getComment() != null){
                ps.setInt(3,collection.getComment().getCommentId());
            }else {
                ps.setNull(3,Types.INTEGER);
            }
            ps.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            rs = ps.getGeneratedKeys();
            int collectionId = -1;
            if(rs.next()) {
                collectionId = rs.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            collection.setCollectionsId(collectionId);
            return collection;
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
     * User can delete collection
     * @param collection
     * @return null
     * @throws SQLException
     */
    public Collections delete(Collections collection) throws SQLException {
        String sql = "DELETE FROM Collections WHERE CollectionId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, collection.getCollectionsId());
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
     * Administrator can get reshare by collectionId
     * @param collectionId
     * @return collection
     * @throws SQLException
     */
    public Collections getCollectionsById(int collectionId) throws SQLException {
        String sql =
                "SELECT CollectionId,UserId,PostId,CommentId " +
                        "FROM Collections " +
                        "WHERE CollectionId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, collectionId);
            rs = ps.executeQuery();
            UsersDao usersDao = UsersDao.getInstance();
            PostsDao postsDao = PostsDao.getInstance();
            CommentsDao commentsDao = CommentsDao.getInstance();
            if(rs.next()) {
                int resultCollectionId = rs.getInt("CollectionId");
                int userId = rs.getInt("UserId");
                int postId = rs.getInt("PostId");
                int commentId = rs.getInt("CommentId");

                Users user = usersDao.getUserFromUserId(userId);
                Posts post = postsDao.getPostByPostId(postId);
                Comments comment = commentsDao.getCommentById(commentId);
                Collections collection = new Collections(resultCollectionId, user, post, comment);
                return collection;
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
     * Users can get collections by userId
     * @param user
     * @return list of collections
     * @throws SQLException
     */
    public List<Collections> getCollectionsByUserId(Users user) throws SQLException {
        List<Collections> collections = new ArrayList<Collections>();
        String sql =
                "SELECT CollectionId,UserId,PostId,CommentId " +
                        "FROM Collections " +
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
                int collectionId = rs.getInt("CollectionId");
                int postId = rs.getInt("PostId");
                int commentId = rs.getInt("CommentId");

                Posts post = postsDao.getPostByPostId(postId);
                Comments comment = commentsDao.getCommentById(commentId);
                Collections collection = new Collections(collectionId, user, post, comment);
                collections.add(collection);
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
        return collections;
    }

    /**
     * Users can get collection by userId and postId
     * @param user, post
     * @return collection
     * @throws SQLException
     */
    public Collections getCollectionByUserIdPostId(Users user, Posts post) throws SQLException {
        String sql =
                "SELECT CollectionId,UserId,PostId,CommentId " +
                        "FROM Collections " +
                        "WHERE UserId=? AND PostId=?;";
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
                int collectionId = rs.getInt("CollectionId");
                int commentId = rs.getInt("CommentId");

                Comments comment = commentsDao.getCommentById(commentId);
                Collections collection = new Collections(collectionId, user, post, comment);
                return collection;
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
     * Users can get collection by userId and commentId
     * @param user, comment
     * @return collection
     * @throws SQLException
     */
    public Collections getCollectionByUserIdCommentId(Users user, Comments comment) throws SQLException {
        String sql =
                "SELECT CollectionId,UserId,PostId,CommentId " +
                        "FROM Collections " +
                        "WHERE UserId=? AND CommentId=?;";
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
                int collectionId = rs.getInt("CollectionId");
                int postId = rs.getInt("PostId");

                Posts post = postsDao.getPostByPostId(postId);
                Collections collection = new Collections(collectionId, user, post, comment);
                return collection;
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
     * Administrator can get all collections
     * @return list of collections
     * @throws SQLException
     */
    public List<Collections> getAllCollections() throws SQLException {
        List<Collections> collections = new ArrayList<Collections>();
        String sql =
                "SELECT CollectionId,UserId,PostId,CommentId FROM Collections;";
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
                int collectionId = rs.getInt("CollectionId");
                int userId = rs.getInt("UserId");
                int postId = rs.getInt("PostId");
                int commentId = rs.getInt("CommentId");

                Users user = usersDao.getUserFromUserId(userId);
                Posts post = postsDao.getPostByPostId(postId);
                Comments comment = commentsDao.getCommentById(commentId);
                Collections reshare = new Collections(collectionId, user, post, comment);
                collections.add(reshare);
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
        return collections;
    }
}
