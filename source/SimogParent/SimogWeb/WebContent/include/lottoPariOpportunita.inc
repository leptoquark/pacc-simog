<%@page import="it.avlp.simog.util.SimogProperties"%>
<!-- fine import popup modali -->

<table cellpadding="3">
	<input type="hidden" id="canSearch" value="false" />
	<tbody>
		<%-- TICKET #31047 : TICKET PARI OPPORTUNITA' START.....
		MEV 37010 3.04.8.1 --%>
		<%
			if (SimogProperties.getInstance()
					.isDataCreatedAfter3047(PageHelper.getFormattedDBDate(dataCreazioneGara)) || integraPariOpportunita) {
		%>
		<tr id="tr_pariopportunita_1_mod2">
			<td colspan="2">
				<h5>Tutela delle pari opportunità generazionali e di genere
					nonchè per l'inclusione delle persone con disabilità</h5>
			</td>
		</tr>
		<!--  previsione quota  -->
		<tr id="tr_cup_flagPrevisioneQuota">
			<th align="left" width="50%"><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_FlagPrevisioneQuota")%>
				for="<%=ParametriServlet.FLAG_PREVISIONE_QUOTA%>">
					Previsione nel bando di gara, nell'avviso o nell'invito,
					dell'obbligo di assicurare, in caso di aggiudicazione del
					contratto, una quota pari almeno al 30 per cento, delle assunzioni
					necessarie per l'esecuzione del contratto o per la realizzazione di
					attività ad esso connesse o strumentali, sia all'occupazione
					giovanile sia all'occupazione femminile (articolo 47, comma 4, D.L.
					77/2021)?
					<td><c:set var="selFlagPrevisioneQuota"
							value="<%=flagPrevisioneQuota%>" /> <select
						id="flagPrevisioneQuotaHere"
						<c:if test="${selFlagPnrrPnc != 'S'}" var="flag"><c:out value="${flag =='true' ? 'disabled' : ''}"/></c:if>
						name="<%=ParametriServlet.FLAG_PREVISIONE_QUOTA%>" CLASS="BOTTONE"
						<%=disabledTranneRipetiz%> <%=disabledTranneEscluso%> <%=disabledEreditati%> 
						<%=disabledElencoCUP%>>
							<option value=""></option>
							<option value="N"
								<c:out value="${selFlagPrevisioneQuota =='N' ? 'selected' : ''}" />>NO</option>
							<option value="S"
								<c:out value="${selFlagPrevisioneQuota =='S' ? 'selected' : ''}" />>SI</option>
							<option value="Q"
								<c:out value="${selFlagPrevisioneQuota =='Q' ? 'selected' : ''}" />>SI
								con previsione di una quota inferiore</option>
					</select></td>
		</tr>
		<% 
		
		if (quotaGiovanile != null && !"".equals(quotaGiovanile))
			quotaGiovanile = PageHelper
			.formattaImporto(new BigDecimal(PageHelper.formattaImporto(quotaGiovanile)));
		
		if (quotaFemminile != null && !"".equals(quotaFemminile))
			quotaFemminile = PageHelper
			.formattaImporto(new BigDecimal(PageHelper.formattaImporto(quotaFemminile)));
		
		%>
		
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "labelQuotaGiovanile")%>>Previsione
					di una quota inferiore con riferimento all'occupazione giovanile</label></th>
			<td><input onblur="validatePercentage(this)" id="quota_giovanile" <%=disabledEreditati%> 
				<c:if test="${selFlagPrevisioneQuota != 'Q'}" var="flag"><c:out value="${flag =='true' ? 'disabled' : ''}"/></c:if>
				<c:if test="${selFlagPnrrPnc =='N'}" var="flag2"><c:out value="${flag2 =='true' ? 'disabled' : ''}"/></c:if>
				type="string" style="text-align: right;"
				name="<%= ParametriServlet.QUOTA_GIOVANILE %>"
				value="<h:requestParameter property='<%=ParametriServlet.QUOTA_GIOVANILE%>' defaultValue='<%=quotaGiovanile%>'/>" />
			</td>
		</tr>
		<tr>
			<th><label
				<%=SimogFlags.checkHighlightField(fieldToHighlight, "labelQuotaFemminile")%>>Previsione
					di una quota inferiore con riferimento all'occupazione femminile</label></th>
			<td><input onblur="validatePercentage(this)" id="quota_femminile"  <%=disabledEreditati%> 
				<c:if test="${selFlagPrevisioneQuota != 'Q'}" var="flag"><c:out value="${flag =='true' ? 'disabled' : ''}"/></c:if>
				type="string" style="text-align: right;"
				name="<%= ParametriServlet.QUOTA_FEMMINILE %>"
				value="<h:requestParameter property='<%=ParametriServlet.QUOTA_FEMMINILE%>' defaultValue='<%=quotaFemminile%>'/>" />
			</td>
		</tr>
		<!--  motivo deroga -->
		<tr id="tr_pariopportunita_2_mod2">
			<td colspan="2">
				<!-- Tipologia Appalto per LAVORI -->
				<div class="inthead">
					<label onclick="showMenu('checkBoxListL1')"
						style="color: black; letter-spacing: 0.2em;"> <img
						src="img/minus.gif" id="imgcheckBoxListL" /> Motivo deroga
					</label> <br />
					<div id="checkBoxListL1" style="display: block; margin-top: 3px;">
						<div class="mbody" style="display: block;">
							<table class="detailHelp" width="100%">
								<tbody>
									<tr>
										<c:set var="selDeroga"
											value="${requestScope['MOTIVO_DEROGA_BEAN_SELECTED']}" />
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
				for="<%=ParametriServlet.FLAG_MISURE_PREMIALI%>"> Previsione
					nel bando di gara, nell'avviso o nell'invito di ulteriori misure
					premiali che attribuiscono un punteggio aggiuntivo all'offerente o
					al candidato</label></th>
			<td><c:set var="selFlagMisure" value="<%=flagMisurePremiali%>" />
				<select id="flagMisurePremialiHere" 
				<c:if test="${selFlagPnrrPnc != 'S'}" var="flag"><c:out value="${flag =='true' ? 'disabled' : ''}"/></c:if>
				name="<%=ParametriServlet.FLAG_MISURE_PREMIALI%>" CLASS="BOTTONE"
				<%=disabledTranneRipetiz%> <%=disabledTranneEscluso%> <%=disabledEreditati%> 
				<%=disabledElencoCUP%>>
					<option value=""></option>
					<option value="N"
						<c:out value="${selFlagMisure =='N' ? 'selected' : ''}" />>NO</option>
					<option value="S"
						<c:out value="${selFlagMisure =='S' ? 'selected' : ''}" />>SI</option>
			</select></td>
		</tr>
		<tr id="tr_pariopportunita_3">
			<td colspan="2">
				<!-- Tipologia Misura Premiale -->
				<div class="inthead">
					<label onclick="showMenu('checkBoxListL2')"
						style="color: black; letter-spacing: 0.2em;"> <img
						src="img/minus.gif" id="imgcheckBoxListL" /> Misure Premiali
					</label> <br />
					<div id="checkBoxListL2" style="display: block; margin-top: 3px;">
						<div class="mbody" style="display: block;">
							<table class="detailHelp" width="100%">
								<tbody>
									<tr>
										<c:set var="compL1"
											value="${requestScope['MISURA_PREMIALE_BEAN_SELECTED']}" />
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
		<%
			}
		%>
		<%-- TICKET #31047 : TICKET PARI OPPORTUNITA' END--%>
	</tbody>
</table>
	
<script type="text/javascript" src="script/customAlertBox.js">
	
</script>