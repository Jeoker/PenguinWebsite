package web.servlet;

import web.dal.*;
import web.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/participatedelete")
public class ParticipateDelete extends HttpServlet {
    protected ParticipatesDao participatesDao;
    protected SitesDao sitesDao;

    @Override
    public void init() throws ServletException {
    	participatesDao = ParticipatesDao.getInstance();
    	sitesDao = SitesDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        int participateId = Integer.parseInt(req.getParameter("participateId"));
        try {
        	Participates participate = participatesDao.getParticipateById(participateId);
        	Participates result = participatesDao.delete(participate);
            if(result == null){
                messages.put("deleteParticipate","Delete Participate Successfully");
            }else {
                messages.put("deleteParticipate","Delete Participate Failed");
            }
            req.getRequestDispatcher("/ResearcherMyProfile.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    
}