<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.beans.Utente" %>
<%@page import="it.avlp.simog.common.servlet.ParametriServletRubrica"%>
<%@page import="it.avlp.simog.beans.InvitatoBean"%>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%//@page import="it.avlp.simog.util.PageHelper"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!--fieldset class="detailHelp" style="border: 2px solid #cfcfcf;"-->	
<%@page import="it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean"%>
<%@page import="java.util.ArrayList"%>
<%String flag="false"; %>
<%
  if((user.isRUP() && !"0".equals(currentRow.getNulledField(PSBD.HASSCHEDE))) || (!user.isRUP())){
	  flag="true";
	  }
%>
	<c:set var="readonlyAffid" value="<%=flag %>" />
	<c:set var="readonlyAffidStr" value="${readonlyAffid ? 'readonly' : ''} " /> 
	  
      <c:set var="aggiudicatari" value="${requestScope['invitati']}"></c:set>
		<% String prefixAgg = PSBD.AGGIUDICATARIO; %>
		<c:set var="prefixAgg" value="<%= prefixAgg %>" scope="page" />	
		<div id="DIVTabella<%= prefixAgg %>" class="scrollTabs" style="height: 200px; width: 99%;">
			<table id="idTabella<%= prefixAgg %>">
			<input type="hidden" id="sottosogliaEsclusi" value="si" />
				<tbody>
					<tr>
					   <th width="125"><utils:message key="table.azione" /></th>  					
						<th class="garaTh"><utils:message key="table.denominazione" /></th>					
						<th class="garaTh"><utils:message key="table.codiceFiscale" /></th>		
						<th class="garaTh"><utils:message key="table.codicePaese" /></th>
					</tr>
					<c:set var="counter" value="0" /> 
					<c:forEach var="aggCorrente" items="${aggiudicatari}">
						<c:set var="id" value="row${prefixAgg}${counter}" scope="page"/>
						<tr id="<c:out value="${id}" />">
							<c:set var="soggPartecipante" value="${aggCorrente.soggettoPartecipante}" />
							<c:if test="${readonlyAffid ne true}">
								<td nowrap="nowrap" class="hmenu">
									<a title="<utils:message key="scheda.modificaInvitato" />" href="javascript:setForModifyRow('<c:out value="${id}" />',[<%= PSBD.argsInvitati %>],[<%=PSBD.argsInvitatiNascosti%>],'<%=prefixAgg%>')"><utils:message key="button.modifica" /></a>
									&nbsp;<a title="<utils:message key="button.cancella" />" href="javascript:deleteRow('<c:out value="${id}" />',[<%= PSBD.argsInvitati %>],[<%=PSBD.argsInvitatiNascosti%>],'<%=prefixAgg%>')"><utils:message key="button.cancella" /></a></td>																		   
							</c:if>
							<c:if test="${readonlyAffid eq true}">
								<td>&nbsp;</td>
							</c:if>		
							
							<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>"><c:out value="${soggPartecipante.denominazione}" /></td>																
							<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>"><c:out value="${soggPartecipante.codiceFiscale}" /></td>
							<td nowrap class="garaTd" id="<c:out value="${id}" /><%=PSBD.FIELD_NAME_AGG_ID_PAESE%>"><c:out value="${(soggPartecipante.id_stato != null)&&(soggPartecipante.id_stato != '')? soggPartecipante.id_stato : 'IT'}" /></td>
						 					 					
							<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE %>" style="display: none;"><c:out value="${soggPartecipante.idSoggettoPartecipante}" /></td>
							<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG %>" style="display: none;"><c:out value="${soggPartecipante.dataInizioSogg}" /></td>
							<%-- <td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_RUOLO %>" style="display: none;"><c:out value="${aggCorrente.ruolo}" /></td> 
							<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>" style="display: none;"><c:out value="${aggCorrente.idTipoAgg}" /></td>	 --%>					
                     <td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE %>" value="<c:out value="${soggPartecipante.idSoggettoPartecipante}" />"></td>
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG %>" value="<c:out value="${soggPartecipante.dataInizioSogg}" />"></td>										
                     <td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>" value="<c:out value="${soggPartecipante.denominazione}" />"></td>	
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_PAESE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_PAESE%>" value="<c:out value="${soggPartecipante.id_stato}" />"></td>	
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>" value="<c:out value="${soggPartecipante.codiceFiscale}" />"></td>
						</tr>
						<c:set var="counter" value="${counter + 1}" scope="page"/>
					</c:forEach>																
				</tbody>
			</table>
		</div>	  				
		<c:if test="${readonlyAffid ne true}">
			<div class="hmenu"><a id="showHide<%= PSBD.AGGIUDICATARIO %>Button" title="<utils:message key="scheda.aggiungiInvitato" />" href="javascript:showSezioneAggiungi([<%= PSBD.argsInvitati %>],[<%=PSBD.argsInvitatiNascosti%>],'<%=prefixAgg%>')" title="<utils:message key="scheda.aggiungiInvitato" />"><utils:message key="scheda.aggiungiInvitato" /></a></div>	
		</c:if>			
		<div class="detailHelp" id="divAgg<%= prefixAgg %>" style="display: none; border: 1px solid #cfcfcf;">
			<table width="100%">
				<tr>
					<th><label for=""><utils:message key="table.codiceFiscale" /></label></th>
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>" maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixAgg%>')" /> 	
					  
					    <input type="text" id="<%= PSBD.FIELD_NAME_AGG_ID_PAESE%>" maxlength="2" size="2" value="" disabled onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
					<td>
						<div class="hmenu"><a title="<utils:message key="scheda.cercaInRubrica" />" href="javascript:apriPopUpRubrica('rubrica','<%= PSBD.TAB_AFFIDATARIO %>','<utils:message key="rubrica.rubricaOperatoriEconomici" />')"><utils:message key="scheda.cercaInRubrica" /></a></div>						
					</td>					
				</tr>
				<tr>
					<th><label><utils:message key="table.denominazione" /></label></th>
					<td>
						<input disabled type="text" id="<%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
				</tr>
				<tr style="display: none;">
					<th><label><utils:message key="scheda.invitato" /></label></th>
					<td>
						<input type="text" disabled id="<%= PSBD.FIELD_NAME_DESCRIZIONE %>" maxlength="16" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>					
				</tr>		
			 	<tr>
					<td>
					<input type="hidden" id="<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>					
				</tr>	
				<tr style="display: none;">
					<th><label><utils:message key="scheda.codiceFiscaleDittaAusiliaria" /></label></th>
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_AGG_CF_AUSILIARIA %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" maxlength="16"/>
					</td>
				</tr>
				<tr style="display: none;">
					<td>
						<input type="text" id="<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
				</tr>
				<tr style="display: none;">
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
				</tr>
				<tr style="display: none;">	
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
				</tr>
				<tr><td class="hmenu"><a id="AddMod<%= prefixAgg %>" href="javascript:addRow([<%= PSBD.argsInvitati %>],[<%=PSBD.argsInvitatiNascosti%>],'<%=prefixAgg%>')"><utils:message key="button.aggiungi" /></a></td></tr>
			</table>	
			<input type="hidden" id="Modificato<%= prefixAgg %>" name ="Modificato<%= prefixAgg %>" value="0">			
		</div>		
<input type="hidden" id="Modificato2" name ="Modificato2" value="<c:out value="${param['modificato2']}" />">
<input type="hidden" id="selected<%= prefixAgg %>" value="0" />