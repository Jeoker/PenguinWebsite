package web.servlet;

import web.dal.CommentsDao;
import web.model.Comments;
import web.model.Posts;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/commentupdate")
public class CommentUpdate extends HttpServlet {
    protected CommentsDao commentsDao;

    @Override
    public void init() throws ServletException {
        commentsDao = CommentsDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int commentId = Integer.parseInt(req.getParameter("commentId"));
        try {
            Comments comment = commentsDao.getCommentById(commentId);
            req.setAttribute("comment",comment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/UpdateComment.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int commentId = Integer.parseInt(req.getParameter("commentId"));
        String newContent = req.getParameter("newContent");
        try {
            Comments comment = commentsDao.getCommentById(commentId);
            comment = commentsDao.updateContent(comment,newContent);
            req.getRequestDispatcher("findcomment").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
