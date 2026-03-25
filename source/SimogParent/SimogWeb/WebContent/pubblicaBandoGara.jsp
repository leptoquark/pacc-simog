<%@page import="it.avlp.simog.beans.cup.CupLottoAggExt"%>
<%@page import="java.util.List"%>
<%@page import="it.avlp.simog.beans.PubblicazioneBean"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.db.SimogFlags" %>
<%@ page import="it.avlp.simog.beans.CIGBean"%>
<%@ page import="it.avlp.simog.beans.StatiScheda"%>
<%@ page import="java.math.BigDecimal"%>
<%@page import="java.util.HashMap"%>
<%@page import="it.avlp.simog.db.Costanti"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletLotto"%>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ taglib prefix="h" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% int indiceTab = 0;%>

<% try{ %>

<!-- calendar stylesheet -->
<link rel="stylesheet" type="text/css" media="all" href="calendar/calendar-blue.css" title="win2k-cold-1" />

<!-- main calendar program -->
<script type="text/javascript" src="calendar/calendar.js"></script>

<!-- language for the calendar -->
<%@ include file="include/calendar-dynamic.inc" %>

<script type="text/javascript" src="xtree/treeutils.js"></script>

<!-- the following script defines the Calendar.setup helper function, which makes
     adding a calendar a matter of 1 or 2 lines of code. -->
<script type="text/javascript" src="calendar/calendar-setup.js"></script>
<%@ include file="include/i18n-init.inc" %>
<script type="text/javascript" src="script/pageutils.js"></script>
<% String currentDate = PageHelper.getCurrentDate(); %>

<%


MessageBean messBean = (it.avlp.simog.beans.MessageBean) request.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.ERRORBEAN);
it.avlp.simog.beans.AllValidationBeans beanErr = null;
HashMap<String, String> fieldToHighlight = new HashMap<String,String>();
if ( messBean != null ) 
if ( messBean instanceof it.avlp.simog.beans.AllValidationBeans ){
beanErr = (it.avlp.simog.beans.AllValidationBeans) request.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.ERRORBEAN);
fieldToHighlight = beanErr.getFieldToHighlight();
}
//fix 40610
String flagIsKo = "";
flagIsKo = request.getParameter(ParametriServlet.FLAG_IS_KO) != null ? (String)request.getParameter(ParametriServlet.FLAG_IS_KO) : "";
//
%>

<%! 
   //metodo per tenere memoria dei lotti selezionati per la cancellazione
   private boolean isCheckedLotto (String idLotto, String [] lotti){
	    boolean result=false;
	    if(lotti!=null && lotti.length>0){
	    	for(int i=0; i<lotti.length; i++){
	    		if (idLotto.equals(lotti[i]))
	    			result=true;
	    	}
	   }
	   return result;
   }
%>

<title>SIMOG - Pubblica Gara</title>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">

</head>
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript">
//mev 3.04.10 43345
$(document).ready(function () {
	$(".derogaRow11").hide();
	if (document.querySelectorAll('#TAB_MESSAGGI_ERRORE li').length >1){
		if ($('.elencoQualificazioneSA input').is(':checked')) {
			$('.elencoQualificazioneSA input').attr("disabled", false);
			if ($('.derogaRow11 input').is(':checked')) {
				$('.elencoQualificazioneSA input').attr("disabled", false);
				$(".derogaRow11").show();
				$("#flagIS_KO").val("S");
			}
			
			
		}else {
			for (let li of document.querySelectorAll('#TAB_MESSAGGI_ERRORE li')){
				
					if (li.textContent.indexOf('SIMOG_LOTTO_040') != -1){
						
						
						
						$('.elencoQualificazioneSA input').attr("disabled", false); 
					
					}
					if (li.textContent.indexOf('SIMOG_LOTTO_041') != -1){
						
						$(".derogaRow11").show();
						$("#flagIS_KO").val("S");
						$('.elencoQualificazioneSA input').attr("disabled", false); 
					
					}
				
			}
		}
	 }
	 else {
		 if ($('.elencoQualificazioneSA input').is(':checked')) {
				$('.elencoQualificazioneSA input').attr("disabled", false);
				if ($('.derogaRow11 input').is(':checked')) {
					$('.elencoQualificazioneSA input').attr("disabled", false);
					$(".derogaRow11").show();
					$("#flagIS_KO").val("S");
				}
				
		}else {
			 	for (let li of document.querySelectorAll('#TAB_MESSAGGI_ERRORE li')){
			 		if (li.textContent.indexOf('SIMOG_LOTTO_040') != -1){
			 			console.log("pop-up 2");
			 			
			 			$('.elencoQualificazioneSA input').attr("disabled", false); 
			 			
					}

			 		if (li.textContent.indexOf('SIMOG_LOTTO_041') != -1){
			 			$(".derogaRow11").show();
			 			$("#flagIS_KO").val("S");
			 			$('.elencoQualificazioneSA input').attr("disabled", false); 
			 		}
				}
		}
	 }
});  
//fine mev 3.04.10 43345

    var impGaraLoc;
	var tipoPubblicazioneLoc;
	var numeroLottiPerfezionatiODaPerfezionareLoc;
	var popupIsClose = 'false';
    
	function checkMessaggio(){
		<%
			String mesg = (String) request.getAttribute(ParametriServlet.MESSAGGIO);
			if (mesg != null && !"".equals(mesg)) {
				String escapedMesg = mesg
					.replace("\\", "\\\\")
					.replace("\"", "\\\"")
					.replace("\r", "\\r")
					.replace("\n", "\\n");
		%>
			alert("<%= escapedMesg %>");
		<% } %>
	}

	function doActionModifica(action){
		if(!hasErrors(document.forms[0]))
			doAction(action);
	}

	function isPubblicazione(){
		// ciclare sui cig attivi no checked
		//gm gestisce l'alert di pubblicazione/perfezionamento in base al risultato
		var beni_culturali = document.getElementById("<%= ParametriServlet.S_FIELD_NAME_FLAG_BENICULT %>");
		var temp = document.forms[0].id_lotti_cancellare;
		var retVal = false;
		var count = 0;
		
		// non ci sono lotti non ammessa pubblicazione
		if(!temp) return false;
		
		// PP organi costituzionali
		<%
		String organo = "";
		if(request.getAttribute(ParametriServlet.IS_ORGANO)!= null){
			organo = (String) request.getAttribute(ParametriServlet.IS_ORGANO);
		}
		%>
		var isOrgano = "<%= organo %>";
		if(isOrgano == "<%=Costanti.FLAG_VALORE_SI %>" ) return false;
		
		var num = temp.length;
		if(!num){
			// esiste un solo lotto
			// bah if(!document.forms[0].id_scelta.value){retVal=true;}
				//TICKET ALM #3922-06.1 e 06.2
			if(document.forms[0].id_scelta.value == "<%= Costanti.PROC_APE %>"
	               || document.forms[0].id_scelta.value == "<%= Costanti.PROC_RIS %>"
	               || document.forms[0].id_scelta.value == "<%= Costanti.DIA_COMP %>"
	               || document.forms[0].id_scelta.value == "<%= Costanti.PROC_NEG_PP %>"
	            	|| (document.forms[0].id_scelta.value == "<%= Costanti.PROC_NEG_NO_PP %>" && document.getElementById("<%= ParametriServlet.S_FIELD_NAME_FLAG_BENICULT %>") != null && document.getElementById("<%= ParametriServlet.S_FIELD_NAME_FLAG_BENICULT %>").checked)
		            ){
	               console.log("TECHNIS 1: document.forms[0].id_scelta.value: "+document.forms[0].id_scelta.value);
	               retVal = true;
		    }
          // unico lotto cancellato
          if(temp.checked) retVal = false;
		}	
		//se ho un elenco di lotti in lavorazione
		else if(num>0){		    
		    for (i = 0; i < num; i++) {
		        if (!temp[i].checked) {
		        // controlli su scelta contraente (creare un nuovo set di hidden parallelo ai checkbox)
		        //TICKET ALM #3922-06.1 e 06.2
		            if(document.forms[0].id_scelta[i].value == "<%= Costanti.PROC_APE %>"
		               || document.forms[0].id_scelta[i].value == "<%= Costanti.PROC_RIS %>"
		               || document.forms[0].id_scelta[i].value == "<%= Costanti.DIA_COMP %>"
		               || document.forms[0].id_scelta[i].value == "<%= Costanti.PROC_NEG_PP %>"
			            || (document.forms[0].id_scelta[i].value == "<%= Costanti.PROC_NEG_NO_PP %>" && document.getElementById("<%= ParametriServlet.S_FIELD_NAME_FLAG_BENICULT %>") != null && document.getElementById("<%= ParametriServlet.S_FIELD_NAME_FLAG_BENICULT %>").checked)
			            ){
		            	console.log("TECHNIS 2: document.forms[0].id_scelta.value: "+document.forms[0].id_scelta[i].value);
		               retVal = true;
		        }}
		        else{count = count +1;}
		    }
		    //se tutti i lotti sono cancellati non effettuo la pubblicazione
		    if(num==count){retVal=false;}    
	   }
		//se non ho nessun lotto non effettuo la pubblicazione
		else if (num==0){retVal=false;}
		return retVal;
	}


	/* MEV 25895 POP-UP */
	//PARTE CHE APRE LA POPUP INFORMATIVA
	function apripopupPubblicaBandoGara(path){
		var dialogArgs = {};
		dialogArgs.Sender = window;
		
		//TB: Ticket risoluzione popup
		if (!window.showModalDialog) {
			return opendialogPubblicaBandoGara(path);
		} 			
			  
	}

	/* MEV 25895 POP-UP */
function opendialogPubblicaBandoGara(page, idDialog) {
      var divDialog = '#dialog';
      if(idDialog)
          divDialog = '#dialog'+idDialog;
                 
	  var $dialog = $(divDialog)
	  .html('<iframe style="border: 0px; " src="' + page + '" width="100%" height="100%"></iframe>')
	  .dialog({
	    title: "ANAC: Autorit&agrave; Nazionale Anticorruzione",
	    autoOpen: false,
	    dialogClass: 'no-close',
	    modal: true,
	    height: 550,
	    width: 800,
	    draggable:true,
	    buttons: {
	        Ok: function() {
	          $( this ).dialog( "close" );
	        }
	     },
	    close:function(){  
	       dialog_confirm_callback('true', '', '', '','','');
	    }
	  });
	  $dialog.dialog('open');

	  $('.ui-button').removeClass( "ui-widget" );
	    	  
	}
		
	function dialog_confirm_callback(value) {
	  if (value === 'true') 
	  {	    
	    popupIsClose = value;
	  } 
	  else if(value === 'false') 
	  {
	    popupIsClose = value;
	  }
	  
	  checkAndGo(impGaraLoc,tipoPubblicazioneLoc,numeroLottiPerfezionatiODaPerfezionareLoc);
	}
	
	
		
	function checkAndGo(impGara, tipoPubblicazione, numeroLottiPerfezionatiODaPerfezionare){
		impGaraLoc = impGara;
		tipoPubblicazioneLoc = tipoPubblicazione;
		numeroLottiPerfezionatiODaPerfezionareLoc = numeroLottiPerfezionatiODaPerfezionare;

		var isRup1 = "<%=user.isRUP()%>";
		if (isRup1 === 'true' && popupIsClose === 'false') {
			apripopupPubblicaBandoGara('popupPubblicaBandoGara.jsp');
		}

		if (popupIsClose === 'true' || isRup1 === 'false') {
			var dacanc = document.forms[0].id_lotti_cancellare;
			var ok = true;

			// Se tutti i lotti sono cancellati e' impossibile proseguire.
			if (dacanc) {
				var num = dacanc.length;
				if (!num) {
					num = 1;
				}

				var numDaCanc = 0;
				for (var i = 0; i < num; i++) {
					if (num > 1 && dacanc[i].checked) {
						numDaCanc = numDaCanc + 1;
					}
					if (num === 1 && dacanc.checked) {
						numDaCanc = numDaCanc + 1;
					}
				}

				if (numDaCanc >= numeroLottiPerfezionatiODaPerfezionare) {
					ok = false;
				}

				if (!ok) {
					if (typeof i18n !== 'undefined' && i18n.alert) {
						i18n.alert('error.lottoRequired');
					} else {
						alert("Almeno un lotto deve essere incluso nella pubblicazione");
					}
					return false;
				}
			}

			var p = isPubblicazione();
			if (
				p === true &&
				impGara >= <%=Costanti.IMPORTO_LOTTO_500000%> &&
				(
					document.getElementById("inputGazzettaRI").value === "" ||
					document.getElementById("numeroGuri").value === "" ||
					document.getElementById("linkSitoCommittente").value === ""
				)
			) {
				var msg = "ATTENZIONE: a norma dell'art. 36 comma 9, e' prevista la pubblicazione sulla GURI e sul profilo del committente. Proseguire comunque?";
				if (confirm(msg)) {
					if (tipoPubblicazione === 'pubblicazione') {
						doAction('salvaBandoGara');
					}
					if (tipoPubblicazione === 'pubblicazioneAvviso') {
						doAction('salvaAvviso');
					}
				} else {
					return false;
				}
			} else {
				if (tipoPubblicazione === 'pubblicazione') {
					doAction('salvaBandoGara');
				}
				if (tipoPubblicazione === 'pubblicazioneAvviso') {
					doAction('salvaAvviso');
				}
			}
		}

		popupIsClose = 'false';
	}
	
	function alertInvito(url, campo, campoPres){

		var isPres = document.getElementById(campoPres);
		if(isPres)
		{
			if(isPres.value == ""){
				if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('error.invitationLetterWarning'); } else { alert("Attenzione! Le lettere d'invito ai sensi dell'art. 204 del Codice devono essere acquisite prive d'indicazioni relative ai soggetti invitati."); }
			}
			apripopupAllegati(url, campo);
		}
	}
	
	function confirmCup(prfx_valido, prfx_okutente)
	{
// 		$("td[id^='row'][id$='" + prfx_valido + "']:contains('Si')").each(function(idx, item){
// 			var pos = item.getAttribute("tabindex");
// 			$("input[name='hiddenrowCUP" + pos + prfx_okutente + "']").attr("value","S");
// 			$("td[id='row" + pos + prfx_okutente + "']").html("Si");
// 			$("td[id='row" + pos + prfx_okutente + "']").css("background-color","#BBFFBB");
// 		});	
		doAction('<%= ParametriCup.ACTION_COFERMA_CUP %>');
	}
</script>
<body>
<% boolean cancellabile = false; 
	boolean cancellato = false; 
	boolean scaduto = false; 
	boolean pagabile = false;
	boolean inLavorazione = false;
	TableBean listaGare = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); 
	//TableBean listaCategorieScorporabili = (TableBean)request.getAttribute(ParametriServlet.CATEGORIA_BEAN); 
	TableBeanRow currentRow = null;
	TableBeanRow nextRow = null;
    //TableBeanRow previousRow = null; 
	String previousLotto = null;
	String nextLotto = null;
   String currentLotto = null; 
	String lottoImportoImpresa = null;
	String lottoImportoSA = null;
	boolean actions = false;
	String tipoPubblicazione = request.getParameter(ParametriServlet.TIPO_PUBBLICAZIONE); 
	String idAggiudicazione = request.getParameter(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE); 
	String datainizioAggiudicazione = request.getParameter(PSBD.FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE); 
   //gm nuovo settaggio simog 3.04
	int numeroLottiPerfezionatiODaPerfezionare = 0;
   
	////MEV 43345 3.04.10
	TableBean listaDerogaQualificazioneSA = (TableBean)request.getAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_BEAN);
	String derogaQualificazioneSASelezionata = (String)request.getAttribute(ParametriServlet.FIELD_NAME_DEROGA_QUALIICAZIONE_SA_LOTTO) != null ? (String)request.getAttribute(ParametriServlet.FIELD_NAME_DEROGA_QUALIICAZIONE_SA_LOTTO) : (String)request.getParameter(ParametriServlet.FIELD_NAME_DEROGA_QUALIICAZIONE_SA_LOTTO);										
	String mostraCampoDerogaQualificazioneSA = (String)request.getAttribute(ParametriServlet.MOSTRA_DEROGA_QUALIFICAZIONE_SA);
	//FINE MEV 43345 3.04.10

	
%>

<!-- TB: ticket popup modali. Import css e js -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<div id="dialog"></div> <%-- TICKET ALM #4196 --%>
<div id="dialogALLEGATO1"></div>
<div id="dialogALLEGATO2"></div>
<div id="dialogALLEGATO3"></div>
<div id="dialogALLEGATO_AVVISO_AGGIUDICAZIONE"></div>
<!-- fine import popup modali -->

<div id="gabbia">
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuGara.inc" %>
	
	<div id="bodypage">
		<div class="bodypage-e">
			<%if (ParametriServlet.PUBBLICAZIONE.equals(tipoPubblicazione)){ %>	    
			<h1><utils:message key="pubblicazione.perfezionamentoPubblicazioneGara" /></h1>
			<% } %>
			<%if (ParametriServlet.PUBBLICAZIONE_AVVISO.equals(tipoPubblicazione)){ %>	    
			<h1><utils:message key="pubblicazione.pubblicazioneAvvisoAggiudicazione" /></h1>
			<% } %>
				<%@ include file="include/gestisciErrore.inc" %>
			<div class="hmenu">
			   <%if (ParametriServlet.PUBBLICAZIONE.equals(tipoPubblicazione)){ %>	    
				<ul><li><a title="<utils:message key="dettaglio.paginaPrecedente" plain="true" />" href="<%=ParametriServlet.SRV_VISUALIZZA_DETTAGLIO%>
				?<%=ParametriServlet.SESSION_ID_GARA%>=<%=listaGare.getRow(0).getNulledField(LOTTO.ID_GARA) %>
				&<%=ParametriServlet.FROM_GARE %>=<%=Costanti.FLAG_VALORE_SI %>"><utils:message key="lotto.ritorna" /></a></li></ul>
				<% } %>
				<%if (ParametriServlet.PUBBLICAZIONE_AVVISO.equals(tipoPubblicazione)){ %>	    
				<ul><li><a title="<utils:message key="dettaglio.paginaPrecedente" plain="true" />" href="<%=ParametriServlet.SRV_SCHEDA_A%>
				?<%=PSBD.FIELD_NAME_ID_AGGIUDICAZIONE%>=<%=idAggiudicazione %>
				&<%=PSBD.FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE %>=<%=datainizioAggiudicazione %>"><utils:message key="lotto.ritorna" /></a></li></ul>
				<% } %>
			</div><%-- hmenu --%>
			
			<%	int rowIndex = 0;
				currentRow = listaGare.getRow(rowIndex);
				String codiceGara = currentRow.getNulledField(GARA.ID_GARA);
				String oggettoGara = PageHelper.formattaTesto(currentRow.getNulledField(GARA.TABLE_NAME + GARA.OGGETTO));
				String dataCreazioneGaraStr = currentRow.getNulledField(GARA.DATA_CREAZIONE)  ;	
				String dataCreazioneGara = PageHelper.getFormattedDate( currentRow.getNulledField(GARA.DATA_CREAZIONE) ) ;			
				String numeroLotti = currentRow.getNulledField(GARA.NUMERO_LOTTI);
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
				if(PageHelper.IMPORTO_ND.equals(importoSAGara)){
					importoSAGara = "Il valore sara' calcolato ad esito della conferma dei dati";
				}				
				//UN Se non ci sono lotti non visualizziamo le info sui lotti
				boolean nienteLotti = "0".equals(currentRow.getNulledField(LOTTO.ID_LOTTO));
				String display = nienteLotti ? "display:none" : "display:block";	
			%>
			<h4>Informazioni Gara</h4>
				<div class="gara">
					<table>
						<tr>
							<th class="garaTh" width="40%">Numero Gara</th>
							<td class="garaTd"><%= codiceGara %></td>
						</tr>				
						<tr>
							<th class="garaTh" width="40%">Oggetto della Gara</th>
							<td class="garaTd"><%= oggettoGara %></td>
						</tr>
						<tr>
							<th class="garaTh" width="40%">Importo della Gara &euro;</th>
							<td class="garaTd"><%= importoGara %></td>
						</tr>
						<tr>
							<th class="garaTh" width="40%">Numero totale dei Lotti</th>
							<td class="garaTd"><%= numeroLotti %></td>
						</tr>
						<tr>
							<th class="garaTh" width="40%">Stato gara</th>
							<td class="garaTd"><%= currentRow.getNulledField( STATI_SCHEDA.DESCRIZIONE )%></td>
						</tr>
					</table>	
		      </div><%-- gara --%>	
		<form method="post" action="<%=ParametriServlet.SRV_BANDO_GARA %>" onsubmit="return controllaData()">
		<input type="hidden" name="<%= ParametriServlet.SESSION_ID_GARA%>" value="<%= codiceGara%>" />	
	   <input type="hidden" name="<%= ParametriServlet.TIPO_PUBBLICAZIONE%>" value="<%= tipoPubblicazione%>" />	
	   <input type="hidden" name="<%= PSBD.FIELD_NAME_ID_AGGIUDICAZIONE%>" value="<%= idAggiudicazione%>" />	
	   <input type="hidden" name="<%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE%>" value="<%= datainizioAggiudicazione%>" />	
		
		<%if (ParametriServlet.PUBBLICAZIONE.equals(tipoPubblicazione)){ %>
			<%--------------------------------- BEGIN ELENCO LOTTI -----------------------------%>		
			<div id="infoLotti" style="<%= display %>">
				<h4>Informazioni sui Lotti componenti</h4>	
				  <%-- <div class="elenco">
					<div class="lotto">
					--%>
					<table width="100%">	
					<tr>
						<th class="garaTh">Cancella</th>
						<th class="garaTh">Lotto CIG</th>
						<th class="garaTh">Oggetto</th>
						<th class="garaTh">Importo</th>
						<td class="garaTh">Stato Lotto</td>
					</tr>		
			<% 	String [] lottiDaCancellare = request.getParameterValues(ParametriServletLotto.FIELD_NAME_LOTTI_CANCELLARE);	
			      for ( rowIndex = 0; rowIndex < listaGare.getTableSize(); rowIndex++ ) { 
					currentRow = listaGare.getRow(rowIndex); 
					currentLotto = currentRow.getNulledField(LOTTO.ID_LOTTO);
					boolean nuovoLotto = ! currentLotto.equalsIgnoreCase( previousLotto );				
					/* Calcolo di fineLotto */
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
						String lottoOggetto = PageHelper.formattaTesto(currentRow.getNulledField(LOTTO.TABLE_NAME + LOTTO.OGGETTO));
						String unformattedImporto =  currentRow.getNulledField(LOTTO.IMPORTO_LOTTO);
						String lottoImporto = PageHelper.IMPORTO_ND;
						if(!"".equals(unformattedImporto) && new BigDecimal(unformattedImporto).compareTo(new BigDecimal(Costanti.IMPORTO_FUORI_SCALA)) != 0){
							lottoImporto = PageHelper.getFormattedImporto(unformattedImporto);
						}				
						%>		
						<% boolean isCancellato = ! "".equals( currentRow.getNulledField( LOTTO.DATA_CANCELLAZIONE_LOTTO ) ) || ! "".equals( currentRow.getNulledField( LOTTO.DATA_INIB_PAGAMENTO ) ); %>
						<% String statoLotto = ! "".equalsIgnoreCase( currentRow.getNulledField( LOTTO.DATA_PUBBLICAZIONE ) ) ? "PERFEZIONATO" : "IN LAVORAZIONE"; %>
						<% statoLotto = isCancellato ? "CANCELLATO" : statoLotto; %>	
						<% String dataPubblicazione = currentRow.getNulledField(LOTTO.DATA_PUBBLICAZIONE); %>
                  <%-- gm nuovo codice pubblicazione bando 3.0 --%>
                  <% // String lottoSceltaContraente = (SimogFlags.is3028_RFWEBGL00Active() ? currentRow.getNulledField(CONTRAENTE_REGIONE.ID_EQUIVALENTE) : currentRow.getNulledField(LOTTO.ID_SCELTA_CONTRAENTE)); %>
                  <% String lottoSceltaContraente = currentRow.getNulledField(LOTTO.ID_SCELTA_CONTRAENTE); %>
						<% String lottoImportoNumerico = currentRow.getNulledField(LOTTO.IMPORTO_LOTTO); %>
						<% String tipoContratto = currentRow.getNulledField(LOTTO.TIPO_CONTRATTO_LOTTO); %>
						<% //gm nuovo settaggio simog 3.04
						   if (!isCancellato){numeroLottiPerfezionatiODaPerfezionare ++;} %>				
						<tr>
						   <% //mostro le checkbox solo per i lotti in lavorazione
						      if (!isCancellato && "".equals(dataPubblicazione)) {%>
						      <input type="hidden" name="id_scelta" value="<%=lottoSceltaContraente %>"/>	
						       <%if (isCheckedLotto(currentLotto,lottiDaCancellare)) {%>
						         <td  class="garaTd"><input type="checkbox" name="<%= ParametriServletLotto.FIELD_NAME_LOTTI_CANCELLARE %>" value="<%= currentRow.getNulledField(LOTTO.ID_LOTTO)%>" checked /></td>					
						       <%} else {%>
						         <td  class="garaTd"><input type="checkbox" name="<%= ParametriServletLotto.FIELD_NAME_LOTTI_CANCELLARE %>" value="<%= currentRow.getNulledField(LOTTO.ID_LOTTO)%>" /></td>						
						       <%} 
						      } else {%>
						         <td class="garaTd">Non cancellabile</td>			         
						   <% } %>
						   <td  class="garaTd"><%= currentCIG %></td>
						   <td  class="garaTd"><%= lottoOggetto %></td>
						   <td  nowrap class="garaTd"><%= lottoImporto %></td>
						   <td  class="garaTd"><strong><%= statoLotto %></strong></td>
						</tr>
				
			   <%-- </div> lotto --%>		
			   <%-- </div> elenco --%>
				<% 	} //if fineLotto %>
				<% 	previousLotto = currentLotto; %>
			    <% 	//previousRow = currentRow; %>
			<% 	} //for %>
			   </table>			
	   </div><%-- infoLotti --%>		
			<%--------------------------------- END ELENCO LOTTI ------------------------------%>	
				    
	     	<div id="cancDiv" style="<%= display %>">	
	      <%-- BEGIN campi Giustificazione della cancellazione --%>	
			    <h4>Giustificazione della cancellazione dei lotti selezionati</h4>  	
	    <table>
		    <tr>
		     <%//Motivazione e Note dalla request dopo una validazione errata
	   	    String reqMotivazione = request.getParameter(ParametriServletLotto.FIELD_NAME_MOTIVAZIONE);
			    String reqNoteCancel = request.getParameter(ParametriServletLotto.FIELD_NAME_NOTE);
		     %>
			  <th>Motivazione</th>
			    <td>
				   <select name="<%= ParametriServletLotto.FIELD_NAME_MOTIVAZIONE %>">
				   	<option></option>				   	
				   	<h:options name="<%= ParametriServletLotto.MOTIVAZIONI_LIST %>" scope="request" value='<%=ParametriServletLotto.FIELD_NAME_MOTIVAZIONE%>' />				     
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
	
		    <%-- END campi Giustificazione della cancellazione --%>	
		</div>
		
		<%-- BEGIN Comferma CUP lotti --%>
		<% List<CupLottoAggExt> elencoCupGara = (List)request.getAttribute(ParametriCup.PARAM_ELENCO_CUP_GARA);
			String currentCig = "";
		%>
<% if(SimogProperties.getInstance().isCUPAttivo()){ %>		
		<div id="confCup" style="<%= display %>">
			<h4><utils:message key="pubblicazione.confermaCup" /></h4>
			<div style="max-height: 200px; overflow-y: auto;">
				<table width="100%">
				<thead>
				<tr>
					<th class="garaTh">CIG</th>
					<th class="garaTh">CUP</th>
					<th class="garaTh">Confermato</th>
					<th class="garaTh">Valido</th>
					<th class="garaTh">Dati DIPE</th>
				</tr>
				</thead>
				<tbody>
				<% int idx = 0; %>
				<% boolean notConfirmed = false; %>
				<% for(CupLottoAggExt item: elencoCupGara) { %>
					<tr>
						<td class="garaTd" tabindex="<%= idx %>" id="row<%= idx %><%= ParametriCup.FIELD_NAME_CIG %>"> <%= !currentCig.equals(item.getCig()) ? item.getCig() : "" %></td>
						<td class="garaTd" tabindex="<%= idx %>" id="row<%= idx %><%= ParametriCup.FIELD_NAME_CUP %>"> <%= item.getCup() %></td>
						<td class="garaTd" tabindex="<%= idx %>" id="row<%= idx %><%= ParametriCup.FIELD_NAME_OK_UTENTE %>"> <%= PageHelper.decodeSN(item.getOkUtente()) %></td>
						<td class="garaTd" tabindex="<%= idx %>" id="row<%= idx %><%= ParametriCup.FIELD_NAME_VALIDO %>"> <%= item.getDatiDIPE() == null ? "" : PageHelper.decodeSN(item.getDatiDIPE().getVALIDO()) %></td>
						<td class="garaTd" tabindex="<%= idx %>" id="row<%= idx %><%= ParametriCup.FIELD_NAME_DATIDIPE %>"> <%= item.getDatiDIPE() == null ? "" : item.getDatiDIPE().getESITO_RICHIESTA() %></td>
						<!-- hidden input -->
						<input type="hidden" name="hiddenrowCUP<%= idx %><%= ParametriCup.FIELD_NAME_CIG %>" value="<%= item.getCig() %>"/>
						<input type="hidden" name="hiddenrowCUP<%= idx %><%= ParametriCup.FIELD_NAME_CUP %>" value="<%= item.getCup() %>"/>
						<input type="hidden" name="hiddenrowCUP<%= idx %><%= ParametriCup.FIELD_NAME_ID_LOTTO %>" value="<%= item.getIdLotto() %>"/>
						<input type="hidden" name="hiddenrowCUP<%= idx %><%= ParametriCup.FIELD_NAME_ID_AGG %>" value="<%= item.getIdAggiudicazione() %>"/>
						<input type="hidden" name="hiddenrowCUP<%= idx %><%= ParametriCup.FIELD_NAME_DATA_INIZIO_AGG %>" value="<%= item.getDataInizioAgg() %>"/>
						<input type="hidden" name="hiddenrowCUP<%= idx %><%= ParametriCup.FIELD_NAME_VALIDO %>" value="<%= item.getDatiDIPE() == null ? "" : item.getDatiDIPE().getVALIDO() %>"/>
					</tr>
				<% currentCig = item.getCig();
					notConfirmed = notConfirmed || (item.getDatiDIPE() != null && Costanti.FLAG_VALORE_SI.equals(item.getDatiDIPE().getVALIDO()) && !Costanti.FLAG_VALORE_SI.equals(item.getOkUtente()));
					idx++;
					} 
				%>
				</tbody>
				</table>	
			</div>
			<div class="hmenu">
			<% if( notConfirmed ) { %>
				<a id="ConfirmCup" href="javascript:confirmCup('<%= ParametriCup.FIELD_NAME_VALIDO %>', '<%= ParametriCup.FIELD_NAME_OK_UTENTE %>')">Conferma CUP</a>
			<% } else { %>
				<a id="disabledMenu">Conferma CUP</a>
			<% } %>
			</div>
		</div>
<% } %>			
		<%-- END   Comferma CUP lotti --%>
		
		<%-- BEGIN campi perfezionamento dei lotti --%>				       
	   <div id="datePerf" style="<%= display %>">			       
	    <h4>Perfezionamento dei lotti non selezionati</h4>
	   
	   <table width="100%">
	     <tbody>

<%
// PP 3.02.1.6
String modoReal = listaGare.getRow(0).getNulledField(GARA.ID_MODO_REAL);
/* 3.04.8 34190 fix */
String labelData = String.valueOf(Costanti.MODOREAL_ADESIONE_NOCOMPET).equals(modoReal) || String.valueOf(Costanti.MODOREAL_CONCESSIONE_NOCOMPET).equals(modoReal) ? "Data di adesione all'accordo quadro/convenzione" : "Data pubblicazione";
%>
				<tr>
					<th><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataPubblicazione") %> for="Data_pubblicazione"><small><%= SimogFlags.is3030_RFWEBGL00Active() ? "1" : "" %></small> <%=labelData %></label></th>
					<td nowrap="nowrap">
						<input style="text-align:center" tabindex="1" 
						onblur="Calendar.validaData(this)" 
						type="text" 
						id="dtpubblicazione" 
						name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE %>" 
						value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE %>"/>" >

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
					<td style="display: none;"><input type="hidden" id="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE %>" value="1" /></td>
					
<% 
     //Ticket ALM #653
     //Non mostrare la data di scadenza di presentazione della lettera di invito in caso di adesione ad accordo quadro senza successivo confonto competitivo
     if( !String.valueOf(Costanti.MODOREAL_ADESIONE_NOCOMPET).equals(modoReal) && !String.valueOf(Costanti.MODOREAL_CONCESSIONE_NOCOMPET).equals(modoReal)){
     //Fine Ticket ALM #653 
    	 %>	
					<th><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataRichiestaInvito") %> for="Data_scadenza_invito"><small><%= SimogFlags.is3030_RFWEBGL00Active() ? "3" : "" %></small> Data di scadenza per la presentazione della richiesta di invito</label></th>
					<td nowrap="nowrap">
						<input style="text-align:center" tabindex="2" 
						onblur="Calendar.validaData(this)" 
						type="text" id="dtscadenzainvito" 
						name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO %>" 
						value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO %>" />">
						
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
<% } %>					
				</tr>
<!-- 3.04.8 34190 fix -->
<% if((String.valueOf(Costanti.MODOREAL_ADESIONE_NOCOMPET).equals(modoReal) || String.valueOf(Costanti.MODOREAL_CONCESSIONE_NOCOMPET).equals(modoReal))==false){
%>				
				<tr>
					<th><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataOfferte") %> for="Data_scadenza_pagamenti"><small><%= SimogFlags.is3030_RFWEBGL00Active() ? "2" : "" %></small> Data scadenza per la presentazione delle offerte</label></th>
					<td nowrap="nowrap">
						<input style="text-align:center" tabindex="2" 
						onblur="Calendar.validaData(this)" 
						type="text" id="dtscadenza" 
						name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA %>" 
						value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA %>" />">
						
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
					<th><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataLetteraInvito") %> for="Data_lettera_invito"><small><%= SimogFlags.is3030_RFWEBGL00Active() ? "4" : "" %></small>Data della lettera di invito</label></th>
					<td nowrap="nowrap">
						<input style="text-align:center" tabindex="2" 
						onblur="Calendar.validaData(this)" 
						type="text" id="dtletterainvito" 
						name="<%= ParametriServlet.FIELD_NAME_DATA_LETTERA_INVITO %>" 
						value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_DATA_LETTERA_INVITO %>" />">
						
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
<!-- 3.04.8 34190 fix -->
<%if ((String.valueOf(Costanti.MODOREAL_ADESIONE_NOCOMPET).equals(modoReal) || String.valueOf(Costanti.MODOREAL_CONCESSIONE_NOCOMPET).equals(modoReal))==false){ %>					
					<tr>
					<th><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_OraScadenza") %> for=ora_scadenza_pagamenti>Ora scadenza<br>per la presentazione delle offerte (hh:mm)</th>
					<td nowrap="nowrap">
						<input style="text-align:center" tabindex="2" 
						type="text" maxlength="5" id="<%= ParametriServlet.FIELD_NAME_ORA_SCADENZA %>" 
						name="<%= ParametriServlet.FIELD_NAME_ORA_SCADENZA %>" 
						value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_ORA_SCADENZA %>" />">
					</td>
					</tr>

<%} %>	

			</tbody>
			<tfoot>
				<tr><td><br/></td></tr>
				<tr>
					<td colspan="4" class="garaTd"><%-- TICKET ALM #4196 --%>
						<a href="javascript:apripopup('help/ProceduraHelp.html');">Guida alla procedura a piu' fasi</a>
					</td>
				</tr>
			</tfoot>
	    </table>
			<%-- END campi perfezionamento dei lotti --%>
		</div>
		<% } %>
			<%-- BEGIN campi pubblicita' dell'appalto 3.0 --%>
			<h4>Pubblicita' dell'appalto</h4>
	   <table>
	     <tbody>
	       <c:set var="pubblicazione" value="${pubblicazione}" scope="page"></c:set>
	       <c:set var="hide" value="${(false)}" />
	       <c:set var="disabled" value="${hide ? 'disabled':'' }"></c:set>
	       <c:set var="pubblicita" value="${(false)}"></c:set>		
	       <c:set var="pubbModificabile" value="${(true)}"></c:set>		     
	       <%-- <%@ include file="include/datiPubblicazione.jsp" --%>
	       
	       <tr>
	         <td><label >Gazzetta Ufficiale Comunita' Europea - GUCE</label></td>
 			   <td>
				  <input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')" 
				    <c:out value="${disabled}"/> <c:if test="${pubblicita == true}"> readonly="readonly" </c:if>
					 type="text" id="inputGazzettaCE" name="<%=  ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_CE %>" 
					 onblur="Calendar.validaData(this)" value="<c:out value='${pubblicazione.dataGuce}'/>">
				    <%-- vecchio controllo <c:if test="${hide == false}"> --%>
				    <c:if test="${pubbModificabile}">
					   <img src="calendar/img.gif" id="calendarGazzettaCE" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
					  <script type="text/javascript">
						    Calendar.setup({
					        inputField     :    "inputGazzettaCE",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendarGazzettaCE",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true							       
				    		});					    	
					  </script>
				   </c:if>
			   </td>
			 <%-- 
	   	 </tr>   	
		    <tr>
		    --%>
			   <th><label for="numeroGuce">Numero</label></th>
				<td>
					<input maxlength="20"  
					type="text" id="numeroGuce"  
					name="<%= ParametriServlet.FIELD_NAME_NUMERO_GUCE %>" 
					<%-- value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_NUMERO_GUCE %>" />" --%>
					value="<c:out value='${pubblicazione.numeroGuce}'/>">	
				</td>
		    </tr>
		    <tr>
	         <td><label >Gazzetta Ufficiale Regionale o Bollettino Regionale</label></td>
 			   <td>
				  <input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  
				    <c:out value="${disabled}"/> <c:if test="${pubblicita == true}"> readonly="readonly" </c:if>
					   type="text" id="inputGazzettaBR" name="<%=  ParametriServlet.FIELD_NAME_BOLLETTINO_REGIONALE %>" 
					   onblur="Calendar.validaData(this)" value="<c:out value='${pubblicazione.dataBore}'/>">
				    <c:if test="${pubbModificabile}">
					   <img src="calendar/img.gif" id="calendarGazzettaBR" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
					   <script type="text/javascript">
						    Calendar.setup({
					        inputField     :    "inputGazzettaBR",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendarGazzettaBR",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true							       
				    		});					    	
					  </script>
				   </c:if>
			   </td>
	   	<%-- 
	   	 </tr>   	
		    <tr>
		    --%>
				<th><label for="numeroBore">Numero</label></th>
				  <td>
					 <input maxlength="20"  
					   type="text" id="numeroBore"  
					   name="<%= ParametriServlet.FIELD_NAME_NUMERO_BORE %>" 
					   <%-- value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_NUMERO_BORE %>" />"--%>
					   value="<c:out value='${pubblicazione.numeroBore}'/>">
				  </td>
		    </tr>
		    <tr>
	         <td><label >Gazzetta Ufficiale Repubblica Italiana - GURI</label></td>
 			   <td>
				  <input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  
				    <c:out value="${disabled}"/> <c:if test="${pubblicita == true}"> readonly="readonly" </c:if>
					   type="text" id="inputGazzettaRI" name="<%=  ParametriServlet.FIELD_NAME_GAZZETTA_UFFICIALE_RI %>" 
					   onblur="Calendar.validaData(this)" value="<c:out value='${pubblicazione.dataGuri}'/>">
				    <c:if test="${pubbModificabile}">
					   <img src="calendar/img.gif" id="calendarGazzettaRI" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
					   <script type="text/javascript">
						    Calendar.setup({
					        inputField     :    "inputGazzettaRI",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendarGazzettaRI",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true							       
				    		});					    	
					  </script>
				   </c:if>
			    </td>
	   	<%-- 
	   	 </tr>   	
		    <tr>
		    --%>
				<th><label for="numeroGuri">Numero</label></th>
				<td>
					<input maxlength="20"
					type="text" id="numeroGuri"  
					name="<%= ParametriServlet.FIELD_NAME_NUMERO_GURI %>" 
					<%-- value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_NUMERO_GURI %>" />"--%>
					value="<c:out value='${pubblicazione.numeroGuri}'/>">		
				</td>
	   	 </tr>   	
		    <tr>
	        <td><label >Albo pretorio del Comune ove si eseguono i lavori</label></td>
 			  <td>
				 <input  tabindex="<%=++indiceTab%>" style="text-align:center;" onchange="setFormModified('Modificato')"  
				   <c:out value="${disabled}"/> <c:if test="${pubblicita == true}"> readonly="readonly" </c:if>
					  type="text" id="inputAP" name="<%=  ParametriServlet.FIELD_NAME_ALBO_PRETORIO %>" 
					  onblur="Calendar.validaData(this)" value="<c:out value='${pubblicazione.dataAlbo}'/>">
				   <c:if test="${pubbModificabile}">
					  <img src="calendar/img.gif" id="calendarAP" style="cursor: pointer; border: 1px solid red;" title="Date selector"
								onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
					  <script type="text/javascript">
						    Calendar.setup({
					        inputField     :    "inputAP",     // id of the input field
					        ifFormat       :    "%d/%m/%Y",      // format of the input field
					        button         :    "calendarAP",  // trigger for the calendar (button ID)
					        align          :    "Tl",           // alignment (defaults to "Bl")
					        singleClick    :    true							       
				    		});					    	
					  </script>
				  </c:if>
			  </td>
	   	</tr>
   		<tr>
 			  <th><label >Quotidiani nazionali</label></th>
 			  <td>
				 <input  name="<%=  ParametriServlet.FIELD_NAME_QUOTIDIANI_NAZIONALI%>"  tabindex="<%=++indiceTab%>" style="text-align:right;"  
				   <c:out value="${disabled}"/> <c:if test="${pubblicita == true}"> readonly="readonly" </c:if>
				     type="text" value="<c:out value='${pubblicazione.quotidianiNaz}'/>" onblur="validateNumber(this)" maxlength="9"/>
			  </td>
	   	</tr>
	   	<tr>
 			  <th><label >Quotidiani locali</label></th>
 			  <td>
				 <input  name="<%=  ParametriServlet.FIELD_NAME_QUOTIDIANI_REGIONALI%>"  tabindex="<%=++indiceTab%>" style="text-align:right;" 
				 <c:out value="${disabled}"/> <c:if test="${pubblicita == true}"> readonly="readonly" </c:if>
				   type="text" value="<c:out value='${pubblicazione.quotidianiReg}'/>"  onblur="validateNumber(this)" maxlength="9"/>
			  </td>
	   	</tr> 
	   	<tr>
 			  <th><label >Periodici</label></th>
 			  <td>
				 <input  name="<%=  ParametriServlet.FIELD_NAME_PERIODICI%>"  tabindex="<%=++indiceTab%>" style="text-align:right;" 
				 <c:out value="${disabled}"/> <c:if test="${pubblicita == true}"> readonly="readonly" </c:if>
				   type="text" value="<c:out value='${pubblicazione.periodici}'/>"  onblur="validateNumber(this)" maxlength="4"/>
			  </td>
	   	</tr>   
	   	<tr>
			   <th><label >Sito Informatico Ministero Infrastrutture<br>e piattaforma digitale ANAC tramite i sistemi<br>informatizzati regionali</label></th>
		  	     <td>
		  	       <c:if test="${pubblicita == true}">
		  	         <input  tabindex="<%=++indiceTab%>" id="check2Y" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_MIN_INF_TRASP %>" value="S" <c:out value="${pubblicazione.sitoMinisteroInfTrasp == 'S' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />SI 
  			         <input  tabindex="<%=++indiceTab%>" id="check2N" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_MIN_INF_TRASP %>" value="N" <c:out value="${pubblicazione.sitoMinisteroInfTrasp == 'N' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />NO			   
		  	       </c:if>
		  	       <c:if test="${pubblicita == false}">  				  	    
		  	         <input  tabindex="<%=++indiceTab%>" id="check2Y" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_MIN_INF_TRASP %>" value="S" <c:out value="${pubblicazione.sitoMinisteroInfTrasp == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />SI 
  			         <input  tabindex="<%=++indiceTab%>" id="check2N" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_MIN_INF_TRASP %>" value="N" <c:out value="${pubblicazione.sitoMinisteroInfTrasp == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />NO
  			       </c:if>
  			     </td>
		    </tr>
		    <%-- 
		    <tr>
			   <th><label >Sito Informatico Osservatorio Contratti Pubblici</label></th>
		  	     <td>
		  		    <c:if test="${pubblicita == true}">
		  	         <input  tabindex="<%=++indiceTab%>" id="check3Y" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_OSSERVATORIO_CP %>" value="S" <c:out value="${pubblicazione.sitoOsservatorioCP == 'S' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />SI 
  			         <input  tabindex="<%=++indiceTab%>" id="check3N" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_OSSERVATORIO_CP %>" value="N" <c:out value="${pubblicazione.sitoOsservatorioCP == 'N' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />NO
		          </c:if>
	 	          <c:if test="${pubblicita == false}">  				    
		  	         <input  tabindex="<%=++indiceTab%>" id="check3Y" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_OSSERVATORIO_CP %>" value="S" <c:out value="${pubblicazione.sitoOsservatorioCP == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />SI 
  			         <input  tabindex="<%=++indiceTab%>" id="check3N" type="radio" name="<%= ParametriServlet.FIELD_NAME_SITO_OSSERVATORIO_CP %>" value="N" <c:out value="${pubblicazione.sitoOsservatorioCP == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />NO
  			       </c:if>
  			     </td>
		    </tr>
		    <tr>
			   <th><label >Profilo del Committente</label></th>
		  	     <td>
		  	       <c:if test="${pubblicita == true}">
                  <input  tabindex="<%=++indiceTab%>" id="check1Y" type="radio" name="<%= ParametriServlet.FIELD_NAME_PROFILO_COMMITTENTE %>" value="S" <c:out value="${pubblicazione.profiloCommitente == 'S' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />SI 
  			         <input  tabindex="<%=++indiceTab%>" id="check1N" type="radio" name="<%= ParametriServlet.FIELD_NAME_PROFILO_COMMITTENTE %>" value="N" <c:out value="${pubblicazione.profiloCommitente == 'N' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />NO
                </c:if>		  	   
		  	       <c:if test="${pubblicita == false}">  
		  	         <input  tabindex="<%=++indiceTab%>" id="check1Y" type="radio" name="<%= ParametriServlet.FIELD_NAME_PROFILO_COMMITTENTE %>" value="S" <c:out value="${pubblicazione.profiloCommitente == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />SI 
  			         <input  tabindex="<%=++indiceTab%>" id="check1N" type="radio" name="<%= ParametriServlet.FIELD_NAME_PROFILO_COMMITTENTE %>" value="N" <c:out value="${pubblicazione.profiloCommitente == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />NO
                </c:if>  			
  			     </td>
		    </tr>
		    --%>
		    <tr>
			   <th><label for="Link Sito Committente">Link Sito Committente</label></th>
				  <td colspan="3">
					 <input maxlength="250"  size="100%"
					 type="text" id="linkSitoCommittente"
					 name="<%= ParametriServlet.FIELD_NAME_LINK_SITO_COMMITTENTE %>" 
					 <%-- value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_LINK_SITO_COMMITTENTE %>" />"> --%>
				    value="<c:out value='${pubblicazione.linkSitoCommittente}'/>">
				  </td>
		    </tr>
		   <%--gm nuovo codice estensione pubblicazione bandi --%>
		   <%--TICKET ALM #3922-06.1 e 06.2 --%>
		   <% 
		      if(!SimogFlags.is3042Active() || !SimogProperties.getInstance().isDataCreatedAfter3042(dataCreazioneGaraStr))
		       if (ParametriServlet.PUBBLICAZIONE.equals(tipoPubblicazione))  { 
		   %>

			   <tr>
				   <th><label >Procedura negoziata ex art. 204 comma 1 D.Lgs. 163/2006</label></th>
			  	     <td>
			  	       <c:if test="${pubblicita == true}">
	                  <input  tabindex="<%=++indiceTab%>" id="<%= ParametriServlet.S_FIELD_NAME_FLAG_BENICULT%>" type="radio" name="<%= ParametriServlet.FIELD_NAME_FLAG_BENICULT %>" value="S" <c:out value="${pubblicazione.flag_benicult == 'S' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />SI 
	  			         <input  tabindex="<%=++indiceTab%>" id="<%= ParametriServlet.N_FIELD_NAME_FLAG_BENICULT%>" type="radio" name="<%= ParametriServlet.FIELD_NAME_FLAG_BENICULT %>" value="N" <c:out value="${pubblicazione.flag_benicult == 'N' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />NO
	                </c:if>		  	   
			  	       <c:if test="${pubblicita == false}">  
			  	         <input  tabindex="<%=++indiceTab%>" id="<%= ParametriServlet.S_FIELD_NAME_FLAG_BENICULT%>" type="radio" name="<%= ParametriServlet.FIELD_NAME_FLAG_BENICULT %>" value="S" <c:out value="${pubblicazione.flag_benicult == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />SI 
	  			         <input  tabindex="<%=++indiceTab%>" id="<%= ParametriServlet.N_FIELD_NAME_FLAG_BENICULT%>" type="radio" name="<%= ParametriServlet.FIELD_NAME_FLAG_BENICULT %>" value="N" <c:out value="${pubblicazione.flag_benicult == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />NO
	                </c:if>  			
	  			     </td>
			    </tr>
			<% } %>
			<%--FINE TICKET ALM #3922-06.1 e 06.2 --%>
	    </tbody>
	  </table>
			<%-- END campi pubblicita' dell'appalto 3.0 --%>
		<% if ( SimogProperties.getInstance().isDocumentiAbilitato() ) { %>
			<br>
			<div id="divAllegati">
			<%if (ParametriServlet.PUBBLICAZIONE.equals(tipoPubblicazione)){ %>
				<h4>Allegati al bando di gara</h4>
				<table>
				<tr>
				<th><label for="">Bando di Gara</label></th>
				<% String func =  "apripopupAllegati('" + ParametriServlet.SRV_GESTISCI_ALLEGATI 
						+ "?" + ParametriServlet.SESSION_ID_GARA + "=" + codiceGara 
						+ "&" + ParametriServlet.TIPODOC + "=" + PubblicazioneBean.TipoDocumento.BANDO.getCodice()
						+ "&" + ParametriServlet.RETFIELD+ "=" + ParametriServlet.ALLEGATO1 + "','" + ParametriServlet.ALLEGATO1 + "'); return false;";
				%>
				<td>
				<input type="button" 
								onclick="<%= func %>" 
								value="Gestisci Bando di Gara" />
				
					<input type="hidden" id="<%= ParametriServlet.ALLEGATO1 %>" name="<%= ParametriServlet.ALLEGATO1 %>"
						value='<h:requestParameter property="<%= ParametriServlet.ALLEGATO1 %>" defaultValue="<%=(String)request.getAttribute(ParametriServlet.ALLEGATO1) %>"/>'/>
				</td>
				<td>
				<input type="text" id="<%= ParametriServlet.ALLEGATO1DESC %>" name="<%= ParametriServlet.ALLEGATO1DESC %>" 
					value='<h:requestParameter property="<%= ParametriServlet.ALLEGATO1DESC %>" defaultValue="<%=(String)request.getAttribute(ParametriServlet.ALLEGATO1DESC) %>"/>' readonly="readonly"/>
				</td>
				</tr>
				<tr>
				<th><label for="">Disciplinare</label></th>
				<% func =  "apripopupAllegati('" + ParametriServlet.SRV_GESTISCI_ALLEGATI 
						+ "?" + ParametriServlet.SESSION_ID_GARA + "=" + codiceGara 
						+ "&" + ParametriServlet.TIPODOC + "=" + PubblicazioneBean.TipoDocumento.DISCIPLINARE.getCodice()
						+ "&" + ParametriServlet.RETFIELD+ "=" + ParametriServlet.ALLEGATO2 + "','" + ParametriServlet.ALLEGATO2 + "'); return false;";
				%>
				<td>
				<input type="button" 
								onclick="<%= func %>" 
								value="Gestisci Disciplinare" />
				<input type="hidden" id="<%= ParametriServlet.ALLEGATO2 %>" name="<%= ParametriServlet.ALLEGATO2 %>"
					value='<h:requestParameter property="<%= ParametriServlet.ALLEGATO2 %>" defaultValue="<%=(String)request.getAttribute(ParametriServlet.ALLEGATO2) %>"/>'/>
				</td>
				<td>
				<input type="text" id="<%= ParametriServlet.ALLEGATO2DESC %>" name="<%= ParametriServlet.ALLEGATO2DESC %>"
					value='<h:requestParameter property="<%= ParametriServlet.ALLEGATO2DESC %>" defaultValue="<%=(String)request.getAttribute(ParametriServlet.ALLEGATO2DESC) %>"/>' readonly="readonly"/>
				</td>
				</tr>
				<%-- gm nuovo codice estensione pubblicazione bandi --%>
				<tr>
				<th><label for="">Lettera di Invito o avviso<br>di preinformazione</label></th>
				<% func =  "alertInvito('" + ParametriServlet.SRV_GESTISCI_ALLEGATI 
						+ "?" + ParametriServlet.SESSION_ID_GARA + "=" + codiceGara 
						+ "&" + ParametriServlet.TIPODOC + "=" + PubblicazioneBean.TipoDocumento.LETTERA_INVITO.getCodice()
						+ "&" + ParametriServlet.RETFIELD+ "=" + ParametriServlet.ALLEGATO3  + "','" + ParametriServlet.ALLEGATO3 + "','"
						+ ParametriServlet.ALLEGATO3DESC + "'); return false;";
				%>
				<td>
				<input type="button" 
								onclick="<%= func %>" 
								value="Gestisci Lettera di Invito" />		
				<input type="hidden" id="<%= ParametriServlet.ALLEGATO3 %>" name="<%= ParametriServlet.ALLEGATO3 %>"
						value='<h:requestParameter property="<%= ParametriServlet.ALLEGATO3 %>" defaultValue="<%=(String)request.getAttribute(ParametriServlet.ALLEGATO3) %>"/>'/>
				</td>
				<td>
				<input type="text" id="<%= ParametriServlet.ALLEGATO3DESC %>" name="<%= ParametriServlet.ALLEGATO3DESC %>" 
					value='<h:requestParameter property="<%= ParametriServlet.ALLEGATO3DESC %>" defaultValue="<%=(String)request.getAttribute(ParametriServlet.ALLEGATO3DESC) %>"/>' readonly="readonly"/>
				</td>
				</tr>
				</table>
			<% } %>
			<%if (ParametriServlet.PUBBLICAZIONE_AVVISO.equals(tipoPubblicazione)){ %>
				<h4>Allegati alla pubblicazione</h4>
				<table>
				<tr>
				<th><label for="">Avviso di Aggiudicazione</label></th>
				<% String func =  "apripopupAllegati('" + ParametriServlet.SRV_GESTISCI_ALLEGATI 
						+ "?" + ParametriServlet.SESSION_ID_GARA + "=" + codiceGara 
						+ "&" + ParametriServlet.TIPODOC + "=" + PubblicazioneBean.TipoDocumento.AVVISO.getCodice()
						+ "&" + ParametriServlet.RETFIELD+ "=" + ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE 
						  + "','" + ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE + "'); return false;";
				%>
				<td>
				<input type="button" 
								onclick="<%= func %>" 
								value="Gestisci Avviso di Aggiudicazione" />
				<input type="hidden" id="<%= ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE %>" name="<%= ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE %>"
						value='<h:requestParameter property="<%= ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE %>" defaultValue="<%=(String)request.getAttribute(ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE) %>"/>'/>
				</td>
				<td>
				<input type="text" id="<%= ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE_DESC %>" name="<%= ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE_DESC %>" 
					value='<h:requestParameter property="<%= ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE_DESC %>" defaultValue="<%=(String)request.getAttribute(ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE_DESC) %>"/>' readonly="readonly"/>
				</td>
				</tr>
			  </table>
			<% } %>
			</div>
			<br>
		<% } %>
		
		<!-- MEV 34470 3.04.8-->
	    <div id="linkDiv" style="<%= display %>">	
	      <%-- BEGIN campi Giustificazione della cancellazione --%>	
			    <h4>Bandi e Contratti</h4>  	
			    <table>
				
				   <tr>
				     <th>
				     
				     <label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_BandiEContratti") %> for="bandi_e_contratti">Link ai documenti relativi all'affidamento diretto in somma urgenza e protezione civile</label></th>
					  <td nowrap="nowrap">
								<input style="text-align:center" tabindex="2" 
								type="text" maxlength="100" id="<%= ParametriServlet.FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO %>" 
								name="<%= ParametriServlet.FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO %>" 
								value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO %>" />">
					 </td>
				   </tr>
			    </table>
	
		    <%-- END campi Giustificazione della cancellazione --%>	
		</div>	
		<!--  FINE MEV -->
		
		<!-- mev 3.04.10 43345 -->
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
			<% for (int i = 0; i < listaDerogaQualificazioneSA.getTableSize(); i++ ) { %>
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
			<!-- fine  mev 3.04.10 43345 -->
		
		
			
	  <div class="infoBlock">	
			 <div class="leftLineInfo">
			 <%  
			 if (user.isRSSAorRUP()) { %>
			 <%-- <input type="submit" value="Procedi">--%>
			 <input type="button" value="Procedi" onclick="checkAndGo(<%=currentRow.getNulledField(GARA.IMPORTO_GARA)%>,'<%=tipoPubblicazione%>','<%=numeroLottiPerfezionatiODaPerfezionare %>')">
			 <input type="hidden"  value="" name="toDo" id="toDo"/>
			 <%if (ParametriServlet.PUBBLICAZIONE.equals(tipoPubblicazione)){ %>
			 <input type="button" value="Reimposta" onclick="reimpostaForm('<%=ParametriServlet.ACTION_CARICA_GARA %>')"/>
			 <% } %>
			 <%if (ParametriServlet.PUBBLICAZIONE_AVVISO.equals(tipoPubblicazione)){ %>
			 <input type="button" value="Reimposta" onclick="reimpostaForm('<%=ParametriServlet.ACTION_CARICA_AVVISO %>')"/>
			 <% } %>
			 <% } %>			 
		  </div>
     </div>
     
     
  	   
	</form>
			    
		</div><%-- bodypage-e --%>
	</div><%-- bodypage --%>
<%@ include file="include/newfooter.inc" %>
</div><%-- gabbia --%>

<script type="text/javascript">
checkMessaggio();
</script>
</body>
<% }catch(Exception e){e.printStackTrace();} %>
</html>
