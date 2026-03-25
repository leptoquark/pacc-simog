<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>

<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<link rel="stylesheet" href="theme/tabmenu.css"/>

<%@ page import="it.avlp.simog.common.servlet.*, it.avlp.simog.db.advanced.*" %>
<%@page import="it.avlp.simog.db.generated.*"%>
<%@ page import="it.avlp.simog.db.Costanti" %>

<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="it.avlp.simog.util.MessageHelper"%>
<title><utils:message key="rubrica.rubricaOperatoriEconomici" /></title>
</head>
<%@ include file="script/domUtils.js" %>
<%@ include file="include/i18n-init.inc" %>
<%@ include file="script/script.js" %>
	<script type="text/javascript" src="script/pageutils.js"></script>

<script type="text/javascript">
function winconf(){
		
	var x = (typeof i18n !== 'undefined' && i18n.confirm) ? i18n.confirm('error.deleteSubject') : window.confirm("Si sta per eliminare il soggetto dalla rubrica. Procedere?")
	alert(x)
	//if (x){
	//	rubrica.target="_self"; //stessa pagina
	//	rubrica.action = "�rubrica?operazione=Cancella";
	//	rubrica.submit();
	//}
}

	function checkFields(){
		
		if(document.getElementById('cFiscale').value=="" ||
	   document.getElementById('Denominazione').value=="" 
	   ){
	   	  return false;
	   		
	   }
	   else
	     return true; 
	}
	
	function doSubmit(action){
		document.getElementById('operazione').value=action;
		document.forms[0].submit();
		
	}
</script>
<body>
<div id="gabbia">

<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuDettRubrica.inc" %>

<form action="rubrica" method="post" name="rubrica" id="rubrica">
<input type="hidden" id="operazione" name="<%= ParametriServletRubrica.OPERAZIONE %>" value = "" />
<div id="bodypage">
<div class="bodypage-e">
<%@ include file="include/gestisciErrore.inc" %>

<div class="testo">

<%if("viewDetail".equals(request.getParameter("operazione"))){
	
	if (request.getAttribute(ParametriServlet.STORICOPARTECIPANTE) != null && request.getAttribute(ParametriServlet.STORICOPARTECIPANTE) != "") {
	 TableBean tab = (TableBean) request.getAttribute(ParametriServlet.STORICOPARTECIPANTE); 
	 if (!tab.isEmpty()) {%>
		
		<fieldset>
		<legend><utils:message key="rubrica.storicoModifiche" /></legend>
				<div align="center" class="scrollLittle">
					<% tab.printHTMLTable(new java.io.PrintWriter(out));%>
				</div>
	</fieldset>		
<%} }}  %>

<fieldset> 
	<legend><utils:message key="rubrica.dettaglioOperatoreEconomico" /></legend>

	<table cellpadding="3" border=0>
			<tbody>
<%
if("viewDetail".equals(request.getParameter("operazione"))){
	TableBean tableBean = (TableBean) request.getAttribute(ParametriServlet.TABLEBEAN); 
	
		int rowIndex=0;		
		TableBeanRow currentRow = tableBean.getRow(rowIndex); 
		 if ( rowIndex == 0 ) { %>
				<input type="hidden" name="<%= ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_PARTECIPANTE %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE) %>">
				<input type="hidden" name="<%= ParametriServletRubrica.FIELD_NAME_DATA_INIZIO_SOGGETTO %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG) %>">
				<input type="hidden" name="<%= ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.CODICE_FISCALE) %>">
				<input type="hidden" name="<%= ParametriServletRubrica.FIELD_NAME_FLAG_ESTERI %>" value="<%= request.getAttribute(ParametriServlet.FLAG_ESTERO) %>">
				<input type="hidden" id="ModificaPartecipante" name="ModificaPartecipante" value="0" />
				<%//@ include file="include/radioBoxPaesi.jsp" %>
				<%//@ include file="include/dropdownPaesi.jsp" %>
				<tr>
					<th><label>Paese Operatore Economico</label></th>
					<% Map<String,String> m = (Map)request.getAttribute(STATI_ESTERI.TABLE_NAME);
					   String paese = m.keySet().iterator().next();  // reperisco il paese
	                %>
					<td><input type="text" disabled value="<%= paese %>" />
					    <input type="hidden" name="<%= ParametriServletRubrica.FIELD_NAME_ID_STATO %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.ID_STATO) %>" />
					</td>
				</tr>
				<tr>
					<th><label for="">Codice Fiscale / Partita Iva</label></th>
					<td><input onchange="setFormModified('ModificaPartecipante')" type="text" disabled id="cFiscale" value="<%=currentRow.getNulledField(SOGGETTI_PARTECIPANTI.CODICE_FISCALE)%>" />
					</td>
				</tr>
				
				<tr>
					<th><label for="">Denominazione</label></th>
					<td>
						<input onchange="setFormModified('ModificaPartecipante')" id="Denominazione" type="text" maxlength="250" name  ="<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.DENOMINAZIONE) %>" />
					</td>
				</tr>
				
				<tr>
					<th><label for="">Camera Commercio</label></th>
					<td>
						<input onchange="setFormModified('ModificaPartecipante')" type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_CAMERA_COMMERCIO %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO) %>" />
					</td>
				</tr>
				
				<tr>
					<th><label for="">Partita IVA</label></th>
					<td>
						<input onchange="setFormModified('ModificaPartecipante')" type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_PARTITA_IVA %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.PARTITA_IVA) %>" />
					</td>
				</tr>
				
				<tr>
					<th><label for="">Indirizzo</label></th>
					<td>
						<input onchange="setFormModified('ModificaPartecipante')" type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.INDIRIZZO) %>" />
					</td>
					
					<th><label for="">Civico</label></th>
					<td>
						<input onchange="setFormModified('ModificaPartecipante')" type="text" maxlength="10" name ="<%= ParametriServletRubrica.FIELD_NAME_CIVICO %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.CIVICO) %>" />
					</td>
				</tr>
				<tr>
					<th><label for="">Citt�</label></th>
					<td>
						<input onchange="setFormModified('ModificaPartecipante')" type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_CITTA %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.CITTA) %>" />
					</td>
					<th><label for="">Provincia</label></th>
					<td>
						<input onchange="setFormModified('ModificaPartecipante')" type="text" maxlength="2" name ="<%= ParametriServletRubrica.FIELD_NAME_PROVINCIA %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.PROVINCIA) %>" />
					</td>
				</tr>
				<tr>	
					<th><label for="">CAP</label></th>
					<td>
						<input onchange="setFormModified('ModificaPartecipante')" type="text" maxlength="10" name ="<%= ParametriServletRubrica.FIELD_NAME_CAP %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.CAP) %>" />
					</td>		
				</tr>
				<tr>
					<th><label for="">Codice Fiscale del Rappresentante</label></th>
					<td>
						<input onchange="setFormModified('ModificaPartecipante')" type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_CF_RAPPRESENTANTE %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE) %>" />
					</td>
				</tr>
				<tr>
					<th><label for="">Cognome</label></th>
					<td>
						<input onchange="setFormModified('ModificaPartecipante')" id="Cognome" type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.COGNOME) %>" />
					</td>
					
					<th><label for="">Nome</label></th>
					<td>
						<input onchange="setFormModified('ModificaPartecipante')" id="Nome" type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_NOME %>" value="<%= currentRow.getNulledField(SOGGETTI_PARTECIPANTI.NOME) %>" />
					</td>
				</tr>
				
				<% } %>
		</tbody>
</table>
</fieldset>

</div>
 

<input type="button"  onclick="doSubmit('Modifica')"  value="Modifica">
<input type="button"  onclick="doSubmit('Cancella')"  value="Cancella">
<input type="button"   onclick="doSubmit('Indietro')"  value="Indietro">

<% } else if ("Aggiungi alla rubrica".equals(request.getParameter("operazione"))) {%>
<%@ include file="include/radioBoxPaesi.jsp" %>
<%@ include file="include/dropdownPaesi.jsp" %>
	<tr>
	<th><label for="">Codice Fiscale / Partita Iva</label></th>
	<td>
		<input type="text" id="cFiscale" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE %>' />" />
	</td>
</tr>

<tr>
	<th><label for="">Denominazione</label></th>
	<td>
		<input id="Denominazione" type="text" maxlength="250" name ="<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE %>' />" />
	</td>
</tr>

<tr>
	<th><label for="">Camera Commercio</label></th>
	<td>
		<input  type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_CAMERA_COMMERCIO %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_CAMERA_COMMERCIO %>' />" />
	</td>
</tr>
<tr>
	<th><label for="">Partita IVA</label></th>
	<td>
		<input type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_PARTITA_IVA %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_PARTITA_IVA %>' />" />
	</td>
</tr>

<tr>
	<th><label for="">Indirizzo</label></th>
	<td>
		<input type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>' />" />
	</td>
	
	<th><label for="">Civico</label></th>
	<td>
		<input type="text" maxlength="10" name ="<%= ParametriServletRubrica.FIELD_NAME_CIVICO %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_CIVICO %>' />" />
	</td>
</tr>				
<tr>
	<th><label for="">Citt�</label></th>
	<td>
		<input type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_CITTA %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_CITTA %>' />" />
	</td>
	<th><label for="">Provincia</label></th>
	<td>
		<input type="text" maxlength="2" name ="<%= ParametriServletRubrica.FIELD_NAME_PROVINCIA %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_PROVINCIA %>' />" />
	</td>
</tr>
<tr>
	<th><label for="">CAP</label></th>
	<td>
		<input type="text" maxlength="10" name ="<%= ParametriServletRubrica.FIELD_NAME_CAP %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_CAP %>' />" />
	</td>		
</tr>
<tr>
	<th><label for="">Codice Fiscale del Rappresentante</label></th>
	<td>
		<input type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_CF_RAPPRESENTANTE %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_CF_RAPPRESENTANTE %>' />" />
	</td>
</tr>

<tr>
	<th><label for="">Cognome</label></th>
	<td>
		<input id="Cognome" type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>' />" />
	</td>
	
	<th><label for="">Nome</label></th>
	<td>
		<input id="Nome" type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_NOME %>" value="<u:requestParameter property='<%= ParametriServletRubrica.FIELD_NAME_NOME %>' />" />
	</td>
</tr>


</tbody>
</table>
</fieldset>

</div>
<input type="button" onclick="doSubmit('Salva')"   value="Salva">
<input type="button" onclick="doSubmit('Indietro')"  value="Indietro">

<% } %>
</div>
</div>
</form>
<%@ include file="include/newfooter.inc" %>

</body>
</html>
