package it.avlp.simog.garamanager.lotto.app;

import it.avlp.simog.errormessage.Messaggi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: SrvVisualizzaDocumento
 *
 */
 public class SrvVisualizzaDocumento extends it.avlp.simog.servlet.ServletBase implements javax.servlet.Servlet {
	 
	 protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 doGet(request, response);
	 }
	 
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		Connection currentActiveConnection = null;	
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			
		   currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
			ps = currentActiveConnection.prepareStatement("SELECT nomeDocumento, DOCUMENTO FROM DOCUMENTO WHERE ID_DOCUMENTO=?");
			ps.setInt(1, Integer.parseInt(id));
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				int len = 0;
				byte b[] = new byte[1024];
				
				String name = rs.getString(1);
				response.setHeader("Content-disposition", "filename="+name);
				logger.debug("nome documento: "+name);
				name = name.toLowerCase();
				
				if(name.endsWith(".txt") || name.endsWith("java"))
					response.setContentType("text/plain");
					
				else if(name.endsWith(".pdf"))
					response.setContentType("application/pdf");
				
				else if(name.endsWith(".jpg") || name.endsWith(".jpeg"))
					response.setContentType("image/jpeg");
				
				else
					response.setContentType("application/octet-stream");
				
				java.io.OutputStream out = response.getOutputStream();
				
				java.io.InputStream in = rs.getBinaryStream(2);
								
				do{
					len = in.read(b);
					out.write(b, 0, len);
					
				} while(len == 1024);
				
				response.flushBuffer();
				out.close();
				in.close();
				
			}
			
		}
		
		catch(Exception e){
			logger.fatal(Messaggi.SIMOG_GARA_006 , e);
		}
		
		finally{
			if(rs != null){rs = null;}
			if(ps != null){ps = null;}
			this.closeConnection(request.getSession().getId(),getClass().getName());
		}
		
	}
   
	  	 	  	  	    
}