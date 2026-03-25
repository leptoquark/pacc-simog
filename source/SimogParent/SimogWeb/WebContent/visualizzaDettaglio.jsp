<%@page import="it.avlp.simog.errormessage.Messaggi"%>
<%@page import="java.util.ArrayList"%>
<%try{ %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.beans.CIGBean"%>
<%@ page import="java.math.BigDecimal"%>


<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>

<script type="text/javascript" src="calendar/calendar.js"></script>
<%@ include file="include/calendar-dynamic.inc" %>
<script type="text/javascript" src="calendar/calendar-setup.js"></script>

<script type="text/javascript"  src="script/pageutils.js"></script>
<script type="text/javascript" src="xtree/treeutils.js"></script>

<!-- TB: ticket popup modali. Import css e js -->
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<!-- fine import popup modali -->

<%if(SimogFlags.is3025_REQUISITIActive()){ %>
	<style type="text/css" media="screen">@import "tabs/tabs.css";</style>
	<script type="text/javascript" src="script/other/jquery.multiselect.js"></script>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/script/other/jquery.multiselect.css" type="text/css" />
	<link rel="stylesheet" href="<%= request.getContextPath() %>/script/other/jquery-ui.css" type="text/css" />
<%} %>

<title>SIMOG - <utils:message key="visualizza.gestioneGare" /></title>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">


</head>
<body>

<%

//Ticket #20055	
boolean	enableButtonModifica =  Boolean.valueOf(String.valueOf(request.getAttribute(ParametriServlet.RETTIFICA_GARA_LOTTI)));
%>

<div id="dialog"></div>
<div id="gabbia">
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuGara.inc" %>
	<div id="bodypage">
		<div class="bodypage-e">
			<h1><utils:message key="dettaglio.titolo" /></h1>
				<%@ include file="include/gestisciErrore.inc" %>
				
			<div class="hmenu">
				<ul>
				<%	
					boolean cancellabile = false; 
					boolean cancellato = false; 
					boolean scaduto = false; 
					boolean pagabile = false;
					boolean inLavorazione = false;
					TableBean listaGare = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); 
					TableBean listaCategorieScorporabili = (TableBean)request.getAttribute(ParametriServlet.CATEGORIA_BEAN); 
					TableBeanRow currentRow = null;
					TableBeanRow nextRow = null;
				    //TableBeanRow previousRow = null; 
					String previousLotto = null;
					String nextLotto = null;
				    String currentLotto = null; 
					String lottoImportoImpresa = null;
					String lottoImportoSA = null;
					boolean actions = false;
					String garaPubblicabile = (String) session.getAttribute(ParametriServlet.GARA_PUBBLICABILE);
					String fromGare = (String) request.getAttribute(ParametriServlet.FROM_GARE);
					String fromRicerca = (String) request.getAttribute(ParametriServlet.FROM_RICERCA);
				   request.setAttribute(ParametriServlet.FROM_RICERCA, fromRicerca);
					if(Costanti.FLAG_VALORE_SI.equals(fromRicerca) || "VIS".equals(fromRicerca)){
					String href = ParametriServlet.SRV_GESTIONE_SCHEDE;
					if("VIS".equals(fromRicerca)){
					    href="elencoCig";
				   		if(session.getAttribute(ParametriServlet.STORIA_PAGINAZIONE) != null && !"".equals(session.getAttribute(ParametriServlet.STORIA_PAGINAZIONE))){
				   			href += "?" + session.getAttribute(ParametriServlet.STORIA_PAGINAZIONE);
						   }
				   		else {
				   			if(fromRicerca != null) href += "?nav=yes&"+ParametriServlet.FROM_RICERCA+"="+fromRicerca;
				   		}
					}
					else{
						if(user.isAmministratore())
				    		href = ParametriServlet.SRV_GESTIONE_GARE_EXT ;
				   	else if(user.isRSSA())
				    		href = ParametriServlet.SRV_GESTIONE_GARE_RSSA ;
				   		if(session.getAttribute(ParametriServlet.STORIA_PAGINAZIONE) != null && !"".equals(session.getAttribute(ParametriServlet.STORIA_PAGINAZIONE))){
				   			href += "?" + session.getAttribute(ParametriServlet.STORIA_PAGINAZIONE);
						   }
				   		else {
				   			if(fromRicerca != null) href += "?"+ParametriServlet.FROM_RICERCA+"="+fromRicerca;
				   		}
					}
					session.setAttribute("ultimaRicerca",href);
					%>
					<li><a title="Pagina precedente" href="<%=href %>">Ritorna</a></li>
					<% } else if(session.getAttribute("ultimaRicerca") != null) {
						String href = (String)session.getAttribute("ultimaRicerca"); %>
						<li><a title="Pagina precedente" href="<%=href %>">Ritorna</a></li>
					<% } %>
					<% boolean Confermato = StatiScheda.CONFERMATO_STRING.equals(listaGare.getRow(0).getNulledField(GARA.ID_STATO));
					   boolean garaCanc = StatiScheda.ANNULLATO_STRING.equals(listaGare.getRow(0).getNulledField(GARA.ID_STATO));	
						// blocco se gara pubblicata e avcpass dice no!
						Boolean bloccoReq = (Boolean)request.getAttribute(PSReq.BLOCCO_AVCPASS);
					   boolean bloccoAVCPASS = SimogFlags.is3028_RFWEBGL07Active() ? bloccoReq : false;
					%>
				</ul>
				<% if( bloccoAVCPASS ){ %>
					<div>
					<br>
					<table width="100%" class="gara">
						<tr>
						<td><img src="img/simogWarning.jpg" height="60px" width="60px"></td>
						<td>
						<p style="color: red;"><big><strong><%= Messaggi.SIMOG_AVCPASS_001 %></strong></big></p>
						</td>
						</tr>		
					</table>
					</div>
				<%} %>
			</div><%-- hmenu --%>
<%
SimogProperties configSimog = (SimogProperties) request.getAttribute(PSReq.SIMOG_PROPERTIES);
Object listaReq = request.getAttribute(PSReq.LISTA_REQUISITI_GARA);
boolean siRequisiti = (SimogFlags.is3025_REQUISITIActive() 
      && configSimog.getDataRequisiti().compareTo(PageHelper.getCurrentDate())<=0
      && (!SimogFlags.is3028_RFWEBGL01Active() || (SimogFlags.is3028_RFWEBGL01Active() 
            && listaReq != null && !"[]".equals(listaReq.toString()))));

if(siRequisiti){ %>
<div id='tab-container'>
		<div class="tab-content">
		<h1 class="tab" title="<utils:message key="dettaglio.informazioniGaraLotti" plain="true" />"><utils:message key="dettaglio.informazioniGaraLotti" /></h1>
<% } %>									
			<div class="testo">
			<%	int rowIndex = 0;
				currentRow = listaGare.getRow(rowIndex);
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
				// PP 3.02.3 gestione errore calcolo contributo 
				String importoSAGara = "";
					importoSAGara = currentRow.getNulledField(GARA.IMPORTO_SA_GARA);
					if(Costanti.IMPORTO_FUORI_SCALA_STRING.equals(importoSAGara) || Costanti.IMPORTO_FUORI_SCALA_STRING_3D.equals(importoSAGara))
						importoSAGara = "per problemi tecnici non e' stato possibile calcolare il contributo. Il contributo sara' correttamente visualizzabile sul sistema di riscossione";
					else
						importoSAGara = PageHelper.getFormattedImporto(currentRow.getNulledField(GARA.IMPORTO_SA_GARA));
					
					if(PageHelper.IMPORTO_ND.equals(importoSAGara)){
						importoSAGara = "Il valore sara' calcolato ad esito della conferma dei dati";
					}				   

				
				String statoGara = currentRow.getNulledField(STATI_SCHEDA.DESCRIZIONE);
				String idSaRiferimento = currentRow.getNulledField(GARA.ID_STAZIONE_APPALTANTE);
				String descrSARiferimento = currentRow.getNulledField( GARA.DENOM_STAZIONE_APPALTANTE );
				String amministrazioneCodiceFiscale	= currentRow.getNulledField(GARA.CF_AMMINISTRAZIONE);
				String amministrazioneDescrizione = currentRow.getNulledField( GARA.DENOM_AMMINISTRAZIONE );
				String RSSA_CodiceFiscale = currentRow.getNulledField(GARA.CF_UTENTE);
						
				String garaDataPerfezionamento = currentRow.getNulledField(GARA.DATA_PERFEZIONAMENTO_BANDO);
				String garaDataCancellazione = currentRow.getNulledField(GARA.DATA_CANCELLAZIONE_GARA);
				String garaDataTerminePagam = currentRow.getNulledField(GARA.DATA_TERMINE_PAGAMENTO);
				String garaDataInibPagam = currentRow.getNulledField(GARA.DATA_INIB_PAGAM);
				String garaDataConferma = currentRow.getNulledField(GARA.DATA_CONFERMA_GARA);
				String garaDataComun = currentRow.getNulledField(GARA.DATA_COMUN);
				
				String tipoScheda = currentRow.getNulledField(TIPI_CATEGORIA.TABLE_NAME);
				String modoGara = currentRow.getNulledField(MODO_INDIZIONE.TABLE_NAME);
				String modoReal = currentRow.getNulledField(MODI_REALIZZAZIONE.TABLE_NAME);
				
				String descMotivGara = currentRow.getNulledField("G_" + MOTIVI_CANCELLAZIONE.DESCRIZIONE);
				String noteCancGara = PageHelper.formattaTesto(currentRow.getNulledField(GARA.NOTE_CANC_GARA));
				String id_motivazioneGara = currentRow.getNulledField(GARA.ID_MOTIVAZIONE_CANC);
				String cigQuadro =  currentRow.getNulledField( GARA.CIG_ACC_QUADRO );
            //gm nuovo codice simog 3.04
        		String numeroLotti =  currentRow.getNulledField( GARA.NUMERO_LOTTI );
            
        		// 659 nuovo campo
        		String durataGiorni = currentRow.getNulledField( GARA.DURATA_GIORNI );
    			// 659

        		//TICKET ALM #664
        		String strumentoSvolgimento = currentRow.getNulledField(STRUMENTI_SVOLGIMENTO_PROCEDURE.TABLE_NAME);
        		//FINE TICKET ALM #664
        		
        		//TICKET ALM #3832
		        String estremaUrgenza = currentRow.getNulledField(ART_ESTREMA_URGENZA_SOMMA_URGENZA.TABLE_NAME);
		        //FINE TICKET ALM #3832
		        
		         //TICKET ALM #3834
		        String modIndAllegatoIX = currentRow.getNulledField(MODALITA_INDIZIONE_ALLEGATO_IX.TABLE_NAME);
		        //FINE TICKET ALM #3834
		        
				//UN Se non ci sono lotti non visualizziamo le info sui lotti
				boolean nienteLotti = "0".equals(currentRow.getNulledField(LOTTO.ID_LOTTO));
				String display = nienteLotti ? "display:none" : "display:block";	
				
				// PP modifica richiesta da Carrabs 3.08.2011
				String idModoRealStr = currentRow.getNulledField(GARA.ID_MODO_REAL);
				if(idModoRealStr == null || "".equals(idModoRealStr))
					idModoRealStr = "0";
				int idModo = Integer.parseInt(idModoRealStr);	
				/* 3.04.8 34190 fix */
	        	boolean	isAdesione = Costanti.MODOREAL_ADESIONE == idModo || Costanti.MODOREAL_ADESIONE_NOCOMPET == idModo || Costanti.MODOREAL_CONCESSIONE == idModo || Costanti.MODOREAL_CONCESSIONE_NOCOMPET == idModo;
	        	
	        	// is3031_ESCL_AVCPASS
	        	String esclusioneAVCPass = SimogFlags.is3031_ESCL_AVCPASS() ? currentRow.getNulledField(GARA.ESCLUSO_AVCPASS) : "";
	        	
	   		//INT85
	   		String sceltaLegge89 = SimogFlags.isINT85_RFWEBGL01Active() ? currentRow.getNulledField(GARA.SCELTA_LEGGE89) : "";  	        	

	   		//INT87
	   		String urgenzaDL133 = SimogFlags.isINT87_RFSIMOGWEB01Active() ? currentRow.getNulledField(GARA.URGENZA_DL133) : "";
	   		
	   		//is30350_RFWEBGL01Active
	   		String motivoEagg = SimogFlags.is30350_RFWEBGL01Active() ? currentRow.getNulledField(EAGG_MOTIVI.TABLE_NAME) : "";
	   		
	   		// mancano le categorie
	   		String[] categEagg = (String[]) request.getAttribute(ParametriServlet.EAGG_CATEGSEL_BEAN);	
	   		
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
	     	
	     
	   		%>

				<h4 title="<%= currentRow.getNulledField(GARA.ID_OSSERVATORIO)%>">Informazioni sulla Gara</h4>
				<%@ include file="include/garaVisual.inc" %>
			
			</div><%-- testo --%>
			
			<%--------------------------------- BEGIN ELENCO LOTTI -----------------------------%>
<% Integer startRowInt = (Integer)request.getAttribute( ParametriServlet.START_ROW ); 
	int tableBeanSize = listaGare.getFullSize();
	int startRow = startRowInt.intValue(); 
	int maxRigheVisualizzabili = Integer.parseInt( (String)request.getAttribute( ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI ) );
	int righeVisualizzate = startRow + (listaGare.getTableSize() > maxRigheVisualizzabili ? maxRigheVisualizzabili : listaGare.getTableSize());
	long resto = (tableBeanSize % maxRigheVisualizzabili);
	long fineElenco = tableBeanSize - resto - maxRigheVisualizzabili - (resto == 0 ? maxRigheVisualizzabili : 0) ; 
	
	String jspRicerca = ParametriServlet.SRV_VISUALIZZA_DETTAGLIO + "?"	+ ParametriServlet.FROM_RICERCA + "=" + fromRicerca;
	
	boolean siLotti = !"".equals(listaGare.getNulledField(LOTTO.CIG, 0));
	if(SimogFlags.is30233_RFWEBGL05Active() && siLotti){ %>
		<div class="hmenu">
			<ul>	
			<% if ( startRowInt >  0 ) { %>
				<li><a onclick="doPost(this.href);" href="<%= jspRicerca %>" title="Visualizza prima pagina">Inizio elenco</a></li>
			<% } 
			else {%> <li><a id="disabledMenu" title="Visualizza prima pagina">Inizio elenco</a></li> <% } %>
			
			<% if ( righeVisualizzate >  maxRigheVisualizzabili ) { %>
				<li><a href="<%= jspRicerca %>&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.REGRESS %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>" title="Visualizza Precedenti">Precedenti</a></li>
			<% }
			else {%> <li><a id="disabledMenu" title="Visualizza Precedenti">Precedenti</a></li> <% } %>
			
			<% if ( tableBeanSize - righeVisualizzate > 0 ) { %>
				<li><a href="<%= jspRicerca %>&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.PROGRESS %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>" title="Visualizza Successive">Successive</a></li>
			<% }
			else {%> <li><a id="disabledMenu" title="Visualizza Successive">Successive</a></li> <% } %>
			
			<% if ( righeVisualizzate != tableBeanSize ) { %>
				<li><a href="<%= jspRicerca %>&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.PROGRESS %>&<%= ParametriServlet.START_ROW %>=<%= fineElenco %>" title="Visualizza ultima pagina">Fine elenco</a></li>
			<% }
			else { %>
				<li><a id="disabledMenu" title="Visualizza ultima pagina">Fine elenco</a></li>
			<%}%>
			</ul>
		</div>
		<h3>Visualizzati <%= righeVisualizzate %>/<%= listaGare.getFullSize() %> Elementi</h3>
<% } %>
			

<% // FLAG GESTIONE PROCEDURA RISTRETTA
   Boolean proceduraRistretta = (Boolean)request.getAttribute("flgProceduraRistretta");
   if(proceduraRistretta == null) proceduraRistretta = false;
%>
			
			<div id="infoLotti" style="<%= display %>">
				<h4>Informazioni sui Lotti componenti</h4>
			<% String noDispLotto = "VIS".equals((String) request.getAttribute(ParametriServlet.FROM_RICERCA)) ? "style='display:none'" : ""; %>	
			<% for ( rowIndex = 0; rowIndex < listaGare.getTableSize(); rowIndex++ ) { 
// 			   	if(SimogFlags.is30233_RFWEBGL05Active() && rowIndex < startRowInt)
// 			   	   continue;
// 			   	if(SimogFlags.is30233_RFWEBGL05Active() && rowIndex >= startRowInt + maxRigheVisualizzabili)
// 				   	break;
			   	
					currentRow = listaGare.getRow(rowIndex); 
					currentLotto = currentRow.getNulledField(LOTTO.ID_LOTTO);
					boolean nuovoLotto = ! currentLotto.equalsIgnoreCase( previousLotto );
					
					/* Calcolo di fineLotto */
					/* La tableBean contiene tante riche del medesimo lotto quante sono le categorie scorporabili associate */
					/* nuovoLotto indica il passaggio ad un lotto nuovo */
					/* fineLotto prevede il passaggio ad un lotto nuovo al prossimo ciclo */ 
					if(rowIndex < listaGare.getTableSize()-1) {
						nextRow	= listaGare.getRow(rowIndex + 1);
						nextLotto = nextRow.getNulledField(LOTTO.ID_LOTTO);
					}
					else
						nextLotto = null;
					boolean fineLotto =  ! currentLotto.equalsIgnoreCase( nextLotto );
					
					if ( nuovoLotto ) { 
				 		String currentCIG =  currentRow.getNulledField(LOTTO.CIG) + currentRow.getNulledField(LOTTO.CIG_KKK);
						String sommaUrgenza = currentRow.getNulledField(LOTTO.SOMMA_URGENZA); 
						currentCIG = PageHelper.getCIG( currentCIG,  sommaUrgenza, currentRow.getNulledField(LOTTO.DATA_CREAZIONE_LOTTO) ); 
						String dataComunicazione =  currentRow.getNulledField(LOTTO.DATA_COMUNICAZIONE); 
			%>
						<h5><big>Lotto CIG [<strong><%= currentCIG %></strong>]</big></h5>
												
			<%			if ( ! "".equalsIgnoreCase(dataComunicazione) ) { %>
							<h6>La fase di perfezionamento &egrave; stata completata automaticamente in data  <strong><%= PageHelper.getFormattedDate(dataComunicazione) %></strong></h6>
			<% 			} %>
			
						<div class="elenco">
						<div class="lotto" style="width: 100%">
						<%
						   String lottoDataPubblicazione = PageHelper.getFormattedDate( currentRow.getNulledField(LOTTO.DATA_PUBBLICAZIONE) );
							
								   lottoImportoImpresa = currentRow.getNulledField(LOTTO.IMPORTO_IMPRESA);
									if(Costanti.IMPORTO_FUORI_SCALA_STRING.equals(lottoImportoImpresa) || Costanti.IMPORTO_FUORI_SCALA_STRING_3D.equals(lottoImportoImpresa))
									   lottoImportoImpresa = "per problemi tecnici non e' stato possibile calcolare il contributo. Il contributo sara' correttamente visualizzabile sul sistema di riscossione";
									else
										lottoImportoImpresa = PageHelper.getFormattedImporto( currentRow.getNulledField(LOTTO.IMPORTO_IMPRESA) );
									
									if(PageHelper.IMPORTO_ND.equals(importoSAGara)){
									   lottoImportoImpresa = "Il valore sara' calcolato ad esito della conferma dei dati";
									}				   
							

								String lottoOggetto = PageHelper.formattaTesto(currentRow.getNulledField(LOTTO.TABLE_NAME + LOTTO.OGGETTO));

								//TB: Ticket ALM #991
								String lottoDataCreazione = PageHelper.getFormattedDate(currentRow.getNulledField(LOTTO.DATA_CREAZIONE_LOTTO));
								//Fine Ticket ALM #991
								
								String unformattedImporto =  currentRow.getNulledField(LOTTO.IMPORTO_LOTTO);
								String lottoImporto = PageHelper.IMPORTO_ND;
								if(!"".equals(unformattedImporto) && new BigDecimal(unformattedImporto).compareTo(new BigDecimal(Costanti.IMPORTO_FUORI_SCALA)) != 0){
									lottoImporto = PageHelper.getFormattedImporto(unformattedImporto);
								}
								String lottoDataInibPagamenti = PageHelper.getFormattedDate( currentRow.getNulledField(LOTTO.DATA_INIB_PAGAMENTO) );
								String lottoDataScadenzaPagamenti = PageHelper.getFormattedDate( currentRow.getNulledField(LOTTO.DATA_SCADENZA_PAGAMENTI) ) ;
								String lottoTipologia = currentRow.getNulledField(TIPOLOGIA.TABLE_NAME);
								String lottoSceltaContraente = currentRow.getNulledField(SCELTA_CONTRAENTE.TABLE_NAME);
								//2846
								String lottoMotivoCollegamento = currentRow.getNulledField(MOTIVO_COLLEGAMENTO.TABLE_NAME);
								//2846
								String dataCancellazioneLotto = PageHelper.getFormattedDate( currentRow.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO) );
								String lottoCpv = currentRow.getNulledField(LOTTO.ID_CPV) + " " + currentRow.getNulledField(CPVEU.TABLE_NAME);
								String lottoCpvSec = currentRow.getNulledField(CPV_LOTTO.TABLE_NAME);
								String lottoCategoriaPrevalente = listaCategorieScorporabili.getFieldBySearchField(CATEGORIA.ID_CATEGORIA, currentRow.getNulledField(LOTTO.ID_CATEGORIA_PREVALENTE), CATEGORIA.DESCRIZIONE );
								String id_motivazione = currentRow.getNulledField(LOTTO.ID_MOTIVAZIONE);
								String desc_motivazione = currentRow.getNulledField("L_" + MOTIVI_CANCELLAZIONE.DESCRIZIONE);
								String noteCancellazione = PageHelper.formattaTesto(currentRow.getNulledField(LOTTO.NOTE_CANC));
								String tipoContratto = currentRow.getNulledField(LOTTO.TIPO_CONTRATTO_LOTTO);
								if(Costanti.TIPO_SCHEDA_LAVORI.equals(tipoContratto)) tipoContratto = Costanti.TIPO_SCHEDA_LAVORI_DESC;
								if(Costanti.TIPO_SCHEDA_SERVIZI.equals(tipoContratto)) tipoContratto = Costanti.TIPO_SCHEDA_SERVIZI_DESC;
								if(Costanti.TIPO_SCHEDA_FORNITURE.equals(tipoContratto)) tipoContratto = Costanti.TIPO_SCHEDA_FORNITURE_DESC;
								
								//MEV 38205 3.04.8.1
								String flagUsoMetodiEdilizia = currentRow.getNulledField(LOTTO.FLAG_USO_METODI_EDILIZIA);
								flagUsoMetodiEdilizia = PageHelper.decodeSN(flagUsoMetodiEdilizia);
								//MEV 38205 3.04.8.1
								
								String oraScadenza = "";
								if(SimogFlags.is3025_RFWEBGL02Active())
								   oraScadenza = currentRow.getNulledField(LOTTO.ORA_SCADENZA);
								
								
								
								String escluso = currentRow.getNulledField(LOTTO.FLAG_ESCLUSO);
								escluso = PageHelper.decodeSN(escluso);
								
								String artEsclusione = currentRow.getNulledField(ART_ESCLUSIONE.TABLE_NAME);

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
								String importo_attuazione_sicurezza = PageHelper.getFormattedImporto(currentRow.getNulledField(LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA));
								//gm fine nuovo codice pubblicazione bando 3.0
								
								// PP B302.3.3
								String flagPrevRip = "";
								// Ticket #20058 - 09 - 02 - 21
								String durataRinnoviRipetizioni = "";
							
								String flagRipetiz = "";
								String cigRipetiz = "";

								if(SimogFlags.is30233_RFWEBGL02Active()){
									
									flagPrevRip = PageHelper.decodeSN(currentRow.getNulledField(LOTTO.FLAG_PREVEDE_RIP));
									// Ticket #20058 - 09 - 02 - 21
									durataRinnoviRipetizioni = currentRow.getNulledField(LOTTO.DURATA_RINNOVI_RIPETIZIONI);
									flagRipetiz = PageHelper.decodeSN(currentRow.getNulledField(LOTTO.FLAG_RIPETIZIONE));
									cigRipetiz = currentRow.getNulledField(LOTTO.CIG_ORIGINE_RIP);
								}
								
								//Ticket #20057
								String durataAffidamentoInGiorni ="";
								if(SimogFlags.is30233_RFWEBGL02Active()){
									
									durataAffidamentoInGiorni = currentRow.getNulledField(LOTTO.DURATA_AFFIDAMENTO_IN_GIORNI);
								}
								
								// fix 40610 3.04.9
								 String derogaQualificazioneSA ="";
						     	derogaQualificazioneSA = currentRow.getNulledField(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_VISUAL);

								
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
					 			
					 			String descCodCat = currentRow.getNulledField(EAGG_CATEGORIE.TABLE_NAME);
					 			
				    	      String lottoDataScadenzaRichiestaInvito = "";
						    	String lottoDataLetteraInvito = "";
								if( SimogFlags.is3030_RFWEBGL00Active() ){
								   lottoDataScadenzaRichiestaInvito = PageHelper.getFormattedDate(currentRow.getNulledField(LOTTO.DATA_SCADENZA_RICHIESTA_INVITO));
								   lottoDataLetteraInvito = PageHelper.getFormattedDate(currentRow.getNulledField(LOTTO.DATA_LETTERA_INVITO));								   
								}
								
								String flagCUP = "";
								if(SimogFlags.is3031_RFWEBGL02Active()) {
								   flagCUP = PageHelper.decodeSN(currentRow.getNulledField(LOTTO.FLAG_CUP));
								}
								
								//TICKET ALM 13691 - 3.04.5
							    String importo_opzioni = PageHelper.getFormattedImporto(currentRow.getNulledField(LOTTO.IMPORTO_OPZIONI));

								
						%>
						<%@ include file="include/lottoVisual.inc" %>
						<table>
							<tr>
								<th>Categorie scorporabili</th>
								<td><ul>
				<% 	} //if nuovoLotto %>
							
							<%-- Visualizzazione Categorie scorporabili --%>
							<%-- NON PIU' La tableBean contiene tante riche del medesimo lotto quante sono le categorie scorporabili associate --%>
							<% if ( ! currentRow.getNulledField(ParametriServlet.CATEGORIA_SCORPORABILE ).trim().equals("") ) { 
								String[] categ = currentRow.getNulledField(ParametriServlet.CATEGORIA_SCORPORABILE ).trim().split("~");
								for(int i = 0; i<categ.length;i++){ 
							%>
								<li><%= listaCategorieScorporabili.getFieldBySearchField(CATEGORIA.ID_CATEGORIA, categ[i], CATEGORIA.DESCRIZIONE) %></li>
							<% }} %>
					
				<%	if ( fineLotto ) { %>
								</ul></td>
							</tr>
						</table>	 
														
						<%-----------  Visualizza Pagamenti e Gestione Lotto  ----------%>	
						
						<%	String hasSchede = currentRow.getNulledField(PSBD.HASSCHEDE);
							cancellabile = "0".equals(hasSchede) 
												&& "".equalsIgnoreCase( currentRow.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO) ) 
												&& "".equalsIgnoreCase( currentRow.getNulledField(LOTTO.DATA_INIB_PAGAMENTO)) 
												&& (user.isAmministratore() || "".equalsIgnoreCase(currentRow.getNulledField(LOTTO.DATA_COMUNICAZIONE))); 
							cancellato = ! ( "".equalsIgnoreCase( currentRow.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO))) 
											|| ! ( "".equalsIgnoreCase( currentRow.getNulledField(LOTTO.DATA_INIB_PAGAMENTO))); 
							
							scaduto = ( ! "".equalsIgnoreCase(currentRow.getNulledField(LOTTO.DATA_SCADENZA_PAGAMENTI) ) ) && PageHelper.getCurrentDate().compareTo(currentRow.getNulledField(LOTTO.DATA_SCADENZA_PAGAMENTI ) ) > 0; 
							pagabile = !("0.00".equals(currentRow.getNulledField(LOTTO.IMPORTO_IMPRESA) ) );
							inLavorazione = "".equals(currentRow.getNulledField(LOTTO.DATA_PUBBLICAZIONE));
							boolean lottoFromWS = CIGBean.APPL_WS.compareToIgnoreCase(currentRow.getNulledField(CIG_STORIA.APPLICAZIONE).trim()) == 0;
							boolean bandoPerfezionato = !"".equals(listaGare.getRow(0).getNulledField(GARA.DATA_PERFEZIONAMENTO_BANDO));
							boolean miagara = user.getLogin().equalsIgnoreCase(listaGare.getRow(0).getNulledField(GARA.CF_UTENTE));
						%> 
							<div class="infoBlock">
							<% 	actions = false; %>
								<div class="hmenu">
								<ul>
									<% if (SimogFlags.is3031_RFWEBGL02Active()) { %>
										<li><a href="gestisciLotto?action=dettaglioLotto&idLotto=<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>">Visualizza altri dati</a></li>
										<% actions = true; %>
									<% } %>
									
									<% 
									//Ticket #20055
									boolean abilitaModifica = false;
									 if(!"".equals(listaGare.getRow(0).getNulledField(GARA.DATA_PERFEZIONAMENTO_BANDO))){
										 if(enableButtonModifica){
											 abilitaModifica = true;
										 }
									 }else{
										 
										 abilitaModifica = true;
									 } 
									%>
								
									<% if ( // PP Confermato && 
											
											((user.isRSSAorRUP() && abilitaModifica) || user.isAmministratore())
											&& "0".equals(hasSchede) 
											// PP && "".equalsIgnoreCase( currentRow.getNulledField(LOTTO.DATA_PUBBLICAZIONE) ) 
											// PP && currentRow.getNulledField(LOTTO.DATA_COMUNICAZIONE).equalsIgnoreCase("") 
											&& ! cancellato  
											&& (currentRow.getNulledField(LOTTO.DATA_COMUNICAZIONE).equals ("") ||  user.isAmministratore())
											&& (!lottoFromWS || user.isAmministratore())
											&& (miagara || user.isAmministratore())
											&& !bloccoAVCPASS
											) { %>
										<li <%=noDispLotto %>><a href="gestisciLotto?action=modifica&idLotto=<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>">Modifica</a></li>
										<% actions = true; %>
									<% } %>
									<% if (false // disabilitata sempre! // !SimogFlags.is3030_RFWEBGL00Active() // PP con procedura ristretta va disabilitata
									      && user.isAmministratore()
											&& ! cancellato 
											&& "0".equals(hasSchede)
											&& !bloccoAVCPASS
											&& (bandoPerfezionato || !SimogFlags.is3030_RFWEBGL00Active())) { %>
										<li <%=noDispLotto %>><a href="gestisciLotto?action=perfeziona&idLotto=<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>">Perfeziona</a></li>
										<% actions = true; %>
									<% } %>
									
									<%--gm aggiunto per simog 3.04 modificare contratti esclusi per lotti confermati --%>	
									<% if (user.isRSSAorRUP() && "0".equals(hasSchede) && !inLavorazione && !cancellato 
									      && !lottoFromWS
											&& miagara
									      && !bloccoAVCPASS) { %>
										<li <%=noDispLotto %>><a href="gestisciLotto?action=modificaContrattoEscluso&idLotto=<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>">Modifica Contratto escluso</a></li>
										<% actions = true; %>
									<% } %>
									<%--gm fine --%>

									<% if (SimogFlags.is30233_RFWEBGL02Active() 
									      && user.isRSSAorRUP() 
									      && miagara
									      && !inLavorazione 
									      && !cancellato 
									      && (!lottoFromWS || user.isAmministratore())
									      && !bloccoAVCPASS){ %>
										<li <%=noDispLotto %>><a href="gestisciLotto?action=modificaRipetizioni&idLotto=<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>">Modifica Dati Ripetizioni</a></li>
										<% actions = true; %>
									<% } %>
									
									<% if (SimogFlags.is3031_RFWEBGL02Active()
									      && SimogFlags.is3031_RFWEBGL04Active()
									      && user.isRSSAorRUP() 
											&& (miagara || user.isAmministratore())
									      && !inLavorazione 
									      && !cancellato 
									      && (!lottoFromWS || user.isAmministratore())
									      && !bloccoAVCPASS){ %>
										<li <%=noDispLotto %>><a href="gestisciLotto?action=modificaDatiCup&idLotto=<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>">Modifica Dati Cup</a></li>
										<% actions = true; %>
									<% } %>		
									
									<!-- MEV 37010 3.04.8.1  --> 
									
									<% 
									String mostraIntegraPariOpportunita = (String)request.getAttribute(ParametriServlet.MOSTRA_INTEGRA_PARI_OPPORTUNITA); 
									
    								
									if ( user.isRSSAorRUP() 
									      && miagara
									      && !inLavorazione 
									      && !cancellato 
									      /* && (!lottoFromWS || user.isAmministratore()) */
									     /*  && user.isAmministratore() */
									      && !bloccoAVCPASS
									      && mostraIntegraPariOpportunita.equals("true")){ %>
										<li <%=noDispLotto %>><a href="gestisciLotto?action=integraPariOpportunita&idLotto=<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>">Integra dati pari opportunit�</a></li>
										<% actions = true; %>
									<% } %>	
									<!-- MEV 37010 3.04.8.1  -->
									
									<!-- MEV 3.04.10 43227  -->
									<% 
    								String mostraModificaDatiPerfezionamento = (String)request.getAttribute(ParametriServlet.MOSTRA_MODIFICA_DATI_PERFEZIONAMENTO);
    								
									if ( user.isRSSAorRUP() 
									      && miagara
									      && !inLavorazione 
									      && !cancellato 
									      && (!lottoFromWS || user.isAmministratore())
									      && !bloccoAVCPASS
									      && mostraModificaDatiPerfezionamento.equals("true")){ %>
										<li <%=noDispLotto %>><a href="gestisciLotto?action=modificaDatiPerfezionamento&idLotto=<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>">Modifica dati perfezionamento</a></li>
										<% actions = true; %>
									<% } %>
									<!-- MEV 3.04.10 43227  -->		
									
									<!-- MEV MEV 53643 3.04.13  -->
									<% 
    								String mostraModificaCPV = (String)request.getAttribute(ParametriServlet.MOSTRA_MODIFICA_CPV);
    								
									if ( user.isRSSAorRUP() 
									      && miagara
									      && !inLavorazione 
									      && !cancellato 
									      && !bloccoAVCPASS
									      && mostraModificaCPV.equals("true")){ %>
										<li <%=noDispLotto %>><a href="gestisciLotto?action=modificaCPV&idLotto=<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>">Modifica CPV</a></li>
										<% actions = true; %>
									<% } %>
									<!-- MEV 53643 3.04.13  -->			
									
									
									<!-- MAD 68089 3.04.16 Inizio -->
									<% 
    								String mostraModificaCategoriaSoa = (String)request.getAttribute(ParametriServlet.MOSTRA_MODIFICA_CAT_SOA);
    								
									if ( user.isAmministratore() 
									      && !inLavorazione 
									      && !cancellato 
									      && !bloccoAVCPASS
									      && mostraModificaCategoriaSoa.equals("true")){ %>
										<li <%=noDispLotto %>><a href="gestisciLotto?action=modificaCategoriaSOA&idLotto=<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>">Modifica Categoria SOA</a></li>
										<% actions = true; %>
									<% } %>
									<!-- MAD 68089 3.04.16 Fine -->				
									
									<% if ( cancellabile 
											&& (user.isAmministratore() || (user.isRSSAorRUP() && inLavorazione)) 
											&& (!lottoFromWS || user.isAmministratore())
											&& (miagara || user.isAmministratore())
											&& !bloccoAVCPASS) { %>
										<li <%=noDispLotto %>><a href="gestisciLotto?action=cancella&idLotto=<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>">Cancella</a></li>
										<% actions = true; %>
									<% } %>

									<% if (SimogFlags.is30230_RFWEBGL00Active()
									      && user.isAmministratore()
									      && !garaCanc
											&& cancellato 
											&& !bloccoAVCPASS) { %>
										<li <%=noDispLotto %>><a href="javascript:confirmAction('gestisciLotto?action=ripristina&idLotto=<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>');">Ripristina</a></li>
										<% actions = true; %>
									<% } %>
																
									
									<% if ( ! actions ) { %>
										<li>Nessuna azione consentita
										</li>
									<% } %>
										<% if (lottoFromWS){%>
										<li>&nbsp;(WS)</li>
									<% } %>
								</ul>
								</div><%-- hmenu --%>
							</div><%-- infoBlock --%>
						</div><%-- lotto --%>		
						</div><%-- elenco --%>
				<% 	} //if fineLotto %>
				<% 	previousLotto = currentLotto; %>
			    <% 	//previousRow = currentRow; %>
			<% 	} //for %>
			</div><%-- infoLotti --%>
			<%--------------------------------- END ELENCO LOTTI ------------------------------%>
<% if(SimogFlags.is30233_RFWEBGL05Active() && siLotti){ %>
		<div class="hmenu">
			<ul>	
			<% if ( startRowInt >  0 ) { %>
				<li><a href="<%= jspRicerca %>" title="Visualizza prima pagina">Inizio elenco</a></li>
			<% } 
			else {%> <li><a id="disabledMenu" title="Visualizza prima pagina">Inizio elenco</a></li> <% } %>
			
			<% if ( righeVisualizzate >  maxRigheVisualizzabili ) { %>
				<li><a href="<%= jspRicerca %>&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.REGRESS %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>" title="Visualizza Precedenti">Precedenti</a></li>
			<% }
			else {%> <li><a id="disabledMenu" title="Visualizza Precedenti">Precedenti</a></li> <% } %>
			
			<% if ( tableBeanSize - righeVisualizzate > 0 ) { %>
				<li><a href="<%= jspRicerca %>&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.PROGRESS %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>" title="Visualizza Successive">Successive</a></li>
			<% }
			else {%> <li><a id="disabledMenu" title="Visualizza Successive">Successive</a></li> <% } %>
			
			<% if ( righeVisualizzate != tableBeanSize ) { %>
				<li><a href="<%= jspRicerca %>&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.PROGRESS %>&<%= ParametriServlet.START_ROW %>=<%= fineElenco %>" title="Visualizza ultima pagina">Fine elenco</a></li>
			<% }
			else { %>
				<li><a id="disabledMenu" title="Visualizza ultima pagina">Fine elenco</a></li>
			<%}%>
			</ul>
		</div>
<% } %>

</div>

<% if(siRequisiti){ %>
<% 	String tabIndex = request.getAttribute(PSReq.CURRENT_TAB_INDEX) != null ? (String)request.getAttribute(PSReq.CURRENT_TAB_INDEX) : "0"; %>
<div class="tab-content">
	<h1 class="tab" title="Gestione dei requisiti per la partecipazione">Gestione dei requisiti</h1>
		<%@ include file="/scheda1/reqGara.jsp" %>
	</div>
	<script type="text/javascript" src="tabs/tabs.js"></script>
	<script>
		ActivateTab('tab-container', <%= tabIndex %>);
	</script>
<%} %>
	
		</div><%-- bodypage-e --%>
	</div><%-- bodypage --%>
<%@ include file="include/newfooter.inc" %>
</div><%-- gabbia --%>
</body>
<%@page import="it.avlp.simog.db.Costanti"%>
</html>
<% } catch (Exception e){e.printStackTrace();} %>