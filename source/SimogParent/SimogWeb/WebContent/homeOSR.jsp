<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>


<title>SIMOG - Gestione Gare <%= user.getProfilo().toUpperCase() %></title>
</head>

<%int indiceTab = 0; %>

<body>
<div id="gabbia">

<%@ include file="include/header.inc" %>
<%@ include file="include/gestisciNews.inc" %>

<%@ include file="include/gestisciErrore.inc" %>

<div id="bodypage">
<div class="bodypage-e">
	<h1><small><%= user.getProfilo().toUpperCase() %></small></h1>
	
	<div> <!-- MEV 44995 3.04.11 -->
		<strong><font color="#FF0000"><utils:message key="avviso.delibera582" /> <a href="https://www.anticorruzione.it/-/delibera-n.-582-del-13-dicembre-2023-adozione-comunicato-relativo-avvio-processo-digitalizzazione" target="_blank">delibera 582 del 13 dicembre 2023</a></font></strong>
	</div>
		<div class="bodypage-b">
			<h4><utils:message key="home.simog" /></h4>
			<p><utils:message key="home.consultazioneGare" /></p>
			<p><utils:message key="home.supportoDesc" /></p>
			<p><i><utils:message key="home.erroreDesc" /></i></p>
			<p><utils:message key="home.codiceErrore" /></p>
			<p><utils:message key="home.guidaServizio" /> <a href="<%= it.avlp.simog.common.servlet.ParametriServlet.HELP_GUIDA_OSR %>" target="_blank"><utils:message key="menu.guidaServizio" /></a>!</p>
			<p><utils:message key="home.indicazioniAggiornate" /></p>
		</div>
		<div class="bodypage-c">
			<h4><utils:message key="home.consultazioneSchede" /></h4>
			<p><utils:message key="home.consultazioneGareDesc" /></p>
			<p><utils:message key="home.menuNavigazione" /></p>
		</div>	
	</div>
	</div>
	<%@ include file="include/newfooter.inc" %>
</div>
</body>
</html>
