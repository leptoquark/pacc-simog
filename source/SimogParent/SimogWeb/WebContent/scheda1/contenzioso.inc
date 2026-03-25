<fieldset class="detailHelp" style="border: 2px solid #cfcfcf;">
<%String modificato3 = (request.getAttribute("Modificato3") != null)? (String)request.getAttribute("Modificato3") : "0";%>
		<%
			String prefixCont = PSBD.CONTENZIOSO;
			String visibleCont = "block";
			String contenziosiSize = "200px";
		%>
		
			<div id="DIVTabella<%= prefixCont %>" class="scrollTabs" style="height: <%= contenziosiSize %>; display: <%= visibleCont %>">
				<table id="idTabella<%= prefixCont %>">
					<tbody>	
						<tr>
							<th class="garaTh"><label for="">Motivazione</th>
							<th class="garaTh"><label for="">Codice fiscale ditta</th>  
							<th class="garaTh"><label for="">Contenzioso In fase Di Gara</th>
						</tr>
						<% 
						for (int numRow = 0; numRow<datiContenziosi.getRowsCount(); numRow++) { 
							String idrowCont = "row"+prefixCont+numRow;
							  TableBeanRow rowContenzioso = datiContenziosi.getRow(numRow);
							  
							 	
								
								String CONTENZIOSO_GARA = rowContenzioso.getNulledField(CONTENZIOSI.CONTENZIOSO_GARA);
								String MOTIVAZIONE = rowContenzioso.getNulledField(CONTENZIOSI.MOTIVAZIONE);
								String CODICE_FISCALE_DITTA = rowContenzioso.getNulledField(CONTENZIOSI.CODICE_FISCALE_DITTA); 
							
							
						%>
							<tr id="<%= idrowCont %>">
								<td class="garaTd"><textarea id="<%= idrowCont + PSBD.FIELD_NAME_MOTIVAZIONE %>" rows="2" cols="40" disabled ><%= MOTIVAZIONE %></textarea></td>
								<td class="garaTd"><input type="text" id="<%= idrowCont + PSBD.FIELD_NAME_CODICE_FISCALE_DITTA %>" maxlength="20" disabled value="<%= CODICE_FISCALE_DITTA %>"></td>
								<% String CONT_GARA = (CONTENZIOSO_GARA.equalsIgnoreCase("S"))? "SI" : "NO"; %>
								<td class="garaTd" id="<%= idrowCont + PSBD.FIELD_NAME_CONTENZIOSO_GARA %>" class="garaTd"><%= CONT_GARA %></td>
								
								<td style="display: none;"><input type="hidden" id="hidden<%= idrowCont + PSBD.FIELD_NAME_MOTIVAZIONE %>" name="<%= idrowCont + PSBD.FIELD_NAME_MOTIVAZIONE %>" value="<%= MOTIVAZIONE %>"></td>
								<td style="display: none;"><input type="hidden" id="hidden<%= idrowCont + PSBD.FIELD_NAME_CODICE_FISCALE_DITTA %>" name="<%= idrowCont + PSBD.FIELD_NAME_CODICE_FISCALE_DITTA %>" value="<%= CODICE_FISCALE_DITTA %>"></td>
								<td style="display: none;"><input type="hidden" id="hidden<%= idrowCont + PSBD.FIELD_NAME_CONTENZIOSO_GARA %>" name="<%= idrowCont + PSBD.FIELD_NAME_CONTENZIOSO_GARA %>" value="<%= CONTENZIOSO_GARA %>"></td>													
									
								<% if (!noOp) {%>
								<td class="hmenu"><a title="Modifica Contenzioso" 
									href="javascript:setForModifyRow('<%= idrowCont %>',[<%= PSBD.argsCont %>],[<%=PSBD.argsContNascosti%>],'<%= prefixCont %>')">Modifica</a></td>
								<td class="hmenu"><a title="Cancella Contenzioso" 
									href="javascript:deleteRow('<%= idrowCont %>',[<%= PSBD.argsCont %>],[<%=PSBD.argsContNascosti%>],'<%= prefixCont %>')" >Cancella</a></td>
								<% } %>						
							</tr>
					<%} %>
					</tbody>
				</table>
	  		</div>	  			  			  			  		
		
		<p></p>
			<% if (!noOp) {%>
						<div class="hmenu"><a id="showHide<%= prefixCont %>Button" title="Aggiungi Contenzioso" href="javascript:showSezioneAggiungi([<%= PSBD.argsCont %>],[<%=PSBD.argsContNascosti%>],'<%= prefixCont %>')">Aggiungi Contenzioso</a></div>
					<% }  %>
		<div class="detailHelp" id="divAgg<%= prefixCont %>" style="display: none; border: 1px solid #cfcfcf;"  onkeypress="setFormModified('Modificato3')">
			<table width="100%">
					
						 			<tr>
										<th><label for="<%= PSBD.FIELD_NAME_CONTENZIOSO_GARA %>">Contenzioso in fase di gara</label></th>
										<td>
											SI<input type="radio" id="<%= PSBD.S_FIELD_NAME_CONTENZIOSO_GARA %>" name="<%= PSBD.FIELD_NAME_CONTENZIOSO_GARA %>" value="S" onchange="setFormModified('Modificato<%=prefixCont%>')">
											NO<input type="radio" id ="<%= PSBD.N_FIELD_NAME_CONTENZIOSO_GARA %>" name="<%= PSBD.FIELD_NAME_CONTENZIOSO_GARA %>" value="N" onchange="setFormModified('Modificato<%=prefixCont%>')">
										</td>
									</tr>
									
									<tr>
										<th><label>Motivazione</label></th>
										<td>
											<textarea rows="10" cols="40" id="<%= PSBD.FIELD_NAME_MOTIVAZIONE %>"  onchange="setFormModified('Modificato<%=prefixCont%>')"></textarea>
										</td>
									</tr>
						
									<tr>
										<th><label>Codice fiscale ditta</label></th>
										<td>
											<input type="text" id="<%= PSBD.FIELD_NAME_CODICE_FISCALE_DITTA %>" name="<%= PSBD.FIELD_NAME_CODICE_FISCALE_DITTA %>" 
												maxlength="16" value=""  onchange="setFormModified('Modificato<%=prefixCont%>')">
										</td>
									</tr>
									<tr><td class="hmenu"><a id="AddMod<%= prefixCont %>" href="javascript:addRow([<%= PSBD.argsCont %>],[<%=PSBD.argsContNascosti%>],'<%= prefixCont %>')" >Aggiungi</a></td></tr>
								</table>  		
								<input type="hidden" id="Modificato<%=prefixCont%>" name="Modificato<%=prefixCont%>" value="0">
			</div>
		
</fieldset>	
<input type="hidden" id="Modificato3" name="Modificato3" value="<%= modificato3 %>">
<input type="hidden" id="selected<%= prefixCont %>" name ="selected<%= prefixCont %>" value="0">