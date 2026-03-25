<%@page import="java.util.Map"%>
<% try{ %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.db.advanced.*"%>
<%@ page import="it.avlp.simog.common.servlet.*"%>
<%@ page import="it.avlp.simog.beans.CIGBean"%>
<%@ page import="it.avlp.simog.db.Costanti"%>
<%@ page import="it.avlp.simog.validatore.SimogValidator"%>
<%@ page import="it.avlp.simog.db.SimogFlags"%>
<%@ page import="it.avlp.simog.beans.PubblicazioneBean"%>

<%@ include file="include/controlloSessione.inc" %>
<%@ include file="include/newbasicHeader.inc" %>

<script type="text/javascript">
function visualizza(url){
 var finestra = window.open(url,"window","scrollbars=1,width=550,height=250,left=240,top=180");
}

function enableProcedi(flag){
	if(flag.value == 'S' || flag.value == 'N'){
		flag.form.btn_procedi.disabled = false;
	}
}
</script>

<title>SIMOG - <utils:message key="elenco.elencoCigCupIntegrare" /></title>
</head>      

<body>
<div id="gabbia">

<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuGara.inc" %>

<% int maxRigheVisualizzabili = Integer.parseInt( (String)request.getAttribute( ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI ) ); 
   Integer startRowInt = (Integer)request.getAttribute( ParametriServlet.START_ROW ); 
	TableBean listaGare = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); 
	Map<String, String> mappaCup = (Map<String, String>)request.getAttribute(ParametriCup.INTEGRAZIONE_CUP); 
	int tableBeanSize = listaGare.getFullSize();
	int startRow = startRowInt.intValue(); 
	//attenzione aggiunto il controllo per le pagine che hanno la stringa "continua"
	//perche la dimensione del table bean in quel caso e' maggiore di uno rispetto a quello che dovrebbe.
	int righeVisualizzate = startRow + (listaGare.getTableSize() > maxRigheVisualizzabili ? maxRigheVisualizzabili : listaGare.getTableSize());
	long resto = (tableBeanSize % maxRigheVisualizzabili);
	long fineElenco = tableBeanSize - resto - maxRigheVisualizzabili - (resto == 0 ? maxRigheVisualizzabili : 0) ; 
	String fromGare = (String) request.getAttribute(ParametriServlet.FROM_GARE);
	String jspGestione = "elencoCigIntegrazioneCup";
	String jspRicerca = "elencoCigIntegrazioneCup?nav=yes";
	if ( righeVisualizzate > tableBeanSize ) { 
		righeVisualizzate = tableBeanSize; 
	} 
	String target = "elencoCigIntegrazioneCup";
%>
	<div id="bodypage">
	<div class="bodypage-e">
		<h1><utils:message key="elenco.elencoCigCupIntegrare" /></h1>
		<%@ include file="include/gestisciErrore.inc" %>
		<div class="hmenu">
			<ul>
			<li><a href="<%= jspGestione %>" title="<utils:message key="dettaglio.nuovaRicerca" />"><utils:message key="dettaglio.nuovaRicerca" /></a></li>
			<li>&nbsp;&nbsp;</li>
			<% if ( startRowInt >  0 ) { %>
				<li><a href="<%= jspRicerca %>" title="<utils:message key="log.visualizzaPrimaPagina" />"><utils:message key="log.inizioElenco" /></a></li>
			<% } 
			else {%> <li><a id="disabledMenu" title="<utils:message key="log.visualizzaPrimaPagina" />"><utils:message key="log.inizioElenco" /></a></li> <% } %>
			
			<% if ( righeVisualizzate >  maxRigheVisualizzabili ) { %>
				<li><a href="<%= jspRicerca %>&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.REGRESS %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>" title="<utils:message key="log.visualizzaPrecedenti" />"><utils:message key="log.precedenti" /></a></li>
			<% }
			else {%> <li><a id="disabledMenu" title="<utils:message key="log.visualizzaPrecedenti" />"><utils:message key="log.precedenti" /></a></li> <% } %>
			
			<% if ( tableBeanSize - righeVisualizzate > 0 ) { %>
				<li><a href="<%= jspRicerca %>&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.PROGRESS %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>" title="<utils:message key="log.visualizzaSuccessive" />"><utils:message key="log.successive" /></a></li>
			<% }
			else {%> <li><a id="disabledMenu" title="<utils:message key="log.visualizzaSuccessive" />"><utils:message key="log.successive" /></a></li> <% } %>
			
			<% if ( righeVisualizzate != tableBeanSize ) { %>
				<li><a href="<%= jspRicerca %>&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.PROGRESS %>&<%= ParametriServlet.START_ROW %>=<%= fineElenco %>" title="<utils:message key="log.visualizzaUltimaPagina" />"><utils:message key="log.fineElenco" /></a></li>
			<% }
			else { %>
				<li><a id="disabledMenu" title="<utils:message key="log.visualizzaUltimaPagina" />"><utils:message key="log.fineElenco" /></a></li>
			<%}%>
			</ul>
		</div>		
		<div class="testo">
			<h3><utils:message key="elenco.visualizzati" /> <%= righeVisualizzate %>/<%= listaGare.getFullSize() %> <utils:message key="elenco.elementi" /></h3>
			<!--  SCROLL -->
			<div class="scroll">
				<!-- SCROLL INSIDE -->
				<div class="scrollInside">
				<div class="elenco">
				<div class="gara">
				<table width="100%" >
							<tr>
							<th class="garaTh">&nbsp;&nbsp;&nbsp;<a href="<%=target%>?nav=yes&ord=yes&<%=ParametriServlet.ORDER_FIELD%>=<%=LOTTO.T_CIG%>">CIG</a></th>
							<th class="garaTh"><a href="<%=target%>?nav=yes&ord=yes&<%=ParametriServlet.ORDER_FIELD%>=<%=GARA.T_DENOM_AMMINISTRAZIONE%>">Stazione Appaltante</a></th>
							<th class="garaTh"><a href="<%=target%>?nav=yes&ord=yes&<%=ParametriServlet.ORDER_FIELD%>=<%=LOTTO.T_OGGETTO%>">Oggetto Lotto</a></th>
<%-- 							
							<th class="garaTh">Importo &euro;</th>
							<th class="garaTh">Data Acquisizione</th>
							<th class="garaTh">Stato Lotto</th>
--%>					
							<th class="garaTh"><a href="<%=target%>?nav=yes&ord=yes&<%=ParametriServlet.ORDER_FIELD%>=<%="TIPO_APPALTO_QUALSIASI"%>">Tipologia Lavoro</a></th>		
							<th class="garaTh">CUP</th>
							<th class="garaTh" style="text-align: center;" nowrap>Prevede CUP?</th>	
							</tr>
				<% TableBeanRow currentRow = null; 
				   String idGara = null;
				   String lastAmm = "*";
				   String lastSA = "*";
				   String lastRUP = "*";
				   for ( int rowIndex = 0; rowIndex < listaGare.getTableSize(); rowIndex++ ) {
			    		currentRow = listaGare.getRow(rowIndex); 
			    		idGara = currentRow.getNulledField(LOTTO.ID_GARA);%>
					
						<% if(!lastAmm.equals(currentRow.getNulledField(GARA.CF_AMMINISTRAZIONE))) {%>
<%--
							<tr>
							<th class="garaTh" >Amministrazione Competente</th>
							<td class="garaTd"><%= currentRow.getNulledField( GARA.DENOM_AMMINISTRAZIONE ) %></td>
							</tr>
						<% lastAmm = currentRow.getNulledField(GARA.CF_AMMINISTRAZIONE); } %>	
						<% if(!lastSA.equals(currentRow.getNulledField(GARA.ID_STAZIONE_APPALTANTE))) {%>
							<tr>
							<th class="garaTh" >Stazione Appaltante</th>
							<td class="garaTd"><%= currentRow.getNulledField( GARA.DENOM_STAZIONE_APPALTANTE ) %></td>
							</tr>
						<% lastSA = currentRow.getNulledField(GARA.ID_STAZIONE_APPALTANTE); } %>	
						<% if(!lastRUP.equals(currentRow.getNulledField(GARA.CF_UTENTE))) {%>
							<tr>
							<th class="garaTh" >RUP</th>
							<td class="garaTd"><%= currentRow.getNulledField(GARA.CF_UTENTE) %>&nbsp;<%= currentRow.getNulledField( SOGGETTI_RESPONSABILI.COGNOME ) %>&nbsp;<%= currentRow.getNulledField( SOGGETTI_RESPONSABILI.NOME ) %></td>
							</tr>
--%>						
						<% lastRUP = currentRow.getNulledField(GARA.CF_UTENTE); } %>	
							<% boolean cancellato = ! "".equals( currentRow.getNulledField( LOTTO.DATA_CANCELLAZIONE_LOTTO ) ) 
									|| ! "".equals( currentRow.getNulledField( LOTTO.DATA_INIB_PAGAMENTO ) ); 
							 String statoLotto = ! "".equalsIgnoreCase( currentRow.getNulledField( LOTTO.DATA_PUBBLICAZIONE ) ) ? "PERFEZIONATO" : "IN LAVORAZIONE"; 
							 statoLotto = cancellato ? "CANCELLATO" : statoLotto; 
							 String dataPubblicazione = currentRow.getNulledField(LOTTO.DATA_PUBBLICAZIONE);
							 String currentCIG = currentRow.getNulledField(LOTTO.CIG) + currentRow.getNulledField(LOTTO.CIG_KKK);
							 boolean cigWS = false;
							 if(user.isRUP() && !Costanti.FLAG_VALORE_SI.equals(fromGare)){
								cigWS = (currentRow.getNulledField(CIG_STORIA.APPLICAZIONE).trim().compareToIgnoreCase(CIGBean.APPL_WS)==0	) ; 
							 }
							 String sommaUrgenza = currentRow.getNulledField(LOTTO.SOMMA_URGENZA); 
							 String unformattedImporto = currentRow.getNulledField(LOTTO.IMPORTO_LOTTO);
							 String lottoImporto = PageHelper.IMPORTO_ND;
							 if(!"".equals(unformattedImporto) && new BigDecimal(unformattedImporto).compareTo(new BigDecimal(Costanti.IMPORTO_FUORI_SCALA)) != 0){
											lottoImporto = PageHelper.getFormattedImporto(unformattedImporto);
							 }
							 %>
							<tr>
								<td class="garaTd">&nbsp;&nbsp;&nbsp;<%= PageHelper.getCIG( currentCIG, sommaUrgenza, currentRow.getNulledField(LOTTO.DATA_CREAZIONE_LOTTO) )  %></td>
					        	<td class="garaTd"><%= currentRow.getNulledField( GARA.DENOM_STAZIONE_APPALTANTE ) %>, <br/> <%= currentRow.getNulledField( GARA.DENOM_STAZIONE_APPALTANTE ) %></td>
					        	<td class="garaTd"><%= PageHelper.formattaTesto(currentRow.getNulledField(LOTTO.TABLE_NAME + LOTTO.OGGETTO)) %></td>
<%--      					
         					<td nowrap class="garaTd"><%= lottoImporto %></td>
        						<td class="garaTd"><%= PageHelper.getFormattedDate( dataPubblicazione ) %></td>
								<td class="garaTd"><strong><%= statoLotto %></strong></td>				
--%>								
								<td class="garaTd"><%= currentRow.getNulledField( "TIPO_APPALTO_QUALSIASI" ) %></td>
								<td class="garaTd"><%= mappaCup.get(currentCIG) %></td>
								<td class="garaTd" align="center" nowrap>
									<% String flagCup = currentRow.getNulledField( LOTTO.FLAG_CUP ); %>
									<form name="<%= currentCIG %>_form" action="<%= ParametriCup.SRV_INTEGRAZIONE_CIG_UPDATE_CUP %>">
										<select name="<%= ParametriCup.FIELD_FLAG_CUP %>" onchange="enableProcedi(this)">
											<option value=""></option>
											<option value="<%= Costanti.FLAG_VALORE_SI %>">Si</option>
											<option value="<%= Costanti.FLAG_VALORE_NO %>">No</option>
										</select>
										<input type="hidden" name="<%= ParametriCup.FIELD_NAME_ID_LOTTO %>" value="<%= currentRow.getNulledField(LOTTO.ID_LOTTO ) %>">
										<input type="submit" name="btn_procedi" value="Procedi"  disabled="disabled"/>
									</form>
								</td>
							</tr>	
					<% } %>
		</table>
		</div>
		</div>
		</div>
		</div>
		<!-- Chiusura Ultima gara -->

		</div>
		<!-- INSIDE SCROLL FINE -->
		
	</div>
	<!-- Scroll FINE -->
</div>
</div> <!-- gabbia -->
<%@ include file="include/newfooter.inc" %>
</div>
</body>
</html>
<%}catch(Exception e){e.printStackTrace();}%>