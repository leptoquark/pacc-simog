<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ page import="it.avlp.simog.common.servlet.*, it.avlp.simog.db.advanced.*" %>
<%@page import="it.avlp.simog.db.generated.*"%>
<%@ include file="../include/basicHeader.inc" %>
<%@ include file="../include/controlloSessione.inc" %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/theme/stile.css"/>
<%@ taglib uri="/WEB-INF/tlds/tagutils.tld" prefix="h" %>
<title><%= request.getAttribute("titleRubrica") %></title>
<base target="_self">
</head>
<%@ include file="../include/i18n-init.inc" %>
<%@ include file="/script/script.js" %>
<%@ include file="/script/domUtils.js" %>

<script type="text/javascript">
<!--
function showElem(id){
	if(document.getElementById(id) != null){
	   var style = document.getElementById(id).style;
		     if (style["display"] != "block")
        {
	        style["display"] = "block"
	        document.images["img"+id].src = "img/minus.gif";	        
        }
        else
        {
	        style["display"] = "none"
	        document.images["img"+id].src = "img/plus.gif";
        }
   	}
}

//-->
</script>
<body>


<% 		
		String ID_AGGIUDICAZIONE = request.getParameter(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE);
		String DATA_INIZIO_AGGIUDICAZIONE = request.getParameter(PSBD.DATA_INIZIO_AGGIUDICAZIONE);	
		String TIPO_CONTRATTO = request.getParameter(ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE);
		String FLAG_ENTE_SPECIALE = request.getParameter(ParametriServlet.FIELD_NAME_TIPO_CONTRATTO);
%>				


<div class="bodypage-e">
<form id="IdFormPopup" name="popupRubrica" action="rubrica?titleRubrica=<%= request.getAttribute("titleRubrica") %>"  method="post">

<%@ include file="../include/gestisciErrore.inc" %>
<%@ include file="../include/campiHidden.inc" %>

<input type="hidden" name="<%=PSBD.TAB%>" id="<%=PSBD.TAB%>" value="<%=request.getParameter(PSBD.TAB) %>">					 
<input type="hidden" name="<%=PSBD.ACTION_TYPE%>" id="<%=PSBD.ACTION_TYPE%>" value="<%=request.getParameter(PSBD.ACTION_TYPE) %>">					 
					 

<div class="testo">
<fieldset>

	<table style="width:100%;">
			<tbody>
				<%@ include file="../include/radioBoxPaesi.jsp" %>
				<%@ include file="../include/dropdownPaesi.jsp" %>
				<tr>
					<th><label for="">Codice Fiscale / Partita Iva</label></th>
					<td>
						<input type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE %>" id="codiceFiscale"
						 value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE %>" />" > 
					</td>
				</tr>
				<tr>
					<th><label for=""><utils:message key="table.denominazione" /></label></th>
					<td>
						<input  style="width:100%;" type="text" maxlength="250" name ="<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE %>" 
						value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE %>"/>" >
					</td>
				</tr>
				<tr>
					<th><label for=""><utils:message key="table.cameraCommercio" /></label></th>
					<td>
						<input type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_CAMERA_COMMERCIO %>" 
						 value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_CAMERA_COMMERCIO %>"/>" >
					</td>
				</tr>
				<tr>
					<th><label for=""><utils:message key="table.partitaIva" /></label></th>
					<td>
						<input type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_PARTITA_IVA %>" 
						 value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_PARTITA_IVA %>"/>" >
					</td>
				</tr>
				<tr>
					<th><label for=""><utils:message key="table.indirizzo" /></label></th>
					<td>
						<input  style="width:100%;" type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>" 
						 value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>"/>" >
					</td>
				</tr>
				<tr>
					<th><label for=""><utils:message key="table.civico" /></label></th>
					<td>
						<input type="text" maxlength="10" name ="<%= ParametriServletRubrica.FIELD_NAME_CIVICO %>" 
						 value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_CIVICO %>"/>" >
					</td>
				</tr>
				<tr>	
					<th><label for=""><utils:message key="table.cap" /></label></th>
					<td>
						<input type="text" maxlength="10" name ="<%= ParametriServletRubrica.FIELD_NAME_CAP %>" 
						 value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_CAP %>"/>" >
					</td>		
				</tr>
				<tr>
					<th><label for="">Citt�</label></th>
					<td>
						<input  style="width:100%;" type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_CITTA %>" 
						 value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_CITTA %>"/>" >
					</td>
				</tr>
				
				<tr>
					<th><label for=""><utils:message key="table.provincia" /></label></th>
					<td>
						<input type="text" maxlength="2" name ="<%= ParametriServletRubrica.FIELD_NAME_PROVINCIA %>" 
						 value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_PROVINCIA %>"/>" >
					</td>
				</tr>
				<tr>
					<th><label for=""><utils:message key="table.cognome" /></label></th>
					<td>
						<input  style="width:100%;" type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>" id="<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>"
						 value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>"/>" >
					</td>
				</tr>
				<tr>
					<th><label for=""><utils:message key="table.nome" /></label></th>
					<td>
						<input  style="width:100%;" type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_NOME %>" id="<%= ParametriServletRubrica.FIELD_NAME_NOME %>"
						 value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_NOME %>"/>" >
					</td>
				</tr>
				<tr>
					<th><label for="">Codice Fiscale del Rappresentante</label></th>
					<td>
						<input type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_CF_RAPPRESENTANTE %>" 
						 value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_CF_RAPPRESENTANTE %>"/>" >
					</td>
				</tr>
			</tbody>
	</table>
</fieldset>
</div>
<!-- 	onclick="javascript:salvalo();" -->
<input type="submit" name="<%= ParametriServletRubrica.OPERAZIONE %>" id="<%= ParametriServletRubrica.OPERAZIONE %>" 
	value="<utils:message key="rubrica.salvaInRubrica" plain="true" />">
<input type="button" value="<utils:message key="button.annulla" plain="true" />" onclick = "javascript: window.close();">
<input type="hidden" name="parametri" id="parametri" value="">					 
</form>
<script type="text/javascript">
function goBack(){

}
</script>

</div>
</body>
</html>
