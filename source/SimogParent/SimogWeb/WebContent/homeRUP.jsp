<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>

<title>SIMOG - Gestione Gare <%= user.getProfilo().toUpperCase() %></title>

<script language="javascript">

function dispMsg(){
	var okDisp = false;
	// array dei mesi, inserire 1 se si vuole che appia nel mese
	var mesi = new Array(1,0,0,0,1,0,0,0,1,0,0,0);
	// primo giorno di visualizzzazione 
	var inizio = 1;
	// ultimo giorno di visualizzazione
	var fine = 15;
	var data = new Date();
	if(mesi[data.getMonth()] == 1 && data.getDate() >= inizio && data.getDate() <= fine) okDisp = true;
	
	if (okDisp) {
		var style = document.getElementById("mavMsg").style;
		style["display"] = "block";
	}
}
</script>
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
	<div id="mavMsg" style="display:none;">
	<br>
	<table width="100%" class="gara">
	<tr>
	<td><img src="img/simogWarning.jpg" height="60px" width="60px"></td>
	<td>
	<p style="color: red;"><big><strong><utils:message key="mav.estrattoConto" /> <a href="https://contributi.anticorruzione.it/">https://contributi.anticorruzione.it</a>
<br><utils:message key="mav.verificaContributi" />
</strong></big></p>
	</td>
	</tr>		
	</table>
	</div>
	
	<script> dispMsg();</script>

<!-- 	<div> -->
<!-- 	<br> -->
<!-- 	<table width="100%" class="gara"> -->
<!-- 	<tr> -->
<!-- 	<td><img src="img/simogWarning.jpg" height="60px" width="60px"></td> -->
<!-- 	<td> -->
<!-- 	<p style="color: red;"><big><strong>ATTENZIONE: Giovedì 6 Settembre il servizio sarà sospeso dalle 13:00 alle 14:00 per consentire l'attivazione della nuova versione. -->
<!-- </strong></big></p> -->
<!-- 	</td> -->
<!-- 	</tr>		 -->
<!-- 	</table> -->
<!-- 	</div> -->
	<div>
		<strong><font color="#FF0000"><utils:message key="avviso.delibera582" /> <a href="https://www.anticorruzione.it/-/delibera-n.-582-del-13-dicembre-2023-adozione-comunicato-relativo-avvio-processo-digitalizzazione" target="_blank">delibera 582 del 13 dicembre 2023</a></font></strong>
	</div> <!-- MEV 44995 3.04.11 -->
		<div class="bodypage-b">
			<h4><utils:message key="home.simog" /></h4>
			<p><utils:message key="home.simogDesc" /></p>
			<p><utils:message key="home.supportoDesc" /></p>
			<p><i><utils:message key="home.erroreDesc" /></i></p>
			<p><utils:message key="home.codiceErrore" /></p>
			<p><utils:message key="home.guidaServizio" /> <a href="<%= it.avlp.simog.common.servlet.ParametriServlet.HELP_GUIDA_RUP %>" target="_blank"><utils:message key="menu.guidaServizio" /></a>!</p>
			<p><utils:message key="home.indicazioniAggiornate" /></p>
		</div>
		<div class="bodypage-c">
			<h4><utils:message key="home.gestioneSchede" /></h4>
			<p><utils:message key="home.codificaGare" /></p>
			<p><utils:message key="home.menuNavigazione" /></p>
			<p><utils:message key="home.gestioneSchedeDesc" /></p>
			<p><utils:message key="home.navigazioneLotti" /></p>
		</div>	
	</div>
	</div>
	<%@ include file="include/newfooter.inc" %>
</div>
</body>
</html>
