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
<title><utils:message key="dati.schedaSubappalto" /></title>
</head>
<body>
	<c:set var="id" value="<%= request.getParameter("idscheda") %>"></c:set>
	<c:set var="data" value="<%= request.getParameter("datascheda") %>"></c:set>
	<c:set var="lista" value="${sessionScope['listaSubappalti']}"></c:set>
	<c:forEach items="${lista}" var="scheda">
		<c:if test="${scheda.idRecord == id && scheda.dataInizioRecord == data}">
			<c:set var="subappalto" value="${scheda}"></c:set>
		</c:if>
	</c:forEach>
	<table width="100%" cellpadding="5">
		<tr>
			<td align="center" colspan="2"><p class="detailHelp"><strong><utils:message key="scheda.subappalti" /></strong></p></td>
		</tr>
		<tr>
			<td>
				<label><utils:message key="dati.codiceFiscaleDitta" /></label>
			</td>
			<td align="center">
				<c:out value="${subappalto.cfDitta}"></c:out>
			</td>
		</tr>
		<!-- MEV 36771 3.04.8.1 -->
		<tr>
			<td>
				<label><utils:message key="dati.dittaSubappaltatriceEstera" /></label>
			</td>
			<td align="center">
				<c:out value="${subappalto.flagDittaSubEstera}"></c:out>
			</td>
		</tr>
		<!--fine MEV 36771 3.04.8.1 -->
		<tr>
			<td>
				<label><utils:message key="dati.dataAutorizzazione" /></label>
			</td>
			<td align="center">
				<c:out value="${subappalto.dataAutorizzazione}"></c:out>
			</td>
		</tr>
		<tr>
			<td>
				<label><utils:message key="dati.oggettoSubappalto" /></label>
			</td>
			<td align="center" width="50%">
				<c:out value="${subappalto.oggettoSubappalto}"></c:out>
			</td>
		</tr>
		<tr>
			<td>
				<label>Importo presunto</label>
			</td>
			<td align="center">
				<c:out value="${subappalto.importoPresunto}"></c:out>
			</td>
		</tr>
		<tr>
			<td>
				<label><utils:message key="dati.cpv" /></label>
			</td>
			<td align="center">
				<c:out value="${subappalto.idCpv}"></c:out>
			</td>
		</tr>
		<tr>
			<td>
				<label><utils:message key="dati.importoEffettivo" /></label>
			</td>
			<td align="center">
				<c:out value="${subappalto.importoEffettivo}"></c:out>
			</td>
		</tr>
	</table>
</body>
</html>