<% int indiceTab = 0; %>
<%
String idAvanz =(String)( (request.getAttribute(STATI_AVANZ.ID_AVANZAMENTO) != null)? request.getAttribute(STATI_AVANZ.ID_AVANZAMENTO) : "" ); 
 String dataInizioAvanz =(String)( (request.getAttribute(STATI_AVANZ.DATA_INIZIO_AVANZAMENTO) != null)? request.getAttribute(STATI_AVANZ.DATA_INIZIO_AVANZAMENTO) : "" ); 
 	String FASE = (statoAvanzamento.getFullSize()>0)? rowStatoAvanzamento.getNulledField(STATI_AVANZ.FASE):(String)request.getAttribute(STATI_AVANZ.FASE); 
 	String faseString = null;
	String DATA_RITARDO = statoAvanzamento.getFullSize()>0? rowStatoAvanzamento.getNulledField(STATI_AVANZ.DATA_RAGGIUNGIMENTO) : "";
	String FLAG_RITARDO = statoAvanzamento.getFullSize()>0? rowStatoAvanzamento.getNulledField(STATI_AVANZ.FLAG_RITARDO) : "";
	String FLAG_PAGAMENTO = statoAvanzamento.getFullSize()>0? rowStatoAvanzamento.getNulledField(STATI_AVANZ.FLAG_PAGAMENTO) : "";
	String NUM_GIORNI_SCOST = statoAvanzamento.getFullSize()>0? rowStatoAvanzamento.getNulledField(STATI_AVANZ.NUM_GIORNI_SCOST) : "";
	String IMPORTO_VARIANTI = statoAvanzamento.getFullSize()>0? PageHelper.formattaImporto(rowStatoAvanzamento.getNulledField(STATI_AVANZ.IMPORTO_VARIANTI)) : "0,000";
	String NUM_GIORNI_SOSP = statoAvanzamento.getFullSize()>0? rowStatoAvanzamento.getNulledField(STATI_AVANZ.NUM_GIORNI_SOSP) : "";
	String ALTRE_MOTIVAZIONI = statoAvanzamento.getFullSize()>0? rowStatoAvanzamento.getNulledField(STATI_AVANZ.ALTRE_MOTIVAZIONI) : "";
	
%>
<%@ taglib uri="/WEB-INF/tlds/tagutils.tld" prefix="utils" %>
<%@page import="it.avlp.simog.db.generated.STATI_AVANZ"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletSchedaB2"%>
<%@page import="it.avlp.simog.util.PageHelper"%>
<%@page import="it.avlp.simog.db.generated.MOTIVI_VARIANTE"%>
<%@page import="it.avlp.simog.db.generated.MOTIVI_SOSPENSIONE"%>

<%@page import="it.avlp.simog.db.generated.ESEC_MOTIVI_VARIANTI"%>
<input type="hidden" name="<%=STATI_AVANZ.ID_AVANZAMENTO %>" 
	id="<%= STATI_AVANZ.ID_AVANZAMENTO %>" 
	value="<%= idAvanz %>">
	    
<input type="hidden" name="<%=STATI_AVANZ.DATA_INIZIO_AVANZAMENTO %>" 
	id="<%= STATI_AVANZ.DATA_INIZIO_AVANZAMENTO %>" 
	value="<%= dataInizioAvanz %>">
<fieldset class="gara">
	<table width="100%">
		<tr>
			<td align="center" colspan="2"><p class="detailHelp"><strong>RIFERIMENTO AI DATI DELLA FASE DI AGGIUDICAZIONE O DI DEFINIZIONE DI PROCEDURA NEGOZIATA</strong></p></td>			
		</tr>
		<tr>
	   		<td><label for="<%= ParametriServlet.FIELD_NAME_CIG %>" >Codice di individuazione dell'intervento (CIG)</label></td>
   			<td  width="40%" > <%= (infoEnteContratto!=null)? infoEnteContratto.getNulledField(LOTTO.CIG, 0): "" %><%= (infoEnteContratto!=null)? infoEnteContratto.getNulledField(LOTTO.CIG_KKK, 0): "" %></td>
	   	</tr>
	   	<tr>
			<td align="center" colspan="2"><p class="detailHelp"><strong>AVANZAMENTO DELLA PRESTAZIONE CONTRATTUALE</strong></p></td>
		</tr>
		<% if(FASE.equals("1")) faseString = "25%";
			else if(FASE.equals("2")) faseString = "50%";
			else if(FASE.equals("3")) faseString = "75%";
			else if(FASE.equals("4")) faseString = "100%";%>
		<tr>
			<th><label>Percentuale di avanzamento raggiunta</label></th>
			<td ><label style="font-weight: bolder;font-size: large"><%= faseString %></label></td>
			<input type="hidden" name="<%= STATI_AVANZ.FASE %>" value="<%= FASE %>"/>
		</tr>	
		<tr>
			<th><label>Data in cui è stato raggiunto l'avanzamento indicato al punto precedente</label></th>
	    	<td>
				<input tabindex="<%=++indiceTab%>" <%= disabilitato %> onchange="setFormModified('Modificato0')" style="text-align:center;" type="text" id="<%= STATI_AVANZ.DATA_RAGGIUNGIMENTO %>" name="<%= STATI_AVANZ.DATA_RAGGIUNGIMENTO %>" onblur="Calendar.validaData(this)" value="<%= PageHelper.getFormattedDate(DATA_RITARDO) %>"/>
				<% if(!confermato) {%>	
				<img  src="calendar/img.gif" id="calendar<%= STATI_AVANZ.DATA_RAGGIUNGIMENTO %>" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				<script type="text/javascript">
					    Calendar.setup({
					        inputField     :    "<%= STATI_AVANZ.DATA_RAGGIUNGIMENTO %>",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendar<%= STATI_AVANZ.DATA_RAGGIUNGIMENTO %>",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true								        
				    });
				</script>
				<% } %>	
			</td>
		</tr>
		<tr>
			<th><label for="<%= STATI_AVANZ.FLAG_RITARDO %>">L'avanzamento raggiunto, rispetto al cronoprogramma di previsione, registra:</label></th>
			<td>
				<% String FLAG_RITARDOSi = ""; %>
				<% String FLAG_RITARDONo = "";%>
				<% if("A".equalsIgnoreCase(FLAG_RITARDO)) {FLAG_RITARDONo = "checked";} %>
				<% if("R".equalsIgnoreCase(FLAG_RITARDO)) {FLAG_RITARDOSi = "checked";} %>
				Ritardo<input tabindex="<%=++indiceTab%>" type="radio" id ="<%= ParametriServletSchedaB2.SI_FLAG_RITARDO %>" name ="<%= STATI_AVANZ.FLAG_RITARDO %>" <%= FLAG_RITARDOSi %> value="R" <%= disabilitato %> onchange="setFormModified('Modificato0')">
				Anticipo<input tabindex="<%=++indiceTab%>" type="radio" id ="<%= ParametriServletSchedaB2.NO_FLAG_RITARDO %>" name ="<%= STATI_AVANZ.FLAG_RITARDO %>" <%= FLAG_RITARDONo %> value="A" <%= disabilitato %> onchange="setFormModified('Modificato0')">
			</td>
		</tr>
		<tr>
			<th><label>Indicare lo scostamento registrato in numero di giorni</label></th>
			<td>
				<input onchange="setFormModified('Modificato0')" maxlength="9" tabindex="<%=++indiceTab%>" onblur="validateNumber(this)" type="text" name="<%= STATI_AVANZ.NUM_GIORNI_SCOST %>" maxlength="1024" value="<%= NUM_GIORNI_SCOST %>" <%= disabilitato %> style="text-align:right;"/>
			</td>
		</tr>							
		<tr>
			<td colspan="2">
				<div class="inthead">
					<label onclick="showMenu('<%= ParametriServletSchedaB2.TAB_CERTIFICATI_PAGAMENTO %>')" style="color:black; letter-spacing:0.2em;">
						<img src="img/minus.gif" id="img<%= ParametriServletSchedaB2.TAB_CERTIFICATI_PAGAMENTO %>"/> CERTIFICATI DI PAGAMENTO GIA' EMESSI ALLA DATA DI RAGGIUNGIMENTO DELL'AVANZAMENTO INDICATO</label>
					<div id="<%= ParametriServletSchedaB2.TAB_CERTIFICATI_PAGAMENTO %>" style="display: block; border: 2px solid #cfcfcf;">
						<%@ include file="certificatiPagamento.inc" %> 
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2"><p class="detailHelp"><strong>SOSPENSIONI DELL'ESECUZIONE / VARIANTI IN CORSO DI ESECUZIONE</strong></p></td>
		</tr>
		<tr>
			<th><label for="<%= STATI_AVANZ.NUM_GIORNI_SOSP %>">Se ci sono state sospensioni dell'esecuzione nel periodo di avanzamento considerato, indicare il n. complessivo dei giorni</label></th>
			<td>
				<input onchange="setFormModified('Modificato0')" maxlength="9" tabindex="<%=++indiceTab%>" onblur="validateNumber(this)" type="text" name="<%= STATI_AVANZ.NUM_GIORNI_SOSP %>" maxlength="1024" value="<%= NUM_GIORNI_SOSP %>" <%= disabilitato %> style="text-align:right;">
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div class="inthead">   
					<label onclick="showMenu('<%= ParametriServletSchedaB2.CHECKBOX_LISTA_MOTIVAZIONI_SOSPENSIONE %>')" style="color:black; letter-spacing:0.2em;">
						<img src="img/minus.gif" id="img<%= ParametriServletSchedaB2.CHECKBOX_LISTA_MOTIVAZIONI_SOSPENSIONE %>"/> Motivazione/i della/e sospensione/i</label>
					<div id="<%= ParametriServletSchedaB2.CHECKBOX_LISTA_MOTIVAZIONI_SOSPENSIONE %>" style="display: block; border: 2px solid #cfcfcf;">
						<table width="80%" class="detailHelp">
								<colgroup>
									<col width="60%"/>
									<col width="40%"/>
     							</colgroup>
								<utils:createCheckGroup campoId="<%=MOTIVI_SOSPENSIONE.ID_MOTIVO_SOSP%>" campoVisualizzato="<%=MOTIVI_SOSPENSIONE.DESCRIZIONE %>" 
									checkedList="<%=selMotivazioniSospensione %>" listaCheckBox="<%=motivazioniSospensione %>" 
									fieldName="<%=ESEC_MOTIVI_SOSP.ID_MOTIVO_SOSP %>" disabled="<%=disabled %>" onchange="setFormModified('Modificato3')"/>
						</table>
						<input type="hidden" id="Modificato3" name="Modificato3" value="<%= modificato3 %>"/>
					</div>
				</div>
			</td>
		</tr>   
		<tr>
			<th><label for="<%= STATI_AVANZ.IMPORTO_VARIANTI %>">Se ci sono state varianti in corso di esecuzione nel periodo di avanzamento considerato, indicare l'importo complessivo delle stesse</label></th>
			<td>
				<input onchange="setFormModified('Modificato0')" tabindex="<%=++indiceTab%>" onblur="validateAmount(this)" type="text" name="<%= STATI_AVANZ.IMPORTO_VARIANTI %>" maxlength="1024" value="<%= IMPORTO_VARIANTI %>" <%= disabilitato %> style="text-align:right;">
			</td>
		</tr>
		<utils:visualizzaCampo mostraCon="LOMO" tipoScheda="<%= contrattoEnte %>">
			<tr>
				<td colspan="2">
					<div class="inthead">   
						<label onclick="showMenu('<%= ParametriServletSchedaB2.CHECKBOX_LISTA_MOTIVAZIONI_VARIANTE %>')" style="color:black; letter-spacing:0.2em;">
							<img src="img/minus.gif" id="img<%= ParametriServletSchedaB2.CHECKBOX_LISTA_MOTIVAZIONI_VARIANTE %>"/> Motivazione/i della/e modifica/he contrattuale/i</label>
						<div id="<%= ParametriServletSchedaB2.CHECKBOX_LISTA_MOTIVAZIONI_VARIANTE %>" style="display: block; border: 2px solid #cfcfcf;">
							<table width="80%" class="detailHelp">
								<colgroup>
									<col width="60%"/>
									<col width="40%"/>
     							</colgroup>
								<utils:createCheckGroup campoId="<%=MOTIVI_VARIANTE.ID_MOTIVO_VAR%>" campoVisualizzato="<%=MOTIVI_VARIANTE.DESCRIZIONE %>" 
										checkedList="<%=selMotivazioniVariante %>" listaCheckBox="<%=motivazioniVariante %>" 
										fieldName="<%= ESEC_MOTIVI_VARIANTI.ID_MOTIVO_VAR %>" disabled="<%=disabled %>" onchange="setFormModified('Modificato4')"/>
							</table>									
						</div>
					</div>
				</td>
			</tr>
		</utils:visualizzaCampo>
		<input type="hidden" id="Modificato4" name="Modificato4" value="<%= modificato4 %>"/>
		<utils:visualizzaCampo mostraCon="SOFO" tipoScheda="<%= contrattoEnte %>">
			<tr>
				<th><label>Indicarne le motivazioni distintamente separate da un ";"</label></th>
				<td>
					<textarea <%= disabilitato %> tabindex="<%=++indiceTab%>" rows="5" cols="30" id="<%= STATI_AVANZ.ALTRE_MOTIVAZIONI %>" name="<%= STATI_AVANZ.ALTRE_MOTIVAZIONI %>" onchange="setFormModified('Modificato0')"><%= ALTRE_MOTIVAZIONI %></textarea>
				</td>
			</tr>
		</utils:visualizzaCampo>
		<utils:visualizzaCampo mostraCon="MO" tipoScheda="<%= contrattoEnte %>">
			<tr>
				<th><label>Cause della modifica contrattuale</label></th>
				<td>
					<textarea <%= disabilitato %> tabindex="<%=++indiceTab%>" rows="5" cols="30" id="<%= STATI_AVANZ.ALTRE_MOTIVAZIONI %>" name="<%= STATI_AVANZ.ALTRE_MOTIVAZIONI %>" onchange="setFormModified('Modificato0')"><%= ALTRE_MOTIVAZIONI %></textarea>
				</td>
			</tr>
		</utils:visualizzaCampo>
		<tr>
			<td colspan="2">
				<div class="inthead">   
					<label onclick="showMenu('<%= ParametriServletSchedaB2.TAB_ATTI_AGGIUNTIVI %>')" style="color:black; letter-spacing:0.2em;">
						<img src="img/minus.gif" id="img<%= ParametriServletSchedaB2.TAB_ATTI_AGGIUNTIVI %>"/> ATTI AGGIUNTIVI / ACCORDI BONARI</label>
					<div id="<%= ParametriServletSchedaB2.TAB_ATTI_AGGIUNTIVI %>" style="display: block; border: 2px solid #cfcfcf;">
						<%@ include file="attiAggiuntivi.inc" %> 
					</div>
				</div>
			</td>
		</tr>
	</table>	
</fieldset>			
		