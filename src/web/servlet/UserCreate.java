package web.servlet;

import web.dal.ResearchersDao;
import web.dal.UsersDao;
import web.model.Researchers;
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

@WebServlet("/usercreate")
public class UserCreate extends HttpServlet {
    protected UsersDao usersDao;
    protected ResearchersDao researchersDao;

    @Override
    public void init() throws ServletException {
        usersDao = UsersDao.getInstance();
        researchersDao = ResearchersDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.
        req.getRequestDispatcher("/SignUp.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String userName = req.getParameter("username");
        String password = req.getParameter("password");

        // if username or password is empty, sign up failed
        if(userName == null || userName.trim().isEmpty() ||
                password == null || password.trim().isEmpty()){
            messages.put("signUp", "Invalid UserName Or Password");
            req.getRequestDispatcher("/SignUp.jsp").forward(req,resp);
        }else {
            String status = req.getParameter("status");
            try {
                if("User".equals(status)){
                    Users user = new Users(userName,password,Users.Status.valueOf(status));
                    // create user
                    usersDao.create(user);
                }else if ("Researcher".equals(status)){
                    Researchers researcher = new Researchers(userName,password,Users.Status.valueOf(status),null,null,0,null,null);
                    researchersDao.create(researcher);
                }

                messages.put("signUp", "Sign Up Successful");
                req.getRequestDispatcher("/index.jsp").forward(req,resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
