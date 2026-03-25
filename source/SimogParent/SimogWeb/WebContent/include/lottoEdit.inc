<%@page import="it.avlp.simog.util.SimogProperties"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="it.avlp.simog.common.servlet.ParametriCup"%>
<%@page import="it.avlp.simog.util.PageHelper"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletLotto"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@ page import="java.math.BigDecimal"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"%>
<%@page import="it.avlp.simog.db.*"%>
<script type="text/javascript" src="xtree/treeutils.js"></script>
<!-- MAC 36768 3.04.8.1 -->
	<!-- import jQuery -->
	<script type="text/javascript" src="script/other/jquery.js"></script>
	<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
	<!-- FINE MAC 36768 -->

<%@ include file="/script/domUtils.js"%>
<!-- 3.04.9 MEV 40610 -->
<script type="text/javascript" src="xtree/treeutils.js"></script>
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />

<!-- TB: ticket popup modali. Import css e js -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<div id="dialog"></div>
<div id="dialogSoggAggr"></div>
<!-- fine import popup modali -->

<script>

//3.04.9 MEV 40610





$(document).ready(function () {
	var isEreditati = "<%=disabledEreditati%>";
	console.log("ereditati " + isEreditati);
	$(".derogaRow11").hide();
	if (document.querySelectorAll('#TAB_MESSAGGI_ERRORE li').length >1){
		if ($('.elencoQualificazioneSA input').is(':checked')) {
			$('.elencoQualificazioneSA input').attr("disabled", false);
			if ($('.derogaRow11 input').is(':checked')) {
				$('.elencoQualificazioneSA input').attr("disabled", false);
				$(".derogaRow11").show();
				$("#flagIS_KO").val("S");
			}
			
			
		}else {
			for (let li of document.querySelectorAll('#TAB_MESSAGGI_ERRORE li')){
				
					if (li.textContent.indexOf('SIMOG_LOTTO_040') != -1){
						
						
						
						$('.elencoQualificazioneSA input').attr("disabled", false); 
					
					}
					if (li.textContent.indexOf('SIMOG_LOTTO_041') != -1){
						
						$(".derogaRow11").show();
						$("#flagIS_KO").val("S");
						$('.elencoQualificazioneSA input').attr("disabled", false); 
					
					}
				
			}
		}
	 }
	 else {
		 if ($('.elencoQualificazioneSA input').is(':checked')) {
				$('.elencoQualificazioneSA input').attr("disabled", false);
				if ($('.derogaRow11 input').is(':checked')) {
					$('.elencoQualificazioneSA input').attr("disabled", false);
					$(".derogaRow11").show();
					$("#flagIS_KO").val("S");
				}
				
		}else {
			 	for (let li of document.querySelectorAll('#TAB_MESSAGGI_ERRORE li')){
			 		if (li.textContent.indexOf('SIMOG_LOTTO_040') != -1){
			 			console.log("pop-up 2");
			 			
			 			$('.elencoQualificazioneSA input').attr("disabled", false); 
			 			
					}

			 		if (li.textContent.indexOf('SIMOG_LOTTO_041') != -1){
			 			$(".derogaRow11").show();
			 			$("#flagIS_KO").val("S");
			 			$('.elencoQualificazioneSA input').attr("disabled", false); 
			 		}
				}
		}
	 }
});  
//fine 3.04.9 MEV 40610

	/* mev 37010 3.04.8.1 */
	function disableParita(flagVALUE,flagType){
		console.log(flagVALUE.value);
		console.log("esiste " + $('#tr_flagDerogaAdesione').length);
		if($('#tr_flagDerogaAdesione').length){
			if (flagType === 'derogaAdesione') {
				if(flagVALUE.value == 'S' || flagVALUE.value == ''){
					document.getElementById('flagPrevisioneQuotaHere').disabled=true;
					document.getElementById('flagMisurePremialiHere').disabled=true;

					//svuotare i campi parità di genere
		 			document.getElementById('flagPrevisioneQuotaHere').value='';
					document.getElementById('flagMisurePremialiHere').value='';
					document.getElementById('quota_giovanile').disabled=true;
					document.getElementById('quota_femminile').disabled=true;
					document.getElementById('quota_giovanile').value='';
					document.getElementById('quota_femminile').value=''; 
					//document.getElementById('idMotivoDeroga').value='';
					document.getElementById('flagMisurePremialiHere').value=''; 
					//document.getElementById('idMisuraPremiale').value='';

		 			var inputsMD = document.querySelectorAll('.checkboxfieldsMD');
					var inputsMP = document.querySelectorAll('.checkboxfieldsMP');
						
			        for (var i = 0; i < inputsMD.length; i++) {
			        	inputsMD[i].checked = false;
			        	inputsMD[i].disabled = true;
			        }

			        for (var i = 0; i < inputsMP.length; i++) {
			        	inputsMP[i].checked = false;
			        	inputsMP[i].disabled = true;
			        } 
				}

				if(flagVALUE.value == 'N'){
					document.getElementById('flagPrevisioneQuotaHere').disabled=false;
					document.getElementById('flagMisurePremialiHere').disabled=false;
				}
			}
		}else{
			console.log("entrato pnrr");
			var isEreditati = "<%=disabledEreditati%>";
			var pnrrDisabledParita = "<%=flagPnrrPnc%>";
			/* console.log("ereditati " + isEreditati);
			console.log("pnrrDisabledParita " + pnrrDisabledParita); */
			/* 3.04.9 mev 41375 */
			if (pnrrDisabledParita === "S" && isEreditati === "disabled" && $("#flagPrevisioneQuotaHere").val() != "") {
				console.log("ereditati con pnrr si");
			}else{
				if(flagVALUE.value == 'N' || flagVALUE.value == ''){

					document.getElementById('flagPrevisioneQuotaHere').disabled=true;
					document.getElementById('flagMisurePremialiHere').disabled=true;

					//svuotare i campi parità di genere
		 			document.getElementById('flagPrevisioneQuotaHere').value='';
					document.getElementById('flagMisurePremialiHere').value='';
					document.getElementById('quota_giovanile').disabled=true;
					document.getElementById('quota_femminile').disabled=true;
					document.getElementById('quota_giovanile').value='';
					document.getElementById('quota_femminile').value=''; 
					//document.getElementById('idMotivoDeroga').value='';
					document.getElementById('flagMisurePremialiHere').value=''; 
					//document.getElementById('idMisuraPremiale').value='';

		 			var inputsMD = document.querySelectorAll('.checkboxfieldsMD');
					var inputsMP = document.querySelectorAll('.checkboxfieldsMP');
						
			        for (var i = 0; i < inputsMD.length; i++) {
			        	inputsMD[i].checked = false;
			        	inputsMD[i].disabled = true;
			        }

			        for (var i = 0; i < inputsMP.length; i++) {
			        	inputsMP[i].checked = false;
			        	inputsMP[i].disabled = true;
			        } 

				}
				
				if(flagVALUE.value == 'S'){
					document.getElementById('flagPrevisioneQuotaHere').disabled=false;
					document.getElementById('flagMisurePremialiHere').disabled=false;
				}
			}
			
		}
	}
	/* mev 37010 3.04.8.1 */
</script>
<%
	String disabledSommaUrgenza = (idLotto != null) ? "disabled" : "";
	//TICKET ALM #3835-02.1
	String readCondizioni = (disabledTranneEscluso.equals("disabled")
			|| disabledTranneRipetiz.equals("disabled") || disabledTranneDatiCup.equals("disabled") || disabledTrannePariOpportunita.equals("disabled")) ? "true"
					: "false";
%>

<%
	MessageBean messBean = (it.avlp.simog.beans.MessageBean) request
			.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.ERRORBEAN);
	it.avlp.simog.beans.AllValidationBeans beanErr = null;
	HashMap<String, String> fieldToHighlight = new HashMap<String, String>();
	if (messBean != null)
		if (messBean instanceof it.avlp.simog.beans.AllValidationBeans) {
			beanErr = (it.avlp.simog.beans.AllValidationBeans) request
					.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.ERRORBEAN);
			fieldToHighlight = beanErr.getFieldToHighlight();
		}
%>

<table cellpadding="3">
	<input type="hidden" id="canSearch" value="false" />

	<tbody>
		<%
			if (!modificaDatiCup) {
		%>
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_OggettoLotto")%>
				for="<%=ParametriServlet.FIELD_NAME_OGGETTO_LOTTO%>">Oggetto
					Lotto*</label></th>
			<td><input type="text" size=100% maxlength="1024"
				value="<h:requestParameter 
					property="<%=ParametriServlet.FIELD_NAME_OGGETTO_LOTTO%>" defaultValue="<%=oggettoLotto%>"/>"
				name="<%=ParametriServlet.FIELD_NAME_OGGETTO_LOTTO%>"
				onchange="javascipt:replaceWordChars(this)"
				<%=disabledTranneEscluso%> <%=disabledTranneRipetiz%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneDatiCup%> <%=disabledTranneCPV%> <%=disabledTranneCatSoa%> %>/></td> <!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
		</tr>
		<%--TICKET ALM #3832-02.1 --%>
		<%
			if (!SimogProperties.getInstance()
						.isDataCreatedAfter3042(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
		%>
		<tr>
			<th><label for="somma_urgenza">Esecuzione di lavori di
					somma urgenza (ex art. 176 DPR 207/2010)</label></th>
			<td><input type="checkbox"
				name="<%=ParametriServlet.FIELD_NAME_SOMMA_URGENZA%>" value=""
				<%=checkedSommaUrgenza%> <%=disabledTranneEscluso%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneRipetiz%> <%=disabledTranneDatiCup%> <%=disabledTranneCatSoa%> /></td>  <!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
		</tr>
		<%
			}
		%>
		<%--FINE TICKET ALM #3832-02.1  --%>
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_ImportoLotto")%>>Importo (al netto dell'IVA)*</label></th>
			<%
				boolean importoLottoAUX = "".equals(importoLottoEuro) ? false
							: Double.parseDouble(Costanti.IMPORTO_FUORI_SCALA_STRING) == Double
									.parseDouble(PageHelper.formattaImporto(importoLottoEuro));
			%>
			<%
				importoLottoEuro = importoLottoAUX ? "" : importoLottoEuro;
			%>


			<%
				// PP 04.10.2012 patch per impedire importo indeterminato
					if (importoLottoAUX) {
						importoLottoEuro = "0";
						importoLottoAUX = false;
					}
			%>

			<%
				boolean importoLottoValido = !importoLottoAUX;
					String checkedNoImportoLotto = "";
					String checkedSIInserisciLotto = "";
					String disabledSIImportoLotto = "";

					if (importoLottoAUX || ParametriServlet.INSERISCI_IMPORTO_NO
							.equals(request.getParameter(ParametriServlet.FIELD_INSERISCI_IMPORTO))) {
						checkedNoImportoLotto = "checked";
						disabledSIImportoLotto = "disabled";
					} else {
						checkedSIInserisciLotto = "checked";
						disabledSIImportoLotto = "";
					}

					if (importoLottoEuro != null && !"".equals(importoLottoEuro))
						importoLottoEuro = PageHelper
								.formattaImporto(new BigDecimal(PageHelper.formattaImporto(importoLottoEuro)));

					if (importo_attuazione_sicurezza != null && !"".equals(importo_attuazione_sicurezza))
						importo_attuazione_sicurezza = PageHelper
								.formattaImporto(new BigDecimal(PageHelper.formattaImporto(importo_attuazione_sicurezza)));

					if (importo_opzioni != null && !"".equals(importo_opzioni))
						importo_opzioni = PageHelper
								.formattaImporto(new BigDecimal(PageHelper.formattaImporto(importo_opzioni)));
			%>

			<td>
				<!-- PP 04.10.2012 patch per impedire importo indeterminato  --> <input
				id="importo_si" type="hidden"
				onclick="checkChange(this, getElementById('importo_no'), '<%=dataComun%>', <%=user.isAmministratore()%>);"
				name="<%=ParametriServlet.FIELD_INSERISCI_IMPORTO%>"
				value="<%=ParametriServlet.INSERISCI_IMPORTO_SI%>"
				<%=checkedSIInserisciLotto%> <%=disabledTranneRipetiz%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneEscluso%> <%=disabledTranneCatSoa%>/>  <!-- MAD 68089 3.04.16 disabledTranneCatSoa --> <input
				onblur="validateAmount2Dots(this)"
				title="Nel caso in cui l'appalto preveda delle ripetizioni o altre opzioni e' necessario che siano ricomprese nell'importo"
				onchange="checkChange(this, this, '<%=dataComun%>', <%=user.isAmministratore()%>);"
				id="euro" type="text" style="text-align: right;"
				name="<%=ParametriServlet.FIELD_NAME_IMPORTO_LOTTO_EURO%>"
				value="<h:requestParameter property='<%=ParametriServlet.FIELD_NAME_IMPORTO_LOTTO_EURO%>' defaultValue='<%=importoLottoEuro%>' />"
				<%=disabledSIImportoLotto%> <%=disabledTranneEscluso%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneRipetiz%> <%=disabledTranneDatiCup%><%=disabledTranneCatSoa%> /> <!-- MAD 68089 3.04.16 disabledTranneCatSoa -->

			</td>

		</tr>
		<%-- TICKET ALM #13691 - 3.04.5 --%>
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_ImportoOpzioniLotto")%>
				for="<%=ParametriServlet.FIELD_NAME_IMPORTO_OPZIONI%>">&nbsp;&nbsp;&nbsp;&nbsp;di
					cui per opzioni/ripetizioni</label></th>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
				title="E' necessario indicare esclusivamente l'importo delle eventuali opzioni/ripetizioni"
				<c:out value="${disabled}" /> type="text" style="text-align: right;"
				id="<%= ParametriServlet.FIELD_NAME_IMPORTO_OPZIONI %>"
				name="<%= ParametriServlet.FIELD_NAME_IMPORTO_OPZIONI %>"
				value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_IMPORTO_OPZIONI %>' 
				   defaultValue='<%= importo_opzioni %>' />"
				onblur="validateAmount(this)" <%=disabledTranneEscluso %>
				<%=disabledTranneRipetiz %> <%= disabledTranneDatiCup %> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%><%=disabledTranneCatSoa%>/> <!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
			</td>
		</tr>
		<%-- FINE TICKET ALM #13691 - 3.04.5 --%>
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_ImportoSicurezzaLotto")%>
				for="<%=ParametriServlet.FIELD_NAME_IMPORTO_SICUREZZA%>">&nbsp;&nbsp;&nbsp;&nbsp;di
					cui per attuazione della sicurezza</label></th>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
				<c:out value="${disabled}" /> type="text" style="text-align: right;"
				id="<%= ParametriServlet.FIELD_NAME_IMPORTO_SICUREZZA %>"
				name="<%= ParametriServlet.FIELD_NAME_IMPORTO_SICUREZZA %>"
				value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_IMPORTO_SICUREZZA %>' 
				   defaultValue='<%= importo_attuazione_sicurezza %>' />"
				onblur="validateAmount(this)" <%=disabledTranneEscluso %> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneRipetiz %> <%= disabledTranneDatiCup %> <%=disabledTranneCatSoa%>/> <!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
			</td>
		</tr>

		<tr>
			<!-- 			PP 04.10.2012 patch per impedire importo indeterminato -->
			<!-- MAC 36767 3.04.8.1-->
			<!-- COMMENTATO TUTTO IL TR-->
			<!-- <th><label>Importo a base d'asta o presunto non
					disponibile</label></th> -->
			<%-- <td><input id="importo_no" type="hidden" type="radio"
				name="<%=ParametriServlet.FIELD_INSERISCI_IMPORTO%>"
				value="<%=ParametriServlet.INSERISCI_IMPORTO_NO%>"
				<%=checkedNoImportoLotto%>
				onclick="checkChange(this, getElementById('importo_si'), '<%=dataComun%>', <%=user.isAmministratore()%>);"
				<%=disabledTranneRipetiz%> <%=disabledTranneEscluso%>
				<%=disabledTranneDatiCup%> /> --%>
			<!-- MAC 36767 3.04.8.1 -->
		</tr>



		<%-- TICKET ALM #4222 - 3.04.4 --%>
		<tr id="tr_cpv_elencoCpv">
			<td colspan="2">
				<div class="inthead" style="padding-bottom: 15px">
					<label onclick="showMenu('TAB_CPV')"
						style="color: black; letter-spacing: 0.2em;"> <img
						src="img/minus.gif" id="imgTAB_CPV" /> CPV
					</label>
					<div id="TAB_CPV" style="display: block;">
						<br />
						<table>
							<tbody>
								<tr>
									<th><label
										<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_CPVLotto")%>
										for="<%=ParametriServlet.FIELD_NAME_CPV%>">CPV
											Prevalente*</label></th>
									<TD id="<%=ParametriServlet.FIELD_NAME_CPV%>">
									<!-- 3.04.8 34190 fix -->
										<%
											if (SimogProperties.getInstance()
														.isDataCreatedAfter3046(PageHelper.getFormattedDBDate(dataCreazioneGara))
														&& (String.valueOf(Costanti.MODOREAL_ADESIONE).equals(idModReal)
																|| String.valueOf(Costanti.MODOREAL_ADESIONE_NOCOMPET).equals(idModReal) || String.valueOf(Costanti.MODOREAL_CONCESSIONE_NOCOMPET).equals(idModReal) || String.valueOf(Costanti.MODOREAL_CONCESSIONE).equals(idModReal))) {
										%> <select
										name="<%=ParametriServlet.FIELD_NAME_CPV%>">
											<option></option>
											<u:options name="LISTA_CPV_ADESIONE"
												value="<%=CPVSelezionata%>" valLocation="tag" />
									</select> <%
 	} else {
 %> <input type="text"
										onblur="searchLE(this.id, 'ricercaCPV.jsp?nameField=sel_CPV', 'isNotCPV')"
										name="<%=ParametriServlet.FIELD_NAME_CPV%>"
										value="<h:requestParameter property='<%=ParametriServlet.FIELD_NAME_CPV%>' 
													   defaultValue='<%=CPVSelezionata%>' />"
										id="sel_CPV"
										onkeyup="checkKeyLE(event, this, 'ricercaCPV.jsp?nameField=sel_CPV', 'isNotCPV')"
										maxlength="12" <%=disabledTranneRipetiz%> <%=disabledTrannePariOpportunita%> 
										<%=disabledTranneEscluso%> <%=disabledTranneDatiCup%> <%=disabledTranneCatSoa%>><!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
										<%
											if (!modificaContrattoEscluso && !modificaRipetiz && !modificaDatiCup) {
										%>
										<img title="Lista CPV Correnti" style="cursor: pointer"
										onclick="apripopup('ricercaCPV.jsp?nameField=sel_CPV', 'sel_CPV')" 
										src="img/icon_info_sml.gif"> <%
 	}
 %>

									</td>
								</tr>
								<tr>
									<th><label for="labelDescCpvPrev">Descrizione</label></th>
									<td><input type="text" name="cpvPrevDesc"
										readonly="readonly" id="sel_CPVDesc"
										value="<%=descCpvPrev%>" <%=disabledTranneRipetiz%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
										<%=disabledTranneEscluso%> <%=disabledTranneDatiCup%><%=disabledTranneCatSoa%>> <!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
										<%
											if (!modificaContrattoEscluso && !modificaRipetiz && !modificaDatiCup) {
										%>
										<img style="cursor: pointer"
										onclick="javascript:callCpvDescPrev('sel_CPV', 'sel_CPVDesc')"
										title="Aggiorna descrizione CPV Prevalente"
										src="img/refresh.png"> <%
 	}
 %></td>
								</tr>
								<%
									}
								%>
							</tbody>
						</table>
						<br />
						<c:set var="readonlyCpv"
							value="<%=(modificaContrattoEscluso || modificaRipetiz)%>" />
						<%@ include file="/include/elencoCPVSecondarie.jsp"%>
					</div>
				</div>
			</td>
		</tr>

		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_SceltaContraenteLotto")%>
				for="<%=ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE%>">Scelta
					del Contraente*</label></th>
			<td><select
				name="<%=ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE%>"
				id="sel_CONTRAENTE" CLASS="BOTTONE" <%=disabledTranneRipetiz%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneEscluso%> <%=disabledTranneDatiCup%><%=disabledTranneCatSoa%>
				onchange="setCombo('sel_CONTRAENTE')"> <!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
					<option></option>
					<u:options name="<%=ParametriServlet.SCELTA_CONTRAENTE_BEAN%>"
						value="<%=contraenteSelezionata%>" valLocation="tag" /></td>
		</tr>

		<%-- TICKET ALM #4222 - 3.04.4  --%>
		<%-- nuovo campo Categoria Merceologica --%>
		<%
			if (SimogProperties.getInstance()
						.isDataCreatedAfter3044(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
		%>
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_CatMercLotto")%>
				for="<%=ParametriServlet.FIELD_NAME_CATEGORIA_LOTTO%>">Categoria
					merceologica di cui al DPCM soggetti aggregatori*</label></th>
			<td><select
				name="<%=ParametriServlet.FIELD_NAME_CATEGORIA_LOTTO%>"
				id="sel_CAT_MERC_LOTTO" CLASS="BOTTONE"
				onchange="resetSoggAggrFields()" <%=disabledTranneRipetiz%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneEscluso%> <%=disabledTranneDatiCup%><%=disabledTranneCatSoa%>> <!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
					<option></option>
					<u:options name="<%=ParametriServlet.LISTA_CATEGORIE_LOTTO%>"
						value="<%=catSelezionata%>" valLocation="tag" /></td>
		</tr>
		<%
			}
		%>

		<%-- TICKET ALM - 3.04.2 NG --%>
		<%-- nuovo campo Tipo appalto riservato (attivo solo per gare dopo l'attivazione della 3.04.2) --%>
		<%
			if (SimogProperties.getInstance()
						.isDataCreatedAfter3042(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
		%>
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_TipoRiservatoLotto")%>
				for="<%=ParametriServlet.FIELD_NAME_AFF_RISERVATI%>">Tipo
					appalto riservato</label></th>
			<td><select <%=disabledTranneEscluso%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneRipetiz%> <%=disabledTranneDatiCup%><%=disabledTranneCatSoa%>
				name="<%=ParametriServlet.FIELD_NAME_AFF_RISERVATI%>"
				id="sel_AFF_RISERVATI"> <!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
					<option></option>
					<u:options name="<%=ParametriServlet.AFF_RISERVATI%>"
						scope="request" value="<%=idAffRiservati%>" valLocation="tag"
						revert="yes" />
			</select></td>
		</tr>



		<%
			}
		%>
		<%-- TICKET ALM - 3.04.2 NG spostamento campo condizioni negoziata da S08 a S02 --%>
		<%
			if (SimogProperties.getInstance()
						.isDataCreatedAfter3042(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
		%>
		<tr>
			<td colspan="2">
				<div class="inthead">
					<label onclick="showMenuNoCheck('checkBoxList')"
						<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_CondizioniLotto", "black")
							+ "letter-spacing:0.2em;\""%>>
						<img src="img/minus.gif" id="imgcheckBoxList" /> Condizioni che
						giustificano il ricorso alla procedura negoziata senza previa
						pubblicazione di un bando oppure senza previa indizione di una
						gara
					</label>
					<div id="checkBoxList" class="mbody" style="display: block;">
						<table width="80%" class="detailHelp">
							<colgroup>
								<col width="60%" />
								<col width="40%" />
							</colgroup>
							<c:set var="condizioni"
								value="${requestScope['CONDIZIONI_LOTTO_SEL']}" scope="request" />
							<u:multibox campo="<%=PSBD.FIELD_NAME_CONDIZIONI_LOTTO%>"
								lista="<%=ParametriServlet.CONDIZIONI_LOTTO_BEAN%>"
								listaCampiSelezionati="condizioni"
								readonly="<%=readCondizioni%>" idField="idCondizione"
								onchange="setFormModified('Modificato4')" />
						</table>
					</div>
				</div>
			</td>
		</tr>
		<%
			}
		%>
		<%-- FINE TICKET ALM - 3.04.2 NG --%>


		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_TipoContrattoLotto")%>
				for="<%=ParametriServlet.FIELD_NAME_TIPO_CONTRATTO%>">Oggetto
					principale del contratto*</label></th>
			<td><c:set var="selTipo" value="<%=selTipoPar%>"></c:set> <select
				name="<%=ParametriServlet.FIELD_NAME_TIPO_CONTRATTO%>"
				CLASS="BOTTONE" <%=disabledTranneRipetiz%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneEscluso%> <%=disabledTranneDatiCup%><%=disabledTranneCatSoa%>><!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
					<option></option>
					<option value="L"
						<c:out value="${selTipo =='L' ? 'selected' : ''}" />>Lavori</option>
					<option value="S"
						<c:out value="${selTipo =='S' ? 'selected' : ''}" />>Servizi</option>
					<option value="F"
						<c:out value="${selTipo =='F' ? 'selected' : ''}" />>Forniture</option>
			</select></td>
		</tr>
		
		<!-- MEV 38205 3..04.8.1 -->
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_FlagUsoMetodiEdilizia")%>>
				Uso di metodi e strumenti elettronici di modellazione per l'edilizia e le infrastrutture?</label></th>
			<td><c:set var="selSiNoMet" value="<%=selSiNoMet%>"></c:set> <select
				name="<%=ParametriServlet.FIELD_NAME_FLAG_USO_METODI_EDILIZIA%>" CLASS="BOTTONE"
				<%=disabledTranneRipetiz%> <%=disabledTranneDatiCup%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%><%=disabledTranneCatSoa%>><!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
					<option></option>
					<option value="N"
						<c:out value="${selSiNoMet =='N' ? 'selected' : ''}" />>NO</option>
					<option value="S"
						<c:out value="${selSiNoMet =='S' ? 'selected' : ''}" />>SI</option>
			</select></td>
		</tr>
		<!-- FINE MEV 38205 3..04.8.1 -->

		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_ContrattoRegimeLotto")%>>Contratto
					escluso*</label></th>
			<td><c:set var="selSiNo" value="<%=selSiNoPar%>"></c:set> <select
				name="<%=ParametriServlet.FIELD_NAME_ESCLUSO%>" CLASS="BOTTONE"
				<%=disabledTranneRipetiz%> <%=disabledTranneDatiCup%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%><%=disabledTranneCatSoa%>><!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
					<option></option>
					<option value="N"
						<c:out value="${selSiNo =='N' ? 'selected' : ''}" />>NO</option>
					<option value="S"
						<c:out value="${selSiNo =='S' ? 'selected' : ''}" />>SI</option>
			</select></td>
		</tr>
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_EsclusioneLotto")%>
				for="<%=ParametriServlet.FIELD_NAME_ID_ESCLUSIONE%>">Esclusione</label></th>
			<td><select <%=disabledTranneRipetiz%>
				<%=disabledTranneDatiCup%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%><%=disabledTranneCatSoa%>
				name="<%=ParametriServlet.FIELD_NAME_ID_ESCLUSIONE%>"
				id="sel_ID_ESCLUSIONE"><!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
					<option></option>
					<u:options name="<%=ParametriServlet.ARTICOLO_ESCLUSIONE%>"
						scope="request" value="<%=idEsclusionePar%>" valLocation="tag"
						revert="yes" />
			</select></td>
		</tr>

		<%-- gm nuovo codice pubblicazione bando 3.0 --%>



		<%--TICKET ALM - 3.04.2 NG - campo flag Contratti Regimi Particolari di appalto (speciale o alleggerito) da visualizzare solo se la gara e' creata successivamente l'attivazione della 3.04.2 --%>
		<%
			if (SimogProperties.getInstance()
						.isDataCreatedAfter3042(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
		%>
		<tr>
			<th><label for="<%=ParametriServlet.FIELD_FLAG_REGIME%>">Contratto
					regime particolare di appalto (speciale o alleggerito)</label></th>
			<td><c:set var="selFlagRegime" value="<%=flagRegime%>"></c:set>
				<select name="<%=ParametriServlet.FIELD_FLAG_REGIME%>"
				CLASS="BOTTONE" <%=disabledTranneRipetiz%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneDatiCup%><%=disabledTranneCatSoa%>><!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
					<option value="N"
						<c:out value="${selFlagRegime !='S' ? 'selected' : ''}" />>NO</option>
					<option value="S"
						<c:out value="${selFlagRegime =='S' ? 'selected' : ''}" />>SI</option>
			</select></td>
		</tr>


		<%-- TICKET ALM - 3.04.2 NG - campo  Art. Regime particolari di appalto da visualizzare solo se la gara e' creata successivamente l'attivazione della 3.04.2 --%>
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_RegimeParticolareLotto")%>
				for="<%=ParametriServlet.FIELD_NAME_ART_REGIME%>">Regime
					particolare di appalto</label></th>
			<td><select <%=disabledTranneRipetiz%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneDatiCup%><%=disabledTranneCatSoa%>
				name="<%=ParametriServlet.FIELD_NAME_ART_REGIME%>"
				id="sel_ART_REGIME"><!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
					<option></option>
					<u:options name="<%=ParametriServlet.ART_REGIME%>"
						scope="request" value="<%=idArtRegime%>" valLocation="tag"
						revert="yes" />
			</select></td>
		</tr>

		<%
			}
		%>
		<%-- FINE TICKET ALM - 3.04.2 NG --%>

		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_ISTATLotto")%>
				for="<%=ParametriServlet.FIELD_NAME_LUOGO_ISTAT%>">Codice
					del luogo di esecuzione del contratto (ISTAT)</label></th>
			<td><input maxlength="9" <c:out value="${disabled}" />
				type="text"
				onblur="searchLE(this.id, 'ricercaIstat.jsp', 'isNotIstat')"
				onkeyup="checkKeyLE(event, this, 'ricercaIstat.jsp', 'isNotIstat')"
				name="<%= ParametriServlet.FIELD_NAME_LUOGO_ISTAT %>"
				value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_LUOGO_ISTAT %>' 
				   defaultValue='<%= luogoIstat %>' />"
				id="sel_ISTAT" <%=disabledTranneRipetiz %>
				<%=disabledTranneEscluso%> <%= disabledTranneDatiCup %> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%><%=disabledTranneCatSoa%> /><!-- MAD 68089 3.04.16 disabledTranneCatSoa --> <c:if
					test="${hide != true}">
					<%
						if (!modificaContrattoEscluso && !modificaRipetiz && !modificaDatiCup) {
					%>
					<a class="getCPV" 
						onclick="apripopup('ricercaIstat.jsp', 'sel_ISTAT')"
						title="Lista codici ISTAT"><img src="img/icon_info_sml.gif"></a>
					<%
						}
					%>
				</c:if></td>
		</tr>
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_NUTSLotto")%>
				for="<%=ParametriServlet.FIELD_NAME_LUOGO_NUTS%>">Codice
					del luogo di esecuzione del contratto (NUTS)</label></th>
			<td><input maxlength="12" <c:out value="${disabled}" />
				type="text"
				onblur="searchLE(this.id, 'ricercaNuts.jsp', 'isNotNuts')"
				onkeyup="checkKeyLE(event, this, 'ricercaNuts.jsp', 'isNotNuts')"
				name="<%= ParametriServlet.FIELD_NAME_LUOGO_NUTS %>"
				value="<h:requestParameter property='<%= ParametriServlet.FIELD_NAME_LUOGO_NUTS %>' 
				   defaultValue='<%= luogoNuts %>' />"
				id="sel_NUTS" <%=disabledTranneRipetiz %> <%=disabledTranneEscluso%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%= disabledTranneDatiCup %> <%=disabledTranneCatSoa%>/> <!-- MAD 68089 3.04.16 disabledTranneCatSoa --> <c:if test="${hide != true}">
					<%
						if (!modificaContrattoEscluso && !modificaRipetiz && !modificaDatiCup) {
					%>
					<a class="getCPV" 
						onclick="apripopup('ricercaNuts.jsp', 'sel_NUTS')"
						title="Lista codici NUTS"><img src="img/icon_info_sml.gif"></a>
					<%
						}
					%>
				</c:if></td>
		</tr>
		<%-- gm fine nuovo codice pubblicazione bando 3.0 --%>


		<%-- TICKET ALM - 3.04.2 NG - nuovo campo flag DL50 da mostrare se la gara e' creata dopo l'attivazione della 3.04.2 --%>
		<%
			if (SimogProperties.getInstance()
						.isDataCreatedAfter3042(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
		%>
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_DL50Lotto")%>
				for="<%=ParametriServlet.FIELD_FLAG_DL50%>">Il lavoro o
					l'acquisto di bene o servizio e' stato previsto all'interno della
					programmazione</label></th>
			<td><c:set var="selFlagDL50" value="<%=flagDL50%>"></c:set> <select
				name="<%=ParametriServlet.FIELD_FLAG_DL50%>" CLASS="BOTTONE"
				<%=disabledTranneEscluso%> <%=disabledTranneDatiCup%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%><%=disabledTranneCatSoa%>><!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
					<option value="N"
						<c:out value="${selFlagDL50 =='N' ? 'selected' : ''}" />>NO</option>
					<option value="S"
						<c:out value="${selFlagDL50 !='N' ? 'selected' : ''}" />>SI</option>
			</select></td>
		</tr>

		<%-- TICKET ALM - 3.04.2 NG - nuovo campo prima annualita da mostrare se la gara fa parte del nuovo codice degli appalti --%>
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_PrimaAnnualitaLotto")%>
				for="">Prima annualita' dell'ultimo programma nel quale e'
					stato inserito l'intervento o l'acquisto (formato aaaa)</label></th>
			<td><input maxlength="4"
				value="<h:requestParameter property='<%=ParametriServlet.FIELD_NAME_PRIMA_ANNUALITA%>' defaultValue='<%=primaAnnualita%>'/>"
				name="<%=ParametriServlet.FIELD_NAME_PRIMA_ANNUALITA%>"
				<%=disabledTranneEscluso%> <%=disabledTranneDatiCup%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%><%=disabledTranneCatSoa%>><!-- MAD 68089 3.04.16 disabledTranneCatSoa --></td>
		</tr>
		<%
			}
		%>

		<%-- test  Ticket #20057 - 09 - 02 - 21--%>

		<%
			if (SimogProperties.getInstance()
						.isDataCreatedAfter3046(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
		%>
		<tr>
			<th><label
				<%=!fieldToHighlight.isEmpty()
							&& fieldToHighlight.containsKey("label_DurataAffidamentoInGiorni") ? "style=\"color: red;\""
									: ""%>>Durata
					dell'affidamento in giorni*</label></th>
			<td><input <c:out value="${disabled}" />
				id="<%= ParametriServlet.FIELD_NAME_DURATA_AFFIDAMENTO_IN_GIORNI %>"
				name="<%= ParametriServlet.FIELD_NAME_DURATA_AFFIDAMENTO_IN_GIORNI %>"
				value="<%= durataAffidamentoInGiorni %>"
				onblur="validateNumber(this)" <%=disabledTranneEscluso %> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneRipetiz %> <%= disabledTranneDatiCup %><%=disabledTranneCatSoa%> /><!-- MAD 68089 3.04.16 disabledTranneCatSoa --></td>
		</tr>
		<%
			}
		%>

		<%-- gm nuovo codice 3.0 --%>

		<%-- TICKET ALM #2845-02.1 e 02.2 - oscuramento vecchi campi	--%>
		<%
			if (!SimogProperties.getInstance()
						.isDataCreatedAfter3042(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
		%>
		<tr>
			<td colspan="2">
				<h5>Estremi programma triennale</h5>
			</td>
		</tr>
		<tr>
			<th><label for="">Triennio anno inizio</label></th>
			<td><input maxlength="4"
				value="<h:requestParameter property='<%=ParametriServlet.FIELD_NAME_TRIENNIO_ANNO_INIZIO%>' defaultValue='<%=triennioAnnoInizio%>'/>"
				name="<%=ParametriServlet.FIELD_NAME_TRIENNIO_ANNO_INIZIO%>"
				<%=disabledTranneRipetiz%> <%=disabledTranneEscluso%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneDatiCup%><%=disabledTranneCatSoa%>><!-- MAD 68089 3.04.16 disabledTranneCatSoa --></td>
		</tr>
		<tr>
			<th><label for="">Triennio anno fine</label></th>
			<td><input maxlength="4"
				value="<h:requestParameter property='<%=ParametriServlet.FIELD_NAME_TRIENNIO_ANNO_FINE%>' defaultValue='<%=triennioAnnoFine%>'/>"
				name="<%=ParametriServlet.FIELD_NAME_TRIENNIO_ANNO_FINE%>"
				<%=disabledTranneRipetiz%> <%=disabledTranneEscluso%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneDatiCup%><%=disabledTranneCatSoa%>><!-- MAD 68089 3.04.16 disabledTranneCatSoa --></td>
		</tr>
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_ProgressivoLotto")%>
				for="">Progressivo nell'ambito del triennio</label></th>
			<td><input maxlength="9"
				value="<h:requestParameter property='<%=ParametriServlet.FIELD_NAME_TRIENNIO_ANNO_PROGRESSIVO%>' defaultValue='<%=triennioAnnoProgressivo%>'/>"
				name="<%=ParametriServlet.FIELD_NAME_TRIENNIO_ANNO_PROGRESSIVO%>"
				<%=disabledTranneRipetiz%> <%=disabledTranneEscluso%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneDatiCup%><%=disabledTranneCatSoa%>><!-- MAD 68089 3.04.16 disabledTranneCatSoa --></td>
		</tr>
		<tr>
			<%
				}
			%>
			<%-- FINE TICKET ALM #2845-02.1 e 02.2 --%>


			<td colspan="2">
				<h5>Estremi programma annuale</h5>
			</td>
		</tr>
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_CUILotto")%>
				for="">CUI programma triennale lavori pubblici o programma
					biennale forniture e servizi</label></th>
			<td><input maxlength="22" size=30%
				value="<h:requestParameter property='<%=ParametriServlet.FIELD_NAME_ANNUALE_CUI_MININF%>' defaultValue='<%=annualeCuiMinInf%>'/>"
				name="<%=ParametriServlet.FIELD_NAME_ANNUALE_CUI_MININF%>"
				<%=disabledTranneRipetiz%> <%=disabledTranneEscluso%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneDatiCup%><%=disabledTranneCatSoa%>><!-- MAD 68089 3.04.16 disabledTranneCatSoa --></td>
		</tr>

		<tr>
			<td colspan="2">
				<h5>Ripetizioni e rinnovi</h5>
			</td>
		</tr>
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_PrevedeRipetizioniLotto")%>
				for="<%=ParametriServlet.FIELD_FLAG_PREVEDE_RIP%>">L'appalto
					prevede ripetizioni o altre opzioni?</label></th>
			<td><c:set var="selFlagPrev" value="<%=flagPrevRip%>"></c:set>
				<select name="<%=ParametriServlet.FIELD_FLAG_PREVEDE_RIP%>"
				CLASS="BOTTONE" <%=disabledTranneEscluso%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%><%=disabledTranneCatSoa%>
				<%=disabledTranneDatiCup%> id="sel_FLAG_PREVEDE_RIP"
				onchange="setCombo('sel_FLAG_PREVEDE_RIP')"><!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
					<option value="N"
						<c:out value="${selFlagPrev =='N' ? 'selected' : ''}" />>NO</option>
					<option value="S"
						<c:out value="${selFlagPrev =='S' ? 'selected' : ''}" />>SI</option>
			</select></td>
		</tr>

		<%-- test  Ticket #20058 - 09 - 02 - 21--%>

		<%
			if (SimogProperties.getInstance()
						.isDataCreatedAfter3046(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
		%>
		<tr>
			<th><label
				<%=!fieldToHighlight.isEmpty()
							&& fieldToHighlight.containsKey("label_DurataRinnoviRipetizioni") ? "style=\"color: red;\""
									: ""%>>Durata
					dei rinnovi e delle ripetizioni in giorni</label></th>
			<td><input
				id="<%=ParametriServlet.FIELD_NAME_DURATA_RINNOVI_RIPETIZIONI%>"
				name="<%=ParametriServlet.FIELD_NAME_DURATA_RINNOVI_RIPETIZIONI%>"
				value="<%=durataRinnoviRipetizioni%>"
				onblur="validateNumber(this)" <%=disabledTranneEscluso%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneDatiCup%><%=disabledTranneCatSoa%> /><!-- MAD 68089 3.04.16 disabledTranneCatSoa --></td>
		</tr>
		<%
			}
		%>



		<%-- TICKET ALM - 3.04.3 #7849 --%>
		<%
			if (!SimogProperties.getInstance()
						.isDataCreatedAfter3043(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
		%>
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_RipetizioneLotto")%>
				for="<%=ParametriServlet.FIELD_FLAG_RIPETIZIONE%>">Ripetizione
					di precedente contratto</label></th>
			<td><c:set var="selFlagRip" value="<%=flagRipetiz%>"></c:set> <select
				name="<%=ParametriServlet.FIELD_FLAG_RIPETIZIONE%>"
				CLASS="BOTTONE" <%=disabledTranneEscluso%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledTranneDatiCup%><%=disabledTranneCatSoa%>><!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
					<option value="N"
						<c:out value="${selFlagRip =='N' ? 'selected' : ''}" />>NO</option>
					<option value="S"
						<c:out value="${selFlagRip =='S' ? 'selected' : ''}" />>SI</option>
			</select></td>
		</tr>
		<%
			}
		%>
		<%-- FINE TICKET ALM - 3.04.3 #7849 --%>

		<%-- TICKET ALM - 3.04.3 #2846 --%>
		<%-- nuovo campo Motivo collegamento CIG --%>
		<%
			if (SimogProperties.getInstance()
						.isDataCreatedAfter3043(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
		%>
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_MotivoCollegamentoLotto")%>
				title="Selezionare il motivo del concatenamento più immediato temporalmente"
				for="<%=ParametriServlet.FIELD_NAME_MOTIVO%>">L'appalto
					deriva da una delle seguenti ipotesi di collegamento?*</label></th>
			<td><input id="ID_<%=ParametriServlet.FIELD_NAME_MOTIVO%>"
				type="hidden" name="<%=ParametriServlet.FIELD_NAME_MOTIVO%>"
				value="<%=motivoCollegamentoCig%>" /> <select id="sel_MOTIVO"
				CLASS="BOTTONE" onchange="setMotivo()" <%=disabledTranneEscluso%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%><%=disabledTranneCatSoa%>
				<%=disabledTranneDatiCup%>><!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
					<option></option>
					<u:options name="<%=ParametriServlet.MOTIVO_COLLEGAMENTO_BEAN%>"
						value="<%=motivoCollegamentoCig%>" valLocation="tag" /></td>
		</tr>
		<%
			}
		%>
		<%-- FINE TICKET ALM - 3.04.3 #2846 --%>

		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_CIGCollegato")%>
				for="">CIG collegato</label></th>
			<td><input maxlength="10"
				value="<h:requestParameter property='<%=ParametriServlet.FIELD_CIG_ORIGINE_RIP%>' defaultValue='<%=cigRipetiz%>'/>"
				name="<%=ParametriServlet.FIELD_CIG_ORIGINE_RIP%>"
				<%=disabledTranneEscluso%> <%=disabledTranneDatiCup%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%><%=disabledTranneCatSoa%>><!-- MAD 68089 3.04.16 disabledTranneCatSoa --></td>
		</tr>

		<%
			} //!modificaDatiCup
		%>
		<%
			// se la gara e' stata creata prima della data di riferimento MEVCUP la sezione non deve essere
			// modificabile
			Boolean paramModIntCup = (Boolean) request.getAttribute(ParametriCup.PARAM_MOD_INT_CUP);
			paramModIntCup = paramModIntCup == null ? false : paramModIntCup;
			boolean okDataAttivazioneCup = SimogProperties.getInstance()
					.isCUPLotto(PageHelper.getFormattedDBDate(dataCreazioneGara));
			String disabledElencoCUP = okDataAttivazioneCup || paramModIntCup ? "" : "disabled";
			String noAppalti = (modificaContrattoEscluso || modificaRipetiz || !okDataAttivazioneCup) && !paramModIntCup
					? "true"
					: "false";
		%>
		<%-- 
				<tr>
				<td colspan="2" width="50%">
					<h5>CUP</h5>
					<%-- 
					<h2>paramModIntCup: <%= paramModIntCup %></h2>
					<h2>okDataAttivazioneCup: <%= okDataAttivazioneCup %></h2>
					<h2>noAppalti: <%= noAppalti %></h2>
					
				</td>
				</tr>			
--%>
		<%-- TICKET #31047 : TICKET PARI OPPORTUNITA'--%>
		<!-- MAC 34696 3.04.8 commentato if sul controllo della data di creazione della gara-->
		<!-- MAC 34696 3.04.8 commentato select togliendo i disabled -->
		<%-- <% if (SimogProperties.getInstance().isDataCreatedAfter3047(PageHelper.getFormattedDBDate(dataCreazioneGara))) { %> --%>
			<tr id="tr_cup_flagPnrrPncLotto">
				<th align="left" width="50%"><label
					<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_FlagPnrrPncLotto")%>
					for="<%=ParametriServlet.FLAG_PNRR_PNC%>"> L'appalto o 	concessione e' afferente gli investimenti pubblici finanziati, in
						tutto o in parte, con le risorse previste dal PNRR (Piano Nazionale
						di Ripresa e Resilienza) e/o dal PNC (Piano nazionale per gli
						investimenti complementari)?*</label></th>
				<td><c:set var="selFlagPnrrPnc" value="<%=flagPnrrPnc%>" scope="request"/> 
					<%-- <select onchange="disableParita(this)" id= "flagPNRR_PNC"
						name="<%=ParametriServlet.FLAG_PNRR_PNC%>" CLASS="BOTTONE" <%=disabledTranneRipetiz%> <%=disabledTranneEscluso%> <%=disabledElencoCUP%>> > --%>
						<select onchange="disableParita(this)" id= "flagPNRR_PNC"  
						name="<%=ParametriServlet.FLAG_PNRR_PNC%>" CLASS="BOTTONE" <%=disabledTranneCPV%><%=disabledTranneCatSoa%>> <!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
							<option value=""></option>
							<option value="N" <c:out value="${selFlagPnrrPnc =='N' ? 'selected' : ''}" />>NO</option>
							<option value="S" <c:out value="${selFlagPnrrPnc =='S' ? 'selected' : ''}" />>SI</option>
					</select>
				</td>
			</tr>
		<%-- <%
			}
		%> --%>



		<tr id="tr_cup_flagCup">
			<th align="left" width="50%"><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_FlagCUPLotto")%>
				for="<%=ParametriCup.FIELD_FLAG_CUP%>"> L'appalto e' finalizzato alla realizzazione di progetti d'investimento pubblico
					per i quali e' prevista l'acquisizione del codice CUP ai sensi
					dell'art. 11 L 3/2003 e ss.mm.? (E' necessario acquisire e
					comunicare il CUP per interventi finanziati, anche in parte, con
					risorse Comunitarie)*</label></th>
			<td><c:set var="selFlagCup" value="<%=flagCup%>" /> <%
 	if ("visCup".equals(fromRicerca)) {
 %>
				<c:set var="selFlagCup" value="S" /> <%
 	}
 %> <select
				name="<%=ParametriCup.FIELD_FLAG_CUP%>" CLASS="BOTTONE"
				<%=disabledTranneRipetiz%> <%=disabledTranneEscluso%> <%=disabledTrannePariOpportunita%> <%=disabledTranneCPV%>
				<%=disabledElencoCUP%><%=disabledTranneCatSoa%>><!-- MAD 68089 3.04.16 disabledTranneCatSoa -->
					<option value=""></option>
					<option value="N"
						<c:out value="${selFlagCup =='N' ? 'selected' : ''}" />>NO</option>
					<option value="S"
						<c:out value="${selFlagCup =='S' ? 'selected' : ''}" />>SI</option>
			</select></td>
		</tr>
		<tr id="tr_cup_elencoCup">
			<td colspan="2">
				<div class="inthead" style="padding-bottom: 15px">
					<label onclick="showMenu('<%=ParametriCup.TAB_CUP%>')"
						style="color: black; letter-spacing: 0.2em;"> <img
						src="img/minus.gif" id="img<%=ParametriCup.TAB_CUP%>" /> Codici
						CUP
					</label>
					<div id="<%=ParametriCup.TAB_CUP%>" style="display: block;">
						<br />
						<%-- 							<c:set var="readonly" value="<%= modificaContrattoEscluso || modificaRipetiz || !okDataAttivazioneCup %>" /> --%>
						<c:set var="readonlyCup"
							value="<%=(modificaContrattoEscluso || modificaRipetiz || !okDataAttivazioneCup) && !paramModIntCup%>" />
						<c:set var="readonlyCupStr"
							value="${readonlyCup eq true? 'readonly' : ''} " />
						<%@ include file="/include/elencoCUP.jsp"%>
					</div>
				</div>
			</td>
		</tr>

		

				<tr id="tr_cup_TipoAppalto_1">
				<td colspan="2">
					<h5>Tipologie Appalto</h5>
				</td>
				</tr>	
				<tr id="tr_cup_TipoAppalto_2">
					<td colspan="2">
						
						<!-- Tipologia Appalto per LAVORI -->
						<div class="inthead">
						<label onclick="showMenu('checkBoxListL')" 
								style="color:black; letter-spacing:0.2em;">
								<img src="img/minus.gif" id="imgcheckBoxListL"/> Tipologia lavoro (se presente la componente lavori) </label>
								<br />	
							<div id="checkBoxListL" style="display: block; margin-top: 3px;" >	
							<div class="mbody" style="display: block;">
								<table class="detailHelp" width="100%">
									<colgroup>
										<col width="60%" />
										<col width="40%" />
									</colgroup>
									<c:set var="compL" value="${requestScope['TIPO_APPALTO_LOTTO_L']}"/>
									<u:multibox campo="<%= PSBD.FIELD_NAME_TIPO_APPALTO_AGG_L %>"
										lista="<%= ParametriServlet.TIPO_APPALTO_BEAN_L %>"
										listaCampiSelezionati="compL" readonly="<%= noAppalti %>"
										idField="idAppalto"/>
								</table>.<a></a>
							</div>
							</div>
						</div>
						
						<!-- Tipologia Appalto per SERVIZI E FORNITURE -->
						<div class="inthead">
						<label onclick="showMenu('checkBoxListSF')" 
								style="color:black; letter-spacing:0.2em;">
								<img src="img/minus.gif" id="imgcheckBoxListSF"/> Modalita di acquisizione forniture / servizi </label>
								<br />	
							<div id="checkBoxListSF" style="display: block; margin-top: 3px;" >		
							<div class="mbody" style="display: block;">
								<table class="detailHelp" width="100%">
									<colgroup>
										<col width="60%" />
										<col width="40%" />
									</colgroup>
									<c:set var="compSF" value="${requestScope['TIPO_APPALTO_LOTTO_SF']}" />
									<u:multibox campo="<%= PSBD.FIELD_NAME_TIPO_APPALTO_AGG_SF %>"
										lista="<%= ParametriServlet.TIPO_APPALTO_BEAN_F %>"
								 	listaCampiSelezionati="compSF" readonly="<%= noAppalti %>"
										idField="idAppalto"/>
								</table>
							</div>
							</div>
						</div>						
					</td>
				</tr>
				
				
		<%-- TICKET #31047 : TICKET PARI OPPORTUNITA' START.....--%>
		<%--
 		<%if(SimogProperties.getInstance().isDataCreatedAfter3047(PageHelper.getFormattedDBDate(dataCreazioneGara))) {  %>	
			
					<tr id="tr_pariopportunita_1_mod2">
						<td colspan="2">
							<h5>Tutela delle pari opportunità generazionali e di genere nonchè per l'inclusione delle persone con disabilità</h5>
						</td>
					</tr>	
								<!--  previsione quota  -->
								<tr id="tr_cup_flagPrevisioneQuota">
										<th align="left" width="50%"><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_FlagPrevisioneQuota") %> for="<%= ParametriServlet.FLAG_PREVISIONE_QUOTA %>"> 
											Previsione nel bando di gara, nell'avviso o nell'invito, dell'obbligo di assicurare, in caso di aggiudicazione del contratto, una quota pari almeno al 30 per cento, delle assunzioni necessarie per l'esecuzione del contratto o per la realizzazione di attività ad esso connesse o strumentali, sia all'occupazione giovanile sia all'occupazione femminile (articolo 47, comma 4, D.L. 77/2021)?	<td>
										
										<c:set var="selFlagPrevisioneQuota" value="<%= flagPrevisioneQuota %>"/>
										<select  id="flagPrevisioneQuotaHere" name="<%= ParametriServlet.FLAG_PREVISIONE_QUOTA %>" CLASS="BOTTONE"  <%=disabledTranneRipetiz %> <%=disabledTranneEscluso%> <%= disabledElencoCUP %> >
											<option value=""></option>
								  			<option value="N" <c:out value="${selFlagPrevisioneQuota =='N' ? 'selected' : ''}" />>NO</option>
								  			<option value="S" <c:out value="${selFlagPrevisioneQuota =='S' ? 'selected' : ''}" />>SI</option>
								  			<option value="Q" <c:out value="${selFlagPrevisioneQuota =='Q' ? 'selected' : ''}" />>SI con previsione di una quota inferiore</option>
								  		</select> 
								  		</td>
									</tr>
									
								
						
	           
								<tr>
									<th><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"labelQuotaGiovanile") %>>Previsione di una quota inferiore con riferimento all'occupazione giovanile</label></th>
										<td>
										<input onblur="validateNumber(this)"  id="quota_giovanile" <c:if test="${selFlagPrevisioneQuota != 'Q'}" var="flag"><c:out value="${flag =='true' ? 'disabled' : ''}"/></c:if>
											 type="string" style="text-align:right;" name="<%= ParametriServlet.QUOTA_GIOVANILE %>" 
											 value="<h:requestParameter property='<%=ParametriServlet.QUOTA_GIOVANILE%>' defaultValue='<%=quotaGiovanile%>'/>" 	/>
										</td>
								</tr>
								<tr>
								<th><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"labelQuotaFemminile") %>>Previsione di una quota inferiore con riferimento all'occupazione femminile</label></th>
										<td>
											<input onblur="validateNumber(this)"  id="quota_femminile" <c:if test="${selFlagPrevisioneQuota != 'Q'}" var="flag"><c:out value="${flag =='true' ? 'disabled' : ''}"/></c:if>
											 type="string" style="text-align:right;" name="<%= ParametriServlet.QUOTA_FEMMINILE %>" 
											 	 value="<h:requestParameter property='<%=ParametriServlet.QUOTA_FEMMINILE%>' defaultValue='<%=quotaFemminile%>'/>" 	/>
										</td>
								</tr>
								
									
								<!--  motivo deroga -->
								<tr id="tr_pariopportunita_2_mod2">
									<td colspan="2">
										
										<!-- Tipologia Appalto per LAVORI -->
										<div class="inthead" >
										<label onclick="showMenu('checkBoxListL1')" 
												style="color:black; letter-spacing:0.2em;">
												<img src="img/minus.gif" id="imgcheckBoxListL"/> Motivo deroga</label>
											<br />	
											<div id="checkBoxListL1" style="display: block; margin-top: 3px;"  >	
											<div class="mbody" style="display: block;">
												<table class="detailHelp" width="100%">
												<tbody>
												 	<tr>				
												 										
														<c:set var="selDeroga" value="${requestScope['MOTIVO_DEROGA_BEAN_SELECTED']}" />
														<u:multibox campo="<%=PSBD.FIELD_NAME_MOTIVO_DEROGA%>"
															lista="<%=ParametriServlet.MOTIVO_DEROGA_BEAN%>"
															disabled="true" listaCampiSelezionati="selDeroga"
															readonly="<%=noAppalti%>" idField="idMotivoDeroga" />
												 	</tr>
												</tbody>
													
												</table>
											</div>
											</div>
										</div>
					
									</td>
								</tr>
								
						
						

								<tr id="test">
									<th align="left" width="50%"><label
										<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_FlagMisurePremialiLotto")%>
										for="<%=ParametriServlet.FLAG_MISURE_PREMIALI%>"> Previsione nel bando di gara, nell'avviso o nell'invito di ulteriori misure
											premiali che attribuiscono un punteggio aggiuntivo all'offerente o al candidato</label></th>
										<td><c:set var="selFlagMisure" value="<%=flagMisurePremiali%>" /> 
											<select  id="flagMisurePremialiHere"
												name="<%=ParametriServlet.FLAG_MISURE_PREMIALI%>" CLASS="BOTTONE" <%=disabledTranneRipetiz%> <%=disabledTranneEscluso%> <%=disabledElencoCUP%>> 
													<option value=""></option>
													<option value="N" <c:out value="${selFlagMisure =='N' ? 'selected' : ''}" />>NO</option>
													<option value="S" <c:out value="${selFlagMisure =='S' ? 'selected' : ''}" />>SI</option>
											</select>
										</td>
										
										
										
								</tr>
		
			
								<tr id="tr_pariopportunita_3">
								<td colspan="2">
									<!-- Tipologia Misura Premiale -->
									<div class="inthead">
										<label onclick="showMenu('checkBoxListL2')" style="color: black; letter-spacing: 0.2em;"> <img
											src="img/minus.gif" id="imgcheckBoxListL" /> Misure Premiali
										</label> 
										<br />
										<div id="checkBoxListL2" style="display: block; margin-top: 3px;">
											<div class="mbody" style="display: block;">
												<table class="detailHelp" width="100%">
													<tbody>
														<tr>
					
															<c:set var="compL1" value="${requestScope['MISURA_PREMIALE_BEAN_SELECTED']}" />
															<u:multibox campo="<%=PSBD.FIELD_NAME_MISURA_PREMIALE%>"
																lista="<%=ParametriServlet.MISURA_PREMIALE_BEAN%>"
																disabled="true" listaCampiSelezionati="compL1" 
																readonly="<%=noAppalti%>" idField="idMisuraPremiale" />
					
																
					
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</td>
							</tr>
		
		<% }%>--%>
		<%-- TICKET #31047 : TICKET PARI OPPORTUNITA' END--%>


	</tbody>
</table>
<%
	// ATTENZIONE VIENE BLOCCATA LA TIPOLOGIA A "LAVORI PUBBLICI"
%>
<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_TIPOLOGIA%>"
	id="tipologia"
	value="<%=ParametriServlet.TIPOLOGIA_LAVORI_PUBBLICI%>">
<input type="hidden"
	name="<%=ParametriServlet.FIELD_NAME_FLAG_SA_NO_DPCM%>"
	id="flagSaNoDPCM" value="<%=flagNoDPCM%>">
<input type="hidden"
	name="<%=ParametriServlet.FIELD_NAME_FLAG_SA_NO_CLASSIFICATA%>"
	id="flagSaNoClassificata" value="<%=flagSANoClassificata%>">
<input type="hidden"
	name="<%=ParametriServlet.FIELD_NAME_CIG_INIZIATIVA_SEL%>"
	id="cigIniziativa" value="<%=cigIniziativa%>">			
	
<input type="hidden" id="cig"  value="<%=modificaDatiCup%>">	

<script type="text/javascript" src="script/customAlertBox.js">

</script>