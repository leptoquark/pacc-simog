	
<%String modificato2 = (request.getAttribute("Modificato2") != null)? (String)request.getAttribute("Modificato2") : "0";%>	  
<% TableBeanRow rowSubappalti = null;
		String prefixSub = ParametriServletSchedaB4.TAB_SUBAPPALTI;
		String visibleSub = "block";
		String subappaltiSize = subappalti.getFullSize()> 3 ? "200px" : "150px";
		%>
<%@page import="it.avlp.simog.db.generated.RESP_INIZIO"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletSchedaB4"%>
<%@page import="it.avlp.simog.db.generated.FINE_LAVORI"%>
<%@page import="it.avlp.simog.db.generated.SUBAPPALTI"%>
<%@page import="it.avlp.simog.util.PageHelper"%>
<table>
	<% String checkedFLAG_SUBAPPALTI = "";
		String enabledTable = "block";
		if("S".equals(FLAG_SUBAPPALTI)){
			checkedFLAG_SUBAPPALTI = "checked";
			//enabledTable = "block";
			}%>
		
	<tr>
		<th><label>Sono stati autorizzati subappalti?</label></th>
		<td>
			<input tabindex="<%=++indiceTab%>" <%= disabilitato %> <%= checkedFLAG_SUBAPPALTI %> type="checkbox" id ="<%= FINE_LAVORI.FLAG_SUBAPPALTI %>" name="<%= FINE_LAVORI.FLAG_SUBAPPALTI %>" value="S"  onclick="setFormModified('Modificato0')">
		</td>
	</tr>
</table>

<fieldset class="detailHelp" style="border: 2px solid #cfcfcf; display: <%= enabledTable %>" id="subappaltiFieldSet">
		<div id="DIVTabella<%= prefixSub %>" class="scrollTabs" style="height: <%= subappaltiSize %>; display: <%= visibleSub %>">
			<table id="idTabella<%= prefixSub %>">
				<tbody>
				<tr>
					<th class="garaTh">Codice fiscale ditta subappaltatrice (impresa singola o mandataria)</th>
					<th class="garaTh"><%= SUB_TIPO_STRING %> subappaltat<%= FINALE %></th>							  
					<th class="garaTh">Importo <%= SUB_TIPO_STRING %> subappaltat<%= FINALE %></th>   					
				</tr>
				<% if(subappalti.getFullSize()>0) { 
				 %>
				<% for ( int rowIndex = 0; rowIndex < subappalti.getTableSize(); rowIndex++ ) { %>  
					<% String idrowResp = "row"+prefixSub+rowIndex; %>
							<tr id="<%= idrowResp %>">
								<% rowSubappalti = subappalti .getRow(rowIndex); %>
								<%
								String CF_DITTA = rowSubappalti.getNulledField(SUBAPPALTI.CF_DITTA);
								String RUOLO_RESP = rowSubappalti.getNulledField(SUBAPPALTI.FORNITURA);
								String IMPORTO = PageHelper.formattaImporto(rowSubappalti.getNulledField(SUBAPPALTI.IMPORTO));
								%>
								
								<td class="garaTd" id="<%= idrowResp + SUBAPPALTI.CF_DITTA %>"><%= CF_DITTA %></td>
								<td class="garaTd" id="<%= idrowResp + SUBAPPALTI.FORNITURA%>"><%= RUOLO_RESP %></td>
								<td class="garaTd" id="<%= idrowResp + SUBAPPALTI.IMPORTO%>"><%= IMPORTO %></td>
																	 
								<td style="display: none"><input type="hidden" id="hidden<%= idrowResp + SUBAPPALTI.CF_DITTA %>" name="<%= idrowResp + SUBAPPALTI.CF_DITTA %>" value="<%= CF_DITTA %>"></td>
								<td style="display: none"><input type="hidden" id="hidden<%= idrowResp + SUBAPPALTI.FORNITURA %>" name="<%= idrowResp + SUBAPPALTI.FORNITURA %>" value="<%= RUOLO_RESP %>"></td>
								<td style="display: none"><input type="hidden" id="hidden<%= idrowResp + SUBAPPALTI.IMPORTO %>" name="<%= idrowResp + SUBAPPALTI.IMPORTO %>" value="<%= IMPORTO %>"></td>
								 
								<% if (!noOp) {%>
									<td class="hmenu"><a title="Modifica <%= prefixSub %>" 
									href="javascript:setForModifyRow('<%= idrowResp %>',[<%= ParametriServletSchedaB4.argsSub %>],[<%=ParametriServletSchedaB4.argsSubNascosti%>],'<%=prefixSub%>')">Modifica</a></td>
									<td class="hmenu"><a title="Cancella <%= prefixSub %>" 
									href="javascript:deleteRow('<%= idrowResp %>',[<%= ParametriServletSchedaB4.argsSub %>],[<%=ParametriServletSchedaB4.argsSubNascosti%>],'<%=prefixSub%>')">Cancella</a></td>
									 
								<% } %>
							</tr> 
						<% } %>
					<% }%>	
				</tbody>
			</table> 
		</div>	 		
		
	<p></p>
		<% if (!noOp) {%>
				<div class="hmenu"><a id="showHide<%= prefixSub %>Button" href="javascript:showSezioneAggiungi([<%= ParametriServletSchedaB4.argsSub %>],[<%=ParametriServletSchedaB4.argsSubNascosti%>],'<%=prefixSub%>')" title="Aggiungi Subappalto">Aggiungi Subappalto</a></div>
			<% } %>	
	<div class="detailHelp" id="divAgg<%= prefixSub %>" style="display: none; border: 1px solid #cfcfcf;">
		<table width="100%">
							<tr>
								<th><label>Codice fiscale ditta subappaltatrice (impresa singola o mandataria)</label></th>
								<td>
									<input type="text" id="<%= SUBAPPALTI.CF_DITTA %>" maxlength="16"
										value="" onchange="setFormModified('Modificato<%=prefixSub%>')"/>
								</td>							
							</tr>
							<%----%>
							<tr>
								<th><label>Codice fiscale ditta aggiudicatrice nel caso di aggiudicatari multipli</label></th>
								<td>
									<input type="text" id="<%= SUBAPPALTI.CF_AGGIUDICATARIO %>" maxlength="16"
										value="" onchange="setFormModified('Modificato<%=prefixSub%>')"/>
								</td>							
							</tr>
							
							<tr>
								<th><label>Servizio subappaltato</label></th>
								<td>
									<textarea rows="10" cols="40" id="<%= SUBAPPALTI.FORNITURA %>"
										onchange="setFormModified('Modificato<%=prefixSub%>')"></textarea>
								</td>							
							</tr>
							<tr>
								<th><label>Importo servizio subappaltato</label></th>
								<td>
									<input type="text" id="<%= SUBAPPALTI.IMPORTO %>" value="" style="text-align:right;"
											onchange="setFormModified('Modificato<%=prefixSub%>')" onblur="validateAmount(this)"/>									
								</td>							
							</tr>							
							<tr><td class="hmenu"><a id="AddMod<%= prefixSub %>" href="javascript:addRow([<%= ParametriServletSchedaB4.argsSub %>],[<%=ParametriServletSchedaB4.argsSubNascosti%>],'<%=prefixSub%>')">Aggiungi</a></td></tr>			
						</table>
				<input type="hidden" id="Modificato<%= prefixSub %>" name ="Modificato<%= prefixSub %>" value="0">
	</div>
</fieldset> 
<input type="hidden" id="Modificato2" name="Modificato2" value="<%= modificato2 %>"> 
<input type="hidden" id="selected<%= prefixSub %>" value="0">
