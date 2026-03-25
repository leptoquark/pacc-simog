package it.avlp.simog.servlet;

import it.avlp.simog.util.ListTypesBuilder;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimpleDbManager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListTypes
 */
public class ListTypes extends ServletBase {

//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		perform(req, resp);
//	}
	@Override
	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try{
			//non si fa il login...
			response.setContentType("text/xml");
			//dice ai browser che e' un attachment e lo fa scaricare con il nome specificato
			response.setHeader("Content-Disposition", "attachment; filename=" + "listTypes-" + PageHelper.getCurrentDate() + ".xsd");

			//wrapper per la connessiones
			SimpleDbManager sdm = new SimpleDbManager(getSimogConnection(request.getSession(true).getId(), getClass().getName()),logger);
			
			String tutti = request.getParameter("all");
			if(tutti == null)
				tutti = "";
			
			boolean allCodes = !"".equals(tutti);
			//scrivo in response...
			ListTypesBuilder.write(response.getWriter(), sdm, allCodes, configuration);
			
			closeConnection(request.getSession().getId(),getClass().getName());
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	} 
}
