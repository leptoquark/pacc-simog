package it.avlp.simog.servlet.luogo;

import it.avcp.simog.managers.luogo.NutsManager;
import it.avlp.simog.beans.aggiudicazione.luogo.NutsBean;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.servlet.ServletBase;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

public class SrvRicercaNuts extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9159933243931110315L;

	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		String reqId = request.getParameter("idNuts");
		if (reqId == null)
			reqId = "0";
		PrintWriter out = response.getWriter();
		try {
			if(virtualDB == null)
				currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
		} catch (SimogException e) {
			logger.fatal(e);
		}
		//NutsManager nutsMan = new NutsManager(currentActiveConnection, logger);
		
		// virtuale			
		NutsManager nutsMan = null;
	    
	    if(virtualDB == null)
	    	nutsMan = new NutsManager(currentActiveConnection, logger);
		else
			nutsMan = new NutsManager(virtualDB.getConnection(),logger);

	    response.setContentType("text/xml; charset=UTF-8");
	    response.setCharacterEncoding("UTF-8");
		try {
			if (reqId != null) {

				List<NutsBean> baseList = new LinkedList<NutsBean>();
				int level = Integer.parseInt(reqId.substring(0, 1));
				String nutsId = (reqId.length() > 3) ? reqId.substring(1)
						: null;
				baseList = nutsMan.getBranch(level, nutsId);

				Iterator<NutsBean> it = baseList.iterator();
				out.println("<tree>");
				level +=1;
				String src = "";
				while (it.hasNext()) {

					NutsBean nuts = (NutsBean) it.next();

					String code = level + nuts.getIdNuts();
					String desc = null;
					src = " src=\"ricercaNuts?idNuts=" + code + "\" ";
					desc = nuts.getDescrizione();
					String action = " action=\"";

					action += "javascript:setSel('NUTS_ID','"
							+ code.substring(1) + "')";

					action += "\"";

					out.println("<tree text=\""
							+ StringEscapeUtils.escapeXml(desc) + "\" " + src
							+ action + " />");
					// PP logger.debug("<tree text=\"" 	+ StringEscapeUtils.escapeXml(desc) + "\" " + src + action + " />");

				}
				out.println("</tree>");
			}
		} catch (Exception e) {
			//e.printStackTrace();
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

			//NutsManager nutsMan = new NutsManager(currentActiveConnection,logger);
			//virtuale
			
			NutsManager nutsMan = null;
		    
		    if(virtualDB == null)
		    	nutsMan = new NutsManager(currentActiveConnection, logger);
			else
				nutsMan = new NutsManager(virtualDB.getConnection(),logger);			
			
			response.setContentType("text/xml");

			String chiavi = request.getParameter("pattern");

			if (chiavi != null && !chiavi.trim().equals("")) {

				request.getSession().setAttribute("NUTS_LIST",
						nutsMan.getVoci(chiavi));
				response.sendRedirect("ricercaNuts.jsp?pattern="
						+ StringEscapeUtils.escapeHtml(chiavi));
			}

			else
				response.sendRedirect("ricercaNuts.jsp?pattern=");

		} catch (Exception e) {
			logger.fatal(e);
		} finally {
			if(virtualDB == null)
				closeConnection(request.getSession().getId(),getClass().getName());
		}

	}

}
