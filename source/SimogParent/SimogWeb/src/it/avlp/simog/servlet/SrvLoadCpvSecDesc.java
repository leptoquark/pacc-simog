package it.avlp.simog.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.avcp.simog.managers.cpv.CPVEUManager;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.util.PageHelper;

/**
 * Servlet implementation class SrvLoadCpvSecDesc
 */
public class SrvLoadCpvSecDesc extends ServletBase implements ParametriServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		perform(request, response);
		
		
		
	}


	@Override
	protected void perform(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

        Connection currentActiveSession = null;
        String idCpvSec = request.getParameter("idCPV");
        String res = "KO";
        try {
        	currentActiveSession = getSimogConnection(request.getSession().getId(),  getClass().getName());
        	CPVEUManager cpvman = new CPVEUManager(currentActiveSession, logger);
        	if(cpvman.checkCPV(idCpvSec, PageHelper.getCurrentDate())) {
        		res = cpvman.getCPVDesc(idCpvSec);
        		res = res.replace("à", "&agrave;").replace("è", "&egrave;").replace("é", "&eacute;").replace("ù","&ugrave;").replace("ò", "&ograve;").replace("ó", "&oacute;");
        	}
        	response.getWriter().append(res);
        } catch(Exception e) {
        	e.printStackTrace();
        	response.getWriter().append("N.D.");
        } finally {
			closeConnection(request.getSession().getId(),getClass().getName());
		}
	}

}
