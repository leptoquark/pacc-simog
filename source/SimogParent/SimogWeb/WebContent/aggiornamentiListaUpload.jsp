<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/controlloSessione.inc"%>
<%@ include file="include/newbasicHeader.inc" %>

<%@ page import="it.avlp.simog.common.servlet.*" %>

<% Object caricamenti = request.getAttribute( ParametriServlet.TAB_CARICAMENTI_ATTIVI); %>
<% Object caricamentiCompletati = request.getAttribute( ParametriServlet.TAB_CARICAMENTI_COMPLETATI); %>

<% String [] listaCaricamenti = caricamenti != null ? (String[])caricamenti : null; %>
<% String [] listaCaricamentiCompletati = caricamentiCompletati != null ? (String[])caricamentiCompletati : null; %>
<title><utils:message key="admin.titoloAmministrazioneTabelle" /></title>
<script>
	function cancellaAggiornamenti(link){
		if(window.confirm("<%= it.avlp.simog.util.MessageHelper.getMessage(request, "admin.confermaCancellazioneAggiornamenti") %>")){
			location.href = link;
		}
	}
</script>
</head>

<body>
<div id="gabbia">
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuAmmTabelleServizio.inc" %>

	<div id="bodypage">
		<div class="bodypage-e">
			<h1><utils:message key="admin.aggiornamentiTabelleServizio" /></h1>
			<div class="hmenu">
				<ul>
				<li><a title="Tabelle di Servizio" href="<%= ParametriServlet.JSP_GESTIONE_TABELLE %>">Tabelle di Servizio</a></li>
				<li><a title="Inserisci Aggiornamento" href="<%= ParametriServlet.JSP_GESTIONE_TABELLE %>">Inserisci Aggiornamento</a></li>
				<% if ( listaCaricamenti != null && listaCaricamenti.length > 0 ) { %>
				<li><a title="Esegui Aggiornamento" href="<%= ParametriServlet.SRV_AGGIORNA_TABELLE %>">Esegui Aggiornamento</a></li>
				<% } %>
				</ul>
			</div>

			<%@ include file="include/gestisciErrore.inc" %>


			<div class="gara">
			<h4>Aggiornamenti da completare</h4>
				<table>
				<% if ( listaCaricamenti != null && listaCaricamenti.length > 0 ) { %>
				<% for ( int i = 0; i < listaCaricamenti.length; i++ ) { %>
					<tr>
						<th><%= listaCaricamenti[i] %></th>
					</tr>
				<% } %>
				<% } else { %>
					<tr>
					<td>Non sono presenti caricamenti in attesa</td>
					</tr>
				<% } %>
				</table>
			</div>
			
			<div class="hmenu">
				<ul>
				<li><a title="CancellaAggiornamenti" href="javascript:cancellaAggiornamenti('cancellaTabelleServizio')">Cancella aggiornamenti da completare</a></li>
				</ul>
			</div>		
			
			<div class="gara">
			<h4>Aggiornamenti completati</h4>
				<table>
				<% if ( listaCaricamentiCompletati != null && listaCaricamentiCompletati.length > 0 ) { %>
				<% for ( int i = 0; i < listaCaricamentiCompletati.length; i++ ) { %>
					<tr>
						<th><%= listaCaricamentiCompletati[i] %></th>
					</tr>
				<% } %>					
			
			<% } else { %>
				<tr>
				<td>Non sono presenti Caricamenti Completati</td>
				</tr>
			<% } %>
				</table>
			</div>
						
		</div>	
	</div>
						<%@ include file="include/newfooter.inc" %>
			
<!-- gabbia -->
</div>
</body>
</html>
