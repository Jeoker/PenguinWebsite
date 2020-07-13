package web.servlet;

import web.dal.UsersDao;
import web.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/userdelete")
public class UserDelete extends HttpServlet {
    protected UsersDao usersDao;

    @Override
    public void init() throws ServletException {
        usersDao = UsersDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        int userId = Integer.parseInt(req.getParameter("userId"));
        try {
            Users user = usersDao.getUserFromUserId(userId);
            Users result = usersDao.delete(user);
            if(result == null){
                messages.put("deleteUser","Delete User Successfully");
            }else {
                messages.put("deleteUser","Delete User Failed");
            }
            req.getRequestDispatcher("/AdministratorMyProfile.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
