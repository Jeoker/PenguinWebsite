package web.servlet;

import web.dal.ImagesDao;
import web.dal.UAVsDao;
import web.model.Images;
import web.model.Posts;
import web.model.Sites;
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

import static java.lang.Integer.max;

@WebServlet("/imagesFromSite")
public class imagesFromSite extends HttpServlet {
    protected ImagesDao imagesDao;
    int startIdx = 0;
    int imagesPerPage = 20;
    int totImages;
    int totalPages;
    int siteId;

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
        String id = req.getParameter("siteId");

        try {
            int tmp = Integer.valueOf(id);
            siteId = tmp==0? siteId : tmp;
            if (siteId == 0)
                return;
        }  catch (NumberFormatException e) {
            if (siteId == 0)
                return;
        }
        try {
            totImages = imagesDao.countImagesFromSite(siteId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        totalPages = (totImages-1) / imagesPerPage + 1;

        String p = req.getParameter("page");
        int page;
        try {
            page = Integer.valueOf(p);
        } catch (NumberFormatException e) {
            page = 1;
        }
        startIdx = (page-1) * imagesPerPage;
        List<Images> imageList = new ArrayList<>();
        try {
            imageList = imagesDao.getImageBySite(siteId,startIdx,imagesPerPage);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("curImages", imageList);
        req.setAttribute("page", page);
        req.setAttribute("totalPages", totalPages);
       req.getRequestDispatcher("/ImageBrowser.jsp").forward(req, resp);
    }
}
