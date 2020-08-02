package web.servlet;

import web.dal.*;
import web.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/sitecreate")
public class SiteCreate extends HttpServlet {
	
	protected SitesDao sitesDao;
	
	@Override
	public void init() throws ServletException {
		sitesDao = SitesDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/SiteCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String name = req.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            messages.put("success", "Invalid Name");
        } else {
        	// Create the BlogUser.
        	String Name = req.getParameter("name");
        	// dob must be in the format yyyy-mm-dd.
        	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	String stringDate = req.getParameter("date");
        	Date date = new Date();
        	try {
        		date = dateFormat.parse(stringDate);
        	} catch (ParseException e) {
        		e.printStackTrace();
				throw new IOException(e);
        	}
	        try {
	        	// Exercise: parse the input for StatusLevel.
	        	Sites site = new Sites(name, date);
	        	site = sitesDao.create(site);
	        	
	        	HttpSession session = req.getSession();
	        	Users user = (Users) session.getAttribute("user");
	        	int userId = user.getUserId();
	        	ResearchersDao researchersDao = ResearchersDao.getInstance();
	        	Researchers researcher = researchersDao.getResearchersByUserId(userId);
	        	Participates participate = new Participates(site, researcher);
	        	ParticipatesDao participatesDao = ParticipatesDao.getInstance();
	        	participate = participatesDao.create(participate);
	        	messages.put("success", "Successfully created " + name);
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/SiteCreate.jsp").forward(req, resp);
    }
}
