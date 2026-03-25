<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String prefixPrest = PSBD.PRESTAZIONE; %>
<c:set var="prefixPrest" value="<%= prefixPrest %>" scope="page" />
<c:set var="readonlyPrest" value="${roByFlusso eq true or (schedaA.aggiudicazione.confirmed and variazioniAnagrafiche ne true ) }" />
<c:set var="readonlyPrestStr" value="${readonlyPrest ? 'readonly' : ''} " />

<script type="text/javascript">
<!--
function modAnagPrest(){var E=document.getElementById("<%= PSBD.FIELD_NAME_PRESTAZIONE_CODICEFISCALE %>").value.indexOf("-")<0;apriPopUpMod(E?"rubricaResponsabili":"rubrica","<%= PSBD.TAB_PRESTAZIONI %>",document.getElementById("<%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RESPONSABILE%>"),document.getElementById("<%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_INIZIO_RES%>"),E?"<utils:message key="rubrica.rubricaIncaricati" />":"<utils:message key="rubrica.rubricaOperatoriEconomici" />",document.getElementById("<%= PSBD.FIELD_NAME_PRESTAZIONE_ANAG %>").value)}
//-->
</script>
<div id="DIVTabella<%= prefixPrest %>" class="scrollTabs" style="height: 200px; width: 99%;">
<table id="idTabella<%= prefixPrest %>">
<tbody>
<tr>
<th width="125"><utils:message key="table.azione" /> ${variazioniAnagrafiche}</th>
<th class="garaTh"><utils:message key="scheda.cognomeDenominazione" /></th>
<th class="garaTh"><utils:message key="table.nome" /></th>							  
<th class="garaTh"><utils:message key="table.codiceFiscale" /></th>   
<th class="garaTh"><utils:message key="scheda.tipologiaSoggetto" /></th>
<th class="garaTh"><utils:message key="scheda.cigAffidamentoIncaricoEsterno" /></th>							  
<th class="garaTh"><utils:message key="scheda.dataAffidamentoIncarico" /></th>							  
<th class="garaTh"><utils:message key="scheda.dataConsegnaProgetto" /></th>							  
</tr>
<c:set var="counter" value='0' scope="page"/>
<c:forEach var="prestCorrente" items="${prestazioni}">
<c:if test="${prestCorrente.mandante eq false }">
<c:set var="id" value="row${prefixPrest}${counter}" scope="page"/>
<tr id="<c:out value="${id}" />">
<c:set var="soggPrestazione" value="${prestCorrente.soggettoResponsabile}" />		
<c:set var="soggPartecipante" value="${prestCorrente.soggettoPartecipante}" />		
<c:if test="${ readonlyPrest ne true}">
<td nowrap="nowrap" class="hmenu">
<a title="<utils:message key="button.modifica" />" href="javascript:setForModifyRow('<c:out value="${id}" />',[<%= PSBD.argsPrest %>],[<%=PSBD.argsPrestNascosti%>],'<%=prefixPrest%>')"><utils:message key="button.modifica" /></a>
<a title="<utils:message key="button.cancella" />" href="javascript:deleteRow('<c:out value="${id}" />',[<%= PSBD.argsPrest %>],[<%=PSBD.argsPrestNascosti%>],'<%=prefixPrest%>')"><utils:message key="button.cancella" /></a></td>
</c:if>
<c:if test="${ readonlyPrest eq true }">
<td>&nbsp;</td>
</c:if>
<c:set var="parametri" value="ID_TABELLA_AFFIDATARI=${id}&SOGGETTI_PARTECIPANTIDENOMINAZIONE=${soggPartecipante ne null ? soggPartecipante.denominazione : soggPrestazione.cognome}&CODICE_FISCALE=${soggPartecipante ne null ? soggPartecipante.codiceFiscale : soggPrestazione.codiceFiscaleResponsabile}&SOGGETTI_PARTECIPANTIID_STATO=${soggPartecipante.id_stato}&RuoloAgg=&ID_GRUPPO=&ID_TIPOAGG=&readonlyAffidatario=${readonlyPrest || fromAVCPass}&variazioniAnagrafiche=" />
<c:if test="${soggPartecipante ne null}">	
<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_COGNOME %>"><c:out value="${soggPartecipante.denominazione}" /></td>
<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_NOME %>"></td>
<c:set var="prefissoCF" value="${(soggPartecipante.id_stato eq null)||(soggPartecipante.id_stato == '')? 'IT' : soggPartecipante.id_stato}" />					
<c:set var="CFcomposto" value="${prefissoCF}${'-'}${soggPartecipante.codiceFiscale}" />				
<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_CODICEFISCALE %>"><c:out value="${CFcomposto}" /></td> 
</c:if>
<c:if test="${soggPrestazione ne null}">
<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_COGNOME %>"><c:out value="${soggPrestazione.cognome}" /></td>
<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_NOME %>"><c:out value="${soggPrestazione.nome}" /></td>
<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_CODICEFISCALE %>"><c:out value="${soggPrestazione.codiceFiscaleResponsabile}" /></td>
</c:if>
<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RUOLO %>"><c:out value="${prestCorrente.descrizioneRuolo}" /></td>
<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_CIG_PROG_ESTERNA %>"><c:out value="${prestCorrente.cigProgEsterna}" /></td>
<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_AFF_PROG_ESTERNA %>"><c:out value="${prestCorrente.dataAffProgEsterna}" /></td>
<td nowrap class="garaTd" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_CONS_PROG_ESTERNA %>"><c:out value="${prestCorrente.dataConsProgEsterna}" /></td>
<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_DESCRIZIONE_RUOLO %>" style="display: none"><c:out value="${prestCorrente.idRuolo}" /></td>
<c:if test="${soggPrestazione ne null}">
<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RESPONSABILE %>" style="display: none"><c:out value="${soggPrestazione.idResponsabile}" /></td>
<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_INIZIO_RES %>" style="display: none"><c:out value="${soggPrestazione.dataInizioRes}" /></td>
<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_ANAG %>" style="display: none"><c:out value="${soggPrestazione.datiModifica}" /></td>         
</c:if>
<c:if test="${soggPartecipante ne null}">
<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RESPONSABILE %>" style="display: none"><c:out value="${soggPartecipante.idSoggettoPartecipante}" /></td>
<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_INIZIO_RES %>" style="display: none"><c:out value="${soggPartecipante.dataInizioSogg}" /></td>
<td id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_ANAG %>" style="display: none"><c:out value="${soggPartecipante.datiModifica}" /></td>         
</c:if>
<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RUOLO %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RUOLO %>" value="<c:out value="${prestCorrente.descrizioneRuolo}" />"></td>
<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_CIG_PROG_ESTERNA %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_CIG_PROG_ESTERNA %>" value="<c:out value="${prestCorrente.cigProgEsterna}" />"></td>
<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_AFF_PROG_ESTERNA %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_AFF_PROG_ESTERNA %>" value="<c:out value="${prestCorrente.dataAffProgEsterna}" />"></td>
<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_CONS_PROG_ESTERNA %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_CONS_PROG_ESTERNA %>" value="<c:out value="${prestCorrente.dataConsProgEsterna}" />"></td>
<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_DESCRIZIONE_RUOLO %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_DESCRIZIONE_RUOLO %>" value="<c:out value="${prestCorrente.idRuolo}" />"></td>
<c:if test="${soggPrestazione ne null}">
<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RESPONSABILE %>" value="<c:out value="${soggPrestazione.idResponsabile}" />"></td>
<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_INIZIO_RES %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_INIZIO_RES %>" value="<c:out value="${soggPrestazione.dataInizioRes}" />"></td>				
<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_COGNOME %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_COGNOME %>" value="<c:out value="${soggPrestazione.cognome}" />"></td>
<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_NOME %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_NOME %>" value="<c:out value="${soggPrestazione.nome}" />"></td>
<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_CODICEFISCALE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_CODICEFISCALE %>" value="<c:out value="${soggPrestazione.codiceFiscaleResponsabile}" />"></td>
<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_ANAG %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_ANAG %>" ><c:out value="${soggPrestazione.datiModifica}" /></td>					
</c:if>
<c:if test="${soggPartecipante ne null}">
<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RESPONSABILE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RESPONSABILE %>" value="<c:out value="${soggPartecipante.idSoggettoPartecipante}" />"></td>
<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_INIZIO_RES %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_INIZIO_RES %>" value="<c:out value="${soggPartecipante.dataInizioSogg}" />"></td>				
<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_COGNOME %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_COGNOME %>" value="<c:out value="${soggPartecipante.denominazione}" />"></td>
<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_NOME %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_NOME %>" value=""></td>
<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_CODICEFISCALE %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_CODICEFISCALE %>" value="<c:out value="${CFcomposto}" />"></td>
<td style="display: none"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_ANAG %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_PRESTAZIONE_ANAG %>" ><c:out value="${soggPartecipante.datiModifica}" /></td>					
</c:if>
<c:if test="${prestCorrente.idRuolo eq 19}">
<td nowrap="nowrap" class="hmenu" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_PARAMETRI_GRUPPI %>">
<a title="<utils:message key="scheda.gestioneRaggruppamentoImpresa" />" href="javascript:apriPopUpRubricaDittaAusiliaria('<c:out value="${id}" />',[],[],'<%=prefixPrest%>','rubricaRaggruppamento','tabInc','<utils:message key="scheda.gestioneRaggruppamentoImpresa" />','<c:out value="${parametri}" />')"><utils:message key="scheda.gestioneRaggruppamentoImpresa" /></a>
</td>
<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_LISTA_GRUPPI %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_LISTA_GRUPPI %>" value="${prestCorrente.ditteRaggruppamentoString}"></td>
</c:if>
<c:if test="${prestCorrente.idRuolo ne 19}">
<td nowrap="nowrap" class="hmenu" id="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_PARAMETRI_GRUPPI %>"></td>
<td style="display: none;"><input type="hidden" id="hidden<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_LISTA_GRUPPI %>" name="<c:out value="${id}" /><%= PSBD.FIELD_NAME_AGG_LISTA_GRUPPI %>" value=""></td>
</c:if>
</tr>
<c:set var="counter" value="${counter + 1}" scope="page"/>
</c:if>
</c:forEach> 
</tbody>
</table> 
</div>	 			
<c:if test="${readonlyPrest ne true }">	
<div class="hmenu"><a id="showHide<%= prefixPrest %>Button" href="javascript:showSezioneAggiungi([<%= PSBD.argsPrest %>],[<%=PSBD.argsPrestNascosti%>],'<%=prefixPrest%>')" title="<utils:message key="scheda.aggiungiPrestazione" />"><utils:message key="scheda.aggiungiPrestazione" /></a></div>
</c:if>		
<div class="detailHelp" id="divAgg<%= prefixPrest %>" style=" width: 99%; display: none; border: 1px solid #cfcfcf;">
<table>
<tr>
<th><label for=""><utils:message key="table.codiceFiscale" /></label></th>
<td>
<input type="text" id="<%= PSBD.FIELD_NAME_PRESTAZIONE_CODICEFISCALE %>" maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixPrest%>')">
</td>	
<td nowrap="nowrap">
<div class="hmenu"><a title="<utils:message key="scheda.cercaIncaricatiInRubrica" />" href="javascript:apriPopUpRubrica('rubrica','<%= PSBD.TAB_PRESTAZIONI %>','<utils:message key="rubrica.rubricaIncaricati" />')"><utils:message key="scheda.cercaIncaricatiInRubrica" /></a></div>
</td>	
<td nowrap="nowrap">
<div class="hmenu"><a title="<utils:message key="scheda.cercaOperatoriEconomiciInRubrica" />" href="javascript:apriPopUpRubrica('rubrica','<%= PSBD.TAB_PRESTAZIONI %>','<utils:message key="rubrica.rubricaOperatoriEconomici" />')"><utils:message key="scheda.cercaOperatoriEconomiciInRubrica" /></a></div>				
</td>					
</tr>		
<tr>
<th><label for=""><utils:message key="scheda.cognomeDenominazione" /></label></th>
<td>
<input type="text" id="<%= PSBD.FIELD_NAME_PRESTAZIONE_COGNOME %>" maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixPrest%>')" />
</td>
<td ${variazioniAnagrafiche eq true ? '' : 'style="display:none;"'}>
<div class="hmenu"><a title="Modifica scheda anagrafica" href="javascript:modAnagPrest();">Modifica anagrafica</a></div></td>
</td>
</tr>		
<tr>
<th><label for="">Nome</label></th>
<td>
<input type="text" id="<%= PSBD.FIELD_NAME_PRESTAZIONE_NOME %>" maxlength="20" value="" disabled onchange="setFormModified('Modificato<%=prefixPrest%>')" />
</td>
</tr>	
<tr>
<th><label for="" style="text-align: left;">CIG affidamento incarico esterno di progettazione (in caso di progettista esterno)</label></th>
<td>
<input  ${readonlyPrestStr} type="text" id="<%= PSBD.FIELD_NAME_PRESTAZIONE_CIG_PROG_ESTERNA %>" maxlength="10" value="" onchange="setFormModified('Modificato<%=prefixPrest%>')" />
</td>
</tr>	
<tr>
<th><label for="">Data di affidamento incarico (per progettazione esterna)</label></th>
<td>
<input  ${readonlyPrestStr} type="text" id="<%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_AFF_PROG_ESTERNA %>" style="text-align:center" <c:out value="${disabled}" /> onchange="setFormModified('Modificato<%=prefixPrest%>')" onblur="Calendar.validaData(this)"  value="" />
<c:if test="${readonlyPrest ne true }">	
<img src="calendar/img.gif" id="CALdaff" style="cursor: pointer; border: 1px solid red;" title="Date selector"
onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
<script type="text/javascript">
Calendar.setup({inputField: "<%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_AFF_PROG_ESTERNA %>", 
ifFormat: "%d/%m/%Y", button: "CALdaff",align : "Tl", singleClick: true });
</script>
</c:if>		
</td>
</tr>	
<tr>
<th><label for="">Data di consegna progetto (per progettazione esterna)</label></th>
<td>
<input type="text" id="<%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_CONS_PROG_ESTERNA %>" style="text-align:center" <c:out value="${disabled}" /> ${readonlyPrestStr} onchange="setFormModified('Modificato<%=prefixPrest%>')" onblur="Calendar.validaData(this)"  value="" />
<c:if test="${readonlyPrest ne true }">	
<img src="calendar/img.gif" id="CALdest" style="cursor: pointer; border: 1px solid red;" title="Date selector"
onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
<script type="text/javascript">
Calendar.setup({inputField:"<%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_CONS_PROG_ESTERNA %>", 
ifFormat: "%d/%m/%Y", button: "CALdest", align: "Tl", singleClick :    true});
</script>
</c:if>	
</td>
</tr>	
<tr><td>&nbsp;</td></tr>
<tr>
<th><label>Tipologia del soggetto incaricato della prestazione</label></th>
<td>
<select id="<%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RUOLO%>" CLASS="BOTTONE" onchange="setFormModified('Modificato<%=prefixPrest%>')" >
<option></option>
<c:set var="idRuoloPrest" value="${prestCorrente.idRuolo}" scope="request" />
<u:options name="<%= ParametriServlet.RUOLI_PRESTAZIONE_BEAN %>" scope="request" value="idRuoloPrest"/>
</select>
</td>							
</tr>	
<tr style="display: none;">
<td>
<input disabled type="text" id="<%= PSBD.FIELD_NAME_PRESTAZIONE_DESCRIZIONE_RUOLO %>" value="" onchange="setFormModified('Modificato<%=prefixPrest%>')"/>
</td>							
</tr>	
<tr style="display: none;">
<td>
<input disabled type="text" id="<%= PSBD.FIELD_NAME_PRESTAZIONE_ID_RESPONSABILE %>" value="" onchange="setFormModified('Modificato<%=prefixPrest%>')"/>
</td>							
</tr>	
<tr style="display: none;">
<td>
<input disabled type="text" id="<%= PSBD.FIELD_NAME_PRESTAZIONE_DATA_INIZIO_RES %>" value="" onchange="setFormModified('Modificato<%=prefixPrest%>')"/>
</td>							
</tr>					
<tr style="display: none;">
<td>
<input disabled type="text" id="<%= PSBD.FIELD_NAME_PRESTAZIONE_ANAG %>"  onchange="setFormModified('Modificato<%=prefixPrest%>')"/>
</td>							
</tr>
<tr><td class="hmenu"><a id="AddMod<%= prefixPrest %>" href="javascript:addRow([<%= PSBD.argsPrest %>],[<%=PSBD.argsPrestNascosti%>],'<%=prefixPrest%>')">Aggiungi</a></td></tr>			
</table>
<input type="hidden" id="Modificato<%= prefixPrest %>" name ="Modificato<%= prefixPrest %>" value="0">
</div>
<input type="hidden" id="Modificato6" name ="Modificato6" value="<c:out value="${param['modificato6']}" />">
<input type="hidden" id="selected<%= prefixPrest %>" value="0" /> 