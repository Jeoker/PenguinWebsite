package web.servlet;

import web.dal.CommentsDao;
import web.dal.PostsDao;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/commentcreate")
public class CommentCreate extends HttpServlet {
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
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String content = req.getParameter("content");
        int postId = Integer.parseInt(req.getParameter("postId"));
        if(content == null || content.trim().isEmpty()){
            messages.put("NewComment","Content cannot be empty");
            req.getRequestDispatcher("/PostComment.jsp").forward(req,resp);
        }else {
            Date date = new Date();
            HttpSession session = req.getSession();
            try {
                Users user = (Users) session.getAttribute("user");
                Posts post = postsDao.getPostByPostId(postId);
                Comments comment = new Comments(content,date,post,user,null);
                commentsDao.create(comment);
                messages.put("NewComment","Comment successfully");
                List<Comments> commentsList = commentsDao.getCommentsByPostId(post);
                session.setAttribute("currentPostComment",commentsList);
                req.getRequestDispatcher("/PostComment.jsp").forward(req,resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
