package it.avlp.simog.servlet.cpv;

import it.avcp.simog.managers.cpv.CPVEUManager;
import it.avlp.simog.beans.CpvEu;
import it.avlp.simog.db.hsql.HSqlManager;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.servlet.ServletBase;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

public class SrvRicercaCPV extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2834575470368863619L;

	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		String reqId = request.getParameter("idCpv");
		if (reqId == null)
			reqId = "00000000";
		PrintWriter out = response.getWriter();
		try {
			if(virtualDB == null)
				currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
		} catch (SimogException e) {
			logger.fatal(e);
		}
		 //CPVEUManager cpvMan = new CPVEUManager(currentActiveConnection, logger);
		//virtuale
		CPVEUManager cpvMan = null;
		
		if(virtualDB == null)
			cpvMan = new CPVEUManager(currentActiveConnection, logger);
		else
			cpvMan = new CPVEUManager(virtualDB.getConnection(), logger);
		
		response.setContentType("text/xml");

		try {
			if (reqId != null) {

				List<CpvEu> baseList = new ArrayList<CpvEu>();
				List<CpvEu> childList = new ArrayList<CpvEu>();

				if ("00000000".equals(reqId))
					baseList = cpvMan.getBranch(null, reqId.substring(2, 3),
							reqId.substring(3, 4), reqId.substring(4, 5), reqId
									.substring(5, 8));
				else
					baseList = cpvMan.getBranch(reqId.substring(0, 2), reqId
							.substring(2, 3), reqId.substring(3, 4), reqId
							.substring(4, 5), reqId.substring(5, 8));
				Iterator<CpvEu> it = baseList.iterator();
				out.println("<tree>");
				String action = null;
				while (it.hasNext()) {
					action = " action=\"";
					CpvEu cpvEu = (CpvEu) it.next();
					String code = cpvEu.getIdDiv() + cpvEu.getIdGrp()
							+ cpvEu.getIdCls() + cpvEu.getIdCtg()
							+ cpvEu.getIdVox();
					String desc = code + "-" + cpvEu.getCheck() + " - "
							+ cpvEu.getDescr();

					// verifica se esistono figli
					childList = cpvMan.getBranch(cpvEu.getIdDiv(), 
												 cpvEu.getIdGrp(),
												 cpvEu.getIdCls(), 
												 cpvEu.getIdCtg(), 
												 cpvEu.getIdVox());
					
					if ((!"0".equals(cpvEu.getIdGrp()) && !"0".equals(cpvEu.getIdCls()))
						 || childList.size() == 0)
						action += "javascript:setSel('CPV_ID','" + code + "-"
								+ cpvEu.getCheck() + "','"+cpvEu.getDescr().replace("'","\\'")+"')";
					action += "\"";
					
					String buff =  "<tree text=\""
						+ StringEscapeUtils.escapeXml(desc)	+ "\"";
										
					// azione per espansione albero
					if (cpvEu.getIdVox().equals("000")) {
						if(childList.size()>0)
							buff = buff + " src=\"ricercaCPV?idCpv=" + code + "\""; 
					}
					
					buff = buff + action + " />";
					
					out.println(buff);
					
					// PP logger.debug(buff);
				}
				out.println("</tree>");
			}
		} catch (Exception e) {
			logger.fatal(e);

		} finally {
			if(virtualDB == null)
				closeConnection(request.getSession().getId(),getClass().getName());
		}

	}

	@Override
	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		try {
			if(virtualDB == null)
				currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());

			//CPVEUManager cpvMan = new CPVEUManager(currentActiveConnection, logger);
			//virtuale
			
			CPVEUManager cpvMan = null;
			
			if(virtualDB == null)
				cpvMan = new CPVEUManager(currentActiveConnection, logger);
			else
				cpvMan = new CPVEUManager(virtualDB.getConnection(), logger);
			
			response.setContentType("text/xml");

			String chiavi = request.getParameter("pattern");

			if (chiavi != null && !chiavi.trim().equals("")) {

				request.getSession().setAttribute("CPV_LIST",
						cpvMan.getVoci(chiavi));
				response.sendRedirect("ricercaCPV.jsp?pattern="
						+ StringEscapeUtils.escapeHtml(chiavi));
			}

			else
				response.sendRedirect("ricercaCPV.jsp?pattern=");

		} catch (Exception e) {
			logger.fatal(e);
		} finally {
			if(virtualDB == null)
				closeConnection(request.getSession().getId(),getClass().getName());
		}
	}

}
