package web.servlet;

import web.dal.CommentsDao;
import web.dal.PostsDao;
import web.model.Comments;
import web.model.Posts;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/postcomment")
public class PostComment extends HttpServlet {
    protected PostsDao postsDao;
    protected CommentsDao commentsDao;

    @Override
    public void init() throws ServletException {
        postsDao = PostsDao.getInstance();
        commentsDao = CommentsDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int postId = Integer.parseInt(req.getParameter("postId"));
        try {
            Posts post = postsDao.getPostByPostId(postId);
            HttpSession session = req.getSession();
            session.setAttribute("currentPost",post);
            List<Comments> commentsList = commentsDao.getCommentsByPostId(post);
            session.setAttribute("currentPostComment",commentsList);
            req.getRequestDispatcher("/PostComment.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}