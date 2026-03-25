<%@ page language="java" contentType="text/html; charset=UTF-8" errorPage="errore.jsp"%>

<%@page import="it.avlp.simog.common.servlet.ParametriServletSubappalti"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletRubrica"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>

<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@page import="java.util.HashMap"%>
<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  
 <% int indiceTab = 2;%>

<%--Carico la lista delle schede gia compilate e i dati della gara --%>
<c:set var="listaSchede" value="${sessionScope['lista_subappalti']}"></c:set>
<c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>

<jsp:useBean id="schedaSubappalti" type="it.avlp.simog.beans.subappalti.SchedaSubAppalti" class="it.avlp.simog.beans.subappalti.SchedaSubAppalti" scope="request"></jsp:useBean>

<c:set var="aggiudicazione" value="${schedaSubappalti.aggiudicazione}" scope="page"></c:set>

<%
	SubappaltiBean subappalti = schedaSubappalti.getSubAppaltiFE();
	pageContext.setAttribute("subappalti",subappalti);
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
 <c:set var="hide" value="${(datiGara.deleted || subappalti.confirmed) || rupOk eq false || UTENTE.ossReg || UTENTE.RASA || (subappalti.idRecord < 1 && !schedaSubappalti.aggiungibile) || schedaSubappalti.delegaScheda || schedaSubappalti.riaggiudicata }" />
  <c:set var="annullabile" value="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && datiGara.deleted ne true && subappalti.confirmed eq true && subappalti.richAnn ne true && subappalti.richDelete ne true && schedaSubappalti.delegaScheda eq false and schedaSubappalti.riaggiudicata eq false}"></c:set>
 <c:set var="disabled" value="${hide ? 'disabled':'' }"></c:set>
<c:set var="noConf" value="${(hide || (subappalti.idRecord le 0)) || subappalti.richAnn eq true ? 'disabled':''}"></c:set>
 <c:set var="modificabile" value="${rupOk && subappalti.confirmed && 
 								    schedaSubappalti.modificabile eq true  && schedaSubappalti.delegaScheda eq false and schedaSubappalti.riaggiudicata eq false}"></c:set> 

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

<title><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.subappalti" /> - <%= user.getProfilo() %></title>

<script type="text/javascript" src="xtree/treeutils.js"></script>
</head>
<body>
<!-- TB: ticket popup modali. Import css e js -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<%@ include file="script/domUtilsSubappalto.js" %>
<div id="dialog"></div>
<div id="dialogDitteAusiliarie"></div>
<!-- fine import popup modali -->

	<div id="gabbia" align="left">
		<%@ include file="/include/header.inc" %>			
		<div class="bodypage-e" align="left">
		<%--Header Scheda e Lista Schede gia compilate --%>
		<h1><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.subappalti" /></h1>
		<div  class="hmenu" align="left">	
			  <ul>
			     <%String riScheda =ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=";%>
			      
					<li><a title="<utils:message key="scheda.tornaListaAggiudicazioni" plain="true" />" href="javascript:changePage('<%=riScheda%>${datiGara.idLotto}','Modificato')"><utils:message key="scheda.listaAggiudicazioni" /></a></li>  
			    <c:if test="${!UTENTE.ossReg && !UTENTE.RASA && subappalti.idRecord > 0 && datiGara.deleted eq false && rupOk eq true && schedaSubappalti.aggiungibile  && schedaSubappalti.delegaScheda eq false and schedaSubappalti.riaggiudicata eq false}"> 
			      <li>
			        <c:url  value="srvSchedaSubappalti" var="newMod">
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
			 <h2>Lista Schede Subappalti</h2>
			<div style="overflow: auto;height: 13em; width: 100%;"  >
			
			<div class="gara">	 	
			 
		     <table align="center" width="300px">   
			<tr> 
		     	<th class="garaTh">Codice Fiscale Ditta</th> 
		     	<%--  <th class="garaTh">Tipologia Comunicazione</th> --%> 
		     	<th class="garaTh">Data Autorizzazione</th>
		     	<th class="garaTh">Stato scheda</th>
		     	<th class="garaTh">Azioni</th>
		     </tr>
		        <c:set var="counter" value='0' scope="page"/>
			<c:forEach items="${listaSchede}" var="scheda">
				<tr>
					<td class="garaTd"><c:out value="${scheda.cfDitta}"></c:out></td>
				 	<%--   <td  class="garaTd"><c:out value="${scheda.tipoComunicazione == '1' ? 'Ritardo nella consegna':'Sospensione della consegna'}"></c:out></td> --%>
					<td  class="garaTd"><c:out value="${scheda.dataAutorizzazione}"></c:out></td>
					<td  class="garaTd"><c:out value="${scheda.descrizioneStato}"></c:out></td>
					
					<td class="hmenu">
						<c:url  value="srvSchedaSubappalti" var="modURL">
						<c:param name="toDo" value="load"></c:param>
					    	<c:param name="toEdit" value="${counter}"></c:param>
					    </c:url>
					    <c:choose >
					    	<c:when test="${!UTENTE.ossReg && !UTENTE.RASA && datiGara.deleted eq false && rupOk eq true && scheda.confirmed eq false  && schedaSubappalti.delegaScheda eq false and schedaSubappalti.riaggiudicata eq false}">
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
		
				<table >	
					<tr>
						<c:if test="${!modificabile}"><td><input <c:out value="${disabled}"/> type="button" value="Salva" onclick="checkAndAction('check',0,'<%=PSBD.ACTION_SALVA %>')"/></td></c:if>
						<c:if test="${modificabile}"><td><input type="button" value="Modifica" onclick="checkAndAction('check',0,'<%=PSBD.ACTION_MODIFICA %>')"/></td></c:if>
						<td><input <c:out value="${noConf}"/>  type="button" value="Conferma" onclick="checkAndAction('check',0,'<%=PSBD.ACTION_CONFERMA %>')"/></td>
						<td><input <c:out value="${disabled}"/> type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_REIMPOSTA %>')"/></td>	
						<c:if test="${annullabile eq true}">
							<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')"/></td>	
						</c:if>
						<c:if test="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && subappalti.okCancellazione eq true  && schedaSubappalti.delegaScheda eq false and schedaSubappalti.riaggiudicata eq false}">
							<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
						</c:if>
				<c:set var="statoid" value="${subappalti.idStato}"/>
				<c:set var="statoann" value="${subappalti.richAnn || subappalti.richDelete}"/>
				<c:set var="statodesc" value="${subappalti.descrizioneStato}"/>
				<%@ include file="../include/statoscheda.inc" %>
						
					</tr>
				</table>
			 <fieldset>
			 <h2>Scheda Subappalti - <c:out value="${subappalti.idRecord < 1 ?  'Inserimento' : (hide == true ? 'Visualizzazione' : 'Modifica')}" /></h2>
			 <form action="<%=ParametriServletSubappalti.SRV_SCHEDA_SUBAPPALTI%>" method="post" onkeypress="setFormModified('Modificato')" >
				<input type="hidden" name="checkIfOK" id="checkIfOK" value="<%=new Integer(session.getAttribute(ParametriServlet.checkIfOK).toString()) + 1%>" />
			 
			 <%--Campi hidden e altro, copiati dalle altre schede, non so se servono. DA VERIFICARE --%>						
				<input type="hidden" name="<%=PSBD.TAB%>" id="<%=PSBD.TAB%>" value="" />	
				<input type="hidden" name="<%=PSBD.ACTION_TYPE%>" id="<%=PSBD.ACTION_TYPE%>" value="" />
				<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_ID_INFO%>" id="<%=ParametriServlet.FIELD_NAME_ID_INFO%>" value='<c:out value="${datiGara.idInfo}"/>' />
				<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO%>" id="<%=ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO%>" value='<c:out value="${datiGara.dataInizioInfo}"/>' />
				<input type="hidden" name="<%=ParametriServletSubappalti.FIELD_NAME_ID_RECORD %>" value='<c:out value="${subappalti.idRecord}"/>'/>
				<input type="hidden" name="<%=ParametriServletSubappalti.FIELD_NAME_DATA_INIZIO_RECORD %>" value='<c:out value="${subappalti.dataInizioRecord}"/>'/>
				<input type="hidden" id="Modificato"  value="0" />
 				<input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_LISTA_GRUPPI %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_LISTA_GRUPPI %>" value="<c:out value="${subappalti.subappaltatoriString}" />">
				
				<fieldset class="gara">
				
				<table width="100%">
				
				  	<tr>
						<td align="center" colspan="2"><p class="detailHelp"><strong>RIFERIMENTO AI DATI DELLA FASE DI AGGIUDICAZIONE O DI DEFINIZIONE DI PROCEDURA NEGOZIATA</strong></p></td>
					</tr>
   				<%@include file="/include/intestazione.jsp" %>
	   				<tr>
						<td align="center" colspan="2"><p class="detailHelp"><strong>SUBAPPALTO</strong></p></td>
					</tr>
					<tr>
	   					<td><label for="<%= ParametriServletSubappalti.FIELD_NAME_CF_DITTA %>" >Codice fiscale ditta subappaltatrice (impresa singola o mandataria)</label></td>
	   					<td>
	   						<input name="<%= ParametriServletSubappalti.FIELD_NAME_CF_DITTA %>" 
	   							   tabindex="<%= ++indiceTab %>" 
	   							   style="text-align: left;" 
	   							   <c:out value="${disabled}"/> 
	   							   type="text" 
	   							   value="<c:out value='${subappalti.cfDitta}'/>"
	   							   maxlength="20" />
	   					</td>
	   				</tr>

					<tr>
						<td></td>
						<td class="hmenu"> 
							 <a title="Gestione raggruppamento" href="javascript:apriPopUpRubricaDA('rubricaRaggruppamento','<%= PSBD.TAB_SUBAPPALTO %>','Gestione mandanti','<c:out value='${subappalti.cfDitta}'/>&<%= PSBD.FIELD_NAME_READONLY_AFFIDATARIO %>=<c:out value="${hide}"/>','<%= ParametriServlet.FIELD_NAME_ID_LOTTO %>')">Gestione mandanti</a>
						</td>
					</tr> 
					
					<tr>
					<!-- MEV 36771 3.04.8.1 -->
						<td><label for="<%=ParametriServletSubappalti.FIELD_FLAG_DITTA_SUB_ESTERA %>">Ditta subappaltatrice estera</label></td>
						<td><select tabindex="<%=++indiceTab%>" name="<%=ParametriServletSubappalti.FIELD_FLAG_DITTA_SUB_ESTERA %>" <c:out value="${disabled}" /> id="sel_FLAG_DITTA_SUB_ESTERA" >
			  				<option></option>
			  				<option value="N" <%= "N".equals(subappalti.getFlagDittaSubEstera()) ? "selected" : "" %>>NO</option> <!-- TODO equals -->
			  				<option value="S" <%= "S".equals(subappalti.getFlagDittaSubEstera()) ? "selected" : "" %>>SI</option> <!-- TODO equals -->
						</select></td>
					</tr>

					 
	   				<%-- //gm nuovo codice 3.0 --%>
	   				<tr>
	   					<td><label for="<%= ParametriServletSubappalti.FIELD_NAME_CF_AGGIUDICATARIO %>" >Codice fiscale ditta aggiudicatrice nel caso di aggiudicatari multipli</label></td>
	   					<td>
	   					
	   					<select onchange="setFormModified('Modificato')" tabindex="<%=++indiceTab%>" style="width:100%" <c:out value="${disabled}" /> name="<%= ParametriServletSubappalti.FIELD_NAME_CF_AGGIUDICATARIO %>" id=<%= ParametriServletSubappalti.FIELD_NAME_CF_AGGIUDICATARIO %> class="BOTTONE">
								<option></option>
				  				<c:set var="aggiudCombo" value="${schedaSubappalti.aggiudicatariCombo}" scope="request" />
				  				<c:set var="currAgg" value="${subappalti.cfAggiudicatario}" scope="request" />
				  				
				  				<u:options name="aggiudCombo" scope="request" value="currAgg"/>
							</select>
	   					
<!--	   						<input name="<%= ParametriServletSubappalti.FIELD_NAME_CF_AGGIUDICATARIO %>" -->
<!--	   							   tabindex="<%= ++indiceTab %>" -->
<!--	   							   style="text-align: left;" -->
<!--	   							   <c:out value="${disabled}"/> -->
<!--	   							   type="text" -->
<!--	   							   value="<c:out value='${subappalti.cfAggiudicatario}'/>"-->
<!--	   							   maxlength="20" />-->
	   					</td>
	   				</tr>
	   			<%--gm fine nuovo codice 3.0 --%> 
	   				<tr>
	   					<td><label for="<%= ParametriServletSubappalti.FIELD_NAME_DATA_AUTORIZZAZIONE %>">Data di autorizzazione subappalto</label></td>
	   					<td><input name="<%= ParametriServletSubappalti.FIELD_NAME_DATA_AUTORIZZAZIONE %>" 
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: center;"
	   							   onchange="setFormModified('Modificato')"
	   							   <c:out value="${disabled}"/>
	   							   type="text"
	   							   id="<%= ParametriServletSubappalti.FIELD_NAME_DATA_AUTORIZZAZIONE %>"
	   							   onblur="Calendar.validaData(this)"
	   							   value="<c:out value='${subappalti.dataAutorizzazione}'/>" />
	   						<c:if test="${hide == false}">
	   							<img src="calendar/img.gif" id="calendarTermine" style="cursor: pointer; border: 1px solid red;" title="Date selector"
	   							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
	   							<script type="text/javascript">
									    Calendar.setup({
								        inputField     :    "<%= ParametriServletSubappalti.FIELD_NAME_DATA_AUTORIZZAZIONE %>",     // id of the input field
								        ifFormat       :    "%d/%m/%Y",      // format of the input field
								        button         :    "calendarTermine",  // trigger for the calendar (button ID)
								        align          :    "Tl",           // alignment (defaults to "Bl")
								        singleClick    :    true							       
							    		});					    	
								</script>
							</c:if>
						</td>
					</tr>
					<tr>
						<td><label for="<%= ParametriServletSubappalti.FIELD_NAME_OGGETTO_SUBAPPALTO %>"> <c:out value="${datiGara.tipoContratto eq 'L' ? 'Lavoro' : (datiGara.tipoContratto eq 'F' ? 'Fornitura' : 'Servizio')}"></c:out>  subappalto</label></td>
						<td><input name="<%= ParametriServletSubappalti.FIELD_NAME_OGGETTO_SUBAPPALTO %>"
								   tabindex="<%= ++indiceTab %>"
								   style="text-align: left;width:100%"
								   <c:out value="${disabled}"/> 
	   							   type="text"
	   							   value="<c:out value='${subappalti.oggettoSubappalto}'/>"
	   							   maxlength="1000" />
	   					</td>
	   				</tr>
	   				<tr>
	   					<td><label for="<%= ParametriServletSubappalti.FIELD_NAME_IMPORTO_PRESUNTO %>">Importo presunto <c:out value="${datiGara.tipoContratto eq 'L' ? 'Lavoro' : (datiGara.tipoContratto eq 'F' ? 'Fornitura' : 'Servizio')}"></c:out> subappalto</label></td>
	   					<% // adds 19052008 
						String importoPresunto = "0";
						if(((SubappaltiBean)pageContext.getAttribute("subappalti")).getImportoPresuntoStr() != null){
							importoPresunto =((SubappaltiBean)pageContext.getAttribute("subappalti")).getImportoPresuntoStr();
						}
						%>
	   					<td><input name="<%= ParametriServletSubappalti.FIELD_NAME_IMPORTO_PRESUNTO %>"
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: right;"
										<c:out value="${disabled}"/>
	   							   type="text"
	   							   value="<c:out value='<%=importoPresunto %>'/>"
	   							   onblur="validateAmount(this)" />
	   					</td>
	   				</tr>
	   				<tr>
	   					<td><label>Categoria <c:out value="${datiGara.tipoContratto eq 'L' ? 'Lavoro' : (datiGara.tipoContratto eq 'F' ? 'Fornitura' : 'Servizio')}"></c:out> subappalto</label></td>
	   					<td>
	   						<select onchange="setFormModified('Modificato')" tabindex="<%=++indiceTab%>" style="width:100%" <c:out value="${disabled}" /> name="<%= ParametriServletSubappalti.FIELD_NAME_ID_CATEGORIA %>" id=<%= ParametriServletSubappalti.FIELD_NAME_ID_CATEGORIA%> class="BOTTONE">
								<option></option>
				  				<c:set var="categoria" value="${subappalti.idCategoria}" scope="request" />
				  				
				  				<u:options name="categorie" scope="request" value="categoria"/>
							</select>
	   					</td>
	   				</tr>
	   				<tr>
	   				<input type="hidden" id="canSearch" value="false" />
	   				<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_ValoreCPV") %> for="<%= ParametriServletSubappalti.FIELD_NAME_ID_CPV %>">CPV Correnti</label></td>
	   					<td><input type="text" onblur="searchLE(this.id, 'ricercaCPV.jsp?nameField=sel_CPV', 'isNotCPV')" 
	   					name="<%= ParametriServletSubappalti.FIELD_NAME_ID_CPV %>" 
	   					value="${subappalti.idCpv }" id="sel_CPV" 
	   					onkeyup="checkKeyLE(event, this, 'ricercaCPV.jsp?nameField=sel_CPV', 'isNotCPV')" <c:out value="${disabled}" /> >
							<c:if test="${hide != true}">
							<a class="getCPV" href="#" onclick="apripopup('ricercaCPV.jsp?nameField=sel_CPV','sel_CPV')" title="Lista CPV Correnti"><img src="img/icon_info_sml.gif"></a>
	   					</c:if>
	   					</td>
	   				</tr>
	   				<tr>
	   					<td><label for="<%= ParametriServletSubappalti.FIELD_NAME_IMPORTO_EFFETTIVO %>">Importo effettivo <c:out value="${datiGara.tipoContratto eq 'L' ? 'Lavoro' : (datiGara.tipoContratto eq 'F' ? 'Fornitura' : 'Servizio')}"></c:out> subappalto</label></td>
	   					<% // adds 19052008 
						String importoEffettivo = "0";
						if(((SubappaltiBean)pageContext.getAttribute("subappalti")).getImportoEffettivoStr() != null){
							importoEffettivo =((SubappaltiBean)pageContext.getAttribute("subappalti")).getImportoEffettivoStr();
						}
						%>
	   					<td><input name="<%= ParametriServletSubappalti.FIELD_NAME_IMPORTO_EFFETTIVO %>"
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: right;"
	   							   <c:out value="${!modificabile ? disabled : ''}"/>
	   							   type="text"
	   							   value="<c:out value='<%=importoEffettivo %>'/>"
	   							   onblur="validateAmount(this)" />
	   					</td>
	   				</tr>
		   				</tr>
	   			</table>	
	   			
	   			<input type="hidden"  value="save" name="toDo" id="toDo"/>		
				
				</fieldset>
				
				<table >	
					<tr>
						<c:if test="${!modificabile}"><td><input <c:out value="${disabled}"/> type="button" value="Salva" onclick="checkAndAction('check',0,'<%=PSBD.ACTION_SALVA %>')"/></td></c:if>
						<c:if test="${modificabile}"><td><input type="button" value="Modifica" onclick="checkAndAction('check',0,'<%=PSBD.ACTION_MODIFICA %>')"/></td></c:if>

						<td><input <c:out value="${noConf}"/>  type="button" value="Conferma" onclick="checkAndAction('check',0,'<%=PSBD.ACTION_CONFERMA %>')"/></td>
						<td><input <c:out value="${disabled}"/> type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_REIMPOSTA %>')"/></td>	
						<c:if test="${annullabile eq true}">
							<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')"/></td>	
						</c:if>
						<c:if test="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && subappalti.okCancellazione eq true  && schedaSubappalti.delegaScheda eq false and schedaSubappalti.riaggiudicata eq false}">
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

<%@page import="it.avlp.simog.beans.subappalti.SubappaltiBean"%>
<%@page import="java.math.BigDecimal"%>
</html>