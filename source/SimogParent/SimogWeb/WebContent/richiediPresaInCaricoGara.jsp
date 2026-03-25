<%@ page contentType="text/html; charset=UTF-8" language="java"  %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>

<%@ include file="/include/newbasicHeader.inc" %>

<title><utils:message key="presaInCarico.gestioneGaraPresaInCarico" /></title>
</head>
<%@ include file="/include/controlloSessione.inc" %>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServletRubrica"%>

<%@ include file="include/i18n-init.inc" %>
<%@ include file="/script/script.js" %>
<body>
	<div id="gabbia">
		<%@ include file="/include/header.inc" %>
		<div id="bodypage">
			<div class="bodypage-e">
				<form name="gestioneTab" action="presaInCaricoGara"  method="post" onkeypress="setFormModified('Modificato')">
					<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_GARA %>" 
						id="<%= ParametriServlet.FIELD_NAME_ID_GARA %>" 
						value="<%= request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA) %>">
					
					<h1><utils:message key="presaInCarico.gestioneGaraPresaInCarico" /></h1>
					<%@ include file="/include/gestisciErrore.inc" %>
					<br>
					<fieldset>
						<fieldset>
							<table width="100%">
								<tr>
									<th><label for=""><utils:message key="presaInCarico.estremiProvvedimento" /></label></th>
									<td>
										<textarea maxlength="250" rows="5" cols="40" name="<%= PSBD.MOTIVAZIONE_ANNULLAMENTO %>" id="<%= PSBD.MOTIVAZIONE_ANNULLAMENTO %>"></textarea>
									</td>
								</tr>
							</table>
						</fieldset>
						<input type="hidden" name="Modificato" value="0">
						<input type="submit" name="toDo" value="<utils:message key="button.conferma" plain="true" />">
						<input type="button" value="<utils:message key="button.torna" plain="true" />" onclick="javascript:history.back(-1)">
						<input type="reset" value="<utils:message key="button.reimposta" plain="true" />" onclick="reimposta()">					
					</fieldset>
				</form>
			</div>
		</div>
		<%@ include file="/include/newfooter.inc" %>
		
	</div>
</body>	
