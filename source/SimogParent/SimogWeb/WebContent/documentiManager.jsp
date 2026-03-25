<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@page import="it.avlp.simog.util.MessageHelper"%>
<%
	String msgSelezionareFile = MessageHelper.getMessage(request, "documenti.selezionareFile");
%>
<%@ include file="include/controlloSessione.inc"%>
<%@ include file="include/newbasicHeader.inc" %>

<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="it.avlp.simog.garamanager.lotto.DocumentoBean" %>
<% String idLotto =  request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO); %>
<% session.setAttribute( ParametriServlet.FIELD_NAME_ID_LOTTO, idLotto ); %>
<title>SIMOG - Gestione Documenti per lotto [<%= idLotto %>]</title>

<script type="text/javascript">
<!--
	function invioFile(){
		if(document.getElementById('InviaFileAggiornamento').putFile.value == ''){
			alert('<%= msgSelezionareFile %>');
			return false;
		}
		return true;
	}
//-->
</script>
</head>
<body>
		<form id="InviaFileAggiornamento" ENCTYPE="multipart/form-data" method="POST" action="<%= ParametriServlet.SRV_GESTISCI_DOCUMENTI %>?<%= ParametriServlet.FIELD_NAME_ID_LOTTO %>=<%= idLotto %>">
<div id="gabbia">
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuGestioneDocumentale.inc" %>


	<div id="bodypage">
		<div class="bodypage-e">
		
		<h1><utils:message key="documenti.titolo" /></h1>
		<%@ include file="include/gestisciErrore.inc" %>

		<div class="hmenu">
			<ul>
			<li><a title="<utils:message key="documenti.tornaModificaTitle" />" href="gestisciLotto?action=modifica&idLotto=<%= request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO) %>"><utils:message key="documenti.tornaModifica" /></a></li>
			</ul>
		</div>		

		<div class="gara">
			<fieldset>
			<legend><utils:message key="documenti.fileDocumentazione" /></legend>
			
	<%					
		ArrayList listaDocumenti =(ArrayList) session.getAttribute("documenti");
		if(listaDocumenti.size()>0){
	%>
		
			<div class="elencoCategorie" style="height:150px;">
								
				<table background="#F1F2F8" class="TableBean" cellpadding="3" style="width:90%;">
					
					<tr>
						<th class='TableBeanTitle'><utils:message key="documenti.codiceDocumento" /></th>
						<th class='TableBeanTitle'><utils:message key="documenti.nomeDocumento" /></th>
					</tr>
				<%
					
					for(int j=0;j<listaDocumenti.size();j++){
						DocumentoBean doc = (DocumentoBean)listaDocumenti.get(j);
				%>
					<tr class="TableBeanOdd">
						<td><%=doc.getId_documento() %></td>
						<td align="left"><%=doc.getNomeDocumento() %></td>
						
						
					</tr>	
						
				<%	}	%>
					
				</table>
				
				
			</div>
			<%}
		else
			out.println("<h5><utils:message key="documenti.nonDisponibiliAllegati" /></h5>");
			%>
					
			</fieldset>
		</div>
		
		<input type="hidden" name="<%=ParametriServlet.FIELD_NAME_ID_LOTTO %>" value="<%=request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO) %>" />

		<div class="testo">
			<fieldset>
			<legend><utils:message key="documenti.documentazioneGara" /></legend>
			<table>
			  <tr>		
				<td><input type="file" name="putFile"></td>
		      	<td><p class="detailHelp"><utils:message key="documenti.selezionarePercorso" /></p></td>
			    </tr>
			  <tr>
			    <td>
			    	<!--input type="hidden" name="ACTION_GET_LIST" value="upload" /-->
			    	
			    	<button type="submit" name="conferma" onclick="return invioFile();"><utils:message key="documenti.aggiungiDocumento" /></button>
			    </td>
			  </tr>
			</table>
			</fieldset>
		</div>
		<!-- input type="hidden" name="<= ParametriServlet.FIELD_NAME_FILE_AGGIORNAMENTO %>" value="viene assegnato"-->
	</div>
	</div>

		<%@ include file="include/newfooter.inc" %>
<!-- gabbia -->
</div>
</form>
</body>
</html>
