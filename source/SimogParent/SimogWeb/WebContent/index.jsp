<%@page import="it.avlp.simog.db.SimogFlags"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>
<%@page import="it.avlp.simog.util.SimogProperties"%>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<script type="text/javascript">
<!--

// check auth type
function chkAuth()
{
<% if(SimogFlags.is3024IAMActive()){ %>
		location.replace("<%= SimogProperties.getInstance().getSamlLoginUrl() %>");
<% } else {%>
		location.replace("<%= request.getContextPath() %>/login.jsp");
<% } %> 	
}

//-->
</script>
</head>
<body onload="chkAuth()">
<center>
	<br><br><br><br><br><br><br>
	<h1><utils:message key="index.connessioneInCorso" /></h1>
</center>
</body>
</html>
  