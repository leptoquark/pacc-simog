<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

<%@ include file="include/newbasicHeader.inc" %>

<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.db.advanced.*"%>
<%@ page import="it.avlp.simog.common.servlet.*"%>
<%@ page import="java.util.TreeMap"%>

<title><utils:message key="ricerca.titoloRicercaGara" /></title>
</head>

<body>
<div id="gabbia">

<%
	TreeMap map = (TreeMap)request.getAttribute("infogara");
	TableBean tab = (TableBean)request.getAttribute("infolotto");
	TableBean docs = (TableBean)request.getAttribute("docs");
%>

	<%@ include file="include/menu/menuRicercaDocumenti.inc" %>

<div id="header">
	<p align="right"><img src="img/repubblica_italiana.gif"/></p>
</div>

	<div id="bodypage">
		<div class="bodypage-e">
	
		<h1><utils:message key="ricerca.ricercaGare" /></h1>
		<%@ include file="include/gestisciErrore.inc" %>

	<div class="testo">

<!--  SCROLL -->
	<div class="scroll">
	
	
	<!-- SCROLL INSIDE -->
	<% if ( tab.getRowsCount() > 0 ) { %>
	
	<div class="scrollInside">

		<% String previousGara = null; %>
		<% String idGara = null; %>	
		<% boolean nuovaGara = true; %>
		
		
							
				<h4>Informazioni Gara</h4>
				<div class="elenco">
				<div class="gara">
					<table cellpadding="2">
						<tr>
							<th class="garaTh" width="40%">Amministrazione Competente</th>
							<td class="garaTd"><%= map.get(GARA.DENOM_AMMINISTRAZIONE) %></td>
						</tr>
						<tr>
						<th class="garaTh" width="40%">Stazione Appaltante</th>
						<td class="garaTd"><%= map.get(GARA.DENOM_STAZIONE_APPALTANTE) %></td>
						</tr>
					
						<tr>
						<th class="garaTh" width="40%">Oggetto della Gara</th>
						<td class="garaTd"><%= map.get( GARA.OGGETTO ) %></td>
						</tr>
						<tr>
						<th class="garaTh" width="40%">Data Creazione</th>
						<td class="garaTd"><%= PageHelper.getFormattedDate( (String)map.get( GARA.DATA_CREAZIONE ) ) %></td>
						</tr>
					</table>	
				<h5>Informazioni Lotti</h5>
			<!-- INFORMAZIONI LOTTI -->
			
			<% tab.printHTMLTable(new java.io.PrintWriter(out)); 
			
				if(docs.getRowsCount()>0){
			%>
			
			<!-- FINE INFORMAZIONI LOTTI -->
			
			<h5>Documenti Allegati</h5>
			
			<div class="elencoCategorie" style="height:150px;">
			<table class="TableBean" style="width:90%;" cellpadding="3">
				<tr>
					<th class="TableBeanTitle">Codice Documento</th>
					<th class="TableBeanTitle">Nome</th>
				</tr>
			
			<% 
				TableBeanRow riga = null;
				
			for(int i=0;i<docs.getRowsCount();i++){
				riga = docs.getRow(i);
				
				String titolo = riga.getNulledField(DOCUMENTO.NOMEDOCUMENTO);
				String id_doc = riga.getNulledField(DOCUMENTO.ID_DOCUMENTO);
				
				%>
				
				<tr class="TableBeanOdd">
					<td><%=id_doc %></td>
					<td><a target="_blank" href="VisualizzaDocumento?id=<%=id_doc %>"><%=titolo %></a></td>
					
				</tr>
				<% } %>
		</table>
		<% } else { %>
				<h5>NON SONO DISPONIBILI ALLEGATI PER IL LOTTO CORRENTE</h5>	
		<% } %>
		</div>
		</div>
		</div>
		<!-- Chiusura Ultima gara -->

		</div>
		<!-- INSIDE SCROLL FINE -->
		
		<% } %>
	<p><a href="RicercaPubblica.jsp">Torna alla pagina di ricerca</a></p>
</div>

</div>
	
<!-- Scroll FINE -->
		</div>
</div>
<%@ include file="include/newfooter.inc" %>
</div>
</body>
</html>
