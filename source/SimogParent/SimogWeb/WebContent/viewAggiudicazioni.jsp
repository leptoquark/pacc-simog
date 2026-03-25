<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="../errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.tags.NavigationComboTag"%>

<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% int indiceTab = 0;%>

<%--Carico la lista delle schede gia compilate e i dati della gara --%>
 
<link rel="stylesheet" href="theme/tabmenu.css"/>
<link rel="stylesheet" href="theme/legend.css"/>

<script type="text/javascript"  src="script/pageutils.js"></script>

<title><utils:message key="multilotto.gestioneAggiudicazioni" /> - <%= user.getProfilo() %></title>

</head>
<c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>

<c:set var="listaAggiudicazioni" value="${sessionScope['listaAggiudicazioni']}"></c:set>
<c:set var="richiestaannullamento" value="${requestScope['daticomunistato']}"></c:set>
<c:set var="InfoComuniValid" value="${requestScope['InfoComuniValid']}"></c:set>
<c:set var="marker" value="<%= NavigationComboTag.MARKER %>"></c:set>
<c:set var="markerOk" value="<%= NavigationComboTag.OKMARKER %>"></c:set>
<c:set var="delegante" value="${requestScope['isDelegante']}"></c:set>
<body>
	<div id="gabbia">
		<%@ include file="include/header.inc" %>
		
	<div class="bodypage">
		<div class="bodypage-e">
			<h1><utils:message key="multilotto.gestioneSchedeAggiudicazioni" /></h1>
			
			<%@ include file="include/gestisciErrore.inc" %>
<SCRIPT type="text/javascript">

function askMe(url){
	if(confirm("Attenzione: la possibilita' di inserire aggiudicazioni successive alla prima e' limitata"
				 + " alle sole gare di servizi e forniture pubblicate entro la data del 31 gennaio 2008 "
				 + " e per le quali era prevista l'acquisizione di un unico CIG anche se espletate su piu' lotti."
				 +"\n\nPer la gare pubblicate a decorrere dal 1 febbraio 2008 e' possibile inserire una aggiudicazione"
				 + " successiva alla prima dopo aver correttamente compilato i dati di conclusione relativi al"
				 + " precedente contratto, e comunque limitatamente ai casi previsti dalla norma"
				 + " \n\nSi intende proseguire comunque?")){
		location.href = url;
	}
}

</script>
		<div class="hmenu">	
		<ul>
		
        <% if(session.getAttribute("ultimaRicerca") != null) {
				String href = (String)session.getAttribute("ultimaRicerca"); %>
				<li><a title="Pagina precedente" href="<%=href %>">Ritorna</a></li>
			<% } %>
		
			<li>
		   		<c:url value="gestioneSchede.jsp" var="backURL" ></c:url>
		   		<c:if test="${UTENTE.amministratore eq true}"> <c:url value="gestioneGareEXT.jsp" var="backURL" /> </c:if>
				<a href="<c:out value='${backURL}'/>">Nuova Ricerca</a>
			</li>  
			
			<li>
				<c:url  value="datiComuni" var="datiComuniURL">
					<c:param name="toDo" value="load"></c:param>
				</c:url>
				<%--gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate	
				<a href="<c:out value='${datiComuniURL}'/>">Mostra Dati Comuni<c:if test="${InfoComuniValid ne null && InfoComuniValid ne 'true'}">&nbsp;<c:out value="${marker}"/></c:if><c:if test="${InfoComuniValid ne null && InfoComuniValid eq 'true'}"><c:out value="${markerOk}"/></c:if></a> 
			   --%>

				<a href="<c:out value='${datiComuniURL}'/>">Mostra Dati Comuni<c:if test="${InfoComuniValid ne null && InfoComuniValid ne 'true'}">&nbsp;<c:out value="${marker}"/></c:if><c:if test="${InfoComuniValid ne null && InfoComuniValid eq 'true'}"><c:out value="${markerOk}"/></c:if></a> 

			</li> 
			<%-- 
			<c:if test="${!UTENTE.ossReg && datiGara.deleted eq false && (UTENTE.login eq datiGara.cfRup || datiGara.cfRup eq null) && richiestaannullamento eq '2' && delegaSchede eq false}">
		    	
		    	<c:choose>
		    	<c:when test="${not empty datiGara.CIG_ACC_QUADRO}">
					<c:url  value="SrvSchedaAdesione" var="newAggudicazioneURL">
						<c:param name="toDo" value="load"></c:param>
					</c:url>
					<c:if test="${not empty listaAggiudicazioni}">
				     <li><a title="Aggiungi una nuova Aggiudicazione" href="javascript:askMe('${newAggudicazioneURL}')">Aggiungi Aggiudicazione</a></li>
					</c:if>	
				</c:when>
			
				<c:when test="${datiComuni.FLAG_ESCLUSO eq 'N' &&  datiGara.importoLotto gt 150000}">
					<c:url  value="schedaA" var="newAggudicazioneURL">
						<c:param name="toDo" value="load"></c:param>
					</c:url>
               <c:if test="${not empty listaAggiudicazioni}">
				     <li><a title="Aggiungi una nuova Aggiudicazione" href="javascript:askMe('${newAggudicazioneURL}')">Aggiungi Aggiudicazione</a></li>
					</c:if>					
				</c:when>			
			  </c:choose>
			</c:if>
		   --%>
		</ul>	
		</div>
		<fieldset>
			<legend>Aggiudicazioni presenti </legend>
			<div style="width: 99%;"  >
			
			<div class="gara">	 	
			 
		    <table align="center" width="300px">   
			<tr> 
		     	<th class="garaTh">CIG e Progressivo Aggiudicazione</th>
		     	<th class="garaTh">&nbsp;</th> <!-- Riepilogo appalti multilotto -->
		     	<th class="garaTh">Data Inserimento Aggiudicazione</th> 
		     	<th class="garaTh">Stato Aggiudicazione</th>
		     	<th class="garaTh">Azioni</th>
		     </tr>
		    
		    <c:if test="${empty listaAggiudicazioni}">
		    <tr>
						<td nowrap="nowrap" class="garaTd">Non presente</td>
						<td nowrap="nowrap" class="hmenu"></td>
					 	<td  class="garaTd">Non presente</td>
						<td  class="garaTd">Non presente</td>
						<td>
							
						    <select onchange="goToSelectedResource(this)"  style="width:120px">
						    	<option></option>
						    	<u:navigationCombo aggiudId="0" 
						    				newStyle="background-color:gray;color:white" 
						    				disabledStyle="background-color:red;color:white" 
						    				loadStyle="background-color:green;color:white" 	
						    				pendingStyle="background-color:yellow;color:blue"
						    				delegante="${ delegante eq 'OK' }"/>
						    </select>
						  
						</td>
					</tr>
		    </c:if>
		    
		    <c:if test="${not empty listaAggiudicazioni}">
			    <c:set var="counter" value='0' scope="page"/>
				<c:forEach items="${listaAggiudicazioni}" var="aggiudicazione" >
					<tr>
						<td nowrap="nowrap" class="garaTd"><c:out value="${datiGara.fullCIG}"/>-<c:out value="${aggiudicazione.progCUI}" /> 			
							<c:if test="${aggiudicazione.progCuiRiaggiudicato > 0}">
								&nbsp;&nbsp;( riaggiudica:&nbsp; ${datiGara.fullCIG}-${aggiudicazione.progCuiRiaggiudicato } )
							</c:if>		
						</td>
						<%--gm aggiunto per appalti multilotto --%>
						<td nowrap="nowrap" class="hmenu">
						  	<c:if test="${aggiudicazione.codiceContratto ne null}">
						  	  <a title="Dettaglio appalti multilotto" href="<%= ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDE_MULTILOTTO %>?<%= ParametriServlet.FIELD_NAME_ID_LOTTO %>=${datiGara.idLotto}&<%= PSBD.FIELD_NAME_CODICE_CONTRATTO %>=${aggiudicazione.codiceContratto}">dettaglio multilotto</a> 	  
						  	</c:if>  
						</td>
					 	<td  class="garaTd"><c:out value="${aggiudicazione.viewDataInizioAggiudicazione}"></c:out></td>
						<td  class="garaTd"><c:out value="${aggiudicazione.descrizioneStato}"></c:out></td>
						<td>
							
						    <select onchange="goToSelectedResource(this)"  style="width:120px">
						    	<option></option>
						    	<u:navigationCombo aggiudId="${aggiudicazione.idAggiudicazione}" 
						    				newStyle="background-color:gray;color:white" 
						    				disabledStyle="background-color:red;color:white" 
						    				loadStyle="background-color:green;color:white" 	
						    				pendingStyle="background-color:yellow;color:blue"
						    				delegante="${ delegante eq 'OK' }"/>
						    </select>
						  
						</td>
						<%-- solo aggiudicazione
						<td class="hmenu">
						    <c:url  value="schedaA" var="modURL">
						        <c:param name="<%= PSBD.FIELD_NAME_ID_AGGIUDICAZIONE %>" value="${aggiudicazione.idAggiudicazione}"></c:param>
						    	<c:param name="<%= PSBD.DATA_INIZIO_AGGIUDICAZIONE %>" value="${aggiudicazione.dataInizioAggiudicazione}"></c:param>
						    </c:url>
						    <a href="<c:out value='${modURL}'/>">Visualizza</a>
						</td>
						--%>
					</tr>
					<c:set var="counter" value="${counter + 1}" scope="page"/>
				</c:forEach>
			</c:if>
			</table>
		</div></div>  
		</fieldset>
		<p></p>
		<div class="my-legend-container">
			<div class="my-legend">
			
			<div class="legend-scale">
			  <ul class="legend-labels">
			    <li style="text-align:left;"><span style="background:gray;"></span>Scheda non inviata</li>
			    <li><span style="background:green"></span>Scheda in definizione</li>
			    <li><span style="background:red"></span>Scheda confermata</li>
			  </ul>
			</div>
			</div>
		</div>
		</div>
		</div>
		<%@ include file="include/newfooter.inc" %>
	</div>
</body>

</html>