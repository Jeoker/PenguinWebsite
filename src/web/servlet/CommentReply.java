package web.servlet;

import web.dal.CommentsDao;
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
import java.util.*;

@WebServlet("/commentreply")
public class CommentReply extends HttpServlet {
    protected CommentsDao commentsDao;

    @Override
    public void init() throws ServletException {
        commentsDao = CommentsDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        int commentId = Integer.parseInt(req.getParameter("commentId"));
        try {
            HttpSession session = req.getSession();
            Comments comment = commentsDao.getCommentById(commentId);
            session.setAttribute("currentComment",comment);

            List<Comments> commentsList = new ArrayList<>();
            commentsList = commentsDao.getReplyCommentsByFatherCommentId(commentId);
            session.setAttribute("currentReplies",commentsList);
            req.getRequestDispatcher("/CommentReply.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        int commentId = Integer.parseInt(req.getParameter("commentId"));
        String content = req.getParameter("content");

        Date date = new Date();
        HttpSession session = req.getSession();
        Users user = (Users)session.getAttribute("user");
        try {
            Comments comment = commentsDao.getCommentById(commentId);
            Comments childComment = new Comments(content,date,comment.getPost(),user,comment);
            Comments resultComment = commentsDao.create(childComment);
            if(resultComment != null){
                messages.put("createReply","Reply Comment Successfully");
                req.getRequestDispatcher("/CommentReply.jsp").forward(req,resp);
            }else {
                messages.put("createReply","Reply Comment Failed");
                req.getRequestDispatcher("/CommentReply.jsp").forward(req,resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}