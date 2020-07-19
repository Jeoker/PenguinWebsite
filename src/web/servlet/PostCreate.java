package web.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import web.dal.PostsDao;
import web.model.Posts;
import web.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/postcreate")
public class PostCreate extends HttpServlet {
    protected PostsDao postsDao;

    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        postsDao = PostsDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        messages.put("isNewPost","yes");
        req.setAttribute("messages", messages);
        //Just render the JSP.
        req.getRequestDispatcher("/UserMyProfile.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String title = req.getParameter("title");
        String picture = req.getParameter("picture");
        String content = req.getParameter("content");

        if(title == null || title.trim().isEmpty()){
            messages.put("NewPost","Title cannot be empty");
            req.getRequestDispatcher("/NewPost.jsp").forward(req,resp);
        }else {
            Date date = new Date();
            HttpSession session = req.getSession();
            Users user = (Users) session.getAttribute("user");
            Posts post = new Posts(title,picture,content,true,date,user);
            try {
                postsDao.create(post);
                messages.put("NewPost","Post Successfully");
                req.getRequestDispatcher("/UserMyProfile.jsp").forward(req,resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
