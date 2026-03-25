<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
	<tr><td colspan="2"><hr></td></tr>
	<tr>	
	<th><label><utils:message key="intestazione.tipoScheda" /></label></th>
	<td>
	<c:choose>
	<c:when test="${datiGara.tipoContratto == 'S' }"><utils:message key="tipo.servizi" /></c:when>
	<c:when test="${datiGara.tipoContratto == 'F' }"><utils:message key="tipo.forniture" /></c:when>
	<c:when test="${datiGara.tipoContratto == 'L' }"><utils:message key="tipo.lavori" /></c:when>
	</c:choose>
	<c:choose>
	<c:when test="${datiGara.tipoEnte == 'S' }"><utils:message key="tipo.settoriSpeciali" /> </c:when>
	<c:when test="${datiGara.tipoEnte == 'O' }"><utils:message key="tipo.settoriOrdinari" /> </c:when>
	</c:choose>	</p>
	</td>
   </tr>
	<tr>
	<th><label ><utils:message key="intestazione.oggettoAppalto" /></label></th>
	<td > <c:out value="${datiGara.oggettoLotto}"></c:out></td>
	</tr>
	<tr> 
	<th><label ><utils:message key="intestazione.numeroCPV" /></label></th>
	<td> <c:out value="${datiGara.idCPV} -- ${datiGara.descrizioneCPV }"></c:out></td>
	</tr>
	<tr>
	<th><label ><utils:message key="intestazione.cfAmministrazione" /></label></th>
	<td><c:out value="${datiGara.cfAmministrazione}" ></c:out></td>
	</tr>
	<tr>
	<th><label ><utils:message key="intestazione.denomAmministrazione" /></label></th>
	<td><c:out value="${datiGara.denomAmministrazione}"></c:out></td>
	</tr>
	<tr>
	<th><label><utils:message key="intestazione.numeroGara" /></label></th>
	<td > <c:out value="${datiGara.idGara}"></c:out></td>
	</tr>
	<c:if test="${aggiudicazione.idAggiudicazione ge 1}">
	<tr>
	<td><label><utils:message key="intestazione.cigAggiudicazione" /></label></td>
	<td><c:out value="${datiGara.fullCIG}"/><c:out value="-${aggiudicazione.progCUI}" /></td>
	</tr>
	</c:if>
	<c:if test="${aggiudicazione.idAggiudicazione le 0}">
	<tr>
	<td><label><utils:message key="intestazione.cigAppalto" /></label></td>
	<td><c:out value="${datiGara.fullCIG}"/></td>
	</tr>
	</c:if>
	<tr><td colspan="2"><hr></td></tr>