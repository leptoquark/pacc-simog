<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>


<%@ page import="it.avlp.simog.common.servlet.*, it.avlp.simog.db.advanced.*" %>

<% TableBean tableBean = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); %>



<title><utils:message key="transazioni.consultazioneTransazioni" /></title>
</head>

<body>
<div id="gabbia">
<%@ include file="include/header.inc" %>	
<%@ include file="include/menu/menuAVCP.inc" %>
			
		<div id="bodypage">
				<div class="bodypage-e">
			<h1><utils:message key="transazioni.consultazioneTransazioni" /></h1>
		<div class="hmenu">
		<% if ( user.isRSSA() ) { %>
			<ul>

			<li><a href="visualizzaDettaglio?<%= ParametriServlet.SESSION_ID_GARA %>=<%= request.getParameter( ParametriServlet.SESSION_ID_GARA) %>" title="<utils:message key="dettaglio.nuovaRicerca" plain="true" />"><utils:message key="dettaglio.tornaRicerca" /></a></li>
			</ul>
		<% } %>
		<% if ( user.isAVLP() ) { %>
			<ul>
			<li><a href="transazioniManager.jsp" title="<utils:message key="dettaglio.nuovaRicerca" plain="true" />"><utils:message key="dettaglio.tornaRicerca" /></a></li>
			</ul>
		<% } %>
		</div>		

		<%@ include file="include/gestisciErrore.inc" %>


	<form name="" action="visualizzaTransazioni" method="post">
<!--  SCROLL -->
	<div class="scroll">
	
	
	<!-- SCROLL INSIDE -->
	<div class="scrollInside">
			<div class="gara">
			<%// tableBean.printNulledHTMLTable(new java.io.PrintWriter (out), "" ); %>
			<% tableBean.printPagamentiList(new java.io.PrintWriter (out)); %>
			</div>
			
		</div>
		</div>
			<% if ( tableBean.getFullSize() > 0 ) { %>
			<input type="submit" name="AvviaEsportazione" value="<utils:message key="transazioni.esportaTransazioni" plain="true" />">
			<% } %>

<input type="hidden" name="AAAAdataScadenza_a" value="<%= request.getParameter("AAAAdataScadenza_a")%>">
<input type="hidden" name="AAAAdataPubblicazione_da" value="<%= request.getParameter("AAAAdataPubblicazione_da")%>">
<input type="hidden" name="MMdataPubblicazione_da" value="<%= request.getParameter("MMdataPubblicazione_da")%>">
<input type="hidden" name="MMdataPubblicazione_a" value="<%= request.getParameter("MMdataPubblicazione_a")%>">
<input type="hidden" name="PUBBLICAZIONE_EFFETTUATA" value="<%= request.getParameter("PUBBLICAZIONE_EFFETTUATA") %>">
<input type="hidden" name="CIG" value="<%= request.getParameter("CIG")%>">
<input type="hidden" name="cfAmministrazione" value="<%= request.getParameter("cfAmministrazione")%>">
<input type="hidden" name="cfUtente" value="<%= request.getParameter("cfUtente")%>">
<input type="hidden" name="AAAAdataScadenza_da" value="<%= request.getParameter("AAAAdataScadenza_da")%>">
<input type="hidden" name="DDdataPubblicazione_a" value="<%= request.getParameter("DDdataPubblicazione_a")%>">
<input type="hidden" name="MMdataScadenza_a" value="<%= request.getParameter("MMdataScadenza_a")%>">
<input type="hidden" name="idStazioneAppaltante" value="<%= request.getParameter("idStazioneAppaltante")%>">
<input type="hidden" name="DDdataPubblicazione_da" value="<%= request.getParameter("DDdataPubblicazione_da")%>">
<input type="hidden" name="AAAAdataPubblicazione_a" value="<%= request.getParameter("AAAAdataPubblicazione_a")%>">
<input type="hidden" name="MMdataScadenza_da" value="<%= request.getParameter("MMdataScadenza_da")%>">
<input type="hidden" name="DDdataScadenza_a" value="<%= request.getParameter("DDdataScadenza_a")%>">
<input type="hidden" name="DDdataScadenza_da" value="<%= request.getParameter("DDdataScadenza_da")%>">
<input type="hidden" name="CSV" value="true">
<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_GARA %>" value="<%= request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA)%>">

<%-- Gestione Visualizza Pagamenti di visualizzaDettagli(Gara) --%>
<% if ( request.getParameter(ParametriServlet.SESSION_ID_GARA) != null ) { %>
<input type="hidden" name="<%= ParametriServlet.SESSION_ID_GARA %>" value="<%= request.getParameter(ParametriServlet.SESSION_ID_GARA)%>">
<% } %>
<% if ( request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO) != null ) { %>
	<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_LOTTO %>" value="<%= request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO)%>">
<% } %>		
			
</form>
		</div>
	</div>
		<%@ include file="include/newfooter.inc" %>

	</div>

</body>
</html>