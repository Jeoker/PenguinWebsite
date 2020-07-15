package web.servlet;

import web.dal.PostsDao;
import web.dal.UsersDao;
import web.model.Posts;
import web.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/findposts")
public class FindPosts extends HttpServlet {
    protected PostsDao postsDao;

    @Override
    public void init() throws ServletException {
        postsDao = PostsDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Posts> postsList = new ArrayList<>();
        try {
            postsList = postsDao.getAllPost();
            req.setAttribute("allPosts",postsList);
            req.getRequestDispatcher("/AdministratorMyProfile.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
