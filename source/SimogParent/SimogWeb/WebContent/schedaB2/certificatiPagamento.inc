<%@page import="it.avlp.simog.db.advanced.TableBeanRow"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletSchedaB2"%>

<%@page import="it.avlp.simog.db.generated.STATI_AVANZ"%>
<%@page import="it.avlp.simog.db.generated.CERTIF_PAGAM"%>
<%@page import="it.avlp.simog.util.PageHelper"%>
<fieldset class="detailHelp" style="border: 2px solid #cfcfcf;">
<%String modificato1 = (request.getAttribute("Modificato1") != null)? (String)request.getAttribute("Modificato1") : "0";%>
		<%
			String prefixCert = ParametriServletSchedaB2.TAB_CERTIFICATI_PAGAMENTO;   
			String visibleCert = "block";
			String certSize = "200px";
			
			String checkedSomma = "";
			String checkedTrasferimenti = "";
			if(FLAG_PAGAMENTO.equals("1") || FLAG_PAGAMENTO.equals("3"))
				checkedSomma = "checked";
			if(FLAG_PAGAMENTO.equals("2") || FLAG_PAGAMENTO.equals("3"))
				checkedTrasferimenti = "checked";
		%>
			<table>
				<tr>
					<th rowspan="2"><label>Modalità di pagamento del corrispettivo</label></th>
					<td  width="40%" >
						<input tabindex="<%=++indiceTab%>" <%= disabilitato %> <%= checkedSomma %> type="checkbox" id ="<%= ParametriServletSchedaB2.MODALITA_PAGAMENTO_SOMMA %>" name="<%= ParametriServletSchedaB2.MODALITA_PAGAMENTO_SOMMA %>" value="1"  onchange="setFormModified('Modificato0')">Somme in denaro
					</td>
				</tr>
				<tr>
					<td>			
						<input tabindex="<%=++indiceTab%>" <%= disabilitato %> <%= checkedTrasferimenti %> type="checkbox" id ="<%= ParametriServletSchedaB2.MODALITA_PAGAMENTO_TRASFERIMENTI %>" name ="<%= ParametriServletSchedaB2.MODALITA_PAGAMENTO_TRASFERIMENTI %>" value="2"  onchange="setFormModified('Modificato0')">Trasferimento in proprietà di beni immobili
					</td>
				</tr>
			</table>	
			<div id="DIVTabella<%= prefixCert %>" class="scrollTabs" style="height: <%= certSize %>; display: <%= visibleCert %>">
				<table id="idTabella<%= prefixCert %>">
					<tbody>	
						<tr>
							<th class="garaTh">Stato di avanzamento n.</th>
							<th class="garaTh">Data di emissione</th>
							<th class="garaTh">Importo Certificato</th>
							<th class="garaTh">Importo Interessi Legali Moratori</th>  							
						</tr>
						<% 
						for (int numRow = 0; numRow<certificatiPagamento.getFullSize(); numRow++) { 
							String idrowCert = "row"+prefixCert+numRow;
							TableBeanRow rowCertficatiPagamento = certificatiPagamento.getRow(numRow);

							String NUM_STATO_AVANZAMENTO = rowCertficatiPagamento.getNulledField(CERTIF_PAGAM.NUM_STATO_AVANZ);
							String DATA_EMISSIONE	= PageHelper.getFormattedDate(rowCertficatiPagamento.getNulledField(CERTIF_PAGAM.DATA_EMISSIONE));		
							String IMPORTO_PAGAM = PageHelper.formattaImporto(rowCertficatiPagamento.getNulledField(CERTIF_PAGAM.IMPORTO_PAGAM)); 
							String IMPORTO_INTER = PageHelper.formattaImporto(rowCertficatiPagamento.getNulledField(CERTIF_PAGAM.IMPORTO_INTER));												
						%>
							<tr id="<%= idrowCert %>">
								<td class="garaTd" id="<%= idrowCert + CERTIF_PAGAM.NUM_STATO_AVANZ %>"><%= NUM_STATO_AVANZAMENTO %></td>
								<td class="garaTd" id="<%= idrowCert + CERTIF_PAGAM.DATA_EMISSIONE %>"><%= DATA_EMISSIONE %></td>																
								<td class="garaTd" id="<%= idrowCert + CERTIF_PAGAM.IMPORTO_PAGAM %>"><%= IMPORTO_PAGAM %></td>
								<td class="garaTd" id="<%= idrowCert + CERTIF_PAGAM.IMPORTO_INTER%>"><%= IMPORTO_INTER %></td>
																																
								<td style="display: none;"><input type="hidden" id="hidden<%= idrowCert + CERTIF_PAGAM.NUM_STATO_AVANZ %>" name="<%= idrowCert + CERTIF_PAGAM.NUM_STATO_AVANZ %>" value="<%= NUM_STATO_AVANZAMENTO %>"></td>
								<td style="display: none;"><input type="hidden" id="hidden<%= idrowCert + CERTIF_PAGAM.DATA_EMISSIONE %>" name="<%= idrowCert + CERTIF_PAGAM.DATA_EMISSIONE %>" value="<%= DATA_EMISSIONE %>"></td>										
								<td style="display: none;"><input type="hidden" id="hidden<%= idrowCert + CERTIF_PAGAM.IMPORTO_PAGAM %>" name="<%= idrowCert + CERTIF_PAGAM.IMPORTO_PAGAM %>" value="<%= IMPORTO_PAGAM %>"></td>
								<td style="display: none;"><input type="hidden" id="hidden<%= idrowCert + CERTIF_PAGAM.IMPORTO_INTER %>" name="<%= idrowCert + CERTIF_PAGAM.IMPORTO_INTER %>" value="<%= IMPORTO_INTER %>"></td>								
									
								<% if (!noOp) {%>
								<td class="hmenu"><a title="Modifica Certificato" 
									href="javascript:setForModifyRow('<%= idrowCert %>',[<%= ParametriServletSchedaB2.argsCert %>],[<%=ParametriServletSchedaB2.argsCertNascosti%>],'<%= prefixCert %>')">Modifica</a></td>
								<td class="hmenu"><a title="Cancella Certificato" 
									href="javascript:deleteRow('<%= idrowCert %>',[<%= ParametriServletSchedaB2.argsCert %>],[<%=ParametriServletSchedaB2.argsCertNascosti%>],'<%= prefixCert %>')" >Cancella</a></td>
								<% } %>						
							</tr>
					<%} %>  
					</tbody>
				</table>
	  		</div>	  			  			  			  		
		
		<p></p>
			<% if (!noOp) {%>
						<div class="hmenu"><a id="showHide<%= prefixCert %>Button" title="Aggiungi Certificato" href="javascript:showSezioneAggiungi([<%= ParametriServletSchedaB2.argsCert %>],[<%=ParametriServletSchedaB2.argsCertNascosti%>],'<%= prefixCert %>')">Aggiungi Certificato</a></div>
					<% }  %>
		<div class="detailHelp" id="divAgg<%= prefixCert %>" style="display: none; border: 1px solid #cfcfcf;">
			<table width="100%">
				<tr>
					<th><label>Stato di avanzamento n.</label></th>
					<td>
						<input maxlength="9" tabindex="<%=++indiceTab%>" onblur="validateNumber(this)" style="text-align: right;" type="text" id="<%= CERTIF_PAGAM.NUM_STATO_AVANZ %>" value="" onchange="setFormModified('Modificato<%=prefixCert%>')">						
					</td>					
				</tr>
				<tr>
					<th><label>Data di emissione del certificato di pagamento</label></th>
			    	<td>
						<input tabindex="<%=++indiceTab%>" <%= disabilitato %> style="text-align:center;" type="text" id="<%= CERTIF_PAGAM.DATA_EMISSIONE %>" name="<%= CERTIF_PAGAM.DATA_EMISSIONE %>" onblur="Calendar.validaData(this)" value="">
						<% if(!confermato) {%>	
						<img  src="calendar/img.gif" id="calendar<%= CERTIF_PAGAM.DATA_EMISSIONE %>" style="cursor: pointer; border: 1px solid red;" title="Date selector"
										onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
						<script type="text/javascript">
							    Calendar.setup({
							        inputField     :    "<%= CERTIF_PAGAM.DATA_EMISSIONE %>",     // id of the input field
							        ifFormat       :    "%d/%m/%Y",      // format of the input field
							        button         :    "calendar<%= CERTIF_PAGAM.DATA_EMISSIONE %>",  // trigger for the calendar (button ID)
							        align          :    "Tl",           // alignment (defaults to "Bl")
							        singleClick    :    true								        
						    });
						</script>
						<% } %>	
					</td>
				</tr>
				<tr>
					<th><label>Importo del certificato di pagamento</label></th>
					<td>
						<input tabindex="<%=++indiceTab%>" onblur="validateAmount(this)" style="text-align: right;" type="text" id="<%= CERTIF_PAGAM.IMPORTO_PAGAM %>" value="" onchange="setFormModified('Modificato<%=prefixCert%>')">
					</td>
				</tr>
				<tr>
					<th><label>Importo eventuali interessi legali moratori</label></th>
					<td>
						<input tabindex="<%=++indiceTab%>" onblur="validateAmount(this)" style="text-align: right;" type="text" id="<%= CERTIF_PAGAM.IMPORTO_INTER %>" value="" onchange="setFormModified('Modificato<%=prefixCert%>')">
					</td>					
				</tr>	 			
				<tr><td class="hmenu"><a id="AddMod<%= prefixCert %>" href="javascript:addRow([<%= ParametriServletSchedaB2.argsCert %>],[<%=ParametriServletSchedaB2.argsCertNascosti%>],'<%= prefixCert %>')" >Aggiungi</a></td></tr>
			</table>  		
			<input type="hidden" id="Modificato<%=prefixCert%>" name="Modificato<%=prefixCert%>" value="0"/>
		</div>		
</fieldset>	
<input type="hidden" id="Modificato1" name="Modificato1" value="<%= modificato1 %>"/>
<input type="hidden" id="selected<%= prefixCert %>" name ="selected<%= prefixCert %>" value="0"/>