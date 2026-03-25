<% try{ %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="../errore.jsp" %>
<%@ include file="../include/newbasicHeader.inc" %>
<%@ include file="../include/controlloSessione.inc" %>
<%@ page import="it.avlp.simog.common.servlet.PSBD"%>
<%@ page import="it.avlp.simog.common.servlet.ParametriServlet" %>
<%@ page import="it.avlp.simog.common.servlet.ParametriServletInizioLavori" %>
<%@page import="it.avlp.simog.common.servlet.ParametriServletRubrica"%>
<%@ page import="it.avlp.simog.util.PageHelper"%>
<%@ page import="it.avlp.simog.beans.InfoGaraBean"%>
<%@ page import="it.avlp.simog.beans.PubblicazioneBean"%>
<%@ page import="it.avlp.simog.util.SimogProperties"%>
<%@ page import="it.avlp.simog.util.MessageHelper"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="h" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="theme/tabmenu.css"/>
<link rel="stylesheet" type="text/css" media="all" href="calendar/calendar-blue.css" title="win2k-cold-1" />
<script type="text/javascript" src="calendar/calendar.js"></script>
<%@ include file="../include/calendar-dynamic.inc" %>
<script type="text/javascript" src="calendar/calendar-setup.js"></script>
<%@ include file="../include/i18n-init.inc" %>
<script type="text/javascript" src="script/pageutils.js"></script>
<script type="text/javascript" src="xtree/treeutils.js"></script>
<%@ include file="/script/script.js" %>
<%@ include file="/script/domUtils.js" %> 
<script type="text/javascript" src="script/schedaA.js"></script>
<title><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.faseAggiudicazione" /> - <%=user.getProfilo()%></title>
</head>
<c:set var="datiGara" value="${sessionScope['dati_gara']}" />
<jsp:useBean id="schedaA" type="it.avlp.simog.beans.aggiudicazione.Scheda_A" class="it.avlp.simog.beans.aggiudicazione.Scheda_A" scope="request"></jsp:useBean>
<c:set var="rupOk" value="${(UTENTE.login eq datiGara.cfRup or datiGara.cfRup eq null ) and schedaA.aggiudicazione.flagAggiudPrincipale ne 'N'}" />
<c:set var="roByFlusso" value="${datiGara.deleted or rupOk eq false or UTENTE.ossReg or UTENTE.RASA eq true or  schedaA.delegaScheda eq true  or schedaA.riaggiudicata eq true}" />
<c:set var="readonly" value="${roByFlusso or schedaA.aggiudicazione.confirmed or schedaA.riaggiudicazione}" />
<c:set var="disabledStr" value="${readonly ? 'disabled':'' }" />
<c:set var="readonlyStr" value="${readonly? 'readonly' : ''}" />
<c:set var="saveAndResetDisabledStr" value="${ (roByFlusso or schedaA.aggiudicazione.confirmed) ? 'disabled' : ''  }"/>
<c:set var="noConf" value="${roByFlusso eq true or (schedaA.aggiudicazione.idAggiudicazione le 0) || schedaA.aggiudicazione.richAnn eq true or schedaA.aggiudicazione.confirmed}" />
<c:set var="annullabile" value="${ roByFlusso ne true and schedaA.aggiudicazione.confirmed eq true and schedaA.aggiudicazione.richAnn ne true and schedaA.aggiudicazione.richDelete ne true and variazioniAnagrafiche ne true}" />
<c:set var="cancellabile" value="${ roByFlusso ne true and schedaA.aggiudicazione.okCancellazione eq true  and variazioniAnagrafiche ne true}" />
<c:set var="variazAnagraf" value="${(schedaA.varAnagActive eq true and roByFlusso ne true and schedaA.aggiudicazione.confirmed eq true and variazioniAnagrafiche ne true and schedaA.aggiudicazione.richAnn ne true and schedaA.aggiudicazione.richDelete ne true)}" />
<c:set var="flagEnteSpeciale" value="${schedaA.infoComuni.flagEnteSpeciale}" />
<c:set var="saveAction" value="setAndSave"/>
   <c:if test="${variazioniAnagrafiche eq true}"><c:set var="saveAction" value="setAndVaria"/></c:if>

<c:set var="fromAVCPass" value="${false}" />
<c:set var="fromAVCPass" value="${schedaA.aggiudicazione.fromAVCPass}" />
<c:set var="readonlyAVCPass" value="${fromAVCPass ? 'readonly' : ''}" />
<c:set var="markFieldAVCPass" value="${fromAVCPass ? '*' : ''}" />

<body>

<!-- TB: ticket popup modali. Import css e js -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<div id="dialog"></div>
<!-- fine import popup modali -->
	<div id="gabbia">
	
	<%if(request.getAttribute("protect")!= null) {%>
		<%@ include file="/include/protect.inc" %>
	<%} %>	
		<%@ include file="../include/header.inc" %>	 
		<div class="bodypage-e">
			<form id="FormSchedaA" name="gestioneTab" action="<%=ParametriServlet.SRV_SCHEDA_A %>" method="post" onkeypress="setFormModified('Modificato')">
			<h1>Gestione Schede - Fase di Aggiudicazione</h1>
			<input type="hidden" name="checkIfOK" id="checkIfOK" value="<%=new Integer(session.getAttribute("checkIfOK").toString()) + 1%>" />
			<input type="hidden" name = "<%=PSBD.FIELD_NAME_ID_AGGIUDICAZIONE %>"  value="<c:out value='${schedaA.aggiudicazione.idAggiudicazione}' />" />
			<input type="hidden" name = "<%=PSBD.DATA_INIZIO_AGGIUDICAZIONE %>"  value="<c:out value='${schedaA.aggiudicazione.dataInizioAggiudicazione}' />" />
			<input type="hidden" name = "descrizioneStato"  value="<c:out value='${schedaA.aggiudicazione.descrizioneStato}' />" />
			<input type="hidden" name = "idStato"  value="<c:out value='${schedaA.aggiudicazione.idStato}' />" />
			<input type="hidden" name = "Modificato" id="Modificato" value="<c:out value="${param['modificato']}" />">						
			<input type="hidden" name="<%=PSBD.TAB%>" id="<%=PSBD.TAB%>" value="">	
			<input type="hidden" name="<%=PSBD.ACTION_TYPE%>" id="<%=PSBD.ACTION_TYPE%>" value="">	
			<input type="hidden" name="<%=PSBD.VAR_ANN%>"  value="${variazioniAnagrafiche}" />	
			<input type="hidden" name="<%=PSBD.FIELD_NAME_ORIGINE_SCHEDA%>" value="${schedaA.aggiudicazione.origine}" />
			<input type="hidden" name="<%=PSBD.ACTION_RIAGGIUDICAZIONE %><%=PSBD.DATA_INIZIO_AGGIUDICAZIONE%>" value="${schedaA.aggiudicazione.dataValidatore}" />
														
			<div  class="hmenu">	
			<ul> 		
				<li><a title="<utils:message key="scheda.mostraDatiComuni" plain="true" />" href="javascript:changePage('<%= PSBD.SRV_DATI_COMUNI %>','Modificato')"><utils:message key="scheda.mostraDatiComuni" /></a></li>
					<%String riScheda =ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=";%>
				<li><a title="<utils:message key="scheda.tornaListaAggiudicazioni" plain="true" />" href="javascript:changePage('<%=riScheda%><c:out value="${datiGara.idLotto}" />','Modificato')"><utils:message key="scheda.listaAggiudicazioni" /></a></li>			    
		 	   <c:if test="${UTENTE.RUP eq true && schedaA.riaggiudicata eq false and schedaA.aggiudicazione.confirmed and schedaA.aggiudicazione.idPubblicazioneAgg le 0 && schedaA.delegaScheda eq false}">
 	            <li><a title="<utils:message key="scheda.avvisoAggiudicazione" plain="true" />" href="<%=ParametriServlet.SRV_BANDO_GARA %>?toDo=<%=ParametriServlet.ACTION_CARICA_AVVISO %>&<%=ParametriServlet.SESSION_ID_GARA %>=${datiGara.idGara}&<%=ParametriServlet.TIPO_PUBBLICAZIONE %>=<%=ParametriServlet.PUBBLICAZIONE_AVVISO %>&<%=PSBD.FIELD_NAME_ID_AGGIUDICAZIONE %>=${schedaA.aggiudicazione.idAggiudicazione}&<%=PSBD.FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE %>=${schedaA.aggiudicazione.dataInizioAggiudicazione}"><utils:message key="scheda.avvisoAggiudicazione" /></a></li>						    	     	
            </c:if>
				<c:if test="${UTENTE.RUP eq true && schedaA.riaggiudicata eq false and schedaA.aggiudicazione.confirmed and schedaA.aggiudicazione.idPubblicazioneAgg ge 1 && schedaA.delegaScheda eq false}">
 	            <li><a title="<utils:message key="scheda.rettificaAvviso" plain="true" />" href="<%=ParametriServlet.SRV_GESTIONE_RETTIFICA %>?toDo=<%=ParametriServlet.ACTION_CARICA_RETTIFICA_AVVISO %>&<%=ParametriServlet.SESSION_ID_GARA %>=${datiGara.idGara}&<%=ParametriServlet.TIPO_PUBBLICAZIONE %>=RETTIFICA&<%=ParametriServlet.TIPO_OPERAZIONE %>=<%=ParametriServlet.PUBBLICAZIONE_RETTIFICA_AVVISO_AGG %>&<%=PSBD.FIELD_NAME_ID_AGGIUDICAZIONE %>=${schedaA.aggiudicazione.idAggiudicazione}&<%=PSBD.FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE %>=${schedaA.aggiudicazione.dataInizioAggiudicazione}"><utils:message key="scheda.rettificaAvviso" /></a></li>							    	     	
            </c:if>
		 	</ul>  					
			</div>
			<%@ include file="/include/gestisciErrore.inc" %>	
			<%@ include file="/include/RichAnnPanel.jsp" %>
			<%@ include file="/include/VarAnagPanel.jsp" %>

<% List<PubblicazioneBean> storicoPubblicazioni = (List)request.getAttribute(ParametriServlet.STORICO_PUBBLICAZIONI); %>
<%if (storicoPubblicazioni != null && !storicoPubblicazioni.isEmpty()) {%>
<fieldset>
<legend><utils:message key="scheda.storicoPubblicazioniAvvisi" /></legend>
<fieldset class="gara">
<div align="center">
<table width="100%">	
<tr>
<th class="garaTh"><utils:message key="scheda.tipoPubblicazione" /></th>
<th class="garaTh"><utils:message key="scheda.dataPubblicazione" /></th>
<th class="garaTh"><utils:message key="scheda.allegati" /></th>
</tr>
<% for (PubblicazioneBean pub : storicoPubblicazioni) {%>
<tr>
 <% String tipoPubblicazione = null; 
if(PubblicazioneBean.TipoOperazione.BANDO.getCodice().equals(pub.getTipoOperazione()))
 tipoPubblicazione = MessageHelper.getMessage(request, "scheda.bandoDiGara");
if(PubblicazioneBean.TipoOperazione.LETTINV.getCodice().equals(pub.getTipoOperazione()))
 tipoPubblicazione = MessageHelper.getMessage(request, "scheda.letteraInvito");
if(PubblicazioneBean.TipoOperazione.AVVISOAGG.getCodice().equals(pub.getTipoOperazione()))
 tipoPubblicazione = MessageHelper.getMessage(request, "scheda.avvisoAggiudicazione");
if(PubblicazioneBean.TipoOperazione.RETTIFICA.getCodice().equals(pub.getTipoOperazione()))
 tipoPubblicazione = MessageHelper.getMessage(request, "scheda.rettificaAvviso");%>
<td><%=tipoPubblicazione %></td>
<td><%=PageHelper.getViewDate(pub.getDataInizioPubblicazione()) %></td>
<td><input type="button" onclick="apripopup('<%=ParametriServlet.SRV_STORICO_ALLEGATI%>?<%=ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE %>=<%=pub.getIdPubblicazione() %>&<%=ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB %>=<%=pub.getDataInizioPubblicazione()%>'); return false;"; value="<utils:message key="button.visualizza" plain="true" />" /></td>    
</tr>
<% } %>
</table>		
</div>
</fieldset>									
</fieldset>
 <%} %>
	<fieldset>
	
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
				    	     
	<table>	
	<tr>
	<td><input  ${variazioniAnagrafiche eq true ? '' : saveAndResetDisabledStr} type="button" value="<utils:message key="button.salva" plain="true" />" onclick="${saveAction}('FormSchedaA','<%=PSBD.TAB_AGGIUDICAZIONE %>')"></td>
	<td><input ${noConf eq true ? 'disabled' : ''} type="button" value="<utils:message key="button.conferma" plain="true" />" onclick="setAndConfirm('FormSchedaA','<%= PSBD.TAB_AGGIUDICAZIONE %>')"></td>
	<td><input  ${variazioniAnagrafiche eq true ? '' : saveAndResetDisabledStr}  type="button" value="<utils:message key="button.reimposta" plain="true" />" onclick="reimposta('<%= PSBD.TAB_AGGIUDICAZIONE%>')"></td>
	<c:if test="${annullabile}">
		<td><input type="button" value="<utils:message key="scheda.richiediAnnullamento" plain="true" />" onclick="loadAnnullamentJSP('<%= PSBD.TAB_AGGIUDICAZIONE %>')"></td>
	</c:if>
	<c:if test="${!fromAVCPass && cancellabile}">
		<td><input type="button" value="<utils:message key="scheda.richiediCancellazione" plain="true" />" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
	</c:if>		
	<c:if test="${variazAnagraf}">
		<td><input type="button" value="<utils:message key="scheda.comunicaVariazioniAnagrafiche" plain="true" />" onclick="doAction('<%=PSBD.ACTION_VARIAZIONI_ANAGRAFICHE %>')"/></td>	
	</c:if>		
	<c:if test="${roByFlusso eq false and schedaA.riaggiudicabile eq true}">
		<td><input type="button" value="<utils:message key="scheda.riaggiudica" plain="true" />" onclick="doAction('<%=PSBD.ACTION_RIAGGIUDICAZIONE %>')"/></td>	
	</c:if>		
	<c:set var="statoid" value="${schedaA.aggiudicazione.idStato}"/>
	<c:set var="statoann" value="${schedaA.aggiudicazione.richAnn || schedaA.aggiudicazione.richDelete}"/>			
	<c:set var="statodesc" value="${schedaA.aggiudicazione.descrizioneStato}"/>
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
	<c:set var="aggiudicazione" value="${schedaA.aggiudicazione}" scope="page"></c:set>
	<%@ include file="/scheda1/aggiudicazioni.jsp" %>    
	</div>
	</td>
	</tr>																						
	</table>
	<table>  
	<tr>
	<td><input  ${variazioniAnagrafiche eq true ? '' : saveAndResetDisabledStr} type="button" value="Salva" onclick="${saveAction}('FormSchedaA','<%=PSBD.TAB_AGGIUDICAZIONE %>')"></td>
	<td><input ${noConf eq true ? 'disabled' : ''} type="button" value="Conferma" onclick="setAndConfirm('FormSchedaA','<%= PSBD.TAB_AGGIUDICAZIONE %>')"></td>
	<td><input  ${variazioniAnagrafiche eq true ? '' : saveAndResetDisabledStr}  type="button" value="Reimposta" onclick="reimposta('<%= PSBD.TAB_AGGIUDICAZIONE%>')"></td>
	<c:if test="${annullabile}">
		<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="loadAnnullamentJSP('<%= PSBD.TAB_AGGIUDICAZIONE %>')"></td>
	</c:if>
	<c:if test="${!fromAVCPass && cancellabile}">
		<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
	</c:if>		
	<c:if test="${variazAnagraf}">
		<td><input type="button" value="Comunica Variazioni Anagrafiche" onclick="doAction('<%=PSBD.ACTION_VARIAZIONI_ANAGRAFICHE %>')"/></td>
	</c:if>		
	<c:if test="${roByFlusso eq false and schedaA.riaggiudicabile eq true}">
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
<% }catch (Exception e){e.printStackTrace();} %>