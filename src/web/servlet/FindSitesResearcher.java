package web.servlet;

import web.dal.*;
import web.model.*;

import java.io.IOException;
import java.sql.SQLException;
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
import java.lang.*;


@WebServlet("/findsitesresearcher")
public class FindSitesResearcher extends HttpServlet {
	
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

        List<Sites> sites = new ArrayList<Sites>();
        
        // Retrieve and validate name.
        	// Retrieve BlogUsers, and store as a message.
        	try {
        		sites = sitesDao.getAllSites();
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
//        	messages.put("success", "Displaying results for " + name);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
//        	messages.put("previousName", name);
        req.setAttribute("sites", sites);
        
        req.getRequestDispatcher("/FindSitesResearcher.jsp").forward(req, resp);
	}

	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Sites> sites = new ArrayList<Sites>();
        
        // Retrieve and validate name.
        // firstname is retrieved from the form POST submission. By default, it
        // is populated by the URL query string (in FindUsers.jsp).
        String name = req.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            messages.put("success", "Please enter a valid name.");
        } else {
        	// Retrieve BlogUsers, and store as a message.
        	try {
            	sites = sitesDao.getSitesByName(name);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + name);
        }
        req.setAttribute("sites", sites);
        
        req.getRequestDispatcher("/FindSitesResearcher.jsp").forward(req, resp);
    }
}