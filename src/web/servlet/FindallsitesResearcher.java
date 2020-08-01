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


@WebServlet("/findallsitesresearcher")
public class FindallsitesResearcher extends HttpServlet {
    protected SitesDao sitesDao;

    @Override
    public void init() throws ServletException {
    	sitesDao = SitesDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Sites> sites = new ArrayList<>();
        try {
        	sites = sitesDao.getAllSites();
            req.setAttribute("sites",sites);
            req.getRequestDispatcher("/FindSitesResearcher.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}