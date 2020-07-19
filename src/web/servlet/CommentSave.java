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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/commentsave")
public class CommentSave extends HttpServlet {
    protected CommentsDao commentsDao;
    protected CollectionsDao collectionsDao;

    @Override
    public void init() throws ServletException {
        commentsDao = CommentsDao.getInstance();
        collectionsDao = CollectionsDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String redirect = req.getParameter("redirect");
        int commentId = Integer.parseInt(req.getParameter("commentId"));
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");
        // if user login, they can save comments; otherwise, they need to sign up or login first
        if (user == null){
            req.getRequestDispatcher("/SignUpLogin.jsp").forward(req,resp);
        }else {
            try {
                Comments comment = commentsDao.getCommentById(commentId);
                Collections collection = new Collections(user,null,comment);
                collectionsDao.create(collection);
                messages.put("SaveComment","Comment Save Successful");
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
                    case "FindComment":
                        req.getRequestDispatcher("findcomment").forward(req, resp);
                        break;
                    case "FindSave":
                        req.getRequestDispatcher("findsave").forward(req, resp);
                        break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
