<%@page import="java.util.HashMap"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>

<%--             IMPORT                        --%>
<%@ page import="it.avlp.simog.common.servlet.ParametriServletAccordo"%>
<%@ page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@ page import="it.avlp.simog.common.servlet.PSBD"%>
<%@ page import="it.avlp.simog.beans.StatiScheda"%>

<%--             INCLUDE                       --%>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>

<%--             TAGLIB                        --%>
<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% int indiceTab = 0;%>

<%--Carico la lista delle schede gia compilate e i dati della gara --%>
 <c:set var="listaSchede" value="${sessionScope['lista_accordo']}"></c:set>
 <c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>
 
 
 <%-- ************************       Creo il Bean   ACCORDO        ********************************************** --%>
 <jsp:useBean id="schedaAccordo" type="it.avlp.simog.beans.accordi.SchedaAccordo" class="it.avlp.simog.beans.accordi.SchedaAccordo" scope="request"></jsp:useBean>

 <jsp:useBean id="aggiudicazione" type="it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean" class="it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean" scope="request"></jsp:useBean>

<% it.avlp.simog.beans.accordi.AccordoBean accordo = schedaAccordo.getAccordoFE();
	pageContext.setAttribute("accordo",accordo);
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

<c:set var="rupOk" value="${(UTENTE.login eq datiGara.cfRup or datiGara.cfRup eq null ) and aggiudicazione.flagAggiudPrincipale ne 'N'}" />
 <c:set var="hide" value="${(datiGara.deleted || accordo.confirmed) || rupOk eq false || UTENTE.ossReg || UTENTE.RASA || (accordo.idAccordo < 1 && !schedaAccordo.aggiungibile) || schedaAccordo.delegaScheda || schedaAccordo.riaggiudicata}" />
 <c:set var="annullabile" value="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && datiGara.deleted ne true && accordo.confirmed eq true && accordo.richAnn ne true && accordo.richDelete ne true && schedaAccordo.delegaScheda eq false  and schedaAccordo.riaggiudicata eq false}"></c:set>
 <c:set var="disabled" value="${hide ? 'disabled':'' }"></c:set>
 <c:set var="noConf" value="${(hide || (accordo.idAccordo le 0)) || accordo.richAnn eq true ? 'disabled':''}"></c:set>
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

<title><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.accordi" /> - <%= user.getProfilo() %></title>

</head>
<body>
	<div id="gabbia" align="left">
		<%@ include file="/include/header.inc" %>			
		<div class="bodypage-e" align="left">
		<%--Header Scheda e Lista Schede gia compilate --%>
			<h1><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.accordi" /></h1>
			<div  class="hmenu" align="left">	
			  <ul>
			  	<%String riScheda =ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=";%>
			     <li><a title="<utils:message key="scheda.tornaListaAggiudicazioni" plain="true" />" href="javascript:changePage('<%=riScheda%>${datiGara.idLotto}','Modificato')"><utils:message key="scheda.listaAggiudicazioni" /></a></li>  
			     <c:if test="${!UTENTE.ossReg && !UTENTE.RASA && accordo.idAccordo > 0 && datiGara.deleted eq false && rupOk eq true && schedaAccordo.aggiungibile && 
			     schedaAccordo.delegaScheda eq false  and schedaAccordo.riaggiudicata eq false}">
			     <li>
			        <c:url  value="<%=ParametriServletAccordo.SRV_SCHEDA_ACCORDO %>" var="newMod">
					    <c:param name="toDo" value="load"></c:param>
					  	<c:param name="toEdit" value="-1"></c:param>
				    </c:url>
				    <a href="<c:out value='${newMod}'/>" >Aggiungi nuova scheda</a>
				 </li>
				</c:if>
				</ul>
			</div> 
			<%--Errori.... --%>
			<%@ include file="/include/gestisciErrore.inc" %>
			 
			<%--Carico la scheda corrente e la lista delle schede --%>
			 <h2>Lista Schede Accordi</h2>
		<div style="overflow: auto;height: 13em; width: 100%;"  >
			
			<div class="gara">	 	
			 
		     <table align="center" width="300px">   
			<tr> 
		     	<th class="garaTh">Data Accordo Bonario</th> 
		     	<th class="garaTh">OneriDerivanti</th> 
		     	<th class="garaTh">N. Riserve transate </th>
		     	<th class="garaTh">Stato scheda</th>
		     	<th class="garaTh">Azioni</th>
		     </tr>
		        <c:set var="counter" value='0' scope="page"/>
			<c:forEach items="${listaSchede}" var="scheda">
				<tr>
					<td class="garaTd"><c:out value="${scheda.dataAccordo}"></c:out></td>
				 	<td class="garaTd"><c:out value="${scheda.oneriDerivantiStr}"></c:out></td>
					<td class="garaTd"><c:out value="${scheda.numeroRiserve}"></c:out></td>
					<td class="garaTd"><c:out value="${scheda.descrizioneStato}"></c:out></td>
					
					<td class="hmenu">
					    <c:url  value="<%= ParametriServletAccordo.SRV_SCHEDA_ACCORDO %>" var="modURL">
					    <c:param name="toDo" value="load"></c:param>
					    	<c:param name="toEdit" value="${counter}"></c:param>
					    </c:url>
					    <c:choose >
					    	<c:when test="${!UTENTE.ossReg && !UTENTE.RASA && datiGara.deleted eq false && rupOk eq true && scheda.confirmed eq false && schedaAccordo.delegaScheda eq false  and schedaAccordo.riaggiudicata eq false}">
					    		<a href="<c:out value='${modURL}'/>">Modifica</a>
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
		
		<%-- PANNELLO DELLE RICHIESTE DI ANNULLAMENTO DELLA SCHEDA [DISATTIVATO] --%>
		<%@ include file="../include/RichAnnPanel.jsp" %>
		<%-- --%>		
		
				<table >	
					<tr>
					   
						<td><input <c:out value="${disabled}"/> type="button" value="Salva" onclick="checkAndAction('check',0,'<%=PSBD.ACTION_SALVA %>')"/></td>
						<td><input <c:out value="${noConf}"/>  type="button" value="Conferma" onclick="checkAndAction('check',0,'<%=PSBD.ACTION_CONFERMA %>')"/></td>
						<td><input <c:out value="${disabled}"/> type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_REIMPOSTA %>')" /></td>
						<c:if test="${annullabile eq true}">
							<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')"/></td>	
						</c:if>		
						<c:if test="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && accordo.okCancellazione eq true && schedaAccordo.delegaScheda eq false  and schedaAccordo.riaggiudicata eq false}">
							<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
						</c:if>
				<c:set var="statoid" value="${accordo.idStato}"/>
				<c:set var="statoann" value="${accordo.richAnn || accordo.richDelete}"/>
				<c:set var="statodesc" value="${accordo.descrizioneStato}"/>

				<%@ include file="../include/statoscheda.inc" %>
							
					</tr>
				</table>
			 <fieldset>
			 <h2>Scheda ACCORDI - <c:out value="${accordo.idAccordo < 1 ?  'Inserimento' : (hide == true ? 'Visualizzazione' : 'Modifica')}" /></h2>
			<form action="<%=ParametriServletAccordo.SRV_SCHEDA_ACCORDO%>" method="post" onkeypress="setFormModified('Modificato')" >
			 <input type="hidden" name="checkIfOK" id="checkIfOK" value="<%=new Integer(session.getAttribute(ParametriServlet.checkIfOK).toString()) + 1%>" />			
			
			<%--Campi hidden e altro, copiati dalle altre schede, non so se servono. DA VERIFICARE --%>						
				<input type="hidden" name="<%=PSBD.TAB%>" id="<%=PSBD.TAB%>" value="" />	
				<input type="hidden" name="<%=PSBD.ACTION_TYPE%>" id="<%=PSBD.ACTION_TYPE%>" value="" />
				
				<input type="hidden" name="<%=ParametriServletAccordo.FIELD_NAME_ID_ACCORDO %>" value='<c:out value="${accordo.idAccordo}"/>'/>
				<input type="hidden" name="<%=ParametriServletAccordo.FIELD_NAME_DATA_INIZIO_ACC %>" value='<c:out value="${accordo.dataInizioAccordo}"/>'/>
				<input type="hidden" id="Modificato"  value="0" />
				
				<fieldset class="gara">
					
				  <table width="100%">
				  	<tr>
						<td align="center" colspan="2"><p class="detailHelp"><strong>RIFERIMENTO AI DATI DELLA FASE DI AGGIUDICAZIONE O DI DEFINIZIONE DI PROCEDURA NEGOZIATA</strong></p></td>
					</tr>
					
					
					
					
					<%-- ********************************************************************************************** --%>
					<%--                                    Prima riga della scheda : CIG                               --%>
					<%-- ********************************************************************************************** --%>
	   				<%@include file="/include/intestazione.jsp" %>
	   				<tr>
						<td align="center" colspan="2"><p class="detailHelp"><strong>ACCORDI BONARI</strong></p></td>
					</tr>
					
					
					
					
					
					<%-- ********************************************************************************************** --%>
					<%--                       Seconda riga della scheda : Data accordo bonario                         --%>
					<%-- ********************************************************************************************** --%>
					<tr>
	   					<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataAccordo") %> for="<%= ParametriServletAccordo.FIELD_NAME_DATA_ACCORDO %>" >Data dell'accordo bonario*</label></td>
	   					<td>
							<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  <c:out value="${disabled}"/>
							type="text" id="<%= ParametriServletAccordo.FIELD_NAME_DATA_ACCORDO %>" name="<%= ParametriServletAccordo.FIELD_NAME_DATA_ACCORDO %>" 
							onblur="Calendar.validaData(this)" value="<c:out value='${accordo.dataAccordo}'/>" />
							<c:if test="${hide == false}">
								<img src="calendar/img.gif" id="calendarVerbAcc" style="cursor: pointer; border: 1px solid red;" title="Date selector"
											onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
								<script type="text/javascript">
									    Calendar.setup({
								        inputField     :    "<%= ParametriServletAccordo.FIELD_NAME_DATA_ACCORDO %>",     // id of the input field
								        ifFormat       :    "%d/%m/%Y",      // format of the input field
								        button         :    "calendarVerbAcc",  // trigger for the calendar (button ID)
								        align          :    "Tl",           // alignment (defaults to "Bl")
								        singleClick    :    true							       
							    		});					    	
								</script>
							</c:if>
						</td>
	   				</tr>
	   				
	   				
	   				
	   				<%-- ********************************************************************************************** --%>
					<%--                        Terza riga della scheda : Oneri derivanti                               --%>
					<%-- ********************************************************************************************** --%>
	   				<tr>
	   					<td><label for="<%= ParametriServletAccordo.FIELD_NAME_ONERI_DERIVANTI%>" >Oneri derivanti</label></td>
	   					<td>
							<input  name="<%= ParametriServletAccordo.FIELD_NAME_ONERI_DERIVANTI%>"  tabindex="<%=++indiceTab%>" style="text-align:right;" <c:out value="${disabled}"/>
							type="text" value="<c:out value='${accordo.oneriDerivantiStr}'/>"  onblur="validateAmount(this)"/>
							
						</td>
	   				</tr>
	   				<%-- ************************************************************************************************ --%>
	   				
	   				
	   				
	   				
	   				
	   				<%-- ********************************************************************************************** --%>
					<%--                   Quarta riga della scheda : N. riserve transate                               --%>
					<%-- ********************************************************************************************** --%>
					
					<tr>
	   					<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_NumRiserve") %> for="<%= ParametriServletAccordo.FIELD_NAME_NUM_RISERVE%>" >Numero di riserve transate</label></td>
	   					<td>
							<input  name="<%= ParametriServletAccordo.FIELD_NAME_NUM_RISERVE%>"  tabindex="<%=++indiceTab%>" style="text-align:right;" <c:out value="${disabled}"/>
							type="text" value="<c:out value='${accordo.numeroRiserve}'/>"  onblur="validateNumber(this)"/>
							
						</td>
	   				</tr>
	   				
	   				<%-- ********************************************************************************************** --%>
				  </table>
				  <input type="hidden"  value="save" name="toDo" id="toDo"/>
				
			</fieldset>
			 
				<table >	
					<tr>
						<td><input <c:out value="${disabled}"/> type="button" value="Salva" onclick="checkAndAction('check',0,'<%=PSBD.ACTION_SALVA %>')"/></td>
						<td><input <c:out value="${noConf}"/>  type="button" value="Conferma" onclick="checkAndAction('check',0,'<%=PSBD.ACTION_CONFERMA %>')"/></td>
						<td><input <c:out value="${disabled}"/> type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_REIMPOSTA %>')" /></td>
						<c:if test="${annullabile eq true}">
							<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')"/>	</td>
						</c:if>		
						<c:if test="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && accordo.okCancellazione eq true && schedaAccordo.delegaScheda eq false  and schedaAccordo.riaggiudicata eq false}">
							<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
						</c:if>
				<c:set var="statoid" value="${accordo.idStato}"/>
				<c:set var="statoann" value="${accordo.richAnn}"/>
				<c:set var="statodesc" value="${accordo.descrizioneStato}"/>
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