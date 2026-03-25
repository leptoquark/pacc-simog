<!--<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="errore.jsp" %>-->
<!--<%@ include file="include/newbasicHeader.inc" %>-->
<!--<%@ include file="include/controlloSessione.inc" %>-->
<!--<%@ taglib prefix="h" uri="http://simog.avlp.it/tags-util"  %>-->
<!--<%@ page import="it.avlp.simog.db.generated.*" %>-->
<!--<%@ page import="it.avlp.simog.util.*" %>-->
<!--<%@ page import="it.avlp.simog.common.servlet.*" %>-->
<!--<%@ page import="it.avlp.simog.db.advanced.*" %>-->
<!---->
<!---->
<!--<title>SIMOG - Gestione gare - Modifica Gara</title>-->
<!--</head>-->
<!---->
<!---->
<!--<% TableBean garaDaModificare = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); %>-->
<!--<% boolean cancellabile = false; %>-->
<!----><% String garaPubblicabile = "falso"; //Per garaVisual.inc %>
<!---->
<!--<body>-->
<!--<div id="gabbia">-->
<!--<%@ include file="include/header.inc" %>-->
<!--<%@ include file="include/menu/menuGara.inc" %>-->
<!---->
<!--	<div id="bodypage">-->
<!--		<div class="bodypage-e">-->
<!--			<% TableBeanRow currentRow = garaDaModificare.getRow(0); %>-->
<!---->
<!--		<h1>Modifica Gara</h1>-->
<!--		<%@ include file="include/gestisciErrore.inc" %>-->
<!--		<h4>Informazioni sula Gara</h4>-->
<!--		-->
<!--<div class="elenco">-->
<!--<div class="gara">		-->
<!---->
<!--	<table>-->
<!--		<tr>-->
<!--		<th class="garaTh" width="40%">Oggetto della Gara</th>-->
<!--		<td class="garaTd" colspan="3"><%= currentRow.getNulledField(GARA.TABLE_NAME + GARA.OGGETTO) %></td>-->
<!--		</tr>-->
<!--		<tr>-->
<!--		<th class="garaTh" width="40%">Data Creazione</th>-->
<!--		<td class="garaTd" colspan="3"><%= PageHelper.getFormattedDate( currentRow.getNulledField(GARA.DATA_CREAZIONE) ) %></td>-->
<!--		</tr>-->
<!--		</table>		-->
<!--		</div>-->
<!--		</div>-->
<!--		-->
<!---->
<!--	-->
<!--		<h5>Stazione Appaltante</h5>-->
<!--		<div class="elenco">-->
<!--		<div class="gara">-->
<!--		<table>-->
<!--		<tr>-->
<!--		<th class="garaTh" width="40%"><label for="sa_riferimento">SA Riferimento</label></th>-->
<!--		<td class="garaTd" colspan="3"><%= user.getDenominazioneUfficioById( currentRow.getNulledField(GARA.ID_STAZIONE_APPALTANTE) ) %></td>-->
<!--		</tr>-->
<!--		<tr>-->
<!--		<th class="garaTh" width="40%"><label for="cf_sa">Stazione Appaltante</label></th>-->
<!--		<td class="garaTd" colspan="3"><%= currentRow.getNulledField(GARA.ID_STAZIONE_APPALTANTE) %></td>-->
<!--		</tr>-->
<!--		<tr>-->
<!--		<th class="garaTh" width="40%"><label for="cf_rssa">RSSA che ha creato la Gara</label></th>-->
<!--		<td class="garaTd" colspan="3"><%= currentRow.getNulledField(GARA.CF_UTENTE) %></td>-->
<!--		</tr>-->
<!--		-->
<!--			</table>-->
<!--			-->
<!--	<div class="infoBlock">-->
<!--		<div class="inlineInfo">-->
<!--		<ul>-->
<!--		<li>Confermare</li>-->
<!--		</ul>-->
<!--		</div>-->
<!--		<div class="rightLineInfo">-->
<!--		<ul>-->
<!--		<% if ( ! user.isAmministratore() && currentRow.getNulledField(LOTTO.DATA_PUBBLICAZIONE) == "") { %>-->
<!--		<li><a href="aggiornaLotto?idLotto=<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>">Conferma la Modifica</a></li>-->
<!--		<% } %>-->
<!--		<% if ( ! user.isAVLP() && cancellabile ) { %>-->
<!--		<li><a href="inibisciLotto?idLotto=<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>">Conferma la Cancellazione</a></li>-->
<!--		<% } %>-->
<!--		</ul>-->
<!--		</div>-->
<!--	</div>			-->
<!--			-->
<!--</div>-->
<!--</div>-->
<!---->
<!--</body>-->
<!--</html>-->