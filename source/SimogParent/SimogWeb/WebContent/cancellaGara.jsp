<% try{ %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="it.avlp.simog.beans.StatiScheda"%>
<%@ page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@ page import="it.avlp.simog.common.servlet.ParametriServletLotto"%>
<%@ page import="it.avlp.simog.db.Costanti"%>
<%@ page import="it.avlp.simog.common.servlet.PSReq"%>
<%@ taglib prefix="x" uri="http://simog.avlp.it/tags-util"  %>

<head>
<!-- TB: ticket popup modali. Import css e js -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<!-- fine import popup modali -->

	<title>SIMOG - <utils:message key="visualizza.gestioneGare" /></title>
</head>

<% TableBean listaGare = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); %>

<%-- Verifica Gara Confermata --%>
<% boolean Confermato = StatiScheda.CONFERMATO_STRING.equals(listaGare.getRow(0).getNulledField(GARA.ID_STATO)); %>
<% String garaPubblicabile = "falso"; //Per garaVisual.inc %>
<% boolean bloccoAVCPASS = false; //Per garaVisual.inc %> 
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
<form name="eseguiCancella" action="cancellaGara" method="post">
		
				<h1><utils:message key="lotto.cancellazioneGara" /></h1>
				<%@ include file="include/gestisciErrore.inc" %>
			<div class="hmenu">
			<ul>
				<li><a title="<utils:message key="dettaglio.paginaPrecedente" />" href="<%=ParametriServlet.SRV_VISUALIZZA_DETTAGLIO %>?<%=ParametriServlet.SESSION_ID_GARA%>=<%=listaGare.getRow(0).getNulledField(LOTTO.ID_GARA) %>"><utils:message key="lotto.ritorna" /></a></li>
			</ul>
			</div>

		<div class="testo">
	<%	int rowIndex = 0;
		TableBeanRow currentRow = listaGare.getRow(rowIndex);
		String codiceGara = currentRow.getNulledField(GARA.ID_GARA);

		String oggettoGara = PageHelper.formattaTesto(currentRow.getNulledField(GARA.TABLE_NAME + GARA.OGGETTO));
		String numeroLotti = currentRow.getNulledField(GARA.NUMERO_LOTTI);
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
		String cigQuadro =  currentRow.getNulledField( GARA.CIG_ACC_QUADRO );

		String garaDataCancellazione = currentRow.getNulledField(GARA.DATA_CANCELLAZIONE_GARA);
		String garaDataTerminePagam = currentRow.getNulledField(GARA.DATA_TERMINE_PAGAMENTO);
		String garaDataInibPagam = currentRow.getNulledField(GARA.DATA_INIB_PAGAM);
		String garaDataConferma = currentRow.getNulledField(GARA.DATA_CONFERMA_GARA);
		String garaDataComun = currentRow.getNulledField(GARA.DATA_COMUN);
		String garaDataPerfezionamento = currentRow.getNulledField(GARA.DATA_PERFEZIONAMENTO_BANDO);

		String tipoScheda = currentRow.getNulledField(TIPI_CATEGORIA.TABLE_NAME);
		String modoGara = currentRow.getNulledField(MODO_INDIZIONE.TABLE_NAME);
		String modoReal = currentRow.getNulledField(MODI_REALIZZAZIONE.TABLE_NAME);
		
		//TICKET ALM #664
		String strumentoSvolgimento = currentRow.getNulledField(STRUMENTI_SVOLGIMENTO_PROCEDURE.TABLE_NAME);
		//FINE TICKET ALM #664

		//TICKET ALM #3832
		String estremaUrgenza = currentRow.getNulledField(ART_ESTREMA_URGENZA_SOMMA_URGENZA.TABLE_NAME);
		//FINE TICKET ALM #3832
		
		//TICKET ALM #3834
		String modIndAllegatoIX = currentRow.getNulledField(MODALITA_INDIZIONE_ALLEGATO_IX.TABLE_NAME);
		//FINE TICKET ALM #3834
		
		String descMotivGara = currentRow.getNulledField("G_" + MOTIVI_CANCELLAZIONE.DESCRIZIONE);
		String noteCancGara = PageHelper.formattaTesto(currentRow.getNulledField(GARA.NOTE_CANC_GARA));
		String id_motivazioneGara = currentRow.getNulledField(GARA.ID_MOTIVAZIONE_CANC);

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
		
		//TICKET ALM - 3.04.3
        String durataGiorni = currentRow.getNulledField(GARA.DURATA_GIORNI);
        //FINE TICKET ALM - 3.04.3
		
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
				
	<input type="hidden" name="idLotto" value="<%= currentRow.getNulledField(LOTTO.ID_LOTTO) %>">
	<input type="hidden" name = "<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>" value="<%=currentRow.getNulledField(GARA.ID_STAZIONE_APPALTANTE)%>"/>
	<input type="hidden" name = "<%= ParametriServlet.FIELD_NAME_CIG %>" value="<%=currentRow.getNulledField(LOTTO.CIG)%><%=currentRow.getNulledField(LOTTO.CIG_KKK)%>"/>
	<input type="hidden" name = "cfAmministrazione" value="<%= currentRow.getNulledField(GARA.CF_AMMINISTRAZIONE) %>"/>
	<input type="hidden" name = "<%= ParametriServlet.FIELD_NAME_ID_GARA %>" value="<%= currentRow.getNulledField(LOTTO.ID_GARA) %>"/>		

	<h4>Informazioni sulla Gara</h4>
	
	<%@ include file="include/garaVisual.inc" %>
   
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
			<% if ( (user.isAmministratore() || user.isRSSAorRUP())) { %>
			<li><input type="submit" value="Conferma la cancellazione"></li>
			<% } %>
			</ul>
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