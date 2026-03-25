<%@ page language="java" contentType="text/html; charset=UTF-8"
    errorPage="errore.jsp"%>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

<%@page import="it.avlp.simog.common.servlet.ParametriServletConclusioni"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>

<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% int indiceTab = 0;%>
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
<c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>
<jsp:useBean id="schedaConclusione" type="it.avlp.simog.beans.conclusione.SchedaConclusione" class="it.avlp.simog.beans.conclusione.SchedaConclusione" scope="request"></jsp:useBean>

<c:set var="aggiudicazione" value="${schedaConclusione.aggiudicazione}" scope="page"></c:set>

<% ConclusioneBean conclusione = schedaConclusione.getConclusione();
	pageContext.setAttribute("conclusione",conclusione);
	
	InizioLavoriBean inizioLavori = schedaConclusione.getInizioLavori();
	pageContext.setAttribute("inizioLavori",inizioLavori);
	
	List<AvanzamentoBean> avanzamenti = schedaConclusione.getAvanzamenti();
	pageContext.setAttribute("avanzamenti",avanzamenti);
%>
<c:set var="rupOk" value="${(UTENTE.login eq datiGara.cfRup or datiGara.cfRup eq null ) and aggiudicazione.flagAggiudPrincipale ne 'N'}" />
 <c:set var="hide" value="${(datiGara.deleted || conclusione.confirmed) || rupOk eq false || schedaConclusione.readOnly || UTENTE.ossReg || UTENTE.RASA || schedaConclusione.delegaScheda || schedaConclusione.riaggiudicata}" />
 <c:set var="annullabile" value="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && datiGara.deleted ne true && conclusione.confirmed eq true && conclusione.richAnn ne true &&
  conclusione.richDelete ne true && schedaConclusione.delegaScheda eq false and schedaConclusione.riaggiudicata eq false}"></c:set>
 <c:set var="disabled" value="${hide ? 'disabled':'' }"></c:set>
<c:set var="noConf" value="${(hide || (conclusione.idUltim le 0)) || conclusione.richAnn eq true ? 'disabled':''}"></c:set>
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

<title><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.conclusione" /> - <%= user.getProfilo() %></title>
</head>
<body>
	
	<div id="gabbia" align="left">
		<%@ include file="/include/header.inc" %>			
		<div class="bodypage-e" align="left">
		
		<h1><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.conclusione" /></h1>
		<div  class="hmenu" align="left">
			<ul>
			    <%String riScheda =ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=";%>
					<li><a title="<utils:message key="scheda.tornaListaAggiudicazioni" plain="true" />" href="javascript:changePage('<%=riScheda%>${datiGara.idLotto}','Modificato')"><utils:message key="scheda.listaAggiudicazioni" /></a></li>
			    <c:if test="${conclusione.idUltim > 0 && datigara.deleted eq false && rupOk eq true && schedaConclusione.readOnly eq false  && schedaConclusione.delegaScheda eq false  and schedaConclusione.riaggiudicata eq false}">
			    	<li>
			        <c:url  value="srvSchedaConclusioni" var="newMod"> 	
			        <c:param name="toDo" value="load"></c:param>
			        </c:url>
				      <a href="<c:out value='${newMod}'/>" >Aggiungi nuova scheda</a>
				 </li>
				</c:if>
			  </ul>
		</div>
		<%--Errori.... --%>
		<%@ include file="/include/gestisciErrore.inc" %>

		<%-- PANNELLO DELLE RICHIESTE DI ANNULLAMENTO DELLA SCHEDA [DISATTIVATO] --%>
		<%@ include file="../include/RichAnnPanel.jsp" %>
		<%-- --%>

  			<table >	
					<tr>
						<td><input <c:out value="${disabled}"/> type="button" value="Salva" onclick="checkAndAction('check',0,'<%=PSBD.ACTION_SALVA %>')"/></td>
						<td><input <c:out value="${noConf}"/>  type="button" value="Conferma" onclick="checkAndAction('check',0,'<%=PSBD.ACTION_CONFERMA %>')"/></td>
						<td><input <c:out value="${disabled}"/> type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_LOAD %>')"/></td>	
						<c:if test="${annullabile eq true}">
							<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')"/>	</td>	
						</c:if>
						<c:if test="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && schedaConclusione.conclusione.okCancellazione eq true  && schedaConclusione.delegaScheda eq false  and schedaConclusione.riaggiudicata eq false}">
							<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
						</c:if>

				<c:set var="statoid" value="${conclusione.idStato}"/>
				<c:set var="statoann" value="${conclusione.richAnn || conclusione.richDelete}"/>
				<c:set var="statodesc" value="${conclusione.descrizioneStato}"/>
				<%@ include file="../include/statoscheda.inc" %>
						
					</tr>
				</table>
			 
			 <fieldset>
			 <h2>Scheda Conclusione</h2>
			 <form action="<%= ParametriServletConclusioni.SRV_SCHEDA_CONCLUSIONI %>"
			 	   method="post"
			 	   onkeypress="setFormModified('Modificato')">
					    <input type="hidden" name="checkIfOK" id="checkIfOK" value="<%=new Integer(session.getAttribute(ParametriServlet.checkIfOK).toString()) + 1%>" />
			 
			 <%--Campi hidden e altro, copiati dalle altre schede, non so se servono. DA VERIFICARE --%>						
				<input type="hidden" name="<%=PSBD.TAB%>" id="<%=PSBD.TAB%>" value="" />	
				<input type="hidden" name="<%=PSBD.ACTION_TYPE%>" id="<%=PSBD.ACTION_TYPE%>" value="" />
				
				<input type="hidden" name="<%=ParametriServletConclusioni.FIELD_NAME_ID_ULTIM %>" value="<c:out value="${conclusione.idUltim}"/>">
				<input type="hidden" name="<%=ParametriServletConclusioni.FIELD_NAME_DATA_INIZIO_ULTIM %>" value="<c:out value="${conclusione.dataIniUltim}"/>">
				<input type="hidden" id="Modificato"  value="0" />
				
				<fieldset class="gara">
				
				<table width="100%">
				
					<tr>
						<td align="center" colspan="2"><p class="detailHelp"><strong>RIFERIMENTO AI DATI DELLA FASE DI AGGIUDICAZIONE O DI DEFINIZIONE DI PROCEDURA NEGOZIATA</strong></p></td>
					</tr>
	   				<%@include file="/include/intestazione.jsp" %>
	   				<tr>
						<td align="center" colspan="2"><p class="detailHelp"><strong>INTERRUZIONE ANTICIPATA DEL PROCEDIMENTO</strong></p></td>
					</tr>
					<tr>
						<td>
							<label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_MotiviInterruzione") %> for="<%=ParametriServletConclusioni.FIELD_NAME_MOTIVO_INTERR %>">Causa dell' interruzione anticipata</label>
						</td>
						<td>
							<select onchange="setFormModified('Modificato')" tabindex="<%=++indiceTab%>" style="width:100%" <c:out value="${disabled}" /> name="<%= ParametriServletConclusioni.FIELD_NAME_MOTIVO_INTERR %>" id=<%= ParametriServletConclusioni.FIELD_NAME_MOTIVO_INTERR %> class="BOTTONE">
								<option></option>
				  				<c:set var="idMotiviInterr" value="${conclusione.motiviInterruzione}" scope="request" />
				  				
				  				<u:options name="motiviInterruzione" scope="request" value="idMotiviInterr"/>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_MotiviRisoluzione") %> for="<%=ParametriServletConclusioni.FIELD_NAME_MOTIVO_RISOL %>">Motivazione della risoluzione (in caso di risoluzione)</label>
						</td>
						<td>
							<select onchange="setFormModified('Modificato')" tabindex="<%=++indiceTab%>" style="width:100%" <c:out value="${disabled}" /> name="<%= ParametriServletConclusioni.FIELD_NAME_MOTIVO_RISOL %>" id=<%= ParametriServletConclusioni.FIELD_NAME_MOTIVO_RISOL %> class="BOTTONE">
								<option></option>
				  				<c:set var="idMotiviRisol" value="${conclusione.motiviRisoluzione}" scope="request" />
				  				
				  				<u:options name="motiviRisoluzione" scope="request" value="idMotiviRisol"/>
							</select>
						</td>
					</tr>
					<tr>
						<td>
						<%-- label diverso per lavori="Data interruzione anticipata" e forniture="Data conclusione anticipata" --%>	
							<c:choose>
								<c:when test="${datiGara.tipoContratto == 'S' || datiGara.tipoContratto == 'F' }">
									<label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataConclusioneAnticipata") %> for="<%=ParametriServletConclusioni.FIELD_NAME_DATA_RISOLUZIONE %>">Data conclusione anticipata</label>
								</c:when>
		  						<c:when test="${datiGara.tipoContratto == 'L' }">
									<label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataInterruzioneAnticipata") %> for="<%=ParametriServletConclusioni.FIELD_NAME_DATA_RISOLUZIONE %>">Data interruzione anticipata</label>
		  				  		</c:when>
							</c:choose>
						</td>
						<td>
							<input name="<%= ParametriServletConclusioni.FIELD_NAME_DATA_RISOLUZIONE %>" 
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: center;"
	   							   onchange="setFormModified('Modificato')"
	   							   <c:out value="${disabled}"/>
	   							   type="text"
	   							   id="<%= ParametriServletConclusioni.FIELD_NAME_DATA_RISOLUZIONE %>"
	   							   onblur="Calendar.validaData(this)"
	   							   value="<c:out value='${conclusione.dataRisoluzione}'/>" />
	   						<c:if test="${hide == false}">
	   							<img src="calendar/img.gif" id="calendardataRisoluzione" style="cursor: pointer; border: 1px solid red;" title="Date selector"
	   							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
	   							<script type="text/javascript">
									    Calendar.setup({
								        inputField     :    "<%= ParametriServletConclusioni.FIELD_NAME_DATA_RISOLUZIONE %>",     // id of the input field
								        ifFormat       :    "%d/%m/%Y",      // format of the input field
								        button         :    "calendardataRisoluzione",  // trigger for the calendar (button ID)
								        align          :    "Tl",           // alignment (defaults to "Bl")
								        singleClick    :    true							       
							    		});					    	
								</script>
							</c:if>
						</td>
					</tr>
					<tr>
						<td>
							<label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_FlagOneri") %> for="<%=ParametriServletConclusioni.FIELD_NAME_FLAG_ONERI %>">Oneri economici derivanti dalla risoluzione/recesso</label>
						</td>
						<td>
							<input tabindex="<%=++indiceTab%>" 
	   							   id="check1O" 
	   							   type="radio" 
	   							   name="<%= ParametriServletConclusioni.FIELD_NAME_FLAG_ONERI %>" 
	   							   value="0" 
	   							   <c:out value="${conclusione.flagOneri == '0' ? 'checked' : ''}" /> 
	   							   <c:out value="${disabled}"/> />Senza oneri <br/>
	   						<input tabindex="<%=++indiceTab%>" 
	   							   id="check2O" 
	   							   type="radio" 
	   							   name="<%= ParametriServletConclusioni.FIELD_NAME_FLAG_ONERI %>" 
	   							   value="1" 
	   							   <c:out value="${conclusione.flagOneri == '1' ? 'checked' : ''}" /> 
	   							   <c:out value="${disabled}"/> />Riconoscimento del mancato utile<br/>
	   						<input tabindex="<%=++indiceTab%>" 
	   							   id="check3O" 
	   							   type="radio" 
	   							   name="<%= ParametriServletConclusioni.FIELD_NAME_FLAG_ONERI %>" 
	   							   value="2" 
	   							   <c:out value="${conclusione.flagOneri == '2' ? 'checked' : ''}" /> 
	   							   <c:out value="${disabled}"/> />Addebito all'appaltatore delle maggiori spese sostenute
						</td>
					</tr>
					<tr>
	   					<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_ImportoOneri") %> for="<%= ParametriServletConclusioni.FIELD_NAME_ONERI_RISOLUZIONE %>">Importo</label></td>
	   					<td><input name="<%= ParametriServletConclusioni.FIELD_NAME_ONERI_RISOLUZIONE %>"
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: right;"
	   							   <c:out value="${disabled}"/>
	   							   type="text"
	   							   value="<c:out value='${conclusione.oneriRisoluzioneStr}'/>"
	   							   onblur="validateAmount(this)" />
	   					</td>
	   				</tr>
	   				<tr>
	   					<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_FlagPolizza") %> for="<%= ParametriServletConclusioni.FIELD_NAME_FLAG_POLIZZA %>" >Incamerata polizza</label></td>
	   					<td width="40%" > <input tabindex="<%=++indiceTab%>" id="check1Y" type="radio" name="<%= ParametriServletConclusioni.FIELD_NAME_FLAG_POLIZZA %>" value="S" <c:out value="${conclusione.flagPolizza == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/>/>SI 
	   					 <input tabindex="<%=++indiceTab%>" id="check1N" type="radio" name="<%= ParametriServletConclusioni.FIELD_NAME_FLAG_POLIZZA %>" value="N" <c:out value="${conclusione.flagPolizza == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/>/>NO</td>
	   				</tr>
	   				<tr>
						<td align="center" colspan="2"><p class="detailHelp"><strong>ULTIMAZIONE DELLE PRESTAZIONI</strong></p></td>
					</tr>
					
					<tr>
						<td>	
							<c:choose>
								<c:when test="${datiGara.tipoContratto == 'S' || datiGara.tipoContratto == 'F' }">
									<label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataVerbaleAvvio") %> for="<%=ParametriServletConclusioni.FIELD_NAME_DATA_CONSEGNA %>">Data verbale di avvio dell'esecuzione del contratto</label>
								</c:when>
		  						<c:when test="${datiGara.tipoContratto == 'L' }">
									<label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataVerbaleConsegna") %> for="<%=ParametriServletConclusioni.FIELD_NAME_DATA_CONSEGNA %>">Data verbale consegna definitiva</label>
		  				  		</c:when>
							</c:choose>
						</td>
						<td>
							<input name="<%= ParametriServletConclusioni.FIELD_NAME_DATA_CONSEGNA %>" 
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: center;"
	   							   onchange="setFormModified('Modificato')"
	   							   <c:out value='${disabled}'/>
	   							   type="text"
	   							   id="<%= ParametriServletConclusioni.FIELD_NAME_DATA_CONSEGNA %>"
	   							   onblur="Calendar.validaData(this)"
  							   		value="<c:out value='${conclusione.dataConsegna}'/>" />
	   							  
	   						<c:if test="${hide == false}">
	   							<img src="calendar/img.gif" id="calendarDataConsegna" style="cursor: pointer; border: 1px solid red;" title="Date selector"
	   							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
	   							<script type="text/javascript">
									    Calendar.setup({
								        inputField     :    "<%= ParametriServletConclusioni.FIELD_NAME_DATA_CONSEGNA %>",     // id of the input field
								        ifFormat       :    "%d/%m/%Y",      // format of the input field
								        button         :    "calendarDataConsegna",  // trigger for the calendar (button ID)
								        align          :    "Tl",           // alignment (defaults to "Bl")
								        singleClick    :    true							       
							    		});					    	
								</script>
							</c:if>
						</td>
					</tr>

					<tr>
						<td>
							<label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_TermineContrattualeUltimazione") %> for="<%=ParametriServletConclusioni.FIELD_NAME_TERMINE_ULTIMAZIONE %>">Termine contrattuale ultimazione lavori/serivizi/forniture</label>
						</td>
						<td>
							<input name="<%= ParametriServletConclusioni.FIELD_NAME_TERMINE_ULTIMAZIONE %>" 
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: center;"
	   							   onchange="setFormModified('Modificato')"
	   							   <c:out value='${disabled}'/>
	   							   type="text"
	   							   id="<%= ParametriServletConclusioni.FIELD_NAME_TERMINE_ULTIMAZIONE %>"
	   							   onblur="Calendar.validaData(this)"
  							   		value="<c:out value='${conclusione.termineUltimazione}'/>" />
	   							  
	   						<c:if test="${hide == false}">
	   							<img src="calendar/img.gif" id="calendartermineUltimazione" style="cursor: pointer; border: 1px solid red;" title="Date selector"
	   							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
	   							<script type="text/javascript">
									    Calendar.setup({
								        inputField     :    "<%= ParametriServletConclusioni.FIELD_NAME_TERMINE_ULTIMAZIONE %>", 
								        ifFormat       :    "%d/%m/%Y",      
								        button         :    "calendartermineUltimazione",  
								        align          :    "Tl",         
								        singleClick    :    true							       
							    		});					    	
								</script>
							</c:if>
						</td>
					</tr>
	   				<tr>
						<td>
							<label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataUltimazione") %> for="<%=ParametriServletConclusioni.FIELD_NAME_DATA_ULTIMAZIONE %>">Data ultimazione <c:out value="${datiGara.tipoContratto eq 'L' ? 'Lavoro' : (datiGara.tipoContratto eq 'F' ? 'Fornitura' : 'Servizio')}"></c:out></label>
						</td>
						<td>
							<input name="<%= ParametriServletConclusioni.FIELD_NAME_DATA_ULTIMAZIONE %>" 
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: center;"
	   							   onchange="setFormModified('Modificato')"
	   							   <c:out value="${disabled}"/>
	   							   type="text"
	   							   id="<%= ParametriServletConclusioni.FIELD_NAME_DATA_ULTIMAZIONE %>"
	   							   onblur="Calendar.validaData(this)"
	   							   value="<c:out value='${conclusione.dataUltimazione}'/>" />
	   						<c:if test="${hide == false}">
	   							<img src="calendar/img.gif" id="calendardataUltimazione" style="cursor: pointer; border: 1px solid red;" title="Date selector"
	   							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
	   							<script type="text/javascript">
									    Calendar.setup({
								        inputField     :    "<%= ParametriServletConclusioni.FIELD_NAME_DATA_ULTIMAZIONE %>",
								        ifFormat       :    "%d/%m/%Y",  
								        button         :    "calendardataUltimazione",  
								        align          :    "Tl", 
								        singleClick    :    true							       
							    		});					    	
								</script>
							</c:if>
						</td>
					</tr>
					<tr>
	   					<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_NumeroInfortuni") %> for="<%= ParametriServletConclusioni.FIELD_NAME_NUMERO_INFORTUNI %>">Numero infortuni</label></td>
	   					<td><input name="<%= ParametriServletConclusioni.FIELD_NAME_NUMERO_INFORTUNI %>"
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: right;"
	   							   <c:out value="${disabled}"/>
	   							   type="text"
	   							   value="<c:out value='${conclusione.numInfortuni}'/>"
	   							   onblur="validateNumber(this)"/>
	   					</td>
	   				</tr>
	   				<tr>
	   					<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_NumeroInfortuniPermanenti") %> for="<%= ParametriServletConclusioni.FIELD_NAME_NUM_INF_PERM %>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;di cui con postumi permanenti</label></td>
	   					<td><input name="<%= ParametriServletConclusioni.FIELD_NAME_NUM_INF_PERM %>"
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: right;"
	   							   <c:out value="${disabled}"/>
	   							   type="text"
	   							   value="<c:out value='${conclusione.numInfPerm}'/>"
	   							   onblur="validateNumber(this)"/>
	   					</td>
	   				</tr>
	   				<tr>
	   					<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_NumeroInfortuniMortali") %> for="<%= ParametriServletConclusioni.FIELD_NAME_NUM_INF_MORT %>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;di cui mortali</label></td>
	   					<td><input name="<%= ParametriServletConclusioni.FIELD_NAME_NUM_INF_MORT %>"
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: right;"
	   							   <c:out value="${disabled}"/>
	   							   type="text"
	   							   value="<c:out value='${conclusione.numInfMort}'/>"
	   							   onblur="validateNumber(this)"/>
	   					</td>
	   				</tr>
	   				<%-- gm nuovo codice 3.0 --%>
	   				<tr>
	   					<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_GiorniProroga") %> for="<%= ParametriServletConclusioni.FIELD_NAME_GIORNI_PROROGA %>">Indicare il numero di giorni di proroga concessi (non conseguenti a varianti)</label></td>
	   					<td><input name="<%= ParametriServletConclusioni.FIELD_NAME_GIORNI_PROROGA %>"
	   							   tabindex="<%= ++indiceTab %>"
	   							   style="text-align: right;"
	   							   <c:out value="${disabled}"/>
	   							   type="text"
	   							   <% 
	   							   int sommaGiorniProroga=0;
	   							   for (int i=0;i<avanzamenti.size();i++)
	   							   		sommaGiorniProroga += avanzamenti.get(i).getNumeroGiorniProroga();
	   							   %>
	   							   value="<c:choose><c:when test="${conclusione.giorniProroga eq null}" ><c:out value='<%=sommaGiorniProroga%>'/></c:when><c:otherwise><c:out value='${conclusione.giorniProroga}'/></c:otherwise></c:choose>"
	   							   onblur="validateNumber(this)"/>
	   					</td>
	   				</tr>
	   				
	   				
	   			</table>
	   			<input type="hidden"  value="save" name="toDo" id="toDo"/>
	   			</fieldset>
	   			<table >	
					<tr>
						<td><input <c:out value="${disabled}"/> type="button" value="Salva" onclick="checkAndAction('check',0,'<%=PSBD.ACTION_SALVA %>')"/></td>
						<td><input <c:out value="${noConf}"/>  type="button" value="Conferma" onclick="checkAndAction('check',0,'<%=PSBD.ACTION_CONFERMA %>')"/></td>
						<td><input <c:out value="${disabled}"/> type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_LOAD %>')"/></td>	
						<c:if test="${annullabile eq true}">
							<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')"/>	</td>	
						</c:if>
						<c:if test="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && schedaConclusione.conclusione.okCancellazione eq true  && schedaConclusione.delegaScheda eq false  and schedaConclusione.riaggiudicata eq false}">
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

<%@page import="it.avlp.simog.beans.conclusione.ConclusioneBean"%>
<%@page import="it.avlp.simog.beans.avanzamento.AvanzamentoBean"%>
<%@page import="it.avlp.simog.beans.inizio.InizioLavoriBean"%>
</html>