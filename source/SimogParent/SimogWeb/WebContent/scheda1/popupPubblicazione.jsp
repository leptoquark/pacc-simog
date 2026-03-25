<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.beans.CIGBean"%>
<%@ page import="it.avlp.simog.beans.StatiScheda"%>
<%@ page import="java.math.BigDecimal"%>
<%@page import="it.avlp.simog.db.Costanti"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletLotto"%>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ taglib prefix="x" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="h" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% int indiceTab = 0;%>

<!-- calendar stylesheet -->
<link rel="stylesheet" type="text/css" media="all" href="calendar/calendar-blue.css" title="win2k-cold-1" />

<!-- main calendar program -->
<script type="text/javascript" src="calendar/calendar.js"></script>

<!-- language for the calendar -->
<%@ include file="../include/calendar-dynamic.inc" %>

<script type="text/javascript" src="xtree/treeutils.js"></script>

<!-- the following script defines the Calendar.setup helper function, which makes
     adding a calendar a matter of 1 or 2 lines of code. -->
<script type="text/javascript" src="calendar/calendar-setup.js"></script>
<script type="text/javascript" src="script/pageutils.js"></script>
<% String currentDate = PageHelper.getCurrentDate(); %>

<script type="text/javascript">
/**
 * Funzione checkAndGo per popupPubblicazione.jsp
 * Esegue il submit del form dopo aver impostato l'azione
 */
function checkAndGo() {
	// Prova diversi modi per trovare il form
	var form = document.forms['pubblicaAvvisoAggiudicazione'] || 
	           document.getElementsByName('pubblicaAvvisoAggiudicazione')[0] ||
	           document.querySelector('form[name="pubblicaAvvisoAggiudicazione"]') ||
	           (document.forms && document.forms.length > 0 ? document.forms[0] : null);
	
	if (form) {
		var toDoField = document.getElementById('toDo');
		if (toDoField) {
			// Imposta l'azione per salvare i dati di pubblicazione
			toDoField.value = 'salvaAvviso';
		}
		form.submit();
	} else {
		console.error('Form pubblicaAvvisoAggiudicazione non trovato');
		alert('Errore: form non trovato');
	}
}
</script>

<title>SIMOG - <utils:message key="scheda.pubblicazioneAvvisoAggiudicazione" /></title>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">

</head>
<%
String codiceGara = request.getParameter(ParametriServlet.SESSION_ID_GARA);
%>
<body>

<!-- TB: ticket popup modali. Import css e js -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<div id="dialog"></div>
<!-- fine import popup modali -->

<div id="gabbia">
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuGara.inc" %>
	
	<div id="bodypage">
		<div class="bodypage-e">
			<h1><utils:message key="scheda.pubblicazioneAvvisoAggiudicazione" /></h1>
				<%@ include file="include/gestisciErrore.inc" %>
			<div class="hmenu">
				<ul><li><a title="<utils:message key="button.ritorna" plain="true" />" href="javascript:history.back()"><utils:message key="button.ritorna" /></a></li></ul>
			</div><%-- hmenu --%>
			
		<form name="pubblicaAvvisoAggiudicazione" action="<%=ParametriServlet.SRV_AVVISO_AGGIUDICAZIONE %>" method="post" >
		   <input type="hidden" name="<%= ParametriServlet.SESSION_ID_GARA%>" value="<%= codiceGara%>" />	
				
			<%-- BEGIN campi pubblicit� dell'appalto 3.0 --%>
			<h4>Pubblicit� dell'appalto</h4>
	   <table>
	     <tbody>
	       <c:set var="pubblicazione" value="${pubblicazione}" scope="page"></c:set>
	       <c:set var="hide" value="${(false)}" />
	       <c:set var="disabled" value="${hide ? 'disabled':'' }"></c:set>
	       <c:set var="pubblicita" value="${(false)}"></c:set>		
	       <c:set var="pubbModificabile" value="${(true)}"></c:set>		     
	       <%-- <%@ include file="include/datiPubblicazione.jsp" --%>
	       
	       <tr>
	         <td><label >Gazzetta Ufficiale Comunit� Europea - GUCE</label></td>
 			   <td>
				  <input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')" 
				    <c:out value="${disabled}"/> <c:if test="${pubblicita == true}"> readonly="readonly" </c:if>
					 type="text" id="inputGazzettaCE" name="<%=  ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE %>" 
					 onblur="Calendar.validaData(this)" value="<c:out value='${pubblicazione.dataGuce}'/>">
				    <%-- vecchio controllo <c:if test="${hide == false}"> --%>
				    <c:if test="${pubbModificabile}">
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
			 <%-- 
	   	 </tr>   	
		    <tr>
		    --%>
			   <th><label for="numeroGuce"><utils:message key="scheda.numero" /></label></th>
				<td>
					<input maxlength="20"  
					type="text" id="numeroGuce"  
					name="<%= ParametriServlet.FIELD_NAME_NUMERO_GUCE %>" 
					<%-- value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_NUMERO_GUCE %>" />" --%>
					value="<c:out value='${pubblicazione.numeroGuce}'/>">	
				</td>
		    </tr>
		    <tr>
	         <td><label ><utils:message key="scheda.gazzettaUfficialeBollettinoRegionale" /></label></td>
 			   <td>
				  <input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  
				    <c:out value="${disabled}"/> <c:if test="${pubblicita == true}"> readonly="readonly" </c:if>
					   type="text" id="inputGazzettaBR" name="<%=  ParametriServlet.FIELD_NAME_BOLLETTINO_REGIONALE %>" 
					   onblur="Calendar.validaData(this)" value="<c:out value='${pubblicazione.dataBore}'/>">
				    <c:if test="${pubbModificabile}">
					   <img src="calendar/img.gif" id="calendarGazzettaBR" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
					   <script type="text/javascript">
						    Calendar.setup({
					        inputField     :    "inputGazzettaBR",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendarGazzettaBR",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true							       
				    		});					    	
					  </script>
				   </c:if>
			   </td>
	   	<%-- 
	   	 </tr>   	
		    <tr>
		    --%>
				<th><label for="numeroBore">Numero</label></th>
				  <td>
					 <input maxlength="20"  
					   type="text" id="numeroBore"  
					   name="<%= ParametriServlet.FIELD_NAME_NUMERO_BORE %>" 
					   <%-- value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_NUMERO_BORE %>" />"--%>
					   value="<c:out value='${pubblicazione.numeroBore}'/>">
				  </td>
		    </tr>
		    <tr>
	         <td><label >Gazzetta Ufficiale Repubblica Italiana - GURI</label></td>
 			   <td>
				  <input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  
				    <c:out value="${disabled}"/> <c:if test="${pubblicita == true}"> readonly="readonly" </c:if>
					   type="text" id="inputGazzettaRI" name="<%=  ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI %>" 
					   onblur="Calendar.validaData(this)" value="<c:out value='${pubblicazione.dataGuri}'/>">
				    <c:if test="${pubbModificabile}">
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
	   	<%-- 
	   	 </tr>   	
		    <tr>
		    --%>
				<th><label for="numeroGuri">Numero</label></th>
				<td>
					<input maxlength="20"
					type="text" id="numeroGuri"  
					name="<%= ParametriServlet.FIELD_NAME_NUMERO_GURI %>" 
					<%-- value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_NUMERO_GURI %>" />"--%>
					value="<c:out value='${pubblicazione.numeroGuri}'/>">		
				</td>
	   	 </tr>   	
		    <tr>
	        <td><label >Albo pretorio del Comune ove si eseguono i lavori</label></td>
 			  <td>
				 <input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  
				   <c:out value="${disabled}"/> <c:if test="${pubblicita == true}"> readonly="readonly" </c:if>
					  type="text" id="inputAP" name="<%=  ParametriServlet.FIELD_NAME_ALBO_PRETORIO %>" 
					  onblur="Calendar.validaData(this)" value="<c:out value='${pubblicazione.dataAlbo}'/>">
				   <c:if test="${pubbModificabile}">
					  <img src="calendar/img.gif" id="calendarAP" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
					  <script type="text/javascript">
						    Calendar.setup({
					        inputField     :    "inputAP",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendarAP",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true							       
				    		});					    	
					  </script>
				  </c:if>
			  </td>
	   	</tr>
   		<tr>
 			  <th><label >Quotidiani nazionali</label></th>
 			  <td>
				 <input  name="<%=  ParametriServlet.FIELD_NAME_QUOTIDIANI_NAZIONALI%>"  tabindex="<%=++indiceTab%>" style="text-align:right;"  
				   <c:out value="${disabled}"/> <c:if test="${pubblicita == true}"> readonly="readonly" </c:if>
				     type="text" value="<c:out value='${pubblicazione.quotidianiNaz}'/>" onblur="validateNumber(this)" maxlength="9"/>
			  </td>
	   	</tr>
	   	<tr>
 			  <th><label >Quotidiani locali</label></th>
 			  <td>
				 <input  name="<%=  ParametriServlet.FIELD_NAME_QUOTIDIANI_REGIONALI%>"  tabindex="<%=++indiceTab%>" style="text-align:right;" 
				 <c:out value="${disabled}"/> <c:if test="${pubblicita == true}"> readonly="readonly" </c:if>
				   type="text" value="<c:out value='${pubblicazione.quotidianiReg}'/>"  onblur="validateNumber(this)" maxlength="9"/>
			  </td>
	   	</tr> 
	   	<tr>
 			  <th><label >Periodici</label></th>
 			  <td>
				 <input  name="<%=  ParametriServlet.FIELD_NAME_PERIODICI%>"  tabindex="<%=++indiceTab%>" style="text-align:right;" 
				 <c:out value="${disabled}"/> <c:if test="${pubblicita == true}"> readonly="readonly" </c:if>
				   type="text" value="<c:out value='${pubblicazione.periodici}'/>"  onblur="validateNumber(this)" maxlength="4"/>
			  </td>
	   	</tr>   
	   	<tr>
			   <th><label >Sito Informatico Ministero Infrastrutture<br>e piattaforma digitale ANAC tramite i sistemi<br>informatizzati regionali</label></th>
		  	     <td>
		  	       <c:if test="${pubblicita == true}">
		  	         <input  tabindex="<%=++indiceTab%>" id="check2Y" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_MIN_INF_TRASP %>" value="S" <c:out value="${pubblicazione.sitoMinisteroInfTrasp == 'S' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />SI 
  			         <input  tabindex="<%=++indiceTab%>" id="check2N" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_MIN_INF_TRASP %>" value="N" <c:out value="${pubblicazione.sitoMinisteroInfTrasp == 'N' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />NO			   
		  	       </c:if>
		  	       <c:if test="${pubblicita == false}">  				  	    
		  	         <input  tabindex="<%=++indiceTab%>" id="check2Y" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_MIN_INF_TRASP %>" value="S" <c:out value="${pubblicazione.sitoMinisteroInfTrasp == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />SI 
  			         <input  tabindex="<%=++indiceTab%>" id="check2N" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_MIN_INF_TRASP %>" value="N" <c:out value="${pubblicazione.sitoMinisteroInfTrasp == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />NO
  			       </c:if>
  			     </td>
		    </tr>
		    <tr>
			   <th><label >Sito Informatico Osservatorio Contratti Pubblici</label></th>
		  	     <td>
		  		    <c:if test="${pubblicita == true}">
		  	         <input  tabindex="<%=++indiceTab%>" id="check3Y" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_OSSERVATORIO_CP %>" value="S" <c:out value="${pubblicazione.sitoOsservatorioCP == 'S' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />SI 
  			         <input  tabindex="<%=++indiceTab%>" id="check3N" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_OSSERVATORIO_CP %>" value="N" <c:out value="${pubblicazione.sitoOsservatorioCP == 'N' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />NO
		          </c:if>
	 	          <c:if test="${pubblicita == false}">  				    
		  	         <input  tabindex="<%=++indiceTab%>" id="check3Y" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_OSSERVATORIO_CP %>" value="S" <c:out value="${pubblicazione.sitoOsservatorioCP == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />SI 
  			         <input  tabindex="<%=++indiceTab%>" id="check3N" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_OSSERVATORIO_CP %>" value="N" <c:out value="${pubblicazione.sitoOsservatorioCP == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />NO
  			       </c:if>
  			     </td>
		    </tr>
		    <tr>
			   <th><label >Profilo del Committente</label></th>
		  	     <td>
		  	       <c:if test="${pubblicita == true}">
                  <input  tabindex="<%=++indiceTab%>" id="check1Y" type="radio" name="<%= ParametriServlet.FIELD_NAME_PROFILO_COMMITTENTE %>" value="S" <c:out value="${pubblicazione.profiloCommitente == 'S' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />SI 
  			         <input  tabindex="<%=++indiceTab%>" id="check1N" type="radio" name="<%= ParametriServlet.FIELD_NAME_PROFILO_COMMITTENTE %>" value="N" <c:out value="${pubblicazione.profiloCommitente == 'N' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />NO
                </c:if>		  	   
		  	       <c:if test="${pubblicita == false}">  
		  	         <input  tabindex="<%=++indiceTab%>" id="check1Y" type="radio" name="<%= ParametriServlet.FIELD_NAME_PROFILO_COMMITTENTE %>" value="S" <c:out value="${pubblicazione.profiloCommitente == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />SI 
  			         <input  tabindex="<%=++indiceTab%>" id="check1N" type="radio" name="<%= ParametriServlet.FIELD_NAME_PROFILO_COMMITTENTE %>" value="N" <c:out value="${pubblicazione.profiloCommitente == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />NO
                </c:if>  			
  			     </td>
		    </tr>
		    <tr>
			   <th><label for="Link Sito Committente">Link Sito Committente</label></th>
				  <td colspan="3">
					 <input maxlength="250"  size="100%"
					 type="text" id="linkSitoCommittente"
					 name="<%= ParametriServlet.FIELD_NAME_LINK_SITO_COMMITTENTE %>" 
					 <%-- value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_LINK_SITO_COMMITTENTE %>" />"> --%>
				    value="<c:out value='${pubblicazione.linkSitoCommittente}'/>">
				  </td>
		    </tr>
		   <%--gm nuovo codice estensione pubblicazione bandi 
		   <tr>
			   <th><label >Flag beni culturali</label></th>
		  	     <td>
		  	       <c:if test="${pubblicita == true}">
                  <input  tabindex="<%=++indiceTab%>" id="<%= ParametriServlet.S_FIELD_NAME_FLAG_BENICULT%>" type="radio" name="<%= ParametriServlet.FIELD_NAME_FLAG_BENICULT %>" value="S" <c:out value="${pubblicazione.flag_benicult == 'S' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />SI 
  			         <input  tabindex="<%=++indiceTab%>" id="<%= ParametriServlet.N_FIELD_NAME_FLAG_BENICULT%>" type="radio" name="<%= ParametriServlet.FIELD_NAME_FLAG_BENICULT %>" value="N" <c:out value="${pubblicazione.flag_benicult == 'N' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />NO
                </c:if>		  	   
		  	       <c:if test="${pubblicita == false}">  
		  	         <input  tabindex="<%=++indiceTab%>" id="<%= ParametriServlet.S_FIELD_NAME_FLAG_BENICULT%>" type="radio" name="<%= ParametriServlet.FIELD_NAME_FLAG_BENICULT %>" value="S" <c:out value="${pubblicazione.flag_benicult == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />SI 
  			         <input  tabindex="<%=++indiceTab%>" id="<%= ParametriServlet.N_FIELD_NAME_FLAG_BENICULT%>" type="radio" name="<%= ParametriServlet.FIELD_NAME_FLAG_BENICULT %>" value="N" <c:out value="${pubblicazione.flag_benicult == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />NO
                </c:if>  			
  			     </td>
		    </tr>
		    --%>
	    </tbody>
	  </table>
			<%-- END campi pubblicit� dell'appalto 3.0 --%>
			
		<% if ( SimogProperties.getInstance().isDocumentiAbilitato() ) { %>
			<br>
			<div id="divAllegati">
				<h4>Allegati al bando di gara</h4>
				<table>
				<tr>
				<th><label for="">Avviso di Aggiudicazione</label></th>
				<% String func =  "apripopup('" + ParametriServlet.SRV_GESTISCI_ALLEGATI 
						+ "?" + ParametriServlet.SESSION_ID_GARA + "=" + codiceGara 
						+ "&" + ParametriServlet.TIPODOC + "=" + PubblicazioneBean.TipoDocumento.AVVISO.getCodice()
						+ "&" + ParametriServlet.RETFIELD+ "=" + ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE_DESC + "'); return false;";
				%>
				<td><input type="button" 
								onclick="<%= func %>" 
								value="Allega Bando di Gara" />
					<input type="hidden" id="<%= ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE %>" name="<%= ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE %>"
						value='<h:requestParameter property="<%= ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE %>" defaultValue="<%=(String)request.getAttribute(ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE) %>"/>'/>
				</td>
				<td>
				<input type="text" id="<%= ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE_DESC %>" name="<%= ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE_DESC %>" 
					value='<h:requestParameter property="<%= ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE_DESC %>" defaultValue="<%=(String)request.getAttribute(ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE_DESC) %>"/>' readonly="readonly"/>
				</td>
				</tr>
			</table>
			</div>
			<br>
		<% } %>
			
	  <div class="infoBlock">	
			 <div class="leftLineInfo">
			 <%  
			 if (user.isRSSAorRUP()) { %>
			 <%-- <input type="submit" value="Procedi">--%>
			 <input type="button" value="Procedi" onclick="checkAndGo()">
			 <input type="hidden"  value="" name="toDo" id="toDo"/>
			 <input type="button" value="Reimposta" onclick="reimpostaForm('<%=ParametriServlet.ACTION_CARICA_GARA %>')"/>
			 <% } %>			 
		  </div>
     </div>
	</form>
			    
		</div><%-- bodypage-e --%>
	</div><%-- bodypage --%>
<%@ include file="include/newfooter.inc" %>
</div><%-- gabbia --%>

</body>

<%@page import="it.avlp.simog.beans.PubblicazioneBean"%>
</html>