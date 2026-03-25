<%try{ %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@page import="it.avlp.simog.beans.AllegatoBean"%>
<%@page import="it.avlp.simog.beans.PubblicazioneBean"%>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="java.util.ArrayList" %>

<%@ include file="include/basicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<title><utils:message key="popup.storicoAllegatiGara" /> [<%= (String) request.getSession().getAttribute(ParametriServlet.SESSION_ID_GARA) %>]</title>
<base target="_self" />

<script type="text/javascript" src="xtree/treeutils.js"></script>

<script type="text/javascript">
<!--

function viewFile(id){
	document.forms[0].action='<%= ParametriServlet.SRV_STORICO_ALLEGATI %>?action=view&<%=ParametriServlet.IDALLEGATO %>=' + id;
   document.forms[0].target="_blank";
	document.forms[0].submit();	
	//var t=setTimeout("window.close()",2000); // patch chrome
}
//-->

//TB: gestione popup modale
function closePopup() {
	if(window.showModalDialog) {
		window.returnValue=null;
		window.close();
	} else {
		window.parent.jQuery("#dialog").dialog("close");
	}
}
</script>

</head>
<body>
	<form id="InviaFileAggiornamento" ENCTYPE="multipart/form-data" method="POST" action="<%= ParametriServlet.SRV_STORICO_ALLEGATI %>?action=save">

<h1><utils:message key="popup.storicoAllegati" /></h1>
	<%@ include file="include/gestisciErrore.inc" %>

	<br><br>
	<div class="hmenu">
		<ul>
		<li><a href="javascript:closePopup();"><utils:message key="button.chiudi" /></a></li>
		</ul>
	</div>		

	<div class="gara">
		<fieldset>
		<legend>Allegati inclusi</legend>
		
		<%	String disabled = "";				
		ArrayList listaDocumenti =(ArrayList) request.getAttribute(ParametriServlet.STORICO_ALLEGATI);
		if(listaDocumenti != null && listaDocumenti.size()>0){
			disabled = "disabled";
	%>
			<div class="elencoCategorie" style="height:150px;">
				<table background="#F1F2F8" class="TableBean" cellpadding="3" style="width:90%;">
					<tr>
						<th class='TableBeanTitle'><utils:message key="popup.tipoDocumento" /></th>
						<th class='TableBeanTitle'><utils:message key="popup.nomeDocumento" /></th>
						<th class='TableBeanTitle'><utils:message key="popup.note" /></th>
					</tr>
				<%
					for(int j=0;j<listaDocumenti.size();j++){
						AllegatoBean doc = (AllegatoBean)listaDocumenti.get(j);
				%>
					<tr class="TableBeanOdd">
						<% String descr = PubblicazioneBean.TipoDocumento.getEnumByTipo(doc.getTipoDoc()).getDescr(); %>
						<td><%= descr %></td>
						<td align="left"><%=doc.getNomeFile() %></td>
						<td align="left"><%=doc.getNote() %></td>
<% if(doc.getEsitoCheck() == null) { %>						
						<td><button type="button" name="visualizza" onclick="viewFile(<%=doc.getIdAllegato() %>);"><utils:message key="button.visualizza" /> </button></td>			
<% } else { %>
						<td><%= doc.getEsitoCheck() %></td>			
<% } %>
					</tr>	
				<%	}	%>
				</table>		
			</div>
			<% } 
		   else
			out.println("<BIG>NON SONO DISPONIBILI ALLEGATI PER LA GARA</BIG>");
			%>
			</fieldset>
		</div>
	</form>
</body>
</html>
<% } catch (Exception e) {e.printStackTrace();}%>