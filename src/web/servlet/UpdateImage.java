package web.servlet;

import web.dal.ImagesDao;
import web.model.Images;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/updateimage")
public class UpdateImage extends HttpServlet {
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
        int tmp_int = Integer.parseInt(req.getParameter("imageId"));
        HttpSession sess = req.getSession();
        sess.setAttribute("cur_imageId", tmp_int);
        req.getRequestDispatcher("/UpdateImage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        System.out.println("imhere");

        int imageId = Integer.parseInt(req.getParameter("imageId"));
        try {
            Images result = imagesDao.getImageById(imageId);
            System.out.println(result.getImageId());
            String path = req.getParameter("path");
            if (path =="" || path == null){
                path = result.getMediaLink();
            }
            System.out.println(path);
            imagesDao.updatePath(result,path);
            messages.put("result","Update Image Successfully");
            req.getRequestDispatcher("/UpdateImage.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
