<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="../errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.tags.NavigationComboTag"%>

<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean"%>
<%@ page import="it.avlp.simog.db.Costanti" %>

<%@ page import="java.util.*"%>
<%@ page import="it.avlp.simog.util.PageHelper" %>
<%
	String msgNessunaAggiudicazione = MessageHelper.getMessage(request, "multilotto.msgNessunaAggiudicazioneSelezionata");
	String msgSoloUnaAggiudicazione = MessageHelper.getMessage(request, "multilotto.msgSoloUnaAggiudicazione");
	String msgContrattoEliminato = MessageHelper.getMessage(request, "multilotto.msgContrattoEliminato");
	String msgAggiudicazioniSingole = MessageHelper.getMessage(request, "multilotto.msgAggiudicazioniSingole");
	String msgErroreSelezione = MessageHelper.getMessage(request, "multilotto.msgErroreSelezione");
%>

<% int indiceTab = 0;%>

<%--Carico la lista delle schede gia compilate e i dati della gara --%>
 
<link rel="stylesheet" href="theme/tabmenu.css"/>

<script type="text/javascript"  src="script/pageutils.js"></script>

<title><utils:message key="multilotto.modificaMultilotto" /> - <%= user.getProfilo() %></title>

</head>
<body>
	<div id="gabbia">
		<%@ include file="include/header.inc" %>
		
	<div class="bodypage">
		<div class="bodypage-e">
			<h1><utils:message key="multilotto.modificaContrattiMultilotto" /></h1>
			
			<%@ include file="include/gestisciErrore.inc" %>

<script type="text/javascript">
	function myDoAction(action, totAggiudicazioni){
		var msg = "";
		if(action=="<%=ParametriServlet.ACTION_MODIFICA_GRUPPO %>"){
			var elenco = document.forms[0].<%=ParametriServlet.AGGIUDICAZIONI_DA_ELIMINARE%>;
			var retVal = false;
			var totDaCancellare = 0;
			
			//se non ci sono aggiudicazioni non � ammessa la modifica
			if(!elenco){
             alert("Non � stata selezionata nessuna Aggiudicazione da eliminare dal contratto.");
				 return false;
			}
			else{
    			 var num = elenco.length;
    			 //se ho un solo elemento
	    		 if(!num){
                 //se l'unico elemento � stato selezionato
                 if(elenco.checked){
                	 totDaCancellare = totDaCancellare+1;
                 }
	    		 }
	    		 else{
	    			 for (i = 0; i < num; i++) {
	    					if (elenco[i].checked) {
	    			          totDaCancellare = totDaCancellare+1;
	    					}
	    			 }
	    		 }

	          //se resta solo un'aggiudicazione nel contratto    
             if(totAggiudicazioni-totDaCancellare == 1){
                 alert("Non � possibile lasciare soltanto una Aggiudicazione nel contratto. Eliminare tutte le Aggiudicazioni dal contratto oppure lasciare almeno due Aggiudicazioni nel contratto.");
   				  return false;
             } 
             //se non ho selezionato alcuna aggiudicazione da cancellare  	
			    else if(totAggiudicazioni-totDaCancellare == totAggiudicazioni){
		             alert("Non � stata selezionata nessuna Aggiudicazione da eliminare dal contratto.");
		             return false;
		       }
             //se non resta alcuna aggiudicazione nel contratto  
             else if(totAggiudicazioni-totDaCancellare == 0){
                 msg="Attenzione, il contratto multilotto sar� eliminato e le Aggiudicazioni torneranno ad essere Aggiudicazioni singole. Procedere?";
             }
             // non ci sono aggiudicazioni non � ammessa la modifica
             else if(totAggiudicazioni-totDaCancellare > 1){
            	   msg="<%= msgAggiudicazioniSingole %>";
             }
			    //se la differenza tra totale e selezionate � minore di zero
			    else{
                 alert("<%= msgErroreSelezione %>");
   				  return false;
		       }

	          //se la selezione � corretta
             if(confirm(msg)){
     	    	    var actElem =  document.getElementById('toDo');
     	    	    actElem.value=action;
     	   	    document.forms[0].submit();
     	   	    return;
     		    }
			}
		}	
	}
</script>

<html>
<head>

<%! 
   //metodo per tenere memoria delle aggiudicazioni selezionate per l'eliminazione da un appalto multilotto
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

<% List<AggiudicazioneBean> listaAggiudicazioni = (List<AggiudicazioneBean>)request.getAttribute(ParametriServlet.LISTA_AGGIUDICAZIONI);%>
<% String [] aggiudicazioniDaEliminare = request.getParameterValues(ParametriServlet.AGGIUDICAZIONI_DA_ELIMINARE);%>	
<% String idAggiudicazionePrincipale = request.getParameter(ParametriServlet.AGGIUDICAZIONE_PRINCIPALE);%>	
<% String idLotto = request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO);%>
<% String codiceContratto = request.getParameter(PSBD.FIELD_NAME_CODICE_CONTRATTO);%>

		<div class="hmenu">	
		<ul>
			<li>
		   	<c:url value="javascript:history.back()" var="backURL" ></c:url>
				<a href="<c:out value='${backURL}'/>">Torna</a>
			</li> 
		</ul>	 
		</div>
		<form action="<%=ParametriServlet.SRV_EDIT_MULTILOTTO %>">
	     <input type="hidden"  value="" name="toDo" id="toDo"/>
	     <input type="hidden"  value="<%=idLotto %>" name="<%=ParametriServlet.FIELD_NAME_ID_LOTTO%>" id="<%=ParametriServlet.FIELD_NAME_ID_LOTTO%>"/>
	     <input type="hidden"  value="<%=codiceContratto %>" name="<%=PSBD.FIELD_NAME_CODICE_CONTRATTO %>" id="<%=PSBD.FIELD_NAME_CODICE_CONTRATTO %>"/>
	     
		<fieldset>
			<legend>Aggiudicazioni presenti per il Codice Contratto n�<strong><%=listaAggiudicazioni.get(0).getCodiceContratto()%></strong></legend>
			<div style="width: 99%;"  >
			
			<div class="gara">	 	
			 
		    <table align="center" width="300px">   
			<tr> 
			<th class="garaTh">Elimina</th>
				<th class="garaTh">Aggiudicazione Principale</th>
		     	<th class="garaTh">CIG e Progressivo Aggiudicazione</th>
		     	<th class="garaTh">Data Inserimento Aggiudicazione</th> 
		     	<th class="garaTh">Stato Aggiudicazione</th>    
		   </tr>
         
         <% for(AggiudicazioneBean aggBean : listaAggiudicazioni){ %>		    
			<tr>
			   <%if (isCheckedAggiudicazione(aggBean.getIdAggiudicazione(),aggiudicazioniDaEliminare)) {%>
		        <td  class="garaTd"><input type="checkbox" name="<%=ParametriServlet.AGGIUDICAZIONI_DA_ELIMINARE%>" value="<%= aggBean.getIdAggiudicazione() %>" checked /></td>					
	         <%} else {%>
	           <td  class="garaTd"><input type="checkbox" name="<%=ParametriServlet.AGGIUDICAZIONI_DA_ELIMINARE%>" value="<%= aggBean.getIdAggiudicazione() %>" /></td>						
   	      <% } %>
   	      <td  class="garaTd"><%if(aggBean.getFlagAggiudPrincipale().equals(Costanti.FLAG_VALORE_SI)) out.print("SI"); else out.print("NO"); %></td>						    
				<td class="garaTd"><%=aggBean.getCig() %>-<%=aggBean.getProgCUI() %></td>
			 	<td  class="garaTd"><%=PageHelper.getViewDate(aggBean.getDataInizioAggiudicazione()) %></td>
				<td  class="garaTd"><%=aggBean.getDescrizioneStato() %></td>
			</tr>
			<% } %>
			</table>
		
		</div>
		</div>
		<div class="infoBlock">	
		  <div class="leftLineInfo">	
			 <input type="button" value="Modifica contratto" onclick="myDoAction('<%=ParametriServlet.ACTION_MODIFICA_GRUPPO %>','<%=listaAggiudicazioni.size()%>');">
		  </div>
		</div>
		</fieldset>
		</form>
		<%@ include file="include/newfooter.inc" %>
	</div>
</body>

</html>