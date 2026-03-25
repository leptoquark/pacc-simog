<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>

<%--             IMPORT                        --%>
<%@ page import="it.avlp.simog.common.servlet.ParametriServletVariante"%>
<%@ page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@ page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.util.SimogProperties"%>
<%@page import="java.util.HashMap"%>
<%@page import="it.avlp.simog.util.MessageHelper"%>
<%
	String totalAmountDescription = MessageHelper.getMessage(request, "js.info.totalAmountDescription");
	String totalAmountDescriptionJs = totalAmountDescription.replace("\\", "\\\\").replace("'", "\\'").replace("\r", "\\r").replace("\n", "\\n");
%>

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
 <c:set var="listaSchede" value="${sessionScope['lista_variante']}"></c:set>
 <c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>
 
 
 <%-- ************************       Creo il Bean   VARIANTE       ********************************************** --%>
 <jsp:useBean id="schedaVariante" type="it.avlp.simog.beans.variante.SchedaVariante" class="it.avlp.simog.beans.variante.SchedaVariante" scope="request"></jsp:useBean>
 
 <jsp:useBean id="aggiudicazione" type="it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean" class="it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean" scope="request"></jsp:useBean>


 <% 
 	
 	VarianteBean variante =  schedaVariante.getVarianteFE();
 	pageContext.setAttribute("variante",variante);
 	 String dataCreazione = (String)session.getAttribute("data_creazione");
 	 String labelPagina = dataCreazione.compareTo(String.valueOf(SimogProperties.getInstance().getDataAttivazione3043())) >= 0 ? "MODIFICA CONTRATTUALE" : "VARIANTE";
 	 
 	
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
 <c:set var="hide" value="${(datiGara.deleted || variante.confirmed ) || rupOk eq false || UTENTE.ossReg || UTENTE.RASA || (variante.idVariante < 1 && !schedaVariante.aggiungibile) || schedaVariante.delegaScheda || schedaVariante.riaggiudicata}" />
 <%-- Eliminato schedaVariante.readOnly || --%>
 <c:set var="annullabile" value="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && datiGara.deleted ne true && variante.confirmed eq true && variante.richAnn ne true && variante.richDelete ne true  && schedaVariante.delegaScheda eq false and schedaVariante.riaggiudicata eq false}"></c:set>
 <c:set var="disabled" value="${hide ? 'disabled':'' }"></c:set>
  <c:set var="noConf" value="${(hide || (variante.idVariante le 0)) || variante.richAnn eq true ? 'disabled':''}"></c:set>
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
<script type="text/javascript" src="xtree/treeutils.js"></script>
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<div id="dialog"></div> <%-- TICKET ALM #34191 --%>

<%-- ****************************************************************************************************** --%>
<%--                             SCRIPT  per la valutazione a runtime dei subtotali                         --%>
<%-- ****************************************************************************************************** --%>
	
	<script type="text/javascript">

	<!-- MEV MARRA 34469 3.04.8 -->              
    function changeRevPrezzi(idMotVariante) {	       
       
       if(idMotVariante.value == '<%= Costanti.MOTIVO_REVISIONE_PREZZI %>' && idMotVariante.checked === true)
       {
          <!-- alert("change - idMotVariante UGUALE DA 22 A TRUE");-->	          
          document.getElementById("<%= ParametriServletVariante.FIELD_NAME_ID_MOTIVO_REV_PREZZI %>").disabled = false;
          document.getElementById("trMotRevPrezzi").style.display = 'table-row';
       }
       else if(idMotVariante.value == '<%= Costanti.MOTIVO_REVISIONE_PREZZI %>' && idMotVariante.checked === false)
       {
          <!-- alert("change - idMotVariante UGUALE A 22 A FALSE");-->	          
          document.getElementById("<%= ParametriServletVariante.FIELD_NAME_ID_MOTIVO_REV_PREZZI %>").disabled = true;	    
          document.getElementById("trMotRevPrezzi").style.display = 'none';      
       }	    
	}
    /* fine mev 34469 */
		<!--
			function valutaSubTotale() {
				var somma = 0;
				var lavori;
				var servizi;
				var forniture;
				var sicurezza;
				var progettazione;
				var disposizione;
				var ulteriori;
				
					if (document.getElementById('importoLavori').value == "" ) 
						lavori = parseFloat('0');
					else  lavori = parseFloat(document.getElementById('importoLavori').value.replace(/\./g,"").replace(',','.'));
					
					if (document.getElementById('importoServizi').value == "" ) 
						servizi = parseFloat('0');
					else  servizi = parseFloat(document.getElementById('importoServizi').value.replace(/\./g,"").replace(',','.'));
					
					
					if (document.getElementById('importoForniture').value == "" ) 
						forniture = parseFloat('0');
					else  forniture = parseFloat(document.getElementById('importoForniture').value.replace(/\./g,"").replace(',','.'));
					
					
					if (document.getElementById('importoSicurezza').value == "" ) 
						sicurezza = parseFloat('0');
					else  sicurezza = parseFloat(document.getElementById('importoSicurezza').value.replace(/\./g,"").replace(',','.'));
					
					if (document.getElementById('importoProgettazione').value == "" ) 
						progettazione = parseFloat('0');
					else  progettazione = parseFloat(document.getElementById('importoProgettazione').value.replace(/\./g,"").replace(',','.'));


					if (document.getElementById('ulterioriSomme').value == "" ) 
						ulteriori = parseFloat('0');
					else  ulteriori = parseFloat(document.getElementById('ulterioriSomme').value.replace(/\./g,"").replace(',','.'));
					
					if (document.getElementById('importoDisposizione').value == "" ) 
						disposizione = parseFloat('0');
					else  disposizione = parseFloat(document.getElementById('importoDisposizione').value.replace(/\./g,"").replace(',','.'));
				
				somma = parseFloat(lavori+servizi+forniture) ;

				//devo aggiungere i punti delle migliaia
				document.getElementById("subtotale1").value = addMyDotsFromCommaString(somma.toFixed(3).replace('.',','));
				
				somma = parseFloat(somma + sicurezza + progettazione + ulteriori);
				document.getElementById("subtotale2").value = addMyDotsFromCommaString(somma.toFixed(3).replace('.',','));
				somma = parseFloat(somma + disposizione);
				document.getElementById("subtotale3").value = addMyDotsFromCommaString(somma.toFixed(3).replace('.',','));
					
				return true;	
			}
		//-->
	</script>
	
		<!-- MEV 34191 PICCA POP-UP -->
<script type="text/javascript" src="xtree/treeutils.js"></script>
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />

<script>
/* MEV 34191 PICCA POP-UP */
//PARTE CHE APRE LA POPUP INFORMATIVA
function apripopupSchedaVarianti(path, prefix, radioNo, toDo){
	console.log("apripopupSchedaVariantiapripopupSchedaVarianti");
	var dialogArgs = new MyDialogArguments();
	dialogArgs.Sender = window;
	
	//TB: Ticket risoluzione popup
	if (!window.showModalDialog) {
		console.log(" IF apripopupSchedaVariantiapripopupSchedaVarianti");
		return opendialogSchedaVarianti(path, prefix, radioNo, toDo);
	} 			
		  
}

/* MEV 34191 PICCA POP-UP */
function opendialogSchedaVarianti(page, prefix, radioNo, toDo, idDialog) {
  var divDialog = '#dialog';
  if(idDialog)
      divDialog = '#dialog'+idDialog;
             
  var $dialog = $(divDialog)
  .html('<iframe style="border: 0px; " src="' + page + '" width="100%" height="100%"></iframe>')
  .dialog({
    title: "ANAC: Autorit&agrave; Nazionale Anticorruzione",
    autoOpen: false,
    dialogClass: 'no-close',
    modal: true,
    height: 300,
    width: 800,
    draggable:true,
    buttons: {
        Ok: function() {
          $( this ).dialog( "close" );
          checkAndAction(prefix,radioNo, toDo);
        }
     },
    close:function(){  
       dialog_confirm_callback('true', '', '', '','','');
    }
  });
  $dialog.dialog('open');
  
  $('.ui-button').removeClass( "ui-widget" );
}

function dialog_confirm_callback(value) {
	  if (value === 'true') 
	  {	    
	    popupIsClose = value;
	  } 
	  else if(value === 'false') 
	  {
	    popupIsClose = value;
	  }
	  
	  
	}

function checkAndActionWithPopUp(prefix,radioNo, toDo){
	��� console.log("checkAndActionWithPopUp");
	��� console.log(document.getElementById('ID_MOTIVO_VAR8'));
	��� console.log(document.getElementById('ID_MOTIVO_VAR7'));
	���� if ((document.getElementById('ID_MOTIVO_VAR8') && document.getElementById('ID_MOTIVO_VAR8').checked === true )�
	������������ || (document.getElementById('ID_MOTIVO_VAR7') && document.getElementById('ID_MOTIVO_VAR7').checked === true))�
	����� {�����
	�������� console.log("if checkAndActionWithPopUp");���
	������� apripopupSchedaVarianti('popupSchedaVarianti.jsp', prefix, radioNo, toDo);
	����� }�
	����� else
	����� {
	������� checkAndAction(prefix,radioNo, toDo);
	����� }����
}
</script>

<%-- ****************************************************************************************************** --%>
<div id="dialog"></div> <%-- TICKET ALM #34191 --%>
<title>Gestione Schede - <%= labelPagina %> - <%= user.getProfilo() %></title>

</head><body>
<div id="dialog"></div> <%-- TICKET ALM #34191 --%>
	<div id="gabbia" align="left">
		<%@ include file="/include/header.inc" %>			
		<div class="bodypage-e" align="left">
		<%--Header Scheda e Lista Schede gia compilate --%>
			<h1>Gestione Schede - <%= labelPagina %></h1>
			<div  class="hmenu" align="left">	
			  <ul>
			    <%String riScheda =ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=";%>
			     
			      
					<li><a title="Torna alla lista Aggiudicazioni" href="javascript:changePage('<%=riScheda%>${datiGara.idLotto}','Modificato')">Lista Aggiudicazioni</a></li>  
			    <c:if test="${!UTENTE.ossReg && !UTENTE.RASA && variante.idVariante > 0 && datiGara.deleted eq false && rupOk eq true && schedaVariante.aggiungibile && schedaVariante.delegaScheda eq false  and schedaVariante.riaggiudicata eq false}">
			      <li>
			        <c:url  value="<%= ParametriServletVariante.SRV_SCHEDA_VARIANTE %>" var="newMod">
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
			
			<%-- ************************************************************************* --%>
			<%--           Carico la scheda corrente e la lista delle schede               --%>
			<%-- ************************************************************************* --%> 
			<% if(dataCreazione.compareTo(String.valueOf(SimogProperties.getInstance().getDataAttivazione3043())) >= 0) { %>
			 <h2>Lista Schede Modifiche Contrattuali</h2>
			 <% } else { %>
			 <h2>Lista Schede Varianti</h2>
			 <% } %>
		<div style="overflow: auto;height: 13em; width: 100%;"  >
			
			<div class="gara">	 	
			 
		     <table align="center" width="300px">   
			<tr> 
		     	<th class="garaTh">Data approvazione <%= labelPagina.toLowerCase() %></th> 
		     	<th class="garaTh">Data sottoscrizione atto aggiuntivo</th> 
		     	<th class="garaTh">N. giorni di proroga/ Tempo aggiuntivo</th>
		     	<th class="garaTh">Stato scheda</th>
		     	<th class="garaTh">Azioni</th>
		     </tr>
		        <c:set var="counter" value='0' scope="page"/>
			<c:forEach items="${listaSchede}" var="scheda">
				<tr>
					<td class="garaTd"><c:out value="${scheda.dataVerbaleApprovazione}"></c:out></td>
				 	<td  class="garaTd"><c:out value="${scheda.dataAttoAggiuntivo}"></c:out></td>
					<td  class="garaTd"><c:out value="${scheda.numGiorniProroga}"></c:out></td>
					<td  class="garaTd"><c:out value="${scheda.descrizioneStato}"></c:out></td>
					
					<td class="hmenu">
					    <c:url  value="<%= ParametriServletVariante.SRV_SCHEDA_VARIANTE %>" var="modURL">
					    <c:param name="toDo" value="load"></c:param>
					    	<c:param name="toEdit" value="${counter}"></c:param>
					    </c:url>
					    <c:choose >
					    	<c:when test="${!UTENTE.ossReg && !UTENTE.RASA && datiGara.deleted eq false && rupOk eq true && scheda.confirmed eq false && schedaVariante.delegaScheda eq false  and schedaVariante.riaggiudicata eq false}">
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
						<td><input <c:out value="${disabled}"/> type="button" value="Salva" onclick="checkAndActionWithPopUp('check',0,'<%=PSBD.ACTION_SALVA %>')"/></td>
						<td><input <c:out value="${noConf}"/>  type="button" value="Conferma" onclick="checkAndAction('check',0,'<%=PSBD.ACTION_CONFERMA %>')"/></td>
						<td><input <c:out value="${disabled}"/> type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_REIMPOSTA %>')"/></td>
						<c:if test="${annullabile eq true}">
							<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')"/></td>	
						</c:if>			
						<c:if test="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && variante.okCancellazione eq true && schedaVariante.delegaScheda eq false  and schedaVariante.riaggiudicata eq false}">
							<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
						</c:if>
				<c:set var="statoid" value="${variante.idStato}"/>
				<c:set var="statoann" value="${variante.richAnn || variante.richDelete}"/>
				<c:set var="statodesc" value="${variante.descrizioneStato}"/>
				<%@ include file="../include/statoscheda.inc" %>
						
					</tr>
				</table>
			 <fieldset>
			 <h2>Scheda <%= labelPagina %> - <c:out value="${variante.idVariante < 1 ?  'Inserimento' : (hide == true ? 'Visualizzazione' : 'Modifica')}" /></h2>
			<form action="<%=ParametriServletVariante.SRV_SCHEDA_VARIANTE%>" method="post" onkeypress="setFormModified('Modificato')" >
			
					    <input type="hidden" name="checkIfOK" id="checkIfOK" value="<%=new Integer(session.getAttribute(ParametriServlet.checkIfOK).toString()) + 1%>" />
									
				<input type="hidden" name="<%=PSBD.TAB%>" id="<%=PSBD.TAB%>" value="" />	
				<input type="hidden" name="<%=PSBD.ACTION_TYPE%>" id="<%=PSBD.ACTION_TYPE%>" value="" />
				
				<input type="hidden" name="<%=ParametriServletVariante.FIELD_NAME_ID_VARIANTE %>" value='<c:out value="${variante.idVariante}"/>'/>
				<input type="hidden" name="<%=ParametriServletVariante.FIELD_NAME_DATA_INIZIO_VAR %>" value='<c:out value="${variante.dataInizioVar}"/>'/>
				
				<!-- MEV 34469 3.04.8 -->
				<input type="hidden" name="idSelectedMotivRevPrezzi" value='${idSelectedMotivRevPrezzi}'/>
				
				<input type="hidden" id="Modificato"  value="0" />
				
				<fieldset class="gara">
					
				  <table width="100%">
				  	<tr>
						<td align="center" colspan="2"><p class="detailHelp"><strong>RIFERIMENTO AI DATI DELLA FASE DI AGGIUDICAZIONE O DI DEFINIZIONE DI PROCEDURA NEGOZIATA</strong></p></td>
					</tr>
					
					
					
					
					<%-- ********************************************************************************************** --%>
					<%--                                    Prima riga della scheda : CIG                               --%>
					<%-- ********************************************************************************************** --%>
					<tr>
	   					<td><label for="<%= ParametriServlet.FIELD_NAME_CIG %>" >Codice di individuazione dell'appalto (CIG)</label></td>
	   					<td  width="40%" ><c:out value="${datiGara.fullCIG}"/></td>
	   				</tr>
					<%@include file="/include/intestazione.jsp" %>
					<%-- ********************************************************************************************** --%>
					<%--                                    paragrafo      VARIANTE                                             --%>
					<%-- ********************************************************************************************** --%>
					<tr>
						<td align="center" colspan="2"><p class="detailHelp"><strong><%=labelPagina %></strong></p></td>
					</tr>
					
					
					<%-- ********************************************************************************************** --%>
					<%--                Seconda riga della scheda : Data di approvazione della variante                 --%>
					<%-- ********************************************************************************************** --%>
					
					<tr>
	   					<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataModificContrattuale") %> for="<%= ParametriServletVariante.FIELD_NAME_DATA_VERB_APPR %>" >Data di approvazione della <%=labelPagina.toLowerCase() %></label></td>
	   					<td>
							<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  <c:out value="${disabled}"/>
							type="text" id="<%= ParametriServletVariante.FIELD_NAME_DATA_VERB_APPR %>" name="<%= ParametriServletVariante.FIELD_NAME_DATA_VERB_APPR %>" 
							onblur="Calendar.validaData(this)" value="<c:out value='${variante.dataVerbaleApprovazione}'/>" />
							<c:if test="${hide == false}">
								<img src="calendar/img.gif" id="calendarVariante" style="cursor: pointer; border: 1px solid red;" title="Date selector"
											onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
								<script type="text/javascript">
									    Calendar.setup({
								        inputField     :    "<%= ParametriServletVariante.FIELD_NAME_DATA_VERB_APPR %>",     // id of the input field
								        ifFormat       :    "%d/%m/%Y",      // format of the input field
								        button         :    "calendarVariante",  // trigger for the calendar (button ID)
								        align          :    "Tl",           // alignment (defaults to "Bl")
								        singleClick    :    true							       
							    		});					    	
								</script>
							</c:if>
						</td>
	   				</tr>
					<%-- ********************************************************************************************** --%>
					<%--                                    paragrafo      VARIANTE                                             --%>
					<%-- ********************************************************************************************** --%>
					<tr>
						<td align="center" colspan="2"><p class="detailHelp"><strong>MOTIVAZIONE <%= labelPagina %></strong></p></td>
					</tr>
	   				<%-- ********************************************************************************************** --%>
					<%--                        Terza riga della scheda : Motivazioni della variante                               --%>
					<%-- ********************************************************************************************** --%>
	   				
	   				<tr>
	   					<%--  <td><label for="<%= ParametriServletVariante.FIELD_NAME_ID_MOTIVAZIONE%>" >Motivazioni Variante</label></td> --%>
	   					<td>
	   						<c:remove var="listaMotivazioni" />
	   						<c:set var="listaMotivazioni" value="${variante.emvb}" /> 
								<%--  PP disabled="${disabled}"  --%>
    						<u:multibox campo="<%=ParametriServletVariante.FIELD_NAME_ID_MOTIVAZIONE %>" 
    							lista="<%= ParametriServletVariante.BEAN_MOTIVI_VARIANTE %>" 
    							listaCampiSelezionati="listaMotivazioni" 
    							readonly="${hide}"
    							idField="idMotivoVariante" 
    							onchange="setFormModified('Modificato');changeRevPrezzi(this);" />	
						</td>
	   				</tr>
	   				
	   				<!-- MEV 34469 3.04.8 NUOVA LISTA MOTIVO REVISIONE PREZZI -->	   				
	   				 <tr id="trMotRevPrezzi"  style="display:none;" >
						<th><label>Motivo Revisioni Prezzi</label></th>
						<td>							
							<select name="<%= ParametriServletVariante.FIELD_NAME_ID_MOTIVO_REV_PREZZI %>" id="<%= ParametriServletVariante.FIELD_NAME_ID_MOTIVO_REV_PREZZI %>">
							    <option value=""><c:out value="" /></option>
							    <c:forEach items="${motivi_revisione_prezzi}" var="motivRevPrezzi">
							        <option value="${motivRevPrezzi.key}" ${motivRevPrezzi.key eq idSelectedMotivRevPrezzi ? 'selected="selected"' : ''} ><c:out value="${motivRevPrezzi.value}" /></option>
							    </c:forEach>
							</select>
						</td>
					</tr>
	   				
	   				<%-- ********************************************************************************************** --%>
					<%--                                    paragrafo DA COMPILARE IN CASO DI PROROGA TECNICA                                                  --%>
					<%-- ********************************************************************************************** --%>
	   				<tr><td colspan="2"><hr></td></tr>
	   				
	   				<!-- MEV 34191 3.04.8 -->
	   				
				   	
				   	<tr>
	   					<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_linkVarianti") %> for="<%= ParametriServletVariante.FIELD_NAME_LINK_VARIANTI%>" >URL documentazione varianti in corso d'opera</label></td>
	   					<td>
							<input name="<%= ParametriServletVariante.FIELD_NAME_LINK_VARIANTI%>"  tabindex="<%=++indiceTab%>" style="width:100%" maxlength="1000" <c:out value="${disabled}"/>
							type="text" value="<c:out value='${variante.linkVarianti}' />"  />
							
						</td>
	   				</tr>
	   				
	   				<!--FINE MEV 34191 3.04.8 -->
	   				
	   				<%--  TICKET ALM - 3.04.3 PT --%>
	   				  <% if(dataCreazione.compareTo(String.valueOf(SimogProperties.getInstance().getDataAttivazione3043())) >= 0) { %>	
						<tr>
							<th><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_CIGProcedura") %> for="">CIG della nuova procedura avviata</label></th>		
						<td>
							<input type="text" maxlength="10" value="<c:out value='${variante.cigProcedura}' />"  name="<%= ParametriServletVariante.FIELD_NAME_CIG_PROCEDURA %>" <c:out value="${disabled}"/>  >
						</td>
						</tr>	
					<% } %>
	   				<%-- FINE  TICKET ALM - 3.04.3 PT --%>
	 
	   				<%-- ********************************************************************************************** --%>
					<%--                      Quarta riga della scheda : Altre Motivazioni                              --%>
					<%-- ********************************************************************************************** --%>
					
					<tr>
	   					<td><label for="<%= ParametriServletVariante.FIELD_NAME_ALTRE_MOTIVAZIONI%>" >Cause della <%= labelPagina.toLowerCase() %></label></td>
	   					<td>
							<input name="<%= ParametriServletVariante.FIELD_NAME_ALTRE_MOTIVAZIONI%>"  tabindex="<%=++indiceTab%>" style="width:100%" maxlength="1000" <c:out value="${disabled}"/>
							type="text" value="<c:out value='${variante.altreMotivazioni}' />"  />
							
						</td>
	   				</tr>
	   				
	   				<%-- ********************************************************************************************** --%>
					<%--                                    paragrafo                                                   --%>
					<%-- ********************************************************************************************** --%>
	   				<tr>
						<td align="center" colspan="2"><p class="detailHelp"><strong>QUADRO ECONOMICO <%= labelPagina %></strong></p></td>
					</tr>	  
					
					<%-- ********************************************************************************************** --%>
					<%--                   Quinta riga della scheda : Importo contrattuale   LAVORI                     --%>
					<%-- ********************************************************************************************** --%>
					
					<tr>
	   					<td><label  <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_ImportoRideterminato") %> for="<%= ParametriServletVariante.FIELD_NAME_IMP_RIDET_LAVORI%>" >Importo contrattuale rideterminato componente lavori in &euro; <br/>(al netto dell'IVA e degli oneri di sicurezza) <img title="<%= totalAmountDescription %>" style="cursor: pointer" onclick="if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('info.totalAmountDescription'); } else { alert('<%= totalAmountDescriptionJs %>'); }" src="img/icon_info_sml.gif"> </label></td>
	   					<td>
							<input name="<%= ParametriServletVariante.FIELD_NAME_IMP_RIDET_LAVORI%>"  tabindex="<%=++indiceTab%>" style="text-align:right;width:120px;" <c:out value="${disabled}"/>
							type="text" id="importoLavori" value="<c:out value='${variante.impRidetLavoriStr}'/>"  onblur="validateAmount(this);valutaSubTotale()"/>
						</td>
	   				</tr>
	   				
	   			    <%-- ********************************************************************************************** --%>
					<%--                   Sesta riga della scheda : Importo contrattuale   SERVIZI                     --%>
					<%-- ********************************************************************************************** --%>
					
					<tr>
	   					<td><label  <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_ImportoRideterminato") %> for="<%= ParametriServletVariante.FIELD_NAME_IMP_RIDET_SERVIZI%>" >Importo contrattuale rideterminato componente servizi in &euro; <br/>(al netto dell'IVA e degli oneri di sicurezza) <img title="<%= totalAmountDescription %>" style="cursor: pointer" onclick="if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('info.totalAmountDescription'); } else { alert('<%= totalAmountDescriptionJs %>'); }" src="img/icon_info_sml.gif"> </label></td>
	   					<td>
							<input  name="<%= ParametriServletVariante.FIELD_NAME_IMP_RIDET_SERVIZI%>"  tabindex="<%=++indiceTab%>" style="text-align:right;width:120px;" <c:out value="${disabled}"/>
							type="text" id="importoServizi" value="<c:out value='${variante.impRidetServiziStr}'/>"  onblur="validateAmount(this);valutaSubTotale()"/>
						</td>
	   				</tr>
	   				
	   				<%-- ********************************************************************************************** --%>
	   			
					<%-- ********************************************************************************************** --%>
					<%--                  Settima riga della scheda : Importo contrattuale FORNITURE                    --%>
					<%-- ********************************************************************************************** --%>
					
					<tr>
	   					<td><label  <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_ImportoRideterminato") %> for="<%= ParametriServletVariante.FIELD_NAME_IMP_DIRET_FORNIT%>" >Importo contrattuale rideterminato componente forniture in &euro; <br/>(al netto dell'IVA e degli oneri di sicurezza) <img title="<%= totalAmountDescription %>" style="cursor: pointer" onclick="if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('info.totalAmountDescription'); } else { alert('<%= totalAmountDescriptionJs %>'); }" src="img/icon_info_sml.gif"> </label></td>
	   					<td>
							<input  name="<%= ParametriServletVariante.FIELD_NAME_IMP_DIRET_FORNIT%>"  tabindex="<%=++indiceTab%>" style="text-align:right;width:120px;" <c:out value="${disabled}"/>
							type="text" id="importoForniture" value="<c:out value='${variante.impRidetFornitStr}'/>"  onblur="validateAmount(this);valutaSubTotale()"/>
						</td>
	   				</tr>
	   				
	   				<%-- ********************************************************************************************** --%>
					<%--                    Ottava riga della scheda : SUBTOTALE (5+6+7)                                --%>
					<%-- ********************************************************************************************** --%>
					
					<tr>
	   					<td>Subtotale</td>
	   					<td>
	   						<input type="text" id="subtotale1" readonly="readonly" value="" style="text-align:right;font-weight: bold; width:120px;"/>
	   						<%-- 
	   						<c:set var='subtotale1' id="subtotale1" value='${variante.impRidetFornit + variante.impRidetServizi + variante.impRidetLavori}' />  
	   						<c:set var='subtotale1'  value='${valutaSubTotale()}' />
							<c:out value='${subtotale1}' />
							 --%>
						</td>
	   				</tr>
	   				
	   				<%-- ********************************************************************************************** --%>
					<%--                Nona riga della scheda : Nuovo importo totale per SICUREZZA                     --%>
					<%-- ********************************************************************************************** --%>
					
					<tr>
	   					<td><label for="<%= ParametriServletVariante.FIELD_NAME_IMP_SICUREZZA%>" >Nuovo importo totale per l'attuazione della sicurezza <br/>(non soggetto a ribasso)</label></td>
	   					<td>
							<input  name="<%= ParametriServletVariante.FIELD_NAME_IMP_SICUREZZA%>"  tabindex="<%=++indiceTab%>" style="text-align:right;width:120px;" <c:out value="${disabled}"/>
							type="text" id="importoSicurezza" value="<c:out value='${variante.impSicurezzaStr}'/>"  onblur="validateAmount(this);valutaSubTotale()"/>
						</td>
	   				</tr>
	   				
					<%-- ********************************************************************************************** --%>
					<%--                   Decima riga della scheda : importo progettazione                             --%>
					<%-- ********************************************************************************************** --%>
					
					<tr>
	   					<td><label for="<%= ParametriServletVariante.FIELD_NAME_IMP_PROGETTAZIONE%>" >Importo progettazione</label></td>
	   					<td>
							<input  name="<%= ParametriServletVariante.FIELD_NAME_IMP_PROGETTAZIONE%>"  tabindex="<%=++indiceTab%>" style="text-align:right;width:120px;" <c:out value="${disabled}"/>
							type="text" id="importoProgettazione" value="<c:out value='${variante.impProgettazioneStr}'/>"  onblur="validateAmount(this);valutaSubTotale()"/>
							
						</td>
	   				</tr>
					
					<%-- ********************************************************************************************** --%>
					<%--                   Decima a riga della scheda : ulteriori somme         					    --%>
					<%-- ********************************************************************************************** --%>
					
					<tr>
	   					<td><label for="<%= ParametriServletVariante.FIELD_NAME_ULTERIORI_SOMME%>" >Ulteriori somme non soggette a ribasso</label></td>
	   					<td>
							<input  name="<%= ParametriServletVariante.FIELD_NAME_ULTERIORI_SOMME%>"  tabindex="<%=++indiceTab%>" style="text-align:right;width:120px;" <c:out value="${disabled}"/>
							type="text" id="ulterioriSomme" value="<c:out value='${variante.ulterioriSommeStr}'/>"  onblur="validateAmount(this);valutaSubTotale()"/>
							
						</td>
	   				</tr>
	   				
					<%-- ********************************************************************************************** --%>
					<%--                   undicesima riga della scheda : SUBTOTALE (8+9+10+10a)                            --%>
					<%-- ********************************************************************************************** --%>
					
					<tr>
	   					<td>Subtotale</td>
	   					<td>
	   						<input type="text" name="subtotale2" id="subtotale2" readonly="readonly" value="${subtotale}" style="text-align:right;font-weight: bold;width:120px;" />
	   						<%-- 
	   						<c:set var='subtotale2'  value='${subtotale1+variante.impSicurezza+variante.impRidetServizi+impProgettazione}' />
							<c:out value='${subtotale2}' />
							--%>
						</td>
	   				</tr>
	   				
					<%-- ********************************************************************************************** --%>
					<%--          Dodicesima riga della scheda : nuovo importo totale SOMME A DISPOSIZIONE              --%>
					<%-- ********************************************************************************************** --%>
					
					<tr>
	   					<td><label for="<%= ParametriServletVariante.FIELD_NAME_IMP_DISPOSIZIONE%>" >Nuovo importo totale spese a disposizione</label></td>
	   					<td>
							<input  name="<%= ParametriServletVariante.FIELD_NAME_IMP_DISPOSIZIONE%>"  tabindex="<%=++indiceTab%>" style="text-align:right;width:120px;" <c:out value="${disabled}"/>
							type="text" id="importoDisposizione" value="<c:out value='${variante.impDisposizioneStr}'/>"  onblur="validateAmount(this);valutaSubTotale()"/>
							
						</td>
	   				</tr>
					
					<%-- ********************************************************************************************** --%>
					<%--                   Tredicesima riga della scheda : SUBTOTALE (11+12)                            --%>
					<%-- ********************************************************************************************** --%>
					
					<tr>
	   					<td>Subtotale</td>
	   					<td>
	   						<input type="text" name="subtotale3" id="subtotale3" readonly="readonly" value="${subtotale3}" style="text-align:right;font-weight: bold;width:120px;"/> 
						<%--	  
	 						<c:set var='subtotale3'  value='${subtotale2+variante.impDisposizione}' /> 
							<c:out value='${subtotale3}' />
						--%>
						</td>
	   				</tr>
					
					
					<%-- ********************************************************************************************** --%>
					<%--                                    paragrafo                                                   --%>
					<%-- ********************************************************************************************** --%>
	   				<tr>
						<td align="center" colspan="2"><p class="detailHelp"><strong>ATTI AGGIUNTIVI</strong></p></td>
					</tr>	  
					
					<%-- ********************************************************************************************** --%>
					<%--           Quattordicesima riga della scheda : Data di approvazione della variante              --%>
					<%-- ********************************************************************************************** --%>
					<tr>
	   					<td><label for="<%= ParametriServletVariante.FIELD_NAME_DATA_ATTO_AGGIUNTIVO %>" >Data sottoscrizione eventuale atto aggiuntivo/sottomissione</label></td>
	   					<td>
							<input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  <c:out value="${disabled}"/>
							type="text" id="<%= ParametriServletVariante.FIELD_NAME_DATA_ATTO_AGGIUNTIVO %>" name="<%= ParametriServletVariante.FIELD_NAME_DATA_ATTO_AGGIUNTIVO %>" 
							onblur="Calendar.validaData(this)" value="<c:out value='${variante.dataAttoAggiuntivo}'/>" />
							<c:if test="${hide == false}">
								<img src="calendar/img.gif" id="calendarAttoAgg" style="cursor: pointer; border: 1px solid red;" title="Date selector"
											onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
								<script type="text/javascript">
									    Calendar.setup({
								        inputField     :    "<%= ParametriServletVariante.FIELD_NAME_DATA_ATTO_AGGIUNTIVO %>",     // id of the input field
								        ifFormat       :    "%d/%m/%Y",      // format of the input field
								        button         :    "calendarAttoAgg",  // trigger for the calendar (button ID)
								        align          :    "Tl",           // alignment (defaults to "Bl")
								        singleClick    :    true							       
							    		});					    	
								</script>
							</c:if>
						</td>
	   				</tr>
					
					<%-- ********************************************************************************************** --%>
					<%--                      Quindicesima riga della scheda : GIORNI DI PROROGA                        --%>
					<%-- ********************************************************************************************** --%>
					
					<tr>
	   					<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_GiorniProroga") %> for="<%= ParametriServletVariante.FIELD_NAME_NUM_GIORNI_PROROGA%>" >Numero di giorni di proroga concessi/ Tempo aggiuntivo rispetto ai termini contrattuali</label></td>
	   					<td>
							<input  name="<%= ParametriServletVariante.FIELD_NAME_NUM_GIORNI_PROROGA%>"  tabindex="<%=++indiceTab%>" style="text-align:right;width:120px;" <c:out value="${disabled}"/>
							type="text" value="<c:out value='${variante.numGiorniProroga}'/>"  onblur="validateNumber(this);"/>
							
						</td>
	   				</tr>
				  </table>
				  
				  <input type="hidden"  value="save" name="toDo" id="toDo"/>
				
			</fieldset>
			 
				<table >	
					<tr>
						<td><input <c:out value="${disabled}"/> type="button" value="Salva" onclick="checkAndActionWithPopUp('check',0,'<%=PSBD.ACTION_SALVA %>')"/></td>
						<td><input <c:out value="${noConf}"/>  type="button" value="Conferma" onclick="checkAndAction('check',0,'<%=PSBD.ACTION_CONFERMA %>')"/></td>
						<td><input <c:out value="${disabled}"/> type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_REIMPOSTA %>')"/></td>
						<c:if test="${annullabile eq true}">
							<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')"/></td>	
						</c:if>			
						<c:if test="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && variante.okCancellazione eq true && schedaVariante.delegaScheda eq false  and schedaVariante.riaggiudicata eq false}">
							<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
						</c:if>
				<c:set var="statoid" value="${variante.idStato}"/>
				<c:set var="statoann" value="${variante.richAnn  || variante.richDelete}"/>
				<c:set var="statodesc" value="${variante.descrizioneStato}"/>
				<%@ include file="../include/statoscheda.inc" %>
						
					</tr>
				</table>
			</form>
			
			
			<script type="text/javascript">
				valutaSubTotale();
				/* MEV 34469 3.04.8 */
				if( '${idMotivoVarianteAttr}' == 'true')
                {
                     <!--alert("FINE PAGINA - idMotivoVarianteAttr UGUALE A 22");-->                    
                     document.getElementById("<%= ParametriServletVariante.FIELD_NAME_ID_MOTIVO_REV_PREZZI %>").disabled = false;
                     document.getElementById("trMotRevPrezzi").style.display = 'table-row';                    
                }
                else
                {
                     <!--alert("FINE PAGINA - idMotivoVarianteAttr DIVERSO DA 22");-->                
                     document.getElementById("<%= ParametriServletVariante.FIELD_NAME_ID_MOTIVO_REV_PREZZI %>").disabled = true;
                     document.getElementById("trMotRevPrezzi").style.display = 'none';               
                }
				/*FINE MEV 34469 3.04.8 */
			</script>
			
			
			</fieldset>
		</div>  
		<%@ include file="include/newfooter.inc" %>
	</div>
</body>



<%@page import="it.avlp.simog.beans.variante.VarianteBean"%>
</html>
