<%//@ page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%//@ page import="it.avlp.simog.common.servlet.PSBD"%>
<%//@ page import="it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="it.avlp.simog.db.SimogFlags"%>
<%@page import="it.avlp.simog.db.Costanti"%>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% int indiceTab = 0; %>
<c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>

<c:set var="is3030_RFWEBSC00" value="${false}"/>
<!-- 3.04.8 34190 fix -->
<% if( SimogFlags.is3030_RFWEBSC00Active() ) {%>
	<c:set var="MODOREAL_ADESIONE_NOCOMPET" value="<%= Costanti.MODOREAL_ADESIONE_NOCOMPET %>"/>
	<c:set var="is3030_RFWEBSC00" value="${datiGara.ID_MODO_REAL == MODOREAL_ADESIONE_NOCOMPET}"/>
	<c:set var="MODOREAL_CONCESSIONE_NOCOMPET" value="<%= Costanti.MODOREAL_CONCESSIONE_NOCOMPET %>"/>
	<c:set var="is3030_RFWEBSC00_CONC" value="${datiGara.ID_MODO_REAL == MODOREAL_CONCESSIONE_NOCOMPET}"/>

<% } %>

<!-- TB: ticket popup modali. Import css e js -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<div id="dialog"></div>
<!-- fine import popup modali -->

<fieldset class="gara">
	<br>
<table width="100%">
	<%@include file="/include/intestazione.jsp" %>
	<table width="100%" ${variazioniAnagrafiche eq true ? 'style="display:none;"' : ''}>
<!--	  <colgroup>-->
<!--		<col width="60%"/>-->
<!--		<col width="40%"/>-->
<!--     </colgroup>-->
	<tr>
	  	<td align="center" colspan="2"><p class="detailHelp"><strong><utils:message key="scheda.oggettoAdesione" /></strong></p></td>
	</tr>
	<input type="hidden" id="canSearch" value="false" />	
	<tr>
	<th><label for="">Codice di individuazione dell'appalto (CIG)</label></th>
		  <td>
			 <input type="text" id="<%= ParametriServlet.FIELD_NAME_CIG %>" name="<%= ParametriServlet.FIELD_NAME_CIG %>" value="<c:out value="${datiGara.fullCIG}" />" disabled >
		  </td>
	</tr>
	
	<tr>
		<th><label for="<%= ParametriServlet.FIELD_NAME_OGGETTO_LOTTO %>">Oggetto del Contratto</label></th>
		  <td>
			 <input  style="width:100%" type="text" id="<%= ParametriServlet.FIELD_NAME_OGGETTO_LOTTO %>" name="<%= ParametriServlet.FIELD_NAME_OGGETTO_LOTTO %>" value="<c:out value="${datiGara.oggettoLotto}" />" disabled >
		  </td>
	</tr>
	<tr>
		<th><label for="">Codice del luogo di esecuzione del contratto (ISTAT)</label></th>
		<td >
			<input onchange="setFormModified('Modificato0')" 
				   maxlength="9" 
				   tabindex="<%=++indiceTab%>" <c:out value="${readonlyStr}" /> 
				   type="text" 
				   name="<%= PSBD.FIELD_NAME_LUOGO_ISTAT %>" 
				   value="<c:out value="${aggiudicazione.luogoIstat}" />" 
				   id="sel_ISTAT" onblur="searchLE(this.id, 'ricercaIstat.jsp', 'isNotIstat')" 
				   onkeyup="checkKeyLE(event, this, 'ricercaIstat.jsp', 'isNotIstat')"
			/>
			<c:if test="${readonly != true}">
				<a class="getCPV" href="#"  onclick="apripopup('ricercaIstat.jsp','sel_ISTAT')" title="Lista codici ISTAT"><img src="img/icon_info_sml.gif"></a>
			</c:if>
		</td>
	</tr>	
	
	<tr>
		<th><label for="">Codice del luogo di esecuzione del contratto (NUTS)</label></th>	
		<td>
			<input onchange="setFormModified('Modificato0')" maxlength="12" tabindex="<%=++indiceTab%>" <c:out value="${readonlyStr}" /> 
			type="text" name="<%= PSBD.FIELD_NAME_LUOGO_NUTS %>" 
			value="<c:out value="${aggiudicazione.luogoNuts}" />" 
			id="sel_NUTS" onblur="searchLE(this.id, 'ricercaNuts.jsp', 'isNotNuts')" 
		       	onkeyup="checkKeyLE(event, this, 'ricercaNuts.jsp', 'isNotNuts')" />
			<c:if test="${readonly != true}">
				<a class="getCPV" href="#"  onclick="apripopup('ricercaNuts.jsp','sel_NUTS')" 
						title="Lista codici NUTS"><img src="img/icon_info_sml.gif"></a>
			</c:if>
		</td>
	</tr>
	
	<tr>
		<td align="center" colspan="2"><p class="detailHelp"><strong><utils:message key="scheda.datiEconomiciAdesione" /></strong></p></td>
	</tr> 
	
	<tr>
		<th><label for="<%= PSBD.FIELD_NAME_COD_STRUMENTO %>">Codice dello strumento di programmazione</label></th>
		<td>
			<select onchange="setFormModified('Modificato0')" tabindex="<%=++indiceTab%>" 
					style="width:100%" <c:out value="${disabledStr}" /> 
					name="<%= PSBD.FIELD_NAME_COD_STRUMENTO %>" 
					id=<%= PSBD.FIELD_NAME_COD_STRUMENTO %> CLASS="BOTTONE">
				<option></option>
			  	<c:set var="codStrumento" value="${aggiudicazione.codStrumento}" scope="request" />
			  	<u:options name="<%= ParametriServlet.TIPO_STRUMENTO_BEAN %>" scope="request" value="codStrumento"/>
			</select>
		</td>
	</tr>
	
	<tr>
		<td colspan="2">
			<div class="inthead">
				<label onclick="showMenu('<%= PSBD.TAB_FINANZIAMENTI %>')" 
					style="color:black; letter-spacing:0.2em; cursor:pointer;">
					<img src="img/minus.gif" id="img<%= PSBD.TAB_FINANZIAMENTI %>"/> FINANZIAMENTI</label>
				<div id="<%= PSBD.TAB_FINANZIAMENTI %>"  style="display: block;" >
				<c:set var="finanziamenti" value="${schedaA.finanziamenti}" scope="page"></c:set>
				<%@ include file="/scheda1/finanziamenti.jsp" %>
				</div>	
			</div>					
		</td>
	</tr>
		
	<tr>
		<th><label for="<%= PSBD.FIELD_NAME_IMPORTO_LAVORI %>">Importo di adesione componente lavori in &#8364; (al netto dell'IVA e degli oneri di sicurezza)</label></th>
		<!-- 3.04.8 34190 fix -->
		<td>
			<input onchange="setFormModified('Modificato0')" 
				   tabindex="<%=++indiceTab%>" <c:out value="${readonlyStr}" />  
				   type="text" style="text-align:right;width:100px;" 
				   id="<%= PSBD.FIELD_NAME_IMPORTO_LAVORI %>" 
				   name="<%= PSBD.FIELD_NAME_IMPORTO_LAVORI %>" 
				   value="<c:out value="${aggiudicazione.importoLavoriStr}" />"
				   onblur="validateAmount(this);valutaSubTotale();${is3030_RFWEBSC00 || is3030_RFWEBSC00_CONC ? "copySubTotale('euro');" : "" }" />
		</td>
	</tr>	
	<tr>
		<th><label for="<%= PSBD.FIELD_NAME_IMPORTO_SERVIZI %>">Importo di adesione componente servizi in &#8364; (come sopra)</label></th>
		<!-- 3.04.8 34190 fix -->
		<td>
			<input onchange="setFormModified('Modificato0')" 
				   tabindex="<%=++indiceTab%>" <c:out value="${readonlyStr}" />  
				   type="text" 
				   style="text-align:right;width:100px;" 
				   id="<%= PSBD.FIELD_NAME_IMPORTO_SERVIZI %>" 
				   name="<%= PSBD.FIELD_NAME_IMPORTO_SERVIZI %>" 
				   value="<c:out value="${aggiudicazione.importoServiziStr}" />" 
				   onblur="validateAmount(this);valutaSubTotale();${is3030_RFWEBSC00 || is3030_RFWEBSC00_CONC ? "copySubTotale('euro');" : "" }" />
		</td>
	</tr>
	<tr>
		<th><label for="<%= PSBD.FIELD_NAME_IMPORTO_FORNITURE %>">Importo di adesione componente forniture in &#8364; (come sopra)</label></th>
		<!-- 3.04.8 34190 fix -->
		<td>
			<input onchange="setFormModified('Modificato0')"
			tabindex="<%=++indiceTab%>" <c:out value="${readonlyStr}" /> type="text"
			style="text-align: right; width: 100px;"
			id="<%= PSBD.FIELD_NAME_IMPORTO_FORNITURE %>"
			name="<%= PSBD.FIELD_NAME_IMPORTO_FORNITURE %>"
			value="<c:out value="${aggiudicazione.importoFornitureStr}" />"
			onblur="validateAmount(this);valutaSubTotale();${is3030_RFWEBSC00 || is3030_RFWEBSC00_CONC ? "copySubTotale('euro');" : "" }" />
		</td>
	</tr>	
	<%-- GM gli altri 4 importi sono hidden nulli, ma devono esistere per usare la funzione valutaSubTotale()  --%>
  
  <!-- INIZIO TICKET ALM #647 -->
  <!-- Commentati importi precedentemente tenuti nascosti -->
  <%-- <tr>
   <td><input type="hidden" id="<%= PSBD.FIELD_NAME_IMPORTO_ATTUAZIONE_SICUREZZA %>" 
              name ="<%= PSBD.FIELD_NAME_IMPORTO_ATTUAZIONE_SICUREZZA %>"  
              value=""></td>
	<td><input type="hidden" id="<%= PSBD.FIELD_NAME_IMPORTO_NON_ASSOG %>" 
              name ="<%= PSBD.FIELD_NAME_IMPORTO_NON_ASSOG %>"  
              value=""></td>	
	<td><input type="hidden" id="<%= PSBD.FIELD_NAME_IMPORTO_PROGETTAZIONE %>" 
              name ="<%= PSBD.FIELD_NAME_IMPORTO_PROGETTAZIONE %>"  
              value=""></td>	
   <td><input type="hidden" id="<%= PSBD.FIELD_NAME_IMPORTO_DISPOSIZIONE %>" 
              name ="<%= PSBD.FIELD_NAME_IMPORTO_DISPOSIZIONE%>"  
              value=""></td>
   </tr>	--%>
	
	<tr>
		<th><label for="SubTotale">SubTotale</label></th>
		<td>
		    <%-- TB: Aggiungo i nuovi importi nel sub totale --%>
                         <c:set var="subTot" value="${aggiudicazione.importoLavori +
                                                 aggiudicazione.importoServizi +
                                                 aggiudicazione.importoForniture }" 
                                                 ></c:set> 

		<% // adds 19052008 
		String subTot_1 = "";
		if(pageContext.getAttribute("subTot") != null && pageContext.getAttribute("subTot") instanceof BigDecimal){
			subTot_1 = PageHelper.formattaImporto((BigDecimal)pageContext.getAttribute("subTot"));
		}
		%>
			<input tabindex="<%=++indiceTab%>" 
					disabled type="text" id="SubTotale" name="SubTotale"
					value="<%=subTot_1 %>" style="text-align:right;font-weight: bold;width:100px;"/>
		</td>
	</tr>
	
	<%-- Rendo gli importi visibili --%>
	<tr>
	<th><label for="<%= PSBD.FIELD_NAME_IMPORTO_ATTUAZIONE_SICUREZZA %>">Importo totale per l'attuazione della sicurezza</label></th>
	<td>
		<input onchange="setFormModified('Modificato0')"   ${readonlyStr}
				tabindex="<%=++indiceTab%>"  
				type="text" style="text-align:right;width:100px;" 
				id="<%= PSBD.FIELD_NAME_IMPORTO_ATTUAZIONE_SICUREZZA %>" 
				name="<%= PSBD.FIELD_NAME_IMPORTO_ATTUAZIONE_SICUREZZA %>" 
				value="<c:out value="${aggiudicazione.importoAttuazioneSicurezzaStr}" />" 
				onblur="validateAmount(this);valutaSubTotale()" />
	</td>
	</tr>
	<tr>
	<th><label for="<%= PSBD.FIELD_NAME_IMPORTO_NON_ASSOG %>">Eventuali ulteriori somme non assoggettate al ribasso d'asta</label></th>
	<td>
		<input onchange="setFormModified('Modificato0')"   ${readonlyStr}
				tabindex="<%=++indiceTab%>" 
				type="text" style="text-align:right;width:100px;" 
				id="<%= PSBD.FIELD_NAME_IMPORTO_NON_ASSOG %>" 
				name="<%= PSBD.FIELD_NAME_IMPORTO_NON_ASSOG %>" 
				value="<c:out value="${aggiudicazione.importoNonAssogStr}" />" 
				onblur="validateAmount(this);valutaSubTotale()" />
	</td>
	</tr>		
	<tr>
	<th><label for="<%= PSBD.FIELD_NAME_IMPORTO_PROGETTAZIONE %>">Importo progettazione</label></th>
	<td>
		<input onchange="setFormModified('Modificato0')"  ${readonlyStr}
				tabindex="<%=++indiceTab%>"   
				type="text" style="text-align:right;width:100px;" 
				id="<%= PSBD.FIELD_NAME_IMPORTO_PROGETTAZIONE %>" 
				name="<%= PSBD.FIELD_NAME_IMPORTO_PROGETTAZIONE %>" 
				value="<c:out value="${aggiudicazione.importoProgettazioneStr}" />" 
				onblur="validateAmount(this);valutaSubTotale()" />
	</td>
	</tr>
	<tr>
		<th><label for="SubTotale">Importo complessivo appalto</label></th>
		<td>
		    <%-- TB: Aggiungo i nuovi importi nel sub totale --%>
                         <c:set var="impComplessivoAppalto" value="${aggiudicazione.importoLavori +
                                                 aggiudicazione.importoServizi +
                                                 aggiudicazione.importoForniture +
                                                 aggiudicazione.importoAttuazioneSicurezza +
                                                 aggiudicazione.importoNonAssog +
                                                 aggiudicazione.importoProgettazione }" 
                                                 ></c:set> 

		<% 
		String impComplessivoAppalto = "";
		if(pageContext.getAttribute("impComplessivoAppalto") != null && pageContext.getAttribute("impComplessivoAppalto") instanceof BigDecimal){
			impComplessivoAppalto = PageHelper.formattaImporto((BigDecimal)pageContext.getAttribute("impComplessivoAppalto"));
		}
		%>
			<input tabindex="<%=++indiceTab%>" 
					disabled type="text" id="ImpComplessivoAppalto" name="ImpComplessivoAppalto"
					value="<%=impComplessivoAppalto %>" style="text-align:right;font-weight: bold;width:100px;"/>
		</td>
	</tr>
	<%-- Fine ticket ALM #647 --%>
	
	<tr>
		<td colspan="2">
			<div class="inthead">
				<label onclick="showMenu('<%= PSBD.TAB_AFFIDATARIO %>')" style="color:black; letter-spacing:0.2em; cursor:pointer;">
					<img src="img/minus.gif" id="img<%= PSBD.TAB_AFFIDATARIO %>"/> AGGIUDICAZIONE / AFFIDAMENTO</label>
				<div id="<%= PSBD.TAB_AFFIDATARIO %>" style="display: block;" >			
				<%--
				<c:set var="aggiudicazioneAccQuadro" value="${sessionScope['aggiudicazioneAccQuadro']}" scope="page"></c:set>	
				<c:set var="aggiudicatariAccQuadro" value="${sessionScope['aggiudicatariAccQuadro']}" scope="page"></c:set>						
				--%>
				<c:set var="aggiudicatari" value="${schedaA.aggiudicatari}" scope="page"></c:set>		
			
				<%@ include file="/scheda1/affidatarioAdesione.jsp" %>
				</div>	
			</div>					
		</td>
	</tr>
</table>

	<c:set var="includerConfirmed" value="${aggiudicazione.confirmed}" scope="page"></c:set> <!-- Per affidatari e responsabili... -->

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
				<label onclick="showMenu('<%= PSBD.TAB_RESPONSABILE_PROCEDIMENTO %>')" style="color:black; letter-spacing:0.2em; cursor:pointer;">
					<img src="img/minus.gif" id="img<%= PSBD.TAB_RESPONSABILE_PROCEDIMENTO %>"/> ANAGRAFICA E RIFERIMENTI DEI SOGGETTI AI QUALI LA STAZIONE APPALTANTE HA CONFERITO INCARICHI</label>
				<div id="<%= PSBD.TAB_RESPONSABILE_PROCEDIMENTO %>"  style="display: block;" >
					<c:set var="responsabili" value="${schedaA.responsabili}" scope="page"></c:set>
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

