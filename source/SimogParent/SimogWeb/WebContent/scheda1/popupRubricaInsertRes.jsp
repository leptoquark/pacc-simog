<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ page import="it.avlp.simog.common.servlet.*, it.avlp.simog.db.advanced.*" %>
<%@page import="it.avlp.simog.db.generated.*"%>
<%@ page import="it.avlp.simog.db.Costanti" %>

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
<script type="text/javascript" src="xtree/treeutils.js"></script>
<body>


<% 		
		String ID_AGGIUDICAZIONE = request.getParameter(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE);
		String DATA_INIZIO_AGGIUDICAZIONE = request.getParameter(PSBD.DATA_INIZIO_AGGIUDICAZIONE);		
		String TIPO_CONTRATTO = request.getParameter(ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE);
		String FLAG_ENTE_SPECIALE = request.getParameter(ParametriServlet.FIELD_NAME_TIPO_CONTRATTO);
%>				

<!-- TB: ticket popup modali. Import css e js -->
<link rel="stylesheet" href="theme/jquery-ui-popup.css" />
<script type="text/javascript" src="script/other/jquery.js"></script>
<script type="text/javascript" src="script/other/jquery-ui.min.js"></script>
<div id="dialog"></div>
<!-- fine import popup modali -->

<div class="bodypage-e">
<form id="IdFormPopup" name="popupRubrica" action="rubrica?titleRubrica=<%= request.getAttribute("titleRubrica") %>"  method="post">

<%@ include file="../include/gestisciErrore.inc" %>
<%@ include file="../include/campiHidden.inc" %>

<input type="hidden" name="<%=PSBD.TAB%>" id="<%=PSBD.TAB%>" value="<%=request.getParameter(PSBD.TAB) %>">					 
<input type="hidden" name="<%=PSBD.ACTION_TYPE%>" id="<%=PSBD.ACTION_TYPE%>" value="<%=request.getParameter(PSBD.ACTION_TYPE) %>">
<input type="hidden" id="popupRubricaResp" value="true">					 
				 

<div class="testo">
<fieldset>

	<table style="width:100%;">
			<tbody >

				<tr>
					<th><label for="">Codice Fiscale</label></th>
					<td>
					<input type="text" maxlength="20" name  ="<%= ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE %>" id="codiceFiscale" 
					value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE %>"/>" >
					</td>
				</tr>
				
				<tr>
					<th><label for="">Cognome</label></th>
					<td>
						
						<input style="width:100%;" type="text" maxlength="50" name  ="<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>" 
						id="<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>"
						value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_COGNOME %>"/>" >
					</td>
				</tr>
				
				<tr>
					<th><label for="">Nome</label></th>
					<td>
						<input style="width:100%;" type="text" maxlength="50" name ="<%= ParametriServletRubrica.FIELD_NAME_NOME %>" 
						id="<%= ParametriServletRubrica.FIELD_NAME_NOME %>"
						value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_NOME %>"/>" >
					</td>
				</tr>
				
				<tr>
					<th><label for="">Telefono</label></th>
					<td>
						<input style="width:100%;" type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_TELEFONO %>" 
						value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_TELEFONO %>"/>" >
					</td>
				</tr>
				
				<tr>
					<th><label for="">Fax</label></th>
					<td>
						<input style="width:100%;" type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_FAX %>" 
						value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_FAX %>"/>" >
					</td>
				</tr>
				<tr>
					<th><label for="">Email</label></th>
					<td>
						<input style="width:100%;" type="text" maxlength="64" name ="<%= ParametriServletRubrica.FIELD_NAME_EMAIL %>" 
						value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_EMAIL %>"/>" >
					</td>
					
				</tr>
<tr>
	<th><label for="">Indirizzo</label></th>
	<td>
		<input style="width:100%;" id="Indirizzo" type="text" maxlength="100" name ="<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>" 
		value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_INDIRIZZO %>"/>" >
	</td>	
</tr>				
<tr>
	<th><label for="">Cap</label></th>
	<td>
		<input id="Cap" type="text" maxlength="10" name ="<%= ParametriServletRubrica.FIELD_NAME_CAP %>"  value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_CAP %>"/>" >
	</td>	
</tr>
<% // id?!? %>
<tr>
	<th><label for="">Comune Istat</label></th>
	<td>
		<input id="sel_ISTAT" type="text" maxlength="20" name ="<%= ParametriServletRubrica.FIELD_NAME_COMUNE_ISTAT %>"  value="<h:requestParameter property="<%= ParametriServletRubrica.FIELD_NAME_COMUNE_ISTAT %>"/>" >
			<a class="getCPV" href="#"  onclick="apripopup('ricercaIstat.jsp','sel_ISTAT')" 
				title="Lista codici ISTAT"><img src="img/icon_info_sml.gif"></a>
	</td>	
</tr>				

</tbody>
</table>
</fieldset>

</div>
<input type="submit" name="<%= ParametriServletRubrica.OPERAZIONE %>" 
	id="<%= ParametriServletRubrica.OPERAZIONE %>" 
	value="Salva in rubrica" >
<input type="button" value="Annulla" onclick=" javascript: window.close();" > 
<!-- <input type="button" value="Annulla" onclick=" javascript:{ document.getElementById( 'codiceFiscale' ).value = '&' ; cercaInRubrica( '<%=request.getParameter(PSBD.TAB) %>' ); }" >  -->
<input type="hidden" name="parametri" id="parametri" value="">					 

</form>


</div>
</body>
</html>
