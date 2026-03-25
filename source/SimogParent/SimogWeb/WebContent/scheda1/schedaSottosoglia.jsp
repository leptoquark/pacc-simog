<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="../errore.jsp" %>
<%@ include file="../include/newbasicHeader.inc" %>
<%@ include file="../include/controlloSessione.inc" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.util.PageHelper"%>
<%@ page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@ page import="it.avlp.simog.common.servlet.PSBD"%>
<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="theme/tabmenu.css"/>
<link rel="stylesheet" type="text/css" media="all" href="calendar/calendar-blue.css" title="win2k-cold-1" />
<script type="text/javascript" src="calendar/calendar.js"></script>
<%@ include file="../include/calendar-dynamic.inc" %>
<script type="text/javascript" src="calendar/calendar-setup.js"></script>
<script type="text/javascript" src="script/pageutils.js"></script>
<script type="text/javascript" src="xtree/treeutils.js"></script>
<%@ include file="../include/i18n-init.inc" %>
<%@ include file="/script/script.js" %>
<%@ include file="/script/domUtilsSotto.js" %> 
<script type="text/javascript" src="script/schedaA.js"></script>

<title><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.faseAggiudicazioneSottoSoglia" /> - <%=user.getProfilo()%></title>

</head>
	<c:set var="datiGara" value="${sessionScope['dati_gara']}" />
	<jsp:useBean id="schedaSottosoglia" type="it.avlp.simog.beans.aggiudicazione.Scheda_A" class="it.avlp.simog.beans.aggiudicazione.Scheda_A" scope="request"></jsp:useBean>
	<jsp:useBean id="disabled" class="java.lang.String" scope="page"></jsp:useBean>
	
	<c:set var="rupOk" value="${(UTENTE.login eq datiGara.cfRup or datiGara.cfRup eq null ) and schedaA.aggiudicazione.flagAggiudPrincipale ne 'N'}" />
	<c:set var="roByFlusso" value="${datiGara.deleted or rupOk eq false or UTENTE.ossReg eq true or UTENTE.RASA eq true or  schedaSottosoglia.delegaScheda eq true  or schedaSottosoglia.riaggiudicata eq true}" />
	
	
	<c:set var="readonly" value="${roByFlusso or schedaSottosoglia.aggiudicazione.confirmed or schedaSottosoglia.riaggiudicazione}" />
 	<c:set var="disabledStr" value="${readonly ? 'disabled':'' }" />
 	<c:set var="readonlyStr" value="${readonly? 'readonly' : ''}" />
 	<c:set var="saveAndResetDisabledStr" value="${ (roByFlusso or schedaSottosoglia.aggiudicazione.confirmed) ? 'disabled' : ''  }"/>
	<c:set var="noConf" value="${roByFlusso eq true or (schedaSottosoglia.aggiudicazione.idAggiudicazione le 0) || schedaSottosoglia.aggiudicazione.richAnn eq true or schedaSottosoglia.aggiudicazione.confirmed}" />
	<c:set var="annullabile" value="${ roByFlusso ne true and schedaSottosoglia.aggiudicazione.confirmed eq true and schedaSottosoglia.aggiudicazione.richAnn ne true and schedaSottosoglia.aggiudicazione.richDelete ne true and variazioniAnagrafiche ne true}" />
	<c:set var="cancellabile" value="${ roByFlusso ne true and schedaSottosoglia.aggiudicazione.okCancellazione eq true  and variazioniAnagrafiche ne true}" />
	<c:set var="variazAnagraf" value="${(schedaSottosoglia.varAnagActive eq true and roByFlusso ne true and schedaSottosoglia.aggiudicazione.confirmed eq true and variazioniAnagrafiche ne true )}" />
	
	<c:set var="flagEnteSpeciale" value="${schedaSottosoglia.infoComuni.flagEnteSpeciale}" />
	
	<c:set var="saveAction" value="setAndSave"/>
    <c:if test="${variazioniAnagrafiche eq true}">
    	 <c:set var="saveAction" value="setAndVaria"/>
	</c:if>
	
	<c:set var="fromAVCPass" value="${false}" />
	<% if (SimogFlags.is3028_RFWEBSC00Active()){ %>
		<c:set var="fromAVCPass" value="${schedaSottosoglia.aggiudicazione.fromAVCPass}" />
	<% } %>
	<c:set var="readonlyAVCPass" value="${fromAVCPass ? 'readonly' : ''}" />
	<c:set var="markFieldAVCPass" value="${fromAVCPass ? '*' : ''}" />		
	
<body> 
	<div id="gabbia">
	
<%-- 		<h1>fromAVCPass: ${fromAVCPass} - origine[${schedaSottosoglia.aggiudicazione.origine}]</h1> --%>
	
	<%if(request.getAttribute("protect")!= null) {%>
		<%@ include file="/include/protect.inc" %>
	<%} %>
		<%@ include file="../include/header.inc" %>	 
		<div class="bodypage-e">
			<form id="FormSchedaSottosoglia" name="gestioneTab" action="<%=ParametriServlet.SRV_SCHEDA_SOTTOSOGLIA %>" method="post" onkeypress="setFormModified('Modificato')">
			<h1><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.faseAggiudicazioneSottoSoglia" /></h1>
			<input type="hidden" name="checkIfOK" id="checkIfOK" value="<%=new Integer(session.getAttribute("checkIfOK").toString()) + 1%>" />
			<input type="hidden" name = "<%=PSBD.FIELD_NAME_ID_AGGIUDICAZIONE %>"  value="<c:out value='${schedaSottosoglia.aggiudicazione.idAggiudicazione}' />" />
			<input type="hidden" name = "<%=PSBD.DATA_INIZIO_AGGIUDICAZIONE %>"  value="<c:out value='${schedaSottosoglia.aggiudicazione.dataInizioAggiudicazione}' />" />
			<input type="hidden" name = "descrizioneStato"  value="<c:out value='${schedaSottosoglia.aggiudicazione.descrizioneStato}' />" />
			<input type="hidden" name = "idStato"  value="<c:out value='${schedaSottosoglia.aggiudicazione.idStato}' />" />
			<input type="hidden" name = "Modificato" id="Modificato" value="<c:out value="${param['modificato']}" />">						
			<input type="hidden" name="<%=PSBD.TAB%>" id="<%=PSBD.TAB%>" value="">	
			<input type="hidden" name="<%=PSBD.ACTION_TYPE%>" id="<%=PSBD.ACTION_TYPE%>" value="">	
			<input type="hidden" name="<%=PSBD.VAR_ANN%>"  value="${variazioniAnagrafiche}" />											
			<div  class="hmenu">	
			<ul> 		
				<li><a title="<utils:message key="scheda.mostraDatiComuni" plain="true" />" href="javascript:changePage('<%= PSBD.SRV_DATI_COMUNI %>','Modificato')"><utils:message key="scheda.mostraDatiComuni" /></a></li>
					<%String riScheda =ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=";%>
				<li><a title="<utils:message key="scheda.tornaListaAggiudicazioni" plain="true" />" href="javascript:changePage('<%=riScheda%><c:out value="${datiGara.idLotto}" />','Modificato')"><utils:message key="scheda.listaAggiudicazioni" /></a></li>			    
		 	</ul>  					
			</div>
			<%@ include file="/include/gestisciErrore.inc" %>
				
			<%@ include file="/include/RichAnnPanel.jsp" %>

			<%@ include file="/include/VarAnagPanel.jsp" %>
	<fieldset>
		<table>	
			<tr>
				<td><input  ${variazioniAnagrafiche eq true ? '' : saveAndResetDisabledStr} type="button" value="Salva" onclick="${saveAction}('FormSchedaSottosoglia','<%=PSBD.TAB_SOTTOSOGLIA %>')"></td>
				<td><input ${noConf eq true ? 'disabled' : ''} type="button" value="Conferma" onclick="setAndConfirm('FormSchedaSottosoglia','<%= PSBD.TAB_SOTTOSOGLIA %>')"></td>
				<td><input  ${variazioniAnagrafiche eq true ? '' : saveAndResetDisabledStr}  type="button" value="Reimposta" onclick="reimposta('<%= PSBD.TAB_SOTTOSOGLIA%>')"></td>
				<c:if test="${annullabile}">
					<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="loadAnnullamentJSP('<%= PSBD.TAB_SOTTOSOGLIA %>')"></td>
				</c:if>
				<c:if test="${!fromAVCPass &&  cancellabile}">
					<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
				</c:if>
				
				<c:if test="${variazAnagraf}">
					<td><input type="button" value="Comunica Variazioni Anagrafiche" onclick="doAction('<%=PSBD.ACTION_VARIAZIONI_ANAGRAFICHE %>')"/></td>	
				</c:if>
				
				<c:if test="${roByFlusso eq false and schedaSottosoglia.riaggiudicabile eq true}">
					<td><input type="button" value="Riaggiudica" onclick="doAction('<%=PSBD.ACTION_RIAGGIUDICAZIONE %>')"/></td>	
				</c:if>
				
				<c:set var="statoid" value="${schedaSottosoglia.aggiudicazione.idStato}"/>
				<c:set var="statoann" value="${schedaSottosoglia.aggiudicazione.richAnn}"/>
				<c:set var="statoann" value="${schedaSottosoglia.aggiudicazione.richAnn || schedaSottosoglia.aggiudicazione.richDelete}"/>			
				<c:set var="statodesc" value="${schedaSottosoglia.aggiudicazione.descrizioneStato}"/>
				<%@ include file="../include/statoscheda.inc" %>
			</tr>
		</table>
		
		<!-- *AVCPASS* -->
		<c:if test="${fromAVCPass}">
			<p style="color: red"><%=Costanti.AVCPASS_ALERT %></p>  
		</c:if>		
		
		<table width="100%">	
			<tr>
				<td colspan="2">
					<div id="<%= PSBD.TAB_AGGIUDICAZIONE %>" >
						<c:set var="aggiudicazione" value="${schedaSottosoglia.aggiudicazione}" scope="page"></c:set>
						<%@ include file="/scheda1/sottosoglia.jsp" %>    
					</div>
				</td>
			</tr>																						
		</table>
		<table>  
			<tr>
				<td><input  ${variazioniAnagrafiche eq true ? '' : saveAndResetDisabledStr} type="button" value="Salva" onclick="${saveAction}('FormSchedaSottosoglia','<%=PSBD.TAB_SOTTOSOGLIA %>')"></td>
				<td><input ${noConf eq true ? 'disabled' : ''} type="button" value="Conferma" onclick="setAndConfirm('FormSchedaSottosoglia','<%= PSBD.TAB_SOTTOSOGLIA %>')"></td>
				<td><input  ${variazioniAnagrafiche eq true ? '' : saveAndResetDisabledStr}  type="button" value="Reimposta" onclick="reimposta('<%= PSBD.TAB_SOTTOSOGLIA%>')"></td>
				<c:if test="${annullabile}">
					<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="loadAnnullamentJSP('<%= PSBD.TAB_SOTTOSOGLIA %>')"></td>
				</c:if>
				<c:if test="${!fromAVCPass && cancellabile}">
					<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
				</c:if>
				
				<c:if test="${variazAnagraf}">
					<td><input type="button" value="Comunica Variazioni Anagrafiche" onclick="doAction('<%=PSBD.ACTION_VARIAZIONI_ANAGRAFICHE %>')"/></td>	
				</c:if>
				
				<c:if test="${roByFlusso eq false and schedaSottosoglia.riaggiudicabile eq true}">
					<td><input type="button" value="Riaggiudica" onclick="doAction('<%=PSBD.ACTION_RIAGGIUDICAZIONE %>')"/></td>	
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