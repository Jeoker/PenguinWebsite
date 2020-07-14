package web.servlet;


import web.dal.ImagesDao;
import web.dal.PostsDao;
import web.model.Images;
import web.model.Posts;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/imagedelete")
public class ImageDelete extends HttpServlet {
    protected ImagesDao imagesDao;

    @Override
    public void init() throws ServletException {
        imagesDao = ImagesDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        int imageId = Integer.parseInt(req.getParameter("imageId"));
        try {
            Images result = imagesDao.delete(new Images(imageId));
            messages.put("result","Delete Image Successfully");
            req.getRequestDispatcher("/ImageBrowser.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
