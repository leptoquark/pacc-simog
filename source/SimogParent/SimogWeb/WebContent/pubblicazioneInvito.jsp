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
<%@ page import="it.avlp.simog.db.Costanti"%>
<%@ page import="it.avlp.simog.common.servlet.ParametriServletLotto"%>
<%@ page import="it.avlp.simog.beans.PubblicazioneBean"%>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ taglib prefix="x" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="h" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% int indiceTab = 0;%>

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

<title>SIMOG - <utils:message key="pubblicazione.perfezionamentoProcedureFasi" /></title><%-- TICKET ALM #4196 --%>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">

</head>

<script type="text/javascript">
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
				
			if(document.forms[0].id_scelta.value == "<%= Costanti.PROC_APE %>"
	               || document.forms[0].id_scelta.value == "<%= Costanti.PROC_RIS %>"
	               || document.forms[0].id_scelta.value == "<%= Costanti.DIA_COMP %>"
	               || document.forms[0].id_scelta.value == "<%= Costanti.PROC_NEG_PP %>"
	            	|| (document.forms[0].id_scelta.value == "<%= Costanti.PROC_NEG_NO_PP %>" && document.getElementById("<%= ParametriServlet.S_FIELD_NAME_FLAG_BENICULT %>").checked)
		            ){
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
		            if(document.forms[0].id_scelta[i].value == "<%= Costanti.PROC_APE %>"
		               || document.forms[0].id_scelta[i].value == "<%= Costanti.PROC_RIS %>"
		               || document.forms[0].id_scelta[i].value == "<%= Costanti.DIA_COMP %>"
		               || document.forms[0].id_scelta[i].value == "<%= Costanti.PROC_NEG_PP %>"
			            || (document.forms[0].id_scelta[i].value == "<%= Costanti.PROC_NEG_NO_PP %>" && document.getElementById("<%= ParametriServlet.S_FIELD_NAME_FLAG_BENICULT %>").checked)
			            ){
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
	
	function checkAndGo(impGara,tipoPubblicazione,numeroLottiPerfezionatiODaPerfezionare){
	
		// se tutti i lotti sono cancellati impossibile proseguire
		var dacanc = document.forms[0].id_lotti_cancellare;
		var ok = true;
		//se dacanc non esiste, sono in pubblicazione senza lotti da cancellare oppure in avviso di aggiudicazione
		if(dacanc){
			var num = dacanc.length;
			if(!num) 
				num = 1;
			var numDaCanc = 0;
			for (i = 0; i < num; i++) {
				if (num>1 && dacanc[i].checked) {
					numDaCanc=numDaCanc+1;
				}
				if (num==1 && dacanc.checked) {
					numDaCanc=numDaCanc+1;
				}
			}
			if(numDaCanc>=numeroLottiPerfezionatiODaPerfezionare)
				ok = false;
			
			if(!ok){		        
				if (typeof i18n !== 'undefined' && i18n.alert) { i18n.alert('error.lottoRequired'); } else { alert("Almeno un lotto deve essere incluso nella pubblicazione"); }
				return false;
			}
		}
		
		var p = isPubblicazione();
		if(p==true && impGara >= <%=Costanti.IMPORTO_LOTTO_500000%>
			&& (document.getElementById("inputGazzettaRI").value == "" 
			|| document.getElementById("numeroGuri").value == "" 
			|| document.getElementById("linkSitoCommittente").value == "")){
			msg="ATTENZIONE: a norma dell'art. 36 comma 9, � prevista la pubblicazione sulla GURI e sul profilo del committente. Proseguire comunque?";
	    	if(confirm(msg)){
		    	if(tipoPubblicazione=='pubblicazione')
				    doAction('salvaBandoGara');
		    	if(tipoPubblicazione=='pubblicazioneAvviso')
				    doAction('salvaAvviso');
			} 
			else return false;
	   }
	   else {
		    if(tipoPubblicazione=='pubblicazione')
		        doAction('salvaBandoGara');
    	    if(tipoPubblicazione=='pubblicazioneAvviso')
		        doAction('salvaAvviso');
	   }
	}
	
	function alertInvito(url, campo, campoPres){

		var isPres = document.getElementById(campoPres);
		if(isPres)
		{
			if(isPres.value == ""){
				alert("Attenzione! Le lettere d'invito ai sensi dell'art. 204 del Codice devono essere acquisite prive d'indicazioni relative ai soggetti invitati.");
			}
			apripopupAllegati(url, campo);
		}
	}
</script>
<body>
<!-- TB: ticket popup modali. Import css e js -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<div id="dialogALLEGATO1"></div>
<div id="dialogALLEGATO2"></div>
<div id="dialogALLEGATO3"></div>
<!-- fine import popup modali -->

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
%>

<div id="gabbia">
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuGara.inc" %>
	
	<div id="bodypage">
		<div class="bodypage-e">
			<%if (ParametriServlet.PUBBLICAZIONE.equals(tipoPubblicazione)){ %>	    
			<h1>Perfezionamento procedure con piu' fasi</h1><%-- TICKET ALM #4196 --%>
			<% } %>
			<%if (ParametriServlet.PUBBLICAZIONE_AVVISO.equals(tipoPubblicazione)){ %>	    
			<h1>Pubblicazione Avviso di Aggiudicazione</h1>
			<% } %>
				<%@ include file="include/gestisciErrore.inc" %>
			<div class="hmenu">
			   <%if (ParametriServlet.PUBBLICAZIONE.equals(tipoPubblicazione)){ %>	    
				<ul><li><a title="Pagina precedente" href="<%=ParametriServlet.SRV_VISUALIZZA_DETTAGLIO%>
				?<%=ParametriServlet.SESSION_ID_GARA%>=<%=listaGare.getRow(0).getNulledField(LOTTO.ID_GARA) %>
				&<%=ParametriServlet.FROM_GARE %>=<%=Costanti.FLAG_VALORE_SI %>">Ritorna</a></li></ul>
				<% } %>
				<%if (ParametriServlet.PUBBLICAZIONE_AVVISO.equals(tipoPubblicazione)){ %>	    
				<ul><li><a title="Pagina precedente" href="<%=ParametriServlet.SRV_SCHEDA_A%>
				?<%=PSBD.FIELD_NAME_ID_AGGIUDICAZIONE%>=<%=idAggiudicazione %>
				&<%=PSBD.FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE %>=<%=datainizioAggiudicazione %>">Ritorna</a></li></ul>
				<% } %>
			</div><%-- hmenu --%>
			
			<%	int rowIndex = 0;
				currentRow = listaGare.getRow(rowIndex);
				String codiceGara = currentRow.getNulledField(GARA.ID_GARA);
				String oggettoGara = PageHelper.formattaTesto(currentRow.getNulledField(GARA.TABLE_NAME + GARA.OGGETTO));
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
	
	   <%-- BEGIN campi perfezionamento dei lotti --%>	
			       
	   <div id="datePerf" style="<%= display %>">			       
	    <h4>Perfezionamento dei lotti non selezionati</h4>
	   
	   <table width="100%">
	     <tbody>

<%
// PP 3.02.1.6
String modoReal = listaGare.getRow(0).getNulledField(GARA.ID_MODO_REAL);
/* 3.04.8 34190 fix */
String labelData = SimogFlags.is30216Active()== true && (String.valueOf(Costanti.MODOREAL_ADESIONE_NOCOMPET).equals(modoReal) || String.valueOf(Costanti.MODOREAL_CONCESSIONE_NOCOMPET).equals(modoReal)) ? "Data di adesione all'accordo quadro/convenzione" : "Data pubblicazione";
%>
				<tr>
					<th><label for="Data_pubblicazione"><%=labelData %></th>
					<td nowrap="nowrap">
						<input style="text-align:center" tabindex="1" 
						onblur="Calendar.validaData(this)" 
						type="text" 
						id="dtpubblicazione" 
						name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE %>" 
						value="<%= request.getAttribute(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE) %>" 
						readonly="readonly">

							<img style="display:none" src="calendar/img.gif" id="CALdtpubblicazionestart" style="cursor: pointer; border: 1px solid red;" title="Date selector"
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
					
<% if( SimogFlags.is3030_RFWEBGL00Active() ){ %>				
					<th><label for="Data_scadenza_invito">Data di scadenza per la presentazione della richiesta di invito</th>
					<td nowrap="nowrap">
						<input style="text-align:center" tabindex="2" 
						onblur="Calendar.validaData(this)" 
						type="text" id="dtscadenzainvito" 
						name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO %>" 
						value="<%= request.getAttribute(ParametriServlet.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO) %>" 
						readonly="readonly">
						
							<img style="display:none" src="calendar/img.gif" id="CALdtscadenzainvito" style="cursor: pointer; border: 1px solid red;" title="Date selector"
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
<% if(SimogFlags.is30216Active() == false || (SimogFlags.is30216Active() == true && ((String.valueOf(Costanti.MODOREAL_ADESIONE_NOCOMPET).equals(modoReal) || String.valueOf(Costanti.MODOREAL_CONCESSIONE_NOCOMPET).equals(modoReal))==false))){
%>				
				<tr>
					<th><label for="Data_scadenza_pagamenti">Data scadenza per la presentazione delle offerte</th>
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
<% if( SimogFlags.is3030_RFWEBGL00Active() ){ %>					
					<th><label for="Data_lettera_invito">Data della lettera di invito</th>
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
<% } %>					
				</tr>	
<% } %>
<!-- 3.04.8 34190 fix -->
<%if (SimogFlags.is3025_RFWEBGL02Active() && ((String.valueOf(Costanti.MODOREAL_ADESIONE_NOCOMPET).equals(modoReal) || String.valueOf(Costanti.MODOREAL_CONCESSIONE_NOCOMPET).equals(modoReal))==false)){ %>					
					<tr>
					<th><label for=ora_scadenza_pagamenti>Ora scadenza<br>per la presentazione delle offerte (hh:mm)</th>
					<td nowrap="nowrap">
						<input style="text-align:center" tabindex="2" 
						type="text" maxlength="5" id="<%= ParametriServlet.FIELD_NAME_ORA_SCADENZA %>" 
						name="<%= ParametriServlet.FIELD_NAME_ORA_SCADENZA %>" 
						value="<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_ORA_SCADENZA %>" />">
					</td>
					</tr>

<%} %>	

			</tbody>
	    </table>
	    <br/>
			<%-- END campi perfezionamento dei lotti --%>
		</div>
		<% } %>

		<!-- END campi pubblicit� dell'appalto 3.0 -->
		<% if ( SimogProperties.getInstance().isDocumentiAbilitato() ) { %>
			<br>
			<div id="divAllegati">
			<%if (ParametriServlet.PUBBLICAZIONE.equals(tipoPubblicazione)){ %>
				<h4>Allegati al bando di gara</h4>
				<table>
				<%-- gm nuovo codice estensione pubblicazione bandi --%>
				<tr>
				<th><label for="">Lettera di Invito (ex art.204 c.1)</label></th>
				<% String func =  "alertInvito('" + ParametriServlet.SRV_GESTISCI_ALLEGATI 
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
			</div>
			<br>
		<% } %>
			
	  <div class="infoBlock">	
			 <div class="leftLineInfo">
			 <%  
			 if (user.isRSSAorRUP()) { %>
			 <%-- <input type="submit" value="Procedi">--%>
			 <input type="button" value="Procedi" onclick="doAction('<%= ParametriServlet.ACTION_SALVA_INVITO %>')">
			 <input type="hidden"  value="" name="toDo" id="toDo"/>
			 <%if (ParametriServlet.PUBBLICAZIONE.equals(tipoPubblicazione)){ %>
			 <input type="button" value="Reimposta" onclick="reimpostaForm('<%=ParametriServlet.ACTION_CARICA_INVITO %>')"/>
			 <% } %>
			 <% } %>			 
		  </div>
     </div>     
	</form>
			    
		</div><%-- bodypage-e --%>
	</div><%-- bodypage --%>
<%@ include file="include/newfooter.inc" %>
</div><%-- gabbia --%>

</body>

</html>