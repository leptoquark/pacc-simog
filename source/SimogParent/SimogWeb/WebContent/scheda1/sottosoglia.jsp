<%@page import="java.math.BigDecimal"%>
<%@ page import="it.avlp.simog.db.SimogFlags" %>
<%@page import="it.avlp.simog.common.servlet.ParametriCup"%>
<%@page import="it.avlp.simog.beans.InfoGaraBean"%>
<%@ page import="it.avlp.simog.util.SimogProperties"%>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- TB: ticket popup modali. Import css e js -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<div id="dialog"></div>
<!-- fine import popup modali -->

<% int indiceTab = 0; %>
<c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>
<input type="hidden" name="<%=PSBD.FIELD_NAME_PRG_CUI_RIAGG %>" value="${aggiudicazione.progCuiRiaggiudicato}" />
<fieldset class="gara">
	<br>
	<table width="100%" ${variazioniAnagrafiche eq true ? 'style="display:none;"' : ''}>
	 <%@include file="/include/intestazione.jsp" %>
	<tr>
	  	<td align="center" colspan="2"><p class="detailHelp"><strong><utils:message key="scheda.oggettoAppalto" /></strong></p></td>
	</tr>
	<input type="hidden" id="canSearch" value="false" />	
	<tr>
		<th><label for=""><utils:message key="scheda.codiceLuogoEsecuzioneISTAT" /></label></th>
		<td >
			<input onchange="setFormModified('Modificato0')" 
				   maxlength="9" 
				   tabindex="<%=++indiceTab%>" ${readonlyStr} 
				   type="text" 
				   name="<%= PSBD.FIELD_NAME_LUOGO_ISTAT %>" 
				   value="<c:out value="${aggiudicazione.luogoIstat}" />" 
				   id="sel_ISTAT" onblur="searchLE(this.id, 'ricercaIstat.jsp', 'isNotIstat')" 
				   onkeyup="checkKeyLE(event, this, 'ricercaIstat.jsp', 'isNotIstat')"
			/>
			<c:if test="${readonly ne true}">
				<a class="getCPV" href="#"  onclick="apripopup('ricercaIstat.jsp','sel_ISTAT')" title="<utils:message key="scheda.listaCodiciISTAT" />"><img src="img/icon_info_sml.gif"></a>
			</c:if>
		</td>
	</tr>		
	<tr>
		<th><label for=""><utils:message key="scheda.codiceLuogoEsecuzioneNUTS" /></label></th>
		
		<td>
			<input onchange="setFormModified('Modificato0')" maxlength="12" tabindex="<%=++indiceTab%>" ${readonlyStr} 
			type="text" name="<%= PSBD.FIELD_NAME_LUOGO_NUTS %>" 
			value="<c:out value="${aggiudicazione.luogoNuts}" />" 
			id="sel_NUTS" onblur="searchLE(this.id, 'ricercaNuts.jsp', 'isNotNuts')" 
		       	onkeyup="checkKeyLE(event, this, 'ricercaNuts.jsp', 'isNotNuts')" />
			<c:if test="${readonly ne true}">
				<a class="getCPV" href="#"  onclick="apripopup('ricercaNuts.jsp','sel_NUTS')" 
						title="<utils:message key="scheda.listaCodiciNUTS" />"><img src="img/icon_info_sml.gif"></a>
			</c:if>
		</td>
	</tr>			
	<% InfoGaraBean datiGara = (InfoGaraBean)session.getAttribute("dati_gara"); 
	boolean competenzaLotto = SimogProperties.getInstance().isCUPLotto(PageHelper.getFormattedDBDate(datiGara.getDataCreazioneGara()));
	%>
	<c:set var="competenzaLotto" value="<%=competenzaLotto %>"  scope="request"/>
	<c:set var="competenzaLotto" value="${competenzaLotto || readonly}"  scope="request"/>
	<% if( SimogFlags.is3031_RFWEBGL02Active() 
	        && SimogProperties.getInstance().isCUPAttivo()){ %>
			<c:set var="readonlyCup" value="${competenzaLotto}" scope="request" />	
			<c:set var="readonlyCupStr" value="${readonlyCup eq true ?  'readonly' : ''} " scope="request" />
			<c:set var="disabledonlyCupStr" value="${readonlyCup eq true ?  'disabled' : ''} " scope="request" />
<%-- 			<c:set var="readonly" value="${readonly || competenzaLotto ? true : false}" scope="request" /> --%>
	<tr>
	<td colspan="2">
		<h5>CUP</h5>
	</td>
	</tr>			
	<tr>
	<th align="left" ><label for="<%= ParametriCup.FIELD_FLAG_CUP %>">
L'appalto � finalizzato alla realizzazione di progetti d'investimento pubblico
per i quali � prevista l'acquisizione del codice CUP ai sensi dell'art. 11 L 3/2003
e ss.mm.? (E' necessario acquisire e comunicare il CUP per interventi finanziati, 
anche in parte, con risorse Comunitarie)</label></th>
	<td>
	<c:set var="selFlagCup" value="${schedaSottosoglia.flagCUP}"></c:set>
	<select name="<%= ParametriCup.FIELD_FLAG_CUP %>" CLASS="BOTTONE"  ${disabledStr} ${disabledonlyCupStr}>
		<option value=""></option>
 			<option value="N" <c:out value="${selFlagCup =='N' ? 'selected' : ''}" />>NO</option>
 			<option value="S" <c:out value="${selFlagCup =='S' ? 'selected' : ''}" />>SI</option>
 		</select> 
	</td>
	</tr>	       
	<c:if test="${readonly eq true || readonlyCup eq true}" >
		<input type="hidden" name="<%= ParametriCup.FIELD_FLAG_CUP  %>" value="${schedaSottosoglia.flagCUP}" />
	</c:if>
	        
	<tr>
	<td colspan="2">
		<div class="inthead" style="padding-bottom: 15px">
			<label onclick="showMenu('<%= ParametriCup.TAB_CUP %>')" 
					style="color:black; letter-spacing:0.2em; cursor:pointer;">
					<img src="img/minus.gif" id="img<%= ParametriCup.TAB_CUP %>"/> <utils:message key="scheda.codiciCUP" /> </label>
			<div id="<%= ParametriCup.TAB_CUP %>"  style="display: block;" ><br />
				<c:if test="${not empty schedaSottosoglia.elencoCup}">
					<c:set var="elencoCup" value="${schedaSottosoglia.elencoCup}" scope="request" />
				</c:if>
				<jsp:include page="/include/elencoCUP.jsp" />
			</div>
		</div>
	</td>
	</tr>
	
	<% } else { %>
	<tr>
		<th ><label for="">Codice CUP</label></th>
		
		<td  width="40%" >
			<input maxlength="15" 
					onchange="setFormModified('Modificato0')" 
					tabindex="<%=++indiceTab%>"  
					${readonlyStr} 
					type="text" name="<%= PSBD.FIELD_NAME_CUP %>" 
					value="<c:out value="${aggiudicazione.cup}" />">
		</td>
	</tr>
<%} %>
	<tr>
		<td align="center" colspan="2"><p class="detailHelp"><strong><utils:message key="scheda.datiEconomiciAppalto" /></strong></p></td>
	</tr> 
	<tr>
		<th><label for="ImpCompAppalto" 
			title="Importo dell'appalto al netto di eventuali oneri per la sicurezza o altre somme non soggette a ribasso">${markFieldAVCPass} Importo soggetto a ribasso</label></th>
		<td>
			<input onchange="setFormModified('Modificato0')" 
					tabindex="<%=++indiceTab%>" ${readonlyStr} ${readonlyAVCPass}  
					type="text" style="text-align:right;width:100px;" 
					id="<%= PSBD.FIELD_NAME_IMPORTO_COMPLESSIVO %>" 
					name="<%= PSBD.FIELD_NAME_IMPORTO_COMPLESSIVO %>" 
					value="<c:out value="${aggiudicazione.importoComplessivoStr}" />" 
					onblur="validateAmount(this);valutaSubTotaleSottoEsclusi()" />
		</td>
	</tr>
	
		<!-- PP 3.02.1.6 -->
<% // PP 3.02.1.6	
if (SimogFlags.is30216Active()){ %>	
	<tr>
		<th><label for="<%= PSBD.FIELD_NAME_IMPORTO_ATTUAZIONE_SICUREZZA %>">Importo per l'attuazione della sicurezza e altre somme non soggette a ribasso</label></th>
		<td>
			<input onchange="setFormModified('Modificato0')"   ${readonlyStr}
					tabindex="<%=++indiceTab%>"  
					type="text" style="text-align:right;width:100px;" 
					id="<%= PSBD.FIELD_NAME_IMPORTO_ATTUAZIONE_SICUREZZA %>" 
					name="<%= PSBD.FIELD_NAME_IMPORTO_ATTUAZIONE_SICUREZZA %>" 
					value="<c:out value="${aggiudicazione.importoAttuazioneSicurezzaStr}" />" 
					onblur="validateAmount(this);valutaSubTotaleSottoEsclusi()" />
		</td>
	</tr>
<% } %>	

	<tr>
		<th><label for="<%= PSBD.FIELD_NAME_IMPORTO_DISPOSIZIONE %>">Importo totale somme a disposizione</label></th>
		<td>
			<input onchange="setFormModified('Modificato0')" 
					tabindex="<%=++indiceTab%>" ${readonlyStr} 
					type="text" style="text-align:right;width:100px;" 
					id="<%= PSBD.FIELD_NAME_IMPORTO_DISPOSIZIONE %>" 
					name="<%= PSBD.FIELD_NAME_IMPORTO_DISPOSIZIONE %>" 
					value="<c:out value="${aggiudicazione.importoDisposizioneStr}" />" 
					onblur="validateAmount(this);valutaSubTotaleSottoEsclusi()" />
		</td>
	</tr>  
	<tr>
		<th><label for="ImpCompIntervento">Importo complessivo dell'intervento</label></th>
		<td>
	     <c:set var="impComp" value="${aggiudicazione.importoComplessivo + aggiudicazione.importoDisposizione + aggiudicazione.importoAttuazioneSicurezza}" ></c:set>	
		  <% // adds 19052008 
		  String subTot_3 = "";
		  if(pageContext.getAttribute("impComp") != null && pageContext.getAttribute("impComp") instanceof BigDecimal){
			subTot_3 = PageHelper.formattaImporto((BigDecimal)pageContext.getAttribute("impComp"));
		  }
		  %>	
			<input tabindex="<%=++indiceTab%>" disabled type="text" id="ImpCompIntervento" name="ImpCompIntervento" value="<%=subTot_3 %>" style="text-align:right;font-weight: bold;width:100px;"/>
		</td>
	</tr>
	<tr>
		<td align="center" colspan="2"><p class="detailHelp"><strong><utils:message key="scheda.datiProceduraliAppalto" /></strong></p></td>
	</tr>								
	<tr>
	 	<c:set var="idSceltaContr" value="${aggiudicazione.idSceltaContraente}" scope="request" />
			<c:if test="${aggiudicazione.idSceltaContraente == 0}">
				<c:set var="idSceltaContr" value="${datiGara.idSceltaContraente}" scope="request" />
			</c:if>		
		<th><label for="<%= ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE %>">Procedura di scelta contraente</label></th>
		<td>
		<select onchange="setFormModified('Modificato0')" tabindex="<%=++indiceTab%>" style="width: 100%" ${disabledStr}  name="<%= ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE %>" id="sel_CONTRAENTE" CLASS="BOTTONE">
			<option></option>
			<u:options name="<%= ParametriServlet.SCELTA_CONTRAENTE_BEAN %>" scope="request" value="idSceltaContr"/>
		</select>
		<c:if test="${readonly eq true}" >
				<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE %>" value="${aggiudicazione.idSceltaContraente}" />
		</c:if>
		</td>
	 </tr>		
	<tr>
		<th><label for="<%= PSBD.FIELD_NAME_ASTA_ELETTRONICA %>">Ricorso all'asta elettronica</label></th>
		<td>
			<u:selectBooleanRadio name="<%= PSBD.FIELD_NAME_ASTA_ELETTRONICA %>" 
		   			 value="${aggiudicazione.astaElettronica}" trueId="<%= PSBD.S_FIELD_NAME_ASTA_ELETTRONICA %>" 
		  			  falseId="<%= PSBD.N_FIELD_NAME_ASTA_ELETTRONICA %>"  readonly="${readonly}" 
		  			  tabindex="<%=++indiceTab%>" onchange="setFormModified('Modificato0')" />
		   			 <%indiceTab++; %>
		</td>
	</tr>	
	<tr>
		<td colspan="2">
			<div class="inthead">	
				<label onclick="showMenuNoCheck('checkBoxList')" style="color:black; letter-spacing:0.2em; cursor:pointer;">
					<img src="img/minus.gif" id="imgcheckBoxList"/>
					Condizioni che giustificano il ricorso alla procedura negoziata senza previa pubblicazione di un bando oppure senza previa indizione di una gara
				</label>
				<div id="checkBoxList" class="mbody" style="display: block;">
					
					<table width="80%" class="detailHelp">
						<colgroup>
							<col width="60%"/>
							<col width="40%"/>
	  						</colgroup>
	  						<c:set var="condizioni" value="${schedaSottosoglia.condizioni}" scope="request" /> 
	  						<u:multibox campo="<%=PSBD.FIELD_NAME_CONDIZIONI_AGG %>" lista="<%= ParametriServlet.CONDIZIONI_AGG_BEAN %>" 
	  						listaCampiSelezionati="condizioni" readonly="${readonly}" idField="idCondizione" onchange="setFormModified('Modificato4')" />								
					</table>
				</div>
			</div>
		</td>
	</tr>
	<c:set var="includerConfirmed" value="${aggiudicazione.confirmed}" scope="page"></c:set> <!-- Per affidatari e responsabili... -->

</table>
<table ${variazioniAnagrafiche eq true ? '' : 'style="display:none;"'}>
	<tr>
		<th><label for="<%= PSBD.FIELD_NAME_MOTIVO_CO %>">Motivazione della variazione anagrafica</label></th>
		<td>
			<select onchange="setFormModified('Modificato0')" tabindex="<%=++indiceTab%>" 
					style="width:100%" 
					name="<%= PSBD.FIELD_NAME_MOTIVO_CO %>" 
					id=<%= PSBD.FIELD_NAME_MOTIVO_CO %> CLASS="BOTTONE">
				<option></option>
			  	<c:set var="idMotivoVarCO" value="${aggiudicazione.idMotivoVarCO}" scope="request" />
			  	<u:options name="<%= ParametriServlet.MOTIVO_VCO_BEAN %>" scope="request" value="idMotivoVarCO"/>
			</select>
		</td>
	</tr>
</table>

<table>
	<tr>
		<td colspan="2">
			<div class="inthead">
				<label onclick="showMenu('<%= PSBD.TAB_AFFIDATARIO %>')" style="color:black; letter-spacing:0.2em; cursor:pointer;">
					<img src="img/minus.gif" id="img<%= PSBD.TAB_AFFIDATARIO %>"/> AGGIUDICAZIONE / AFFIDAMENTO</label>
				<div id="<%= PSBD.TAB_AFFIDATARIO %>" style="display: block;" >
				<c:set var="aggiudicatari" value="${schedaSottosoglia.aggiudicatari}" scope="page"></c:set>
				
				<%@ include file="/scheda1/affidatarioSotto.jsp" %>
				</div>	
			</div>					
		</td>
	</tr>					
	<tr>
		<td colspan="2">
			<div class="inthead">
				<label onclick="showMenu('<%= PSBD.TAB_RESPONSABILE_PROCEDIMENTO %>')" style="color:black; letter-spacing:0.2em; cursor:pointer;">
					<img src="img/minus.gif" id="img<%= PSBD.TAB_RESPONSABILE_PROCEDIMENTO %>"/> ANAGRAFICA E RIFERIMENTI DEI SOGGETTI AI QUALI LA STAZIONE APPALTANTE HA CONFERITO INCARICHI</label>
				<div id="<%= PSBD.TAB_RESPONSABILE_PROCEDIMENTO %>"  style="display: block;" >
					<c:set var="responsabili" value="${schedaSottosoglia.responsabili}" scope="page"></c:set>
					
					<%@ include file="/scheda1/responsabile.jsp" %>
				</div>
			</div>
		</td>
	</tr>	
	</table>  			 
</fieldset>	
<input type="hidden" name="<%= PSBD.FIELD_NAME_CODICE_CONTRATTO %>" id="<%= PSBD.FIELD_NAME_CODICE_CONTRATTO %>" value="${aggiudicazione.codiceContratto}" />
<input type="hidden" name="<%= PSBD.FIELD_NAME_FLAG_AGGIUD_PRINCIPALE %>" id="<%= PSBD.FIELD_NAME_FLAG_AGGIUD_PRINCIPALE %>" value="${aggiudicazione.flagAggiudPrincipale}" />
	 
<input type="hidden" id="Modificato0" name ="Modificato0" value="<c:out value="${param['modificato0']}" />">
<input type="hidden" id="Modificato4" name ="Modificato4" value="<c:out value="${param['modificato4']}" />">
<input type="hidden" id="Modificato5" name ="Modificato5" value="<c:out value="${param['modificato5']}" />">