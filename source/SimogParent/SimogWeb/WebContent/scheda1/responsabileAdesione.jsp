<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
		
<% String prefixResp = PSBD.RESPONSABILE; %>


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
				<th class="garaTh"><utils:message key="scheda.indirizzo" /></th>
				<th class="garaTh"><utils:message key="scheda.telefono" /></th>
				<th class="garaTh"><utils:message key="scheda.fax" /></th>
				<th class="garaTh"><utils:message key="scheda.email" /></th>
			</tr>
			<c:set var="counter" value='0' scope="page"/>
			<c:forEach var="respCorrente" items="${responsabili}">
				<c:set var="id" value="row${prefixResp}${counter}" scope="page"/>
				<tr id="<c:out value="${id}" />">
					<c:set var="soggResponsabile" value="${respCorrente.soggettoResponsabile}" />
					
					<c:if test="${hide != true}">
						<td nowrap="nowrap" class="hmenu"><a title="<utils:message key="button.modifica" />" href="javascript:setForModifyRow('<c:out value="${id}" />',[<%= PSBD.argsRespAdesione %>],[<%=PSBD.argsRespNascosti%>],'<%=prefixResp%>')"><utils:message key="button.modifica" /></a>
						&nbsp;<a title="<utils:message key="button.cancella" />" href="javascript:deleteRow('<c:out value="${id}" />',[<%= PSBD.argsRespAdesione %>],[<%=PSBD.argsRespNascosti%>],'<%=prefixResp%>')"><utils:message key="button.cancella" /></a></td>
					</c:if>
				   <c:if test="${hide == true}">
						<td>&nbsp;</td>
					</c:if>
					<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_COGNOME_RESPONSABILE %>"><c:out value="${soggResponsabile.cognome}" /></td>
					<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_NOME_RESPONSABILE %>"><c:out value="${soggResponsabile.nome}" /></td>
					<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_CODICE_FISCALE_RESPONSABILE %>"><c:out value="${soggResponsabile.codiceFiscaleResponsabile}" /></td>
					<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE %>"><c:out value="${respCorrente.descrizioneRuolo}" /></td>
									
									   <td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_INDIRIZZO_RESPONSABILE %>"><c:out value="${soggResponsabile.indirizzo}" /></td>		
										<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_TELEFONO_RESPONSABILE %>"><c:out value="${soggResponsabile.telefono}" /></td>
										<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_FAX_RESPONSABILE %>"><c:out value="${soggResponsabile.fax}" /></td>
										<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_EMAIL_RESPONSABILE %>"><c:out value="${soggResponsabile.email}" /></td>
				                
					<td nowrap id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE %>" style="display: none"><c:out value="${respCorrente.idRuolo}" /></td>
					<td nowrap id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_RESPONSABILE%>" style="display: none"><c:out value="${soggResponsabile.idResponsabile}" /></td>
					<td nowrap id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_DATA_INIZIO_RES%>" style="display: none"><c:out value="${soggResponsabile.dataInizioRes}" /></td>
					
					<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_COGNOME_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_COGNOME_RESPONSABILE %>" value="<c:out value="${soggResponsabile.cognome}" />"></td>
					<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_NOME_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_NOME_RESPONSABILE %>" value="<c:out value="${soggResponsabile.nome}" />"></td>
					<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_CODICE_FISCALE_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_CODICE_FISCALE_RESPONSABILE %>" value="<c:out value="${soggResponsabile.codiceFiscaleResponsabile}" />"></td>
					<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE %>" value="<c:out value="${respCorrente.descrizioneRuolo}" />"></td>
					              
										<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_INDIRIZZO_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_INDIRIZZO_RESPONSABILE %>" value="<c:out value="${soggResponsabile.indirizzo}" />"></td>
										<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_TELEFONO_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_TELEFONO_RESPONSABILE %>" value="<c:out value="${soggResponsabile.telefono}" />"></td>
										<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_FAX_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_FAX_RESPONSABILE %>" value="<c:out value="${soggResponsabile.fax}" />"></td>
										<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_EMAIL_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_EMAIL_RESPONSABILE %>" value="<c:out value="${soggResponsabile.email}" />"></td>
					             											 
					<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE %>" value="<c:out value="${respCorrente.idRuolo}" />"></td>
					<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_ID_RESPONSABILE %>" value="<c:out value="${soggResponsabile.idResponsabile}" />"></td>
					<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_DATA_INIZIO_RES %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_DATA_INIZIO_RES %>" value="<c:out value="${soggResponsabile.dataInizioRes}" />"></td>
				
					
				</tr>
				<c:set var="counter" value="${counter + 1}" scope="page"/>
			</c:forEach>
				</tbody>
			</table> 
		</div>	 		
			
		<c:if test="${hide != true}">			
			<div class="hmenu"><a id="showHide<%= prefixResp %>Button" href="javascript:showSezioneAggiungi([<%= PSBD.argsResp %>],[<%=PSBD.argsRespNascosti%>],'<%=prefixResp%>')" title="Aggiungi Incaricato">Aggiungi Incaricato</a></div>
		</c:if>
			
		<div class="detailHelp" id="divAgg<%= prefixResp %>" style="display: none; border: 1px solid #cfcfcf;">
			<table width="100%">
				<tr>
					<th><label for="">Codice fiscale Incaricato</label></th>
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_CODICE_FISCALE_RESPONSABILE %>" maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixResp%>')">
					</td>
					<td>
						<div class="hmenu"><a title="Cerca in rubrica" href="javascript:apriPopUpRubrica('rubrica','<%= PSBD.TAB_RESPONSABILE_PROCEDIMENTO %>','Rubrica Incaricati')">Cerca in rubrica</a></div>
					</td>					
				</tr>
					
				<tr>
					<th><label for="">Cognome</label></th>
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_COGNOME_RESPONSABILE %>" maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixResp%>')" />
					</td>
				</tr>
					
				<tr>
					<th><label for="">Nome</label></th>
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_NOME_RESPONSABILE %>" maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixResp%>')" />
					</td>
				</tr>
				
				<tr><td>&nbsp;</td></tr>
				<tr>
					<th><label>Ruolo</label></th>
					<td>
						<select id="<%= PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE%>" CLASS="BOTTONE" onchange="setFormModified('Modificato<%=prefixResp%>')" >
							<option></option>
							<c:set var="idRuoloResp" value="${respCorrente.idRuolo}" scope="request" />
							<u:options name="<%= ParametriServlet.RUOLI_RESPONSABILE_BEAN %>" scope="request" value="idRuoloResp"/>
						</select>
					</td>							
				</tr>
				
				<tr>
					<th><label for="">Indirizzo</label></th>
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_INDIRIZZO_RESPONSABILE %>" maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixResp%>')" />
					</td>
				</tr>
				
				<tr>
					<th><label for="">Telefono</label></th>
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_TELEFONO_RESPONSABILE %>" maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixResp%>')" />
					</td>
				</tr>
				
				<tr>
					<th><label for="">Fax</label></th>
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_FAX_RESPONSABILE %>" maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixResp%>')" />
					</td>
				</tr>
				
				<tr>
					<th><label for="">Email</label></th>
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_EMAIL_RESPONSABILE %>" maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixResp%>')" />
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
								
				<tr><td class="hmenu"><a id="AddMod<%= prefixResp %>" href="javascript:addRow([<%= PSBD.argsResp %>],[<%=PSBD.argsRespNascosti%>],'<%=prefixResp%>')">Aggiungi</a></td></tr>			
			</table>
					<input type="hidden" id="Modificato<%= prefixResp %>" name ="Modificato<%= prefixResp %>" value="0">
		</div>
<input type="hidden" id="Modificato1" name ="Modificato1" value="<c:out value="${param['modificato1']}" />">
<input type="hidden" id="selected<%= prefixResp %>" value="0" /> 

%> 
 