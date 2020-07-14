package web.servlet;

import web.dal.ImagesDao;
import web.model.Cameras;
import web.model.Images;
import web.model.Sites;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/UploadNewImage")
public class UploadNewImage extends HttpServlet {
     protected ImagesDao imagesDao;

    @Override
    public void init() throws ServletException {
        imagesDao = ImagesDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        req.getRequestDispatcher("/UploadNewImage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String name = req.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            messages.put("success", "Invalid Name");
        } else {
            // Create the BlogUser.
            String type = req.getParameter("type");
            String path = req.getParameter("path");
            int siteId = Integer.valueOf(req.getParameter("site"));
            Timestamp time = new Timestamp(System.currentTimeMillis());
            try {
                // Exercise: parse the input for StatusLevel.
                Images image = new Images(name, type, 2, new Sites(siteId),
                      path,time,800,600,0,0,new Cameras(2));
                image = imagesDao.create(image);
                messages.put("success", "Successfully created " + name);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        req.getRequestDispatcher("/UploadNewImage.jsp").forward(req, resp);
    }

}
