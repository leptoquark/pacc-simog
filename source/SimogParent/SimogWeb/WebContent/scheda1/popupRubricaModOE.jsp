<% try{ %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ page import="it.avlp.simog.common.servlet.*, it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.db.generated.*"%>
<%@ page import="it.avlp.simog.db.Costanti" %>

<%@ include file="../include/basicHeader.inc" %>
<%@ include file="../include/controlloSessione.inc" %>

<%@ taglib uri="/WEB-INF/tlds/tagutils.tld" prefix="h" %>
<%@page import="java.util.Map"%>
<title><%= request.getAttribute("titleRubrica") %></title>
<base target="_self">
</head>
<%@ include file="../include/i18n-init.inc" %>
<%@ include file="/script/script.js" %>
<%@ include file="/script/domUtils.js" %>
<%@ include file="/script/encodeUtils.js" %>

<script type="text/javascript">
<!--
function buildDati (){

	var dati = [];

	dati[0] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_PARTECIPANTE %>").value;
	dati[1] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE %>").value;
	dati[2] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE %>").value;
	dati[3] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_CAMERA_COMMERCIO %>").value;
	dati[4] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_PARTITA_IVA %>").value;
	dati[5] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>").value;
	dati[6] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_CIVICO %>").value;
	dati[7] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_CITTA %>").value;
	dati[8] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_PROVINCIA %>").value;
	dati[9] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_CAP %>").value;
	dati[10] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_CF_RAPPRESENTANTE %>").value;
	dati[11] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>").value;
	dati[12] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_NOME %>").value;
	dati[13] = document.getElementById("<%= ParametriServletRubrica.FIELD_NAME_ID_STATO %>").value;
	dati[14] = ".";
	
	return base64_encode(dati.join('<%= PSBD.SEP_VARANAG %>'));
}

function fillCaller (win, tom){

	var val = tom.value;
	var dati = base64_decode(val).split('<%= PSBD.SEP_VARANAG %>');
	
<%	if (PSBD.TAB_AFFIDATARIO.equals(request.getParameter(PSBD.TAB))){ %>	
		win.document.getElementById("<%= PSBD.FIELD_NAME_AGG_DENOMINAZIONE %>").value = dati[2];
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

<%	if (PSBD.TAB_AFFIDATARIO.equals(request.getParameter(PSBD.TAB))){ %>	
		var tom = win.document.getElementById('<%= PSBD.FIELD_NAME_ANAGOE %>');
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
	<input type="hidden" id="<%= ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_PARTECIPANTE %>" name="<%= ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_PARTECIPANTE %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE) %>">
	<input type="hidden" name="<%= ParametriServletRubrica.FIELD_NAME_DATA_INIZIO_SOGGETTO %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG) %>">
	<input type="hidden" name="<%= ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.CODICE_FISCALE) %>">
	<input type="hidden" name="<%= ParametriServletRubrica.FIELD_NAME_FLAG_ESTERI %>" value="<%= request.getAttribute(ParametriServlet.FLAG_ESTERO) %>">

	<table style="width:100%;">
			<tbody >
				<tr>
					<th><label>Paese Operatore Economico</label></th>
					<% Map <String,String> m = (Map)request.getAttribute(STATI_ESTERI.TABLE_NAME);
					   String paese = m.keySet().iterator().next();  // reperisco il paese
	                %>
					<td><input type="text" readonly value="<%= paese %>" />
					    <input type="hidden" id="<%= ParametriServletRubrica.FIELD_NAME_ID_STATO %>" name="<%= ParametriServletRubrica.FIELD_NAME_ID_STATO %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.ID_STATO) %>" />
					</td>
				</tr>

	<tr>
	<th><label for="">Codice Fiscale / Partita Iva</label></th>
	<td>
		<input readonly type="text" id="<%= ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE %>" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE %>" value="<h:requestParameter defaultValue="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.CODICE_FISCALE) %>" property='<%= ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE %>' />" />
	</td>
</tr>

<tr>
	<th><label for="">Denominazione</label></th>
	<td>
		<input <%= disabled %> id="<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE %>" type="text" maxlength="250" name ="<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE %>" value="<h:requestParameter defaultValue="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.DENOMINAZIONE) %>" property='<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE %>' />" />
	</td>
</tr>

<tr>
	<th><label for="">Camera Commercio</label></th>
	<td>
		<input <%= disabled %> id="<%= ParametriServletRubrica.FIELD_NAME_CAMERA_COMMERCIO %>"
		 type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_CAMERA_COMMERCIO %>" value="<h:requestParameter defaultValue="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO) %>" property='<%= ParametriServletRubrica.FIELD_NAME_CAMERA_COMMERCIO %>' />" />
	</td>
</tr>
<tr>
	<th><label for="">Partita IVA</label></th>
	<td>
		<input <%= disabled %> id="<%= ParametriServletRubrica.FIELD_NAME_PARTITA_IVA %>" 
		type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_PARTITA_IVA %>" value="<h:requestParameter  defaultValue="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.PARTITA_IVA) %>" property='<%= ParametriServletRubrica.FIELD_NAME_PARTITA_IVA %>' />" />
	</td>
</tr>

<tr>
	<th><label for="">Indirizzo</label></th>
	<td>
		<input <%= disabled %> id="<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>" 
		type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>" value="<h:requestParameter  defaultValue="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.INDIRIZZO) %>" property='<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>' />" />
	</td>
	
	<th><label for="">Civico</label></th>
	<td>
		<input <%= disabled %> id="<%= ParametriServletRubrica.FIELD_NAME_CIVICO %>" 
		 type="text" maxlength="10" name ="<%= ParametriServletRubrica.FIELD_NAME_CIVICO %>" value="<h:requestParameter  defaultValue="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.CIVICO) %>" property='<%= ParametriServletRubrica.FIELD_NAME_CIVICO %>' />" />
	</td>
</tr>				
<tr>
	<th><label for="">Citt�</label></th>
	<td>
		<input <%= disabled %> id="<%= ParametriServletRubrica.FIELD_NAME_CITTA %>" 
		type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_CITTA %>"  value="<h:requestParameter  defaultValue="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.CITTA) %>" property='<%= ParametriServletRubrica.FIELD_NAME_CITTA %>' />" />
	</td>
	<th><label for="">Provincia</label></th>
	<td>
		<input <%= disabled %>  id="<%= ParametriServletRubrica.FIELD_NAME_PROVINCIA %>" 
		type="text" maxlength="2" name ="<%= ParametriServletRubrica.FIELD_NAME_PROVINCIA %>" value="<h:requestParameter  defaultValue="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.PROVINCIA) %>" property='<%= ParametriServletRubrica.FIELD_NAME_PROVINCIA %>' />" />
	</td>
</tr>
<tr>
	<th><label for="">CAP</label></th>
	<td>
		<input <%= disabled %> id="<%= ParametriServletRubrica.FIELD_NAME_CAP %>" 
		type="text" maxlength="10" name ="<%= ParametriServletRubrica.FIELD_NAME_CAP %>" value="<h:requestParameter  defaultValue="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.CAP) %>" property='<%= ParametriServletRubrica.FIELD_NAME_CAP %>' />" />
	</td>		
</tr>
<tr>
	<th><label for="">Codice Fiscale del Rappresentante</label></th>
	<td>
		<input <%= disabled %> id="<%= ParametriServletRubrica.FIELD_NAME_CF_RAPPRESENTANTE %>" 
		type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_CF_RAPPRESENTANTE %>" value="<h:requestParameter  defaultValue="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE) %>" property='<%= ParametriServletRubrica.FIELD_NAME_CF_RAPPRESENTANTE %>' />" />
	</td>
</tr>

<tr>
	<th><label for="">Cognome</label></th>
	<td>
		<input <%= disabled %> id="<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>" type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>" value="<h:requestParameter  defaultValue="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.COGNOME) %>" property='<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>' />" />
	</td>
	
	<th><label for="">Nome</label></th>
	<td>
		<input <%= disabled %> id="<%= ParametriServletRubrica.FIELD_NAME_NOME %>" type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_NOME %>" value="<h:requestParameter  defaultValue="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.NOME) %>" property='<%= ParametriServletRubrica.FIELD_NAME_NOME %>' />" />
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
<input type="submit" name="<%= ParametriServletRubrica.OPERAZIONE %>" value="Salva" onclick="javascript:document.forms[0].action = 'rubrica?titleRubrica=<%= request.getAttribute("titleRubrica") %>&operazione=validaDati'; return validaDati('<h:requestParameter property="okVal" defaultValue="" />');">
<input type="button" value="Annulla" onclick=" javascript: window.close();" > 
<input type="hidden" name="parametri" id="parametri" value="">					 

</form>
</div>
<p>Nota: I dati sono prelevati dalla scheda attualmente attiva nell'anagrafica comune</p>
</body>
</html>
<% } catch (Exception e) {e.printStackTrace();} %>