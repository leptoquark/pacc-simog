<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>


<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.util.*" %>

<% String currentDate = PageHelper.getCurrentDate(); %>

<title>SIMOG - <utils:message key="transazioni.consultazioneTransazioni" /></title>
</head>

<%int indiceTab = 0; %>

<body>
<div id="gabbia">
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuAVCP.inc" %>

	<form action="visualizzaTransazioni" method="post" >

	<div id="bodypage">
		<div class="bodypage-e">
		
			<h1><utils:message key="transazioni.visualizzazioneTransazioni" /></h1>
			<%@ include file="include/gestisciErrore.inc" %>

			<div class="testo">
				<fieldset>
				<legend><utils:message key="ricerca.filtriNominali" /></legend>
<TABLE>
					<tr>
						<td class="detailHelp" colspan="2"><utils:message key="transazioni.ricercaPerOperatore" /></td>			
					</tr>
					
				    <tr>
						<td><utils:message key="transazioni.cfRSSA" /></td>
						<td><input tabindex="<%= indiceTab %>" type="text" name="<%= ParametriServlet.FIELD_NAME_CF_OPERATORE %>" size="18" maxlength="16"></td>
					</tr>
					<tr>
						<td class="detailHelp" colspan="2"><utils:message key="transazioni.ricercaPerStazioneAppaltante" /></td>			
					</tr>
					
				    <tr>
						<td><utils:message key="ricerca.idStazioneAppaltante" /></td>
						<td><input tabindex="<%= indiceTab %>" type="text" name="<%= ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE %>" size="18" maxlength="16"></td>
					</tr>
					
					<tr>
						<td class="detailHelp" colspan="2"><utils:message key="transazioni.ricercaPerAmministrazione" /></td>
					</tr>
					<tr>
						<td><utils:message key="ricerca.cfAmministrazione" /></td>
						<td><input tabindex="<%= indiceTab %>" type="text" name="<%= ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE %>" size="18" maxlength="16"></td>
		
					</tr>
					
					<tr>
						<td class="detailHelp" colspan="2"><utils:message key="transazioni.ricercaPerGara" /></td>
					</tr>
					<tr>
						<td><utils:message key="ricerca.numeroGara" /></td>
						<td><input tabindex="<%= indiceTab %>" type="text" name="<%= ParametriServlet.FIELD_NAME_ID_GARA %>" size="14" maxlength="8"></td>
					
					</tr>
<tr>
					
					</tr>
<tr>
						<td class="detailHelp" colspan="2"><utils:message key="transazioni.ricercaPerLotto" /></td>
</tr>

					
    <tr>
						<td><utils:message key="transazioni.cigLotto" /></td>
						<td><input tabindex="<%= indiceTab %>" type="text" name="<%= ParametriServlet.FIELD_NAME_CIG %>" size="14" maxlength="10"></td>
					</tr>
    <tr>
      <td class="detailHelp"><utils:message key="transazioni.includiNonPagati" /></td>
      <td><input type="checkbox" name="includiNonPagati" value="nonPagati"></td>
    </tr>
  </table>
</fieldset>
				</div>
				
				
			<div class="testo">
				<fieldset>
					<legend><utils:message key="ricerca.filtriTemporali" /></legend>
				<table cellpadding="3">
			    <tr>
			      <td colspan="3">
			      <table>
			        
			          <tr>
			            <td colspan="3"><utils:message key="transazioni.daDataPubblicazione" /></td>
			          </tr>
			          <tr>
			            <td><select tabindex="<%=++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_DD_START %>">
			                    <% it.avlp.simog.util.PageHelper.printGiorni(out, null); %>
								</select></td>
			            <td><select tabindex="<%=++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_MM_START %>">
			                    <% it.avlp.simog.util.PageHelper.printMesi(out, null); %>
								</select></td>
			            <td> <select tabindex="<%=++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_AAAA_START %>">
			            <% it.avlp.simog.util.PageHelper.printAnniStart(out); %>
								</select>
								
							</td>
			          </tr>
			        </table>
			      </td>
			      <td colspan="3">
			      <table>
			        
			          <tr>
			            <td colspan="3"><utils:message key="transazioni.aDataPubblicazione" /></td>
			          </tr>
			          <tr>
			            <td><select tabindex="<%=++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_DD_END %>">
			                    <% PageHelper.printGiorni(out, PageHelper.getDay(currentDate)); %>
								</select></td>
			            <td><select tabindex="<%=++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_MM_END %>">
			                    <% it.avlp.simog.util.PageHelper.printMesi(out, PageHelper.getMonth(currentDate)); %>
								</select></td>
			            <td> <select tabindex="<%=++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_AAAA_END %>">
			            <% it.avlp.simog.util.PageHelper.printAnni(out, PageHelper.getYear(currentDate)); %>
								</select>
							</td>
			          </tr>
			        </table>
			      </td>
			      <td><utils:message key="transazioni.inserireIntervalloPubblicazione" /></td>
			    </tr>
			    <tr>
			      <td colspan="3">
			      <table width="100%">
			        
			          <tr>
			            <td colspan="3"><utils:message key="transazioni.daDataScadenza" /></td>
			          </tr>
			          <tr>
			            <td><select tabindex="<%=++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_DD_START %>">
			                    <% it.avlp.simog.util.PageHelper.printMesi(out, null); %>
								</select></td>
			            <td><select tabindex="<%=++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_MM_START %>">
			                    <% it.avlp.simog.util.PageHelper.printMesi(out, null); %>
								</select></td>
			            <td><select tabindex="<%=++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_AAAA_START %>">
			            <% it.avlp.simog.util.PageHelper.printAnniStart(out); %>
							</select></td>
			          </tr>
			        
			      </table>
			      </td>
			      <td colspan="3">
			      <table width="100%">
			        
			          <tr>
			            <td colspan="3"><utils:message key="transazioni.aDataScadenza" /></td>
			          </tr>
			          <tr>
			            <td><select tabindex="<%=++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_DD_END %>">
			                    <% it.avlp.simog.util.PageHelper.printGiorni(out, PageHelper.getDay(currentDate)); %>
							</select></td>
			            <td><select tabindex="<%=++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_MM_END %>">
			                    <% PageHelper.printMesi(out, PageHelper.getMonth(currentDate)); %>
							</select></td>
			            <td><select tabindex="<%=++indiceTab%>" name="<%= ParametriServlet.FIELD_NAME_DATA_SCADENZA_AAAA_END %>">
			            <% it.avlp.simog.util.PageHelper.printAnni(out, "" + PageHelper.SELECTED_MAX_ANNO); %>
						</select></td>
			          </tr>
			      </table>
			      </td>
			      <td><utils:message key="transazioni.inserireIntervalloScadenza" /></td>
			    </tr>
			    <tr>
			    <td colspan="2"><input type="submit" name="ricerca" value="<utils:message key="button.avviaRicerca" plain="true" />"></td>
			    </tr>
			  </table>				
			  </fieldset>
			</div>
</div>
</div>
<%@ include file="include/newfooter.inc" %>	

</body>
</html>
