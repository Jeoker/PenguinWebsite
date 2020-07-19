package web.servlet;

import web.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/finduser")
public class FindUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");
        req.setAttribute("currentUser",user);
        //Just render the JSP.
        req.getRequestDispatcher("/UserMyProfile.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user");
        req.setAttribute("currentUser",user);
        messages.put("update", "Change Successful");
        //Just render the JSP.
        req.getRequestDispatcher("/UserMyProfile.jsp").forward(req,resp);
    }
}
