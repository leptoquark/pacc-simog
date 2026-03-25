<%try{ %>
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


<title><utils:message key="paramCig.titoloElencoCigAcquisiti" /> - <%= user.getProfilo() %></title>
</head>

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
//-->
</script>

<% int indiceTab = 0; %>

<body>
	<div id="gabbia">
	
		<%@ include file="include/header.inc" %>
		<%@ include file="include/menu/menuGara.inc" %> 
	
		<form action="elencoCig" method="post" name="ric_gara" id="ins_gara">
			<input type="hidden" name="chiChiama" id="chiChiama" value="paginaDiRicerca">	
			<input type="hidden" name="<%=ParametriServlet.FROM_RICERCA %>" id="<%=ParametriServlet.FROM_RICERCA %>" value="<%=Costanti.FLAG_VALORE_SI %>">	
			<div id="bodypage">			
				<div class="bodypage-e">
					<h1><utils:message key="paramCig.titoloElencoCigAcquisiti" /> - <utils:message key="paramCig.parametriRicerca" /></h1>
					<%@ include file="include/gestisciErrore.inc" %>
					
					<fieldset>
						<legend><utils:message key="paramCig.filtriNominali" /></legend>
						
						<table>
						    <tr>
						          <td class="detailHelp" colspan="2"><utils:message key="paramCig.indicareCodiceFiscaleRUP" /></td>
						    </tr>
							<tr>
								<td><utils:message key="paramCig.codiceFiscaleRUP" /></td>
	      						<td>
									<input tabindex="<%= ++indiceTab%>" size=20" maxlength="16" type="text" title="<utils:message key="paramCig.codiceFiscaleRUP" plain="true" />" id="txt_OggettoGara" name="<%= ParametriServlet.FIELD_NAME_CF_OPERATORE %>" 
									value="<h:requestParameter  property='<%= ParametriServlet.FIELD_NAME_CF_OPERATORE %>' />"/>
								</td>
						    </tr>
	    					<tr>
						    	<td class="detailHelp" colspan="2"><utils:message key="paramCig.indicareCentroCosto" /></td>
						    </tr>
						    <tr>
	      						<td><utils:message key="paramCig.centroCosto" /></td>
	      						<td>
	      							<select name="<%= ParametriServlet.FIELD_NAME_CODICE_CC %>">
	      								<option/>
	      								<h:options name="listaSAsess" scope="session" value="<%= ParametriServlet.FIELD_NAME_CODICE_CC %>" />
	      							</select>
								</td>
	    					</tr>
						</table>
					</fieldset>
				   <fieldset>
				 
						<legend><utils:message key="paramCig.filtriTemporali" /></legend>
			
						<table width="100%" cellpadding="3" style="table-layout: fixed" >
						
					    	<tr  class="TableBeanOdd" >
					     		<td width="5%"><input type="checkbox" onclick="disablePub()" id="checkbox_datapubblicazione" 
					     		<% if(request.getAttribute(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_START)!=null
					     					|| request.getAttribute(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_END)!=null) { %>
					     		checked="checked" <% } %> /></td>	
					      		<td colspan="3">
						      		<table>
						        		<tr>
						            		<td colspan="3"><utils:message key="paramCig.daDataAcquisizione" /></td>
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
							        		<td colspan="3">A data acquisizione</td>
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
					      		<td width="40%">Inserire l'intervallo di date di acquisizione</td>
					    	</tr>
		   				</table>
		 			</fieldset>
				<input tabindex="<%= ++indiceTab%>" type="submit" value="Cerca">
				</div>
			</div>
		</form>
		<%@ include file="include/newfooter.inc" %>
	</div>	
<script type="text/javascript">
disablePub();
</script>
</body>
</html>
<%} catch(Exception e){ e.printStackTrace();}%>