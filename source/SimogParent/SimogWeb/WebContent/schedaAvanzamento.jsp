<%@ page language="java" contentType="text/html; charset=UTF-8"
    errorPage="errore.jsp"%>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

<%@page import="it.avlp.simog.common.servlet.ParametriServletAvanzamento"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.beans.avanzamento.AvanzamentoBean"%>
<%@page import="java.util.HashMap"%>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>

<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

<% int indiceTab = 0;%>

<%--Carico la lista delle schede gia compilate e i dati della gara --%>
<c:set var="listaSchede" value="${sessionScope['listaAvanzamenti']}"></c:set>
<c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>
<jsp:useBean id="schedaAvanzamento" type="it.avlp.simog.beans.avanzamento.SchedaAvanzamento" class="it.avlp.simog.beans.avanzamento.SchedaAvanzamento" scope="request"></jsp:useBean>

<jsp:useBean id="aggiudicazione" type="it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean" class="it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean" scope="request"></jsp:useBean>

<% AvanzamentoBean avanzamento = schedaAvanzamento.getAvanzamentoFE(); 
	pageContext.setAttribute("avanzamento",avanzamento);%>	
<c:set var="rupOk" value="${(UTENTE.login eq datiGara.cfRup or datiGara.cfRup eq null ) and aggiudicazione.flagAggiudPrincipale ne 'N'}" />
<%-- aggiunto avanzamento.idAvanzamento tra le condizioni (preso esempio da accordi)--%>
<c:set var="hide" value="${datiGara.deleted || 
							avanzamento.confirmed || 
							rupOk eq false || 
							(avanzamento.idAvanzamento < 1 && !schedaAvanzamento.aggiungibile) || 
							UTENTE.ossReg || UTENTE.RASA || schedaAvanzamento.delegaScheda || schedaAvanzamento.riaggiudicata}" />
<c:set var="annullabile" value="${!UTENTE.ossReg && !UTENTE.RASA &&
									rupOk eq true && 
									datiGara.deleted ne true && 
									avanzamento.confirmed eq true &&
									avanzamento.richAnn ne true
									 && avanzamento.richDelete ne true 
									 && schedaAvanzamento.delegaScheda eq false 
									 and schedaAvanzamento.riaggiudicata eq false}"></c:set>
									
<c:set var="disabled" value="${hide ? 'disabled':''}"></c:set>

<c:set var="noConf" value="${(hide || (avanzamento.idAvanzamento le 0)) || avanzamento.richAnn eq true ? 'disabled':''}"></c:set>




<link rel="stylesheet" href="theme/tabmenu.css"/>
<!-- calendar stylesheet -->
<link rel="stylesheet" type="text/css" media="all" href="calendar/calendar-blue.css" title="win2k-cold-1" />
<!-- main calendar program -->
<script type="text/javascript" src="calendar/calendar.js"></script>
<!-- language for the calendar -->
<%@ include file="include/calendar-dynamic.inc" %>
<!-- the following script defines the Calendar.setup helper function, which makes
     adding a calendar a matter of 1 or 2 lines of code. -->
<script type="text/javascript" src="calendar/calendar-setup.js"></script>
<script type="text/javascript"  src="script/pageutils.js"></script>
<%@ include file="include/i18n-init.inc" %>

<title><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.avanzamento" /> - <%= user.getProfilo() %></title>

</head>
<body>
	<div id="gabbia" align="left">
		<%@ include file="/include/header.inc" %>			
		<div class="bodypage-e" align="left">
		<%--Header Scheda e Lista Schede gia compilate --%>
		<h1><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.avanzamento" /></h1>
		<div  class="hmenu" align="left">	
			  <ul>
			  	<%String riScheda =ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=";%>
			     <li><a title="<utils:message key="scheda.tornaListaAggiudicazioni" plain="true" />" href="javascript:changePage('<%=riScheda%>${datiGara.idLotto}','Modificato')"><utils:message key="scheda.listaAggiudicazioni" /></a></li>  
			    <c:if test="${!UTENTE.ossReg && !UTENTE.RASA && avanzamento.idAvanzamento > 0 && datiGara.deleted eq false && rupOk eq true && 
			    schedaAvanzamento.aggiungibile &&  schedaAvanzamento.delegaScheda eq false and schedaAvanzamento.riaggiudicata eq false}">
			    	<li>
			        <c:url  value="srvSchedaAvanzamento" var="newMod">
			        	<c:param name="toDo" value="load"></c:param>
					    <c:param name="toEdit" value="-1"></c:param>
				    </c:url>
				    <a href="<c:out value='${newMod}'/>" ><utils:message key="scheda.aggiungiNuovaScheda" /></a>
				 	</li>
				</c:if>
			    <c:if test="${datiGara.deleted eq false and rupOk eq true and 
			    	schedaAvanzamento.noInserimenti eq true and schedaAvanzamento.delegaScheda eq false 
			    	and schedaAvanzamento.riaggiudicata eq false}">
			    	<li>
			        <p style="color: red;"><big><utils:message key="scheda.attenzioneTrasmissione" /></big></p>
				 	</li>
				</c:if>
			  </ul>
		</div>

		<%@ include file="/include/gestisciErrore.inc" %>
		
			 <h2><utils:message key="scheda.listaSchedeAvanzamenti" /></h2>
			 <div style="overflow: auto;height: 13em; width: 100%;"  >
			
			<div class="gara">	 	
			 
		     <table align="center" width="300px">   
				<tr> 
					<th class="garaTh"><utils:message key="scheda.numeroAvanzamento" /></th>
					<th class="garaTh"><utils:message key="scheda.dataStatoAvanzamento" /></th>
			     	<th class="garaTh"><utils:message key="scheda.importoSal" /></th>
			     	<th class="garaTh"><utils:message key="scheda.dataEmissioneCertificatoPagamento" /></th>
			     	<th class="garaTh"><utils:message key="scheda.importoCertificatoPagamento" /></th>
			     	<th class="garaTh"><utils:message key="scheda.statoScheda" /></th>
			     	<th class="garaTh"><utils:message key="table.azione" /></th>
			    </tr>
			 
		     <c:set var="counter" value='0' scope="page"/>
			 <c:forEach items="${listaSchede}" var="scheda">
			 	<tr>
			 		<td class="garaTd"><c:out value="${scheda.numeroAvanzamento}"/></td>
			 		<td class="garaTd"><c:out value="${scheda.dataRaggiungimento}"></c:out></td>
			 		<td class="garaTd"><c:out value="${scheda.importoSalStr}"></c:out></td>
			 		<td class="garaTd"><c:out value="${scheda.dataCertificato}"></c:out></td>
			 		<td class="garaTd"><c:out value="${scheda.importoCertificatoStr}"></c:out></td>
			 		<td class="garaTd"><c:out value="${scheda.descrizioneStato}"></c:out></td>
			 		<td class="hmenu">
						<c:url  value="srvSchedaAvanzamento" var="modURL">
						<c:param name="toDo" value="load"></c:param>
					    	<c:param name="toEdit" value="${counter}"></c:param>
					    </c:url>
					    <c:choose>
					    	<c:when test="${!UTENTE.ossReg && !UTENTE.RASA && datiGara.deleted eq false && rupOk eq true && scheda.confirmed eq false && 
					    	schedaAvanzamento.delegaScheda eq false and schedaAvanzamento.riaggiudicata eq false}">
					    		<a href="<c:out value='${modURL}'/>"><utils:message key="button.modifica" /></a>
					    	</c:when>
					    	<c:otherwise>
					    		<a href="<c:out value='${modURL}'/>"><utils:message key="button.visualizzazione" /></a>
					    	</c:otherwise>
					    </c:choose>
					</td>
				</tr>
				<c:set var="counter" value="${counter + 1}" scope="page"/>
			</c:forEach> 
			</table>
		</div></div>
		 <br /><br /><br /> 
		 
		<%-- PANNELLO DELLE RICHIESTE DI ANNULLAMENTO DELLA SCHEDA --%>
		<%@ include file="../include/RichAnnPanel.jsp" %>
		<%-- --%>
		 
			<table >	
				<tr>
				<td><input <c:out value="${disabled}"/> type="button" value="<utils:message key="button.salva" plain="true" />" onclick="checkAndAction('check',1,'<%=PSBD.ACTION_SALVA %>')"/></td>
				<td><input <c:out value="${noConf}"/>  type="button" value="<utils:message key="button.conferma" plain="true" />" onclick="checkAndAction('check',1,'<%=PSBD.ACTION_CONFERMA %>')"/></td>
				<td><input <c:out value="${disabled}"/> type="button" value="<utils:message key="button.reimposta" plain="true" />" onclick="reimpostaForm('<%=PSBD.ACTION_REIMPOSTA %>')"/></td>	
				<c:if test="${annullabile eq true}">
					<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')"/>	</td>
				</c:if>
				<c:if test="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && avanzamento.okCancellazione eq true && schedaAvanzamento.delegaScheda eq false and schedaAvanzamento.riaggiudicata eq false}">
					<td><input type="button" value="<utils:message key="scheda.richiediCancellazione" plain="true" />" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
				</c:if>
			<c:set var="statoid" value="${avanzamento.idStato}"/>
			<c:set var="statoann" value="${avanzamento.richAnn || avanzamento.richDelete}"/>
			<c:set var="statodesc" value="${avanzamento.descrizioneStato}"/>

			<%@ include file="../include/statoscheda.inc" %>
				</tr>
			</table>

			 <fieldset>
			 	<h2>
			 		<utils:message key="scheda.schedaAvanzamento" /> -
			 		<c:choose>
			 			<c:when test="${avanzamento.idAvanzamento < 1}">
			 				<utils:message key="button.inserimento" />
			 			</c:when>
			 			<c:when test="${hide == true}">
			 				<utils:message key="button.visualizzazione" />
			 			</c:when>
			 			<c:otherwise>
			 				<utils:message key="button.modifica" />
			 			</c:otherwise>
			 		</c:choose>
			 	</h2>
			 	<form action="<%=ParametriServletAvanzamento.SRV_SCHEDA_AVANZAMENTO %>" method="post" onkeypress="setFormModified('Modificato')">
			 	
				 	<%--Campi hidden e altro, copiati dalle altre schede, non so se servono. DA VERIFICARE --%>					
					<input type="hidden" name="<%=PSBD.TAB%>" id="<%=PSBD.TAB%>" value="" />	
					<input type="hidden" name="<%=PSBD.ACTION_TYPE%>" id="<%=PSBD.ACTION_TYPE%>" value="" />
					<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_ID_INFO%>" id="<%=ParametriServlet.FIELD_NAME_ID_INFO%>" value='<c:out value="${datiGara.idInfo}"/>' />
					<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO%>" id="<%=ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO%>" value='<c:out value="${datiGara.dataInizioInfo}"/>' />
					<input type="hidden" name="<%=ParametriServletAvanzamento.FIELD_NAME_ID_AVANZAMENTO %>" value='<c:out value="${avanzamento.idAvanzamento}"/>'/>
					<input type="hidden" name="<%=ParametriServletAvanzamento.FIELD_NAME_DATA_INIZIO_AVANZAMENTO %>" value='<c:out value="${avanzamento.dataInizioAvanzamento}"/>'/>
					<input type="hidden" id="Modificato"  value="0" />
				
					<fieldset class="gara">
				
					<table width="100%">
						<tr>
							<td align="center" colspan="2"><p class="detailHelp"><strong><utils:message key="scheda.riferimentoDatiFaseAggiudicazione" /></strong></p></td>
						</tr>
   					<%@include file="/include/intestazione.jsp" %>
						<tr>
							<td><label><utils:message key="scheda.statoAvanzamento" /></label></td>
	   					<td><input name="<%= ParametriServletAvanzamento.FIELD_NAME_NUMERO_AVANZAMENTO%>" 
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: right;"
	   							   readonly="readonly"
	   							   type="text"
	   							   value="<c:out value='${(avanzamento.numeroAvanzamento)}'/>" />
	   					</td>
						</tr>
						<tr>
						<td colspan="2">
						<hr>
						</td>
						</tr>
			
						<tr>
							<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DenomStatoAvanz") %> for="<%= ParametriServletAvanzamento.FIELD_NAME_DENOM_STATO_AVANZ %>"><utils:message key="scheda.denominazioneStatoAvanzamento" />*</label></td>
							<td>
								<textarea name="<%= ParametriServletAvanzamento.FIELD_NAME_DENOM_STATO_AVANZ %>"  
										  tabindex="<%=++indiceTab%>" <c:out value='${disabled}'/> 
										  rows="3" cols="40"/><c:out value='${(avanzamento.denomStatoAvanz)}'/></textarea>
							</td>	
						</tr>
				
						<tr>
							<td rowspan="2"><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_ModPagamento") %>>Modalit&agrave; di pagamento del corrispettivo</label></td>
							<td>
								<input name="modoPagamento" type="checkbox" value="1" <c:out value="${disabled}"/> <c:out value="${(avanzamento.flagPagamento eq '1' || avanzamento.flagPagamento eq '3') ? 'checked' :''}"/> />Somme in denaro
							</td>
						</tr>
						<tr>
							<td>
								<input name="modoPagamento" type="checkbox" value="2" <c:out value="${disabled}"/> <c:out value="${(avanzamento.flagPagamento eq '2' || avanzamento.flagPagamento eq '3') ? 'checked' :''}"/> />Trasferimento in propriet&agrave; di beni immobili
							</td>
						</tr>
						<tr>
							<td><label for="<%=ParametriServletAvanzamento.FIELD_NAME_IMPORTO_ANTICIPAZIONE %>">Eventuale anticipazione</label></td>
							<td>
								<input
										name="<%= ParametriServletAvanzamento.FIELD_NAME_IMPORTO_ANTICIPAZIONE %>"
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: right;"
	   							   <c:out value="${disabled}"/>
	   							   type="text"
	   							   value="<c:out value='${avanzamento.importoAnticipazioneStr}'/>"
	   							   onblur="validateAmount(this)" />
							</td>
						</tr>
						<tr>
							<td><label for="<%=ParametriServletAvanzamento.FIELD_NAME_DATA_ANTICIPAZIONE %>">Data del certificato di pagamento relativo all'anticipazione</label></td>
							<td>
								<input name="<%= ParametriServletAvanzamento.FIELD_NAME_DATA_ANTICIPAZIONE %>" 
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: center;"
	   							   onchange="setFormModified('Modificato')"
	   							   <c:out value="${disabled}"/>
	   							   type="text"
	   							   id="dataAnticipField"
	   							   onblur="Calendar.validaData(this)"
	   							   value="<c:out value='${avanzamento.dataAnticipazione}'/>" />
	   						<c:if test="${hide == false}">
	   						<c:if test="${avanzamento.numeroAvanzamento eq 1}">
	   							<img src="calendar/img.gif" id="calendarDataAnticip" style="cursor: pointer; border: 1px solid red;" title="Date selector"
	   							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
	   							<script type="text/javascript">
									    Calendar.setup({
								        inputField     :    "dataAnticipField",     // id of the input field
								        ifFormat       :    "%d/%m/%Y",      // format of the input field
								        button         :    "calendarDataAnticip",  // trigger for the calendar (button ID)
								        align          :    "Tl",           // alignment (defaults to "Bl")
								        singleClick    :    true							       
							    		});					    	
								</script>
							</c:if>
							</c:if>
							</td>
						</tr>
						<tr>
							<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataAvanz") %> for="<%=ParametriServletAvanzamento.FIELD_NAME_DATA_RAGGIUNGIMENTO %>">Data Stato di avanzamento*</label></td>
							<td>
								<input name="<%= ParametriServletAvanzamento.FIELD_NAME_DATA_RAGGIUNGIMENTO %>" 
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: center;"
	   							   onchange="setFormModified('Modificato')"
	   							   <c:out value="${disabled}"/>
	   							   type="text"
	   							   id="dataRaggiungField"
	   							   onblur="Calendar.validaData(this)"
	   							   value="<c:out value='${avanzamento.dataRaggiungimento}'/>" />
	   						<c:if test="${hide == false}">
	   							<img src="calendar/img.gif" id="calendardataRaggiung" style="cursor: pointer; border: 1px solid red;" title="Date selector"
	   							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
	   							<script type="text/javascript">
									    Calendar.setup({
								        inputField     :    "dataRaggiungField",     // id of the input field
								        ifFormat       :    "%d/%m/%Y",      // format of the input field
								        button         :    "calendardataRaggiung",  // trigger for the calendar (button ID)
								        align          :    "Tl",           // alignment (defaults to "Bl")
								        singleClick    :    true							       
							    		});					    	
								</script>
							</c:if>
							</td>
						</tr>
						<tr>
							<td>
								<label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_ImportoAvanz") %> for="<%=ParametriServletAvanzamento.FIELD_NAME_IMPORTO_SAL %>">Importo stato avanzamento*</label>
							</td>
							<td>
								<input name="<%= ParametriServletAvanzamento.FIELD_NAME_IMPORTO_SAL %>"
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: right;"
	   							   <c:out value="${disabled}"/>
	   							   type="text"
	   							   value="<c:out value='${avanzamento.importoSalStr}'/>"
	   							   onblur="validateAmount(this)" />
							</td>
						</tr>
						<tr>
							<td><label for="<%=ParametriServletAvanzamento.FIELD_NAME_DATA_CERTIFICATO %>">Data di emissione del certificato/mandato di pagamento</label></td>
							<td>
								<input name="<%= ParametriServletAvanzamento.FIELD_NAME_DATA_CERTIFICATO %>" 
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: center;"
	   							   onchange="setFormModified('Modificato')"
	   							   <c:out value="${disabled}"/>
	   							   type="text"
	   							   id="<%= ParametriServletAvanzamento.FIELD_NAME_DATA_CERTIFICATO %>"
	   							   onblur="Calendar.validaData(this)"
	   							   value="<c:out value='${avanzamento.dataCertificato}'/>" />
	   						<c:if test="${hide == false}">
	   							<img src="calendar/img.gif" id="calendarDataCert" style="cursor: pointer; border: 1px solid red;" title="Date selector"
	   							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
	   							<script type="text/javascript">
									    Calendar.setup({
								        inputField     :    "<%= ParametriServletAvanzamento.FIELD_NAME_DATA_CERTIFICATO%>",     // id of the input field
								        ifFormat       :    "%d/%m/%Y",      // format of the input field
								        button         :    "calendarDataCert",  // trigger for the calendar (button ID)
								        align          :    "Tl",           // alignment (defaults to "Bl")
								        singleClick    :    true							       
							    		});					    	
								</script>
							</c:if>
							</td>
						</tr>
						<tr>
							<td>
								<label for="<%=ParametriServletAvanzamento.FIELD_NAME_IMPORTO_CERTIFICATO %>">Importo del certificato/mandato di pagamento</label>
							</td>
							<td>
								<input name="<%= ParametriServletAvanzamento.FIELD_NAME_IMPORTO_CERTIFICATO %>"
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: right;"
	   							   <c:out value="${disabled}"/>
	   							   type="text"
	   							   value="<c:out value='${avanzamento.importoCertificatoStr}'/>"
	   							   onblur="validateAmount(this)" />
							</td>
						</tr>
						<tr>
						<td>
							<label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_FlagRitardo") %> for="<%= ParametriServletAvanzamento.FIELD_NAME_FLAG_RITARDO %>">L'avanzamento raggiunto, rispetto al cronoprogramma di previsione registra*</label>
						</td>
						<td>
							<input tabindex="<%=++indiceTab%>" 
	   							   id="check1P" 
	   							   type="radio" 
	   							   name="<%= ParametriServletAvanzamento.FIELD_NAME_FLAG_RITARDO %>" 
	   							   value="<%= AvanzamentoBean.AVANZ_PUNTUALE %>" 
	   							   <%= avanzamento.AVANZ_PUNTUALE.equals(avanzamento.getFlagRitardo()) ? "checked" : "" %>
	   							   <c:out value="${disabled}"/> />Puntuale<br/>
	   						<input tabindex="<%=++indiceTab%>" 
	   							   id="check1Y" 
	   							   type="radio" 
	   							   name="<%= ParametriServletAvanzamento.FIELD_NAME_FLAG_RITARDO %>" 
	   							   value="<%= AvanzamentoBean.AVANZ_ANTICIPO %>" 
	   							   <%= avanzamento.AVANZ_ANTICIPO.equals(avanzamento.getFlagRitardo()) ? "checked" : "" %> 
	   							   <c:out value="${disabled}"/> />Anticipo
	   						<input tabindex="<%=++indiceTab%>" 
	   							   id="check1N" 
	   							   type="radio" 
	   							   name="<%= ParametriServletAvanzamento.FIELD_NAME_FLAG_RITARDO %>" 
	   							   value="<%= AvanzamentoBean.AVANZ_RITARDO %>" 
	   							   <%= avanzamento.AVANZ_RITARDO.equals(avanzamento.getFlagRitardo()) ? "checked" : "" %>
	   							   <c:out value="${disabled}"/> />Ritardo
						</td>
					</tr>
					<tr>
						<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_ScostNumGiorni") %> style="padding-left:20px;" for="<%= ParametriServletAvanzamento.FIELD_NAME_NUMERO_GIORNI_SCOST%>">Indicare lo scostamento registrato in numero di giorni</label></td>
	   					<td><input name="<%= ParametriServletAvanzamento.FIELD_NAME_NUMERO_GIORNI_SCOST%>"
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: right;"
	   							   <c:out value="${disabled}"/>
	   							   type="text"
	   							   value="<c:out value='${avanzamento.numeroGiorniScost}'/>"
	   							   onblur="validateNumber(this)"/>
	   					</td>
					</tr>
					<tr>
						<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_ProrogaGiorni") %> style="padding-left:20px;" for="<%= ParametriServletAvanzamento.FIELD_NAME_NUMERO_GIORNI_PROROGA%>">Indicare il numero di giorni di proroga concessi(non conseguenti a varianti)</label></td>
	   					<td><input name="<%= ParametriServletAvanzamento.FIELD_NAME_NUMERO_GIORNI_PROROGA%>"
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: right;"
	   							   <c:out value="${disabled}"/>
	   							   type="text"
	   							   value="<c:out value='${avanzamento.numeroGiorniProroga}'/>"
	   							   onblur="validateNumber(this)"/>
	   					</td>
					</tr>
					</table>
					<input type="hidden"  value="save" name="toDo" id="toDo"/>	
					</fieldset>
					<table >	
					<tr>
					    <input type="hidden" name="checkIfOK" id="checkIfOK" value="<%=new Integer(session.getAttribute(ParametriServlet.checkIfOK).toString()) + 1%>" />
						<td><input <c:out value="${disabled}"/> type="button" value="Salva" onclick="checkAndAction('check',1,'<%=PSBD.ACTION_SALVA %>')"/></td>
						<td><input <c:out value="${noConf}"/>  type="button" value="Conferma" onclick="checkAndAction('check',1,'<%=PSBD.ACTION_CONFERMA %>')"/></td>
						<td><input <c:out value="${disabled}"/> type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_REIMPOSTA %>')"/></td>	
						<c:if test="${annullabile eq true}">
							<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')"/>	</td>
						</c:if>
						<c:if test="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && avanzamento.okCancellazione eq true && schedaAvanzamento.delegaScheda eq false and schedaAvanzamento.riaggiudicata eq false}">			
					<td><input type="button" value="<utils:message key="scheda.richiediCancellazione" plain="true" />" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
						</c:if>
				<%@ include file="../include/statoscheda.inc" %>
						
					</tr>
				</table>
	
			 	</form>
			 	</fieldset>
			 	</div>
			 	<%@ include file="include/newfooter.inc" %>
			 	</div>
			 	

</body>

</html>
