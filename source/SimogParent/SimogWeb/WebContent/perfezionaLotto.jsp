<% try{ %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="h" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>

<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>
<%@ page import="java.math.BigDecimal"%>
<%@page import="it.avlp.simog.db.Costanti"%>
<%@page import="it.avlp.simog.db.SimogFlags"%>

<!-- calendar stylesheet -->
<link rel="stylesheet" type="text/css" media="all" href="calendar/calendar-blue.css" title="win2k-cold-1" />

<!-- main calendar program -->
<script type="text/javascript" src="calendar/calendar.js"></script>

<!-- language for the calendar -->
<%@ include file="include/calendar-dynamic.inc" %>

<!-- the following script defines the Calendar.setup helper function, which makes
     adding a calendar a matter of 1 or 2 lines of code. -->
<script type="text/javascript" src="calendar/calendar-setup.js"></script>
<script type="text/javascript" src="script/pageutils.js"></script>

<% String currentDate = PageHelper.getCurrentDate(); %>

<title>SIMOG - <utils:message key="visualizza.gestioneGare" /> - <utils:message key="lotto.perfezionamentoLotto" /></title>

<script type="text/javascript">
<!--
	function controllaData(){
		if(document.getElementById('dtscadenza').value == ''){
			
			alert('<%= MessageHelper.getMessage(request, "lotto.verificareDataScadenza") %>');
			return false;
		}
		//GGDataPubblicazione
		
		if(document.getElementById('dtpubblicazione').value == ''){
			
			alert('<%= MessageHelper.getMessage(request, "lotto.verificareDataPubblicazione") %>');
			return false;
		}
		
		return true;
		
	}
//-->
</script>

<!-- TB: ticket popup modali. Import css e js -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<!-- fine import popup modali -->

</head>


<% TableBean infoLotto = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); 
	TableBean listaGare = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); %>

<% TableBean listaTipologia = (TableBean)request.getAttribute(ParametriServlet.TIPOLOGIA_BEAN); %>

<% TableBean listaCategoriaPrevalente = (TableBean)request.getAttribute(ParametriServlet.CATEGORIA_BEAN); %>

<% TableBeanRow currentRow = null; %>

<%-- Verifica Gara Confermata --%>
<% boolean Confermato = StatiScheda.CONFERMATO_STRING.equals(infoLotto.getRow(0).getNulledField(GARA.ID_STATO)); %>

<% boolean nienteLotti = false; //Per garaVisual.inc 
boolean bloccoAVCPASS = false; //Per garaVisual.inc%>

<body>
<div id="dialog"></div>
<form name="PerfezionamentoLotto" action="perfezionaLotto" method="post" onsubmit="return controllaData()">

<div id="gabbia">
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuGara.inc" %>

	<div id="bodypage">
		<div class="bodypage-e">
		
		<h1><utils:message key="lotto.perfezionamentoLotto" /></h1>
		<%@ include file="include/gestisciErrore.inc" %>
		<div class="hmenu">
			<ul><li><a title="<utils:message key="dettaglio.paginaPrecedente" plain="true" />" href="<%=ParametriServlet.SRV_VISUALIZZA_DETTAGLIO%>
				?<%=ParametriServlet.SESSION_ID_GARA%>=<%=infoLotto.getRow(0).getNulledField(LOTTO.ID_GARA) %>
				&<%=ParametriServlet.FROM_GARE %>=<%=Costanti.FLAG_VALORE_SI %>"><utils:message key="lotto.ritorna" /></a></li></ul>
		</div>
		
		<div class="errore">
		<p><utils:message key="lotto.attenzionePerfezionamento" /></p>
		<p><utils:message key="lotto.fasePerfezionamento" /></p>
		</div>

		<% for ( int rowIndex = 0; rowIndex < infoLotto.getTableSize(); rowIndex++ ) { %>
		
			<% currentRow = infoLotto.getRow(rowIndex); %>
			
			<% if ( rowIndex == 0 ) { %>

				<%
				String codiceGara = currentRow.getNulledField(GARA.ID_GARA);

				String oggettoGara = PageHelper.formattaTesto(currentRow.getNulledField(GARA.TABLE_NAME + GARA.OGGETTO));
				String dataCreazioneGara = PageHelper.getFormattedDate( currentRow.getNulledField(GARA.DATA_CREAZIONE) ) ;		
				/***************************************************/
				/****  Visualizzazione N.D. per l'importo gara  ****/
				/***************************************************/
				String importoGara = PageHelper.IMPORTO_ND;	
				try{
					//senza '&euro;'
					String unformattedImporto = currentRow.getNulledField(GARA.IMPORTO_GARA);
					if(!"".equals(unformattedImporto) && new BigDecimal(unformattedImporto).compareTo(new BigDecimal(Costanti.IMPORTO_FUORI_SCALA)) != 0){
						importoGara = PageHelper.getFormattedImporto(unformattedImporto);
					}
				}catch(NumberFormatException t){	t.printStackTrace();	}
				/***************************************************/
				String importoSAGara = PageHelper.getFormattedImporto(currentRow.getNulledField(GARA.IMPORTO_SA_GARA));
				String statoGara = currentRow.getNulledField(STATI_SCHEDA.DESCRIZIONE);
				String idSaRiferimento = currentRow.getNulledField(GARA.ID_STAZIONE_APPALTANTE);
				String descrSARiferimento = currentRow.getNulledField( GARA.DENOM_STAZIONE_APPALTANTE );				
				String amministrazioneCodiceFiscale	= currentRow.getNulledField(GARA.CF_AMMINISTRAZIONE);
				String amministrazioneDescrizione = currentRow.getNulledField( GARA.DENOM_AMMINISTRAZIONE );

				String garaDataCancellazione = currentRow.getNulledField(GARA.DATA_CANCELLAZIONE_GARA);
				String garaDataTerminePagam = currentRow.getNulledField(GARA.DATA_TERMINE_PAGAMENTO);
				String garaDataInibPagam = currentRow.getNulledField(GARA.DATA_INIB_PAGAM);
				String garaDataConferma = currentRow.getNulledField(GARA.DATA_CONFERMA_GARA);
				String garaDataComun = currentRow.getNulledField(GARA.DATA_COMUN);
				String garaDataPerfezionamento = currentRow.getNulledField(GARA.DATA_PERFEZIONAMENTO_BANDO);
				String descMotivGara = currentRow.getNulledField("G_" + MOTIVI_CANCELLAZIONE.DESCRIZIONE);
				String noteCancGara = PageHelper.formattaTesto(currentRow.getNulledField(GARA.NOTE_CANC_GARA));
				String id_motivazioneGara = currentRow.getNulledField(GARA.ID_MOTIVAZIONE_CANC);
				//gm nuovo campo simog 3.04
				String numeroLotti = currentRow.getNulledField(GARA.NUMERO_LOTTI);

				String RSSA_CodiceFiscale = currentRow.getNulledField(GARA.CF_UTENTE);

				String tipoScheda = currentRow.getNulledField(TIPI_CATEGORIA.TABLE_NAME);
				String modoGara = currentRow.getNulledField(MODO_INDIZIONE.TABLE_NAME);
				String modoReal = currentRow.getNulledField(MODI_REALIZZAZIONE.TABLE_NAME);
				String idModoReal = currentRow.getNulledField(MODI_REALIZZAZIONE.ID_MODO_REAL);
				String cigQuadro =  currentRow.getNulledField( GARA.CIG_ACC_QUADRO );
				
				//TICKET ALM #664
				String strumentoSvolgimento = currentRow.getNulledField(STRUMENTI_SVOLGIMENTO_PROCEDURE.TABLE_NAME);
				//FINE TICKET ALM #664
				
				//TICKET ALM #3832
		        String estremaUrgenza = currentRow.getNulledField(ART_ESTREMA_URGENZA_SOMMA_URGENZA.TABLE_NAME);
		        //FINE TICKET ALM #3832
		        
		        //TICKET ALM #3834
		        String modIndAllegatoIX = currentRow.getNulledField(MODALITA_INDIZIONE_ALLEGATO_IX.TABLE_NAME);
		        //FINE TICKET ALM #3834
  
	        	// is3031_ESCL_AVCPASS
	        	String esclusioneAVCPass = SimogFlags.is3031_ESCL_AVCPASS() ? currentRow.getNulledField(GARA.ESCLUSO_AVCPASS) : "";
	        	
	   		//INT85
	   		String sceltaLegge89 = SimogFlags.isINT85_RFWEBGL01Active() ? currentRow.getNulledField(GARA.SCELTA_LEGGE89) : "";
	   		
	   		//INT87
	   		String urgenzaDL133 = SimogFlags.isINT87_RFSIMOGWEB01Active() ? currentRow.getNulledField(GARA.URGENZA_DL133) : "";  	 

	   		//is30350_RFWEBGL01Active
	   		String motivoEagg = SimogFlags.is30350_RFWEBGL01Active() ? currentRow.getNulledField(EAGG_MOTIVI.TABLE_NAME) : "";
	   		String[] categEagg = new String[0];	
	   		
	   	//TICKET ALM #659 - 3.04.4
	   		String flagSAAgente = currentRow.getNulledField(FUNZIONI_DELEGATE_GARA.T_FLAG_SA_AGENTE);
	   		String idFDelegate = currentRow.getNulledField(FUNZIONI_DELEGATE.TABLE_NAME);
	   		String cfAmmDelegante = currentRow.getNulledField(FUNZIONI_DELEGATE_GARA.T_CF_AMM_AGENTE);
	   		String denAmmDelegante =currentRow.getNulledField(FUNZIONI_DELEGATE_GARA.T_DEN_AMM_AGENTE);
	   		
	   		String codiceAusa = currentRow.getNulledField(GARA.CODICE_AUSA);
	   		// fix 34470 3.04.8
	   		String linkAffidamentoDiretto = (String) request.getAttribute(ParametriServlet.FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO);
	
				%>

				<h4>Gara di cui fa parte il lotto</h4>
				<%@ include file="include/garaVisual.inc" %>
				
				<h4>Lotto da perfezionare</h4>		
				
				<% String cigCompleto = PageHelper.getCIG( currentRow.getNulledField(LOTTO.CIG) + currentRow.getNulledField(LOTTO.CIG_KKK), currentRow.getNulledField(LOTTO.SOMMA_URGENZA), currentRow.getNulledField(LOTTO.DATA_CREAZIONE_LOTTO) ); %>
					
				<h5>Lotto - CIG  [<%= cigCompleto %>]</h5>
				<% String dataComunicazione =  currentRow.getNulledField(LOTTO.DATA_COMUNICAZIONE); %>
				<% if ( ! dataComunicazione.equalsIgnoreCase("") ) { %>
					<h6>Il lotto &egrave; stato comunicato al Sistema Esattore il <strong><%= PageHelper.getFormattedDate( dataComunicazione ) %></strong></h6>
				<% } else { %>
					<h6>Il SIMOG provveder&agrave; a comunicare al Sistema Esattore le informazioni relative al perfezionamento. <strong>Dopo questa fase non sar&agrave; pi&ugrave; possibile effettuare modifiche</strong></h6>
				<% } %>

				<% String dataPubblicazione =  currentRow.getNulledField(LOTTO.DATA_PUBBLICAZIONE);
				   String dataScadenzaPagamenti =  currentRow.getNulledField(LOTTO.DATA_SCADENZA_PAGAMENTI); 
		   		String id_motivazione = currentRow.getNulledField(LOTTO.ID_MOTIVAZIONE);
		   		String desc_motivazione = currentRow.getNulledField("L_" + MOTIVI_CANCELLAZIONE.DESCRIZIONE);
					String noteCancellazione = PageHelper.formattaTesto(currentRow.getNulledField(LOTTO.NOTE_CANC));
					boolean perfezionato =  ! "".equalsIgnoreCase(dataPubblicazione); 
				   //is3025_RFWEBGL02Active
					String oraScadenzaPagamenti =  currentRow.getNulledField(LOTTO.ORA_SCADENZA); 
				   
				   //is3030_RFWEBGL00Active
	    	      String dataScadenzaRichiestaInvito = "";
			    	String dataLetteraInvito = "";
					if(SimogFlags.is3030_RFWEBGL00Active()){
					   dataScadenzaRichiestaInvito = currentRow.getNulledField(LOTTO.DATA_SCADENZA_RICHIESTA_INVITO); 
					   dataLetteraInvito = currentRow.getNulledField(LOTTO.DATA_LETTERA_INVITO); 
					}
			   %>
				
				
				<% if ( perfezionato ) { %>
				
					<p>Il lotto risulta gi&agrave; perfezionato con data di pubblicazione <strong><%= PageHelper.getFormattedDate( dataPubblicazione ) %></strong></p>
					<p>Il lotto risulta gi&agrave; perfezionato con data scadenza per la presentazione delle offerte <strong><%= PageHelper.getFormattedDate( dataScadenzaPagamenti ) %></strong></p>
				<% } %>

			<div class="elenco">
			<div class="lotto">

			<table>
				<tbody>
					<tr>
					<th><label for="oggetto_lotto">Oggetto Lotto</label></th>
					<td><%= PageHelper.formattaTesto(currentRow.getNulledField(LOTTO.TABLE_NAME + LOTTO.OGGETTO)) %></td>
					</tr>
					<tr>
<%if(SimogFlags.is30233_NRFWEBGL04Active()) {%>				
				<th><label for="somma_urgenza">Esecuzione di lavori di somma urgenza (ex art. 176 DPR 207/2010)</label></th>
<%} else { %>
				<th><label for="somma_urgenza">Esecuzione di lavori di somma urgenza (ex art. 147 DPR 554/99)</label></th>
<%} %>
					<td><%= currentRow.getNulledField(LOTTO.SOMMA_URGENZA) %></td>
					</tr>
					<tr>
					<th><label for="importo_lotto">Importo del lotto</label></th>
					<td><%= PageHelper.getFormattedImporto( currentRow.getNulledField(LOTTO.IMPORTO_LOTTO) ) %></td>
					</tr>
					<tr>
					
					<tr>
					<th><label for="Data_pubblicazione">Data pubblicazione</th>
					<td nowrap="nowrap">
						<input style="text-align:center" tabindex="1" 
						onblur="Calendar.validaData(this)" 
						type="text" 
						id="dtpubblicazione" 
						name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE %>" 
						value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE %>" 
						defaultValue="<%=PageHelper.getFormattedDate( dataPubblicazione ) %>"/>" >

									<img  src="calendar/img.gif" id="CALdtpubblicazionestart" style="cursor: pointer; border: 1px solid red;" title="Date selector"
	  									onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									<script type="text/javascript">
									    Calendar.setup({
									        inputField     :    "dtpubblicazione",     // id of the input field
									        ifFormat       :    "%d/%m/%Y",      // format of the input field
									        button         :    "CALdtpubblicazionestart",  // trigger for the calendar (button ID)
										    align          :    "Tl",           // alignment (defaults to "Bl")
										    singleClick    :    true
									    });
						</script>
												
					</td>
					</tr>
									
					<tr>					
					<th><label for="Data_scadenza_pagamenti">Data scadenza<br>per la presentazione delle offerte</th>
					<td nowrap="nowrap">
						<input style="text-align:center" tabindex="4" 
						onblur="Calendar.validaData(this)" type="text" id="dtscadenza" name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA %>" value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA %>" defaultValue="<%=PageHelper.getFormattedDate( dataScadenzaPagamenti ) %>"/>">
						
									<img  src="calendar/img.gif" id="CALdtscadenza" style="cursor: pointer; border: 1px solid red;" title="Date selector"
	  									onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									<script type="text/javascript">
									    Calendar.setup({
									        inputField     :    "dtscadenza",     // id of the input field
									        ifFormat       :    "%d/%m/%Y",      // format of the input field
									        button         :    "CALdtscadenza",  // trigger for the calendar (button ID)
										    align          :    "Tl",           // alignment (defaults to "Bl")
										    singleClick    :    true
									    });
						</script>
												
					</td>
					</tr>
					<!-- 3.04.8 34190 fix -->
<%if (SimogFlags.is3025_RFWEBGL02Active()
      && (!String.valueOf(Costanti.MODOREAL_ADESIONE_NOCOMPET).equals(idModoReal) && !String.valueOf(Costanti.MODOREAL_CONCESSIONE_NOCOMPET).equals(idModoReal))) { %>					
					<tr>
					<th><label for=ora_scadenza_pagamenti>Ora scadenza<br>per la presentazione delle offerte (hh:mm)</th>
					<td nowrap="nowrap">
						<input style="text-align:center" tabindex="5" 
						type="text" maxlength="5" id="<%= ParametriServlet.FIELD_NAME_ORA_SCADENZA %>" 
						name="<%= ParametriServlet.FIELD_NAME_ORA_SCADENZA %>" 
						value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_ORA_SCADENZA %>" defaultValue="<%= oraScadenzaPagamenti %>"/>">
					</td>
					</tr>
<%} %>	
			
<% //Ticket ALM #653
//3.04.8 34190 fix
   //Non mostrare la data di scadenza di presentazione della lettera di invito in caso di adesione ad accordo quadro senza successivo confonto competitivo
   if( SimogFlags.is3030_RFWEBGL00Active() 
	&& (!String.valueOf(Costanti.MODOREAL_ADESIONE_NOCOMPET).equals(idModoReal) && !String.valueOf(Costanti.MODOREAL_CONCESSIONE_NOCOMPET).equals(idModoReal))) { 
   //Fine Ticket ALM
%>				
					<tr>
						<th><label for="Data_scadenza_invito">Data di scadenza per la presentazione<br>della richiesta di invito</th>
						<td nowrap="nowrap">
							<input style="text-align:center" tabindex="2" 
							onblur="Calendar.validaData(this)" 
							type="text" id="dtscadenzainvito" 
							name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO %>" 
							value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO %>" 
							defaultValue="<%=PageHelper.getFormattedDate( dataScadenzaRichiestaInvito ) %>"/>"/>
							
								<img  src="calendar/img.gif" id="CALdtscadenzainvito" style="cursor: pointer; border: 1px solid red;" title="Date selector"
		  							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
								<script type="text/javascript">
										    Calendar.setup({
										        inputField     :    "dtscadenzainvito",     // id of the input field
										        ifFormat       :    "%d/%m/%Y",      // format of the input field
										        button         :    "CALdtscadenzainvito",  // trigger for the calendar (button ID)
											    align          :    "Tl",           // alignment (defaults to "Bl")
											    singleClick    :    true
										    });
							   </script>
													
						</td>
					</tr>
						
					<tr>
						<th><label for="Data_lettera_invito">Data della lettera di invito</th>
						<td nowrap="nowrap">
							<input style="text-align:center" tabindex="3" 
							onblur="Calendar.validaData(this)" 
							type="text" id="dtletterainvito" 
							name="<%= ParametriServlet.FIELD_NAME_DATA_LETTERA_INVITO %>" 
							value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_DATA_LETTERA_INVITO %>"
							defaultValue="<%=PageHelper.getFormattedDate( dataLetteraInvito ) %>"/>"/>
							
								<img  src="calendar/img.gif" id="CALdtletterainvito" style="cursor: pointer; border: 1px solid red;" title="Date selector"
		  							onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
								<script type="text/javascript">
										    Calendar.setup({
										        inputField     :    "dtletterainvito",     // id of the input field
										        ifFormat       :    "%d/%m/%Y",      // format of the input field
										        button         :    "CALdtletterainvito",  // trigger for the calendar (button ID)
											    align          :    "Tl",           // alignment (defaults to "Bl")
											    singleClick    :    true
										    });
							   </script>
													
						</td>
					</tr>					
<% } %>		
					
				</tbody>
			</table>

			</div>
			</div>

		<% } // Fine inizializzazione tabella top %>			

	<% } %>
	<input type="hidden" name="idLotto" value="<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>">
	<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_CIG %>" value="<%= currentRow.getNulledField(LOTTO.CIG) %>">
	<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_CIG_KKK %>" value="<%= currentRow.getNulledField(LOTTO.CIG_KKK) %>">
	<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_SOMMA_URGENZA %>" value="<%= currentRow.getNulledField(LOTTO.SOMMA_URGENZA) %>">	
	<input type="hidden" name = "<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>" value="<%=currentRow.getNulledField(GARA.ID_STAZIONE_APPALTANTE)%>"/>
	<input type="hidden" name = "<%= ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE %>" value="<%= currentRow.getNulledField(GARA.CF_AMMINISTRAZIONE) %>"/>
	<input type="hidden" name="<%= ParametriServlet.SESSION_ID_GARA %>" value="<%= currentRow.getNulledField(GARA.ID_GARA)%>">
			<div class="infoBlock">
				<div class="inlineInfo">
					<ul>
					<li>Confermare</li>
					</ul>
					</div>
					<div class="rightLineInfo">
					<ul>
					<% if ( user.isRSSAorRUP() || user.isAmministratore()) { %>
					<li><input type="button" value="Conferma il perfezionamento" onclick="validateAndAction()"></li>
					<% } %>
					</ul>
					</div>
				</div>
			</div>
			
	</div>	
		<%@ include file="include/newfooter.inc" %>
	</div>
	
	
</form>
</body>

</html>
<%} catch (Exception e){e.printStackTrace();} %>