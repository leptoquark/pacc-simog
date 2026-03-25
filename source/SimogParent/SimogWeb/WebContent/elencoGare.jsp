<% // ******** PAGINA NON USATA ***************** %>
<% // ******** PAGINA NON USATA ***************** %>
<% // ******** PAGINA NON USATA ***************** %>
<% // ******** PAGINA NON USATA ***************** %>
<% // ******** PAGINA NON USATA ***************** %>
<% // ******** PAGINA NON USATA ***************** %>
<% // ******** PAGINA NON USATA ***************** %>


<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ include file="include/newbasicHeader.inc" %>

<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.db.advanced.*"%>
<%@ page import="it.avlp.simog.common.servlet.*"%>

<!-- MEV 25895 POP-UP -->
<script type="text/javascript" src="xtree/treeutils.js"></script>
<script type="text/javascript" src="script/other/jquery.js"></script>

<script>
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
    height: height: 550,
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

<% TableBean listaGare = (TableBean)request.getAttribute(it.avlp.simog.servlet.ParametriServlet.TABLEBEAN); %>

<title>SIMOG - Ricerca Gara - Elenco Gare</title>
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

	<div id="bodypage">
			<%@ include file="include/menu/menuGara.inc"%>
	
		<div class="bodypage-e">
				
		<h1>Ricerca Gare</h1>
		<%@ include file="include/gestisciErrore.inc" %>
				<div class="hmenu">
			<ul>
			<li><a title="Nuova Ricercaaaaaaaaaaa" href="<%= ParametriServlet.JSP_GESTIONE_GARE_EXT %>">Nuova Ricerca</a></li>
			</ul>
		</div>		
		
		<% String currentGara = null; %>
		<% String idGara = null; %>	
		
	
		<% for ( int rowIndex = 0; rowIndex < listaGare.getTableSize(); rowIndex++ ) { %>
		
			<% TableBeanRow currentRow = listaGare.getRow(rowIndex); %>
			
			<% idGara = currentRow.getNulledField(GARA.ID_GARA); %>
			
			<% boolean nuovaGara = ! idGara.equalsIgnoreCase(currentGara);%>
						
			<%  if ( nuovaGara ) { %>
			
				<% if ( nuovaGara && currentGara != null ) { %>
					</table>
					<a href="visualizzaDettaglio?idGara=<%= idGara %>">Dettaglio Gara</a>					
					</div>
					</div>
				<% } %>
				<div class="elenco">
				<h4>Informazioni Gara</h4>
				<div class="gara">	
					<table>
						<tr>
							<th class="garaTh" width="40%">Amministrazione Competente</th>
							<% String cf_amministrazione = currentRow.getNulledField( GARA.CF_AMMINISTRAZIONE ); %>
							<td class="garaTd"><%= user.getDenomAmministrazByCf( cf_amministrazione ) %></td>
						</tr>
						<tr>
							<th class="garaTh" width="40%">Stazione Appaltante</th>
							<td class="garaTd"><%= user.getDenominazioneUfficioById( currentRow.getNulledField( GARA.ID_STAZIONE_APPALTANTE ) ) %></td>
						</tr>					
						<tr>
							<th class="garaTh" width="40%">Numero Gara</th>
							<td class="garaTd"><%= currentRow.getNulledField( GARA.ID_GARA ) %></td>
						</tr>					
						<tr>
							<th class="garaTh" width="40%">Oggetto della Gara</th>
							<td class="garaTd"><%= currentRow.getNulledField( GARA.OGGETTO ) %></td>
						</tr>
						<tr>
							<th class="garaTh" width="40%">Data Creazione</th>
							<td class="garaTd"><%= PageHelper.getFormattedDate( currentRow.getNulledField( GARA.DATA_CREAZIONE ) ) %></td>
						</tr>
					</table>		
				<h5>Informazioni Lotti</h5>
			<table width="100%">
			<tr>
			<th class="garaTh" width="40%">CIG</th>
			<th class="garaTh" width="40%">Oggetto Lotto</th>
			<th class="garaTh" width="40%">Importo</th>
			<th class="garaTh" width="40%">Data Pubblicazione</th>
			<th class="garaTh" width="40%">Stato del lotto</th>
			</tr>
			
			<% boolean cancellato = ! "".equals( currentRow.getNulledField( LOTTO.DATA_CANCELLAZIONE_LOTTO ) ) || ! "".equals( currentRow.getNulledField( LOTTO.DATA_INIB_PAGAMENTO ) ); %>
			<% String statoLotto = ! "".equalsIgnoreCase( currentRow.getNulledField( LOTTO.DATA_PUBBLICAZIONE ) ) ? "PUBBLICATO" : ""; %>
			<% statoLotto = cancellato ? "CANCELLATO" : statoLotto; %>
			
			<tr>
			<% String currentCIG = currentRow.getNulledField(LOTTO.CIG) + currentRow.getNulledField(LOTTO.CIG_KKK); %>
			<% String sommaUrgenza = currentRow.getNulledField(LOTTO.SOMMA_URGENZA); %>
			
            <td class="garaTd"><%= PageHelper.getCIG( currentCIG, sommaUrgenza, currentRow.getNulledField(LOTTO.DATA_CREAZIONE_LOTTO) )  %></td>

            <td class="garaTd"><%= currentRow.getNulledField(LOTTO.TABLE_NAME + LOTTO.OGGETTO) %></td>
            <td class="garaTd"><%= currentRow.getNulledField(LOTTO.IMPORTO_LOTTO) %></td>
            <td class="garaTd"><%= PageHelper.getFormattedDate( currentRow.getNulledField(LOTTO.DATA_PUBBLICAZIONE) ) %></td>
            <td class="garaTd"><strong><%= statoLotto %>
			<!-- MEV 25895 POP-UP -->
            <% if(statoLotto.equalsIgnoreCase("PERFEZIONATO")){%>
            	<img onclick="apripopupPubblicaBandoGara('popupPubblicaBandoGara.jsp');" src="img/icon14bc.gif">
            <% } %>
            </strong> </td>
			</tr>
		<% } %>
		<%// Chiusura ciclo %>
		</table>
		<a href="visualizzaDettaglio?idGara=<%= idGara %>">Dettaglio Gara</a>		
		</div>
		</div>
		<!-- Chiusura Ultima gara -->

		</div>
	</div>
</div>

</body>
</html>
