<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>


<%@ page import="it.avlp.simog.common.servlet.*, it.avlp.simog.db.advanced.*" %>
<%@page import="it.avlp.simog.db.generated.*"%>

<% TableBean tableBean = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); %>



<title>SIMOG - <utils:message key="visualizza.lottiPubblicati" /></title>
</head>

<body>
<div id="gabbia">
<%@ include file="include/header.inc" %>	
<%@ include file="include/menu/menuAVCP.inc" %>

		<div id="bodypage">
				<div class="bodypage-e">
			<h1><utils:message key="visualizza.lottiPubblicati" /></h1>	
		
		<% if ( false ) { %>	
		<div class="hmenu">
			<ul>
			<li><a href="<%= ParametriServlet.JSP_RICERCA_TRANSAZIONI %>" title="<utils:message key="dettaglio.nuovaRicerca" />"><utils:message key="dettaglio.tornaRicerca" /></a></li>
			</ul>
		</div>		
		<% } %>
		
		<%@ include file="include/gestisciErrore.inc" %>
		

	<form name="" action="<%= ParametriServlet.SRV_VISUALIZZA_LOTTI %>" method="post">
<!--  SCROLL -->
	<div class="scroll">
	
	
	<!-- SCROLL INSIDE -->
	<div class="scrollInside">
			<div class="gara">

		<% TableBeanRow currentRow = null; %>
		<% String previousGara = null; %>
		<% String previousLotto = null; %>
		<% String idGara = null; %>	
		<% String idLotto = null; %>
		<% boolean nuovaGara = true; %>
		<% boolean nuovoLotto = true; %>
			

		<% for ( int rowIndex = 0; rowIndex < tableBean.getTableSize(); rowIndex++ ) { %>
			<% currentRow = tableBean.getRow(rowIndex); %>
			<% int counter = 0; %>
			<% boolean pagamentiPresenti = false; %>
			
			<% idGara = currentRow.getNulledField(GARA.ID_GARA); %>
			<% idLotto = currentRow.getNulledField(LOTTO.ID_LOTTO); %>
			<% nuovoLotto = ! idLotto.equalsIgnoreCase(previousLotto); %>
			<% nuovaGara = ! idGara.equalsIgnoreCase(previousGara);%>
					
			<%  if ( nuovaGara ) { %>
			
				<% if ( nuovaGara && previousGara != null ) { %>
					</table>
					<span class="risalto"><p><a href="<%= ParametriServlet.SRV_VISUALIZZA_LOTTI %>?<%= ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE %>=<%= currentRow.getNulledField(GARA.CF_AMMINISTRAZIONE) %>" onClick="this.enabled=false;"><utils:message key="visualizza.lottiAmministrazione" /></a></p></span>
					</div>
					</div>
				<% } %>

				<h4><utils:message key="visualizza.informazioniGara" /></h4>
				<div class="elenco">
				<div class="gara">
					<table>
						<tr>
							<th class="garaTh" width="40%"><utils:message key="visualizza.amministrazioneCompetente" /></th>
							<td class="garaTd"><%= currentRow.getNulledField( GARA.DENOM_AMMINISTRAZIONE ) %></td>
						</tr>
						<tr>
						<th class="garaTh" width="40%"><utils:message key="visualizza.stazioneAppaltante" /></th>
						<td class="garaTd"><%= currentRow.getNulledField( GARA.DENOM_STAZIONE_APPALTANTE ) %></td>
						</tr>
					
						<tr>
						<th class="garaTh" width="40%"><utils:message key="visualizza.oggettoGara" /></th>
						<td class="garaTd"><%= currentRow.getNulledField( GARA.OGGETTO ) %></td>
						</tr>
					</table>
					
					<h5><utils:message key="visualizza.informazioniLotti" /></h5>
						<% previousGara = idGara; %>
						<table width="100%">
						<tr>
						<th class="garaTh" width="40%"><utils:message key="ricerca.cig" /></th>
						<th class="garaTh" width="40%"><utils:message key="visualizza.oggettoLotto" /></th>
						<th class="garaTh" width="40%"><utils:message key="visualizza.importo" /> &euro;</th>
						<th class="garaTh" width="40%"><utils:message key="visualizza.pagamenti" /></th>
						</tr>
					<% } %>

					<% if ( nuovoLotto ) { %>
						<% String rowStyle = ( counter++ % 2 == 0 )  ? "TableBeanEven" : "TableBeanOdd"; %>
						<tr class="<%= rowStyle %>">
							<tr>
							<td><%= currentRow.getNulledField(LOTTO.CIG) %></td>
							<td><%= currentRow.getNulledField(LOTTO.TABLE_NAME+LOTTO.OGGETTO) %></td>
							<td><%= currentRow.getNulledField(LOTTO.IMPORTO_LOTTO) %></td>
							<% pagamentiPresenti = ! "".equalsIgnoreCase( currentRow.getNulledField(PAGAMENTO.ID_PAGAMENTO) );%>
<!--							<td>-->
<!--							<% if ( pagamentiPresenti ) { %>-->
<!--							<a href="visualizzaTransazioni?idLotto=<%= currentRow.getNulledField(LOTTO.ID_LOTTO)%>&<%= ParametriServlet.SESSION_ID_GARA %>=<%= currentRow.getNulledField(GARA.ID_GARA) %>" title="Visualizza Pagamenti"><img src="img/euroc.jpg"></a>-->
<!--							<% } else { %>-->
<!--							--->
<!--							<% } %>-->
<!--							</td>						-->
						</tr>
					<% } %>
			<% previousLotto = idLotto; %>
			<% } %>
					</table>
					<span class="risalto"><p><a href="<%= ParametriServlet.SRV_VISUALIZZA_LOTTI %>?<%= ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE %>=<%= currentRow.getNulledField(GARA.CF_AMMINISTRAZIONE) %>" onClick="this.enabled=false;"><utils:message key="visualizza.lottiAmministrazione" /></a></p></span>
					</div>
					</div>
		</div>

<input type="hidden" name="CSV" value="true">
			
</form>
		</div>
	</div>
		<%@ include file="include/newfooter.inc" %>
	</div>
<%// tableBean.printHTMLTable(new java.io.PrintWriter ( out ) ); %>

</body>

</html>