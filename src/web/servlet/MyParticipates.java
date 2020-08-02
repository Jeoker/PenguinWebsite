package web.servlet;

import web.dal.ParticipatesDao;
import web.dal.ResearchersDao;
import web.dal.UsersDao;
import web.model.Users;
import web.model.Participates;
import web.model.Researchers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/myparticipates")
public class MyParticipates extends HttpServlet {
    protected ParticipatesDao participatesDao;

    @Override
    public void init() throws ServletException {
    	participatesDao = ParticipatesDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Participates> participates = new ArrayList<>();
        try {
        	
        	HttpSession session = req.getSession();
        	Users user = (Users) session.getAttribute("user");
        	int userId = user.getUserId();
        	ResearchersDao researchersDao = ResearchersDao.getInstance();
        	Researchers researcher = researchersDao.getResearchersByUserId(userId);
        	participates = participatesDao.getParticipatesByUserId(researcher);
            req.setAttribute("MyParticipates",participates);
            req.getRequestDispatcher("/ResearcherMyProfile.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}