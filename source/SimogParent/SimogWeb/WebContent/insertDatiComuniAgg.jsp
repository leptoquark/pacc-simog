<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

<%@page import="it.avlp.simog.util.*"%>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.db.advanced.*"%>
<%@ page import= "it.avlp.simog.beans.*" %>
<%@ page import= "it.avlp.simog.common.servlet.*" %>

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

<%	int indiceTab = 0; %>

<title>SIMOG - <utils:message key="scheda.gestioneSchede" /> <%= user.getProfilo().toUpperCase() %></title>
<script type="text/javascript" src="script/funzioni.js"></script>
</head>
<% TableBean dati = (TableBean)request.getAttribute(ParametriServlet.DATI_PREINSERT_TABLEBEAN); %>
<% TableBeanRow row = dati.getRow(0); %>
<body>
<div id="gabbia">

	<%@ include file="include/header.inc" %>
<!--  	< %@ include file="include/menu/menuGara.inc" %>-->
	<% String currentDate = PageHelper.getCurrentDate(); %>
	<div id="bodypage">
		<div class="bodypage-e">
			<form name="insertDatiComuniAgg" action="loadBloccoDati"  method="post">
				<h1><utils:message key="datiComuni.creazioneAggiudicazione" /> - <utils:message key="datiComuni.datiComuni" /></h1>
				<%@ include file="include/gestisciErrore.inc" %>
				<fieldset>
					<legend><utils:message key="datiComuni.inserimentoDatiComuniAggiudicazioni" /></legend>
					<table width="100%">
						<tr>
							<th>Data</th>
							<td><%=	PageHelper.getFormattedDate(currentDate) %></td>
								<% session.setAttribute( ParametriServlet.SESSION_DATA_CREAZIONE_GARA, currentDate); %>
						</tr>
						<tr>
							<th>CF UTENTE</th>
							<td><%= user.getLogin() %></td>
						</tr>
					</table>
					<!-- < %@ include file="include/dati_gara_lotto.inc" %>-->
					<br>
					<fieldset>
						<table width="100%">
							<tr>
						     	<td colspan="2"><p class="detailHelp">Info Aggiudicazioni</p></td>
							</tr>
							<tr>
								<th><label for="<%= ParametriServlet.FIELD_NAME_PROCEDURA_ACC %>">Procedura Accelerata</label></th>
								<td>
									SI<input tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServlet.FIELD_NAME_PROCEDURA_ACC %>" value="S">
									NO<input tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServlet.FIELD_NAME_PROCEDURA_ACC %>" value="N">
								</td>
							</tr>
							<tr>
								<th><label for="<%= ParametriServlet.FIELD_NAME_PREINFORMAZIONE %>">Preinformazione</label></th>
								<td>
									SI<input tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServlet.FIELD_NAME_PREINFORMAZIONE %>" value="S">
									NO<input tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServlet.FIELD_NAME_PREINFORMAZIONE %>" value="N">
								</td>
							</tr>
							<tr>
								<th><label for="<%= ParametriServlet.FIELD_NAME_TERMINE_RIDOTTO %>">Termine Ridotto</label></th>
								<td>
									SI<input tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServlet.FIELD_NAME_TERMINE_RIDOTTO %>" value="S">
									NO<input tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServlet.FIELD_NAME_TERMINE_RIDOTTO %>" value="N">
								</td>							
							</tr>							
						</table>
					</fieldset>
					<fieldset>
						<table width="100%">
							<tr>
						     	<td colspan="2"><p class="detailHelp">Pubblicazione Gara</p></td>
							</tr>
							<tr>
								<th><label for="<%= ParametriServlet.FIELD_NAME_ALBO_PRETORIO %>">Albo Pretorio</label></th>
								<td>
									<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ALBO_PRETORIO_DD %>" id="<%= ParametriServlet.FIELD_NAME_ALBO_PRETORIO_DD %>" value="">
									<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ALBO_PRETORIO_MM %>" id="<%= ParametriServlet.FIELD_NAME_ALBO_PRETORIO_MM %>" value="">
									<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ALBO_PRETORIO_AAAA %>" id="<%= ParametriServlet.FIELD_NAME_ALBO_PRETORIO_AAAA %>" value="">
											
									<input type="text" id="<%= ParametriServlet.FIELD_NAME_ALBO_PRETORIO %>" readonly value="">
									<img src="calendar/img.gif" id="calendarAlbo" style="cursor: pointer; border: 1px solid red;" title="Date selector"
			  							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									<script type="text/javascript">
									    Calendar.setup({
									        inputField     :    "<%= ParametriServlet.FIELD_NAME_ALBO_PRETORIO %>",     // id of the input field
									        ifFormat       :    "%d/%m/%Y",      // format of the input field
									        button         :    "calendarAlbo",  // trigger for the calendar (button ID)
										    align          :    "Tl",           // alignment (defaults to "Bl")
										    singleClick    :    true,
				        					onUpdate       :    catcalc1,
								        	onClear		   :	pulisce1
														        
									    });
									    
									     function catcalc1(cal) {
											var date = cal.date;
										    if (calendar.dateClicked) {
			
				   								var albodate = document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_ALBO_PRETORIO %>'].value.split("/");
											   									      
											    document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_ALBO_PRETORIO_DD %>'].value=albodate[0];
											    document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_ALBO_PRETORIO_MM %>'].value=albodate[1];
											    document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_ALBO_PRETORIO_AAAA %>'].value=albodate[2];
													     
											    }
											}
											function pulisce1(cal) {
											
												document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_ALBO_PRETORIO_DD %>'].value="";
											    document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_ALBO_PRETORIO_MM %>'].value="";
											    document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_ALBO_PRETORIO_AAAA %>'].value="";
											}
									</script>
									
								</td>
							</tr>
							<tr>
								<th><label for="<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE %>">Gazzetta Ufficiale Comunit� Europea</label></th>
								<td>
									<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE_DD %>" id="<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE_DD %>" value="">
									<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE_MM %>" id="<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE_MM %>" value="">
									<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE_AAAA %>" id="<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE_AAAA %>" value="">
											
									<input type="text" id="<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE %>" readonly value="">
									<img src="calendar/img.gif" id="calendarGuce" style="cursor: pointer; border: 1px solid red;" title="Date selector"
			  							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									<script type="text/javascript">
										    Calendar.setup({
									        inputField     :    "<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE %>",     // id of the input field
									        ifFormat       :    "%d/%m/%Y",      // format of the input field
									        button         :    "calendarGuce",  // trigger for the calendar (button ID)
									        align          :    "Tl",           // alignment (defaults to "Bl")
									        singleClick    :    true,
				        					onUpdate       :    catcalc2,
								        	onClear		   :	pulisce2
									        
								    		});
											    
								    	function catcalc2(cal) {
											if (calendar.dateClicked) {
									        var gucedate = document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE %>'].value.split("/");
										   									      
										    document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE_DD %>'].value=gucedate[0];
										    document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE_MM %>'].value=gucedate[1];
										    document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE_AAAA %>'].value=gucedate[2];									     
												    }
											    }
										function pulisce2(cal) {
										
											document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE_DD %>'].value="";
										    document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE_MM %>'].value="";
										    document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE_AAAA %>'].value="";
										}
									</script>
						
								</td>
							</tr>
							<tr>
								<th><label for="<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI %>">Gazzetta Ufficiale Repubblica Italiana</label></th>
								<td>									
									<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI_DD %>" id="<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI_DD %>" value="">
									<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI_MM %>" id="<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI_MM %>" value="">
									<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI_AAAA %>" id="<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI_AAAA %>" value="">
							
									<input type="text" id="<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI %>" readonly value="">
							
									<img src="calendar/img.gif" id="calendarGuri" style="cursor: pointer; border: 1px solid red;" title="Date selector"
  										onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									<script type="text/javascript">
									    Calendar.setup({
								       		inputField     :    "<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI %>",     // id of the input field
								        	ifFormat       :    "%d/%m/%Y",      // format of the input field
								        	button         :    "calendarGuri",  // trigger for the calendar (button ID)
								        	align          :    "Tl",           // alignment (defaults to "Bl")
								        	singleClick    :    true,
				        					onUpdate       :    catcalc3,
								        	onClear		   :	pulisce3
									        							        	
							   			 });
								    
								    	function catcalc3(cal) {
									        var date = cal.date;
									        if (calendar.dateClicked) {
										      	var guridate = document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI %>'].value.split("/");
								   									      
								    			document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI_DD %>'].value=guridate[0];
								   				document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI_MM %>'].value=guridate[1];
								    			document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI_AAAA %>'].value=guridate[2];										     
										    }
									    }
									    function pulisce3(cal) {
										
											document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI_DD %>'].value="";
										    document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI_MM %>'].value="";
										    document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI_AAAA %>'].value="";
										}
									</script>
							
								</td>
							</tr>
							<tr>
								<th><label for="<%= ParametriServlet.FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE %>">Bollettino Ufficiale Regionale</label></th>
								<td>									
									<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE_DD %>" id="<%= ParametriServlet.FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE_DD %>" value="">
									<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE_MM %>" id="<%= ParametriServlet.FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE_MM %>" value="">
									<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE_AAAA %>" id="<%= ParametriServlet.FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE_AAAA %>" value="">
									
									<input type="text" id="<%= ParametriServlet.FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE %>" readonly value="">
									
									<img src="calendar/img.gif" id="calendarBur" style="cursor: pointer; border: 1px solid red;" title="Date selector"
      									onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									<script type="text/javascript">
									    Calendar.setup({
									        inputField     :    "<%= ParametriServlet.FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE %>",     // id of the input field
									        ifFormat       :    "%d/%m/%Y",      // format of the input field
									        button         :    "calendarBur",  // trigger for the calendar (button ID)
									        align          :    "Tl",           // alignment (defaults to "Bl")
									        singleClick    :    true,
				        					onUpdate       :    catcalc4,
								        	onClear		   :	pulisce4
									        
									    });
									    
									    function catcalc4(cal) {
									        var date = cal.date;
									        if (calendar.dateClicked) {
										     	var burdate = document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE %>'].value.split("/");
							   									      
							    				document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE_DD %>'].value=burdate[0];
							   					document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE_MM %>'].value=burdate[1];
							    				document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE_AAAA %>'].value=burdate[2];										     

										    }
									    }
									    function pulisce4(cal) {
								
											document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE_DD %>'].value="";
										    document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE_MM %>'].value="";
										    document.forms[0].elements['<%= ParametriServlet.FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE_AAAA %>'].value="";
										}
									</script>
									
								</td>
							</tr>
							<tr>
								<th><label for="<%= ParametriServlet.FIELD_NAME_QUOTIDIANI_NAZIONALI %>">Quotidiani Nazionali</label></th>
								<td>
									<input type="text" name="<%= ParametriServlet.FIELD_NAME_QUOTIDIANI_NAZIONALI %>" maxlength="1024" style="text-align:right;" value="">
								</td>
							</tr>
							<tr>
								<th><label for="<%= ParametriServlet.FIELD_NAME_QUOTIDIANI_REGIONALI %>">Quotidiani Regionali</label></th>
								<td>
									<input type="text" name="<%= ParametriServlet.FIELD_NAME_QUOTIDIANI_REGIONALI %>" maxlength="1024" style="text-align:right;" value="">
								</td>
							</tr>
							<tr>
								<th><label for="<%= ParametriServlet.FIELD_NAME_QUOTIDIANI_PROVINCIALI %>">Quotidiani Provinciali</label></th>
								<td>
									<input type="text" name="<%= ParametriServlet.FIELD_NAME_QUOTIDIANI_PROVINCIALI %>" maxlength="1024" style="text-align:right;" value="">
								</td>
							</tr>
							
							<tr>
								<th><label for="<%= ParametriServlet.FIELD_NAME_PROFILO_COMMITTENTE %>">Profilo Committente</label></th>
								<td>
									SI<input tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServlet.FIELD_NAME_PROFILO_COMMITTENTE %>" value="S">
									NO<input tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServlet.FIELD_NAME_PROFILO_COMMITTENTE %>" value="N">
								</td>							
							</tr>
							
							<tr>
								<th><label for="<%= ParametriServlet.FIELD_NAME_SITO_MIN_INF_TRASP %>">Sito Informativo Ministero Infrastrutture e Trasporti</label></th>
								<td>
									SI<input tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServlet.FIELD_NAME_SITO_MIN_INF_TRASP %>" value="S">
									NO<input tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServlet.FIELD_NAME_SITO_MIN_INF_TRASP %>" value="N">
								</td>							
							</tr>	
							
							<tr>
								<th><label for="<%= ParametriServlet.FIELD_NAME_SITO_OSSERVATORIO_CP %>">Sito Informativo Osservatorio Contratti Pubblici</label></th>
								<td>
									SI<input tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServlet.FIELD_NAME_SITO_OSSERVATORIO_CP %>" value="S">
									NO<input tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServlet.FIELD_NAME_SITO_OSSERVATORIO_CP %>" value="N">
								</td>							
							</tr>						
						</table>
					</fieldset>
				<%
				String noConf = "";
				if(request.getParameter(ParametriServlet.ACTION_NEW)!=null)
					noConf = "disabled";
				%>

					<tr>
						<td><input type="submit" name="<%= PSBD.ACTION_TYPE %>" value="<%= ParametriServlet.ACTION_SALVA %>"></td>
						<td><input type="submit" name="<%= PSBD.ACTION_TYPE %>" value="<%= ParametriServlet.ACTION_CONFERMA %>"  <%=noConf %>></td>													
					</tr>
				</fieldset>					
				<input type="hidden" name="<%= ParametriServlet.ACTION_NEW %>" value="<%= request.getParameter(ParametriServlet.ACTION_NEW) %>">
				<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_LOTTO %>" value="<%= row.getNulledField(LOTTO.ID_LOTTO) %>">
				<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_CIG %>" value="<%= row.getNulledField(LOTTO.CIG) %>">
				<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_CIG_CYCLE %>" value="<%= row.getNulledField(LOTTO.CIG_CICLE) %>">
				<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE %>" value="<%= row.getNulledField(SCELTA_CONTRAENTE.ID_SCELTA_CONTRAENTE) %>">
				<!-- 2846 -->
				<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_MOTIVO %>" value="<%= row.getNulledField(MOTIVO_COLLEGAMENTO.ID_MOTIVO) %>">
				<!-- 2846 -->
			</form>					
		</div>
	</div>
	<%@ include file="include/newfooter.inc" %>
</div>
</body>
</html>
