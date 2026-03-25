<%@ page contentType="text/html; charset=iso-8859-1" language="java"  %>

<%@ include file="/include/newbasicHeader.inc" %>

<title>Presa in carico</title>
<script type="text/javascript">



function checkSelection() {
	if(confirm("Procedere con la presa in carico?"))
		document.getElementById("presaInCarico").submit(); 
	else
		return false;
}

</script>
</head>
<%@ include file="/include/controlloSessione.inc" %>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletRubrica"%>

<%@ include file="include/i18n-init.inc" %>
<%@ include file="/script/script.js" %>
<body>
	<div id="gabbia">
		<%@ include file="/include/header.inc" %>
		<div id="bodypage">
			<div class="bodypage-e">
				<form name="gestioneTab" id="presaInCarico" action="presaInCarico"  method="post" onkeypress="setFormModified('Modificato')">
					<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_LOTTO %>" 
						id="<%= ParametriServlet.FIELD_NAME_ID_LOTTO %>" 
						value="<%= request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO) %>">
					
					<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_INFO %>" 
						id="<%= ParametriServlet.FIELD_NAME_ID_INFO %>" 
						value="<%= request.getParameter(ParametriServlet.FIELD_NAME_ID_INFO) %>">
		
					<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO %>"
						id="<%= ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO %>"
						value="<%= request.getParameter(ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO) %>">
									
					<input type="hidden" name="pagina" id="pagina" value="">
					<input type="hidden" name="tipoAzione" id="tipoAzione" value="">					
					<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO %>" 
								id="<%= ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO %>" 
								value="<%= request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO)%>">					
										 
					<h1>Gestione Schede - Presa in carico</h1>
					<%@ include file="/include/gestisciErrore.inc" %>
					<br>
					<fieldset>
					<% Object cfAmmAgente = request.getAttribute(ParametriServlet.CF_AMM_DELEGANTE);
					     if(cfAmmAgente!=null && !"".equals(cfAmmAgente))	{
					     %>
							<div class="gara">
									<fieldset>
									<legend>Selezionare il Centro di Costo di destinazione della gara</legend>
											<table width="100%">
												<%  int i=0;
												    String cfAmmDelegante = (String) request.getAttribute(ParametriServlet.CF_AMM_DELEGANTE);
												     for ( java.util.Enumeration e = user.getUfficiByProfilo(user.getProfiloEnum()).elements(); e.hasMoreElements(); ) { %>
												     <% StazioneAppaltante currentSA =  (StazioneAppaltante)e.nextElement(); %>
												     <% if(cfAmmDelegante.equals(currentSA.getAmministrazione().getCodiceFiscale())) { %>
													   <tr>
														
														<td><input type="radio" name="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>" value="<%= currentSA.getIdUfficio()%>" tabindex="<%=i %>"
														<%= i==0 ? "checked":"" %>></td>
														<td><label for="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>"><%=currentSA.getDenominazioneAmministrazione()%> - <%= currentSA.getDenominazione()%></label></td>
													  </tr>
											 	   <% i++; 
													
												       } 
												    }%>
											 </table>					
										</fieldset>
									</div>
								<% } %>		
						<fieldset>
							<table width="100%">
							<% 
							if("false".equals(request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO))) {%>
								<tr>
									<th><label for="">Estremi del provvedimento di nomina</label></th>
									<td>
										<textarea maxlength="250" rows="5" cols="40" name="<%= PSBD.MOTIVAZIONE_ANNULLAMENTO %>" id="<%= PSBD.MOTIVAZIONE_ANNULLAMENTO %>"></textarea>
									</td>
								</tr>
							<% } else {%>
								<tr>
									<td>
										<h2>ATTENZIONE: NON E' POSSIBILE PROCEDERE ALLA PRESA IN CARICO IN QUANTO LA SCHEDA DATI COMUNI HA UNA RICHIESTA DI MODIFICA O CANCELLAZIONE IN CORSO</h2>
									</td>
								</tr>
							<% } %>
							</table>
						</fieldset>
						<input type="hidden" name="Modificato" value="0">
						<% if("false".equals(request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO))) {  
						       if(cfAmmAgente==null) {    %>
							<input type="submit" name="toDo" value="Conferma">
							<% } else { %>
							<input type="submit" onclick="javascript: checkSelection();" name="toDo" value="Conferma">
							<% } %>
						<% } %>
						<input type="button" value="Torna" onclick="javascript:history.back(-1)">
						<input type="reset" value="Reimposta" onclick="reimposta()">					
					</fieldset>
				</form>
			</div>
		</div>
		<%@ include file="/include/newfooter.inc" %>
		
	</div>
</body>	
