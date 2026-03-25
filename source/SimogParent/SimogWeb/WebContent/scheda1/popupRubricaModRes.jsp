<% try{ %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ page import="it.avlp.simog.common.servlet.*, it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.db.generated.*"%>
<%@ page import="it.avlp.simog.db.Costanti" %>

<%@ include file="../include/basicHeader.inc" %>
<%@ include file="../include/controlloSessione.inc" %>

<%@ taglib uri="/WEB-INF/tlds/tagutils.tld" prefix="h" %>
<title><%= request.getAttribute("titleRubrica") %></title>
<base target="_self">
</head>
<%@ include file="../include/i18n-init.inc" %>
<%@ include file="/script/script.js" %>
<%@ include file="/script/domUtils.js" %>
<%@ include file="/script/encodeUtils.js" %>

<!-- TB: ticket popup modali. Import css e js -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<div id="dialog"></div>
<!-- fine import popup modali -->

<script type="text/javascript">
<!--
function buildDati (){

	var dati = [];

	dati[0] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_RESPONSABILE %>").value;
	dati[1] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE %>").value;
	dati[2] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>").value;
	dati[3] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_NOME %>").value;
	dati[4] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_TELEFONO %>").value;
	dati[5] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_FAX %>").value;
	dati[6] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_EMAIL %>").value;
	dati[7] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>").value;
	dati[8] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_CAP %>").value;
	dati[9] = document.getElementById("sel_ISTAT").value;
	dati[10] = ".";
	
	return base64_encode(dati.join('<%= PSBD.SEP_VARANAG %>'));
}

function fillCaller (win, tom){

	var val = tom.value;
	var dati = base64_decode(val).split('<%= PSBD.SEP_VARANAG %>');
	
<%	if (PSBD.TAB_RESPONSABILE_PROCEDIMENTO.equals(request.getParameter(PSBD.TAB))){ %>	
		win.document.getElementById("<%= PSBD.FIELD_NAME_COGNOME_RESPONSABILE %>").value = dati[2];
		win.document.getElementById("<%= PSBD.FIELD_NAME_NOME_RESPONSABILE %>").value = dati[3];
<% } else if(PSBD.TAB_PRESTAZIONI.equals(request.getParameter(PSBD.TAB))){%>
		win.document.getElementById("<%= PSBD.FIELD_NAME_PRESTAZIONE_COGNOME %>").value = dati[2];
		win.document.getElementById("<%= PSBD.FIELD_NAME_PRESTAZIONE_NOME %>").value = dati[3];
<%}%>
}

function validaDati(okVal) {

	if (okVal){
		var wind;
		if(isNav6){
			win = window.opener;
		}
		else{
			win = window.dialogArguments.Sender;
		}		

<%	if (PSBD.TAB_RESPONSABILE_PROCEDIMENTO.equals(request.getParameter(PSBD.TAB))){ %>	
		var tom = win.document.getElementById('<%= PSBD.FIELD_NAME_ANAG %>');
<% } else if(PSBD.TAB_PRESTAZIONI.equals(request.getParameter(PSBD.TAB))){%>
		var tom = win.document.getElementById('<%= PSBD.FIELD_NAME_PRESTAZIONE_ANAG %>');
<%}%>		
		tom.value = buildDati();
				
		fillCaller(win, tom);
		
		if(isNav6){window.opener.focus();}else{window.dialogArguments.Sender.focus();}		

		window.close();
		
		return false;
	 }
	 else
	 {
	    document.forms[0].elements['<%=ParametriServletRubrica.OPERAZIONE%>'].value = "Valida";
	    document.forms[0].submit();
    }
}

//-->
</script>
<script type="text/javascript" src="xtree/treeutils.js"></script>
<body>
<% 		
		String disabled = (request.getParameter("okVal") == null ? "" : "readonly");
%>				
<div class="bodypage-e">
<h1>Modifica dati anagrafici</h1>
<form id="IdFormPopup" name="popupRubrica" action="rubricaR"  method="post">

<%@ include file="../include/gestisciErrore.inc" %>

<input type="hidden" name="<%=PSBD.ACTION_TYPE%>" id="<%=PSBD.ACTION_TYPE%>" value="<%=request.getParameter(PSBD.ACTION_TYPE) %>">
				 
<%	TableBean tableBean = (TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); 
   TableBeanRow currentRow = tableBean.getRow(0);
 %>
<div class="testo">
<fieldset>
	<input type="hidden" id="<%= ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_RESPONSABILE %>" name="<%= ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_RESPONSABILE %>" value="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.ID_RESPONSABILE) %>">
	<input type="hidden" name="<%= ParametriServletRubrica.FIELD_NAME_DATA_INIZIO_SOGGETTO %>" value="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.DATA_INIZIO_RES) %>">

	<table style="width:100%;">
			<tbody >

				<tr>
					<th><label for="">Codice Fiscale</label></th>
					<td>
					<input readonly type="text" maxlength="20" name  ="<%= ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE %>" id="<%= ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE %>" 
					value="<h:requestParameter defaultValue="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE) %>" property="<%= ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE %>"/>" >
					</td>
				</tr>
				
				<tr>
					<th><label for="">Cognome</label></th>
					<td>
						
						<input <%= disabled %> style="width:100%;" type="text" maxlength="50" name  ="<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>" 
						id="<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>"
						value="<h:requestParameter defaultValue="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.COGNOME) %>" property="<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>"/>" >
					</td>
				</tr>
				
				<tr>
					<th><label for="">Nome</label></th>
					<td>
						<input <%= disabled %> style="width:100%;" type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_NOME %>" 
						id="<%= ParametriServletRubrica.FIELD_NAME_NOME %>"
						value="<h:requestParameter defaultValue="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.NOME) %>" property="<%= ParametriServletRubrica.FIELD_NAME_NOME %>"/>" >
					</td>
				</tr>
				
				<tr>
					<th><label for="">Telefono</label></th>
					<td>
						<input <%= disabled %> style="width:100%;" type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_TELEFONO %>" 
						id ="<%= ParametriServletRubrica.FIELD_NAME_TELEFONO %>" 
						value="<h:requestParameter defaultValue="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.TELEFONO) %>" property="<%= ParametriServletRubrica.FIELD_NAME_TELEFONO %>"/>" >
					</td>
				</tr>
				
				<tr>
					<th><label for="">Fax</label></th>
					<td>
						<input <%= disabled %> style="width:100%;" type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_FAX %>" 
						id ="<%= ParametriServletRubrica.FIELD_NAME_FAX %>" 
						value="<h:requestParameter defaultValue="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.FAX) %>" property="<%= ParametriServletRubrica.FIELD_NAME_FAX %>"/>" >
					</td>
				</tr>
				<tr>
					<th><label for="">Email</label></th>
					<td>
						<input <%= disabled %> style="width:100%;" type="text" maxlength="64" name ="<%= ParametriServletRubrica.FIELD_NAME_EMAIL %>" 
						id ="<%= ParametriServletRubrica.FIELD_NAME_EMAIL %>" 
						value="<h:requestParameter defaultValue="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.EMAIL) %>" property="<%= ParametriServletRubrica.FIELD_NAME_EMAIL %>"/>" >
					</td>
					
				</tr>
<tr>
	<th><label for="">Indirizzo</label></th>
	<td>
		<input <%= disabled %> style="width:100%;" type="text" maxlength="100" name ="<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>" 
		id ="<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>" 
		value="<h:requestParameter defaultValue="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.INDIRIZZO) %>" property="<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>"/>" >
	</td>	
</tr>				
<tr>
	<th><label for="">Cap</label></th>
	<td>
		<input <%= disabled %> type="text" maxlength="10" name ="<%= ParametriServletRubrica.FIELD_NAME_CAP %>"  
		id ="<%= ParametriServletRubrica.FIELD_NAME_CAP %>" 
		value="<h:requestParameter  defaultValue="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.CAP) %>"property="<%= ParametriServletRubrica.FIELD_NAME_CAP %>"/>" >
	</td>	
</tr>

<tr>
	<th><label for="">Comune Istat</label></th>
	<td>
		<input <%= disabled %> id="sel_ISTAT" type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_COMUNE_ISTAT %>"  
		value="<h:requestParameter  defaultValue="<%= currentRow.getNulledField(SOGGETTI_RESPONSABILI.COMUNE_ISTAT) %>" property="<%= ParametriServletRubrica.FIELD_NAME_COMUNE_ISTAT %>"/>" >
<%if("".equals(disabled)){ %>
			<a class="getCPV" href="#"  onclick="apripopup('ricercaIstat.jsp','sel_ISTAT')" 
				title="Lista codici ISTAT"><img src="img/icon_info_sml.gif"></a>
<% } %>
	</td>	
</tr>				
</tbody>
</table>
<input type="hidden" name="id" id="id" value="<h:requestParameter property="id"/>">					 
<input type="hidden" name="data" id="data" value="<h:requestParameter property="data"/>">					 
<input type="hidden" name="elem" id="elem" value="<h:requestParameter property="elem"/>">					 
<input type="hidden" name="<%= PSBD.TAB %>" id="<%= PSBD.TAB %>" value="<h:requestParameter property="<%= PSBD.TAB %>"/>">					 
</fieldset>
</div>
<input type="submit" name="<%= ParametriServletRubrica.OPERAZIONE %>" value="Salva" onclick="javascript:document.forms[0].action = 'rubricaResponsabili?titleRubrica=<%= request.getAttribute("titleRubrica") %>&operazione=validaDati'; return validaDati('<h:requestParameter property="okVal" defaultValue="" />');">
<input type="button" value="Annulla" onclick=" javascript: window.close();" > 
<input type="hidden" name="parametri" id="parametri" value="">					 
</form>
</div>
<p>Nota: I dati sono prelevati dalla scheda attualmente attiva nell'anagrafica comune</p>
</body>
</html>
<% } catch (Exception e) {e.printStackTrace();} %>