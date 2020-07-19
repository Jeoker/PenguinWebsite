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

@WebServlet("/postunsave")
public class PostUnsave extends HttpServlet {
    protected CollectionsDao collectionsDao;
    protected PostsDao postsDao;

    @Override
    public void init() throws ServletException {
        collectionsDao = CollectionsDao.getInstance();
        postsDao = PostsDao.getInstance();
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
        try {
            Posts post = postsDao.getPostByPostId(postId);
            Collections collection = collectionsDao.getCollectionByUserIdPostId(user,post);
            Collections result = collectionsDao.delete(collection);
            if(result == null){
                messages.put("PostUnsave","Unsave Post Successful");
            }else {
                messages.put("PostUnsave","Unsave Post Failed");
            }
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
