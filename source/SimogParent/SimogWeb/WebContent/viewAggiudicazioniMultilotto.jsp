<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="../errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.tags.NavigationComboTag"%>
<%@ page import="it.avlp.simog.util.MessageHelper"%>
<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String msgAttenzioneAggiudicazioni = MessageHelper.getMessage(request, "multilotto.msgAttenzioneAggiudicazioniSuccessive");
	String msgAttenzioneAggiudicazioniJs = msgAttenzioneAggiudicazioni.replace("\\", "\\\\").replace("\"", "\\\"").replace("\r", "\\r").replace("\n", "\\n");
%>
<% int indiceTab = 0;%>

<%--Carico la lista delle schede gia compilate e i dati della gara --%>
 
<link rel="stylesheet" href="theme/tabmenu.css"/>

<script type="text/javascript"  src="script/pageutils.js"></script>

<title><utils:message key="multilotto.gestioneAggiudicazioniMultilotto" /> - <%= user.getProfilo() %></title>

</head>
<c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>

<c:set var="listaAggiudicazioni" value="${sessionScope['listaAggiudicazioni']}"></c:set>
<c:set var="richiestaannullamento" value="${requestScope['daticomunistato']}"></c:set>
<c:set var="InfoComuniValid" value="${requestScope['InfoComuniValid']}"></c:set>
<c:set var="marker" value="<%= NavigationComboTag.MARKER %>"></c:set>
<c:set var="markerOk" value="<%= NavigationComboTag.OKMARKER %>"></c:set>

<% String idLotto = (String)request.getSession().getAttribute(ParametriServlet.FIELD_NAME_ID_LOTTO); %>

<body>
	<div id="gabbia">
		<%@ include file="include/header.inc" %>
		
	<div class="bodypage">
		<div class="bodypage-e">
			<h1><utils:message key="multilotto.gestioneSchedeAggiudicazioniMultilotto" /></h1>
			
			<%@ include file="include/gestisciErrore.inc" %>
<SCRIPT type="text/javascript">

function askMe(url){
	if(confirm("<%= msgAttenzioneAggiudicazioniJs %>")){
		location.href = url;
	}
}

</script>
		<div class="hmenu">	
		<ul>
			<li>
		   	<c:url value="javascript:history.back()" var="backURL" ></c:url>
				<a href="<c:out value='${backURL}'/>">Torna</a>
			</li> 
		</ul>	 
		</div>
		<fieldset>
			<legend>Aggiudicazioni presenti per il Codice Contratto n� <strong>${listaAggiudicazioni[0].codiceContratto}</strong></legend>
			<div style="width: 99%;"  >
			
			<div class="gara">	 	
			 
		    <table align="center" width="300px">   
			<tr> 
		     	<th class="garaTh">CIG e Progressivo Aggiudicazione</th>
		     	<th class="garaTh">Data Inserimento Aggiudicazione</th> 
		     	<th class="garaTh">Stato Aggiudicazione</th>
		     	<th class="garaTh">Aggiudicazione Principale</th>
		     	<th class="garaTh">Dettaglio Aggiudicazione</th>
		     </tr>
		    
		    <c:set var="counter" value='0' scope="page"/>
		   <c:set var="mapInfoComuniMulti" value="${sessionScope['mapInfoComuniMulti']}"></c:set>
			<c:forEach items="${listaAggiudicazioni}" var="aggiudicazione" >
				<tr>
				   <%-- 
					<td class="garaTd"><c:out value="${datiGara.fullCIG}"/>-<c:out value="${aggiudicazione.progCUI}" /> 			
						<c:if test="${aggiudicazione.progCuiRiaggiudicato > 0}">
							&nbsp;&nbsp;( riaggiudica:&nbsp; ${datiGara.fullCIG}-${aggiudicazione.progCuiRiaggiudicato } )
						</c:if>		
					</td>
					--%>
					<td nowrap="nowrap" class="garaTd"><c:out value="${aggiudicazione.cig}"/>-<c:out value="${aggiudicazione.progCUI}" /> 			
						<c:if test="${aggiudicazione.progCuiRiaggiudicato > 0}">
							&nbsp;&nbsp;( riaggiudica:&nbsp; ${aggiudicazione.cig}-${aggiudicazione.progCuiRiaggiudicato } )
						</c:if>		
					</td>
				 	<td  class="garaTd"><c:out value="${aggiudicazione.viewDataInizioAggiudicazione}"></c:out></td>
					<td  class="garaTd"><c:out value="${aggiudicazione.descrizioneStato}"></c:out></td>
					<td  class="garaTd"><c:out value="${aggiudicazione.flagAggiudPrincipale eq 'S' ? 'SI' : 'NO'}"></c:out></td>
					<td nowrap="nowrap" class="hmenu">
				      <a title="<utils:message key="multilotto.vaiDettaglioAggiudicazione" />" href="<%=ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA %>?<%=ParametriServlet.FIELD_NAME_ID_LOTTO %>=${mapInfoComuniMulti[aggiudicazione.idAggiudicazione].idLotto}"><utils:message key="multilotto.visualizza" /></a>			   									      
					</td>
				</tr>
				<c:set var="counter" value="${counter + 1}" scope="page"/>
			</c:forEach>
			</table>
		</div></div>
		<% if(user.isRUP()){ %>
		<table align="left" width="300px">
		  <tr><td nowrap="nowrap" class="hmenu">  
			 <a title="<utils:message key="multilotto.modificaContrattoMultilotto" />" href="<%=ParametriServlet.SRV_EDIT_MULTILOTTO %>?<%=ParametriServlet.FIELD_NAME_ID_LOTTO %>=<%=idLotto %>&<%=PSBD.FIELD_NAME_CODICE_CONTRATTO%>=${listaAggiudicazioni[0].codiceContratto}"><utils:message key="multilotto.modificaContrattoMultilotto" /></a>			    
		  </td></tr>
		</table>
		<% } %>
		</fieldset>
		</div>
		</div>
		<%@ include file="include/newfooter.inc" %>
	</div>
</body>

</html>
