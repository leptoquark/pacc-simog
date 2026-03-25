<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/controlloSessione.inc"%>
<%@ include file="include/newbasicHeader.inc" %>

<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>

<% TableBean listaVariazioni = (TableBean) request.getAttribute(ParametriServlet.TABLEBEAN); %>
<% String tabellaCorrente = (String)request.getAttribute(ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO); %>

<title><utils:message key="admin.titoloAmministrazioneTabelle" /></title>
</head>

<body>
<div id="gabbia">
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuAmmTabelleServizio.inc" %>

	<div id="bodypage">
		<div class="bodypage-e">
			<h1><utils:message key="admin.listaAggiornamenti" /></h1>
			<%@ include file="include/gestisciErrore.inc" %>
			<div class="hmenu">
				<ul>
				<% //if ( listaVariazioni.getRowsCount() > 0 ) { %>
					<li><a title="Visualizza Tabella completa" href="visualizzaStato?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= tabellaCorrente %>&list=true">Visualizza Tabella completa</a></li>
				<% //} %>
				<li><a title="Inserisci Aggiornamento" href="<%= ParametriServlet.JSP_GESTIONE_TABELLE %>">Inserisci Aggiornamento</a></li>
				</ul>
			</div>
			
			
			<h2>Tabella di servizio <%= tabellaCorrente %></h2>
			<div class="gara">
				<table>
				<% if ( listaVariazioni.getRowsCount() == 0 ) { %>
					<tr>
					<th>Non risultano presenti aggiornamenti per la tabella <strong><%=  tabellaCorrente %></strong></th>
					<td><a title="indietro" href="#" onClick="javascript:history.back();">Torna alle tabelle</a></td>
					</tr>					
				<% } %>
				
				<% for ( int rowNumber = 0; rowNumber < listaVariazioni.getTableSize(); rowNumber++ ) { %>
					<% TableBeanRow currentRow = new TableBeanRow ( listaVariazioni, rowNumber); %>
					<% String lastUpdate = currentRow.getNulledField(TIPOLOGIA.DATA_ULTIMA_MODIFICA); %>
					<tr>
					<th>Aggiornamento del <strong><%= PageHelper.getFormattedDate(lastUpdate) %></strong></th>
					<td><a title="Dettaglio al <%= PageHelper.getFormattedDate( lastUpdate ) %>" href="visualizzaStato?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= tabellaCorrente %>&<%= ParametriServlet.TAB_SERVIZIO_DATA %>=<%= lastUpdate %>">Visualizza dettaglio al <strong><%= PageHelper.getFormattedDate( lastUpdate ) %></strong></a></td>
					</tr>
				<% } %>
				</table>
			</div>
		</div>	
	</div>
	<%@ include file="include/newfooter.inc" %>
	
	<%// listaVariazioni.printNulledHTMLTable(new java.io.PrintWriter( out ), ""); %>		
<!-- gabbia -->
</div>
</body>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
</html>
