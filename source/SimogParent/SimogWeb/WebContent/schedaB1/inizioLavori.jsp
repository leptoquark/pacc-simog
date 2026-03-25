<% try { %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="../errore.jsp" %>
<%@ include file="../include/newbasicHeader.inc" %>
<%@ include file="../include/controlloSessione.inc" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

<%@ page import="it.avlp.simog.beans.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="java.util.HashMap"%>
<% int indiceTab = 0; %>
<%@page import="it.avlp.simog.util.PageHelper"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.util.SimogProperties"%>

<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="theme/tabmenu.css"/>
<!-- calendar stylesheet -->
<link rel="stylesheet" type="text/css" media="all" href="calendar/calendar-blue.css" title="win2k-cold-1" />
<!-- main calendar program -->
<script type="text/javascript" src="calendar/calendar.js"></script>
<!-- language for the calendar -->
<%@ include file="../include/calendar-dynamic.inc" %>
<script type="text/javascript" src="calendar/calendar-setup.js"></script>
<script type="text/javascript" src="script/pageutils.js"></script>
<%@ include file="../include/i18n-init.inc" %>
<%@ include file="/script/script.js" %>
<%@ include file="/script/domUtils.js" %>

<c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>
<jsp:useBean id="schedaLavori" type="it.avlp.simog.beans.inizio.SchedaInizioLavori" class="it.avlp.simog.beans.inizio.SchedaInizioLavori" scope="request"></jsp:useBean>

<jsp:useBean id="inizioLavori" type="it.avlp.simog.beans.inizio.InizioLavoriBean" class="it.avlp.simog.beans.inizio.InizioLavoriBean" scope="request"></jsp:useBean>
<c:set var="inizioLavori" value="${schedaLavori.datiInizio}"></c:set>
<c:set var="rupOk" value="${(UTENTE.login eq datiGara.cfRup or datiGara.cfRup eq null ) and schedaLavori.aggiudicazione.flagAggiudPrincipale ne 'N'}" />
<c:set var="roByFlusso" value="${datiGara.deleted or rupOk eq false or UTENTE.ossReg eq true or UTENTE.RASA eq true or schedaLavori.delegaScheda eq true  or schedaLavori.riaggiudicata eq true or  schedaLavori.readOnly eq true}" />
<c:set var="readonly" value="${roByFlusso or inizioLavori.confirmed }" />
<c:set var="disabledStr" value="${readonly ? 'disabled':'' }" />
<c:set var="readonlyStr" value="${readonly? 'readonly' : ''}" />
<c:set var="saveAndResetDisabledStr" value="${ (roByFlusso or inizioLavori.confirmed) ? 'disabled' : ''  }"/>
<c:set var="noConf" value="${roByFlusso eq true or (inizioLavori.idInizioLavori le 0) or inizioLavori.richAnn eq true  or inizioLavori.confirmed}" />
<c:set var="annullabile" value="${ roByFlusso ne true and inizioLavori.confirmed eq true and inizioLavori.richAnn ne true and inizioLavori.richDelete ne true  and variazioniAnagrafiche ne true}" />
<c:set var="cancellabile" value="${ roByFlusso ne true and inizioLavori.okCancellazione eq true  and variazioniAnagrafiche ne true}" />
<c:set var="variazAnagraf" value="${(schedaLavori.varAnagActive eq true and  roByFlusso ne true and inizioLavori.confirmed eq true and variazioniAnagrafiche ne true )}" />

<c:set var="aggiudicazione" value="${schedaLavori.aggiudicazione}" scope="page"></c:set>
 
 <%


it.avlp.simog.beans.MessageBean messBean = (it.avlp.simog.beans.MessageBean) request.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.ERRORBEAN);
it.avlp.simog.beans.AllValidationBeans beanErr = null;
HashMap<String, String> fieldToHighlight = new HashMap<String,String>();
if ( messBean != null ) 
if ( messBean instanceof it.avlp.simog.beans.AllValidationBeans ){
beanErr = (it.avlp.simog.beans.AllValidationBeans) request.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.ERRORBEAN);
fieldToHighlight = beanErr.getFieldToHighlight();
}
%>

<c:set var="saveAction" value="<%=PSBD.ACTION_SALVA %>"/>
    <c:if test="${variazioniAnagrafiche eq true}">
    	 <c:set var="saveAction" value="<%=PSBD.ACTION_VARIAZIONI_ANAGRAFICHE %>"/>
</c:if>
 
	<title><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.faseIniziale" /> - <%= user.getProfilo() %></title>
	</head>
	<body>
		<div id="gabbia">
		<%if(request.getAttribute("protect")!= null) {%>
		<%@ include file="/include/protect.inc" %>
		<%} %>
			<%@ include file="../include/header.inc" %>			
			<div class="bodypage-e">
				
				<form id="FormInizioLavori" name="gestioneTab" action="<%= ParametriServletInizioLavori.SRV_INIZIO_LAVORI %>" method="post" onkeypress="setFormModified('Modificato0')">
					<h1><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.faseIniziale" /></h1>
					<input type="hidden"  value="load" name="toDo" id="toDo"/>
					
					<input type="hidden" name = "<%=ParametriServletInizioLavori.ID_INIZIO_LAVORI %>"  value="${schedaLavori.datiInizio.idInizioLavori}" />
			        <input type="hidden" name = "<%=ParametriServletInizioLavori.DATA_INIZIO_LAVORI %>"  value="${schedaLavori.datiInizio.dataInizioLavori}" />
		
			     	<input type="hidden" name = "descrizioneStato"  value="<c:out value='${inizioLavori.descrizioneStato}' />" />
			    	<input type="hidden" name = "idStato"  value="<c:out value='${inizioLavori.idStato}' />" />
					<input type="hidden" id="Modificato" name="Modificato" value="${param['modificato']}">
					<input type="hidden" id="Modificato0" name="Modificato0" value="${param['modificato0']}">
					<input type="hidden" name="<%=PSBD.TAB%>" id="<%=PSBD.TAB%>" value="">	
					<input type="hidden" name="<%=PSBD.ACTION_TYPE%>" id="<%=PSBD.ACTION_TYPE%>" value="">
					<input type="hidden" name="<%=PSBD.VAR_ANN%>"  value="${variazioniAnagrafiche}" />
										
					
					<div  class="hmenu">	
							<ul> 
								<%-- 
								<li><a title="Mostra Dati Comuni" href="javascript:changePage('<%= PSBD.SRV_DATI_COMUNI %>','Modificato')">Mostra Dati Comuni</a></li>
								--%>
								<%String riScheda =ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=";%>
								
			    				
								<li><a title="Torna alla lista Aggiudicazioni" href="javascript:changePage('<%=riScheda%><c:out value="${datiGara.idLotto}" />','Modificato')">Lista Aggiudicazioni</a></li>			    
			 					
								    
							</ul>  					
						</div>
						<%@ include file="/include/gestisciErrore.inc" %>
						
						<%-- PANNELLO DELLE RICHIESTE DI ANNULLAMENTO DELLA SCHEDA [DISATTIVATO] --%>
						<%@ include file="../include/RichAnnPanel.jsp" %>
						<%@ include file="../include/VarAnagPanel.jsp" %>
						<%-- --%>
						
				<fieldset>
				<table>	
				
					<tr>
					    
					    
						<td><input ${variazioniAnagrafiche eq true ? '' : saveAndResetDisabledStr} type="button" value="Salva" onclick="creaHidden('FormInizioLavori','<%=PSBD.RESPONSABILE %>');creaHidden('FormInizioLavori','<%=ParametriServletInizioLavori.POSIZIONE_AGGIUDICATARIO %>');checkAndAction('check',5,'${saveAction}')"></td>
						<td><input ${noConf eq true ? 'disabled' : ''} type="button" value="Conferma" onclick="creaHidden('FormInizioLavori','<%=PSBD.RESPONSABILE %>');creaHidden('FormInizioLavori','<%=ParametriServletInizioLavori.POSIZIONE_AGGIUDICATARIO %>');checkAndAction('check',5,'<%=PSBD.ACTION_CONFERMA %>')"></td>
						<td><input ${variazioniAnagrafiche eq true ? '' : saveAndResetDisabledStr} type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_LOAD %>')"></td>
						<c:if test="${annullabile}">
							<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="loadAnnullamentJSP('<%= ParametriServletInizioLavori.TAB_INIZIO_LAVORI %>')"></td>
						</c:if>
						
						<!-- MAD 58518 Controllo che l'utente loggato abbia la stessa Amm della gara in modo che un RUP diverso ma della stessa amm possa gestire le schede anche senza presa in carico 
	USARLO IN CASO CONTINUINO AD ARRIVARE TICKET DI RUP CHE NON RIESCONO A GESTIRE SCHEDE DI UN ALTRO RUP DELLO STESSO CENTRO DI COSTO
	e aggiungere questo controllo anche nelle jsp delle altre schede come fatto nella scheda fase iniziale inizioLavori.jsp -->
	<%-- <% String cfAmmGara = "${datiGara.cfAmministrazione}";
						 
						boolean saDeleganteLogged=false;
							Hashtable collaborazioni = user.getAmministrazioniByProfilo(it.avlp.simog.beans.ProfiloEnum.RUP);
							java.util.Enumeration<String> listaCollaborazioni = collaborazioni.keys();
				    	   
				    	     while(listaCollaborazioni.hasMoreElements()) {	
				                 String key = listaCollaborazioni.nextElement(); %>
				                 <c:set var="cfAmmUtente" value="<%=key%>"/>
				                  <c:set var="isSameAmm" value="false"/>
				                 <c:if test="${cfAmmUtente eq datiGara.cfAmministrazione}">
								    	 <c:set var="isSameAmm" value="true"/>
								</c:if>
				                 <% 
				                 
							}
				    	     %> --%>
				    	     
				    	     <%-- <c:if test="${cancellabile or isSameAmm = 'true'}"> --%>
				    	    <!--  FINE MAD 58518 -->
				    	    
						<c:if test="${cancellabile}">
							<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
						</c:if>
						<c:if test="${variazAnagraf}">
							<td><input type="button" value="Comunica Variazioni Anagrafiche" onclick="doAction('<%=PSBD.ACTION_VARIAZIONI_ANAGRAFICHE %>')"/></td>	
						</c:if>
						<c:set var="statoid" value="${inizioLavori.idStato}"/>
						<c:set var="statoann" value="${inizioLavori.richAnn || inizioLavori.richDelete}"/>
						<c:set var="statodesc" value="${inizioLavori.descrizioneStato}"/>
						<%@ include file="../include/statoscheda.inc" %>						
					</tr>
				</table>
				<fieldset class="gara">
				<table width="100%">
						<tr>
							<td align="center" colspan="2"><p class="detailHelp"><strong>RIFERIMENTO AI DATI DELLA FASE DI AGGIUDICAZIONE O DI DEFINIZIONE DI PROCEDURA NEGOZIATA</strong></p></td>
						</tr>
						  <%@include file="/include/intestazione.jsp" %>
			   <table width="100%"  ${variazioniAnagrafiche eq true ? 'style="display:none;"' : ''}>
						<colgroup>
							<col width="60%"/>
							<col width="40%"/>
						</colgroup>
						  <c:if test="${aggiudicazione.hasDatiEconomici}"> 
							  <tr>
			     			      <td colspan="2" align="center"><p class="detailHelp"><strong>DATI ECONOMICI CONTRATTO MULTILOTTO</strong></p></td>
							  </tr>
							   <c:set var="datiEconomici" value="${aggiudicazione.datiEconomici}" scope="page"></c:set>
							  <%@ include file="/include/datiEconomici.jsp" %>
						  </c:if>
						  <tr>
		     			      <td colspan="2" align="center"><p class="detailHelp"><strong>PUBBLICAZIONE ESITO PROCEDURA DI SELEZIONE</strong></p></td>
						  </tr>
						   <c:set var="pubblicazione" value="${inizioLavori.pubblicazione}" scope="page"></c:set>
						  <%@ include file="/include/datiPubblicazione.jsp" %>
						  <tr>
		     			      <td colspan="2" align="center"><p class="detailHelp"><strong>CONTRATTO DI APPALTO</strong></p></td>
						  </tr>
						  <tr>
						    <td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataStipula") %>>Data stipula contratto</label></td>
				 			<td>
								<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  ${readonlyStr}
									type="text" id="inputDataStipula" name="<%=  ParametriServletInizioLavori.FIELD_NAME_DATA_STIPULA %>" 
									onblur="Calendar.validaData(this)" value="<c:out value='${inizioLavori.dataStipula}'/>">
								<c:if test="${readonly ne true}">
									<img src="calendar/img.gif" id="calendarDataStipula" style="cursor: pointer; border: 1px solid red;" title="Date selector"
												onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									<script type="text/javascript">
										    Calendar.setup({
									        inputField     :    "inputDataStipula",     // id of the input field
									        ifFormat       :    "%d/%m/%Y",      // format of the input field
									        button         :    "calendarDataStipula",  // trigger for the calendar (button ID)
									        align          :    "Tl",           // alignment (defaults to "Bl")
									        singleClick    :    true							       
								    		});					    	
									</script>
								</c:if>
							 </td>
					     </tr>
					      <tr>
						    <td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataEsecutivita") %>>Data esecutivita' contratto (ove previsto)</label></td>
				 			<td>
								<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')" ${readonlyStr}
									type="text" id="inputDataEsecutivita" name="<%=  ParametriServletInizioLavori.FIELD_NAME_DATA_ESECUTIVITA %>" 
									onblur="Calendar.validaData(this)" value="<c:out value='${inizioLavori.dataEsecutivita}'/>">
								<c:if test="${readonly ne true}">
									<img src="calendar/img.gif" id="calendarDataEsecutivita" style="cursor: pointer; border: 1px solid red;" title="Date selector"
												onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									<script type="text/javascript">
										    Calendar.setup({
									        inputField     :    "inputDataEsecutivita",     // id of the input field
									        ifFormat       :    "%d/%m/%Y",      // format of the input field
									        button         :    "calendarDataEsecutivita",  // trigger for the calendar (button ID)
									        align          :    "Tl",           // alignment (defaults to "Bl")
									        singleClick    :    true							       
								    		});					    	
									</script>
								</c:if>
							 </td>
					     </tr>
					     
					     <tr>
							<th><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_ImportoCauzione") %>>Importo cauzione definitiva in &#8364;* </label></th>
							<td>
								<input onchange="setFormModified('Modificato0')" tabindex="<%=++indiceTab%>" ${readonlyStr}  
								type="text" style="text-align:right;" id="importoCauzione" name="<%= ParametriServletInizioLavori.FIELD_NAME_IMPORTO_CAUZIONE %>" value="<c:out value="${inizioLavori.importoCauzioneStr}" />" onblur="validateAmount(this);" />
							</td>
						</tr>
						<tr>
		     			      <td colspan="2" align="center"><p class="detailHelp"><strong>TERMINI DI ESECUZIONE</strong></p></td>
						  </tr>
						  <tr>
						    <td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataInizioProgEsecutiva") %>>Data disposizione dell'inizio della prog. Esecutiva</label></td>
				 			<td>
								<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  ${readonlyStr}
									type="text" id="inputDataInizioPE" name="<%=  ParametriServletInizioLavori.FIELD_NAME_DATA_INI_PROG_ESEC %>" 
									onblur="Calendar.validaData(this)" value="<c:out value='${inizioLavori.dataIniProgEsec}'/>">
								<c:if test="${readonly ne true}">
									<img src="calendar/img.gif" id="calendarDataInizioPE" style="cursor: pointer; border: 1px solid red;" title="Date selector"
												onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									<script type="text/javascript">
										    Calendar.setup({
									        inputField     :    "inputDataInizioPE",     // id of the input field
									        ifFormat       :    "%d/%m/%Y",      // format of the input field
									        button         :    "calendarDataInizioPE",  // trigger for the calendar (button ID)
									        align          :    "Tl",           // alignment (defaults to "Bl")
									        singleClick    :    true							       
								    		});					    	
									</script>
								</c:if>
							 </td>
					     </tr>
					      <tr>
						    <td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataApprovazioneEsecutiva") %>>Data di approvazione del progetto esecutivo</label></td>
				 			<td>
								<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  ${readonlyStr}
									type="text" id="inputDataAppPE" name="<%=  ParametriServletInizioLavori.FIELD_NAME_DATA_APP_PROG_ESEC %>" 
									onblur="Calendar.validaData(this)" value="<c:out value='${inizioLavori.dataAppProgEsec}'/>">
								<c:if test="${readonly ne true}">
									<img src="calendar/img.gif" id="calendarDataAppPE" style="cursor: pointer; border: 1px solid red;" title="Date selector"
												onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									<script type="text/javascript">
										    Calendar.setup({
									        inputField     :    "inputDataAppPE",     // id of the input field
									        ifFormat       :    "%d/%m/%Y",      // format of the input field
									        button         :    "calendarDataAppPE",  // trigger for the calendar (button ID)
									        align          :    "Tl",           // alignment (defaults to "Bl")
									        singleClick    :    true							       
								    		});					    	
									</script>
								</c:if>
							 </td>
					     </tr>
					     <tr>
					        <c:choose>
					        	<c:when test="${datiGara.tipoContratto eq 'L'}">
									<th><label >Consegna frazionata*</label></th>
								</c:when>
								<c:otherwise><th><label >L'avvio dell'esecuzione del contratto e' per fasi*</label></th></c:otherwise>
							</c:choose>
					  		<td> 
					  		   <u:selectBooleanRadio name="<%= ParametriServletInizioLavori.FIELD_NAME_CONSEGNA_FRAZIONATA%>" 
			   				    value="${inizioLavori.flagFrazionata}" trueId="check4Y" 
			                    falseId="check4N" readonly="${readonly}" 
			                    tabindex="<%=++indiceTab%>" />
			                    <%indiceTab++; %>
			  				</td>
						</tr>
						 <tr>
						    <td>
<!--  <label >Data verbale prima consegna lavori (in caso di consegna frazionata)</label> -->
<label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataVerbaleConsegnaLavori") %>>
<c:choose>
	<c:when test="${datiGara.tipoContratto == 'S' || datiGara.tipoContratto == 'F' }">Data verbale di avvio della prima fase dell'esecuzione del contratto</c:when>
	<c:when test="${datiGara.tipoContratto == 'L' }">Data verbale prima consegna lavori (in caso di consegna frazionata)</c:when>
</c:choose>
</label>
</td>
		 			<td>
						<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')" ${readonlyStr}
							type="text" id="inputDataVerbPC" name="<%=  ParametriServletInizioLavori.FIELD_NAME_DATA_VERB_PRIMA_CONSEGNA %>" 
							onblur="Calendar.validaData(this)" value="<c:out value='${inizioLavori.dataVerbaleCons}'/>">
						<c:if test="${readonly ne true}">
							<img src="calendar/img.gif" id="calendarDataVerbPC" style="cursor: pointer; border: 1px solid red;" title="Date selector"
										onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
							<script type="text/javascript">
								    Calendar.setup({
							        inputField     :    "inputDataVerbPC",     // id of the input field
							        ifFormat       :    "%d/%m/%Y",      // format of the input field
							        button         :    "calendarDataVerbPC",  // trigger for the calendar (button ID)
							        align          :    "Tl",           // alignment (defaults to "Bl")
							        singleClick    :    true							       
						    		});					    	
							</script>
						</c:if>
					 </td>
			     </tr>
			      <tr>
				    <td>					    
<label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataVerbaleAvvioEsecuzioneLavori") %>>
<c:choose>
	<c:when test="${datiGara.tipoContratto == 'S' || datiGara.tipoContratto == 'F' }">Data verbale di avvio dell'esecuzione del contratto</c:when>
	<c:when test="${datiGara.tipoContratto == 'L' }">Data verbale consegna definitiva</c:when>
</c:choose>
</label>
						</td>
			 			<td>
							<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  ${readonlyStr}
								type="text" id="inputDataVerbCD" name="<%=  ParametriServletInizioLavori.FIELD_NAME_DATA_VERB_CONSEGNA_DEF %>" 
								onblur="Calendar.validaData(this)" value="<c:out value='${inizioLavori.dataVerbaleDef}'/>">
							<c:if test="${readonly ne true}">
								<img src="calendar/img.gif" id="calendarDataVerbCD" style="cursor: pointer; border: 1px solid red;" title="Date selector"
											onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
								<script type="text/javascript">
									    Calendar.setup({
								        inputField     :    "inputDataVerbCD",     // id of the input field
								        ifFormat       :    "%d/%m/%Y",      // format of the input field
								        button         :    "calendarDataVerbCD",  // trigger for the calendar (button ID)
								        align          :    "Tl",           // alignment (defaults to "Bl")
								        singleClick    :    true							       
							    		});					    	
								</script>
							</c:if>
						 </td>
				     </tr>
					     <tr>
							<th><label >Consegna sotto riserva di legge?*</label></th>
					  		<td> 
					  		    <u:selectBooleanRadio name="<%= ParametriServletInizioLavori.FIELD_NAME_CONSEGNA_RISERVA%>" 
			   				    value="${inizioLavori.flagRiserva}" trueId="check5Y" 
			                    falseId="check5N" readonly="${readonly}" 
			                    tabindex="<%=++indiceTab%>" />
			                    <%indiceTab++; %>
			  				</td>
						</tr>
						
						<tr>
						    <td><label >Data di effettivo inizio lavori/servizi/forniture*</label></td>
				 			<td>
								<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  ${readonlyStr}
									type="text" id="inputDataInizioLFS" name="<%=  ParametriServletInizioLavori.FIELD_NAME_DATA_VERB_INIZIO %>" 
									onblur="Calendar.validaData(this)" value="<c:out value='${inizioLavori.dataVerbaleInizio}'/>">
								<c:if test="${readonly ne true}">
									<img src="calendar/img.gif" id="calendarDataInizioLFS" style="cursor: pointer; border: 1px solid red;" title="Date selector"
												onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									<script type="text/javascript">
										    Calendar.setup({
									        inputField     :    "inputDataInizioLFS",     // id of the input field
									        ifFormat       :    "%d/%m/%Y",      // format of the input field
									        button         :    "calendarDataInizioLFS",  // trigger for the calendar (button ID)
									        align          :    "Tl",           // alignment (defaults to "Bl")
									        singleClick    :    true							       
								    		});					    	
									</script>
								</c:if>
							 </td>
					     </tr>
					      <tr>
					         <!-- Ticket ALM #656 -->
						    <td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataFinePrevistaUltimazione") %>>Data fine prevista per dare ultimazione ai lavori/servizi/forniture*</label></td>
						     <!-- Fine Ticket ALM #656 -->
				 			<td>
								<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"   ${readonlyStr}
									type="text" id="inputDataFineLFS" name="<%=  ParametriServletInizioLavori.FIELD_NAME_DATA_TERMINE %>" 
									onblur="Calendar.validaData(this)" value="<c:out value='${inizioLavori.dataTermine}'/>">
								<c:if test="${readonly ne true}">
									<img src="calendar/img.gif" id="calendarDataFineLFS" style="cursor: pointer; border: 1px solid red;" title="Date selector"
												onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									<script type="text/javascript">
										    Calendar.setup({
									        inputField     :    "inputDataFineLFS",     // id of the input field
									        ifFormat       :    "%d/%m/%Y",      // format of the input field
									        button         :    "calendarDataFineLFS",  // trigger for the calendar (button ID)
									        align          :    "Tl",           // alignment (defaults to "Bl")
									        singleClick    :    true							       
								    		});					    	
									</script>
								</c:if>
							 </td>
					     </tr>
					</table>					     
					<table ${variazioniAnagrafiche eq true ? '' : 'style="display:none;"'}>
						<tr>
							<th><label for="<%= PSBD.FIELD_NAME_MOTIVO_CO %>">Motivazione della variazione anagrafica</label></th>
							<td>
								<select onchange="setFormModified('Modificato0')" tabindex="<%=++indiceTab%>" 
										style="width:100%" 
										name="<%= PSBD.FIELD_NAME_MOTIVO_CO %>" 
										id=<%= PSBD.FIELD_NAME_MOTIVO_CO %> CLASS="BOTTONE">
									<option></option>
								  	<c:set var="idMotivoVarCO" value="${inizioLavori.idMotivoVarCO}" scope="request" />
								  	<u:options name="<%= ParametriServlet.MOTIVO_VCO_BEAN %>" scope="request" value="idMotivoVarCO"/>
								</select>
							</td>
						</tr>
					</table>
					<table>	
				      <tr>
							<td colspan="2">
								<div class="inthead">
									<label onclick="showMenu('<%= ParametriServletInizioLavori.TAB_POSIZIONE_AGGIUDICATARI %>')" style="color:black; letter-spacing:0.2em;">
										<img src="img/minus.gif" id="img<%= ParametriServletInizioLavori.TAB_POSIZIONE_AGGIUDICATARI %>"/>POSIZIONE CONTRIBUTIVA/ASSICURATIVA IMPRESA AFFIDATARIA/AGGIUDICATARIA</label>
									<div id="<%= ParametriServletInizioLavori.TAB_POSIZIONE_AGGIUDICATARI %>" style="display: block;" >
										<c:set var="aggiudicatari" value="${schedaLavori.posizioneAggiudicatari}" scope="page"></c:set>
										<%@ include file="/schedaB1/posizioneAggiudicatari.jsp" %>     
									</div>
								</div>
							</td>
						</tr>
					     
					     <tr>
							<td colspan="2">
								<div class="inthead">
									<label onclick="showMenu('<%= PSBD.TAB_RESPONSABILE_PROCEDIMENTO %>')" style="color:black; letter-spacing:0.2em;">
										<img src="img/minus.gif" id="img<%= PSBD.TAB_RESPONSABILE_PROCEDIMENTO %>"/>SOGGETTI AI QUALI SONO STATI  CONFERITI INCARICHI</label>
									<div id="<%= PSBD.TAB_RESPONSABILE_PROCEDIMENTO %>" style="display: block;" >
										<c:set var="responsabili" value="${schedaLavori.responsabiliInizio}" scope="page"></c:set>
										<c:set var="includerConfirmed" value="${inizioLavori.confirmed}" scope="page"></c:set>
										<%@ include file="/scheda1/responsabile.jsp" %>     
									</div>
								</div>
							</td>
						</tr>
				
					</table>
				</fieldset>
				<table>  
					<tr>
						 <input type="hidden" name="checkIfOK" id="checkIfOK" value="<%=new Integer(session.getAttribute("checkIfOK").toString()) + 1%>" />
					    
						<td><input ${variazioniAnagrafiche eq true ? '' : saveAndResetDisabledStr} type="button" value="Salva" onclick="creaHidden('FormInizioLavori','<%=PSBD.RESPONSABILE %>');creaHidden('FormInizioLavori','<%=ParametriServletInizioLavori.POSIZIONE_AGGIUDICATARIO %>');checkAndAction('check',5,'${saveAction}')"></td>
						<td><input ${noConf eq true ? 'disabled' : ''} type="button" value="Conferma" onclick="creaHidden('FormInizioLavori','<%=PSBD.RESPONSABILE %>');creaHidden('FormInizioLavori','<%=ParametriServletInizioLavori.POSIZIONE_AGGIUDICATARIO %>');checkAndAction('check',5,'<%=PSBD.ACTION_CONFERMA %>')"></td>
						<td><input ${variazioniAnagrafiche eq true ? '' : saveAndResetDisabledStr} type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_LOAD %>')"></td>
						<c:if test="${annullabile}">
							<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="loadAnnullamentJSP('<%= ParametriServletInizioLavori.TAB_INIZIO_LAVORI %>')"></td>
						</c:if>
						<c:if test="${cancellabile}">
							<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
						</c:if>
						<c:if test="${variazAnagraf}">
							<td><input type="button" value="Comunica Variazioni Anagrafiche" onclick="doAction('<%=PSBD.ACTION_VARIAZIONI_ANAGRAFICHE %>')"/></td>	
						</c:if>
						<%@ include file="../include/statoscheda.inc" %>
					</tr>
						</table>
					</fieldset>						
				</form>
			</div>
			<%@ include file="../include/newfooter.inc" %>
		</div>
	</body>		
</html>
<% } catch (Exception e) {e.printStackTrace();}%>	
