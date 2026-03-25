<% try{ %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.db.*" %>
<%@ page import="java.math.BigDecimal"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletLotto"%>
<%@page import="it.avlp.simog.db.Costanti"%>
<%@ page import="it.avlp.simog.common.servlet.PSReq"%>
<%@ taglib prefix="x" uri="http://simog.avlp.it/tags-util"  %>
<!-- TB: ticket popup modali. Import css e js -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<!-- fine import popup modali -->
<title>SIMOG - <utils:message key="visualizza.gestioneGare" /></title>
</head>

<% TableBean listaGare = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); %>
<% TableBean listaCategorieScorporabili = (TableBean)request.getAttribute(ParametriServlet.CATEGORIA_BEAN); %>

<% boolean cancellabile = false; 
 boolean cancellato = false; 
//gm in inserimento lotto tutti i campi sono editabili, quindi nessuno � disabled
boolean modificaContrattoEscluso = false;
String disabledTranneEscluso = "";
boolean modificaRipetiz = false;
String disabledTranneRipetiz = "";
//MEV 37010 3.04.8.1
boolean integraPariOpportunita = false;
String disabledTrannePariOpportunita = "";
/* MEV 53643 3.04.13 */
String disabledTranneCPV = "";
/* MAD 68089 3.04.16 Inizio */
String disabledTranneCatSoa = "";
/* MAD 68089 3.04.16 Fine */
%>
<%-- Verifica Gara Confermata --%>
<% boolean Confermato = StatiScheda.CONFERMATO_STRING.equals(listaGare.getRow(0).getNulledField(GARA.ID_STATO)); %>

<body>
<div id="dialog"></div>
<div id="gabbia">
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuGara.inc" %>
<%

//Ticket #20055	
boolean	enableButtonModifica =  Boolean.valueOf(String.valueOf(request.getAttribute(ParametriServlet.RETTIFICA_GARA_LOTTI)));
%>

	<div id="bodypage">
		<div class="bodypage-e">
				<h1><utils:message key="lotto.cancellazioneLotto" /></h1>
				<%@ include file="include/gestisciErrore.inc" %>
			<div class="hmenu">
			<ul>
				<li><a title="<utils:message key="dettaglio.paginaPrecedente" />" href="<%=ParametriServlet.SRV_VISUALIZZA_DETTAGLIO%>?<%=ParametriServlet.SESSION_ID_GARA%>=<%=listaGare.getRow(0).getNulledField(LOTTO.ID_GARA) %>"><utils:message key="lotto.ritorna" /></a></li>
			</ul>
			</div>
<form name="eseguiCancella" action="cancellaLotto" method="post">

		<div class="testo">
	<% String currentLotto = null;
		String idLotto = null;
     	boolean nienteLotti = false; //Per garaVisual.inc
     	String garaPubblicabile = "falso"; //Per garaVisual.inc 
      boolean bloccoAVCPASS = false; //Per garaVisual.inc 
		TableBeanRow previousRow = null; 
     	int rowIndex = 0;
		TableBeanRow currentRow = listaGare.getRow(rowIndex);
		idLotto = currentRow.getNulledField(LOTTO.ID_LOTTO); 
		boolean nuovoLotto = ! idLotto.equalsIgnoreCase( currentLotto ); 
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

		String tipoScheda = currentRow.getNulledField(TIPI_CATEGORIA.TABLE_NAME);
		String modoGara = currentRow.getNulledField(MODO_INDIZIONE.TABLE_NAME);
		String modoReal = currentRow.getNulledField(MODI_REALIZZAZIONE.TABLE_NAME);
		String cigQuadro =  currentRow.getNulledField( GARA.CIG_ACC_QUADRO );

		//gm nuovo campo simog 3.04
		String numeroLotti =  currentRow.getNulledField( GARA.NUMERO_LOTTI );
		
		//TICKET ALM - 3.04.3
        String durataGiorni = currentRow.getNulledField(GARA.DURATA_GIORNI);
        //FINE TICKET ALM - 3.04.3
		
		String descMotivGara = currentRow.getNulledField("G_" + MOTIVI_CANCELLAZIONE.DESCRIZIONE);
		String noteCancGara = PageHelper.formattaTesto(currentRow.getNulledField(GARA.NOTE_CANC_GARA));
		String id_motivazioneGara = currentRow.getNulledField(GARA.ID_MOTIVAZIONE_CANC);

		String RSSA_CodiceFiscale = currentRow.getNulledField(GARA.CF_UTENTE);
		
		cancellabile = "".equalsIgnoreCase( currentRow.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO) ) && "".equalsIgnoreCase(currentRow.getNulledField(LOTTO.DATA_INIB_PAGAMENTO));
		cancellato = ! cancellabile;
		
		// PP modifica richiesta da Carrabs 3.08.2011
		String idModoRealStr = currentRow.getNulledField(GARA.ID_MODO_REAL);
		if(idModoRealStr == null || "".equals(idModoRealStr))
			idModoRealStr = "0";
		int idModo = Integer.parseInt(idModoRealStr);	
		/* 3.04.8 34190 fix */
    	boolean	isAdesione = Costanti.MODOREAL_ADESIONE == idModo || Costanti.MODOREAL_ADESIONE_NOCOMPET == idModo || Costanti.MODOREAL_CONCESSIONE == idModo || Costanti.MODOREAL_CONCESSIONE_NOCOMPET == idModo;

    	// is3031_ESCL_AVCPASS
     	String esclusioneAVCPass = SimogFlags.is3031_ESCL_AVCPASS() ? currentRow.getNulledField(GARA.ESCLUSO_AVCPASS) : "";
     	
		//TICKET ALM #664
		String strumentoSvolgimento = currentRow.getNulledField(STRUMENTI_SVOLGIMENTO_PROCEDURE.TABLE_NAME);
		//FINE TICKET ALM #664
		
		//TICKET ALM #3832
		String estremaUrgenza = currentRow.getNulledField(ART_ESTREMA_URGENZA_SOMMA_URGENZA.TABLE_NAME);
		//FINE TICKET ALM #3832
		
		//TICKET ALM #3834
		String modIndAllegatoIX = currentRow.getNulledField(MODALITA_INDIZIONE_ALLEGATO_IX.TABLE_NAME);
		//FINE TICKET ALM #3834
		
		//INT85
		String sceltaLegge89 = SimogFlags.isINT85_RFWEBGL01Active() ? currentRow.getNulledField(GARA.SCELTA_LEGGE89) : "";  	
		
		//INT87
		String urgenzaDL133 = SimogFlags.isINT87_RFSIMOGWEB01Active() ? currentRow.getNulledField(GARA.URGENZA_DL133) : "";  	 

		//is30350_RFWEBGL01Active non faccio vedere le categorie
  		String motivoEagg = SimogFlags.is30350_RFWEBGL01Active() ? currentRow.getNulledField(EAGG_MOTIVI.TABLE_NAME) : "";
		String[] categEagg = new String[0];
		
		//TICKET ALM #659 - 3.04.4
   		String flagSAAgente = currentRow.getNulledField(FUNZIONI_DELEGATE_GARA.FLAG_SA_AGENTE);
   		String idFDelegate = currentRow.getNulledField(FUNZIONI_DELEGATE.TABLE_NAME);
   		String cfAmmDelegante = currentRow.getNulledField(FUNZIONI_DELEGATE_GARA.CF_AMM_AGENTE);
   		String denAmmDelegante =currentRow.getNulledField(FUNZIONI_DELEGATE_GARA.DEN_AMM_AGENTE);
   		
      	//TICKET MAC #10467
   		String rupCreatoGara = (String)request.getAttribute(ParametriServlet.RUP_CREATO_GARA);
   		String codiceAusa = currentRow.getNulledField(GARA.CODICE_AUSA);
   		// fix 34470 3.04.8
   		String linkAffidamentoDiretto = (String) request.getAttribute(ParametriServlet.FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO);

   		String derogaQualificazioneSA = "";
   		derogaQualificazioneSA = (String) request.getAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_VISUAL);	

	%>
				
	<input type="hidden" name="idLotto" value="<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>">
	<input type="hidden" name = "<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>" value="<%=currentRow.getNulledField(GARA.ID_STAZIONE_APPALTANTE)%>"/>
	<input type="hidden" name = "<%= ParametriServlet.FIELD_NAME_CIG %>" value="<%=currentRow.getNulledField(LOTTO.CIG)%><%=currentRow.getNulledField(LOTTO.CIG_KKK)%>"/>
	<input type="hidden" name = "cfAmministrazione" value="<%= currentRow.getNulledField(GARA.CF_AMMINISTRAZIONE) %>"/>
	<input type="hidden" name = "<%= ParametriServlet.FIELD_NAME_ID_GARA %>" value="<%= currentRow.getNulledField(LOTTO.ID_GARA) %>"/>		

	<h4>Informazioni sulla Gara</h4>
	<%@ include file="include/garaVisual.inc"  %>

	<h4>Informazioni sui Lotti componenti</h4>
							
	<% String currentCIG =  currentRow.getNulledField(LOTTO.CIG) + currentRow.getNulledField(LOTTO.CIG_KKK); 
	 String sommaUrgenza = currentRow.getNulledField(LOTTO.SOMMA_URGENZA); 
	 currentCIG = PageHelper.getCIG( currentCIG,  sommaUrgenza, currentRow.getNulledField(LOTTO.DATA_CREAZIONE_LOTTO) ); %>

	<h5>Lotto - CIG [<strong><%= currentCIG %></strong>]</h5>
	<% String dataComunicazione =  currentRow.getNulledField(LOTTO.DATA_COMUNICAZIONE); %>
	<% if ( ! "".equalsIgnoreCase(dataComunicazione) ) { %>
		<h6>La pubblicazione del lotto &egrave; stata comunicata al Sistema Esattore in data <strong><%= PageHelper.getFormattedDate(dataComunicazione) %></strong></h6>
	<% } %>
	
<% // FLAG GESTIONE PROCEDURA RISTRETTA
   Boolean proceduraRistretta = (Boolean)request.getAttribute("flgProceduraRistretta");
   if(proceduraRistretta == null) proceduraRistretta = false;
%>	

	<div class="elenco">
		<div class="lotto">

		<%
		   String lottoOggetto = PageHelper.formattaTesto(currentRow.getNulledField(LOTTO.TABLE_NAME + LOTTO.OGGETTO));
					String lottoImporto = PageHelper.getFormattedImporto( currentRow.getNulledField(LOTTO.IMPORTO_LOTTO) );
					String lottoImportoSA = PageHelper.getFormattedImporto( currentRow.getNulledField(LOTTO.IMPORTO_SA) );
					String lottoImportoImpresa = PageHelper.getFormattedImporto( currentRow.getNulledField(LOTTO.IMPORTO_IMPRESA) );
					String lottoDataInibPagamenti = PageHelper.getFormattedDate( currentRow.getNulledField(LOTTO.DATA_INIB_PAGAMENTO) );
					String lottoDataScadenzaPagamenti = PageHelper.getFormattedDate( currentRow.getNulledField(LOTTO.DATA_SCADENZA_PAGAMENTI) ) ;
					String lottoDataPubblicazione = PageHelper.getFormattedDate( currentRow.getNulledField(LOTTO.DATA_PUBBLICAZIONE) );
					String lottoTipologia = currentRow.getNulledField(TIPOLOGIA.TABLE_NAME);
					String lottoSceltaContraente = currentRow.getNulledField(SCELTA_CONTRAENTE.TABLE_NAME);
					//2846
					String lottoMotivoCollegamento = currentRow.getNulledField(MOTIVO_COLLEGAMENTO.TABLE_NAME);
					//2846
					String dataCancellazioneLotto = PageHelper.getFormattedDate( currentRow.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO) );
					String lottoCpv = currentRow.getNulledField(CPVEU.TABLE_NAME);
					String lottoCpvSec = request.getAttribute("elencoCPVSecondarieHtml") != null ? (String) request.getAttribute("elencoCPVSecondarieHtml") : "";
					String lottoCategoriaPrevalente = listaCategorieScorporabili.getFieldBySearchField(CATEGORIA.ID_CATEGORIA, currentRow.getNulledField(LOTTO.ID_CATEGORIA_PREVALENTE), CATEGORIA.DESCRIZIONE );
					String tipoContratto = currentRow.getNulledField(LOTTO.TIPO_CONTRATTO_LOTTO);
					if(Costanti.TIPO_SCHEDA_LAVORI.equals(tipoContratto)) tipoContratto = Costanti.TIPO_SCHEDA_LAVORI_DESC;
					if(Costanti.TIPO_SCHEDA_SERVIZI.equals(tipoContratto)) tipoContratto = Costanti.TIPO_SCHEDA_SERVIZI_DESC;
					if(Costanti.TIPO_SCHEDA_FORNITURE.equals(tipoContratto)) tipoContratto = Costanti.TIPO_SCHEDA_FORNITURE_DESC;
					String escluso = currentRow.getNulledField(LOTTO.FLAG_ESCLUSO);
					escluso = PageHelper.decodeSN(escluso);
					String artEsclusione = currentRow.getNulledField(ART_ESCLUSIONE.TABLE_NAME);

		   		String id_motivazione = currentRow.getNulledField(LOTTO.ID_MOTIVAZIONE);
		   		String desc_motivazione = currentRow.getNulledField("L_" + MOTIVI_CANCELLAZIONE.DESCRIZIONE);
					String noteCancellazione = PageHelper.formattaTesto(currentRow.getNulledField(LOTTO.NOTE_CANC));
						
					String triennioAnnoInizio = currentRow.getNulledField(LOTTO.TRIENNIO_ANNO_INIZIO);
					String triennioAnnoFine = currentRow.getNulledField(LOTTO.TRIENNIO_ANNO_FINE);
					String triennioAnnoProgressivo = currentRow.getNulledField(LOTTO.TRIENNIO_PROGRESSIVO);
					String annualeCuiMinInf = currentRow.getNulledField(LOTTO.ANNUALE_CUI_MININF);
					
					if("0".equals(triennioAnnoInizio)) triennioAnnoInizio = "";
					if("0".equals(triennioAnnoFine)) triennioAnnoFine = "";
					if("0".equals(triennioAnnoProgressivo)) triennioAnnoProgressivo = "";

					//MEV 38205 3.04.8.1
					String flagUsoMetodiEdilizia = currentRow.getNulledField(LOTTO.FLAG_USO_METODI_EDILIZIA);
					flagUsoMetodiEdilizia = PageHelper.decodeSN(flagUsoMetodiEdilizia);
					//MEV 38205 3.04.8.1
					
					String oraScadenza = "";
					if(SimogFlags.is3025_RFWEBGL02Active())
					   oraScadenza = currentRow.getNulledField(LOTTO.ORA_SCADENZA);
					
					//gm nuovo codice pubblicazione bando 3.0
					String luogoIstat = currentRow.getNulledField(LOTTO.LUOGO_ISTAT);
					String luogoNuts = currentRow.getNulledField(LOTTO.LUOGO_NUTS);
					String importo_attuazione_sicurezza = PageHelper.getFormattedImporto(currentRow.getNulledField(LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA));
					//gm fine nuovo codice pubblicazione bando 3.0
					

					 //TICKET ALM #2845
					 String flagDL50 = PageHelper.decodeSN(currentRow.getNulledField(LOTTO.FLAG_DL50));
					 String primaAnnualita = currentRow.getNulledField(LOTTO.PRIMA_ANNUALITA);
					 //FINE TICKET ALM #2845
					
					 
					 //TICKET ALM #3835
					 String idAffRiservati = currentRow.getNulledField(AFFIDAMENTI_RISERVATI.TABLE_NAME);
					 //FINE TICKET ALM #3835
					 
					 //TICKET ALM #3836
					 String flagRegime = PageHelper.decodeSN(currentRow.getNulledField(LOTTO.FLAG_REGIME));
					 String idArtRegime = currentRow.getNulledField(ART_ESCLUSIONE.TABLE_NAME);
					 //FINE TICKET ALM #3836
					 
					// PP B302.2.3.3
					String flagPrevRip = "";
					String flagRipetiz = "";
					String cigRipetiz = "";

					if(SimogFlags.is30233_RFWEBGL02Active()){
						flagPrevRip = PageHelper.decodeSN(currentRow.getNulledField(LOTTO.FLAG_PREVEDE_RIP));
						flagRipetiz = PageHelper.decodeSN(currentRow.getNulledField(LOTTO.FLAG_RIPETIZIONE));
						cigRipetiz = currentRow.getNulledField(LOTTO.CIG_ORIGINE_RIP);
					}
					
	    	      String lottoDataScadenzaRichiestaInvito = "";
			    	String lottoDataLetteraInvito = "";
					if(SimogFlags.is3030_RFWEBGL00Active()){
					   lottoDataScadenzaRichiestaInvito = PageHelper.getFormattedDate(currentRow.getNulledField(LOTTO.DATA_SCADENZA_RICHIESTA_INVITO));
					   lottoDataLetteraInvito = PageHelper.getFormattedDate(currentRow.getNulledField(LOTTO.DATA_LETTERA_INVITO));								   
					}
					
					String flagCUP = "";
					if(SimogFlags.is3031_RFWEBGL02Active()){
					   flagCUP = PageHelper.decodeSN(currentRow.getNulledField(LOTTO.FLAG_CUP));
					}
					//TB: Ticket ALM #991
					String lottoDataCreazione = PageHelper.getFormattedDate(currentRow.getNulledField(LOTTO.DATA_CREAZIONE_LOTTO));
					//Fine Ticket ALM #991
				   
					String descCodCat = currentRow.getNulledField(EAGG_CATEGORIE.TABLE_NAME);
					
					//TICKET ALM #13691 - 3.04.5
					String importo_opzioni = PageHelper.getFormattedImporto(currentRow.getNulledField(LOTTO.IMPORTO_OPZIONI));

					
					if(user.isAmministratore()&&!lottoDataPubblicazione.equals(""))
					{
		%>
				<input type="hidden" name="pubblicato" value = "true"/>
			<%} %>
		
		  <%
		   
		// Ticket #20058 - 09 - 02 - 21
			String durataRinnoviRipetizioni = "0";
			
			///durataRinnoviRipetizioni = (request.getAttribute(ParametriServlet.FIELD_NAME_DURATA_RINNOVI_RIPETIZIONI) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_DURATA_RINNOVI_RIPETIZIONI): "";
			String durataRinnoviRipetizioniPar = (request.getParameter(ParametriServlet.FIELD_NAME_DURATA_RINNOVI_RIPETIZIONI) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_DURATA_RINNOVI_RIPETIZIONI): "0";
			if(!durataRinnoviRipetizioniPar.equals("0")){
				
				durataRinnoviRipetizioni=durataRinnoviRipetizioniPar;
			}
		   	// Ticket #20057 - 09 - 02 - 21
			String durataAffidamentoInGiorni = "0";
			String durataAffidamentoInGiorniPar = (request.getParameter(ParametriServlet.FIELD_NAME_DURATA_AFFIDAMENTO_IN_GIORNI) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_DURATA_AFFIDAMENTO_IN_GIORNI): "0";
			if(!durataAffidamentoInGiorniPar.equals("0")){
				
				durataAffidamentoInGiorni=durataAffidamentoInGiorniPar;
			}
			
		   %>
		
			<%@ include file="include/lottoVisual.inc" %>
			
			<table>
				<tr>
					<th>Categorie scorporabili</th>
					<td><ul>
			<% currentLotto =  idLotto; 
			 	previousRow = currentRow; 
				if ( ! currentRow.getNulledField(ParametriServlet.CATEGORIA_SCORPORABILE ).trim().equals("") ) { %>
						<li><%= listaCategorieScorporabili.getFieldBySearchField(CATEGORIA.ID_CATEGORIA, currentRow.getNulledField(ParametriServlet.CATEGORIA_SCORPORABILE ), CATEGORIA.DESCRIZIONE) %></li>
			<% } %>
				</ul>
			</td>
		</tr>
	</table>


	<h4>Giustificazione della cancellazione</h4>
	<table>
		<tr>
		   <% // Motivazione e Note dalla request dopo una validazione errata
	   	  String reqMotivazione = request.getParameter(ParametriServletLotto.FIELD_NAME_MOTIVAZIONE);
			  String reqNoteCancel = request.getParameter(ParametriServletLotto.FIELD_NAME_NOTE);
		   %>
			<th>Motivazione*</th>
			<td>
				<select name="<%= ParametriServletLotto.FIELD_NAME_MOTIVAZIONE %>">
					<option></option>
					<x:options name="<%= ParametriServletLotto.MOTIVAZIONI_LIST %>" scope="request" value='<%=reqMotivazione != null ? reqMotivazione : ""%>'/>
				</select>
			</td>
		</tr>
		<tr>
			<th>Note</th>
			<td>
				<textarea rows="2" cols="35" name="<%= ParametriServletLotto.FIELD_NAME_NOTE %>"><%=reqNoteCancel != null ? reqNoteCancel : ""%></textarea>
			</td>
		</tr>
	</table>
											
	<div class="infoBlock">
		<div class="inlineInfo">
			<ul>
			<li>Confermare</li>
			</ul>
			</div>
			<div class="rightLineInfo">
			<ul>
			<% if ( (user.isAmministratore() || user.isRSSAorRUP()) && cancellabile ) { %>
			<li><input type="submit" value="Conferma la cancellazione"></li>
			<% } %>
			</ul>
		</div>
	</div>
				
	</div>	
	</div>
	</div>	
</form>
	
	</div>
	</div>
	<%@ include file="include/newfooter.inc" %>
	
</div>
</body>
</html>
<%}catch(Exception e){e.printStackTrace();}%>	