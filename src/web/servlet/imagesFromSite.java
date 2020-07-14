package web.servlet;

import web.dal.ImagesDao;
import web.model.Images;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/imagesFromSite")
public class imagesFromSite extends HttpServlet {
    protected ImagesDao imagesDao;

    @Override
    public void init() throws ServletException {
        imagesDao = ImagesDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Images> imageList = new ArrayList<>();
        try {
            imageList = imagesDao.getImageBySite(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("siteImages", imageList);
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");
        req.getRequestDispatcher("/ImageBrowser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Images> imageList = new ArrayList<>();
        try {
            imageList = imagesDao.getImageBySite(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("siteImages", imageList);
        HttpSession session = req.getSession();
        req.getRequestDispatcher("/ImageBrowser.jsp").forward(req, resp);
    }
}
