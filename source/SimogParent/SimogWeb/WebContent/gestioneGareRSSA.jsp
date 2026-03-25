<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/newbasicHeader.inc" %>
<title>Gestione Gare</title>
</head>
<%@ include file="include/controlloSessione.inc" %>

<%@ taglib prefix="h" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.avlp.simog.beans.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<!-- calendar stylesheet -->
<link rel="stylesheet" type="text/css" media="all" href="calendar/calendar-blue.css" title="win2k-cold-1" />

<!-- main calendar program -->
<script type="text/javascript" src="calendar/calendar.js"></script>

<!-- language for the calendar -->
<%@ include file="include/calendar-dynamic.inc" %>

<!-- the following script defines the Calendar.setup helper function, which makes
     adding a calendar a matter of 1 or 2 lines of code. -->
<script type="text/javascript" src="calendar/calendar-setup.js"></script>
<script type="text/javascript" src="script/pageutils.js"></script>

<% String currentDate = PageHelper.getCurrentDate(); %>
<script type="text/javascript">
<!--
	function disablePub(){
	   var startDate = document.getElementById('dtpubblicazionestart');
	   var endDate = document.getElementById('dtpubblicazioneend');
		if(document.getElementById("checkbox_datapubblicazione").checked == true){
			startDate.disabled = false;
			endDate.disabled = false;
			document.getElementById('CALdtpubblicazionestart').style['display'] = '';
			document.getElementById('CALdtpubblicazioneend').style['display'] = '';
			
		}
		else{
			startDate.value = "";
			endDate.value="";
			startDate.style.borderColor = '';
			endDate.style.borderColor = '';
			startDate.disabled = true;
			endDate.disabled = true;
			document.getElementById('CALdtpubblicazionestart').style['display'] = 'none';
			document.getElementById('CALdtpubblicazioneend').style['display'] = 'none';
		}
	}
	
	function disableScad(){
		var startDate = document.getElementById('dtscadenzastart');
	   var endDate = document.getElementById('dtscadenzaend');
		if(document.getElementById("checkbox_datascadenza").checked == true){
			startDate.disabled = false;
			endDate.disabled = false;
			document.getElementById('CALdtscadenzastart').style['display'] = '';
			document.getElementById('CALdtscadenzaend').style['display'] = '';
			
		}
		else{
			startDate.value = "";
			endDate.value="";
			startDate.style.borderColor = '';
			endDate.style.borderColor = '';
			startDate.disabled = true;
			endDate.disabled = true;
			document.getElementById('CALdtscadenzastart').style['display'] = 'none';
			document.getElementById('CALdtscadenzaend').style['display'] = 'none';
		}
	}
//-->
</script>


<% int indiceTab = 0; %>

<body onload="disablePub();disableScad()">
<div id="gabbia">

<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuGara.inc" %>


	<form action="ricercaGare" method="post" name="ric_gara" id="ins_gara">
	<input type="hidden" name="chiChiama" id="chiChiama" value="paginaDiRicerca">	
		
		
	<div id="bodypage">			
		<div class="bodypage-e">

			<h1><utils:message key="ricerca.titolo" /></h1>
			<%@ include file="include/gestisciErrore.inc" %>
			<div class="hmenu">
				<ul>
			<% if ( user.isRSSAorRUP() ) { 
			// FIXME: PP per disabilitare mettere solo isRUP
			%>
				<li><a title="<utils:message key="ricerca.creaNuovaGaraTitle" plain="true" />" href="inizializzaGara"><utils:message key="ricerca.creaNuovaGara" /></a></li>
			<% } %>
				</ul>
			</div>
			
			<div class="testo">
				<fieldset>
					<legend><utils:message key="ricerca.filtriNominali" /></legend>
				
					<table>
					    <tr>
					          <td class="detailHelp" colspan="2"><utils:message key="ricerca.inserireChiaviGara" /></td>
					    </tr>
						 <tr>
							 <td><utils:message key="ricerca.oggettoGara" /></td>
      						<td>
								<input tabindex="<%= ++indiceTab%>" size="50" type="text" title="<utils:message key="ricerca.oggettoGara" plain="true" />" id="txt_OggettoGara" name="<%= ParametriServlet.FIELD_NAME_OGGETTO_GARA %>"
								value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_OGGETTO_GARA %>' />"/>
							 </td>
					    </tr>
					    <tr>						     
					    	<td class="detailHelp" colspan="2"><utils:message key="ricerca.inserireChiaviLotto" /></td></tr>
						 <tr>
					    
						 <tr>
							 <td><utils:message key="ricerca.oggettoLotto" /></td>
      						<td>
								<input tabindex="<%= ++indiceTab%>" type="text" size="50" title="<utils:message key="ricerca.oggettoLotto" plain="true" />" id="txt_OggettoLotto" name="<%= ParametriServlet.FIELD_NAME_OGGETTO_LOTTO %>"
								value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_OGGETTO_LOTTO %>' />"/>
							 </td>
					    </tr>
					    
					    <tr>
					          <td class="detailHelp" colspan="2"><utils:message key="ricerca.indicareCig" /></td>
					    </tr>
						<tr>
					      <td><utils:message key="ricerca.cig" /></td>
					      <td>
								<input tabindex="<%= ++indiceTab%>" type="text" size="10" maxlength="10" title="<utils:message key="ricerca.cig" plain="true" />" id="txt_CIG" name="<%= ParametriServlet.FIELD_NAME_CIG %>"
								value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_CIG %>' />"/>
							 </td>
					    </tr>
    
    					<tr>
					    	<td class="detailHelp" colspan="2"><utils:message key="ricerca.indicareNumeroGara" /></td>
					    </tr>
					    <tr>
      						<td><utils:message key="ricerca.numeroGara" /></td>
      						<td>
								<input tabindex="<%= ++indiceTab%>" type="text" size="10" maxlength="8" title="Numero della gara" id="txt_IDGARA" name="<%= ParametriServlet.FIELD_NAME_ID_GARA %>"
								value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_ID_GARA %>' />"/>
							</td>
    					</tr>
    					
    					<tr>
					    	<td class="detailHelp" colspan="2">Indicare la fascia importo di interesse</td>
					    </tr> 
					    <tr>
      						<td>Fascia Importo</td>
      						<td>
      							<select name="<%= ParametriServlet.ID_SOGLIA_IMPORTO %>">
      								<option/>
      								<h:options name="<%= ParametriServlet.SOGLIE_IMPORTO %>" scope="session"/>
      							</select>
							</td>
    					</tr>
    						
    
    <tr>
          <td class="detailHelp" colspan="2">Stazione appaltante che ha bandito la Gara</td>
    </tr>
    
    <tr>
							<td colspan="2">
							<div class="scrollLittle">
							<table width="100%">
								<tr>
								<td><input type="radio" checked name="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>" value="TUTTE" tabindex="<%= ++indiceTab %>"></td><td><label for="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>">TUTTE (TRANNE LE STAZIONI APPALTANTI DELEGATE)</label></td>
								</tr>

							<% for ( java.util.Enumeration e = user.getUfficiByProfilo(user.getProfiloEnum()).elements(); e.hasMoreElements(); ) { %>
								<tr>
								<% String idStazioneAppaltante = request.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE); %>
								<% StazioneAppaltante currentSA =  (StazioneAppaltante)e.nextElement(); %>
								<td><input type="radio" name="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>" value="<%= currentSA.getIdUfficio()%>" tabindex="<%= ++indiceTab %>"
								    <%= currentSA.getIdUfficio().equals(idStazioneAppaltante) ? "checked":"" %>></td><td><label for="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>"><%=currentSA.getDenominazioneAmministrazione()%> - <%= currentSA.getDenominazione()%></label></td>
								</tr>
							 <% } %>
							 </table>
							</div>
							</td>
    </tr>
					</table>
				</fieldset>
			</div>

			<div class="testo">
				
				<fieldset>
						<legend>Filtri temporali</legend>
			
						<table width="100%" cellpadding="3" style="table-layout: fixed" >
						
					    	<tr  class="TableBeanOdd" >
					     		<td width="5%"><input type="checkbox" onclick="disablePub()" id="checkbox_datapubblicazione" 
					     		<% if(session.getAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE)!=null &&
					     			(Boolean)session.getAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE)==true) { %>
					     		checked="checked" <% } %> /></td>	
					      		<td colspan="3">
						      		<table>
						        		<tr>
						            		<td colspan="3">Da data Pubblicazione</td>
						          		</tr>
						          		 <tr>
								<td >
									<input tabindex="<%=++indiceTab%>"  style="text-align:center"  onblur="Calendar.validaData(this)" type="text" id="dtpubblicazionestart" name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_START %>" 
									value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_START %>' />"/>
						
									<img  src="calendar/img.gif" id="CALdtpubblicazionestart" style="cursor: pointer; border: 1px solid red; display:none" title="Date selector"
	  									onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									<script type="text/javascript">
									    Calendar.setup({
									        inputField     :    "dtpubblicazionestart",     // id of the input field
									        ifFormat       :    "%d/%m/%Y",      // format of the input field
									        button         :    "CALdtpubblicazionestart",  // trigger for the calendar (button ID)
										    align          :    "Tl",           // alignment (defaults to "Bl")
										    singleClick    :    true
									    });
									</script>
												
								</td>
							</tr>
						        	</table>
								</td>
					      		<td colspan="3">
							    	<table>
							        	<tr>
							        		<td colspan="3">A data Pubblicazione</td>
							          	</tr>
							          	   		 <tr>
								<td >
									<input tabindex="<%=++indiceTab%>" style="text-align:center"  onblur="Calendar.validaData(this)" type="text" id="dtpubblicazioneend" name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_END %>" 
									value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_END %>' />"/>
						
									<img   src="calendar/img.gif" id="CALdtpubblicazioneend" style="cursor: pointer; border: 1px solid red;display:none" title="Date selector"
	  									onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									<script type="text/javascript">
									    Calendar.setup({
									        inputField     :    "dtpubblicazioneend",     // id of the input field
									        ifFormat       :    "%d/%m/%Y",      // format of the input field
									        button         :    "CALdtpubblicazioneend",  // trigger for the calendar (button ID)
										    align          :    "Tl",           // alignment (defaults to "Bl")
										    singleClick    :    true
									    });
									</script>
												
								</td>
							</tr>
							        </table>
					      		</td>
					      		<td width="40%">Inserire l'intervallo di date di pubblicazione</td>
					    	</tr>
					    	<tr class="TableBeanEven" >
					     		<td>
					     			<input type="checkbox" onclick="disableScad()" id="checkbox_datascadenza" 
					     			<% if(session.getAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE)!=null &&
					     				(Boolean)session.getAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE)==true) { %>
					     			checked="checked" <% } %> />
					     		</td>
				      			<td colspan="3">
				      				<table width="100%">
				                		<tr>
								        	<td colspan="3">Da data Scadenza</td>
								        </tr>
								        
				         <tr>
								<td >
									<input tabindex="<%=++indiceTab%>" style="text-align:center"  onblur="Calendar.validaData(this)" type="text" id="dtscadenzastart" name="<%= ParametriServlet.FIELD_NAME_SCADENZA_START %>" 
									value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_SCADENZA_START %>' />"/>
						
									<img   src="calendar/img.gif" id="CALdtscadenzastart" style="cursor: pointer; border: 1px solid red;display:none" title="Date selector"
	  									onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									<script type="text/javascript">
									    Calendar.setup({
									        inputField     :    "dtscadenzastart",     // id of the input field
									        ifFormat       :    "%d/%m/%Y",      // format of the input field
									        button         :    "CALdtscadenzastart",  // trigger for the calendar (button ID)
										    align          :    "Tl",           // alignment (defaults to "Bl")
										    singleClick    :    true
									    });
									</script>
												
								</td>
							</tr>
								        
								  	</table>
	     						</td>
	      						<td colspan="3">
									<table width="100%">
								    	<tr>
								            <td colspan="3">A data Scadenza</td>
								        </tr>
								         <tr>
								<td >
									<input tabindex="<%=++indiceTab%>" style="text-align:center"  onblur="Calendar.validaData(this)" type="text" id="dtscadenzaend" name="<%= ParametriServlet.FIELD_NAME_SCADENZA_END %>" 
									value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_SCADENZA_END %>' />"/>
						
									<img   src="calendar/img.gif" id="CALdtscadenzaend" style="cursor: pointer; border: 1px solid red;display:none" title="Date selector"
	  									onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									<script type="text/javascript">
									    Calendar.setup({
									        inputField     :    "dtscadenzaend",     // id of the input field
									        ifFormat       :    "%d/%m/%Y",      // format of the input field
									        button         :    "CALdtscadenzaend",  // trigger for the calendar (button ID)
										    align          :    "Tl",           // alignment (defaults to "Bl")
										    singleClick    :    true
									    });
									</script>
												
								</td>
							</tr>
								    </table>
	     						</td>
		      					<td>Inserire l'intervallo di date di scadenza richiesto</td>
		   					</tr>
		   				</table>
		 			</fieldset>
		
			</div>
			<input tabindex="<%= ++indiceTab%>" type="submit" value="Cerca">

		</div>
		</div>
		</form>
	
		<%@ include file="include/newfooter.inc" %>
	
	</div>

</body>
</html>
