<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>

<%@ page import="it.avlp.simog.common.servlet.*, it.avlp.simog.db.advanced.*" %>
<%@page import="it.avlp.simog.db.generated.*"%>
<%@page import="it.avlp.simog.util.MessageHelper"%>

<title><utils:message key="rubrica.rubricaIncaricati" /></title>


<script type="text/javascript">
 <!--
   function getKey(e){
		var keynum;
		var keychar;
		var numcheck;
		if(window.event) // IE
		{
			keynum = e.keyCode;
		}
		else if(e.which) // Netscape/Firefox/Opera
		{
			keynum = e.which;
		}
		//keychar = String.fromCharCode(keynum);
		return keynum;
	}
	function submitRubricaResp(operazione){
			document.forms[0].action = "rubricaResponsabili?operazione="+operazione;
			document.forms[0].submit();
	}  
    function parti(daDove){
       if(daDove=="cerca") {
       codFiscale = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE %>").value;
	   cognome = document.getElementById("denominazione").value;
	   cognome = trim(cognome);
	   
	    	if ( codFiscale == "" && cognome == "" ) {
	    		alert( "<%= MessageHelper.getMessage(request, "rubrica.msg.inserireFiltri") %>" );
	    		return;
	   		}
	   		else if (cognome.length < 2 && codFiscale == "" ) {
	   			alert('<%= MessageHelper.getMessage(request, "rubrica.msg.filtroCognomeCorto") %>');
	   			return;
	   			}
	   		document.getElementById('cerca').disabled = true;
	   		submitRubricaResp('view');
    }
    		
    }
	function trim(stringa) {
		while (stringa.substring(0,1) == ' ')
			{
			stringa = stringa.substring(1, stringa.length);
			}
		while (stringa.substring(stringa.length-1, stringa.length) == ' ')
			{
			stringa = stringa.substring(0,stringa.length-1);
			}
		tmpStringa = "";
		for (i = 0 ; i<stringa.length ; i = i+1) {
			if(stringa.charAt(i)==' ' || stringa.charAt()=='%' ) {}
			else tmpStringa = tmpStringa + stringa.charAt(i);
			
		}
		return tmpStringa;
		}
	function submitIfKeyPress(e, key, operaz, thisElem){
		if(thisElem.value != "" && getKey(e) == key){
			submitRubricaResp(operaz)
		}
	} 
 // -->
</script>


</head>


<% int indiceTab = 0; %>


<body>
<div id="gabbia">
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuRubrica.inc" %>

<% int maxRigheVisualizzabili = Integer.parseInt( (String)request.getAttribute( ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI ) ); %>

<% Integer startRowInt = (Integer)request.getAttribute( ParametriServlet.START_ROW ); %>
<% int startRow = startRowInt.intValue(); %>

<% TableBean tableBean = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); 
	int tableBeanSize = tableBean.getFullSize();
	long resto = (tableBeanSize % maxRigheVisualizzabili);
	long fineElenco = tableBeanSize - resto - maxRigheVisualizzabili - (resto == 0 ? maxRigheVisualizzabili : 0) ; 
%>
<% int righeVisualizzate = startRow + tableBean.getTableSize(); %>
<% if ( righeVisualizzate > tableBeanSize ) { %>
	<% righeVisualizzate = tableBeanSize; %>
<% } %>


<form action="rubricaResponsabili" method="post" name="rubrica" id="rubrica" >
<input type="hidden" name="paginazioneResponsabili" id="paginazioneResponsabili" value="ricercaPagine">
<div id="bodypage">
<div class="bodypage-e">
	<h1>Rubrica Incaricati</h1>
	<br/>
	<%@ include file="include/gestisciErrore.inc" %>
	
	<div class="hmenu">
	<% if(tableBeanSize > 0) { %>
		<% if ( startRowInt >  0 ) { %>
				<li><a href="rubricaResponsabili?operazione=view" title="Visualizza prima pagina">Inizio elenco</a></li>
		<% }
		else {%> <li><a id="disabledMenu" title="<utils:message key="scheda.inizioElenco" plain="true" />"><utils:message key="scheda.inizioElenco" /></a></li> <% } %>
		
		<% if ( righeVisualizzate >  maxRigheVisualizzabili ) { %>			
					<li><a href="rubricaResponsabili?operazione=view&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.REGRESS %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>" title="<utils:message key="scheda.precedenti" plain="true" />"><utils:message key="scheda.precedenti" /></a></li>
		<% }
		else {%> <li><a id="disabledMenu" title="<utils:message key="scheda.precedenti" plain="true" />"><utils:message key="scheda.precedenti" /></a></li> <% } %>
		
		<% if ( tableBeanSize - righeVisualizzate > 0 ) { %>
			<li><a href="rubricaResponsabili?operazione=view&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.PROGRESS %>&<%= ParametriServlet.START_ROW %>=<%= startRow %>" title="<utils:message key="scheda.successive" plain="true" />"><utils:message key="scheda.successive" /></a></li>			
		<% }
		else {%> <li><a id="disabledMenu" title="<utils:message key="scheda.successive" plain="true" />"><utils:message key="scheda.successive" /></a></li> <% } %>
		
		<% if ( righeVisualizzate != tableBeanSize ) { %>
				<li><a href="rubricaResponsabili?operazione=view&<%= ParametriServlet.ACTION_GET_LIST %>=<%= ParametriServlet.PROGRESS %>&<%= ParametriServlet.START_ROW %>=<%= fineElenco %>" title="<utils:message key="scheda.fineElenco" plain="true" />"><utils:message key="scheda.fineElenco" /></a></li>
		<% }
		else {%> <li><a id="disabledMenu" title="<utils:message key="scheda.fineElenco" plain="true" />"><utils:message key="scheda.fineElenco" /></a></li> <% } %>
	<% } %>
<%-- 
		<p><%= "startRowInt " + (startRowInt)%></p>
		<p><%= "righeVisualizzate " + (righeVisualizzate)%></p>
		<p><%= "tableBean.getFullSize()  - resto " + (tableBean.getFullSize() - resto)%></p>
		<p><%= "fine elenco " + fineElenco%></p>
		<p><%= "if( "+righeVisualizzate+" < "+(tableBean.getFullSize() - resto)+" )"%></p>
--%>
	</div>
	
	<div>
		<fieldset>
			<legend><utils:message key="rubrica.filtriNominali" /></legend>	
			<table>
			    <tr>
					<td class="detailHelp" colspan="2">Inserire il codice fiscale dell'incaricato</td>
			    </tr>
			    <tr>
			    	<td>
						<input tabindex="<%= ++indiceTab%>" size="50" type="text" title="<utils:message key="table.codiceFiscale" plain="true" />" id="<%= ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE %>" name="<%= ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE %>" onkeypress="submitIfKeyPress(event,'13','view',this)">
					</td>
				</tr>
				<tr>
					<td class="detailHelp" colspan="2"><utils:message key="rubrica.inserireCognomeNomeIncaricato" /></td>
			    </tr>
			    <tr>
			    	<td>
						<input tabindex="<%= ++indiceTab%>" size="50" type="text" title="<utils:message key="table.denominazione" plain="true" />" id="denominazione" name="<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>" onkeypress="submitIfKeyPress(event,'13','view',this)">
					</td>
				</tr>
			</table>
		</fieldset>
	</div>					    					    
	<div>
		<fieldset>
		<legend><utils:message key="rubrica.elencoIncaricati" /></legend>
		<div class="scrollDyn">
			<% TableBeanRow currentRow = null; %>
			<% TableBeanRow previousRow = null; %>
			
			<% String nominativo = null; %>	
			<% String codice = null; %>
			<% int id_partecipante = 0; %>
			<% if(tableBean!=null) { %>
				<table class="gara">
					<tr>							
						<th class="garaTh" width="50%" name=""><utils:message key="table.cognomeNome" /></th>
						<th class="garaTh" width="30%"><utils:message key="scheda.codice" /></th>
						<th class="garaTh" width="1%"></th>
					</tr>
					
					
					<% for ( int rowIndex = 0; rowIndex < tableBean.getTableSize(); rowIndex++ ) { %>
						<% currentRow = tableBean.getRow(rowIndex); %>
						<% int counter = 0; %>
						<% nominativo = PageHelper.formattaTesto(currentRow.getNulledField(SOGGETTI_RESPONSABILI.COGNOME)) + " " + PageHelper.formattaTesto(currentRow.getNulledField(SOGGETTI_RESPONSABILI.NOME));%>
						<% codice = currentRow.getNulledField(SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE);%>
						<%
								id_partecipante = Integer.parseInt(currentRow.getNulledField(SOGGETTI_RESPONSABILI.ID_RESPONSABILE)); 
								
						%>
						<%
						//id_partecipante = Integer.parseInt(currentRow.getNulledField(PARTECIPANTI.CF_SOGG_PARTECIPANTE));
						%>
						<tr>
							<td class="garaTd"><%=nominativo%></td>
							<td class="garaTd"><%=currentRow.getNulledField(SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE)%></td>
							<td class="hmenu"><a href="rubricaResponsabili?<%= ParametriServletRubrica.OPERAZIONE %>=viewDetail&<%= ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_RESPONSABILE %>=<%= id_partecipante%>">Dettaglio</a></td>
						</tr>						
					<% } %>
				</table>
			<% } %>
	</div>
	</fieldset>
	</div>
	<input type="button" id="cerca" name="<%= ParametriServletRubrica.OPERAZIONE %>" value="Cerca" onclick="parti('cerca');">
	<input type="button" name="<%= ParametriServletRubrica.OPERAZIONE %>" value="Aggiungi alla rubrica" onclick="submitRubricaResp('Aggiungi alla rubrica')">
</div>
</div>
</form>

<%@ include file="include/newfooter.inc" %>
</div>

</body>

<%@page import="it.avlp.simog.util.PageHelper"%></html>
