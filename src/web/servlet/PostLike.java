package web.servlet;

import web.dal.LikesDao;
import web.dal.PostsDao;
import web.model.Collections;
import web.model.Likes;
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
import java.util.List;

@WebServlet("/postlike")
public class PostLike extends HttpServlet {
    protected LikesDao likesDao;
    protected PostsDao postsDao;

    @Override
    public void init() throws ServletException {
        likesDao = LikesDao.getInstance();
        postsDao = PostsDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int postId = Integer.parseInt(req.getParameter("postId"));
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");

        String redirect = req.getParameter("redirect");
        // if user login, they can like posts; otherwise, they need to sign up or login first
        if (user == null){
            req.getRequestDispatcher("/SignUpLogin.jsp").forward(req,resp);
        }else {
            try {
                Posts post = postsDao.getPostByPostId(postId);
                Likes like = new Likes(user,post,null);
                likesDao.create(like);
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
