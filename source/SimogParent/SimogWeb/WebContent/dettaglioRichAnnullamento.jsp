<%@page import="it.avlp.simog.db.SimogFlags"%>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="../include/newbasicHeader.inc" %>
<title><utils:message key="richiesta.dettaglioRichiestaModifica" /></title>
</head>
<%@ include file="../include/controlloSessione.inc" %>
<%@ page import="it.avlp.simog.beans.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.db.generated.*" %>

<%@page import="it.avlp.simog.util.PageHelper"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.util.SimogProperties"%>

<%
   TableBean dati = (TableBean)request.getAttribute(ParametriServletRichAnnullamento.DETTAGLIO_TABLEBEAN); 
   int indiceTab =0;
   String listaScelta = request.getParameter(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA);
   String dataInizioRecord = request.getParameter(ParametriServletRichAnnullamento.FIELD_NAME_DATA_INIZIO_RECORD);
   String cig_lotto = request.getParameter(ParametriServletRichAnnullamento.FIELD_NAME_FILTRO_CIG);
%>

<%@ include file="include/i18n-init.inc" %>
<%@ include file="/script/script.js" %> 

<body>
	<div id="gabbia">
		<%@ include file="/include/header.inc" %>
<div id="bodypage">
	<div class="bodypage-e">
	<%@ include file="../include/gestisciErrore.inc" %>

	<form action="richiestaAnnullamento" method="post" name="richiestaAnnullamento" id="richiestaAnnullamento">

	<input type="hidden" name="<%= ParametriServletRichAnnullamento.FIELD_NAME_ID_RICHIESTA %>" 
				id="<%= ParametriServletRichAnnullamento.FIELD_NAME_ID_RICHIESTA %>" 
				value="<%= request.getParameter(ParametriServletRichAnnullamento.FIELD_NAME_ID_RICHIESTA) %>">	
				
	<input type="hidden" name="<%= ParametriServletRichAnnullamento.FIELD_NAME_ID_RECORD %>" 
				id="<%= ParametriServletRichAnnullamento.FIELD_NAME_ID_RECORD %>" 
				value="<%= request.getParameter(ParametriServletRichAnnullamento.FIELD_NAME_ID_RECORD) %>">	
				
	<input type="hidden" name="<%= ParametriServletRichAnnullamento.FIELD_NAME_BLOCCO %>" 
				id="<%= ParametriServletRichAnnullamento.FIELD_NAME_BLOCCO %>" 
				value="<%= request.getParameter(ParametriServletRichAnnullamento.FIELD_NAME_BLOCCO) %>">	
				
	<input type="hidden" name="<%= ParametriServletRichAnnullamento.FIELD_NAME_DATA_INIZIO_RECORD %>" 
				id="<%= ParametriServletRichAnnullamento.FIELD_NAME_DATA_INIZIO_RECORD %>" 
				value="<%= dataInizioRecord %>">	
					
	<input type="hidden" name="<%= ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA %>" 
				id="<%= ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA %>" 
				value="<%= listaScelta %>">	

	<input type="hidden" name="<%= ParametriServletRichAnnullamento.FIELD_NAME_FILTRO_CIG %>" 
				id="<%= ParametriServletRichAnnullamento.FIELD_NAME_FILTRO_CIG %>" 
				value="<%= cig_lotto %>">	
	

	<fieldset>
	<% String CIG = (dati.getFullSize()>0) ? dati.getRow(0).getNulledField(LOTTO.CIG) : ""; 
	if(CIG.length()>10)
				 CIG = CIG.substring(2);%>
	<% String BLOCCO = (dati.getFullSize()>0) 
				? it.avlp.simog.gestioneannullamentomanager.AnnullamentoManager.returnTableNameAnnullamento(dati.getRow(0).getNulledField(RICHIESTA_ANNULLAMENTO.BLOCCO)) : ""; %>
	<center><table><tr><td><p class="garaTh">SCHEDA: <%= BLOCCO %></p></td><td>&nbsp;</td><td><p class="garaTh">CIG / CUI: <%= CIG %></p></td></tr></table></center>
		<fieldset>
					<table>	
					<% if (dati.getFullSize()>0) {%>						
						<tr>
							<th width = "25%"><label for=""><utils:message key="richiesta.motivazioneRichiestaModifica" /></th>
							<th width="25%"><label for="">&nbsp;</th>
						</tr>
						<% 
						for (int numRow = 0; numRow<dati.getRowsCount(); numRow++) { 
						
							  TableBeanRow row = dati.getRow(numRow);
							  String ESITO = (dati.getFullSize()>0) ? row.getNulledField(RICHIESTA_ANNULLAMENTO.ESITO) : "";
							  String MOTIVAZIONE_RICHIESTA = (dati.getFullSize()>0) ? row.getNulledField(RICHIESTA_ANNULLAMENTO.MOTIVO_RICHIESTA) : "";
							  String MOTIVAZIONE_ESITO = (dati.getFullSize()>0) ? row.getNulledField(RICHIESTA_ANNULLAMENTO.MOTIVO_ESITO) : "";
							  String DATA_FINE = (dati.getFullSize()>0) ? row.getNulledField(RICHIESTA_ANNULLAMENTO.DATA_FINE) : "";
							  String idMotivoAnn = (SimogFlags.is30230_RFWEBSC03Active() ? row.getNulledField(RICHIESTA_ANNULLAMENTO.ID_MOTIVO_RICH) : "");

							  String desc = "";
								if (SimogFlags.is30230_RFWEBSC03Active()){
								   if("1".equals(idMotivoAnn))
								   	desc = "Errore materiale";
								   else if("2".equals(idMotivoAnn))
								      desc = "Duplicazione della scheda";
							  	   else if("3".equals(idMotivoAnn))
							  	      desc = "Scheda non dovuta";
							  	   else if("4".equals(idMotivoAnn))
							 	      desc = "Cancellazione scheda a ritroso";
							  	   else if("5".equals(idMotivoAnn))
								      desc = "Altro";

								   if("".equals(idMotivoAnn))
								   	desc = MOTIVAZIONE_RICHIESTA;
								   else
										desc = desc + "&nbsp;(" + MOTIVAZIONE_RICHIESTA + ")";
								}
								else {
									desc = MOTIVAZIONE_RICHIESTA;
								}

						%>
						<tr>
							<td><textarea rows="3" cols="40" readonly
							name="<%= ParametriServletRichAnnullamento.FIELD_NAME_MOTIVO_RICHIESTA %>"><%= desc %></textarea>
							</td>
									
						</tr>
						
						<% String checkedAccettata = ""; %>
						<% String checkedRifiutata = "";%>
						<% //if("".equals(verificaRequisiti)){ %>
						<% 	//verificaRequisiti="N"; %>
						<% //} %>
						<% if(ESITO!=null && ESITO!=""){ %>
						<% 		if("A".equalsIgnoreCase(ESITO)) {checkedAccettata = "checked";} %>
						<%		if("R".equalsIgnoreCase(ESITO)) {checkedRifiutata = "checked";} %>
						<% } %>
						
						
						
						<% 
						String disabled="";
						if(!listaScelta.equals(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA_DA_VALUTARE) && !listaScelta.equals(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA)) {
							disabled = "disabled";
							
						}%>
						
						<tr>
							<td>																					
								Richiesta Accettata<input id="radio1" tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServletRichAnnullamento.FIELD_NAME_ESITO %>" <%= checkedAccettata %>  <%= disabled %> value="<%= RichiestaAnnullamento.RICHIESTA_ACCETTATA %>">
								Richiesta Respinta<input id="radio2" tabindex="<%=++indiceTab%>" type="radio" name ="<%= ParametriServletRichAnnullamento.FIELD_NAME_ESITO %>" <%= checkedRifiutata %>  <%= disabled %> value="<%= RichiestaAnnullamento.RICHIESTA_RIFIUTATA %>">
								&nbsp;&nbsp;in data <%= it.avlp.simog.util.PageHelper.getFormattedDateTime(DATA_FINE) %>
							</td>
						</tr>
						
						<tr>
							<!--  <th><label for="">Contenzioso gara</th>  -->
							<th width = "25%"><label for="">Motivazione Esito Richiesta Modifica</th>
							<th width="25%"><label for="">&nbsp;</th>
						</tr>
						
						<tr>
							<td><textarea <%= "".equals(disabled) ? "" : "readonly" %>  rows="3" cols="40" 
							name="<%= ParametriServletRichAnnullamento.FIELD_NAME_MOTIVO_ESITO %>" id="<%= ParametriServletRichAnnullamento.FIELD_NAME_MOTIVO_ESITO %>"><%= MOTIVAZIONE_ESITO %></textarea>
							</td>
								
						</tr>
						
						<tr>
							<td>
								<%-- <input type="submit" onclick="return inputRadio()" name="<%= ParametriServletRubrica.OPERAZIONE %>" <%= disabled %> value="Salva" > --%>
								<input type="submit" onclick="return (inputRadio() && richiediAnnullamento2('<%= ParametriServletRichAnnullamento.FIELD_NAME_MOTIVO_ESITO%>'))" name="<%= ParametriServletRubrica.OPERAZIONE %>" <%= disabled %> value="Salva" >
								<input type="reset" name="<%= PSBD.ACTION %>" <%= disabled %> value="Reimposta">
								<input type="submit" name="<%= ParametriServletRubrica.OPERAZIONE %>" value="Torna" >	
							</td>
						</tr>
					<%	} 
						
					}%>
				
					</table>
				</fieldset>	
			</fieldset> 
	</form>
	</div>
	</div>
		<%@ include file="../include/newfooter.inc" %>
	</div>
</body>
</html>
