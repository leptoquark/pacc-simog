<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="../errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.db.Costanti" %>
<%@ page import="it.avlp.simog.tags.NavigationComboTag"%>
<%@ page import="it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean"%>
<%@ page import="it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean"%>
<%@ page import="it.avlp.simog.util.MessageHelper"%>
<%@ page import="java.util.*"%>
<%@ page import="it.avlp.simog.util.PageHelper" %>
<%
	String msgNonRaggruppata = MessageHelper.getMessage(request, "multilotto.msgNonRaggruppata");
	String msgAggiuntaGruppo = MessageHelper.getMessage(request, "multilotto.msgAggiuntaGruppo");
	String msgAggiuntaNuovoGruppo = MessageHelper.getMessage(request, "multilotto.msgAggiuntaNuovoGruppo");
%>

<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
	function myDoAction(action, codice){
		var msg = "";
		if(action=="<%=ParametriServlet.ACTION_AGGIUNGI_SINGOLA %>"){
			msg="L'aggiudicazione non sar� raggruppata. Procedere?";
		}
		if(action=="<%=ParametriServlet.ACTION_AGGIUNGI_AL_GRUPPO %>"){
			msg="L'aggiudicazione sar� aggiunta al gruppo selezionato. Procedere?";
		}
		if(action=="<%=ParametriServlet.ACTION_CREA_NUOVO_GRUPPO %>"){
			msg="L'aggiudicazione sar� aggiunta al nuovo gruppo. Procedere?";
		}
		if(confirm(msg)){
	    	var actElem =  document.getElementById('toDo');
	    	actElem.value=action;
	    	var codiceSelezionato =  document.getElementById('<%=ParametriServlet.CODICE_SELEZIONATO%>');
	    	codiceSelezionato.value=codice;
	   	document.forms[0].submit();
	   	return;
		}
	}
</script>

<html>
<head>
<% int indiceTab = 0;%>

<%! 
   //metodo per tenere memoria delle aggiudicazioni selezionate per un nuovo appalto multilotto
   private boolean isCheckedAggiudicazione (long idAggiudicazione, String [] aggiudicazioni){
	    boolean result=false;
	    String idAggStr = String.valueOf(idAggiudicazione);
	    if(aggiudicazioni!=null && aggiudicazioni.length>0){
	    	for(int i=0; i<aggiudicazioni.length; i++){
	    		if (idAggStr.equals(aggiudicazioni[i]))
	    			result=true;
	    	}
	   }
	   return result;
   }
%>

<%--Carico la lista delle schede gia compilate e i dati della gara --%>
 
<link rel="stylesheet" href="theme/tabmenu.css"/>

<script type="text/javascript"  src="script/pageutils.js"></script>

<title><utils:message key="multilotto.gestioneMultilotto" /> - <%= user.getProfilo() %></title>

</head>
<c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>
<%-- 
<c:set var="aggiudicazione" value="${requestScope['<%=ParametriServlet.AGGIUDICAZIONE_CORRENTE%>']}"></c:set>
--%>
<% List<AggiudicatarioBean> listaAggiudicatari = (List<AggiudicatarioBean>)request.getAttribute(ParametriServlet.AGGIUDICATARI_CORRENTI); %>
<% AggiudicazioneBean aggiudicazione = (AggiudicazioneBean)request.getAttribute(ParametriServlet.AGGIUDICAZIONE_CORRENTE); %>
<% Map<String,List<AggiudicazioneBean>> mappaMultilotto = (Map)request.getAttribute(ParametriServlet.MAPPA_MULTILOTTO); %>
<% String [] aggiudicazioniDaAggiungere = request.getParameterValues(ParametriServlet.AGGIUDICAZIONI_DA_AGGIUNGERE);%>	
<% String idAggiudicazionePrincipale = request.getParameter(ParametriServlet.AGGIUDICAZIONE_PRINCIPALE);%>	

<body>
	<div id="gabbia">
		<%@ include file="include/header.inc" %>
	
	<form action="<%=ParametriServlet.SRV_GESTIONE_MULTILOTTO_NEW %>">
	<input type="hidden"  value="" name="toDo" id="toDo"/>
	<input type="hidden"  value="" name="<%=ParametriServlet.CODICE_SELEZIONATO%>" id="<%=ParametriServlet.CODICE_SELEZIONATO%>"/>	
	<input type="hidden"  value="<%=aggiudicazione.getIdAggiudicazione() %>" name="<%=PSBD.FIELD_NAME_AGG_ID_AGGIUDICAZIONE%>" id="<%=PSBD.FIELD_NAME_AGG_ID_AGGIUDICAZIONE%>"/>	
	<input type="hidden"  value="<%=aggiudicazione.getDataInizioAggiudicazione() %>" name="<%=PSBD.FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE%>" id="<%=PSBD.FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE%>"/>	
		
	<input type="hidden"  value="<c:out value='${datiGara.idLotto}'/>" name="<%=ParametriServlet.FIELD_NAME_ID_LOTTO %>" id="<%=ParametriServlet.FIELD_NAME_ID_LOTTO %>"/>	
	<div class="bodypage">
		<div class="bodypage-e">
			<h1><utils:message key="multilotto.gestioneContrattiMultilotto" /></h1>
			
			<%@ include file="include/gestisciErrore.inc" %>

		<div class="hmenu">	
		<ul>
			<li>
				<c:url  value="datiComuni" var="datiComuniURL">
					<c:param name="toDo" value="load"></c:param>
				</c:url>
			   <a href="<c:out value='${datiComuniURL}'/>"><utils:message key="multilotto.mostraDatiComuni" />&nbsp;<c:out value="${markerOk}"/></a> 		   
			</li> 
		</ul>	
		</div>
			
		<fieldset>
		   <% if(listaAggiudicatari.size()>1){ %>
			<legend><utils:message key="multilotto.aggiudicatariComuni" /></legend>
			<%}else{ %>
			<legend><utils:message key="multilotto.aggiudicatarioComune" /></legend>
			<% } %>
			<div style="width: 99%;"  >
			
			<div class="gara">	 	
			 
		    <table align="center" width="300px">   
			<tr> 
		     	<th class="garaTh"><utils:message key="multilotto.denominazione" /></th>
		     	<th class="garaTh"><utils:message key="multilotto.codiceFiscale" /></th> 
		   </tr>
		    
		   <%for(AggiudicatarioBean aggBean : listaAggiudicatari){ %>
				<tr>
					<td class="garaTd"><%=aggBean.getSoggettoPartecipante().getDenominazione() %></td>
				 	<td  class="garaTd"><%=aggBean.getSoggettoPartecipante().getCodiceFiscale()%></td>
				</tr>
			<% } %>
			</table>
		</div>
		</div>
		</fieldset>
		<br />
		<br />
			
		<fieldset>
			<legend><utils:message key="multilotto.aggiudicazioneSelezionata" /></legend>
			<div style="width: 99%;"  >
			
			<div class="gara">	 	
			 
		    <table align="center" width="300px">   
			<tr> 
		     	<th class="garaTh">CIG e Progressivo Aggiudicazione</th>
		     	<th class="garaTh">Data Inserimento Aggiudicazione</th> 
		     	<th class="garaTh">Stato Aggiudicazione</th>
		   </tr>
		    
		    <c:set var="counter" value='0' scope="page"/>
				<tr>
					<td class="garaTd"><c:out value="${datiGara.fullCIG}"/>-<%=aggiudicazione.getProgCUI() %> 			
						<% if(aggiudicazione.getProgCuiRiaggiudicato()>0){ %>
							&nbsp;&nbsp;( riaggiudica:&nbsp; ${datiGara.fullCIG}-<%=aggiudicazione.getProgCuiRiaggiudicato() %> )
						<% } %>
					</td>
				 	<td  class="garaTd"><%=PageHelper.getViewDate(aggiudicazione.getDataInizioAggiudicazione()) %></td>
					<td  class="garaTd"><%=aggiudicazione.getDescrizioneStato() %></td>
				</tr>
			</table>
		</div>
		</div>
		<div class="infoBlock">	
			 <div class="leftLineInfo">
			 <input type="button" value="Mantieni l'Aggiudicazione selezionata singola" onclick="myDoAction('<%=ParametriServlet.ACTION_AGGIUNGI_SINGOLA %>','');">
		    </div>  
		</div>
		</fieldset>
		<br />
		<br />
		<% if(mappaMultilotto.size()>0){
			int indice = 1;
		   Set<String> codiciContratto = mappaMultilotto.keySet();
		   for(String codice : codiciContratto){
			   List<AggiudicazioneBean> listaAggiudicazioni = mappaMultilotto.get(codice);
		%>
		<fieldset>
		   <% if (!"".equals(codice)) {%>
			<legend>Raggruppamento n�<%=indice %> </legend>
			<% indice++; %>
			<%}else{ %>
			<legend>Aggiudicazioni non raggruppate </legend>
			<% } %>			
			<div style="width: 99%;"  >
			
			<div class="gara">	 	
			 
		    <table align="center" width="300px">   
			<tr>
			   <% if ("".equals(codice)) {%>
			   <th class="garaTh">Aggiungi</th> 
			   <% }else{ %>
			   <th class="garaTh">Aggiudicazione principale</th> 
			   <% } %>
		     	<th class="garaTh">CIG e Progressivo Aggiudicazione</th>
		     	<th class="garaTh">Data Inserimento Aggiudicazione</th> 
		     	<th class="garaTh">Stato Aggiudicazione</th>
		   </tr>
		   <% for(AggiudicazioneBean agg : listaAggiudicazioni){ %>
			<tr>
			<%  if ("".equals(codice)) {%>
			   <%if (isCheckedAggiudicazione(agg.getIdAggiudicazione(),aggiudicazioniDaAggiungere)) {%>
		        <td  class="garaTd"><input type="checkbox" name="<%=ParametriServlet.AGGIUDICAZIONI_DA_AGGIUNGERE%>" value="<%= agg.getIdAggiudicazione() %>" checked /></td>					
	         <%} else {%>
	         <td  class="garaTd"><input type="checkbox" name="<%=ParametriServlet.AGGIUDICAZIONI_DA_AGGIUNGERE%>" value="<%= agg.getIdAggiudicazione() %>" /></td>						
   	      <% }} else {%>
	         <td  class="garaTd"><%if(agg.getFlagAggiudPrincipale().equals(Costanti.FLAG_VALORE_SI)) out.print("SI"); else out.print("NO"); %></td>						
   	      <% } %>
				<td class="garaTd"><%=agg.getCig() %>-<%=agg.getProgCUI() %></td>
			 	<td  class="garaTd"><%=PageHelper.getViewDate(agg.getDataInizioAggiudicazione()) %></td>
				<td  class="garaTd"><%=agg.getDescrizioneStato() %></td>
			</tr>
			<% } %>
			</table>
		</div>
		</div>
		<div class="infoBlock">	
			 <div class="leftLineInfo">
			 <% if (!"".equals(codice)) {%>
			 <input type="button" value="Associa a questo contratto" onclick="myDoAction('<%=ParametriServlet.ACTION_AGGIUNGI_AL_GRUPPO %>','<%=codice %>');">
			 <%}else{ %>
			 <input type="button" value="Crea un nuovo contratto" onclick="myDoAction('<%=ParametriServlet.ACTION_CREA_NUOVO_GRUPPO %>','');">
			 <% } %>
		</div></div>
		</fieldset>
		<br />
		<br />
		<%    
		     }
	    	} 
	   %>
		</div>
		</div>
		</form>
		<%@ include file="include/newfooter.inc" %>
	</div>
</body>

</html>