<%@page import="it.avlp.simog.util.SimogProperties"%>
<%@page import="it.avlp.simog.beans.InfoGaraBean"%>
<%@page import="it.avlp.simog.db.SimogFlags"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="it.avlp.simog.common.servlet.ParametriCup"%>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- TB: ticket popup modali. Import css e js -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<div id="dialog"></div>
<!-- fine import popup modali -->
<% int iT = 0; %>
<c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>
<c:set var="isAccordoQuadro" value="${datiGara.ID_MODO_REAL eq 9 || datiGara.ID_MODO_REAL eq 17 || datiGara.ID_MODO_REAL eq 18}"></c:set>
<input type="hidden" name="<%=PSBD.FIELD_NAME_PRG_CUI_RIAGG %>" value="${aggiudicazione.progCuiRiaggiudicato}" />
<fieldset class="gara"><br>
	<table width="100%">
	 <%@include file="/include/intestazione.jsp" %>
<table width="100%" ${variazioniAnagrafiche eq true ? 'style="display:none;"' : ''}>
<tr><td align="center" colspan="2"><p class="detailHelp"><strong><utils:message key="scheda.oggettoAppalto" /></strong></p></td></tr>
	<input type="hidden" id="canSearch" value="false" />	
	<tr><th><label><utils:message key="scheda.codiceLuogoEsecuzioneISTAT" /></label></th><td >
	<input onblur="searchLE(this.id, 'ricercaIstat.jsp', 'isNotIstat')" 
		   onkeyup="checkKeyLE(event, this, 'ricercaIstat.jsp', 'isNotIstat')" onchange="setFormModified('Modificato0')" ${readonlyStr} maxlength="9" tabindex="<%=++iT%>" type="text" name="<%= PSBD.FIELD_NAME_LUOGO_ISTAT %>"  value="${aggiudicazione.luogoIstat}" id="sel_ISTAT" />
	<c:if test="${readonly ne true}">
		<a class="getCPV" href="#"  onclick="apripopup('ricercaIstat.jsp', 'sel_ISTAT')" title="<utils:message key="scheda.listaCodiciISTAT" />"><img src="img/icon_info_sml.gif"></a>
	</c:if>
</td></tr>		
<tr><th><label><utils:message key="scheda.codiceLuogoEsecuzioneNUTS" /></label></th>	
<td><input onblur="searchLE(this.id, 'ricercaNuts.jsp', 'isNotNuts')" onkeyup="checkKeyLE(event, this, 'ricercaNuts.jsp', 'isNotNuts')" onchange="setFormModified('Modificato0')" maxlength="12" tabindex="<%=++iT%>" ${readonlyStr} type="text" name="<%= PSBD.FIELD_NAME_LUOGO_NUTS %>" value="<c:out value="${aggiudicazione.luogoNuts}" />" id="sel_NUTS" />
<c:if test="${readonly ne true}">
	<a class="getCPV" href="#"  onclick="apripopup('ricercaNuts.jsp','sel_NUTS')" title="<utils:message key="scheda.listaCodiciNUTS" />"><img src="img/icon_info_sml.gif"></a>
</c:if>
</td></tr>

<% InfoGaraBean datiGara = (InfoGaraBean)session.getAttribute("dati_gara"); 
   boolean competenzaLotto = SimogProperties.getInstance().isCUPLotto(PageHelper.getFormattedDBDate(datiGara.getDataCreazioneGara()));
   String dataCreazione = (String)session.getAttribute("data_creazione");
   boolean is3042 = dataCreazione.compareTo(String.valueOf(SimogProperties.getInstance().getDataAttivazione3042())) < 0;
%>

 <% if(dataCreazione.compareTo(String.valueOf(SimogProperties.getInstance().getDataAttivazione3045())) >= 0) { %>	
<tr><th align="left" ><label for="RELAZIONE_UNICA">
Il sottoscritto dichiara che questa SA ha redatto la Relazione Unica sulle Procedure di Aggiudicazione degli Appalti e che la stessa � disponibile a richiesta*</label></th>
<td>
<c:set var="selFlagRelazioneUnica" value="${aggiudicazione.relazioneUnica}"></c:set>
<select name="RELAZIONE_UNICA" CLASS="BOTTONE"  ${disabledStr} >
	<option value=""></option>
 		<option value="N" <c:out value="${selFlagRelazioneUnica =='N' ? 'selected' : ''}" />>NO</option>
 		<option value="S" <c:out value="${selFlagRelazioneUnica =='S' ? 'selected' : ''}" />>SI</option>
 	</select> 
</td></tr>	       
<c:if test="${readonly eq true}" >
	<input type="hidden" name="RELAZIONE_UNICA" value="${aggiudicazione.relazioneUnica}" />
</c:if>
<% } %>

<c:set var="competenzaLotto" value="<%=competenzaLotto %>"  scope="request"/>
<c:set var="competenzaLotto" value="${competenzaLotto || readonly}"  scope="request"/>
<% if( SimogProperties.getInstance().isCUPAttivo()){ %>
	<c:set var="readonlyCup" value="${competenzaLotto}" scope="request" />	
	<c:set var="readonlyCupStr" value="${readonlyCup eq true ?  'readonly' : ''} " scope="request" />
	<c:set var="disabledonlyCupStr" value="${readonlyCup eq true ?  'disabled' : ''} " scope="request" />
<tr><td colspan="2"><h5>CUP</h5></td></tr>			
<tr><th align="left" ><label for="<%= ParametriCup.FIELD_FLAG_CUP %>">
L'appalto � finalizzato alla realizzazione di progetti d'investimento pubblico
per i quali � prevista l'acquisizione del codice CUP ai sensi dell'art. 11 L 3/2003
e ss.mm.? (E' necessario acquisire e comunicare il CUP per interventi finanziati, 
anche in parte, con risorse Comunitarie)</label></th>
<td>
<c:set var="selFlagCup" value="${schedaA.flagCUP}"></c:set>
<select name="<%= ParametriCup.FIELD_FLAG_CUP %>" CLASS="BOTTONE"  ${disabledStr} ${disabledonlyCupStr}>
	<option value=""></option>
 		<option value="N" <c:out value="${selFlagCup =='N' ? 'selected' : ''}" />>NO</option>
 		<option value="S" <c:out value="${selFlagCup =='S' ? 'selected' : ''}" />>SI</option>
 	</select> 
</td></tr>	       
<c:if test="${readonly eq true || readonlyCup eq true}" >
	<input type="hidden" name="FLAG_CUP" value="${schedaA.flagCUP}" />
</c:if>
<tr><td colspan="2">
<div class="inthead" style="padding-bottom: 15px">
	<label onclick="showMenu('<%= ParametriCup.TAB_CUP %>')" style="color:black; letter-spacing:0.2em; cursor:pointer;">
	<img src="img/minus.gif" id="img<%= ParametriCup.TAB_CUP %>"/> Codici CUP </label>
	<div id="<%= ParametriCup.TAB_CUP %>"  style="display: block;" ><br />
	<c:if test="${not empty schedaA.elencoCup}">
		<c:set var="elencoCup" value="${schedaA.elencoCup}" scope="request" />
	</c:if>
	<jsp:include page="/include/elencoCUP.jsp" />
	</div>
</div>
</td></tr>	
	<% } else { %>
<tr><th><label for="">Codice CUP</label></th>		
<td  width="40%" ><input maxlength="15" ${readonlyStr} onchange="setFormModified('Modificato0')" tabindex="<%=++iT%>"  type="text" name="<%= PSBD.FIELD_NAME_CUP %>"  value="${aggiudicazione.cup}"></td></tr>		
<% } %>
<tr><td colspan="2">
<div class="inthead">
<label onclick="showMenuNoCheck('checkBoxListL')" style="color:black; letter-spacing:0.2em;">
	<img src="img/minus.gif" id="imgcheckBoxListL"/> Tipologia lavoro <c:if test="${datiGara.tipoContratto == 'S' || datiGara.tipoContratto == 'F' }">(se presente la componente lavori)</c:if>
</label>
<div id="checkBoxListL" class="mbody" style="display: block;">
<table class="detailHelp">
	<colgroup><col width="60%"/><col width="40%"/></colgroup>
	<c:set var="compL" value="${schedaA.tipoLavoro}" scope="request" /> 
	<u:multibox  campo="<%=PSBD.FIELD_NAME_TIPO_APPALTO_AGG_L %>" lista="<%= ParametriServlet.TIPO_APPALTO_BEAN_L %>" listaCampiSelezionati="compL" readonly="${competenzaLotto ? 'true' : 'false'}" idField="idAppalto" onchange="setFormModified('Modificato5')" />								
</table>
</div></div></td></tr>  
<c:if test="${datiGara.tipoContratto == 'L' }">
	<tr><th><label for="OPERE_URBANIZZAZIONE">Opere di urbanizzazione a scomputo*</label></th>
	<td width="40%" > 
	 <u:selectBooleanRadio name="OPERE_URBANIZZAZIONE" value="${aggiudicazione.opereUrbanizzazione}" trueId="check4Y" falseId="check4N" disabled="${hide}" readonly="${readonly}" tabindex="<%=++iT%>" onchange="setFormModified('Modificato0')" />
  	<%iT++; %>
	</td></tr>
</c:if>
<tr><td colspan="2"><div class="inthead">
<label onclick="showMenuNoCheck('checkBoxListSF')" style="color:black; letter-spacing:0.2em;">
	<img src="img/minus.gif" id="imgcheckBoxListSF"/> Modalita di acquisizione
	<c:if test="${datiGara.tipoContratto == 'L' }">forniture / servizi</c:if>
</label>
<div id="checkBoxListSF" class="mbody" style="display: block;">
<table class="detailHelp"><colgroup><col width="60%"/><col width="40%"/></colgroup>
<c:set var="compFS" value="${schedaA.tipoFS}" scope="request" /> 
<u:multibox campo="<%=PSBD.FIELD_NAME_TIPO_APPALTO_AGG_SF %>" lista="<%= ParametriServlet.TIPO_APPALTO_BEAN_F %>" listaCampiSelezionati="compFS" readonly="${competenzaLotto ? 'true' : 'false'}" idField="idAppalto" onchange="setFormModified('Modificato5')" />								
</table></div></div></td></tr>  
<tr><th><label for="ID_TIPO_PRESTAZIONE">Prestazioni comprese nell'appalto*</label></th><td>
<select onchange="setFormModified('Modificato0')" tabindex="<%=++iT%>" style="width:100%" ${disabledStr} name="ID_TIPO_PRESTAZIONE" id="ID_TIPO_PRESTAZIONE" CLASS="BOTTONE">
	<option></option>
	<c:set var="idTipoPrest" value="${aggiudicazione.idTipoPrestazione}" scope="request" />
	<u:options name="<%= ParametriServlet.TIPO_PRESTAZIONE_BEAN %>" scope="request" value="idTipoPrest"/>
</select>
<c:if test="${readonly eq true}" >
		<input type="hidden" name="ID_TIPO_PRESTAZIONE" value="${aggiudicazione.idTipoPrestazione}" />
</c:if>
</td></tr>	
</table>
<table ${variazioniAnagrafiche eq true ? '' : 'style="display:none;"'}>
<tr><th><label for="<%= PSBD.FIELD_NAME_MOTIVO_CO %>">Motivazione della variazione anagrafica</label></th>
<td><select onchange="setFormModified('Modificato0')" tabindex="<%=++iT%>" style="width:100%" name="<%= PSBD.FIELD_NAME_MOTIVO_CO %>" id=<%= PSBD.FIELD_NAME_MOTIVO_CO %> CLASS="BOTTONE">
	<option></option>
	<c:set var="idMotivoVarCO" value="${aggiudicazione.idMotivoVarCO}" scope="request" />
	<u:options name="<%= ParametriServlet.MOTIVO_VCO_BEAN %>" scope="request" value="idMotivoVarCO"/>
</select></td></tr>
</table>
<tr><td colspan="2"><div class="inthead">
<label onclick="showMenu('<%= PSBD.TAB_PRESTAZIONI %>')" style="color:black; letter-spacing:0.2em; cursor:pointer;">
	<img src="img/minus.gif" id="img<%= PSBD.TAB_PRESTAZIONI %>"/> PRESTAZIONI PROGETTUALI</label>
	<div id="<%= PSBD.TAB_PRESTAZIONI %>" style="display: block;">
	<c:set var="prestazioni" value="${schedaA.prestazioni}" scope="page"></c:set>
		<%@ include file="/scheda1/prestazioni.jsp" %>     
</div></div></td></tr>
<table width="100%" ${variazioniAnagrafiche eq true ? 'style="display:none;"' : ''}><tr>
	<td colspan="2"><div class="inthead"><label onclick="showMenu('<%= PSBD.TAB_FINANZIAMENTI %>')" style="color:black; letter-spacing:0.2em; cursor:pointer;">
		<img src="img/minus.gif" id="img<%= PSBD.TAB_FINANZIAMENTI %>"/> FINANZIAMENTI</label>
	<div id="<%= PSBD.TAB_FINANZIAMENTI %>"  style="display: block;" >
	<c:set var="finanziamenti" value="${schedaA.finanziamenti}" scope="page"></c:set>
	<%@ include file="/scheda1/finanziamenti.jsp" %>
</div></div></td></tr>
<tr><td align="center" colspan="2"><p class="detailHelp"><strong>DATI ECONOMICI DELL'APPALTO</strong></p></td></tr> 
<tr><th><label for="COD_STRUMENTO">Strumento di programmazione</label></th><td>
<select onchange="setFormModified('Modificato0')" tabindex="<%=++iT%>" style="width:100%" ${disabledStr} name="COD_STRUMENTO" id="COD_STRUMENTO" CLASS="BOTTONE">
<option></option>
<c:set var="codStrumento" value="${aggiudicazione.codStrumento}" scope="request" />
	  <u:options name="<%= ParametriServlet.TIPO_STRUMENTO_BEAN %>" scope="request" value="codStrumento"/>
</select>
<c:if test="${readonly eq true}" >
	<input type="hidden" name="COD_STRUMENTO" value="${aggiudicazione.codStrumento}" />
</c:if>
</td></tr>
<tr><th><label  for="IMPORTO_LAVORI">Importo componente lavori in &#8364; (al netto dell'IVA e degli oneri di sicurezza)</label></th>
<td><input onchange="setFormModified('Modificato0')" ${readonlyStr} tabindex="<%=++iT%>" type="text" style="text-align:right;width:100px;" id="IMPORTO_LAVORI"  name="IMPORTO_LAVORI"  value="<c:out value="${aggiudicazione.importoLavoriStr}" />" onblur="validateAmount(this);valutaSubTotale()" /></td></tr>	
<tr><th><label  for="IMPORTO_SERVIZI">Importo componente servizi in &#8364; (come sopra)</label></th><td>
	<input onchange="setFormModified('Modificato0')"  ${readonlyStr}  tabindex="<%=++iT%>"  type="text" style="text-align:right;width:100px;" id="IMPORTO_SERVIZI"  name="IMPORTO_SERVIZI"  value="<c:out value="${aggiudicazione.importoServiziStr}" />" onblur="validateAmount(this);valutaSubTotale()" />
</td></tr>
<tr><th><label  for="IMPORTO_FORNITURE">Importo componente forniture in &#8364; (come sopra)</label></th>
<td><input onchange="setFormModified('Modificato0')"   ${readonlyStr} tabindex="<%=++iT%>"  type="text" style="text-align:right;width:100px;" id="IMPORTO_FORNITURE"  name="IMPORTO_FORNITURE"  value="<c:out value="${aggiudicazione.importoFornitureStr}" />" onblur="validateAmount(this);valutaSubTotale()" /></td></tr>	
<tr><th><label for="SubTotale">SubTotale</label></th><td>
    <c:set var="subTot" value="${aggiudicazione.importoLavori + aggiudicazione.importoServizi + aggiudicazione.importoForniture }" ></c:set>
<% String subTot_1 = "";
	if(pageContext.getAttribute("subTot") != null && pageContext.getAttribute("subTot") instanceof BigDecimal){
		subTot_1 = PageHelper.formattaImporto((BigDecimal)pageContext.getAttribute("subTot"));
	} %>
	<input tabindex="<%=++iT%>" disabled type="text" id="SubTotale" name="SubTotale" value="<%=subTot_1 %>" style="text-align:right;font-weight: bold;width:100px;"/>
</td></tr>
<tr><th><label  for="IMPORTO_ATTUAZIONE_SICUREZZA">Importo totale per l'attuazione della sicurezza</label></th>
<td><input onchange="setFormModified('Modificato0')"   ${readonlyStr} tabindex="<%=++iT%>" type="text" style="text-align:right;width:100px;" id="IMPORTO_ATTUAZIONE_SICUREZZA"  name="IMPORTO_ATTUAZIONE_SICUREZZA"  value="<c:out value="${aggiudicazione.importoAttuazioneSicurezzaStr}" />"  onblur="validateAmount(this);valutaSubTotale()" /></td></tr>
<tr><th><label for="IMP_NON_ASSOG">Eventuali ulteriori somme non assoggettate al ribasso d'asta</label></th>
<td><input onchange="setFormModified('Modificato0')"  ${readonlyStr} tabindex="<%=++iT%>" type="text" style="text-align:right;width:100px;" id="IMP_NON_ASSOG"  name="IMP_NON_ASSOG"  value="<c:out value="${aggiudicazione.importoNonAssogStr}" />"  onblur="validateAmount(this);valutaSubTotale()" /></td></tr>		
<tr><th><label for="IMPORTO_PROGETTAZIONE">Importo progettazione</label></th>
<td><input onchange="setFormModified('Modificato0')"  ${readonlyStr} tabindex="<%=++iT%>" type="text" style="text-align:right;width:100px;"  id="IMPORTO_PROGETTAZIONE"  name="IMPORTO_PROGETTAZIONE" value="<c:out value="${aggiudicazione.importoProgettazioneStr}" />" onblur="validateAmount(this);valutaSubTotale()" /></td></tr>
<tr><th><label for="">Importo a base d'asta indicato in acquisizione CIG </label> </th><td>
	<c:set var="impBaseLotto" value="${datiGara.importoLotto}" ></c:set>
	<% 
	String impLottoCig = "";
	if(pageContext.getAttribute("impBaseLotto") != null && pageContext.getAttribute("impBaseLotto") instanceof BigDecimal){
		impLottoCig = PageHelper.formattaImporto((BigDecimal)pageContext.getAttribute("impBaseLotto"));
	}%> 
	<input tabindex="<%=++iT%>"   
				disabled type="text" id="importoLottoCig" name="importoLottoCig" 
				<%-- value="<c:out value="${datiGara.importoLotto}"/>" --%>
				value="<%=impLottoCig %>"
				style="border:0;text-align:right;font-weight: bold;width:100px;"/>	
	</td></tr>	
<tr><th><label for="ImpCompAppalto">Importo complessivo appalto</label></th><td>
	<c:set var="impComp" value="${subTot + aggiudicazione.importoProgettazione + aggiudicazione.importoAttuazioneSicurezza + aggiudicazione.importoNonAssog}" ></c:set>
	<% // adds 19052008 
	String subTot_2 = "";
	Object impCo = pageContext.getAttribute("impComp");
	if(impCo != null && impCo instanceof BigDecimal){
		subTot_2 = PageHelper.formattaImporto((BigDecimal)impCo);
	}
	%>
	<input tabindex="<%=++iT%>" disabled type="text" id="ImpCompAppalto" name="ImpCompAppalto" value="<%=subTot_2 %>" style="text-align:right;font-weight: bold;width:100px;" />
</td></tr>
<tr><th><label  for="IMPORTO_DISPOSIZIONE">Importo totale somme a disposizione*</label></th>
	<td>
		<input onchange="setFormModified('Modificato0')"  ${readonlyStr}
				tabindex="<%=++iT%>"  
				type="text" style="text-align:right;width:100px;" 
				id="IMPORTO_DISPOSIZIONE" 
				name="IMPORTO_DISPOSIZIONE" 
				value="<c:out value="${aggiudicazione.importoDisposizioneStr}" />" 
				onblur="validateAmount(this);valutaSubTotale()" />
	</td></tr>
	<tr><th><label for="ImpCompIntervento">Importo complessivo dell'intervento</label></th><td>
	<c:set var="impCompI" value="${impComp + aggiudicazione.importoDisposizione}" ></c:set>
	<% String subTot_3 = "";
	Object impCoI = pageContext.getAttribute("impCompI");
	if(impCoI != null && impCoI instanceof BigDecimal){
		subTot_3 = PageHelper.formattaImporto((BigDecimal)impCoI);
	}
	%>
		<input tabindex="<%=++iT%>" disabled type="text" id="ImpCompIntervento" name="ImpCompIntervento" value="<%=subTot_3 %>" style="text-align:right;font-weight: bold;width:100px;"/>
	</td></tr>
	<tr><td align="center" colspan="2"><p class="detailHelp"><strong>DATI PROCEDURALI DELL'APPALTO</strong></p></td></tr>								
	<c:set var="idSceltaContr" value="${aggiudicazione.idSceltaContraente}" scope="request" />
	<c:if test="${aggiudicazione.idSceltaContraente == 0}">
		<c:set var="idSceltaContr" value="${datiGara.idSceltaContraente}" scope="request" />
	</c:if>

   <% if(is3042) { %>			
		<tr><th><label for="<%= ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE %>">Procedura di scelta contraente</label></th>
			<td>
				<select onchange="setFormModified('Modificato0')" tabindex="<%=++iT%>" style="width: 100%" ${disabledStr}  name="<%= ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE %>" id="sel_CONTRAENTE" CLASS="BOTTONE">
					<option></option>
					<u:optionsLinked name="<%= ParametriServlet.SCELTA_CONTRAENTE_BEAN %>" scope="request" value="idSceltaContr"/>
				</select>
		<c:if test="${readonly eq true}" >
			<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE %>" value="${aggiudicazione.idSceltaContraente}" />
		</c:if>
		</td></tr>
	<% } %>
<c:set var="modoRiagg" value="${aggiudicazione.modalitaRiaggiudicazione}" scope="request" />
<c:set var="readonlyModoRiagg" value="${roByFlusso eq true or (aggiudicazione.confirmed and variazioniAnagrafiche ne true)}" />
<c:set var="readonlyModoRiaggStr" value="${readonlyModoRiagg and modoRiagg != 0 ? 'disabled' : ''} " /> 	
<tr><th><label for="MODALITA_RIAGGIUDICAZIONE">Modalit� di riaggiudicazione/affidamento dell'appalto</label></th>
	<td><select onchange="setFormModified('Modificato0')" tabindex="<%=++iT%>" style="width:100%" ${readonlyModoRiaggStr} name="MODALITA_RIAGGIUDICAZIONE" id="MODALITA_RIAGGIUDICAZIONE" CLASS="BOTTONE">
		<option></option>
		<u:options name="<%= ParametriServlet.MODO_RIAGG_BEAN %>" scope="request" value="modoRiagg"/>
		</select>
		<c:if test="${readonly eq true}" >
			<input type="hidden" name="MODALITA_RIAGGIUDICAZIONE" value="${aggiudicazione.modalitaRiaggiudicazione}" />
		</c:if>
</td></tr>
<tr><th><label for="ASTA_ELETTRONICA">Ricorso all'asta elettronica*</label></th>
	<td><u:selectBooleanRadio name="ASTA_ELETTRONICA" value="${aggiudicazione.astaElettronica}" trueId="<%= PSBD.S_FIELD_NAME_ASTA_ELETTRONICA %>" falseId="<%= PSBD.N_FIELD_NAME_ASTA_ELETTRONICA %>"  readonly="${readonly}"  tabindex="<%=++iT%>" onchange="setFormModified('Modificato0')" />
		   			 <%iT++; %>	
	</td></tr>	

   <% if(is3042) { %>	
		<tr><td colspan="2">
			<div class="inthead">	
			<label onclick="showMenuNoCheck('checkBoxList')" style="color:black; letter-spacing:0.2em;">
				<img src="img/minus.gif" id="imgcheckBoxList"/>
				Condizioni che giustificano il ricorso alla procedura negoziata senza previa pubblicazione di un bando oppure senza previa indizione di una gara
			</label>
			<div id="checkBoxList" class="mbody" style="display: block;">
			<table width="80%" class="detailHelp"><colgroup><col width="60%"/><col width="40%"/></colgroup>
			<c:set var="condizioni" value="${schedaA.condizioni}" scope="request" /> 
			<u:multibox campo="ID_CONDIZIONE" lista="<%= ParametriServlet.CONDIZIONI_AGG_BEAN %>" listaCampiSelezionati="condizioni" readonly="${readonly}" idField="idCondizione" onchange="setFormModified('Modificato4')" />								
			</table>
		</div></div>
		</td></tr>  
		<% } %>
<tr><th><label  for="<%= PSBD.FIELD_NAME_ID_MODALITA_GARA %>">Criteri di aggiudicazione</label></th>
	<td><select onchange="setFormModified('Modificato0')" tabindex="<%=++iT%>" style="width: 100%" ${disabledStr} name="<%= PSBD.FIELD_NAME_ID_MODALITA_GARA %>" id="sel_MODALITA_GARA" CLASS="BOTTONE">
		<c:set var="idModGara" value="${aggiudicazione.idModalitaGara}" scope="request" />
		<option></option>
		<u:options name="<%= ParametriServlet.CRITERI_AGGIUDICAZIONE_BEAN %>" scope="request" value="idModGara"/>
		</select>
		<c:if test="${readonly eq true}" >
				<input type="hidden" name="<%= PSBD.FIELD_NAME_ID_MODALITA_GARA %>" value="${aggiudicazione.idModalitaGara}" />
		</c:if>
</td></tr>	
<tr><th><label >E' stata utilizzata la procedura accelerata per ragioni di urgenza?*</label></th>
	<td width="40%"> 
	 	<u:selectBooleanRadio name="<%= PSBD.FIELD_NAME_PROCEDURA_ACC %>" value="${aggiudicazione.proceduraAcc}" trueId="check2Y" falseId="check2N" readonly="${readonly}" tabindex="<%=++iT%>" onchange="setFormModified('Modificato0')" />
		<%iT++; %>
	</td></tr> 	
<tr><th><label >E' stata effettuata la preinformazione?*</label></th>
<td width="40%"> 
	 		<u:selectBooleanRadio name="<%= PSBD.FIELD_NAME_PREINFORMAZIONE %>" value="${aggiudicazione.preinformazione}" trueId="check3Y" falseId="check3N" readonly="${readonly}"  tabindex="<%=++iT%>" onchange="setFormModified('Modificato0')" />
		   			 <%iT++; %>
</td></tr>	 	
<tr><th><label >E' stato utilizzato un termine ridotto con avviso di preinformazione?*</label></th>
	<td width="40%"> 
		<u:selectBooleanRadio name="<%= PSBD.FIELD_NAME_TERMINE_RIDOTTO %>" value="${aggiudicazione.termineRidotto}" trueId="check4Y" falseId="check4N" readonly="${readonly}" tabindex="<%=++iT%>" onchange="setFormModified('Modificato0')" />
		<%iT++; %>
	</td></tr>
	
   <% if(is3042) {  %>	
<tr><th><label for="ID_MODO_GARA">Modalita di indizione della gara (art. 224 c. 1) Settori Speciali</label></th>
<td><select onchange="setFormModified('Modificato0')" tabindex="<%=++iT%>" style="width: 100%" ${disabledStr} name="ID_MODO_GARA" id="sel_MODO_INDIZIONE_GARA" CLASS="BOTTONE">
		<c:set var="idModoIndizione" value="${aggiudicazione.idModoIndizione}" scope="request" />
		<option></option>
		<u:options name="<%= ParametriServlet.MODO_INDIZIONE_GARA %>" scope="request" value="idModoIndizione"/>
		</select>
		<c:if test="${readonly eq true}" >
			<input type="hidden" name="ID_MODO_GARA" value="${aggiudicazione.idModoIndizione}" />
		</c:if>
</td></tr>
    <% } %>
  
<tr><td colspan="2">
<div class="inthead">
	<label onclick="showMenuNoCheck('TabRequisiti')" style="color:black; letter-spacing:0.2em;">
	<img src="img/minus.gif" id="imgTabRequisiti"/>
			 REQUISITI DI PARTECIPAZIONE / QUALIFICAZIONE
			 </label>
	<div id="TabRequisiti"  style="display: block;" ${readonlyStr }>
	<c:set var="requisiti" value="${schedaA.requisiti}" scope="page"></c:set>
	<%@ include file="/scheda1/requisiti.jsp" %>     
</div></div>
</td></tr>	
<tr><td colspan="2" align="center"><p class="detailHelp"><strong>INVITI E OFFERTE / SOGLIA DI ANOMALIA</strong></p></td></tr>
<tr><th><label for="">Data di scadenza per la presentazione delle manifestazioni di interesse</label></th><td>
<input  tabindex="<%=++iT%>" style="text-align:center" ${readonlyStr} onchange="setFormModified('Modificato0')" onblur="Calendar.validaData(this)" type="text" id="dtmanif" name="<%= PSBD.FIELD_NAME_DATA_MANIF_INTERESSE %>"  value="<c:out value="${aggiudicazione.dataManifInteresse}" />">
	<c:if test="${readonly ne true}">
	<img src="calendar/img.gif" id="CALdtmanif" style="cursor: pointer; border: 1px solid red;" title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
	<script type="text/javascript">
	    Calendar.setup({inputField:"dtmanif",ifFormat:"%d/%m/%Y",button:"CALdtmanif",align:"Tl",singleClick:true});
	</script>
	</c:if>
</td></tr>
<!-- inizio mev 34183 3.04.8-->
<tr><th><label for="">Data di scadenza per la presentazione della richiesta di invito</label></th><td>
<input  tabindex="<%=++iT%>" style="pointer-events: none;text-align:center" ${readonlyStr} onchange="setFormModified('Modificato0')" onblur="Calendar.validaData(this)" type="text" id="dtscadinv" name="<%= PSBD.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO %>"  value="<c:out value="${aggiudicazione.dataScadenzaRichiestaInvito}" />">
	<c:if test="${readonly ne true}">
	<img src="calendar/img.gif" id="CALdtscadinv" style="pointer-events: none; title="Date selector"  />
	<script type="text/javascript">
    Calendar.setup({inputField:"dtscadinv",ifFormat:"%d/%m/%Y",button:"CALdtscadinv",align:"Tl",singleClick:true});
	</script>
	</c:if>							
</td></tr>		
<tr><th><label>Data Invito</label></th><td>
<input  tabindex="<%=++iT%>" style="pointer-events: none;text-align:center" ${readonlyStr} onchange="setFormModified('Modificato0')" onblur="Calendar.validaData(this)" type="text" id="dtinvito" name="<%= PSBD.FIELD_NAME_DATA_INVITO %>" value="<c:out value="${aggiudicazione.dataInvito}" />">
	<c:if test="${readonly ne true}">
	<img src="calendar/img.gif" id="CALdtinvito" style="pointer-events: none;" title="Date selector" />
	<script type="text/javascript">
    Calendar.setup({inputField:"dtinvito",ifFormat:"%d/%m/%Y",button:"CALdtinvito",align:"Tl",singleClick:true});
	</script>
	</c:if>						
</td></tr>			
<tr><th><label>${markFieldAVCPass} Data di scadenza per la presentazione delle offerte*</label></th>
<td><input tabindex="<%=++iT%>" style="pointer-events: none;text-align:center" ${readonlyStr} ${readonlyAVCPass} onchange="setFormModified('Modificato0')" onblur="Calendar.validaData(this)" type="text" id="dtscadoff" name="<%= PSBD.FIELD_NAME_DATA_SCADENZA_PRES_OFFERTA %>" value="<c:out value="${aggiudicazione.dataScadenzaPresOfferta}" />">
	<c:if test="${readonly ne true && !fromAVCPass}">
		<img src="calendar/img.gif" id="CALdtscadoff" style="pointer-events: none;" title="Date selector" />
		<script type="text/javascript">
		    Calendar.setup({inputField:"dtscadoff",ifFormat:"%d/%m/%Y",button:"CALdtscadoff",align:"Tl",singleClick:true});
		</script>
	</c:if>
</td></tr>	
<!-- fine mev 34183 3.04.8-->	
<tr><th><label >n� Soggetti che hanno presentato manifestazione di interesse</label></th><td><input maxlength="9" style="text-align:right;" onblur="validateNumber(this)" tabindex="<%=++iT%>" ${readonlyStr} type="text"  name="<%= PSBD.FIELD_NAME_NUM_MANIF_INTERESSE %>" value="<c:out value="${aggiudicazione.numManifInteresse}" />" onchange="setFormModified('Modificato0')"></td></tr>	
<tr><th><label >n� Soggetti che hanno presentato richiesta di invito</label></th><td><input style="text-align:right;" onblur="validateNumber(this)" tabindex="<%=++iT%>" ${readonlyStr} type="text" name="<%= PSBD.FIELD_NAME_NUM_IMPRESE_RICHIEDENTI %>" maxlength="5" value="<c:out value="${aggiudicazione.numImpreseRichiedenti}" />" onchange="setFormModified('Modificato0')"></td></tr>
<tr><th><label >${markFieldAVCPass} n� Soggetti invitati a presentare offerta</label></th><td><input style="text-align:right;" onblur="validateNumber(this)" tabindex="<%=++iT%>" ${readonlyStr} ${readonlyAVCPass} type="text" name="<%= PSBD.FIELD_NAME_NUM_IMPRESE_INVITATE %>" maxlength="5" value="<c:out value="${aggiudicazione.numImpreseInvitate}" />" onchange="setFormModified('Modificato0')"></td></tr>
<tr><th><label >${markFieldAVCPass} n� Soggetti che hanno presentato offerta</label></th><td><input style="text-align:right;" onblur="validateNumber(this)" tabindex="<%=++iT%>" ${readonlyStr} ${readonlyAVCPass} type="text" name="<%= PSBD.FIELD_NAME_NUM_IMPRESE_OFFERENTI %>" maxlength="5" value="<c:out value="${aggiudicazione.numImpreseOfferenti}" />" onchange="setFormModified('Modificato0')"></td></tr>
<tr><th><label >${markFieldAVCPass} n� offerte ammesse*</label></th><td><input style="text-align:right;" onblur="validateNumber(this)" tabindex="<%=++iT%>" ${readonlyStr} ${readonlyAVCPass} type="text" name="<%= PSBD.FIELD_NAME_NUM_OFFERTE_AMMESSE %>" maxlength="5" value="<c:out value="${aggiudicazione.numOfferteAmmesse}" />" onchange="setFormModified('Modificato0')"></td></tr>
<tr><th><label >${markFieldAVCPass} Offerta di massimo ribasso</label></th><td><input tabindex="<%=++iT%>" ${readonlyStr} ${readonlyAVCPass} onblur="validatePercentage(this)" style="text-align: right" type="text" name="<%= PSBD.FIELD_NAME_OFFERTA_MASSIMO_RIBASSO %>" size="9" maxlength="9" value="<c:out value="${aggiudicazione.offertaMassimoStr}" />" onchange="setFormModified('Modificato0')">%</td></tr>
<tr><th><label >${markFieldAVCPass} Offerta di minimo ribasso</label></th><td><input tabindex="<%=++iT%>" ${readonlyStr} ${readonlyAVCPass} onblur="validatePercentage(this)" style="text-align: right" type="text" name="<%= PSBD.FIELD_NAME_OFFERTA_MINIMO_RIBASSO %>" size="9" maxlength="9" value="<c:out value="${aggiudicazione.offertaMinimaStr}" />" onchange="setFormModified('Modificato0')" >%</td></tr>
<tr><th><label >Valore soglia anomalia</label></th><td><input tabindex="<%=++iT%>" ${readonlyStr} onblur="validatePercentage(this)" style="text-align: right" type="text" name="<%= PSBD.FIELD_NAME_VALORE_SOGLIA_ANOMALIA %>" size="9" maxlength="9" value="<c:out value="${aggiudicazione.valSogliaAnomaliaStr}" />"  onchange="setFormModified('Modificato0')" >%</td></tr>
<tr><th><label >N.offerte >= soglia anomalia</label></th><td><input maxlength="9" tabindex="<%=++iT%>" ${readonlyStr} onblur="validateNumber(this)" style="text-align: right" type="text" name="<%= PSBD.FIELD_NAME_NUM_OFFERTE_MAG_SOGLIA %>" maxlength="1024" value="<c:out value="${aggiudicazione.numOfferteFuoriSoglia}" />" onchange="setFormModified('Modificato0')"></td></tr>
<tr><th><label >Numero imprese escluse automaticamente</label></th><td><input maxlength="9" tabindex="<%=++iT%>" ${readonlyStr} onblur="validateNumber(this)" style="text-align: right" type="text" name="<%= PSBD.FIELD_NAME_NUM_IMP_ESCLUSE_AUTOMATICAMENTE %>" maxlength="1024" value="<c:out value="${aggiudicazione.numOfferteEscluse}" />" onchange="setFormModified('Modificato0')"></td></tr>
<tr><th><label >Numero imprese escluse per insufficienti giustificazioni</label></th><td><input maxlength="9" tabindex="<%=++iT%>" ${readonlyStr} onblur="validateNumber(this)" style="text-align: right" type="text" name="<%= PSBD.FIELD_NAME_NUM_IMP_ESCLUSE_INSUF_GIUST %>" maxlength="1024" value="<c:out value="${aggiudicazione.numImpEscluseInsufGiust}" />" onchange="setFormModified('Modificato0')"></td></tr>
</table>	
	<c:set var="includerConfirmed" value="${aggiudicazione.confirmed}" scope="page"></c:set> <!-- Per affidatari e responsabili... -->
<tr><td colspan="2"><div class="inthead">
	<label onclick="showMenu('TabAffidatario')" style="color:black; letter-spacing:0.2em; cursor:pointer;">
		<img src="img/minus.gif" id="imgTabAffidatario"/> AGGIUDICAZIONE / AFFIDAMENTO</label>
	<div id="TabAffidatario" style="display: block;" >
	<c:set var="aggiudicatari" value="${schedaA.aggiudicatari}" scope="page"></c:set>		
	<%@ include file="/scheda1/affidatario.jsp" %>
	</div></div>					
</td></tr>					
<tr><td colspan="2">
<div class="inthead">
	<label onclick="showMenu('TabResponsabileProcedimento')" style="color:black; letter-spacing:0.2em; cursor:pointer;">
		<img src="img/minus.gif" id="imgTabResponsabileProcedimento"/> ANAGRAFICA E RIFERIMENTI DEI SOGGETTI AI QUALI LA STAZIONE APPALTANTE HA CONFERITO INCARICHI</label>
	<div id="TabResponsabileProcedimento"  style="display: block;" >
		<c:set var="responsabili" value="${schedaA.responsabili}" scope="page"></c:set>	
		<%@ include file="/scheda1/responsabile.jsp" %>
	</div>
</div></td></tr>	
</table>  			 
</fieldset>
<input type="hidden" name="FLAG_ACCORDO_QUADRO" id="FLAG_ACCORDO_QUADRO" value="${aggiudicazione.flagAccordoQuadro}" />
<input type="hidden" name="CODICE_CONTRATTO" id="CODICE_CONTRATTO" value="${aggiudicazione.codiceContratto}" />
<input type="hidden" name="FLAG_AGGIUD_PRINCIPALE" id="FLAG_AGGIUD_PRINCIPALE" value="${aggiudicazione.flagAggiudPrincipale}" />
<input type="hidden" id="Modificato0" name ="Modificato0" value="<c:out value="${param['modificato0']}" />">
<input type="hidden" id="Modificato4" name ="Modificato4" value="<c:out value="${param['modificato4']}" />">
<input type="hidden" id="Modificato5" name ="Modificato5" value="<c:out value="${param['modificato5']}" />">