<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="../errore.jsp" %>
<%@ include file="../include/basicHeader.inc" %>
<%@ include file="../include/controlloSessione.inc" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

<%@ page import="it.avlp.simog.beans.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@page import="java.util.HashMap"%>
<%@page import="it.avlp.simog.util.PageHelper"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.util.SimogProperties"%>

<%@ taglib uri="/WEB-INF/tlds/tagutils.tld" prefix="utils" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<link rel="stylesheet" href="theme/tabmenu.css"/>

<!-- calendar stylesheet -->
<link rel="stylesheet" type="text/css" media="all" href="calendar/calendar-blue.css" title="win2k-cold-1" />

<!-- main calendar program -->
<script type="text/javascript" src="calendar/calendar.js"></script>

<!-- language for the calendar -->
<%@ include file="../include/calendar-dynamic.inc" %>

<!-- the following script defines the Calendar.setup helper function, which makes
     adding a calendar a matter of 1 or 2 lines of code. -->
<script type="text/javascript" src="calendar/calendar-setup.js"></script>
<script type="text/javascript" src="script/pageutils.js"></script>
<%@ include file="../include/i18n-init.inc" %>
<%@ include file="/script/script.js" %>
<%@ include file="/script/domUtils.js" %>

<%	
	TableBean infoEnteContratto = (request.getAttribute(ParametriServlet.DATI_ENTE_CONTRATTO_TABLEBEAN)) != null ? (TableBean)request.getAttribute(ParametriServlet.DATI_ENTE_CONTRATTO_TABLEBEAN): null;
	TableBean fineLavori = (request.getAttribute(ParametriServletSchedaB4.FINE_LAVORI_TABLEBEAN)) != null ? (TableBean)request.getAttribute(ParametriServletSchedaB4.FINE_LAVORI_TABLEBEAN): new TableBean();
	TableBeanRow rowFineLavori = fineLavori.getRow(0);
	TableBean subappalti = (request.getAttribute(ParametriServletSchedaB4.SUBAPPALTI_TABLEBEAN)) != null ? (TableBean)request.getAttribute(ParametriServletSchedaB4.SUBAPPALTI_TABLEBEAN): null;
	TableBean datiResponsabile = (request.getAttribute(ParametriServletSchedaB4.RESPONSABILI_FINE_TABLEBEAN)) != null ? (TableBean)request.getAttribute(ParametriServletSchedaB4.RESPONSABILI_FINE_TABLEBEAN): null;
	TableBean ruoliResponsabili = (request.getAttribute(PSBD.RUOLI_RESPONSABILE_TABLEBEAN)) != null ? (TableBean)request.getAttribute(PSBD.RUOLI_RESPONSABILE_TABLEBEAN): null;
	
	String FLAG_ENTE_SPECIALE = infoEnteContratto.getNulledField(INFO_AGGIUDICAZIONI.FLAG_ENTE_SPECIALE,0);
	String TIPO_CONTRATTO = infoEnteContratto.getNulledField(INFO_AGGIUDICAZIONI.TIPO_CONTRATTO,0);
	String contrattoEnte = TIPO_CONTRATTO + FLAG_ENTE_SPECIALE;
	
	String DESCRIZIONE_STATO	= (fineLavori.getFullSize()>0) ? rowFineLavori.getNulledField(STATI_SCHEDA.DESCRIZIONE) : "";
	String ID_AGGIUDICAZIONE	=	(request.getParameter(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE) != null)? request.getParameter(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE) : "";
   String DATA_INIZIO_AGGIUDICAZIONE	= (request.getParameter(PSBD.DATA_INIZIO_AGGIUDICAZIONE) != null) ? request.getParameter(PSBD.DATA_INIZIO_AGGIUDICAZIONE) : "";
   
   String data_cancellazione_lotto = (infoEnteContratto!=null)? infoEnteContratto.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO,0) : ""; 
	String data_inib_pagamenti = (infoEnteContratto!=null)? infoEnteContratto.getNulledField(LOTTO.DATA_INIB_PAGAMENTO,0) : ""; 
	boolean lottoCancellato = ! "".equals( data_cancellazione_lotto ) || ! "".equals( data_inib_pagamenti );
   boolean noOp = false;
	String ID_STATO	= (fineLavori.getFullSize()>0) ? rowFineLavori.getNulledField(AGGIUDICAZIONE.ID_STATO) : "";
	noOp = (lottoCancellato || ID_STATO.equalsIgnoreCase(StatiScheda.CONFERMATO_STRING)) ? true:false;
	String disabled = (lottoCancellato || ID_STATO.equalsIgnoreCase(StatiScheda.CONFERMATO_STRING)) ? "disabled":"";
	boolean confermato = ID_STATO.equalsIgnoreCase(StatiScheda.CONFERMATO_STRING);
	String disabilitato = (lottoCancellato || confermato) ? "disabled" : ""; 
	String noconf = ID_AGGIUDICAZIONE.equalsIgnoreCase("") ? "disabled":"";	

	String modificato = (request.getAttribute("Modificato")!=null)?(String)request.getAttribute("Modificato"):"0";
	String modificato0 = (request.getAttribute("Modificato0")!=null)?(String)request.getAttribute("Modificato0"):"0";
	
%>
<%


MessageBean messBean = (it.avlp.simog.beans.MessageBean) request.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.ERRORBEAN);
it.avlp.simog.beans.AllValidationBeans beanErr = null;
HashMap<String, String> fieldToHighlight = new HashMap<String,String>();
if ( messBean != null ) 
if ( messBean instanceof it.avlp.simog.beans.AllValidationBeans ){
beanErr = (it.avlp.simog.beans.AllValidationBeans) request.getAttribute(it.avlp.simog.common.servlet.ParametriServlet.ERRORBEAN);
fieldToHighlight = beanErr.getFieldToHighlight();
}
%>
<html>
	<head>
	<title><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.fineLavori" /> - <%= user.getProfilo() %></title>
	</head>
	<body>
	    
		<div id="gabbia">
			<%@ include file="../include/header.inc" %>			
			<div class="bodypage-e">
				<form id="<%= ParametriServletSchedaB4.FORM_SCHEDA_B4 %>" name="gestioneTab" action="<%= ParametriServletSchedaB4.SRV_SCHEDA_B4 %>" method="post" onkeypress="setFormModified('Modificato0')">
					<h1><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.fineLavori" /></h1>
					
					<%@ include file="../include/campiHidden.inc" %>
					
					<input type="hidden" id="Modificato" name="Modificato" value="<%= modificato %>">
					<input type="hidden" id="Modificato0" name="Modificato0" value="<%= modificato0 %>">
											
					<input type="hidden" name="<%=PSBD.TAB%>" id="<%=PSBD.TAB%>" value="">	
					<input type="hidden" name="<%=PSBD.ACTION_TYPE%>" id="<%=PSBD.ACTION_TYPE%>" value="">
					
					<div  class="hmenu">	
						<ul>
							<li><a title="Torna alla lista Aggiudicazioni" href="javascript:changePage('<%=ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA%>','Modificato')">Torna</a></li>  
						</ul>
					</div>
					<%@ include file="/include/gestisciErrore.inc" %>
					<fieldset>
						<table border="1" width="100%" >
							<tr>
								<td width="5%"><img src="img/logorepubblica.gif"></td>
								<td width="95%" align="center" style="font-weight: bold; font-size: medium;color: blue;">Autorit� per la vigilanza sui contratti pubblici di lavori, servizi, forniture</td>
							</tr>
							 <tr>
							  <td colspan="2" align="center"><strong style="font-style: italic; font-size: small;">Osservatorio dei contratti pubblici relativi a lavori, servizi e forniture</strong></td>
							 </tr>
						</table>
						<table width = "100%">
							<colgroup>
								<col width="47%"/>
								<col width="6%"/>
								<col width="47%"/>
							</colgroup>
							<tr>
								<td align="center" class="AA<%=TIPO_CONTRATTO%>"><p style="font-weight: bolder; color: black">MODELLO PER LA TRASMISSIONE DI DATI</p></td>
								<td>&nbsp;</td>
								<td align="center" class="AA<%=TIPO_CONTRATTO%>"><p style="font-weight: bolder; color: black">CONTRATTI DI APPALTO DI IMPORTO &gt; 150.000 EURO </p></td>
							</tr>
							 
							<tr>
								<td colspan="3" align="center" class="S<%=FLAG_ENTE_SPECIALE%>"><p style="font-weight: bolder; color: black">Codice dei contratti pubblici relativi a lavori, servizi e forniture - D.Lgs. 12  aprile 2006, n. 163 - art. 7, c. 8, lett. a)</p></td>
							</tr>
							<tr>
								<td align="center" class="AA<%=TIPO_CONTRATTO%>"><font color="black" style="font-weight: bold;">FASE DI CONCLUSIONE DEL CONTRATTO</font></td>
								<td align="center"  style="font-size: large; font-weight: bold">UA<%= TIPO_CONTRATTO %></td>
								<% String TIPO_STRING = null; %>
								<% String SUB_TIPO_STRING = null,FINALE = "o"; %>
								<%if("S".equalsIgnoreCase(TIPO_CONTRATTO)){
									TIPO_STRING = "SERVIZI";
									SUB_TIPO_STRING = "Servizio";
							    }else if("F".equalsIgnoreCase(TIPO_CONTRATTO)){
							  		TIPO_STRING = "FORNITURE"; 
							  		SUB_TIPO_STRING = "Fornitura";
									FINALE = "a";
							    }else if("L".equalsIgnoreCase(TIPO_CONTRATTO)){
							  		TIPO_STRING = "LAVORI";
							  		 SUB_TIPO_STRING = "Lavoro";
							  	}else{
							  		TIPO_STRING = "LAVORI/SERVIZI/FORNITURE (MISTO)";
							  		SUB_TIPO_STRING = "Lavoro/Servizio/Fornitura";
							 		FINALE = "o/a";
							  	} %>							  	
								
						  		<td align="center" class="AA<%=TIPO_CONTRATTO%>"><p style="font-weight: bolder; color: black"><%= TIPO_STRING %></p></td>
							</tr>
							<tr>
								<td align="center" class="S<%=FLAG_ENTE_SPECIALE%>"><p style="font-weight: bolder; color: black">STAZIONI APPALTANTI ED ENTI AGGIUDICATORI</p></td>
							 	<td align="center" style="font-size: large; font-weight: bold">S<%= FLAG_ENTE_SPECIALE %></td>
							 	
							 	<% String ENTE_STRING = null;
							 	if("O".equalsIgnoreCase(FLAG_ENTE_SPECIALE)){ 
							  		ENTE_STRING = "SETTORI ORDINARI";
							 	}else{ 
							 		ENTE_STRING = "SETTORI SPECIALI";
							   	} %>							 		
							   	<td align="center" class="S<%=FLAG_ENTE_SPECIALE%>"><p style="font-weight: bolder; color: black"><%=ENTE_STRING%></p></td>

							</tr>
						</table>
					</fieldset>
					<fieldset>
						<table>  
							<tr>
								<th><label for="">Stato dei dati</label></th>
								<td>
									<p class="detailHelp"><%= DESCRIZIONE_STATO %></p>
								</td>
							</tr>
						</table>
						<table>	
							<tr>
								<td><input <%=disabled%> type="button" value="Salva" onclick="setAndSave('<%= ParametriServletSchedaB4.FORM_SCHEDA_B4 %>','<%= ParametriServletSchedaB4.TAB_FINE_LAVORI %>')"></td>
								<td><input <%=disabled%> <%=noconf%> type="button" value="Conferma" onclick="setAndConfirm('<%= ParametriServletSchedaB4.FORM_SCHEDA_B4 %>','<%= ParametriServletSchedaB4.TAB_FINE_LAVORI  %>')"></td>
								<td><input <%=disabled%> type="button" value="Reimposta" onclick="reimposta('<%= ParametriServletSchedaB4.TAB_FINE_LAVORI %>')"></td>
								<% if(confermato && !DESCRIZIONE_STATO.contains(PSBD.MSG_RICHIESTO_ANNULLAMENTO)) { %>
									<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="loadAnnullamentJSP('<%= ParametriServletSchedaB4.TAB_FINE_LAVORI  %>')"></td>
								<% } %>
							</tr>
						</table>
						<table width="100%">	
							<tr>
								<td colspan="2">
									<div id="<%= ParametriServletSchedaB4.TAB_FINE_LAVORI  %>" onkeypress="setFormModified('Modificato0')">
										<%@ include file="datiSchedaB4.inc" %>    
									</div>
								</td>
							</tr>			
						</table>
						<table>  
							<tr>
								<td><input <%=disabled%> type="button" value="Salva" onclick="setAndSave('<%= ParametriServletSchedaB4.FORM_SCHEDA_B4 %>','<%= ParametriServletSchedaB4.TAB_FINE_LAVORI %>')"></td>
								<td><input <%=disabled%> <%=noconf%> type="button" value="Conferma" onclick="setAndConfirm('<%= ParametriServletSchedaB4.FORM_SCHEDA_B4 %>','<%= ParametriServletSchedaB4.TAB_FINE_LAVORI  %>')"></td>
								<td><input <%=disabled%> type="button" value="Reimposta" onclick="reimposta('<%= ParametriServletSchedaB4.TAB_FINE_LAVORI %>')"></td>
								<% if(confermato && !DESCRIZIONE_STATO.contains(PSBD.MSG_RICHIESTO_ANNULLAMENTO)) { %>
									<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="loadAnnullamentJSP('<%= ParametriServletSchedaB4.TAB_FINE_LAVORI  %>')"></td>
								<% } %>
							</tr>
						</table>
					</fieldset>						
				</form>
			</div>			
			<%@ include file="../include/footer.inc" %>   
		</div>		
	</body>		
</html>
