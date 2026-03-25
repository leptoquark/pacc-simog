<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="h" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ page import="it.avlp.simog.common.servlet.ParametriServlet" %>

<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<% String currentDate = it.avlp.simog.util.PageHelper.getCurrentDate(); %>

<title><utils:message key="log.consultazioneLog" /></title>
</head>
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

<script type="text/javascript">
<!--
function disableDataLog(){
	   var startDate = document.getElementById('dtlogstart');
	   var endDate = document.getElementById('dtlogend');
		if(document.getElementById("checkbox_datalog").checked == true){
			startDate.disabled = false;
			endDate.disabled = false;
			document.getElementById('CALdtlogstart').style['display'] = '';
			document.getElementById('CALdtlogend').style['display'] = '';
			
		}
		else{
			startDate.value = "";
			endDate.value="";
			startDate.style.borderColor = '';
			endDate.style.borderColor = '';
			startDate.disabled = true;
			endDate.disabled = true;
			document.getElementById('CALdtlogstart').style['display'] = 'none';
			document.getElementById('CALdtlogend').style['display'] = 'none';
		}
	}
	
function checkDates(){
	if(document.getElementById("checkbox_datalog").checked == false){
	    return true;
	    }
	else{
		var result =  !(document.getElementById("dtlogend").value == "" || document.getElementById("dtlogstart").value== "");
		if(result == false)
		     alert("<%= it.avlp.simog.util.MessageHelper.getMessage(request, "log.intervalloDateNonValide") %>");
		return result;
	}
}
//-->
</script>
<% int indiceTab = 0; %>

<body onload="disableDataLog()">
<div id="gabbia">
<%@ include file="include/header.inc" %>

	<div id="bodypage">
		<div class="bodypage-e">
		<%@ include file="include/menu/menuAmmLog.inc"%>
		
		
		<div class="testo">
		
		<form action="<%= ParametriServlet.CONSULTA_LOG %>" method="post" onsubmit="return checkDates()" >

			<h1><utils:message key="log.consultazioneLog" /></h1>
			<%@ include file="include/gestisciErrore.inc" %>
			
			<fieldset>
				<legend><utils:message key="ricerca.filtriNominali" /></legend>
				<table>
				<tr>
					<td><utils:message key="visualizza.stazioneAppaltante" /></td>
					<td><input tabindex="<%=++indiceTab%>" type="text" name="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>" 
					value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>' />"/>
					<td><p class="detailHelp"><utils:message key="log.indicareStazioneAppaltante" /></p></td>
				</tr>
				
				<tr>
					<td><utils:message key="transazioni.cfRSSA" /></td>
					<td><input tabindex="<%=++indiceTab%>" type="text" name="<%= ParametriServlet.FIELD_NAME_CF_OPERATORE %>" 
					value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_CF_OPERATORE %>' />"/>
					<td><p class="detailHelp"><utils:message key="log.indicareCfOperatore" /></p></td>
				</tr>
				
				<tr>
					<td><utils:message key="ricerca.cfAmministrazione" /></td>
					<td><input tabindex="<%=++indiceTab%>" type="text" name="<%= ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE %>" 
					value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE %>' />"/>
					<td><p class="detailHelp"><utils:message key="log.indicareCfAmministrazione" /></p></td>
				</tr>
				
				<tr>
					<td><utils:message key="ricerca.cig" /></td>
					<td><input tabindex="<%= ++indiceTab%>" type="text" size="10" maxlength="10" title="CIG" id="txt_CIG" name="<%= ParametriServlet.FIELD_NAME_CIG %>" 
					value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_CIG %>' />"/>
					<td><p class="detailHelp"><utils:message key="log.indicareCigLotto" /></p></td>
				</tr>
				<tr>
					<td><utils:message key="ricerca.numeroGara" /></td>
					<td><input tabindex="<%= ++indiceTab%>" type="text" size="10" maxlength="8" title="ID_GARA" id="txt_ID_GARA" name="<%= ParametriServlet.FIELD_NAME_ID_GARA %>" 
					value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_ID_GARA %>' />"/>
					<td><p class="detailHelp"><utils:message key="log.indicareNumeroGara" /></p></td>
				</tr>
			</table>
		</fieldset>

			

			<div class="testo">
				
					<fieldset>
						<legend><utils:message key="ricerca.filtriTemporali" /></legend>
			
						<table width="100%" cellpadding="3" style="table-layout: fixed" >
						
					    	<tr  class="TableBeanEven" >
					     			<td>
					     			<input type="checkbox" onclick="disableDataLog()" id="checkbox_datalog" 
					     			<% if(session.getAttribute(ParametriServlet.SESSION_DATA_INSERITA_CONSULTA_LOG) != null && 
					     			(Boolean)session.getAttribute(ParametriServlet.SESSION_DATA_INSERITA_CONSULTA_LOG) == true &&
					     			request.getParameter(ParametriServlet.FIELD_NAME_DATA_START_LOG) != null) { 
					     			%> checked="checked" <% } %> />
					     		</td>
					      		 <td colspan="3">
						      		<table>
						        		<tr>
						            		<td colspan="3"><utils:message key="log.daDataLog" /></td>
						                 </tr>
						                  <tr>
								             <td >
									          <input tabindex="<%=++indiceTab%>" style="text-align:center"  onblur="Calendar.validaData(this)" type="text" id="dtlogstart" name="<%= ParametriServlet.FIELD_NAME_DATA_START_LOG %>" 
									          value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_DATA_START_LOG %>' />"/>

									          <img   src="calendar/img.gif" id="CALdtlogstart" style="cursor: pointer; border: 1px solid red; display:none" title="Date selector"
	  									      onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									          <script type="text/javascript">
											    Calendar.setup({
											        inputField     :    "dtlogstart",     // id of the input field
											        ifFormat       :    "%d/%m/%Y",      // format of the input field
											        button         :    "CALdtlogstart",  // trigger for the calendar (button ID)
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
							        		<td colspan="3"><utils:message key="log.aDataLog" /></td>
							          	</tr>
							    <tr>
								  <td >
								  	<input tabindex="<%=++indiceTab%>" style="text-align:center"  onblur="Calendar.validaData(this)" type="text" id="dtlogend" name="<%= ParametriServlet.FIELD_NAME_DATA_END_LOG %>" 
									value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_DATA_END_LOG %>' />"/>
									<img   src="calendar/img.gif" id="CALdtlogend" style="cursor: pointer; border: 1px solid red;display:none" title="Date selector"
	  									onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									<script type="text/javascript">
									    Calendar.setup({
									        inputField     :    "dtlogend",     // id of the input field
									        ifFormat       :    "%d/%m/%Y",      // format of the input field
									        button         :    "CALdtlogend",  // trigger for the calendar (button ID)
										    align          :    "Tl",           // alignment (defaults to "Bl")
										    singleClick    :    true
									    });
									</script>
						 						
						     		</td>
					     		</tr>
					  </table>
						</td>
		      				<td><utils:message key="log.inserireIntervalloDate" /></td>
		   			</tr>
		   			<tr><td><input tabindex="<%=++indiceTab%>" type="submit" value="<utils:message key="button.consulta" plain="true" />"/></td></tr>
				</table>      
		   				
		 			</fieldset>
		 			
		     </div>
	</form>
	</div>
	</div>
</div>
			<%@ include file="include/newfooter.inc" %>
</div>

</body>
</html>
