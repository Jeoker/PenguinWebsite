package web.servlet;

import web.dal.PostsDao;
import web.model.Posts;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/postupdate")
public class PostUpdate extends HttpServlet {
    protected PostsDao postsDao;

    @Override
    public void init() throws ServletException {
        postsDao = PostsDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int postId = Integer.parseInt(req.getParameter("postId"));
        try {
            Posts post = postsDao.getPostByPostId(postId);
            req.setAttribute("post",post);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/UpdatePost.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int postId = Integer.parseInt(req.getParameter("postId"));
        String newContent = req.getParameter("newContent");
        try {
            Posts post = postsDao.getPostByPostId(postId);
            post = postsDao.updateContent(post,newContent);
            req.getRequestDispatcher("findpost").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
