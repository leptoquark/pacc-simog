<%@page import="it.avlp.simog.db.generated.FINE_LAVORI"%>
<% int indiceTab = 0; %>
<%
	String idUltimazione =(String)( (request.getAttribute(FINE_LAVORI.ID_ULTIM) != null)? request.getAttribute(FINE_LAVORI.ID_ULTIM) : "" ); 
	String dataInizioUltimazione =(String)( (request.getAttribute(FINE_LAVORI.DATA_INIZIO_ULTIM) != null)? request.getAttribute(FINE_LAVORI.DATA_INIZIO_ULTIM) : "" ); 
	String DATA_ULTIMAZIONE = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.DATA_ULTIMAZIONE): "";
	String IMPORTO_FINALE = (fineLavori.getFullSize()>0)? PageHelper.formattaImporto(rowFineLavori.getNulledField(FINE_LAVORI.IMPORTO_FINALE)): "0,000";
	String NUM_GIORNI_SOSP = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.NUM_GIORNI_SOSP): "0";
	String NUM_GIORNI_PRO = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.NUM_GIORNI_PRO): "0";
	String NUM_VARIANTI = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.NUM_VARIANTI): "0";
	String IMPORTO_VARIANTI = (fineLavori.getFullSize()>0)? PageHelper.formattaImporto(rowFineLavori.getNulledField(FINE_LAVORI.IMPORTO_VARIANTI)): "0,000";
	String NUM_ACCORDI = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.NUM_ACCORDI): "0";
	String IMPORTO_ACCORDI = (fineLavori.getFullSize()>0)? PageHelper.formattaImporto(rowFineLavori.getNulledField(FINE_LAVORI.IMPORTO_ACCORDI)): "0,000";
	String NUM_RIS_BONARIE = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.NUM_RIS_BONARIE): "0";
	String IMP_RISERVE_BONARIE = (fineLavori.getFullSize()>0)? PageHelper.formattaImporto(rowFineLavori.getNulledField(FINE_LAVORI.IMP_RISERVE_BONARIE)): "0,000";
	String NUM_RIS_ARBITRARIE = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.NUM_RIS_ARBITRARIE): "0";
	String IMP_RISERVE_ARBITRARIE = (fineLavori.getFullSize()>0)? PageHelper.formattaImporto(rowFineLavori.getNulledField(FINE_LAVORI.IMP_RISERVE_ARBITRARIE)): "0,000";
	String NUM_RIS_GIUDIZIALE = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.NUM_RIS_GIUDIZIALE): "0";
	String IMP_RISERVE_GIUDIZIALE = (fineLavori.getFullSize()>0)? PageHelper.formattaImporto(rowFineLavori.getNulledField(FINE_LAVORI.IMP_RISERVE_GIUDIZIALE)): "0,000";
	String FLAG_RIS_ANTIC = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.FLAG_RIS_ANTIC): "";
	String FLAG_DANNO = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.FLAG_DANNO): "";
	String RISOL_RESC = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.RISOL_RESC): "0";
	String DATA_RISOLUZIONE = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.DATA_RISOLUZIONE): "";
	String ONERI_RISOLUZIONE = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.ONERI_RISOLUZIONE): "0";
	String FLAG_POLIZZA = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.FLAG_POLIZZA): "";
	String FLAG_SUBAPPALTI = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.FLAG_SUBAPPALTI): "";
	String ONERI_CORRISPOSTI = (fineLavori.getFullSize()>0)? PageHelper.formattaImporto(rowFineLavori.getNulledField(FINE_LAVORI.ONERI_CORRISPOSTI)): "0,000";
	String DANNO_ECONOMICO = (fineLavori.getFullSize()>0)? PageHelper.formattaImporto(rowFineLavori.getNulledField(FINE_LAVORI.DANNO_ECONOMICO)): "0,000";
	String DATA_COLLAUDO_STAT = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.DATA_COLLAUDO_STAT): "";
	String MODO_COLLAUDO = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.MODO_COLLAUDO): "";
	String DATA_NOMINA_COLL = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.DATA_NOMINA_COLL): "";
	String DATA_INIZIO_COLL = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.DATA_INIZIO_COLL): "";
	String DATA_CERT_COLLAUDO = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.DATA_CERT_COLLAUDO): "";
	String ESITO_COLLAUDO = (fineLavori.getFullSize()>0)? rowFineLavori.getNulledField(FINE_LAVORI.ESITO_COLLAUDO): "";
	  
	
%>

<%@ taglib uri="/WEB-INF/tlds/tagutils.tld" prefix="utils" %>
<%@page import="it.avlp.simog.common.servlet.ParametriServletSchedaB4"%>
<input type="hidden" name="<%=FINE_LAVORI.ID_ULTIM %>" 
	id="<%= FINE_LAVORI.ID_ULTIM %>" 
	value="<%= idUltimazione %>">
	
<input type="hidden" name="<%=FINE_LAVORI.DATA_INIZIO_ULTIM %>" 
	id="<%= FINE_LAVORI.DATA_INIZIO_ULTIM %>" 
	value="<%= dataInizioUltimazione %>">
	
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
			<td colspan="2">
				<div class="inthead">
					<label onclick="showMenu('<%= ParametriServletSchedaB4.TAB_SUBAPPALTI %>')" style="color:black; letter-spacing:0.2em;">
						<img src="img/minus.gif" id="img<%= ParametriServletSchedaB4.TAB_SUBAPPALTI %>"/> SUBAPPALTI</label>
					<div id="<%= ParametriServletSchedaB4.TAB_SUBAPPALTI %>" style="display: block; border: 2px solid #cfcfcf;">
						<%@ include file="subappalti.inc" %> 
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2"><p class="detailHelp"><strong>ULTIMAZIONE <%= TIPO_STRING %></strong></p></td>
		</tr>
		<tr>
			<th><label>Data verbale ultimazione <%= SUB_TIPO_STRING %></label></th>
	    	<td>
				<input tabindex="<%=++indiceTab%>" onchange="setFormModified('Modificato0')" style="text-align:center;" type="text" id="<%= FINE_LAVORI.DATA_ULTIMAZIONE %>" name="<%= FINE_LAVORI.DATA_ULTIMAZIONE %>" onblur="Calendar.validaData(this)" value="<%= PageHelper.getFormattedDate(DATA_ULTIMAZIONE) %>">
				<% if(!confermato) {%>	
				<img  src="calendar/img.gif" id="calendar<%= FINE_LAVORI.DATA_ULTIMAZIONE %>" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				<script type="text/javascript">
					    Calendar.setup({
					        inputField     :    "<%= FINE_LAVORI.DATA_ULTIMAZIONE %>",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendar<%= FINE_LAVORI.DATA_ULTIMAZIONE %>",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true								        
				    });
				</script>
				<% } %>	
			</td>
		</tr>
		<tr>
			<th><label for="<%= FINE_LAVORI.IMPORTO_FINALE %>">Importo finale contabilizzato</label></th>
			<td>
				<input onchange="setFormModified('Modificato0')" tabindex="<%=++indiceTab%>" onblur="validateAmount(this)" type="text" name="<%= FINE_LAVORI.IMPORTO_FINALE %>" maxlength="1024" value="<%= IMPORTO_FINALE %>" <%= disabilitato %> style="text-align:right;">
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2"><p class="detailHelp"><strong>SOSPENSIONI DELL'ESECUZIONE / CONCESSIONI DI PROROGHE/VARIANTI/ACCORDI BONARI</strong></p></td>
		</tr>
		<tr>
			<th><label for="<%= FINE_LAVORI.NUM_GIORNI_SOSP %>">Numero complessivo di giorni di sospensione nell'intero corso d'esecuzione del contratto</label></th>
			<td>
				<input onchange="setFormModified('Modificato0')" maxlength="9" tabindex="<%=++indiceTab%>" onblur="validateNumber(this)" type="text" name="<%= FINE_LAVORI.NUM_GIORNI_SOSP %>" maxlength="1024" value="<%= NUM_GIORNI_SOSP %>" <%= disabilitato %> style="text-align:right;">
			</td>
		</tr>
		<tr>
			<th><label for="<%= FINE_LAVORI.NUM_GIORNI_PRO %>">Numero complessivo di giorni di proroga concessi, rispetto ai termini contrattuali </label></th>
			<td>
				<input onchange="setFormModified('Modificato0')" maxlength="9" tabindex="<%=++indiceTab%>" onblur="validateNumber(this)" type="text" name="<%= FINE_LAVORI.NUM_GIORNI_PRO %>" maxlength="1024" value="<%= NUM_GIORNI_PRO %>" <%= disabilitato %> style="text-align:right;">
			</td>
		</tr>
		<tr>
			<th><label for="<%= FINE_LAVORI.NUM_VARIANTI %>">Numero complessivo varianti</label></th>
			<td>
				<input onchange="setFormModified('Modificato0')" maxlength="9" tabindex="<%=++indiceTab%>" onblur="validateNumber(this)" type="text" name="<%= FINE_LAVORI.NUM_VARIANTI %>" maxlength="1024" value="<%= NUM_VARIANTI %>" <%= disabilitato %> style="text-align:right;">
			</td>
		</tr>
		<tr>
			<th><label for="<%= FINE_LAVORI.IMPORTO_VARIANTI %>">Importo complessivo riconosciuto per varianti</label></th>
			<td>
				<input onchange="setFormModified('Modificato0')" tabindex="<%=++indiceTab%>" onblur="validateAmount(this)" type="text" name="<%= FINE_LAVORI.IMPORTO_VARIANTI %>" maxlength="1024" value="<%= IMPORTO_VARIANTI %>" <%= disabilitato %> style="text-align:right;">
			</td>
		</tr>
		<tr>
			<th><label for="<%= FINE_LAVORI.NUM_ACCORDI %>">Numero complessivo accordi bonari sottoscritti</label></th>
			<td>
				<input onchange="setFormModified('Modificato0')" maxlength="9" tabindex="<%=++indiceTab%>" onblur="validateNumber(this)" type="text" name="<%= FINE_LAVORI.NUM_ACCORDI %>" maxlength="1024" value="<%= NUM_ACCORDI %>" <%= disabilitato %> style="text-align:right;">
			</td>
		</tr>
		<tr>
			<th><label for="<%= FINE_LAVORI.IMPORTO_ACCORDI %>">Importo complessivo derivante dalla sottoscrizione di accordi bonari</label></th>
			<td>
				<input onchange="setFormModified('Modificato0')" tabindex="<%=++indiceTab%>" onblur="validateAmount(this)" type="text" name="<%= FINE_LAVORI.IMPORTO_ACCORDI %>" maxlength="1024" value="<%= IMPORTO_ACCORDI %>" <%= disabilitato %> style="text-align:right;">
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2"><p class="detailHelp"><strong>RISERVE</strong></p></td>
		</tr>
		<tr class="detailHelp">
			<th rowspan="2"><label>Definite per via bonaria</label></th>
			<td>
				<input onchange="setFormModified('Modificato0')" maxlength="9" tabindex="<%=++indiceTab%>" onblur="validateNumber(this)" type="text" name="<%= FINE_LAVORI.NUM_RIS_BONARIE %>" maxlength="1024" value="<%= NUM_RIS_BONARIE %>" <%= disabilitato %> style="text-align:right;">Numero
			</td>
		</tr>
		<tr class="detailHelp">
			<td>
				<input onchange="setFormModified('Modificato0')" tabindex="<%=++indiceTab%>" onblur="validateAmount(this)" type="text" name="<%= FINE_LAVORI.IMP_RISERVE_BONARIE %>" maxlength="1024" value="<%= IMP_RISERVE_BONARIE %>" <%= disabilitato %> style="text-align:right;">Importo
			</td>
		</tr>
		<tr class="detailHelp">
			<th rowspan="2"><label>Definite in via arbitrale</label></th>
			<td>
				<input onchange="setFormModified('Modificato0')" maxlength="9" tabindex="<%=++indiceTab%>" onblur="validateNumber(this)" type="text" name="<%= FINE_LAVORI.NUM_RIS_ARBITRARIE %>" maxlength="1024" value="<%= NUM_RIS_ARBITRARIE %>" <%= disabilitato %> style="text-align:right;">Numero
			</td>
		</tr>
		<tr class="detailHelp">
			<td>
				<input onchange="setFormModified('Modificato0')" tabindex="<%=++indiceTab%>" onblur="validateAmount(this)" type="text" name="<%= FINE_LAVORI.IMP_RISERVE_ARBITRARIE%>" maxlength="1024" value="<%= IMP_RISERVE_ARBITRARIE %>" <%= disabilitato %> style="text-align:right;">Importo
			</td>
		</tr>
		<tr class="detailHelp">
			<th rowspan="2"><label>Definite in via giudiziale</label></th>
			<td>
				<input onchange="setFormModified('Modificato0')" maxlength="9" tabindex="<%=++indiceTab%>" onblur="validateNumber(this)" type="text" name="<%= FINE_LAVORI.NUM_RIS_GIUDIZIALE %>" maxlength="1024" value="<%= NUM_RIS_GIUDIZIALE %>" <%= disabilitato %> style="text-align:right;">Numero
			</td>
		</tr>
		<tr class="detailHelp">
			<td>
				<input onchange="setFormModified('Modificato0')" tabindex="<%=++indiceTab%>" onblur="validateAmount(this)" type="text" name="<%= FINE_LAVORI.IMP_RISERVE_GIUDIZIALE%>" maxlength="1024" value="<%= IMP_RISERVE_GIUDIZIALE %>" <%= disabilitato %> style="text-align:right;">Importo
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2"><p class="detailHelp"><strong>RISOLUZIONI / RESCISSIONI CONTRATTUALI </strong></p></td>
		</tr>
		<% String checkedFLAG_RIS_ANTIC = ""; if("S".equals(FLAG_RIS_ANTIC)) checkedFLAG_RIS_ANTIC = "checked";  %>
		<tr>
			<th><label>Risoluzione anticipata consensuale</label></th>
			<td>
				<input tabindex="<%=++indiceTab%>" <%= disabilitato %> <%= checkedFLAG_RIS_ANTIC %> type="checkbox" name ="<%= FINE_LAVORI.FLAG_RIS_ANTIC %>" id ="<%= FINE_LAVORI.FLAG_RIS_ANTIC %>"  value="S"  onchange="setFormModified('Modificato0')">
			</td>
		</tr>
		<% String checkedFLAG_DANNO = ""; if("S".equals(FLAG_DANNO)) checkedFLAG_DANNO = "checked";  %>
		<tr>
			<th><label>Rescissione in danno</label></th>
			<td>
				<input tabindex="<%=++indiceTab%>" <%= disabilitato %> <%= checkedFLAG_DANNO %> type="checkbox" id ="<%= FINE_LAVORI.FLAG_DANNO %>" name="<%= FINE_LAVORI.FLAG_DANNO %>" value="S"  onchange="setFormModified('Modificato0')">
			</td>
		</tr>
		<% int maskFrode = 1,maskNegligenza = 2,maskInadempimento = 4,maskRitardo = 8; %>
		<% String checkedFrode = ((Integer.parseInt(RISOL_RESC) & maskFrode) == maskFrode)? "checked": ""; %>
		<% String checkedNegligenza = ((Integer.parseInt(RISOL_RESC) & maskNegligenza) == maskNegligenza)? "checked": ""; %>
		<% String checkedInadempimento = ((Integer.parseInt(RISOL_RESC) & maskInadempimento) == maskInadempimento)? "checked": ""; %>
		<% String checkedRitardo = ((Integer.parseInt(RISOL_RESC) & maskRitardo) == maskRitardo)? "checked": ""; %>
		
		<tr class="detailHelp">
			<th rowspan="4"><label>Motivo della rescissione</label></th>
			<td>
				<input tabindex="<%=++indiceTab%>" <%= disabilitato %> <%= checkedFrode %> type="checkbox" id ="<%= ParametriServletSchedaB4.FRODE %>" value="1"  onchange="setBitWise('<%= FINE_LAVORI.RISOL_RESC %>',this);setFormModified('Modificato0')">Per frode
			</td>
		</tr>
		<tr class="detailHelp">
			<td>			
				<input tabindex="<%=++indiceTab%>" <%= disabilitato %> <%= checkedNegligenza %> type="checkbox" id ="<%= ParametriServletSchedaB4.GRAVE_NEGLIGENZA %>" value="2"  onchange="setBitWise('<%= FINE_LAVORI.RISOL_RESC %>',this);setFormModified('Modificato0')">Per grave negligenza
			</td>
		</tr>
		<tr class="detailHelp">
			<td>			
				<input tabindex="<%=++indiceTab%>" <%= disabilitato %> <%= checkedInadempimento %> type="checkbox" id ="<%= ParametriServletSchedaB4.INADEMPIMENTO_CONTRATTUALE %>" value="4"  onchange="setBitWise('<%= FINE_LAVORI.RISOL_RESC %>',this);setFormModified('Modificato0')">Per inadempimento contrattuale
			</td>
		</tr>
		<tr class="detailHelp">
			<td>			
				<input tabindex="<%=++indiceTab%>" <%= disabilitato %> <%= checkedRitardo %> type="checkbox" id ="<%= ParametriServletSchedaB4.GRAVE_RITARDO %>" value="8"  onchange="setBitWise('<%= FINE_LAVORI.RISOL_RESC %>',this);setFormModified('Modificato0')">Per grave ritardo
			</td>
		</tr>
		<input type="hidden" id="<%= FINE_LAVORI.RISOL_RESC %>" name="<%= FINE_LAVORI.RISOL_RESC %>" value="<%= RISOL_RESC %>"/>
		<tr>
			<th><label>Data risoluzione / rescissione</label></th>
	    	<td>
				<input tabindex="<%=++indiceTab%>"  onchange="setFormModified('Modificato0')" style="text-align:center;" type="text" id="<%= FINE_LAVORI.DATA_RISOLUZIONE %>" name="<%= FINE_LAVORI.DATA_RISOLUZIONE %>" onblur="Calendar.validaData(this)" value="<%= PageHelper.getFormattedDate(DATA_RISOLUZIONE) %>">
				<% if(!confermato) {%>	
				<img  src="calendar/img.gif" id="calendar<%= FINE_LAVORI.DATA_RISOLUZIONE %>" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				<script type="text/javascript">
					    Calendar.setup({
					        inputField     :    "<%= FINE_LAVORI.DATA_RISOLUZIONE %>",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendar<%= FINE_LAVORI.DATA_RISOLUZIONE %>",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true								        
				    });
				</script>
				<% } %>	
			</td>
		</tr>
		<% int maskNoOneri = 1,maskManacatoUtile = 2,maskContabilizzazione = 4,maskAddebito = 8; %>
		<% String checkedNoOneri = ((Integer.parseInt(ONERI_RISOLUZIONE) & maskNoOneri) == maskNoOneri)? "checked": ""; %>
		<% String checkedMancatoUtile = ((Integer.parseInt(ONERI_RISOLUZIONE) & maskManacatoUtile) == maskManacatoUtile)? "checked": ""; %>
		<% String checkedContabilizzazione = ((Integer.parseInt(ONERI_RISOLUZIONE) & maskContabilizzazione) == maskContabilizzazione)? "checked": ""; %>
		<% String checkedAddebito = ((Integer.parseInt(ONERI_RISOLUZIONE) & maskAddebito) == maskAddebito)? "checked": ""; %>
		<tr class="detailHelp">
			<th rowspan="4"><label>Oneri economici derivanti dalla risoluzione / rescissione</label></th>
			<td>
				<input tabindex="<%=++indiceTab%>" <%= disabilitato %> <%= checkedNoOneri %> type="checkbox" id ="<%= ParametriServletSchedaB4.SENZA_ONERI %>" value="1"  onchange="setBitWise('<%= FINE_LAVORI.ONERI_RISOLUZIONE %>',this);setFormModified('Modificato0')">Senza oneri
			</td>
		</tr>
		<tr class="detailHelp">
			<td>			
				<input tabindex="<%=++indiceTab%>" <%= disabilitato %> <%= checkedMancatoUtile %> type="checkbox" id ="<%= ParametriServletSchedaB4.MANCATO_UTILE %>" value="2"  onchange="setBitWise('<%= FINE_LAVORI.ONERI_RISOLUZIONE %>',this);setFormModified('Modificato0')">Riconoscimento del mancato utile
			</td>
		</tr>
		<tr class="detailHelp">
			<td>			
				<input tabindex="<%=++indiceTab%>" <%= disabilitato %> <%= checkedContabilizzazione %> type="checkbox" id ="<%= ParametriServletSchedaB4.AVANZAMENTO_CONSEGUITO %>" value="4"  onchange="setBitWise('<%= FINE_LAVORI.ONERI_RISOLUZIONE %>',this);setFormModified('Modificato0')">Contabilizzazione avanzamento conseguito
			</td>
		</tr>
		<tr class="detailHelp">
			<td>			
				<input tabindex="<%=++indiceTab%>" <%= disabilitato %> <%= checkedAddebito %> type="checkbox" id ="<%= ParametriServletSchedaB4.ADDEBITO_DANNO_APPALTATORE %>" value="8"  onchange="setBitWise('<%= FINE_LAVORI.ONERI_RISOLUZIONE %>',this);setFormModified('Modificato0')">Addebito del danno all'appaltatore
			</td>
		</tr>
		<input type="hidden" id="<%= FINE_LAVORI.ONERI_RISOLUZIONE %>" name="<%= FINE_LAVORI.ONERI_RISOLUZIONE %>" value = "<%= ONERI_RISOLUZIONE %>"/>
		<% String checkedPolizza = ""; if("S".equals(FLAG_POLIZZA)) checkedPolizza = "checked"; %>
		<tr>
			<th><label>Incamerata polizza</label></th>
			<td>
				<input tabindex="<%=++indiceTab%>" <%= disabilitato %> <%= checkedPolizza %> type="checkbox" id ="<%= FINE_LAVORI.FLAG_POLIZZA %>" name="<%= FINE_LAVORI.FLAG_POLIZZA %>" value="S"  onchange="setFormModified('Modificato0')">
			</td>
		</tr>
		<tr>
			<th><label>Oneri corrisposti</label></th>
			<td>
				<input onchange="setFormModified('Modificato0')" tabindex="<%=++indiceTab%>" onblur="validateAmount(this)" type="text" name="<%= FINE_LAVORI.ONERI_CORRISPOSTI%>" maxlength="1024" value="<%= ONERI_CORRISPOSTI %>" <%= disabilitato %> style="text-align:right;">
			</td>
		</tr>
		<tr>
			<th><label>Quantificazione del danno economico (eventuale)</label></th>
			<td>
				<input onchange="setFormModified('Modificato0')" tabindex="<%=++indiceTab%>" onblur="validateAmount(this)" type="text" name="<%= FINE_LAVORI.DANNO_ECONOMICO%>" maxlength="1024" value="<%= ONERI_CORRISPOSTI %>" <%= disabilitato %> style="text-align:right;">
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2"><p class="detailHelp"><strong>COLLAUDO</strong></p></td>
		</tr>
		<utils:visualizzaCampo mostraCon="SOFOMO" tipoScheda="<%= contrattoEnte %>">
		<tr>
			<th><label>Certificato di regolare esecuzione</label></th>
	    	<td>
				<input tabindex="<%=++indiceTab%>" onchange="setFormModified('Modificato0')" style="text-align:center;" type="text" id="<%= FINE_LAVORI.DATA_COLLAUDO_STAT %>" name="<%= FINE_LAVORI.DATA_COLLAUDO_STAT %>" onblur="Calendar.validaData(this)" value="<%= PageHelper.getFormattedDate(DATA_COLLAUDO_STAT) %>">
				<% if(!confermato) {%>	
				<img  src="calendar/img.gif" id="calendar<%= FINE_LAVORI.DATA_COLLAUDO_STAT %>" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				<script type="text/javascript">
					    Calendar.setup({
					        inputField     :    "<%= FINE_LAVORI.DATA_COLLAUDO_STAT %>",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendar<%= FINE_LAVORI.DATA_COLLAUDO_STAT %>",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true								        
				    });
				</script>
				<% } %>	
			</td>
		</tr>
		</utils:visualizzaCampo>
		<utils:visualizzaCampo mostraCon="LOMO" tipoScheda="<%= contrattoEnte %>">
		<tr>
			<th><label>Collaudo Statico (Ove ricorra)</label></th>
	    	<td>
				<input tabindex="<%=++indiceTab%>" onchange="setFormModified('Modificato0')" style="text-align:center;" type="text" id="<%= FINE_LAVORI.DATA_COLLAUDO_STAT %>" name="<%= FINE_LAVORI.DATA_COLLAUDO_STAT %>" onblur="Calendar.validaData(this)" value="<%= PageHelper.getFormattedDate(DATA_COLLAUDO_STAT) %>">
				<% if(!confermato) {%>	
				<img  src="calendar/img.gif" id="calendar<%= FINE_LAVORI.DATA_COLLAUDO_STAT %>" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				<script type="text/javascript">
					    Calendar.setup({
					        inputField     :    "<%= FINE_LAVORI.DATA_COLLAUDO_STAT %>",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendar<%= FINE_LAVORI.DATA_COLLAUDO_STAT %>",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true								        
				    });
				</script>
				<% } %>	
			</td>
		</tr>
		</utils:visualizzaCampo>
		<tr class="detailHelp">
			<th rowspan="2"><label for="<%= FINE_LAVORI.MODO_COLLAUDO %>">Modalità del Collaudo Tecnico Amministrativo</label></th>
			<td>   
				<% String MODO_COLLAUDOFINALE = ""; %>
				<% String MODO_COLLAUDOCORSO = "";%>
				<% if("2".equalsIgnoreCase(MODO_COLLAUDO)) {MODO_COLLAUDOCORSO = "checked";}
				   if("1".equalsIgnoreCase(MODO_COLLAUDO)) {MODO_COLLAUDOFINALE = "checked";} %>
				  <input tabindex="<%=++indiceTab%>" type="radio" id ="<%= ParametriServletSchedaB4.SI_MODO_COLLAUDO %>" name ="<%= FINE_LAVORI.MODO_COLLAUDO %>" <%= MODO_COLLAUDOFINALE %> value="1" <%= disabilitato %> onchange="setFormModified('Modificato0')">Collaudo Finale
				
			</td>
		</tr>
		<tr class="detailHelp">
			<td>
				<input tabindex="<%=++indiceTab%>" type="radio" id ="<%= ParametriServletSchedaB4.NO_MODO_COLLAUDO %>" name ="<%= FINE_LAVORI.MODO_COLLAUDO %>" <%= MODO_COLLAUDOCORSO %> value="2" <%= disabilitato %> onchange="setFormModified('Modificato0')">Collaudo in corso d'opera   
			</td>
		</tr>
		<tr>
			<th><label>Data nomina collaudatore/Commissione</label></th>
	    	<td>
				<input tabindex="<%=++indiceTab%>" onchange="setFormModified('Modificato0')" style="text-align:center;" type="text" id="<%= FINE_LAVORI.DATA_NOMINA_COLL %>" name="<%= FINE_LAVORI.DATA_NOMINA_COLL %>" onblur="Calendar.validaData(this)" value="<%= PageHelper.getFormattedDate(DATA_NOMINA_COLL) %>">
				<% if(!confermato) {%>	
				<img  src="calendar/img.gif" id="calendar<%= FINE_LAVORI.DATA_NOMINA_COLL %>" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				<script type="text/javascript">
					    Calendar.setup({
					        inputField     :    "<%= FINE_LAVORI.DATA_NOMINA_COLL %>",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendar<%= FINE_LAVORI.DATA_NOMINA_COLL %>",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true								        
				    });
				</script>
				<% } %>	
			</td>
		</tr>
		<tr>
			<th><label>Data inizio operazioni di collaudo</label></th>
	    	<td>
				<input tabindex="<%=++indiceTab%>" onchange="setFormModified('Modificato0')" style="text-align:center;" type="text" id="<%= FINE_LAVORI.DATA_INIZIO_COLL %>" name="<%= FINE_LAVORI.DATA_INIZIO_COLL %>" onblur="Calendar.validaData(this)" value="<%= PageHelper.getFormattedDate(DATA_INIZIO_COLL) %>">
				<% if(!confermato) {%>	
				<img  src="calendar/img.gif" id="calendar<%= FINE_LAVORI.DATA_INIZIO_COLL %>" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				<script type="text/javascript">
					    Calendar.setup({
					        inputField     :    "<%= FINE_LAVORI.DATA_INIZIO_COLL %>",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendar<%= FINE_LAVORI.DATA_INIZIO_COLL %>",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true								        
				    });
				</script>
				<% } %>	
			</td>
		</tr>
		<tr>
			<th><label>Data redazione certificato di collaudo</label></th>
	    	<td>
				<input tabindex="<%=++indiceTab%>" onchange="setFormModified('Modificato0')" style="text-align:center;" type="text" id="<%= FINE_LAVORI.DATA_CERT_COLLAUDO %>" name="<%= FINE_LAVORI.DATA_CERT_COLLAUDO %>" onblur="Calendar.validaData(this)" value="<%= PageHelper.getFormattedDate(DATA_CERT_COLLAUDO) %>">
				<% if(!confermato) {%>	
				<img  src="calendar/img.gif" id="calendar<%= FINE_LAVORI.DATA_CERT_COLLAUDO %>" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				<script type="text/javascript">
					    Calendar.setup({
					        inputField     :    "<%= FINE_LAVORI.DATA_CERT_COLLAUDO %>",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendar<%= FINE_LAVORI.DATA_CERT_COLLAUDO %>",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true								        
				    });
				</script>
				<% } %>	
			</td>
		</tr>
		<tr class="detailHelp">
			<th rowspan="2"><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"") %> for="<%= FINE_LAVORI.ESITO_COLLAUDO %>">Esito del Collaudo*</label></th>
			<td>
				<% String ESITO_COLLAUDO_SI = ""; %>
				<% String ESITO_COLLAUDO_NO = ""; %>
				<% if("N".equalsIgnoreCase(ESITO_COLLAUDO)) {ESITO_COLLAUDO_NO = "checked";} %>   
				<% if("S".equalsIgnoreCase(ESITO_COLLAUDO)) {ESITO_COLLAUDO_SI = "checked";} %>
				<input tabindex="<%=++indiceTab%>" type="radio" id ="<%= ParametriServletSchedaB4.SI_ESITO_COLLAUDO %>" name ="<%= FINE_LAVORI.ESITO_COLLAUDO %>" <%= ESITO_COLLAUDO_SI %> value="S" <%= disabilitato %> onchange="setFormModified('Modificato0')">Positivo					
			</td>
		</tr>
		<tr class="detailHelp">
			<td>
				<input tabindex="<%=++indiceTab%>" type="radio" id ="<%= ParametriServletSchedaB4.NO_ESITO_COLLAUDO %>" name ="<%= FINE_LAVORI.ESITO_COLLAUDO %>" <%= ESITO_COLLAUDO_NO %> value="N" <%= disabilitato %> onchange="setFormModified('Modificato0')">Negativo
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div class="inthead">
					<label onclick="showMenu('<%= ParametriServletSchedaB4.TAB_RESP_FINE %>')" style="color:black; letter-spacing:0.2em;">
						<img src="img/minus.gif" id="img<%= ParametriServletSchedaB4.TAB_RESP_FINE %>"/> ANAGRAFICA E RIFERIMENTI DI SOGGETTI AI QUALI LA STAZIONE APPALTANTE HA CONFERITO INCARICHI</label>
					<div id="<%= ParametriServletSchedaB4.TAB_RESP_FINE %>" style="display: block; border: 2px solid #cfcfcf;">
						<%@ include file="responsabiliFine.inc" %> 
					</div>
				</div>
			</td>
		</tr>
	</table>
</fieldset>		