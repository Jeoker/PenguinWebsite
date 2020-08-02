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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/userlogin")
public class UserLogin extends HttpServlet {
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
        req.getRequestDispatcher("/Login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        String status = req.getParameter("status");

        // if username, password or status is empty, login failed
        if(userName == null || userName.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                status == null || status.trim().isEmpty()){
            messages.put("login", "Invalid UserName Or Password Or Status");
        }else {
            try {
                Users user = new Users(userName,password,Users.Status.valueOf(status));
                // Verify user identity
                Users resultUser = usersDao.getUserByUserNamePasswordStatus(user);
                if (resultUser != null){
                    messages.put("login", "Login Successful");
                    // put user into HttpSession for later using
                    HttpSession session = req.getSession();
                    if ("User".equals(resultUser.getStatus().toString()))
                    {
                    	session.setAttribute("user",resultUser);
                    }
                    else if ("Researcher".equals(resultUser.getStatus().toString()))
                    {
                    	Researchers researcher = researchersDao.getResearchersByUserId(resultUser.getUserId());
                    	session.setAttribute("researcher",researcher);
                    	session.setAttribute("user",resultUser);
                    }
                    else if ("Administrator".equals(resultUser.getStatus().toString()))
                    {
                    	session.setAttribute("user",resultUser);
                    }
                    req.getRequestDispatcher("/index.jsp").forward(req,resp);
                }else {
                    messages.put("login", "Incorrect UserName Or Password Or Status");
                    req.getRequestDispatcher("/Login.jsp").forward(req,resp);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
