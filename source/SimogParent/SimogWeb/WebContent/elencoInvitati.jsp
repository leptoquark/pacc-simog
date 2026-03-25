<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.beans.CIGBean"%>
<%@ page import="it.avlp.simog.beans.StatiScheda"%>
<%@ page import="java.math.BigDecimal"%>
<%@page import="it.avlp.simog.db.Costanti"%>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ taglib prefix="x" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="h" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/script/domUtilsInvitato.js" %>  
<%@ include file="include/i18n-init.inc" %>
<%@ include file="/script/script.js" %>  



<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean"%>

<script>

function myAction(action){
	var message="";
	if(action=="<%=ParametriServlet.ACTION_SALVA%>")
		message="<%= it.avlp.simog.util.MessageHelper.getMessage(request, "elenco.salvataggioListaInvitati") %>";
	if(confirm(message)){
		var todo=document.getElementById('toDo');
		todo.value=action;
		document.forms[0].submit();
		return;
	}
		
}

</script>
<c:set var="readonlyAffid" value="${roByFlusso eq true or (includerConfirmed and variazioniAnagrafiche ne true)}" />
<c:set var="readonlyAffidStr" value="${readonlyAffid ? 'readonly' : ''} " />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><utils:message key="elenco.gestioneElencoInvitati" /></title>
</head>
<body>


	<div id="gabbia">
<%@ include file="include/header.inc" %>
<%@ include file="include/menu/menuGara.inc" %>

   <%
     TableBean listaGare = null;
	  listaGare=(TableBean)request.getAttribute(ParametriServlet.TABLEBEAN); 
	  TableBeanRow currentRow = null;
   %>
	
		<div class="bodypage-e">
			<c:set var="saveAction" value="setAndSave"/>
			<h1><utils:message key="elenco.gestioneElencoInvitati" /></h1>
				<%@ include file="include/gestisciErrore.inc" %>
			<div class="hmenu">
				<ul><li><a title="<utils:message key="button.paginaPrecedente" plain="true" />" href="<%=ParametriServlet.SRV_VISUALIZZA_DETTAGLIO%>?<%=ParametriServlet.SESSION_ID_GARA%>=<%=listaGare.getRow(0).getNulledField(LOTTO.ID_GARA) %>&<%=ParametriServlet.FROM_GARE %>=<%=Costanti.FLAG_VALORE_SI %>"><utils:message key="button.ritorna" /></a></li></ul>
	
	
			</div><%-- hmenu --%>
		
			 <%	  
			 	int rowIndex = 0;
				currentRow = listaGare.getRow(rowIndex);
				String codiceGara = currentRow.getNulledField(GARA.ID_GARA);
				String oggettoGara = PageHelper.formattaTesto(currentRow.getNulledField(GARA.TABLE_NAME + GARA.OGGETTO));
				String numeroLotti = currentRow.getNulledField(GARA.NUMERO_LOTTI);
				/***************************************************/
				/****  Visualizzazione N.D. per l'importo gara  ****/
				/***************************************************/
				String importoGara = PageHelper.IMPORTO_ND;	
				try{
					String unformattedImporto = currentRow.getNulledField(GARA.IMPORTO_GARA);
					if(!"".equals(unformattedImporto) && new BigDecimal(unformattedImporto).compareTo(new BigDecimal(Costanti.IMPORTO_FUORI_SCALA)) != 0){
						importoGara = PageHelper.getFormattedImporto(unformattedImporto);
					}
				}catch(NumberFormatException t){	t.printStackTrace();	}
				/***************************************************/
				String importoSAGara = PageHelper.getFormattedImporto(currentRow.getNulledField(GARA.IMPORTO_SA_GARA));
				if(PageHelper.IMPORTO_ND.equals(importoSAGara)){
					importoSAGara = "Il valore sara' calcolato ad esito della conferma dei dati";
				}				
			 %>
				<h4>Informazioni Gara</h4>
				<div class="gara">
					<table>
						<tr>
							<th class="garaTh" width="40%">Numero Gara</th>
							<td class="garaTd"><%= codiceGara %></td>
						</tr>				
						<tr>
							<th class="garaTh" width="40%">Oggetto della Gara</th>
							<td class="garaTd"><%= oggettoGara %></td>
						</tr>
						<tr>
							<th class="garaTh" width="40%">Importo della Gara &euro;</th>
							<td class="garaTd"><%= importoGara %></td>
						</tr>
						<tr>
							<th class="garaTh" width="40%">Numero totale dei Lotti</th>
							<td class="garaTd"><%= numeroLotti %></td>
						</tr>
						<tr>
							<th class="garaTh" width="40%">Stato gara</th>
							<td class="garaTd"><%= currentRow.getNulledField( STATI_SCHEDA.DESCRIZIONE )%></td>
						</tr>
					</table>	

			</div>
		
		
<fieldset class="gara">

<form id="FormElencoInvitati" name="gestioneTab" action="gestioneElencoInvitati" method="post" onkeypress="setFormModified('Modificato')">

   <table width="100%">
		<tr>
		<td colspan="2">
			<div class="inthead">
				<label onclick="showMenu('<%= PSBD.TAB_AFFIDATARIO %>')" style="color:black; letter-spacing:0.2em;">
					<img src="img/minus.gif" id="img<%= PSBD.TAB_AFFIDATARIO %>"/> <utils:message key="elenco.elencoInvitati" /> </label>
				<div id="<%= PSBD.TAB_AFFIDATARIO %>" style="display: block;" >
				
				<%@ include file="/scheda1/invitato.jsp" %>
				</div>	
			</div>	    
			<input type="hidden"  value="" name="toDo" id="toDo">  
			<input type="hidden" name="<%=PSBD.TAB%>" id="<%=PSBD.TAB%>" value="">	
			<input type="hidden" name="<%=PSBD.ACTION_TYPE%>" id="<%=PSBD.ACTION_TYPE%>" value="">	
			<input type="hidden"  value="<%= codiceGara %>" name="<%= ParametriServlet.SESSION_ID_GARA%>">   
			<input type="hidden"  value="<%= PSBD.ACTION_SALVA %>" name="<%= PSBD.ACTION_SALVA %>" >       
		<c:if test="${readonlyAffid ne true}">                            
		<input type="button" value="<utils:message key="button.salva" plain="true" />" onclick="${saveAction}('FormElencoInvitati','<%=PSBD.TAB_INVITATO %>')">
		</c:if>
		</td>
	</tr>					
		
  </table>	
  </form>	
</fieldset>	
		
</div>
	<%@ include file="include/newfooter.inc" %>				
</div>
</body>
</html>