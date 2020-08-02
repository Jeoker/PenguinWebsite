package web.servlet;

import web.dal.UsersDao;
import web.model.Users;
import web.dal.ResearchersDao;
import web.model.Researchers;

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

@WebServlet("/researcherupdate")
public class ResearcherUpdate extends HttpServlet {

    protected ResearchersDao researchersDao;

    @Override
    public void init() throws ServletException {
    	researchersDao = ResearchersDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve/Validate username and password.
        String firstName = req.getParameter("FirstName");
        String lastName = req.getParameter("LastName");
        //int Gender = req.parseInt("Gender");
        int Gender = Integer.parseInt(req.getParameter("Gender"));
        String academicPaper = req.getParameter("AcademicPaper");
        String institute = req.getParameter("Institute");
        if(firstName == null || firstName.trim().isEmpty() ||
        		lastName == null || lastName.trim().isEmpty()){
            messages.put("update", "Invalid firstName Or lastName");
            req.getRequestDispatcher("/ResearcherInformation.jsp").forward(req,resp);
        }else {
            int userId = Integer.parseInt(req.getParameter("userId"));
            try {
            	Researchers researcher = researchersDao.getResearchersByUserId(userId);
                researcher = researchersDao.updateFirstName(researcher,firstName);
                researcher = researchersDao.updateLastName(researcher,lastName);
                researcher = researchersDao.updateGender(researcher,Gender);
                researcher = researchersDao.updateAcademicPaper(researcher,academicPaper);
                researcher = researchersDao.updateInstitute(researcher,institute);
                HttpSession session = req.getSession();
                session.setAttribute("researcher",researcher);
                messages.put("update", "Change Successful");
                req.getRequestDispatcher("/ResearcherInformation.jsp").forward(req,resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}