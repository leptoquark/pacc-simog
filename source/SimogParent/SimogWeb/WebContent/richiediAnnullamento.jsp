<%@page import="it.avlp.simog.db.SimogFlags"%>
<%@ page contentType="text/html; charset=UTF-8" language="java"  %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ page import="it.avlp.simog.util.MessageHelper"%>

<%@ include file="/include/newbasicHeader.inc" %>

<%@page import="it.avlp.simog.common.servlet.ParametriServletInizioLavori"%>
<%@page import="it.avlp.simog.db.generated.INIZIO_LAVORI"%>

<%@page import="it.avlp.simog.db.generated.STATI_AVANZ"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletSchedaB4"%>
<%@page import="it.avlp.simog.db.generated.FINE_LAVORI"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletR129"%>
<%@page import="it.avlp.simog.db.generated.R129"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletAvanzamento"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletConclusioni"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletCollaudo"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletAccordo"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletSospensioni"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletSubappalti"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletVariante"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletStipula"%>
<%@page import="it.avlp.simog.beans.IdentificativoSchede"%>
<title><utils:message key="richiesta.richiestaModificaCancellazione" /></title>
</head>
<%@ include file="/include/controlloSessione.inc" %>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletRubrica"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"%>
<c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>

<% String tab = (String)request.getAttribute(PSBD.TAB); %>
<%String showDatiComuni = (request.getAttribute(ParametriServlet.SHOW_DATI_COMUNI)!=null)? (String)request.getAttribute(ParametriServlet.SHOW_DATI_COMUNI): "false"; %>
<%String actionString = null;
	if(IdentificativoSchede.TAB_INFO_COMUNI.equals(tab))
		actionString = PSBD.SRV_DATI_COMUNI;
	else if(IdentificativoSchede.TAB_AGGIUDICAZIONE.equals(tab))
		actionString = ParametriServlet.SRV_SCHEDA_A; 
	else if(IdentificativoSchede.TAB_SOTTOSOGLIA.equals(tab))
		actionString = ParametriServlet.SRV_SCHEDA_SOTTOSOGLIA; 
	else if(IdentificativoSchede.TAB_ESCLUSI.equals(tab))
		actionString = ParametriServlet.SRV_SCHEDA_ESCLUSI; 
	else if(IdentificativoSchede.TAB_INIZIO_LAVORI.equals(tab))
		actionString = ParametriServletInizioLavori.SRV_INIZIO_LAVORI;
	else if(IdentificativoSchede.TAB_AVANZAMENTO.equals(tab))
		actionString = ParametriServletAvanzamento.SRV_SCHEDA_AVANZAMENTO;
	else if(IdentificativoSchede.TAB_FINELAVORI.equals(tab))
		actionString = ParametriServletConclusioni.SRV_SCHEDA_CONCLUSIONI;
	else if(IdentificativoSchede.TAB_COLLAUDO.equals(tab))
		actionString = ParametriServletCollaudo.SRV_SCHEDA_COLLAUDO;
	else if(IdentificativoSchede.TAB_ACCORDO.equals(tab))
		actionString = ParametriServletAccordo.SRV_SCHEDA_ACCORDO;
	else if(IdentificativoSchede.TAB_SOSPENSIONE.equals(tab))
		actionString = ParametriServletSospensioni.SRV_SCHEDA_SOSPENSIONI;
	else if(IdentificativoSchede.TAB_SUBAPPALTO.equals(tab))
		actionString = ParametriServletSubappalti.SRV_SCHEDA_SUBAPPALTI;
	else if(IdentificativoSchede.TAB_VARIANTE.equals(tab))
		actionString = ParametriServletVariante.SRV_SCHEDA_VARIANTE;
	else if(IdentificativoSchede.TAB_RITARDO.equals(tab))
		actionString = ParametriServletR129.SRV_SCHEDA_R129;
	else if(IdentificativoSchede.TAB_STIPULA.equals(tab))
		actionString = ParametriServletStipula.SRV_STIPULA;
	else if(IdentificativoSchede.TAB_ADESIONE.equals(tab))
		actionString = ParametriServlet.SRV_SCHEDA_ADESIONE;
%>

	
<SCRIPT type="text/javascript">
<!--	
	
	/***************************************************************************************
	 *   javascript che imposta l'azione a carica utile nel tasto Torna.  LUCA
	 */
	function torna (  ) {
		document.forms[0].elements['toDo'].value = "Carica";
		document.forms[0].submit();
	} 
	
	function tornaDatiComuni (  ) {
		document.forms[0].elements['toDo'].value = "Carica";
		changePage('visualizzaRiepilogoScheda','Modificato');
	} 
	
	function richiediAnnullamento1(tabName, idMotivazione) {  
		var motivazione = document.forms[0].elements[idMotivazione].value;  
		var checkMotivazione = true;
<% if (SimogFlags.is30230_RFWEBSC03Active()){%>		
		var codMotivo = document.forms[0].elements["<%= PSBD.FIELD_NAME_MOTIVO_RICH %>"].value;  
		if (codMotivo.length == 0){
			if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('error.specifyMotivation'); } else { alert("Specificare una motivazione dall'elenco"); }
			return;
		}
		
		if (codMotivo == "5" && trim(motivazione).length == 0){
			alert('<%= MessageHelper.getMessage(request, "richiesta.altroDescrizioneMotivazione") %>');
			return;			
		}
		else
			checkMotivazione = false;
		
<% } %>		
		if (checkMotivazione == false || (trim(motivazione).length >= 10 && trim(motivazione).length <= 1000)) {  
			document.forms[0].elements['<%=PSBD.TAB%>'].value = tabName;  
			document.forms[0].elements['<%=PSBD.ACTION_TYPE%>'].value = "<%=PSBD.ACTION_RICHIESTA_ANNULLAMENTO%>";  
			if (tabName == "<%=PSBD.TAB_INFO_COMUNI%>") {  
				document.forms[0].elements['<%= ParametriServlet.SHOW_DATI_COMUNI %>'].value = "true";  
			}  
			
			setForwardPage(tabName);  
			document.forms[0].submit();  
		} 
		else {  
			if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('error.motivationLength'); } else { alert('Inserire una motivazione di almeno 10 caratteri e minore di 1000'); }  
		} 
	}

// --> 
</SCRIPT>
<%@ include file="include/i18n-init.inc" %>
<%@ include file="/script/script.js" %>
 
<body>
	<div id="gabbia">
		<%@ include file="/include/header.inc" %>
		<div id="bodypage">
			<div class="bodypage-e">
<!--				<form name="gestioneTab" action="<%=actionString%>"  method="post" onkeypress="setFormModified('Modificato')">-->
					<form name="gestioneTab" action="<%=actionString%>"  method="post" >
					<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_LOTTO %>" 
						id="<%= ParametriServlet.FIELD_NAME_ID_LOTTO %>" 
						value="<%= request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO) %>">
					<input type="hidden" name="toDo"  value="<%=PSBD.ACTION_RICHIESTA_ANNULLAMENTO %>">
					<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_INFO %>" 
						id="<%= ParametriServlet.FIELD_NAME_ID_INFO %>" 
						value="<%= request.getParameter(ParametriServlet.FIELD_NAME_ID_INFO) %>">
		
					<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO %>"
						id="<%= ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO %>"
						value="<%= request.getParameter(ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO) %>">
		
					<% if(tab.equals(IdentificativoSchede.TAB_INFO_COMUNI)){ %>
						
						<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE %>" 
							id="<%= ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE %>" 
							value="<%= request.getParameter(ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE) %>">
			
						<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB %>"
							id="<%= ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB %>"
							value="<%= request.getParameter(ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB) %>">
		
					<% } %>
					
					<% if(tab.equals(IdentificativoSchede.TAB_INIZIO_LAVORI)){ %>
							
						<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE %>" 
							id="<%= ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE %>" 
							value="<%= request.getParameter(ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE) %>">
			
						<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB %>"
							id="<%= ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB %>"
							value="<%= request.getParameter(ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB) %>">
							
					<input type="hidden" name="<%=ParametriServletInizioLavori.ID_INIZIO_LAVORI %>" 
							id="<%= ParametriServletInizioLavori.ID_INIZIO_LAVORI %>" 
								value="<%= request.getParameter(ParametriServletInizioLavori.ID_INIZIO_LAVORI) %>">
	
					<input type="hidden" name="<%=ParametriServletInizioLavori.DATA_INIZIO_LAVORI %>" 
							id="<%= ParametriServletInizioLavori.DATA_INIZIO_LAVORI %>" 
							value="<%= request.getParameter(ParametriServletInizioLavori.DATA_INIZIO_LAVORI) %>">
		
					<% } %>
					
					<% if(tab.equals(IdentificativoSchede.TAB_STIPULA)){ %>
							
						<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE %>" 
							id="<%= ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE %>" 
							value="<%= request.getParameter(ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE) %>">
			
						<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB %>"
							id="<%= ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB %>"
							value="<%= request.getParameter(ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB) %>">
							
					<input type="hidden" name="<%=ParametriServletStipula.ID_STIPULA %>" 
							id="<%= ParametriServletStipula.ID_STIPULA %>" 
								value="<%= request.getParameter(ParametriServletStipula.ID_STIPULA) %>">
	
					<input type="hidden" name="<%=ParametriServletStipula.DATA_INIZIO_STIPULA %>" 
							id="<%= ParametriServletStipula.DATA_INIZIO_STIPULA %>" 
							value="<%= request.getParameter(ParametriServletStipula.DATA_INIZIO_STIPULA) %>">
		
					<% } %>
					
					<% if(tab.equals(ParametriServletAvanzamento.TAB_AVANZAMENTO)){ %>
							
						<input type="hidden" name="<%= STATI_AVANZ.ID_AVANZAMENTO %>" 
							id="<%= STATI_AVANZ.ID_AVANZAMENTO %>" 
							value="<%= request.getParameter(STATI_AVANZ.ID_AVANZAMENTO) %>">
			
						<input type="hidden" name="<%= STATI_AVANZ.DATA_INIZIO_AVANZAMENTO %>"
							id="<%= STATI_AVANZ.DATA_INIZIO_AVANZAMENTO %>"
							value="<%= request.getParameter(STATI_AVANZ.DATA_INIZIO_AVANZAMENTO) %>">
		
					<% } %>
					
					<% if(tab.equals(ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI)){ %>
						
					<input type="hidden" name="<%=ParametriServletConclusioni.FIELD_NAME_ID_ULTIM %>" 
							id="<%= ParametriServletConclusioni.FIELD_NAME_ID_ULTIM %>" 
								value="<%= request.getParameter(ParametriServletConclusioni.FIELD_NAME_ID_ULTIM) %>">
	
					<input type="hidden" name="<%=ParametriServletConclusioni.FIELD_NAME_DATA_INIZIO_ULTIM %>" 
							id="<%= ParametriServletConclusioni.FIELD_NAME_DATA_INIZIO_ULTIM %>" 
							value="<%= request.getParameter(ParametriServletConclusioni.FIELD_NAME_DATA_INIZIO_ULTIM) %>">
		
					<% } %>
					
					<% if(tab.equals(ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO)){ %>
							
							
					<input type="hidden" name="<%=ParametriServletCollaudo.FIELD_NAME_ID_COLLAUDO %>" 
							id="<%= ParametriServletCollaudo.FIELD_NAME_ID_COLLAUDO %>" 
								value="<%= request.getParameter(ParametriServletCollaudo.FIELD_NAME_ID_COLLAUDO ) %>">
	
					<input type="hidden" name="<%=ParametriServletCollaudo.FIELD_NAME_DATA_INIZIO_COLL %>" 
							id="<%= ParametriServletCollaudo.FIELD_NAME_DATA_INIZIO_COLL %>" 
								value="<%= request.getParameter(ParametriServletCollaudo.FIELD_NAME_DATA_INIZIO_COLL) %>">
		
					<% } %>
					
					<% if(tab.equals(ParametriServletR129.TAB_SCHEDA_R129)){ %>
							
							
					<input type="hidden" name="<%=ParametriServletR129.FIELD_NAME_ID_RECORD %>" 
							id="<%= ParametriServletR129.FIELD_NAME_ID_RECORD  %>" 
								value="<%= request.getParameter(ParametriServletR129.FIELD_NAME_ID_RECORD ) %>">
	
					<input type="hidden" name="<%=ParametriServletR129.FIELD_NAME_DATA_INIZIO_RECORD %>" 
							id="<%= ParametriServletR129.FIELD_NAME_DATA_INIZIO_RECORD %>" 
								value="<%= request.getParameter(ParametriServletR129.FIELD_NAME_DATA_INIZIO_RECORD) %>">
		
					<% } %>
					
					<% if(tab.equals(ParametriServletAccordo.TAB_SCHEDA_ACCORDO)){ %>
							
							
					<input type="hidden" name="<%=ParametriServletAccordo.FIELD_NAME_ID_ACCORDO %>" 
							id="<%= ParametriServletAccordo.FIELD_NAME_ID_ACCORDO  %>" 
								value="<%= request.getParameter(ParametriServletAccordo.FIELD_NAME_ID_ACCORDO ) %>">
	
					<input type="hidden" name="<%=ParametriServletAccordo.FIELD_NAME_DATA_INIZIO_ACC %>" 
							id="<%= ParametriServletAccordo.FIELD_NAME_DATA_INIZIO_ACC %>" 
								value="<%= request.getParameter(ParametriServletAccordo.FIELD_NAME_DATA_INIZIO_ACC) %>">
		
					<% } %>
					<% if(tab.equals(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI)){ %>
							
							
					<input type="hidden" name="<%=ParametriServletSospensioni.FIELD_NAME_ID_SOSPENSIONE %>" 
							id="<%= ParametriServletSospensioni.FIELD_NAME_ID_SOSPENSIONE  %>" 
								value="<%= request.getParameter(ParametriServletSospensioni.FIELD_NAME_ID_SOSPENSIONE ) %>">
	
					<input type="hidden" name="<%=ParametriServletSospensioni.FIELD_NAME_DATA_INIZIO_SOSP %>" 
							id="<%=ParametriServletSospensioni.FIELD_NAME_DATA_INIZIO_SOSP %>" 
								value="<%= request.getParameter(ParametriServletSospensioni.FIELD_NAME_DATA_INIZIO_SOSP) %>">
		
					<% } %>
					<% if(tab.equals(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI)){ %>
							
							
					<input type="hidden" name="<%=ParametriServletSubappalti.FIELD_NAME_ID_RECORD %>" 
							id="<%= ParametriServletSubappalti.FIELD_NAME_ID_RECORD  %>" 
								value="<%= request.getParameter(ParametriServletSubappalti.FIELD_NAME_ID_RECORD ) %>">
	
					<input type="hidden" name="<%=ParametriServletSubappalti.FIELD_NAME_DATA_INIZIO_RECORD %>" 
							id="<%= ParametriServletSubappalti.FIELD_NAME_DATA_INIZIO_RECORD %>" 
								value="<%= request.getParameter(ParametriServletSubappalti.FIELD_NAME_DATA_INIZIO_RECORD) %>">
		
					<% } %>
					<% if(tab.equals(ParametriServletVariante.TAB_SCHEDA_VARIANTE)){ %>
							
							
					<input type="hidden" name="<%=ParametriServletVariante.FIELD_NAME_ID_VARIANTE %>" 
							id="<%= ParametriServletVariante.FIELD_NAME_ID_VARIANTE  %>" 
								value="<%= request.getParameter(ParametriServletVariante.FIELD_NAME_ID_VARIANTE ) %>">
	
					<input type="hidden" name="<%=ParametriServletVariante.FIELD_NAME_DATA_INIZIO_VAR %>" 
							id="<%= ParametriServletVariante.FIELD_NAME_DATA_INIZIO_VAR %>" 
								value="<%= request.getParameter(ParametriServletVariante.FIELD_NAME_DATA_INIZIO_VAR) %>">
		
					<% } %>
					
					
					
					
					<input type="hidden" name="<%= PSBD.FIELD_NAME_ID_AGGIUDICAZIONE %>" 
						id="<%= PSBD.FIELD_NAME_ID_AGGIUDICAZIONE %>" 
						value="<%= request.getParameter(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE) %>">	
					
					<input type="hidden" name="<%=PSBD.DATA_INIZIO_AGGIUDICAZIONE %>" 
						id="<%= PSBD.DATA_INIZIO_AGGIUDICAZIONE %>" 
						value="<%= request.getParameter(PSBD.DATA_INIZIO_AGGIUDICAZIONE) %>">
				
					
					
		
					<input type="hidden" name="<%=PSBD.TAB%>"
						 id="<%=PSBD.TAB%>" value="">	
		
					<input type="hidden" name="pagina" id="pagina" value="">
					<input type="hidden" name="tipoAzione" id="tipoAzione" value="">					
					
					<% if(showDatiComuni.equals("true")) {%>
						<input type="hidden" name="<%= ParametriServlet.SHOW_DATI_COMUNI %>" value="false">
					<% } else { %>
						<input type="hidden" name="<%= ParametriServlet.SHOW_DATI_COMUNI %>" value="true">
					<% } %>		
					 
					<h1><utils:message key="scheda.gestioneSchede" /> - <utils:message key="richiesta.richiestaModifica" /></h1>
					<%@ include file="/include/gestisciErrore.inc" %>
					<br>
					<fieldset>
						<fieldset>
						
							<label for="">Codice di individuazione dell'appalto (CIG) : <c:out value="${datiGara.fullCIG}"/> </label>
							<table width="100%">
<% if (SimogFlags.is30230_RFWEBSC03Active()){%>		
		<tr><td>&nbsp;</td></tr>					
		<tr>
			<th><label for="<%= PSBD.FIELD_NAME_MOTIVO_RICH %>">Motivazione della richiesta</label></th>
			<td>
				<select onchange="setFormModified('Modificato')"  
						name="<%= PSBD.FIELD_NAME_MOTIVO_RICH %>" 
						id=<%= PSBD.FIELD_NAME_MOTIVO_RICH %> CLASS="BOTTONE">
					<option></option>
					<option value="1">Errore materiale</option>
					<option value="2">Duplicazione della scheda</option>
					<option value="3">Scheda non dovuta</option>
					<option value="4">Cancellazione scheda a ritroso</option>
					<option value="5">Altro</option>
				</select>
			</td>
		</tr>
<%} %>
								<tr>
									<th><label for="">Motivazione</label></th>
									<td>
										<textarea rows="5" cols="50" name="<%= PSBD.MOTIVAZIONE_ANNULLAMENTO %>" id="<%= PSBD.MOTIVAZIONE_ANNULLAMENTO %>"></textarea>
									</td>
								</tr>
							</table>
						</fieldset>
						<input type="hidden" name="Modificato" value="0">
						<input type="button" value="Salva" onclick="richiediAnnullamento1('<%= tab %>','<%= PSBD.MOTIVAZIONE_ANNULLAMENTO %>')" >
						<%// String link = "changeTab('"+tab+"','Modificato')"; %>
						<%// la variabile link contiene il parametro da passare all'onclick  ***************
						  // del bottone "torna" ***********************************************************%>
						<% String link = "torna()"; %>
						
						<% if(tab.equals(IdentificativoSchede.TAB_INFO_COMUNI)){ %>
							<% link = "tornaDatiComuni()"; %>
						<% } %>
						<input type="button" value="Torna" onclick="<%=link %>">
						<input type="reset" value="Reimposta" onclick="reimposta()">					
					</fieldset>
				</form>
			</div>
		</div>
		<%@ include file="/include/newfooter.inc" %>
		
	</div>
</body>	
