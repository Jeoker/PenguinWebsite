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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/postdelete")
public class PostDelete extends HttpServlet {
    protected PostsDao postsDao;

    @Override
    public void init() throws ServletException {
        postsDao = PostsDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        int postId = Integer.parseInt(req.getParameter("postId"));
        try {
            Posts post = postsDao.getPostByPostId(postId);
            Posts result = postsDao.delete(post);
            if(result == null){
                messages.put("deletePost","Delete Post Successfully");
            }else {
                messages.put("deletePost","Delete Post Failed");
            }
            req.getRequestDispatcher("/AdministratorMyProfile.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        int postId = Integer.parseInt(req.getParameter("postId"));
        try {
            Posts post = postsDao.getPostByPostId(postId);
            Posts result = postsDao.delete(post);
            if(result == null){
                messages.put("deletePost","Delete Post Successfully");
            }else {
                messages.put("deletePost","Delete Post Failed");
            }
            req.getRequestDispatcher("findpost").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
