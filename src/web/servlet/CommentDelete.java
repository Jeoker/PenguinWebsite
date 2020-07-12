package web.servlet;

import web.dal.CommentsDao;
import web.model.Comments;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/commentdelete")
public class CommentDelete extends HttpServlet {
    protected CommentsDao commentsDao;

    @Override
    public void init() throws ServletException {
        commentsDao = CommentsDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        int commentId = Integer.parseInt(req.getParameter("commentId"));
        try {
            Comments comment = commentsDao.getCommentById(commentId);
            Comments result = commentsDao.delete(comment);
            if(result == null){
                messages.put("commentDelete","Delete Comment Successfully");
            }else {
                messages.put("commentDelete","Delete Comment Failed");
            }
            req.getRequestDispatcher("/UserMyProfile.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
