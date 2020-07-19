package web.servlet;

import web.dal.CollectionsDao;
import web.dal.PostsDao;
import web.model.Collections;
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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/postsave")
public class PostSave extends HttpServlet {
    protected PostsDao postsDao;
    protected CollectionsDao collectionsDao;

    @Override
    public void init() throws ServletException {
        postsDao = PostsDao.getInstance();
        collectionsDao = CollectionsDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String redirect = req.getParameter("redirect");
        int postId = Integer.parseInt(req.getParameter("postId"));
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");
        // if user login, they can save posts; otherwise, they need to sign up or login first
        if (user == null){
            req.getRequestDispatcher("/SignUpLogin.jsp").forward(req,resp);
        }else {
            try {
                Posts post = postsDao.getPostByPostId(postId);
                Collections collection = new Collections(user,post,null);
                collectionsDao.create(collection);
                messages.put("SavePost","Post Save Successful");
                switch (redirect) {
                    case "index":
                        req.getRequestDispatcher("/index.jsp").forward(req, resp);
                        break;
                    case "UserMyProfile":
                        req.getRequestDispatcher("/UserMyProfile.jsp").forward(req, resp);
                        break;
                    case "PostComment":
                        req.getRequestDispatcher("/PostComment.jsp").forward(req, resp);
                        break;
                    case "FindPost":
                        req.getRequestDispatcher("findpost").forward(req, resp);
                        break;
                    case "FindSave":
                        req.getRequestDispatcher("findsave").forward(req, resp);
                        break;
                    case "CommentReply":
                        req.getRequestDispatcher("/CommentReply.jsp").forward(req, resp);
                        break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
