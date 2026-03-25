<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="../errore.jsp"%>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="../include/basicHeader.inc"%>

<%@ include file="../include/controlloSessione.inc"%>
<title><%=request.getAttribute("titleRubrica")%></title>
<base target="_self" />
</head>

<%@ page import="it.avlp.simog.beans.*"%>
<%@ page
	import="it.avlp.simog.common.servlet.*,it.avlp.simog.db.advanced.*"%>
<%@ page import="it.avlp.simog.util.*"%>
<%@ page import="it.avlp.simog.db.advanced.*"%>
<%@ page import="it.avlp.simog.db.generated.*"%>

<%@page import="it.avlp.simog.util.PageHelper"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.util.SimogProperties"%>
<%@page import="it.avlp.simog.db.Costanti"%>
<%try{ %>
<%@ include file="../include/i18n-init.inc" %>
<%@ include file="/script/script.js"%>
<%@ include file="/script/domUtils.js"%>
<%@ include file="../include/gestisciErrore.inc"%>
<%@ include file="/script/AjaxPaesi.js"%>
<script type="text/javascript" src="script/pageutils.js"></script>

<script type="text/javascript">
  <!--
  function getKey(e){
		var keynum;
		var keychar;
		var numcheck;
		if(window.event) // IE
		{
			keynum = e.keyCode;
		}
		else if(e.which) // Netscape/Firefox/Opera
		{
			keynum = e.which;
		}
		return keynum;
	}
	function submitRubricaResp(operazione){
			document.forms[0].action = "rubricaResponsabili?operazione="+operazione;
			document.forms[0].submit();
	}  
	function doAction() {
		document.forms[0].action = 'rubrica'; 
		document.forms[0].elements['<%=PSBD.TAB%>'].value='<%=request.getParameter(PSBD.TAB)%>';	
		document.forms[0].elements['<%=ParametriServletRubrica.OPERAZIONE%>'].value="<%= MessageHelper.getMessage(request, "button.aggiungi") %>";
		document.forms[0].submit();
	}
  function submitIfKeyPress(e, key, thisElem, tab){
		if(thisElem.value != "" && getKey(e) == key){
			cercaInRubrica(tab);
		}
	} 
 //-->
</script>

<%
	String tab = request.getParameter(PSBD.TAB);
	if (tab.equals(PSBD.TAB_AFFIDATARIO)
			//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
			|| (tab.equals(PSBD.TAB_PRESTAZIONI) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_OPERATORE_ECONOMICO))
			|| tab.equals(ParametriServletInizioLavori.TAB_POSIZIONE_AGGIUDICATARI)
			|| tab.equals(PSBD.TAB_DITTA_AUSILIARIA)
			|| tab.equals(PSBD.TAB_DITTA_RAGGRUPPAMENTO)) {
%>
<body onload="ajaxRead('comboPaesi')">
<%
	} else {
%>
<body>
<%
	}
%>

<div>
<%
	TableBean tableBean = (TableBean) request
			.getAttribute(ParametriServlet.TABLEBEAN);
	int size = tableBean.getFullSize();
	int indiceTab = 0;
%>


<div class="bodypage-e">
<form id="IdFormPopup" name="popupRubrica" action="../rubrica"
	method="post"><input type="hidden" name="titleRubrica"
	id="titleRubrica" value="<%=request.getAttribute("titleRubrica")%>">
<input type="hidden" name="paginazione" id="paginazione"
	value="ricercaPagine"> <input type="hidden"
	name="<%=PSBD.TAB%>" id="<%=PSBD.TAB%>" value=""> <%
 	// String tab = request.getParameter(PSBD.TAB);
 	String prefix = null;

 	String titleRubrica = ""; // Stringa per determinare il nome della rubrica da inserire per la paginazione.

 	if (PSBD.TAB_AFFIDATARIO.equalsIgnoreCase(tab)) {
 		prefix = PSBD.AGGIUDICATARIO;
 		titleRubrica = MessageHelper.getMessage(request, "rubrica.rubricaOperatoriEconomici");
 	} else if (PSBD.TAB_RESPONSABILE_PROCEDIMENTO.equalsIgnoreCase(tab)) {
 		prefix = PSBD.RESPONSABILE;
 		titleRubrica = MessageHelper.getMessage(request, "rubrica.rubricaIncaricati");
 	//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
 	} else if ((PSBD.TAB_PRESTAZIONI.equalsIgnoreCase(tab)) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_INCARICATI)) {
 		prefix = PSBD.PRESTAZIONE;
 		titleRubrica = MessageHelper.getMessage(request, "rubrica.rubricaIncaricati");
 	//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
 	} else if ((PSBD.TAB_PRESTAZIONI.equalsIgnoreCase(tab)) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_OPERATORE_ECONOMICO)) {
 		prefix = PSBD.PRESTAZIONE;
 		titleRubrica = MessageHelper.getMessage(request, "rubrica.rubricaOperatoriEconomici");
 	} else if (ParametriServletInizioLavori.TAB_POSIZIONE_AGGIUDICATARI.equalsIgnoreCase(tab)) {
 		prefix = ParametriServletInizioLavori.POSIZIONE_AGGIUDICATARIO;
 		titleRubrica = MessageHelper.getMessage(request, "rubrica.rubricaOperatoriEconomici");
 	} else if (PSBD.TAB_DITTA_AUSILIARIA.equalsIgnoreCase(tab)) {
 		prefix = PSBD.DITTA_AUSILIARIA;
 		titleRubrica = MessageHelper.getMessage(request, "rubrica.rubricaOperatoriEconomici");
 	}else if (PSBD.TAB_DITTA_RAGGRUPPAMENTO.equalsIgnoreCase(tab)) {
		prefix = PSBD.DITTA_RAGGRUPPAMENTO;
		titleRubrica = MessageHelper.getMessage(request, "rubrica.rubricaOperatoriEconomici");
   }
 %> <!-- *********************************************   Parte inserita per la paginazione ********************************* -->

<%
	int maxRigheVisualizzabili = Integer
			.parseInt((String) request
					.getAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI));
%>

<%
	Integer startRowInt = (Integer) request
			.getAttribute(ParametriServlet.START_ROW);
%>
<%
	int startRow = startRowInt.intValue();
%> <%
 	long resto = (size % maxRigheVisualizzabili);
 	long fineElenco = tableBean.getFullSize() - resto
 			- maxRigheVisualizzabili
 			- (resto == 0 ? maxRigheVisualizzabili : 0);
 %> <%
 	int righeVisualizzate = startRow + tableBean.getTableSize();
 %>
<%
	if (righeVisualizzate > size) {
%> <%
 	righeVisualizzate = size;
 %> <%
 	}
 %>

<div class="hmenu">
<%
	if (size > 0) {
%> <%
 	if (startRowInt > 0) {
 %>
<li><a
	href="rubrica?<%=PSBD.TAB%>=<%=tab%>&titleRubrica=<%=titleRubrica%>&operazione=<%= MessageHelper.getMessage(request, "rubrica.cercaInRubrica") %>"
	title="<utils:message key="rubrica.visualizzaPrimaPagina" plain="true" />"><utils:message key="rubrica.inizioElenco" /></a></li>
<%
	} else {
%>
<li><a id="disabledMenu" title="<utils:message key="rubrica.visualizzaPrimaPagina" plain="true" />"><utils:message key="rubrica.inizioElenco" /></a></li>
<%
	}
%> <%
 	if (righeVisualizzate > maxRigheVisualizzabili) {
%>
<li><a
	href="rubrica?<%=PSBD.TAB%>=<%=tab%>&titleRubrica=<%=titleRubrica%>&operazione=<%= MessageHelper.getMessage(request, "rubrica.cercaInRubrica") %>&<%=ParametriServlet.ACTION_GET_LIST%>=<%=ParametriServlet.REGRESS%>&<%=ParametriServlet.START_ROW%>=<%=startRow%>"
	title="<utils:message key="rubrica.visualizzaPrecedenti" plain="true" />"><utils:message key="rubrica.precedenti" /></a></li>
<%
	} else {
%>
<li><a id="disabledMenu" title="<utils:message key="rubrica.visualizzaPrecedenti" plain="true" />"><utils:message key="rubrica.precedenti" /></a></li>
<%
	}
%> <%
 	if (size - righeVisualizzate > 0) {
%>
<li><a
	href="rubrica?<%=PSBD.TAB%>=<%=tab%>&titleRubrica=<%=titleRubrica%>&operazione=<%= MessageHelper.getMessage(request, "rubrica.cercaInRubrica") %>&<%=ParametriServlet.ACTION_GET_LIST%>=<%=ParametriServlet.PROGRESS%>&<%=ParametriServlet.START_ROW%>=<%=startRow%>"
	title="<utils:message key="rubrica.visualizzaSuccessive" plain="true" />"><utils:message key="rubrica.successive" /></a></li>
<%
	} else {
%>
<li><a id="disabledMenu" title="<utils:message key="rubrica.visualizzaSuccessive" plain="true" />"><utils:message key="rubrica.successive" /></a></li>
<%
	}
%> <%
 	if (righeVisualizzate != size) {
%>
<li><a
	href="rubrica?<%=PSBD.TAB%>=<%=tab%>&titleRubrica=<%=titleRubrica%>&operazione=<%= MessageHelper.getMessage(request, "rubrica.cercaInRubrica") %>&<%=ParametriServlet.ACTION_GET_LIST%>=<%=ParametriServlet.PROGRESS%>&<%=ParametriServlet.START_ROW%>=<%=fineElenco%>"
	title="<utils:message key="rubrica.visualizzaUltimaPagina" plain="true" />"><utils:message key="rubrica.fineElenco" /></a></li>
<%
	} else {
%>
<li><a id="disabledMenu" title="<utils:message key="rubrica.visualizzaUltimaPagina" plain="true" />"><utils:message key="rubrica.fineElenco" /></a></li>
<%
	}
%> <%
 	}
 %> <%-- 
							<p><%= "startRowInt " + (startRowInt)%></p>
							<p><%= "righeVisualizzate " + (righeVisualizzate)%></p>
							<p><%= "tableBean.getFullSize()  - resto " + (size - resto)%></p>
							<p><%= "fine elenco " + fineElenco%></p>
							<p><%= "if( "+righeVisualizzate+" < "+(size - resto)+" )"%></p>
							--%></div>


<!-- ******************************************************************************************************************  -->




<div class="header"><br />
<fieldset><legend><utils:message key="rubrica.elencoSoggetti" /></legend> <!-- <div class="elenco">-->
<div class="scrollLittle" style="height: 250px;">
<div class="gara">
<%
	TableBeanRow currentRow = null;
%> <%
 	TableBeanRow previousRow = null;
 %>
<%
	String dataIniziosoggetto = null;
%> <%
 	String cognome = null;
 %> <%
 	String nome = null;
 %>
<%
	String codice = null;
%> <%
 	String denom = null;
 %> <%
 	String id_stato = null;
 %>
<%
	String parametri = "";
%> <%
 	int id_partecipante = 0;
 %> <%
 	if (tableBean != null) {
 %>
<table>
	<%
		if (tab.equals(PSBD.TAB_AFFIDATARIO)
					//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
					|| (tab.equals(PSBD.TAB_PRESTAZIONI) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_OPERATORE_ECONOMICO))
					|| tab.equals(ParametriServletInizioLavori.TAB_POSIZIONE_AGGIUDICATARI)
					|| tab.equals(PSBD.TAB_DITTA_AUSILIARIA)
					|| tab.equals(PSBD.TAB_DITTA_RAGGRUPPAMENTO)) {
	%>
	<tr>
		<th class="garaTh" width="40%">Denominazione</th>
		<th class="garaTh" width="10%">Codice Paese</th>
		<th class="garaTh" width="20%">Codice Fiscale / Partita Iva</th>
		<th class="garaTh" width="1%"></th>
	</tr>
	<%
		} else {
	%>
	<tr>
		<th class="garaTh" width="30%">Cognome</th>
		<th class="garaTh" width="30%">Nome</th>
		<th class="garaTh" width="20%">Codice</th>
		<th class="garaTh" width="10%"></th>
	</tr>
	<%
		}
	%>

	<%
		for (int rowIndex = 0; rowIndex < tableBean.getTableSize(); rowIndex++) {
				currentRow = tableBean.getRow(rowIndex);

				if (tab.equals(PSBD.TAB_AFFIDATARIO)
					//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI		
					||(tab.equals(PSBD.TAB_PRESTAZIONI) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_OPERATORE_ECONOMICO))
					|| tab.equals(PSBD.TAB_DITTA_AUSILIARIA)
					|| tab.equals(PSBD.TAB_DITTA_RAGGRUPPAMENTO)) {

					codice = currentRow.getNulledField(SOGGETTI_PARTECIPANTI.CODICE_FISCALE);
					denom = currentRow.getNulledField(SOGGETTI_PARTECIPANTI.DENOMINAZIONE);
					id_partecipante = Integer.parseInt(currentRow.getNulledField(SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE));
					dataIniziosoggetto = currentRow.getNulledField(SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG);
					id_stato = currentRow.getNulledField(SOGGETTI_PARTECIPANTI.ID_STATO);
					if ("".equals(id_stato))
						id_stato = Costanti.CODICE_STATO_ITALIANO; //Non valorizzato vuol dire che � italiano

					//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI			
					if(tab.equals(PSBD.TAB_PRESTAZIONI) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_OPERATORE_ECONOMICO)){
					parametri = "'"
							+ PSBD.FIELD_NAME_PRESTAZIONE_ID_RESPONSABILE
							+ "','" + id_partecipante + "','"
							+ PSBD.FIELD_NAME_PRESTAZIONE_DATA_INIZIO_RES
							+ "','" + dataIniziosoggetto + "','"
							+ PSBD.FIELD_NAME_PRESTAZIONE_CODICEFISCALE
							+ "','" + id_stato + "-" + codice + "','"
							+ PSBD.FIELD_NAME_PRESTAZIONE_COGNOME + "','"
							+ denom.replaceAll("'", "" + (char) 180)
							+ "','" + PSBD.FIELD_NAME_PRESTAZIONE_NOME
							//+ "','" + denom.replaceAll("'", "" + (char) 180)
							//+ "'";	
							+ "',''";
					}
					else{
					parametri = "'" + PSBD.FIELD_NAME_AGG_DENOMINAZIONE
							+ "','"
							+ denom.replaceAll("'", "" + (char) 180)
							+ "','" +

							PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG + "','"
							+ dataIniziosoggetto + "','"
							+ PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE
							+ "','" + id_partecipante
							+ "','"
							+
							//PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO+"','"+ (id_stato == null || "".equals(id_stato) ? it.avcp.simog.manager.paesi.PaesiManager.CODICE_STATO_ITALIANO : id_stato) +" - "+codice+"'";
							PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO
							+ "','" + codice + "','"
							+
							//PSBD.FIELD_NAME_AGG_ID_STATO+"','"+(id_stato == null || "".equals(id_stato) ? it.avcp.simog.manager.paesi.PaesiManager.CODICE_STATO_ITALIANO : id_stato)+"'";
							PSBD.FIELD_NAME_AGG_ID_PAESE + "','" + id_stato
							+ "'";
					}
				//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
				} else if (tab.equals(PSBD.TAB_RESPONSABILE_PROCEDIMENTO) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_INCARICATI)){				
				//} else if (tab.equals(PSBD.TAB_PRESTAZIONI)) {

				    cognome = currentRow
							.getNulledField(SOGGETTI_RESPONSABILI.COGNOME);
					nome = currentRow
							.getNulledField(SOGGETTI_RESPONSABILI.NOME);

					codice = currentRow
							.getNulledField(SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE);
					id_partecipante = Integer
							.parseInt(currentRow
									.getNulledField(SOGGETTI_RESPONSABILI.ID_RESPONSABILE));
					dataIniziosoggetto = currentRow
							.getNulledField(SOGGETTI_RESPONSABILI.DATA_INIZIO_RES);

					parametri = "'" + PSBD.FIELD_NAME_ID_RESPONSABILE
							+ "','" + id_partecipante + "','"
							+ PSBD.FIELD_NAME_DATA_INIZIO_RES + "','"
							+ dataIniziosoggetto + "','"
							+ PSBD.FIELD_NAME_CODICE_FISCALE_RESPONSABILE
							+ "','" + codice + "','"
							+ PSBD.FIELD_NAME_COGNOME_RESPONSABILE + "','"
							+ cognome.replaceAll("'", "" + (char) 180)
							+ "','" + PSBD.FIELD_NAME_NOME_RESPONSABILE
							+ "','" + nome.replaceAll("'", "" + (char) 180)
							+ "'";
					
				//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
				} else if (tab.equals(PSBD.TAB_PRESTAZIONI) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_INCARICATI)){				
				//} else if (tab.equals(PSBD.TAB_PRESTAZIONI)) {

					cognome = currentRow
							.getNulledField(SOGGETTI_RESPONSABILI.COGNOME);
					nome = currentRow
							.getNulledField(SOGGETTI_RESPONSABILI.NOME);
					codice = currentRow
							.getNulledField(SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE);
					id_partecipante = Integer
							.parseInt(currentRow
									.getNulledField(SOGGETTI_RESPONSABILI.ID_RESPONSABILE));
					dataIniziosoggetto = currentRow
							.getNulledField(SOGGETTI_RESPONSABILI.DATA_INIZIO_RES);

					parametri = "'"
							+ PSBD.FIELD_NAME_PRESTAZIONE_ID_RESPONSABILE
							+ "','" + id_partecipante + "','"
							+ PSBD.FIELD_NAME_PRESTAZIONE_DATA_INIZIO_RES
							+ "','" + dataIniziosoggetto + "','"
							+ PSBD.FIELD_NAME_PRESTAZIONE_CODICEFISCALE
							+ "','" + codice + "','"
							+ PSBD.FIELD_NAME_PRESTAZIONE_COGNOME + "','"
							+ cognome.replaceAll("'", "" + (char) 180)
							+ "','" + PSBD.FIELD_NAME_PRESTAZIONE_NOME
							+ "','" + nome.replaceAll("'", "" + (char) 180)
							+ "'";
				} else if (ParametriServletInizioLavori.TAB_POSIZIONE_AGGIUDICATARI
						.equalsIgnoreCase(tab)) {
					codice = currentRow
							.getNulledField(SOGGETTI_PARTECIPANTI.CODICE_FISCALE);
					id_stato = currentRow
							.getNulledField(SOGGETTI_PARTECIPANTI.ID_STATO);

					// ----------- controllo per la presenza di ' nella denominazione - cambia il tipo di apice -------------------
					denom = currentRow
							.getNulledField(SOGGETTI_PARTECIPANTI.DENOMINAZIONE);
					// ------------------------------------------------------------------------------------------------------------

					id_partecipante = Integer
							.parseInt(currentRow
									.getNulledField(SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE));
					dataIniziosoggetto = currentRow
							.getNulledField(SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG);

					parametri = "'" + PSBD.FIELD_NAME_AGG_DENOMINAZIONE
							+ "','"
							+ denom.replaceAll("'", "" + (char) 180)
							+ "','" + PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG
							+ "','" + dataIniziosoggetto + "','"
							+ PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE
							+ "','" + id_partecipante + "','"
							+ PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO
							+ "','" + codice + "'";

				}
	%>

	<tr>
		<%
			if (tab.equals(PSBD.TAB_AFFIDATARIO)
							|| tab.equals(ParametriServletInizioLavori.TAB_POSIZIONE_AGGIUDICATARI)
							//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
					      || (tab.equals(PSBD.TAB_PRESTAZIONI) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_OPERATORE_ECONOMICO))	
							|| tab.equals(PSBD.TAB_DITTA_AUSILIARIA)
							|| tab.equals(PSBD.TAB_DITTA_RAGGRUPPAMENTO)) {
		%>
		<td class="garaTd"><%=PageHelper.formattaTesto(denom)%></td>
		<td class="garaTd"><%=id_stato%></td>
		<td class="garaTd"><%=codice%></td>

		<%
			} else {
		%>
		<td class="garaTd"><%=PageHelper.formattaTesto(cognome)%></td>
		<td class="garaTd"><%=PageHelper.formattaTesto(nome)%></td>
		<td class="garaTd"><%=codice%></td>
		<%
			}
		%>
		<td>
		<div class="hmenu"><a title="Selezione"
			href="javascript:selectElement('<%=prefix%>',[<%=parametri%>],window.dialogArguments != null ? window.dialogArguments : window)"
			title="Selezione">Seleziona</a></div>
		</td>
	</tr>
	<%
		}
	%>
</table>
<%
	}
%>
</div>
</div>
</fieldset>

</div>
<p></p>
<fieldset>
<fieldset><legend>Filtri nominali</legend>

<table>
	<tr>
		<%
			if (!tab.equals(PSBD.TAB_AFFIDATARIO)
					//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
					&& !(tab.equals(PSBD.TAB_PRESTAZIONI) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_OPERATORE_ECONOMICO))
					&& !tab.equals(ParametriServletInizioLavori.TAB_POSIZIONE_AGGIUDICATARI)
					&& !tab.equals(PSBD.TAB_DITTA_AUSILIARIA)
					&& !tab.equals(PSBD.TAB_DITTA_RAGGRUPPAMENTO)) {
		%>
		<td class="detailHelp" colspan="2"><utils:message key="rubrica.inserireCodiceFiscaleIncaricato" /></td>
		<%
			} else {
		%>		<td class="detailHelp" colspan="2"><utils:message key="rubrica.inserireCodiceFiscalePartitaIva" /></td>
		<%
			}
		%>
	</tr>
	<tr>
		<td><input tabindex="<%=++indiceTab%>" size="50" type="text"
			title="Codice Fiscale" id="codiceFiscale"
			name="<%=ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE%>"
			onkeypress="submitIfKeyPress(event,'13',this,'<%=request.getParameter(PSBD.TAB)%>')">
		</td>
	</tr>
	<tr>
		<%
			if (!tab.equals(PSBD.TAB_AFFIDATARIO)
					//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
					&& !(tab.equals(PSBD.TAB_PRESTAZIONI) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_OPERATORE_ECONOMICO))
					&& !tab.equals(ParametriServletInizioLavori.TAB_POSIZIONE_AGGIUDICATARI)
					&& !tab.equals(PSBD.TAB_DITTA_AUSILIARIA)
					&& !tab.equals(PSBD.TAB_DITTA_RAGGRUPPAMENTO)) {
		%>
		<td class="detailHelp" colspan="2"><utils:message key="rubrica.inserireCognomeNomeIncaricato" /></td>
		<%
			} else {
		%>
		<td class="detailHelp" colspan="2"><utils:message key="rubrica.inserireDenominazioneSoggetto" /></td>
		<%
			}
		%>
	</tr>
	<tr>
		<td><input tabindex="<%=++indiceTab%>" size="50" type="text"
			title="<utils:message key="table.cognome" plain="true" />" id="<%=ParametriServletRubrica.FIELD_NAME_COGNOME%>"
			name="<%=ParametriServletRubrica.FIELD_NAME_COGNOME%>"
			onkeypress="submitIfKeyPress(event,'13',this,'<%=request.getParameter(PSBD.TAB)%>')">
		</td>
	</tr>
	<%
		if (!tab.equals(PSBD.TAB_AFFIDATARIO)
				//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
				&& !(tab.equals(PSBD.TAB_PRESTAZIONI) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_OPERATORE_ECONOMICO))
				&& !tab.equals(ParametriServletInizioLavori.TAB_POSIZIONE_AGGIUDICATARI)
				&& !tab.equals(PSBD.TAB_DITTA_AUSILIARIA)
				&& !tab.equals(PSBD.TAB_DITTA_RAGGRUPPAMENTO)) {
	%>
	<tr>
		<td class="detailHelp" colspan="2"><utils:message key="rubrica.inserireCognomeNomeIncaricato" /></td>
	</tr>
	<tr>
		<td><input tabindex="<%=++indiceTab%>" size="50" type="text"
			title="<utils:message key="table.nome" plain="true" />" id="<%=ParametriServletRubrica.FIELD_NAME_NOME%>"
			name="<%=ParametriServletRubrica.FIELD_NAME_NOME%>"
			onkeypress="submitIfKeyPress(event,'13',this,'<%=request.getParameter(PSBD.TAB)%>')">
		</td>
	</tr>
	<%
		}
	%>
	<%
		if (tab.equals(PSBD.TAB_AFFIDATARIO)
				//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
				|| (tab.equals(PSBD.TAB_PRESTAZIONI) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_OPERATORE_ECONOMICO))
				|| tab.equals(ParametriServletInizioLavori.TAB_POSIZIONE_AGGIUDICATARI)
				|| tab.equals(PSBD.TAB_DITTA_AUSILIARIA)
				|| tab.equals(PSBD.TAB_DITTA_RAGGRUPPAMENTO)) {
	%>
	<tr>
		<td class="detailHelp" colspan="2">Selezionare un paese ( Se
		Operatore Estero )</td>
	</tr>
	<tr>
		<td id="comboPaesi"></td>
	</tr>
	<%
		}
	%>
</table>
</fieldset>
<p></p>
<input type="hidden" id="<%=ParametriServletRubrica.OPERAZIONE%>"
	name="<%=ParametriServletRubrica.OPERAZIONE%>"> <input
	type="button" id="cerca" name="cerca" value="<utils:message key="button.cerca" plain="true" />"
	onclick="javascript:cercaInRubrica('<%=request.getParameter(PSBD.TAB)%>');">
<input type="button" id="aggiungi" name="aggiungi" value="<utils:message key="button.aggiungi" plain="true" />"
	onclick="javascript:doAction();"> <input type="button"
	value="<utils:message key="rubrica.torna" plain="true" />" onclick="chiudiPopUp()"></fieldset>

</form>

</div>
</body>
<%}catch(Exception e){e.printStackTrace();} %>
</html>