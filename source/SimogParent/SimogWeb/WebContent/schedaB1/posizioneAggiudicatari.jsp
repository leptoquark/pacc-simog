<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletRubrica"%>
<%@page import="it.avlp.simog.util.PageHelper"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletInizioLavori"%>
<%@page import="it.avlp.simog.db.Costanti"%>
<!-- fieldset class="detailHelp" style="border: 2px solid #cfcfcf;"-->

<c:set var="readonlyAffid" value="${roByFlusso eq true or (inizioLavori.confirmed and variazioniAnagrafiche ne true ) }" />
<c:set var="readonlyAffidStr" value="${readonlyAffid ? 'readonly' : ''} " />

<% String prefixAgg = ParametriServletInizioLavori.POSIZIONE_AGGIUDICATARIO; %>
<c:set var="prefixAgg" value="<%= prefixAgg %>" scope="page" />
<script type="text/javascript">
<!--
// La funzione controlla che si sia inserito un valore nella combo-box
// altrimenti visualizza un messagio di alert. 
// Al momento questa funzione non risulta utilizzata
function contrInserimento(element) {
	if (element.selectedIndex == 0 ) {
		alert("Attenzione selezionare dal menu un codice fiscale aggiudicatario ");
	}  	
}

// Vengono prelevati i valori dalla select ed inseriti negli elementi così da essere presenti nella request
// LUCA : viene effettuato inoltre il parse del value che contiene idSoggetto e dataInizioSoggetto che dovranno
// essere inseriti negli opportuni campi hidden per poter poi essere recuperati all'interno della request.

function copiaDenom(index) {

	var parseArray = document.getElementById("<%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI%>").options[index].value.split("~");
	var denominazione = parseArray[0];
	var codice_paese = parseArray[3]
	var idSoggetto = parseArray[1];
	var dataInizioSoggetto = parseArray[2];
		
	document.getElementById("<%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>").value = denominazione;
	if(codice_paese == "" || codice_paese == null) codice_paese = "<%= Costanti.CODICE_STATO_ITALIANO %>"; 
	document.getElementById("<%= PSBD.FIELD_NAME_AGG_ID_PAESE %>").value = codice_paese;
	
	var id = "<%= PSBD.FIELD_NAME_AGG_ID_SOGG_POSIZIONI %>";
	document.getElementById(id).value = idSoggetto;
	
	id = "<%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG_POSIZIONI %>";
	document.getElementById(id).value = dataInizioSoggetto;
	  

document.getElementById("selected<%= prefixAgg %>").value 
	= document.getElementById("<%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI%>").options[index].text;
}
//-->
</script>	
		
		<div id="DIVTabella<%= prefixAgg %>" class="scrollTabs" style="height: 200px; width:99%">
			<table id="idTabella<%= prefixAgg %>">
				<tbody>
					<tr>
						<th width="125">Azione</th>
						<th class="garaTh">Denominazione</th>
						<th class="garaTh">Codice Fiscale</th>							
						<th class="garaTh">Codice Paese</th>
						<th class="garaTh">Codice INPS</th>
						<th class="garaTh">Codice INAIL</th>  
						<th class="garaTh">Codice Cassa Edile</th>
					</tr>
					<c:set var="counter" value="0" /> 
					<c:forEach var="aggCorrente" items="${aggiudicatari}">
						<c:set var="id" value="row${prefixAgg}${counter}" scope="page"/>
						<tr id="<c:out value="${id}" />">
							<c:set var="soggPartecipante" value="${aggCorrente.soggettoPartecipante}" />
							
							<c:if test="${readonlyAffid ne true}">
								<td nowrap="nowrap" class="hmenu">
									<a title="Modifica Aggiudicatario" href="javascript:setForModifyRow('<c:out value="${id}" />',[<%= ParametriServletInizioLavori.argsPos %>],[<%=ParametriServletInizioLavori.argsPosNascosti%>],'<%=prefixAgg%>')">Modifica</a>
									&nbsp;<a title="Cancella <%= prefixAgg %>" href="javascript:deleteRow('<c:out value="${id}" />',[<%= ParametriServletInizioLavori.argsPos %>],[<%=ParametriServletInizioLavori.argsPosNascosti%>],'<%=prefixAgg%>')" <%= prefixAgg %>">Cancella</a></td>											
							</c:if>
							<c:if test="${readonlyAffid eq true}">
								<td>&nbsp;</td>
							</c:if>
							 
							<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>"><c:out value="${soggPartecipante.denominazione}" /></td>																
							<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI %>"><c:out value="${soggPartecipante.codiceFiscale}" /></td>
							<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_PAESE%>">
							<c:if test="${soggPartecipante.id_stato != null && soggPartecipante.id_stato != ''}">
								<c:out value="${soggPartecipante.id_stato}"/>
							</c:if>
							<c:if test="${soggPartecipante.id_stato == null || soggPartecipante.id_stato == ''}">
								<%= Costanti.CODICE_STATO_ITALIANO %>
							</c:if>
							</td>
							<td nowrap class="garaTd" id="<c:out value="${id}" /><%= ParametriServletInizioLavori.FIELD_NAME_CODICE_INPS %>"><c:out value="${aggCorrente.codiceINPS}" /></td>
							<td nowrap class="garaTd" id="<c:out value="${id}" /><%= ParametriServletInizioLavori.FIELD_NAME_CODICE_INAIL %>"><c:out value="${aggCorrente.codiceINAIL}" /></td>
							<td nowrap class="garaTd" id="<c:out value="${id}" /><%= ParametriServletInizioLavori.FIELD_NAME_CODICE_CASSA %>"><c:out value="${aggCorrente.codiceCassa}" /></td>
							
							<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_SOGG_POSIZIONI %>" style="display: none;"><c:out value="${soggPartecipante.idSoggettoPartecipante}" /></td>
							<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG_POSIZIONI %>" style="display: none;"><c:out value="${soggPartecipante.dataInizioSogg}" /></td>
							
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_SOGG_POSIZIONI %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_SOGG_POSIZIONI %>" value="<c:out value="${soggPartecipante.idSoggettoPartecipante}" />"></td>
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG_POSIZIONI %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG_POSIZIONI %>" value="<c:out value="${soggPartecipante.dataInizioSogg}" />"></td>										
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>" value="<c:out value="${soggPartecipante.denominazione}" />"></td>
							
						    <td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_PAESE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_PAESE %>" value="<c:out value="${soggPartecipante.id_stato}" />"></td>
						    	
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI %>" value="<c:out value="${soggPartecipante.codiceFiscale}" />"></td>
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= ParametriServletInizioLavori.FIELD_NAME_CODICE_INPS %>" name="<c:out value="${id}" /><%= ParametriServletInizioLavori.FIELD_NAME_CODICE_INPS %>" value="<c:out value="${aggCorrente.codiceINPS}" />"></td>
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= ParametriServletInizioLavori.FIELD_NAME_CODICE_INAIL %>" name="<c:out value="${id}" /><%= ParametriServletInizioLavori.FIELD_NAME_CODICE_INAIL %>" value="<c:out value="${aggCorrente.codiceINAIL}" />"></td>
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= ParametriServletInizioLavori.FIELD_NAME_CODICE_CASSA %>" name="<c:out value="${id}" /><%= ParametriServletInizioLavori.FIELD_NAME_CODICE_CASSA %>" value="<c:out value="${aggCorrente.codiceCassa}" />"></td>
																								
						</tr>
						<c:set var="counter" value="${counter + 1}" scope="page"/>
					</c:forEach>																
				</tbody>
			</table>
		</div>	  		
		
		<c:if test="${readonlyAffid ne true}">
			<div class="hmenu"><a id="showHide<%= prefixAgg %>Button" title="Aggiungi <%= prefixAgg %>" href="javascript:showSezioneAggiungi([<%= ParametriServletInizioLavori.argsPos %>],[<%=ParametriServletInizioLavori.argsPosNascosti%>],'<%=prefixAgg%>')" title="Aggiungi <%= prefixAgg %>">Aggiungi Posizione</a></div>
		</c:if>
		<div class="detailHelp" id="divAgg<%= prefixAgg %>" style="display: none; border: 1px solid #cfcfcf;"> 
			<table width="100%">
				<tr>
					<th><label for="">Codice fiscale Aggiudicatario</label></th>
					<td>
						<select id="<%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI%>" CLASS="BOTTONE" onchange="setFormModified('Modificato<%=prefixAgg%>');copiaDenom(this.selectedIndex);" >
							<option value="~~~ ~"></option>
							<u:options name="<%= ParametriServletInizioLavori.ATTRIB_AGGIUDICATARI %>" scope="request" value="<%= PSBD.FIELD_NAME_COD_FISC_POSIZIONI %>"/>
						</select>
						<input disabled type="text" id="<%= PSBD.FIELD_NAME_AGG_ID_PAESE%>" maxlength ="2" size=2 value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>					
				</tr>
				<tr>
					<th><label>Denominazione</label></th>
					<td>
						<input disabled type="text" id="<%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
					
				</tr>
				<tr style="display: none;">
					<th><label>Aggiudicatario</label></th>
					<td>
						<input type="text" disabled id="<%= PSBD.FIELD_NAME_DESCRIZIONE %>" maxlength="16" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>					
				</tr>
					
				<tr>
					<th><label>Codice INPS</label></th>
					<td>
						<input type="text" id="<%= ParametriServletInizioLavori.FIELD_NAME_CODICE_INPS %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" maxlength="16"/>
					</td>
				</tr>
				<tr>
					<th><label>Codice INAIL</label></th>
					<td>
						<input type="text" id="<%= ParametriServletInizioLavori.FIELD_NAME_CODICE_INAIL %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" maxlength="16"/>
					</td>
				</tr>
				<tr>
					<th><label>Codice Cassa</label></th>
					<td>
						<input type="text" id="<%= ParametriServletInizioLavori.FIELD_NAME_CODICE_CASSA %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" maxlength="16"/>
					</td>
				</tr>
				<tr style="display: none;">
					<td>
						<input type="text" id="<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
				</tr>
				<tr style="display: none;">
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_AGG_ID_SOGG_POSIZIONI %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
				</tr>
				<tr style="display: none;">	
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG_POSIZIONI %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
				</tr>
				<tr><td class="hmenu"><a id="AddMod<%= prefixAgg %>" href="javascript:addRow([<%= ParametriServletInizioLavori.argsPos %>],[<%=ParametriServletInizioLavori.argsPosNascosti%>],'<%=prefixAgg%>')">Aggiungi</a></td></tr>
			</table>
			<%
			boolean bool = true;
			int i = 0;
			while(bool){
				if((String)request.getAttribute("idPaese"+i) != null){
				%><input type="hidden" id="idPaese<%=i %>" name="idPaese<%=i %>" value="<%=(String)request.getAttribute("idPaese"+i) %>"><%
				i++;
				}else{bool = false;}
				
			} %>
			
			<input type="hidden" id="Modificato<%= prefixAgg %>" name ="Modificato<%= prefixAgg %>" value="0">			
		</div>		
		
</fieldset> 
<input type="hidden" id="selected<%= prefixAgg %>" value="0" /> 
<input type="hidden" id="Modificato3" name ="Modificato3" value="<c:out value="${param['modificato3']}" />" />
