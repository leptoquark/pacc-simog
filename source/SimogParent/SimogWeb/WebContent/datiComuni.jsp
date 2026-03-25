<%@page import="it.avlp.simog.util.SimogProperties"%>
<%@page import="it.avlp.simog.garamanager.lotto.LottoManager"%>
<%try { %>
<%@page import="it.avlp.simog.validatore.SimogFlusso"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp"%>
<%@page import="it.avlp.simog.beans.InfoComuniBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<title><utils:message key="scheda.gestioneSchede" /> - <utils:message key="datiComuni.datiComuni" /> - <%= user.getProfilo() %></title>
<% int indiceTab = 0;%>
	
	<link rel="stylesheet" type="text/css" href="theme/tabmenu.css"/>

	<!-- calendar stylesheet -->
	<link rel="stylesheet" type="text/css" media="all" href="calendar/calendar-blue.css" title="win2k-cold-1" />

	<!-- main calendar program -->
	<script type="text/javascript" src="calendar/calendar.js"></script>
	<script type="text/javascript" src="script/pageutils.js"></script>
	<!-- language for the calendar -->
	<%@ include file="include/calendar-dynamic.inc" %>

	<!-- the following script defines the Calendar.setup helper function, which makes
     adding a calendar a matter of 1 or 2 lines of code. -->
	<script type="text/javascript" src="calendar/calendar-setup.js"></script>
	<%@ include file="include/i18n-init.inc" %>
	<!-- MAC 36768 3.04.8.1 -->
	<!-- import jQuery -->
	<script type="text/javascript" src="script/other/jquery.js"></script>
	<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
	<!-- FINE MAC 36768 -->
	
	<script type="text/javascript">
	function showHint(settore, contratto, escluso, modoReal, dataPubb, importo)
	{
		var xmlhttp;
		var str;
		if (window.XMLHttpRequest)
		{// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp=new XMLHttpRequest();
		}
		else
		{// code for IE6, IE5
		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange=function()
		{
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		  {	// impostazione ritorno
		    document.getElementById("tipoFlusso").innerHTML=xmlhttp.responseText;
		  }
		}
		str="&s=" + settore + "&c=" + contratto + "&e=" + escluso + "&m=" + modoReal + "&d=" + dataPubb + "&i=" + importo;
		xmlhttp.open("GET","datiComuni?toDo=qry"+str,true);
		xmlhttp.send();
	}
	</script>
	<!-- MAC 36768 3.04.8.1 -->
	<script>
	$( document ).ready(function() {
		 if (document.querySelectorAll('#TAB_MESSAGGI_ERRORE li').length >1){
				for (let li of document.querySelectorAll('#TAB_MESSAGGI_ERRORE li')){
					li.style.display = li.textContent.indexOf('SIMOG_VALIDAZIONE_264') != -1 ? "none" : "";
					}
			 }
			 else {
				 	for (let li of document.querySelectorAll('#TAB_MESSAGGI_ERRORE li')){
						if (li.textContent.indexOf('SIMOG_VALIDAZIONE_264') != -1)
							$('#TAB_MESSAGGI_ERRORE').closest('table').hide();
					}
			 }
		});
	</script>
	<!--FINE MAC 36768-->
</head>
<c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>
<jsp:useBean id="dati_comuni" type="it.avlp.simog.beans.InfoComuniBean" class="it.avlp.simog.beans.InfoComuniBean" scope="request"></jsp:useBean>
<c:set var="rupOk" value="${UTENTE.login eq datiGara.cfRup || datiGara.cfRup eq null }"></c:set>
<c:set var="hide" value="${(datiGara.deleted || dati_comuni.confirmed || rupOk eq false || UTENTE.ossReg || UTENTE.RASA || delegaSchede eq true)}" />
<c:set var="disabled" value="${hide ? 'disabled':'' }"></c:set>
<c:set var="noConf" value="${(hide || (dati_comuni.idInfo le 0)) || dati_comuni.richAnn eq true ? 'disabled':''}"></c:set>
<c:set var="aggiudicata" value="${dati_comuni.esitoProcedura eq 1}"></c:set>
<c:set var="fromAVCPass" value="${false}" />
<c:set var="vReadonlyDelegante" value="${readonlyDelegante eq 'readonly'}" />
<% if (SimogFlags.is3028_RFWEBSC00Active()){ %>
	<c:set var="fromAVCPass" value="${dati_comuni.fromAVCPass}" />
<% } %>
<c:set var="disabledAVCPass" value="${fromAVCPass ? 'disabled' : ''}" />
<c:set var="markFieldAVCPass" value="${fromAVCPass ? '*' : ''}" />

<% 
	InfoGaraBean infG = (InfoGaraBean) request.getSession(false).getAttribute("dati_gara"); 
	String dataGara = infG.getDataPubblicazione();
	String impRifStr = SimogValidator.getImportoRifStr(infG.getTipoContratto(), dataGara);
	request.setAttribute("impRifStr", impRifStr);
	String testo = SimogFlusso.getLogicaJsp();
	
	//TICKET ALM - 3.04.3
	String dataCreazione = infG.getDataCreazioneGara();
    //FINE TICKET ALM - 3.04.3
    
    //TICKET ALM #659 - 3.04.4
    boolean disable3044flag = dataCreazione.compareTo(String.valueOf(SimogProperties.getInstance().getDataAttivazione3044())) > 0;
    String disabled3044 = disable3044flag ? "disabled" : "";
    String checkNumber = disable3044flag ? "3" : "5";
    
    //TICKET ALM #14286 - 3.04.4.1
    boolean disableDisclaimer = dataCreazione.compareTo(String.valueOf(SimogProperties.getInstance().getDataObblighiComunicativiSpeciali())) > 0;

    
%>

<%


MessageBean messBean = (it.avlp.simog.beans.MessageBean) request.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.ERRORBEAN);
it.avlp.simog.beans.AllValidationBeans beanErr = null;
HashMap<String, String> fieldToHighlight = new HashMap<String,String>();
if ( messBean != null ) 
if ( messBean instanceof it.avlp.simog.beans.AllValidationBeans ){
beanErr = (it.avlp.simog.beans.AllValidationBeans) request.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.ERRORBEAN);
fieldToHighlight = beanErr.getFieldToHighlight();
}
%>

<SCRIPT type="text/javascript">
// codice ricavato da classe SimogFlusso
<%= testo %>

function askMe1(url){
	var confirmMsg = (typeof i18n !== 'undefined' && i18n.get) ? i18n.get('error.subsequentAwardWarning') + "\n\nPer la gare pubblicate a decorrere dal 1 febbraio 2008 e' possibile inserire una aggiudicazione successiva alla prima dopo aver correttamente compilato i dati di conclusione relativi al precedente contratto, e comunque limitatamente ai casi previsti dalla norma \n\nSi intende proseguire comunque?" : "Attenzione: la possibilita' di inserire aggiudicazioni successive alla prima e' limitata alle sole gare di servizi e forniture pubblicate entro la data del 31 gennaio 2008 e per le quali era prevista l'acquisizione di un unico CIG anche se espletate su piu' lotti.\n\nPer la gare pubblicate a decorrere dal 1 febbraio 2008 e' possibile inserire una aggiudicazione successiva alla prima dopo aver correttamente compilato i dati di conclusione relativi al precedente contratto, e comunque limitatamente ai casi previsti dalla norma \n\nSi intende proseguire comunque?";
	if(!(typeof i18n !== 'undefined' && i18n.confirm ? i18n.confirm('error.subsequentAwardWarning', {}) : confirm(confirmMsg))){
		if(url != null){
			location.href = url;
		}else{
			location.href = '';
		}
	}
}

</script>
<body>
<div id="gabbia">
		<%@ include file="/include/header.inc" %>		
		
<%-- 		<h1>fromAVCPass: ${fromAVCPass} - origine[${dati_comuni.origine}]</h1> --%>
			
		<div class="bodypage-e">
		<%--Header Scheda e Lista Schede gia compilate --%>
			<h1><utils:message key="scheda.gestioneSchede" /> - <utils:message key="datiComuni.datiComuni" /></h1>
			<br>
			<div class="hmenu" >	
			  <ul> 
			     <% if(session.getAttribute("ultimaRicerca") != null) {
						String href = (String)session.getAttribute("ultimaRicerca"); %>
						<li><a title="Pagina precedente" href="<%=href %>">Ritorna</a></li>
					<% } %>
			  
			     <%String riScheda =ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + dati_comuni.getIdLotto();  %>
			     
			     <c:if test="${dati_comuni.confirmed eq true && aggiudicata eq true}">  
					<li><a title="<utils:message key="scheda.tornaListaAggiudicazioni" plain="true" />" href="javascript:changePage('<%=riScheda%>','Modificato')"><utils:message key="scheda.listaAggiudicazioni" /></a></li>			    
			 	</c:if>
			 </ul>
			</div> 
			<br>
		<%--Errori.... --%>
		<%@ include file="/include/gestisciErrore.inc" %>
		
		 	<%if (request.getAttribute("infoPresaIncarico") != null && request.getAttribute("infoPresaIncarico") != "") {%>
		 		<% TableBean tab = (TableBean) request.getAttribute("infoPresaIncarico"); %>
		 		
		 		<% if (!tab.isEmpty()) {%>
			 		<fieldset>
						<legend>Storico Presa in carico</legend>
						
			 				<fieldset class="gara">
								<div align="center">
									<% tab.printHTMLTable(new java.io.PrintWriter(out)); %>
									<br>
								</div>
							</fieldset>	
									
					</fieldset>			
				<%} %>
		    <%} %>

		
		<%-- PANNELLO DELLE RICHIESTE DI ANNULLAMENTO DELLA SCHEDA --%>
		<%@ include file="../include/RichAnnPanel.jsp" %>
		<%-- --%>
			
 		<table >	
			<tr>
				<td><input <c:out value="${disabled}"/> type="button" value="<utils:message key="button.salva" plain="true" />" onclick="checkAndAction('check',<%= checkNumber %>,'<%=PSBD.ACTION_SALVA %>')"/></td>
				<td><input <c:out value="${noConf}"/>  type="button" value="<utils:message key="button.conferma" plain="true" />" onclick="checkAndAction('check',<%= checkNumber %>,'<%=PSBD.ACTION_CONFERMA %>')"/></td>
				<td><input <c:out value="${disabled}"/> type="button" value="<utils:message key="button.reimposta" plain="true" />" onclick="reimpostaForm('<%=PSBD.ACTION_LOAD %>')"/></td>
				<c:if test="${(!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && datiGara.deleted != true && dati_comuni.confirmed == true && dati_comuni.richAnn ne true && dati_comuni.richDelete ne true && delegaSchede eq false && !dati_comuni.hasAwards)}">
					<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')"/></td>	
				</c:if>
				<c:if test="${!fromAVCPass}">
				<c:if test="${(!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && dati_comuni.okCancellazione eq true && dati_comuni.richAnn ne true && delegaSchede eq false)}">
					<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
				</c:if>
				</c:if>

				<c:set var="statoid" value="${dati_comuni.idStato}"/>
				<c:set var="statoann" value="${dati_comuni.richAnn || dati_comuni.richDelete}"/>
				<c:set var="statodesc" value="${dati_comuni.descrizioneStato}"/>
				<%@ include file="../include/statoscheda.inc" %>
			</tr>
		</table>
		
		 <fieldset>
		 <legend>Dati Comuni Aggiudicazioni</legend>
		 
		 	<c:if test="${fromAVCPass}">
				<p style="color: red">(*) Il campo � protetto perch� validato dal sistema AVCPass</p> 
			</c:if>
		 
		 	<form action="<%=PSBD.SRV_DATI_COMUNI%>" method="post" onkeypress="setFormModified('Modificato')" >
		 		<input type="hidden"  value="load" name="toDo" id="toDo"/>
		 		<input type="hidden" id="Modificato"  value="0">
		 		<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_ID_INFO%>" id="<%=ParametriServlet.FIELD_NAME_ID_INFO%>" value='<c:out value="${dati_comuni.idInfo}"/>' />
				<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO%>" id="<%=ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO%>" value='<c:out value="${dati_comuni.dataInizioInfo}"/>' />
				<input type="hidden" name = "descrizioneStato"  value="<c:out value='${dati_comuni.descrizioneStato}' />" />
				<input type="hidden" name = "idStato"  value="<c:out value='${dati_comuni.idStato}' />" />
				<input type="hidden" name="<%= PSBD.FIELD_NAME_ORIGINE_SCHEDA %>" value="${dati_comuni.origine}"/>
				
		 		<fieldset class="gara">
				  <table width="100%">
				  	<tr>
				  		<th><label >Responsabile del procedimento</label></th>
				  		<td  width="45%" > <strong><c:out value="${datiGara.cfRup}"></c:out></strong></td>
				  	</tr>
				  	<tr>
		   				<td colspan="2"><hr></td>
					</tr>
					<tr>
				  		<th><label >Numero Gara</label></th>
				  		<td  width="45%" >
				  			<c:out value="<%=infG.getIdGara()%>"/>
				  		</td>
				  	</tr>
				  	<tr>
				  		<th><label >Codice di individuazione dell'appalto (CIG)</label></th>
				  		<td  width="45%" >
				  			<c:out value="${datiGara.fullCIG}"/>
				  		</td>
				  	</tr>
				  	<tr>
				  		<th><label >Oggetto dell'appalto</label></th>
				  		<td  > <c:out value="${datiGara.oggettoLotto}"></c:out></td>
				  	</tr>
				  	<tr>
				  		<th><label >Numero di riferimento alla nomenclatura CPV</label></th>
				  		<td > <c:out value="${datiGara.idCPV} -- ${datiGara.descrizioneCPV }"></c:out></td>
				  	</tr>
				  	<tr>
				  		<th><label >Codice Fiscale dell'Amministrazione</label></th>
				  		<td  > <c:out value="${datiGara.cfAmministrazione}" ></c:out></td>
				  	</tr>
				  	<tr>
				  		<th><label >Denominazione dell'Amministrazione</label></th>
				  		<td   > <c:out value="${datiGara.denomAmministrazione}"></c:out></td>
				  	</tr>
				  	<tr>
		   				<td colspan="2"><hr></td>
					</tr>
					<c:set var="categoriaPrevalente" value="${requestScope['categoriaPrevalente']}"></c:set>
				  	<tr>
				  		<th><label >Importo lotto</label></th>
				  		<td>
				  		<c:set var="impLotto" value="${datiGara.importoLotto}" ></c:set>
				  		<% String impLotto1 = ((BigDecimal)pageContext.getAttribute("impLotto")).floatValue() == -1 ? "NON DETERMINATO" : PageHelper.formattaImporto((BigDecimal)pageContext.getAttribute("impLotto"));%>
						<input disabled type="text" value="<%=impLotto1 %>" style="text-align:right;font-weight: bold;width:120px;"/>
				  		</td>
				  	</tr>
				  	<tr>
				  		<th><label >${markFieldAVCPass} Tipo Settore</label></th>
				  		<td>
				  		<select 
				  		   <%if(!disableDisclaimer) { %>
				  			onchange="showHint(
				  							document.getElementById('<%=ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE%>').value
				  						 , document.getElementById('<%=ParametriServlet.FIELD_NAME_TIPO_CONTRATTO%>').value
				  						 , document.getElementById('check5Y').checked
				  						 , document.getElementById('<%=ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE%>').value
				  						 , ${datiGara.dataPubblicazione} 
				  						 , ${datiGara.importoLotto});"
				  			<%} %>
				  			            id="<%=ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE %>" name="<%=ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE %>" style="width: 13em"  
				  			<c:if test="${vReadonlyDelegante || fromAVCPass || hide || dati_comuni.confirmed || (dati_comuni.flagEnteSpeciale eq 'O' && !dati_comuni.onlyAwards) || UTENTE.ossReg || UTENTE.RASA || UTENTE.amministratore}">disabled</c:if> >
				  			<c:set var="flagEnte" value="${dati_comuni.flagEnteSpeciale}" />
				  			<option></option>
				  			<u:options name="listaTipiEnte" scope="request" value="flagEnte"/>  
				  		</select>
			  			<c:if test="${fromAVCPass || vReadonlyDelegante}">
			  				<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE %>" value="${dati_comuni.flagEnteSpeciale}"/>
			  			</c:if>				  		
				  	</td>
				  	</tr>
				  	<tr>
				  		<th><label>${markFieldAVCPass} Oggetto principale del contratto</label></th>
				  		<td> 
				  		<select 
				  		<%if(!disableDisclaimer) { %>
				  			onchange="showHint(
				  							document.getElementById('<%=ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE%>').value
				  						 , document.getElementById('<%=ParametriServlet.FIELD_NAME_TIPO_CONTRATTO%>').value
				  						 , document.getElementById('check5Y').checked
				  						 , document.getElementById('<%=ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE%>').value
				  						 , ${datiGara.dataPubblicazione} 
				  						 , ${datiGara.importoLotto});" 
				  				<% } %>
							id="<%=ParametriServlet.FIELD_NAME_TIPO_CONTRATTO %>" name="<%=ParametriServlet.FIELD_NAME_TIPO_CONTRATTO %>" style="width: 13em" 
				  			<c:if test="${vReadonlyDelegante || fromAVCPass || hide || dati_comuni.confirmed || dati_comuni.hasAwards || UTENTE.ossReg || UTENTE.RASA || UTENTE.amministratore}">disabled</c:if> >			  			
				  			<option></option>
				  			<option value="L" <c:out value="${dati_comuni.tipoContratto =='L' ? 'selected' : ''}" />>Lavori</option>
				  			<option value="S" <c:out value="${dati_comuni.tipoContratto =='S' ? 'selected' : ''}" />>Servizi</option>
				  			<option value="F" <c:out value="${dati_comuni.tipoContratto =='F' ? 'selected' : ''}" />>Forniture</option>
				  		</select>
				  		<c:if test="${fromAVCPass || vReadonlyDelegante}">
				  			<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_TIPO_CONTRATTO %>" value="${dati_comuni.tipoContratto}"/>
				  		</c:if>	
				  	</td>
				  	</tr>
				  	<tr>
						<th><label for="<%= ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE %>">${markFieldAVCPass} Modalita' di realizzazione*</label></th>
						<td>
				  			<c:set var="modoReal" value="${dati_comuni.ID_MODO_REAL}" />
							<select 
							<%if(!disableDisclaimer) { %>
					  			onchange="showHint(
					  							document.getElementById('<%=ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE%>').value
					  						 , document.getElementById('<%=ParametriServlet.FIELD_NAME_TIPO_CONTRATTO%>').value
					  						 , document.getElementById('check5Y').checked
					  						 , document.getElementById('<%=ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE%>').value
					  						 , ${datiGara.dataPubblicazione} 
					  						 , ${datiGara.importoLotto});" 
				  				<%} %>
							id="<%=ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE %>" name="<%=ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE %>" 
							<c:if test="${vReadonlyDelegante || fromAVCPass || hide || dati_comuni.confirmed || (dati_comuni.flagEnteSpeciale eq 'O' && !dati_comuni.onlyAwards) || UTENTE.ossReg || UTENTE.RASA || UTENTE.amministratore}">disabled</c:if> >
				  			<option></option>
				  			<u:options name="listaModiReal" scope="request" value="modoReal"/>  
				  			</select>
							<c:if test="${fromAVCPass || vReadonlyDelegante}">
			  					<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE %>" value="${dati_comuni.ID_MODO_REAL}"/>
			  				</c:if>				  			
						</td>
					</tr>
					<tr style="display:none">
					<th><label>${markFieldAVCPass} Contratto escluso in tutto o in parte dall'ambito di applicazione del codice</label></th>
					<td>
					<c:set var="selSiNo" value="${dati_comuni.FLAG_ESCLUSO}"></c:set>
		  	   	<input 
				  		<%if(SimogFlags.is30233_RFWEBSC00Active()){ %>
				  			onclick="showHint(
				  							document.getElementById('<%=ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE%>').value
				  						 , document.getElementById('<%=ParametriServlet.FIELD_NAME_TIPO_CONTRATTO%>').value
				  						 , document.getElementById('check5Y').checked
				  						 , document.getElementById('<%=ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE%>').value
				  						 , ${datiGara.dataPubblicazione} 
				  						 , ${datiGara.importoLotto});" 
				  		<%} %>	
						<%--TICKET ALM - 3.04.2 2005 --%>
						<%-- <c:if test="${fromAVCPass || hide || dati_comuni.confirmed || (dati_comuni.flagEnteSpeciale eq 'O' && !dati_comuni.onlyAwards) || UTENTE.ossReg || UTENTE.amministratore}">disabled</c:if> --%>
		  	   		    disabled
		  	   		    <%--FINE TICKET ALM - 3.04.2 2005 --%>
		  	   		<c:if test="${selSiNo eq 'S'}">checked="checked"</c:if> id="check5Y" type="radio" name="<%= ParametriServlet.FIELD_NAME_ESCLUSO %>" value="S" />SI
  			   	<input 
				  		<%if(SimogFlags.is30233_RFWEBSC00Active()){ %>
				  			onclick="showHint(
				  							document.getElementById('<%=ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE%>').value
				  						 , document.getElementById('<%=ParametriServlet.FIELD_NAME_TIPO_CONTRATTO%>').value
				  						 , document.getElementById('check5Y').checked
				  						 , document.getElementById('<%=ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE%>').value
				  						 , ${datiGara.dataPubblicazione} 
				  						 , ${datiGara.importoLotto});" 
				  		<%} else {%>	
				  			onclick="getDescrizioneTipoFlusso(${datiGara.importoLotto}, ${datiGara.dataPubblicazione});"
				  		<%} %>	
				  		<%--TICKET ALM - 3.04.2 2005 --%>
						<%-- <c:if test="${fromAVCPass || hide || dati_comuni.confirmed || (dati_comuni.flagEnteSpeciale eq 'O' && !dati_comuni.onlyAwards) || UTENTE.ossReg || UTENTE.amministratore}">disabled</c:if> --%>
  			   		     disabled
		  	   		    <%--FINE TICKET ALM - 3.04.2 2005 --%>
  			   		<c:if test="${selSiNo eq 'N'}">checked="checked"</c:if> id="check5N" type="radio" name="<%= ParametriServlet.FIELD_NAME_ESCLUSO %>" value="N" />NO
  			   		
		  	   		<c:if test="${fromAVCPass}">
			  				<intput type="hidden" name="<%=ParametriServlet.FIELD_NAME_ESCLUSO %>" value="${dati_comuni.FLAG_ESCLUSO}"/>
			  			</c:if>	   			   		
  			   	</td>
				</tr>
			 	<tr style="display:none">
				<th><label for="<%= ParametriServlet.FIELD_NAME_ID_ESCLUSIONE %>">${markFieldAVCPass} Esclusione ai sensi dell'articolo</label></th>
				<td>
		  			<c:set var="idEsclusione" value="${dati_comuni.ID_ESCLUSIONE}" />
					<select name="<%= ParametriServlet.FIELD_NAME_ID_ESCLUSIONE %>" 
					<%--TICKET ALM - 3.04.2 2005 --%>
					<%-- 	<c:if test="${fromAVCPass || hide || dati_comuni.confirmed || (dati_comuni.flagEnteSpeciale eq 'O' && !dati_comuni.onlyAwards) || UTENTE.ossReg || UTENTE.amministratore}">disabled</c:if> --%>
					 disabled
		  	   		 <%--FINE TICKET ALM - 3.04.2 2005 --%>
					>
						<option></option>
						<u:options name="listaArtEsclusione" scope="request" value="idEsclusione" revert="yes"/>
					</select>
	  	   		<c:if test="${fromAVCPass}">
		  				<intput type="hidden" name="<%=ParametriServlet.FIELD_NAME_ID_ESCLUSIONE %>" value="${dati_comuni.ID_ESCLUSIONE}"/>
		  			</c:if>	 					
				</td>
			 </tr>
			 
			 
			 <% if(!disableDisclaimer) { %>
			  	<tr>
	   				<td colspan="2"><hr></td>
				</tr>
	
	
				<tr>
					<td colspan="2" align="center">In base alle informazioni fornite la rilevazione sar� strutturata secondo quanto previsto per i </td>
				</tr>				
				<tr>
					<td colspan="2"><center><h2 style="color:red;" id="tipoFlusso">.</h2></center></td>
				</tr>				
			  	<tr>
	  				<td colspan="2"><hr></td>
				</tr>
			<% } %>

					<tr>
						<td align="center" colspan="2"><p class="detailHelp"><strong>STAZIONE APPALTANTE</strong></p></td>
					</tr>
					<tr>  
	   					<th><label>Codice Fiscale della Stazione Appaltante</label></th>
	   					<td><input readonly="readonly"  name="<%= ParametriServlet.FIELD_NAME_CF_STAZIONE_APPALTANTE%>"  tabindex="<%=++indiceTab%>" <c:out value="${disabled}"/>
							type="text" value="<c:out value='${dati_comuni.cfAmministrazione == null ? datiGara.cfAmministrazione : dati_comuni.cfAmministrazione}'/>"   maxlength="11"/></td>
	   				</tr>
	   				<tr>
	   					<th><label>Denom.ne della Stazione Appaltante competente</label></th>
	   					<td><input readonly="readonly" style="width:100%"  name="<%= ParametriServlet.FIELD_NAME_DENOM_STAZIONE_APPALTANTE%>"  tabindex="<%=++indiceTab%>" <c:out value="${disabled}"/>
							type="text" value="<c:out value='${dati_comuni.denAmministrazione == null ? datiGara.denomAmministrazione : dati_comuni.denAmministrazione}'/>"  /></td>
	   				</tr>
	   				<tr>
	   					<th><label>Codice univoco centro di costo</label></th>
	   					<td><input readonly="readonly" style="width:100%"  name="<%= ParametriServlet.FIELD_NAME_CODICE_CC%>"  tabindex="<%=++indiceTab%>" <c:out value="${disabled}"/>
							type="text" value="<c:out value='${dati_comuni.codiceCC == null ? datiGara.cfSA : dati_comuni.codiceCC}'/>"   maxlength="50"/></td>
	   				</tr>
	   				<tr>
	   					<th><label>Denominazione del centro di costo nell'ambito della Stazione Appaltante</label></th>
	   					<td><input readonly="readonly" maxlength="50" style="width:100%"  name="<%= ParametriServlet.FIELD_NAME_DENOM_CC%>"  tabindex="<%=++indiceTab%>" <c:out value="${disabled}"/>
							type="text" value="<c:out value='${dati_comuni.denomCC == null ? datiGara.denomSA : dati_comuni.denomCC}'/>" maxlength="50"/></td>
	   				</tr>
<!--	   			<tr>-->
<!--				  		<th><label >Categoria Stazione Appaltante</label></th>-->
<!--				  		<td  > -->
<!--				  		<select  name="<%=ParametriServlet.FIELD_NAME_ID_CATEG_SA %>" style="width: 100%" <c:out value="${disabled}"></c:out> >-->
<!--				  			<option></option>-->
<!--				  			<c:set var="categSA" value="${dati_comuni.idCategSa}" scope="request" />-->
<!--				  			<u:options name="listaCategorieSA" scope="request" value="categSA"/>  -->
<!--				  		</select>-->
<!--				  		</td>-->
<!--				  	</tr>-->
						<!--PP hidden su richiesta di Obino 28.10.2009-->
						<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_ID_CATEG_SA %>" value="${dati_comuni.idCategSa}" />
				  	
				  	<% if(!disable3044flag) { %>
				  	<tr>
						<th><label >La stazione appaltante agisce per conto di altro soggetto singolo?*</label></th>
				  		<td width="40%" > 
				  			<input <%= disabled3044 %> onchange="chComboStateOnFieldChange('tipologiaSA','check4Y');chComboStateOnFieldChange('tipologiaProcedura','check4Y');chElemStateOnRadioChange(['cfSoggettoA','denSoggettoA','<%= ParametriServlet.S_FIELD_NAME_FLAG_PROCEDE_STIPULA%>','<%= ParametriServlet.N_FIELD_NAME_FLAG_PROCEDE_STIPULA%>'],'check4Y')"  tabindex="<%=++indiceTab%>" id="check4Y" type="radio" name="<%= ParametriServlet.FIELD_NAME_FLAG_SA_AGENTE %>" value="S" <c:out value="${dati_comuni.flagSAAgente == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/>/>SI 
	   					<input <%= disabled3044 %> onchange="chComboStateOnFieldChange('tipologiaSA','check4Y');chComboStateOnFieldChange('tipologiaProcedura','check4Y');chElemStateOnRadioChange(['cfSoggettoA','denSoggettoA','<%= ParametriServlet.S_FIELD_NAME_FLAG_PROCEDE_STIPULA%>','<%= ParametriServlet.N_FIELD_NAME_FLAG_PROCEDE_STIPULA%>'],'check4Y')" tabindex="<%=++indiceTab%>" id="check4N" type="radio" name="<%= ParametriServlet.FIELD_NAME_FLAG_SA_AGENTE %>" value="N" <%= disabled3044.equals("disabled") ? "checked" : ""  %> <c:out value="${dati_comuni.flagSAAgente == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/>/>NO
	   					</td>
				  	</tr>
				  	
				  	


				  	<tr>
				  		<th><label >Tipologia della SA (nel caso agisca per conto di altro/i soggetto/i)</label></th>
				  		<td  > 
				  		<select disabled id="tipologiaSA" name="<%=ParametriServlet.FIELD_NAME_ID_TIPO_SA %>" style="width: 100%" <c:out value="${disabled}"/> >
				  			<option></option>
				  			<c:set var="tipologSA" value="${dati_comuni.tipologiaSA}" scope="request" />
				  			<u:options name="listaTipologieSA" scope="request" value="tipologSA"/>  
				  		</select>
				  		</td>
				  	</tr>	               	  	 	
				  	<%--gm  nuovi campi dati comuni--%>
				  	<tr>
				  		<th><label >Tipologia procedura (nel caso agisca per conto di altro/i soggetto/i)</label></th>
				  		<td  > 
				  		<select disabled id="tipologiaProcedura" name="<%=ParametriServlet.FIELD_NAME_ID_TIPO_PROCEDURA %>" style="width: 100%" <c:out value="${disabled}"/> >
				  			<option></option>
				  			<c:set var="tipologProc" value="${dati_comuni.tipologiaProcedura}" scope="request" />
				  			<u:options name="listaTipologieProcedura" scope="request" value="tipologProc"/>  
				  		</select>
				  		</td>
				  	</tr>
				  	<% } %>
				  	<%	 if(dataCreazione.compareTo(String.valueOf(SimogProperties.getInstance().getDataAttivazione3043())) < 0) { %>	
					  	<tr>
			           <th><label>Durata della convenzione o accordo quadro in giorni</label></th>
			           <td>
				          <input id="durataConvenzione" name="<%= ParametriServlet.FIELD_NAME_DURATA_CONVENZIONE%>" 
				          tabindex="<%=++indiceTab%>" maxlength="9" onblur="validateNumber(this)" 
				          style="text-align: right" type="text" value="<c:out value="${dati_comuni.durataConvenzione}" />" 
				          <c:out value="${disabled}" /> onchange="setFormModified('Modificato0')">
			           </td>
		            </tr>
	            <% } %>
	            <% if(!disable3044flag) { %>
				  	<tr>
		           <th><label for="<%= ParametriServlet.FIELD_NAME_FLAG_PROCEDE_STIPULA %>">La centrale di committenza procede alla stipula? </label></th>
		           <td>

			          <input disabled onchange="setFormModified('Modificato0')" 
					      tabindex="<%//=++indiceTab%>" id="<%= ParametriServlet.S_FIELD_NAME_FLAG_PROCEDE_STIPULA%>" 
					      type="radio" name="<%= ParametriServlet.FIELD_NAME_FLAG_PROCEDE_STIPULA%>" 
					      value="S" <c:out value="${dati_comuni.flagProcedeStipula == 'S' ? 'checked' : ''}" /> 
					      <c:out value="${disabled}"/>/>SI 
	  			       <input disabled onchange="setFormModified('Modificato0')" 
	  					   tabindex="<%//=++indiceTab%>" id="<%= ParametriServlet.N_FIELD_NAME_FLAG_PROCEDE_STIPULA %>" 
	  					   type="radio" name="<%= ParametriServlet.FIELD_NAME_FLAG_PROCEDE_STIPULA%>" 
	  					   value="N" <c:out value="${dati_comuni.flagProcedeStipula == 'N' ? 'checked' : ''}" /> 
	  					   <c:out value="${disabled}"/>/>NO
		           </td>
	            </tr>	
          

				  	<%--gm  fine nuovi campi dati comuni--%>
				  	<tr>
	   					<th><label>Codice fiscale soggetto per conto del quale agisce la S.A.</label></th>
	   					<td><input <%= disabled3044 %> id="cfSoggettoA"   name="<%= ParametriServlet.FIELD_NAME_CF_AMM_AGENTE%>"  tabindex="<%=++indiceTab%>" <c:out value="${disabled}"/> 
							type="text" value="<c:out value='${dati_comuni.cfAmmAgente}'/>" maxlength="20"/></td>
	   				</tr>

				   	<tr>
	   					<th><label>Denominazione dell'Amministrazione per la quale agisce la S.A.</label></th>
	   					<td><input  <%= disabled3044 %> id="denSoggettoA"  maxlength="50" style="width:100%"  name="<%= ParametriServlet.FIELD_NAME_DEN_AMM_AGENTE%>"  tabindex="<%=++indiceTab%>" 
							type="text" value="<c:out value='${dati_comuni.denAmmAgente}'/>" <c:out value="${disabled}"/> /></td>
	   				</tr>
	   				<%} %>

				  	<tr>
		     			<td colspan="2" align="center"><p class="detailHelp"><strong>PUBBLICITA' DELL'APPALTO</strong></p></td>
					</tr>
					<c:set var="pubblicazione" value="${dati_comuni.pubblicazione}" scope="page"></c:set>
					<c:set var="pubblicita" value="${datiGara.idPubblicazione ge 1}"></c:set>
					<c:set var="confermata" value="${dati_comuni.confirmed}"></c:set>
					<c:set var="pubbNonModificabileDatiComuni" value="${rupOk eq false || !(pubblicita==false and confermata==false) || delegaSchede eq true}"></c:set>
					<%@ include file="include/datiPubblicazione.jsp" %>
					
					<tr>
		     			<td colspan="2" align="center"><p class="detailHelp"><strong>STATO ATTUALE</strong></p></td>
					</tr>
					<tr>
						<th><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_EsitoProcedura") %>>${markFieldAVCPass} Esito della procedura*</label></th>
						<td>
							<select name="<%=ParametriServlet.FIELD_NAME_ESITO_PROCEDURA %>" <c:out value="${disabled}"/> <c:out value="${disabledAVCPass}"/> >
								<option/>
								<c:set var="esito" value="${dati_comuni.esitoProcedura}" scope="request" />
								<u:options name="listaEsitiProcedura" scope="request" value="esito"/>
							</select>
							<c:if test="${fromAVCPass}">
								<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_ESITO_PROCEDURA %>" value="${dati_comuni.esitoProcedura}"/>
							</c:if>
						</td>
					</tr>
		
				  </table>
				</fieldset>
		 		<table>	
					<tr>
					   <input type="hidden" name="checkIfOK" id="checkIfOK" value="<%=new Integer(session.getAttribute(ParametriServlet.checkIfOK).toString()) + 1%>" />
						<td><input <c:out value="${disabled}"/> type="button" value="Salva" onclick="checkAndAction('check',<%= checkNumber %>,'<%=PSBD.ACTION_SALVA %>')"/></td>
						<td><input <c:out value="${noConf}"/>  type="button" value="Conferma" onclick="checkAndAction('check',<%= checkNumber %>,'<%=PSBD.ACTION_CONFERMA %>')"/></td>
						<td><input <c:out value="${disabled}"/> type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_LOAD %>')"/></td>
						<c:if test="${(!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && datiGara.deleted != true && dati_comuni.confirmed == true && dati_comuni.richAnn ne true && dati_comuni.richDelete ne true && delegaSchede eq false && !dati_comuni.hasAwards)}">
							<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')"/></td>	
						</c:if>
						<c:if test="${!fromAVCPass}">
						<c:if test="${(!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && dati_comuni.okCancellazione eq true && dati_comuni.richAnn ne true && delegaSchede eq false)}">
							<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
						</c:if>
						</c:if>
						<%@ include file="../include/statoscheda.inc" %>
					</tr>
				</table>

		  		<c:if test="${dati_comuni.idInfo > 0 }">
		  			<input type="hidden"  name="<%=ParametriServlet.FIELD_NAME_TIPO_CONTRATTO %>" value="${dati_comuni.tipoContratto}" />
		  			<input type="hidden"  name="<%=ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE %>" value="${dati_comuni.flagEnteSpeciale}" />
		  			<input type="hidden"  name="<%=ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE %>" value="${dati_comuni.ID_MODO_REAL}" />
		  			<input type="hidden"  name="<%=ParametriServlet.FIELD_NAME_ESCLUSO %>" value="${dati_comuni.FLAG_ESCLUSO}" />
		  			<input type="hidden"  name="<%=ParametriServlet.FIELD_NAME_ID_ESCLUSIONE %>" value="${dati_comuni.ID_ESCLUSIONE}" />
		  		</c:if>

		 	</form>
		 
		 </fieldset>		 
		</div>  
		
		<%@ include file="include/newfooter.inc" %>
	</div>
	    <c:if test="${hide eq false}">
			 <script type="text/javascript">
			 chComboStateOnFieldChange('tipologiaSA','check4Y');
			 chComboStateOnFieldChange('tipologiaProcedura','check4Y');
			 chElemStateOnRadioChange(['cfSoggettoA','denSoggettoA','<%= ParametriServlet.S_FIELD_NAME_FLAG_PROCEDE_STIPULA%>','<%= ParametriServlet.N_FIELD_NAME_FLAG_PROCEDE_STIPULA%>'],'check4Y')	 	
			 </script>	
		 </c:if>
<script type="text/javascript">
	// valorizzazione iniziale tipo flusso
	<%if(SimogFlags.is30233_RFWEBSC00Active()){ %>
		showHint(document.getElementById('<%=ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE%>').value
				 , document.getElementById('<%=ParametriServlet.FIELD_NAME_TIPO_CONTRATTO%>').value
				 , document.getElementById('check5Y').checked
				 , document.getElementById('<%=ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE%>').value
				 , ${datiGara.dataPubblicazione} 
				 , ${datiGara.importoLotto}); 
  	<%} else {%>	
 		getDescrizioneTipoFlusso(${datiGara.importoLotto}, ${datiGara.dataPubblicazione});
  	<%} %>	
</script>
</body>

<%@page import="it.avlp.simog.validatore.SimogValidator"%>
<%@page import="it.avlp.simog.db.advanced.TableBean"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="it.avlp.simog.beans.IdentificativoSchede"%>
<%@page import="it.avlp.simog.beans.InfoGaraBean"%>
</html>
<% } catch(Exception e){e.printStackTrace();} %>