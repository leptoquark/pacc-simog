				
<%String modificato1 = (request.getAttribute("Modificato1") != null)? (String)request.getAttribute("Modificato1") : "0";%>	  
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.db.generated.RESP_INIZIO"%>
<fieldset class="detailHelp" style="border: 2px solid #cfcfcf;">
		
		<% TableBeanRow rowResponsabile = null;
			String prefixResp = PSBD.RESPONSABILE;
			String visibleResp = "block";
			String responsabiliSize = datiResponsabile.getFullSize()> 3 ? "200px" : "150px";
			%>
			<div id="DIVTabella<%= prefixResp %>" class="scrollTabs" style="height: <%= responsabiliSize %>; display: <%= visibleResp %>">
				<table id="idTabella<%= prefixResp %>">
					<tbody>
					<tr>
						<th class="garaTh">Cognome</th>
						<th class="garaTh">Nome</th>							  
						<th class="garaTh">Codice Fiscale</th>   
						<th class="garaTh">Ruolo</th>
					</tr>
					<% if(datiResponsabile.getFullSize()>0) { 
					 %>
					<% for ( int rowIndex = 0; rowIndex < datiResponsabile.getTableSize(); rowIndex++ ) { %>  
						<% String idrowResp = "row"+prefixResp+rowIndex; %>
								<tr id="<%= idrowResp %>">
									<% rowResponsabile = datiResponsabile .getRow(rowIndex); %>
									<% String ID_RESPONSABILE = rowResponsabile.getNulledField(RESP_INIZIO.ID_RESPONSABILE);
									String DATA_INIZIO_RES = rowResponsabile.getNulledField(RESP_INIZIO.DATA_INIZIO_RES);
									String CODICE_FISCALE_RESPONSABILE = rowResponsabile.getNulledField(SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE);
									String COGNOME = rowResponsabile.getNulledField(SOGGETTI_RESPONSABILI.COGNOME);
									String NOME = rowResponsabile.getNulledField(SOGGETTI_RESPONSABILI.NOME);
									String RUOLO_RESP = rowResponsabile.getNulledField(RUOLI_RESPONSABILE.TABLE_NAME+RUOLI_RESPONSABILE.DESCRIZIONE);
									String ID_RUOLO_RESP = rowResponsabile.getNulledField(RESP_INIZIO.ID_RUOLO);
									%>
									
									<td class="garaTd" id="<%= idrowResp + PSBD.FIELD_NAME_COGNOME_RESPONSABILE %>"><%= COGNOME %></td>
									<td class="garaTd" id="<%= idrowResp + PSBD.FIELD_NAME_NOME_RESPONSABILE %>"><%= NOME %></td>
									<td class="garaTd" id="<%= idrowResp + PSBD.FIELD_NAME_CODICE_FISCALE_RESPONSABILE %>"><%= CODICE_FISCALE_RESPONSABILE %></td>
									<td class="garaTd" id="<%= idrowResp + PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE %>"><%= RUOLO_RESP %></td>
									<td id="<%= idrowResp + PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE %>" style="display: none"><%= ID_RUOLO_RESP %></td>
									<td id="<%= idrowResp + PSBD.FIELD_NAME_ID_RESPONSABILE%>" style="display: none"><%= ID_RESPONSABILE %></td>
									<td id="<%= idrowResp + PSBD.FIELD_NAME_DATA_INIZIO_RES%>" style="display: none"><%= DATA_INIZIO_RES %></td>
									
									<td style="display: none"><input type="hidden" id="hidden<%= idrowResp + PSBD.FIELD_NAME_COGNOME_RESPONSABILE %>" name="<%= idrowResp + PSBD.FIELD_NAME_COGNOME_RESPONSABILE %>" value="<%= COGNOME %>"></td>
									<td style="display: none"><input type="hidden" id="hidden<%= idrowResp + PSBD.FIELD_NAME_NOME_RESPONSABILE %>" name="<%= idrowResp + PSBD.FIELD_NAME_NOME_RESPONSABILE %>" value="<%= NOME %>"></td>
									<td style="display: none"><input type="hidden" id="hidden<%= idrowResp + PSBD.FIELD_NAME_CODICE_FISCALE_RESPONSABILE %>" name="<%= idrowResp + PSBD.FIELD_NAME_CODICE_FISCALE_RESPONSABILE %>" value="<%= CODICE_FISCALE_RESPONSABILE %>"></td>
									<td style="display: none"><input type="hidden" id="hidden<%= idrowResp + PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE %>" name="<%= idrowResp + PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE %>" value="<%= RUOLO_RESP %>"></td>
																		 
									<td style="display: none"><input type="hidden" id="hidden<%= idrowResp + PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE %>" name="<%= idrowResp + PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE %>" value="<%= ID_RUOLO_RESP %>"></td>
									<td style="display: none"><input type="hidden" id="hidden<%= idrowResp + PSBD.FIELD_NAME_ID_RESPONSABILE %>" name="<%= idrowResp + PSBD.FIELD_NAME_ID_RESPONSABILE %>" value="<%= ID_RESPONSABILE %>"></td>
									<td style="display: none"><input type="hidden" id="hidden<%= idrowResp + PSBD.FIELD_NAME_DATA_INIZIO_RES %>" name="<%= idrowResp + PSBD.FIELD_NAME_DATA_INIZIO_RES %>" value="<%= DATA_INIZIO_RES %>"></td>
									 
									<% if (!noOp) {%>
										<td class="hmenu"><a title="Modifica <%= prefixResp %>" 
										href="javascript:setForModifyRow('<%= idrowResp %>',[<%= PSBD.argsResp %>],[<%=PSBD.argsRespNascosti%>],'<%=prefixResp%>')">Modifica</a></td>
										<td class="hmenu"><a title="Cancella <%= prefixResp %>" 
										href="javascript:deleteRow('<%= idrowResp %>',[<%= PSBD.argsResp %>],[<%=PSBD.argsRespNascosti%>],'<%=prefixResp%>')">Cancella</a></td>
										 
									<% } %>
								</tr> 
							<% } %>
						<% }%>	
					</tbody>
				</table> 
			</div>	 		
			
		<p></p>
			<% if (!noOp) {%>
					<div class="hmenu"><a id="showHide<%= prefixResp %>Button" href="javascript:showSezioneAggiungi([<%= PSBD.argsResp %>],[<%=PSBD.argsRespNascosti%>],'<%=prefixResp%>')" title="Aggiungi Incaricato">Aggiungi Incaricato</a></div>
				<% } %>	
		<div class="detailHelp" id="divAgg<%= prefixResp %>" style="display: none; border: 1px solid #cfcfcf;">
			<table width="100%">
								<tr>
									<th><label for="">Codice Fiscale Incaricato</label></th>
									<td>
										<input type="text" id="<%= PSBD.FIELD_NAME_CODICE_FISCALE_RESPONSABILE %>" 
											maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixResp%>')">
									</td>
									<td>
										<div class="hmenu"><a title="Cerca in rubrica" href="javascript:apriPopUpRubrica('rubrica','<%= PSBD.TAB_RESPONSABILE_PROCEDIMENTO %>','Rubrica Incaricati')">Cerca in rubrica</a></div>
									</td>
									
								</tr>
					
								<tr>
									<th><label for="">Cognome</label></th>
									<td>
										<input type="text" id="<%= PSBD.FIELD_NAME_COGNOME_RESPONSABILE %>"
											maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixResp%>')">
									</td>
								</tr>
					
								<tr>
									<th><label for="">Nome</label></th>
									<td>
										<input type="text" id="<%= PSBD.FIELD_NAME_NOME_RESPONSABILE %>" 
											maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixResp%>')">
									</td>
								</tr>
								<tr><td>&nbsp;</td></tr>
								<tr>
									<th><label>Ruolo</label></th>
									<td>
										<select tabindex="<%=++indiceTab%>" id="<%= PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE%>" CLASS="BOTTONE" onchange="setFormModified('Modificato<%=prefixResp%>')">
											<% ruoliResponsabili.printOptionList(RUOLI_RESPONSABILE.ID_RUOLO,RUOLI_RESPONSABILE.DESCRIZIONE,null,out); %>												
										</select>
									</td>							
								</tr>
								<tr style="display: none;">
									<td>
										<input disabled type="text" id="<%= PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE%>"
												value="" onchange="setFormModified('Modificato<%=prefixResp%>')"/>
									</td>							
								</tr>
								<tr style="display: none;">
									<td>
										<input disabled type="text" id="<%= PSBD.FIELD_NAME_ID_RESPONSABILE%>"
												value="" onchange="setFormModified('Modificato<%=prefixResp%>')"/>
									</td>							
								</tr>
								<tr style="display: none;">
									<td>
										<input disabled type="text" id="<%= PSBD.FIELD_NAME_DATA_INIZIO_RES%>"
												value="" onchange="setFormModified('Modificato<%=prefixResp%>')"/>
									</td>							
								</tr>
								<tr><td class="hmenu"><a id="AddMod<%= prefixResp %>" href="javascript:addRow([<%= PSBD.argsResp %>],[<%=PSBD.argsRespNascosti%>],'<%=prefixResp%>')">Aggiungi</a></td></tr>			
							</table>
					<input type="hidden" id="Modificato<%= prefixResp %>" name ="Modificato<%= prefixResp %>" value="0">
		</div>
	</fieldset> 
	<input type="hidden" id="Modificato1" name="Modificato1" value="<%= modificato1 %>"> 
	<input type="hidden" id="selected<%= prefixResp %>" value="0">
		