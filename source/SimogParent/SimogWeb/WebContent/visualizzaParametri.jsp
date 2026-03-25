<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<h1><utils:message key="debug.listaParametri" /> [<%= request.getRequestURL() %>]</h1>



<%
		for ( java.util.Enumeration e = request.getParameterNames(); e.hasMoreElements(); ) {
			String currentParamName = (String) e.nextElement();
			String[] currentValue = request.getParameterValues(currentParamName);			
			for ( int i = 0; i < currentValue.length; i++ ) {
				out.write("<li>Il parametro <b>[" + currentParamName + "](" + i + ")</b> vale <b>[" + currentValue[i] + "]</b>");
			}
		}

		out.write("<br>");

	/**
		for ( java.util.Enumeration e = request.getParameterNames(); e.hasMoreElements(); ) {
			String currentParamName = (String) e.nextElement();
			String currentValue = request.getParameter(currentParamName);
			out.write("input type=\"hidden\" name=\"" + currentParamName + "\" value=\"<% request.getParameter(" + currentParamName + ") >>" );
			out.write("<br>");
		}
	*/
%>