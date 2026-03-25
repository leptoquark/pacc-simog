
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ taglib prefix="h" uri="http://simog.avlp.it/tags-util"  %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.db.advanced.*"%>
<%@ page import= "it.avlp.simog.common.servlet.*"%>

<%try{ %>
 
<% TableBean listaTipologia = (TableBean)request.getAttribute(ParametriServlet.TIPOLOGIA_BEAN); %>

<% TableBean listaCategorie = (TableBean)request.getAttribute(ParametriServlet.CATEGORIA_BEAN); %>

<% TableBean listaDerogaQualificazioneSA = (TableBean)request.getAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_BEAN); %>

<% 
//gm in inserimento lotto tutti i campi sono editabili, quindi nessuno � disabled
boolean modificaContrattoEscluso = false;
String disabledTranneEscluso = "";
boolean modificaRipetiz = false;
String disabledTranneRipetiz = "";	
//MEV 37010 3.04.8.1
boolean integraPariOpportunita = false;
String disabledTrannePariOpportunita = "";
//MEV 37010 3.04.8.1
//MEV 37010 3.04.8.1
String isEreditati = (String)request.getAttribute(ParametriServletLotto.IS_EREDITATI);
String disabledEreditati = isEreditati.equals("true") ? "disabled" : "";
boolean modificaDatiCup = false;
String disabledTranneDatiCup = "";   
String fromRicerca = "";
/* MEV 53643 3.04.13 */
String disabledTranneCPV = ""; 
/* MAD 68089 3.04.16 Inizio */
String disabledTranneCatSoa = "";
/* MAD 68089 3.04.16 Fine */
%>

<title>SIMOG - Gestione gare - Inserimento Lotto</title>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<script type="text/javascript" src="script/funzioni.js"></script>
<script type="text/javascript" src="script/pageutils.js"></script>
<script type="text/javascript" src="script/scriptMotivoCollegamento.js"></script>
<script type="text/javascript" src="script/scriptSoggAggr.js"></script>
</head>

<body onload="checkDisable()"><%-- TICKET ALM - 3.04.3 #4202 --%>
<div id="gabbia">
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuGara.inc" %>


<% String oggettoGara = (String) session.getAttribute(ParametriServlet.FIELD_NAME_OGGETTO_GARA_1);
	Object motivazioniDeroga = (request.getAttribute(ParametriServlet.MOTIVO_DEROGA_TABLEBEAN)) != null ? request.getAttribute(ParametriServlet.MOTIVO_DEROGA_TABLEBEAN): null;
	Object selMotivazioniDeroga = (request.getAttribute(ParametriServlet.MOTIVO_DEROGA_SELECTED_TABLEBEAN)) != null ? request.getAttribute(ParametriServlet.MOTIVO_DEROGA_SELECTED_TABLEBEAN): null;
	%>

<form name="confermaLotto" action="InserisciLotto" method="post">
    <input type="hidden" name="checkIfOK" id="checkIfOK" value="<%=new Integer(session.getAttribute(ParametriServlet.checkIfOK).toString()) + 1%>" />
	<div id="bodypage">
		<div class="bodypage-e">

			<h1><utils:message key="lotto.creazioneNuovoLotto" /></h1>
			<%@ include file="include/gestisciErrore.inc" %>
			<%
			String idSaRiferimento = (String) session.getAttribute(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE_1);
			String descrSARiferimento = user.getDenominazioneUfficioById(idSaRiferimento);
			String amministrazioneCodiceFiscale = user.getCodiceFiscaleAmministrazioneByIdUfficio(idSaRiferimento);
			String amministrazioneDescrizione = user.getDenomAmministrazByCf(amministrazioneCodiceFiscale);
			String RSSA_CodiceFiscale = user.getLogin();
			String dataCreazioneGara = (String)session.getAttribute(ParametriServlet.SESSION_DATA_CREAZIONE_GARA_1);
			String idGara = (String)session.getAttribute(ParametriServlet.SESSION_ID_GARA);
			String idModReal = (String)session.getAttribute("IDMODREAL_PER_LOTTO");
			//TICKET MAC #10467
	   		String rupCreatoGara = (String)request.getAttribute(ParametriServlet.RUP_CREATO_GARA);
			%>			
				
		    <%-- Tasto Ritorna --%>
			<% String href = ParametriServlet.SRV_VISUALIZZA_DETTAGLIO + "?" + ParametriServlet.SESSION_ID_GARA + "=" + idGara
						+ "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI; %>
			<div class="hmenu">
				<ul><li><a title="<utils:message key="lotto.paginaPrecedente" plain="true" />" href="<%= href %>"><utils:message key="lotto.ritorna" /></a></li></ul>
			</div>
			<fieldset>
			<legend><utils:message key="lotto.datiGara" /></legend>
			<div>
				<table>
				<tr><td><utils:message key="lotto.numeroGara" />:</td><td><%=idGara %></td></tr>
				</table>
			</div>
			</fieldset>
			
			<fieldset>
			<legend><utils:message key="lotto.datiLotto" /></legend>
			<div>
				<h4>Lotto n� <%= session.getAttribute(ParametriServlet.SESSION_NUMERO_LOTTI_CREATI)%></h4>
				<%
					String oggettoLotto = (String) request.getAttribute(ParametriServlet.FIELD_NAME_OGGETTO_LOTTO);
				   if (oggettoLotto != null)
						oggettoLotto = oggettoLotto.replace("\"", "'");
					String sommaUrgenza = "";
					String checkedSommaUrgenza = request.getParameter(ParametriServlet.FIELD_NAME_SOMMA_URGENZA) != null ? "checked" : "";
					String importoLottoEuro = "";
					String importoLottoCentesimi = "00";
					String tipologiaSelezionata = ParametriServlet.TIPOLOGIA_LAVORI_PUBBLICI;
					String CPVSelezionata = request.getParameter(ParametriServlet.FIELD_NAME_CPV) != null ? request.getParameter(ParametriServlet.FIELD_NAME_CPV) : "";
					String contraenteSelezionata = (String)request.getAttribute(ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE) != null ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE) : (String)request.getParameter(ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE);

					//TICKET 31047
					String motivoDeroga = (String)request.getAttribute(ParametriServlet.FIELD_NAME_MOTIVO_DEROGA) != null ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_MOTIVO_DEROGA) : (String)request.getParameter(ParametriServlet.FIELD_NAME_MOTIVO_DEROGA);										

					//TICKET ALM - 3.04.3 #2846
					String motivoCollegamentoCig = (String)request.getAttribute(ParametriServlet.FIELD_NAME_MOTIVO) != null ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_MOTIVO) : (String)request.getParameter(ParametriServlet.FIELD_NAME_MOTIVO);										
					//FINE TICKET ALM - 3.04.3 #2846
					
					//TICKET ALM #4222 - 3.04.4
					String catSelezionata = (String)request.getAttribute(ParametriServlet.FIELD_NAME_CATEGORIA_LOTTO) != null ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_CATEGORIA_LOTTO) : (String)request.getParameter(ParametriServlet.FIELD_NAME_CATEGORIA_LOTTO);										
					//FINE TICKET ALM #4222 - 3.04.4
					
					//3.04.9 MEV 40610
					String derogaQualificazioneSASelezionata = (String)request.getAttribute(ParametriServlet.FIELD_NAME_DEROGA_QUALIICAZIONE_SA_LOTTO) != null ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_DEROGA_QUALIICAZIONE_SA_LOTTO) : (String)request.getParameter(ParametriServlet.FIELD_NAME_DEROGA_QUALIICAZIONE_SA_LOTTO);										
					//FINE 3.04.9 MEV 40610
					
					String idLotto = null;
					
					String selTipo = (request.getAttribute(ParametriServlet.FIELD_NAME_TIPO_CONTRATTO) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_TIPO_CONTRATTO): "";
					String selTipoPar = (request.getParameter(ParametriServlet.FIELD_NAME_TIPO_CONTRATTO) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_TIPO_CONTRATTO): "";
					if("".equals(selTipoPar)) selTipoPar = selTipo;

					String selSiNo = (request.getAttribute(ParametriServlet.FIELD_NAME_ESCLUSO) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_ESCLUSO): "";
					String selSiNoPar = (request.getParameter(ParametriServlet.FIELD_NAME_ESCLUSO) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_ESCLUSO): "";
					if("".equals(selSiNoPar)) selSiNoPar = selSiNo;
					
					// pp organi costituzionali
					if(request.getAttribute(ParametriServlet.IS_ORGANO)!= null){
						String organo = (String) request.getAttribute(ParametriServlet.IS_ORGANO);
						if(organo.equals(Costanti.FLAG_VALORE_SI) && "".equals(selSiNoPar))
							selSiNoPar = Costanti.FLAG_VALORE_NO;
					}

					//MEV 38202 3.04.8.1
					String selSiNoMet = (request.getParameter(ParametriServlet.FIELD_NAME_FLAG_USO_METODI_EDILIZIA) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_FLAG_USO_METODI_EDILIZIA): "";

					String idEsclusione = (request.getAttribute(ParametriServlet.FIELD_NAME_ID_ESCLUSIONE) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_ID_ESCLUSIONE): "";
					String idEsclusionePar = (request.getParameter(ParametriServlet.FIELD_NAME_ID_ESCLUSIONE) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_ID_ESCLUSIONE): "";
					if("".equals(idEsclusionePar)) idEsclusionePar = idEsclusione;
					String dataComun = (String) request.getAttribute(LOTTO.DATA_COMUNICAZIONE);
					
					String triennioAnnoInizio = "";
					String triennioAnnoFine = "";
					String triennioAnnoProgressivo = "";
					String annualeCuiMinInf = "";
					
					String luogoIstat = "";
					String luogoNuts = "";
					String importo_attuazione_sicurezza = "";

// PP B302.3.3
					String flagPrevRip = "";
					String flagRipetiz = "";
					String cigRipetiz = "";
					String flagRipetizPar = "";
					String cigRipetizPar = "";
					// Ticket #20058 - 09 - 02 - 21
					String durataRinnoviRipetizioni = "0";
					
					///durataRinnoviRipetizioni = (request.getAttribute(ParametriServlet.FIELD_NAME_DURATA_RINNOVI_RIPETIZIONI) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_DURATA_RINNOVI_RIPETIZIONI): "";
					String durataRinnoviRipetizioniPar = (request.getParameter(ParametriServlet.FIELD_NAME_DURATA_RINNOVI_RIPETIZIONI) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_DURATA_RINNOVI_RIPETIZIONI): "0";
					if(!durataRinnoviRipetizioniPar.equals("0")){
						
						durataRinnoviRipetizioni=durataRinnoviRipetizioniPar;
					}
					
					
					flagPrevRip = (request.getAttribute(ParametriServlet.FIELD_FLAG_PREVEDE_RIP) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_FLAG_PREVEDE_RIP): "";
					String flagPrevRipPar = (request.getParameter(ParametriServlet.FIELD_FLAG_PREVEDE_RIP) != null) ? (String)request.getParameter(ParametriServlet.FIELD_FLAG_PREVEDE_RIP): "";
					if(!"".equals(flagPrevRipPar)) flagPrevRip = flagPrevRipPar;
					
					 //TICKET ALM #2845
					 String flagDL50 = (request.getAttribute(ParametriServlet.FIELD_FLAG_DL50) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_FLAG_DL50): "";
					 String flagDL50par = (request.getParameter(ParametriServlet.FIELD_FLAG_DL50) != null) ? (String)request.getParameter(ParametriServlet.FIELD_FLAG_DL50): "";
					 if(!"".equals(flagDL50par)) flagDL50 = flagDL50par;
					 
					 String primaAnnualita = (request.getAttribute(ParametriServlet.FIELD_NAME_PRIMA_ANNUALITA) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_PRIMA_ANNUALITA): "";
					 String primaAnnualitaPar = (request.getParameter(ParametriServlet.FIELD_NAME_PRIMA_ANNUALITA) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_PRIMA_ANNUALITA): "";
					 if(!"".equals(primaAnnualitaPar)) primaAnnualita = primaAnnualitaPar;
					 //FINE TICKET ALM #2845
					 
					// Ticket #20057 - 09 - 02 - 21
						String durataAffidamentoInGiorni = "0";
						
					
						String durataAffidamentoInGiorniPar = (request.getParameter(ParametriServlet.FIELD_NAME_DURATA_AFFIDAMENTO_IN_GIORNI) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_DURATA_AFFIDAMENTO_IN_GIORNI): "0";
						if(!durataAffidamentoInGiorniPar.equals("0")){
							
							durataAffidamentoInGiorni=durataAffidamentoInGiorniPar;
						}
					
					 //TICKET ALM #3835
					 String idAffRiservati = (request.getAttribute(ParametriServlet.FIELD_NAME_AFF_RISERVATI) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_AFF_RISERVATI): "";
					 String idAffRiservatiPar = (request.getParameter(ParametriServlet.FIELD_NAME_AFF_RISERVATI) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_AFF_RISERVATI): "";
					 if(!"".equals(idAffRiservatiPar)) idAffRiservati = idAffRiservatiPar;
					 //FINE TICKET ALM #3835
					 
					
					 //TICKET ALM #3836
					 String flagRegime = (request.getAttribute(ParametriServlet.FIELD_FLAG_REGIME) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_FLAG_REGIME): "";
					 String flagRegimePar = (request.getParameter(ParametriServlet.FIELD_FLAG_REGIME) != null) ? (String)request.getParameter(ParametriServlet.FIELD_FLAG_REGIME): "";
					 if(!"".equals(flagRegimePar)) flagRegime = flagRegimePar;
					 
					 String idArtRegime = (request.getAttribute(ParametriServlet.FIELD_NAME_ART_REGIME) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_ART_REGIME): "";
					 String idArtRegimePar = (request.getParameter(ParametriServlet.FIELD_NAME_ART_REGIME) != null) ? (String)request.getParameter(ParametriServlet.FIELD_NAME_ART_REGIME): "";
					 if(!"".equals(idArtRegimePar)) idArtRegime = idArtRegimePar;
					 //FINE TICKET ALM #3836
					  
					flagRipetiz = (request.getAttribute(ParametriServlet.FIELD_FLAG_RIPETIZIONE) != null) ? (String)request.getAttribute(ParametriServlet.FIELD_FLAG_RIPETIZIONE) : "";
					flagRipetizPar = (request.getParameter(ParametriServlet.FIELD_FLAG_RIPETIZIONE) != null) ? (String) request.getParameter(ParametriServlet.FIELD_FLAG_RIPETIZIONE) : "";
					if(!"".equals(flagRipetizPar)) flagRipetiz = flagRipetizPar;	
					// UN 3.03.1
					String flagCup = "";
					if(SimogFlags.is3031_RFWEBGL02Active()){
					   flagCup = request.getParameter(ParametriCup.FIELD_FLAG_CUP) != null ? (String)request.getParameter(ParametriCup.FIELD_FLAG_CUP) : "";
					}	
					
					//TICKET #31047 : TICKET PARI OPPORTUNITA'
					
					

					String flagPnrrPnc = "";
					//MEV 34696 3.04.8
					//if(SimogProperties.getInstance().isDataCreatedAfter3047(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
						flagPnrrPnc = request.getAttribute(ParametriServlet.FLAG_PNRR_PNC) != null ? (String)request.getAttribute(ParametriServlet.FLAG_PNRR_PNC) : "";
					//}	
					
					//MEV 37010 3.04.8.1
					String flagDerogaAdesione = "";
					flagDerogaAdesione = request.getParameter(ParametriServlet.FLAG_DEROGA_ADESIONE) != null ? (String)request.getParameter(ParametriServlet.FLAG_DEROGA_ADESIONE) : "";
					//FINE MEV 37010 3.04.8.1
					
					//fix 40610
					String flagIsKo = "";
					flagIsKo = request.getParameter(ParametriServlet.FLAG_IS_KO) != null ? (String)request.getParameter(ParametriServlet.FLAG_IS_KO) : "";
					//
					
					String flagPrevisioneQuota = "";
					if(SimogProperties.getInstance().isDataCreatedAfter3047(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
						flagPrevisioneQuota = request.getAttribute(ParametriServlet.FLAG_PREVISIONE_QUOTA) != null ? (String)request.getAttribute(ParametriServlet.FLAG_PREVISIONE_QUOTA) : "";
					}	
					
					String flagMisurePremiali = "";
					if(SimogProperties.getInstance().isDataCreatedAfter3047(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
						flagMisurePremiali = request.getAttribute(ParametriServlet.FLAG_MISURE_PREMIALI) != null ? (String)request.getAttribute(ParametriServlet.FLAG_MISURE_PREMIALI) : "";
					}
					
					String quotaGiovanile = "";
					if(SimogProperties.getInstance().isDataCreatedAfter3047(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
						quotaGiovanile = request.getAttribute(ParametriServlet.QUOTA_GIOVANILE) != null ? request.getAttribute(ParametriServlet.QUOTA_GIOVANILE).toString() : "";
					}	
					
					String quotaFemminile = "";
					if(SimogProperties.getInstance().isDataCreatedAfter3047(PageHelper.getFormattedDBDate(dataCreazioneGara))) {
						quotaFemminile = request.getAttribute(ParametriServlet.QUOTA_FEMMINILE) != null ? request.getAttribute(ParametriServlet.QUOTA_FEMMINILE).toString() : "";
					}
					
					
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
					
					
					//TICKET ALM 13453 - 3.04.5
					String descCpvPrev = request.getParameter("cpvPrevDesc") != null ? request.getParameter("cpvPrevDesc") : "";
	                
					//TICKET ALM 13691 - 3.04.5
					String importo_opzioni = request.getParameter(ParametriServlet.FIELD_NAME_IMPORTO_OPZIONI) != null ? request.getParameter(ParametriServlet.FIELD_NAME_IMPORTO_OPZIONI) : "";
				
					
					String urgenzaDL133_session="";
					Integer selectedMotivo=(Integer)request.getAttribute(ParametriServlet.FIELD_NAME_MOTIVO_URGENZA);
					if (selectedMotivo==Costanti.TIPO_ESTREMA_URGENZA_PROTEZIONE_CIVILE){
						urgenzaDL133_session="S"; 			
					}
					
					//MEV 37010 3.04.8.1
					String mostraCampoDerogaAdesione = (String)request.getAttribute(ParametriServlet.MOSTRA_DEROGA_ADESIONE);
					
					//3.04.9 MEV 40610
					String mostraCampoDerogaQualificazioneSA = (String)request.getAttribute(ParametriServlet.MOSTRA_DEROGA_QUALIFICAZIONE_SA);
				%>
				
				<input type="hidden" id="urgenzaDL133_session"  value="<%=urgenzaDL133_session %>">		
			
				<%@ include file="include/lottoEdit.inc" %>
								
                 <!-- TICKET ALM #724 -->
			     <input type="hidden" name="<%= ParametriServlet.SESSION_ID_GARA+"_form" %>" id="idGara" value="<%= idGara %>">
				 <!-- Fine Ticket ALM #724 -->
				 
				<h5>Categoria prevalente o scorporabile</h5>
				<%@ include file="include/elencoCategoriaScorporabilePerSelezione.inc" %>
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
			String prevFromReqDQSA = request.getParameter(PSBD.FIELD_NAME_DEROGA_QUALIFICAZIONE_SA);%>
			<% for ( i = 0; i < listaDerogaQualificazioneSA.getTableSize(); i++ ) { %>
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
	
	<table cellpadding="3" style="display: none;">
				<tbody>
				<tr id="tr_flagIsKo">
					<th align="left" width="50%"><label
						<%=SimogFlags.checkHighlightField(fieldToHighlight, "label_FlagIsKo")%>
						for="<%=ParametriServlet.FLAG_IS_KO%>">is qualificata ko</label></th>
					<td><c:set var="selFlagIsKo" value="<%=flagIsKo%>" scope="request"/> 
							<select id= "flagIS_KO"
							name="<%=ParametriServlet.FLAG_IS_KO%>" CLASS="BOTTONE" > 
								<option value=""></option>
								<option value="N" <c:out value="${selFlagIsKo =='N' ? 'selected' : ''}" /> >NO</option> 
								<option value="S" <c:out value="${selFlagIsKo =='S' ? 'selected' : ''}" /> >SI</option> 
						</select>
					</td>
				</tr>
				</tbody>
				</table>
	<%
			}
			%>
			<!-- fine 3.04.9 MEV 40610 -->
			</div>
			</fieldset>
			<br/>
			<!-- colonna destra -->
			<div align="right">
				<input type="button" value="<utils:message key="button.salva" plain="true" />" onclick="doCallSoggAggrLotto(null,<%= idGara %>, <%= user.isAmministratore() %> ,<%= SimogProperties.getInstance().isDataCreatedAfter3044(PageHelper.getFormattedDBDate(dataCreazioneGara))%>)">
			</div>
		</div>
	</div>
</form>	
<%@ include file="include/newfooter.inc" %>
</body>
<%@page import="it.avlp.simog.util.SimogProperties"%>
<%@page import="it.avlp.simog.db.Costanti"%>
</html>
<% } catch (Exception e ){e.printStackTrace();}%>