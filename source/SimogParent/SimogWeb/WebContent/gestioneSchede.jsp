<%@page import="it.avlp.simog.db.SimogFlags"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>

<%@ taglib prefix="h" uri="http://simog.avlp.it/tags-util"  %>

<%@ page import="it.avlp.simog.beans.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.db.Costanti" %>

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


<% String currentDate =  PageHelper.getCurrentDate(); %>


<title>Gestione Schede - <%= user.getProfilo() %></title>
</head>

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
	function disableAgg(){
		if(document.getElementById("checkbox_aggiudicate").checked == true){
			
			document.getElementById("aggOn").disabled = false;
			document.getElementById("aggOff").disabled = false;
			
		}
		else{
		
			document.getElementById("aggOn").disabled = true;
			document.getElementById("aggOff").disabled = true;
			
		}
	}
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
	
	
		<form action="ricercaGareRUP_CS" method="post" name="ric_gara" id="ins_gara">
			<input type="hidden" name="chiChiama" id="chiChiama" value="paginaDiRicerca">	
			<input type="hidden" name="<%=ParametriServlet.FROM_RICERCA %>" id="<%=ParametriServlet.FROM_RICERCA %>" value="<%=Costanti.FLAG_VALORE_SI %>">	
			<div id="bodypage">			
				<div class="bodypage-e">
					<h1><utils:message key="ricerca.titolo" /></h1>
					<%@ include file="include/gestisciErrore.inc" %>
					<div class="hmenu">
						<ul>
							<% if ( user.isRSSAorRUP() ) { %>
								<li><a title="<utils:message key="ricerca.creaNuovaGaraTitle" plain="true" />" href="inizializzaGara"><utils:message key="ricerca.creaNuovaGara" /></a></li>
							<% } %>
						</ul>
					</div>
					
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
									<input tabindex="<%= ++indiceTab%>" size="50" type="text" title="<utils:message key="ricerca.oggettoLotto" plain="true" />" id="txt_OggettoLotto" name="<%= ParametriServlet.FIELD_NAME_OGGETTO_LOTTO %>"
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
									<input tabindex="<%= ++indiceTab%>" type="text" size="10" maxlength="8" title="<utils:message key="ricerca.numeroGaraTitle" plain="true" />" id="txt_IDGARA" name="<%= ParametriServlet.FIELD_NAME_ID_GARA %>"
									value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_ID_GARA %>' />"/>
								</td>
	    					</tr>
	    					
<%-- GESTIONE FILTRI cfRUP e ID stazione appaltante --%>  						    						    										    
<% if ( user.isOssReg() || user.isRASA()) { %>
						   
					   <% if(!user.isRASA()) { %>  
						    <tr>
						    	<td class="detailHelp" colspan="2"><utils:message key="ricerca.indicareNumeroGara" /></td>
						    </tr>
						    <tr>
	      						<td><utils:message key="ricerca.cfAmministrazione" /></td>
	      						
	      						<td>
									<input tabindex="<%= ++indiceTab%>" type="text" title="<utils:message key="ricerca.cfAmministrazioneTitle" plain="true" />" id="txt_CFAMM" name="<%= ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE %>"
									value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE %>' />" size="20" maxlength="20" />
								</td>
	    					</tr>
	    			  <% 	} %>
	    					<tr>
						    	<td colspan="2" class="detailHelp"><utils:message key="ricerca.stazioneAppaltante" /></td>
						    </tr>	    					
	    				    <tr>
	    				    	<td><utils:message key="ricerca.idStazioneAppaltante" /></td>
							 	<td>
							 		<input tabindex="<%= ++indiceTab%>" type="text" title="<utils:message key="ricerca.cfStazioneAppaltanteTitle" plain="true" />" id="txt_CFSA" name="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>" 
							 		value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>' />" size="50" maxlength="40"/></td>
  							</tr>
  							
  							<tr>
						    	<td class="detailHelp" colspan="2"><utils:message key="ricerca.indicareCfRup" /></td>
						    </tr>
						    <tr>
	      						<td><utils:message key="ricerca.cfRup" /></td>
	      						<td>
									<input tabindex="<%= ++indiceTab%>" type="text" title="<utils:message key="ricerca.cfRupTitle" plain="true" />" id="txt_CFRUP" name="<%= ParametriServlet.FIELD_NAME_CF_OPERATORE %>"
									value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_CF_OPERATORE %>' />" size="20" maxlength="20"/>
								</td>
						    </tr>
<% } else {%>

	    					<tr>
						    	<td class="detailHelp" colspan="2"><utils:message key="ricerca.indicareFasciaImporto" /></td>
						    </tr>
						    <tr>
	      						<td><utils:message key="ricerca.fasciaImporto" /></td>
	      						<td>
	      							<select name="<%= ParametriServlet.ID_SOGLIA_IMPORTO %>">
	      								<option/>
	      								<h:options name="<%= ParametriServlet.SOGLIE_IMPORTO %>" scope="session"/>
	      							</select>
								</td>
	    					</tr>


						    <tr>
						          <td class="detailHelp" colspan="2"><utils:message key="ricerca.stazioneAppaltante" /></td>
						    </tr>
		    				<tr>
								<td colspan="2">
									<div class="scrollLittle">
										<table width="100%">
											<tr><%-- TICKET ALM - 3.04.3 --%>
												<td><input type="radio" name="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>" value="SA_DELEGATE" tabindex="<%= ++indiceTab %>"></td><td><label for="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>"><utils:message key="ricerca.saDelegate" /></label></td>
											</tr>
											<tr>
												<td><input type="radio" checked name="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>" value="TUTTE" tabindex="<%= ++indiceTab %>"></td><td><label for="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>"><utils:message key="ricerca.tutteTranneDelegate" /></label></td>
											</tr>
											<% String idStazioneAppaltante = request.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE); %>
											<% for ( java.util.Enumeration e = user.getUfficiByProfilo(user.getProfiloEnum()).elements(); e.hasMoreElements(); ) { %>
											<tr>
												<% StazioneAppaltante currentSA =  (StazioneAppaltante)e.nextElement(); %>
												<td><input type="radio" name="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>" value="<%= currentSA.getIdUfficio()%>" tabindex="<%= ++indiceTab %>" 
												<%= currentSA.getIdUfficio().equals(idStazioneAppaltante) ? "checked":"" %>></td>
												<td><label for="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>"><%=currentSA.getDenominazioneAmministrazione()%> - <%= currentSA.getDenominazione()%></label></td>
											</tr>
										 	<% } %>
										 </table>
									</div>
								</td>
		    				</tr>
	<%if(SimogFlags.is30233_RFWEBGL03Active()){ %>
    						<tr>
					      <td>
								<input tabindex="<%= ++indiceTab%>" type="checkbox" id="checkMie" name="<%= ParametriServlet.FIELD_NAME_CHECKMIE %>"
								<% if(session.getAttribute(ParametriServlet.FIELD_NAME_CHECKMIE)!=null) { %>
					     		checked="checked" <% } %>
								/><utils:message key="ricerca.soloGareCreateDaMe" /></td>
					    </tr>
	<%} %> 		    				
<% } %>
						</table>
					</fieldset>
<!--				</div>-->
<!--				<div class="testo">-->
				<fieldset>
					<legend><utils:message key="ricerca.filtriAccessori" /></legend>
				<table width="100%">
			    <tr class="TableBeanOdd">
			      <td><input type="checkbox" onclick="disableAnn()" id="checkbox_annullamento" 
			      <% if(request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO) != null ) { %>
			      checked="checked" <% } %> /><utils:message key="ricerca.richiesteModifica" /></td>	
			      <td colspan="3">
			      <table width="100%">
			          <tr class="TableBeanEven">			          	
			            <td width="50%"><input tabindex="<%=++indiceTab%>" type="radio" value="S" name="<%= ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO %>" id="annOn" 
			            <% if(request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO) == null) { %>	disabled checked <% } 
			            else if(((String)(request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO))).equals("S")) { %>
			            	checked="checked" <% } %> /><utils:message key="comune.presente" /></td>
			            <td width="50%"> <input tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO %>"  value="N" id="annOff" 
			            <% if(request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO) == null) { %> disabled <% } 
			            else if(((String)(request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO))).equals("N")) { %>
			            	checked="checked" <% } %> /><utils:message key="comune.assente" /></td>
			          </tr>
			        </table>
			      </td>
			      </tr>
<% if ( user.isOssReg()) { %>
			    <tr class="TableBeanOdd">
			      <td><input type="checkbox" onclick="disableAgg()" id="checkbox_aggiudicate" 
			      <% if(request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_AGGIUDICATE) != null ) { %>
			      checked="checked" <% } %> /><utils:message key="ricerca.visualizzaContrattiNonAggiudicati" /></td>	
			      <td colspan="3">
			      <table width="100%">
			          <tr class="TableBeanEven">			          	
			            <td width="50%"><input tabindex="<%=++indiceTab%>" type="radio" value="S" name="<%= ParametriServlet.FIELD_NAME_RICHIESTA_AGGIUDICATE %>" id="aggOn" 
			            <% if(request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_AGGIUDICATE) == null) { %>	disabled checked <% } 
			            else if(((String)(request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_AGGIUDICATE))).equals("S")) { %>
			            	checked="checked" <% } %> /><utils:message key="comune.si" /></td>
			            <td width="50%"> <input tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServlet.FIELD_NAME_RICHIESTA_AGGIUDICATE %>"  value="N" id="aggOff" 
			            <% if(request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_AGGIUDICATE) == null) { %> disabled <% } 
			            else if(((String)(request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_AGGIUDICATE))).equals("N")) { %>
			            	checked="checked" <% } %> /><utils:message key="comune.no" /></td>
			          </tr>
			        </table>
			      </td>
			      </tr>
<% } %>
			      </table>
			      </fieldset>
				 <div class="testo">
				   <fieldset>
				 
						<legend><utils:message key="ricerca.filtriTemporali" /></legend>
			
						<table width="100%" cellpadding="3" style="table-layout: fixed" >
						
					    	<tr  class="TableBeanOdd" >
					     		<td width="5%"><input type="checkbox" onclick="disablePub()" id="checkbox_datapubblicazione" 
					     		<% if(session.getAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE)!=null &&
					     			(Boolean)session.getAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE)==true) { %>
					     		checked="checked" <% } %> /></td>	
					      		<td colspan="3">
						      		<table>
						        		<tr>
						            		<td colspan="3"><utils:message key="ricerca.daDataPubblicazione" /></td>
						          		</tr>
						          		 <tr>
								<td >
									<input tabindex="<%=++indiceTab%>" style="text-align:center"  onblur="Calendar.validaData(this)" type="text" id="dtpubblicazionestart" name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_START %>" 
									value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_START %>' />"/>
									
									<img   src="calendar/img.gif" id="CALdtpubblicazionestart" style="cursor: pointer; border: 1px solid red; display:none" title="Date selector"
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
							        		<td colspan="3"><utils:message key="ricerca.aDataPubblicazione" /></td>
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
					      		<td width="40%"><utils:message key="ricerca.inserireIntervalloDataPubblicazione" /></td>
					    	</tr>
					    	<tr class="TableBeanEven" >
					     		<td>
					     			<input type="checkbox" onclick="disableScad()" id="checkbox_datascadenza" 
					     			<% if(session.getAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE)!=null &&
					     				(Boolean)session.getAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE)==true) { %>
					     			checked="checked" <% } %> /></td>	
					     		</td>
				      			<td colspan="3">
				      				<table width="100%">
				                		<tr>
								        	<td colspan="3"><utils:message key="ricerca.daDataScadenza" /></td>
								        </tr>
								        
				         <tr>
								<td >
									<input tabindex="<%=++indiceTab%>" style="text-align:center"  onblur="Calendar.validaData(this)" type="text" id="dtscadenzastart" name="<%= ParametriServlet.FIELD_NAME_SCADENZA_START %>" 
									value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_SCADENZA_START %>' />"/>
						
									<img  src="calendar/img.gif" id="CALdtscadenzastart" style="cursor: pointer; border: 1px solid red;display:none" title="Date selector"
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
								            <td colspan="3"><utils:message key="ricerca.aDataScadenza" /></td>
								        </tr>
								         <tr>
								<td >
									<input tabindex="<%=++indiceTab%>" style="text-align:center"  onblur="Calendar.validaData(this)" type="text" id="dtscadenzaend" name="<%= ParametriServlet.FIELD_NAME_SCADENZA_END %>" 
									value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_SCADENZA_END %>' />"/>
						
									<img src="calendar/img.gif" id="CALdtscadenzaend" style="cursor: pointer; border: 1px solid red;display:none" title="Date selector"
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
		      					<td><utils:message key="ricerca.inserireIntervalloDataScadenza" /></td>
		   					</tr>
		   				</table>
		 			</fieldset>
		 		</div>
				<input tabindex="<%= ++indiceTab%>" type="submit" value="<utils:message key="ricerca.cerca" plain="true" />">
				</div>
			</div>
		</form>
		<%@ include file="include/newfooter.inc" %>
	</div>	
</body>
</html>
