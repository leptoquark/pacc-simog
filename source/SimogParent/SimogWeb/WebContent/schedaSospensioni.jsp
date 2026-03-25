<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

<%@page import="it.avlp.simog.common.servlet.ParametriServletSospensioni"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% int indiceTab = 0;%>
<%--Carico la lista delle schede gia compilate e i dati della gara --%>
 <c:set var="listaSchede" value="${sessionScope['lista_sospensioni']}"></c:set>
 <c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>
 <jsp:useBean id="schedaSospensioni" type="it.avlp.simog.beans.sospensioni.SchedaSospensione" class="it.avlp.simog.beans.sospensioni.SchedaSospensione" scope="request"></jsp:useBean>
 
  <jsp:useBean id="aggiudicazione" type="it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean" class="it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean" scope="request"></jsp:useBean>
 <%
 	SospensioniBean sospensioni = schedaSospensioni.getSospensioneFE();
 	pageContext.setAttribute("sospensioni",sospensioni);
%>
<c:set var="rupOk" value="${(UTENTE.login eq datiGara.cfRup or datiGara.cfRup eq null ) and aggiudicazione.flagAggiudPrincipale ne 'N'}" />
 <c:set var="hide" value="${(datiGara.deleted || 
 								sospensioni.confirmed) || 
 								rupOk eq false  || 
 								 schedaSospensioni.delegaScheda ||  schedaSospensioni.riaggiudicata ||
 								UTENTE.ossReg || UTENTE.RASA ||
 								(sospensioni.idSospensione < 1 && !schedaSospensioni.aggiungibile)}" />
 								<%-- Eliminato schedaSospensioni.readOnly eq true || --%>
 <c:set var="annullabile" value="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && 
 									datiGara.deleted ne true && 
 									sospensioni.confirmed eq true && 
 									sospensioni.richAnn ne true && 
 									schedaSospensioni.readOnly eq false
 									&& sospensioni.richDelete ne true && schedaSospensioni.delegaScheda eq false  and  schedaSospensioni.riaggiudicata eq false}"></c:set>
 <c:set var="disabled" value="${hide ? 'disabled':'' }"></c:set>
 <c:set var="noConf" value="${(hide || 
  								(sospensioni.idSospensione le 0)) || 
  								sospensioni.richAnn eq true ? 'disabled':''}"></c:set>
 <c:set var="modificabile" value="${rupOk && sospensioni.confirmed && 
 								    schedaSospensioni.modificabile eq true && schedaSospensioni.delegaScheda eq false and  schedaSospensioni.riaggiudicata eq false}"></c:set> 

<%-- tests output

<p>hide[schedaSospensioni.readOnly eq true] - <c:out value="${schedaSospensioni.readOnly eq true}"></c:out></p>
								
<p>rupOK - <c:out value="${rupOk}"></c:out></p>
<p>hide - <c:out value="${hide}"></c:out></p>
<p>annullabile - <c:out value="${annullabile}"></c:out></p>
<p>disabled - <c:out value="${disabled}"></c:out></p>
<p>noConf - <c:out value="${noConf}"></c:out></p>
 --%>
 
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

<title><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.sospensioni" /> - <%= user.getProfilo() %></title>

</head>

<body>
	<div id="gabbia" align="left">
		<%@ include file="/include/header.inc" %>			
		<div class="bodypage-e" align="left">
		<%--Header Scheda e Lista Schede gia compilate --%>
			<h1><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.sospensioni" /></h1>
			<div  class="hmenu" align="left">	
			  <ul>
			    <%String riScheda =ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=";%>
			     
			      
					<li><a title="<utils:message key="scheda.tornaListaAggiudicazioni" plain="true" />" href="javascript:changePage('<%=riScheda%>${datiGara.idLotto}','Modificato')"><utils:message key="scheda.listaAggiudicazioni" /></a></li>  
			    <c:if test="${!UTENTE.ossReg && !UTENTE.RASA && sospensioni.idSospensione > 0 && datiGara.deleted eq false && rupOk eq true && schedaSospensioni.aggiungibile && schedaSospensioni.delegaScheda eq false and  schedaSospensioni.riaggiudicata eq false}">
			      <li>
			        <c:url  value="<%=ParametriServletSospensioni.SRV_SCHEDA_SOSPENSIONI %>" var="newMod">
					    <c:param name="toDo" value="load"></c:param>
					    	<c:param name="toEdit" value="-1"></c:param>
				      </c:url>
				      <a href="<c:out value='${newMod}'/>" ><utils:message key="scheda.aggiungiNuovaScheda" /></a>
				 </li>
				</c:if>
				</ul>
			</div> 
			<%--Errori.... --%>
			<%@ include file="/include/gestisciErrore.inc" %>
			  
			  <%--Carico la scheda corrente e la lista delle schede --%>
			 <h2><utils:message key="scheda.listaSchedeSospensioni" /></h2>
		<div style="overflow: auto;height: 13em; width: 100%;"  >
			
			<div class="gara">	 	
			 
		     <table align="center" width="300px">   
			<tr> 
		     	<th class="garaTh">Data Verbale Sospensione</th> 
		     	<th class="garaTh">Data Verbale Ripresa</th> 
		     	<th class="garaTh">Motivazione </th>
		     	<th class="garaTh">Stato scheda</th>
		     	<th class="garaTh">Azioni</th>
		     </tr>
		        <c:set var="counter" value='0' scope="page"/>
			<c:forEach items="${listaSchede}" var="scheda">
				<tr>
					<td class="garaTd"><c:out value="${scheda.dataVerbSosp}"></c:out></td>
				 	<td  class="garaTd"><c:out value="${scheda.dataVerbRipr}"></c:out></td>
					<td  class="garaTd"><c:out value="${scheda.descrizioneMotivo}"></c:out></td>
					<td  class="garaTd"><c:out value="${scheda.descrizioneStato}"></c:out></td>
					
					<td class="hmenu">
					    <c:url  value="<%= ParametriServletSospensioni.SRV_SCHEDA_SOSPENSIONI %>" var="modURL">
					    <c:param name="toDo" value="load"></c:param>
					    	<c:param name="toEdit" value="${counter}"></c:param>
					    </c:url>
					    <c:choose >
					    	<c:when test="${!UTENTE.ossReg && !UTENTE.RASA && datiGara.deleted eq false && rupOk eq true && scheda.confirmed eq false && schedaSospensioni.delegaScheda eq false and  schedaSospensioni.riaggiudicata eq false}">
					    		<a href="<c:out value='${modURL}'/>">Modifica</a>
					    	</c:when>
					    	<c:otherwise>
					    		<a href="<c:out value='${modURL}'/>">Visualizza</a>
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
		
				<table>	
					<tr>
						<c:if test="${!modificabile}"><td><input <c:out value="${disabled}"/> type="button" value="Salva" onclick="checkAndAction('check',2,'<%=PSBD.ACTION_SALVA %>')"/></td></c:if>
						<c:if test="${modificabile}"><td><input type="button" value="Modifica" onclick="checkAndAction('check',2,'<%=PSBD.ACTION_MODIFICA %>')"/></td></c:if>
						<td><input <c:out value="${noConf}"/>  type="button" value="Conferma" onclick="checkAndAction('check',2,'<%=PSBD.ACTION_CONFERMA %>')"/></td>
						<td><input <c:out value="${disabled}"/> type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_REIMPOSTA %>')"/></td>
						<c:if test="${rupOk eq true && datiGara.deleted ne true && sospensioni.confirmed eq true && sospensioni.richAnn ne true && schedaSospensioni.delegaScheda eq false and  schedaSospensioni.riaggiudicata eq false}">
							<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')"/></td>	
						</c:if>	
						<c:if test="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && sospensioni.okCancellazione eq true && schedaSospensioni.delegaScheda eq false and  schedaSospensioni.riaggiudicata eq false}">
							<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
						</c:if>
				<c:set var="statoid" value="${sospensioni.idStato}"/>
				<c:set var="statoann" value="${sospensioni.richAnn || sospensioni.richDelete}"/>
				<c:set var="statodesc" value="${sospensioni.descrizioneStato}"/>
				<%@ include file="../include/statoscheda.inc" %>
								
					</tr>
				</table>
			 <fieldset>
			 <h2>Scheda SOSPENSIONI - <c:out value="${sospensioni.idSospensione < 1 ?  'Inserimento' : (hide == true ? 'Visualizzazione' : 'Modifica')}" /></h2>
			<form action="<%=ParametriServletSospensioni.SRV_SCHEDA_SOSPENSIONI%>" method="post" onkeypress="setFormModified('Modificato')" >
		    <input type="hidden" name="checkIfOK" id="checkIfOK" value="<%=new Integer(session.getAttribute(ParametriServlet.checkIfOK).toString()) + 1%>" />
			
			<%--Campi hidden e altro, copiati dalle altre schede, non so se servono. DA VERIFICARE --%>						
				<input type="hidden" name="<%=PSBD.TAB%>" id="<%=PSBD.TAB%>" value="" />	
				<input type="hidden" name="<%=PSBD.ACTION_TYPE%>" id="<%=PSBD.ACTION_TYPE%>" value="" />
				
				<input type="hidden" name="<%=ParametriServletSospensioni.FIELD_NAME_ID_SOSPENSIONE %>" value='<c:out value="${sospensioni.idSospensione}"/>'/>
				<input type="hidden" name="<%=ParametriServletSospensioni.FIELD_NAME_DATA_INIZIO_SOSP %>" value='<c:out value="${sospensioni.dataInizioSosp}"/>'/>
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
						<td align="center" colspan="2"><p class="detailHelp"><strong>SOSPENSIONI DELL'ESECUZIONE</strong></p></td>
					</tr>
					
					
					
					
					
					<%-- ********************************************************************************************** --%>
					<%--                       Seconda riga della scheda : Data verbale sospensione                     --%>
					<%-- ********************************************************************************************** --%>
					<tr>
	   					<td><label for="<%= ParametriServletSospensioni.FIELD_NAME_DATA_VERB_SOSP %>" >Data del verbale di sospensione</label></td>
	   					<td>
							<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  <c:out value="${disabled}"/>
							type="text" id="<%= ParametriServletSospensioni.FIELD_NAME_DATA_VERB_SOSP %>" name="<%= ParametriServletSospensioni.FIELD_NAME_DATA_VERB_SOSP %>" 
							onblur="Calendar.validaData(this)" value="<c:out value='${sospensioni.dataVerbSosp}'/>" />
							<c:if test="${hide == false}">
								<img src="calendar/img.gif" id="calendarVerbSosp" style="cursor: pointer; border: 1px solid red;" title="Date selector"
											onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
								<script type="text/javascript">
									    Calendar.setup({
								        inputField     :    "<%= ParametriServletSospensioni.FIELD_NAME_DATA_VERB_SOSP %>",     // id of the input field
								        ifFormat       :    "%d/%m/%Y",      // format of the input field
								        button         :    "calendarVerbSosp",  // trigger for the calendar (button ID)
								        align          :    "Tl",           // alignment (defaults to "Bl")
								        singleClick    :    true							       
							    		});					    	
								</script>
							</c:if>
						</td>
	   				</tr>
	   				
	   				
	   				
	   				<%-- ********************************************************************************************** --%>
					<%--                        Terza riga della scheda : Data verbale di ripresa                       --%>
					<%-- ********************************************************************************************** --%>
	   				<tr>
	   					<td><label for="<%= ParametriServletSospensioni.FIELD_NAME_DATA_VERB_RIPR %>" >Data del verbale ripresa</label></td>
	   					<td>
							<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  <c:out value="${!modificabile ? disabled : ''}"/>
							type="text" id="<%= ParametriServletSospensioni.FIELD_NAME_DATA_VERB_RIPR %>" name="<%= ParametriServletSospensioni.FIELD_NAME_DATA_VERB_RIPR %>" 
							onblur="Calendar.validaData(this)" value="<c:out value='${sospensioni.dataVerbRipr}'/>" />			
							<c:if test="${hide == false || modificabile == true}">
								<img src="calendar/img.gif" id="calendarVerbRipr" style="cursor: pointer; border: 1px solid red;" title="Date selector"
											onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
								<script type="text/javascript">
									    Calendar.setup({
								        inputField     :    "<%= ParametriServletSospensioni.FIELD_NAME_DATA_VERB_RIPR %>",     // id of the input field
								        ifFormat       :    "%d/%m/%Y",      // format of the input field
								        button         :    "calendarVerbRipr",  // trigger for the calendar (button ID)
								        align          :    "Tl",           // alignment (defaults to "Bl")
								        singleClick    :    true							       
							    		});					    	
								</script>
							</c:if>
						</td>
	   				</tr>
	   				<%-- ************************************************************************************************ --%>
	   				
	   				
	   				
	   				
	   				
	   				<%-- ********************************************************************************************** --%>
					<%--                   Quarta riga della scheda : Motivazione della sospensione                     --%>
					<%-- ********************************************************************************************** --%>
					
					<tr>
	   					<td><label for="<%= ParametriServletSospensioni.FIELD_NAME_ID_MOTIVO_SOSP %>" >Motivazione della sospensione</label></td>
	   					
	   					<td width="40%" > 
	   						<select onchange="setFormModified('Modificato')" tabindex="<%=++indiceTab%>" style="width:100%" <c:out value="${disabled}" /> name="<%= ParametriServletSospensioni.FIELD_NAME_ID_MOTIVO_SOSP %>" id=<%= ParametriServletSospensioni.FIELD_NAME_ID_MOTIVO_SOSP %> CLASS="BOTTONE">
								<option></option>
				  				<c:set var="idMotivoSospensione" value="${sospensioni.idMotivoSosp}" scope="request" />
				  				
				  				<u:options name="<%= ParametriServlet.MOTIVI_SOSPENSIONE_BEAN %>" scope="request" value="idMotivoSospensione"/>
							</select>
						</td>
	   				</tr>
	   				
	   				<%-- ********************************************************************************************** --%>
	   				
	   				
	   				
	   				
	   				<%-- ********************************************************************************************** --%>
					<%--                       Quinta riga della scheda : Superamento quarto del mese                   --%>
					<%-- ********************************************************************************************** --%>
	   				
	   				<tr>
		   				<td><label for="<%= ParametriServletSospensioni.FIELD_NAME_FLAG_SUPERO_TEMP %>" >E' stato superato il quarto del tempo contrattuale</label></td>
	   					<td width="40%" > 
	   						<select tabindex="<%=++indiceTab%>" name="<%= ParametriServletSospensioni.FIELD_NAME_FLAG_SUPERO_TEMP %>" <c:out value="${!modificabile ? disabled : ''}"/>>
	   							<option></option>
	   							<option <c:out value="${sospensioni.flagSuperoTemp == 'S' ? 'selected' : ''}" /> value="S">SI</option>
	   							<option <c:out value="${sospensioni.flagSuperoTemp == 'N' ? 'selected' : ''}" /> value="N">NO</option>
	   						</select>
	   					</td>
	   				</tr>
	   				
	   				<%-- ********************************************************************************************** --%>
	   				
	   				
	   				
	   				<%-- ********************************************************************************************** --%>
	   				<%--                                 Sesta riga: Iscrizione di riserve                              --%>
	   				<%-- ********************************************************************************************** --%>
	   				<tr>
	   					<td><label for="<%= ParametriServletSospensioni.FIELD_NAME_FLAG_RISERVE %>" >Iscrizione di riserve dell'appaltatore nei verbali di sospensione e/o ripresa dei lavori </label></td>
	   					<td width="40%" > 
	   						<input tabindex="<%=++indiceTab%>" id="check1Y" type="radio" name="<%= ParametriServletSospensioni.FIELD_NAME_FLAG_RISERVE %>" value="S" <c:out value="${sospensioni.flagRiserve == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/>/>SI 
	   						<input tabindex="<%=++indiceTab%>" id="check1N" type="radio" name="<%= ParametriServletSospensioni.FIELD_NAME_FLAG_RISERVE %>" value="N" <c:out value="${sospensioni.flagRiserve == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/>/>NO
	   						</td>
	   				</tr>
	   				<%-- ********************************************************************************************** --%>
	   				
	   				<%-- ********************************************************************************************** --%>
	   				<%--                                  Settima riga: verbali non sottoscritti                        --%>
	   				<%-- ********************************************************************************************** --%>
	   				<tr>
	   					<td><label for="<%= ParametriServletSospensioni.FIELD_NAME_FLAG_VERBALE %>" >Verbale/i non sottoscritti dall'appaltatore</label></td>
	   					<td width="40%" > 
	   						<input tabindex="<%=++indiceTab%>" id="check2Y" type="radio" name="<%= ParametriServletSospensioni.FIELD_NAME_FLAG_VERBALE %>" value="S" <c:out value="${sospensioni.flagVerbale == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/>/>SI 
	   						<input tabindex="<%=++indiceTab%>" id="check2N" type="radio" name="<%= ParametriServletSospensioni.FIELD_NAME_FLAG_VERBALE %>" value="N" <c:out value="${sospensioni.flagVerbale == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/>/>NO</td>
	   				</tr>
	   				<%-- ********************************************************************************************** --%>
				  </table>
				  <input type="hidden"  value="save" name="toDo" id="toDo"/>
				
			</fieldset>
			 
				<table>	
					<tr>
						<c:if test="${!modificabile}"><td><input <c:out value="${disabled}"/> type="button" value="Salva" onclick="checkAndAction('check',2,'<%=PSBD.ACTION_SALVA %>')"/></td></c:if>
						<c:if test="${modificabile}"><td><input type="button" value="Modifica" onclick="checkAndAction('check',2,'<%=PSBD.ACTION_MODIFICA %>')"/></td></c:if>
						<td><input <c:out value="${noConf}"/>   id="Conferma" type="button" value="Conferma" onclick="checkAndAction('check',2,'<%=PSBD.ACTION_CONFERMA %>')"/></td>
						<td><input <c:out value="${disabled}"/> id="Reimposta" type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_REIMPOSTA %>')"/></td>
						<c:if test="${rupOk eq true && datiGara.deleted ne true && sospensioni.confirmed eq true && sospensioni.richAnn ne true && schedaSospensioni.delegaScheda eq false}">
							<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')"/></td>	
						</c:if>	
						<c:if test="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && sospensioni.okCancellazione eq true && schedaSospensioni.delegaScheda eq false and  schedaSospensioni.riaggiudicata eq false}">
							<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
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


<%@page import="it.avlp.simog.beans.sospensioni.SospensioniBean"%>
<%@page import="it.avlp.simog.beans.sospensioni.SchedaSospensione"%>
</html>