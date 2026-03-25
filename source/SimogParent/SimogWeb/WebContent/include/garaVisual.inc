<%
/****************************
* 	Questa pagina viene inclusa per la visualizzazione delle informazioni
*	riguardanti la gara.
*
*	Devono essere fornite le variabili:
*
*  codiceGara
*	oggettoGara
*	dataCreazioneGara
*	saRiferimentoDescrizione
*	saRiferimentoCodiceFiscale
*	amministrazioneDescrizione
*	amministrazioneCodiceFiscale
*	RSSA_CodiceFiscal
*  proceduraRistretta
*  esclusioneAVCPass
*/
%>


<%@page import="it.avlp.simog.db.advanced.TableBean"%>
<%@page import="it.avlp.simog.db.Costanti"%>
<%@page import="it.avlp.simog.db.SimogFlags"%>

<script type="text/javascript">
//<!--

function popupAnnullaRettifica(param){ 

	 if(confirm("Vuoi annullare la rettifica?")){

		 window.location.href="annullaRettifica?"+param
	 }else{
		return false;
	}
	 return true;
}


function getDateAndAction(azione){ 

	var dataInput = prompt("Modifica data di conferma", "Inserire la nuova data in formato GG/MM/AAAA");

	
	var mesg = isDate(dataInput);
	if(mesg != ""){
		alert(mesg);
		return;
	}
	if (confirm("La data di conferma sarà modificata. Procedere?")) {
		window.location.href=azione + dataInput;
	}
}


function getConfermaSbloccaGara(){
	msg="Attenzione si sta per sbloccare la gara. Continuare?";
	if(confirm(msg)){
		alert("ok");
	}	
	
}

function doActionPerfezionamentoGaraLotti(url, action){
	
	if(action==null){
//		if(confirm("Confermare l'operazione. Procedere ?")){
			location.href = url;
//		}
	}
	else if(action==true){
		if(confirm("Il perfezionamento non richiede l'inserimento di ulteriori informazioni. Procedere ?")){
			location.href = url;
		}
	}
	else if(action==false){
		if(confirm("Alcuni dati non risultano coerenti. Controllare la scelta contraente dei lotti inseriti, oppure il numero dei lotti afferenti alla Gara. Si desidera proseguire comunque?")){
			location.href = url;
		}
	}		
}
//-->
</script>

<%@page import="it.avlp.simog.util.PageHelper"%>
<%@page import="it.avlp.simog.beans.StatiScheda"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.util.SimogProperties"%>

<h5>Stazione Appaltante</h5>
<div class="elenco">
<div class="gara">
<table>
	<tr>
		<th class="garaTh" width="40%">Denom.ne della Stazione Appaltante competente</th>
		<td class="garaTd"><%= amministrazioneDescrizione %></TD>
	</tr>
	<tr>
		<th class="garaTh" width="40%">Codice Fiscale Amministrazione Competente</th>
		<td class="garaTd"><%= amministrazioneCodiceFiscale %></TD>
	</tr>
	<% if(codiceAusa!=null && !"".equals(codiceAusa)) { %>
	   <tr>
		<th class="garaTh" width="40%">Codice AUSA</th>
		<td class="garaTd"><%= codiceAusa %></TD>
	</tr>
	<% } %>
	<tr>
		<th class="garaTh" width="40%">Codice univoco centro di costo</th>
		<td class="garaTd"><%= idSaRiferimento %></TD>
	</tr>
	<tr>
		<th class="garaTh" width="40%">Denominazione del centro di costo nell'ambito della Stazione Appaltante</th>
		<td class="garaTd"><%= descrSARiferimento %></TD>
	</tr>
	<%-- TICKET ALM #4325 --%>
	<% if(rupCreatoGara!=null & !RSSA_CodiceFiscale.equals(rupCreatoGara)) { %>
	<tr>
		<th class="garaTh" width="40%">RUP che ha creato la gara</th> 
		<td class="garaTd"><%= rupCreatoGara %></TD>
	</tr>
	<% } %>
	<tr>
		<th class="garaTh" width="40%">RUP che ha in carico la gara</th> 
		<td class="garaTd"><%= RSSA_CodiceFiscale %></TD>
		</tr>
	</table>
</div>
</div>

<div class="elenco">
<div class="gara">	
<table>
	<tr>
		<th class="garaTh" width="40%">Numero Gara</th>
		<td>
		<table><tr>
		<td nowrap class="garaTd"><%= codiceGara%>&nbsp;</td>
		<td>(da utilizzare in sede di versamento del contributo da parte della SA)</td>
		</tr></table>
		</td>
	</tr>
	<tr>
		<th class="garaTh" width="40%">Oggetto della Gara</th>
		<td class="garaTd"><%= oggettoGara %></TD>
	</tr>
	<tr>
		<th class="garaTh" width="40%">Data Creazione</th>
		<td class="garaTd"><%= dataCreazioneGara%></TD>
	</tr>
	
	<tr>
		<th class="garaTh" width="40%">Importo complessivo Gara</th>
		<td class="garaTd"><%= importoGara%></TD>
	</tr>
	<tr>
		<th class="garaTh" width="40%">Importo contributo SA</th>
		<td class="garaTd"><%= importoSAGara%>&nbsp;</td>
	</tr>
	
	<%--gm nuovo campo simog 3.04 --%>
	<tr>
		<th class="garaTh" width="40%">Numero totale dei Lotti</th>
		<td class="garaTd"><%= numeroLotti %></TD>
	</tr>
	<tr>
		<th class="garaTh" width="40%">Settore attivita' della SA</th>
		<td class="garaTd"><%= tipoScheda%></TD>
	</tr>
	<tr>
		<th class="garaTh" width="40%">Modalita' di indizione</th>
		<td class="garaTd"><%= modoGara%></TD>
	</tr>
	

	<%  //TICKET ALM - 3.04.2 NG
	//Per visualizzare il campo la gara deve essere creata dopo l'attivazione della 3.04.2
	if(SimogProperties.getInstance().isDataCreatedAfter3042(PageHelper.getFormattedDBDate(dataCreazioneGara))) { %>	

	<tr>
		<th class="garaTh" width="40%">Modalita' di indizione servizi di cui all'allegato IX</th>
		<td class="garaTd"><%= modIndAllegatoIX %></TD>
	</tr>

	<% } //FINE TICKET ALM - 3.04.2 NG %>
	

	<tr>

		<th class="garaTh" width="40%">Modalita' di realizzazione</th>
		<td class="garaTd"><%= modoReal%></TD>
	</tr>
	

	<%  //TICKET ALM - 3.04.3 #659
	if(SimogProperties.getInstance().isDataCreatedAfter3043(PageHelper.getFormattedDBDate(dataCreazioneGara)) &&  !SimogProperties.getInstance().isDataCreatedAfter3046(PageHelper.getFormattedDBDate(dataCreazioneGara))) { %>	
	
	
	<tr>
		<th class="garaTh" width="40%">Durata della convenzione o accordo quadro in giorni</th>
		<td class="garaTd"><%= "0".equals(durataGiorni) ? "" : durataGiorni %></TD>
	</tr>
	<% } //FINE TICKET ALM - 3.04.3 #659 %>
	
   <%  //TICKET ALM - 3.04.2 NG
	//Per visualizzare il campo la gara deve far parte del nuovo codice degli appalti (se dlgs50=N non si deve vedere)
	if(SimogProperties.getInstance().isDataCreatedAfter3042(PageHelper.getFormattedDBDate(dataCreazioneGara))) { %>		
	
	<tr>
		<th class="garaTh" width="40%">Strumenti per lo svolgimento delle procedure</th>
		<td class="garaTd"><%= strumentoSvolgimento%></TD>
	</tr>
	<tr>
		<th class="garaTh" width="40%">Motivo urgenza</th>
		<td class="garaTd"><%= estremaUrgenza %></TD>
	</tr>
	<% } //FINE TICKET ALM - 3.04.2 NG %>

	<tr>
		<th class="garaTh" width="40%">CIG relativo all'accordo quadro/convenzione cui si aderisce</th>
		<td class="garaTd"><%= cigQuadro%></TD>
	</tr>

	<tr>
		<th class="garaTh" width="40%">Stato gara</th>
		<td class="garaTd"><%= statoGara%> <% if( Confermato ){%>(<%= PageHelper.getFormattedDate(garaDataConferma) %>)<%}%></td>
	</tr>
	<tr>
		<th class="garaTh" width="40%">Data Cancellazione</th>
		<td class="warning"><%= PageHelper.getFormattedDate(garaDataCancellazione) %></TD>
	</tr>
	
	<% if(user.isAmministratore()) { %>	
	<tr>
		<th class="garaTh" width="40%">Data Termine Pagamenti</th>
		<td class="garaTd"><%= PageHelper.getFormattedDate(garaDataTerminePagam)%></TD>
	</tr>
	<tr>
		<th class="garaTh" width="40%">Data Comunicazione Pagamento</th>
		<td class="garaTd"><%= PageHelper.getFormattedDate(garaDataComun) %></TD>
	</tr>	
	<tr>
		<th class="garaTh" width="40%">Data Inibizione Pagamento</th>
		<td class="warning"><%= PageHelper.getFormattedDate(garaDataInibPagam) %></TD>
	</tr>	
	<% } %>
	<tr>
		<th class="garaTh" width="40%">Data perfezionamento bando</th>
		<td class="garaTd"><%= PageHelper.getFormattedDate(garaDataPerfezionamento) %></TD>
	</tr>
	<% if(id_motivazioneGara != null && id_motivazioneGara.trim().length() > 0) { %>
		<TR>
			<TH>Motivazione </TH>
			<TD class="warning"><%= descMotivGara %></TD>
		</TR>
	<% } %>
	<% if(noteCancGara != null && noteCancGara.trim().length() > 0) { %>
		<TR>
			<TH>Note</TH>
			<TD class="warning"><%= noteCancGara %></TD>
		</TR>
	<% } %>
	<tr>
		<!-- 3.04.7.1 cambiata la label AVCPass in FVOE -->
		<th class="garaTh" width="40%">Gara esclusa dall'acquisizione obbligatoria dei requisiti ai fini FVOE</th>
		<td class="garaTd"><%= Costanti.FLAG_VALORE_SI.equals(esclusioneAVCPass) ? "SI" : Costanti.FLAG_VALORE_NO.equals(esclusioneAVCPass) ? "NO" : "" %></TD>
	</tr>	
	<% if(sceltaLegge89 != null && !"0".equals(sceltaLegge89)
	      && !"".equals(sceltaLegge89) && !"null".equals(sceltaLegge89)){ %>
	<tr>
		<th class="garaTh" width="40%"><%=Costanti.LEGGE89_TITLE %></th>
		<td class="garaTd"><%= Costanti.LEGGE89_1.equals(sceltaLegge89) ? Costanti.LEGGE89_1_DICH : Costanti.LEGGE89_2.equals(sceltaLegge89) ? Costanti.LEGGE89_2_DICH : "" %></TD>
	</tr>	
	<% } %>	
	<% if(Costanti.FLAG_VALORE_SI.equals(urgenzaDL133) || Costanti.FLAG_VALORE_NO.equals(urgenzaDL133)
	      //&& (SimogProperties.getInstance().isINT87Attivo(PageHelper.getFormattedDBDate(dataCreazioneGara))
	      //      || (garaDataPerfezionamento != null && SimogProperties.getInstance().isINT87Attivo(PageHelper.getFormattedDBDate(garaDataPerfezionamento))))
	      //&& urgenzaDL133 != null && !"0".equals(urgenzaDL133)
	      //&& !"".equals(urgenzaDL133) && !"null".equals(urgenzaDL133)
	      ){ %>
	<tr>
		<th class="garaTh" width="40%">Estrema urgenza/Esecuzione di lavori di somma urgenza</th>
		<td class="garaTd"><%= Costanti.FLAG_VALORE_SI.equals(urgenzaDL133) ? "SI" : Costanti.FLAG_VALORE_NO.equals(urgenzaDL133) ? "NO" : "" %></TD>
	</tr>	
	<% } %>	
	<!-- fix 34470 3.04.8 -->
	<tr>
		<th class="garaTh" width="40%">Link ai documenti relativi all'affidamento diretto in somma urgenza e protezione civile</th>
		<td class="garaTd"><%= linkAffidamentoDiretto%></td>
	</tr>
	<!-- FINE fix 34470 3.04.8 -->
	<%	if(categEagg.length > 0){ 
	   		int i = 0; 
			%>
		 <tr>
			 <th class="garaTh" width="40%"><%= Costanti.EAGG_LABEL_CATEGORIE %></th>
			 <td class="garaTd">
<!-- 				<table width="100%"> -->
				<% for ( i = 0; i < categEagg.length; i++ ) { %> 
<!-- 					<tr> -->
					<p><%=categEagg[i] %></p>
<!-- 					</tr> -->
				<% } %>
<!-- 				</table> -->
			</td>
		 </tr>
	<% } %>

	<% if(SimogFlags.is30350_RFWEBGL01Active() && !"".equals(motivoEagg)
	      ){ %>
	<tr>
		<th class="garaTh" width="40%"><%= Costanti.EAGG_LABEL_MOTIVI %></th>
		<td class="garaTd"><%= motivoEagg %></TD>
	</tr>	
	<% } %>		
	<%--TICKET ALM #659 - 3.04.4 --%>
	<% if(SimogProperties.getInstance().isDataCreatedAfter3044(PageHelper.getFormattedDBDate(dataCreazioneGara))) { %>	
			<tr>
				<th class="garaTh" width="40%">La stazione appaltante agisce per conto di altro soggetto?</th>
				<td class="garaTd"><%= Costanti.FLAG_VALORE_SI.equals(flagSAAgente) ? "SI" : Costanti.FLAG_VALORE_NO.equals(flagSAAgente) ? "NO" : "" %></TD>
			</tr>	
			<% if(Costanti.FLAG_VALORE_SI.equals(flagSAAgente)) { %>
			<tr>
				<th class="garaTh" width="40%">Funzioni delegate</th>
				<td class="garaTd"><%= idFDelegate%></TD>
			</tr>
			<tr>
				<th class="garaTh" width="40%">Codice fiscale soggetto per conto del quale agisce la S.A. (in caso di soggetto singolo)</th>
				<td class="garaTd"><%= cfAmmDelegante%></TD>
			</tr>
			<tr>
				<th class="garaTh" width="40%">Denominazione dell'Amministrazione per la quale agisce la S.A.</th>
				<td class="garaTd"><%= denAmmDelegante%></TD>
			</tr>
			<% }%>
		<% } %>
	</table>
	<div class="hmenu">
	<ul>
<!-- 	<li> -->
	<% //--- UN Gestione Gara ---
	if( user.isRSSAorRUP() || user.isAVLP() || user.isAmministratore()) {	
	   
		// PP se tutti i lotti sono cancellati allora la gara è cancellabile
		boolean tuttiLottiCanc = true;
		for ( int rowChk = 0; rowChk < listaGare.getTableSize(); rowChk++ ) {
			boolean canc = !("".equals(listaGare.getRow(rowChk).getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO)))
							|| !("".equals(listaGare.getRow(rowChk).getNulledField(LOTTO.DATA_INIB_PAGAMENTO)));

			if(currentRow.getNulledField(GARA.ID_GARA).equals(listaGare.getRow(rowChk).getNulledField(GARA.ID_GARA))
				&& !"".equals(currentRow.getNulledField(LOTTO.ID_LOTTO))
				&& (!canc)){
				tuttiLottiCanc = false;
				break;
			}
		}
		
		// PP se almeno un lotto ha schede aggiuntive la gara non è modificabile
		boolean garaHasSchede = false;
		for ( int rowChk = 0; rowChk < listaGare.getTableSize(); rowChk++ ) {
			if(currentRow.getNulledField(GARA.ID_GARA).equals(listaGare.getRow(rowChk).getNulledField(GARA.ID_GARA))
				&& !"".equals(currentRow.getNulledField(LOTTO.ID_LOTTO))
				&& !"0".equals(listaGare.getRow(rowChk).getNulledField(PSBD.HASSCHEDE))){
				garaHasSchede = true;
				break;
			}
		}
		boolean cancellabile_gara =  "".equalsIgnoreCase( currentRow.getNulledField(GARA.DATA_CANCELLAZIONE_GARA))
					&& "".equalsIgnoreCase( currentRow.getNulledField(GARA.DATA_INIB_PAGAM)) 
					&& ("".equalsIgnoreCase(currentRow.getNulledField(GARA.DATA_COMUN)) || user.isAmministratore()) 
					&& ((tuttiLottiCanc || "0".equalsIgnoreCase(currentRow.getNulledField(LOTTO.ID_LOTTO))) )
					&& !garaHasSchede; 
		
		boolean scaduto_gara = ( !"".equalsIgnoreCase(currentRow.getNulledField(GARA.DATA_TERMINE_PAGAMENTO) ) ); 
		boolean cancellato_gara = ! ( "".equalsIgnoreCase( currentRow.getNulledField(GARA.DATA_CANCELLAZIONE_GARA))
											 || ! ("".equalsIgnoreCase( currentRow.getNulledField(GARA.DATA_INIB_PAGAM))));
		boolean pagabile_gara = ( !"0.00".equals(currentRow.getNulledField(GARA.IMPORTO_SA_GARA) ) );
		boolean inDefinizione = (StatiScheda.IN_DEFINIZIONE_STRING.equals(currentRow.getNulledField(GARA.ID_STATO)));	
		boolean pubblicata_gara = (!"".equals(currentRow.getNulledField(GARA.DATA_PERFEZIONAMENTO_BANDO)));
      boolean comunicata = currentRow.getNulledField(GARA.DATA_COMUN) != null && !currentRow.getNulledField(GARA.DATA_COMUN).equalsIgnoreCase("");
      //boolean confermataGara = !"".equals(currentRow.getNulledField(GARA.DATA_CONFERMA_GARA));
		boolean prendibile = !user.getLogin().equalsIgnoreCase(RSSA_CodiceFiscale);

		// INT87
		String dataPubbLotto = "";
		for ( int rowChk = 0; rowChk < listaGare.getTableSize(); rowChk++ ) {
         if (!"".equals(listaGare.getNulledField(LOTTO.DATA_PUBBLICAZIONE, rowChk))){
            dataPubbLotto = listaGare.getNulledField(LOTTO.DATA_PUBBLICAZIONE, rowChk);
            break;
         }		
      }
		
		//TICKET ALM - 3.04.3
		//Prima verifica se la gara ha una data di pubblicazione e se essa sia successiva l'attivazione della 3.04.3
		//Poi verifica se i CIG della gara hanno una scheda di aggiudicazione confermata
		
		// before #ticket 31062
//		boolean hasAllAggiudicazioniConfermate =  SimogProperties.getInstance().isDataCreatedAfter3043(PageHelper.getFormattedDBDate(dataCreazioneGara))
//		                                          && (request.getAttribute(ParametriServlet.HAS_AGG_CONFERMATE) != null 
//		                                          && ((String) request.getAttribute(ParametriServlet.HAS_AGG_CONFERMATE)).equals("OK"));				
		//#ticket 31062 
		boolean hasAllAggiudicazioniConfermate =  SimogProperties.getInstance().isDataCreatedAfter3043(PageHelper.getFormattedDBDate(dataCreazioneGara));
		
		boolean isDelega = request.getAttribute(ParametriServlet.IS_DELEGA) != null && ((String) request.getAttribute(ParametriServlet.IS_DELEGA)).equals("OK");
		
		boolean isAccQuadroNc = request.getAttribute(ParametriServlet.IS_ACC_QUADRO_NC) != null && ((String) request.getAttribute(ParametriServlet.IS_ACC_QUADRO_NC)).equals("OK");
		
		boolean checkDatiComuni = request.getAttribute(PSReq.INFO_COMUNI_I_FASE) == null || "OK".equals((String) request.getAttribute(PSReq.INFO_COMUNI_I_FASE));
		
      String noDisplay = "VIS".equals((String) request.getAttribute(ParametriServlet.FROM_RICERCA)) || isAccQuadroNc ? "style='display:none'" : "";

		//MAC 34163 3.04.8     
       String noDisplayAggiuStipContrat = ("VIS".equals((String) request.getAttribute(ParametriServlet.FROM_RICERCA)) || isAccQuadroNc) || (!"".equalsIgnoreCase(cfAmmDelegante) && idFDelegate != null && idFDelegate.equalsIgnoreCase("Aggiudicazione, stipula ed esecuzione del contratto")) ? "style='display:none'" : "";     
      //
      
		if ( (user.isRSSAorRUP() && !isAccQuadroNc) || user.isAmministratore()) {
		 	if((!"cancella".equals(request.getAttribute(ParametriServlet.ACTION)))
		 			&&(!"modifica".equals(request.getAttribute(ParametriServlet.ACTION)))
		 			&&(!"perfeziona".equals(request.getAttribute(ParametriServlet.ACTION)))){
				if ( (!pubblicata_gara  || user.isAmministratore())
						  && ((user.isRSSAorRUP() && !pubblicata_gara) || user.isAmministratore()) 
						  && (!comunicata || user.isAmministratore())
						  && !cancellato_gara 
						  && !garaHasSchede
						  && !bloccoAVCPASS) { %>    
<%-- 						<td <%=noDisplay %> nowrap="nowrap"> --%>
<!-- 						<ul> -->
						<li <%=noDisplay %>>
						<a title="Modifica Gara" href="inizializzaGara?<%= ParametriServlet.SESSION_ID_GARA %>=<%= currentRow.getNulledField(GARA.ID_GARA)%>">Modifica Gara</a>
						</li>
<!-- 					</ul> -->
<!-- 						</td> -->
			<%	}%>
			<%				
				if ( cancellabile_gara 	
				      && (user.isAmministratore() || (user.isRSSAorRUP() && !pubblicata_gara))
				      && !bloccoAVCPASS) { %>
<%-- 					<td <%=noDisplay %> nowrap="nowrap"> --%>
<!-- 					<ul> -->
					<li <%=noDisplay %>>
					<a title="Cancella Gara" href="inizializzaGara?action=cancella&<%= ParametriServlet.SESSION_ID_GARA %>=<%= currentRow.getNulledField(GARA.ID_GARA) %>">Cancella Gara</a>
					</li>
<!-- 					</ul> -->
<!-- 					</td> -->
			<% } %>
			
			<% if (user.isAmministratore()
			      && cancellato_gara
			      && !bloccoAVCPASS
					) { %>
<%-- 				<td <%=noDisplay %> nowrap="nowrap"> --%>
<!-- 				<ul> -->
				<li <%=noDisplay %>>
				<a href="javascript:confirmAction('inizializzaGara?action=ripristina&<%= ParametriServlet.SESSION_ID_GARA %>=<%= currentRow.getNulledField(GARA.ID_GARA) %>');">Ripristina</a>
				</li>
<!-- 				</td> -->
<!-- 				</ul> -->
			<% } %>

			<%	 if ( user.isRSSAorRUP() // PP nooo || user.isAmministratore()
						 ) {
					   //se la gara e' confermata e non e' pubblicata � possibile aggiungervi nuovi lotti 
				     if(!cancellato_gara  
						//confermataGara && 
						&& "".equals(listaGare.getRow(0).getNulledField(GARA.DATA_PERFEZIONAMENTO_BANDO))
						&& !bloccoAVCPASS){%>
<%-- 					<td <%=noDisplay %> nowrap="nowrap"> --%>
<!-- 					<ul> -->
					<li <%=noDisplay %>>
					<a title="Aggiungere un lotto" href="inizializzaLotto">Aggiungi lotto</a>
					</li>
<!-- 					</ul> -->
			<%     					
				     } 
				     if ( user.isRSSAorRUP() 
				    	 && !cancellato_gara 
				    	 //&& confermataGara 
				    	 && !tuttiLottiCanc 
				    	 && !"0".equalsIgnoreCase(currentRow.getNulledField(LOTTO.ID_LOTTO)) 
				    	 && !bloccoAVCPASS) {
				    	 
				    	   //gm nuovo codice per pubblicazione adesioni in simog 3.06
				    	    Boolean adesioneValida  = request.getAttribute(ParametriServlet.ADESIONE_VALIDA)!= null ? (Boolean)request.getAttribute(ParametriServlet.ADESIONE_VALIDA) : null;
    					
					      //gm aggiunto pulsante per la pubblicazione del bando di gara
					      if(!pubblicata_gara){	
					          String url = ParametriServlet.SRV_BANDO_GARA+"?"+ParametriServlet.SESSION_ID_GARA+"="+currentRow.getNulledField(GARA.ID_GARA)+"&"+ParametriServlet.TIPO_PUBBLICAZIONE+"="+ParametriServlet.PUBBLICAZIONE;
					          if(adesioneValida!=null && adesioneValida==true){
					        	    url = url + "&toDo="+ParametriServlet.ACTION_SALVA_ADESIONE_GARA;
					          }
					          else{
					        	    url = url + "&toDo="+ParametriServlet.ACTION_CARICA_GARA;
					          }
								 String label = "Perfezionamento gara/lotti e Pubblicazione bando";
								 String organo = "";
								 if(request.getAttribute(ParametriServlet.IS_ORGANO)!= null){
									organo = (String) request.getAttribute(ParametriServlet.IS_ORGANO);
									if (Costanti.FLAG_VALORE_SI.equals(organo))
											label = "Perfezionamento gara/lotti";
								 }

			   %>
<%-- 				             <td <%=noDisplay %> nowrap="nowrap"> --%>
<!-- 				             <ul> -->
								<li <%=noDisplay %>>
				             <a title="Pubblica/Perfeziona bando di gara" href="javascript:doActionPerfezionamentoGaraLotti('<%=url %>',<%= adesioneValida %>)"><%=label %></a>
				             </li>
<!-- 				             </ul>							    	     -->
			   <%  		            	   	
					      }                
			            else if(pubblicata_gara 
			                  && !"".equals(currentRow.getNulledField(GARA.ID_PUBBLICAZIONE)) 
			                  && !"".equals(currentRow.getNulledField(GARA.DATA_INIZIO_PUBB)) 
			                  &&	currentRow.existField(PUBBLICAZIONI.FLAG_SOSPESO) 
			                  && !Costanti.FLAG_VALORE_SI.equals(currentRow.getNulledField(PUBBLICAZIONI.FLAG_SOSPESO))
			                  && !isAccQuadroNc //TICKET ALM - 3.04.3
			                  && !isDelega //TICKET ALM - 3.04.3
			            		){ %>
<%-- 							    <td <%=noDisplay %> nowrap="nowrap"> --%>
<!-- 							    <ul> -->
								<li <%=noDisplay %>>
							    <a title="Pubblica rettifica" href="<%=ParametriServlet.SRV_GESTIONE_RETTIFICA %>?toDo=<%=ParametriServlet.ACTION_CARICA_RETTIFICA %>&<%=ParametriServlet.SESSION_ID_GARA %>=<%= currentRow.getNulledField(GARA.ID_GARA) %>&<%=ParametriServlet.TIPO_OPERAZIONE %>=<%=ParametriServlet.PUBBLICAZIONE_RETTIFICA %>&<%=ParametriServlet.TIPO_PUBBLICAZIONE %>=RETTIFICA&<%=ParametriServlet.TIPO_ALLEGATO %>=RETTIFICA">Pubblica rettifica</a>
							    </li>
<!-- 							    </ul> -->
							 <%
			           }%>
					      
			  <% 			//is3030_RFWEBGL00Active
						   Boolean proceduraRistretta = (Boolean)request.getAttribute("flgProceduraRistretta");
						   if(proceduraRistretta == null) proceduraRistretta = false;
						   boolean proceduraNonCompleta = "".equals(currentRow.getNulledField(LOTTO.DATA_SCADENZA_PAGAMENTI));
			  				if( pubblicata_gara && proceduraRistretta && proceduraNonCompleta && !isAccQuadroNc 
                                && !isDelega && checkDatiComuni
                               ){//TICKET ALM - 3.04.3
			  %>
			              <li>
			              		<a title="Perfezionamento procedure con piu' fasi" href="<%=ParametriServlet.SRV_BANDO_GARA %>?toDo=<%=ParametriServlet.ACTION_CARICA_INVITO %>&<%=ParametriServlet.SESSION_ID_GARA %>=<%= currentRow.getNulledField(GARA.ID_GARA) %>&<%=ParametriServlet.TIPO_PUBBLICAZIONE%>=<%=ParametriServlet.PUBBLICAZIONE%>">Perfezionamento procedure con piu' fasi</a> <%-- TICKET ALM #4196 --%>
			              </li>
           <% 			}
				  	}
			  }
				
				String tipoOperazione=currentRow.existField(PUBBLICAZIONI.TIPO_OPERAZIONE) ? currentRow.getNulledField(PUBBLICAZIONI.TIPO_OPERAZIONE) : "";
				List<PubblicazioneBean> storico = (List)request.getAttribute(ParametriServlet.STORICO_PUBBLICAZIONI);
			   boolean storicoHasInvito = false;
				if (storico != null && !storico.isEmpty()) {
				    for(PubblicazioneBean pubb : storico){
				    	if(pubb.getTipoOperazione().equals(PubblicazioneBean.TipoOperazione.LETTINV.getCodice()))
				    		storicoHasInvito = true;
				    }
				}
				if(tipoOperazione.equals(PubblicazioneBean.TipoOperazione.LETTINV.getCodice()) || storicoHasInvito){
			%>	      	         
<%-- 			   		    <td <%=noDisplay %> nowrap="nowrap"> --%>
<!-- 			   		    <ul> -->
							<li <%=noDisplay %>>
			   		    <a title="Gestisci elenco invitati" href="<%= ParametriServlet.SRV_GESTIONE_ELENCO_INVITATI%>?toDo=<%=ParametriServlet.ACTION_NEW%>&<%= ParametriServlet.SESSION_ID_GARA %>=<%= currentRow.getNulledField(GARA.ID_GARA)%>">Gestisci elenco invitati</a>
			   		    </li>
<!-- 			   		    </ul>	 -->
<%
				}			
				if(!inDefinizione && user.isAmministratore() && !cancellato_gara){
					/*******************************************************************************/
					/******* VL - aggiunto per permettere l'inserimento della gara tramite link ****/
					/*******************************************************************************/
					int checkIfOk = 0;
					try{			
						checkIfOk = Integer.parseInt(String.valueOf(request.getSession().getAttribute("checkIfOK")));
						checkIfOk++;
					}catch(Exception e){
						e.printStackTrace();
					}
			%>
					<%--<td nowrap="nowrap"><a title="Modifica data conferma" href="javascript:getDateAndAction('InserisciGara?toDo=<%=ParametriServlet.ACTION_CONFERMA%>&<%= ParametriServlet.SESSION_ID_GARA %>=<%= currentRow.getNulledField(GARA.ID_GARA)%>&checkIfOK=<%=checkIfOk%>&dataConferma=');">Modifica data conferma</a></td>--%>
			<%		
					if(user.isAmministratore() 
					      && !garaHasSchede && pubblicata_gara
					      && !bloccoAVCPASS){
		   %>
<%-- 			 <td <%=noDisplay %> nowrap="nowrap"> --%>
<!-- 			 <ul> -->
			<li <%=noDisplay %>>
			 <a title="Sblocca gara" href="javascript:confirmAction('InserisciGara?toDo=<%=ParametriServlet.SBLOCCA_GARA%>&<%= ParametriServlet.FIELD_NAME_ID_GARA %>=<%= currentRow.getNulledField(GARA.ID_GARA)%>&checkIfOK=<%=checkIfOk%>');">Sblocca gara</a>
			 </li>
<!-- 			 </ul> -->
		   <%		
				}
					
			if(request.getAttribute(ParametriServlet.ID_REAL) !=null 
			      && (Boolean)request.getAttribute(ParametriServlet.ID_REAL)==true
			      && !bloccoAVCPASS){
						
			%>		
<%-- 			 <td <%=noDisplay %> nowrap="nowrap"> --%>
<!-- 			 <ul> -->
				<li <%=noDisplay %>>
			 <a title="Modifica mod realizzazione" href="javascript:confirmAction('InserisciGara?toDo=<%=ParametriServlet.MODIFICA_REALIZZAZIONE_GARA%>&<%= ParametriServlet.FIELD_NAME_ID_GARA %>=<%= currentRow.getNulledField(GARA.ID_GARA)%>&checkIfOK=<%=checkIfOk%>');">Cambia in accordo quadro</a>
			 </li>
<!-- 			 </ul> -->
				<%
					}
			}

			if(//&& SimogProperties.getInstance().isINT87Attivo(PageHelper.getFormattedDBDate(dataCreazioneGara))
			      //&& ! "".equals(listaGare.getRow(0).getNulledField(GARA.DATA_PERFEZIONAMENTO_BANDO))
			       SimogProperties.getInstance().isINT87Attivo(PageHelper.getFormattedDBDate(dataPubbLotto))
			      && user.isRSSAorRUP() 
			      && pubblicata_gara
				   && !cancellato_gara
				   && !isDelega //TICKET ALM - 3.04.3
				   && !isAccQuadroNc //TICKET ALM - 3.04.3
				   // nei requisiti nonn e' menzionato  && !bloccoAVCPASS
				   ){
							
				%>		
	<%-- 			 <td <%=noDisplay %> nowrap="nowrap"> --%>
	<!-- 			 <ul> -->
					<li <%=noDisplay %>>
				 <a title="Modifica limitata al solo valore del campo Estrema urgenza ex art. 9 commi 1 e 2 D.L. 133/2014" href="inizializzaGara?<%= ParametriServlet.SESSION_ID_GARA %>=<%= currentRow.getNulledField(GARA.ID_GARA)%>&toDo=<%=ParametriServlet.ACTION_MODIFICA_DL133%>">Modifica Gara</a>
				 </li>
	<!-- 			 </ul> -->
	
			<!-- Ticket 20055 -->
			
				<%if(enableButtonModifica){ %>
			
	            	  <li <%=noDisplay %>>
						<!--   <a title="Annulla Rettifica" href="annullaRettifica?<%= ParametriServlet.SESSION_ID_GARA %>=<%= currentRow.getNulledField(GARA.ID_GARA)%>&toDo=<%=ParametriServlet.ACTION_ANULLA_RETTIFICA%>">Annulla Rettifica</a> -->
						<a title="Termina Rettifica" onclick="popupAnnullaRettifica('<%= ParametriServlet.SESSION_ID_GARA %>=<%= currentRow.getNulledField(GARA.ID_GARA)%>&toDo=<%=ParametriServlet.ACTION_ANULLA_RETTIFICA%>')">Termina Rettifica</a> 
					  </li>
				<%} %>
				
				
					<%
				}
 
				if(SimogProperties.getInstance().getWsAnagUrl() != null 
				      && !cancellato_gara 
				      && user.isAmministratore()
				      && !bloccoAVCPASS) {
				
			%>
<%-- 				  <td <%=noDisplay %> nowrap="nowrap"> --%>
<!-- 				  <ul> -->
					<li <%=noDisplay %>>
				  <a title="Variazione Stazione Appaltante" href="variazioneSA?<%=ParametriServlet.SESSION_ID_GARA %>=<%= currentRow.getNulledField(GARA.ID_GARA) %>">Variazione Stazione Appaltante</a>
					</li>				  
<!-- 				  </ul> -->
		<%    
		       }

				if(user.isRUP() && !cancellato_gara && !isAccQuadroNc){
				//out.print("<br>user.isRUP() && !cancellato_gara && !isAccQuadroNc : "+ user.isRUP()+"___"+cancellato_gara+"___"+isAccQuadroNc+"__prendibile__"+prendibile);
					if(prendibile){
						boolean userGrant=false;
						Hashtable centriDiCostoUser = user.getUfficiByProfilo(it.avlp.simog.beans.ProfiloEnum.RUP);
						//out.print("centriDiCostoUser size : "+centriDiCostoUser.size());						
						java.util.Enumeration<String> listaCdc = centriDiCostoUser.keys();						
						while(listaCdc.hasMoreElements()) {						
			                 String key = listaCdc.nextElement();
			                 //out.print("<br>KEY : "+key);
			                 if(key.equals(idSaRiferimento)){
			                	 userGrant=true;
			                 }
			             }
						%>
						<!-- MAC 48964 3.04.12.1 -->
						<%if(!userGrant){ %>
						<li <%=noDisplayAggiuStipContrat %>> <!--MAC 34163 3.04.8--> 
						<%-- <li <%=noDisplay %>> --%> 						
					       <a href="javascript:confirmAction('presaInCaricoGara?<%= ParametriServlet.FIELD_NAME_ID_GARA %>=<%= currentRow.getNulledField(GARA.ID_GARA) %>');">Presa in carico</a>
					    </li>
						
				<% }	//MAC 48964 3.04.12.1
						if(userGrant) {%>
							<li <%=noDisplay%>> 					
					       <a href="javascript:confirmAction('presaInCaricoGara?<%= ParametriServlet.FIELD_NAME_ID_GARA %>=<%= currentRow.getNulledField(GARA.ID_GARA) %>');">Presa in carico</a>
					    </li>
						<%	
						}//fine MAC 48964			
					}
					if(pubblicata_gara && Costanti.FLAG_VALORE_SI.equals(flagSAAgente)){
						List<String> datiStoricoDelega = (List)request.getAttribute(ParametriServlet.DATI_STORICO_DELEGA);
						//La gara non e' stata presa in carico dalla delegante.
						//Verifica che l'utente abbia una collaborazione associata alla sa delegante
						if(datiStoricoDelega!=null && datiStoricoDelega.size() > 0 && "".equals(datiStoricoDelega.get(5))) {
						    boolean saDeleganteLogged=false;
							Hashtable collaborazioni = user.getAmministrazioniByProfilo(it.avlp.simog.beans.ProfiloEnum.RUP);
							java.util.Enumeration<String> listaCollaborazioni = collaborazioni.keys();
				    	   
				    	     while(listaCollaborazioni.hasMoreElements()) {
				                 String key = listaCollaborazioni.nextElement();
				                 if(key.equals(cfAmmDelegante)){
				                	 saDeleganteLogged=true;
			}
		}
				    	     if(saDeleganteLogged){
							
				%>		
	<%-- 			 <td <%=noDisplay %> nowrap="nowrap"> --%>
	<!-- 			 <ul> -->
					<li <%=noDisplayAggiuStipContrat %>> <!--MAC 34163 3.04.8-->
	                <!--<li <%=noDisplay %>>-->					
				 		<a title="Presa in carico gara delegata"  href="<%=ParametriServlet.SRV_PRESA_IN_CARICO_GARA_DELEGATA%>?<%= ParametriServlet.SESSION_ID_GARA %>=<%= currentRow.getNulledField(GARA.ID_GARA)%>&action=load">Presa in carico gara delegata</a>
				 	</li>
	<!-- 			 </ul> -->
					<%
				}
							
						}
					}
					
					
				}
			
			}
		}
		
	}
   %>
<!--     </li> -->
		</ul>
    </div>


			<%-- gm pannello dello storico delle pubblicazioni --%>
			<% List<PubblicazioneBean> storicoPubblicazioni = (List)request.getAttribute(ParametriServlet.STORICO_PUBBLICAZIONI); %>
			<%if (storicoPubblicazioni != null && !storicoPubblicazioni.isEmpty()) {%>
		 		
			 		<fieldset>
						<legend>Storico Pubblicazioni</legend>
						
			 				<fieldset class="gara">
								<div align="center">
									<table width="100%">	
					              <tr>
						             <th class="garaTh">Tipo pubblicazione</th>
						             <th class="garaTh">Data pubblicazione</th>
						             <th class="garaTh">Allegati</th>
					              </tr>
					              <% for (PubblicazioneBean pub : storicoPubblicazioni) {%>
					              <tr>
					                <% String tipoPubblicazione = null; 
					                   if(PubblicazioneBean.TipoOperazione.BANDO.getCodice().equals(pub.getTipoOperazione()))
					                	   tipoPubblicazione = "Bando di Gara";
					                   else if(PubblicazioneBean.TipoOperazione.LETTINV.getCodice().equals(pub.getTipoOperazione()))
					                	   tipoPubblicazione = "Lettera d'Invito";
					                   else if(PubblicazioneBean.TipoOperazione.AVVISOAGG.getCodice().equals(pub.getTipoOperazione()))
					                	   tipoPubblicazione = "Avviso di Aggiudicazione";
					                   else if(PubblicazioneBean.TipoOperazione.RETTIFICA.getCodice().equals(pub.getTipoOperazione()))
					                	   tipoPubblicazione = "Rettifica";
					                   else
					                      tipoPubblicazione = "Perfezionamento";
					                %>
					                <td><%=tipoPubblicazione %></td>
					                <td><%=PageHelper.getViewDate(pub.getDataInizioPubblicazione()) %></td>
					                <td><input type="button" onclick="apripopup('<%=ParametriServlet.SRV_STORICO_ALLEGATI%>?<%=ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE %>=<%=pub.getIdPubblicazione() %>&<%=ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB %>=<%=pub.getDataInizioPubblicazione()%>'); return false;"; value="visualizza" /></td>    
					                <%-- 
					                <td><a title="link allegati" href="<%=ParametriServlet.SRV_STORICO_ALLEGATI%>?<%=ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE %>=<%=pub.getIdPubblicazione() %>&<%=ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB %>=<%=pub.getDataInizioPubblicazione() %>" >visualizza</a></td>
					                --%>
					              </tr>
					              <% } %>
					            </table>		
								</div>
							</fieldset>									
					</fieldset>			
				
		    <%} %>
		    
		    <%-- TICKET ALM #659 - 3.04.4 --%>
		    <% List<String> datiStoricoDelega = (List)request.getAttribute(ParametriServlet.DATI_STORICO_DELEGA);  %>
		    <% if(datiStoricoDelega!=null && datiStoricoDelega.size() > 0 && !"".equals(datiStoricoDelega.get(5))) { %>
			    <fieldset>
					<legend>Storico Delega Gara</legend>
					<table>
								<tr>
									<th class="garaTh" width="40%">RUP Amministrazione delegata</th>
									<td class="garaTd"><%= datiStoricoDelega.get(0) %></TD>
								</tr>
								<tr>
									<th class="garaTh" width="40%">Codice Fiscale Amministrazione Delegata</th>
									<td class="garaTd"><%= datiStoricoDelega.get(1) %></TD>
								</tr>
								<tr>
									<th class="garaTh" width="40%">Denominazione Amministrazione Delegata</th>
									<td class="garaTd"><%= datiStoricoDelega.get(2) %></TD>
								</tr>
								<tr>
									<th class="garaTh" width="40%">ID SA Delegata</th>
									<td class="garaTd"><%= datiStoricoDelega.get(3) %></TD>
								</tr>
								<tr>
									<th class="garaTh" width="40%">Denominazione SA Delegata</th> 
									<td class="garaTd"><%=  datiStoricoDelega.get(4) %></TD>
								</tr>
								<tr>
									<th class="garaTh" width="40%">Data della presa in carico da parte dell'amministrazione delegante</th> 
									<td class="garaTd"><%=  datiStoricoDelega.get(5) %></TD>
								</tr>
						</table>
				</fieldset>
		    <% } %>
	
</div>
</div>
<%@page import="it.avlp.simog.beans.PubblicazioneBean"%>
<%@page import="java.util.List"%>