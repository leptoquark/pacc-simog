<%@ page language="java" contentType="text/html; charset=UTF-8"
    errorPage="errore.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>

<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<title><utils:message key="dati.schedaVariante" /> <%= request.getParameter("label") %></title>
</head>
<body>
	<c:set var="id" value="<%= request.getParameter("idscheda") %>"></c:set>
	<c:set var="data" value="<%= request.getParameter("datascheda") %>"></c:set>
	<c:set var="lista" value="${sessionScope['listaVarianti']}"></c:set>
	<c:forEach items="${lista}" var="scheda">
		<c:if test="${scheda.idVariante == id && scheda.dataInizioVar == data}">
			<c:set var="variante" value="${scheda}"></c:set>
		</c:if>
	</c:forEach>
	<table width="100%" cellpadding="5">
		<tr>
			<td align="center" colspan="2"><p class="detailHelp"><strong><%= (String)request.getParameter("label").toUpperCase() %></strong></p></td>
		</tr>
		<tr>
			<td>
				<label>Data di approvazione della <%= request.getParameter("label") %></label>
			</td>
			<td align="center">
				<c:out value="${variante.dataVerbaleApprovazione}"></c:out>
			</td>
		</tr>
		<tr>
			<td>
				<label><utils:message key="dati.motivazioniVariante" /> <%= request.getParameter("label") %></label>
			</td>
			<td align="center">
				<table>
					<c:forEach items="${variante.emvb}" var="motivo">
						<tr>
							<td><c:out value="${motivo.descrizione}"></c:out>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<label><utils:message key="dati.causeVariante" /> <%= request.getParameter("label") %></label>
			</td>
			<td align="center" width="50%">
				<c:out value="${variante.altreMotivazioni}"></c:out>
			</td>
		</tr>
		<tr>
			<td>
				<label><utils:message key="dati.importoLavori" /></label>
			</td>
			<td align="center">
				<c:out value="${variante.impRidetLavori}"></c:out>
			</td>
		</tr>
		<tr>
			<td>
				<label><utils:message key="dati.importoServizi" /></label>
			</td>
			<td align="center">
				<c:out value="${variante.impRidetServizi}"></c:out>
			</td>
		</tr>
		<tr>
			<td>
				<label><utils:message key="dati.importoForniture" /></label>
			</td>
			<td align="center">
				<c:out value="${variante.impRidetFornit}"></c:out>
			</td>
		</tr>
		<tr>
			<td>
				<label>Importo totale per l'attuazione della sicurezza</label>
			</td>
			<td align="center">
				<c:out value="${variante.impSicurezza}"></c:out>
			</td>
		</tr>
		<tr>
			<td>
				<label><utils:message key="dati.importoProgettazione" /></label>
			</td>
			<td align="center">
				<c:out value="${variante.impProgettazione}"></c:out>
			</td>
		</tr>
		<tr>
			<td>
				<label><utils:message key="dati.importoSommeDisposizione" /></label>
			</td>
			<td align="center">
				<c:out value="${variante.impDisposizione}"></c:out>
			</td>
		</tr>
	</table>
</body>
</html>