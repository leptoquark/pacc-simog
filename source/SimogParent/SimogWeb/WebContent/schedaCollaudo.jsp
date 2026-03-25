<%@page import="java.util.HashMap"%>
<%@page import="it.avlp.simog.util.SimogProperties"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	errorPage="errore.jsp"%>

<%@page import="it.avlp.simog.util.SimogProperties"%>

<%@ include file="include/newbasicHeader.inc"%>
<%@ include file="include/controlloSessione.inc"%>

<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
int indiceTab = 0;
%>

<c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>
<jsp:useBean id="schedaCollaudo"
	type="it.avlp.simog.beans.collaudo.SchedaCollaudo"
	class="it.avlp.simog.beans.collaudo.SchedaCollaudo" scope="request"></jsp:useBean>
<%
		CollaudoBean collaudo = schedaCollaudo.getCollaudo();
		pageContext.setAttribute("collaudo", collaudo);
		
		String dataCreazione = (String)session.getAttribute("data_creazione");
	 	 String labelPagina = dataCreazione.compareTo(String.valueOf(SimogProperties.getInstance().getDataAttivazione3043())) >= 0 ? "modifica contrattuale" : "variante";
         String labelPaginaPlur = dataCreazione.compareTo(String.valueOf(SimogProperties.getInstance().getDataAttivazione3043())) >= 0 ? "modifiche contrattuali" : "varianti";
		
%>

<%@ page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@ page
	import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletCollaudo"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletInizioLavori"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletRubrica"%>
<%@ page import="java.math.*"%>
<%@page import="it.avlp.simog.beans.collaudo.CollaudoBean"%>

<c:set var="aggiudicazione" value="${schedaCollaudo.aggiudicazione}" scope="page"></c:set>

<c:set var="rupOk" value="${(UTENTE.login eq datiGara.cfRup or datiGara.cfRup eq null ) and aggiudicazione.flagAggiudPrincipale ne 'N'}" />
<c:set var="roByFlusso" value="${datiGara.deleted or rupOk eq false or UTENTE.ossReg eq true or UTENTE.RASA eq true or  schedaCollaudo.delegaScheda eq true  or schedaCollaudo.riaggiudicata eq true or  schedaCollaudo.readOnly eq true}" />
<c:set var="readonly" value="${roByFlusso or collaudo.confirmed }" />
<c:set var="disabledStr" value="${readonly ? 'disabled':'' }" />
<c:set var="readonlyStr" value="${readonly? 'readonly' : ''}" />
<c:set var="saveAndResetDisabledStr" value="${ (roByFlusso or collaudo.confirmed) ? 'disabled' : ''  }"/> 	
<c:set var="noConf" value="${roByFlusso eq true or (collaudo.idCollaudo le 0) or collaudo.richAnn eq true  or collaudo.confirmed}" />
<c:set var="annullabile" value="${ roByFlusso ne true and collaudo.confirmed eq true and collaudo.richAnn ne true and collaudo.richDelete ne true  and variazioniAnagrafiche ne true}" />
<c:set var="cancellabile" value="${ roByFlusso ne true and collaudo.okCancellazione eq true  and variazioniAnagrafiche ne true}" />
<c:set var="variazAnagraf" value="${(schedaCollaudo.varAnagActive eq true and roByFlusso ne true and collaudo.confirmed eq true and variazioniAnagrafiche ne true )}" />



<c:set var="saveAction" value="<%=PSBD.ACTION_SALVA %>"/>
    <c:if test="${variazioniAnagrafiche eq true}">
    	 <c:set var="saveAction" value="<%=PSBD.ACTION_VARIAZIONI_ANAGRAFICHE_SAVE %>"/>
</c:if>



<link rel="stylesheet" href="theme/tabmenu.css" />
<!-- calendar stylesheet -->
<link rel="stylesheet" type="text/css" media="all"
	href="calendar/calendar-blue.css" title="win2k-cold-1" />
<!-- main calendar program -->
<script type="text/javascript" src="calendar/calendar.js"></script>
<!-- language for the calendar -->
<%@ include file="include/calendar-dynamic.inc" %>
<!-- the following script defines the Calendar.setup helper function, which makes
     adding a calendar a matter of 1 or 2 lines of code. -->
<script type="text/javascript" src="calendar/calendar-setup.js"></script>
<script type="text/javascript" src="script/pageutils.js"></script>
<%@ include file="include/i18n-init.inc" %>
<%@ include file="/script/script.js"%>
<%@ include file="/script/domUtils.js"%>

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

<script type="text/javascript">
<!--
	function valutaSubTotale() {
		var sommaA = 0;
		var sommaD = 0;
		var sommaC = 0;
		var avanzate;
		var definite;
		var contenzioso;

		// definite
		if (document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_AMM_NUM_DEFINITE%>').value == "" ) 
			definite = parseFloat('0');
		else  definite = parseFloat(document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_AMM_NUM_DEFINITE%>').value.replace(/\./g,"").replace(',','.'));
							
		sommaD = sommaD + parseFloat(definite);

		if (document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_GIU_NUM_DEFINITE%>').value == "" ) 
			definite = parseFloat('0');
		else  definite = parseFloat(document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_GIU_NUM_DEFINITE%>').value.replace(/\./g,"").replace(',','.'));
							
		sommaD = sommaD + parseFloat(definite);

		if (document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_ARB_NUM_DEFINITE%>').value == "" ) 
			definite = parseFloat('0');
		else  definite = parseFloat(document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_ARB_NUM_DEFINITE%>').value.replace(/\./g,"").replace(',','.'));
							
		sommaD = sommaD + parseFloat(definite);

		if (document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_TRA_NUM_DEFINITE%>').value == "" ) 
			definite = parseFloat('0');
		else  definite = parseFloat(document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_TRA_NUM_DEFINITE%>').value.replace(/\./g,"").replace(',','.'));
							
		sommaD = sommaD + parseFloat(definite);
		
		document.getElementById("subtotaleD").value = addMyDotsFromCommaString(sommaD.toFixed(3).replace('.',','));
							
		// avanzate
		if (document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_AMM_NUM_DADEF%>').value == "" ) 
			avanzate = parseFloat('0');
		else  avanzate = parseFloat(document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_AMM_NUM_DADEF%>').value.replace(/\./g,"").replace(',','.'));
							
		sommaA = sommaA + parseFloat(avanzate);

		if (document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_GIU_NUM_DADEF%>').value == "" ) 
			avanzate = parseFloat('0');
		else  avanzate = parseFloat(document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_GIU_NUM_DADEF%>').value.replace(/\./g,"").replace(',','.'));
							
		sommaA = sommaA + parseFloat(avanzate);

		if (document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_ARB_NUM_DADEF%>').value == "" ) 
			avanzate = parseFloat('0');
		else  avanzate = parseFloat(document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_ARB_NUM_DADEF%>').value.replace(/\./g,"").replace(',','.'));
							
		sommaA = sommaA + parseFloat(avanzate);

		if (document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_TRA_NUM_DADEF%>').value == "" ) 
			avanzate = parseFloat('0');
		else  avanzate = parseFloat(document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_TRA_NUM_DADEF%>').value.replace(/\./g,"").replace(',','.'));
							
		sommaA = sommaA + parseFloat(avanzate) + parseFloat(sommaD); // aggiungo le definite
		
		document.getElementById("subtotaleA").value = addMyDotsFromCommaString(sommaA.toFixed(3).replace('.',','));

		// contenzioso
		if (document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_AMM_IMPORTO_DEF%>').value == "" ) 
			contenzioso = parseFloat('0');
		else  contenzioso = parseFloat(document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_AMM_IMPORTO_DEF%>').value.replace(/\./g,"").replace(',','.'));
							
		sommaC = sommaC + parseFloat(contenzioso);
		
		if (document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_GIU_IMPORTO_DEF%>').value == "" ) 
			contenzioso = parseFloat('0');
		else  contenzioso = parseFloat(document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_GIU_IMPORTO_DEF%>').value.replace(/\./g,"").replace(',','.'));
							
		sommaC = sommaC + parseFloat(contenzioso);
		
		if (document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_ARB_IMPORTO_DEF%>').value == "" ) 
			contenzioso = parseFloat('0');
		else  contenzioso = parseFloat(document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_ARB_IMPORTO_DEF%>').value.replace(/\./g,"").replace(',','.'));
							
		sommaC = sommaC + parseFloat(contenzioso);
		
		if (document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_TRA_IMPORTO_DEF%>').value == "" ) 
			contenzioso = parseFloat('0');
		else  contenzioso = parseFloat(document.getElementById('<%= ParametriServletCollaudo.FIELD_NAME_TRA_IMPORTO_DEF%>').value.replace(/\./g,"").replace(',','.'));
							
		sommaC = sommaC + parseFloat(contenzioso);
		
		document.getElementById("subtotaleC").value = addMyDotsFromCommaString(sommaC.toFixed(3).replace('.',','));

		return true;	
	}
//-->
</script>

<title>Gestione Schede - COLLAUDO - <%=user.getProfilo()%></title>
</head>
<body>
<div id="gabbia" align="left">
		<%if(request.getAttribute("protect")!= null) {%>
		<%@ include file="/include/protect.inc" %>
		<%} %>
		<%@ include	file="/include/header.inc"%>
<div class="bodypage-e" align="left">

<h1>Gestione Schede - COLLAUDO</h1>
<div class="hmenu" align="left">
<ul>
	<%
			String riScheda = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA
			+ "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=";
	%>
	<li><a title="Torna alla lista Aggiudicazioni"
		href="javascript:changePage('<%=riScheda%>${datiGara.idLotto}','Modificato')">Lista
	Aggiudicazioni</a></li>
<!--	<c:if test="${collaudo.idCollaudo > 0 and roByFlusso eq false}">-->
<!--		<li><c:url value="srvSchedaCollaudo" var="newMod">-->
<!--			<c:param name="toDo" value="load"></c:param>-->
<!--		</c:url> <a href="<c:out value='${newMod}'/>">Aggiungi nuova scheda</a></li>-->
<!--	</c:if>-->
</ul>
</div>
<%@ include file="/include/gestisciErrore.inc"%>

<%-- PANNELLO DELLE RICHIESTE DI ANNULLAMENTO DELLA SCHEDA [DISATTIVATO] --%>
<%@ include file="../include/RichAnnPanel.jsp" %>
<%@ include file="../include/VarAnagPanel.jsp" %>

<table >
	<tr>
		
		   <td> <input ${variazioniAnagrafiche eq true ? '' : saveAndResetDisabledStr} type="button" value="Salva" onclick="creaHidden('FormCollaudo','<%= PSBD.RESPONSABILE %>');checkAndAction('check',2,'${saveAction}')"></td>
			<td><input ${noConf eq true ? 'disabled' : ''} type="button" value="Conferma" onclick="creaHidden('FormCollaudo','<%= PSBD.RESPONSABILE %>');checkAndAction('check',2,'<%=PSBD.ACTION_CONFERMA %>')" /></td>
			<td><input ${variazioniAnagrafiche eq true ? '' : saveAndResetDisabledStr} type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_LOAD %>')"></td>
			<c:if test="${annullabile}">
				<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>"
				      onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')" /></td>
			</c:if>
			<c:if test="${cancellabile}">
				<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
			</c:if>
			<c:if test="${variazAnagraf}">
				<td><input type="button" value="Comunica Variazioni Anagrafiche" onclick="doAction('<%=PSBD.ACTION_VARIAZIONI_ANAGRAFICHE %>')"/></td>	
			</c:if>
						
						
		<c:set var="statoid" value="${collaudo.idStato}"/>
		<c:set var="statoann" value="${collaudo.richAnn || collaudo.richDelete}"/>
		<c:set var="statodesc" value="${collaudo.descrizioneStato}"/>
		<%@ include file="../include/statoscheda.inc" %>
		</td>
	</tr>
</table>

<fieldset>
<h2>Scheda Collaudo</h2>
<form action="<%= ParametriServletCollaudo.SRV_SCHEDA_COLLAUDO %>"
	method="post" id="FormCollaudo" name="FormCollaudo"
	onkeypress="setFormModified('Modificato')">
	<input type="hidden" name="<%=PSBD.TAB%>" id="<%=PSBD.TAB%>" value="" /> 
	<input type="hidden" name="<%=PSBD.ACTION_TYPE%>" id="<%=PSBD.ACTION_TYPE%>" value="" /> 
	<input type="hidden" name="<%=ParametriServletCollaudo.FIELD_NAME_ID_COLLAUDO %>" value="<c:out value="${collaudo.idCollaudo}"/>"> 
	<input type="hidden" name="<%=ParametriServletCollaudo.FIELD_NAME_DATA_INIZIO_COLL %>" value="<c:out value="${collaudo.dataIniColl}"/>"> 
	<input type="hidden" id="Modificato" value="0" />
	<input type="hidden" name="<%=PSBD.VAR_ANN%>"  value="${variazioniAnagrafiche}" />

<fieldset class="gara">
	<table width="100%">
			<tr>
				<td align="center" colspan="2"><p class="detailHelp"><strong>RIFERIMENTO AI DATI DELLA FASE DI AGGIUDICAZIONE O DI DEFINIZIONE DI PROCEDURA NEGOZIATA</strong></p></td>
			</tr>
			  <%@include file="/include/intestazione.jsp" %>
	</table>
   <table width="100%"  ${variazioniAnagrafiche eq true ? 'style="display:none;"' : ''}>
	<tr>
		<td align="center" colspan="2">
		<p class="detailHelp"><strong>COLLAUDO/VERIFICA DI
		CONFORMIT&Agrave; DELLE PRESTAZIONI ESEGUITE O ESITI ACCERTAMENTO
		TECNICO-CONTABILE</strong></p>
		</td>
	</tr>
	<tr>
		<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataCollaudoStatico") %>
			for="<%=ParametriServletCollaudo.FIELD_NAME_DATA_COLLAUDO_STAT %>">Data
		del collaudo statico (ove ricorra)</label></td>
		<td><input
			name="<%= ParametriServletCollaudo.FIELD_NAME_DATA_COLLAUDO_STAT %>"
			tabindex="<%= ++indiceTab %>" style="text-align: center;"
			onchange="setFormModified('Modificato')" ${readonlyStr}
			type="text"
			id="<%= ParametriServletCollaudo.FIELD_NAME_DATA_COLLAUDO_STAT %>"
			onblur="Calendar.validaData(this)"
			value="<c:out value='${collaudo.dataCollaudoStat}'/>" /> <c:if
			test="${readonly ne true}">
			<img src="calendar/img.gif" id="calendarTermine"
				style="cursor: pointer; border: 1px solid red;"
				title="Date selector" onmouseover="this.style.background='red';"
				onmouseout="this.style.background=''" />
			<script type="text/javascript">
								    Calendar.setup({
							        inputField     :    "<%= ParametriServletCollaudo.FIELD_NAME_DATA_COLLAUDO_STAT%>",     // id of the input field
							        ifFormat       :    "%d/%m/%Y",      // format of the input field
							        button         :    "calendarTermine",  // trigger for the calendar (button ID)
							        align          :    "Tl",           // alignment (defaults to "Bl")
							        singleClick    :    true							       
						    		});					    	
							</script>
		</c:if></td>
	</tr>
	<tr>
		<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataRegolareEsecuzione") %>
			for="<%=ParametriServletCollaudo.FIELD_NAME_DATA_REGOLARE_ESEC %>">Data
		del certificato di regolare esecuzione</label></td>
		<td><input
			name="<%= ParametriServletCollaudo.FIELD_NAME_DATA_REGOLARE_ESEC %>"
			tabindex="<%= ++indiceTab %>" style="text-align: center;"
			onchange="setFormModified('Modificato')" ${readonlyStr}
			type="text"
			id="<%= ParametriServletCollaudo.FIELD_NAME_DATA_REGOLARE_ESEC %>"
			onblur="Calendar.validaData(this)"
			value="<c:out value='${collaudo.dataRegolareEsec}'/>" /> <c:if
			test="${readonly ne true}">
			<img src="calendar/img.gif" id="calendarRegEsec"
				style="cursor: pointer; border: 1px solid red;"
				title="Date selector" onmouseover="this.style.background='red';"
				onmouseout="this.style.background=''" />
			<script type="text/javascript">
								    Calendar.setup({
							        inputField     :    "<%= ParametriServletCollaudo.FIELD_NAME_DATA_REGOLARE_ESEC%>",     // id of the input field
							        ifFormat       :    "%d/%m/%Y",      // format of the input field
							        button         :    "calendarRegEsec",  // trigger for the calendar (button ID)
							        align          :    "Tl",           // alignment (defaults to "Bl")
							        singleClick    :    true							       
						    		});					    	
							</script>
		</c:if></td>
	</tr>
	<tr>
		<td rowspan="2"><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_ModoCollaudo") %>>Modalit&agrave; del Collaudo
		Tecnico Amministrativo</label></td>
		<td><input name="<%= ParametriServletCollaudo.FIELD_NAME_MODO_COLLAUDO%>" type="checkbox" value="1" ${disabledStr}
			 <c:out value="${(collaudo.modoCollaudo eq '1' || collaudo.modoCollaudo eq '2') ? 'checked' :''}" /> />Collaudo finale</td>
		<c:if test="${(collaudo.modoCollaudo eq '1' || collaudo.modoCollaudo eq '2') and readonly eq true}" >
			<input name="<%= ParametriServletCollaudo.FIELD_NAME_MODO_COLLAUDO%>" type="hidden" value="1" />
		</c:if>
	</tr>
	<tr>
		<td><input name="<%= ParametriServletCollaudo.FIELD_NAME_MODO_COLLAUDO%>" type="checkbox" value="2" ${disabledStr}
			<c:out value="${(collaudo.modoCollaudo eq '2' || collaudo.modoCollaudo eq '3') ? 'checked' :''}" /> />Collaudo
		in corso d'opera</td>
		<c:if test="${(collaudo.modoCollaudo eq '2' || collaudo.modoCollaudo eq '3') and readonly eq true}" >
			<input name="<%= ParametriServletCollaudo.FIELD_NAME_MODO_COLLAUDO%>" type="hidden" value="2" />
		</c:if>
	</tr>
	<tr>
		<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataNominaCollaudatore") %>
			for="<%=ParametriServletCollaudo.FIELD_NAME_DATA_NOMINA_COLL %>">Data
		nomina collaudatore/Commissione</label></td>
		<td><input
			name="<%= ParametriServletCollaudo.FIELD_NAME_DATA_NOMINA_COLL %>"
			tabindex="<%= ++indiceTab %>" style="text-align: center;"
			onchange="setFormModified('Modificato')" ${readonlyStr}
			type="text"
			id="<%= ParametriServletCollaudo.FIELD_NAME_DATA_NOMINA_COLL %>"
			onblur="Calendar.validaData(this)"
			value="<c:out value='${collaudo.dataNominaColl}'/>" /> <c:if
			test="${readonly ne true}">
			<img src="calendar/img.gif" id="calendarNomina"
				style="cursor: pointer; border: 1px solid red;"
				title="Date selector" onmouseover="this.style.background='red';"
				onmouseout="this.style.background=''" />
			<script type="text/javascript">
								    Calendar.setup({
							        inputField     :    "<%= ParametriServletCollaudo.FIELD_NAME_DATA_NOMINA_COLL%>",     // id of the input field
							        ifFormat       :    "%d/%m/%Y",      // format of the input field
							        button         :    "calendarNomina",  // trigger for the calendar (button ID)
							        align          :    "Tl",           // alignment (defaults to "Bl")
							        singleClick    :    true							       
						    		});					    	
							</script>
		</c:if></td>
	</tr>
	<tr>
		<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataInizioOperColl") %>
			for="<%=ParametriServletCollaudo.FIELD_NAME_DATA_INIZIO_OPER %>">Data
		inizio operazioni di collaudo</label></td>
		<td><input
			name="<%= ParametriServletCollaudo.FIELD_NAME_DATA_INIZIO_OPER %>"
			tabindex="<%= ++indiceTab %>" style="text-align: center;"
			onchange="setFormModified('Modificato')" ${readonlyStr}
			type="text"
			id="<%= ParametriServletCollaudo.FIELD_NAME_DATA_INIZIO_OPER %>"
			onblur="Calendar.validaData(this)"
			value="<c:out value='${collaudo.dataIniOper}'/>" /> <c:if
			test="${readonly ne true}">
			<img src="calendar/img.gif" id="calendarInizioOp"
				style="cursor: pointer; border: 1px solid red;"
				title="Date selector" onmouseover="this.style.background='red';"
				onmouseout="this.style.background=''" />
			<script type="text/javascript">
								    Calendar.setup({
							        inputField     :    "<%= ParametriServletCollaudo.FIELD_NAME_DATA_INIZIO_OPER%>",     // id of the input field
							        ifFormat       :    "%d/%m/%Y",      // format of the input field
							        button         :    "calendarInizioOp",  // trigger for the calendar (button ID)
							        align          :    "Tl",           // alignment (defaults to "Bl")
							        singleClick    :    true							       
						    		});					    	
							</script>
		</c:if></td>
	</tr>
	<tr>
		<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataRedCert") %>
			for="<%=ParametriServletCollaudo.FIELD_NAME_DATA_CERT_COLLAUDO %>">Data
		redazione certificato di collaudo</label></td>
		<td><input
			name="<%= ParametriServletCollaudo.FIELD_NAME_DATA_CERT_COLLAUDO %>"
			tabindex="<%= ++indiceTab %>" style="text-align: center;"
			onchange="setFormModified('Modificato')" ${readonlyStr}
			type="text"
			id="<%= ParametriServletCollaudo.FIELD_NAME_DATA_CERT_COLLAUDO %>"
			onblur="Calendar.validaData(this)"
			value="<c:out value='${collaudo.dataCertCollaudo}'/>" /> <c:if
			test="${readonly ne true}">
			<img src="calendar/img.gif" id="calendarCertColl"
				style="cursor: pointer; border: 1px solid red;"
				title="Date selector" onmouseover="this.style.background='red';"
				onmouseout="this.style.background=''" />
			<script type="text/javascript">
								    Calendar.setup({
							        inputField     :    "<%= ParametriServletCollaudo.FIELD_NAME_DATA_CERT_COLLAUDO%>",     // id of the input field
							        ifFormat       :    "%d/%m/%Y",      // format of the input field
							        button         :    "calendarCertColl",  // trigger for the calendar (button ID)
							        align          :    "Tl",           // alignment (defaults to "Bl")
							        singleClick    :    true							       
						    		});					    	
							</script>
		</c:if></td>
	</tr>
	<tr>
		<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_DataDelibera") %>
			for="<%=ParametriServletCollaudo.FIELD_NAME_DATA_DELIBERA %>">Data
		delibera di ammissibilit&agrave; del collaudo (ove prevista)</label></td>
		<td><input
			name="<%= ParametriServletCollaudo.FIELD_NAME_DATA_DELIBERA %>"
			tabindex="<%= ++indiceTab %>" style="text-align: center;"
			onchange="setFormModified('Modificato')" ${readonlyStr}
			type="text"
			id="<%= ParametriServletCollaudo.FIELD_NAME_DATA_DELIBERA %>"
			onblur="Calendar.validaData(this)"
			value="<c:out value='${collaudo.dataDelibera}'/>" /> <c:if
			test="${readonly ne true}">
			<img src="calendar/img.gif" id="calendarDelib"
				style="cursor: pointer; border: 1px solid red;"
				title="Date selector" onmouseover="this.style.background='red';"
				onmouseout="this.style.background=''" />
			<script type="text/javascript">
								    Calendar.setup({
							        inputField     :    "<%= ParametriServletCollaudo.FIELD_NAME_DATA_DELIBERA%>",     // id of the input field
							        ifFormat       :    "%d/%m/%Y",      // format of the input field
							        button         :    "calendarDelib",  // trigger for the calendar (button ID)
							        align          :    "Tl",           // alignment (defaults to "Bl")
							        singleClick    :    true							       
						    		});					    	
							</script>
		</c:if></td>
	</tr>
	<tr>
		<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_EsitoCollaudo") %>
			for="<%= ParametriServletCollaudo.FIELD_NAME_ESITO_COLLAUDO %>">Esito
		del collaudo</label></td>
		<td>
				<u:selectBooleanRadio name="<%= ParametriServletCollaudo.FIELD_NAME_ESITO_COLLAUDO%>" 
   				    value="${collaudo.esitoCollaudo}" 
   				    trueId="check1Y" trueVal="P" trueLabel="Positivo"
                    falseId="check1N" falseVal="N" falseLabel="Negativo"
                    readonly="${readonly}" 
                    tabindex="<%=++indiceTab%>" />
                    <%indiceTab++; %>
	  </td>
			
	</tr>
	<tr>
		<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_ImportoLavori") %>
			for="<%= ParametriServletCollaudo.FIELD_NAME_IMP_FINALE_LAVORI %>">Importo
		finale componente lavori in &euro; (al netto dell'IVA e degli oneri di
		sicurezza)</label></td>
		<td><input
			name="<%= ParametriServletCollaudo.FIELD_NAME_IMP_FINALE_LAVORI %>"
			id="1" tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.impFinaleLavoriStr}'/>"
			onblur="validateAmount(this);somma1();somma2();somma3()" /></td>
	</tr>
	<tr>
		<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_ImportoServizi") %>
			for="<%= ParametriServletCollaudo.FIELD_NAME_IMP_FINALE_SERVIZI %>">Importo
		finale componente servizi in &euro; (come sopra)</label></td>
		<td><input
			name="<%= ParametriServletCollaudo.FIELD_NAME_IMP_FINALE_SERVIZI %>"
			id="2" tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.impFinaleServiziStr}'/>"
			onblur="validateAmount(this);somma1();somma2();somma3()" /></td>
	</tr>
	<tr>
		<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_ImportoForn") %>
			for="<%= ParametriServletCollaudo.FIELD_NAME_IMP_FINALE_FORNIT %>">Importo
		finale componente forniture in &euro; (come sopra)</label></td>
		<td><input
			name="<%= ParametriServletCollaudo.FIELD_NAME_IMP_FINALE_FORNIT %>"
			id="3" tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.impFinaleFornitStr}'/>"
			onblur="validateAmount(this);somma1();somma2();somma3()" /></td>
	</tr>
	<tr>
		<td><label>Subtotale</label></td>
		<td><input type="text" name="sub" value="${collaudo.subStr}" disabled
			style="text-align: right;font-weight:bold;width: 120px" /> <%-- <c:set var="subtotale" value="${collaudo.impFinaleLavori + collaudo.impFinaleServizi + collaudo.impFinaleFornit}" scope="request"/> --%>

		</td>
	</tr>
	<tr>
		<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_ImportoSic") %>
			for="<%= ParametriServletCollaudo.FIELD_NAME_IMP_FINALE_SICUR %>">Importo
		finale per l'attuazione della sicurezza</label></td>
		<td><input
			name="<%= ParametriServletCollaudo.FIELD_NAME_IMP_FINALE_SICUR %>"
			id="4" 
			tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.impFinaleSicurStr}'/>"
			onblur="validateAmount(this);somma1();somma2();somma3()" /></td>
	</tr>
	<tr>
		<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_ImportoProg") %>
			for="<%= ParametriServletCollaudo.FIELD_NAME_IMP_PROGETTAZIONE %>">Importo
		progettazione</label></td>
		<td><input
			name="<%= ParametriServletCollaudo.FIELD_NAME_IMP_PROGETTAZIONE %>"
			id="5" tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.impProgettazioneStr}'/>"
			onblur="validateAmount(this);somma1();somma2();somma3()" /></td>
	</tr>
	<tr>
		<%-- subtotale 2 --%>
		<td><label>Importo finale complessivo dell'appalto</label></td>
		<td><input type="text" name="sub2" value="${collaudo.sub2Str}" disabled
			style="text-align: right;font-weight:bold;width: 120px" /></td>
	</tr>
	<tr>
		<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_ImportoDisp") %>
			for="<%= ParametriServletCollaudo.FIELD_NAME_IMP_DISPOSIZIONE %>">Importo
		complessivo "somme a disposizione" effettivamente impiegate*</label></td>
		<td><input
			name="<%= ParametriServletCollaudo.FIELD_NAME_IMP_DISPOSIZIONE %>"
			id="6" tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.impDisposizioneStr}'/>"
			onblur="validateAmount(this);somma1();somma2();somma3()" /></td>
	</tr>
	<tr>
		<%-- importo finale --%>
		<td><label>Importo a consuntivo dell'intervento</label></td>
		<td><input type="text" name="<%= ParametriServletCollaudo.FIELD_NAME_IMP_FINALE_TOTALE%>" disabled
			value="${collaudo.finaleStr}" style="text-align: right;font-weight:bold;width: 120px" /></td>
	</tr>


	<tr>
		<td><label <%=SimogFlags.checkHighlightField(fieldToHighlight,"label_LavoriAnnEstesi") %> >Lavori annuali estesi a piu' esercizi*</label></td>
		<td>
			<u:selectBooleanRadio name="<%= ParametriServletCollaudo.FIELD_NAME_LAVORI_ANNUALI_ESTESI%>" 
  				    value="${collaudo.flagLavoriEstesi}" trueId="check2Y" 
                   falseId="check2N" readonly="${readonly}" 
                   tabindex="<%=++indiceTab%>" />
                   <%indiceTab++; %>
			                    	
		</td>
	</tr>


	<tr>
		<td align="center" colspan="2">
		<p class="detailHelp"><strong>CONTENZIOSO</strong></p>
		</td>
	</tr>
	<tr>
		<td><label>Numero totale riserve definite con accordo
		bonario</label></td>
		<td><c:set var="numeroRiserve"
			value="${requestScope['numRiserve']}"></c:set> <c:out
			value="${numeroRiserve}"></c:out></td>
	</tr>
	<tr>
		<td><label>Oneri complessivi derivanti</label></td>
		<td><c:set var="oneriDerivanti"
			value="${requestScope['oneriDerivanti']}"></c:set> <c:out
			value="${oneriDerivanti}"></c:out></td>
	</tr>
	<tr>
		<td colspan="2">
		<p style="color:black; font-weight:bold; text-align:left;">Ulteriori
		riserve in via amministrativa in sede di collaudo</p>
		</td>
	</tr>
	<tr>
		<td><label style="padding-left:20px;"
			for="<%= ParametriServletCollaudo.FIELD_NAME_AMM_NUM_DEFINITE%>">Numero
		riserve definite</label></td>
		<td><input
			id="<%= ParametriServletCollaudo.FIELD_NAME_AMM_NUM_DEFINITE%>"
			name="<%= ParametriServletCollaudo.FIELD_NAME_AMM_NUM_DEFINITE%>"
			tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.ammNumDefinite}'/>"
			onblur="validateNumber(this);valutaSubTotale()" /></td>
	</tr>
	<tr>
		<td><label style="padding-left:20px;" 
			for="<%= ParametriServletCollaudo.FIELD_NAME_AMM_NUM_DADEF%>">Numero
		riserve da definire</label></td>
		<td><input
			id="<%= ParametriServletCollaudo.FIELD_NAME_AMM_NUM_DADEF%>"
			name="<%= ParametriServletCollaudo.FIELD_NAME_AMM_NUM_DADEF%>"
			tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.ammNumDaDef}'/>"
			onblur="validateNumber(this);valutaSubTotale()" /></td>
	</tr>
	<tr>
		<td><label style="padding-left:20px;"
			for="<%= ParametriServletCollaudo.FIELD_NAME_AMM_IMPORTO_RICH %>">Importo
		totale richiesto</label></td>
		<td><input
			id="<%= ParametriServletCollaudo.FIELD_NAME_AMM_IMPORTO_RICH %>"
			name="<%= ParametriServletCollaudo.FIELD_NAME_AMM_IMPORTO_RICH %>"
			tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.ammImportoRichStr}'/>"
			onblur="validateAmount(this)" /></td>
	</tr>
	<tr>
		<td><label style="padding-left:20px;"
			for="<%= ParametriServletCollaudo.FIELD_NAME_AMM_IMPORTO_DEF %>">Importo
		totale eventuale definizione</label></td>
		<td><input
			id="<%= ParametriServletCollaudo.FIELD_NAME_AMM_IMPORTO_DEF %>"
			name="<%= ParametriServletCollaudo.FIELD_NAME_AMM_IMPORTO_DEF %>"
			tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.ammImportoDefStr}'/>"
			onblur="validateAmount(this);valutaSubTotale()" /></td>
	</tr>
	<tr>
		<td colspan="2">
		<p style="color:black; font-weight:bold; text-align:left;">Ulteriori
		riserve in via arbitrale</p>
		</td>
	</tr>
	<tr>
		<td><label style="padding-left:20px;"
			for="<%= ParametriServletCollaudo.FIELD_NAME_ARB_NUM_DEFINITE%>">Numero
		riserve definite</label></td>
		<td><input
			id="<%= ParametriServletCollaudo.FIELD_NAME_ARB_NUM_DEFINITE%>"
			name="<%= ParametriServletCollaudo.FIELD_NAME_ARB_NUM_DEFINITE%>"
			tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.arbNumDefinite}'/>"
			onblur="validateNumber(this);valutaSubTotale()" /></td>
	</tr>
	<tr>
		<td><label style="padding-left:20px;"
			for="<%= ParametriServletCollaudo.FIELD_NAME_ARB_NUM_DADEF%>">Numero
		riserve da definire</label></td>
		<td><input
			id="<%= ParametriServletCollaudo.FIELD_NAME_ARB_NUM_DADEF%>"
			name="<%= ParametriServletCollaudo.FIELD_NAME_ARB_NUM_DADEF%>"
			tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.arbNumDaDef}'/>"
			onblur="validateNumber(this);valutaSubTotale()" /></td>
	</tr>
	<tr>
		<td><label style="padding-left:20px;"
			for="<%= ParametriServletCollaudo.FIELD_NAME_ARB_IMPORTO_RICH %>">Importo
		totale richiesto</label></td>
		<td><input
			id="<%= ParametriServletCollaudo.FIELD_NAME_ARB_IMPORTO_RICH %>"
			name="<%= ParametriServletCollaudo.FIELD_NAME_ARB_IMPORTO_RICH %>"
			tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.arbImportoRichStr}'/>"
			onblur="validateAmount(this)" /></td>
	</tr>
	<tr>
		<td><label style="padding-left:20px;"
			for="<%= ParametriServletCollaudo.FIELD_NAME_ARB_IMPORTO_DEF %>">Importo
		totale eventuale definizione</label></td>
		<td><input
			id="<%= ParametriServletCollaudo.FIELD_NAME_ARB_IMPORTO_DEF %>"
			name="<%= ParametriServletCollaudo.FIELD_NAME_ARB_IMPORTO_DEF %>"
			tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.arbImportoDefStr}'/>"
			onblur="validateAmount(this);valutaSubTotale()" /></td>
	</tr>
	<tr>
		<td colspan="2">
		<p style="color:black; font-weight:bold; text-align:left;">Ulteriori
		riserve in via giudiziale</p>
		</td>
	</tr>
	<tr>
		<td><label style="padding-left:20px;"
			for="<%=ParametriServletCollaudo.FIELD_NAME_GIU_NUM_DEFINITE %>">Numero
		riserve definite</label></td>
		<td><input
			id="<%= ParametriServletCollaudo.FIELD_NAME_GIU_NUM_DEFINITE%>"
			name="<%= ParametriServletCollaudo.FIELD_NAME_GIU_NUM_DEFINITE%>"
			tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.giuNumDefinite}'/>"
			onblur="validateNumber(this);valutaSubTotale()" /></td>
	</tr>
	<tr>
		<td><label style="padding-left:20px;"
			for="<%= ParametriServletCollaudo.FIELD_NAME_GIU_NUM_DADEF%>">Numero
		riserve da definire</label></td>
		<td><input
			id="<%= ParametriServletCollaudo.FIELD_NAME_GIU_NUM_DADEF%>"
			name="<%= ParametriServletCollaudo.FIELD_NAME_GIU_NUM_DADEF%>"
			tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.giuNumDaDef}'/>"
			onblur="validateNumber(this);valutaSubTotale()" /></td>
	</tr>
	<tr>
		<td><label style="padding-left:20px;"
			for="<%= ParametriServletCollaudo.FIELD_NAME_GIU_IMPORTO_RICH %>">Importo
		totale richiesto</label></td>
		<td><input
			id="<%= ParametriServletCollaudo.FIELD_NAME_GIU_IMPORTO_RICH %>"
			name="<%= ParametriServletCollaudo.FIELD_NAME_GIU_IMPORTO_RICH %>"
			tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.giuImportORichStr}'/>"
			onblur="validateAmount(this)" /></td>
	</tr>
	<tr>
		<td><label style="padding-left:20px;"
			for="<%= ParametriServletCollaudo.FIELD_NAME_GIU_IMPORTO_DEF %>">Importo
		totale eventuale definizione</label></td>
		<td><input
			id="<%= ParametriServletCollaudo.FIELD_NAME_GIU_IMPORTO_DEF %>"
			name="<%= ParametriServletCollaudo.FIELD_NAME_GIU_IMPORTO_DEF %>"
			tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.giuImportoDefStr}'/>"
			onblur="validateAmount(this);valutaSubTotale()" /></td>
	</tr>
	<tr>
		<td colspan="2">
		<p style="color:black; font-weight:bold; text-align:left;">Ulteriori
		riserve in via transattiva</p>
		</td>
	</tr>
	<tr>
		<td><label style="padding-left:20px;"
			for="<%=ParametriServletCollaudo.FIELD_NAME_TRA_NUM_DEFINITE %>">Numero
		riserve definite</label></td>
		<td><input
			id="<%= ParametriServletCollaudo.FIELD_NAME_TRA_NUM_DEFINITE%>"
			name="<%= ParametriServletCollaudo.FIELD_NAME_TRA_NUM_DEFINITE%>"
			tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.traNumDefinite}'/>"
			onblur="validateNumber(this);valutaSubTotale()" /></td>
	</tr>
	<tr>
		<td><label style="padding-left:20px;"
			for="<%= ParametriServletCollaudo.FIELD_NAME_TRA_NUM_DADEF%>">Numero
		riserve da definire</label></td>
		<td><input
			id="<%= ParametriServletCollaudo.FIELD_NAME_TRA_NUM_DADEF%>"
			name="<%= ParametriServletCollaudo.FIELD_NAME_TRA_NUM_DADEF%>"
			tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.traNumDaDef}'/>"
			onblur="validateNumber(this);valutaSubTotale()" /></td>
	</tr>
	<tr>
		<td><label style="padding-left:20px;"
			for="<%= ParametriServletCollaudo.FIELD_NAME_TRA_IMPORTO_RICH %>">Importo
		totale richiesto</label></td>
		<td><input
			id="<%= ParametriServletCollaudo.FIELD_NAME_TRA_IMPORTO_RICH %>"
			name="<%= ParametriServletCollaudo.FIELD_NAME_TRA_IMPORTO_RICH %>"
			tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.traImportoRichStr}'/>"
			onblur="validateAmount(this)" /></td>
	</tr>
	<tr>
		<td><label style="padding-left:20px;"
			for="<%= ParametriServletCollaudo.FIELD_NAME_TRA_IMPORTO_DEF %>">Importo
		totale eventuale definizione</label></td>
		<td><input
			id="<%= ParametriServletCollaudo.FIELD_NAME_TRA_IMPORTO_DEF %>"
			name="<%= ParametriServletCollaudo.FIELD_NAME_TRA_IMPORTO_DEF %>"
			tabindex="<%= ++indiceTab %>" style="text-align: right;"
			${readonlyStr} type="text"
			value="<c:out value='${collaudo.traImportoDefStr}'/>"
			onblur="validateAmount(this);valutaSubTotale()" /></td>
	</tr>
	<tr>
	<th><label  style="color:black; font-weight:bold; text-align:left;" for="subtotaleA">Numero totale riserve avanzate</label></th>
	<td>
	<input disabled type="text" id="subtotaleA" name="subtotaleA" value="0" style="text-align:right;font-weight: bold;"/>
	</td>
	</tr>
	<tr>
	<th><label style="color:black; font-weight:bold; text-align:left;" for="subtotaleD">Numero totale riserve definite</label></th>
	<td>
	<input disabled type="text" id="subtotaleD" name="subtotaleD" value="0" style="text-align:right;font-weight: bold;w"/>
	</td>
	</tr>
	<tr>
	<th><label style="color:black; font-weight:bold; text-align:left;" for="subtotaleC">Importo totale contenzioso risolto</label></th>
	<td>
	<input disabled type="text" id="subtotaleC" name="subtotaleC" value="0" style="text-align:right;font-weight: bold;"/>
	</td>
	</tr>
	</table>					     
	<table ${variazioniAnagrafiche eq true ? '' : 'style="display:none;"'}>
		<tr>
			<th><label for="<%= PSBD.FIELD_NAME_MOTIVO_CO %>">Motivazione della variazione anagrafica</label></th>
			<td>
				<select onchange="setFormModified('Modificato0')" tabindex="<%=++indiceTab%>" 
						style="width:100%" 
						name="<%= PSBD.FIELD_NAME_MOTIVO_CO %>" 
						id=<%= PSBD.FIELD_NAME_MOTIVO_CO %> CLASS="BOTTONE">
					<option></option>
				  	<c:set var="idMotivoVarCO" value="${collaudo.idMotivoVarCO}" scope="request" />
				  	<u:options name="<%= ParametriServlet.MOTIVO_VCO_BEAN %>" scope="request" value="idMotivoVarCO"/>
				</select>
			</td>
		</tr>
	</table>
	<table>	
	<tr>
		<td align="center" colspan="2">
		<p class="detailHelp"><strong>SOGGETTI AI QUALI SONO
		STATI CONFERITI INCARICHI</strong></p>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<div class="inthead"><label
			onclick="showMenu('<%= PSBD.TAB_RESPONSABILE_PROCEDIMENTO %>')"
			style="color:black; letter-spacing:0.2em;"> <img
			src="img/minus.gif"
			id="img<%= PSBD.TAB_RESPONSABILE_PROCEDIMENTO %>" />
		ANAGRAFICA E RIFERIMENTI DEI SOGGETTI AI QUALI LA STAZIONE APPALTANTE
		HA CONFERITO INCARICHI</label>
		<div
			id="<%= PSBD.TAB_RESPONSABILE_PROCEDIMENTO %>"
			style="display: block;">
			<c:set var="responsabili" value="${collaudo.respBean}" scope="page"></c:set>
			<c:set var="includerConfirmed" value="${collaudo.confirmed}" scope="page"></c:set>
			 <%@ include file="/scheda1/responsabile.jsp"%></div>
		</div>
		</td>
	</tr>
	</table>
   <div width="100%"  ${variazioniAnagrafiche eq true ? 'style="display:none;"' : ''}>
	<table>
	<tr>

		<td align="center" colspan="2">
		<p class="detailHelp"><strong>ELENCO SUBAPPALTI
		COMUNICATI</strong></p>
		</td>
	</tr>
	<tr>
	<table align="center" width="300px">
		<tr>
			<c:set var="listaSubappalti"
				value="${sessionScope['listaSubappalti']}"></c:set>
			<th class="garaTh">Codice Fiscale Ditta</th>
			<th class="garaTh"><c:out
				value="${datiGara.tipoContratto eq 'L' ? 'Lavoro' : (datiGara.tipoContratto eq 'F' ? 'Fornitura' : 'Servizio')}"></c:out>
			subappalto</th>
			<th class="garaTh">Importo presunto <c:out
				value="${datiGara.tipoContratto eq 'L' ? 'Lavoro' : (datiGara.tipoContratto eq 'F' ? 'Fornitura' : 'Servizio')}"></c:out>
			subappalto</th>
			<th class="garaTh">Importo effettivo <c:out
				value="${datiGara.tipoContratto eq 'L' ? 'Lavoro' : (datiGara.tipoContratto eq 'F' ? 'Fornitura' : 'Servizio')}"></c:out>
			subappalto</th>
		</tr>
		<c:forEach items="${listaSubappalti}" var="schedaSubappalto">
			<tr id="riga"
				onclick="visualizza('datiSubappalto.jsp?idscheda=${schedaSubappalto.idRecord}&datascheda=${schedaSubappalto.dataInizioRecord}')">

				<td class="garaTd"><c:out value="${schedaSubappalto.cfDitta}"></c:out></td>
				<td class="garaTd"><textarea rows="2" cols="20"
					readonly="readonly"><c:out
					value="${schedaSubappalto.oggettoSubappalto}"></c:out></textarea></td>
				<td class="garaTd"><c:out
					value="${schedaSubappalto.importoPresuntoStr}"></c:out></td>
				<td class="garaTd"><c:out
					value="${schedaSubappalto.importoEffettivoStr}"></c:out></td>
			</tr>
		</c:forEach>
	</table>
	</tr>
	<tr>
		<td colspan="2">
		<p class="detailHelp" align="center"><strong>ELENCO
		DELLE SOSPENSIONI DELL'ESECUZIONE COMUNICATE</strong></p>
		</td>
	</tr>
	<tr>
	<table align="center" width="300px">
		<tr>
			<c:set var="listaSospensioni"
				value="${sessionScope['listaSospensioni']}"></c:set>
			<th class="garaTh">Data del verbale di sospensione</th>
			<th class="garaTh">Data del verbale di ripresa</th>
			<th class="garaTh">Numero di giorni di durata della sospensione</th>
			<th class="garaTh">Motivo della sospensione</th>
		</tr>
		<c:forEach items="${listaSospensioni}" var="schedaSospensioni">
			<tr
				onclick="visualizza('datiSospensione.jsp?idscheda=${schedaSospensioni.idSospensione}&datascheda=${schedaSospensioni.dataInizioSosp}')">
				<td class="garaTd"><c:out
					value="${schedaSospensioni.dataVerbSosp}"></c:out></td>
				<td class="garaTd"><c:out
					value="${schedaSospensioni.dataVerbRipr}"></c:out></td>
				<td class="garaTd">${schedaSospensioni.giorniProroga}</td>
				<td class="garaTd"><textarea rows="2" cols="20"
					readonly="readonly"><c:out
					value="${schedaSospensioni.descrizioneMotivo}"></c:out></textarea></td>
			</tr>
		</c:forEach>
	</table>
	</tr>
	<tr>
		<td colspan="2">
		<p class="detailHelp" align="center"><strong>ELENCO DELLE
		<%= labelPaginaPlur.toUpperCase() %> COMUNICATE</strong></p>
		</td>
	</tr>
	<tr>
	<table align="center" width="300px">
		<tr>
			<c:set var="listaVarianti" value="${sessionScope['listaVarianti']}"></c:set>
			<th class="garaTh">Data della <%=labelPagina %></th>
			<th class="garaTh">Importo della <%=labelPagina %></th>
			<th class="garaTh">Motivazione della <%=labelPagina %></th>
		</tr>
		<c:forEach items="${listaVarianti}" var="schedaVarianti">
			<tr
				onclick="visualizza('datiVarianti.jsp?idscheda=${schedaVarianti.idVariante}&datascheda=${schedaVarianti.dataInizioVar}&label=<%=labelPagina%>')">
				<td class="garaTd"><c:out
					value="${schedaVarianti.dataVerbaleApprovazione}"></c:out></td>
				<c:set var="importo"
					value="${schedaVarianti.impRidetLavori + schedaVarianti.impRidetServizi + schedaVarianti.impRidetFornit + schedaVarianti.impSicurezza + schedaVarianti.impProgettazione + schedaVarianti.impDisposizione}"></c:set>
				<td class="garaTd"><c:out value="${importo}"></c:out></td>
				<td>
				<table>
					<c:forEach items="${schedaVarianti.emvb}" var="motivo">
						<tr>
							<td class="garaTd"><c:out value="${motivo.descrizione}"></c:out>
							</td>
						</tr>
					</c:forEach>
				</table>
				</td>
			</tr>
		</c:forEach>
	</table>
	</tr>
</table>
</div>

<input type="hidden" value="save" name="toDo" id="toDo" /></fieldset>
<table >
	<tr>
		<input type="hidden" name="checkIfOK" id="checkIfOK"
			value="<%=new Integer(session.getAttribute(ParametriServlet.checkIfOK).toString()) + 1%>" />
		   <td> <input ${variazioniAnagrafiche eq true ? '' : saveAndResetDisabledStr} type="button" value="Salva" onclick="creaHidden('FormCollaudo','<%= PSBD.RESPONSABILE %>');checkAndAction('check',2,'${saveAction}')"></td>
			<td><input ${noConf eq true ? 'disabled' : ''} type="button" value="Conferma" onclick="creaHidden('FormCollaudo','<%= PSBD.RESPONSABILE %>');checkAndAction('check',2,'<%=PSBD.ACTION_CONFERMA %>')" /></td>
			<td><input ${variazioniAnagrafiche eq true ? '' : saveAndResetDisabledStr} type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_LOAD %>')"></td>
			<c:if test="${annullabile}">
				<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>"
				      onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')" /></td>
			</c:if>
			<c:if test="${cancellabile}">
				<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
			</c:if>
			<c:if test="${variazAnagraf}">
				<td><input type="button" value="Comunica Variazioni Anagrafiche" onclick="doAction('<%=PSBD.ACTION_VARIAZIONI_ANAGRAFICHE %>')"/></td>	
			</c:if>
		<%@ include file="../include/statoscheda.inc" %>
	</tr>
</table>
</form>
</fieldset>
</div>
<%@ include file="include/newfooter.inc"%>
</div>
<script type="text/javascript">
if(document.getElementById('4').value==""){
	document.getElementById('4').value="0";
	validateAmount(document.getElementById('4'));
}
if(document.getElementById('5').value==""){
	document.getElementById('5').value="0";
	validateAmount(document.getElementById('5'));
}
valutaSubTotale();
</script>
</body>
</html>

