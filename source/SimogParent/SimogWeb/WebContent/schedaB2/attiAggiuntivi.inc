<%@page import="it.avlp.simog.db.advanced.TableBeanRow"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletSchedaB2"%>

<%@page import="it.avlp.simog.db.generated.STATI_AVANZ"%>
<%@page import="it.avlp.simog.db.generated.ATTI_AGGIUNTIVI"%>
<fieldset class="detailHelp" style="border: 2px solid #cfcfcf;">
<%String modificato2 = (request.getAttribute("Modificato2") != null)? (String)request.getAttribute("Modificato2") : "0";%>
		<%
			String prefixAtti = ParametriServletSchedaB2.TAB_ATTI_AGGIUNTIVI;   
			String visibleAtt = "block";
			String attiSize = "200px";
		%>
			<div id="DIVTabella<%= prefixAtti %>" class="scrollTabs" style="height: <%= attiSize %>; display: <%= visibleAtt %>">
				<table id="idTabella<%= prefixAtti %>">
					<tbody>	
						<tr>
							<th class="garaTh">Data Sottoscrizione</th>
							<th class="garaTh">Oggetto</th>
							<th class="garaTh">Importo</th>
							<th class="garaTh">Data Sottoscrizione Eventuale Accordo Bonario</th>  							
							<th class="garaTh">Oneri Derivanti</th>  		
							<th class="garaTh">Eventuali Interessi Legali</th>  		
						</tr>
						<% 
						for (int numRow = 0; numRow<attiAggiuntivi.getFullSize(); numRow++) { 
							String idrowCert = "row"+prefixAtti+numRow;
							TableBeanRow rowAttiAggiuntivi = attiAggiuntivi.getRow(numRow);

							String DATA_ATTO = PageHelper.getFormattedDate(rowAttiAggiuntivi.getNulledField(ATTI_AGGIUNTIVI.DATA_ATTO));
							String OGGETTO	= rowAttiAggiuntivi.getNulledField(ATTI_AGGIUNTIVI.OGGETTO);		
							String IMPORTO = PageHelper.formattaImporto(rowAttiAggiuntivi.getNulledField(ATTI_AGGIUNTIVI.IMPORTO)); 
							String DATA_ACCORDO = PageHelper.getFormattedDate(rowAttiAggiuntivi.getNulledField(ATTI_AGGIUNTIVI.DATA_ACCORDO));	
							String ONERI = PageHelper.formattaImporto(rowAttiAggiuntivi.getNulledField(ATTI_AGGIUNTIVI.ONERI));		
							String INTERESSI = PageHelper.formattaImporto(rowAttiAggiuntivi.getNulledField(ATTI_AGGIUNTIVI.INTERESSI));		
						%>
							<tr id="<%= idrowCert %>">
								<td class="garaTd" id="<%= idrowCert + ATTI_AGGIUNTIVI.DATA_ATTO %>"><%= DATA_ATTO %></td>
								<td class="garaTd" id="<%= idrowCert + ATTI_AGGIUNTIVI.OGGETTO %>"><%= OGGETTO %></td>																
								<td class="garaTd" id="<%= idrowCert + ATTI_AGGIUNTIVI.IMPORTO %>"><%= IMPORTO %></td>
								<td class="garaTd" id="<%= idrowCert + ATTI_AGGIUNTIVI.DATA_ACCORDO%>"><%= DATA_ACCORDO %></td>
								<td class="garaTd" id="<%= idrowCert + ATTI_AGGIUNTIVI.ONERI%>"><%= ONERI %></td>
								<td class="garaTd" id="<%= idrowCert + ATTI_AGGIUNTIVI.INTERESSI%>"><%= INTERESSI %></td>
																																
								<td style="display: none;"><input type="hidden" id="hidden<%= idrowCert + ATTI_AGGIUNTIVI.DATA_ATTO %>" name="<%= idrowCert + ATTI_AGGIUNTIVI.DATA_ATTO %>" value="<%= DATA_ATTO %>"></td>
								<td style="display: none;"><input type="hidden" id="hidden<%= idrowCert + ATTI_AGGIUNTIVI.OGGETTO %>" name="<%= idrowCert + ATTI_AGGIUNTIVI.OGGETTO %>" value="<%= OGGETTO %>"></td>										
								<td style="display: none;"><input type="hidden" id="hidden<%= idrowCert + ATTI_AGGIUNTIVI.IMPORTO %>" name="<%= idrowCert + ATTI_AGGIUNTIVI.IMPORTO %>" value="<%= IMPORTO %>"></td>
								<td style="display: none;"><input type="hidden" id="hidden<%= idrowCert + ATTI_AGGIUNTIVI.DATA_ACCORDO %>" name="<%= idrowCert + ATTI_AGGIUNTIVI.DATA_ACCORDO %>" value="<%= DATA_ACCORDO %>"></td>								
								<td style="display: none;"><input type="hidden" id="hidden<%= idrowCert + ATTI_AGGIUNTIVI.ONERI %>" name="<%= idrowCert + ATTI_AGGIUNTIVI.ONERI %>" value="<%= ONERI %>"></td>								
								<td style="display: none;"><input type="hidden" id="hidden<%= idrowCert + ATTI_AGGIUNTIVI.INTERESSI %>" name="<%= idrowCert + ATTI_AGGIUNTIVI.INTERESSI %>" value="<%= INTERESSI %>"></td>								
									
								<% if (!noOp) {%>
								<td class="hmenu"><a title="Modifica Atto" 
									href="javascript:setForModifyRow('<%= idrowCert %>',[<%= ParametriServletSchedaB2.argsAtti %>],[<%=ParametriServletSchedaB2.argsAttiNascosti%>],'<%= prefixAtti %>')">Modifica</a></td>
								<td class="hmenu"><a title="Cancella Atto" 
									href="javascript:deleteRow('<%= idrowCert %>',[<%= ParametriServletSchedaB2.argsAtti %>],[<%=ParametriServletSchedaB2.argsAttiNascosti%>],'<%= prefixAtti %>')" >Cancella</a></td>
								<% } %>						
							</tr>
					<%} %>  
					</tbody>
				</table>
	  		</div>	  			  			  			  		
		
		<p></p>
			<% if (!noOp) {%>
						<div class="hmenu"><a id="showHide<%= prefixAtti %>Button" title="Aggiungi Atto" href="javascript:showSezioneAggiungi([<%= ParametriServletSchedaB2.argsAtti %>],[<%=ParametriServletSchedaB2.argsAttiNascosti%>],'<%= prefixAtti %>')">Aggiungi Atto</a></div>
					<% }  %>
		<div class="detailHelp" id="divAgg<%= prefixAtti %>" style="display: none; border: 1px solid #cfcfcf;">
			<table width="100%">
				<tr>
					<th><label>Data sottoscrizione eventuale atto aggiuntivo</label></th>
			    	<td>
						<input tabindex="<%=++indiceTab%>" <%= disabilitato %> style="text-align:center;" type="text" id="<%= ATTI_AGGIUNTIVI.DATA_ATTO %>" name="<%= ATTI_AGGIUNTIVI.DATA_ATTO %>" onblur="Calendar.validaData(this)" value="">
						<% if(!confermato) {%>	
						<img  src="calendar/img.gif" id="calendar<%= ATTI_AGGIUNTIVI.DATA_ATTO %>" style="cursor: pointer; border: 1px solid red;" title="Date selector"
										onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
						<script type="text/javascript">
							    Calendar.setup({
							        inputField     :    "<%= ATTI_AGGIUNTIVI.DATA_ATTO %>",     // id of the input field
							        ifFormat       :    "%d/%m/%Y",      // format of the input field
							        button         :    "calendar<%= ATTI_AGGIUNTIVI.DATA_ATTO %>",  // trigger for the calendar (button ID)
							        align          :    "Tl",           // alignment (defaults to "Bl")
							        singleClick    :    true								        
						    });
						</script>
						<% } %>	
					</td>
				</tr>
				<tr>
					<th><label>Oggetto dell'Atto Aggiuntivo</label></th>
					<td>
						<input tabindex="<%=++indiceTab%>" type="text" size="50" id="<%= ATTI_AGGIUNTIVI.OGGETTO %>" value="" onchange="setFormModified('Modificato<%=prefixAtti%>')">						
					</td>					
				</tr>
				<tr>
					<th><label>Importo dell'Atto aggiuntivo</label></th>
					<td>
						<input tabindex="<%=++indiceTab%>" style="text-align: right;" onblur="validateAmount(this)" type="text" id="<%= ATTI_AGGIUNTIVI.IMPORTO %>" value="" onchange="setFormModified('Modificato<%=prefixAtti%>')">
					</td>
				</tr>
				<tr>
					<th><label>Data sottoscrizione eventuale accordo bonario</label></th>
			    	<td>
						<input tabindex="<%=++indiceTab%>" <%= disabilitato %> style="text-align:center;" type="text" id="<%= ATTI_AGGIUNTIVI.DATA_ACCORDO %>" name="<%= ATTI_AGGIUNTIVI.DATA_ACCORDO %>" onblur="Calendar.validaData(this)" value="">
						<% if(!confermato) {%>	
						<img  src="calendar/img.gif" id="calendar<%= ATTI_AGGIUNTIVI.DATA_ACCORDO %>" style="cursor: pointer; border: 1px solid red;" title="Date selector"
										onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
						<script type="text/javascript">
							    Calendar.setup({
							        inputField     :    "<%= ATTI_AGGIUNTIVI.DATA_ACCORDO %>",     // id of the input field
							        ifFormat       :    "%d/%m/%Y",      // format of the input field
							        button         :    "calendar<%= ATTI_AGGIUNTIVI.DATA_ACCORDO %>",  // trigger for the calendar (button ID)
							        align          :    "Tl",           // alignment (defaults to "Bl")
							        singleClick    :    true								        
						    });
						</script>
						<% } %>	
					</td>
				</tr>
				<tr>
					<th><label>Oneri derivanti</label></th>
					<td>
						<input tabindex="<%=++indiceTab%>" style="text-align: right;" onblur="validateAmount(this)" type="text" id="<%= ATTI_AGGIUNTIVI.ONERI %>" value="" onchange="setFormModified('Modificato<%=prefixAtti%>')">
					</td>					
				</tr>	 			
				<tr>
					<th><label>Eventuali interessi legali</label></th>
					<td>
						<input tabindex="<%=++indiceTab%>" style="text-align: right;" onblur="validateAmount(this)" type="text" id="<%= ATTI_AGGIUNTIVI.INTERESSI %>" value="" onchange="setFormModified('Modificato<%=prefixAtti%>')">
					</td>					
				</tr>	
				<tr><td class="hmenu"><a id="AddMod<%= prefixAtti %>" href="javascript:addRow([<%= ParametriServletSchedaB2.argsAtti %>],[<%=ParametriServletSchedaB2.argsAttiNascosti%>],'<%= prefixAtti %>')" >Aggiungi</a></td></tr>
			</table>  		
			<input type="hidden" id="Modificato<%=prefixAtti%>" name="Modificato<%=prefixAtti%>" value="0">
			</div>
		
</fieldset>	
<input type="hidden" id="Modificato2" name="Modificato2" value="<%= modificato2 %>">
<input type="hidden" id="selected<%= prefixAtti %>" name ="selected<%= prefixAtti %>" value="0">