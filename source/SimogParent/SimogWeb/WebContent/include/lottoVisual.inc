<%@page import="it.avlp.simog.util.PageHelper"%>
<%@page import="it.avlp.simog.util.SimogProperties"%>
<%
/*
Variabili da utilizzare : 

	lottoOggetto
	sommaUrgenza
	lottoImporto
	lottoImportoSA
	lottoImportoImpresa
	lottoDataInibPagamenti
	lottoDataScadenzaPagamenti
	lottoDataPubblicazione
	lottoTipologia
	lottoSceltaContraente
	lottoMotivoCollegamento
	lottoDataScadenzaRichiestaInvito
	lottoDataLetteraInvito	
	flagCUP
	lottoDataCreazione					//Ticket ALM #991
*/
%>

					
<%@page import="it.avlp.simog.db.SimogFlags"%>
<table>
					<TBODY>
					    <!-- Ticket ALM #991 -->
					    <TR>
					    	<TH>Data creazione lotto</TH>
					    	<TD><%= lottoDataCreazione %></TD>
					    </TR>
					    <!-- Fine Ticket ALM #991 -->
						<TR>
							<TH>Oggetto</TH>
							<TD><%= lottoOggetto %></TD>
                    </TR>
    <%--TICKET ALM #3832-02.1 --%>				
<%if(SimogFlags.is3042Active() && !SimogProperties.getInstance().isDataCreatedAfter3042(PageHelper.getFormattedDBDate(dataCreazioneGara))) { %>				
						
						<TR>
<%if(SimogFlags.is30233_NRFWEBGL04Active()) {%>				
				<th>Esecuzione di lavori di somma urgenza (ex art. 176 DPR 207/2010)</th>
<%} else { %>
				<th>Esecuzione di lavori di somma urgenza (ex art. 147 DPR 554/99)</th>
<%} %>
							<TD><%= sommaUrgenza %></TD>
						</TR>
						<%} %>
<%--FINE TICKET ALM #3832-02.1  --%>	
						<TR>
							<TH>Importo del lotto</TH>
							<TD><%= lottoImporto %></TD>
						</TR>
						<TR>
								<TH>&nbsp;&nbsp;&nbsp;&nbsp;di cui per opzioni</TH>
								<TD><%= importo_opzioni %></TD>
						</TR>
						<TR>
								<TH>&nbsp;&nbsp;&nbsp;&nbsp;di cui per attuazione della sicurezza</TH>
								<TD><%= importo_attuazione_sicurezza %></TD>
						</TR>
												
					<%--  	Non piu' visualizzato	
						<TR>
							<TH>Importo SA</TH>
							<TD><%= lottoImportoSA %></TD>
						</TR>
					--%>
						<TR>
							<TH>Importo contributo per il partecipante</TH>
							<TD><%= lottoImportoImpresa %></TD>
						</TR>
						<TR>
							<TH>Data Cancellazione Lotto</TH>
							<TD class="warning"><%= dataCancellazioneLotto %></TD>
						</TR>	
						<TR>
							<TH>Data inibizione di pagamento</TH>
							<TD class="warning"><%= lottoDataInibPagamenti %></TD>
						</TR>
						
					<%-- Motivazione e Note sulla cancellazione --%>

					<% if(id_motivazione != null && id_motivazione.trim().length() > 0) { %>
						<TR>
							<TH>Motivazione </TH>
							<TD class="warning"><%= desc_motivazione %></TD>
						</TR>
					<% } %>
					<% if(noteCancellazione != null && noteCancellazione.trim().length() > 0) { %>
						<TR>
							<TH>Note</TH>
							<TD class="warning"><%= noteCancellazione %></TD>
						</TR>
					<% } %>

					<%-- End -------------------------------------%>	
<% if (!isAdesione){ %>						
						<TR>
							<TH>Data scadenza pagamenti</TH>
							<TD class="message"><%= lottoDataScadenzaPagamenti%></TD>
						</TR>						
<% } %>
						<TR>
<% if (!isAdesione){ %>						
						<TR>
							<TH>Ora scadenza pagamenti</TH>
							<TD class="message"><%= oraScadenza%></TD>
						</TR>						
<% } %>
<% if(!Costanti.DEFAULT_DATA_PUBB_TED_DATA.equals(lottoDataPubblicazione)) { %>
						<TR>
<% if (!isAdesione){ %>						
							<TH>Data pubblicazione</TH>
<% } else { %>
							<TH>Data di adesione all'accordo quadro/convenzione</TH>
<% } %>
							<TD class="message"><%=lottoDataPubblicazione  %></TD>
						</TR>
						
						<% } %>
<% if (proceduraRistretta){ %>	
						<TR>
							<TH>Data di scadenza per la presentazione della richiesta di invito</TH>
							<TD class="message"><%= lottoDataScadenzaRichiestaInvito %></TD>
						</TR>
						<TR>
							<TH>Data della lettera di invito</TH>
							<TD class="message"><%= lottoDataLetteraInvito %></TD>
						</TR>						
<% } %>
						<TR>
							<TH>CPV Prevalente</TH>
							<TD><%= lottoCpv %></TD>
						</TR>
						<TR>
							<TH>CPV Secondarie</TH>
							<TD><%= lottoCpvSec %></TD>
						</TR>
						<TR>
							<TH>Scelta del contraente</TH>
							<TD><%= lottoSceltaContraente %></TD>
						</TR>

                        <%-- TICKET ALM - 3.04.3 #7849 --%>
						 <%if(SimogProperties.getInstance().isDataCreatedAfter3044(PageHelper.getFormattedDBDate(dataCreazioneGara))) { %>
								<TR>
										<TH>Categoria merceologica di cui al DPCM soggetti aggregatori</TH>
										<TD><%= descCodCat %></TD>
								</TR>
						<% } %>

						<%-- TICKET ALM - 3.04.2 NG --%>
						<% if(SimogFlags.is3042Active() && SimogProperties.getInstance().isDataCreatedAfter3042(PageHelper.getFormattedDBDate(dataCreazioneGara))) { %>
							<TR>
								<TH>Tipo appalto riservato</TH>
								<TD><%= idAffRiservati %></TD>
							</TR>
						<% } %>
						<%-- FINE TICKET ALM - 3.04.2 NG  --%>
						
						<TR>
							<TH>Oggetto principale del contratto</TH>
							<TD><%= tipoContratto %></TD>
						</TR>	
						<!-- MEV 38205 3.04.8.1 -->
						<TR>
							<TH>Uso di metodi e strumenti elettronici di modellazione per l'edilizia e le infrastrutture?</TH>
							<TD><%= flagUsoMetodiEdilizia %></TD>
						</TR>	
						<!-- MEV 38205 3.04.8.1 -->				
						<TR>
							<TH>Contratto escluso</TH>
							<TD><%= escluso %></TD>
						</TR>				
						<TR>
							<TH>Esclusione</TH>
							
							<% if(!"No".equals(escluso)) { %>
							   <TD><%= artEsclusione %></TD>
							   <% } else { %>
							   <TD></TD>
							   <%} %>
						</TR>	
						<%-- TICKET ALM - 3.04.2 NG --%>
						<% if(SimogFlags.is3042Active() && SimogProperties.getInstance().isDataCreatedAfter3042(PageHelper.getFormattedDBDate(dataCreazioneGara))) { %>
							<TR>
							   <TH>Contratto regime particolare di appalto (speciale o alleggerito)</TH>
							   <TD><%=flagRegime %></TD>
							</TR>
							<TR>
							   <TH>Regime particolare di appalto</TH>
							   <% if(!"No".equals(flagRegime)) { %>
							   <TD><%= idArtRegime %></TD>
							   <% } else { %>
							   <TD></TD>
							   <%} %>
							</TR>
						<% } %>
						<%-- FINE TICKET ALM - 3.04.2 NG  --%>
						<TR>
							<TH>Categoria Prevalente o Scorporabile</TH>
							<TD><%= lottoCategoriaPrevalente %></TD>
						</TR>		

				       <%-- TICKET ALM - 3.04.2 NG --%>
						<% if(SimogFlags.is3042Active() && SimogProperties.getInstance().isDataCreatedAfter3042(PageHelper.getFormattedDBDate(dataCreazioneGara))) { %>	
					        <TR>
								<TH>Il lavoro o l'acquisto di bene o servizio e' stato previsto all'interno della programmazione</TH>
								<TD><%= flagDL50 %></TD>
							</TR>				
							
							<TR>
								<TH>Prima annualita' dell'ultimo programma nel quale e' stato inserito l'intervento o l'acquisto</TH>
								<TD><%= primaAnnualita %></TD>
							</TR>	
							<%-- Ticket #20057 - 09 - 02 - 21 --%>
						<%if(SimogFlags.is3043Active() && SimogProperties.getInstance().isDataCreatedAfter3046(PageHelper.getFormattedDBDate(dataCreazioneGara))) { %>	
								<TR>
										<TH>Durata dell'affidamento in giorni</TH>
										<TD><%= durataAffidamentoInGiorni %></TD>
								</TR>
						<% } %>
                         <% } %>
						<%-- FINE TICKET ALM - 3.04.2 NG  --%>
			      <%-- gm nuovo codice 3.0 --%>
				  <%-- TICKET ALM #2845 - oscuramento vecchi campi --%>
				   <%--TICKET ALM #3832-02.1 --%>				
<%if(SimogFlags.is3042Active() && !SimogProperties.getInstance().isDataCreatedAfter3042(PageHelper.getFormattedDBDate(dataCreazioneGara))) { %>				
	
						<TR>
								<TH>Triennio anno inizio</TH>
								<TD><%= triennioAnnoInizio %></TD>
							</TR>
							<TR>
								<TH>Triennio anno fine</TH>
								<TD><%= triennioAnnoFine %></TD>
							</TR>
							<TR>
								<TH>Progressivo nell'ambito del triennio</TH>
								<TD><%= triennioAnnoProgressivo %></TD>
							</TR>
							<% } %>
			<%--	FINE TICKET ALM #2845 --%>

							<TR>
								<TH>CUI programma triennale lavori pubblici o programma biennale forniture e servizi</TH>
								<TD><%= annualeCuiMinInf %></TD>
							</TR>	
					
					<%--gm nuovo codice pubblicazione bando 3.0 --%>
					   <TR>
								<TH>Codice del luogo di esecuzione del contratto (ISTAT)</TH>
								<TD><%= luogoIstat %></TD>
						</TR>
						<TR>
								<TH>Codice del luogo di esecuzione del contratto (NUTS)</TH>
								<TD><%= luogoNuts %></TD>
						</TR>
					<%--gm fine nuovo codice pubblicazione bando 3.0 --%>
					</tbody>
<% if(SimogFlags.is30233_RFWEBGL02Active()){ %>		
	                   <TR>
								<TH>L'appalto prevede ripetizioni o altre opzioni?</TH>
								<TD><%= flagPrevRip %></TD>
						</TR>
						<%-- TICKET ALM - 3.04.3 #7849 --%>
						 <%if(SimogFlags.is3043Active() && !SimogProperties.getInstance().isDataCreatedAfter3043(PageHelper.getFormattedDBDate(dataCreazioneGara))) { %>
								<TR>
										<TH>Ripetizione di precedente contratto</TH>
										<TD><%= flagRipetiz %></TD>
								</TR>
						<% } %>
						<%-- FINE TICKET ALM - 3.04.3 #7849 --%>
						
						<%-- Ticket #20058 - 09 - 02 - 21 --%>
						<%if(SimogFlags.is3043Active() && SimogProperties.getInstance().isDataCreatedAfter3046(PageHelper.getFormattedDBDate(dataCreazioneGara))) { %>	
								<TR>
										<TH>Durata dei rinnovi e delle ripetizioni in giorni</TH>
										<TD><%= durataRinnoviRipetizioni %></TD>
								</TR>
						<% } %>
						<%-- fine --%>
						
						<%-- TICKET  ALM - 3.04.3 #2846 --%>
						<%if(SimogFlags.is3043Active() && SimogProperties.getInstance().isDataCreatedAfter3043(PageHelper.getFormattedDBDate(dataCreazioneGara))) { %>				
							<TR>
								<TH>L'appalto deriva da una delle seguenti ipotesi di collegamento?</TH>
								<TD><%= lottoMotivoCollegamento %></TD>
							</TR>
						<% } %>
						<%-- FINE TICKET ALM - 3.04.3 #2846  --%>	
						
						<TR>
								<TH>CIG collegato</TH>
								<TD><%= cigRipetiz %></TD>
						</TR>
<%} %>			

<% 
	if(SimogFlags.is3031_RFWEBGL02Active()){ %>
						<TR>
								<TH>L'appalto e' finalizzato alla realizzazione di  progetti  d'investimento pubblico <br/> per i quali e' prevista l'acquisizione del codice CUP ai sensi dell'art. 11 L 3/2003 e ss.mm.? <br/> 
				 					 (E' necessario acquisire e comunicare il CUP per interventi finanziati, anche in parte, <br/> con risorse Comunitarie)</TH>
								<TD><%= flagCUP %></TD>
						</TR>
						
						<!-- fix 40610 3.04.9 -->
						<tr>
							<th >Autodichiarazione deroga qualificazione SA</th>
							<td><%= derogaQualificazioneSA %></td>
						</tr>
						<!-- FINE fix 40610 3.04.9 -->
<% } %>			
				</table>
