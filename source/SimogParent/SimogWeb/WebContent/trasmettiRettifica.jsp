<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.beans.CIGBean"%>
<%@ page import="it.avlp.simog.beans.StatiScheda"%>
<%@ page import="java.math.BigDecimal"%>
<%@page import="java.util.List"%>
<%@page import="it.avlp.simog.db.Costanti"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletLotto"%>
<%@page import="it.avlp.simog.beans.Gara"%>
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
<script type="text/javascript" src="script/pageutils.js"></script>
<% String currentDate = PageHelper.getCurrentDate(); %>

<title>SIMOG - <utils:message key="pubblicazione.pubblicazioneRettifiche" /></title>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">

</head>

<script type="text/javascript">
	function doActionModifica(action){
		if(!hasErrors(document.forms[0]))
			doAction(action);
	}
	
</script>

<body>

<!-- TB: ticket popup modali. Import css e js -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<div id="dialogALLEGATO_RETTIFICA"></div>
<!-- fine import popup modali -->

<% boolean cancellabile = false; 
	boolean cancellato = false; 
	boolean scaduto = false; 
	boolean pagabile = false;
	boolean inLavorazione = false;
	Gara gara = (Gara)request.getAttribute("GARA"); 
	String tipo_allegato = (String)request.getAttribute(ParametriServlet.TIPO_ALLEGATO);
	boolean actions = false;
	String tipo_operazione = (String)request.getAttribute(ParametriServlet.TIPO_OPERAZIONE);
	String idAggiudicazione = (String)request.getAttribute(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE);
	String dataInizioAgg = (String)request.getAttribute(PSBD.FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE);
	
	//3.04.6 rettifica
	String dataPubblicazione = (String)request.getAttribute(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE);
	String dataRichiestaInvito = (String)request.getAttribute(ParametriServlet.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO);
	String dataScadPag = (String)request.getAttribute(ParametriServlet.FIELD_NAME_DATA_SCADENZA);
	String dataLettInvito = (String)request.getAttribute(ParametriServlet.FIELD_NAME_DATA_LETTERA_INVITO);
	String oraScadenza = (String)request.getAttribute(ParametriServlet.FIELD_NAME_ORA_SCADENZA);
	String disableDataRet = (String)request.getAttribute("DateRettifica");
	
%>

<div id="gabbia">
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuGara.inc" %>
	
	<div id="bodypage">
		<div class="bodypage-e">
			<h1><utils:message key="pubblicazione.pubblicazioneRettifica" /></h1>
				<%@ include file="include/gestisciErrore.inc" %>
			<div class="hmenu">
			 <% if(ParametriServlet.PUBBLICAZIONE_RETTIFICA.equals(tipo_operazione)){ %>
				<ul><li><a title="<utils:message key="dettaglio.paginaPrecedente" plain="true" />" href="<%=ParametriServlet.SRV_VISUALIZZA_DETTAGLIO%>
				?<%=ParametriServlet.SESSION_ID_GARA%>=<%=gara.getId_Gara() %>
				&<%=ParametriServlet.FROM_GARE %>=<%=Costanti.FLAG_VALORE_SI %>"><utils:message key="lotto.ritorna" /></a></li></ul>
			 <% } %>
			 <% if(ParametriServlet.PUBBLICAZIONE_RETTIFICA_AVVISO_AGG.equals(tipo_operazione)){ %>
				<ul><li><a title="<utils:message key="dettaglio.paginaPrecedente" plain="true" />" href="<%=ParametriServlet.SRV_SCHEDA_A%>
				?<%=PSBD.FIELD_NAME_AGG_ID_AGGIUDICAZIONE%>=<%=idAggiudicazione %>
				&<%=PSBD.DATA_INIZIO_AGGIUDICAZIONE %>=<%=dataInizioAgg %>"><utils:message key="lotto.ritorna" /></a></li></ul>
			 <% } %>
			
			</div><%-- hmenu --%>
			
			<%-- gm pannello dello storico delle pubblicazioni --%>
			<% List<PubblicazioneBean> storicoPubblicazioni = (List)request.getAttribute(ParametriServlet.STORICO_PUBBLICAZIONI); %>
			<%if (storicoPubblicazioni != null && !storicoPubblicazioni.isEmpty()) {%>
		 		
			 		<fieldset>
						<legend>Storico Pubblicazioni</legend>
						
			 				<fieldset class="gara">
								<div align="center">
									<table width="100%">	
					              <tr>
						             <th class="garaTh">Tipo pubblicazione</th>
						             <th class="garaTh">Data pubblicazione</th>
						             <th class="garaTh">Allegati</th>
					              </tr>
					              <% for (PubblicazioneBean pub : storicoPubblicazioni) {%>
					              <tr>
					                <% String tipoPubblicazione = null; 
					                   if(PubblicazioneBean.TipoOperazione.BANDO.getCodice().equals(pub.getTipoOperazione()))
					                	   tipoPubblicazione = "Bando di Gara";
					                   if(PubblicazioneBean.TipoOperazione.LETTINV.getCodice().equals(pub.getTipoOperazione()))
					                	   tipoPubblicazione = "Lettera d'Invito";
					                   if(PubblicazioneBean.TipoOperazione.AVVISOAGG.getCodice().equals(pub.getTipoOperazione()))
					                	   tipoPubblicazione = "Avviso di Aggiudicazione";
					                   if(PubblicazioneBean.TipoOperazione.RETTIFICA.getCodice().equals(pub.getTipoOperazione()))
					                	   tipoPubblicazione = "Rettifica";      
					                %>
					                <td><%=tipoPubblicazione %></td>
					                <td><%=PageHelper.getViewDate(pub.getDataInizioPubblicazione()) %></td>
					                <td><input type="button" onclick="apripopup('<%=ParametriServlet.SRV_STORICO_ALLEGATI%>?<%=ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE %>=<%=pub.getIdPubblicazione() %>&<%=ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB %>=<%=pub.getDataInizioPubblicazione()%>'); return false;"; value="visualizza" /></td>    
					                <%-- 
					                <td><a title="link allegati" href="<%=ParametriServlet.SRV_STORICO_ALLEGATI%>?<%=ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE %>=<%=pub.getIdPubblicazione() %>&<%=ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB %>=<%=pub.getDataInizioPubblicazione() %>" >visualizza</a></td>
					                --%>
					              </tr>
					              <% } %>
					            </table>		
								</div>
							</fieldset>									
					</fieldset>			
				
		    <%} %>
			
			<%	int rowIndex = 0;
				String codiceGara = String.valueOf(gara.getId_Gara());
				String oggettoGara = PageHelper.formattaTesto(gara.getOggetto());
				String dataCreazioneGara = PageHelper.getFormattedDate(gara.getData_creazione()) ;			
				/***************************************************/
				/****  Visualizzazione N.D. per l'importo gara  ****/
				/***************************************************/
				String importoGara = PageHelper.IMPORTO_ND;	
				try{
					//senza '&euro;'
					String unformattedImporto = String.valueOf(gara.getIMPORTO_GARA());
					if(!"".equals(unformattedImporto) && new BigDecimal(unformattedImporto).compareTo(new BigDecimal(Costanti.IMPORTO_FUORI_SCALA)) != 0){
						importoGara = PageHelper.getFormattedImporto(unformattedImporto);
					}
				}catch(NumberFormatException t){	t.printStackTrace();	}
				/***************************************************/
				String importoSAGara = PageHelper.getFormattedImporto(String.valueOf(gara.getIMPORTO_SA_GARA()));
				if(PageHelper.IMPORTO_ND.equals(importoSAGara)){
					importoSAGara = "Il valore sara' calcolato ad esito della conferma dei dati";
				}				
			%>
			<%-- 
			<div class="testo">			
			<h4>Informazioni sulla Gara</h4>
	      <h5>Codice Gara: <%= codiceGara%></h5>
			<h5>Oggetto Gara: <%= oggettoGara%></h5>
			<h5>Importo Gara: <%= importoGara%></h5>			
			</div><%-- testo --%>	
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
						
					</table>	
		      </div><%-- gara --%>		
		<form method="post" action="<%=ParametriServlet.SRV_GESTIONE_RETTIFICA %>">
	   <input type="hidden" value="load" name="toDo" id="toDo" />	
		<input type="hidden" name="<%= ParametriServlet.SESSION_ID_GARA%>" value="<%= codiceGara%>" />	
		<input type="hidden" name="<%= ParametriServlet.TIPO_ALLEGATO%>" value="<%= tipo_allegato%>" />	
	   <input type="hidden" name="<%= PSBD.FIELD_NAME_ID_AGGIUDICAZIONE%>" value="<%= idAggiudicazione%>" />	
		<input type="hidden" name="<%= PSBD.FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE%>" value="<%= dataInizioAgg%>" />	
	  	<input type="hidden" name="<%= ParametriServlet.TIPO_OPERAZIONE%>" value="<%= tipo_operazione%>" />	

		<%-- BEGIN campi pubblicit� dell'appalto 3.0 --%>
		<h4>Pubblicit� dell'appalto</h4>
	   <table>
	     <tbody>
	       <c:set var="pubblicazione" value="${pubblicazione}" scope="page"></c:set>
	       <c:set var="hide" value="${(false)}" />
	       <c:set var="disabled" value="${hide ? 'disabled':'' }"></c:set>
	       <c:set var="pubblicita" value="${(false)}"></c:set>		
	       <c:set var="pubbModificabile" value="${(true)}"></c:set>		     
	       <%-- <%@ include file="include/datiPubblicazione.jsp" --%>
	       
	       <tr>
	         <td><label >Gazzetta Ufficiale Comunit� Europea - GUCE</label></td>
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
  			 <% if(ParametriServlet.PUBBLICAZIONE_RETTIFICA.equals(tipo_operazione)){ %>
  			   <%-- gm flag sospeso --%>
			   <tr>
				   <th><label >La pubblicazione della rettifica comporta modifica dei dati di gara e lotto</label></th>
			  	     <td>
			  	       <c:if test="${pubblicita == true}">
	                  <input  tabindex="<%=++indiceTab%>" id="<%= ParametriServlet.S_FIELD_NAME_FLAG_SOSPESO%>" type="radio" name="<%= ParametriServlet.FIELD_NAME_FLAG_SOSPESO %>" value="S" <c:out value="${pubblicazione.flag_sospeso == 'S' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />SI 
	  			         <input  tabindex="<%=++indiceTab%>" id="<%= ParametriServlet.N_FIELD_NAME_FLAG_SOSPESO%>" type="radio" name="<%= ParametriServlet.FIELD_NAME_FLAG_SOSPESO %>" value="N" <c:out value="${pubblicazione.flag_sospeso == 'N' ? 'checked' : 'disabled'}" /> <c:out value="${disabled}"/> />NO
	                </c:if>		  	   
			  	       <c:if test="${pubblicita == false}">  
			  	         <input  tabindex="<%=++indiceTab%>" id="<%= ParametriServlet.S_FIELD_NAME_FLAG_SOSPESO%>" type="radio" name="<%= ParametriServlet.FIELD_NAME_FLAG_SOSPESO %>" value="S" <c:out value="${pubblicazione.flag_sospeso == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />SI 
	  			         <input  tabindex="<%=++indiceTab%>" id="<%= ParametriServlet.N_FIELD_NAME_FLAG_SOSPESO%>" type="radio" name="<%= ParametriServlet.FIELD_NAME_FLAG_SOSPESO %>" value="N" <c:out value="${pubblicazione.flag_sospeso == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/> />NO
	                </c:if>  			
	  			     </td>
			    </tr>
			 <% } %>
	    </tbody>
	  </table>
			<%-- END campi pubblicit� dell'appalto 3.0 --%>
			
			
			
			<%-- start rettifica --%>
			<% if(disableDataRet.equals("")) { %>
			<br>
					<%-- BEGIN campi perfezionamento dei lotti --%>				       
	   <div id="datePerf" >			       
	    <h4>Rettifica date pubblicazione </h4>
	   
	   <table width="100%">
	     <tbody>

<%
//TODO - inserire controllo che se la data scadenza pagamenti < data corrente i campi devono essere in sola lettura
String modoReal = String.valueOf(gara.getID_MODO_REAL());
/* 3.04.8 34190 fix */
String labelData = String.valueOf(Costanti.MODOREAL_ADESIONE_NOCOMPET).equals(modoReal) || String.valueOf(Costanti.MODOREAL_CONCESSIONE_NOCOMPET).equals(modoReal) ? "Data di adesione all'accordo quadro/convenzione" : "Data pubblicazione";
%>
				<tr>
					<th><label for="Data_pubblicazione"><small>1</small> <%=labelData %></label></th>
					<td nowrap="nowrap">
						<input style="text-align:center" tabindex="1" 
						onblur="Calendar.validaData(this)" 
						type="text" 
						id="dtpubblicazione" 
						name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE %>" 
						value="<%= dataPubblicazione %>" >

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
     //3.04.8 34190 fix
     if( !String.valueOf(Costanti.MODOREAL_ADESIONE_NOCOMPET).equals(modoReal) && !String.valueOf(Costanti.MODOREAL_CONCESSIONE_NOCOMPET).equals(modoReal) ){
     //Fine Ticket ALM #653 
    	 %>	
					<th><label for="Data_scadenza_invito"><small>3</small> Data di scadenza per la presentazione della richiesta di invito</label></th>
					<td nowrap="nowrap">
						<input style="text-align:center" tabindex="2" 
						onblur="Calendar.validaData(this)" 
						type="text" id="dtscadenzainvito" 
						name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO %>" 
						value="<%= dataRichiestaInvito %>">
						
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
					<th><label for="Data_scadenza_pagamenti"><small>2</small> Data scadenza per la presentazione delle offerte</label></th>
					<td nowrap="nowrap">
						<input style="text-align:center" tabindex="2" 
						onblur="Calendar.validaData(this)" 
						type="text" id="dtscadenza" 
						name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA %>" 
						value="<%= dataScadPag %>">
						
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
					<th><label for="Data_lettera_invito"><small>4</small>Data della lettera di invito</label></th>
					<td nowrap="nowrap">
						<input style="text-align:center" tabindex="2" 
						onblur="Calendar.validaData(this)" 
						type="text" id="dtletterainvito" 
						name="<%= ParametriServlet.FIELD_NAME_DATA_LETTERA_INVITO %>" 
						value="<%= dataLettInvito %>">
						
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
					<th><label for=ora_scadenza_pagamenti>Ora scadenza<br>per la presentazione delle offerte (hh:mm)</th>
					<td nowrap="nowrap">
						<input style="text-align:center" tabindex="2" 
						type="text" maxlength="5" id="<%= ParametriServlet.FIELD_NAME_ORA_SCADENZA %>" 
						name="<%= ParametriServlet.FIELD_NAME_ORA_SCADENZA %>" 
						value="<%= oraScadenza %>">
					</td>
					</tr>

<%} %>	

			</tbody>
	    </table>
			<%-- END campi perfezionamento dei lotti --%>
		</div>
		<% } %>
		<%-- end rettifica --%>
			
			
		<% if ( SimogProperties.getInstance().isDocumentiAbilitato() ) { %>
			<br>
			<div id="divAllegati">
				<h4>Allegati alla Rettifica</h4>
				<table>
				<tr>
				<th><label for="">Rettifica</label></th>
				<%--gm nuovo codice estensione pubblicazione bandi, allegato rettifica --%>
				<% String func = ""; %>
  			   <% if(ParametriServlet.PUBBLICAZIONE_RETTIFICA.equals(tipo_operazione)){ %>
				<% func =  "apripopupAllegati('" + ParametriServlet.SRV_GESTISCI_ALLEGATI 
						+ "?" + ParametriServlet.SESSION_ID_GARA + "=" + codiceGara 
						+ "&" + ParametriServlet.TIPODOC + "=" + PubblicazioneBean.TipoDocumento.RETTIFICA.getCodice()
						+ "&" + ParametriServlet.RETFIELD+ "=" + ParametriServlet.ALLEGATO_RETTIFICA 
						+ "','" + ParametriServlet.ALLEGATO_RETTIFICA + "'); return false;";
				%>
				<% } else { %>
				<%--gm nuovo codice estensione pubblicazione bandi, allegato rettifica avviso di aggiudicazione--%>
				<% func =  "apripopupAllegati('" + ParametriServlet.SRV_GESTISCI_ALLEGATI 
						+ "?" + ParametriServlet.SESSION_ID_GARA + "=" + codiceGara 
						+ "&" + ParametriServlet.TIPODOC + "=" + PubblicazioneBean.TipoDocumento.RETTIFICAAVVISO.getCodice()
						+ "&" + ParametriServlet.RETFIELD+ "=" + ParametriServlet.ALLEGATO_RETTIFICA 
						+ "','" + ParametriServlet.ALLEGATO_RETTIFICA + "'); return false;";
				%>
				<% } %>
				<td>
				<input type="button" 
								onclick="<%= func %>" 
								value="Gestisci Rettifica" />
				<input type="hidden" id="<%= ParametriServlet.ALLEGATO_RETTIFICA %>" name="<%= ParametriServlet.ALLEGATO_RETTIFICA %>"
						value='<h:requestParameter property="<%= ParametriServlet.ALLEGATO_RETTIFICA %>" defaultValue="<%=(String)request.getAttribute(ParametriServlet.ALLEGATO_RETTIFICA) %>"/>'/>
				</td>
				<td>
				<input type="text" id="<%= ParametriServlet.ALLEGATO_RETTIFICA_DESC %>" name="<%= ParametriServlet.ALLEGATO_RETTIFICA_DESC %>" 
					value='<h:requestParameter property="<%= ParametriServlet.ALLEGATO_RETTIFICA_DESC %>" defaultValue="<%=(String)request.getAttribute(ParametriServlet.ALLEGATO_RETTIFICA_DESC) %>"/>' readonly="readonly"/>
				</td>
				</tr>
				<%--gm nuovo campo note rettifica --%>
				<tr>
				<th><label for="note">Note</label></th>
				<td>
					<input maxlength="250" size="100%"
					type="text" id=<%=ParametriServlet.FIELD_NAME_NOTE_ALLEGATO %>
					name="<%= ParametriServlet.FIELD_NAME_NOTE_ALLEGATO %>" 
					value='<h:requestParameter property="<%= ParametriServlet.FIELD_NAME_NOTE_ALLEGATO %>" defaultValue="<%=(String)request.getAttribute(ParametriServlet.FIELD_NAME_NOTE_ALLEGATO) %>"/>'/>

				</td>
				<%--gm fine nuovo campo note rettifica --%>
				</tr>
			</table>
			</div>
			<br>
		<% } %>
			
	  <div class="infoBlock">	
			 <div class="leftLineInfo">
			 <%  
			 if (user.isRSSAorRUP()) { %>
			 <%-- <input type="submit" value="Procedi">--%>
			 <% if(ParametriServlet.PUBBLICAZIONE_RETTIFICA.equals(tipo_operazione)){ %>
			   <input type="button" value="Procedi" onclick="doAction('<%=ParametriServlet.ACTION_SALVA_RETTIFICA%>')"/>
			 <% } %>
			 <% if(ParametriServlet.PUBBLICAZIONE_RETTIFICA_AVVISO_AGG.equals(tipo_operazione)){ %>
			   <input type="button" value="Procedi" onclick="doAction('<%=ParametriServlet.ACTION_SALVA_RETTIFICA_AVVISO%>')"/>
			 <% } %>
			 <% if(ParametriServlet.PUBBLICAZIONE_RETTIFICA.equals(tipo_operazione)){ %>			 
			   <input type="button" value="Reimposta" onclick="reimpostaForm('<%=ParametriServlet.ACTION_CARICA_RETTIFICA %>')"/>
			 <% } %>
			 <% if(ParametriServlet.PUBBLICAZIONE_RETTIFICA_AVVISO_AGG.equals(tipo_operazione)){ %>			 
			   <input type="button" value="Reimposta" onclick="reimpostaForm('<%=ParametriServlet.ACTION_CARICA_RETTIFICA_AVVISO %>')"/>
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

<%@page import="it.avlp.simog.beans.PubblicazioneBean"%>
</html>