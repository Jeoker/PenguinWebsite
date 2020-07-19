package web.servlet;

import web.dal.CommentsDao;
import web.model.Comments;
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
import java.util.List;

@WebServlet("/findcomment")
public class FindComment extends HttpServlet {
    protected CommentsDao commentsDao;

    @Override
    public void init() throws ServletException {
        commentsDao = CommentsDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");
        List<Comments> commentsList = new ArrayList<>();
        try {
            commentsList = commentsDao.getCommentsByUserId(user);
            req.setAttribute("userComment",commentsList);
            req.getRequestDispatcher("/UserMyProfile.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");
        List<Comments> commentsList = new ArrayList<>();
        try {
            commentsList = commentsDao.getCommentsByUserId(user);
            req.setAttribute("userComment",commentsList);
            req.getRequestDispatcher("/UserMyProfile.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
