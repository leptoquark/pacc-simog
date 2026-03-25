<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/controlloSessione.inc"%>
<%@ include file="include/newbasicHeader.inc" %>

<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.db.Costanti" %>
<%@ include file="/script/domUtils.js" %>
<link rel="stylesheet" type="text/css" href="theme/tabmenu.css"/>

<% TableBean listaVariazioni = (TableBean) request.getAttribute(ParametriServlet.TABLEBEAN); %>
<% String tabellaCorrente = (String)request.getAttribute(ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO); %>
<% String dataRichiesta = (String)request.getAttribute(ParametriServlet.TAB_SERVIZIO_DATA);  %>
<% String tabellaInfo = (String)request.getAttribute(ParametriServlet.FIELD_NAME_TABELLA_INFO); %>

<title><utils:message key="admin.titoloAmministrazioneTabelle" /></title>
</head>

<body>
<div id="gabbia">
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuAmmTabelleServizio.inc" %>

	<div id="bodypage">
		<div class="bodypage-e">
			<h1><utils:message key="admin.dettaglioAggiornamento" /></h1>
			<div class="hmenu">
				<ul>
				<li><a title="Tabelle di Servizio" href="<%= ParametriServlet.JSP_GESTIONE_TABELLE %>">Tabelle di Servizio</a></li>
				<% //if ( listaVariazioni.getRowsCount() > 0 ) { %>
					<li><a title="Visualizza Tabella completa" href="visualizzaStato?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= tabellaCorrente %>&list=true">Visualizza Tabella completa</a></li>
				<% //} %>
				<li><a title="Inserisci Aggiornamento" href="<%= ParametriServlet.JSP_GESTIONE_TABELLE %>">Inserisci Aggiornamento</a></li>
				</ul>
			</div>
			<%@ include file="include/gestisciErrore.inc" %>
			
			<div class="testo">
			
			<h2>Tabella di servizio <%= tabellaCorrente %>
			
			<% if ( dataRichiesta != null ) { %>
				Dettaglio al <strong><%= PageHelper.getFormattedDate(dataRichiesta) %></strong>
			<% } %>
			</h2>
			
			<div class="inthead">
				<label onclick="showMenu('INFOTABELLA')" 	style="color:black; letter-spacing:0.2em;">
					<img src="img/plus.gif" id="imgINFOTABELLA"/> Informazioni tabella </label>
					<br>
				<div id="INFOTABELLA" class="mbody" style="display: none;" >
					<p>Campi chiave: <%= it.avlp.simog.tabmanager.xml.parser.TabelleManagerXMLHandler.getKeyNameByTableName(tabellaCorrente) %></p>
					<p>Elenco campi:<br> <%= tabellaInfo %></p>
				</div>	
			</div>					
			<br>
			
			
	<!--  SCROLL -->
	<div class="scroll">
	
	
	<!-- SCROLL INSIDE -->
	<div class="scrollInside">
	
			<div class="gara">
			<% if ( listaVariazioni.getRowsCount() == 0 ) { %>
				<tr>
				<th>Non risultano presenti aggiornamenti per la tabella <strong><%=  tabellaCorrente %>&nbsp;&nbsp;&nbsp;</strong></th>
				<td><a title="indietro" href="#" onClick="javascript:history.back();">Torna alle tabelle</a></td>
				</tr>
			<% } %>
			
			<%
			String urlparams = "visualizzaStato?" + ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO + "=" + tabellaCorrente;
			if ( dataRichiesta != null ) { 
				urlparams += "&dataUltimaModifica="+dataRichiesta;
			} %>
			
			<% listaVariazioni.printNulledHTMLTable( new java.io.PrintWriter ( out ), urlparams); %>
			</div>
		</div>	
		</div>
		
	</div>
	</div>
		
	</div>
	<%@ include file="include/newfooter.inc" %>
			
<!-- gabbia -->
</div>

<%// listaVariazioni.printNulledHTMLTable(new java.io.PrintWriter( out )); %>
</body>
<%@page import="it.avlp.simog.tabmanager.TabManager"%>
</html>
