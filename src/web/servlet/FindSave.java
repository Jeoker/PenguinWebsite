package web.servlet;

import web.dal.CollectionsDao;
import web.dal.CommentsDao;
import web.dal.PostsDao;
import web.model.Collections;
import web.model.Comments;
import web.model.Posts;
import web.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/findsave")
public class FindSave extends HttpServlet {
    protected CollectionsDao collectionsDao;
    protected PostsDao postsDao;
    protected CommentsDao commentsDao;

    @Override
    public void init() throws ServletException {
        collectionsDao = CollectionsDao.getInstance();
        postsDao = PostsDao.getInstance();
        commentsDao = CommentsDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");
        List<Collections> collectionsList = new ArrayList<>();
        List<Posts> postsList = new ArrayList<>();
        List<Comments> commentsList = new ArrayList<>();
        try {
            collectionsList = collectionsDao.getCollectionsByUserId(user);
            for(Collections collection : collectionsList){
                if (collection.getPost() != null){
                    Posts post = postsDao.getPostByPostId(collection.getPost().getPostId());
                    postsList.add(post);
                }else if (collection.getComment() != null){
                    Comments comment = commentsDao.getCommentById(collection.getComment().getCommentId());
                    commentsList.add(comment);
                }

            }
            req.setAttribute("savedPost",postsList);
            req.setAttribute("savedComment",commentsList);
            req.getRequestDispatcher("/UserMyProfile.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");
        List<Collections> collectionsList = new ArrayList<>();
        List<Posts> postsList = new ArrayList<>();
        List<Comments> commentsList = new ArrayList<>();
        try {
            collectionsList = collectionsDao.getCollectionsByUserId(user);
            for(Collections collection : collectionsList){
                if (collection.getPost() != null){
                    Posts post = postsDao.getPostByPostId(collection.getPost().getPostId());
                    postsList.add(post);
                }else if (collection.getComment() != null){
                    Comments comment = commentsDao.getCommentById(collection.getComment().getCommentId());
                    commentsList.add(comment);
                }

            }
            req.setAttribute("savedPost",postsList);
            req.setAttribute("savedComment",commentsList);
            req.getRequestDispatcher("/UserMyProfile.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
