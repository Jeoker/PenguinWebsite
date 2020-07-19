package web.servlet;

import web.dal.PostsDao;
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

@WebServlet("/findpost")
public class FindPost extends HttpServlet {
    protected PostsDao postsDao;

    @Override
    public void init() throws ServletException {
        postsDao = PostsDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");
        List<Posts> postsList = new ArrayList<>();
        try {
            postsList = postsDao.getPostsByUserId(user);
            req.setAttribute("userpost",postsList);
            req.getRequestDispatcher("/UserMyProfile.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");
        List<Posts> postsList = new ArrayList<>();
        try {
            postsList = postsDao.getPostsByUserId(user);
            req.setAttribute("userpost",postsList);
            req.getRequestDispatcher("/UserMyProfile.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
