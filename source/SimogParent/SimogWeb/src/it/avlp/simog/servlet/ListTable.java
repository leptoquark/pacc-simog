package it.avlp.simog.servlet;

import it.avlp.simog.tabmanager.xml.parser.TabelleManagerXMLHandler;
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
public class ListTable extends ServletBase {

//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		perform(req, resp);
//	}
   
	@Override
	protected void perform(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {

	   try{
		    String nomeTabella = request.getParameter("tab");
		    String campoOrd = request.getParameter("ord");
		    
		    if(nomeTabella == null){
		       // ritorno la lista delle tabelle
		       response.getWriter().write("Elenco tabelle tipologiche SIMOG - TimeStamp: " + PageHelper.getCurrentDateTime());
		       response.getWriter().append("\n\n" + TabelleManagerXMLHandler.getMappaTabelle().toString().replace(",", "\n").replace("{","").replace("}",""));
		       return;
		    }
		    
          if(TabelleManagerXMLHandler.getKeyNameByTableName(nomeTabella.trim()) == null){
             response.getWriter().write("ERRORE: tabella '$1' non esiste!".replace("$1", nomeTabella.trim()));
             return;
          }
		    
          if(campoOrd == null)
             campoOrd = TabelleManagerXMLHandler.getKeyNameByTableName(nomeTabella).replace(";",",");
          
		    // String campoValidita = request.getParameter("validita");
		   
			//non si fa il login...
			response.setContentType("text/xml");
			//dice ai browser che e' un attachment e lo fa scaricare con il nome specificato
			response.setHeader("Content-Disposition", "attachment; filename=" + "listTable-" + nomeTabella + "-" + PageHelper.getCurrentDate() + ".xml");

			//wrapper per la connessiones
			SimpleDbManager sdm = new SimpleDbManager(getSimogConnection(request.getSession(true).getId(), getClass().getName()),logger);
			
			//scrivo in response...
			ListTypesBuilder.writeTabellaXML(response.getWriter(), sdm, nomeTabella, campoOrd, null);
			
		}catch (Exception e) {
			//e.printStackTrace();
			//throw new ServletException(e);
		   response.getWriter().write("ECCEZIONE: $1".replace("$1", e.getMessage()));
		}
		finally{
		   closeConnection(request.getSession().getId(),getClass().getName());
		}
	} 
}
