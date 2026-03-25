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


<title><utils:message key="paramCig.titoloElencoCigCupIntegrare" /> - <%= user.getProfilo() %></title>
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
	
		<form action="elencoCigIntegrazioneCup" method="post" name="ric_gara" id="ins_gara">
			<input type="hidden" name="chiChiama" id="chiChiama" value="paginaDiRicerca">	
			<input type="hidden" name="<%=ParametriServlet.FROM_RICERCA %>" id="<%=ParametriServlet.FROM_RICERCA %>" value="<%=Costanti.FLAG_VALORE_SI %>">	
			<div id="bodypage">			
				<div class="bodypage-e">
					<h1>Elenco CIG/CUP da integrare - Parametri di ricerca</h1>
					<%@ include file="include/gestisciErrore.inc" %>
<%--					  					
					<fieldset>
						<legend>Filtri nominali</legend>
						
						<table>
						    <tr>
						          <td class="detailHelp" colspan="2">Indicare il CIG del lotto di interesse</td>
						    </tr>
							<tr>
								<td>CIG</td>
	      					<td>
									<input title="Codice CIG" id="txt_cig" name="<%= ParametriServlet.FIELD_NAME_CIG %>" value=""/>
								</td>
						    </tr>
	    					<tr>
						    	<td class="detailHelp" colspan="2">Indicare la tipologia di lavoro</td>
						    </tr>
						    <tr>
	      					 <td>Tipologia Lavoro</td>
	      					 <td>
									<input title="Tipolgia Lavoro" id="txt_tipLavoro" name="<%= ParametriServlet.FIELD_NAME_TIPOLOGIA %>" value=""/>
								 </td>
	    					</tr>
	    					<tr>
						    	<td class="detailHelp" colspan="2">Indicare il Codice CUP</td>
						    </tr>
						    <tr>
	      					 <td>Centro di costo</td>
	      					 <td>
									<input title="Codice CUP" id="txt_cup" name="<%= ParametriCup.FIELD_NAME_CUP %>" value=""/>
								 </td>
	    					</tr>	    					
						</table>
					</fieldset>
--%>
<br/>
				   <fieldset>
				 
						<legend>Filtri temporali</legend>
			
						<table width="100%" cellpadding="3" style="table-layout: fixed" >
						
					    	<tr  class="TableBeanOdd" >
					     		<td width="5%"><input type="checkbox" onclick="disablePub()" id="checkbox_datapubblicazione" 
					     		<% if(request.getAttribute(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_START)!=null
					     					|| request.getAttribute(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_END)!=null) { %>
					     		checked="checked" <% } %> /></td>	
					      		<td colspan="3">
						      		<table>
						        		<tr>
						            		<td colspan="3">Da data acquisizione</td>
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
		 			<br/>
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