<%try{ %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>


<%@ page import="it.avlp.simog.common.servlet.*, it.avlp.simog.db.advanced.*" %>

<% TableBean tableBean = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); %>
<% String target =  ParametriServlet.CONSULTA_LOG; %>

<title>SIMOG - <utils:message key="log.consultazioneLog" /></title>
</head>

<body>
<div id="gabbia">
<%@ include file="include/header.inc" %>
		<%@ include file="include/menu/menuAmmLog.inc"%>

	<div id="bodypage">
		<div class="bodypage-e">
		
			<h1><utils:message key="log.visualizzazioneLog" /></h1>
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
	String urlOrderField = orderField != null ? "?"+ParametriServlet.ORDER_FIELD +"="+orderField+"&" : "?";

	String jspRicerca = "consultaLog" + urlOrderField;
	%>
		<div class="hmenu">
			<ul>
				<li><a href="consultazioneLog.jsp" title="<utils:message key="dettaglio.nuovaRicerca" />"><utils:message key="dettaglio.tornaRicerca" /></a></li>
			<li>&nbsp;&nbsp;</li>
			<% if ( startRowInt >  0 ) { %>
				<li><a href="<%= jspRicerca %><%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.REGRESS %>&<%= ParametriServlet.START_ROW %>=<%= maxRigheVisualizzabili %>" title="<utils:message key="log.visualizzaPrimaPagina" />"><utils:message key="log.inizioElenco" /></a></li>
			<% }
			else {%> <li><a id="disabledMenu" title="<utils:message key="log.visualizzaPrimaPagina" />"><utils:message key="log.inizioElenco" /></a></li> <% } %>

			<% if ( righeVisualizzate >  maxRigheVisualizzabili ) { %>
				<li><a href="<%= jspRicerca %><%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.REGRESS %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>" title="<utils:message key="log.visualizzaPrecedenti" />"><utils:message key="log.precedenti" /></a></li>
			<% }
			else {%> <li><a id="disabledMenu" title="<utils:message key="log.visualizzaPrecedenti" />"><utils:message key="log.precedenti" /></a></li> <% } %>
			
			<% if ( tableBeanSize - righeVisualizzate > 0 ) { %>
				<li><a href="<%= jspRicerca %><%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.PROGRESS %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>" title="<utils:message key="log.visualizzaSuccessive" />"><utils:message key="log.successive" /></a></li>
			<% }
			else {%> <li><a id="disabledMenu" title="<utils:message key="log.visualizzaSuccessive" />"><utils:message key="log.successive" /></a></li> <% } %>
			
			<% if ( righeVisualizzate != tableBeanSize ) { %>
				<li><a href="<%= jspRicerca %><%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.PROGRESS %>&<%= ParametriServlet.START_ROW %>=<%= fineElenco %>" title="<utils:message key="log.visualizzaUltimaPagina" />"><utils:message key="log.fineElenco" /></a></li>
			<% }
			else {%> <li><a id="disabledMenu" title="<utils:message key="log.visualizzaUltimaPagina" />"><utils:message key="log.fineElenco" /></a></li> <% } %>
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
				<table class="TableBean">
				<tbody>
				<tr>
<%-- 				<td class="TableBeanTitle"><a title="Ordina per questo campo" href="<%= target %>?<%= ParametriServlet.ORDER_FIELD %>=<%= LOG.T_ID_RECORD %>">ID LOG</a></td> --%>
				<td class="TableBeanTitle"><a title="<utils:message key="log.ordinaPerCampo" />" href="<%= target %>?<%= ParametriServlet.ORDER_FIELD %>=<%= LOG.T_ID_LOTTO %>"><utils:message key="log.idLotto" /></a></td>
				<td class="TableBeanTitle"><a title="<utils:message key="log.ordinamentoNonAttivo" />" href="#"><utils:message key="ricerca.cig" /></td>
				<td class="TableBeanTitle"><a title="<utils:message key="log.ordinaPerCampo" />" href="<%= target %>?<%= ParametriServlet.ORDER_FIELD %>=<%= LOG.T_ID_GARA %>"><utils:message key="ricerca.numeroGara" /></a></td>
				<td class="TableBeanTitle"><a title="<utils:message key="log.ordinaPerCampo" />" href="<%= target %>?<%= ParametriServlet.ORDER_FIELD %>=<%= LOG.DESCRIZIONE_AZIONE %>"><utils:message key="log.operazione" /></a></td>
				<td class="TableBeanTitle"><a title="<utils:message key="log.ordinaPerCampo" />" href="<%= target %>?<%= ParametriServlet.ORDER_FIELD %>=<%= LOG.CF_UTENTE %>"><utils:message key="log.utente" /></td>
				<td class="TableBeanTitle"><a title="<utils:message key="log.ordinaPerCampo" />" href="<%= target %>?<%= ParametriServlet.ORDER_FIELD %>=<%= LOG.DATA_MODIFICA %>"><utils:message key="log.data" /></td>
				<td class="TableBeanTitle"><a title="<utils:message key="log.ordinaPerCampo" />" href="<%= target %>?<%= ParametriServlet.ORDER_FIELD %>=<%= LOG.ID_SA_RIFERIMENTO %>"><utils:message key="log.idStazAppalt" /></td>
				<td class="TableBeanTitle"><a title="<utils:message key="log.ordinaPerCampo" />" href="<%= target %>?<%= ParametriServlet.ORDER_FIELD %>=<%= LOG.CF_AMMINISTRAZIONE %>"><utils:message key="log.cfAmministrazione" /></td>
				</tr>
				<% for ( int i = 0; i < tableBean.getRowsCount(); i++ ) { %>
					<% TableBeanRow currentRow = tableBean.getRow(i); %>
					<% String sommaUrgenza = currentRow.getNulledField(LOTTO.SOMMA_URGENZA); %>
					<% String oddEven = ( i % 2 == 0 ) ? "TableBeanEven" : "TableBeanOdd" ;  %>
			<tr class="<%= oddEven %>">
<%-- 				<td><%= currentRow.getNulledField(LOG.ID_RECORD) %></td> --%>
				<td><%= currentRow.getNulledField(LOG.ID_LOTTO) %></td>
				<td><%= PageHelper.getCIG( currentRow.getNulledField(LOTTO.CIG), sommaUrgenza, currentRow.getNulledField(LOTTO.DATA_CREAZIONE_LOTTO) ) %></td>
				<td><%= currentRow.getNulledField(LOG.ID_GARA) %></td>
				<td><%= currentRow.getNulledField(LOG.DESCRIZIONE_AZIONE) %></td>
				<td><%= currentRow.getNulledField(LOG.CF_UTENTE) %></td>
				<td><%= PageHelper.getFormattedDate( currentRow.getNulledField(LOG.DATA_MODIFICA) ) %></td>
				<td><%= currentRow.getNulledField(LOG.ID_SA_RIFERIMENTO) %></td>
				<td><%= currentRow.getNulledField(LOG.CF_AMMINISTRAZIONE) %></td>
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
<%@page import="it.avlp.simog.db.generated.LOG"%>
<%@page import="it.avlp.simog.db.generated.LOTTO"%>
<%@page import="it.avlp.simog.util.PageHelper"%>
</html>
<%}catch(Exception e){e.printStackTrace();}%>	