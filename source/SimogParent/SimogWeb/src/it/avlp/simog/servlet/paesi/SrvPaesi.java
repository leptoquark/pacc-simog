package it.avlp.simog.servlet.paesi;

import it.avcp.simog.manager.paesi.PaesiManager;
import it.avlp.simog.common.servlet.ParametriServletRubrica;
import it.avlp.simog.servlet.ServletBase;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SrvPaesi extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7394490766311996891L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		perform(request, response);
	}

	@Override
	protected void perform(HttpServletRequest request,HttpServletResponse response) 
		throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
			Connection currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
        	PaesiManager pm = new PaesiManager(currentActiveConnection,logger);
        	Map<String,String> paesi = pm.loadPaesi(null);
        	Set<String> set = paesi.keySet();
        	ArrayList<String> l = new ArrayList<String>(set);
        	Collections.sort(l);
        	out.println("<select id=\""+ParametriServletRubrica.FIELD_NAME_ID_STATO +"\" name=\""+ParametriServletRubrica.FIELD_NAME_ID_STATO +"\">");
        	out.println("<option></option>");
        	for(String s : l){
        		out.println("<option value=\""+paesi.get(s)+"\" >"+s+"</option>");
        	}
        	out.println("</select>");
        }catch (Exception ex)
        {
        	ex.printStackTrace();
            logger.fatal(ex.getMessage());
        }finally{
        	closeConnection(request.getSession().getId(),getClass().getName());
        	out.close();
        }
	}

//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		perform(req, resp);
//	}
}
