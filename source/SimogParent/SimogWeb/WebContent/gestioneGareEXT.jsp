<%@page import="it.avlp.simog.db.SimogFlags"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/newbasicHeader.inc" %>
<title>SIMOG - <utils:message key="visualizza.gestioneGare" /></title>
</head>
<%@ include file="include/controlloSessione.inc" %>

<%@ page import="it.avlp.simog.common.servlet.ParametriServlet" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.db.Costanti" %>

<%@ taglib prefix="h" uri="http://simog.avlp.it/tags-util"  %>
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

<% //String currentDate = PageHelper.getCurrentDate(); %>
<script type="text/javascript">
<!--
	function disableAnn(){
		if(document.getElementById("checkbox_annullamento").checked == true){
			
			document.getElementById("annOn").disabled = false;
			document.getElementById("annOff").disabled = false;			
		}
		else{
		
			document.getElementById("annOn").disabled = true;
			document.getElementById("annOff").disabled = true;
		}
	}
	
	function disablePub(){
	   var startDate = document.getElementById('dtpubblicazionestart');
	   var endDate = document.getElementById('dtpubblicazioneend');
		if(document.getElementById("checkbox_datapubblicazione").checked == true){
			startDate.disabled = false;
			endDate.disabled = false;
			
			document.getElementById('CALdtpubblicazionestart').style["display"] = "";
			document.getElementById('CALdtpubblicazioneend').style["display"] = "";
			
		}
		else{
			startDate.value = "";
			endDate.value="";
			startDate.style.borderColor = '';
			endDate.style.borderColor = '';
			startDate.disabled = true;
			endDate.disabled = true;
			
			document.getElementById('CALdtpubblicazionestart').style["display"] = "none";
			document.getElementById('CALdtpubblicazioneend').style["display"] = "none";
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

<%int indiceTab = 0; %>

<body onload="disablePub();disableScad();">
<div id="gabbia">	
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuAmmGara.inc" %>

	<form action="ricercaGareExt" method="post" name="ric_gara" id="ins_gara">
	<div id="bodypage">			
		<div class="bodypage-e">
			<h1>Ricerca Gara</h1>
			<%@ include file="include/gestisciErrore.inc" %> 
			
	<input type="hidden" name="chiChiama" id="chiChiama" value="paginaDiRicerca">
	<input type="hidden" name="<%=ParametriServlet.FROM_RICERCA %>" id="<%=ParametriServlet.FROM_RICERCA %>" value="<%=Costanti.FLAG_VALORE_SI %>">	

			<div class="testo">
				<fieldset>
					<legend>Filtri nominali</legend>
				
					<table width="100%">
					    <tr>
					          <td colspan="2" class="detailHelp">Inserire una o pi&ugrave; chiavi di ricerca per l'oggetto della gara</td>
					    </tr>
						 <tr>
							 <td>Oggetto della gara</td>
							 <% String oggettoGara = (String)session.getAttribute(ParametriServlet.FIELD_NAME_OGGETTO_GARA_RIPROPOSIZIONE);
								if(oggettoGara == null) oggettoGara = "";
								%>
      						<td>
								<input tabindex="<%= ++indiceTab%>" size="50" type="text" title="Oggetto della gara" id="txt_OggettoGara" name="<%= ParametriServlet.FIELD_NAME_OGGETTO_GARA %>" 
								<% if( session.getAttribute(ParametriServlet.SESSION_DATI_INSERITI_ERROR_GESTIONE_GARE) != null &&
								(Boolean)session.getAttribute(ParametriServlet.SESSION_DATI_INSERITI_ERROR_GESTIONE_GARE) == true ) { %>
								value="<%= oggettoGara %>" <% } %> />
							 </td>
					    </tr>
					    <tr>						     
					    	<td colspan="2" class="detailHelp">Inserire una o pi&ugrave; chiavi di ricerca per l'oggetto del lotto
					    	</td>
					    	</tr>
						 <tr>
					    
						 <tr>
							 <td>Oggetto del lotto</td>
							 <% String oggettoLotto = (String)session.getAttribute(ParametriServlet.FIELD_NAME_OGGETTO_LOTTO_RIPROPOSIZIONE);
								if(oggettoLotto == null) oggettoLotto = "";
								%>
      						<td>
								<input tabindex="<%= ++indiceTab%>" size="50" type="text" title="Oggetto del lotto" id="txt_OggettoLotto" name="<%= ParametriServlet.FIELD_NAME_OGGETTO_LOTTO %>" 
								<% if( session.getAttribute(ParametriServlet.SESSION_DATI_INSERITI_ERROR_GESTIONE_GARE) != null &&
								(Boolean)session.getAttribute(ParametriServlet.SESSION_DATI_INSERITI_ERROR_GESTIONE_GARE) == true ) { %>
								value="<%= oggettoLotto %>" <% } %> />
							 </td>
					    </tr>
					    <tr>
					          <td colspan="2" class="detailHelp">Indicare il CIG del lotto di interesse</td>
					    </tr>
					    <tr>
      						<td>CIG</td>
      						<% String cig = (String)session.getAttribute(ParametriServlet.FIELD_NAME_CIG);
								if(cig == null) cig = "";
								%>
      						<td>
								<input tabindex="<%= ++indiceTab%>" type="text" size="10" maxlength="10" title="CIG" id="txt_CIG" name="<%= ParametriServlet.FIELD_NAME_CIG %>" 
								<% if( session.getAttribute(ParametriServlet.SESSION_DATI_INSERITI_ERROR_GESTIONE_GARE) != null &&
								(Boolean)session.getAttribute(ParametriServlet.SESSION_DATI_INSERITI_ERROR_GESTIONE_GARE) == true ) { %>
								value="<%= cig %>" <% } %> />
							 </td>
    					</tr>
    					
    					<tr>
					    	<td class="detailHelp" colspan="2">Indicare il Numero della Gara di interesse</td>
					    </tr>
					    <tr>
      						<td>Numero Gara</td>
      						<% String numGara = (String)session.getAttribute(ParametriServlet.FIELD_NAME_ID_GARA);
								if(numGara == null) numGara = "";
								%>
      						<td>
      							<input tabindex="<%= ++indiceTab%>" type="text" size="10" maxlength="8" title="Numero della gara" id="txt_IDGARA" name="<%= ParametriServlet.FIELD_NAME_ID_GARA %>"
								<% if( session.getAttribute(ParametriServlet.SESSION_DATI_INSERITI_ERROR_GESTIONE_GARE) != null &&
								(Boolean)session.getAttribute(ParametriServlet.SESSION_DATI_INSERITI_ERROR_GESTIONE_GARE) == true ) { %>
								value="<%= numGara %>" <% } %> />
							 </td>
    					</tr>
					    </tr>
<%if(SimogFlags.is30230_RFWEBGL01Active()){ %>		
    					<tr>
					    	<td class="detailHelp" colspan="2">Indicare il Codice Fiscale dell'Amministrazione</td>
					    </tr>
					    <tr>
      						<td>CF Amministrazione</td>
      						<% String ammin = (String)session.getAttribute(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);
								if(ammin == null) ammin = "";
								%>
      						<td>
								<input tabindex="<%= ++indiceTab%>" type="text" size="40" maxlength="16" title="CF Amministrazione" id="txt_CFAMM" name="<%= ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE %>" 
								<% if( session.getAttribute(ParametriServlet.SESSION_DATI_INSERITI_ERROR_GESTIONE_GARE) != null &&
								(Boolean)session.getAttribute(ParametriServlet.SESSION_DATI_INSERITI_ERROR_GESTIONE_GARE) == true ) { %>
								value="<%= ammin %>" <% } %> />
							 </td>
    					</tr>
<% } %>    										
					   <tr>
					       <td colspan="2" class="detailHelp">Stazione appaltante che ha bandito la Gara</td>
					   </tr>
    					<tr>
							 <td>ID Stazione Appaltante</td>
							 <% String stazioneAppaltante = (String)session.getAttribute(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE_RIPROPOSIZIONE);
								if(stazioneAppaltante == null) stazioneAppaltante = "";
								%>
							 <td><input type="text" tabindex="<%= ++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>" 
							 <% if( session.getAttribute(ParametriServlet.SESSION_DATI_INSERITI_ERROR_GESTIONE_GARE) != null &&
								(Boolean)session.getAttribute(ParametriServlet.SESSION_DATI_INSERITI_ERROR_GESTIONE_GARE) == true ) { %>
							 value="<%= stazioneAppaltante %>" <% } %> size="40" maxlength="200"/></td>
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
    
					</table>
				</fieldset>
				</div>
				<div class="testo">
				<fieldset>
					<legend>Filtri Accessori</legend>
				<table width="100%">
			  
			    <tr class="TableBeanOdd">
			      <td><input type="checkbox" onclick="disableAnn()" id="checkbox_annullamento" 
			      <% if(request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO) != null ) { %>
			      checked="checked" <% } %> />Richieste Modifica</td>	
			      <td colspan="3">
			      <table width="100%">
			          <tr class="TableBeanEven">			          	
			            <td width="50%"><input tabindex="<%=++indiceTab%>" type="radio" value="S" name="<%= ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO %>" id="annOn" 
			            <% if(request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO) == null) { %>	disabled checked <% } 
			            else if(((String)(request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO))).equals("S")) { %>
			            	checked="checked" <% } %> />Presente</td>
			            <td width="50%"> <input tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO %>"  value="N" id="annOff" 
			            <% if(request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO) == null) { %> disabled <% } 
			            else if(((String)(request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO))).equals("N")) { %>
			            	checked="checked" <% } %> />Assente</td>
			          </tr>
			        </table>
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
	
	</div><!-- container --></body>
</html>