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


@WebServlet("/siteupdate")
public class SiteUpdate extends HttpServlet {
	
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
        

        // Retrieve user and validate.
        int siteId = Integer.valueOf(req.getParameter("siteId"));
        if (siteId < 1) {
            messages.put("success", "Please enter a valid Name.");
        } else {
        	try {
        		Sites site = sitesDao.getSitesBySiteId(siteId);
        		if(site == null) {
        			messages.put("success", "UserName does not exist.");
        		}
        		req.setAttribute("site", site);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/SiteUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        int siteId = Integer.parseInt(req.getParameter("siteId"));
        if (siteId < 1) {
            messages.put("success", "Please enter a valid Name.");
        } else {
        	try {
        		Sites site = sitesDao.getSitesBySiteId(siteId);
        		if(site == null) {
        			messages.put("success", "Name does not exist. No update to perform.");
        		} else {
        			String newAbout = req.getParameter("name");
        			if (newAbout == null || newAbout.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid Name.");
        	        } else {
        	        	site = sitesDao.updateAbout(site, newAbout);
        	        	messages.put("success", "Successfully updated " + newAbout);
        	        }
        		}
        		req.setAttribute("site", site);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/SiteUpdate.jsp").forward(req, resp);
    }
}
