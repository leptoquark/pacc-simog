package it.avlp.simog.servlet.luogo;

import it.avcp.simog.managers.luogo.IstatManager;
import it.avlp.simog.beans.aggiudicazione.luogo.IstatBean;
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

public class SrvRicercaIstat extends ServletBase {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1875252924247984723L;
	
	private String BASE_COMUNE="000000";
	private String BASE_PROVINCIA="000";
	
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection currentActiveConnection = null;
		String reqId = request.getParameter("idIstat");
		if(reqId == null)
			reqId = "00000000000";
		PrintWriter out = response.getWriter();
		try{
			if(virtualDB == null)
				currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
			}catch (SimogException e) {
			   logger.fatal(e);
			}
			
		//IstatManager istatMan = new IstatManager(currentActiveConnection,logger);
		
		// virtuale
	    IstatManager istatMan = null;
	    
	    if(virtualDB == null)
	    	istatMan = new IstatManager(currentActiveConnection, logger);
		else
			istatMan = new IstatManager(virtualDB.getConnection(),logger);
		
		response.setContentType("text/xml; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			if(reqId != null){
				
				List<IstatBean> baseList = new LinkedList<IstatBean>();
				String level = reqId.substring(0, 1);
			
				if("0".equals(level))
					baseList = istatMan.getBranch(null, BASE_PROVINCIA,BASE_COMUNE);
				else if("1".equals(level))
					baseList = istatMan.getBranch(reqId.substring(1, 3), BASE_PROVINCIA,BASE_COMUNE);
				else if("2".equals(level)) baseList = istatMan.getBranch(reqId.substring(1, 3), reqId.substring(3, 6),BASE_COMUNE);
				Iterator<IstatBean> it = baseList.iterator();
				out.println("<tree>");
				level = Integer.toString((Integer.parseInt(level) +1));
				String src = "";
				while (it.hasNext()) {
					
					IstatBean cpvEu = (IstatBean) it.next();
				
					
					String code = level + cpvEu.getIdRegione() + cpvEu.getIdProvincia() + cpvEu.getIdComune();
					String desc =null;
					src = " src=\"ricercaIstat?idIstat=" + code + "\" ";
					String action = " action=\"";
					if("3".equals(level)){
						desc =  cpvEu.getDenomComune();
						action += "javascript:setSel('ISTAT_ID','" + code.substring(6) + "')";
						src=" ";
					}
					else if("2".equals(level))
						desc =  cpvEu.getDenomProvincia();
					else
						desc =  cpvEu.getDenomRegione();
					action +="\"";
					
						
					out.println("<tree text=\"" + StringEscapeUtils.escapeXml(desc) +"\" "  + src + action + " />");
					// PP logger.debug("<tree text=\"" + StringEscapeUtils.escapeXml(desc) +"\" "  + src + action + " />");
					
					
				}
				out.println("</tree>");
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.fatal(e);
		}finally {
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

			//IstatManager istatMan = new IstatManager(currentActiveConnection,logger);
			
			// virtuale
		    IstatManager istatMan = null;
		    
		    if(virtualDB == null)
		    	istatMan = new IstatManager(currentActiveConnection, logger);
			else
				istatMan = new IstatManager(virtualDB.getConnection(),logger);
			
			response.setContentType("text/xml; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

			String chiavi = request.getParameter("pattern");

			if (chiavi != null && !chiavi.trim().equals("")) {

 				request.getSession().setAttribute("ISTAT_LIST",
						istatMan.getVoci(chiavi));
				response.sendRedirect("ricercaIstat.jsp?pattern="
						+ StringEscapeUtils.escapeHtml(chiavi));
			}

			else
				response.sendRedirect("ricercaIstat.jsp?pattern=");

		} catch (Exception e) {
			logger.fatal(e);
		} finally {
			if(virtualDB == null)
				closeConnection(request.getSession().getId(),getClass().getName());
		}
		

	}

}
