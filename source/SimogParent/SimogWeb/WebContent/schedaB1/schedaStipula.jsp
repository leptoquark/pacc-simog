<% try { %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="../errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="../include/newbasicHeader.inc" %>
<%@ include file="../include/controlloSessione.inc" %>

<%@ page import="it.avlp.simog.beans.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<% int indiceTab = 0; %>
<%@page import="it.avlp.simog.util.PageHelper"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.util.SimogProperties"%>
<%@page import="java.util.HashMap"%>
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
<!-- the following script defines the Calendar.setup helper function, which makes
     adding a calendar a matter of 1 or 2 lines of code. -->
<script type="text/javascript" src="calendar/calendar-setup.js"></script>
<script type="text/javascript" src="script/pageutils.js"></script>
<%@ include file="../include/i18n-init.inc" %>
<%@ include file="/script/script.js" %>
<%@ include file="/script/domUtils.js" %>

<c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>
<jsp:useBean id="schedaStipula" type="it.avlp.simog.beans.stipula.SchedaStipula" class="it.avlp.simog.beans.stipula.SchedaStipula" scope="request"></jsp:useBean>
<jsp:useBean id="disabled" class="java.lang.String" scope="page"></jsp:useBean>
<jsp:useBean id="stipula" type="it.avlp.simog.beans.stipula.StipulaBean" class="it.avlp.simog.beans.stipula.StipulaBean" scope="request"></jsp:useBean>

<c:set var="aggiudicazione" value="${schedaStipula.aggiudicazione}" scope="page"></c:set>

<c:set var="rupOk" value="${(UTENTE.login eq datiGara.cfRup or datiGara.cfRup eq null ) and aggiudicazione.flagAggiudPrincipale ne 'N'}" />
<c:set var="hide" value="${(datiGara.deleted || schedaStipula.stipula.confirmed || rupOk eq false) || schedaStipula.readOnly || UTENTE.ossReg || UTENTE.RASA || schedaStipula.delegaScheda || schedaStipula.riaggiudicata}" />
 <c:set var="stipula" value="${schedaStipula.stipula}"></c:set>
 <c:set var="annullabile" value="${!UTENTE.ossReg && !UTENTE.RASA &&  rupOk eq true && datiGara.deleted ne true && stipula.confirmed eq true && stipula.richAnn ne true && stipula.richDelete ne true and schedaStipula.delegaScheda eq false and schedaStipula.riaggiudicata eq false }"></c:set> 
 <c:set var="disabled" value="${hide ? 'disabled':'' }"></c:set>
 <c:set var="noConf" value="${(hide || (schedaStipula.stipula.idStipula le 0)) || schedaStipula.stipula.richAnn eq true ? 'disabled':''}"></c:set>
 
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
 
<title><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.faseStipula" /> - <%= user.getProfilo() %></title>
	</head>
	<body>
		<div id="gabbia">
			<%@ include file="../include/header.inc" %>			
			<div class="bodypage-e">
				
				<form action="<%= ParametriServletStipula.SRV_STIPULA %>" method="post" onkeypress="setFormModified('Modificato')">
					
				    <h1><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.faseStipulaAccordoQuadro" /></h1>
                 
                 <input type="hidden"  value="load" name="toDo" id="toDo"/>
					  <input type="hidden" name = "<%=ParametriServletStipula.ID_STIPULA %>"  value="${schedaStipula.stipula.idStipula}" />
			        <input type="hidden" name = "<%=ParametriServletStipula.DATA_INIZIO_STIPULA %>"  value="${schedaStipula.stipula.dataInizioStipula}" />
			     	  <input type="hidden" name = "<%=ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE %>"  value="${schedaStipula.stipula.pubblicazione.idPubblicazione}" />
			        <input type="hidden" name = "<%=ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB %>"  value="${schedaStipula.stipula.pubblicazione.dataInizioPubblicazione}" />
			     	  
			     	  <input type="hidden" name = "descrizioneStato"  value="<c:out value='${stipula.descrizioneStato}' />" />
			    	  <input type="hidden" name = "idStato"  value="<c:out value='${stipula.idStato}' />" />
					  <%-- 
					  <input type="hidden" id="Modificato" name="Modificato" value="${param['modificato']}">
					  <input type="hidden" id="Modificato0" name="Modificato0" value="${param['modificato0']}">					
					  --%>
					  <input type="hidden" id="Modificato"  value="0">  
					  <input type="hidden" name="<%=PSBD.TAB%>" id="<%=PSBD.TAB%>" value="">	
					  <input type="hidden" name="<%=PSBD.ACTION_TYPE%>" id="<%=PSBD.ACTION_TYPE%>" value="">
						
              <div  class="hmenu">	
							<ul> 
								<%-- 
								<li><a title="Mostra Dati Comuni" href="javascript:changePage('<%= PSBD.SRV_DATI_COMUNI %>','Modificato')">Mostra Dati Comuni</a></li>
								--%>
								<%String riScheda =ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=";%>			    				
								<li><a title="<utils:message key="scheda.tornaListaAggiudicazioni" plain="true" />" href="javascript:changePage('<%=riScheda%><c:out value="${datiGara.idLotto}" />','Modificato')"><utils:message key="scheda.listaAggiudicazioni" /></a></li>			    			    
							</ul>  					
					</div>
					<%@ include file="/include/gestisciErrore.inc" %>
						
					<%-- PANNELLO DELLE RICHIESTE DI ANNULLAMENTO DELLA SCHEDA [DISATTIVATO] --%>
					<%@ include file="../include/RichAnnPanel.jsp" %>
					<%-- --%>
						
				<fieldset>
				    <table>				
                  <tr>					
						<td><input <c:out value="${disabled}"/> type="button" value="Salva" onclick="checkAndAction('check',3,'<%=PSBD.ACTION_SALVA %>')"/></td>					
				      <td><input <c:out value="${noConf}"/>  type="button" value="Conferma" onclick="checkAndAction('check',3,'<%=PSBD.ACTION_CONFERMA %>')"/></td>			      
				      <td><input <c:out value="${disabled}"/> type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_LOAD %>')"/></td>
				      
                  <c:if test="${annullabile eq true}">					     
                    <td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="loadAnnullamentJSP('<%= ParametriServletStipula.TAB_STIPULA %>')"/></td>					      
				      </c:if>
						<c:if test="${!UTENTE.ossReg && !UTENTE.RASA && stipula.okCancellazione eq true && rupOk eq true and schedaStipula.delegaScheda eq false and schedaStipula.riaggiudicata eq false }">
					     <td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>					      
				      </c:if>
						
						<c:set var="statoid" value="${stipula.idStato}"/>
						<c:set var="statoann" value="${stipula.richAnn || stipula.richDelete}"/>
						<c:set var="statodesc" value="${stipula.descrizioneStato}"/>
						<%@ include file="../include/statoscheda.inc" %>						
					   </tr>
                </table>
            </fieldset>
         
            <fieldset class="gara">
					<table width="100%">	
						<colgroup>
							<col width="60%"/>
							<col width="40%"/>
						</colgroup>
						<tr>
						   <td align="center" colspan="2"><p class="detailHelp"><strong>RIFERIMENTO AI DATI DELLA FASE DI AGGIUDICAZIONE O DI DEFINIZIONE DI PROCEDURA NEGOZIATA</strong></p></td>
					  </tr>

	   				<%@include file="/include/intestazione.jsp" %>
	   			  
	   			  <tr>
						   <td align="center" colspan="2"><p class="detailHelp"><strong>PUBBLICAZIONE ESITO PROCEDURA DI SELEZIONE</strong></p></td>
					  </tr>
						  <c:set var="pubblicazione" value="${stipula.pubblicazione}" scope="page"></c:set>
			<tr>			  
	   	<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DatatGUCE") %> >Gazzetta Ufficiale Comunit� Europea - GUCE</label></td>
 			<td>
				<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')" 
				   <c:out value="${disabled}"/> 
					type="text" id="inputGazzettaCE" name="<%=  ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE %>" 
					onblur="Calendar.validaData(this)" value="<c:out value='${pubblicazione.dataGuce}'/>">
				<%-- vecchio controllo <c:if test="${hide == false}"> --%>
				<c:if test="${hide == false}">
					<img src="calendar/img.gif" id="calendarGazzettaCE" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
					<script type="text/javascript">
						    Calendar.setup({
					        inputField     :    "inputGazzettaCE",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendarGazzettaCE",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true							       
				    		});					    	
					</script>
				</c:if>
			</td>
	   	</tr>
	   	<tr>
	     <td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DatatGORI") %> >Gazzetta Ufficiale Repubblica Italiana - GURI</label></td>
 			<td>
				<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  
				<c:out value="${disabled}"/>
					type="text" id="inputGazzettaRI" name="<%=  ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI %>" 
					onblur="Calendar.validaData(this)" value="<c:out value='${pubblicazione.dataGuri}'/>">
				<c:if test="${hide == false}">
					<img src="calendar/img.gif" id="calendarGazzettaRI" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
					<script type="text/javascript">
						    Calendar.setup({
					        inputField     :    "inputGazzettaRI",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendarGazzettaRI",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true							       
				    		});					    	
					</script>
				</c:if>
			</td>
	   	</tr>
	   	<tr>
 			<th><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_QN") %> >Quotidiani nazionali</label></th>
 			<td>
				<input  name="<%=  ParametriServlet.FIELD_NAME_QUOTIDIANI_NAZIONALI%>"  tabindex="<%=++indiceTab%>" style="text-align:right;"  
				<c:out value="${disabled}"/>
				type="text" value="<c:out value='${pubblicazione.quotidianiNaz}'/>" onblur="validateNumber(this)" maxlength="9"/>
			</td>
	   	</tr>
	   	<tr>
 			<th><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_QL") %> >Quotidiani locali</label></th>
 			<td>
				<input  name="<%=  ParametriServlet.FIELD_NAME_QUOTIDIANI_REGIONALI%>"  tabindex="<%=++indiceTab%>" style="text-align:right;" 
				<c:out value="${disabled}"/>
				type="text" value="<c:out value='${pubblicazione.quotidianiReg}'/>"  onblur="validateNumber(this)" maxlength="9"/>
			</td>
	   	</tr>
	   	<tr>
			<th><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_PC") %> >Profilo del Committente</label></th>
		  	<td>
		  	     <input  tabindex="<%=++indiceTab%>" id="check1Y" type="radio" name="<%= ParametriServlet.FIELD_NAME_PROFILO_COMMITTENTE %>" value="S" <c:out value="${pubblicazione.profiloCommitente == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />SI 
  			     <input  tabindex="<%=++indiceTab%>" id="check1N" type="radio" name="<%= ParametriServlet.FIELD_NAME_PROFILO_COMMITTENTE %>" value="N" <c:out value="${pubblicazione.profiloCommitente == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />NO
  			</td>
		</tr>
		<tr>
			<th><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_SMI") %> >Sito Informatico Ministero Infrastrutture<br>e piattaforma digitale ANAC tramite i sistemi<br>informatizzati regionali</label></th>
		  	<td>	  	    
		  	     <input  tabindex="<%=++indiceTab%>" id="check2Y" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_MIN_INF_TRASP %>" value="S" <c:out value="${pubblicazione.sitoMinisteroInfTrasp == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />SI 
  			     <input  tabindex="<%=++indiceTab%>" id="check2N" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_MIN_INF_TRASP %>" value="N" <c:out value="${pubblicazione.sitoMinisteroInfTrasp == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />NO
  			</td>
		</tr>
		<tr>
			<th><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_SCP") %> >Sito Informatico Osservatorio Contratti Pubblici</label></th>
		  	<td>		    
		  	     <input  tabindex="<%=++indiceTab%>" id="check3Y" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_OSSERVATORIO_CP %>" value="S" <c:out value="${pubblicazione.sitoOsservatorioCP == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />SI 
  			     <input  tabindex="<%=++indiceTab%>" id="check3N" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_OSSERVATORIO_CP %>" value="N" <c:out value="${pubblicazione.sitoOsservatorioCP == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />NO
  			</td>
		</tr>
	   			  <tr>
		     			   <td colspan="2" align="center"><p class="detailHelp"><strong>ACCORDO QUADRO/CONVENZIONE</strong></p></td>
					  </tr>			  
	   
	              <tr>
	                  <td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataStipula") %>>Data stipula accordo quadro/convenzione</label></td>
 			            <td>
				           <input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')" 
				           <c:out value="${disabled}"/>
					        type="text" id="inputDataStipulaContratto" name="<%=  ParametriServletStipula.FIELD_NAME_DATA_STIPULA_CONTRATTO %>" 
					        onblur="Calendar.validaData(this)" value="<c:out value='${stipula.dataStipulaContratto}'/>">
				           <%-- vecchio controllo <c:if test="${hide == false}"> --%>
				           <c:if test="${hide == false}">
					          <img src="calendar/img.gif" id="calendarDataStipulaContratto" style="cursor: pointer; border: 1px solid red;" title="Date selector"
							    onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
					          <script type="text/javascript">
						         Calendar.setup({
					            inputField     :    "inputDataStipulaContratto",     // id of the input field
					            ifFormat       :    "%d/%m/%Y",      // format of the input field
					            button         :    "calendarDataStipulaContratto",  // trigger for the calendar (button ID)
					            align          :    "Tl",           // alignment (defaults to "Bl")
					            singleClick    :    true							       
				    		      });					    	
					          </script>
				           </c:if>
			            </td>
	    	        </tr>
	    	        
	    	        <tr>
		     			   <td colspan="2" align="center"><p class="detailHelp"><strong>TERMINI DI ESECUZIONE</strong></p></td>
					  </tr>	
	    	        
	   	        <tr>
	                 <td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataDecorrenza") %>>Data decorrenza contrattuale</label></td>
 			           <td>
				          <input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  
				          <c:out value="${disabled}"/>
					       type="text" id="inputDataDecorrenzaContrattuale" name="<%=  ParametriServletStipula.FIELD_NAME_DATA_DECORRENZA_STIPULA %>" 
					       onblur="Calendar.validaData(this)" value="<c:out value='${stipula.dataDecorrenza}'/>">
				            <c:if test="${hide == false}">
					         <img src="calendar/img.gif" id="calendarDataDecorrenzaContrattuale" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
					         <script type="text/javascript">
						        Calendar.setup({
					           inputField     :    "inputDataDecorrenzaContrattuale",     // id of the input field
					           ifFormat       :    "%d/%m/%Y",      // format of the input field
					           button         :    "calendarDataDecorrenzaContrattuale",  // trigger for the calendar (button ID)
					           align          :    "Tl",           // alignment (defaults to "Bl")
					           singleClick    :    true							       
				    		     });					    	
					         </script>
       				      </c:if>
			          </td>
	   	       </tr>
	   	       <tr>
	                <td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataScadenza") %>>Data scadenza contrattuale</label></td>
 			          <td>
				         <input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  
				         <c:out value="${disabled}"/>
					      type="text" id="inputDataScadenzaContrattuale" name="<%=  ParametriServletStipula.FIELD_NAME_DATA_SCADENZA_STIPULA %>" 
					      onblur="Calendar.validaData(this)" value="<c:out value='${stipula.dataScadenza}'/>">
				           <c:if test="${hide == false}">
					        <img src="calendar/img.gif" id="calendarDataScadenzaContrattuale" style="cursor: pointer; border: 1px solid red;" title="Date selector"
							  onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
					        <script type="text/javascript">
						       Calendar.setup({
					          inputField     :    "inputDataScadenzaContrattuale",     // id of the input field
					          ifFormat       :    "%d/%m/%Y",      // format of the input field
					          button         :    "calendarDataScadenzaContrattuale",  // trigger for the calendar (button ID)
					          align          :    "Tl",           // alignment (defaults to "Bl")
					          singleClick    :    true							       
				    		    });					    	
					       </script>
				          </c:if>
			          </td>
	   	      </tr>
	   	      <tr>
 			        <th><label >Oggetto contrattuale</label></th>
 			        <td>
				       <input  name="<%=  ParametriServletStipula.FIELD_NAME_OGGETTO%>"  tabindex="<%=++indiceTab%>" size="100%"
				       <c:out value="${disabled}"/>
				       type="text" value="<c:out value='${datiGara.oggettoLotto}'/>" disabled/>
			        </td>
	   	     </tr>	
	   	   <%-- 
		      </table>
		      </fieldset>
		      --%>
		        <c:set var="aggiudicazione" value="${schedaStipula.aggiudicazione}" scope="page"></c:set>
	   	    
	   	          <tr>
							<td colspan="2">
								<div class="inthead">
								<label onclick="showMenu('<%= ParametriServletStipula.TAB_POSIZIONE_AGGIUDICATARI %>')" style="color:black; letter-spacing:0.2em;">
								<img src="img/minus.gif" id="img<%= ParametriServletStipula.TAB_POSIZIONE_AGGIUDICATARI %>"/>SOGGETTI AGGIUDICATARI</label>
								<div id="<%= ParametriServletStipula.TAB_POSIZIONE_AGGIUDICATARI %>" style="display: block;" >				
									
	   	     <c:set var="aggiudicatari" value="${schedaStipula.aggiudicatari}" scope="page"></c:set>   	     
	   	     <% String prefixAgg = PSBD.AGGIUDICATARIO; %>
		        <c:set var="prefixAgg" value="<%= prefixAgg %>" scope="page" />
		
		        <div id="DIVTabella<%= prefixAgg %>" class="scrollTabs" style="height: 200px; width: 99%;">
			       <table id="idTabella<%= prefixAgg %>">
				      <tbody>
					     <tr>
						    <th class="garaTh">Denominazione</th>
						    <th class="garaTh">Codice Fiscale</th>		
						    <th class="garaTh">Partita IVA</th>		
					     </tr>
					     <c:set var="counter" value="0" /> 
					     
					     <c:forEach var="aggCorrente" items="${aggiudicatari}">
						    <c:set var="id" value="row${prefixAgg}${counter}" scope="page"/>
						    <tr id="<c:out value="${id}" />">
						    <c:set var="soggPartecipante" value="${aggCorrente.soggettoPartecipante}" /> 
						      <td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>"><c:out value="${soggPartecipante.denominazione}" /></td>																
							   <td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>"><c:out value="${soggPartecipante.codiceFiscale}" /></td>
							   <td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PARTITA_IVA_AFFIDATARIO %>"><c:out value="${soggPartecipante.partitaIva}" /></td>
						    </tr>
						    <c:set var="counter" value="${counter + 1}" scope="page"/>
					     </c:forEach>																
				      </tbody>
			       </table>
		        </div>	  
		        
		                             </div>
								         </div>
							          </td>
						           </tr>			
			<%-- 
			     <fieldset>	
	   	       <table>
	   	--%>
	   	         <tr>
 			           <th><label >Importo aggiudicazione/affidamento in &euro;</label></th>
 			           <td>
				          <input  name="<%=  PSBD.FIELD_NAME_IMPORTO_AGGIUDICAZIONE%>"  tabindex="<%=++indiceTab%>" style="text-align:right;"  
				          <c:out value="${disabled}"/>
				          type="text" value="<c:out value='${aggiudicazione.importoAggiudicazioneStr}'/>" disabled/>
			           </td>
	   	         </tr>
	             </table>
		        </fieldset>
		       
		        <fieldset>				
		          <table>  
		       
			         <tr>
				        <input type="hidden" name="checkIfOK" id="checkIfOK" value="<%=new Integer(session.getAttribute("checkIfOK").toString()) + 1%>" />
					     <td><input <c:out value="${disabled}"/> type="button" value="Salva" onclick="checkAndAction('check',3,'<%=PSBD.ACTION_SALVA %>')"/></td>					
				        <td><input <c:out value="${noConf}"/>  type="button" value="Conferma" onclick="checkAndAction('check',3,'<%=PSBD.ACTION_CONFERMA %>')"/></td>			      
				        <td><input <c:out value="${disabled}"/> type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_LOAD %>')"/></td>
				      
					     <c:if test="${annullabile eq true}">
                      <td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="loadAnnullamentJSP('<%= ParametriServletStipula.TAB_STIPULA %>')"/></td>					      
					     </c:if>
					     <c:if test="${!UTENTE.ossReg && !UTENTE.RASA && stipula.okCancellazione eq true && rupOk eq true and schedaStipula.delegaScheda eq false and schedaStipula.riaggiudicata eq false}">
						    <td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
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
	   