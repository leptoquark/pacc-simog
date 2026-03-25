<%@page import="it.avlp.simog.beans.StazioneAppaltante"%>
<%try{ %>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="it.avlp.simog.util.MessageHelper"%>

<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<title>SIMOG - <utils:message key="presaInCarico.presaInCaricoGaraDelegata" /> <utils:message key="presaInCarico.perGaraNumero" /> [<%= (String) request.getSession().getAttribute(ParametriServlet.SESSION_ID_GARA) %>]</title>
<base target="_self" />

<script type="text/javascript" src="xtree/treeutils.js"></script>

<script type="text/javascript">



function checkSelection() {
	if(confirm("<%= MessageHelper.getMessage(request, "presaInCarico.confermaPresaInCaricoGara") %>"))
		document.getElementById("PresaInCaricoGaraDelegata").submit(); 
	else
		return false;
}

</script>

<% String idGara =  (String) request.getAttribute(ParametriServlet.SESSION_ID_GARA); %>

</head>
<body>
	<div id="gabbia">
	
		<%@ include file="include/header.inc" %>
		<%@ include file="include/menu/menuGara.inc" %> 

	<form id="PresaInCaricoGaraDelegata" method="POST" action="<%= ParametriServlet.SRV_PRESA_IN_CARICO_GARA_DELEGATA %>?<%=ParametriServlet.SESSION_ID_GARA %>=<%=idGara%>&action=save">

<h1><utils:message key="presaInCarico.presaInCaricoGaraDelegata" /></h1>
	<%@ include file="include/gestisciErrore.inc" %>

	<br><br>
	<div class="hmenu">
		<ul>
		<li><a href="visualizzaDettaglio?<%=ParametriServlet.SESSION_ID_GARA %>=<%=idGara%>"><utils:message key="button.ritorna" /></a></li>
		</ul>
	</div>		

	<div class="gara">
		<fieldset>
		<legend><utils:message key="presaInCarico.selezionaCentroCosto" /></legend>
		
	
					<table width="100%">
						<%  int i=0;
						    String cfAmmDelegante = (String) request.getAttribute(ParametriServlet.CF_AMM_DELEGANTE);
						     for ( java.util.Enumeration e = user.getUfficiByProfilo(user.getProfiloEnum()).elements(); e.hasMoreElements(); ) { %>
						     <% StazioneAppaltante currentSA =  (StazioneAppaltante)e.nextElement(); %>
						     <!-- aggiunto trim() per eventuale cf con spazi -->
						     <% if(cfAmmDelegante != null && cfAmmDelegante.trim().equals(currentSA.getAmministrazione().getCodiceFiscale())) { %>
							   <tr>
								
								<td><input type="radio" name="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>" value="<%= currentSA.getIdUfficio()%>" tabindex="<%=i %>"
								<%= i==0 ? "checked":"" %>></td>
								<td><label for="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>"><%=currentSA.getDenominazioneAmministrazione()%> - <%= currentSA.getDenominazione()%></label></td>
							  </tr>
					 	   <% i++; 
							
						       } 
						    }%>
					 </table>
			<input type="button" onclick="javascript: checkSelection()"; value="<utils:message key="button.procedi" plain="true" />" />					

		<%--	String disabled = "";				
		ArrayList listaDocumenti =(ArrayList) request.getAttribute(ParametriServlet.STORICO_ALLEGATI);
		if(listaDocumenti != null && listaDocumenti.size()>0){
			disabled = "disabled";
	%>
			<div class="elencoCategorie" style="height:150px;">
				<table background="#F1F2F8" class="TableBean" cellpadding="3" style="width:90%;">
					<tr>
						<th class='TableBeanTitle'>Tipo Documento</th>
						<th class='TableBeanTitle'>Nome Documento</th>
						<th class='TableBeanTitle'>Note</th>
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
						<td><button type="button" name="visualizza" onclick="viewFile(<%=doc.getIdAllegato() %>);">Visualizza </button></td>			
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
			*/ --%>
			</fieldset>
		</div>
	</form>
	<%@ include file="include/newfooter.inc" %>
	</div>	
</body>
</html>
<% } catch (Exception e) {e.printStackTrace();}%>