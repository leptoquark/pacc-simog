<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletRubrica"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean"%>
<c:set var="readonlyAffid" value="${roByFlusso eq true or (includerConfirmed and variazioniAnagrafiche ne true)}" />
<c:set var="readonlyAffidStr" value="${readonlyAffid ? 'readonly' : ''} " />
<script type="text/javascript">
var contAffid=${fn:length(aggiudicatari)};
  function setImportoDiAggiudicazione(element, noSup){
	  	if(!(element.value.length > 0)){
			return false;
		}
		if(!validatePercentage(element, noSup)){
			return false;
		}
		
		if(document.getElementById('IMPORTO_COMPLESSIVO').value == null || document.getElementById('IMPORTO_COMPLESSIVO').value <= 0 ){
			return false;
		}
		subtot = parseFloat(document.getElementById('IMPORTO_COMPLESSIVO').value.replace(/\./g,"").replace(',','.'));
		
		importo = 0;
		if(element.id == 'ribAgg'){
		    var perc = parseFloat(element.value.replace(',','.'))/100.00
			perc = eval(1.00-perc)		
			importo = (parseFloat(subtot)* perc) ;
		}else{
			var perc = parseFloat(element.value.replace(',','.'))/100.00
			perc = eval(1.00+perc)
			importo = (parseFloat(subtot)* perc) ;
		}
		
		if(document.getElementById('IMPORTO_ATTUAZIONE_SICUREZZA').value == ""){
			sicurezza = parseFloat('0');
		}else{
			sicurezza = parseFloat(document.getElementById('IMPORTO_ATTUAZIONE_SICUREZZA').value.replace(/\./g,"").replace(',','.'));}
	
		importo = importo + sicurezza;
		
		document.getElementById('euroHidden').value = addMyDotsFromCommaString(importo.toFixed(3).replace('.',','));
		return true;
	}
	function copyValueElement(dest,src){
		dest.value = src.value;
		focusOnField(dest);
	}
	function modAnag(){
	apriPopUpMod('rubrica','<%= PSBD.TAB_AFFIDATARIO %>',document.getElementById('<%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE%>'),document.getElementById('<%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG%>'),'Rubrica Operatori Economici',document.getElementById('<%= PSBD.FIELD_NAME_ANAGOE %>').value);
	}	
	</script>
	<div id="dialogDitteAusiliarie"></div>
	<table  ${variazioniAnagrafiche eq true ? 'style="display:none;"' : ''}>
	 <colgroup>
		<col width="60%"/>
		<col width="40%"/>
     </colgroup> 
		<tr>
			<th><label for="">${markFieldAVCPass} Ribasso di aggiudicazione</label></th>
			<td>
				<input ${readonlyAffidStr} ${readonlyAVCPass} id="ribAgg" tabindex="<%=++indiceTab%>" onblur="calcoloAggiudicazione(this,document.getElementById('percAum'),'<%= PSBD.AGGIUDICATARIO %>')" style="text-align: right;" type="text" size="9" maxlength="9" name="<%= PSBD.FIELD_NAME_PERC_RIBASSO_AGG %>" value="<c:out value="${aggiudicazione.percRibassoAggStr}" />" onchange="setFormModified('Modificato0')" >
			</td>
		</tr>
			<tr>
				<th><label for="">${markFieldAVCPass} <utils:message key="scheda.offertaInAumento" /></label></th>
				<td>
				<input ${readonlyAffidStr} ${readonlyAVCPass} id="percAum" tabindex="<%=++indiceTab%>" onblur="calcoloAggiudicazione(document.getElementById('ribAgg'),this,'<%= PSBD.AGGIUDICATARIO %>')"  style="text-align: right;" type="text" size="9" maxlength="9" name="<%= PSBD.FIELD_NAME_PERC_OFF_AUMENTO %>" value="<c:out value="${aggiudicazione.percOffAumentoStr}" />" onchange="setFormModified('Modificato0')">			
				</td>
			</tr>
			<tr>
				<th><label for=""><utils:message key="scheda.importoAggiudicazioneAffidamento" />*</label></th>
				<td>
				<fmt:formatNumber value="${impAgg}" var="importoA"  type="value" minIntegerDigits="3" maxIntegerDigits="3"/> 
				<input tabindex="<%=++indiceTab%>" id="euro" type="text" style="text-align:right;font-weight:bold;width: 120px" name="<%= PSBD.FIELD_NAME_IMPORTO_AGGIUDICAZIONE %>" value="<c:out value="${aggiudicazione.importoAggiudicazioneStr}" />"
					  ${readonlyAffidStr } 
					   onchange="setFormModified('Modificato0')" 
					   onblur="validateAmount(this)" />
				<input id="euroHidden" type="hidden" />
				<c:if test="${readonlyAffid ne true && !fromAVCPass}">
					<a title="<utils:message key="scheda.calcolaImportoAggiudicazione" />" href="javascript:copyValueElement(document.getElementById('euro'),document.getElementById('euroHidden'))">
					<img src="img/calc_icon.gif"/></a>
				</c:if>			
						
				</td>
			</tr>
			<tr>			
				<th><label for="">${markFieldAVCPass} <utils:message key="scheda.dataAggiudicazioneDefinitiva" />*</label></th>
				<td>
					<input tabindex="<%=++indiceTab%>" style="text-align:center" ${readonlyAffidStr} ${readonlyAVCPass}  onchange="setFormModified('Modificato0')" onblur="Calendar.validaData(this)" type="text" id="dtaggdef" name="<%= PSBD.FIELD_NAME_DATA_AGGIUDICAZIONE_DEFINITIVA %>"  value="<c:out value="${aggiudicazione.dataVerbaleAggiudicazione}" />">
					<c:if test="${readonlyAffid ne true && !fromAVCPass}">
						<img src="calendar/img.gif" id="CALdtaggdef" style="cursor: pointer; border: 1px solid red;" title="Date selector"
  							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
						<script type="text/javascript">
						    Calendar.setup({
						        inputField     :    "dtaggdef",     
						        ifFormat       :    "%d/%m/%Y",     
						        button         :    "CALdtaggdef",  
							    align          :    "Tl",           
							    singleClick    :    true
						    });
						</script>
					</c:if>								
				</td>
				</tr>
				<tr>			
				<th><label for=""><utils:message key="scheda.dataStipulaContratto" /></label></th>
				<td>
					<input tabindex="<%=++indiceTab%>" style="text-align:center" ${readonlyAffidStr} onchange="setFormModified('Modificato0')" onblur="Calendar.validaData(this)" type="text" id="dtstip" name="<%= PSBD.FIELD_NAME_DATA_STIPULA %>"  value="${aggiudicazione.dataStipula}">
					<c:if test="${readonlyAffid ne true}">
						<img src="calendar/img.gif" id="CALdtstip" style="cursor: pointer; border: 1px solid red;" title="Date selector"
  							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
						<script type="text/javascript">
						    Calendar.setup({
						        inputField     :    "dtstip",    
						        ifFormat       :    "%d/%m/%Y",  
						        button         :    "CALdtstip", 
							    align          :    "Tl",         
							    singleClick    :    true
						    });
						</script>
					</c:if>								
				</td>
				</tr>
				<tr>
				<th><label for=""><utils:message key="scheda.durataContrattualeGiorni" /></label></th>
				<td>
					<input id="durContr" tabindex="<%=++indiceTab%>" ${readonlyAffidStr} style="text-align: right;" type="text" size="9" maxlength="9" name="<%= PSBD.FIELD_NAME_DURATA_CONTRATTUALE %>" value="${aggiudicazione.durataContrattuale}" onblur="validateNumber(this)" onchange="setFormModified('Modificato0')">
				</td>
			</tr>
		  	
		</table>
		 
			<% String prefixAgg = PSBD.AGGIUDICATARIO; %>
		<c:set var="prefixAgg" value="<%= prefixAgg %>" scope="page" />	
		
		<!--gm hidden per disabilitare il tasto gestione ditte ausiliarie -->	
		<input type="hidden" id="sottosogliaEsclusi" value="si" />
		<input type="hidden" id="<%= PSBD.FIELD_NAME_READONLY_AFFIDATARIO %>" name="<%= PSBD.FIELD_NAME_READONLY_AFFIDATARIO %>" value="<c:out value="${readonlyAffid}" />">
		
		<div id="DIVTabella<%= prefixAgg %>" class="scrollTabs" style="height: 200px; width: 99%;">
			<table id="idTabella<%= prefixAgg %>">
				<tbody>
					<tr>
						<th width="125"><utils:message key="table.azione" /></th>					
						<th class="garaTh"><utils:message key="table.denominazione" /></th>					
						<th class="garaTh"><utils:message key="table.codiceFiscale" /></th>		
						<th class="garaTh"><utils:message key="table.codicePaese" /></th>
						<th class="garaTh"><utils:message key="table.tipo" /></th>
						<!-- Rinaldo tiket 654 -->
						<th class="garaTh"><utils:message key="table.importoAggiudicazione" /></th>
						<th class="garaTh"><utils:message key="table.ribassoAggiudicazione" /></th>
						<th class="garaTh"><utils:message key="table.offertaInAumento" /></th>
						<!-- ///////////////// -->
						<%-- <th class="garaTh">Ruolo</th>
						<th class="garaTh">Progressivo Raggruppamento (1)</th>--%>
						<th class="garaTh">Selezione</th>
					</tr>
					<c:set var="counter" value="0" /> 
					<c:forEach var="aggCorrente" items="${aggiudicatari}">
						<c:set var="id" value="row${prefixAgg}${counter}" scope="page"/>
						<tr id="<c:out value="${id}" />">
							<c:set var="soggPartecipante" value="${aggCorrente.soggettoPartecipante}" />
							<c:set var="parametri" value="ID_TABELLA_AFFIDATARI=${id}&SOGGETTI_PARTECIPANTIDENOMINAZIONE=${soggPartecipante.denominazionePulitaSl}&CODICE_FISCALE=${soggPartecipante.codiceFiscale}&SOGGETTI_PARTECIPANTIID_STATO=${soggPartecipante.id_stato}&RuoloAgg=${aggCorrente.ruolo}&ID_GRUPPO=${aggCorrente.idGruppo}&ID_TIPOAGG=${aggCorrente.idTipoAgg}&readonlyAffidatario=${readonlyAffid || fromAVCPass}&variazioniAnagrafiche=${variazioniAnagrafiche}"/>				
							<c:if test="${readonlyAffid ne true && !fromAVCPass}">
								<td nowrap="nowrap" class="hmenu">
									<a title="<utils:message key="scheda.modificaAggiudicatario" />" href="javascript:setForModifyRow('<c:out value="${id}" />',[<%= PSBD.argsAggiud %>],[<%=PSBD.argsAggiudNascosti%>],'<%=prefixAgg%>')"><utils:message key="button.modifica" /></a>
									&nbsp;<a title="<utils:message key="button.cancella" />" href="javascript:deleteRow('<c:out value="${id}" />',[<%= PSBD.argsAggiud %>],[<%=PSBD.argsAggiudNascosti%>],'<%=prefixAgg%>')"><utils:message key="button.cancella" /></a></td>																		   
							</c:if>
							<c:if test="${readonlyAffid eq true || fromAVCPass}">
								<td>&nbsp;</td>
							</c:if>						
							<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>"><c:out value="${soggPartecipante.denominazionePulita}" /></td>																
							<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>"><c:out value="${soggPartecipante.codiceFiscale}" /></td>
							<td nowrap class="garaTd" id="<c:out value="${id}" /><%=PSBD.FIELD_NAME_AGG_ID_PAESE%>"><c:out value="${(soggPartecipante.id_stato != null)&&(soggPartecipante.id_stato != '')? soggPartecipante.id_stato : 'IT'}" /></td>   <%-- UN: Non utilizzo PaesiManager.CODICE_STATO_ITALIANO altrimenti va byte limit exceed --%>
							<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_IMP_AGGIUDICATARIO %>"><c:out value="${aggCorrente.impAggiudicatarioStr}" /></td>
							<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PERC_RIBASSO_AGGIUDICATARIO %>"><c:out value="${aggCorrente.percRibassoAggiudicatarioStr}" /></td>
							<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PERC_AUMENTO_AGGIUDICATARIO %>"><c:out value="${aggCorrente.percAumentoAggiudicatarioStr}" /></td>

 						   <%--
 						   <td class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_RUOLO %>">
								<c:choose>
									<c:when test="${aggCorrente.ruolo eq 1}">Mandataria</c:when>
									<c:when test="${aggCorrente.ruolo eq 2}">Mandante</c:when>		
								</c:choose>
							</td>	
							--%>
							<td nowrap class="garaTd" id="<c:out value="${id}" /><%=PSBD.FIELD_NAME_AGG_TIPO%>">
 						      <c:choose>
									<c:when test="${aggCorrente.idTipoAgg eq 1}"><%=PSBD.FIELD_NAME_AGG_ATI %></c:when>
									<c:when test="${aggCorrente.idTipoAgg eq 2}"><%=PSBD.FIELD_NAME_AGG_CONSORZIO %></c:when>
									<c:when test="${aggCorrente.idTipoAgg eq 3}"><%=PSBD.FIELD_NAME_AGG_IMPRESA_SINGOLA %></c:when>		
									<c:when test="${aggCorrente.idTipoAgg eq 4}"><%=PSBD.FIELD_NAME_AGG_GEIE %></c:when>	
									<c:when test="${aggCorrente.idTipoAgg eq 5}"><%=PSBD.FIELD_NAME_ASSOCIAZIONE_CATEGORIA %></c:when>			
								</c:choose>
 						   </td>	
 						<!-- Rinaldo tiket 654 -->
						<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_IMP_AGGIUDICATARIO %>"><c:out value="${aggCorrente.impAggiudicatarioStr}" /></td>
						<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PERC_RIBASSO_AGGIUDICATARIO %>"><c:out value="${aggCorrente.percRibassoAggiudicatarioStr}" /></td>
						<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PERC_AUMENTO_AGGIUDICATARIO %>"><c:out value="${aggCorrente.percAumentoAggiudicatarioStr}" /></td>
						<!-- ///////////////// --> 	
 						   <%--gm aggiunto per NUOVI raggruppamenti d'impresa--%>					 
						    <%-- <c:if test="${readonlyAffid ne true}">--%>
						       <c:if test="${aggCorrente.idTipoAgg eq 1 or aggCorrente.idTipoAgg eq 2}">	 
							      <td nowrap="nowrap" class="hmenu" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_PARAMETRI_GRUPPI %>">
                             <a title="<utils:message key="scheda.gestioneRaggruppamentoImpresa" />" href="javascript:apriPopUpRubricaDittaAusiliaria('<c:out value="${id}" />',[<%= PSBD.argsRaggruppamento %>],[<%=PSBD.argsRaggruppamentoNascosti%>],'<%=prefixAgg%>','rubricaRaggruppamento','<%= PSBD.TAB_AFFIDATARIO %>','<utils:message key="scheda.gestioneRaggruppamentoImpresa" />','<c:out value="${parametri}" />')"><utils:message key="scheda.gestioneRaggruppamentoImpresa" /></a>						 
							      </td>
							    </c:if>
							    <c:if test="${aggCorrente.idTipoAgg ne 1 and aggCorrente.idTipoAgg ne 2}">
							      <td nowrap="nowrap" class="hmenu" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_PARAMETRI_GRUPPI %>"></td>	
							    </c:if>
							  <%--</c:if>
							  <c:if test="${readonlyAffid eq true}">
								<td>&nbsp;</td>
							  </c:if>--%>
							<%-- 				
							<td class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_GRUPPO %>"><c:out value="${aggCorrente.idGruppo}" /></td>												
							<c:if test="${readonlyAffid eq true}">
								<td>&nbsp;</td>
							</c:if>
							--%>		 						
							<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE %>" style="display: none;"><c:out value="${soggPartecipante.idSoggettoPartecipante}" /></td>
							<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG %>" style="display: none;"><c:out value="${soggPartecipante.dataInizioSogg}" /></td>
							<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_RUOLO %>" style="display: none;"><c:out value="${aggCorrente.ruolo}" /></td>
							<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>" style="display: none;"><c:out value="${aggCorrente.idTipoAgg}" /></td>						
							<!-- Rinaldo tiket 654 -->
							<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_IMP_AGGIUDICATARIO %>" style="display: none;"><c:out value="${aggCorrente.impAggiudicatarioStr}" /></td>
							<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PERC_RIBASSO_AGGIUDICATARIO %>" style="display: none;"><c:out value="${aggCorrente.percRibassoAggiudicatarioStr}" /></td>
							<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PERC_AUMENTO_AGGIUDICATARIO %>" style="display: none;"><c:out value="${aggCorrente.percAumentoAggiudicatarioStr}" /></td>
							<!-- ///////////////// -->
							<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_ANAGOE %>" style="display: none"><c:out value="${soggPartecipante.datiModifica}" /></td>                  
                     <td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE %>" value="<c:out value="${soggPartecipante.idSoggettoPartecipante}" />"></td>
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG %>" value="<c:out value="${soggPartecipante.dataInizioSogg}" />"></td>										
                     <td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>" value="<c:out value="${soggPartecipante.denominazione}" />"></td>	
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_PAESE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_PAESE%>" value="<c:out value="${soggPartecipante.id_stato}" />"></td>	
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>" value="<c:out value="${soggPartecipante.codiceFiscale}" />"></td>
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_RUOLO %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_RUOLO %>" value="<c:out value="${aggCorrente.ruolo}" />"></td>
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG %>" value="<c:out value="${aggCorrente.idTipoAgg}" />"></td>
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_FLAG_AVVALIMENTO %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_FLAG_AVVALIMENTO %>" value="<c:out value="${aggCorrente.flagAvvalimento}" />"></td>
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_CF_AUSILIARIA %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_CF_AUSILIARIA %>" value="<c:out value="${aggCorrente.cfAusiliaria}" />"></td>																													
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_GRUPPO %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_GRUPPO %>" value="<c:out value="${aggCorrente.idGruppo}" />"></td>																						
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_LISTA_AUSILIARIE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_LISTA_AUSILIARIE %>" value="<c:out value="${aggCorrente.ditteAusiliarieString}" />"></td>																										
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_TIPO %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_TIPO %>" value="<c:out value="${aggCorrente.idTipoAgg}" />"></td>																																		
						   	<!-- Rinaldo tiket 654 -->
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_IMP_AGGIUDICATARIO %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_IMP_AGGIUDICATARIO %>" value="<c:out value="${aggCorrente.impAggiudicatarioStr}" />"></td>
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PERC_RIBASSO_AGGIUDICATARIO %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PERC_RIBASSO_AGGIUDICATARIO %>" value="<c:out value="${aggCorrente.percRibassoAggiudicatarioStr}" />"></td>
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PERC_AUMENTO_AGGIUDICATARIO %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PERC_AUMENTO_AGGIUDICATARIO %>" value="<c:out value="${aggCorrente.percAumentoAggiudicatarioStr}" />"></td>
							<!-- ///////////////// -->
						   <%-- gm aggiunto per raggruppamenti di impresa --%>
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_GRUPPO %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_ID_GRUPPO %>" value="<c:out value="${aggCorrente.idGruppo}" />"></td>																						
						   <%--gm aggiunto per NUOVI raggruppamenti d'impresa--%>
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_LISTA_GRUPPI %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_LISTA_GRUPPI %>" value="<c:out value="${aggCorrente.ditteRaggruppamentoString}" />"></td> 
							<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_ANAGOE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_ANAGOE %>" value="<c:out value="${soggPartecipante.datiModifica}" />"></td>					
						</tr>
						<c:set var="counter" value="${counter + 1}" scope="page"/>
					</c:forEach>																
				</tbody>
			</table>
		</div>	  	
		<div>
		<p class="garaTh">Per visualizzare/modficare il raggruppamento d'impresa premere il pulsante "Gestione Raggruppamento d'Impresa"</p>
		</div>			
		<c:if test="${readonlyAffid ne true && !fromAVCPass}">
			<div class="hmenu"><a id="showHide<%= PSBD.AGGIUDICATARIO %>Button" title="Aggiungi <%= prefixAgg %>" href="javascript:showSezioneAggiungi([<%= PSBD.argsAggiud %>],[<%=PSBD.argsAggiudNascosti%>],'<%=prefixAgg%>')" title="Aggiungi <%= prefixAgg %>">Aggiungi Aggiudicatario</a></div>
		</c:if>			
		<div class="detailHelp" id="divAgg<%= prefixAgg %>" style="display: none; border: 1px solid #cfcfcf;">
			<table width="100%">
				<tr>
					<th><label for=""><utils:message key="scheda.codiceFiscaleAggiudicatarioAffidatario" /></label></th>
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO %>" maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixAgg%>')" /> 	
					    <input type="text" id="<%= PSBD.FIELD_NAME_AGG_ID_PAESE%>" maxlength="2" size="2" value="" disabled onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
					<td ${variazioniAnagrafiche eq true ? '' : 'style="display:none;"'}>
					<div class="hmenu"><a title="<utils:message key="scheda.modificaAnagrafica" />" href="javascript:modAnag();"><utils:message key="scheda.modificaAnagrafica" /></a></div></td>
					</td>
					<td>
						<div class="hmenu"><a title="<utils:message key="scheda.cercaInRubrica" />" href="javascript:apriPopUpRubrica('rubrica','<%= PSBD.TAB_AFFIDATARIO %>','<utils:message key="rubrica.rubricaOperatoriEconomici" />')"><utils:message key="scheda.cercaInRubrica" /></a></div>						
					</td>					
				</tr>
				<tr>
					<th><label><utils:message key="table.denominazione" /></label></th>
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
					<th><label>Tipo Aggiudicatario</label></th>
					<td colspan="2">
						<select id="<%= PSBD.FIELD_NAME_AGG_ID_TIPO_AGG%>" CLASS="BOTTONE" onchange="setFormModified('Modificato<%=prefixAgg%>')">
							<option></option>
							<u:options name="<%= ParametriServlet.TIPO_AGGIUDICATARIO_BEAN %>" scope="request" />
						</select>
					</td>					
				</tr>
				<tr style="display: none;">
				<td>
					<input type="text" id="<%= PSBD.FIELD_NAME_AGG_TIPO %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
				</td>	
				</tr>	
				
				<!-- Rinaldo tiket 654 -->
				<tr><th><label>Importo di aggiudicazione/affidamento</label></th>
				<td><input type="text" id="<%= PSBD.FIELD_NAME_IMP_AGGIUDICATARIO %>" maxlength="16" value=""  onchange="setFormModified('Modificato<%=prefixAgg%>')" onblur="validateAmount(this);"/></td></tr>
				<tr><th><label>Ribasso di aggiudicazione</label></th>
				<td><input type="text" id="<%= PSBD.FIELD_NAME_PERC_RIBASSO_AGGIUDICATARIO %>" maxlength="16" value=""  onchange="setFormModified('Modificato<%=prefixAgg%>')" onblur="validatePercentage(this);"/>%</td></tr>
				<tr><th><label>Offerta in aumento</label></th>
				<td><input type="text" id="<%= PSBD.FIELD_NAME_PERC_AUMENTO_AGGIUDICATARIO %>" maxlength="16" value=""  onchange="setFormModified('Modificato<%=prefixAgg%>')" onblur="validatePercentage(this);"/>%</td></tr>
				<!-- ///////////////// -->
				
				<tr style="display: none;">
					<th><label>Ruolo</label></th>
					<td>
						<select id="<%= PSBD.FIELD_NAME_AGG_RUOLO%>" CLASS="BOTTONE"  onchange="setFormModified('Modificato<%=prefixAgg%>')">
							<option value=""></option>
							<option value="1">Mandataria</option>
							<option value="2">Mandante</option>
						</select>
					</td>					
				</tr>			
				<tr style="display: none;">
					<th><label>Percentuale</label></th>
					<td>
						<input onblur="validatePercentage(this)" type="text" id="<%= PSBD.FIELD_NAME_AGG_PERCENTUALE %>"
							size="6" maxlength="6"  style="text-align: right" value=""  onchange="setFormModified('Modificato<%=prefixAgg%>')" />%
					</td>
				</tr>
			
				<tr style="display: none;">
					<th rowspan="2"><label>L'aggiudicatario ha fatto ricorso all'istituto dell'Avvalimento</label></th>
					<td>
						<input type="checkbox" id ="<%= PSBD.S_FIELD_NAME_AGG_FLAG_AVVALIMENTO %>" value="1"  onchange="setFormModified('Modificato<%=prefixAgg%>')" />Per i Requisiti
					</td>
				</tr>
				<tr style="display: none;">
					<td>			
						<input type="checkbox" id ="<%= PSBD.N_FIELD_NAME_AGG_FLAG_AVVALIMENTO %>" value="2"  onchange="setFormModified('Modificato<%=prefixAgg%>')" />Per l'Attestazione
					</td>
				</tr>				
				<tr style="display: none;">
					<th><label>Codice Fiscale Ditta Ausiliaria</label></th>
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_AGG_CF_AUSILIARIA %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" maxlength="16"/>
					</td>
				</tr>
				<tr style="display: none;">
					<th><label>Progressivo Raggruppamento (1)</label></th>
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_AGG_ID_GRUPPO %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" maxlength="16" onblur="validateNumber(this)" />
					</td>
				</tr>
				<tr style="display: none;">
					<td>
						<input type="text" id="<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
				</tr>
				<tr style="display: none;">
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
				</tr>
				<tr style="display: none;">	
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
				</tr>
				<tr style="display: none;">	
					<td>
						<input type="text" id="<%= PSBD.FIELD_NAME_AGG_ID_RUOLO %>" value="" onchange="setFormModified('Modificato<%=prefixAgg%>')" />
					</td>
				</tr>			
				<tr style="display: none;"><td><input type="text" id="<%= PSBD.FIELD_NAME_ANAGOE %>"  onchange="setFormModified('Modificato<%=prefixAgg%>')"/>
				</td></tr>
				<tr><td class="hmenu"><a id="AddMod<%= prefixAgg %>" href="javascript:addRow([<%= PSBD.argsAggiud %>],[<%=PSBD.argsAggiudNascosti%>],'<%=prefixAgg%>')">Aggiungi</a></td></tr>
				<tr style="display: none;"><td>(1) In caso di raggruppamento indicare lo stesso 'progressivo raggruppamento' per ogni operatore economico facente parte dello stesso raggruppamento</td></tr>
			</table>	
			<input type="hidden" id="Modificato<%= prefixAgg %>" name ="Modificato<%= prefixAgg %>" value="0">			
		</div>		
<input type="hidden" id="Modificato2" name ="Modificato2" value="<c:out value="${param['modificato2']}" />">
<input type="hidden" id="selected<%= prefixAgg %>" value="0" />
<input type="hidden" id="<%= PSBD.VAR_ANN %>" name ="<%= PSBD.VAR_ANN %>" value="<c:out value="${variazioniAnagrafiche}" />">
<script language=javascript type="text/javascript"> 
		calcoloAggiudicazione(document.getElementById('ribAgg'),document.getElementById('percAum'),'<%= PSBD.AGGIUDICATARIO %>');
</script>	