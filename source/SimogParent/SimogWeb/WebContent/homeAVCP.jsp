<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>

<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>


<title>SIMOG - Profilo AVCP</title>
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
			<p><utils:message key="home.description" /></p>
			<p><utils:message key="home.supporto" /></p>
			<p><utils:message key="home.errore" /></p>
		</div>
		<div class="bodypage-c">
			<h4><utils:message key="home.analisiGare" /></h4>
			<p><utils:message key="home.analisiGareDesc" /></p>
			<p><utils:message key="home.menu" /></p>
		</div>
		<div class="bodypage-d">
			<h4><utils:message key="home.accessoInfo" /></h4>
			<p><utils:message key="home.ricercaGara" /></p>
			<p><utils:message key="home.lotti" /></p>
		</div>	
	</div>
	</div>
	<%@ include file="include/newfooter.inc" %>
</div>
</body>
</html>
