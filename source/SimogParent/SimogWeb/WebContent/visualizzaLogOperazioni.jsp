<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/newbasicHeader.inc" %>
<title>SIMOG - <utils:message key="log.consultazioneLogSchede" /></title>
</head>
<%@ include file="include/controlloSessione.inc" %>

<%@ page import="it.avlp.simog.common.servlet.*, it.avlp.simog.db.advanced.*" %>
<% TableBean tableBean = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); %>
<% String target =  ParametriServlet.CONSULTA_LOG_OPERAZIONI; %>

<body>
<div id="gabbia">
<%@ include file="include/header.inc" %>
		<%@ include file="include/menu/menuAmmLog.inc"%>

	<div id="bodypage">
		<div class="bodypage-e">
		
			<h1><utils:message key="log.visualizzazioneLogSchede" /></h1>
			<%@ include file="include/gestisciErrore.inc" %>



<% 
	int maxRigheVisualizzabili = ( (Integer)request.getAttribute( ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI ) ).intValue(); 
    Integer startRowInt = (Integer)request.getAttribute( ParametriServlet.START_ROW ); 
	TableBean listaGare = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); 
	int tableBeanSize = listaGare.getFullSize();
	int startRow = startRowInt.intValue(); 
	int righeVisualizzate = startRow + listaGare.getTableSize();
	long resto = (tableBeanSize % maxRigheVisualizzabili);
	long fineElenco = tableBeanSize - resto - maxRigheVisualizzabili - (resto == 0 ? maxRigheVisualizzabili : 0) ; 

	String orderField = (String)request.getParameter(ParametriServlet.ORDER_FIELD);
	String fullCIG = (String)request.getParameter(ParametriServlet.FIELD_NAME_CIG);
	String urlOrderField = orderField != null && !orderField.equals("") ? ParametriServlet.ORDER_FIELD +"="+orderField+"&" : "";
	urlOrderField += fullCIG != null && !fullCIG.equals("") ? ParametriServlet.FIELD_NAME_CIG + "=" +fullCIG +"&" : "";

	
	String jspRicerca = "consultaLogOperazioni?" + urlOrderField;
	

	%>
		<div class="hmenu">
			<ul>
				<li><a href="consultaLogOperazioni.jsp" title="Nuova Ricerca">Torna alla Ricerca</a></li>
			<li>&nbsp;&nbsp;</li>
			<% if ( startRowInt >  0 ) { %>
				<li><a href="<%= jspRicerca %><%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.REGRESS %>&<%= ParametriServlet.START_ROW %>=<%= maxRigheVisualizzabili %>" title="Visualizza prima pagina">Inizio elenco</a></li>
			<% }
			else {%> <li><a id="disabledMenu" title="Visualizza prima pagina">Inizio elenco</a></li> <% } %>
			
			<% if ( righeVisualizzate >  maxRigheVisualizzabili ) { %>
				<li><a href="<%= jspRicerca %><%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.REGRESS %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>" title="Visualizza Precedenti">Precedenti</a></li>
			<% }
			else {%> <li><a id="disabledMenu" title="Visualizza Precedenti">Precedenti</a></li> <% } %>
			
			<% if ( tableBeanSize - righeVisualizzate > 0 ) { %>
				<li><a href="<%= jspRicerca %><%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.PROGRESS %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>" title="Visualizza Successive">Successive</a></li>
			<% } 
			else {%> <li><a id="disabledMenu" title="Visualizza Successive">Successive</a></li> <% } %>
			
			<% if ( righeVisualizzate != tableBeanSize ) { %>
				<li><a href="<%= jspRicerca %><%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.PROGRESS %>&<%= ParametriServlet.START_ROW %>=<%= fineElenco %>" title="Visualizza ultima pagina">Fine elenco</a></li>
			<% }
			else {%> <li><a id="disabledMenu" title="Visualizza ultima pagina">Fine elenco</a></li> <% } %>
			<%-- 
			<p><%= "startRowInt " + (startRowInt)%></p>
			<p><%= "righeVisualizzate " + (righeVisualizzate)%></p>
			<p><%= "tableBean.getFullSize()- resto " + (listaGare.getFullSize()- resto)%></p>
			<p><%= "fine elenco " + fineElenco%></p>
			<p><%= "if( "+righeVisualizzate+" < "+(listaGare.getFullSize()- resto)+" )"%></p>
			--%>
			</ul>
		</div>

<!--  SCROLL -->
	<div class="scroll">
	<!-- SCROLL INSIDE -->
	<div class="scrollInside">		
				<div class="gara">
				<table class="TableBean" width="100%" >
				<tbody>
				<tr>
				<td class="TableBeanTitle"><a title="Ordina per questo campo" href="<%= target %>?<%= ParametriServlet.ORDER_FIELD %>=<%= LOG_OPERAZIONI.ID_LOG %>">ID_LOG</a></td>
				<td class="TableBeanTitle"><a title="Ordina per questo campo" href="<%= target %>?<%= ParametriServlet.ORDER_FIELD %>=<%= LOG_OPERAZIONI.CF_UTENTE %>">UTENTE</td>
				<td class="TableBeanTitle"><a title="Ordina per questo campo" href="<%= target %>?<%= ParametriServlet.ORDER_FIELD %>=<%= LOG_OPERAZIONI.OPERAZIONE %>">OPERAZIONE</a></td>			
				<td class="TableBeanTitle"><a title="Ordina per questo campo" href="<%= target %>?<%= ParametriServlet.ORDER_FIELD %>=<%= LOG_OPERAZIONI.DATA_OPERAZIONE %>">DATA</td>
				<td class="TableBeanTitle"><a title="Ordina per questo campo" href="<%= target %>?<%= ParametriServlet.ORDER_FIELD %>=<%= LOG_OPERAZIONI.BLOCCO_DATI %>">SCHEDA</td>
				<td class="TableBeanTitle">CIG / CUI</td>
				<%--<td class="TableBeanTitle"><a title="Ordina per questo campo" href="<%= target %>?<%= ParametriServlet.ORDER_FIELD %>=<%= LOG_OPERAZIONI.ID_RECORD %>">ID RECORD</td>--%>
				</tr>
				<% for ( int i = 0; i < tableBean.getRowsCount(); i++ ) { %>
					<% TableBeanRow currentRow = tableBean.getRow(i); %>
				
			<tr class="TableBeanOdd">
				<td><%= currentRow.getNulledField(LOG_OPERAZIONI.ID_LOG) %></td>
				<td><%= currentRow.getNulledField(LOG_OPERAZIONI.CF_UTENTE) %></td>
				<td><%= currentRow.getNulledField(LOG_OPERAZIONI.OPERAZIONE) %></td>
				<td><%= PageHelper.getFormattedDateTime( currentRow.getNulledField(LOG_OPERAZIONI.DATA_OPERAZIONE).replace(".0","") ) %></td>
				<%--<td><%= currentRow.getNulledField(LOG_OPERAZIONI.DATA_OPERAZIONE).replace(".0","") %></td>--%>
				<td><%= IdentificativoSchede.getScheda(currentRow.getNulledField(LOG_OPERAZIONI.BLOCCO_DATI)) %></td>
				 <td><%= currentRow.getNulledField("CIGCUI") %></td>
				<%--<td><%= currentRow.getNulledField(LOG_OPERAZIONI.ID_RECORD) %></td>--%>
				</tr>
				<% } %>
				</tbody>
				</table>
				</div>
			</div>
			</div>
		</div>
	</div>

	<%@ include file="include/newfooter.inc" %>
	
</div>

</body>
<%@page import="it.avlp.simog.util.PageHelper"%>
<%@page import="it.avlp.simog.db.generated.LOG_OPERAZIONI"%>
<%@page import="java.util.Enumeration"%>
<%@page import="it.avlp.simog.beans.IdentificativoSchede"%>
</html>