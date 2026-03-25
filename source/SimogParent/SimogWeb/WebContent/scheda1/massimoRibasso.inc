<%
   
  
   String VAL_SOGLIA_ANOMALIA	= (datiAggiudicazione.getFullSize()>0) ? PageHelper.formattaImporto(rowAggiudicazione.getNulledField(AGGIUDICAZIONI.VAL_SOGLIA_ANOMALIA)) : "0,000";		
   String OFFERTA_MASSIMO	= (datiAggiudicazione.getFullSize()>0) ? PageHelper.replaceDotsWithCommas(rowAggiudicazione.getNulledField(AGGIUDICAZIONI.OFFERTA_MASSIMO)) : "0,000";		
   String NUM_OFFERTE_ESCLUSE	= (datiAggiudicazione.getFullSize()>0) ? rowAggiudicazione.getNulledField(AGGIUDICAZIONI.NUM_OFFERTE_ESCLUSE) : "0";		
  

//   String DATA_INIZIO_AGGIUDICAZIONE	= (datiAggiudicazione.getFullSize()>0) ? rowAggiudicazione.getNulledField(AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE) : "";		
   String NUM_OFFERTE_FUORI_SOGLIA	= (datiAggiudicazione.getFullSize()>0) ? rowAggiudicazione.getNulledField(AGGIUDICAZIONI.NUM_OFFERTE_FUORI_SOGLIA) : "0";		
  
   
   String OFFERTA_MINIMA	= (datiAggiudicazione.getFullSize()>0) ? PageHelper.replaceDotsWithCommas(rowAggiudicazione.getNulledField(AGGIUDICAZIONI.OFFERTA_MINIMA)) : "0,000";
  
   String modificato2 = request.getAttribute("Modificato2") != null ? (String)request.getAttribute("Modificato2") : "0";
%>

			<% //@page import="it.avlp.simog.util.PageHelper"%>

			
	<fieldset class="detailHelp" style="border: 2px solid #cfcfcf;">
		<table width="100%">
		<colgroup>
			<col width="60%"/>
			<col width="40%"/>
      </colgroup>
			<tr>
					<th><label for="">Data di scadenza per la presentazione delle manifestazioni di interesse</label></th>
					<td>
						<input tabindex="<%=++indiceTab%>" style="text-align:center" <%=disabled%> onchange="setFormModified('Modificato0')" onblur="Calendar.validaData(this)" type="text" id="dtmanif" name="<%= PSBD.FIELD_NAME_DATA_MANIF_INTERESSE %>"  value="<%= dataManifInteresse%>">
						<% if(disabled.equals("")) {%>
							<img  <%=disabled%> src="calendar/img.gif" id="CALdtmanif" style="cursor: pointer; border: 1px solid red;" title="Date selector"
	  							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
							<script type="text/javascript">
							    Calendar.setup({
							        inputField     :    "dtmanif",     // id of the input field
							        ifFormat       :    "%d/%m/%Y",      // format of the input field
							        button         :    "CALdtmanif",  // trigger for the calendar (button ID)
								    align          :    "Tl",           // alignment (defaults to "Bl")
								    singleClick    :    true
							    });
							</script>
						<% } %>								
					</td>
				</tr>
			
				<tr>
					<th><label for="">Data di scadenza per la presentazione della richiesta di invito</label></th>
					<td>
						<input tabindex="<%=++indiceTab%>" style="text-align:center" <%=disabled%> onchange="setFormModified('Modificato0')" onblur="Calendar.validaData(this)" type="text" id="dtscadinv" name="<%= PSBD.DATA_SCADENZA_RICHIESTA_INVITO %>"  value="<%= dataScadenzaRichiestaInvito%>">
						<% if(disabled.equals("")) {%>
							<img  <%=disabled%> src="calendar/img.gif" id="CALdtscadinv" style="cursor: pointer; border: 1px solid red;" title="Date selector"
	  							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
							<script type="text/javascript">
							    Calendar.setup({
							        inputField     :    "dtscadinv",     // id of the input field
							        ifFormat       :    "%d/%m/%Y",      // format of the input field
							        button         :    "CALdtscadinv",  // trigger for the calendar (button ID)
								    align          :    "Tl",           // alignment (defaults to "Bl")
								    singleClick    :    true
							    });
							</script>
						<% } %>								
					</td>
				</tr>
				
				<tr>
					<th><label for="">Data Invito</label></th>
					<td>
						<input tabindex="<%=++indiceTab%>" style="text-align:center" <%=disabled%> onchange="setFormModified('Modificato0')" onblur="Calendar.validaData(this)" type="text" id="dtinvito" name="<%= PSBD.FIELD_NAME_DATA_INVITO %>" value="<%= dataInvito%>">
						<% if(disabled.equals("")) {%>
							<img  <%=disabled%> src="calendar/img.gif" id="CALdtinvito" style="cursor: pointer; border: 1px solid red;" title="Date selector"
	  							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
							<script type="text/javascript">
							    Calendar.setup({
							        inputField     :    "dtinvito",     // id of the input field
							        ifFormat       :    "%d/%m/%Y",      // format of the input field
							        button         :    "CALdtinvito",  // trigger for the calendar (button ID)
								    align          :    "Tl",           // alignment (defaults to "Bl")
								    singleClick    :    true
							    });
							</script>
						<% } %>							
					</td>
				</tr>
				
				
				<tr>
					<th><label for="">Data di scadenza per la presentazione delle offerte</label></th>
					<td>
						<input tabindex="<%=++indiceTab%>" style="text-align:center" <%=disabled%> onchange="setFormModified('Modificato0')" onblur="Calendar.validaData(this)" type="text" id="dtscadoff" name="<%= PSBD.DATA_SCADENZA_PRES_OFFERTA %>" value="<%= dataScadenzaPresOfferta%>">
						<% if(disabled.equals("")) {%>
							<img  <%=disabled%> src="calendar/img.gif" id="CALdtscadoff" style="cursor: pointer; border: 1px solid red;" title="Date selector"
	  							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
							<script type="text/javascript">
							    Calendar.setup({
							        inputField     :    "dtscadoff",     // id of the input field
							        ifFormat       :    "%d/%m/%Y",      // format of the input field
							        button         :    "CALdtscadoff",  // trigger for the calendar (button ID)
								    align          :    "Tl",           // alignment (defaults to "Bl")
								    singleClick    :    true
							    });
							</script>
						<% } %>
					</td>
				</tr>
				
			  <tr>
					<th><label for="">n°. Soggetti che hanno presentato manifestazione di interesse</label></th>
					<td>
						<input maxlength="9" style="text-align:right;" onblur="validateNumber(this)" tabindex="<%=++indiceTab%>" <%=disabled%> type="text"  name="<%= PSBD.FIELD_NAME_NUM_MANIF_INTERESSE %>" value="<%= numManifInteresse%>" onchange="setFormModified('Modificato0')">
					</td>
				</tr>
				
				<tr>
					<th><label for="">n. Soggetti che hanno presentato richiesta di invito</label></th>
					<td>
						<input maxlength="9" style="text-align:right;" onblur="validateNumber(this)" tabindex="<%=++indiceTab%>" <%=disabled%> type="text" name="<%= PSBD.FIELD_NAME_NUM_IMPRESE_RICHIEDENTI %>" maxlength="5" value="<%= numImpreseRichiedenti%>" onchange="setFormModified('Modificato0')">
					</td>
				</tr>
				<tr>
					<th><label for="">n°. Soggetti invitati a presentare offerta</label></th>
					<td>
						<input maxlength="9" style="text-align:right;" onblur="validateNumber(this)" tabindex="<%=++indiceTab%>" <%=disabled%> type="text" name="<%= PSBD.FIELD_NAME_NUM_IMPRESE_INVITATE %>" maxlength="5" value="<%= numImpreseInvitate%>" onchange="setFormModified('Modificato0')">
					</td>
				</tr>
				<tr>
					<th><label for="">n° Soggetti che hanno presentato offerta</label></th>
					<td>
						<input maxlength="9" style="text-align:right;" onblur="validateNumber(this)" tabindex="<%=++indiceTab%>" <%=disabled%> type="text" name="<%= PSBD.FIELD_NAME_NUM_IMPRESE_OFFERENTI %>" maxlength="5" value="<%= numImpreseOfferenti%>" onchange="setFormModified('Modificato0')">
					</td>
				</tr>
				<tr>
					<th><label for="">n° offerte ammesse</label></th>
					<td>
						<input maxlength="9" style="text-align:right;" onblur="validateNumber(this)" tabindex="<%=++indiceTab%>" <%=disabled%> type="text" name="<%= PSBD.FIELD_NAME_NUM_OFFERTE_AMMESSE %>" maxlength="5" value="<%= numOfferteAmmesse%>" onchange="setFormModified('Modificato0')">
					</td>
				</tr>
				
				<!-- 
			
				<tr>
					<th><label for="< %= PSBD.FIELD_NAME_VERIFICA_REQUISITI %>">Verifica a campione dei requisiti</label></th>
					<td>
					< % String checkedRequisitiSi = ""; %>
					< % String checkedRequisitiNo = "";%>
					< % if(verificaRequisiti!=""){ %>
					< % 		if("N".equalsIgnoreCase(verificaRequisiti)) {checkedRequisitiNo = "checked";} %>
					< %		if("S".equalsIgnoreCase(verificaRequisiti)) {checkedRequisitiSi = "checked";} %>
					< % } %>
						SI<input < %=disabled%> tabindex="< %=++indiceTab%>" type="radio" name ="< %= PSBD.FIELD_NAME_VERIFICA_REQUISITI %>" < %= checkedRequisitiSi %> value="S" onchange="setFormModified('Modificato0')">
						NO<input < %=disabled%> tabindex="< %=++indiceTab%>" type="radio" name ="< %= PSBD.FIELD_NAME_VERIFICA_REQUISITI %>" < %= checkedRequisitiNo %> value="N" onchange="setFormModified('Modificato0')">
					</td>
				</tr>
			
				<tr>
					<th><label for="">Numero imprese escluse in seguito a verifica</label></th>
					<td>
						<input maxlength="9" tabindex="< %=++indiceTab%>" < %=disabled%> type="text" name="< %= PSBD.FIELD_NAME_NUM_IMP_ESCLUSE_DOPO_VERIFICA %>" maxlength="5" value="< %= numImpEscluseDopoVerifica%>">
					</td>
				</tr>
			
				<tr>
					<th><label for="">Data verbale di aggiudicazione</label></th>
					<td>
						<input onchange="setFormModified('Modificato0')" readonly type="text" id="dtverbagg" name="< %= PSBD.DATA_VERBALE_AGGIUDICAZIONE %>" value="< %= dataVerbaleAggiudicazione%>">
						< % if(disabled.equals("")) {%>
							<img tabindex="< %=++indiceTab%>" < %=disabled%> src="calendar/img.gif" id="CALdtverbagg" style="cursor: pointer; border: 1px solid red;" title="Date selector"
	  							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
							<script type="text/javascript">
							    Calendar.setup({
							        inputField     :    "dtverbagg",     // id of the input field
							        ifFormat       :    "%d/%m/%Y",      // format of the input field
							        button         :    "CALdtverbagg",  // trigger for the calendar (button ID)
								    align          :    "Tl",           // alignment (defaults to "Bl")
								    singleClick    :    true
							    });
							</script>
						< % } %>
					</td>
				</tr>
			-->
			
			
			
			<tr>
				<th><label>Offerta di massimo ribasso</label></th>
				<td>
					<input tabindex="<%=++indiceTab%>" onblur="validatePercentage(this)" style="text-align: right" type="text" name="<%= PSBD.FIELD_NAME_OFFERTA_MASSIMO_RIBASSO %>" size="6" maxlength="6" value="<%= OFFERTA_MASSIMO %>" <%=disabled%> onchange="setFormModified('Modificato2')">%
				</td>
			</tr>

			<tr>
				<th><label>Offerta di minimo ribasso</label></th>
				<td >
					<input tabindex="<%=++indiceTab%>" onblur="validatePercentage(this)" style="text-align: right" type="text" name="<%= PSBD.FIELD_NAME_OFFERTA_MINIMO_RIBASSO %>" size="6" maxlength="6" value="<%= OFFERTA_MINIMA %>" <%=disabled%> onchange="setFormModified('Modificato2')" >%
				</td>
			</tr>

			<tr>
				<th><label>Valore soglia anomalia</label></th>
				<td>
					<input tabindex="<%=++indiceTab%>" onblur="validatePercentage(this)" style="text-align: right" type="text" name="<%= PSBD.FIELD_NAME_VALORE_SOGLIA_ANOMALIA %>" size="6" maxlength="6" value="<%= VAL_SOGLIA_ANOMALIA %>" <%=disabled%> onchange="setFormModified('Modificato2')" >%
				</td>
			</tr>
			
			<tr>
				<th><label>N.offerte >= soglia anomalia</label></th>
				<td>
					<input maxlength="9" tabindex="<%=++indiceTab%>" onblur="validateNumber(this)" style="text-align: right" type="text" name="<%= PSBD.FIELD_NAME_NUM_OFFERTE_MAG_SOGLIA %>" maxlength="1024" value="<%= NUM_OFFERTE_FUORI_SOGLIA %>" <%=disabled%> onchange="setFormModified('Modificato2')">
				</td>
			</tr>

		
			
			<tr>
				<th><label>Numero offerte escluse automaticamente</label></th>
				<td>
					<input maxlength="9" tabindex="<%=++indiceTab%>" onblur="validateNumber(this)" style="text-align: right" type="text" name="<%= PSBD.FIELD_NAME_NUM_OFFERTE_ESCLUS_AUTOMATICA %>" maxlength="1024" value="<%= NUM_OFFERTE_ESCLUSE %>" <%=disabled%> onchange="setFormModified('Modificato2')">
				</td>
			</tr>

			

		</table>  						
	</fieldset> 
	<input type="hidden" id="Modificato2" name="Modificato2" value="<%=modificato2 %>">