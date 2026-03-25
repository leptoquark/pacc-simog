<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String prefixResp = PSBD.RESPONSABILE; %>
<script type="text/javascript">
<!--
function modAnagResp(){
apriPopUpMod('rubricaResponsabili','<%= PSBD.TAB_RESPONSABILE_PROCEDIMENTO %>',document.getElementById('<%= PSBD.FIELD_NAME_ID_RESPONSABILE%>'),document.getElementById('<%= PSBD.FIELD_NAME_DATA_INIZIO_RES%>'),'<utils:message key="rubrica.rubricaIncaricati" />',document.getElementById('<%= PSBD.FIELD_NAME_ANAG %>').value);
}
//-->
</script>
<c:set var="readonlyResp" value="${roByFlusso eq true or (includerConfirmed and variazioniAnagrafiche ne true ) }" />
<c:set var="readonlyRespStr" value="${readonlyResp ? 'readonly' : ''} " />
<c:set var="prefixResp" value="<%= prefixResp %>" scope="page" />
	<div id="DIVTabella<%= prefixResp %>" class="scrollTabs" style="height: 200px; width: 99%;">
		<table id="idTabella<%= prefixResp %>">
			<tbody>
			<tr>
				<th width="125"><utils:message key="table.azione" /></th>
				<th class="garaTh"><utils:message key="table.cognome" /></th>
				<th class="garaTh"><utils:message key="table.nome" /></th>							  
				<th class="garaTh"><utils:message key="table.codiceFiscale" /></th>   
				<th class="garaTh"><utils:message key="table.ruolo" /></th>
			</tr>
			<c:set var="counter" value='0' scope="page"/>
			<c:forEach var="respCorrente" items="${responsabili}">
				<c:set var="id" value="row${prefixResp}${counter}" scope="page"/>
				<tr id="<c:out value="${id}" />">
					<c:set var="soggResponsabile" value="${respCorrente.soggettoResponsabile}" />
					
					<c:if test="${readonlyResp ne true}">
						<td nowrap="nowrap" class="hmenu"><a title="<utils:message key="button.modifica" plain="true" />" href="javascript:setForModifyRow('<c:out value="${id}" />',[<%= PSBD.argsResp %>],[<%=PSBD.argsRespNascosti%>],'<%=prefixResp%>')"><utils:message key="button.modifica" /></a>
						&nbsp;<a title="<utils:message key="button.cancella" plain="true" />" href="javascript:deleteRow('<c:out value="${id}" />',[<%= PSBD.argsResp %>],[<%=PSBD.argsRespNascosti%>],'<%=prefixResp%>')"><utils:message key="button.cancella" /></a></td>
					</c:if>
				    <c:if test="${readonlyResp eq true}">
						<td>&nbsp;</td>
					</c:if>
					<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_COGNOME_RESPONSABILE %>"><c:out value="${soggResponsabile.cognome}" /></td>
					<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_NOME_RESPONSABILE %>"><c:out value="${soggResponsabile.nome}" /></td>
					<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_CODICE_FISCALE_RESPONSABILE %>"><c:out value="${soggResponsabile.codiceFiscaleResponsabile}" /></td>
					<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE %>"><c:out value="${respCorrente.descrizioneRuolo}" /></td>
					<td nowrap id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE %>" style="display: none"><c:out value="${respCorrente.idRuolo}" /></td>
					<td nowrap id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_RESPONSABILE%>" style="display: none"><c:out value="${soggResponsabile.idResponsabile}" /></td>
					<td nowrap id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_DATA_INIZIO_RES%>" style="display: none"><c:out value="${soggResponsabile.dataInizioRes}" /></td>
					<td nowrap id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_ANAG %>" style="display: none"><c:out value="${soggResponsabile.datiModifica}" /></td>

					<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_COGNOME_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_COGNOME_RESPONSABILE %>" value="<c:out value="${soggResponsabile.cognome}" />"></td>
					<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_NOME_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_NOME_RESPONSABILE %>" value="<c:out value="${soggResponsabile.nome}" />"></td>
					<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_CODICE_FISCALE_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_CODICE_FISCALE_RESPONSABILE %>" value="<c:out value="${soggResponsabile.codiceFiscaleResponsabile}" />"></td>
					<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE %>" value="<c:out value="${respCorrente.descrizioneRuolo}" />"></td>
					<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE %>" value="<c:out value="${respCorrente.idRuolo}" />"></td>
					<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_RESPONSABILE %>" value="<c:out value="${soggResponsabile.idResponsabile}" />"></td>
					<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_DATA_INIZIO_RES %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_DATA_INIZIO_RES %>" value="<c:out value="${soggResponsabile.dataInizioRes}" />"></td>
					<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_ANAG %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_ANAG %>" value="<c:out value="${soggResponsabile.datiModifica}" />"></td>					
				</tr>
				<c:set var="counter" value="${counter + 1}" scope="page"/>
			</c:forEach>
				</tbody>
			</table> 
		</div>	 		
		<c:if test="${readonlyResp ne true}">
			<div class="hmenu"><a id="showHide<%= prefixResp %>Button" href="javascript:showSezioneAggiungi([<%= PSBD.argsResp %>],[<%=PSBD.argsRespNascosti%>],'<%=prefixResp%>')" title="<utils:message key="scheda.aggiungiIncaricato" plain="true" />"><utils:message key="scheda.aggiungiIncaricato" /></a></div>
		</c:if>
		<div class="detailHelp" id="divAgg<%= prefixResp %>" style="display: none; border: 1px solid #cfcfcf;">
			<table width="100%">
				<tr>
					<th><label for=""><utils:message key="scheda.codiceFiscaleIncaricato" /></label></th>
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_CODICE_FISCALE_RESPONSABILE %>" maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixResp%>')">
					</td>

					<td ${variazioniAnagrafiche eq true ? '' : 'style="display:none;"'}>
						<div class="hmenu"><a title="<utils:message key="scheda.modificaAnagrafica" plain="true" />" href="javascript:modAnagResp();"><utils:message key="scheda.modificaAnagrafica" /></a></div></td>
					</td>

					<td>
						<div class="hmenu"><a title="<utils:message key="scheda.cercaInRubrica" plain="true" />" href="javascript:apriPopUpRubrica('rubrica','<%= PSBD.TAB_RESPONSABILE_PROCEDIMENTO %>','<utils:message key="rubrica.rubricaIncaricati" />')"><utils:message key="scheda.cercaInRubrica" /></a></div>
					</td>					
				</tr>
				<tr>
					<th><label for=""><utils:message key="table.cognome" /></label></th>
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_COGNOME_RESPONSABILE %>" maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixResp%>')" />
					</td>
				</tr>
				<tr>
					<th><label for=""><utils:message key="table.nome" /></label></th>
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_NOME_RESPONSABILE %>" maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixResp%>')" />
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<th><label><utils:message key="table.ruolo" /></label></th>
					<td>
						<select id="<%= PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE%>" CLASS="BOTTONE" onchange="setFormModified('Modificato<%=prefixResp%>')" >
							<option></option>
							<c:set var="idRuoloResp" value="${respCorrente.idRuolo}" scope="request" />
							<u:options name="<%= ParametriServlet.RUOLI_RESPONSABILE_BEAN %>" scope="request" value="idRuoloResp"/>
						</select>
					</td>							
				</tr>
				
				<tr style="display: none;">
					<td>
						<input disabled type="text" id="<%= PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE%>" value="" onchange="setFormModified('Modificato<%=prefixResp%>')"/>
					</td>							
				</tr>
				
				<tr style="display: none;">
					<td>
						<input disabled type="text" id="<%= PSBD.FIELD_NAME_ID_RESPONSABILE%>" value="" onchange="setFormModified('Modificato<%=prefixResp%>')"/>
					</td>							
				</tr>
				
				<tr style="display: none;">
					<td>
						<input disabled type="text" id="<%= PSBD.FIELD_NAME_DATA_INIZIO_RES%>" value="" onchange="setFormModified('Modificato<%=prefixResp%>')"/>
					</td>							
				</tr>								
				<tr style="display: none;">
					<td>
						<input disabled type="text" id="<%= PSBD.FIELD_NAME_ANAG %>" value="" onchange="setFormModified('Modificato<%=prefixResp%>')"/>
					</td>							
				</tr>
				<tr><td class="hmenu"><a id="AddMod<%= prefixResp %>" href="javascript:addRow([<%= PSBD.argsResp %>],[<%=PSBD.argsRespNascosti%>],'<%=prefixResp%>')"><utils:message key="button.aggiungi" /></a></td></tr>			
			</table>
					<input type="hidden" id="Modificato<%= prefixResp %>" name ="Modificato<%= prefixResp %>" value="0">
		</div>
<input type="hidden" id="Modificato1" name ="Modificato1" value="<c:out value="${param['modificato1']}" />">
<input type="hidden" id="selected<%= prefixResp %>" value="0" /> 