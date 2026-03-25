<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_LOTTO %>" 
	id="<%= ParametriServlet.FIELD_NAME_ID_LOTTO %>" 
	value="<%= request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO) %>">
			
<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_INFO %>" 
	id="<%= ParametriServlet.FIELD_NAME_ID_INFO %>" 
	value="<%= request.getParameter(ParametriServlet.FIELD_NAME_ID_INFO) %>">

<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO %>"
	id="<%= ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO %>"
	value="<%= request.getParameter(ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO) %>">

<input type="hidden" name="<%= PSBD.FIELD_NAME_ID_AGGIUDICAZIONE %>" 
	id="<%= PSBD.FIELD_NAME_ID_AGGIUDICAZIONE %>" 
	value="<%= ID_AGGIUDICAZIONE %>">	

<input type="hidden" name="<%=PSBD.DATA_INIZIO_AGGIUDICAZIONE %>" 
	id="<%= PSBD.DATA_INIZIO_AGGIUDICAZIONE %>" 
	value="<%= DATA_INIZIO_AGGIUDICAZIONE %>">

<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE %>" 
	id="<%= ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE %>" 
	value="<%= FLAG_ENTE_SPECIALE %>">
	
<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_TIPO_CONTRATTO %>" 
	id="<%= ParametriServlet.FIELD_NAME_TIPO_CONTRATTO %>" 
	value="<%= TIPO_CONTRATTO %>">
<input type="hidden" name="checkIfOK" id="checkIfOK" value="<%=new Integer(session.getAttribute(ParametriServlet.checkIfOK).toString()) + 1%>" />