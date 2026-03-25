<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="../include/basicHeader.inc" %>
<%@ include file="../include/controlloSessione.inc" %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/theme/stile.css"/>
<%@ page import="it.avlp.simog.beans.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.db.generated.*" %>

<%@page import="it.avlp.simog.util.PageHelper"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.util.SimogProperties"%>

<html>
<link rel="stylesheet" href="/theme/tabmenu.css"/>
<head>
<title><utils:message key="scheda.gestioneAggiudicazioni" /> - <%= user.getProfilo() %></title>
</head>
<% TableBean listaPartecipanti = (TableBean)request.getAttribute(PSBD.BLOCCO_DATI_AGGIUDICATARI); 

   int indiceTab = 0; %>
<%@ include file="../include/i18n-init.inc" %>
<%@ include file="script.js" %>
<body>

	<div id="gabbia">
		<%@ include file="../include/header.inc" %>

		
	<div id="bodypage">
		<div class="bodypage-e">
		<form name="gestioneTab" action="<%=ParametriServlet.SRV_SCHEDA_A %>"  method="post" onkeypress="setFormModified(this)">
			<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_LOTTO %>" 
				id="<%= ParametriServlet.FIELD_NAME_ID_LOTTO %>" 
				value="<%= request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO) %>">
			
			<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_INFO %>" 
				id="<%= ParametriServlet.FIELD_NAME_ID_INFO %>" 
				value="<%= request.getParameter(ParametriServlet.FIELD_NAME_ID_INFO) %>">

			<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO %>"
				id="<%= ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO %>"
				value="<%= request.getParameter(ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO) %>">

			<input type="hidden" name="<%= PSBD.FIELD_NAME_ID_AGGIUDICAZIONE %>" 
				id="<%= PSBD.FIELD_NAME_ID_AGGIUDICAZIONE %>" 
				value="<%= request.getParameter(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE) %>">	
			
			<input type="hidden" name="<%=PSBD.DATA_INIZIO_AGGIUDICAZIONE %>" 
				id="<%= PSBD.DATA_INIZIO_AGGIUDICAZIONE %>" 
				value="<%= request.getParameter(PSBD.DATA_INIZIO_AGGIUDICAZIONE) %>">

			<input type="hidden" name="<%=PSBD.TAB%>"
				 id="<%=PSBD.TAB%>" value="">	
				 
				 
			<input type="hidden" name="pagina" id="pagina" value="">
			<input type="hidden" name="<%= PSBD.ACTION_TYPE %>" id="<%= PSBD.ACTION_TYPE %>" value="">

			<%--<input type="hidden" name="<%= ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_PARTECIPANTE %>" id="<%= ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_PARTECIPANTE %>" value="">--%>
			
			<input type="hidden" name="<%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE %>" id="<%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE %>" value="">
			<input type="hidden" name="<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE %>" id="<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE %>" value="">
			<input type="hidden" name="<%= ParametriServletRubrica.FIELD_NAME_DATA_INIZIO_SOGGETTO %>" id="<%= ParametriServletRubrica.FIELD_NAME_DATA_INIZIO_SOGGETTO %>" value="">
			<input type="hidden" name="Modificato" value="0">
			

			<div class="testo">
				<fieldset>
					<fieldset>
						<legend>Filtri nominali</legend>
					
						<table>
						    <tr>
						          <td class="detailHelp" colspan="2"><utils:message key="scheda.inserireCodiceFiscalePartecipante" /></td>
						    </tr>
						    <tr>
						    	<td>
									<input tabindex="<%= ++indiceTab%>" size="50" type="text" title="<utils:message key="table.codiceFiscale" plain="true" />" id="codiceFiscale" name="<%= ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE %>">
								</td>
							</tr>
							<tr>
						          <td class="detailHelp" colspan="2"><utils:message key="scheda.inserireNomeCognomePartecipante" /></td>
						    </tr>
						    <tr>
						    	<td>
									<input tabindex="<%= ++indiceTab%>" size="50" type="text" title="<utils:message key="table.denominazione" plain="true" />" id="<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE_RIC%>" name="<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE_RIC %>">
								</td>
							</tr>
						
						</table>
					</fieldset>
			</div>					    
					    
		<div class="header">
		
			<fieldset>
				<legend>Elenco partecipanti</legend>
				
				<!-- <div class="elenco">-->
				<div class="scrollInside">
						<div class="gara">
						
						<% TableBeanRow currentRow = null; %>				
						<% String nominativo = null; %>	
						<% String codice = null; %>
						<% int id_partecipante = 0; %>
						<% String dataInizioAggiudicatario = null; %>
						<% if(listaPartecipanti!=null) { %>
									<table>
												<tr>							
													<th class="garaTh" width="40%" name=""><utils:message key="scheda.nominativo" /></th>
													<th class="garaTh" width="40%"><utils:message key="scheda.codice" /></th>
													<th class="garaTh" width="1%"></th>
												</tr>
												<tr>
						
									
									<% for ( int rowIndex = 0; rowIndex < listaPartecipanti.getTableSize(); rowIndex++ ) { %>
									
									
										<% currentRow = listaPartecipanti.getRow(rowIndex); %>
										<% int counter = 0; %>
										<%
										nominativo = currentRow.getNulledField(SOGGETTI_PARTECIPANTI.DENOM_SOGG_PARTECIPANTE);
										%>
										<%
										codice = currentRow.getNulledField(SOGGETTI_PARTECIPANTI.CF_SOGG_PARTECIPANTE);
										%>
										<%
										id_partecipante = Integer.parseInt(currentRow.getNulledField(SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE));
										%>
										<%
										dataInizioAggiudicatario = currentRow.getNulledField(SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGGETTO);
										%>
										
												<td class="garaTd"><%=currentRow.getNulledField(SOGGETTI_PARTECIPANTI.DENOM_SOGG_PARTECIPANTE)%></td>
												<td class="garaTd"><%=currentRow.getNulledField(SOGGETTI_PARTECIPANTI.CF_SOGG_PARTECIPANTE)%></td>
												<!-- <td class="risalto"><p><a href="rubrica?< %= ParametriServletRubrica.OPERAZIONE %>=viewDetail&id_partecipante=< %= id_partecipante%>">Seleziona</a></p></td>-->
												<td><div class="hmenu"><a title="<utils:message key="table.selezione" plain="true" />" href="javascript:changeFromAffidatario('<%=PSBD.TAB_AFFIDATARIO_SELECTED%>','<%= id_partecipante%>','<%= nominativo%>','<%= dataInizioAggiudicatario%>')"><utils:message key="table.selezione" /></a></div></td>
												
											</tr>
								
					<% } %>
							
							
				<% } %>
					</table>
				</div>
			</div>
		</fieldset>
			
	</div>
	<input type="submit" name="<%= ParametriServletRubrica.OPERAZIONE %>" value="<utils:message key="button.cerca" plain="true" />" onclick="javascript:cerca('<%=PSBD.TAB_RUBRICA_AFFIDATARIO%>')">
	<!--<div class="hmenu"><a title="<utils:message key="button.cerca" />" href="javascript:cerca('<%=PSBD.TAB_RUBRICA_AFFIDATARIO%>')" title="<utils:message key="button.cerca" />"><utils:message key="button.cerca" /></a></div> -->
	<input type="button" name="<%= PSBD.ACTION %>" value="<utils:message key="button.torna" plain="true" />" onclick="changeTab('<%=PSBD.TAB_ADD_AFFIDATARIO%>','Modificato')" >
</div>
</div>
</form>

<%@ include file="../include/footer.inc" %>
</div>

</body>
</html>
