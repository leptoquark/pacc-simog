<tr>
	<td><label >Importo componente lavori in Euro (al netto dell'IVA e degli oneri di sicurezza)</label></td>
	<td>
	<input readonly="readonly" type="text" style="text-align:right;width:100px;" 
		   id="a1" value="<c:out value="${aggiudicazione.datiEconomici.importoLavoriStr}" />" />
	</td>
</tr>
<tr>
	<td><label >Importo componente servizi in Euro (come sopra)</label></td>
	<td>
	<input readonly="readonly" type="text" style="text-align:right;width:100px;" 
		   id="a1" value="<c:out value="${aggiudicazione.datiEconomici.importoServiziStr}" />" />
	</td>
</tr>
<tr>
	<td><label >Importo componente forniture in Euro (come sopra)</label></td>
	<td>
	<input readonly="readonly" type="text" style="text-align:right;width:100px;" 
		   id="a1" value="<c:out value="${aggiudicazione.datiEconomici.importoFornitureStr}" />" />
	</td>
</tr>
<tr>
	<td><label >Importo complessivo altre fattispecie in Euro (come sopra)</label></td>
	<td>
	<input readonly="readonly" type="text" style="text-align:right;width:100px;" 
		   id="a1" value="<c:out value="${aggiudicazione.datiEconomici.importoComplessivoStr}" />" />
	</td>
</tr>
<tr>
	<td><label style="color: black;">Subtotale</label></td>
	<td>
	<input readonly="readonly" type="text" style="text-align:right;width:100px;" 
		   id="a1" value="<c:out value="${aggiudicazione.datiEconomici.subTotaleStr}" />" />
	</td>
</tr>
<tr>
	<td><label >Importo totale per l'attuazione della sicurezza</label></td>
	<td>
	<input readonly="readonly" type="text" style="text-align:right;width:100px;" 
		   id="a1" value="<c:out value="${aggiudicazione.datiEconomici.importoSicurezzaStr}" />" />
	</td>
</tr>
<tr>
	<td><label >Eventuali ulteriori somme non assoggettate al ribasso d'asta</label></td>
	<td>
	<input readonly="readonly" type="text" style="text-align:right;width:100px;" 
		   id="a1" value="<c:out value="${aggiudicazione.datiEconomici.importoNonAssogStr}" />" />
	</td>
</tr>
<tr>
	<td><label >Importo progettazione</label></td>
	<td>
	<input readonly="readonly" type="text" style="text-align:right;width:100px;" 
		   id="a1" value="<c:out value="${aggiudicazione.datiEconomici.importoProgettazioneStr}" />" />
	</td>
</tr>
<tr>
	<td><label style="color: black;">Importo complessivo appalto</label></td>
	<td>
	<input readonly="readonly" type="text" style="text-align:right;width:100px;" 
		   id="a1" value="<c:out value="${aggiudicazione.datiEconomici.importoComplessivoAppStr}" />" />
	</td>
</tr>
<tr>
	<td><label >Importo totale somme a disposizione</label></td>
	<td>
	<input readonly="readonly" type="text" style="text-align:right;width:100px;" 
		   id="a1" value="<c:out value="${aggiudicazione.datiEconomici.importoDisposizioneStr}" />" />
	</td>
</tr>
<tr>
	<td><label style="color: black;">Importo complessivo dell'intervento</label></td>
	<td>
	<input readonly="readonly" type="text" style="text-align:right;width:100px;" 
		   id="a1" value="<c:out value="${aggiudicazione.datiEconomici.importoInterventoStr}" />" />
	</td>
</tr>
<tr>
	<td><label >Importo di aggiudicazione</label></td>
	<td>
	<input readonly="readonly" type="text" style="text-align:right;width:100px;" 
		   id="a1" value="<c:out value="${aggiudicazione.datiEconomici.importoAggiudicazioneStr}" />" />
	</td>
</tr>
<tr>
	<td><label >Data aggiudicazione di riferimento</label></td>
	<td>
	<input readonly="readonly" type="text" style="text-align:right;width:100px;" 
		   id="a1" value="<c:out value="${aggiudicazione.datiEconomici.dataAggiudicazioneStr}" />" />
	</td>
</tr>
<tr>
	<td><label style="color: black;">AGGIUDICATARI</label></td>
</tr>
<c:forEach var="aggCorrente" items="${aggiudicazione.datiEconomici.aggiudicatari}">
	<tr>
		<td  style="width: 10%; padding-left: 20px;" class="garaTd"><c:out value="${aggCorrente.soggettoPartecipante.codiceFiscale}" /> - <c:out value="${aggCorrente.soggettoPartecipante.denominazione}" /></td>
	</tr>
</c:forEach>
			