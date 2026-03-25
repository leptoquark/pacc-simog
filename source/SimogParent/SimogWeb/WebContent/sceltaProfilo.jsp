<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="../errore.jsp" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>
<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.avlp.simog.util.MessageHelper" %>

<%--Scelta profilo --%>
 
<link rel="stylesheet" href="theme/tabmenu.css"/>

<script type="text/javascript"  src="script/pageutils.js"></script>

<script type="text/javascript">
//<!--
function scegli(){
	document.forms[0].submit();
}
function selscegli(nome){
	var obj = document.getElementById(nome);
	obj.checked=true;
	
	document.forms[0].submit();
}
//-->
</script>

<title><utils:message key="profilo.selezioneProfilo" /></title>

</head>
<%try{ %>
<body>
<div id="gabbia">  
<div id="header"></div>
	<c:set var="utenteBean" scope="page" value="${sessionScope['UTENTE']}"></c:set>
		
	<div id="bodypage">	
		<div class="bodypage-e">
			<h4><utils:message key="profilo.selezioneProfilo" /></h4>
			<%@ include file="include/gestisciErrore.inc" %>
			<p><utils:message key="profilo.selezionareProfilo" /></p>
			<!-- value="${param['profiloId']}" -->
			<c:if test="${empty utenteBean.profili}" >
			
				<c:choose>
					<c:when test="${utenteBean.RUP}">
						<jsp:forward page="<%=ParametriServlet.JSP_RUP_CS_HOME %>"></jsp:forward>
					</c:when>
					<c:when test="${utenteBean.amministratore}">
						<jsp:forward page="<%=ParametriServlet.JSP_AMM_HOME %>"></jsp:forward>
					</c:when>
					<c:when test="${utenteBean.RSSA}">
						<jsp:forward page="<%=ParametriServlet.JSP_RSSA_HOME %>"></jsp:forward>
					</c:when>
					<c:when test="${utenteBean.AVLP}">
						<jsp:forward page="<%=ParametriServlet.JSP_AVCP_HOME %>"></jsp:forward>
					</c:when>
					<c:when test="${utenteBean.ossReg}">
						<jsp:forward page="<%=ParametriServlet.JSP_OSSREG_HOME %>"></jsp:forward>
					</c:when>
					<c:otherwise>
						<jsp:forward page="<%=ParametriServlet.JSP_ERRORE %>"></jsp:forward>
					</c:otherwise>
				</c:choose>
			</c:if> 
	
			<form name="frmProfilo" method="post" action="srvSceltaProfilo">
				<fieldset id="fldProfilo">
					
					<legend><utils:message key="profilo.elencoProfili" /></legend>
					
					<c:set var="listaProfili" value="${utenteBean.profili}" scope="page"></c:set>
					<table>
						<c:forEach var="profilo" items="${listaProfili}">
							<tr>
								<td style="color:gray; font-size:9; font-weight: bold" ondblclick="javascript:selscegli('radio_${profilo.key}');">
									<input id="radio_${profilo.key}" ondblclick="javascript:scegli();" type="radio" name="<%=ParametriServlet.SCELTA_PROFILO%>"  value="<c:out value="${profilo.key}" />" /> 
									<%
									java.util.Map.Entry entry = (java.util.Map.Entry) pageContext.getAttribute("profilo");
									String keyProfilo = entry != null ? "profilo." + entry.getKey() : "";
									String descProfilo = entry != null ? MessageHelper.getMessage(request, keyProfilo) : "";
									if (descProfilo == null || descProfilo.isEmpty() || descProfilo.equals(keyProfilo)) {
										descProfilo = entry != null ? MessageHelper.getMessage(new java.util.Locale("it"), keyProfilo) : "";
									}
									if (descProfilo == null || descProfilo.isEmpty() || descProfilo.equals(keyProfilo)) {
										descProfilo = entry != null ? (String) entry.getValue() : "";
									}
									%><%= descProfilo %>
									<br><br> 
								</td>
							</tr>	
						</c:forEach>
					</table>
					
				</fieldset>
			<input type="submit" value="<utils:message key="button.accedi" plain="true" />"/>
			</form>
		</div>		
	</div>
	<%@ include file="include/newfooter.inc" %>
</div>
</body>

<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%}catch(Exception e){
	e.printStackTrace();}%>
}
</html>
