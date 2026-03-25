<% try { %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ taglib prefix="h" uri="http://simog.avlp.it/tags-util"  %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>
<%@page import="it.avlp.simog.db.*"%>
<%@page import="it.avlp.simog.beans.StatiScheda"%>
<%@ page import="it.avlp.simog.garamanager.lotto.DocumentoBean" %>
<%@ page import="java.math.BigDecimal"%>

<% session.removeAttribute( ParametriServlet.FIELD_NAME_ID_LOTTO ); %>
		
<title>SIMOG - Gestione gare - Modifica Lotto</title>
<script type="text/javascript" src="script/funzioni.js"></script>
<script type="text/javascript" src="script/pageutils.js"></script>
<script type="text/javascript" src="script/scriptMotivoCollegamento.js"></script>
<script type="text/javascript" src="script/scriptSoggAggr.js"></script>

<!-- TB: ticket popup modali. Import css e js -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<!-- fine import popup modali -->

<!-- MEV 3.04.10 43227 -->
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
<!-- FINE MEV 3.04.10 43227 -->

</head>

<% TableBean infoLotto = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); 
	TableBean listaGare = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); 
	TableBean listaTipologia = (TableBean)request.getAttribute(ParametriServlet.TIPOLOGIA_BEAN);
	
	TableBean listaDerogaQualificazioneSA = (TableBean)request.getAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_BEAN);// 3.04.9 MEV 40610
	TableBean listaCategorie = (TableBean)request.getAttribute(ParametriServlet.CATEGORIA_BEAN); 
	TableBean listaCategorieScorporabili = (TableBean)request.getAttribute(ParametriServlet.CATEGORIA_SCORPORABILE_BEAN);
	java.util.ArrayList listaDocumenti = (java.util.ArrayList)session.getAttribute("documenti"); 
	String id_lotto = request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO) == null ?  (String)session.getAttribute(ParametriServlet.FIELD_NAME_ID_LOTTO) : request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO); 
	String garaPubblicabile = "falso"; //Per garaVisual.inc  
	TableBeanRow currentRow = null;
	java.util.Vector categorie = new java.util.Vector(); 
	//gm modifica simog 3.04
   String action = request.getParameter(ParametriServlet.ACTION);
	boolean modificaContrattoEscluso = action.equals(ParametriServlet.ACTION_MODIFICA_CONTRATTO_ESCLUSO);
   String disabledTranneEscluso = modificaContrattoEscluso? "disabled" : "";		
	boolean modificaRipetiz = action.equals(ParametriServlet.ACTION_MODIFICA_RIPETIZIONI);
   String disabledTranneRipetiz = modificaRipetiz? "disabled" : "";
	boolean modificaDatiCup = action.equals(ParametriCup.ACTION_MODIFICA_DATI_CUP);
   String disabledTranneDatiCup = modificaDatiCup ? "disabled" : "";   
   /* MEV 37010 3.04.8.1 */
   boolean integraPariOpportunita = action.equals(ParametriServlet.ACTION_INTEGRA_PARI_OPPORTNITA);
  String disabledTrannePariOpportunita = integraPariOpportunita ? "disabled" : ""; 
  String isEreditati = (String)request.getAttribute(ParametriServletLotto.IS_EREDITATI);
  String disabledEreditati = isEreditati.equals("true") ? "disabled" : "";
  
  /* MEV 3.04.10 43227 */
  boolean modificaDatiPerfezionamento = action.equals(ParametriServlet.ACTION_MODIFICA_DATI_PERFEZIONAMENTO);
  String disabledTranneDatiPerfezionamento = modificaDatiPerfezionamento ? "disabled" : ""; 
  boolean mostraDatiFaseUno = "true".equals((String)request.getAttribute(ParametriServlet.MOSTRA_DATI_FASE_UNO));
  boolean mostraDatiFaseDue = "true".equals((String)request.getAttribute(ParametriServlet.MOSTRA_DATI_FASE_DUE));
  
  /* MEV 53643 3.04.13 */
  boolean modificaCPV = action.equals(ParametriServlet.ACTION_MODIFICA_CPV);
 String disabledTranneCPV = modificaCPV ? "disabled" : ""; 
 
 /* MAD 68089 3.04.16 Inizio */
 boolean modificaCategoriaSoa = action.equals(ParametriServlet.ACTION_MODIFICA_CAT_SOA);
 String disabledTranneCatSoa = modificaCategoriaSoa ? "disabled" : ""; 
 /* MAD 68089 3.04.16 Fine */
%>

<%-- Verifica Gara Confermata --%>
<% boolean Confermato = StatiScheda.CONFERMATO_STRING.equals(infoLotto.getRow(0).getNulledField(GARA.ID_STATO)); %>

<% String idGara = ""; //Ticket ALM #2452 
   String idModReal = "";
%>
<% boolean nienteLotti = false;   //Per garaVisual.inc 
   boolean bloccoAVCPASS = false; //Per garaVisual.inc %>

<script type="text/javascript">
	function doActionModifica(action){
		if(!hasErrors(document.forms[0]))
			doAction(action);
	}
</script>

<<body onload="checkDisable()"><%-- TICKET ALM - 3.04.3 #4202 --%>

<div id="gabbia">
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuGara.inc" %>

	<div id="bodypage">
		<div class="bodypage-e">
		
		<h1><utils:message key="lotto.modificaLotto" /></h1>
		<%@ include file="include/gestisciErrore.inc" %>		
		<div class="hmenu">
		
		<%
			String ritorna_href = ParametriServlet.SRV_VISUALIZZA_DETTAGLIO 
			                  + "?" + ParametriServlet.SESSION_ID_GARA + " = " + infoLotto.getRow(0).getNulledField(LOTTO.ID_GARA)
			                  + "&" + ParametriServlet.FROM_GARE + " = " + Costanti.FLAG_VALORE_SI;
			
			String fromIntegrazioneCigCup = (String) session.getAttribute(ParametriCup.FROM_ELENCO_CUP);
			String fromRicerca = (String) request.getParameter(ParametriServlet.FROM_RICERCA);
			if("visCup".equals(fromIntegrazioneCigCup)){
			   ritorna_href = "elencoCigIntegrazioneCup";
	   		if(session.getAttribute(ParametriServlet.STORIA_PAGINAZIONE) != null && !"".equals(session.getAttribute(ParametriServlet.STORIA_PAGINAZIONE))){
	   		   ritorna_href += "?" + session.getAttribute(ParametriServlet.STORIA_PAGINAZIONE);
			   }
	   		else {
	   			if(fromRicerca != null) ritorna_href += "?nav=yes&"+ParametriServlet.FROM_RICERCA+"="+fromRicerca;
	   		}
			}
			
			String dataCreazioneGaraP = "";
		%>
		
			<ul><li><a title="Pagina precedente" href="<%= ritorna_href %>">Ritorna</a></li></ul>
		</div>
		
		<form name="eseguiModifica" action="aggiornaLotto" method="post">
		<!-- MAC 35585 3.04.9-->
		<% for ( int rowIndex = 0; rowIndex < infoLotto.getTableSize(); rowIndex++ ) { %>
			<% currentRow = infoLotto.getRow(rowIndex);
			categorie.add(currentRow.getNulledField(ParametriServlet.CATEGORIA_SCORPORABILE) );
			} %>
		<!-- FINE MAC -->
		<% for ( int rowIndex = 0; rowIndex < infoLotto.getTableSize(); rowIndex++ ) { %>
			<% currentRow = infoLotto.getRow(rowIndex); %>
			
			<% if ( rowIndex == 0 ) { %>

				<%
				String codiceGara = currentRow.getNulledField(GARA.ID_GARA);

				String oggettoGara = PageHelper.formattaTesto(currentRow.getNulledField(GARA.TABLE_NAME + GARA.OGGETTO));
				String dataCreazioneGara = PageHelper.getFormattedDate( currentRow.getNulledField(GARA.DATA_CREAZIONE) ) ;	
				dataCreazioneGaraP = dataCreazioneGara;
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
				String amministrazioneDescrizione = currentRow.getNulledField( GARA.DENOM_STAZIONE_APPALTANTE );

				String garaDataCancellazione = currentRow.getNulledField(GARA.DATA_CANCELLAZIONE_GARA);
				String garaDataTerminePagam = currentRow.getNulledField(GARA.DATA_TERMINE_PAGAMENTO);
				String garaDataInibPagam = currentRow.getNulledField(GARA.DATA_INIB_PAGAM);
				String garaDataConferma = currentRow.getNulledField(GARA.DATA_CONFERMA_GARA);
				String garaDataComun = currentRow.getNulledField(GARA.DATA_COMUN);
				String garaDataPerfezionamento = currentRow.getNulledField(GARA.DATA_PERFEZIONAMENTO_BANDO);

				String tipoScheda = currentRow.getNulledField(TIPI_CATEGORIA.TABLE_NAME);
				String modoGara = currentRow.getNulledField(MODO_INDIZIONE.TABLE_NAME);
				String modoReal = currentRow.getNulledField(MODI_REALIZZAZIONE.TABLE_NAME);
				String cigQuadro =  currentRow.getNulledField( GARA.CIG_ACC_QUADRO );

				//TICKET ALM #664
				String strumentoSvolgimento = currentRow.getNulledField(STRUMENTI_SVOLGIMENTO_PROCEDURE.TABLE_NAME);
				//FINE TICKET ALM #664
				
				//TICKET #659
				String durataGiorni = currentRow.getNulledField(GARA.DURATA_GIORNI);
				//FINE TICKET 659
						
				//TICKET ALM #3832
		        String estremaUrgenza = currentRow.getNulledField(ART_ESTREMA_URGENZA_SOMMA_URGENZA.TABLE_NAME);
		        //FINE TICKET ALM #3832
				
		        //TICKET ALM #3834
				String modIndAllegatoIX = currentRow.getNulledField(MODALITA_INDIZIONE_ALLEGATO_IX.TABLE_NAME);
				//FINE TICKET ALM #3834
				
				String descMotivGara = currentRow.getNulledField("G_" + MOTIVI_CANCELLAZIONE.DESCRIZIONE);
				String noteCancGara = PageHelper.formattaTesto(currentRow.getNulledField(GARA.NOTE_CANC_GARA));
				String id_motivazioneGara = currentRow.getNulledField(GARA.ID_MOTIVAZIONE_CANC);
				
            //gm nuovo campo simog 3.04
				String numeroLotti = currentRow.getNulledField(GARA.NUMERO_LOTTI);
            
				String RSSA_CodiceFiscale = currentRow.getNulledField(GARA.CF_UTENTE);

	        	// is3031_ESCL_AVCPASS
	        	String esclusioneAVCPass = SimogFlags.is3031_ESCL_AVCPASS() ? currentRow.getNulledField(GARA.ESCLUSO_AVCPASS) : "";
	        	
	   		//INT85
	   		String sceltaLegge89 = SimogFlags.isINT85_RFWEBGL01Active() ? currentRow.getNulledField(GARA.SCELTA_LEGGE89) : ""; 
	   		
	   		//INT87
	   		String urgenzaDL133 = SimogFlags.isINT87_RFSIMOGWEB01Active() ? currentRow.getNulledField(GARA.URGENZA_DL133) : "";  	
	   		
	   		//is30350_RFWEBGL01Active
	   		String motivoEagg = SimogFlags.is30350_RFWEBGL01Active() ? currentRow.getNulledField(EAGG_MOTIVI.TABLE_NAME) : "";
	   		String[] categEagg = new String[0];	 

			//Ticket ALM #2452
	   		idGara = currentRow.getNulledField(GARA.ID_GARA);
	   		idModReal = currentRow.getNulledField(GARA.ID_MODO_REAL);
			
	   	   //TICKET ALM #659 - 3.04.4
	   		String flagSAAgente = currentRow.getNulledField(FUNZIONI_DELEGATE_GARA.FLAG_SA_AGENTE);
	   		String idFDelegate = currentRow.getNulledField(FUNZIONI_DELEGATE.TABLE_NAME);
	   		String cfAmmDelegante = currentRow.getNulledField(FUNZIONI_DELEGATE_GARA.CF_AMM_AGENTE);
	   		String denAmmDelegante =currentRow.getNulledField(FUNZIONI_DELEGATE_GARA.DEN_AMM_AGENTE);
			
	   		//TICKET ALM #4222 - 3.04.4
	   		String flagNoDPCM = (request.getAttribute(ParametriServlet.FIELD_NAME_FLAG_SA_NO_DPCM) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_FLAG_SA_NO_DPCM): "";
			String flagNoDPCMPar = (request.getParameter(ParametriServlet.FIELD_NAME_FLAG_SA_NO_DPCM) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_FLAG_SA_NO_DPCM): "";
			if(!"".equals(flagNoDPCMPar)) flagNoDPCM = flagNoDPCMPar;	
			
			String flagSANoClassificata = (request.getAttribute(ParametriServlet.FIELD_NAME_FLAG_SA_NO_CLASSIFICATA) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_FLAG_SA_NO_CLASSIFICATA): "";
			String flagSANoClassificataPar = (request.getParameter(ParametriServlet.FIELD_NAME_FLAG_SA_NO_CLASSIFICATA) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_FLAG_SA_NO_CLASSIFICATA): "";
			if(!"".equals(flagSANoClassificataPar)) flagSANoClassificata = flagSANoClassificataPar;	
			
			String cigIniziativa = (request.getAttribute(ParametriServlet.FIELD_NAME_CIG_INIZIATIVA_SEL) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_CIG_INIZIATIVA_SEL): "";
			String cigIniziativaPar = (request.getParameter(ParametriServlet.FIELD_NAME_CIG_INIZIATIVA_SEL) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_CIG_INIZIATIVA_SEL): "";
			if(!"".equals(cigIniziativa)) cigIniziativa = cigIniziativaPar;	
	   		
	      	//TICKET MAC #10467
	   		String rupCreatoGara = (String)request.getAttribute(ParametriServlet.RUP_CREATO_GARA);
	   		String codiceAusa = currentRow.getNulledField(GARA.CODICE_AUSA);
	   		// fix 34470 3.04.8
	   		String linkAffidamentoDiretto = (String) request.getAttribute(ParametriServlet.FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO);
	
				%>

	<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_LOTTO %>" value="<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>">
	<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>" value="<%=currentRow.getNulledField(GARA.ID_STAZIONE_APPALTANTE)%>"/>
	<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE %>" value="<%= currentRow.getNulledField(GARA.CF_AMMINISTRAZIONE) %>"/>	
	<input type="hidden" name="<%= ParametriServlet.ACTION %>" value="<%= request.getAttribute( ParametriServlet.ACTION ) %>"/>	
	<input type="hidden" name="<%= ParametriServlet.SESSION_ID_GARA %>" value="<%= currentRow.getNulledField(GARA.ID_GARA)%>">
	<input type="hidden" name="<%= ParametriServlet.ACTION %>" value="<%= action %>">

				<h4>Gara di cui fa parte il lotto</h4>
     
     <%
       //Ticket #20055	
       boolean	enableButtonModifica =  Boolean.valueOf(String.valueOf(request.getAttribute(ParametriServlet.RETTIFICA_GARA_LOTTI)));
	 %>
	         <%@ include file="include/garaVisual.inc" %>
            
				<%
            					String idLotto = currentRow.getNulledField( LOTTO.ID_LOTTO );
            								String oggettoLotto = PageHelper.formattaTesto(currentRow.getNulledField(LOTTO.TABLE_NAME + LOTTO.OGGETTO));
            								String sommaUrgenza = currentRow.getNulledField(LOTTO.SOMMA_URGENZA);
            								String checkedSommaUrgenza = "S".equalsIgnoreCase(sommaUrgenza) ? "checked" : "";
            								String importoLottoEuro = request.getParameter(ParametriServlet.FIELD_NAME_IMPORTO_LOTTO_EURO) != null ? request.getParameter(ParametriServlet.FIELD_NAME_IMPORTO_LOTTO_EURO) : currentRow.getNulledField(LOTTO.IMPORTO_LOTTO);
            								String tipologiaSelezionata = currentRow.getNulledField(LOTTO.ID_TIPOLOGIA);
            								String CPVSelezionata = request.getParameter(ParametriServlet.FIELD_NAME_CPV) != null ? request.getParameter(ParametriServlet.FIELD_NAME_CPV) : currentRow.getNulledField(LOTTO.ID_CPV);
            								String contraenteSelezionata = request.getParameter(ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE) != null ? request.getParameter(ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE) :currentRow.getNulledField(LOTTO.ID_SCELTA_CONTRAENTE);

            								//2846
            								String motivoCollegamentoCig = request.getParameter(ParametriServlet.FIELD_NAME_MOTIVO) != null ? request.getParameter(ParametriServlet.FIELD_NAME_MOTIVO) :currentRow.getNulledField(LOTTO.ID_MOTIVO);															
            								//2846
            								
            								//TICKET ALM #4222 - 3.04.4
											String catSelezionata = request.getParameter(ParametriServlet.FIELD_NAME_CATEGORIA_LOTTO) != null ? request.getParameter(ParametriServlet.FIELD_NAME_CATEGORIA_LOTTO) : currentRow.getNulledField(LOTTO.COD_CATEGORIA);											
											//FINE TICKET ALM #4222 - 3.04.4
											
											//3.04.9 MEV 40610
											String derogaQualificazioneSASelezionata = (String)request.getAttribute(ParametriServlet.FIELD_NAME_DEROGA_QUALIICAZIONE_SA_LOTTO) != null ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_DEROGA_QUALIICAZIONE_SA_LOTTO) : (String)request.getParameter(ParametriServlet.FIELD_NAME_DEROGA_QUALIICAZIONE_SA_LOTTO);										
											//FINE 3.04.9 MEV 40610
            								
            								String prevalenteSelezionata = currentRow.getNulledField(LOTTO.ID_CATEGORIA_PREVALENTE);

            								String selTipoPar = request.getParameter(ParametriServlet.FIELD_NAME_TIPO_CONTRATTO) != null ? request.getParameter(ParametriServlet.FIELD_NAME_TIPO_CONTRATTO) : currentRow.getNulledField(LOTTO.TIPO_CONTRATTO_LOTTO);
            								String selSiNoPar = request.getParameter(ParametriServlet.FIELD_NAME_ESCLUSO) != null ? request.getParameter(ParametriServlet.FIELD_NAME_ESCLUSO) : currentRow.getNulledField(LOTTO.FLAG_ESCLUSO);
            								String idEsclusionePar = request.getParameter(ParametriServlet.FIELD_NAME_ID_ESCLUSIONE) != null ? request.getParameter(ParametriServlet.FIELD_NAME_ID_ESCLUSIONE) : currentRow.getNulledField(LOTTO.ID_ESCLUSIONE);
            								
            								//MEV 38205 3.04.8.1
            								String selSiNoMet = request.getParameter(ParametriServlet.FIELD_NAME_FLAG_USO_METODI_EDILIZIA) != null ? request.getParameter(ParametriServlet.FIELD_NAME_FLAG_USO_METODI_EDILIZIA) : currentRow.getNulledField(LOTTO.FLAG_USO_METODI_EDILIZIA);
            								
            								
            								String dataComun = (String) request.getAttribute(LOTTO.DATA_COMUNICAZIONE);

            								String triennioAnnoInizio = currentRow.getNulledField(LOTTO.TRIENNIO_ANNO_INIZIO);
            								String triennioAnnoFine = currentRow.getNulledField(LOTTO.TRIENNIO_ANNO_FINE);
            								String triennioAnnoProgressivo = currentRow.getNulledField(LOTTO.TRIENNIO_PROGRESSIVO);
            								String annualeCuiMinInf = currentRow.getNulledField(LOTTO.ANNUALE_CUI_MININF);
            								
            								if("0".equals(triennioAnnoInizio)) triennioAnnoInizio = "";
            								if("0".equals(triennioAnnoFine)) triennioAnnoFine = "";
            								if("0".equals(triennioAnnoProgressivo)) triennioAnnoProgressivo = "";
            								
            								//gm nuovo codice pubblicazione bando 3.0
            								String luogoIstat = currentRow.getNulledField(LOTTO.LUOGO_ISTAT);
            								String luogoNuts = currentRow.getNulledField(LOTTO.LUOGO_NUTS);
            								//String importo_attuazione_sicurezza = currentRow.getNulledField(LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA);
            								String importo_attuazione_sicurezza = request.getParameter(ParametriServlet.FIELD_NAME_IMPORTO_SICUREZZA) != null ? request.getParameter(ParametriServlet.FIELD_NAME_IMPORTO_SICUREZZA) : currentRow.getNulledField(LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA);
            								//gm fine nuovo codice pubblicazione bando 3.0
            								
            								// PP B302.3.3
            								String flagPrevRip = "";
            								String flagRipetiz = "";
            								String cigRipetiz = "";
            								// Ticket #20058 - 09 - 02 - 21
            								String durataRinnoviRipetizioni = "";

            								if(SimogFlags.is30233_RFWEBGL02Active()){
            									flagPrevRip = request.getParameter(ParametriServlet.FIELD_FLAG_PREVEDE_RIP) != null ? request.getParameter(ParametriServlet.FIELD_FLAG_PREVEDE_RIP) : currentRow.getNulledField(LOTTO.FLAG_PREVEDE_RIP);
            									// Ticket #20058 - 09 - 02 - 21
            									durataRinnoviRipetizioni = request.getParameter(ParametriServlet.FIELD_NAME_DURATA_RINNOVI_RIPETIZIONI) != null ? request.getParameter(ParametriServlet.FIELD_NAME_DURATA_RINNOVI_RIPETIZIONI) : currentRow.getNulledField(LOTTO.DURATA_RINNOVI_RIPETIZIONI);
            									flagRipetiz = request.getParameter(ParametriServlet.FIELD_FLAG_RIPETIZIONE) != null ? request.getParameter(ParametriServlet.FIELD_FLAG_RIPETIZIONE) : currentRow.getNulledField(LOTTO.FLAG_RIPETIZIONE);
            									cigRipetiz = currentRow.getNulledField(LOTTO.CIG_ORIGINE_RIP);
            								}
            								
            								//Ticket #20057
            								String durataAffidamentoInGiorni = "";
            								if(SimogFlags.is30233_RFWEBGL02Active()){
            									
            									durataAffidamentoInGiorni = request.getParameter(ParametriServlet.FIELD_NAME_DURATA_AFFIDAMENTO_IN_GIORNI) != null ? request.getParameter(ParametriServlet.FIELD_NAME_DURATA_AFFIDAMENTO_IN_GIORNI) : currentRow.getNulledField(LOTTO.DURATA_AFFIDAMENTO_IN_GIORNI);
            									
            								}
            								
            						
            								
            								
            								 //TICKET ALM #2845
            								String flagDL50 = request.getParameter(ParametriServlet.FIELD_FLAG_DL50) != null ? request.getParameter(ParametriServlet.FIELD_FLAG_DL50) : currentRow.getNulledField(LOTTO.FLAG_DL50);
            								String primaAnnualita = request.getParameter(ParametriServlet.FIELD_NAME_PRIMA_ANNUALITA) != null ? request.getParameter(ParametriServlet.FIELD_NAME_PRIMA_ANNUALITA) : currentRow.getNulledField(LOTTO.PRIMA_ANNUALITA);
            								 //FINE TICKET ALM #2845
            								
            								
            								 //TICKET ALM #3835
            								 String idAffRiservati = request.getParameter(ParametriServlet.FIELD_NAME_AFF_RISERVATI) != null ? request.getParameter(ParametriServlet.FIELD_NAME_AFF_RISERVATI) : currentRow.getNulledField(LOTTO.ID_AFF_RISERVATI);
            								 //FINE TICKET ALM #3835
            								 
            								  //TICKET ALM #3836
            								  String flagRegime = request.getParameter(ParametriServlet.FIELD_FLAG_REGIME) != null ? request.getParameter(ParametriServlet.FIELD_FLAG_REGIME) : currentRow.getNulledField(LOTTO.FLAG_REGIME);
            								  String idArtRegime = request.getParameter(ParametriServlet.FIELD_NAME_ART_REGIME) != null ? request.getParameter(ParametriServlet.FIELD_NAME_ART_REGIME) : currentRow.getNulledField(LOTTO.ID_ESCLUSIONE);
            								  //TICKET ALM #3836
            								
            								// UN 3.03.1
            								String flagCup = "";
            								if( SimogFlags.is3031_RFWEBGL02Active() ){
            								   flagCup = request.getParameter(ParametriCup.FIELD_FLAG_CUP) != null ? request.getParameter(ParametriCup.FIELD_FLAG_CUP) : currentRow.getNulledField(LOTTO.FLAG_CUP);
            								}
            								
            								//TICKET #31047 : TICKET PARITA DI GENERE	     

            								String flagPnrrPnc = "";
            								//MEV 34696 3.04.8
            								//if(SimogProperties.getInstance().isDataCreatedAfter3047(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
            									flagPnrrPnc = request.getParameter(ParametriServlet.FLAG_PNRR_PNC) != null ? (String)request.getParameter(ParametriServlet.FLAG_PNRR_PNC) : currentRow.getNulledField(LOTTO.FLAG_PNRR_PNC);
            								//}
            								
           									//MEV 37010 3.04.8.1
               								String flagDerogaAdesione = "";
               								flagDerogaAdesione = currentRow.getNulledField(LOTTO.FLAG_DEROGA_ADESIONE);
               								//FINE MEV 37010 3.04.8.1
               								
               								//fix 40610
											String flagIsKo = "";
											flagIsKo = request.getParameter(ParametriServlet.FLAG_IS_KO) != null ? (String)request.getParameter(ParametriServlet.FLAG_IS_KO) : "";
											//
                								
            								String flagPrevisioneQuota = "";
            								//if(SimogProperties.getInstance().isDataCreatedAfter3047(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
            									flagPrevisioneQuota = request.getParameter(ParametriServlet.FLAG_PREVISIONE_QUOTA) != null ? (String)request.getParameter(ParametriServlet.FLAG_PREVISIONE_QUOTA) : currentRow.getNulledField(LOTTO.FLAG_PREVISIONE_QUOTA);
            								//}	
            								
            								String flagMisurePremiali = "";
            								//if(SimogProperties.getInstance().isDataCreatedAfter3047(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
            									flagMisurePremiali = request.getParameter(ParametriServlet.FLAG_MISURE_PREMIALI) != null ? (String)request.getParameter(ParametriServlet.FLAG_MISURE_PREMIALI) : currentRow.getNulledField(LOTTO.FLAG_MISURE_PREMIALI);
            								//}
            								
            								String quotaGiovanile = "";
            								//if(SimogProperties.getInstance().isDataCreatedAfter3047(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
            									quotaGiovanile = request.getParameter(ParametriServlet.QUOTA_GIOVANILE) != null ? (String)request.getParameter(ParametriServlet.QUOTA_GIOVANILE) : currentRow.getNulledField(LOTTO.QUOTA_GIOVANILE);
            								//}	
            								
            								String quotaFemminile = "";
            								//if(SimogProperties.getInstance().isDataCreatedAfter3047(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
            									quotaFemminile = request.getParameter(ParametriServlet.QUOTA_FEMMINILE) != null ? (String)request.getParameter(ParametriServlet.QUOTA_FEMMINILE) : currentRow.getNulledField(LOTTO.QUOTA_FEMMINILE);
            								//}
            								
            								
            								
            								
            								
            								
            								//TICKET ALM 13453 - 3.04.5
            								String descCpvPrev = request.getParameter("cpvPrevDesc") != null ? request.getParameter("cpvPrevDesc") : currentRow.getNulledField(CPVEU.TABLE_NAME);
            								
            								//TICKET ALM 13691 - 3.04.5
            								String importo_opzioni = request.getParameter(ParametriServlet.FIELD_NAME_IMPORTO_OPZIONI) != null ? request.getParameter(ParametriServlet.FIELD_NAME_IMPORTO_OPZIONI) : currentRow.getNulledField(LOTTO.IMPORTO_OPZIONI);

            								
            			                       String urgenzaDL133_session="";
            			   					Integer selectedMotivo=(Integer)request.getAttribute(ParametriServlet.FIELD_NAME_MOTIVO_URGENZA);
            			   					if (selectedMotivo!= null && selectedMotivo==Costanti.TIPO_ESTREMA_URGENZA_PROTEZIONE_CIVILE){
            			   						urgenzaDL133_session="S"; 			
            			   					}
            			   					
            			   				//MEV 37010 3.04.8.1
            								String mostraCampoDerogaAdesione = (String)request.getAttribute(ParametriServlet.MOSTRA_DEROGA_ADESIONE);
            								
            								//3.04.9 MEV 40610
            								String mostraCampoDerogaQualificazioneSA = (String)request.getAttribute(ParametriServlet.MOSTRA_DEROGA_QUALIFICAZIONE_SA);
            							
            								
            				%>

				<!-- MEV 3.04.10 43227 -->
				<% if(modificaDatiPerfezionamento) { %>
				<%String dataScadenzaPagamentiGara = PageHelper.getFormattedDate( currentRow.getNulledField(LOTTO.DATA_SCADENZA_PAGAMENTI) ) ;
				String dataScadenzaRichiestaInvitoGara = PageHelper.getFormattedDate( currentRow.getNulledField(LOTTO.DATA_SCADENZA_RICHIESTA_INVITO) ) ;
				String oraScadenzaPagamentiGara = currentRow.getNulledField(LOTTO.ORA_SCADENZA) ;%>
				<h4>Modifica dati perfezionamento</h4>
				<table cellpadding="3">
					<tbody>
					<% if(mostraDatiFaseUno) { %>
					<tr><th><label>Data di scadenza per la presentazione della richiesta di invito*</label></th>
						<td><input style="text-align:center" onchange="setFormModified('Modificato1')" onblur="Calendar.validaData(this)" type="text" id="dtscadinv" name="<%= ParametriServlet.DATA_SCADENZA_RICHIESTA_INVITO %>" value="<%=dataScadenzaRichiestaInvitoGara%>" />
							
								<img src="calendar/img.gif" id="CALdtscadinv" style="" title="Date selector" />
								<script type="text/javascript">
								    Calendar.setup({inputField:"dtscadinv",ifFormat:"%d/%m/%Y",button:"CALdtscadinv",align:"Tl",singleClick:true});
								</script>
							
						</td>
					</tr>
					<% } %>
				<% if(mostraDatiFaseDue) { %>
				<tr><th><label>Data di scadenza per la presentazione delle offerte*</label></th>
						<td><input style="text-align:center" onchange="setFormModified('Modificato0')" onblur="Calendar.validaData(this)" type="text" id="dtscadoff" name="<%= ParametriServlet.DATA_SCADENZA_PAGAMENTI %>" value="<%=dataScadenzaPagamentiGara%>" />
							
								<img src="calendar/img.gif" id="CALdtscadoff" style="" title="Date selector" />
								<script type="text/javascript">
								    Calendar.setup({inputField:"dtscadoff",ifFormat:"%d/%m/%Y",button:"CALdtscadoff",align:"Tl",singleClick:true});
								</script>
							
						</td>
					</tr>
					<tr><th><label>Ora scadenza per la presentazione delle offerte (hh:mm)*</label></th>
						<td><input style="text-align:center" type="text" name="<%= ParametriServlet.ORA_SCADENZA %>" value="<%=oraScadenzaPagamentiGara%>" />
						</td>
					</tr>
					
				
				<% } %>
				
				
				
				</tbody>
				</table>
				<% } %>
				
				<% if(!modificaDatiPerfezionamento) { %>
				<!--FINE MEV 3.04.10 43227 -->
				
				<h4>Lotto da modificare</h4>			
				<% String cigLotto = PageHelper.getCIG ( currentRow.getNulledField(LOTTO.CIG)  + currentRow.getNulledField(LOTTO.CIG_KKK),  sommaUrgenza, currentRow.getNulledField(LOTTO.DATA_CREAZIONE_LOTTO) ); %>
				<h5>Lotto - CIG [<%=  cigLotto %>]</h5>
	
	<input type="hidden" id="cigLottoEdit" value="<%= cigLotto %>">
	<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_CIG %>" value="<%= cigLotto %>">
	<input type="hidden" id="urgenzaDL133_session"  value="<%= urgenzaDL133_session%>">		
			
	
		<%@ include file="include/lottoEdit.inc" %>
		<%-- MAC 35585 3.04.9 
		<% categorie.add(currentRow.getNulledField(ParametriServlet.CATEGORIA_SCORPORABILE) ); %> --%>
				
	<input type="hidden" name="<%= ParametriServlet.SESSION_ID_GARA+"_form" %>" id="idGara" value="<%= idGara %>">
	<% if(!modificaDatiCup){ %>


	<% String categoriaPrevalente = currentRow.getNulledField(LOTTO.ID_CATEGORIA_PREVALENTE); %>
	<% String categoriaPrevalenteDescrizione = listaCategorie.getFieldBySearchField(CATEGORIA.ID_CATEGORIA, categoriaPrevalente, CATEGORIA.DESCRIZIONE); %>
	
		<h5>Categoria prevalente o scorporabile</h5>
			<%@ include file="include/elencoCategorie.inc" %>
	
	<% } %>
	<!-- MEV 37010 3.04.8.1 -->
				<%
				if (mostraCampoDerogaAdesione.equals("true")) {
			%>
				<table cellpadding="3">
				<tbody>
				<tr id="tr_flagDerogaAdesione">
					<th align="left" width="50%"><label
						<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_FlagDerogaAdesione")%>
						for="<%=ParametriServlet.FLAG_DEROGA_ADESIONE%>">Deroga per adesione ad AQ/Convenzione precedente alle linee guida DPO sull'articolo 47 del DL 77/2021 e ss.m.i.?</label></th>
					<td><c:set var="selFlagDerogaAdesione" value="<%=flagDerogaAdesione%>" scope="request"/> 
							<select onchange="disableParita(this,'derogaAdesione')" id= "flagDEROGA_ADESIONE"
							name="<%=ParametriServlet.FLAG_DEROGA_ADESIONE%>" CLASS="BOTTONE" > 
								<option value=""></option>
								<option value="N" <c:out value="${selFlagDerogaAdesione =='N' ? 'selected' : ''}" /> >NO</option> 
								<option value="S" <c:out value="${selFlagDerogaAdesione =='S' ? 'selected' : ''}" /> >SI</option> 
						</select>
					</td>
				</tr>
				</tbody>
				</table>
				<%
			}
			%>
				<!-- MEV 37010 3.04.8.1 -->
	<%@ include file="include/lottoPariOpportunita.inc" %>
	<!-- 3.04.9 MEV 40610 -->
				<%
			if (mostraCampoDerogaQualificazioneSA.equals("true")) {
		%>
		<tr id="tr_derogaQualificazioneStazioneAppaltante_1_mod2">
			<td colspan="2">
				<h5>Autodichiarazione Deroga qualificazione Stazione Appaltante</h5>
				<div>La dichiarazione ha valore di autocertificazione ai fini delle successive verifiche, con correlativa applicazione delle sanzioni previste in caso di dichiarazioni mendaci</div>
			</td>
		</tr>
			<div colspan="2">
		<div class="inthead elencoQualificazioneSA">
		<table>
			<tr>
				<th></th>
				<th></th>
			</tr>
			
			
			<% TableBeanRow currentDQSARow = null; 
			String prevFromReqDQSA = (String) request.getAttribute(PSBD.FIELD_NAME_DEROGA_QUALIFICAZIONE_SA);%>
			<% for ( int i = 0; i < listaDerogaQualificazioneSA.getTableSize(); i++ ) { %>
			<% currentDQSARow = listaDerogaQualificazioneSA.getRow(i); %>
			<% String currentIdDerogaQualificazioneSA = currentDQSARow.getNulledField(DEROGA_QUALIFICAZIONE_SA.ID_DEROGA_QUALIFICAZIONE); %>
				<tr class="derogaRow<%= currentIdDerogaQualificazioneSA %>">
				
				
				<td><label for="derogaQualificazioneSA[<%= i %>]"><%= currentDQSARow.getNulledField(DEROGA_QUALIFICAZIONE_SA.DESCRIZIONE) %></label></td>
				
				<td><input disabled="disabled" type="radio" name="<%= PSBD.FIELD_NAME_DEROGA_QUALIFICAZIONE_SA %>" value="<%= currentIdDerogaQualificazioneSA %>" <%= currentIdDerogaQualificazioneSA.equals(prevFromReqDQSA) ? "checked" : "" %> id="SelDerogaQualificazioneSA<%= currentIdDerogaQualificazioneSA %>"></td>
				</tr>
			<% } %>
			
		</table>
	</div>
	</div>
	<%
			}
			%>
			<!-- fine 3.04.9 MEV 40610 -->
	<% } // Fine inizializzazione tabella top %>

	<% } %>
	
    <!-- TICKET ALM #724 #2452 -->
	<!-- Fine Ticket ALM #724 #2452 -->
	
<!-- STAVANO QUA GLI HIDDEN -->
<!-- MEV 3.04.10 43227 -->
			<% } %>
			<!-- MEV 3.04.10 43227 -->
			
			<div class="infoBlock">
				<div class="inlineInfo">
					<ul>
					<li>Confermare</li>
					</ul>
				</div>
				<div class="rightLineInfo">
					<ul>
					<li><input type="button" value="Conferma la Modifica" onclick="doCallSoggAggrLotto('Salva',<%= idGara %>,<%= user.isAmministratore() %>,<%= SimogProperties.getInstance().isDataCreatedAfter3044(PageHelper.getFormattedDBDate(dataCreazioneGaraP))%>)"></li>
					</ul>
					<input type="hidden" id="toDo"><!-- Necessario a causa di doActionModifica(toDo) -->
				</div>
		   </div>
			
			</form>
		</div>	
	</div>

		<%@ include file="include/newfooter.inc" %>
</div>

</body>
</html>
<% } catch (Exception e) {e.printStackTrace();} %>