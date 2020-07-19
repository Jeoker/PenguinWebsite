package web.servlet;

import web.dal.LikesDao;
import web.model.Likes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/commentlikedelete")
public class CommentLikeDelete extends HttpServlet {
    protected LikesDao likesDao;

    @Override
    public void init() throws ServletException {
        likesDao = LikesDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String redirect = req.getParameter("redirect");
        int likeId = Integer.parseInt(req.getParameter("likeId"));
        try {
            Likes like = likesDao.getLikeById(likeId);
            likesDao.delete(like);
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
