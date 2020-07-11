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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/allpost")
public class allpost extends HttpServlet {
    protected PostsDao postsDao;

    @Override
    public void init() throws ServletException {
        postsDao = PostsDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Posts> postsList = new ArrayList<>();
        try {
            postsList = postsDao.getAllPost();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("allPosts", postsList);
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);

    }
}
