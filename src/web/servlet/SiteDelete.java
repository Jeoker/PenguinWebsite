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


@WebServlet("/sitedelete")
public class SiteDelete extends HttpServlet {
	
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
        // Provide a title and render the JSP.
        messages.put("title", "Delete Site");        
        req.getRequestDispatcher("/SiteDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String name = req.getParameter("name");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	String stringDate = req.getParameter("date");
    	Date date = new Date();
    	try {
    		date = dateFormat.parse(stringDate);
    	} catch (ParseException e) {
    		e.printStackTrace();
			throw new IOException(e);
    	}
        if (name == null || name.trim().isEmpty() || date == null) {
            messages.put("title", "Invalid Name or Date");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the BlogUser.
	        Sites site = new Sites(name, date);
	        
	        try {
	        	site = sitesDao.delete(site);
	        	
	        	// Update the message.
		        if (site == null) {
		            messages.put("title", "Successfully deleted " + name +" "+ date);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + name +" "+ date);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/SiteDelete.jsp").forward(req, resp);
    }
}
