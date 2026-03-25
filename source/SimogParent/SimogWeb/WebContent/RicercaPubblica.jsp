<!--<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="errore.jsp"%>-->
<!--<%@ include file="include/newbasicHeader.inc" %>-->
<!---->
<!---->
<!--<%@ page import="it.avlp.simog.beans.*"%>-->
<!--<%@ page import="it.avlp.simog.common.servlet.*"%>-->
<!--<%@ page import="it.avlp.simog.util.*"%>-->
<!---->
<!---->
<!--<% String currentDate = PageHelper.getCurrentDate(); %>-->
<!---->
<!---->
<!--<% if ( ! SimogProperties.isDocumentiAbilitato() ) { %>-->
<!--	<META HTTP-EQUIV=REFRESH CONTENT="0;URL=login.jsp">-->
<!--<% } %>-->
<!--<title>Gestione Gare - RSSA</title>-->
<!---->
<!--<script type="text/javascript">-->
<!--	function onRicerca(){-->
<!--		if(document.getElementById('lotto').cig.value == ''){-->
<!--			alert('Il valore del CIG è obbligatorio');-->
<!--			return false;-->
<!--		}-->
<!--		-->
<!--		return true;-->
<!--		-->
<!--	}-->
<!--</script>-->
<!--</head>-->
<!---->
<!--<% int indiceTab = 0; %>-->
<!---->
<!--<body>-->
<!---->
<!--<div id="gabbia">-->
<!---->
<!---->
<!--<div id="menu">-->
<!--		<div class="menu-a">-->
<!--			<h2>Help Gara</h2>-->
<!--			<p><strong>La ricerca permette di individuare la gara di interesse e proseguire con l'azione desiderata.</strong></p>-->
<!--		</div>-->
<!--		-->
<!--		-->
<!--			<div class="menu-c">-->
<!--				<h3>Ricerca Documentazione di Gara</h3>-->
<!--				<p>Per ricercare le informazioni dei lotti inserire il codice identificativo CIG e verranno visualizzate le informazioni relative-->
<!--				alle gare di interesse con la documentazione allegata.</p>-->
<!--				-->
<!--				<p>Il codice CIG &egrave; obbligatorio, i filtri temporali si riferiscono alla data di scadenza e alla data di pubblicazione del lotto.</p>-->
<!--			</div>-->
<!--	</div>-->
<!---->
<!--<div id="header">-->
<!--	<p align="right"><img src="img/repubblica_italiana.gif"/></p>-->
<!--</div>-->
<!---->
<!--<form action="RicercaLotto" method="post" id="lotto" onSubmit="return onRicerca();">-->
<!---->
<!--<div id="bodypage">-->
<!--<div class="bodypage-e">-->
<!---->
<!--<h1>Ricerca Gara</h1>-->
<!--<%@ include file="include/gestisciErrore.inc"%>-->
<!---->
<!---->
<!--<div class="testo">-->
<!--<fieldset><legend>Filtri nominali</legend>-->
<!---->
<!--<table>-->
<!---->
<!--	<tr>-->
<!--		<td class="detailHelp" colspan="2">Indicare il CIG del lotto di-->
<!--		interesse</td>-->
<!--	</tr>-->
<!--	<tr>-->
<!--		<td>CIG</td>-->
<!--		<td><input id="cig" tabindex="<%= ++indiceTab%>" type="text" size="10"-->
<!--			maxlength="10" title="CIG" id="txt_CIG"-->
<!--			name="<%= ParametriServlet.FIELD_NAME_CIG %>"></td>-->
<!--	</tr>-->
<!---->
<!--</table>-->
<!--</fieldset>-->
<!--</div>-->
<!---->
<!--<div class="testo">-->
<!--<fieldset><legend>Filtri temporali</legend>-->
<!---->
<!--<table cellpadding="3">-->
<!--	<tr>-->
<!--		<td colspan="3">-->
<!--		<table>-->
<!---->
<!--			<tr>-->
<!--				<td colspan="3">Da data Pubblicazione</td>-->
<!--			</tr>-->
<!--			<tr>-->
<!--				<td><select tabindex="<%=++indiceTab%>"-->
<!--					name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_DD_START %>">-->
<!--					<%-->
<!--					it.avlp.simog.util.PageHelper.printGiorni(out, null);-->
<!--					%>-->
<!--				</select></td>-->
<!--				<td><select tabindex="<%=++indiceTab%>"-->
<!--					name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_MM_START %>">-->
<!--					<%-->
<!--					it.avlp.simog.util.PageHelper.printMesi(out, null);-->
<!--					%>-->
<!--				</select></td>-->
<!--				<td><select tabindex="<%=++indiceTab%>"-->
<!--					name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_AAAA_START %>">-->
<!--					<%-->
<!--					it.avlp.simog.util.PageHelper.printAnniStart(out);-->
<!--					%>-->
<!--				</select></td>-->
<!--			</tr>-->
<!--		</table>-->
<!--		</td>-->
<!--		<td colspan="3">-->
<!--		<table>-->
<!---->
<!--			<tr>-->
<!--				<td colspan="3">A data Pubblicazione</td>-->
<!--			</tr>-->
<!--			<tr>-->
<!--				<td><select tabindex="<%=++indiceTab%>"-->
<!--					name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_DD_END %>">-->
<!--					<%-->
<!--								it.avlp.simog.util.PageHelper.printGiorni(out, PageHelper-->
<!--								.getDay(currentDate));-->
<!--					%>-->
<!--				</select></td>-->
<!--				<td><select tabindex="<%=++indiceTab%>"-->
<!--					name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_MM_END %>">-->
<!--					<%-->
<!--								it.avlp.simog.util.PageHelper.printMesi(out, PageHelper-->
<!--								.getMonth(currentDate));-->
<!--					%>-->
<!--				</select></td>-->
<!--				<td><select tabindex="<%=++indiceTab%>"-->
<!--					name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_AAAA_END %>">-->
<!--					<%-->
<!--								it.avlp.simog.util.PageHelper.printAnni(out, PageHelper-->
<!--								.getYear(currentDate));-->
<!--					%>-->
<!--				</select></td>-->
<!--			</tr>-->
<!--		</table>-->
<!--		</td>-->
<!--		<td>Inserire l'intervallo di date di pubblicazione</td>-->
<!--	</tr>-->
<!--	<tr>-->
<!--		<td colspan="3">-->
<!--		<table width="100%">-->
<!---->
<!--			<tr>-->
<!--				<td colspan="3">Da data Scadenza</td>-->
<!--			</tr>-->
<!--			<tr>-->
<!--				<td><select tabindex="<%=++indiceTab%>"-->
<!--					name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_DD_START %>">-->
<!--					<%-->
<!--								it.avlp.simog.util.PageHelper.printGiorni(out, PageHelper-->
<!--								.getDay(currentDate));-->
<!--					%>-->
<!--				</select></td>-->
<!--				<td><select tabindex="<%=++indiceTab%>"-->
<!--					name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_MM_START %>">-->
<!--					<%-->
<!--								it.avlp.simog.util.PageHelper.printMesi(out, PageHelper-->
<!--								.getMonth(currentDate));-->
<!--					%>-->
<!--				</select></td>-->
<!--				<td><select tabindex="<%=++indiceTab%>"-->
<!--					name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_AAAA_START %>">-->
<!--					<%-->
<!--					it.avlp.simog.util.PageHelper.printAnniStart(out);-->
<!--					%>-->
<!--				</select></td>-->
<!--			</tr>-->
<!---->
<!--		</table>-->
<!--		</td>-->
<!--		<td colspan="3">-->
<!--		<table width="100%">-->
<!---->
<!--			<tr>-->
<!--				<td colspan="3">A data Scadenza</td>-->
<!--			</tr>-->
<!--			<tr>-->
<!--				<td><select tabindex="<%=++indiceTab%>"-->
<!--					name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_DD_END %>">-->
<!--					<%-->
<!--								it.avlp.simog.util.PageHelper.printGiorni(out, PageHelper-->
<!--								.getDay(currentDate));-->
<!--					%>-->
<!--				</select></td>-->
<!--				<td><select tabindex="<%=++indiceTab%>"-->
<!--					name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_MM_END %>">-->
<!--					<%-->
<!--								it.avlp.simog.util.PageHelper.printMesi(out, PageHelper-->
<!--								.getMonth(currentDate));-->
<!--					%>-->
<!--				</select></td>-->
<!--				<td><select tabindex="<%=++indiceTab%>"-->
<!--					name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_AAAA_END %>">-->
<!--					<%-->
<!--								it.avlp.simog.util.PageHelper.printAnni(out, PageHelper-->
<!--								.getYear(currentDate));-->
<!--					%>-->
<!--				</select></td>-->
<!--			</tr>-->
<!---->
<!--		</table>-->
<!--		</td>-->
<!--		<td>Inserire l'intervallo di date di scadenza richiesto</td>-->
<!--	</tr>-->
<!--</table>-->
<!--</fieldset>-->
<!--</div>-->
<!--<input tabindex="<%= ++indiceTab%>" type="submit" value="Cerca">-->
<!---->
<!--</div>-->
<!--</div>-->
<!--</form>-->
<!---->
<!--<%@ include file="include/newfooter.inc"%></div>-->
<!--</div>-->
<!--</body>-->
<!--</html>-->
