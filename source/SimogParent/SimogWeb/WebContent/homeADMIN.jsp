<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>


<title>SIMOG - <utils:message key="home.gestioneGare" /> - <utils:message key="home.amministratore" /></title>
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
		<div class="bodypage-b">
			<h4><utils:message key="home.simog" /></h4>
			<p><utils:message key="home.adminDesc" /></p>
			<p><utils:message key="home.supportoDesc" /></p>
			<p><utils:message key="home.erroreDesc" /> <utils:message key="home.codiceErrore" /></p>
		</div>
		<div class="bodypage-c">
			<h4><utils:message key="home.gestioneGare" /></h4>
			<p><utils:message key="home.gestioneGareDesc" /></p>
			<p><utils:message key="home.menuNavigazione" /></p>
			<p><utils:message key="home.ricercaGare" /></p>
			<p><utils:message key="home.navigazioneLotti" /></p>
		</div>
	</div>
	</div>
	<%@ include file="include/newfooter.inc" %>
</div>
</body>
</html>
