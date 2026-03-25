<%@page import="java.util.Enumeration"%>
<%@page import="it.avlp.simog.beans.EsitoEnum"%>
<%@page import="it.avlp.simog.beans.PubblicazioneBean"%>
<%try{%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/controlloSessione.inc" %>
<%@ include file="include/newbasicHeader.inc" %>

<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.db.advanced.*"%>
<%@ page import="it.avlp.simog.common.servlet.*"%>
<%@ page import="java.math.BigDecimal"%>

<!-- MEV 25895 POP-UP -->
<script type="text/javascript" src="xtree/treeutils.js"></script>
<script type="text/javascript" src="script/other/jquery.js"></script>

<script type="text/javascript">
function visualizza(url){
 var finestra = window.open(url,"window","scrollbars=1,width=550,height=250,left=240,top=180");
}
function avviso(printWarning, maxRigheExp){
	if(printWarning)
		alert("<%= MessageHelper.getMessage(request, "elenco.attenzioneCigEsportare") %> " + maxRigheExp + " <%= MessageHelper.getMessage(request, "elenco.cig") %>");
}

/* MEV 25895 POP-UP */
//PARTE CHE APRE LA POPUP INFORMATIVA
function apripopupPubblicaBandoGara(path){
	var dialogArgs = new MyDialogArguments();
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
	  
	  
	}
</script>


<title>SIMOG - <utils:message key="ricerca.ricercaGara" /> - <utils:message key="ricerca.elencoGare" /></title>
</head>      

<body>
<!-- MEV 25895 POP-UP -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<div id="dialog"></div>


<div id="gabbia">


<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuGara.inc" %>


<% int maxRigheVisualizzabili = Integer.parseInt( (String)request.getAttribute( ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI ) );
   Integer maxRigheExport = (Integer)request.getAttribute( ParametriServlet.CONFIG_ELEMENTI_EXPORT ); 
   Integer startRowInt = (Integer)request.getAttribute( ParametriServlet.START_ROW ); 
	TableBean listaGare = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); 
	int tableBeanSize = listaGare.getFullSize();
	boolean printExportAlert = tableBeanSize >= maxRigheExport;
	int startRow = startRowInt.intValue(); 
	//attenzione aggiunto il controllo per le pagine che hanno la stringa "continua"
	//perche la dimensione del table bean in quel caso e' maggiore di uno rispetto a quello che dovrebbe.
	int righeVisualizzate = startRow + (listaGare.getTableSize() > maxRigheVisualizzabili ? maxRigheVisualizzabili : listaGare.getTableSize());
	long resto = (tableBeanSize % maxRigheVisualizzabili);
	long fineElenco = tableBeanSize - resto - maxRigheVisualizzabili - (resto == 0 ? maxRigheVisualizzabili : 0) ; 
	String fromGare = (String) request.getAttribute(ParametriServlet.FROM_GARE);
	String fromRicerca = (String) request.getAttribute(ParametriServlet.FROM_RICERCA);
	
	String jspGestione = user.isAmministratore() ? ParametriServlet.JSP_GESTIONE_GARE_EXT 
						: user.isRSSA() ? ParametriServlet.JSP_GESTIONE_GARE_RSSA 
						: user.isCS() || user.isOssReg() || user.isRASA() ? ParametriServlet.JSP_GESTIONE_SCHEDE
						: user.isRUP() && !Costanti.FLAG_VALORE_SI.equals(fromGare) ? ParametriServlet.JSP_GESTIONE_SCHEDE								
						: user.isRUP() && Costanti.FLAG_VALORE_SI.equals(fromGare) ? ParametriServlet.JSP_GESTIONE_GARE_RSSA								
						: "";
	//TICKET ALM - 3.04.3 #3916
	boolean isAccQuadroNc = request.getAttribute(ParametriServlet.IS_ACC_QUADRO_NC) != null && ((String) request.getAttribute(ParametriServlet.IS_ACC_QUADRO_NC)).equals("OK");

	String jspRicerca = user.isAmministratore() ? "ricercaGareExt" 
						: user.isRSSA() ? "ricercaGare"  
						: user.isCS() || user.isOssReg() ? "ricercaGareRUP_CS"
						: user.isRUP() && Costanti.FLAG_VALORE_SI.equals(fromGare) ? "ricercaGareRUP_CS"
						: user.isRUP() && !Costanti.FLAG_VALORE_SI.equals(fromGare) ? "ricercaGareRUP_CS"
						: "";
	
	jspRicerca = jspRicerca	+ "?"	+ ParametriServlet.FROM_RICERCA + "=" + fromRicerca;

	if ( righeVisualizzate > tableBeanSize ) { 
		righeVisualizzate = tableBeanSize; 
	} %>
	<div id="bodypage">
		<div class="bodypage-e">
	
		<h1>Ricerca Gare</h1>
		<%@ include file="include/gestisciErrore.inc" %>
		<div class="hmenu">
			<ul>
			<li><a href="<%= jspGestione %>" title="Nuova Ricerca">Nuova Ricerca</a></li>
			<li>&nbsp;&nbsp;</li>
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
			<% String id_soglia = (String)request.getSession().getAttribute(ParametriServlet.ID_SOGLIA_IMPORTO);

			if(SimogFlags.isEsportaCSV() == true && (user.isRUP() || user.isCS() || user.isRASA())){ //|| user.isOssReg() %>
				  <li><a href="javascript:avviso(<%= printExportAlert %>,<%=maxRigheExport %>);visualizza('ricercaGareRUP_CS?<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.ESPORTAELENCO %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>');">Esporta elenco</a></li>
			<% }
			if(user.isAmministratore()){	
				String msg = "Esporta elenco" + (tableBeanSize > Costanti.CSV_MAX_RECORDS ? " (primi " + SimogProperties.getInstance().getELEMENTI_EXPORT() +  " elementi)" : "");
			%>
			  	 <li><a href="ricercaGareExt?<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.ESPORTAELENCO %>&<%= ParametriServlet.START_ROW %>=0"><%=msg %></a></li>
		<% }
			if(user.isOssReg()){%>
				  <li><a href="javascript:visualizza('ricercaGareRUP_CS?<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.ESPORTAELENCO %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>');">Esporta elenco</a></li>
			<% } %>
			
			<%-- 
			<p><%= "startRowInt " + (startRowInt)%></p>
			<p><%= "righeVisualizzate " + (righeVisualizzate)%></p>
			<p><%= "tableBean.getFullSize()- resto " + (listaGare.getFullSize()- resto)%></p>
			<p><%= "fine elenco " + fineElenco%></p>
			<p><%= "if( "+righeVisualizzate+" < "+(listaGare.getFullSize()- resto)+" )"%></p>
			--%>
			</ul>
		</div>		
	<div class="testo">
<h3>Visualizzati <%= righeVisualizzate %>/<%= listaGare.getFullSize() %> Elementi</h3>
<!--  SCROLL -->
	<div class="scroll">
	
	
	<!-- SCROLL INSIDE -->
	<div class="scrollInside">
		<% TableBeanRow currentRow = null; %>
		<% TableBeanRow previousRow = null; %>
		<% String previousGara = null; %>
		<% String idGara = null; %>	
		<% boolean nuovaGara = true; %>
		<% boolean nienteLotti = true; %>
		<% String display = null; %> 
		<% boolean isDelega = false; %>
		<% boolean isUserDelegato = false; %>
		<% int idDelega = 0; %>
		<% boolean cigPrendibile = false; %>
		
	
		<% for ( int rowIndex = 0; rowIndex < listaGare.getTableSize(); rowIndex++ ) { 
		
			 currentRow = listaGare.getRow(rowIndex); 
			
			if(currentRow.getNulledField("DC_ESITO_PROCEDURA") != null){%><!-- MAC 57135 -->
			
				<% idGara = currentRow.getNulledField(GARA.ID_GARA); %>
				<% nuovaGara = ! idGara.equalsIgnoreCase(previousGara); %>
				<% nienteLotti = "0".equals(currentRow.getNulledField(LOTTO.ID_LOTTO)); %>
				<% display = nienteLotti ? "style='display:none'" : ""; %>
				<% String dataGaraPubblicata = currentRow.getNulledField(GARA.DATA_PERFEZIONAMENTO_BANDO); %>
				

				<% boolean delegaPresente = currentRow.getNulledField("T_ID_F_DELEGATE") != null  
                        && !"".equals(currentRow.getNulledField("T_ID_F_DELEGATE"));
				      isUserDelegato = delegaPresente
			                        && user.getLogin().equals(currentRow.getNulledField(GARA.CF_UTENTE));
				
				isDelega = currentRow.getNulledField("T_ID_F_DELEGATE") != null  
			              && !"".equals(currentRow.getNulledField("T_ID_F_DELEGATE")) 
		            	  && !currentRow.getNulledField(GARA.CF_AMMINISTRAZIONE).equals(currentRow.getNulledField("T_CF_AMM_AGENTE"));
				    if(isDelega)
				    	idDelega = Integer.parseInt(currentRow.getNulledField("T_ID_F_DELEGATE"));
				//Se la gara e' in delega (non ancora presa in carico dalla delegante) ed e' connesso il RUP delegato
				//verifica i casi in cui non puo' vedere il pulsante
				  if((isUserDelegato && isDelega) || Costanti.DELEGA3 ==  idDelega){
					  isDelega = false; 
				  }
				
				if(idDelega!=0){
					boolean datiComuniConfermata = !(currentRow.getNulledField(INFO_AGGIUDICAZIONI.ID_INFO).equals("")) &&
							currentRow.getNulledField(INFO_AGGIUDICAZIONI.CF_AMM).equals(currentRow.getNulledField(GARA.CF_AMMINISTRAZIONE))
							&& (currentRow.getNulledField("DC_STATO").equals("2"));
					boolean datiComuniAggiudicata = currentRow.getNulledField("DC_ESITO_PROCEDURA").equals(EsitoEnum.AGGIUDICATA.codice());
					if(idDelega==Costanti.DELEGA4){
						cigPrendibile = datiComuniConfermata;
					} else if(idDelega==Costanti.DELEGA1 || idDelega==Costanti.DELEGA2){
						//Se il CIG non ha esito aggiudicato, consenti la presa in carico
						cigPrendibile = datiComuniConfermata && !datiComuniAggiudicata;

						//Se il CIG ha esito aggiudicato, verifica lo stato dell'aggiudicazione
						if(!cigPrendibile && datiComuniConfermata && datiComuniAggiudicata){
							if(idDelega==Costanti.DELEGA1 || idDelega==Costanti.DELEGA2){
								cigPrendibile = currentRow.getNulledField("HAS_AGGIUDICAZIONE")!=null && !"".equals(currentRow.getNulledField("HAS_AGGIUDICAZIONE"));
							//3.04.14 commentata parte sotto, si comporta come per delega 1
							//} else if(idDelega==Costanti.DELEGA2){
								//cigPrendibile = currentRow.getNulledField("HAS_STIPULA")!=null && !"".equals(currentRow.getNulledField("HAS_STIPULA"));								
							}
						}
						
					}
				}
				
				//Se la gara e' in delega (non ancora presa in carico dalla delegante) ed e' connesso il RUP delegante
				//verifica i casi in cui non puo' vedere il pulsante
			/*	  if(!isUserDelegato && isDelega){
					  //Se la delega e' 1 o 2 oppure non ci sono scchede comuni, non vedere mai il pulsante
					  isDelega = Costanti.DELEGA1 == Integer.parseInt(currentRow.getNulledField(FUNZIONI_DELEGATE_GARA.ID_F_DELEGATE)) ||
							     Costanti.DELEGA2 == Integer.parseInt(currentRow.getNulledField(FUNZIONI_DELEGATE_GARA.ID_F_DELEGATE)) ||
							     "".equals(currentRow.getNulledField(INFO_AGGIUDICAZIONI.ID_INFO));
				  }*/
				
				              %>
				
			<%-- Gestione delle gare distribuite tra una pagina e l'altra											--%>
			<%-- Quando ci si imbatte in una riga "sentinella" con i campi tutti -1 allora la gara e distribuita	--%>
			<%-- Si termina e si inserisce il testo "Continua"														--%>

			<% 	if ("-1".equals(currentRow.getNulledField(LOTTO.ID_LOTTO))) { %>
					<th class="garaTh" colspan="6" align="center">&lt&lt Nella prossima pagina altri lotti appartenenti alla gara numero <%= previousGara %> &gt&gt</th>
			<%		break;
			   	}
			%>	
			
			<%--------------------------------------------------------------------------------------------------------%>
			
			<% if ( nuovaGara ) { %>
			
				<% if ( nuovaGara && previousGara != null ) { %>
					</table>
						<tr>
							<td>
								 <p><span class="risalto"><a href="visualizzaDettaglio?<%= ParametriServlet.SESSION_ID_GARA %>=<%= previousGara %>&<%= ParametriServlet.FROM_RICERCA %>=<%= fromRicerca %>">Dettaglio Gara</a></span></p>
							</td>
						</tr>
					</div>
					</div>
				<% } %>
				
				<h4>Informazioni Gara</h4>
				<div class="elenco">
				<div class="gara">
					<table>
						<tr>
							<th class="garaTh" width="40%">Amministrazione Competente</th>
							<td class="garaTd"><%= currentRow.getNulledField( GARA.DENOM_AMMINISTRAZIONE ) %></td>
						</tr>
						<tr>
							<th class="garaTh" width="40%">Stazione Appaltante</th>
							<td class="garaTd"><%= currentRow.getNulledField( GARA.DENOM_STAZIONE_APPALTANTE ) %></td>
						</tr>
						<tr>
							<th class="garaTh" width="40%">Numero Gara</th>
							<td class="garaTd"><%= currentRow.getNulledField( GARA.ID_GARA ) %></td>
						</tr>				
						<tr>
							<th class="garaTh" width="40%">Oggetto della Gara</th>
							<td class="garaTd"><%= PageHelper.formattaTesto(currentRow.getNulledField( GARA.OGGETTO )) %></td>
						</tr>
						<tr>
<% String garaunformattedImporto = currentRow.getNulledField(GARA.IMPORTO_GARA);
	String garaImporto = PageHelper.IMPORTO_ND;
	if(!"".equals(garaunformattedImporto) 
			&& new BigDecimal(garaunformattedImporto).compareTo(new BigDecimal(Costanti.IMPORTO_FUORI_SCALA)) != 0){
					garaImporto = PageHelper.getFormattedImporto(garaunformattedImporto);}	%>
							<th class="garaTh" width="40%">Importo Gara</th>
							<td class="garaTd"><%= garaImporto %></td>
						</tr>
						<tr>
							<th class="garaTh" width="40%">Data Creazione</th>
							<td class="garaTd"><%= PageHelper.getFormattedDate( currentRow.getNulledField( GARA.DATA_CREAZIONE ) ) %></td>
						</tr>
						<tr>
							<th class="garaTh" width="40%">Data perfezionamento bando</th>
							<td class="garaTd"><%= PageHelper.getFormattedDate( currentRow.getNulledField( GARA.DATA_PERFEZIONAMENTO_BANDO ) ) %></td>
						</tr>	
						<tr>
							<th class="garaTh" width="40%">Stato gara</th>
							<td class="garaTd"><%= currentRow.getNulledField( "STATOSCHEDA" )%></td>
						</tr>
						
						<% if(delegaPresente) { %>
						<tr>
							<th class="garaTh" width="40%">Funzione delegata</th>
							<td class="garaTd"><%= currentRow.getNulledField( FUNZIONI_DELEGATE.TABLE_NAME )%></td>
						</tr>
						<tr>
							<th class="garaTh" width="40%">CF Amministrazione delegata</th>
							<td class="garaTd"><%= currentRow.getNulledField( "T_CF_AMM_DELEGATA" )%></td>
						</tr>
						<tr>
							<th class="garaTh" width="40%">Denominazione Amministrazione delegata</th>
							<td class="garaTd"><%= currentRow.getNulledField( "T_DEN_AMM_DELEGATA" )%></td>
						</tr>
						<tr>
							<th class="garaTh" width="40%">CF Amministrazione delegante</th>
							<td class="garaTd"><%= currentRow.getNulledField( "T_CF_AMM_AGENTE" )%></td>
						</tr>
						<tr>
							<th class="garaTh" width="40%">Denominazione Amministrazione delegante</th>
							<td class="garaTd"><%= currentRow.getNulledField( "T_DEN_AMM_AGENTE" )%></td>
						</tr>
						<% } %>
					</table>		
				<h5 <%= display %> >Informazioni Lotti</h5>
				<% previousGara = idGara; %>
			<table width="100%" <%= display %> >
			<tr>
			<th class="garaTh">CIG</th>
			<th class="garaTh">Oggetto Lotto</th>
			<th class="garaTh">Importo &euro;</th>
			<th class="garaTh">Data Pubblicazione</th>
			<th class="garaTh">Stato Lotto</th>
			<th class="garaTh">Esito Procedura</th>
			<% if( (user.isRUP() && !Costanti.FLAG_VALORE_SI.equals(fromGare)) || user.isCS() || user.isOssReg() || user.isAmministratore()) { %>
				<th class="garaTh"></th>
			<% } %>
			</tr>
			<% } %>
			<% previousRow = currentRow; 
			 boolean cancellato = ! "".equals( currentRow.getNulledField( LOTTO.DATA_CANCELLAZIONE_LOTTO ) ) || ! "".equals( currentRow.getNulledField( LOTTO.DATA_INIB_PAGAMENTO ) ); 
			 String statoLotto = ! "".equalsIgnoreCase( currentRow.getNulledField( LOTTO.DATA_PUBBLICAZIONE ) ) ? "PERFEZIONATO" : "IN LAVORAZIONE"; 
			 statoLotto = cancellato ? "CANCELLATO" : statoLotto;
			 String idEsitoProcedura = currentRow.getNulledField("DC_ESITO_PROCEDURA");
			 String descrizioneEsitoProcedura="";
			 if(idEsitoProcedura != null && !idEsitoProcedura.isEmpty()){
				 descrizioneEsitoProcedura=EsitoEnum.getEnumByCodice(idEsitoProcedura).descrizione().toUpperCase();
			 }
			 String dataPubblicazione = currentRow.getNulledField(LOTTO.DATA_PUBBLICAZIONE); 
			 String dataScadenza = currentRow.getNulledField(LOTTO.DATA_SCADENZA_PAGAMENTI);
			 // PP se non esiste la data scdenza pagamenti non posso inserire i dati comuni
			 if ("".equals(dataScadenza))
			    dataScadenza = "99999999"; // forzo massima data per non accnedere il pulsante
			 %>
			<tr>
			<% String currentCIG = currentRow.getNulledField(LOTTO.CIG) + currentRow.getNulledField(LOTTO.CIG_KKK); %>
			<% boolean cigWS = false; %> 
			
			<% if(user.isRUP() && !Costanti.FLAG_VALORE_SI.equals(fromGare)){
			cigWS = (currentRow.getNulledField(CIG_STORIA.APPLICAZIONE).trim().compareToIgnoreCase(CIGBean.APPL_WS)==0	) ; 
			}%>
			<% String sommaUrgenza = currentRow.getNulledField(LOTTO.SOMMA_URGENZA); 
				String unformattedImporto = currentRow.getNulledField(LOTTO.IMPORTO_LOTTO);
				String lottoImporto = PageHelper.IMPORTO_ND;
				if(!"".equals(unformattedImporto) && new BigDecimal(unformattedImporto).compareTo(new BigDecimal(Costanti.IMPORTO_FUORI_SCALA)) != 0){
								lottoImporto = PageHelper.getFormattedImporto(unformattedImporto);
				}
			%>
			<td class="garaTd"><%= PageHelper.getCIG( currentCIG, sommaUrgenza, currentRow.getNulledField(LOTTO.DATA_CREAZIONE_LOTTO) )  %></td>
        	<td class="garaTd"><%= PageHelper.formattaTesto(currentRow.getNulledField(LOTTO.TABLE_NAME + LOTTO.OGGETTO)) %></td>
         	<td nowrap class="garaTd"><%= lottoImporto %></td>
        	<td class="garaTd"><%= PageHelper.getFormattedDate( dataPubblicazione ) %></td>
			<td class="garaTd"><strong><%= statoLotto %>
			<!-- MEV 25895 POP-UP -->
            <% if(statoLotto.equalsIgnoreCase("PERFEZIONATO")){%>
            	<img onclick="apripopupPubblicaBandoGara('popupPubblicaBandoGara.jsp');" src="img/icon14bc.gif">
            <% } %>
            </strong> </td>
			<td class="garaTd"><strong><%= descrizioneEsitoProcedura %></strong></td>
			

				<%
				   String cfAmmDelegante = (String)currentRow.getNulledField( "T_CF_AMM_AGENTE" ); //MAC 34163 3.04.8 
				   //out.print("<br>cfAmmDelegante : "+cfAmmDelegante); //MAC 34163 3.04.8 

				   String idFDelegate = (String)currentRow.getNulledField("T_ID_F_DELEGATE") ; //MAC 34163 3.04.8
				   //out.print("<br>idFDelegate : "+idFDelegate);  //MAC 34163 3.04.8
				   
				   String vis = (String)request.getAttribute(ParametriServlet.FROM_RICERCA);
				   //out.print("<br>vis : "+vis);  //MAC 34163 3.04.8
				   
				   //out.print("<br>isAccQuadroNc : "+isAccQuadroNc);  //MAC 34163 3.04.8
				   
			       boolean disablePresInCarAggiuStipContrat = ( (!"".equalsIgnoreCase(cfAmmDelegante) && idFDelegate != null && idFDelegate.equalsIgnoreCase("3"))) ? true : false;
			       //out.print("<br>disablePresInCarAggiuStipContrat : "+disablePresInCarAggiuStipContrat);  //MAC 34163 3.04.8
				   
				   boolean hasAwards = !(currentRow.getNulledField(INFO_AGGIUDICAZIONI.ID_INFO).equals("")); 
								   String parametri = ParametriServlet.FIELD_NAME_ID_LOTTO+"="+currentRow.getNulledField(LOTTO.ID_LOTTO) + "&" + ParametriServlet.START_ROW + "=" +startRow + (request.getAttribute(ParametriServlet.ACTION_GET_LIST) != null ?  ("&" +  ParametriServlet.ACTION_GET_LIST + "=" + request.getAttribute(ParametriServlet.ACTION_GET_LIST)) :"");
										if(!hasAwards)parametri += "&toDo=new";
									String href = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA+"?"+parametri; 
									String titlehref = hasAwards ? "Riepilogo Scheda" : "Crea Scheda"; 
									String hrefPresa = ParametriServlet.SRV_PRESA_IN_CARICO+"?"+parametri; 
									String hrefPresaSiDelegante = ParametriServlet.SRV_PRESA_IN_CARICO+"?fromDelegante=SI&"+parametri; 
									String hrefPresaNoDelegante = ParametriServlet.SRV_PRESA_IN_CARICO+"?fromDelegante=NO&"+parametri; 
									String titlehrefPresa = "Presa in carico"; 
									
									boolean okTasto = false;
									// OSSR e ADMIN se il CIG ha schede
									if((user.isOssReg() || user.isAmministratore() || user.isRASA()) && hasAwards ) okTasto = true;
									
									// RUP se ha schede o e nelle condizioni per poterle inserire
									if(user.isRUP() && hasAwards) okTasto = true;
									
									Integer modoReal = new Integer(0);
									if(!"".equals(currentRow.getNulledField(GARA.ID_MODO_REAL)))
										modoReal = Integer.parseInt(currentRow.getNulledField(GARA.ID_MODO_REAL));
									
									if(user.isRUP()
										 	&& !cancellato 
								 			&& !"".equals(dataPubblicazione) 
								 			//&& PageHelper.getCurrentDate().compareTo(dataScadenza) > 0 
								 			&& ((SimogFlags.is30233_RFWEBSC00Active() && SimogValidator.checkOkSchede3023(Float.parseFloat(currentRow.getNulledField(LOTTO.IMPORTO_LOTTO))
								 			         									, currentRow.getNulledField(LOTTO.TIPO_CONTRATTO_LOTTO)
								 			         									, dataPubblicazione
								 			      	   								, currentRow.getNulledField(GARA.TIPO_SCHEDA_GARA)
								 			      		   							, currentRow.getNulledField(LOTTO.FLAG_ESCLUSO)
								 			      			   						, modoReal))
								 			//MAC #2119
								 			//Verifica se la scelta contraente del lotto sia diversa dall'affidamento diretto per variante superiore al 20%
								 			&& !Costanti.SCELTA_CONTRAENTE_AFF_DIRETTO.equals(currentRow.getNulledField(LOTTO.ID_SCELTA_CONTRAENTE))
								 			//Fine MAC #2119
								 			
								 			//TICKET ALM #659 - 3.04.4
								 			&& !isDelega
								 					)
										) okTasto = true;

									String pubblicata=null;
									String tipoOperazione=null;
									int salta=0;
									int listaInvitati=0;
									String elencoInvitatiTrovati = currentRow.getNulledField(Costanti.PRESENTI_INVITATI);
									String storicoHasLetteraInvito = currentRow.getNulledField(Costanti.STORICO_HAS_LETTERA_INVITO);

								   pubblicata=currentRow.getNulledField(PUBBLICAZIONI.ID_PUBBLICAZIONE);
									tipoOperazione=currentRow.getNulledField(PUBBLICAZIONI.TIPO_OPERAZIONE);
									boolean inserireInvitati = (hasAwards == false) && !pubblicata.equals("") 
										&& elencoInvitatiTrovati.equals("NO")
										&& (tipoOperazione.equals(PubblicazioneBean.TipoOperazione.LETTINV.getCodice()) ||
											"SI".equals(storicoHasLetteraInvito));
				%>											
					
					<td nowrap colspan="1" class="hmenu" align="center">&nbsp;
				
					<% if(okTasto && (!inserireInvitati || cigWS==true)){%>
								<p><a href="<%= href %>"><%= titlehref %></a></p>
					<% } %>
												
						
						<% if(!cancellato && hasAwards && user.isRUP() && !Costanti.FLAG_VALORE_SI.equals(fromGare) && !isAccQuadroNc) { 
						   boolean cdcPresente=false;
						   boolean saPresente=false;
						   boolean saDeleganteLogged=false;
						   boolean cigPresoInCarico=false;
						   
						     //Gestione in caso di RUP diversi
						     //Pulsante presa in carico da RUP a RUP appartenenti allo stesso CDC
						       if(!currentRow.getNulledField(INFO_AGGIUDICAZIONI.CF_RUP).equalsIgnoreCase(user.getLogin())){
						    	     
						             Hashtable uffici = user.getUfficiByProfilo(ProfiloEnum.RUP);
						             Enumeration<String> listaUffici = uffici.keys();
						             //cigPresoInCarico=delegaPresente && currentRow.getNulledField(INFO_AGGIUDICAZIONI.CF_AMM).equals(currentRow.getNulledField("T_CF_AMM_AGENTE"));
						             while(listaUffici.hasMoreElements()) {
						                 String key = listaUffici.nextElement();
						                 if(key.equals(currentRow.getNulledField(GARA.ID_STAZIONE_APPALTANTE))){
						                	 cdcPresente=true;
						                	 break;
						                 }
						             }
						             
						            // if(cdcPresente && !cigPresoInCarico){ 
						            if(cdcPresente ){ 
						              %>
						            	
						            	<%  //if(!disablePresInCarAggiuStipContrat){ %> <!-- MAC 34163 3.04.8 -->
							            	  						            	   
									            <p><a href="<%= hrefPresaNoDelegante %>">Presa in carico</a></p>
						            	        <!-- FINE MAC 34163 3.04.8 -->	
						               <% //}  
						            	}
						             
						           }
						            //MAD 58518 3.04.14
						             //Se il cig e' gestito con funzione di delega, verifica se mostrare il pulsante di presa in carico
						             if(delegaPresente) 
						                  {
						            	       cigPresoInCarico=currentRow.getNulledField(INFO_AGGIUDICAZIONI.CF_AMM).equals(currentRow.getNulledField("T_CF_AMM_AGENTE"));
						            	 
								            	 Hashtable collaborazioni = user.getAmministrazioniByProfilo(ProfiloEnum.RUP);
									    	     Enumeration<String> listaCollaborazioni = collaborazioni.keys();
									    	   
									    	     while(listaCollaborazioni.hasMoreElements()) {
									                 String key = listaCollaborazioni.nextElement();
									                 if(key.equals(currentRow.getNulledField("T_CF_AMM_AGENTE"))){
									                	 saDeleganteLogged=true;
									                 }
									             }
									    	     
												//3 condizioni: delegante loggato, il cig e' gia' in carico alla delegante, il cig e' prendibile   
									    	     if(saDeleganteLogged && !cigPresoInCarico && cigPrendibile){ %>
									            	
									            	<%  if(!disablePresInCarAggiuStipContrat){ %> <!-- MAC 34163 3.04.8 -->
									            	  		<!-- MAC 34163 3.04.8 -->						            	   
									            			<p><a href="<%= hrefPresaSiDelegante %>">Presa in carico CIG delegato</a></p>
									            			<!-- FINE MAC 34163 3.04.8 -->	
										        <% } }
						             }
						       }
						     
						     
						     
						     
						 %>
						 
						 
						<%-- 
						<%  //------------------------------------------------------
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
									<li <%=noDisplay %>> 						
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
							 <td <%=noDisplay %> nowrap="nowrap">
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
						     
						     //_______________________________________________________%>
						     --%>
						
						
						
						
						<%-- 
					<% if(!cancellato && hasAwards && (user.isRUP() 
							&& !Costanti.FLAG_VALORE_SI.equals(fromGare)) 
							&& !currentRow.getNulledField(INFO_AGGIUDICAZIONI.CF_RUP).equalsIgnoreCase(user.getLogin())
							&& !isAccQuadroNc //TICKET ALM - 3.04.3 #3916
							&& !okPresaInCaricoDelegaCig
							&& (currentRow.getNulledField("T_CF_AMM_AGENTE")==null || "".equals(currentRow.getNulledField("T_CF_AMM_AGENTE")))
							&& !isDelega
						) { %>
							<p><a href="<%= hrefPresa %>"><%= titlehrefPresa %></a>111</p>
					<% } else if(!cancellato && (user.isRUP() 
							&& !Costanti.FLAG_VALORE_SI.equals(fromGare))
							//&& !currentRow.getNulledField(INFO_AGGIUDICAZIONI.CF_RUP).equalsIgnoreCase(user.getLogin())
							&& !currentRow.getNulledField(INFO_AGGIUDICAZIONI.CF_AMM).equals(currentRow.getNulledField("T_CF_AMM_AGENTE"))
							&& currentRow.getNulledField(GARA.CF_AMMINISTRAZIONE).equals(currentRow.getNulledField(INFO_AGGIUDICAZIONI.CF_AMM))
							&& !isAccQuadroNc
							&& okPresaInCaricoDelegaCig) { %>
							<p><a href="<%= hrefPresa %>"><%= titlehrefPresa %></a>222</p>
						<% } %>--%>
						
					<%//MIO
					if(inserireInvitati && cigWS==false){
							String noCreaScheda = user.isRUP() ? "INSERIRE INVITATI" : ""; %>
							<p><strong><%= noCreaScheda %></strong></p>
					<%	} %>
					</td>		

			</tr>	
		<%} } %> <!-- MAC 57135 -->
		<%// Chiusura ciclo %>
		</table>
		<p><span class="risalto"><a href="visualizzaDettaglio?<%= ParametriServlet.SESSION_ID_GARA %>=<%= previousGara %>&<%= ParametriServlet.FROM_GARE %>=<%= fromGare %>&<%= ParametriServlet.FROM_RICERCA %>=<%= fromRicerca %>">Dettaglio Gara</a></span></p>
		</div>
		</div>
		<!-- Chiusura Ultima gara -->

		</div>
		<!-- INSIDE SCROLL FINE -->
		
</div>
<!-- Scroll FINE -->


</div>

		</div>
	</div>
					<%@ include file="include/newfooter.inc" %>
</div>

<%// listaGare.printHTMLTable(new java.io.PrintWriter ( out ) ); %>
</body>
<%@page import="it.avlp.simog.beans.CIGBean"%>
<%@page import="it.avlp.simog.db.Costanti"%>
<%@page import="it.avlp.simog.validatore.SimogValidator"%>
<%@page import="it.avlp.simog.validatore.SimogFlusso"%>
<%@page import="it.avlp.simog.db.SimogFlags"%>
</html>
<%}catch(Exception e){e.printStackTrace();}%>