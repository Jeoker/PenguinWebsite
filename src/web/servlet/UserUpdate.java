package web.servlet;

import web.dal.UsersDao;
import web.model.Users;

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

@WebServlet("/userupdate")
public class UserUpdate extends HttpServlet {

    protected UsersDao usersDao;

    @Override
    public void init() throws ServletException {
        usersDao = UsersDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve/Validate username and password.
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        if(userName == null || userName.trim().isEmpty() ||
                password == null || password.trim().isEmpty()){
            messages.put("update", "Invalid UserName Or Password");
            req.getRequestDispatcher("/UserSetting.jsp").forward(req,resp);
        }else {
            int userId = Integer.parseInt(req.getParameter("userId"));
            try {
                Users user = usersDao.getUserFromUserId(userId);
                user = usersDao.updateUserName(user,userName);
                user = usersDao.updatePassword(user,password);
                HttpSession session = req.getSession();
                session.setAttribute("user",user);
                messages.put("update", "Change Successful");
                req.getRequestDispatcher("finduser").forward(req,resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
