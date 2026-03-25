<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/controlloSessione.inc"%>
<%@ include file="include/newbasicHeader.inc" %>
<title>SIMOG - <utils:message key="tabelle.aggiornamentoTabelle" /></title>
</head>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.db.*" %>

<body>

		<form name="InviaFileAggiornamento" ENCTYPE="multipart/form-data" method="POST" action="importaFile">
<div id="gabbia">
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuAmmTabelleServizio.inc" %>

	<div id="bodypage">
		<div class="bodypage-e">
		
		<h1><utils:message key="tabelle.aggiornamentoTabelle" /></h1>
		<%@ include file="include/gestisciErrore.inc" %>
		
		<div class="testo">
			<fieldset>
			<legend><utils:message key="tabelle.fileAggiornamento" /></legend>
			<table>
			  <tr>		
				<td><input type="file" name="putFile"></td>
		      	<td><p class="detailHelp"><utils:message key="tabelle.selezionarePercorso" /></p></td>
			    </tr>
			  <tr>
			    <td><input type="submit" name="conferma" value="<utils:message key="tabelle.caricaAggiornamento" plain="true" />"></td>
			  </tr>
			</table>
			</fieldset>
		</div>
		<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_FILE_AGGIORNAMENTO %>" value="justAValue">

		<div class="hmenu">
			<ul>
			<li><a title="<utils:message key="tabelle.statoCaricamenti" plain="true" />" href="<%= ParametriServlet.TAB_UPLOAD %>"><utils:message key="tabelle.statoCaricamenti" /></a></li>
			</ul>
		</div>
		
		<!-- categoria gara (G) -->
		<div class="gara">
			<fieldset><legend><utils:message key="tabelle.gara" /></legend>
			<table>
			<!-- separator -->
			<tr>
			<th class="primaCol">Tabella Categorie</th>
			<td class="secondaCol"><%= CATEGORIA.TABLE_NAME %></td>
			<td class="terzaCol"><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= CATEGORIA.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>
			<th>Tabella Importi</th>
			<td><%= IMPORTI.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= IMPORTI.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>
			<th>Tabella CPV</th>
			<td><%= CPVEU.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= CPVEU.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<!-- 2846 -->
			<tr>	
			<th>Tabella Motivo Collegamento</th>
			<td><%= MOTIVO_COLLEGAMENTO.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= MOTIVO_COLLEGAMENTO.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>			
			<!-- 2846 -->
			<tr>	
			<th>Tabella Scelta Contraente</th>
			<td><%= SCELTA_CONTRAENTE.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= SCELTA_CONTRAENTE.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
<% if (SimogFlags.is3028_RFWEBGL00Active()){ %>			
			<tr>	
			<th>Tabella Personalizzazione Scelta Contraente</th>
			<td><%= CONTRAENTE_REGIONE.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= CONTRAENTE_REGIONE.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
<% } %>			
			<tr>
			<th>Tabella Canale Pagamento Esattore</th>
			<td><%= ESATTORECANALEPAGAMENTO.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%=ESATTORECANALEPAGAMENTO.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>		
			<th>Tabella Stato Pagamento Esattore</th>
			<td><%= ESATTORESTATOPAGAMENTO.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= ESATTORESTATOPAGAMENTO.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>	
			<th>Tabella Tipo Utenza Esattore</th>
			<td><%= ESATTORETIPOUTENZA.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= ESATTORETIPOUTENZA.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>	
			<th>Modalita' di realizzazione</th>
			<td><%= MODI_REALIZZAZIONE.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= MODI_REALIZZAZIONE.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>	
			<th>Articoli esclusione</th>
			<td><%= ART_ESCLUSIONE.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= ART_ESCLUSIONE.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>	
			<th>Motivazioni cancellazione</th>
			<td><%= MOTIVI_CANCELLAZIONE.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= MOTIVI_CANCELLAZIONE.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<%--gm nuova tipologica per simog 3.06 --%>
			<tr>	
			<th>Motivazioni Variazione SA</th>
			<td><%= MOTIVI_VARIAZIONE_SA.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= MOTIVI_VARIAZIONE_SA.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>	
			<th>Organi Costituzionali</th>
			<td><%= ORGANI_COSTITUZIONALI.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= ORGANI_COSTITUZIONALI.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>			

<% if (SimogFlags.is3025_REQUISITIActive()){ %>
			<tr>	
			<th>Tipo Uso</th>
			<td><%= TIPO_USO.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= TIPO_USO.TABLE_NAME %>&<%=ParametriServlet.ACTION %>=req"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>			
			<tr>	
			<th>Tipo Requisito</th>
			<td><%= TIPO_REQUISITO.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= TIPO_REQUISITO.TABLE_NAME %>&<%=ParametriServlet.ACTION %>=req"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>			
			<tr>	
			<th>Disposto Normativo</th>
			<td><%= DISPOSTO_NORMATIVO.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= DISPOSTO_NORMATIVO.TABLE_NAME %>&<%=ParametriServlet.ACTION %>=req"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>			
			<tr>	
			<th>Tipo Unita' Misura</th>
			<td><%= TIPO_UNITA_MISURA.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= TIPO_UNITA_MISURA.TABLE_NAME %>&<%=ParametriServlet.ACTION %>=req"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			<tr>	
			</tr>			
			<th>Tipo Fonte Documento</th>
			<td><%= TIPO_FONTE_DOCUMENTO.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= TIPO_FONTE_DOCUMENTO.TABLE_NAME %>&<%=ParametriServlet.ACTION %>=req"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>			
			<tr>	
			<th>Requisito</th>
			<td><%= REQUISITO.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= REQUISITO.TABLE_NAME %>&<%=ParametriServlet.ACTION %>=req"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>			
			<tr>	
			<th>Tipo Documento Requisito</th>
			<td><%= TIPO_DOCUMENTO_REQ.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= TIPO_DOCUMENTO_REQ.TABLE_NAME %>&<%=ParametriServlet.ACTION %>=req"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>			
			<tr>	
			<th>Dettaglio Requisito</th>
			<td><%= DETTAGLIO_REQUISITO.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= DETTAGLIO_REQUISITO.TABLE_NAME %>&<%=ParametriServlet.ACTION %>=req"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>			
			<tr>	
			<th>Documento Requisito</th>
			<td><%= DOCUMENTO_REQUISITO.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= DOCUMENTO_REQUISITO.TABLE_NAME %>&<%=ParametriServlet.ACTION %>=req"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>			
<%} %>
<% if (SimogFlags.is30350_RFWEBGL01Active()){ %>			
			<tr>	
			<th>Tabella Motivi Richiesta CIG</th>
			<td><%=EAGG_MOTIVI.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= EAGG_MOTIVI.TABLE_NAME %>&<%=ParametriServlet.ACTION %>=req"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>	
			<th>Tabella Categorie Merceologiche</th>
			<td><%=EAGG_CATEGORIE.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= EAGG_CATEGORIE.TABLE_NAME %>&<%=ParametriServlet.ACTION %>=req"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
<% } %>	
			<!-- separator end -->
			</table>			
			</fieldset> 
		</div>		
		<!-- categoria appalto (A) -->
		<div class="gara">
			<fieldset><legend><utils:message key="tabelle.appalto" /></legend>
			<table>
			<!-- separator -->
			<tr>	
			<th  class="primaCol">Tabella Tipo Categoria</th>
			<td  class="secondaCol"><%= TIPI_CATEGORIA.TABLE_NAME %></td>
			<td  class="terzaCol"><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= TIPI_CATEGORIA.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>		
			<tr>	
			<th>Tabella Tipo Appalto</th>
			<td><%= TIPI_APPALTI.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= TIPI_APPALTI.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>	
			<tr>	
			<th>Tabella Tipo Prestazione</th>
			<td><%= TIPI_PRESTAZIONI.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= TIPI_PRESTAZIONI.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>	
			<th>Tabella Classi Importo</th>
			<td><%= CLASSI_IMPORTO.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= CLASSI_IMPORTO.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>			
			<tr>	
			<th>Tabella Categoria Stazione Appaltante</th>
			<td><%= CATEGORIA_SA.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= CATEGORIA_SA.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>	
			<tr>	
			<th>Tabella Tipo Aggiudicatario</th>
			<td><%= TIPO_AGGIUDICATARIO.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= TIPO_AGGIUDICATARIO.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>	
			<tr>	
			<th>Tabella Ruoli Responsabile</th>
			<td><%= RUOLI_RESPONSABILE.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= RUOLI_RESPONSABILE.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>	
			<tr>	
			<th>Tabella Condizioni Ricorso</th>
			<td><%= CONDIZIONI.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= CONDIZIONI.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>	
			<tr>	
			<th>Tabella Tipi Appalto per Categoria</th>
			<td><%= APPALTI_PER_CATEGORIA.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= APPALTI_PER_CATEGORIA.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>							
			<tr>	
			<th>Tabella Prestazioni per Categoria</th>
			<td><%= PRESTAZIONI_PER_CATEGORIA.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= PRESTAZIONI_PER_CATEGORIA.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>	
			<th>Tabella Ruoli Responsabili per scheda</th>
			<td><%= RUOLI_RESP_SCHEDA.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= RUOLI_RESP_SCHEDA.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>	
			<th>Tabella Tipologia Stazione Appaltante</th>
			<td><%= TIPOLOGIA_SA.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= TIPOLOGIA_SA.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>	
			<tr>	
			<th>Tabella Modo Indizione</th>
			<td><%= MODO_INDIZIONE.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= MODO_INDIZIONE.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>					
			<tr>	
			<th>Tabella Tipo Finanziamento</th>
			<td><%= TIPO_FINANZIAMENTO.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= TIPO_FINANZIAMENTO.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>					
			<tr>	
			<th>Tabella Comuni</th>
			<td><%= CODICI_ISTAT.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= CODICI_ISTAT.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>				
			<tr>	
			<th>Tabella Codici NUTS</th>
			<td><%= CODICI_NUTS.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= CODICI_NUTS.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>					
			<tr>	
			<th>Tabella Regione/Provincia</th>
			<td><%= REGIONE_PROVINCIA.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= REGIONE_PROVINCIA.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>	
			<th>Tabella Modalit� Gara</th>
			<td><%= MODALITA_GARA.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= MODALITA_GARA.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>	
			<th>Tabella Tipo Strumento</th>
			<td><%= TIPO_STRUMENTO.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= TIPO_STRUMENTO.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>
			<th>Tabella Stati Esteri</th>
			<td><%= STATI_ESTERI.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= STATI_ESTERI.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>
			<th>Tabella Indice Dispersione</th>
			<td><%= INDICE_DISPERSIONE.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= INDICE_DISPERSIONE.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>
			<th>Tabella Tipologia Procedura</th>
			<td><%= TIPOLOGIA_PROCEDURA.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= TIPOLOGIA_PROCEDURA.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<!-- separator end -->
			</table>			
			</fieldset>
		</div>
		<!-- categoria eventi (E) -->
				<div class="gara">
			<fieldset><legend><utils:message key="tabelle.eventi" /></legend>
			<table>
			<!-- separator -->
			<tr>	
			<th  class="primaCol">Tabella Motivi Interruzione</th>
			<td  class="secondaCol"><%= MOTIVI_INTERRUZIONE.TABLE_NAME %></td>
			<td  class="terzaCol"><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= MOTIVI_INTERRUZIONE.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>	
			<th>Tabella Motivi Risoluzione</th>
			<td><%= MOTIVI_RISOLUZIONE.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= MOTIVI_RISOLUZIONE.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>	
			<th>Tabella Motivi Sospensione</th>
			<td><%= MOTIVI_SOSPENSIONE.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= MOTIVI_SOSPENSIONE.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>	
			<th>Tabella Motivi Modifiche Contrattuali</th>
			<td><%= MOTIVI_VARIANTE.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= MOTIVI_VARIANTE.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>	
			<th>Tabella Modifica Contrattuale Categoria</th>
			<td><%= VARIANTE_CATEGORIA.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= VARIANTE_CATEGORIA.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>	
			<th>Tabella Delega Dati Simog</th>
			<td><%= DELEGA_DATI_SIMOG.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= DELEGA_DATI_SIMOG.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<tr>	
			<th>Tabella Modi Riaggiudicazione</th>
			<td><%= MODI_RIAGGIUD.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= MODI_RIAGGIUD.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
<%if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive()){ %>
			<tr>	
			<th>Tabella Motivi Variazione C.O.</th>
			<td><%= MOTIVI_VARIAZIONE_CO.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= MOTIVI_VARIAZIONE_CO.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
<% } %>			
			<!-- separator end -->
			</table>			
			</fieldset>
		</div>
		<%-- 
		<!--  original -->
		<div class="gara">
			<fieldset><legend>Dead table</legend>
			<table>
			<!-- dead table say PP -->
			<tr>
			<th>Tabella Tipologie</th>
			<td><%= TIPOLOGIA.TABLE_NAME %></td>
			<td><a href="visualizzaAggiornamenti?<%= ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO %>=<%= TIPOLOGIA.TABLE_NAME %>"><utils:message key="tabelle.visualizzaAggiornamenti" /></a></td>
			</tr>
			<!-- end -->									
			</table>			
			</fieldset>
		</div>
		--%>
	</div>
	</div>

		<%@ include file="include/newfooter.inc" %>
<!-- gabbia -->
</div>
</form>
</body>
</html>
