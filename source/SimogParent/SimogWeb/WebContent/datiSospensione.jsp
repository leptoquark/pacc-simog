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
<title><utils:message key="dati.schedaSospensione" /></title>
</head>
<body>
	<c:set var="id" value="<%= request.getParameter("idscheda") %>"></c:set>
	<c:set var="data" value="<%= request.getParameter("datascheda") %>"></c:set>
	<c:set var="lista" value="${sessionScope['listaSospensioni']}"></c:set>
	<c:forEach items="${lista}" var="scheda">
		<c:if test="${scheda.idSospensione == id && scheda.dataInizioSosp == data}">
			<c:set var="sospensione" value="${scheda}"></c:set>
		</c:if>
	</c:forEach>
	<table width="100%" cellpadding="5" >
		<tr>
			<td align="center" colspan="2"><p class="detailHelp"><strong><utils:message key="scheda.sospensioni" /></strong></p></td>
		</tr>
		<tr>
			<td>
				<label><utils:message key="dati.dataVerbaleSospensione" /></label>
			</td>
			<td align="center">
				<c:out value="${sospensione.dataVerbSosp}"></c:out>
			</td>
		</tr>
		<tr>
			<td>
				<label><utils:message key="dati.dataVerbaleRipresa" /></label>
			</td>
			<td align="center">
				<c:out value="${sospensione.dataVerbRipr}"></c:out>
			</td>
		</tr>
		<tr>
			<td>
				<label><utils:message key="dati.motivazioneSospensione" /></label>
			</td>
			<td align="center" width="50%">
				<c:out value="${sospensione.descrizioneMotivo}"></c:out>
			</td>
		</tr>
		<tr>
			<td>
				<label>&Egrave; stato superato il quarto di tempo contrattuale</label>
			</td>
			<td align="center">
				<c:out value="${sospensione.flagSuperoTemp eq 'N' ? 'NO' : 'SI'}"></c:out>
			</td>
		</tr>
		<tr>
			<td>
				<label><utils:message key="dati.iscrizioneRiserveAppaltatore" /></label>
			</td>
			<td align="center">
				<c:out value="${sospensione.flagRiserve eq 'N' ? 'NO' : 'SI'}"></c:out>
			</td>
		</tr>
		<tr>
			<td>
				<label><utils:message key="dati.verbaleNonSottoscritto" /></label>
			</td>
			<td align="center">
				<c:out value="${sospensione.flagVerbale eq 'N' ? 'NO' : 'SI'}"></c:out>
			</td>
		</tr>
	</table>
</body>
</html>